package com.foxablazeultimate.network;

import com.foxablazeultimate.client.FoxAblazeUltimateClientAccess;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record OpenBeelzebubStoragePayload(int containerId, int size, int stackSize, int page, int entityId, ResourceLocation skill)
   implements CustomPacketPayload {
   public static final Type<OpenBeelzebubStoragePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "open_beelzebub_storage"));
   public static final StreamCodec<FriendlyByteBuf, OpenBeelzebubStoragePayload> STREAM_CODEC = CustomPacketPayload.codec(
      OpenBeelzebubStoragePayload::encode, OpenBeelzebubStoragePayload::new
   );

   public OpenBeelzebubStoragePayload(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readResourceLocation());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.containerId);
      buf.writeInt(this.size);
      buf.writeInt(this.stackSize);
      buf.writeInt(this.page);
      buf.writeInt(this.entityId);
      buf.writeResourceLocation(this.skill);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> FoxAblazeUltimateClientAccess.handleOpenBeelzebubStorage(this));
      }
   }

   public Type<OpenBeelzebubStoragePayload> type() {
      return TYPE;
   }
}
