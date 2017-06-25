package com.grumpybear.chromeng.init;

import com.grumpybear.chromeng.block.BlockBonfire;
import com.grumpybear.chromeng.block.BlockChromaOre;
import com.grumpybear.chromeng.block.BlockEsotericShrine;
import com.grumpybear.chromeng.block.BlockShrineReceptacle;
import com.grumpybear.chromeng.block.chroma.BlockChromaticExtractor;
import com.grumpybear.chromeng.block.tile.TileBonfire;
import com.grumpybear.chromeng.block.tile.TileDisplacer;
import com.grumpybear.chromeng.block.tile.TileEnergiser;
import com.grumpybear.chromeng.block.tile.TileExtractor;
import com.grumpybear.chromeng.block.tile.TileReceptacle;
import com.grumpybear.chromeng.block.tile.TileShrine;
import com.grumpybear.chromeng.block.workonlater.BlockEnergiser;
import com.grumpybear.chromeng.block.workonlater.BlockFluidDisplacer;
import com.grumpybear.chromeng.lib.LibBlocks;
import com.grumpybear.chromeng.lib.LibMain;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Block esotericShrine;
	public static Block fluidDisplacer;
	public static Block shrineReceptacle;
	public static Block energiser;
	public static Block chromaOre;
	public static Block extractor;
	public static Block bonfire;
	
	public static void init() {
		esotericShrine = new BlockEsotericShrine();
		fluidDisplacer = new BlockFluidDisplacer();
		shrineReceptacle = new BlockShrineReceptacle();
		energiser = new BlockEnergiser();
		chromaOre = new BlockChromaOre();
		extractor = new BlockChromaticExtractor();
		bonfire = new BlockBonfire();
		
		initTileEntities();
	}
	
	public static void initTileEntities() {
		registerTile(TileShrine.class, LibBlocks.ESOTERIC_SHRINE);
		registerTile(TileDisplacer.class, LibBlocks.FLUID_DISPLACER);
		registerTile(TileReceptacle.class, LibBlocks.SHRINE_RECEPTACLE);
		registerTile(TileEnergiser.class, LibBlocks.ENERGISER);
		registerTile(TileExtractor.class, LibBlocks.CHROMATIC_EXTRACTOR);
		registerTile(TileBonfire.class, LibBlocks.BONFIRE);
	}
	
	private static void registerTile(Class<? extends TileEntity> clazz, String key) {
		GameRegistry.registerTileEntity(clazz, LibMain.MOD_ID + ":" + key);
	}
}
