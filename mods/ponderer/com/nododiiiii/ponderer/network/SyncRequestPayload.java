package com.nododiiiii.ponderer.network;

import com.nododiiiii.ponderer.ponder.UploadPermissions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public record SyncRequestPayload() implements CustomPacketPayload {
   public static final Type<SyncRequestPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("ponderer", "sync_request"));
   public static final StreamCodec<RegistryFriendlyByteBuf, SyncRequestPayload> CODEC = StreamCodec.of(SyncRequestPayload::encode, SyncRequestPayload::decode);

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   private static void encode(RegistryFriendlyByteBuf buf, SyncRequestPayload payload) {
   }

   private static SyncRequestPayload decode(RegistryFriendlyByteBuf buf) {
      return new SyncRequestPayload();
   }

   public static void handle(SyncRequestPayload payload, @Nullable ServerPlayer player) {
      if (player != null) {
         UploadPermissions.ensurePullAccess(player);
         if (!UploadPermissions.canPull(player)) {
            player.sendSystemMessage(Component.translatable("ponderer.cmd.pull.no_permission"));
         } else {
            SyncResponsePayload.sendBatched(player);
         }
      }
   }
}
