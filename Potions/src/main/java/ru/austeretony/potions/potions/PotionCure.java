package ru.austeretony.potions.potions;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import ru.austeretony.potions.main.PotionsMain;

public class PotionCure extends Potion {
	
	public PotionCure(String potionName, boolean isBadEffect, int liquidColor) {
		
		super(isBadEffect, liquidColor);
		
		this.setName(potionName);
	}
	
	public void setName(String potionName) {
		
		this.setRegistryName(PotionsMain.MODID, potionName);
		this.setPotionName("effect." + this.getRegistryName().toString());
	}
	
	@Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLivingBase, int amplifier, double health) {
    	
    	if (entityLivingBase.isPotionActive(MobEffects.POISON)) {
    		
    		entityLivingBase.removePotionEffect(MobEffects.POISON);
    	}
    }
	
	@Override
    public boolean isInstant() {
    	
        return true;
    }
}
