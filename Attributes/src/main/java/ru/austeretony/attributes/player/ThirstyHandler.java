package ru.austeretony.attributes.player;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import ru.austeretony.attributes.main.AttributesMain;
import ru.austeretony.attributes.main.PotionsRegistry;
import ru.austeretony.attributes.player.gui.ThirstyBarRenderer;

public class ThirstyHandler {
		
	//Возвращает макс. значение жажды.
	public static float getThirstyMax(EntityPlayer player) {		
		
		return (float) player.getEntityAttribute(PropertiesRegistry.THIRSTY_MAX).getAttributeValue();				
	}
	
	//Возвращает текущее значение жажды.
    public static float getThirsty(EntityPlayer player) {
    	    	
        return player.getDataWatcher().getWatchableObjectFloat(PropertiesRegistry.THIRSTY);
    }
	
    //Устанавливает текущее значение жажды.
	public static void setThirsty(EntityPlayer player, float value) {	
		
		player.getDataWatcher().updateObject(PropertiesRegistry.THIRSTY, MathHelper.clamp_float(value, 0.0F, getThirstyMax(player)));
	}
	
	//Сбрасывает значение жажды.
	public static void refillThirsty(EntityPlayer player) {	
		
		player.getDataWatcher().updateObject(PropertiesRegistry.THIRSTY, getThirstyMax(player));
	}
	
	//Уменьшает жажду на указанную величину.
	public static void decreaseThirsty(EntityPlayer player, float value) {
		
		player.getDataWatcher().updateObject(PropertiesRegistry.THIRSTY, MathHelper.clamp_float(getThirsty(player) + value, 0.0F, getThirstyMax(player)));	
	}
	
	//Увеличивает жажду на указанную величину.
	public static void increaseThirsty(EntityPlayer player, float value) {

		player.getDataWatcher().updateObject(PropertiesRegistry.THIRSTY, MathHelper.clamp_float(getThirsty(player) - value, 0.0F, getThirstyMax(player)));
	}
	
	//Сохранение значения жажды в NBT игрока.
	public static void saveThirstyToNBT(EntityPlayer player) {
		
		player.getEntityData().setFloat(AttributesMain.MODID + ":thirsty", getThirsty(player));
	}
	
	//Загрузка жажды из NBT.
	public static float loadThirstyFromNBT(EntityPlayer player) {
		
		return player.getEntityData().hasKey(AttributesMain.MODID + ":thirsty") ? player.getEntityData().getFloat(AttributesMain.MODID + ":thirsty") : getThirstyMax(player);
	}
	
	@SubscribeEvent
	public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
		
		if (event.entity instanceof EntityPlayer) {
			
			AttributesMain.logger().info("Player join world...");
			
			EntityPlayer player = (EntityPlayer) event.entity;	
			
			if (!player.worldObj.isRemote) {
								
				//Симуляция изменения значения для принудительной 
				//синхронизации текущего параметра жажды с клиентом
				//при переходе между мирами.
				if (getThirsty(player) < getThirstyMax(player)) {
					
					setThirsty(player, getThirsty(player) + 1.0F);
					setThirsty(player, getThirsty(player) - 1.0F);
				}
			}
		}
	}
    
	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event) {
		
		if (event.entityLiving instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) event.entityLiving;	

			if (!player.worldObj.isRemote) {
			
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
	public void onPlayerLoad(PlayerEvent.LoadFromFile event) {
		
		AttributesMain.logger().info("Player loading from file...");
		
		//Загрузка значения жажды из NBT при входе на сервер (срабатывает для физического и логического серверов).
		setThirsty(event.entityPlayer, loadThirstyFromNBT(event.entityPlayer));
	}
	
	@SubscribeEvent
	public void onPlayerSave(PlayerEvent.SaveToFile event) {
		
		AttributesMain.logger().info("Player saving to file...");
		
		//Сохранение значения жажды в NBT игрока при выходе.
		saveThirstyToNBT(event.entityPlayer);
	}
	
	@SubscribeEvent
	public void onPlayerEat(PlayerUseItemEvent.Tick event) {
		
		if (event.entityLiving instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) event.entityLiving;	
			
			if (player.getItemInUseCount() == 1) {
						
				if (event.item.getItem() == Items.potionitem) {
					
					decreaseThirsty(player, 7.0F);//Уменьшение жажды при выпивании зелья на 3.5 ед.
				}
				
				else if (event.item.getItem() == Items.melon) {
					
					decreaseThirsty(player, 3.0F);//Уменьшение жажды при съедании дольки арбуза на 1.5 ед.
				}
				
				else if (event.item.getItem() == Items.apple) {
					
					decreaseThirsty(player, 2.0F);//Уменьшение жажды при съедании яблока на 2 ед.
				}
				
				else if (event.item.getItem() == Items.golden_apple || event.item.getItemDamage() == 1) {
										
					player.addPotionEffect(new PotionEffect(PotionsRegistry.WETTING.getId(), 6000));//Эффект "Насыщение" на 5 минут при съедании зачарованного яблока.
					
					decreaseThirsty(player, 10.0F);//Уменьшение жажды при съедании зачарованного яблока на 10 ед.
				}
			}
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
    public void onRenderOverlayPre(RenderGameOverlayEvent.Pre event) {	
		
		if (event.type == ElementType.AIR) {
			
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, - 11.0F, 0.0F);//Смещение полоски запаса воздуха на 11 пикселей выше стандартной позиции.
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
    public void onRenderOverlayPost(RenderGameOverlayEvent.Post event) {			
		
		if (event.type == ElementType.AIR) {
			
			GL11.glPopMatrix();
		}
		
		if (event.type == ElementType.TEXT) {
			
			ThirstyBarRenderer.getInstance().renderThirstyBar();//Отрисовка полоски жажды.
		}
	}
}
