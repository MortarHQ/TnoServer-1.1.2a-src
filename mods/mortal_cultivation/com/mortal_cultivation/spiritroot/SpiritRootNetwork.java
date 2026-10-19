package com.mortal_cultivation.spiritroot;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.Nullable;

public final class SpiritRootNetwork {
   private SpiritRootNetwork() {
   }

   public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("1.0");
      registrar.playToClient(SpiritRootSyncPayload.TYPE, SpiritRootSyncPayload.STREAM_CODEC, SpiritRootNetwork::handleClientSync);
   }

   public static void syncToClient(ServerPlayer player, @Nullable SpiritRootType root) {
      PacketDistributor.sendToPlayer(player, new SpiritRootSyncPayload(root == null ? "" : root.getId()), new CustomPacketPayload[0]);
   }

   private static void handleClientSync(SpiritRootSyncPayload payload, IPayloadContext context) {
      context.enqueueWork(() -> SpiritRootClientState.setCurrentRootId(payload.rootId()));
   }
}
