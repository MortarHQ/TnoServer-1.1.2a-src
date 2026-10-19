package com.foxablazeultimate.network;

import com.foxablazeultimate.predation.PredationFilterHelper;
import dev.architectury.networking.NetworkManager.PacketContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record RequestPredationFilterSyncPayload() implements CustomPacketPayload {
   public static final Type<RequestPredationFilterSyncPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "request_predation_filter_sync")
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, RequestPredationFilterSyncPayload> STREAM_CODEC = CustomPacketPayload.codec(
      RequestPredationFilterSyncPayload::encode, RequestPredationFilterSyncPayload::new
   );

   public RequestPredationFilterSyncPayload(RegistryFriendlyByteBuf buf) {
      this();
   }

   public void encode(RegistryFriendlyByteBuf buf) {
   }

   public static void handle(RequestPredationFilterSyncPayload payload, PacketContext context) {
      if (context.getPlayer() instanceof ServerPlayer player) {
         context.queue(() -> PredationFilterHelper.sendSync(player));
      }
   }

   public Type<RequestPredationFilterSyncPayload> type() {
      return TYPE;
   }
}
