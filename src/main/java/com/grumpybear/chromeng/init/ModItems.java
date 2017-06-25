package com.grumpybear.chromeng.init;

import com.grumpybear.chromeng.item.ItemChromaDust;
import com.grumpybear.chromeng.item.ItemDarksign;
import com.grumpybear.chromeng.item.workonlater.ItemBlockDesignator;
import com.grumpybear.chromeng.item.workonlater.ItemLocationCard;

import net.minecraft.item.Item;

public class ModItems {
	public static Item locationCard;
	public static Item blockDesignator;
	public static Item chromaDust;
	public static Item darksign;
	
	public static void init() {
		locationCard = new ItemLocationCard();
		blockDesignator = new ItemBlockDesignator();
		chromaDust = new ItemChromaDust();
		darksign = new ItemDarksign();
	}
}
