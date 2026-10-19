package com.chronodawn.core.time;

import com.chronodawn.data.DimensionStateData;
import com.chronodawn.entities.bosses.ChronosWardenEntity;
import com.chronodawn.entities.bosses.ClockworkColossusEntity;
import com.chronodawn.entities.bosses.EntropyKeeperEntity;
import com.chronodawn.entities.bosses.TemporalPhantomEntity;
import com.chronodawn.entities.bosses.TimeGuardianEntity;
import com.chronodawn.entities.bosses.TimeTyrantEntity;
import com.chronodawn.entities.mobs.FloqEntity;
import com.chronodawn.entities.mobs.TimeKeeperEntity;
import com.chronodawn.registry.ModDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

public class TimeDistortionEffect {
   private static final int EFFECT_DURATION = 100;
   private static final int SLOWNESS_AMPLIFIER = 3;
   private static final int ENHANCED_SLOWNESS_AMPLIFIER = 4;

   public static void applyTimeDistortion(LivingEntity entity) {
      if (isInChronoDawnDimension(entity)) {
         if (isHostileMob(entity)) {
            boolean isEnhanced = isDimensionEnhanced(entity);
            int amplifier = isEnhanced ? 4 : 3;
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, amplifier, false, false));
         }
      }
   }

   private static boolean isInChronoDawnDimension(LivingEntity entity) {
      return entity.level().dimension().location().equals(ModDimensions.CHRONO_DAWN_DIMENSION.location());
   }

   private static boolean isHostileMob(LivingEntity entity) {
      if (entity instanceof Player player) {
         return false;
      } else if (entity instanceof TimeGuardianEntity) {
         return false;
      } else if (entity instanceof TimeTyrantEntity) {
         return false;
      } else if (entity instanceof ChronosWardenEntity) {
         return false;
      } else if (entity instanceof ClockworkColossusEntity) {
         return false;
      } else if (entity instanceof EntropyKeeperEntity) {
         return false;
      } else if (entity instanceof TemporalPhantomEntity) {
         return false;
      } else if (entity instanceof TimeKeeperEntity) {
         return false;
      } else {
         return entity instanceof FloqEntity ? false : entity instanceof Monster;
      }
   }

   private static boolean isDimensionEnhanced(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel serverLevel) {
         DimensionStateData dimensionState = DimensionStateData.get(serverLevel);
         return dimensionState.getTimeDistortionLevel() == DimensionStateData.TimeDistortionLevel.SLOWNESS_V;
      } else {
         return false;
      }
   }
}
