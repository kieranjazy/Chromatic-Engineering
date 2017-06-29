package com.grumpybear.chromeng.chroma;

import net.minecraft.item.ItemStack;

/**
 * Created by Kieran on 6/27/2017.
 */
public interface ICharger {
   public void addCE(ItemStack stack,  int ce, EnumColour colour);
   public void minusCE(ItemStack stack, int ce, EnumColour colour);
   
}
