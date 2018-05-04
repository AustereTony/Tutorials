package ru.austeretony.attributes.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import ru.austeretony.attributes.main.PotionsRegistry;
import ru.austeretony.attributes.player.CurrencyHandler;
import ru.austeretony.attributes.player.PropertiesRegistry;
import ru.austeretony.attributes.player.ThirstyHandler;

public class CommonProxy {
	
    public void preInit(FMLPreInitializationEvent event) {
    	
    	//Инициализация PotionsRegistry, в котором регистрируются зелья вызовом пустого метода register().
    	PotionsRegistry.register();
    	
    	//Регистрация классов с событиями.
    	
    	MinecraftForge.EVENT_BUS.register(new PropertiesRegistry());
    	
    	MinecraftForge.EVENT_BUS.register(new ThirstyHandler());
    	MinecraftForge.EVENT_BUS.register(new CurrencyHandler());
    }

    public void init(FMLInitializationEvent event) {}
}
