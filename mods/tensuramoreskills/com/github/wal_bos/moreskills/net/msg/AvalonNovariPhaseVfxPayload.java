package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.AvalonNovariPhaseVfxClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record AvalonNovariPhaseVfxPayload(int entityId, int nextPhase, double x, double y, double z, int duration, int seed) implements CustomPacketPayload {
   public static final Type<AvalonNovariPhaseVfxPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_phase_vfx"));
   public static final StreamCodec<FriendlyByteBuf, AvalonNovariPhaseVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      AvalonNovariPhaseVfxPayload::encode, AvalonNovariPhaseVfxPayload::new
   );

   public AvalonNovariPhaseVfxPayload(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readInt());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.writeInt(this.nextPhase);
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeInt(this.duration);
      buf.writeInt(this.seed);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> AvalonNovariPhaseVfxClient.add(this.entityId, this.nextPhase, this.x, this.y, this.z, this.duration, this.seed));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
