package ru.austeretony.enchantments.main;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import ru.austeretony.enchantments.items.tools.ItemBlazeAxe;
import ru.austeretony.enchantments.items.tools.ItemBlazeHoe;
import ru.austeretony.enchantments.items.tools.ItemBlazePickaxe;
import ru.austeretony.enchantments.items.tools.ItemBlazeShovel;
import ru.austeretony.enchantments.items.weapon.ItemBlazeSword;

public class ItemsRegistry {

    public static final Item 
    BLAZE_PICKAXE = new ItemBlazePickaxe("blaze_pickaxe", ToolMaterial.IRON).setCreativeTab(EnchantmentsMain.ENCHANTMENTS),
    BLAZE_AXE = new ItemBlazeAxe("blaze_axe", ToolMaterial.IRON).setCreativeTab(EnchantmentsMain.ENCHANTMENTS),
    BLAZE_SHOVEL = new ItemBlazeShovel("blaze_shovel", ToolMaterial.IRON).setCreativeTab(EnchantmentsMain.ENCHANTMENTS),
    BLAZE_HOE = new ItemBlazeHoe("blaze_hoe", ToolMaterial.IRON).setCreativeTab(EnchantmentsMain.ENCHANTMENTS),
    
    BLAZE_SWORD = new ItemBlazeSword("blaze_sword", ToolMaterial.IRON).setCreativeTab(EnchantmentsMain.ENCHANTMENTS);
    
    public static final Item[] ITEMS = new Item[] {
    		
    		BLAZE_PICKAXE,
    		BLAZE_AXE,
    		BLAZE_SHOVEL,
    		BLAZE_HOE,
    		BLAZE_SWORD
    };
    
    public static void register() {
    	
    	registerItems(ITEMS);
    }
    
    private static void registerItems(Item... items) {
    	
		for (int i = 0; i < items.length; i++) {

			GameRegistry.registerItem(items[i], items[i].getUnlocalizedName());
		}
    }
}
