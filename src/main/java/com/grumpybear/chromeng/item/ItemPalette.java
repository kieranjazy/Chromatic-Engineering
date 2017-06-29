package com.grumpybear.chromeng.item;

import com.grumpybear.chromeng.chroma.EnumColour;
import com.grumpybear.chromeng.chroma.IChargeableSingle;
import com.grumpybear.chromeng.lib.LibItems;
import com.grumpybear.chromeng.util.EnumColourUtil;
import com.grumpybear.chromeng.util.ItemStackUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Kieran on 6/26/2017.
 */
public class ItemPalette extends ItemChargeMulti{
   public final int TRANSFER_RATE = 5;

   public ItemPalette() {
      super(LibItems.PALETTE);
   }

   @Override
   public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
      if (playerIn.isSneaking()) {
         if (getMode(playerIn.getHeldItemMainhand()) == 0) {
            setMode(playerIn.getHeldItemMainhand(), 1, playerIn, worldIn.isRemote);
         } else {
            setMode(playerIn.getHeldItemMainhand(), 0, playerIn, worldIn.isRemote);
         }
      }

      return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
   }

   @Override
   public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
         ItemStackUtil.getNBT(stack).getInteger("Mode"); //Defaults to 0 anyway if doesn't exist
   }

   @Override
   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (getMode(stack) == 1) {
         for (ItemStack item : ((EntityPlayer) entityIn).inventory.mainInventory) {
            if (item.getItem() instanceof IChargeableSingle) {
               if (this.getChromaStorage(stack).getColour(((IChargeableSingle) item.getItem()).getColourType()).getCurrentCE() - TRANSFER_RATE >= 0) {
                  IChargeableSingle charge = (IChargeableSingle) item.getItem();
                  charge.addCE(item, TRANSFER_RATE);
                  this.minusCE(stack, TRANSFER_RATE, charge.getColourType());
               }
            }
         }
      }
   }

   public void setMode(ItemStack stack, int i, EntityPlayer player, boolean remote) {
      ItemStackUtil.getNBT(stack).setInteger("Mode", i);
      if (!remote) {
         player.sendMessage(new TextComponentString("Mode: " + (i == 0 ? "Retain" : "Charge")));
      }
   }

   public int getMode(ItemStack stack) {
      return ItemStackUtil.getNBT(stack).getInteger("Mode");
   }

   @Override
   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
      for (EnumColour colour : EnumColour.values()) {
         tooltip.add(EnumColourUtil.colourToFormatting(colour) + EnumColourUtil.colourToStringCaps(colour) + " CE: " + TextFormatting.WHITE + getChromaStorage(stack).getColour(colour).getCurrentCE());
      }
   }
}
