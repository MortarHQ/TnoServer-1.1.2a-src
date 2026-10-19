package com.tensura_tno.network;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.Side;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public final class MagicOreSensePackets {
   public static void registerS2C() {
      NetworkManager.registerReceiver(
         Side.S2C,
         MagicOreSensePackets.StartScanPayload.TYPE,
         MagicOreSensePackets.StartScanPayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> startClientScan(payload))
      );
   }

   private static void startClientScan(MagicOreSensePackets.StartScanPayload payload) {
      try {
         Class<?> xrayClass = Class.forName("com.tensura_tno.client.MagicOreSenseXray");
         xrayClass.getMethod("startScan", int.class, int.class).invoke(null, payload.radius(), payload.durationTicks());
      } catch (ReflectiveOperationException var2) {
      }
   }

   private MagicOreSensePackets() {
   }

   public record StartScanPayload(int radius, int durationTicks) implements CustomPacketPayload {
      public static final Type<MagicOreSensePackets.StartScanPayload> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "magic_ore_sense_start")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, MagicOreSensePackets.StartScanPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.VAR_INT,
         MagicOreSensePackets.StartScanPayload::radius,
         ByteBufCodecs.VAR_INT,
         MagicOreSensePackets.StartScanPayload::durationTicks,
         MagicOreSensePackets.StartScanPayload::new
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }
}
