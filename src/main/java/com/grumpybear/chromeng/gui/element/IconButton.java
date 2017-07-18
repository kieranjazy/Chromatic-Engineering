package com.grumpybear.chromeng.gui.element;

import com.grumpybear.chromeng.lib.LibTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Kieran on 7/17/2017.
 */
public class IconButton extends Button {
   private final ResourceLocation ICON_LOCATION = new ResourceLocation("chromaticengineering:textures/icons.png");

   public int iconX;
   public int iconY;
   public String hoverName;

   public IconButton(int xPos, int yPos, Gui gui, String texture, int texX, int texY, int hoverX, int hoverY, int iconX, int iconY, String hoverName) {
      super(20, 21, xPos, yPos, gui, texture, texX, texY, hoverX, hoverY);
      this.iconX = iconX;
      this.iconY = iconY;
      this.hoverName = hoverName;
   }

   public IconButton(int width, int height, int xPos, int yPos, Gui gui, String texture, int texX, int texY, int hoverX, int hoverY) {
      super(width, height, xPos, yPos, gui, texture, texX, texY, hoverX, hoverY);
   }

   public void setIcon(int iconX, int iconY) {
      this.iconX = iconX;
      this.iconY = iconY;
   }

   public void setIcon(LibTextures.Pair pair) {
      setIcon(pair.getX(), pair.getY());
   }

   @Override
   public void drawBackground(int mouseX, int mouseY) {
      super.drawBackground(mouseX, mouseY);
   }

   @Override
   public void drawForeground(FontRenderer fontRenderer) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(ICON_LOCATION);

      drawRectangle(xPos + 2, yPos + 2, iconX, iconY, 16, 16);
   }

   public boolean hasIcon() {
      if (this.iconX  == 0 || this.iconY == 0)
         return false;

      return true;
   }
}
