package ru.austeretony.enchantments.main;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import ru.austeretony.enchantments.enchantments.EnchantmentKeenBlade;
import ru.austeretony.enchantments.enchantments.EnchantmentSafeFall;
import ru.austeretony.enchantments.enchantments.EnchantmentSmelting;

public class EnchanmtmentsRegistry {

	public static final Enchantment 
	SAFE_FALL = new EnchantmentSafeFall("safe_fall", 100, 5, EnumEnchantmentType.armor_feet),//Безопасное Падение
	SMELTING = new EnchantmentSmelting("smelting", 101, 3, EnumEnchantmentType.digger),//Переплавка	
	KEEN_BLADE = new EnchantmentKeenBlade("keen_blade", 102, 10, EnumEnchantmentType.weapon);//Острое Лезвие
	
	public static void register() {}
}
