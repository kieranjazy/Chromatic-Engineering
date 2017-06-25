package com.grumpybear.chromeng.handler;

import com.grumpybear.chromeng.init.ModBlocks;
import com.grumpybear.chromeng.init.ModItems;
import com.grumpybear.chromeng.lib.LibMain;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class CECreativeTab extends CreativeTabs{
	NonNullList<ItemStack> list;
	
	public static final CECreativeTab INSTANCE = new CECreativeTab();
	
	public CECreativeTab() {
		super(LibMain.MOD_ID);
		super.setNoTitle();
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(ModBlocks.esotericShrine);
	}
	
	private void addBlock(Block block) {
		block.getSubBlocks(this, list);
	}
	
	private void addItem(Item item) {
		item.getSubItems(this, list);
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> list) {
		this.list = list;
		addItem(ModItems.locationCard);
		addItem(ModItems.blockDesignator);
		addItem(ModItems.chromaDust);
		addItem(ModItems.darksign);
		addBlock(ModBlocks.bonfire);
		addBlock(ModBlocks.esotericShrine);
		addBlock(ModBlocks.fluidDisplacer);
		addBlock(ModBlocks.shrineReceptacle);
		addBlock(ModBlocks.energiser);
		addBlock(ModBlocks.chromaOre);
		addBlock(ModBlocks.extractor);
	}

	@Override
	public ItemStack getTabIconItem() {
		return getIconItemStack();
	}
	
	
}
