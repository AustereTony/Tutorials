package ru.austeretony.potions.events;

import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.austeretony.potions.main.PotionsMain;
import ru.austeretony.potions.main.PotionsRegistry;

@Mod.EventBusSubscriber(modid = PotionsMain.MODID)
public class VanishEffectHandler {

	@SideOnly(Side.CLIENT)
    @SubscribeEvent
	public static void vanishPlayer(RenderPlayerEvent.Pre event) {
		
    	if (event.getEntityPlayer().isPotionActive(PotionsRegistry.VANISH)) {
    		
    		event.setCanceled(true);
    	}
    }
}
