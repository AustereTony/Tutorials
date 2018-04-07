package ru.austeretony.attributes.player.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import ru.austeretony.attributes.player.CurrencyHandler;

public class PotionLooting extends PotionBase {
	
	/*
	 * Увеличивает дроп монет в 2 раза. 
	 */
		
	public PotionLooting(String potionName, boolean isBadEffect, int liquidColor) {
		
		super(potionName, isBadEffect, liquidColor);
	}

	@Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBase, AbstractAttributeMap attributeMap, int amplifier) {
    	
		if (entityLivingBase instanceof EntityPlayer) {

			CurrencyHandler.setCurrencyLootBoost((EntityPlayer) entityLivingBase, true);
		}
    }
    
	@Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBase, AbstractAttributeMap attributeMap, int amplifier) {
    	
		if (entityLivingBase instanceof EntityPlayer) {

			CurrencyHandler.setCurrencyLootBoost((EntityPlayer) entityLivingBase, false);
		}
	}
}
