package com.grumpybear.chromeng.util;

import net.minecraft.util.math.BlockPos;

public class BlockPosUtil {
	

	// Changes BlockPos to unique long
	public static long posToLong(BlockPos pos) {
		return pos.toLong();
	}
	
	// Gets BlockPos from unique long
	public static BlockPos longToPos(long l) {
		if (l == 0) {
			
		}
		return BlockPos.fromLong(l);
	}
	
}
