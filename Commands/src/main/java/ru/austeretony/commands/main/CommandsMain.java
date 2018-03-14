package ru.austeretony.commands.main;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import ru.austeretony.commands.proxy.CommonProxy;

@Mod(modid = CommandsMain.MODID, name = CommandsMain.NAME, version = CommandsMain.VERSION)
public class CommandsMain {
	
    public static final String
    MODID = "commands",
    NAME = "Commands",
    VERSION = "1.0";

    private static Logger logger;
    
	@Instance(CommandsMain.MODID)
	public static CommandsMain instance;	
    
    @SidedProxy(clientSide = "ru.austeretony.commands.proxy.ClientProxy", serverSide = "ru.austeretony.commands.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
    	
    	this.proxy.serverStarting(event);//Регистрация команд происходит на серверной стороне (в CommonProxy)
    }

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
