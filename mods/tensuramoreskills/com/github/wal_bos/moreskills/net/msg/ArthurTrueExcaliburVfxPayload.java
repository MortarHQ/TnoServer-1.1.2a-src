package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.arthur.ArthurTrueExcaliburVfxClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record ArthurTrueExcaliburVfxPayload(ArthurAvalonVfxData data) implements CustomPacketPayload {
   public static final Type<ArthurTrueExcaliburVfxPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "arthur_true_excalibur_vfx")
   );
   public static final StreamCodec<FriendlyByteBuf, ArthurTrueExcaliburVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      ArthurTrueExcaliburVfxPayload::encode, ArthurTrueExcaliburVfxPayload::new
   );

   public ArthurTrueExcaliburVfxPayload(FriendlyByteBuf buffer) {
      this(new ArthurAvalonVfxData(buffer));
   }

   public void encode(FriendlyByteBuf buffer) {
      this.data.encode(buffer);
   }

   public static void send(ServerLevel level, LivingEntity source, LivingEntity target, Vec3 start, Vec3 end, float radius, int durationTicks, int variant) {
      ArthurAvalonVfxData data = ArthurAvalonVfxData.create(level, source, target, start, end, radius, durationTicks, variant);
      ArthurAvalonVfxPacketSender.send(level, new ArthurTrueExcaliburVfxPayload(data), start, end, radius);
   }

   public static void handle(ArthurTrueExcaliburVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> ArthurTrueExcaliburVfxClient.spawn(payload.data()));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
