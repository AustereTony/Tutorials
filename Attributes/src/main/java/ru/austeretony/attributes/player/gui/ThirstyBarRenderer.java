package ru.austeretony.attributes.player.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
		
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
        	
	        EntityPlayer player = (EntityPlayer) this.mc.getRenderViewEntity();

			if (!player.capabilities.isCreativeMode && !(this.mc.currentScreen instanceof GuiGameOver)) {
			        			        
		        ScaledResolution resolution = new ScaledResolution(this.mc);
		        
		        int 
		        i = resolution.getScaledWidth() / 2 + 10,
		        j = resolution.getScaledHeight() - 50,
		        k,
		        iconWidth = 80 / (((int) ThirstyHandler.getThirstyMax(player) / 2));
		        		                
	            GlStateManager.disableRescaleNormal();              
	            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	            GlStateManager.enableBlend();
		            
		        this.mc.getTextureManager().bindTexture(this.THIRSTY_ICON);  
		        
		        for (k = 0; k < ((int) (ThirstyHandler.getThirstyMax(player) - ThirstyHandler.getThirsty(player)) / 2); k++) {
		        	
		        	this.drawCustomSizedTexturedRect(i + k * iconWidth, j, 20, 0, 9, 10, 29, 10);		        	
		        }
		        
		        if ((int) ThirstyHandler.getThirsty(player) % 2 != 0) {
		        	
		        	this.drawCustomSizedTexturedRect(i + k * iconWidth, j, 10, 0, 9, 10, 29, 10);
		        }
		        
		        for (k = 0; k < (int) ThirstyHandler.getThirsty(player) / 2; k++) {
		        	
		        	this.drawCustomSizedTexturedRect(i + 72 - k * iconWidth, j, 0, 0, 9, 10, 29, 10);
		        		
		        }			     
		        
		        GlStateManager.disableBlend();
			}
        }       
	}
	
	private static void drawCustomSizedTexturedRect(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight) {
    	
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();       
        
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        
        bufferbuilder.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + (float) height) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + (float) width) * f), (double) ((v + (float) height) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + (float) width) * f), (double) (v * f1)).endVertex();
        bufferbuilder.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
        
        tessellator.draw();
    }
}
