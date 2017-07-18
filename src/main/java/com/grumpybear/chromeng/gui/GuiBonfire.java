package com.grumpybear.chromeng.gui;

import com.grumpybear.chromeng.block.tile.TileBonfire;
import com.grumpybear.chromeng.gui.container.ContainerBonfire;
import com.grumpybear.chromeng.gui.element.Button;
import com.grumpybear.chromeng.init.ModItems;
import com.grumpybear.chromeng.network.ChromEngPacketHandler;
import com.grumpybear.chromeng.network.MessageLink;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

/**
 * Created by Kieran on 7/2/2017.
 */
public class GuiBonfire extends GuiCE{

   private GuiTextField nameField;
   private TileBonfire bonfire;

   private String texLoc = "chromaticengineering:textures/gui/container/bonfire.png";
   private ResourceLocation resLoc = new ResourceLocation(texLoc);

   public Button linkButton;

   public GuiBonfire(IInventory playerInv, TileBonfire tile) {
      super(new ContainerBonfire(playerInv, tile));

      this.bonfire = tile;
   }

   @Override
   public void initGui() {
      super.initGui();
      //linkButton = new Button(27, 18, this.guiLeft + 91, this.guiTop + 63, this, texLoc, 0, 198, 0, 216, "Link");
      linkButton = new Button(27, 18, this.guiLeft + 91, this.guiTop + 63, this, texLoc, 0, 198, 0, 216, "");

      addElement(linkButton);

      Keyboard.enableRepeatEvents(true);

      this.nameField = new GuiTextField(0, this.fontRendererObj, this.guiLeft + 12, this.guiTop + 32, 103, 32);
      this.nameField.setTextColor(-1);
      this.nameField.setDisabledTextColour(-1);
      this.nameField.setEnableBackgroundDrawing(false);
      this.nameField.setMaxStringLength(17);

   }

   @Override
   public void onGuiClosed() {
      super.onGuiClosed();
      Keyboard.enableRepeatEvents(false);

   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      Item item = bonfire.getStackInSlot(0).getItem();
      this.linkButton.buttonString = item != Items.AIR ?         //If stack in slot 0 isn't empty
              (item == ModItems.darksign ? //If stack is a darksign
                      "Link"               //Text = "link"
                      : (item == ModItems.lordvessel ? //If not a Darksign but a lordvessel
                      "Add" :              //Text = "add
                      ""))         //If not either text = "Link/Add"
              : TextFormatting.GRAY + "Empty";
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

      GlStateManager.color(1.0f,  1.0f,  1.0f, 1.0f);
      Minecraft.getMinecraft().getTextureManager().bindTexture(resLoc);
      this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 28, 0, this.ySize + (this.bonfire.getStackInSlot(0) == ItemStack.EMPTY ? 0 : 16), 110, 16);

      this.nameField.drawTextBox();

      super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
   }

   @Override
   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      super.drawGuiContainerForegroundLayer(mouseX, mouseY);
      fontRendererObj.drawString("Name:", 8, 19, 4210752);
   }

   @Override
   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      super.mouseClicked(mouseX, mouseY, mouseButton);

      nameField.mouseClicked(mouseX, mouseY, mouseButton);

      if (elements.get(0).intersectsWith(mouseX, mouseY)) {
         if (bonfire.getStackInSlot(0).getItem() == ModItems.darksign) {
            ChromEngPacketHandler.INSTANCE.sendToServer(new MessageLink(bonfire.getPos(), nameField.getText()));
         }
      }
   }

   @Override
   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (this.nameField.textboxKeyTyped(typedChar, keyCode))
      {
      }
      else
      {
         super.keyTyped(typedChar, keyCode);
      }
   }



}
