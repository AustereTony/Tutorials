package ru.austeretony.enchantments.items.weapon;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import ru.austeretony.enchantments.enchantments.EnchantmentKeenBlade;

public class ItemBlazeSword extends ItemSword {

	public ItemBlazeSword(String name, ToolMaterial material) {
		
		super(material);
		
	    this.setUnlocalizedName(name);
	    this.setRegistryName(name);
	}
	
    public boolean canApplyAtEnchantingTable(ItemStack itemStack, Enchantment enchantment) {
    	
        return enchantment instanceof EnchantmentKeenBlade;
    }
}
