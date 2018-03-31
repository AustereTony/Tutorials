package ru.austeretony.enchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import ru.austeretony.enchantments.items.tools.ItemBlazeAxe;
import ru.austeretony.enchantments.items.tools.ItemBlazeHoe;
import ru.austeretony.enchantments.items.tools.ItemBlazePickaxe;
import ru.austeretony.enchantments.items.tools.ItemBlazeShovel;

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

	public EnchantmentSmelting(String enchantmentName, int enchantmentId, int weight, EnumEnchantmentType enchantmentType) {
		
		super(enchantmentId, weight, enchantmentType);
		
		this.setName(enchantmentName);
	}
	
    @Override
	public boolean canApplyTogether(Enchantment enchantment) {
    	
    	return super.canApplyTogether(enchantment) && enchantment != Enchantment.silkTouch;
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
