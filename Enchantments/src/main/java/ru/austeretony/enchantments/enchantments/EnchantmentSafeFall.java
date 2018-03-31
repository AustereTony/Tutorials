package ru.austeretony.enchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentSafeFall extends Enchantment {
	
	/*
	 * Падение не наносит урона. Эффект применяется с помощью эвента в EnchantmentsEvents.
	 * 
	 * 
	 * Кол-во уровней чар: 1
	 * 
	 * Уровни опыта для зачаровывания: 10 - 20
	 * 
	 * Несовместимости: Невесомость.
	 * 
	 * Целевые предметы: Любые ботинки.
	 * 
	 * Эффект: Полное исключение урона при падении, но ботинки получают урон равный высоте, с которой упал игрок.
	 */

	public EnchantmentSafeFall(String enchantmentName, int enchantmentId, int weight, EnumEnchantmentType enchantmentType) {
		
		super(enchantmentId, weight, enchantmentType);
		
		this.setName(enchantmentName);
	}
	
    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
    	
        return super.canApplyTogether(enchantment) && enchantment != Enchantment.featherFalling;
    }
	
	@Override
    public int getMinEnchantability(int enchantmentLevel) {
    	
        return 10;
    }

	@Override
    public int getMaxEnchantability(int enchantmentLevel) {
    	
        return 20;
    }
}
