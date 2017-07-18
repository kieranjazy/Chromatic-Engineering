package com.grumpybear.chromeng.gui.container.chroma;

import com.grumpybear.chromeng.block.tile.TileCE;
import com.grumpybear.chromeng.block.tile.TileExtractor;
import com.grumpybear.chromeng.chroma.IChromaStorage;
import com.grumpybear.chromeng.gui.container.ContainerCE;
import com.grumpybear.chromeng.gui.slot.SlotEnergyOutput;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerChroma extends ContainerCE{
	
	IChromaStorage chromaHandler;
	private int current1;
	private int current2;
	private int current3;
	private int current4;
	private int current5;
	private int current6;
	private int current7;

	public ContainerChroma(IInventory playerInv, TileEntity chromaHandler) {
		super(playerInv, (TileCE) chromaHandler);
		this.chromaHandler = (IChromaStorage) chromaHandler;

		TileExtractor tile = (TileExtractor) chromaHandler;

		addSlotToContainer(new SlotEnergyOutput(tile, 4, 179, 116));

	}
	
	@Override
	public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, (IInventory) this.chromaHandler);
    }

    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        TileExtractor tile = (TileExtractor) this.chromaHandler;
        
        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.current1 != tile.getField(4))
            {
                icontainerlistener.sendProgressBarUpdate(this, 4, tile.getField(4));
            }

            if (this.current1 != tile.getField(5))
            {
                icontainerlistener.sendProgressBarUpdate(this, 5, tile.getField(5));
            }
            
            if (this.current1 != tile.getField(6))
            {
                icontainerlistener.sendProgressBarUpdate(this, 6, tile.getField(6));
            }
            
            if (this.current1 != tile.getField(7))
            {
                icontainerlistener.sendProgressBarUpdate(this, 7, tile.getField(7));
            }
            
            if (this.current1 != tile.getField(8))
            {
                icontainerlistener.sendProgressBarUpdate(this, 8, tile.getField(8));
            }
            
            if (this.current1 != tile.getField(9))
            {
                icontainerlistener.sendProgressBarUpdate(this, 9, tile.getField(9));
            }
            
            if (this.current1 != tile.getField(10))
            {
                icontainerlistener.sendProgressBarUpdate(this, 10, tile.getField(10));
            }
        }

        this.current1 = tile.getField(4);
        this.current2 = tile.getField(5);
        this.current3 = tile.getField(6);
        this.current4 = tile.getField(7);
        this.current5 = tile.getField(8);
        this.current6 = tile.getField(9);
        this.current7 = tile.getField(10);
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        ((TileExtractor) chromaHandler).setField(id, data);
    }
	
}
