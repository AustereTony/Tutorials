package ru.austeretony.enchantments.items.tools;

import net.minecraft.item.ItemPickaxe;
import ru.austeretony.enchantments.main.EnchantmentsMain;

public class ItemBlazePickaxe extends ItemPickaxe {

	public ItemBlazePickaxe(String name, ToolMaterial material) {
		
		super(material);
		
	    this.setUnlocalizedName(name);
	    this.setTextureName(EnchantmentsMain.MODID + ":" + "tools/blaze_pickaxe");
	}
}
