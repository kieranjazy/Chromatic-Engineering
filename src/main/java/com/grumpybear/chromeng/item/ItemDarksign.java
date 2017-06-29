package com.grumpybear.chromeng.item;

import com.grumpybear.chromeng.block.tile.TileBonfire;
import com.grumpybear.chromeng.chroma.IChargeableSingle;
import com.grumpybear.chromeng.lib.LibItemTypes;
import com.grumpybear.chromeng.lib.LibItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static com.grumpybear.chromeng.util.ItemStackUtil.getNBT;

public class ItemDarksign extends ItemChargeSingle implements IChargeableSingle {
	public final int CE_COST = 0;
	
	public ItemDarksign() {
		super(LibItems.DARKSIGN, LibItemTypes.DARKSIGN);
	}


	public void linkBonfire(TileBonfire bonfire, ItemStack stack, EntityPlayer playerIn, World world) {
		if (bonfire.isBurning()) {
			bonfire.writeToNBT(getNBT(stack));
		} else {
			if (!world.isRemote)
				playerIn.sendMessage(new TextComponentString("This bonfire has not been lit. Cannot link Darksign."));
		}
	}

	public boolean isLinked(ItemStack stack) {
		if (getNBT(stack).hasKey("Burning"))
			return true;

		return false;
	}


	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (this.getChromaUnit(playerIn.getHeldItemMainhand()).getCurrentCE() - CE_COST >= 0) {
			if (!getNBT(playerIn.getHeldItemMainhand()).hasNoTags()) {
				if (getNBT(playerIn.getHeldItemMainhand()).getBoolean("Burning")) {
					TileBonfire temp = new TileBonfire();
					temp.readFromNBT(getNBT(playerIn.getHeldItemMainhand()));

					particleLogic(playerIn, worldIn);

					playerIn.setPositionAndUpdate(temp.getPos().offset(EnumFacing.SOUTH).getX() + 1, temp.getPos().offset(EnumFacing.SOUTH).getY(), temp.getPos().offset(EnumFacing.SOUTH).getZ() + 1);
					this.minusCE(playerIn.getHeldItemMainhand(), CE_COST);

					particleLogic(playerIn, worldIn);
				}
			}
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
	}

	private void particleLogic(EntityPlayer player, World worldIn) {
		if (worldIn.isRemote) {
			Random rand = new Random();
			BlockPos pos = player.getPosition();
			double d0 = (double)pos.getX() + 0.5D;
			double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 8.0D;
			double d2 = (double)pos.getZ() + 0.5D;

			double motionX = rand.nextGaussian() * 0.02D;
			double motionY = rand.nextGaussian() * 0.02D;
			double motionZ = rand.nextGaussian() * 0.02D;

			for (int i = 0; i < 100; i++)
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + (rand.nextDouble() * 0.6D - 0.3D), 0.0D, 0.0D, 0.0D);
		}
	}


	@Override
	public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
	   super.addInformation(stack, playerIn, tooltip, advanced);

	   if (isLinked(stack)) {
			TileBonfire pos = new TileBonfire();
			pos.readFromNBT(getNBT(stack));
			tooltip.add("Linked Coords: " + "X:" + pos.getPos().getX() + " Y:" + pos.getPos().getY() + " Z:" + pos.getPos().getZ());
	   }
	}

}
