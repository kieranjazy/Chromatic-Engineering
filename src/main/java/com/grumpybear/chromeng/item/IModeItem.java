package com.grumpybear.chromeng.item;

import com.grumpybear.chromeng.util.ItemStackUtil;
import net.minecraft.item.ItemStack;

/**
 * Created by Kieran on 7/12/2017.
 */
public interface IModeItem {

   default void setMode(ItemStack stack, int i) {
      ItemStackUtil.getNBT(stack).setInteger("Mode", i);
   }

   default int getMode(ItemStack stack) {
      return ItemStackUtil.getNBT(stack).getInteger("Mode");
   }

   default void switchMode(ItemStack stack) {
      int temp = (getMode(stack) + 1) <= getModeLimit() ? (getMode(stack) + 1) : 0;
      setMode(stack, temp);
   }

   String[] getModeNames();

   default String getCurrentModeString(ItemStack stack) {
      return getModeNames()[getMode(stack)];
   }

   default int getModeLimit() {
      return getModeNames().length - 1;
   }
}
