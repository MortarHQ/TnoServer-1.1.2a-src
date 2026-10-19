package net.blay09.mods.waystones.network.message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.blay09.mods.waystones.Waystones;
import net.blay09.mods.waystones.api.WaystoneVisibility;
import net.blay09.mods.waystones.api.error.WaystoneEditError;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WaystoneImpl;
import net.blay09.mods.waystones.core.WaystoneManagerImpl;
import net.blay09.mods.waystones.core.WaystonePermissionManager;
import net.blay09.mods.waystones.core.WaystoneProxy;
import net.blay09.mods.waystones.core.WaystoneSyncManager;
import net.blay09.mods.waystones.core.WaystoneVisibilities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class EditWaystoneMessage implements CustomPacketPayload {
   public static final Type<EditWaystoneMessage> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("waystones", "edit_waystone"));
   private final UUID waystoneUid;
   private final String name;
   private final WaystoneVisibility visibility;

   public EditWaystoneMessage(UUID waystoneUid, String name, WaystoneVisibility visibility) {
      this.waystoneUid = waystoneUid;
      this.name = name;
      this.visibility = visibility;
   }

   public static void encode(FriendlyByteBuf buf, EditWaystoneMessage message) {
      buf.writeUUID(message.waystoneUid);
      buf.writeUtf(message.name);
      buf.writeEnum(message.visibility);
   }

   public static EditWaystoneMessage decode(FriendlyByteBuf buf) {
      UUID waystoneUid = buf.readUUID();
      String name = buf.readUtf(255);
      WaystoneVisibility visibility = (WaystoneVisibility)buf.readEnum(WaystoneVisibility.class);
      return new EditWaystoneMessage(waystoneUid, name, visibility);
   }

   public static void handle(ServerPlayer player, EditWaystoneMessage message) {
      WaystoneProxy waystone = new WaystoneProxy(player.server, message.waystoneUid);
      if (!waystone.isValid()) {
         Waystones.logger.warn("{} tried to edit an invalid waystone with id {}", player.getName().getString(), message.waystoneUid);
      } else {
         Optional<WaystoneEditError> error = WaystonePermissionManager.mayEditWaystone(player, waystone);
         if (!error.isPresent()) {
            WaystoneVisibility visibility = message.visibility;
            List<WaystoneVisibility> visibilityOptions = WaystoneVisibilities.getVisibilityOptions(player, waystone);
            if (!visibilityOptions.contains(message.visibility)) {
               Waystones.logger.warn("{} tried to edit a waystone with an invalid visibility {}", player.getName().getString(), message.visibility);
               visibility = visibilityOptions.getFirst();
            }

            if (!WaystonePermissionManager.isAllowedVisibility(visibility) && !WaystonePermissionManager.skipsPermissions(player)) {
               Waystones.logger.warn("{} tried to edit a restricted waystone without permission", player.getName().getString());
            } else {
               BlockPos pos = waystone.getPos();
               if (!(player.distanceToSqr(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F) > 64.0)) {
                  WaystoneImpl backingWaystone = (WaystoneImpl)waystone.getBackingWaystone();
                  Component legalName = makeNameLegal(player.server, message.name);
                  backingWaystone.setName(legalName);
                  if (visibility == WaystoneVisibility.GLOBAL
                     && (WaystonePermissionManager.isAllowedVisibility(visibility) || WaystonePermissionManager.skipsPermissions(player))
                     && backingWaystone.getVisibility() != WaystoneVisibility.GLOBAL) {
                     PlayerWaystoneManager.activeWaystoneForEveryone(player.server, backingWaystone);
                  }

                  backingWaystone.setVisibility(visibility);
                  WaystoneManagerImpl.get(player.server).setDirty();
                  WaystoneSyncManager.sendWaystoneUpdateToAll(player.server, backingWaystone);
                  player.closeContainer();
               }
            }
         }
      }
   }

   private static Component makeNameLegal(MinecraftServer server, String input) {
      if (input.trim().isEmpty()) {
         return Component.translatable("waystones.untitled_waystone");
      }

      String inventoryButtonMode = WaystonesConfig.getActive().inventoryButton.inventoryButton;
      return inventoryButtonMode.equals(input) && WaystoneManagerImpl.get(server).findWaystoneByName(input).isPresent()
         ? Component.literal(input + "*")
         : Component.literal(input);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
