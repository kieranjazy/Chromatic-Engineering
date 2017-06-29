package com.grumpybear.chromeng.chroma;

import net.minecraft.nbt.NBTTagCompound;

public class ChromaStorage {
	
	
	
	public ChromaUnit red;
	public ChromaUnit orange;
	public ChromaUnit yellow;
	public ChromaUnit green;
	public ChromaUnit blue;
	public ChromaUnit indigo;
	public ChromaUnit violet;
	
	int MAX_CE;

	public ChromaStorage(EnumColour c1, EnumColour c2, EnumColour c3, EnumColour c4, EnumColour c5, EnumColour c6, EnumColour c7, int max) {
		MAX_CE = max;
		
		EnumColour[] params = new EnumColour[] {c1,	c2,	c3, c4, c5, c6, c7};
		
		for (EnumColour colour : params) {
			if (colour == null) {
				
			}
		
			switch(colour) {
				case RED: 		red = new ChromaUnit(colour);
								break;
				case ORANGE:    orange = new ChromaUnit(colour);
								break;
				case YELLOW: 	yellow = new ChromaUnit(colour);
								break;
				case GREEN: 	green = new ChromaUnit(colour);
								break;
				case BLUE: 		blue = new ChromaUnit(colour);
								break;
				case INDIGO:    indigo = new ChromaUnit(colour);
								break;
				case VIOLET: 	violet = new ChromaUnit(colour);
								break;
			}
		}
	}
	
	public ChromaStorage(EnumColour c1, EnumColour c2, EnumColour c3, EnumColour c4, EnumColour c5, EnumColour c6, int max) {
		this(c1, c2, c3, c4, c5, c6, null, max);
	}
	
	public ChromaStorage(EnumColour c1, EnumColour c2, EnumColour c3, EnumColour c4, EnumColour c5, int max) {
		this(c1, c2, c3, c4, c5, null, max);
	}
	
	public ChromaStorage(EnumColour c1, EnumColour c2, EnumColour c3, EnumColour c4, int max) {
		this(c1, c2, c3, c4, null, max);
	}
	
	public ChromaStorage(EnumColour c1, EnumColour c2, EnumColour c3, int max) {
		this(c1, c2, c3, null, max);
	}
	
	public ChromaStorage(EnumColour c1, EnumColour c2, int max) {
		this(c1, c2, null, max);
	}
	
	public ChromaStorage(EnumColour c1, int max) {
		this(c1, null, max);
	}
	
	public ChromaStorage(int max) { 
		this(EnumColour.RED, EnumColour.ORANGE, EnumColour.YELLOW, EnumColour.GREEN, EnumColour.BLUE, EnumColour.INDIGO, EnumColour.VIOLET, max);
	}
	
	public ChromaUnit getColour(EnumColour colour) {
		for (ChromaUnit unit : getChromaUnits()) {
			if (unit.getChromaType() == colour) {
				return unit;
			}
		}
		
		return null;
	}
	
	public ChromaUnit[] getChromaUnits() {
		return new ChromaUnit[] {red, orange, yellow, green, blue, indigo, violet};
	}
	
	public int getMaxCE() {
		return MAX_CE;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		for (ChromaUnit unit : getChromaUnits()) {
			unit.writeToNBT(nbt);
		}
		
		return nbt;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		for (ChromaUnit unit : getChromaUnits()) {
			unit.readFromNBT(nbt);
		}
	}

	public static ChromaStorage storageFromNBT(NBTTagCompound nbt) {
		ChromaStorage temp = new ChromaStorage(1000);
		if (nbt.hasNoTags()) {
			return temp;
		}


		for (ChromaUnit unit : temp.getChromaUnits()) {
			unit.readFromNBT(nbt);
		}

		return temp; //Returns an accurate ChromaStorage with 10CE
	}

	public static void storageToNBT(ChromaStorage storage, NBTTagCompound nbt) {

		for (ChromaUnit unit : storage.getChromaUnits()) {
			unit.writeToNBT(nbt);
		}
		nbt.setBoolean("Empty", storage.isEmpty());
	}

	public boolean isEmpty() {
		for (ChromaUnit unit : getChromaUnits()) {
			if (!unit.isEmpty()) {
				return false;
			}
		}
		return true;
	}
}
