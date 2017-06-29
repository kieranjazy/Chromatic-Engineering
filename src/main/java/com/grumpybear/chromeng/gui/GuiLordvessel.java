package com.grumpybear.chromeng.gui;

import com.grumpybear.chromeng.gui.container.ContainerLordvessel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiSlider;

/**
 * Created by Kieran on 6/29/2017.
 */
public class GuiLordvessel extends GuiContainer implements GuiSlider.ISlider{

   private GuiSlider slider;

   public double sliderProg = 0.0D;


   public GuiLordvessel(IInventory playerInv) {
      super(new ContainerLordvessel(playerInv));

      this.xSize = 176;
      this.ySize = 166;

      slider = new GuiSlider(0, 12, 13, 17, 11, "", "", 0, 50, 0, false, true, this );
   }


   @Override
   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color(1.0f,  1.0f,  1.0f, 1.0f);
      this.mc.getTextureManager().bindTexture(new ResourceLocation("chromaticengineering:textures/gui/lordvessel.png"));
      this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
   }

   @Override
   public void onChangeSliderValue(GuiSlider slider) {
      sliderProg = slider.getValue();
   }
}
