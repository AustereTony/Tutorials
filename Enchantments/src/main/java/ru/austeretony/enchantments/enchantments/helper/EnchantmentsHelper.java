package ru.austeretony.enchantments.enchantments.helper;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import ru.austeretony.enchantments.main.EnchantmentsRegistry;

public class EnchantmentsHelper {
	
	/*
	 * Вспомогательный класс для быстрой проверки наличия определённых чар на экипировке игрока.
	 */

    public static boolean hasSafeFallEnchantment(EntityPlayer player) {
    	
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentsRegistry.SAFE_FALL, player.inventory.armorItemInSlot(0)) > 0;
    }
    
    public static boolean hasSmeltingEnchantment(EntityPlayer player) {
    	
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentsRegistry.SMELTING, player.getHeldItemMainhand()) > 0;
    }
}
