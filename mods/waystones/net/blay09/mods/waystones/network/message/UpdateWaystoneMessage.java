package net.blay09.mods.waystones.network.message;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.event.WaystoneUpdateReceivedEvent;
import net.blay09.mods.waystones.core.WaystoneImpl;
import net.blay09.mods.waystones.core.WaystoneManagerImpl;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class UpdateWaystoneMessage implements CustomPacketPayload {
   public static final Type<UpdateWaystoneMessage> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("waystones", "update_waystone"));
   private final Waystone waystone;

   public UpdateWaystoneMessage(Waystone waystone) {
      this.waystone = waystone;
   }

   public static void encode(RegistryFriendlyByteBuf buf, UpdateWaystoneMessage message) {
      WaystoneImpl.write(buf, message.waystone);
   }

   public static UpdateWaystoneMessage decode(RegistryFriendlyByteBuf buf) {
      return new UpdateWaystoneMessage(WaystoneImpl.read(buf));
   }

   public static void handle(Player player, UpdateWaystoneMessage message) {
      WaystoneManagerImpl.get(player.getServer()).updateWaystone(message.waystone);
      Balm.getEvents().fireEvent(new WaystoneUpdateReceivedEvent(message.waystone));
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
