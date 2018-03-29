package ru.austeretony.enchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import ru.austeretony.enchantments.items.tools.ItemBlazeAxe;
import ru.austeretony.enchantments.items.tools.ItemBlazeHoe;
import ru.austeretony.enchantments.items.tools.ItemBlazePickaxe;
import ru.austeretony.enchantments.items.tools.ItemBlazeShovel;
import ru.austeretony.enchantments.main.EnchantmentsMain;

public class EnchantmentSmelting extends Enchantment {
	
	/*
	 * Автоматическая переплавка некоторых блоков. Эффекты применяются с помощью эвентов в EnchantmentsEvents.
	 * 
	 * 
	 * Кол-во уровней чар: 3 (зависит кол-во дропа)
	 * 
	 * Уровни опыта для зачаровывания: 10 - 30
	 * 
	 * Несовместимости: Шелковое Касание
	 * 
	 * Целевые предметы: Огненные кирка, топор, лопата и мотыга.
	 * 
	 * Эффект: Кирка - переплавка железной и золотой руд в слитки,
	 *         Топор - переплавка брёвен в древесный уголь,
	 *         Лопата - переплавка песка в стекло,
	 *         Мотыга - при вспахивании превращает землю в подзол.
	 *         
	 * Примечание: Чары можно наложить на кирку только на наковальне с помощью книги.
	 */

	public EnchantmentSmelting(String enchantmentName, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		
		super(rarity, type, slots);
		
		this.setEnchantmentName(enchantmentName);
	}
	
	private void setEnchantmentName(String enchantmentName) {
		
		this.setRegistryName(EnchantmentsMain.MODID, enchantmentName);
		this.setName(enchantmentName);
	}
	
    @Override
    protected boolean canApplyTogether(Enchantment enchantment) {
    	
    	return super.canApplyTogether(enchantment) && enchantment != Enchantments.SILK_TOUCH;
    }
    
    @Override
    public boolean canApply(ItemStack itemStack) {
    	
        return itemStack.getItem() instanceof ItemBlazePickaxe || 
        	itemStack.getItem() instanceof ItemBlazeAxe || 
        	itemStack.getItem() instanceof ItemBlazeShovel ||
        	itemStack.getItem() instanceof ItemBlazeHoe;
    }
	
    @Override
    public int getMinLevel() {
    	
        return 1;
    }

    @Override
    public int getMaxLevel() {
    	
        return 3;
    }
	
	@Override
    public int getMinEnchantability(int enchantmentLevel) {
    	
        return 5 + (5 * enchantmentLevel);
    }

	@Override
    public int getMaxEnchantability(int enchantmentLevel) {
    	
        return this.getMinEnchantability(enchantmentLevel) + 10;
    }
}
