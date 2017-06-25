package com.grumpybear.chromeng.gui.container;

import com.grumpybear.chromeng.block.tile.TileEnergiser;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerEnergiser extends ContainerCE{
	TileEnergiser myTile;

	public ContainerEnergiser(IInventory playerInv, TileEntity tile) {
		super(playerInv);
		
		myTile = (TileEnergiser) tile;
		
		addSlotToContainer(new Slot(myTile, 0, 80, 35));
	}

}
