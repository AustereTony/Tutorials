package ru.austeretony.tilesfirst.main;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.austeretony.tilesfirst.block.BlockCounter;
import ru.austeretony.tilesfirst.block.BlockCounterPersonal;
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
	
	public static final Block
	COUNTER = new BlockCounter("counter", Material.rock, 5.0F, 5.0F, Block.soundTypeStone).setCreativeTab(TilesMain.TILES),
	COUNTER_PERSONAL = new BlockCounterPersonal("counter_personal", Material.rock, 10.0F, 10.0F, Block.soundTypeStone).setCreativeTab(TilesMain.TILES);
	
	public static final Block[] BLOCKS = new Block[] {
			
			COUNTER,
			COUNTER_PERSONAL
	};

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	
        this.logger = event.getModLog();
        
    	this.proxy.preInit(event);    	  	
    	
    	this.registerBlocks(BLOCKS);
     }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	
    	this.proxy.init(event); 	    
    }
    
    public static Logger logger() {
    	
    	return logger;
    }
    
    private void registerBlocks(Block... blocks) {
    	
		for (int i = 0; i < blocks.length; i++) {

			GameRegistry.registerBlock(blocks[i], blocks[i].getUnlocalizedName());
		}
    }
}
