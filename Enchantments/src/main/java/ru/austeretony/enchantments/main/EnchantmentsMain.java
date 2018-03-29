package ru.austeretony.enchantments.main;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.austeretony.enchantments.main.tab.TabEnchantments;
import ru.austeretony.enchantments.proxy.CommonProxy;

@Mod(modid = EnchantmentsMain.MODID, name = EnchantmentsMain.NAME, version = EnchantmentsMain.VERSION)
public class EnchantmentsMain {
	
    public static final String 
    MODID = "enchantments",
    NAME = "Enchantments",
    VERSION = "1.0";

    private static Logger logger;
    
	@Instance(EnchantmentsMain.MODID)
	public static EnchantmentsMain instance;	
    
    @SidedProxy(clientSide = "ru.austeretony.enchantments.proxy.ClientProxy", serverSide = "ru.austeretony.enchantments.proxy.CommonProxy")
    public static CommonProxy proxy;
    
	public static final CreativeTabs ENCHANTMENTS = new TabEnchantments(CreativeTabs.getNextID(), "tab_enchantments");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	
        logger = event.getModLog();
        
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
