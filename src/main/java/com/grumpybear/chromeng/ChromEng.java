package com.grumpybear.chromeng;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.grumpybear.chromeng.handler.GuiHandler;
import com.grumpybear.chromeng.init.ModBlocks;
import com.grumpybear.chromeng.init.ModItems;
import com.grumpybear.chromeng.lib.LibMain;
import com.grumpybear.chromeng.proxy.IProxy;
import com.grumpybear.chromeng.world.CEWorldGen;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid=LibMain.MOD_ID, name=LibMain.MOD_NAME, version=LibMain.VERSION)
public class ChromEng {
	@Mod.Instance(LibMain.MOD_ID)
	public static ChromEng instance;
	
	@SidedProxy(clientSide = "com.grumpybear.chromeng.proxy.ClientProxy", serverSide = "com.grumpybear.chromeng.proxy.ServerProxy")
	public static IProxy proxy;
	
	public static final Logger logger = LogManager.getLogger(LibMain.MOD_ID);
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModBlocks.init();
		ModItems.init();
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		CEWorldGen worldGenerator = new CEWorldGen();
		GameRegistry.registerWorldGenerator(worldGenerator, 0);
	}
}
