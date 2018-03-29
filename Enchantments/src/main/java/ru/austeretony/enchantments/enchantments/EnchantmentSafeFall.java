package ru.austeretony.enchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import ru.austeretony.enchantments.main.EnchantmentsMain;

public class EnchantmentSafeFall extends Enchantment {
	
	/*
	 * Падение не наносит урона. Эффект применяется с помощью эвента в EnchantmentsEvents.
	 * 
	 * 
	 * Кол-во уровней чар: 1
	 * 
	 * Уровни опыта для зачаровывания: 10 - 20
	 * 
	 * Несовместимости: Невесомость, Покоритель глубин и Ледяная Поступь.
	 * 
	 * Целевые предметы: Любые ботинки.
	 * 
	 * Эффект: Полное исключение урона при падении, но ботинки получают урон равный высоте, с которой упал игрок.
	 */

	public EnchantmentSafeFall(String enchantmentName, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		
		super(rarity, type, slots);
		
		this.setEnchantmentName(enchantmentName);
	}
	
	private void setEnchantmentName(String enchantmentName) {
		
		this.setRegistryName(EnchantmentsMain.MODID, enchantmentName);
		this.setName(enchantmentName);
	}
	
    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
    	
        return super.canApplyTogether(enchantment) && enchantment != Enchantments.FEATHER_FALLING;
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
