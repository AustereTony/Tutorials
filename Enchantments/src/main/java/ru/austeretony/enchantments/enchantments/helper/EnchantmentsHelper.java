package ru.austeretony.enchantments.enchantments.helper;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import ru.austeretony.enchantments.main.EnchanmtmentsRegistry;

public class EnchantmentsHelper {
	
	/*
	 * Вспомогательный класс для быстрой проверки наличия определённых чар на экипировке игрока.
	 */

    public static boolean hasSafeFallEnchantment(EntityPlayer player) {
    	
        return EnchantmentHelper.getEnchantmentLevel(EnchanmtmentsRegistry.SAFE_FALL.effectId, player.inventory.armorItemInSlot(0)) > 0;
    }
    
    public static boolean hasSmeltingEnchantment(EntityPlayer player) {
    	
        return EnchantmentHelper.getEnchantmentLevel(EnchanmtmentsRegistry.SMELTING.effectId, player.getHeldItem()) > 0;
    }
}
