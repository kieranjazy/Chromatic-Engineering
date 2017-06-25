package com.grumpybear.chromeng.item;

import com.grumpybear.chromeng.block.tile.TileBonfire;
import com.grumpybear.chromeng.chroma.ChromaUnit;
import com.grumpybear.chromeng.chroma.EnumColour;
import com.grumpybear.chromeng.chroma.IChargeable;
import com.grumpybear.chromeng.lib.LibItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemDarksign extends ItemCE implements IChargeable{
	
	
	public ItemDarksign() {
		super(LibItems.DARKSIGN);
	}
	
	
	public void linkBonfire(TileBonfire bonfire, ItemStack stack) {
		bonfire.writeToNBT(getNBT(stack));
	}

	private ChromaUnit getChromaUnit(ItemStack stack) {
       ChromaUnit temp = new ChromaUnit(EnumColour.ORANGE, 1000);

	   if (!getNBT(stack).hasKey("Colour")) {
          temp.writeToNBT(getNBT(stack));
          return temp;
       }

	   temp.readFromNBT(getNBT(stack));
	   return temp;

    }

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!getNBT(playerIn.getHeldItemMainhand()).hasNoTags()) {
			if (getNBT(playerIn.getHeldItemMainhand()).getBoolean("Burning")) {
				TileBonfire temp = new TileBonfire();
				temp.readFromNBT(getNBT(playerIn.getHeldItemMainhand()));

				playerIn.setPositionAndUpdate(temp.getPos().offset(EnumFacing.SOUTH).getX() + 1, temp.getPos().offset(EnumFacing.SOUTH).getY(), temp.getPos().offset(EnumFacing.SOUTH).getZ() + 1);
			}
		}

		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
	}

	public NBTTagCompound getNBT(ItemStack stack) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		return stack.getTagCompound();
	}


}
