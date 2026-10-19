package com.chronodawn.items.shield;

import com.chronodawn.data.PlayerProgressData;
import com.chronodawn.network.ModNetworking;
import com.chronodawn.registry.ModParticles;
import com.chronodawn.registry.ModSounds;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public final class ChronoShieldEffectHandler {
   private static final Set<ResourceLocation> SHORTENED_EFFECTS = Set.of(
      BuiltInRegistries.MOB_EFFECT.getKey((MobEffect)MobEffects.MOVEMENT_SLOWDOWN.value()),
      BuiltInRegistries.MOB_EFFECT.getKey((MobEffect)MobEffects.WEAKNESS.value()),
      BuiltInRegistries.MOB_EFFECT.getKey((MobEffect)MobEffects.DIG_SLOWDOWN.value())
   );
   private static final int SPEED_DURATION_TICKS = 20;
   private static final int SPEED_COOLDOWN_TICKS = 60;
   private static final int ECHO_ACTIVE_TICKS = 100;
   private static final int ECHO_COOLDOWN_TICKS = 600;

   private ChronoShieldEffectHandler() {
   }

   public static void onBlockSuccess(ServerPlayer player, ChronoShieldTier tier) {
      if (tier.hasSpeedOnBlock) {
         long now = player.level().getGameTime();
         PlayerProgressData data = PlayerProgressData.get((ServerLevel)player.level());
         long cdEnd = data.getShieldSpeedCooldownEnd(player.getUUID());
         if (now >= cdEnd) {
            data.setShieldSpeedCooldownEnd(player.getUUID(), now + 60L);
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 0, false, false, true));
            ModNetworking.sendShieldSpeedCooldown(player, 60);
         }
      }

      tryGenerateEcho(player, tier);
   }

   public static void tryGenerateEcho(ServerPlayer player, ChronoShieldTier tier) {
      if (tier.hasTimeEcho) {
         long now = player.level().getGameTime();
         PlayerProgressData data = PlayerProgressData.get((ServerLevel)player.level());
         long cdEnd = data.getShieldEchoCooldownEnd(player.getUUID());
         if (now >= cdEnd) {
            long activeUntil = data.getShieldEchoActiveUntil(player.getUUID());
            if (now >= activeUntil) {
               data.setShieldEchoActiveUntil(player.getUUID(), now + 100L);
               emitEchoGenerateFx(player);
            }
         }
      }
   }

   private static void emitEchoGenerateFx(ServerPlayer player) {
      try {
         ServerLevel level = (ServerLevel)player.level();

         for (int i = 0; i < 8; i++) {
            double angle = (Math.PI * 2) * i / 8.0;
            double dx = Math.cos(angle) * 0.8;
            double dz = Math.sin(angle) * 0.8;
            level.sendParticles(
               (SimpleParticleType)ModParticles.CHRONO_SHIELD_ECHO.get(), player.getX() + dx, player.getY() + 1.2, player.getZ() + dz, 1, 0.0, 0.0, 0.0, 0.0
            );
         }

         level.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.SHIELD_ECHO_GENERATE.get(), SoundSource.PLAYERS, 0.7F, 1.2F);
      } catch (Throwable var9) {
      }
   }

   public static boolean tryConsumeEcho(ServerPlayer player) {
      long now = player.level().getGameTime();
      PlayerProgressData data = PlayerProgressData.get((ServerLevel)player.level());
      long activeUntil = data.getShieldEchoActiveUntil(player.getUUID());
      if (now >= activeUntil) {
         return false;
      }

      data.setShieldEchoActiveUntil(player.getUUID(), 0L);
      data.setShieldEchoCooldownEnd(player.getUUID(), now + 600L);
      ModNetworking.sendShieldEchoCooldown(player, 600);

      for (InteractionHand hand : InteractionHand.values()) {
         ItemStack stack = player.getItemInHand(hand);
         if (stack.getItem() instanceof ChronoShieldMarker) {
            stack.hurtAndBreak(1, player, EquipmentSlot.OFFHAND);
            break;
         }
      }

      emitEchoConsumeFx(player);
      return true;
   }

   private static void emitEchoConsumeFx(ServerPlayer player) {
      try {
         ServerLevel level = (ServerLevel)player.level();
         RandomSource random = level.getRandom();

         for (int i = 0; i < 12; i++) {
            double dx = (random.nextDouble() - 0.5) * 1.5;
            double dy = random.nextDouble() * 1.0;
            double dz = (random.nextDouble() - 0.5) * 1.5;
            level.sendParticles(
               (SimpleParticleType)ModParticles.CHRONO_SHIELD_ECHO.get(), player.getX() + dx, player.getY() + dy, player.getZ() + dz, 1, 0.0, 0.05, 0.0, 0.02
            );
         }

         level.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.SHIELD_ECHO_CONSUME.get(), SoundSource.PLAYERS, 0.8F, 0.9F);
      } catch (Throwable var10) {
      }
   }

   public static void emitEchoActiveDriftFx(ServerPlayer player) {
      try {
         ServerLevel level = (ServerLevel)player.level();
         level.sendParticles(
            (SimpleParticleType)ModParticles.CHRONO_SHIELD_ECHO.get(), player.getX(), player.getY() + 1.0, player.getZ(), 1, 0.3, 0.3, 0.3, 0.01
         );
      } catch (Throwable var2) {
      }
   }

   public static MobEffectInstance maybeShortenDebuff(LivingEntity target, MobEffectInstance incoming) {
      if (!isHoldingChronoShield(target)) {
         return incoming;
      }

      Holder<MobEffect> holder = incoming.getEffect();
      ResourceLocation id = BuiltInRegistries.MOB_EFFECT.getKey((MobEffect)holder.value());
      if (!SHORTENED_EFFECTS.contains(id)) {
         return incoming;
      }

      int halved = Math.max(1, incoming.getDuration() / 2);
      return new MobEffectInstance(holder, halved, incoming.getAmplifier(), incoming.isAmbient(), incoming.isVisible(), incoming.showIcon());
   }

   public static boolean isHoldingChronoShield(LivingEntity entity) {
      return entity.getMainHandItem().getItem() instanceof ChronoShieldMarker || entity.getOffhandItem().getItem() instanceof ChronoShieldMarker;
   }

   public static boolean isBlockingWithChronoShield(LivingEntity entity) {
      if (!entity.isBlocking()) {
         return false;
      }

      ItemStack useItem = entity.getUseItem();
      return useItem.getItem() instanceof ChronoShieldMarker;
   }
}
