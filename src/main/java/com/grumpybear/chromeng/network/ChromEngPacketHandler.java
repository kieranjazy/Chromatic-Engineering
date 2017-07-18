package com.grumpybear.chromeng.network;

import com.grumpybear.chromeng.lib.LibMain;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Kieran on 7/5/2017.
 */
public class ChromEngPacketHandler {
   public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LibMain.MOD_ID);

   public static void register() {
      INSTANCE.registerMessage(MessageLink.MessageLinkHandler.class, MessageLink.class, 0, Side.SERVER);
      INSTANCE.registerMessage(MessageSwitchMode.MessageSwitchModeHandler.class, MessageSwitchMode.class, 1, Side.SERVER);
   }
}
