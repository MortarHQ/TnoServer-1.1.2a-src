package com.faboslav.variantsandventures.common.network;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.network.packet.SyncZombieIsShakingPacketFromServer;
import com.teamresourceful.resourcefullib.common.network.Network;

public final class MessageHandler {
   public static final Network DEFAULT_CHANNEL = new Network(VariantsAndVentures.makeID("networking"), 1);

   public static void init() {
      DEFAULT_CHANNEL.register(SyncZombieIsShakingPacketFromServer.TYPE);
   }
}
