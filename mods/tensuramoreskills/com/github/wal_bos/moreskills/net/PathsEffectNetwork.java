package com.github.wal_bos.moreskills.net;

import com.github.wal_bos.moreskills.client.fx.PathsClientState;
import java.util.Locale;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;

public final class PathsEffectNetwork {
   private PathsEffectNetwork() {
   }

   public static void register(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("1");
      registrar.playToClient(
         PathsEffectNetwork.PathsEffectPayload.TYPE, PathsEffectNetwork.PathsEffectPayload.STREAM_CODEC, PathsEffectNetwork.PathsEffectPayload::handle
      );
   }

   public static void sendFlash(ServerPlayer player, PathsEffectNetwork.FlashMode mode, int durationTicks, float alpha) {
      PacketDistributor.sendToPlayer(player, PathsEffectNetwork.PathsEffectPayload.flash(mode, durationTicks, alpha), new CustomPacketPayload[0]);
   }

   public static void sendShake(ServerPlayer player, int durationTicks, float strength, float impactStrength) {
      PacketDistributor.sendToPlayer(player, PathsEffectNetwork.PathsEffectPayload.shake(durationTicks, strength, impactStrength), new CustomPacketPayload[0]);
   }

   private static final class ClientOnly {
      private static void handle(PathsEffectNetwork.PathsEffectPayload payload) {
         if (payload.effectKind() == PathsEffectNetwork.EffectKind.FLASH) {
            PathsClientState.FlashMode clientMode = switch (payload.flashMode()) {
               case NONE -> PathsClientState.FlashMode.NONE;
               case BLACK -> PathsClientState.FlashMode.BLACK;
               default -> PathsClientState.FlashMode.WHITE;
            };
            PathsClientState.triggerFlash(clientMode, payload.durationTicks(), payload.alpha());
         } else if (payload.effectKind() == PathsEffectNetwork.EffectKind.SHAKE) {
            PathsClientState.triggerManualShake(payload.durationTicks(), payload.strength(), payload.impactStrength());
         }
      }
   }

   public enum EffectKind {
      FLASH,
      SHAKE;

      public static PathsEffectNetwork.EffectKind byId(int id) {
         PathsEffectNetwork.EffectKind[] values = values();
         return id >= 0 && id < values.length ? values[id] : FLASH;
      }
   }

   public enum FlashMode {
      NONE,
      WHITE,
      BLACK;

      public static PathsEffectNetwork.FlashMode byName(String name) {
         if (name == null) {
            return WHITE;
         } else {
            String lower = name.toLowerCase(Locale.ROOT);
            if (lower.equals("black") || lower.equals("dark")) {
               return BLACK;
            } else {
               return lower.equals("none") ? NONE : WHITE;
            }
         }
      }

      public static PathsEffectNetwork.FlashMode byId(int id) {
         PathsEffectNetwork.FlashMode[] values = values();
         return id >= 0 && id < values.length ? values[id] : WHITE;
      }
   }

   public record PathsEffectPayload(
      PathsEffectNetwork.EffectKind effectKind, PathsEffectNetwork.FlashMode flashMode, int durationTicks, float alpha, float strength, float impactStrength
   ) implements CustomPacketPayload {
      public static final Type<PathsEffectNetwork.PathsEffectPayload> TYPE = new Type(
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "paths_effect")
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, PathsEffectNetwork.PathsEffectPayload> STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, PathsEffectNetwork.PathsEffectPayload>() {
         @NotNull
         public PathsEffectNetwork.PathsEffectPayload decode(@NotNull RegistryFriendlyByteBuf buf) {
            PathsEffectNetwork.EffectKind effectKind = PathsEffectNetwork.EffectKind.byId(buf.readVarInt());
            PathsEffectNetwork.FlashMode flashMode = PathsEffectNetwork.FlashMode.byId(buf.readVarInt());
            int durationTicks = buf.readVarInt();
            float alpha = buf.readFloat();
            float strength = buf.readFloat();
            float impactStrength = buf.readFloat();
            return new PathsEffectNetwork.PathsEffectPayload(effectKind, flashMode, durationTicks, alpha, strength, impactStrength);
         }

         public void encode(@NotNull RegistryFriendlyByteBuf buf, @NotNull PathsEffectNetwork.PathsEffectPayload payload) {
            buf.writeVarInt(payload.effectKind().ordinal());
            buf.writeVarInt(payload.flashMode().ordinal());
            buf.writeVarInt(payload.durationTicks());
            buf.writeFloat(payload.alpha());
            buf.writeFloat(payload.strength());
            buf.writeFloat(payload.impactStrength());
         }
      };

      public static PathsEffectNetwork.PathsEffectPayload flash(PathsEffectNetwork.FlashMode mode, int durationTicks, float alpha) {
         return new PathsEffectNetwork.PathsEffectPayload(
            PathsEffectNetwork.EffectKind.FLASH,
            mode == null ? PathsEffectNetwork.FlashMode.WHITE : mode,
            Mth.clamp(durationTicks, 1, 12000),
            Mth.clamp(alpha, 0.0F, 1.0F),
            0.0F,
            0.0F
         );
      }

      public static PathsEffectNetwork.PathsEffectPayload shake(int durationTicks, float strength, float impactStrength) {
         return new PathsEffectNetwork.PathsEffectPayload(
            PathsEffectNetwork.EffectKind.SHAKE,
            PathsEffectNetwork.FlashMode.NONE,
            Mth.clamp(durationTicks, 1, 12000),
            0.0F,
            Mth.clamp(strength, 0.0F, 20.0F),
            Mth.clamp(impactStrength, 0.0F, 20.0F)
         );
      }

      public static void handle(PathsEffectNetwork.PathsEffectPayload payload, IPayloadContext context) {
         context.enqueueWork(() -> {
            if (FMLEnvironment.dist.isClient()) {
               PathsEffectNetwork.ClientOnly.handle(payload);
            }
         });
      }

      @NotNull
      public Type<? extends CustomPacketPayload> type() {
         return TYPE;
      }
   }
}
