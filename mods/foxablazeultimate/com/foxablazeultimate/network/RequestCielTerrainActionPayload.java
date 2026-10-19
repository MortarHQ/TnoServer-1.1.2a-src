package com.foxablazeultimate.network;

import com.foxablazeultimate.ability.skill.ciel.CielTakeoverTerrainHandler;
import dev.architectury.networking.NetworkManager.PacketContext;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record RequestCielTerrainActionPayload(RequestCielTerrainActionPayload.Action action, BlockPos position) implements CustomPacketPayload {
   public static final Type<RequestCielTerrainActionPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "request_ciel_terrain_action")
   );
   public static final StreamCodec<FriendlyByteBuf, RequestCielTerrainActionPayload> STREAM_CODEC = CustomPacketPayload.codec(
      RequestCielTerrainActionPayload::encode, RequestCielTerrainActionPayload::new
   );

   public RequestCielTerrainActionPayload(FriendlyByteBuf buffer) {
      this((RequestCielTerrainActionPayload.Action)buffer.readEnum(RequestCielTerrainActionPayload.Action.class), buffer.readBlockPos());
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeEnum(this.action);
      buffer.writeBlockPos(this.position);
   }

   public static void handle(RequestCielTerrainActionPayload payload, PacketContext context) {
      if (context.getPlayer() instanceof ServerPlayer player) {
         context.queue(() -> CielTakeoverTerrainHandler.handleIntent(player, payload.action(), payload.position()));
      }
   }

   public Type<RequestCielTerrainActionPayload> type() {
      return TYPE;
   }

   public enum Action {
      PLACE_PILLAR,
      START_BREAK,
      CANCEL_BREAK;
   }
}
