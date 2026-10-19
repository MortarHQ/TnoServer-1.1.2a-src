package net.blay09.mods.waystones.network.message;

import java.util.UUID;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.event.WaystoneRemoveReceivedEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class WaystoneRemovedMessage implements CustomPacketPayload {
   public static final Type<WaystoneRemovedMessage> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("waystones", "waystone_removed"));
   private final ResourceLocation waystoneType;
   private final UUID waystoneId;
   private final boolean wasDestroyed;

   public WaystoneRemovedMessage(ResourceLocation waystoneType, UUID waystoneId, boolean wasDestroyed) {
      this.waystoneType = waystoneType;
      this.waystoneId = waystoneId;
      this.wasDestroyed = wasDestroyed;
   }

   public static void encode(FriendlyByteBuf buf, WaystoneRemovedMessage message) {
      buf.writeResourceLocation(message.waystoneType);
      buf.writeUUID(message.waystoneId);
      buf.writeBoolean(message.wasDestroyed);
   }

   public static WaystoneRemovedMessage decode(FriendlyByteBuf buf) {
      return new WaystoneRemovedMessage(buf.readResourceLocation(), buf.readUUID(), buf.readBoolean());
   }

   public static void handle(Player player, WaystoneRemovedMessage message) {
      Balm.getEvents().fireEvent(new WaystoneRemoveReceivedEvent(message.waystoneType, message.waystoneId, message.wasDestroyed));
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
