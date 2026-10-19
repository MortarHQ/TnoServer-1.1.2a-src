package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.AxisJudgmentClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record AxisJudgmentPayload(int bossId, int targetId, double x, double y, double z, float radius, int duration, int seed) implements CustomPacketPayload {
   public static final Type<AxisJudgmentPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "axis_judgment"));
   public static final StreamCodec<FriendlyByteBuf, AxisJudgmentPayload> STREAM_CODEC = CustomPacketPayload.codec(
      AxisJudgmentPayload::encode, AxisJudgmentPayload::new
   );

   public AxisJudgmentPayload(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readInt(), buf.readInt());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.bossId);
      buf.writeInt(this.targetId);
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeFloat(this.radius);
      buf.writeInt(this.duration);
      buf.writeInt(this.seed);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> AxisJudgmentClient.add(this.bossId, this.targetId, this.x, this.y, this.z, this.radius, this.duration, this.seed));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
