package com.grumpybear.chromeng.chroma;

import net.minecraft.item.ItemStack;

/**
 * Created by Kieran on 6/26/2017.
 */
public interface IChargeableMulti {
   public void addCE(ItemStack stack, int i, EnumColour colour);

   public void minusCE(ItemStack stack, int i, EnumColour colour);

   public ChromaStorage getChromaStorage(ItemStack stack);
}
