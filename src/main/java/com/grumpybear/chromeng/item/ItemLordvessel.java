package com.grumpybear.chromeng.item;

import com.grumpybear.chromeng.ChromEng;
import com.grumpybear.chromeng.handler.GuiHandler;
import com.grumpybear.chromeng.lib.LibItemTypes;
import com.grumpybear.chromeng.lib.LibItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * Created by Kieran on 6/28/2017.
 */
public class ItemLordvessel extends ItemChargeSingle{

   public ItemLordvessel() {
      super(LibItems.LORDVESSEL, LibItemTypes.LORDVESSEL);
   }

   @Override
   public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
      if (!worldIn.isRemote)
          playerIn.openGui(ChromEng.instance, GuiHandler.LORDVESSEL, worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ());

      return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
   }
}
