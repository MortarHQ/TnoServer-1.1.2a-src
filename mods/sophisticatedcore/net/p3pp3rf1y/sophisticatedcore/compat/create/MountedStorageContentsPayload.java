package net.p3pp3rf1y.sophisticatedcore.compat.create;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;

public record MountedStorageContentsPayload(UUID storageUuid, CompoundTag contents) implements CustomPacketPayload {
   public static final Type<MountedStorageContentsPayload> TYPE = new Type(SophisticatedCore.getRL("mounted_storage_contents"));
   public static final StreamCodec<ByteBuf, MountedStorageContentsPayload> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC,
      MountedStorageContentsPayload::storageUuid,
      ByteBufCodecs.COMPOUND_TAG,
      MountedStorageContentsPayload::contents,
      MountedStorageContentsPayload::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handlePayload(MountedStorageContentsPayload payload, IPayloadContext context) {
      MountedStorageData.get(payload.storageUuid).setContents(payload.storageUuid, payload.contents);
   }
}
