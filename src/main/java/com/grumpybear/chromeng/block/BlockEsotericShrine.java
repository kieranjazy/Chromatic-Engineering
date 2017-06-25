package com.grumpybear.chromeng.block;

import javax.annotation.Nonnull;

import com.grumpybear.chromeng.block.tile.TileShrine;
import com.grumpybear.chromeng.lib.LibBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEsotericShrine extends BlockCE {
	
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.5, 1);
	
	public BlockEsotericShrine() {
		super(Material.ROCK, LibBlocks.ESOTERIC_SHRINE);
	}

	@Nonnull
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return AABB;
	}
	
	@Nonnull
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float par7, float par8, float par9) {
		TileShrine tile = (TileShrine) world.getTileEntity(pos);
		if(!(tile instanceof TileShrine)) {
			return false;
		}
		
		ItemStack stack = player.getHeldItem(hand);
		if (stack.isEmpty()) {
			player.setHeldItem(hand, tile.removeStackFromSlot(0));
			return true;
		} else {
			if (stack.getItem() == Items.BOOK) {
				tile.setInventorySlotContents(0, stack.copy());
				player.setHeldItem(hand, ItemStack.EMPTY);
			}
			return true;
		}
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nonnull
	@Override
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
		return new TileShrine();
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}
