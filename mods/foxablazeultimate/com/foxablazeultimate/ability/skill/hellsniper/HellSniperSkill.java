package com.foxablazeultimate.ability.skill.hellsniper;

import com.foxablazeultimate.config.HellSniperConfig;
import com.foxablazeultimate.entity.HellSniperBeamEntity;
import com.foxablazeultimate.entity.HellSniperCircleEntity;
import com.foxablazeultimate.registry.FoxAblazeUltimateEntityTypes;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HellSniperSkill extends Skill {
   public static final int MODE_SNIPING = 0;
   public static final int MODE_INFINITE_SNIPING = 1;
   private static final int MODE_COUNT = 2;
   private static final int MIN_INFINITE_CIRCLES = 3;
   private static final int MAX_INFINITE_CIRCLES = 32;
   private static final double MAGIC_CIRCLE_VISUAL_DIAMETER = 4.05;
   private static final String TAG_INFINITE_END = "HellSniperInfiniteEnd";
   private static final String TAG_INFINITE_DIMENSION = "HellSniperInfiniteDimension";

   public HellSniperSkill() {
      super(SkillType.ULTIMATE);
   }

   private static HellSniperConfig.HellSniperSettings settings() {
      return HellSniperConfig.settings();
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/skill/hell_sniper.png");
   }

   public MutableComponent getColoredName() {
      MutableComponent name = super.getName();
      return name == null ? null : name.withStyle(ChatFormatting.DARK_RED);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return Double.POSITIVE_INFINITY;
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return false;
   }

   public int getModes(ManasSkillInstance instance) {
      return isInfiniteModeUnlocked(instance) ? 2 : 1;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int safeMode = sanitizeMode(mode);
      if (!isInfiniteModeUnlocked(instance)) {
         return safeMode == 0 ? -1 : 0;
      } else if (reverse) {
         return safeMode == 0 ? 1 : safeMode - 1;
      } else {
         return safeMode == 1 ? 0 : safeMode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (sanitizeMode(mode)) {
         case 0 -> "hell_sniper.sniping";
         case 1 -> "hell_sniper.infinite_sniping";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      HellSniperConfig.HellSniperSettings config = settings();
      return sanitizeMode(mode) == 1 ? Math.max(0.0, config.infiniteMagiculeCost) : Math.max(0.0, config.magiculeCost);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         int safeMode = sanitizeMode(mode);
         if (safeMode != 1 || isInfiniteModeUnlocked(instance)) {
            normalizeCooldowns(instance);
            if (!instance.onCoolDown(safeMode) || instance.canIgnoreCoolDown(entity, safeMode)) {
               if (safeMode == 1 && isInfiniteSnipingActive(instance, entity)) {
                  playFailureSound(entity);
               }
            }
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (entity.level().isClientSide()) {
         return false;
      }

      int safeMode = sanitizeMode(mode);
      if (safeMode == 1 && !isInfiniteModeUnlocked(instance)) {
         return false;
      }

      if (instance.onCoolDown(safeMode) && !instance.canIgnoreCoolDown(entity, safeMode)) {
         return false;
      }

      if (safeMode == 1 && isInfiniteSnipingActive(instance, entity)) {
         return false;
      }

      int requiredTicks = getChargeTicks(safeMode);
      showChargeProgress(entity, heldTicks, requiredTicks);
      return true;
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         int safeMode = sanitizeMode(mode);
         if (safeMode != 1 || isInfiniteModeUnlocked(instance)) {
            normalizeCooldowns(instance);
            int requiredTicks = getChargeTicks(safeMode);
            if (heldTicks < requiredTicks || instance.onCoolDown(safeMode) && !instance.canIgnoreCoolDown(entity, safeMode)) {
               if (heldTicks > 0 && heldTicks < requiredTicks) {
                  playFailureSound(entity);
               }
            } else if (safeMode == 1) {
               if (!isInfiniteSnipingActive(instance, entity) && !EnergyHelper.isOutOfEnergy(entity, instance, safeMode)) {
                  beginInfiniteCast(instance, entity);
               }
            } else {
               LivingEntity target = acquireTarget(entity);
               if (target == null) {
                  playFailureSound(entity);
               } else if (!EnergyHelper.isOutOfEnergy(entity, instance, safeMode)) {
                  beginCast(instance, entity, target, safeMode);
               }
            }
         }
      }
   }

   public int getMaxHeldTime(ManasSkillInstance instance, LivingEntity entity) {
      int inherited = super.getMaxHeldTime(instance, entity);
      return Math.max(inherited, getChargeTicks(1) + 1);
   }

   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      return true;
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      if (entity instanceof ServerPlayer player && !instance.isTemporarySkill() && !instance.isSubInstance()) {
         this.claimUniqueOwnership(player);
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getTag();
      if (tag != null) {
         tag.remove("HellSniperInfiniteEnd");
         tag.remove("HellSniperInfiniteDimension");
         instance.markDirty();
      }

      if (entity instanceof ServerPlayer player && !instance.isTemporarySkill() && !instance.isSubInstance()) {
         this.releaseUniqueOwnership(player);
      }

      super.onForgetSkill(instance, entity);
   }

   private static void beginCast(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, int mode) {
      if (owner.level() instanceof ServerLevel level) {
         HellSniperCircleEntity circle = new HellSniperCircleEntity(
            (EntityType<? extends HellSniperCircleEntity>)FoxAblazeUltimateEntityTypes.HELL_SNIPER_CIRCLE.get(), level
         );
         circle.initialize(owner, target);
         level.addFreshEntity(circle);
      }

      instance.setCoolDown(Math.max(0, settings().cooldownSeconds), mode);
      instance.markDirty();
      SkillAPI.getSkillsFrom(owner).checkAndMarkDirty(instance);
   }

   private static boolean beginInfiniteCast(ManasSkillInstance instance, LivingEntity owner) {
      if (owner.level() instanceof ServerLevel level) {
         HellSniperConfig.HellSniperSettings var28 = settings();
         int circleCount = getInfiniteCircleCount();
         double radius = getInfiniteFormationRadius();
         Vec3 centre = owner.getEyePosition();
         Vec3 facing = getHorizontalFacing(owner);
         Vec3 side = new Vec3(-facing.z, 0.0, facing.x);
         int appearanceTicks = Math.max(1, var28.appearanceDurationTicks);
         int appearanceStagger = Math.max(1, Math.min(appearanceTicks, var28.infiniteAppearanceStaggerTicks));
         int formationAppearanceTicks = appearanceTicks + appearanceStagger * Math.max(0, circleCount - 1);
         long castStart = level.getGameTime();
         long fireAt = (long)formationAppearanceTicks + Math.max(1, var28.aimingDurationTicks) + Math.max(1, var28.firingWarmupTicks);
         long durationTicks = Math.max(1L, var28.infiniteDurationSeconds) * 20L;
         long attackEnd = castStart + fireAt + durationTicks;
         ArrayList circles = new ArrayList(circleCount);

         for (int tag = 0; tag < circleCount; tag++) {
            double positionAngle = (Math.PI * 2) * tag / circleCount;
            Vec3 positionRadial = facing.scale(Math.cos(positionAngle)).add(side.scale(Math.sin(positionAngle)));
            Vec3 position = centre.add(positionRadial.scale(radius));
            HellSniperCircleEntity circle = new HellSniperCircleEntity(
               (EntityType<? extends HellSniperCircleEntity>)FoxAblazeUltimateEntityTypes.HELL_SNIPER_CIRCLE.get(), level
            );
            circle.initializeInfinite(
               owner, position, position.add(positionRadial.scale(10.0)), castStart, attackEnd, tag * appearanceStagger, formationAppearanceTicks
            );
            if (level.addFreshEntity(circle)) {
               circles.add(circle);
            }
         }

         if (circles.isEmpty()) {
            return false;
         }

         ((HellSniperCircleEntity)circles.getFirst()).configureInfiniteController(centre, circles);
         CompoundTag tag = instance.getOrCreateTag();
         tag.putLong("HellSniperInfiniteEnd", attackEnd + Math.max(0, var28.postFireDurationTicks));
         tag.putString("HellSniperInfiniteDimension", level.dimension().location().toString());
         instance.setCoolDown(Math.max(0, var28.infiniteCooldownSeconds), 1);
         instance.addMasteryPoint(owner);
         instance.markDirty();
         SkillAPI.getSkillsFrom(owner).checkAndMarkDirty(instance);
         return true;
      } else {
         return false;
      }
   }

   public static boolean releaseProjectile(LivingEntity owner, LivingEntity target, Vec3 launchPosition) {
      return releaseProjectile(owner, target, launchPosition, 0, true);
   }

   public static boolean releaseProjectile(LivingEntity owner, LivingEntity target, Vec3 launchPosition, int mode, boolean awardMastery) {
      if (owner.level() instanceof ServerLevel level) {
         HellSniperSkill var13 = (HellSniperSkill)FoxAblazeUltimateSkills.HELL_SNIPER.get();
         Skills skills = SkillAPI.getSkillsFrom(owner);
         ManasSkillInstance instance = (ManasSkillInstance)skills.getSkill(var13).orElse(null);
         if (instance == null) {
            return false;
         }

         HellSniperConfig.HellSniperSettings config = settings();
         float damage = (float)Math.max(0.0, instance.isMastered(owner) ? config.damageMastered : config.damage);
         HellSniperBeamEntity beam = new HellSniperBeamEntity(
            (EntityType<? extends HellSniperBeamEntity>)FoxAblazeUltimateEntityTypes.HELL_SNIPER_BEAM.get(), level
         );
         int safeMode = sanitizeMode(mode);
         beam.initialize(owner, target, instance, var13, safeMode, damage, config.explosionPower, config.explosionDestroysTerrain, launchPosition);
         level.addFreshEntity(beam);
         if (awardMastery) {
            instance.addMasteryPoint(owner);
            instance.markDirty();
            skills.checkAndMarkDirty(instance);
         }

         return true;
      } else {
         return false;
      }
   }

   public static void fireInfiniteVolley(LivingEntity owner, List<HellSniperCircleEntity> circles, Vec3 centre, long volleyIndex) {
      if (owner.level() instanceof ServerLevel level && !circles.isEmpty()) {
         double range = Math.max(1.0, settings().infiniteTargetingRange);
         AABB searchBox = new AABB(centre.x - range, centre.y - range, centre.z - range, centre.x + range, centre.y + range, centre.z + range);
         List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, searchBox, targetx -> isValidInfiniteTarget(owner, targetx, centre, range));
         targets.sort(Comparator.<LivingEntity>comparingDouble(targetx -> targetCenter(targetx).distanceToSqr(centre)).thenComparingInt(Entity::getId));

         for (HellSniperCircleEntity circle : circles) {
            circle.setTarget(null);
         }

         if (!targets.isEmpty()) {
            List<List<LivingEntity>> assigned = new ArrayList<>(circles.size());

            for (int index = 0; index < circles.size(); index++) {
               assigned.add(new ArrayList<>());
            }

            for (LivingEntity target : targets) {
               int best = -1;
               double bestDeviation = Double.MAX_VALUE;

               for (int index = 0; index < circles.size(); index++) {
                  double deviation = aimDeviation(circles.get(index), targetCenter(target));
                  if (deviation >= 0.0 && deviation < bestDeviation) {
                     bestDeviation = deviation;
                     best = index;
                  }
               }

               if (best >= 0) {
                  assigned.get(best).add(target);
               }
            }

            for (int index = 0; index < circles.size(); index++) {
               List<LivingEntity> reachable = assigned.get(index);
               if (!reachable.isEmpty()) {
                  LivingEntity target = reachable.get(Math.floorMod(volleyIndex + index, reachable.size()));
                  HellSniperCircleEntity circle = circles.get(index);
                  circle.trackInfiniteTarget(target);
                  releaseProjectile(owner, target, circle.position(), 1, false);
               }
            }
         }
      }
   }

   private static double aimDeviation(HellSniperCircleEntity circle, Vec3 targetPosition) {
      Vec3 delta = targetPosition.subtract(circle.position());
      if (Double.isFinite(delta.x) && Double.isFinite(delta.y) && Double.isFinite(delta.z) && !(delta.lengthSqr() < 1.0E-8)) {
         Vec3 direction = delta.normalize();
         double elevation = Math.toDegrees(Math.asin(Mth.clamp(-direction.y, -1.0, 1.0)));
         if (Math.abs(elevation) > verticalAimLimitDegrees()) {
            return -1.0;
         }

         if (direction.x * direction.x + direction.z * direction.z < 1.0E-8) {
            return -1.0;
         }

         float targetYaw = (float)Math.toDegrees(-Math.atan2(direction.x, direction.z));
         double deviation = Math.abs(Mth.degreesDifference(circle.getYRot(), targetYaw));
         return deviation > firingConeDegrees() ? -1.0 : deviation;
      } else {
         return -1.0;
      }
   }

   private static double verticalAimLimitDegrees() {
      double configured = settings().verticalAimLimitDegrees;
      return Double.isFinite(configured) ? Mth.clamp(configured, 0.0, 90.0) : 35.0;
   }

   private static double firingConeDegrees() {
      double configured = settings().infiniteFiringConeDegrees;
      return Double.isFinite(configured) ? Mth.clamp(configured, 1.0, 180.0) : 60.0;
   }

   private static LivingEntity acquireTarget(LivingEntity owner) {
      double range = Math.max(1.0, settings().targetingRange);
      LivingEntity target = (LivingEntity)ObjectSelectionHelper.getTargetingEntity(LivingEntity.class, owner, range, 0.25, true, true, true);
      return isValidLockedTarget(owner, target) ? target : null;
   }

   public static boolean isValidLockedTarget(LivingEntity owner, LivingEntity target) {
      if (!isEligibleTarget(owner, target)) {
         return false;
      }

      double range = Math.max(1.0, settings().targetingRange);
      return owner.distanceToSqr(target) <= range * range;
   }

   public static boolean isEligibleTarget(LivingEntity owner, LivingEntity target) {
      return target != null
         && target != owner
         && !(target instanceof Player)
         && target.isAlive()
         && !target.isRemoved()
         && !target.hasInfiniteMaterials()
         && !target.isAlliedTo(owner)
         && !owner.isAlliedTo(target)
         && target.level() == owner.level();
   }

   public static boolean isValidInfiniteTarget(LivingEntity owner, LivingEntity target, Vec3 centre, double range) {
      return isEligibleTarget(owner, target) && target.level().isLoaded(target.blockPosition()) && targetCenter(target).distanceToSqr(centre) <= range * range;
   }

   public static boolean ownsHellSniper(LivingEntity owner) {
      return SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)FoxAblazeUltimateSkills.HELL_SNIPER.get()).isPresent();
   }

   public static int getInfiniteCircleCount() {
      return Math.min(32, Math.max(3, settings().infiniteCircleCount));
   }

   public static double getInfiniteFormationRadius() {
      HellSniperConfig.HellSniperSettings config = settings();
      int circleCount = getInfiniteCircleCount();
      double spacing = finiteNonNegative(config.infiniteMinimumCircleSpacing, 1.0);
      double requestedRadius = finiteNonNegative(config.infiniteRingRadius, 7.0);
      double requiredChord = 4.05 + spacing;
      double minimumRadius = requiredChord / (2.0 * Math.sin(Math.PI / circleCount));
      return Math.max(minimumRadius, requestedRadius);
   }

   private static double finiteNonNegative(double value, double fallback) {
      return Double.isFinite(value) ? Math.max(0.0, value) : fallback;
   }

   private static Vec3 getHorizontalFacing(LivingEntity owner) {
      Vec3 look = owner.getLookAngle();
      Vec3 horizontal = new Vec3(look.x, 0.0, look.z);
      if (horizontal.lengthSqr() > 1.0E-8 && Double.isFinite(horizontal.x) && Double.isFinite(horizontal.z)) {
         return horizontal.normalize();
      }

      double yaw = Math.toRadians(owner.getYRot());
      return new Vec3(-Math.sin(yaw), 0.0, Math.cos(yaw));
   }

   private static boolean isInfiniteSnipingActive(ManasSkillInstance instance, LivingEntity owner) {
      CompoundTag tag = instance.getOrCreateTag();
      long end = tag.getLong("HellSniperInfiniteEnd");
      String dimension = tag.getString("HellSniperInfiniteDimension");
      boolean active = end >= owner.level().getGameTime() && dimension.equals(owner.level().dimension().location().toString());
      if (!active && (tag.contains("HellSniperInfiniteEnd") || tag.contains("HellSniperInfiniteDimension"))) {
         tag.remove("HellSniperInfiniteEnd");
         tag.remove("HellSniperInfiniteDimension");
         instance.markDirty();
      }

      return active;
   }

   private static Vec3 targetCenter(LivingEntity target) {
      return new Vec3(target.getX(), target.getY(0.5), target.getZ());
   }

   private static int sanitizeMode(int mode) {
      return Math.floorMod(mode, 2);
   }

   private static boolean isInfiniteModeUnlocked(ManasSkillInstance instance) {
      return instance != null && instance.getMastery() >= instance.getMaxMastery();
   }

   private static int getChargeTicks(int mode) {
      int seconds = sanitizeMode(mode) == 1 ? settings().infiniteChargeSeconds : settings().snipingChargeSeconds;
      long safeSeconds = Math.max(1L, seconds);
      return (int)Math.min(2147483646L, safeSeconds * 20L);
   }

   private static void showChargeProgress(LivingEntity entity, int heldTicks, int requiredTicks) {
      if (entity instanceof Player player && (heldTicks % 4 == 0 || heldTicks >= requiredTicks)) {
         int shownTicks = Math.min(Math.max(0, heldTicks), requiredTicks);
         String current = SkillUtils.ROUND_DOUBLE.format(shownTicks / 20.0);
         String maximum = SkillUtils.ROUND_DOUBLE.format(requiredTicks / 20.0);
         player.displayClientMessage(
            Component.translatable("tensura.magic.cast_time.max", new Object[]{current, maximum}).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)), true
         );
      }
   }

   private static void normalizeCooldowns(ManasSkillInstance instance) {
      int modeCount = Math.max(1, instance.getModes());
      List<Integer> cooldowns = new ArrayList<>(instance.getCooldownList());

      while (cooldowns.size() < modeCount) {
         cooldowns.add(0);
      }

      if (cooldowns.size() > modeCount) {
         cooldowns = new ArrayList<>(cooldowns.subList(0, modeCount));
      }

      if (!cooldowns.equals(instance.getCooldownList())) {
         instance.setCoolDownList(cooldowns);
         instance.markDirty();
      }
   }

   private static void playFailureSound(LivingEntity entity) {
      entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.DISPENSER_FAIL, SoundSource.PLAYERS, 0.7F, 1.15F);
   }

   private void claimUniqueOwnership(ServerPlayer player) {
      if (player.level().getGameRules().getBoolean(FoxAblazeGameRules.HELL_SNIPER_TRULY_UNIQUE)) {
         ServerLevel overworld = overworld(player);
         ResourceLocation id = this.getRegistryName();
         if (overworld != null && id != null) {
            ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
            UUID owner = unique.getOwner(id);
            if (owner == null || owner.equals(player.getUUID())) {
               unique.addSkill(id, player.getUUID());
               unique.markDirty();
            }
         }
      }
   }

   private void releaseUniqueOwnership(ServerPlayer player) {
      ServerLevel overworld = overworld(player);
      ResourceLocation id = this.getRegistryName();
      if (overworld != null && id != null) {
         ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
         if (player.getUUID().equals(unique.getOwner(id))) {
            unique.removeSkill(id);
            unique.markDirty();
         }
      }
   }

   private static ServerLevel overworld(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      return server == null ? null : server.overworld();
   }
}
