package it.hurts.octostudios.reliquified_twilight_forest.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record EntityStopRidingPacket(int entityID) implements CustomPacketPayload {
   public static final Type<EntityStopRidingPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("reliquified_twilight_forest", "entity_stop_riding"));
   public static final StreamCodec<RegistryFriendlyByteBuf, EntityStopRidingPacket> STREAM_CODEC = CustomPacketPayload.codec(
      EntityStopRidingPacket::write, EntityStopRidingPacket::new
   );

   public EntityStopRidingPacket(RegistryFriendlyByteBuf buf) {
      this(buf.readInt());
   }

   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeInt(this.entityID);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(EntityStopRidingPacket packet, IPayloadContext ctx) {
      if (!ctx.flow().isServerbound()) {
         ctx.enqueueWork(() -> {
            Entity entity = ctx.player().level().getEntity(packet.entityID);
            if (entity != null) {
               entity.stopRiding();
            }
         });
      }
   }
}
