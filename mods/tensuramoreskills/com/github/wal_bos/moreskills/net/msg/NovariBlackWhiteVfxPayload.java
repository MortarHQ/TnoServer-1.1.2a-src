package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.NovariBlackWhiteVfxClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record NovariBlackWhiteVfxPayload(int bossId, int targetId, int visualId, double x, double y, double z, float radius, int duration, int seed)
   implements CustomPacketPayload {
   public static final Type<NovariBlackWhiteVfxPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "novari_black_white_vfx"));
   public static final StreamCodec<FriendlyByteBuf, NovariBlackWhiteVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      NovariBlackWhiteVfxPayload::encode, NovariBlackWhiteVfxPayload::new
   );

   public NovariBlackWhiteVfxPayload(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readInt(), buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readInt(), buf.readInt());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.bossId);
      buf.writeInt(this.targetId);
      buf.writeInt(this.visualId);
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeFloat(this.radius);
      buf.writeInt(this.duration);
      buf.writeInt(this.seed);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(
            () -> NovariBlackWhiteVfxClient.add(this.bossId, this.targetId, this.visualId, this.x, this.y, this.z, this.radius, this.duration, this.seed)
         );
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
