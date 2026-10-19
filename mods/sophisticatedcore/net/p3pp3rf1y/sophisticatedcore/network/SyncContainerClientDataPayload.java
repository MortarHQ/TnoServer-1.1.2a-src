package net.p3pp3rf1y.sophisticatedcore.network;

import io.netty.buffer.ByteBuf;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.common.gui.ISyncedContainer;
import net.p3pp3rf1y.sophisticatedcore.util.StreamCodecHelper;

public record SyncContainerClientDataPayload(@Nullable CompoundTag data) implements CustomPacketPayload {
   public static final Type<SyncContainerClientDataPayload> TYPE = new Type(SophisticatedCore.getRL("sync_container_client_data"));
   public static final StreamCodec<ByteBuf, SyncContainerClientDataPayload> STREAM_CODEC = StreamCodec.composite(
      StreamCodecHelper.ofNullable(ByteBufCodecs.COMPOUND_TAG), SyncContainerClientDataPayload::data, SyncContainerClientDataPayload::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handlePayload(SyncContainerClientDataPayload payload, IPayloadContext context) {
      if (payload.data != null) {
         if (context.player().containerMenu instanceof ISyncedContainer container) {
            container.handlePacket(payload.data);
         }
      }
   }
}
