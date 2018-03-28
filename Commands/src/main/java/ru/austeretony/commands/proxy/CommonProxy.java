package ru.austeretony.commands.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import ru.austeretony.commands.commands.CommandCleanse;
import ru.austeretony.commands.commands.CommandRepair;
import ru.austeretony.commands.commands.CommandYell;

public class CommonProxy {

    public void serverStarting(FMLServerStartingEvent event) {
    	
    	event.registerServerCommand(new CommandRepair());//Регистрация команд	
    	event.registerServerCommand(new CommandCleanse());	
    	event.registerServerCommand(new CommandYell());	
    }
	
    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {}
}
