package com.grumpybear.chromeng.proxy;

import com.grumpybear.chromeng.handler.ModelHandler;
import com.grumpybear.chromeng.handler.RenderWorldLastEventHandler;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy{
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		ModelHandler.registerModels();
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void renderWorldLastEvent(RenderWorldLastEvent event) {
		RenderWorldLastEventHandler.tick(event);
	}
}
