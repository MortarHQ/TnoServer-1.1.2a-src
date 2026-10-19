package twilightforest.network;

import java.util.UUID;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.TFSounds;
import twilightforest.init.custom.TravellersModifiersManager;

public record GogglesZoomPacket(boolean isUsingZoom, UUID playerUUID) implements CustomPacketPayload {
   public static final Type<GogglesZoomPacket> TYPE = new Type(TwilightForestMod.prefix("goggles_zoom_packet"));
   public static final StreamCodec<RegistryFriendlyByteBuf, GogglesZoomPacket> STREAM_CODEC = CustomPacketPayload.codec(
      GogglesZoomPacket::write, GogglesZoomPacket::new
   );

   public GogglesZoomPacket(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
      this(registryFriendlyByteBuf.readBoolean(), registryFriendlyByteBuf.readUUID());
   }

   private void write(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
      registryFriendlyByteBuf.writeBoolean(this.isUsingZoom);
      registryFriendlyByteBuf.writeUUID(this.playerUUID);
   }

   public static void handle(GogglesZoomPacket packet, IPayloadContext ctx) {
      ctx.enqueueWork(
         () -> {
            Player player = ctx.player().level().getPlayerByUUID(packet.playerUUID);
            if (player != null) {
               if (player.level().isClientSide()) {
                  player.setData(TFDataAttachments.IS_USING_GOGGLES_ZOOM_MODIFIER, packet.isUsingZoom);
               } else {
                  boolean canChangeZoomState = TravellersModifiersManager.isModifierActive(player, TravellersModifiersManager.ZOOM_ABILITY);
                  if (canChangeZoomState) {
                     player.setData(TFDataAttachments.IS_USING_GOGGLES_ZOOM_MODIFIER, packet.isUsingZoom);
                     player.playSound(packet.isUsingZoom ? (SoundEvent)TFSounds.GOGGLES_ZOOM_IN.get() : (SoundEvent)TFSounds.GOGGLES_ZOOM_OUT.get());
                     PacketDistributor.sendToPlayersTrackingEntity(
                        player, new GogglesZoomPacket(packet.isUsingZoom, player.getUUID()), new CustomPacketPayload[0]
                     );
                  }
               }
            }
         }
      );
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
