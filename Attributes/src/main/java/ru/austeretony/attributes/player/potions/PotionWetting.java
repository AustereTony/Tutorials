package ru.austeretony.attributes.player.potions;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import ru.austeretony.attributes.player.PropertiesRegistry;
import ru.austeretony.attributes.player.ThirstyHandler;

public class PotionWetting extends PotionBase {
	
	/*
	 * Увеличивает запас "жажды" на 6 ед. 
	 */
	
	public static final UUID THIRSTY_MODIFIER_UUID = UUID.fromString("0ec2fc39-b7d8-4f22-977a-b5b2a57b6d55");
	
	public PotionWetting(String potionName, boolean isBadEffect, int liquidColor) {
		
		super(potionName, isBadEffect, liquidColor);
	}

	@Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBase, AbstractAttributeMap attributeMap, int amplifier) {
    	
		if (entityLivingBase instanceof EntityPlayer) {
						
			attributeMap.getAttributeInstance(PropertiesRegistry.THIRSTY_MAX).applyModifier(new AttributeModifier(this.THIRSTY_MODIFIER_UUID, PropertiesRegistry.THIRSTY_MAX.getName(), 6.0D, 0));
		}
    }
    
	@Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBase, AbstractAttributeMap attributeMap, int amplifier) {
    	
		if (entityLivingBase instanceof EntityPlayer) {
						
			attributeMap.getAttributeInstance(PropertiesRegistry.THIRSTY_MAX).removeModifier(this.THIRSTY_MODIFIER_UUID);
			
			//Обновление текущего значения для вызова синхронизации с клиентом (по завершению
			//эффекта или при смерти клиент не получает обновленное значение самостоятельно).
			ThirstyHandler.setThirsty((EntityPlayer) entityLivingBase, ThirstyHandler.getThirsty((EntityPlayer) entityLivingBase));
		}
    }
}
