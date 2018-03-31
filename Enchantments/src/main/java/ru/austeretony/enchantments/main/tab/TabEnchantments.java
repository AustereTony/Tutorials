package ru.austeretony.enchantments.main.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ru.austeretony.enchantments.main.ItemsRegistry;

public class TabEnchantments extends CreativeTabs {

	public TabEnchantments(int index, String label) {
		
		super(index, label);
	}

	@Override
	public Item getTabIconItem() {
		
		return ItemsRegistry.BLAZE_PICKAXE;
	}
}
