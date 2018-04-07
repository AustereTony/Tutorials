package ru.austeretony.attributes.player.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.austeretony.attributes.main.AttributesMain;

public class PotionBase extends Potion {
	
	/*
	 * Основа эффектов.
	 */
	
	private final ResourceLocation icon;
		
	public PotionBase(String potionName, boolean isBadEffect, int liquidColor) {
		
		super(isBadEffect, liquidColor);
		
		this.setName(potionName);
		
		this.icon = new ResourceLocation(AttributesMain.MODID, "textures/potions/" + potionName + ".png");
	}
	
	public void setName(String potionName) {
		
		this.setRegistryName(AttributesMain.MODID, potionName);
		this.setPotionName("effect." + this.getRegistryName().toString());
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
