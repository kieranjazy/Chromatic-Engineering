package com.grumpybear.chromeng.chroma;

import com.grumpybear.chromeng.util.EnumColourUtil;

import net.minecraft.nbt.NBTTagCompound;

public class ChromaUnit {
	int currentCE;
	int maxCE;
	
	EnumColour chromaType;
	
	public ChromaUnit(EnumColour chroma) {
		this.maxCE = 1000;
		this.currentCE = 0;
		this.chromaType = chroma;
	}
	
	public ChromaUnit(EnumColour chroma, int max) {
		this(chroma);
		this.maxCE = max;
	}
	
	public EnumColour getChromaType() {
		return chromaType;
	}
	
	public int getCurrentCE() {
		return currentCE;
	}
	
	public int getMaxCE() {
		return maxCE;
	}
	
	public void addCurrentCE(int currentCE) {
		this.currentCE += currentCE;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		String temp = EnumColourUtil.colourToString(chromaType);
		
		nbt.setInteger("Current" + temp, currentCE);
		nbt.setInteger("Max" + temp, maxCE);
		nbt.setByte("Colour" + temp, EnumColourUtil.colourToByte(chromaType));
		
		return nbt;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		String temp = EnumColourUtil.colourToString(chromaType);
		
		currentCE = nbt.getInteger("Current" + temp);
		maxCE = nbt.getInteger("Max" + temp);
		chromaType = EnumColourUtil.byteToColour(nbt.getByte("Colour" + temp));
	}
	
	public void setCurrentCE(int currentCE) {
		this.currentCE = currentCE;
	}
}
