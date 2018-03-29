package ru.austeretony.enchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import ru.austeretony.enchantments.items.weapon.ItemBlazeSword;
import ru.austeretony.enchantments.main.EnchantmentsMain;

public class EnchantmentKeenBlade extends Enchantment {
	
	/*
	 * Аналог чар Остроты. Эффект применяется аналогично чарам Остроты.
	 * 
	 * 
	 * Кол-во уровней чар: 4
	 * 
	 * Уровни опыта для зачаровывания: 5 - 20
	 * 
	 * Несовместимости: Нет, так как у чар один целевой предмет, который не может иметь других чар.
	 * 
	 * Целевые предметы: Огненный меч (ItemBlazeSword).
	 * 
	 * Эффект: Повышение урона атаки.
	 */
	
	public EnchantmentKeenBlade(String enchantmentName, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot[] slots) {
		
		super(rarity, type, slots);
		
		this.setEnchantmentName(enchantmentName);
	}
	
	private void setEnchantmentName(String enchantmentName) {
		
		this.setRegistryName(EnchantmentsMain.MODID, enchantmentName);
		this.setName(enchantmentName);
	}
	
    @Override
    public boolean canApply(ItemStack itemStack) {
    	
        return itemStack.getItem() instanceof ItemBlazeSword;
    }
    
    @Override
    public int getMinLevel() {
    	
        return 1;
    }

    @Override
    public int getMaxLevel() {
    	
        return 4;
    }
	
	@Override
    public int getMinEnchantability(int enchantmentLevel) {
    	
        return 5 + (5 * (enchantmentLevel - 1));
    }

	@Override
    public int getMaxEnchantability(int enchantmentLevel) {
    	
        return this.getMinEnchantability(enchantmentLevel) + 10;
    }
	
	//Вызывается при атаке предметом с данными чарами. Возвращает прибавку к урону в зависимости от уровня чар.
	@Override
    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
    	
		return 1.0F + (float) Math.max(0, level - 1) * 0.5F;
    }
}
