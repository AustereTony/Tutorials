package ru.austeretony.enchantments.items.tools;

import net.minecraft.item.ItemAxe;
import ru.austeretony.enchantments.main.EnchantmentsMain;

public class ItemBlazeAxe extends ItemAxe {

	public ItemBlazeAxe(String name, ToolMaterial material) {
		
		super(material);
		
	    this.setUnlocalizedName(name);
	    this.setTextureName(EnchantmentsMain.MODID + ":" + "tools/blaze_axe");
	}
}
