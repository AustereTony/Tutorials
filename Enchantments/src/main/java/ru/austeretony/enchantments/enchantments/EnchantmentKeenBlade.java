package ru.austeretony.enchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import ru.austeretony.enchantments.items.weapon.ItemBlazeSword;

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
	
	public EnchantmentKeenBlade(String enchantmentName, int enchantmentId, int weight, EnumEnchantmentType enchantmentType) {
		
		super(enchantmentId, weight, enchantmentType);
		
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
    public float func_152376_a(int level, EnumCreatureAttribute creatureType) {
    	
		return 1.0F + (float) Math.max(0, level - 1) * 0.5F;
    }
}
