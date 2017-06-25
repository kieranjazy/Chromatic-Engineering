package com.grumpybear.chromeng.block.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileBonfire extends TileCE{

	private boolean isBurning;
	
	
	
	
	
	
	
	public boolean isBurning() {
		return isBurning;
	}
	
	public void setBurning(boolean bool) {
		isBurning = bool;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("Burning", isBurning);
		
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		isBurning = nbt.getBoolean("Burning");
	}
	
}
