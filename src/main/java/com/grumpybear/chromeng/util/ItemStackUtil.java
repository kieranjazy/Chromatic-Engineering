package com.grumpybear.chromeng.util;

import com.grumpybear.chromeng.chroma.ChromaStorage;
import com.grumpybear.chromeng.chroma.ChromaUnit;
import com.grumpybear.chromeng.chroma.EnumColour;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Kieran on 6/26/2017.
 */
public class ItemStackUtil {
   public static NBTTagCompound getNBT(ItemStack stack) {
      if(!stack.hasTagCompound())
         stack.setTagCompound(new NBTTagCompound());
      return stack.getTagCompound();
   }

   public static ChromaUnit getChromaUnit(ItemStack stack) {
      ChromaUnit temp = new ChromaUnit(EnumColour.ORANGE, 1000);

      if (!getNBT(stack).hasKey("Colourorange")) {
         temp.writeToNBT(getNBT(stack));
         return temp;
      }

      temp.readFromNBT(getNBT(stack));
      return temp;

   }

   public static ChromaStorage getChromaStorage(ItemStack stack) {
      ChromaStorage temp = new ChromaStorage(1000);

      if (!ItemStackUtil.getNBT(stack).hasKey("Colour")) {
         temp.writeToNBT(ItemStackUtil.getNBT(stack));
         return temp;
      }

      temp.readFromNBT(ItemStackUtil.getNBT(stack));
      return temp;

   }

}
