package ru.austeretony.enchantments.items.tools;

import net.minecraft.item.ItemSpade;
import ru.austeretony.enchantments.main.EnchantmentsMain;

public class ItemBlazeShovel extends ItemSpade {

	public ItemBlazeShovel(String name, ToolMaterial material) {
		
		super(material);
		
	    this.setUnlocalizedName(name);
	    this.setTextureName(EnchantmentsMain.MODID + ":" + "tools/blaze_shovel");
	}
}
