package ru.austeretony.potions.potions;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.austeretony.potions.main.PotionsMain;

public class PotionIronSkin extends Potion {

	private final ResourceLocation icon;
	
	public final static UUID IRON_SKIN_UUID = UUID.fromString("0ec2fc39-b7d8-4f22-977a-b5b2a57b6d55");

	public PotionIronSkin(String potionName, boolean isBadEffect, int liquidColor) {
		
		super(isBadEffect, liquidColor);
		
		this.setName(potionName);
		
		this.icon = new ResourceLocation(PotionsMain.MODID, "textures/potions/" + potionName + ".png");
	}
	
	public void setName(String potionName) {
		
		this.setRegistryName(PotionsMain.MODID, potionName);
		this.setPotionName("effect." + this.getRegistryName().toString());
	}

	@Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBase, AbstractAttributeMap attributeMap, int amplifier) {
    	
		attributeMap.getAttributeInstance(SharedMonsterAttributes.ARMOR).applyModifier(new AttributeModifier(this.IRON_SKIN_UUID, this.getName(), 3.0D, 0));
    }
    
	@Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBase, AbstractAttributeMap attributeMap, int amplifier) {
    	
		attributeMap.getAttributeInstance(SharedMonsterAttributes.ARMOR).removeModifier(this.IRON_SKIN_UUID);
    }
	
	@Override
	public boolean hasStatusIcon() {
		
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect potionEffect, Minecraft mc) {
		
		if (mc.currentScreen != null) {
			
			mc.getTextureManager().bindTexture(this.icon);
			
			Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUDEffect(int x, int y, PotionEffect potionEffect, Minecraft mc, float alpha) {
		
		mc.getTextureManager().bindTexture(this.icon);
		
		Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
	}
}
