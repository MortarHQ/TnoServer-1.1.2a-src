package com.nododiiiii.ponderer.network;

import com.nododiiiii.ponderer.platform.PondererServices;
import com.nododiiiii.ponderer.ponder.UploadPermissions;
import com.nododiiiii.ponderer.ui.PermissionManagementScreen;
import com.nododiiiii.ponderer.ui.UIText;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record PermissionListResponsePayload(
   List<PermissionListResponsePayload.Entry> entries,
   String viewerRole,
   boolean serverOperator,
   boolean canManage,
   String messageKey,
   String messageSubject,
   boolean error
) implements CustomPacketPayload {
   public static final Type<PermissionListResponsePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("ponderer", "permission_list_response"));
   public static final StreamCodec<RegistryFriendlyByteBuf, PermissionListResponsePayload> CODEC = StreamCodec.of(
      PermissionListResponsePayload::encode, PermissionListResponsePayload::decode
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   private static void encode(RegistryFriendlyByteBuf buf, PermissionListResponsePayload payload) {
      buf.writeVarInt(payload.entries().size());

      for (PermissionListResponsePayload.Entry entry : payload.entries()) {
         buf.writeUtf(entry.subject());
         buf.writeUtf(entry.role());
         buf.writeBoolean(entry.locked());
      }

      buf.writeUtf(payload.viewerRole() == null ? "" : payload.viewerRole());
      buf.writeBoolean(payload.serverOperator());
      buf.writeBoolean(payload.canManage());
      buf.writeUtf(payload.messageKey() == null ? "" : payload.messageKey());
      buf.writeUtf(payload.messageSubject() == null ? "" : payload.messageSubject());
      buf.writeBoolean(payload.error());
   }

   private static PermissionListResponsePayload decode(RegistryFriendlyByteBuf buf) {
      int size = buf.readVarInt();
      List<PermissionListResponsePayload.Entry> entries = new ArrayList<>(size);

      for (int i = 0; i < size; i++) {
         entries.add(new PermissionListResponsePayload.Entry(buf.readUtf(), buf.readUtf(), buf.readBoolean()));
      }

      return new PermissionListResponsePayload(
         List.copyOf(entries), buf.readUtf(), buf.readBoolean(), buf.readBoolean(), buf.readUtf(), buf.readUtf(), buf.readBoolean()
      );
   }

   public static void sendSnapshot(ServerPlayer player, String messageKey, String messageSubject, boolean error) {
      UploadPermissions.Snapshot snapshot = UploadPermissions.snapshotFor(player);
      List<PermissionListResponsePayload.Entry> entries = new ArrayList<>();

      for (UploadPermissions.Entry entry : snapshot.entries()) {
         entries.add(new PermissionListResponsePayload.Entry(entry.subject(), entry.role().id(), entry.operatorManaged()));
      }

      UploadPermissions.Role viewerRole = snapshot.viewerRole();
      PondererServices.NETWORK
         .sendToPlayer(
            player,
            new PermissionListResponsePayload(
               List.copyOf(entries),
               viewerRole == null ? "" : viewerRole.id(),
               snapshot.serverOperator(),
               snapshot.canManage(),
               messageKey == null ? "" : messageKey,
               messageSubject == null ? "" : messageSubject,
               error
            )
         );
   }

   public static void handle(PermissionListResponsePayload payload) {
      Minecraft client = Minecraft.getInstance();
      if (client.screen instanceof PermissionManagementScreen screen) {
         screen.receiveSnapshot(payload);
      } else {
         if (payload.messageKey() != null && !payload.messageKey().isBlank() && client.player != null) {
            String text = payload.messageSubject() != null && !payload.messageSubject().isBlank()
               ? UIText.of(payload.messageKey(), payload.messageSubject())
               : UIText.of(payload.messageKey());
            client.player.displayClientMessage(Component.literal(text), false);
         }
      }
   }

   public record Entry(String subject, String role, boolean locked) {
   }
}
