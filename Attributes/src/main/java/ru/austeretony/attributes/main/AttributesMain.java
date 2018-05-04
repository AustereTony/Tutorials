package ru.austeretony.attributes.main;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ru.austeretony.attributes.proxy.CommonProxy;

@Mod(modid = AttributesMain.MODID, name = AttributesMain.NAME, version = AttributesMain.VERSION)
public class AttributesMain {
	
    public static final String 
    MODID = "attributes",
    NAME = "Attributes",
    VERSION = "1.0.0";

    private static Logger logger;
    
    @SidedProxy(clientSide = "ru.austeretony.attributes.proxy.ClientProxy", serverSide = "ru.austeretony.attributes.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	
        this.logger = event.getModLog();
        
        this.proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	
        this.proxy.init(event);
    }
    
    public static Logger logger() {
    	
    	return logger;
    }
}
