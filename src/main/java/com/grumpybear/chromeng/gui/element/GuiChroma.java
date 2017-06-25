package com.grumpybear.chromeng.gui.element;

import com.grumpybear.chromeng.chroma.IChromaStorage;
import com.grumpybear.chromeng.lib.LibTextures;
import com.grumpybear.chromeng.lib.LibTextures.Pair;
import com.grumpybear.chromeng.util.EnumColourUtil;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiChroma extends GuiContainer{

	private IChromaStorage chromaHandler;
	
	public GuiChroma(Container inventorySlotsIn, IChromaStorage chromaHandler) {
		super(inventorySlotsIn);
		this.chromaHandler = chromaHandler;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		chromaContainer();
		chromaHandlerLogic();
	}
	
	protected void chromaContainer() {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("chromaticengineering:textures/gui/chroma_base.png"));
		this.drawTexturedModalRect(this.guiLeft + 173, this.guiTop + 3, 0, 0, LibTextures.CONTAINER_SIZE_X, LibTextures.CONTAINER_SIZE_Y);
		
		this.mc.getTextureManager().bindTexture(new ResourceLocation("chromaticengineering:textures/gui/chroma_storage.png"));
		for (Pair pair : LibTextures.COLOR_LOCATIONS) {
			this.drawTexturedModalRect(this.guiLeft + 173 + pair.getX(), this.guiTop + 7, pair.getX(), pair.getY(), LibTextures.SIZE_X, LibTextures.SIZE_Y);
		}
	}
	
	protected void chromaHandlerLogic() {
		//int maxCE = chromaHandler.getChromaStorage().getMaxCE();
		
		this.mc.getTextureManager().bindTexture(new ResourceLocation("chromaticengineering:textures/gui/chroma_storage.png"));
		for (Pair pair : LibTextures.COLOR_FULL_LOCATIONS) {
			this.drawTexturedModalRect(this.guiLeft + pair.getX() + 102, this.guiTop + 8 + getYModifier(pair), pair.getX(), pair.getY() + getYModifier(pair), LibTextures.SIZE_FULL_X, LibTextures.SIZE_FULL_Y);
		}
	}

	
	protected int getYModifier(Pair pair) {
		int maxCE = chromaHandler.getChromaStorage().getMaxCE();
		int currCE = chromaHandler.getChromaStorage().getColour(EnumColourUtil.pairToColour(pair)).getCurrentCE();
		
		return currCE == 0 ? 101 : 100 - (currCE / (maxCE / 100)); 
	}
}
