package ru.austeretony.enchantments.main;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import ru.austeretony.enchantments.events.EnchantmentsEvents;
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
    	
        this.logger = event.getModLog();
        
        this.proxy.preInit(event);
        
        ItemsRegistry.register();
        EnchanmtmentsRegistry.register();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	
        this.proxy.init(event);
        
		MinecraftForge.EVENT_BUS.register(new EnchantmentsEvents());
    }
    
    public static Logger logger() {
    	
    	return logger;
    }
}
