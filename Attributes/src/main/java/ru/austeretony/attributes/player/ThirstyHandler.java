package ru.austeretony.attributes.player;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.austeretony.attributes.main.AttributesMain;
import ru.austeretony.attributes.main.PotionsRegistry;
import ru.austeretony.attributes.player.gui.ThirstyBarRenderer;

@Mod.EventBusSubscriber(modid = AttributesMain.MODID)
public class ThirstyHandler {
		
	//Возвращает макс. значение жажды.
	public static float getThirstyMax(EntityPlayer player) {		
		
		return (float) player.getEntityAttribute(PropertiesRegistry.THIRSTY_MAX).getAttributeValue();				
	}
	
	//Возвращает текущее значение жажды.
    public static float getThirsty(EntityPlayer player) {
    	    	
        return player.getDataManager().get(PropertiesRegistry.THIRSTY);
    }
	
    //Устанавливает текущее значение жажды.
	public static void setThirsty(EntityPlayer player, float value) {	
		
		player.getDataManager().set(PropertiesRegistry.THIRSTY, MathHelper.clamp(value, 0.0F, getThirstyMax(player)));
	}
	
	//Сбрасывает значение жажды.
	public static void refillThirsty(EntityPlayer player) {	
		
		player.getDataManager().set(PropertiesRegistry.THIRSTY, getThirstyMax(player));
	}
	
	//Уменьшает жажду на указанную величину.
	public static void decreaseThirsty(EntityPlayer player, float value) {
		
		player.getDataManager().set(PropertiesRegistry.THIRSTY, MathHelper.clamp(getThirsty(player) + value, 0.0F, getThirstyMax(player)));	
	}
	
	//Увеличивает жажду на указанную величину.
	public static void increaseThirsty(EntityPlayer player, float value) {

		player.getDataManager().set(PropertiesRegistry.THIRSTY, MathHelper.clamp(getThirsty(player) - value, 0.0F, getThirstyMax(player)));
	}
	
	//Сохранение значения жажды в NBT игрока.
	//Желательно сохранять значение при каждом изменении, иначе в случае аварийного завершения 
	//игры (к примеру комбинацией Alt+F4) данные не будут сохранены (PlayerLoggedOutEvent не сработает).
	private static void saveThirstyToNBT(EntityPlayer player) {
		
		player.getEntityData().setFloat(AttributesMain.MODID + ":thirsty", getThirsty(player));
	}
	
	//Загрузка жажды из NBT.
	private static float loadThirstyFromNBT(EntityPlayer player) {
		
		return player.getEntityData().hasKey(AttributesMain.MODID + ":thirsty") ? player.getEntityData().getFloat(AttributesMain.MODID + ":thirsty") : getThirstyMax(player);
	}
	
	@SubscribeEvent
	public static void onPlayerLogIn(PlayerLoggedInEvent event) {
		
		AttributesMain.logger().info("Player logging in...");
							
		//Загрузка значения жажды из NBT при входе на сервер (срабатывает для физического и логического серверов).
		setThirsty(event.player, loadThirstyFromNBT(event.player));
	}
	
	@SubscribeEvent
	public static void onPlayerLogOut(PlayerLoggedOutEvent event) {
		
		AttributesMain.logger().info("Player logging out...");
		
		//Сохранение значения жажды в NBT игрока при выходе.
		saveThirstyToNBT(event.player);
	}
    
	@SubscribeEvent
	public static void onPlayerUpdate(LivingUpdateEvent event) {
		
		if (event.getEntityLiving() instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();	

			if (!player.world.isRemote) {
			
				if (player.isSprinting()) {
															
					if (player.ticksExisted % 60 == 0) {
						
						increaseThirsty(player, 1.0F);//Увеличение жажды при беге каждые 3 сек. на 1 ед.
					}
				}
				
				else if (player.isSneaking()) {
					
					if (player.ticksExisted % 120 == 0) {
						
						increaseThirsty(player, 1.0F);//Увеличение жажды в присяди каждые 6 сек. на 1 ед.
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerEat(LivingEntityUseItemEvent.Tick event) {
		
		if (event.getEntityLiving() instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();	
			
			if (player.getItemInUseCount() == 1) {
						
				if (event.getItem().getItem() == Items.POTIONITEM) {
					
					decreaseThirsty(player, 7.0F);//Уменьшение жажды при выпивании зелья на 3.5 ед.
				}
				
				else if (event.getItem().getItem() == Items.MELON) {
					
					decreaseThirsty(player, 3.0F);//Уменьшение жажды при съедании дольки арбуза на 1.5 ед.
				}
				
				else if (event.getItem().getItem() == Items.APPLE) {
					
					decreaseThirsty(player, 2.0F);//Уменьшение жажды при съедании яблока на 2 ед.
				}
				
				else if (event.getItem().getItem() == Items.GOLDEN_APPLE || event.getItem().getMetadata() == 1) {
										
					player.addPotionEffect(new PotionEffect(PotionsRegistry.WETTING, 6000));//Эффект "Насыщение" на 5 минут при съедании зачарованного яблока.
					
					decreaseThirsty(player, 10.0F);//Уменьшение жажды при съедании зачарованного яблока на 10 ед.
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		
		if (event.getEntityLiving() instanceof EntityPlayer) {
			
			if (event.getEntityLiving().isPotionActive(PotionsRegistry.WETTING)) {
									
				//Требуется для синхронизации максимального значения с клиентом.
				event.getEntityLiving().getActivePotionEffect(PotionsRegistry.WETTING).getPotion().removeAttributesModifiersFromEntity(event.getEntityLiving(), event.getEntityLiving().getAttributeMap(), 0);;		
			}
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
    public static void onRenderOverlayPre(RenderGameOverlayEvent.Pre event) {	
		
		if (event.getType() == ElementType.AIR) {
			
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, - 11.0F, 0.0F);//Смещение полоски запаса воздуха на 11 пикселей выше стандартной позиции.
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
    public static void onRenderOverlayPost(RenderGameOverlayEvent.Post event) {			
		
		if (event.getType() == ElementType.AIR) {
			
			GlStateManager.popMatrix();
		}
		
		if (event.getType() == ElementType.TEXT) {
			
			ThirstyBarRenderer.getInstance().renderThirstyBar();//Отрисовка полоски жажды.
		}
	}
}
