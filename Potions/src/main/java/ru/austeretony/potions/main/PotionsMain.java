package ru.austeretony.potions.main;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.austeretony.potions.proxy.CommonProxy;

@Mod(modid = PotionsMain.MODID, name = PotionsMain.NAME, version = PotionsMain.VERSION)
public class PotionsMain {
	
    public static final String 
    MODID = "potions",
    NAME = "Potions",
    VERSION = "1.0";

    private static Logger logger;
    
    @Mod.Instance(PotionsMain.MODID)
	public static PotionsMain instance;	
    
    @SidedProxy(clientSide = "ru.austeretony.potions.proxy.ClientProxy", serverSide = "ru.austeretony.potions.proxy.CommonProxy")
    public static CommonProxy proxy;
    
	public PotionsMain() {
		
		this.instance = this;
	}
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	
        logger = event.getModLog();
        
    	this.proxy.preInit(event);    	  	
    }

	@Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    	
    	this.proxy.init(event);    	  	
    }
    
    public static Logger logger() {
    	
    	return logger;
    }
}
