package net.blay09.mods.waystones.network.message;

import java.util.UUID;
import net.blay09.mods.waystones.Waystones;
import net.blay09.mods.waystones.api.WaystonesAPI;
import net.blay09.mods.waystones.core.WaystoneProxy;
import net.blay09.mods.waystones.menu.WaystoneSelectionMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class SelectWaystoneMessage implements CustomPacketPayload {
   public static final Type<SelectWaystoneMessage> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("waystones", "select_waystone"));
   private final UUID waystoneUid;

   public SelectWaystoneMessage(UUID waystoneUid) {
      this.waystoneUid = waystoneUid;
   }

   public static void encode(FriendlyByteBuf buf, SelectWaystoneMessage message) {
      buf.writeUUID(message.waystoneUid);
   }

   public static SelectWaystoneMessage decode(FriendlyByteBuf buf) {
      UUID waystoneUid = buf.readUUID();
      return new SelectWaystoneMessage(waystoneUid);
   }

   public static void handle(ServerPlayer player, SelectWaystoneMessage message) {
      if (player.containerMenu instanceof WaystoneSelectionMenu selectionMenu) {
         WaystoneProxy var4 = new WaystoneProxy(player.server, message.waystoneUid);
         if (selectionMenu.getWaystones().stream().noneMatch(it -> it.getWaystoneUid().equals(var4.getWaystoneUid()))) {
            Waystones.logger.warn("{} tried to teleport to waystone {} that they don't have access to.", player.getName().getString(), var4.getWaystoneUid());
         } else {
            WaystonesAPI.createDefaultTeleportContext(player, var4, it -> {
                  it.setFromWaystone(selectionMenu.getWaystoneFrom());
                  it.setWarpItem(selectionMenu.getWarpItem());
                  it.addFlags(selectionMenu.getFlags());
               })
               .ifLeft(WaystonesAPI::tryTeleport)
               .ifLeft(selectionMenu.getPostTeleportHandler())
               .ifRight(error -> player.displayClientMessage(error.getComponent().copy().withStyle(ChatFormatting.DARK_RED), false));
            player.closeContainer();
         }
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
