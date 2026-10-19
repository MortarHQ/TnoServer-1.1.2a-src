package com.github.b4ndithelps.tennogamenolife.ability.skill.extra;

import com.github.b4ndithelps.tennogamenolife.race.Flugel.IFlugelRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.subclass.ISpatialMovement;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class HeavenlyShiftSkill extends Skill implements ISpatialMovement {
   private static final double UNLOCK_MAGICULE = 5000.0;
   private static final double INSTANT_BASE_COST = 500.0;
   private static final double INSTANT_COST_PER_BLOCK = 50.0;
   private static final double MARK_COST = 500.0;
   private static final double RETURN_BASE_COST = 1000.0;
   private static final double RETURN_COST_PER_BLOCK = 50.0;
   private static final double CROSS_DIMENSION_MULTIPLIER = 5.0;
   private static final double INSTANT_RANGE = 200.0;
   private static final int RETURN_CHARGE_TICKS = 40;
   private static final String COORDINATE_SUB_MODE = "CoordinateSubMode";
   private static final String SKIP_COORDINATE_CAST = "SkipCoordinateCast";
   private static final String HAS_MARK = "HasHeavenlyShiftMark";
   private static final String MARK_DIMENSION = "HeavenlyShiftDimension";
   private static final String MARK_X = "HeavenlyShiftX";
   private static final String MARK_Y = "HeavenlyShiftY";
   private static final String MARK_Z = "HeavenlyShiftZ";

   public HeavenlyShiftSkill() {
      super(SkillType.EXTRA);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/skill/extra/heavenly_shift.png");
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return meetsUnlockRequirement(entity);
   }

   public static boolean meetsUnlockRequirement(Player player) {
      boolean isFlugel = RaceAPI.getRaceFrom(player).getRace().map(instance -> instance.getRace() instanceof IFlugelRace).orElse(false);
      return isFlugel && EnergyHelper.getMaxMagicule(player) > 5000.0;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 0.0;
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "heavenly_shift.instant";
         case 1 -> "heavenly_shift.coordinate";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return mode == 0 ? 500.0 : 500.0;
   }

   public boolean canDimensionTravel(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (mode == 0) {
            this.performInstantShift(instance, entity);
         } else if (mode == 1) {
            CompoundTag tag = instance.getOrCreateTag();
            if (entity.isShiftKeyDown()) {
               int nextSubMode = tag.getInt("CoordinateSubMode") == 0 ? 1 : 0;
               tag.putInt("CoordinateSubMode", nextSubMode);
               tag.putBoolean("SkipCoordinateCast", true);
               instance.markDirty();
               this.displayActionBar(
                  entity,
                  Component.translatable(
                        nextSubMode == 0 ? "tennogamenolife.skill.heavenly_shift.submode.mark" : "tennogamenolife.skill.heavenly_shift.submode.return"
                     )
                     .withStyle(ChatFormatting.AQUA)
               );
               entity.level()
                  .playSound(
                     null, entity.blockPosition(), (SoundEvent)SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 0.8F, nextSubMode == 0 ? 1.1F : 0.9F
                  );
            } else {
               tag.putBoolean("SkipCoordinateCast", false);
               if (tag.getInt("CoordinateSubMode") == 0) {
                  this.imprintCoordinate(instance, entity);
               } else if (!tag.getBoolean("HasHeavenlyShiftMark")) {
                  entity.sendSystemMessage(Component.translatable("tennogamenolife.skill.heavenly_shift.no_mark").withStyle(ChatFormatting.RED));
                  entity.level().playSound(null, entity.blockPosition(), (SoundEvent)SoundEvents.NOTE_BLOCK_BASS.value(), SoundSource.PLAYERS, 1.0F, 0.7F);
               }
            }
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode != 1) {
         return false;
      }

      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.getBoolean("SkipCoordinateCast") && tag.getInt("CoordinateSubMode") != 0 && tag.getBoolean("HasHeavenlyShiftMark")) {
         if (entity instanceof Player player && heldTicks % 10 == 0) {
            player.displayClientMessage(
               Component.translatable(
                     "tennogamenolife.skill.heavenly_shift.return_charging",
                     new Object[]{String.format(Locale.ROOT, "%.1f", Math.min(heldTicks, 40) / 20.0), String.format(Locale.ROOT, "%.1f", 2.0)}
                  )
                  .withStyle(ChatFormatting.GOLD),
               true
            );
         }

         return true;
      } else {
         return false;
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 1 && !entity.level().isClientSide()) {
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.getBoolean("SkipCoordinateCast")) {
            tag.putBoolean("SkipCoordinateCast", false);
            instance.markDirty();
         } else if (tag.getInt("CoordinateSubMode") == 1 && tag.getBoolean("HasHeavenlyShiftMark")) {
            if (heldTicks < 40) {
               this.displayActionBar(entity, Component.translatable("tennogamenolife.skill.heavenly_shift.return_not_ready").withStyle(ChatFormatting.RED));
            } else {
               this.returnToCoordinate(instance, entity);
            }
         }
      }
   }

   private void performInstantShift(ManasSkillInstance instance, LivingEntity entity) {
      if (SkillUtils.shouldCancelTeleportation(entity)) {
         this.teleportBlocked(entity);
      } else {
         Vec3 destination = this.findInstantDestination(entity);
         if (destination == null) {
            this.displayActionBar(entity, Component.translatable("tennogamenolife.skill.heavenly_shift.no_safe_position").withStyle(ChatFormatting.RED));
         } else {
            double distance = entity.position().distanceTo(destination);
            double cost = 500.0 + distance * 50.0;
            if (!this.isOutOfEnergy(entity, instance, 0.0, cost)) {
               ISpatialMovement.warp(entity, destination.x, destination.y, destination.z);
               instance.setCoolDown(60, 0);
               instance.addMasteryPoint(entity);
            }
         }
      }
   }

   private Vec3 findInstantDestination(LivingEntity entity) {
      Level level = entity.level();
      Vec3 look = entity.getViewVector(1.0F).normalize();
      Vec3 eye = entity.getEyePosition(1.0F);
      Vec3 end = eye.add(look.scale(200.0));
      BlockHitResult hit = level.clip(new ClipContext(eye, end, Block.COLLIDER, Fluid.NONE, entity));
      Vec3 targetEye = hit.getType() == Type.MISS ? end : hit.getLocation().subtract(look.scale(0.75));
      Vec3 preferredFeet = targetEye.subtract(0.0, entity.getEyeHeight(), 0.0);

      for (int step = 0; step <= 48; step++) {
         Vec3 candidate = preferredFeet.subtract(look.scale(step * 0.5));
         if (this.isSafeDestination(level, entity, candidate)) {
            return candidate;
         }
      }

      return null;
   }

   private void imprintCoordinate(ManasSkillInstance instance, LivingEntity entity) {
      if (!this.isOutOfEnergy(entity, instance, 0.0, 500.0)) {
         CompoundTag tag = instance.getOrCreateTag();
         tag.putBoolean("HasHeavenlyShiftMark", true);
         tag.putString("HeavenlyShiftDimension", entity.level().dimension().location().toString());
         tag.putDouble("HeavenlyShiftX", entity.getX());
         tag.putDouble("HeavenlyShiftY", entity.getY());
         tag.putDouble("HeavenlyShiftZ", entity.getZ());
         instance.markDirty();
         instance.addMasteryPoint(entity);
         entity.sendSystemMessage(
            Component.translatable(
                  "tennogamenolife.skill.heavenly_shift.marked",
                  new Object[]{entity.blockPosition().getX(), entity.blockPosition().getY(), entity.blockPosition().getZ()}
               )
               .withStyle(ChatFormatting.AQUA)
         );
         entity.level().playSound(null, entity.blockPosition(), SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.PLAYERS, 0.8F, 1.2F);
      }
   }

   private void returnToCoordinate(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         if (SkillUtils.shouldCancelTeleportation(entity)) {
            this.teleportBlocked(entity);
         } else {
            CompoundTag tag = instance.getOrCreateTag();

            ResourceLocation dimensionId;
            try {
               dimensionId = ResourceLocation.parse(tag.getString("HeavenlyShiftDimension"));
            } catch (Exception ignored) {
               this.clearInvalidMark(instance, entity);
               return;
            }

            if (this.getBlacklistDimensions().contains(dimensionId.toString())) {
               entity.sendSystemMessage(Component.translatable("tennogamenolife.skill.heavenly_shift.dimension_blocked").withStyle(ChatFormatting.RED));
            } else {
               ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, dimensionId);
               MinecraftServer server = player.getServer();
               ServerLevel destinationLevel = server == null ? null : server.getLevel(dimension);
               if (destinationLevel == null) {
                  this.clearInvalidMark(instance, entity);
               } else {
                  Vec3 stored = new Vec3(tag.getDouble("HeavenlyShiftX"), tag.getDouble("HeavenlyShiftY"), tag.getDouble("HeavenlyShiftZ"));
                  Vec3 safe = this.findSafeMarkedDestination(destinationLevel, entity, stored);
                  if (safe == null) {
                     entity.sendSystemMessage(Component.translatable("tennogamenolife.skill.heavenly_shift.no_safe_position").withStyle(ChatFormatting.RED));
                  } else {
                     boolean crossDimension = entity.level().dimension() != dimension;
                     double distance = crossDimension ? 0.0 : entity.position().distanceTo(safe);
                     double cost = 1000.0 + distance * 50.0;
                     if (crossDimension) {
                        cost *= 5.0;
                     }

                     if (!this.isOutOfEnergy(entity, instance, 0.0, cost)) {
                        ISpatialMovement.warp(entity, entity, safe.x, safe.y, safe.z, dimension);
                        instance.setCoolDown(300, 1);
                        instance.addMasteryPoint(entity);
                     }
                  }
               }
            }
         }
      }
   }

   private Vec3 findSafeMarkedDestination(Level level, LivingEntity entity, Vec3 stored) {
      int[] verticalOffsets = new int[]{0, 1, 2, 3, -1};

      for (int offset : verticalOffsets) {
         Vec3 candidate = stored.add(0.0, offset, 0.0);
         if (this.isSafeDestination(level, entity, candidate)) {
            return candidate;
         }
      }

      return null;
   }

   private boolean isSafeDestination(Level level, LivingEntity entity, Vec3 destination) {
      if (destination.y < level.getMinBuildHeight() || destination.y + entity.getBbHeight() >= level.getMaxBuildHeight()) {
         return false;
      }

      if (!level.getWorldBorder().isWithinBounds(BlockPos.containing(destination))) {
         return false;
      }

      AABB movedBox = entity.getBoundingBox().move(destination.x - entity.getX(), destination.y - entity.getY(), destination.z - entity.getZ());
      return level.noCollision(entity, movedBox);
   }

   private void clearInvalidMark(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.putBoolean("HasHeavenlyShiftMark", false);
      instance.markDirty();
      entity.sendSystemMessage(Component.translatable("tennogamenolife.skill.heavenly_shift.invalid_mark").withStyle(ChatFormatting.RED));
   }

   private void teleportBlocked(LivingEntity entity) {
      this.displayActionBar(entity, Component.translatable("tensura.skill.spatial_blockade").withStyle(ChatFormatting.RED));
      entity.level().playSound(null, entity.blockPosition(), (SoundEvent)SoundEvents.NOTE_BLOCK_BASS.value(), SoundSource.PLAYERS, 1.0F, 0.6F);
   }

   private void displayActionBar(LivingEntity entity, Component message) {
      if (entity instanceof Player player) {
         player.displayClientMessage(message, true);
      } else {
         entity.sendSystemMessage(message);
      }
   }
}
