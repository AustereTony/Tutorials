package ru.austeretony.enchantments.items.tools;

import net.minecraft.item.ItemHoe;
import ru.austeretony.enchantments.main.EnchantmentsMain;

public class ItemBlazeHoe extends ItemHoe {

	public ItemBlazeHoe(String name, ToolMaterial material) {
		
		super(material);
		
	    this.setUnlocalizedName(name);
	    this.setTextureName(EnchantmentsMain.MODID + ":" + "tools/blaze_hoe");
	}
}
