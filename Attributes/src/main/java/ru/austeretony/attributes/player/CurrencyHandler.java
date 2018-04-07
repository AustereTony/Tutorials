package ru.austeretony.attributes.player;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.austeretony.attributes.main.AttributesMain;
import ru.austeretony.attributes.main.PotionsRegistry;

@Mod.EventBusSubscriber(modid = AttributesMain.MODID)
public class CurrencyHandler {

	public static final int CURRENCY_MAX = 100000;
	
	//Возвращает текущее значение запаса монет.
    public static int getCurrency(EntityPlayer player) {
    	
        return player.getDataManager().get(PropertiesRegistry.CURRENCY);
    }
    
    //Устанавливает текущее значение запаса монет.
	public static void setCurrency(EntityPlayer player, int value) {	
		
		player.getDataManager().set(PropertiesRegistry.CURRENCY, value);
	}
	
	//Добавление монет.
	public static void addCurrency(EntityPlayer player, int value) {
		
		int val = isCurrencyLootBoosted(player) ? value * 2 : value;//Кол-во монет зависит наличия буста.
		
		player.getDataManager().set(PropertiesRegistry.CURRENCY, MathHelper.clamp(getCurrency(player) + val, 0, CURRENCY_MAX));
		
		player.sendMessage(new TextComponentTranslation("currency.recieved", val));//Вывод сообщения с кол-вом полученных монет.
	}
	
	//Уменьшение запаса монет.
	public static void removeCurrency(EntityPlayer player, int value) {
		
		player.getDataManager().set(PropertiesRegistry.CURRENCY, MathHelper.clamp(getCurrency(player) - value, 0, CURRENCY_MAX));
	}
	
	//Возращает значение объекта CURRENCY_BOOSTED.
	public static boolean isCurrencyLootBoosted(EntityPlayer player) {
		
        return player.getDataManager().get(PropertiesRegistry.CURRENCY_LOOT_BOOST);
	}
	
	//Устанавливает значение объекта CURRENCY_BOOSTED (ускоренный сбор монет).
	public static void setCurrencyLootBoost(EntityPlayer player, boolean isBoosted) {

		player.getDataManager().set(PropertiesRegistry.CURRENCY_LOOT_BOOST, isBoosted);
	}
	
	//Сохранение значения запаса монет в NBT игрока.
	//Желательно сохранять значение при каждом изменении, иначе в случае аварийного завершения 
	//игры (к примеру комбинацией Alt+F4) данные не будут сохранены (PlayerLoggedOutEvent не сработает).
	private static void saveCurrencyToNBT(EntityPlayer player) {
		
		player.getEntityData().setInteger(AttributesMain.MODID + ":currency", getCurrency(player));
	}
	
	//Загрузка из NBT.
	private static int loadCurrencyFromNBT(EntityPlayer player) {
		
		return player.getEntityData().hasKey(AttributesMain.MODID + ":currency") ? player.getEntityData().getInteger(AttributesMain.MODID + ":currency") : 0;
	}
	
	private static void saveCurrencyBoostToNBT(EntityPlayer player) {
		
		player.getEntityData().setBoolean(AttributesMain.MODID + ":currencyBoost", isCurrencyLootBoosted(player));
	}
	
	private static boolean loadCurrencyBoostFromNBT(EntityPlayer player) {
		
		return player.getEntityData().hasKey(AttributesMain.MODID + ":currencyBoost") ? player.getEntityData().getBoolean(AttributesMain.MODID + ":currencyBoost") : false;
	}
	
	@SubscribeEvent
	public static void onPlayerLogIn(PlayerLoggedInEvent event) {
						
		setCurrency(event.player, loadCurrencyFromNBT(event.player));//Загрузка значения запаса монет из NBT при входе на сервер (срабатывает для физического и логического серверов).
		setCurrencyLootBoost(event.player, loadCurrencyBoostFromNBT(event.player));
	}
	
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
    	    			    	
    	setCurrency(event.getEntityPlayer(), getCurrency(event.getOriginal()));//Перенос запаса монет для новой сущности игрока при клонировании (например в результате смерти).
    	
    	saveCurrencyToNBT(event.getEntityPlayer());//Сохранение запаса монет для новой сущности.
    }
	
	@SubscribeEvent
	public static void onPlayerLogOut(PlayerLoggedOutEvent event) {
						
		saveCurrencyToNBT(event.player);//Сохранение значения запаса монет в NBT игрока при выходе.
    	saveCurrencyBoostToNBT(event.player);
	}
	
	@SubscribeEvent
	public static void onPlayerEat(LivingEntityUseItemEvent.Tick event) {
		
		if (event.getEntityLiving() instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();	
			
			if (player.getItemInUseCount() == 1) {				
				
				if (event.getItem().getItem() == Items.GOLDEN_APPLE || event.getItem().getMetadata() == 1) {
					
					player.addPotionEffect(new PotionEffect(PotionsRegistry.LOOTING, 6000));//Эффект "Грабёж" на 5 минут при съедании зачарованного яблока.
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		
		if (event.getSource().getImmediateSource() instanceof EntityPlayer) {
			
			addCurrency((EntityPlayer) event.getSource().getImmediateSource(), 6);//Добавление игроку 6 монет игроку при убийстве любого моба.
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
    public static void onRenderInventoryPost(GuiScreenEvent.DrawScreenEvent.Post event) {	
		
		if (event.getGui() instanceof GuiInventory) {
			
	        ScaledResolution resolution = new ScaledResolution(event.getGui().mc);
	        
	        int 
	        i = resolution.getScaledWidth() / 2 + 35,
	        j = resolution.getScaledHeight() / 2 - 22;
	        
	        GlStateManager.disableLighting();
			
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
						
			event.getGui().mc.getTextureManager().bindTexture(new ResourceLocation(AttributesMain.MODID, "textures/potions/looting.png"));
			Gui.drawModalRectWithCustomSizedTexture(i, j, 0, 0, 18, 18, 18, 18);	
			
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			
			event.getGui().mc.fontRenderer.drawString(String.valueOf(getCurrency(event.getGui().mc.player)), i + 16, j + 6, 0x303030);
		}
	}
}
