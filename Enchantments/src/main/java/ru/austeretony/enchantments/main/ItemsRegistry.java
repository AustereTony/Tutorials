package ru.austeretony.enchantments.main;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.austeretony.enchantments.items.tools.ItemBlazeAxe;
import ru.austeretony.enchantments.items.tools.ItemBlazeHoe;
import ru.austeretony.enchantments.items.tools.ItemBlazePickaxe;
import ru.austeretony.enchantments.items.tools.ItemBlazeShovel;
import ru.austeretony.enchantments.items.weapon.ItemBlazeSword;

@Mod.EventBusSubscriber(modid = EnchantmentsMain.MODID)
public class ItemsRegistry {

    public static final Item 
    BLAZE_PICKAXE = new ItemBlazePickaxe("blaze_pickaxe", ToolMaterial.IRON).setCreativeTab(EnchantmentsMain.ENCHANTMENTS),
    BLAZE_AXE = new ItemBlazeAxe("blaze_axe", ToolMaterial.IRON).setCreativeTab(EnchantmentsMain.ENCHANTMENTS),
    BLAZE_SHOVEL = new ItemBlazeShovel("blaze_shovel", ToolMaterial.IRON).setCreativeTab(EnchantmentsMain.ENCHANTMENTS),
    BLAZE_HOE = new ItemBlazeHoe("blaze_hoe", ToolMaterial.IRON).setCreativeTab(EnchantmentsMain.ENCHANTMENTS),
    
    BLAZE_SWORD = new ItemBlazeSword("blaze_sword", ToolMaterial.IRON).setCreativeTab(EnchantmentsMain.ENCHANTMENTS);

	@SubscribeEvent
	public static void registerEnchantments(RegistryEvent.Register<Item> event) {
		
		event.getRegistry().registerAll(
				
				BLAZE_PICKAXE,
				BLAZE_AXE,
				BLAZE_SHOVEL,
				BLAZE_HOE,
				BLAZE_SWORD
		);
	}
	
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
    	
    	setRenderForAll(
		
    			BLAZE_PICKAXE,
    			BLAZE_AXE,
    			BLAZE_SHOVEL,
    			BLAZE_HOE,
    			BLAZE_SWORD
		);
    }
    
    @SideOnly(Side.CLIENT)
    private static void setRenderForAll(Item... items) {
    	
    	for (int i = 0; i < items.length; i++) {
    		
    		ModelLoader.setCustomModelResourceLocation(items[i], 0, new ModelResourceLocation(new ResourceLocation(EnchantmentsMain.MODID, items[i].getUnlocalizedName().substring(5)), "inventory"));
    	}
    }
}
