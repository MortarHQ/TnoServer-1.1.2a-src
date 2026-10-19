package com.tno_tensura_extras.network;

import com.tno_tensura_extras.util.LooterManager;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import io.github.manasmods.manascore.network.api.util.NetworkUtils;
import java.lang.reflect.Constructor;
import java.util.UUID;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final class LooterPackets {
   private LooterPackets() {
   }

   public static void registerC2S() {
      NetworkUtils.registerC2SPayload(
         LooterPackets.SelectShadowPayload.TYPE,
         LooterPackets.SelectShadowPayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> handleSelect(payload, context))
      );
   }

   public static void registerS2C() {
      NetworkUtils.registerS2CPayload(
         LooterPackets.OpenScreenPayload.TYPE,
         LooterPackets.OpenScreenPayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> openClientScreen(payload))
      );
      NetworkUtils.registerS2CPayload(
         LooterPackets.ShadowStatePayload.TYPE,
         LooterPackets.ShadowStatePayload.STREAM_CODEC,
         (payload, context) -> context.queue(() -> applyClientShadowState(payload))
      );
   }

   public static void sendShadowState(Iterable<ServerPlayer> players, UUID entityId, boolean shadow) {
      if (entityId != null) {
         NetworkManager.sendToPlayers(players, new LooterPackets.ShadowStatePayload(entityId.toString(), shadow));
      }
   }

   private static void handleSelect(LooterPackets.SelectShadowPayload payload, PacketContext context) {
      if (context.getPlayer() instanceof ServerPlayer player) {
         try {
            LooterManager.beginSummon(player, UUID.fromString(payload.entryId()));
         } catch (IllegalArgumentException var4) {
         }
      }
   }

   private static void openClientScreen(LooterPackets.OpenScreenPayload payload) {
      try {
         Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft");
         Object minecraft = minecraftClass.getMethod("getInstance").invoke(null);
         Class<?> screenClass = Class.forName("com.tno_tensura_extras.client.LooterScreen");
         Constructor<?> constructor = screenClass.getConstructor(String.class);
         Object screen = constructor.newInstance(payload.entries());
         minecraftClass.getMethod("setScreen", Class.forName("net.minecraft.client.gui.screens.Screen")).invoke(minecraft, screen);
      } catch (ReflectiveOperationException var6) {
      }
   }

   private static void applyClientShadowState(LooterPackets.ShadowStatePayload payload) {
      try {
         Class<?> clientEvents = Class.forName("com.tno_tensura_extras.client.LooterClientEvents");
         clientEvents.getMethod("setKnownShadow", UUID.class, boolean.class).invoke(null, UUID.fromString(payload.entityId()), payload.shadow());
      } catch (ReflectiveOperationException | IllegalArgumentException var2) {
      }
   }

   public record OpenScreenPayload(String entries) implements CustomPacketPayload {
      public static final Type<LooterPackets.OpenScreenPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "looter_open"));
      public static final StreamCodec<RegistryFriendlyByteBuf, LooterPackets.OpenScreenPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.STRING_UTF8, LooterPackets.OpenScreenPayload::entries, LooterPackets.OpenScreenPayload::new
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }

   public record SelectShadowPayload(String entryId) implements CustomPacketPayload {
      public static final Type<LooterPackets.SelectShadowPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "looter_select"));
      public static final StreamCodec<RegistryFriendlyByteBuf, LooterPackets.SelectShadowPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.STRING_UTF8, LooterPackets.SelectShadowPayload::entryId, LooterPackets.SelectShadowPayload::new
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }

   public record ShadowStatePayload(String entityId, boolean shadow) implements CustomPacketPayload {
      public static final Type<LooterPackets.ShadowStatePayload> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "looter_shadow_state")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, LooterPackets.ShadowStatePayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.STRING_UTF8,
         LooterPackets.ShadowStatePayload::entityId,
         ByteBufCodecs.BOOL,
         LooterPackets.ShadowStatePayload::shadow,
         LooterPackets.ShadowStatePayload::new
      );

      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }
}
