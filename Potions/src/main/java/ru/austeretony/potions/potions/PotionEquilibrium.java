package ru.austeretony.potions.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.austeretony.potions.main.PotionsMain;

public class PotionEquilibrium extends Potion {
	
	private final ResourceLocation icon;

	public PotionEquilibrium(String potionName, boolean isBadEffect, int liquidColor) {
		
		super(isBadEffect, liquidColor);
		
		this.setName(potionName);
		
		this.icon = new ResourceLocation(PotionsMain.MODID, "textures/potions/" + potionName + ".png");
	}
	
	public void setName(String potionName) {
		
		this.setRegistryName(PotionsMain.MODID, potionName);
		this.setPotionName("effect." + this.getRegistryName().toString());
	}
	
	@Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
		
		if (entityLivingBase instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) entityLivingBase;
			
			if (player.getHealth() < player.getMaxHealth()) {
				
				if (player.experienceLevel >= 1) {
					
					player.heal(2.0F);//Хил 2 пункта (1 сердце).
					
					player.experienceLevel -= 1;//За один уровень опыта.
				}
			}
		}
	}
	
	@Override
    public boolean isReady(int duration, int amplifier) {
		
		return duration % (amplifier == 0 ? 20 : 10) == 0;
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
