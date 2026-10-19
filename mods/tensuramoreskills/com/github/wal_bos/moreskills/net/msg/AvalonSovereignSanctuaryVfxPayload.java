package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.avalon.AvalonSovereignSanctuaryVfxClient;
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

public record AvalonSovereignSanctuaryVfxPayload(ArthurAvalonVfxData data) implements CustomPacketPayload {
   public static final Type<AvalonSovereignSanctuaryVfxPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_sovereign_sanctuary_vfx")
   );
   public static final StreamCodec<FriendlyByteBuf, AvalonSovereignSanctuaryVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      AvalonSovereignSanctuaryVfxPayload::encode, AvalonSovereignSanctuaryVfxPayload::new
   );

   public AvalonSovereignSanctuaryVfxPayload(FriendlyByteBuf buffer) {
      this(new ArthurAvalonVfxData(buffer));
   }

   public void encode(FriendlyByteBuf buffer) {
      this.data.encode(buffer);
   }

   public static void send(ServerLevel level, LivingEntity source, LivingEntity target, Vec3 start, Vec3 end, float radius, int durationTicks, int variant) {
      ArthurAvalonVfxData data = ArthurAvalonVfxData.create(level, source, target, start, end, radius, durationTicks, variant);
      ArthurAvalonVfxPacketSender.send(level, new AvalonSovereignSanctuaryVfxPayload(data), start, end, radius);
   }

   public static void handle(AvalonSovereignSanctuaryVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> AvalonSovereignSanctuaryVfxClient.spawn(payload.data()));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
