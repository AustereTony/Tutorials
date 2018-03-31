package ru.austeretony.enchantments.items.weapon;

import net.minecraft.item.ItemSword;
import ru.austeretony.enchantments.main.EnchantmentsMain;

public class ItemBlazeSword extends ItemSword {

	public ItemBlazeSword(String name, ToolMaterial material) {
		
		super(material);
		
	    this.setUnlocalizedName(name);
	    this.setTextureName(EnchantmentsMain.MODID + ":" + "weapon/blaze_sword");
	}
	
	//Запрет на зачаровывание.
	@Override
    public int getItemEnchantability() {
    	
        return 0;
    }
}
