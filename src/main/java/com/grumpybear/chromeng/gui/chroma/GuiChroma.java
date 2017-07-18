package com.grumpybear.chromeng.gui.chroma;

import com.grumpybear.chromeng.chroma.IChromaStorage;
import com.grumpybear.chromeng.lib.LibTextures;
import com.grumpybear.chromeng.lib.LibTextures.Pair;
import com.grumpybear.chromeng.util.EnumColourUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

public class GuiChroma extends GuiContainer{

	private IChromaStorage chromaHandler;
	protected List<String> tooltip = new LinkedList<>();
	
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
		this.drawTexturedModalRect(this.guiLeft + 173, this.guiTop + 3, 0, 0, LibTextures.CONTAINER_SIZE_X, LibTextures.CONTAINER_SIZE_Y); //Draws the red background container
		
		this.mc.getTextureManager().bindTexture(new ResourceLocation("chromaticengineering:textures/gui/chroma_storage.png"));
		for (Pair pair : LibTextures.COLOR_LOCATIONS) {
			this.drawTexturedModalRect(this.guiLeft + 173 + pair.getX(), this.guiTop + 7, pair.getX(), pair.getY(), LibTextures.SIZE_X, LibTextures.SIZE_Y); //Draws a chroma unit for each in storage
		}
	}
	
	protected void chromaHandlerLogic() {
		
		this.mc.getTextureManager().bindTexture(new ResourceLocation("chromaticengineering:textures/gui/chroma_storage.png"));
		for (Pair pair : LibTextures.COLOR_FULL_LOCATIONS) {
			this.drawTexturedModalRect(this.guiLeft + pair.getX() + 102, this.guiTop + 8 + getYModifier(pair), pair.getX(), pair.getY() + getYModifier(pair), LibTextures.SIZE_FULL_X, LibTextures.SIZE_FULL_Y);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		func_191948_b(mouseX, mouseY);
	}

	protected int getYModifier(Pair pair) { //Method for getting modifier for correctly displaying CE levels
		int maxCE = chromaHandler.getChromaStorage().getMaxCE();
		int currCE = chromaHandler.getChromaStorage().getColour(EnumColourUtil.pairToColour(pair)).getCurrentCE();
		
		return currCE == 0 ? 101 : 100 - (currCE / (maxCE / 100)); 
	}

}
