package ru.austeretony.attributes.main;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.austeretony.attributes.player.potions.PotionLooting;
import ru.austeretony.attributes.player.potions.PotionWetting;

@Mod.EventBusSubscriber(modid = AttributesMain.MODID)
public class PotionsRegistry {

	public static final Potion 
	WETTING = new PotionWetting("wetting", false, 0x00C2FF),
	LOOTING = new PotionLooting("looting", false, 0x00C2FF);
	
	@SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event) {
    	    	
		event.getRegistry().registerAll(
				
				WETTING,
				LOOTING
		);
	}
}
