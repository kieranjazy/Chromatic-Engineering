package com.grumpybear.chromeng.gui;

import com.grumpybear.chromeng.gui.container.ContainerLordvessel;
import com.grumpybear.chromeng.gui.element.Button;
import com.grumpybear.chromeng.gui.element.IconButton;
import com.grumpybear.chromeng.lib.LibIcons;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Kieran on 6/29/2017.
 */
public class GuiLordvessel extends GuiCE{
   Button[] buttonSet;
   public IconButton iconButton;

   private final String texLoc = "chromaticengineering:textures/gui/lordvessel.png";
   private final ResourceLocation GUI_LOCATION = new ResourceLocation(texLoc) ;

   public GuiLordvessel(IInventory playerInv) {
      super(new ContainerLordvessel(playerInv));

      this.xSize = 176;
      this.ySize = 166;

      this.buttonSet = new Button[8];

   }

   @Override
   public void initGui() {
      super.initGui();

      iconButton = new IconButton(this.guiLeft + 9, this.guiTop + 9, this, texLoc, 0, 166, 0, 187, LibIcons.HOUSE.getX(), LibIcons.HOUSE.getY(), "Home");
      addElement(iconButton);
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      drawDefaultBackground();

      super.drawScreen(mouseX, mouseY, partialTicks);
      fontRendererObj.drawString("Name:", this.guiLeft + 99, this.guiTop + 7, 16579836);
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color(1.0f,  1.0f,  1.0f, 1.0f);
      this.mc.getTextureManager().bindTexture(GUI_LOCATION);
      this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
   }

}
