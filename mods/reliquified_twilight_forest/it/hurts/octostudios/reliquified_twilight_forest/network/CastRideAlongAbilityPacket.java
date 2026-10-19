package it.hurts.octostudios.reliquified_twilight_forest.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CastRideAlongAbilityPacket(int entityID, boolean isMounting) implements CustomPacketPayload {
   public static final Type<CastRideAlongAbilityPacket> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("reliquified_twilight_forest", "cast_ride_along_ability")
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, CastRideAlongAbilityPacket> STREAM_CODEC = CustomPacketPayload.codec(
      CastRideAlongAbilityPacket::write, CastRideAlongAbilityPacket::new
   );

   public CastRideAlongAbilityPacket(RegistryFriendlyByteBuf buf) {
      this(buf.readInt(), buf.readBoolean());
   }

   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeInt(this.entityID);
      buf.writeBoolean(this.isMounting);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(CastRideAlongAbilityPacket packet, IPayloadContext ctx) {
      if (!ctx.flow().isClientbound()) {
         ctx.enqueueWork(
            () -> {
               Entity vehicle = ctx.player();
               Entity entity = vehicle.level().getEntity(packet.entityID);
               Entity passenger = vehicle.getFirstPassenger();
               if (!packet.isMounting() && passenger != null) {
                  passenger.stopRiding();
                  PacketDistributor.sendToPlayersTrackingEntityAndSelf(passenger, new EntityStopRidingPacket(passenger.getId()), new CustomPacketPayload[0]);
               }

               if (entity != null) {
                  if (passenger != null) {
                     passenger.setPos(entity.position());
                     passenger.setOldPosAndRot();
                  }

                  entity.getPersistentData().putBoolean("reliquified_twilight_forest:on_antlers", true);
                  entity.startRiding(vehicle, true);
                  PacketDistributor.sendToPlayersTrackingEntityAndSelf(
                     vehicle, new EntityStartRidingPacket(packet.entityID, vehicle.getId()), new CustomPacketPayload[0]
                  );
               }
            }
         );
      }
   }
}
