package fuzs.puzzleslib.impl.attachment;

import java.util.Optional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record ClientboundEntityDataAttachmentMessage<V>(Type<ClientboundEntityDataAttachmentMessage<V>> messageType, int entityId, Optional<V> value)
   implements CustomPacketPayload {
   public static <V> StreamCodec<? super RegistryFriendlyByteBuf, ClientboundEntityDataAttachmentMessage<V>> streamCodec(
      Type<ClientboundEntityDataAttachmentMessage<V>> type, StreamCodec<? super RegistryFriendlyByteBuf, V> valueStreamCodec
   ) {
      return StreamCodec.composite(
         ByteBufCodecs.VAR_INT,
         ClientboundEntityDataAttachmentMessage::entityId,
         ByteBufCodecs.optional(valueStreamCodec),
         ClientboundEntityDataAttachmentMessage::value,
         (entityId, value) -> new ClientboundEntityDataAttachmentMessage(type, entityId, value)
      );
   }

   public Type<ClientboundEntityDataAttachmentMessage<V>> type() {
      return this.messageType;
   }
}
