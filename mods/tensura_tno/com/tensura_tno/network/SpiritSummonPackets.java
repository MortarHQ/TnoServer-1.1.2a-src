package com.tensura_tno.network;

import com.tensura_tno.ability.skill.SpiritSummonSkill;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.networking.NetworkManager.Side;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final class SpiritSummonPackets {
   public static void registerC2S() {
      NetworkManager.registerReceiver(
         Side.C2S,
         SpiritSummonPackets.SelectEntityPayload.TYPE,
         SpiritSummonPackets.SelectEntityPayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> handleSelect(payload, context))
      );
      NetworkManager.registerReceiver(
         Side.C2S,
         SpiritSummonPackets.ReleaseEntityPayload.TYPE,
         SpiritSummonPackets.ReleaseEntityPayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> handleRelease(payload, context))
      );
   }

   public static void registerS2C() {
      NetworkManager.registerReceiver(
         Side.S2C,
         SpiritSummonPackets.OpenScreenPayload.TYPE,
         SpiritSummonPackets.OpenScreenPayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> openClientScreen(payload))
      );
   }

   private static void handleSelect(SpiritSummonPackets.SelectEntityPayload payload, PacketContext context) {
      if (context.getPlayer() instanceof ServerPlayer player) {
         SpiritSummonSkill.summonFromPocket(player, payload.entityId());
      }
   }

   private static void handleRelease(SpiritSummonPackets.ReleaseEntityPayload payload, PacketContext context) {
      if (context.getPlayer() instanceof ServerPlayer player) {
         String var5 = payload.entityId();
         SpiritSummonSkill.SpiritSummonPockets.removeAbsorbedEntity(player, var5);
         String pending = player.getPersistentData().getString("tensura_tno_pending_summon");
         if (var5.equals(pending)) {
            player.getPersistentData().remove("tensura_tno_pending_summon");
         }
      }
   }

   private static void openClientScreen(SpiritSummonPackets.OpenScreenPayload payload) {
      try {
         Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft");
         Object minecraft = minecraftClass.getMethod("getInstance").invoke(null);
         Class<?> screenClass = Class.forName("com.tensura_tno.client.screen.SpiritSummonScreen");
         Object screen = screenClass.getConstructor(String.class).newInstance(payload.absorbedCsv());
         minecraftClass.getMethod("setScreen", Class.forName("net.minecraft.client.gui.screens.Screen")).invoke(minecraft, screen);
      } catch (Exception var5) {
      }
   }

   private SpiritSummonPackets() {
   }

   public record OpenScreenPayload(String absorbedCsv) implements CustomPacketPayload {
      public static final Type<SpiritSummonPackets.OpenScreenPayload> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "spirit_summon_open")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, SpiritSummonPackets.OpenScreenPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.STRING_UTF8, SpiritSummonPackets.OpenScreenPayload::absorbedCsv, SpiritSummonPackets.OpenScreenPayload::new
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }

   public record ReleaseEntityPayload(String entityId) implements CustomPacketPayload {
      public static final Type<SpiritSummonPackets.ReleaseEntityPayload> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "spirit_summon_release")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, SpiritSummonPackets.ReleaseEntityPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.STRING_UTF8, SpiritSummonPackets.ReleaseEntityPayload::entityId, SpiritSummonPackets.ReleaseEntityPayload::new
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }

   public record SelectEntityPayload(String entityId) implements CustomPacketPayload {
      public static final Type<SpiritSummonPackets.SelectEntityPayload> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "spirit_summon_select")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, SpiritSummonPackets.SelectEntityPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.STRING_UTF8, SpiritSummonPackets.SelectEntityPayload::entityId, SpiritSummonPackets.SelectEntityPayload::new
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }
}
