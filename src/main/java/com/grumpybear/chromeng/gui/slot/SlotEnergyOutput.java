package com.grumpybear.chromeng.gui.slot;

import com.grumpybear.chromeng.chroma.IChargeableMulti;
import com.grumpybear.chromeng.chroma.IChargeableSingle;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Kieran on 6/26/2017.
 */
public class SlotEnergyOutput extends Slot{

   public SlotEnergyOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
      super(inventoryIn, index, xPosition, yPosition);
   }

   @Override
   public boolean isItemValid(ItemStack stack) {
      if (stack.getItem() instanceof IChargeableSingle || stack.getItem() instanceof IChargeableMulti)
         return true;

      return false;
   }
}
