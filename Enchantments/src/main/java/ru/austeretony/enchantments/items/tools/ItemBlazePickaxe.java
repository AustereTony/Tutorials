package ru.austeretony.enchantments.items.tools;

import net.minecraft.item.ItemPickaxe;

public class ItemBlazePickaxe extends ItemPickaxe {

	public ItemBlazePickaxe(String name, ToolMaterial material) {
		
		super(material);
		
	    this.setUnlocalizedName(name);
	    this.setRegistryName(name);
	}
}
