package ru.austeretony.attributes.player.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import ru.austeretony.attributes.main.AttributesMain;
import ru.austeretony.attributes.player.ThirstyHandler;

@SideOnly(Side.CLIENT)
public class ThirstyBarRenderer {

	private Minecraft mc = Minecraft.getMinecraft();
	
	private static final ThirstyBarRenderer INSTANCE = new ThirstyBarRenderer();
	
	private static final ResourceLocation THIRSTY_ICON = new ResourceLocation(AttributesMain.MODID, "textures/gui/thirsty.png");	
	
	private ThirstyBarRenderer() {}
	
	public static ThirstyBarRenderer getInstance() {
		
		return INSTANCE;
	}
	
	public void renderThirstyBar() {
		        	
        EntityPlayer player = (EntityPlayer) this.mc.thePlayer;

		if (!player.capabilities.isCreativeMode && !(this.mc.currentScreen instanceof GuiGameOver)) {
		        			        
	        ScaledResolution resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
	        
	        int 
	        i = resolution.getScaledWidth() / 2 + 10,
	        j = resolution.getScaledHeight() - 50,
	        k,
	        iconWidth = 80 / (((int) ThirstyHandler.getThirstyMax(player) / 2));
	        		
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        
	        this.mc.getTextureManager().bindTexture(this.THIRSTY_ICON);  
	        
	        for (k = 0; k < ((int) (ThirstyHandler.getThirstyMax(player) - ThirstyHandler.getThirsty(player)) / 2); k++) {
	        	
	        	Gui.func_146110_a(i + k * iconWidth, j, 20, 0, 9, 10, 29, 10);		        	
	        }
	        
	        if ((int) ThirstyHandler.getThirsty(player) % 2 != 0) {
	        	
	        	Gui.func_146110_a(i + k * iconWidth, j, 10, 0, 9, 10, 29, 10);
	        }
	        
	        for (k = 0; k < (int) ThirstyHandler.getThirsty(player) / 2; k++) {
	        	
	        	Gui.func_146110_a(i + 72 - k * iconWidth, j, 0, 0, 9, 10, 29, 10);	        		
	        }			     
	        
	        GL11.glDisable(GL11.GL_BLEND);
		} 
	}
}
