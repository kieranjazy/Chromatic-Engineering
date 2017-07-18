package com.grumpybear.chromeng.chroma;

import net.minecraft.item.ItemStack;

/**
 * Created by Kieran on 6/25/2017.
 */
public interface IChargeableSingle {

   public boolean addCE(ItemStack stack, int i);

   public boolean minusCE(ItemStack stack, int i);

   public ChromaUnit getChromaUnit(ItemStack stack);

   public EnumColour getColourType();
}
