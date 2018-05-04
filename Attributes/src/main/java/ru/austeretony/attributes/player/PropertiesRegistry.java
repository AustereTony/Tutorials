package ru.austeretony.attributes.player;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import ru.austeretony.attributes.main.AttributesMain;

public class PropertiesRegistry {
	
	//Жажда
	public static final IAttribute THIRSTY_MAX = new RangedAttribute(AttributesMain.MODID + ".thirsty", 20.0D, 0.0D, 30.0D).setShouldWatch(true);
	
	//Идентификатор для нового параметра - значения "Жажда" в DataWatcher.
	public static final int THIRSTY = 20;
	
	//Запас монет
	//Идентификатор для нового параметра - значения "Запас монет" в DataWatcher.
	public static final int CURRENCY = 21;	
	//Идентификатор для нового параметра - флага "Увеличение дропа монет" в DataWatcher.
	public static final int CURRENCY_LOOT_BOOST = 22;

	@SubscribeEvent
	public void onPlayerConstructing(EntityEvent.EntityConstructing event) {
		
		if (event.entity instanceof EntityPlayer) {
			
			AttributesMain.logger().info("Player constructing...");
			
			EntityPlayer player = (EntityPlayer) event.entity;		
					
			//Жажда
			player.getAttributeMap().registerAttribute(this.THIRSTY_MAX);//Регистрация атрибута "Жажда".
					
			//Регистрация нового объекта DataWatcher для управления значением "Жажды".	
			//Базовое значение равно значению атрибута.
			player.getDataWatcher().addObject(this.THIRSTY, (float) this.THIRSTY_MAX.getDefaultValue());			
			
			//Запас монет
			//Регистрация нового объекта DataWatcher для управления значением запаса монет.			
			//Базовое значение - 0.
			player.getDataWatcher().addObject(this.CURRENCY, 0);	
			//Регистрация нового объекта DataWatcher, определяющего увеличение дропа монет.
			//Базовое значение - 0 (хранится как byte) - увеличения дропа монет по умолчанию нет.
			player.getDataWatcher().addObject(this.CURRENCY_LOOT_BOOST, (byte) 0);
		}		
	}
}
