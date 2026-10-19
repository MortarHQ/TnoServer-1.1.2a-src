package com.nododiiiii.ponderer.platform.services;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public interface NetworkHelper {
   void registerPackets();

   void sendToServer(CustomPacketPayload var1);

   void sendToPlayer(ServerPlayer var1, CustomPacketPayload var2);
}
