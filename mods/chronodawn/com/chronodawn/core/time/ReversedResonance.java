package com.chronodawn.core.time;

import com.chronodawn.ChronoDawn;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ReversedResonance {
   public static final int DURATION_SHORT = 600;
   public static final int DURATION_LONG = 1200;
   public static final double EFFECT_RANGE = 20.0;

   public static void trigger(ServerLevel level, Vec3 center, int durationTicks, String triggerSource) {
      ChronoDawn.LOGGER
         .debug(
            "Reversed Resonance triggered at [{}, {}, {}] for {} ticks (source: {})", new Object[]{center.x, center.y, center.z, durationTicks, triggerSource}
         );
      AABB effectArea = new AABB(center, center).inflate(20.0);
      level.getEntitiesOfClass(Player.class, effectArea).forEach(player -> applyPlayerEffect(player, durationTicks));
      level.getEntitiesOfClass(LivingEntity.class, effectArea).forEach(entity -> {
         if (!(entity instanceof Player) && !entity.getType().getCategory().isFriendly()) {
            applyMobEffect(entity, durationTicks);
         }
      });
   }

   private static void applyPlayerEffect(Player player, int durationTicks) {
      if (player instanceof ServerPlayer serverPlayer) {
         Component warningMessage = Component.literal("⚠ 反転共鳴 / Reversed Resonance ⚠").withStyle(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.BOLD});
         serverPlayer.displayClientMessage(warningMessage, false);
         Component title = Component.literal("反転共鳴").withStyle(new ChatFormatting[]{ChatFormatting.DARK_RED, ChatFormatting.BOLD});
         Component subtitle = Component.literal("Reversed Resonance").withStyle(ChatFormatting.RED);
         serverPlayer.connection.send(new ClientboundSetTitlesAnimationPacket(10, 40, 10));
         serverPlayer.connection.send(new ClientboundSetTitleTextPacket(title));
         serverPlayer.connection.send(new ClientboundSetSubtitleTextPacket(subtitle));
      }

      MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, durationTicks, 3, false, true, true);
      player.addEffect(slowness);
      ChronoDawn.LOGGER.debug("Applied Slowness IV to player {} for {} ticks", player.getName().getString(), durationTicks);
   }

   private static void applyMobEffect(LivingEntity entity, int durationTicks) {
      MobEffectInstance speed = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, durationTicks, 1, false, true, true);
      entity.addEffect(speed);
      ChronoDawn.LOGGER.debug("Applied Speed II to mob {} for {} ticks", entity.getType().getDescriptionId(), durationTicks);
   }

   public static void triggerOnTimeGuardianDefeat(ServerLevel level, Vec3 center) {
      trigger(level, center, 600, "Time Guardian defeat");
   }

   public static void triggerOnTimeTyrantDefeat(ServerLevel level, Vec3 center) {
      trigger(level, center, 1200, "Time Tyrant defeat");
   }

   public static void triggerOnUnstableHourglassCraft(ServerLevel level, Vec3 center) {
      trigger(level, center, 600, "Unstable Hourglass craft");
   }
}
