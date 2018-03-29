package ru.austeretony.enchantments.main.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import ru.austeretony.enchantments.main.ItemsRegistry;

public class TabEnchantments extends CreativeTabs {

	public TabEnchantments(int index, String label) {
		
		super(index, label);
	}

	@Override
	public ItemStack getTabIconItem() {
		
		return new ItemStack(ItemsRegistry.BLAZE_PICKAXE);
	}
}
