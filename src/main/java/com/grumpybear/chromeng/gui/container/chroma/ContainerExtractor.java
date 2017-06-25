package com.grumpybear.chromeng.gui.container.chroma;

import com.grumpybear.chromeng.block.tile.TileExtractor;
import com.grumpybear.chromeng.chroma.IChromaStorage;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerExtractor extends ContainerChroma{
	TileExtractor myTile;
	private int cookTime;
    private int totalCookTime;
    private int extractorBurnTime;
    private int currentItemBurnTime;
	
	public ContainerExtractor(IInventory playerInv, TileEntity tile) {
		super(playerInv, (IChromaStorage) tile);
		
		myTile = (TileExtractor) tile;

		addSlotToContainer(new Slot(myTile, 0, 56, 17)); //Chroma Dust Input
		addSlotToContainer(new SlotFurnaceFuel(myTile, 1, 56, 53)); //Fuel Input
		addSlotToContainer(new SlotFurnaceOutput(((InventoryPlayer) playerInv).player, myTile, 2, 116, 35)); //Output
		addSlotToContainer(new Slot(myTile, 3, 149, 6));  //Focus
		
	}
	
	@Override
	public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.myTile);
    }

	@Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.cookTime != this.myTile.getField(2))
            {
                icontainerlistener.sendProgressBarUpdate(this, 2, this.myTile.getField(2));
            }

            if (this.extractorBurnTime != this.myTile.getField(0))
            {
                icontainerlistener.sendProgressBarUpdate(this, 0, this.myTile.getField(0));
            }

            if (this.currentItemBurnTime != this.myTile.getField(1))
            {
                icontainerlistener.sendProgressBarUpdate(this, 1, this.myTile.getField(1));
            }

            if (this.totalCookTime != this.myTile.getField(3))
            {
                icontainerlistener.sendProgressBarUpdate(this, 3, this.myTile.getField(3));
            }
        }

        this.cookTime = this.myTile.getField(2);
        this.extractorBurnTime = this.myTile.getField(0);
        this.currentItemBurnTime = this.myTile.getField(1);
        this.totalCookTime = this.myTile.getField(3);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.myTile.setField(id, data);
    }
}
