package cn.leolezury.eternalstarlight.common.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.world.entity.player.Player;

public class ESPackets {
   public static final ESPackets.PacketInfo<SyncAttachmentsPacket> SYNC_ATTACHMENTS = new ESPackets.PacketInfo<>(
      SyncAttachmentsPacket.TYPE, SyncAttachmentsPacket.STREAM_CODEC, SyncAttachmentsPacket::handle
   );
   public static final ESPackets.PacketInfo<SimpleActionPacket> NO_PARAMETERS = new ESPackets.PacketInfo<>(
      SimpleActionPacket.TYPE, SimpleActionPacket.STREAM_CODEC, SimpleActionPacket::handle
   );
   public static final ESPackets.PacketInfo<ParticlePacket> PARTICLE = new ESPackets.PacketInfo<>(
      ParticlePacket.TYPE, ParticlePacket.STREAM_CODEC, ParticlePacket::handle
   );
   public static final ESPackets.PacketInfo<VfxPacket> VFX = new ESPackets.PacketInfo<>(VfxPacket.TYPE, VfxPacket.STREAM_CODEC, VfxPacket::handle);
   public static final ESPackets.PacketInfo<UpdateWeatherPacket> UPDATE_WEATHER = new ESPackets.PacketInfo<>(
      UpdateWeatherPacket.TYPE, UpdateWeatherPacket.STREAM_CODEC, UpdateWeatherPacket::handle
   );
   public static final ESPackets.PacketInfo<OpenCrestGuiPacket> OPEN_CREST_GUI = new ESPackets.PacketInfo<>(
      OpenCrestGuiPacket.TYPE, OpenCrestGuiPacket.STREAM_CODEC, OpenCrestGuiPacket::handle
   );
   public static final ESPackets.PacketInfo<UpdateCrestsPacket> UPDATE_CRESTS = new ESPackets.PacketInfo<>(
      UpdateCrestsPacket.TYPE, UpdateCrestsPacket.STREAM_CODEC, UpdateCrestsPacket::handle
   );
   public static final ESPackets.PacketInfo<UpdateCameraPacket> UPDATE_CAMERA = new ESPackets.PacketInfo<>(
      UpdateCameraPacket.TYPE, UpdateCameraPacket.STREAM_CODEC, UpdateCameraPacket::handle
   );
   public static final ESPackets.PacketInfo<ClientMountPacket> CLIENT_MOUNT = new ESPackets.PacketInfo<>(
      ClientMountPacket.TYPE, ClientMountPacket.STREAM_CODEC, ClientMountPacket::handle
   );
   public static final ESPackets.PacketInfo<ClientDismountPacket> CLIENT_DISMOUNT = new ESPackets.PacketInfo<>(
      ClientDismountPacket.TYPE, ClientDismountPacket.STREAM_CODEC, ClientDismountPacket::handle
   );
   public static final ESPackets.PacketInfo<OpenGatekeeperGuiPacket> OPEN_GATEKEEPER_GUI = new ESPackets.PacketInfo<>(
      OpenGatekeeperGuiPacket.TYPE, OpenGatekeeperGuiPacket.STREAM_CODEC, OpenGatekeeperGuiPacket::handle
   );
   public static final ESPackets.PacketInfo<CloseGatekeeperGuiPacket> CLOSE_GATEKEEPER_GUI = new ESPackets.PacketInfo<>(
      CloseGatekeeperGuiPacket.TYPE, CloseGatekeeperGuiPacket.STREAM_CODEC, CloseGatekeeperGuiPacket::handle
   );
   public static final ESPackets.PacketInfo<UpdateBookPacket> UPDATE_BOOK = new ESPackets.PacketInfo<>(
      UpdateBookPacket.TYPE, UpdateBookPacket.STREAM_CODEC, UpdateBookPacket::handle
   );
   public static final ESPackets.PacketInfo<UpdateBookProgressionPacket> UPDATE_BOOK_PROGRESSION = new ESPackets.PacketInfo<>(
      UpdateBookProgressionPacket.TYPE, UpdateBookProgressionPacket.STREAM_CODEC, UpdateBookProgressionPacket::handle
   );
   public static final ESPackets.PacketInfo<OpenBookPacket> OPEN_BOOK = new ESPackets.PacketInfo<>(
      OpenBookPacket.TYPE, OpenBookPacket.STREAM_CODEC, OpenBookPacket::handle
   );
   public static final ESPackets.PacketInfo<TriggerEntityEventPacket> TRIGGER_ENTITY_EVENT = new ESPackets.PacketInfo<>(
      TriggerEntityEventPacket.TYPE, TriggerEntityEventPacket.STREAM_CODEC, TriggerEntityEventPacket::handle
   );

   public interface Handler<T extends CustomPacketPayload> {
      void handle(T var1, Player var2);
   }

   public record PacketInfo<T extends CustomPacketPayload>(Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, ESPackets.Handler<T> handler) {
   }
}
