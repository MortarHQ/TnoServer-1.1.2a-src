package com.tensura_tno.network;

import com.tensura_tno.TensuraTNOMod;
import com.tensura_tno.race.SlimeHumanFormState;
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

public final class SlimeHumanFormPackets {
   public static volatile boolean clientUnlocked = false;
   public static volatile boolean clientChecked = true;

   private SlimeHumanFormPackets() {
   }

   public static void registerC2S() {
      NetworkManager.registerReceiver(
         Side.C2S,
         SlimeHumanFormPackets.SetHumanFormPayload.TYPE,
         SlimeHumanFormPackets.SetHumanFormPayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> handleSetHumanForm(payload, context))
      );
   }

   public static void registerS2C() {
      NetworkManager.registerReceiver(
         Side.S2C,
         SlimeHumanFormPackets.SyncHumanFormPayload.TYPE,
         SlimeHumanFormPackets.SyncHumanFormPayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> {
            clientUnlocked = payload.unlocked();
            clientChecked = payload.checked();
         })
      );
   }

   public static void syncToClient(ServerPlayer player) {
      if (player != null) {
         try {
            NetworkManager.sendToPlayer(
               player, new SlimeHumanFormPackets.SyncHumanFormPayload(SlimeHumanFormState.isUnlocked(player), SlimeHumanFormState.isChecked(player))
            );
         } catch (Throwable t) {
            TensuraTNOMod.LOGGER.warn("[TensuraTNO] Failed to sync slime human-form state to {}: {}", player.getGameProfile().getName(), t.toString());
         }
      }
   }

   private static void handleSetHumanForm(SlimeHumanFormPackets.SetHumanFormPayload payload, PacketContext context) {
      if (context.getPlayer() instanceof ServerPlayer player) {
         SlimeHumanFormState.setChecked(player, payload.checked());
      }
   }

   public record SetHumanFormPayload(boolean checked) implements CustomPacketPayload {
      public static final Type<SlimeHumanFormPackets.SetHumanFormPayload> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "set_slime_human_form")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, SlimeHumanFormPackets.SetHumanFormPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.BOOL, SlimeHumanFormPackets.SetHumanFormPayload::checked, SlimeHumanFormPackets.SetHumanFormPayload::new
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }

   public record SyncHumanFormPayload(boolean unlocked, boolean checked) implements CustomPacketPayload {
      public static final Type<SlimeHumanFormPackets.SyncHumanFormPayload> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "sync_slime_human_form")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, SlimeHumanFormPackets.SyncHumanFormPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.BOOL,
         SlimeHumanFormPackets.SyncHumanFormPayload::unlocked,
         ByteBufCodecs.BOOL,
         SlimeHumanFormPackets.SyncHumanFormPayload::checked,
         SlimeHumanFormPackets.SyncHumanFormPayload::new
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }
}
