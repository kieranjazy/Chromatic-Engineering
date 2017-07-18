package com.grumpybear.chromeng.block.tile;

import com.grumpybear.chromeng.chroma.*;
import com.grumpybear.chromeng.init.ModItems;
import com.grumpybear.chromeng.lib.LibNumbers;
import com.grumpybear.chromeng.util.ItemStackUtil;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TileExtractor extends TileInventory implements ISidedInventory, IChromaStorage, ICharger{

	//Slot 0 - Dust Input
	//Slot 1 - Fuel
	//Slot 2 - Output
	
	private int extractorBurnTime;
	private int cookTime;
	private int currentItemBurnTime;
	private int totalCookTime;

   private int randInt;

   private ArrayList<EnumColour> colours;
	
	public static final int MAX_CHROMA = 1000;
	
	public final ChromaStorage chromaStorage;
	
	public TileExtractor() {
		chromaStorage = new ChromaStorage(MAX_CHROMA);
       randInt = 7;
       colours = new ArrayList<EnumColour>(Arrays.asList(EnumColour.values()));
    }
	

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
    {
        ItemStack itemstack = this.getStackInSlot(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        super.setInventorySlotContents(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag)
        {
            this.totalCookTime = this.getCookTime(stack);
            this.cookTime = 0;
            this.markDirty();
        }
    }
	
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.extractorBurnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.currentItemBurnTime = 200;
        
        chromaStorage.readFromNBT(compound);

    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", (short)this.extractorBurnTime);
        compound.setInteger("CookTime", (short)this.cookTime);
        compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
        
        chromaStorage.writeToNBT(compound);

        return compound;
    }
	
	
	
	@Override
	public void update() {

       // *Charging logic

       tryCharge(4);

		// *Extraction Logic

		boolean flag = this.isBurning();
        boolean flag1 = false;

        if (this.isBurning())
        {
            --this.extractorBurnTime;
        }

        if (!this.world.isRemote)
        {
            ItemStack itemstack = this.getStackInSlot(1);

            if (this.isBurning() || !itemstack.isEmpty() && !((ItemStack)this.getStackInSlot(0)).isEmpty()) //If currently burning or has coal and an item but not burning
            {
                if (!this.isBurning() && this.canSmelt()) //If not burning but can smelt and has coal
                {
                    this.extractorBurnTime = 300;
                    this.currentItemBurnTime = this.extractorBurnTime;

                    if (this.isBurning()) //If its burning
                    {
                        flag1 = true;

                        if (!itemstack.isEmpty()) //If has item
                        {
                            Item item = itemstack.getItem(); //Gets the item
                            itemstack.shrink(1); //Shrinks it

                            if (itemstack.isEmpty()) //If empty after shrinking
                            {
                                ItemStack item1 = item.getContainerItem(itemstack);
                                this.setInventorySlotContents(1, item1);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt()) //If burning and can smelt
                {
                    ++this.cookTime; //Increases cook time

                    ArrayList<EnumColour> colours= new ArrayList<EnumColour>(Arrays.asList(EnumColour.values()));
                    Random rand = new Random();
                    EnumColour colour = colours.get(rand.nextInt(randInt));
                    
                    if (chromaStorage.getColour(colour).getCurrentCE() + LibNumbers.TRANSFER_RATE < 1005) {
                    	chromaStorage.getColour(colour).addCurrentCE(LibNumbers.TRANSFER_RATE);
                    }

                    if (this.cookTime == this.totalCookTime) //If cook time = totalCookTIme
                    {
                        this.cookTime = 0; //Cook Time resets
                        this.totalCookTime = this.getCookTime(this.getStackInSlot(0));
                        this.extractItem();
                        flag1 = true;
                    }
                }
                else
                {
                    this.cookTime = 0;
                }
            }
            else if (!this.isBurning() && this.cookTime > 0) //If not burning but cookTime > 0
            {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (flag != this.isBurning())
            {
                flag1 = true;
            }
        }

        if (flag1)
        {
            this.markDirty();
        }

	}
	
	public int getCookTime(ItemStack stack)
    {
        return 300;
    }

    public void tryCharge(int i) {
	   ItemStack stack = getStackInSlot(i);
	   if (stack.getItem() instanceof IChargeableSingle) {
	      if (this.getChromaStorage().getColour(EnumColour.ORANGE).getCurrentCE() - LibNumbers.TRANSFER_RATE >= 0 && ItemStackUtil.getChromaUnit(stack).getCurrentCE() + LibNumbers.TRANSFER_RATE <= ItemStackUtil.getChromaUnit(stack).getMaxCE()) {
             if (((IChargeableSingle) stack.getItem()).addCE(stack, LibNumbers.TRANSFER_RATE))
               this.getChromaStorage().getColour(EnumColour.ORANGE).minusCurrentCE(LibNumbers.TRANSFER_RATE);
          }
       }

       if (stack.getItem() instanceof IChargeableMulti) {
	      IChargeableMulti chargeItem = (IChargeableMulti) stack.getItem();
	      for (EnumColour colour : colours) {
	         if (this.getChromaStorage().getColour(colour).getCurrentCE() - LibNumbers.TRANSFER_RATE >= 0 && chargeItem.getChromaStorage(stack).getColour(colour).getCurrentCE() + LibNumbers.TRANSFER_RATE <= chargeItem.getChromaStorage(stack).getMaxCE()) {
                chargeItem.addCE(stack, LibNumbers.TRANSFER_RATE, colour);
                this.getChromaStorage().getColour(colour).minusCurrentCE(LibNumbers.TRANSFER_RATE);
             }
          }
       }
    }
	
	public void extractItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = this.getStackInSlot(0);
            ItemStack itemstack1 = new ItemStack(ModItems.chromaDust, 1, 1);
            ItemStack itemstack2 = this.getStackInSlot(2);

            if (itemstack2.isEmpty())
            {
                this.setInventorySlotContents(2, itemstack1.copy());
            }
            else if (itemstack2.getItem() == itemstack1.getItem())
            {
                itemstack2.grow(itemstack1.getCount());
            }

            itemstack.shrink(1);
        }
        
        /*
        Random rand = new Random();
        EnumColour colour = EnumColour.values()[rand.nextInt(7)];
        
        if (chromaStorage.getColour(colour).getCurrentCE() + 100 <= 1000) {
        	chromaStorage.getColour(colour).addCurrentCE(100);
        	ChromEng.logger.warn(chromaStorage.getColour(colour).getChromaType().toString() + chromaStorage.getColour(colour).getCurrentCE());
        }
        */
    }
	
	private boolean canSmelt()
    {
        if (((ItemStack)this.getStackInSlot(0)).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack itemstack = new ItemStack(ModItems.chromaDust, 1, 1);

            if (itemstack.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack itemstack1 = this.getStackInSlot(2);

                if (itemstack1.isEmpty() && this.getStackInSlot(0).getItem() == ModItems.chromaDust)
                {
                    return true;
                }
                else if (!itemstack1.isItemEqual(itemstack))
                {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
                {
                    return true;
                }
                else
                {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        }
    }

	
	
	public boolean isBurning() {
		return this.extractorBurnTime > 0;
	}
	
	
	public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.extractorBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            case 4:
            	return this.chromaStorage.getColour(EnumColour.RED).getCurrentCE();
            case 5:
            	return this.chromaStorage.getColour(EnumColour.ORANGE).getCurrentCE();
            case 6:
            	return this.chromaStorage.getColour(EnumColour.YELLOW).getCurrentCE();
            case 7:
            	return this.chromaStorage.getColour(EnumColour.GREEN).getCurrentCE();
            case 8:
            	return this.chromaStorage.getColour(EnumColour.BLUE).getCurrentCE();
            case 9:
            	return this.chromaStorage.getColour(EnumColour.INDIGO).getCurrentCE();
            case 10:
            	return this.chromaStorage.getColour(EnumColour.VIOLET).getCurrentCE();	
            default:
                return 0;
        }
    }
	
	@Override
	public void setField(int id, int value) {
		switch (id)
        {
            case 0:
                this.extractorBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
                break;
            case 4:
            	this.chromaStorage.getColour(EnumColour.RED).setCurrentCE(value);
            	break;
            case 5:
            	this.chromaStorage.getColour(EnumColour.ORANGE).setCurrentCE(value);
            	break;
            case 6:
            	this.chromaStorage.getColour(EnumColour.YELLOW).setCurrentCE(value);
            	break;
            case 7:
            	this.chromaStorage.getColour(EnumColour.GREEN).setCurrentCE(value);
            	break;
            case 8:
            	this.chromaStorage.getColour(EnumColour.BLUE).setCurrentCE(value);
            	break;
            case 9:
            	this.chromaStorage.getColour(EnumColour.INDIGO).setCurrentCE(value);
            	break;
            case 10:
            	this.chromaStorage.getColour(EnumColour.VIOLET).setCurrentCE(value);
            	break;	
        }
	}
	

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }
	
	@Override
	public int getSizeInventory() {
		return 4 + 1;
	}
	
	
	//*ISidedInventory*
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		if (side == EnumFacing.DOWN)
			return new int[] {1};
		
		if (side == EnumFacing.UP)
			return new int[] {0};
		
		if (side == EnumFacing.EAST)
			return new int[] {2};
		
		return new int[] {};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		if (index == 2)
			return false;
		
		if (index == 1 && itemStackIn.getItem() == Items.COAL) 
			return true;
		
		if (index == 0 && itemStackIn.getItem() == ModItems.chromaDust) {
			if (itemStackIn.getItemDamage() == 0) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if (index == 2)
			return true;
		
		return false;
	}

	@Override
	public ChromaStorage getChromaStorage() {
		return chromaStorage;
	}


   @Override
   public void addCE(ItemStack stack, int ce, EnumColour theColour) { //Adds CE to a chargeable item and takes that CE from this chroma storage
      if (stack.getItem() instanceof IChargeableSingle) {
         IChargeableSingle item = (IChargeableSingle) stack.getItem();

         if (this.getChromaStorage().getColour(item.getColourType()).getCurrentCE() - LibNumbers.TRANSFER_RATE >= 0 && item.getChromaUnit(stack).getCurrentCE() + LibNumbers.TRANSFER_RATE <= item.getChromaUnit(stack).getMaxCE()) {
            item.addCE(stack, LibNumbers.TRANSFER_RATE);
            minusCE(stack, LibNumbers.TRANSFER_RATE, item.getColourType());
         }
      }

      if (stack.getItem() instanceof IChargeableMulti) {
         IChargeableMulti item = (IChargeableMulti) stack.getItem();
         for (EnumColour colour : colours) {
            if (this.getChromaStorage().getColour(colour).getCurrentCE() - LibNumbers.TRANSFER_RATE >= 0 && item.getChromaStorage(stack).getColour(colour).getCurrentCE() + LibNumbers.TRANSFER_RATE <= item.getChromaStorage(stack).getMaxCE()) {
               item.addCE(stack, LibNumbers.TRANSFER_RATE, colour);
               minusCE(stack, ce, colour);
            }
         }
      }
   }

   @Override
   public void minusCE(ItemStack stack, int ce, EnumColour colour) {
      this.getChromaStorage().getColour(colour).minusCurrentCE(ce);
   }
}
