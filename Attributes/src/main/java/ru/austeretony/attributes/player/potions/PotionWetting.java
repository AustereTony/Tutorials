package ru.austeretony.attributes.player.potions;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import ru.austeretony.attributes.player.PropertiesRegistry;
import ru.austeretony.attributes.player.ThirstyHandler;

public class PotionWetting extends PotionBase {
	
	/*
	 * Увеличивает запас "жажды" на 6 ед. 
	 */
	
	public static final UUID THIRSTY_MODIFIER_UUID = UUID.fromString("0ec2fc39-b7d8-4f22-977a-b5b2a57b6d55");
	
	public final static AttributeModifier THIRSTY_MODIFIER = new AttributeModifier(THIRSTY_MODIFIER_UUID, "decreasedThirsty", 6.0D, 0);
	
	public PotionWetting(String potionName, int id, boolean isBadEffect, int liquidColor) {
		
		super(potionName, id, isBadEffect, liquidColor);
	}

	@Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBase, BaseAttributeMap attributeMap, int amplifier) {
    	
		if (entityLivingBase instanceof EntityPlayer) {
						
			attributeMap.getAttributeInstance(PropertiesRegistry.THIRSTY_MAX).applyModifier(this.THIRSTY_MODIFIER);
		}
    }
    
	@Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBase, BaseAttributeMap attributeMap, int amplifier) {
    	
		if (entityLivingBase instanceof EntityPlayer) {
						
			attributeMap.getAttributeInstance(PropertiesRegistry.THIRSTY_MAX).removeModifier(this.THIRSTY_MODIFIER);
			
			//Обновление текущего значения для вызова синхронизации с клиентом (по завершению
			//эффекта клиент не получает обновленное значение самостоятельно).
			ThirstyHandler.setThirsty((EntityPlayer) entityLivingBase, ThirstyHandler.getThirsty((EntityPlayer) entityLivingBase));
		}
    }
}
