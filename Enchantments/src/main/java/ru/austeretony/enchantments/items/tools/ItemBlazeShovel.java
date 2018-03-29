package ru.austeretony.enchantments.items.tools;

import net.minecraft.item.ItemSpade;

public class ItemBlazeShovel extends ItemSpade {

	public ItemBlazeShovel(String name, ToolMaterial material) {
		
		super(material);
		
	    this.setUnlocalizedName(name);
	    this.setRegistryName(name);
	}
}
