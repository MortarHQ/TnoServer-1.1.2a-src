package twilightforest.asmhooks;

import java.util.function.BiPredicate;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.custom.TravellersModifiersManager;
import twilightforest.item.travellers_gear.TravellersGearLogic;

public class EntityHooks {
   @Nullable
   public static boolean processWaterWalking(boolean o, LivingEntity livingEntity, FluidState fluidState) {
      if (!fluidState.is(FluidTags.WATER)) {
         return o;
      }

      if (!TravellersModifiersManager.isModifierActive(livingEntity, TravellersModifiersManager.WATER_WALK_MODIFIER)) {
         return o;
      }

      boolean isWaterWalking = TravellersGearLogic.isBelowMaxWaterWalkingSubmergedHeight(livingEntity) && !livingEntity.isShiftKeyDown();
      if (livingEntity.getFluidTypeHeight((FluidType)NeoForgeMod.WATER_TYPE.value()) > 0.0 && isWaterWalking && livingEntity.level().getGameTime() % 3L == 1L) {
         TravellersGearLogic.waterWalkingSplashEffect(livingEntity);
      }

      return isWaterWalking;
   }

   public static boolean unrestrainedSprintingInWater(boolean isInWater, LivingEntity livingEntity) {
      return !TravellersModifiersManager.isModifierActive(livingEntity, TravellersModifiersManager.UNRESTRAINED_MODIFIER)
         ? isInWater
         : !livingEntity.canStandOnFluid(livingEntity.level().getFluidState(livingEntity.blockPosition())) && isInWater;
   }

   public static BiPredicate<FluidType, Double> unrestrainedSwimPredicate(BiPredicate<FluidType, Double> o, LivingEntity livingEntity) {
      return (fluidType, height) -> {
         FluidState fs = livingEntity.level().getFluidState(livingEntity.blockPosition());
         boolean oResult = o.test(fluidType, height);
         return fluidType != NeoForgeMod.WATER_TYPE.value() ? oResult : unrestrainedSprintingInWater(oResult, livingEntity);
      };
   }

   public static boolean overrideStayCloseToHolder(boolean prior, PathfinderMob mob) {
      return prior && !mob.hasData(TFDataAttachments.LEASH_PATHFINDER_OVERRIDE);
   }

   public static float resetFactorWithUnrestrained(float o, Entity entity) {
      return TravellersModifiersManager.isModifierActive(entity, TravellersModifiersManager.UNRESTRAINED_MODIFIER) ? 1.0F : o;
   }

   public static Entity resetStuckUnrestrained(Entity entity) {
      if (entity instanceof LivingEntity living
         && !(living.stuckSpeedMultiplier.lengthSqr() <= 1.0E-7)
         && TravellersModifiersManager.isModifierActive(entity, TravellersModifiersManager.UNRESTRAINED_MODIFIER)) {
         living.stuckSpeedMultiplier = Vec3.ZERO;
         return entity;
      } else {
         return entity;
      }
   }
}
