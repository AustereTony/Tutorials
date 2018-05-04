package ru.austeretony.attributes.player;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.austeretony.attributes.main.AttributesMain;

@Mod.EventBusSubscriber(modid = AttributesMain.MODID)
public class PropertiesRegistry {
	
	//Жажда
	public static final IAttribute THIRSTY_MAX = new RangedAttribute(null, AttributesMain.MODID + ".thirsty", 20.0D, 0.0D, 30.0D).setShouldWatch(true);
	
	public static final DataParameter<Float> THIRSTY = EntityDataManager.<Float>createKey(EntityPlayer.class, DataSerializers.FLOAT);
	
	//Запас монет
	public static final DataParameter<Integer> CURRENCY = EntityDataManager.<Integer>createKey(EntityPlayer.class, DataSerializers.VARINT);	
	public static final DataParameter<Boolean> CURRENCY_LOOT_BOOST = EntityDataManager.<Boolean>createKey(EntityPlayer.class, DataSerializers.BOOLEAN);

	@SubscribeEvent
	public static void onPlayerConstructing(EntityEvent.EntityConstructing event) {
		
		if (event.getEntity() instanceof EntityPlayer) {
			
			AttributesMain.logger().info("Player constructing...");
			
			EntityPlayer player = (EntityPlayer) event.getEntity();		
					
			//Жажда
			player.getAttributeMap().registerAttribute(THIRSTY_MAX);//Регистрация атрибута "Жажда".
					
			//Регистрация нового объекта DataManager для управления значением "Жажды".	
			//Базовое значение равно значению атрибута.
			player.getDataManager().register(THIRSTY, (float) THIRSTY_MAX.getDefaultValue());			
			
			//Запас монет
			player.getDataManager().register(CURRENCY, 0);//Регистрация нового объекта DataManager для управления значением запаса монет.				
			player.getDataManager().register(CURRENCY_LOOT_BOOST, false);//Регистрация нового объекта DataManager, определяющего увеличение дропа монет.
		}		
	}
}
