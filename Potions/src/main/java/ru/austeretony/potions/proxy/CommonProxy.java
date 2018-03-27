package ru.austeretony.potions.proxy;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.austeretony.potions.main.PotionsRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {
    	
    	PotionsRegistry.registerSimpleRecipes();    	
    	PotionsRegistry.registerAdvancedRecipes(); 
    	
    	GameRegistry.addShapedRecipe(new ResourceLocation("Utils"), new ResourceLocation("Recipes"), new ItemStack(Items.EXPERIENCE_BOTTLE), new Object[] {"   ", " S ", "   ", 'S', PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM, 1), PotionsRegistry.EQUILIBRIUM_TYPE_STANDARD)});
    }
}
