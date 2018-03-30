package ru.austeretony.tilesfirst.main;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.austeretony.tilesfirst.main.tab.TabTiles;
import ru.austeretony.tilesfirst.proxy.CommonProxy;

@Mod(modid = TilesMain.MODID, name = TilesMain.NAME, version = TilesMain.VERSION)
public class TilesMain {
	
    public static final String 
    MODID = "tilesfirst",
    NAME = "Tiles Vol.1",
    VERSION = "1.0";

    private static Logger logger;
    
	@Instance(TilesMain.MODID)
	public static TilesMain instance;
    
    @SidedProxy(clientSide = "ru.austeretony.tilesfirst.proxy.ClientProxy", serverSide = "ru.austeretony.tilesfirst.proxy.CommonProxy")
    public static CommonProxy proxy;
    
	public static final CreativeTabs TILES = new TabTiles(CreativeTabs.getNextID(), "tab_tiles_v1");

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
