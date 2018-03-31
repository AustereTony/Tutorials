package ru.austeretony.tilesfirst.main.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ru.austeretony.tilesfirst.main.BlocksRegistry;

public class TabTiles extends CreativeTabs {

	public TabTiles(int index, String label) {
		
		super(index, label);
	}

	@Override
	public Item getTabIconItem() {
		
		return Item.getItemFromBlock(BlocksRegistry.COUNTER);
	}
}
