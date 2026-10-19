package com.tensura_tno.network;

import com.tensura_tno.TensuraTNOCompatConfig;
import com.tensura_tno.TensuraTNOMod;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.networking.NetworkManager.Side;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public final class RimuruModePackets {
   public static final String NBT_PENDING_RIMURU_MODE = "tensura_tno:pending_rimuru_mode";
   public static volatile boolean clientNextMenuFromCharacterReset = false;
   public static volatile boolean clientNextMenuFromCharacterResetForRules = false;

   public static boolean isClientCheckboxEnabled() {
      return TensuraTNOCompatConfig.isRimuruModeCheckboxEnabled();
   }

   private RimuruModePackets() {
   }

   public static void registerC2S() {
      NetworkManager.registerReceiver(
         Side.C2S,
         RimuruModePackets.SetRimuruPendingPayload.TYPE,
         RimuruModePackets.SetRimuruPendingPayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> handleSetPending(payload, context))
      );
   }

   public static void registerS2C() {
      NetworkManager.registerReceiver(
         Side.S2C,
         RimuruModePackets.CharacterResetMenuPayload.TYPE,
         RimuruModePackets.CharacterResetMenuPayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> {
            clientNextMenuFromCharacterReset = payload.fromCharacterReset();
            clientNextMenuFromCharacterResetForRules = payload.fromCharacterReset();
         })
      );
   }

   public static void notifyCharacterReset(ServerPlayer player) {
      try {
         NetworkManager.sendToPlayer(player, new RimuruModePackets.CharacterResetMenuPayload(true));
      } catch (Throwable t) {
         TensuraTNOMod.LOGGER.warn("[TensuraTNO] Failed to send character-reset marker to {}: {}", player.getGameProfile().getName(), t.toString());
      }
   }

   private static void handleSetPending(RimuruModePackets.SetRimuruPendingPayload payload, PacketContext context) {
      if (context.getPlayer() instanceof ServerPlayer player) {
         MinecraftServer srv = player.getServer();
         boolean dedicated = srv != null && srv.isDedicatedServer();
         if (dedicated && !TensuraTNOCompatConfig.isRimuruModeCheckboxEnabled()) {
            player.getPersistentData().remove("tensura_tno:pending_rimuru_mode");
         } else {
            if (payload.checked()) {
               player.getPersistentData().putBoolean("tensura_tno:pending_rimuru_mode", true);
            } else {
               player.getPersistentData().remove("tensura_tno:pending_rimuru_mode");
            }
         }
      }
   }

   public record CharacterResetMenuPayload(boolean fromCharacterReset) implements CustomPacketPayload {
      public static final Type<RimuruModePackets.CharacterResetMenuPayload> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "rimuru_char_reset_marker")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, RimuruModePackets.CharacterResetMenuPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.BOOL, RimuruModePackets.CharacterResetMenuPayload::fromCharacterReset, RimuruModePackets.CharacterResetMenuPayload::new
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }

   public record SetRimuruPendingPayload(boolean checked) implements CustomPacketPayload {
      public static final Type<RimuruModePackets.SetRimuruPendingPayload> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "set_rimuru_pending")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, RimuruModePackets.SetRimuruPendingPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.BOOL, RimuruModePackets.SetRimuruPendingPayload::checked, RimuruModePackets.SetRimuruPendingPayload::new
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }
}
