package net.blay09.mods.waystones.network.message;

import java.util.Objects;
import java.util.UUID;
import net.blay09.mods.waystones.api.MutableWaystone;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.WaystoneVisibility;
import net.blay09.mods.waystones.block.WaystoneBlock;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WaystoneManagerImpl;
import net.blay09.mods.waystones.core.WaystoneProxy;
import net.blay09.mods.waystones.core.WaystoneSyncManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;

public class RemoveWaystoneMessage implements CustomPacketPayload {
   public static final Type<RemoveWaystoneMessage> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("waystones", "remove_waystone"));
   private final UUID waystoneUid;

   public RemoveWaystoneMessage(UUID waystoneUid) {
      this.waystoneUid = waystoneUid;
   }

   public static void encode(FriendlyByteBuf buf, RemoveWaystoneMessage message) {
      buf.writeUUID(message.waystoneUid);
   }

   public static RemoveWaystoneMessage decode(FriendlyByteBuf buf) {
      UUID waystoneUid = buf.readUUID();
      return new RemoveWaystoneMessage(waystoneUid);
   }

   public static void handle(ServerPlayer player, RemoveWaystoneMessage message) {
      WaystoneProxy waystone = new WaystoneProxy(player.server, message.waystoneUid);
      PlayerWaystoneManager.deactivateWaystone(player, waystone);
      if (player.getAbilities().instabuild) {
         if (WaystoneTypes.isSharestone(waystone.getWaystoneType())) {
            WaystoneManagerImpl.get(player.server).removeWaystone(waystone);
         } else if (waystone.getVisibility() == WaystoneVisibility.GLOBAL) {
            Waystone backingWaystone = waystone.getBackingWaystone();
            if (backingWaystone instanceof MutableWaystone mutableWaystone) {
               mutableWaystone.setVisibility(WaystoneVisibility.ACTIVATION);
               ServerLevel targetWorld = Objects.requireNonNull(player.level().getServer()).getLevel(backingWaystone.getDimension());
               BlockPos pos = backingWaystone.getPos();
               BlockState state = targetWorld != null ? targetWorld.getBlockState(pos) : null;
               if (targetWorld == null || !(state.getBlock() instanceof WaystoneBlock)) {
                  WaystoneManagerImpl.get(player.server).removeWaystone(backingWaystone);
                  PlayerWaystoneManager.removeKnownWaystone(player.server, backingWaystone);
                  WaystoneSyncManager.sendWaystoneRemovalToAll(player.server, backingWaystone, true);
               }
            }
         }
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
