package ru.austeretony.attributes.player;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import ru.austeretony.attributes.main.AttributesMain;
import ru.austeretony.attributes.main.PotionsRegistry;

public class CurrencyHandler {

	public static final int CURRENCY_MAX = 100000;
	
	//Возвращает текущее значение запаса монет.
    public static int getCurrency(EntityPlayer player) {
    	
        return player.getDataWatcher().getWatchableObjectInt(PropertiesRegistry.CURRENCY);
    }
    
    //Устанавливает текущее значение запаса монет.
	public static void setCurrency(EntityPlayer player, int value) {	
		
		player.getDataWatcher().updateObject(PropertiesRegistry.CURRENCY, value);
	}
	
	//Добавление монет.
	public static void addCurrency(EntityPlayer player, int value) {
		
		int val = isCurrencyLootBoosted(player) ? value * 2 : value;//Кол-во монет зависит наличия буста.
		
		player.getDataWatcher().updateObject(PropertiesRegistry.CURRENCY, MathHelper.clamp_int(getCurrency(player) + val, 0, CURRENCY_MAX));
		
		player.addChatMessage(new ChatComponentTranslation("currency.recieved", val));//Вывод сообщения с кол-вом полученных монет.
	}
	
	//Уменьшение запаса монет.
	public static void removeCurrency(EntityPlayer player, int value) {
		
		player.getDataWatcher().updateObject(PropertiesRegistry.CURRENCY, MathHelper.clamp_int(getCurrency(player) - value, 0, CURRENCY_MAX));
	}
	
	//Возращает значение объекта CURRENCY_BOOSTED (ускоренный сбор монет).
	public static boolean isCurrencyLootBoosted(EntityPlayer player) {
		
        return player.getDataWatcher().getWatchableObjectByte(PropertiesRegistry.CURRENCY_LOOT_BOOST) == 1 ? true : false;
	}
	
	//Устанавливает значение объекта CURRENCY_BOOSTED (ускоренный сбор монет).
	public static void setCurrencyLootBoost(EntityPlayer player, boolean isBoosted) {

		player.getDataWatcher().updateObject(PropertiesRegistry.CURRENCY_LOOT_BOOST, (byte) (isBoosted ? 1 : 0));
	}
	
	//Сохранение значения запаса монет в NBT игрока.
	public static void saveCurrencyToNBT(EntityPlayer player) {
		
		player.getEntityData().setInteger(AttributesMain.MODID + ":currency", getCurrency(player));
	}
	
	//Загрузка из NBT.
	public static int loadCurrencyFromNBT(EntityPlayer player) {
		
		return player.getEntityData().hasKey(AttributesMain.MODID + ":currency") ? player.getEntityData().getInteger(AttributesMain.MODID + ":currency") : 0;
	}
	
	public static void saveCurrencyBoostToNBT(EntityPlayer player) {
		
		player.getEntityData().setBoolean(AttributesMain.MODID + ":currencyBoost", isCurrencyLootBoosted(player));
	}
	
	public static boolean loadCurrencyBoostFromNBT(EntityPlayer player) {
		
		return player.getEntityData().hasKey(AttributesMain.MODID + ":currencyBoost") ? player.getEntityData().getBoolean(AttributesMain.MODID + ":currencyBoost") : false;
	}
	
	@SubscribeEvent
	public void onPlayerLoad(PlayerEvent.LoadFromFile event) {
		
		//Загрузка значения запаса монет из NBT при входе на сервер (срабатывает для физического и логического серверов).
		setCurrency(event.entityPlayer, loadCurrencyFromNBT(event.entityPlayer));
		setCurrencyLootBoost(event.entityPlayer, loadCurrencyBoostFromNBT(event.entityPlayer));
	}
	
    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
    	
    	AttributesMain.logger().info("Cloning player...");
    	    	    			    	
    	setCurrency(event.entityPlayer, getCurrency(event.original));//Перенос запаса монет для новой сущности игрока при клонировании (например в результате смерти).
    	
    	saveCurrencyToNBT(event.entityPlayer);//Сохранение запаса монет для новой сущности.
    }
    
	@SubscribeEvent
	public void onPlayerSave(PlayerEvent.SaveToFile event) {
				
		//Сохранение значения запаса монет в NBT игрока при выходе.
		saveCurrencyToNBT(event.entityPlayer);
		saveCurrencyBoostToNBT(event.entityPlayer);
	}
	
	@SubscribeEvent
	public void onPlayerEat(PlayerUseItemEvent.Tick event) {
		
		if (event.entityLiving instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) event.entityLiving;	
			
			if (player.getItemInUseCount() == 1) {				
				
				if (event.item.getItem() == Items.golden_apple || event.item.getItemDamage() == 1) {
					
					player.addPotionEffect(new PotionEffect(PotionsRegistry.LOOTING.getId(), 6000));//Эффект "Грабёж" на 5 минут при съедании зачарованного яблока.
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		
		if (event.source.getEntity() instanceof EntityPlayer) {
			
			addCurrency((EntityPlayer) event.source.getEntity(), 6);//Добавление игроку 6 монет игроку при убийстве любого моба.
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
    public void onRenderInventoryPost(GuiScreenEvent.DrawScreenEvent.Post event) {	
		
		if (event.gui instanceof GuiInventory) {
			
	        ScaledResolution resolution = new ScaledResolution(event.gui.mc, event.gui.mc.displayWidth, event.gui.mc.displayHeight);
	        
	        int 
	        i = resolution.getScaledWidth() / 2 + 35,
	        j = resolution.getScaledHeight() / 2 - 22;
	        
	        GL11.glDisable(GL11.GL_LIGHTING);			
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						
			event.gui.mc.getTextureManager().bindTexture(new ResourceLocation(AttributesMain.MODID, "textures/potions/looting.png"));
			Gui.func_146110_a(i, j, 0, 0, 18, 18, 18, 18);	
						
			event.gui.mc.fontRenderer.drawString(String.valueOf(getCurrency(event.gui.mc.thePlayer)), i + 16, j + 6, 0x303030);
		}
	}
}
