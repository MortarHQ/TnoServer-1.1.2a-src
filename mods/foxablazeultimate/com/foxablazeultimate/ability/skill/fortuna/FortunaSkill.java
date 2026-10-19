package com.foxablazeultimate.ability.skill.fortuna;

import com.foxablazeultimate.config.FortunaConfig;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill.AttributeTemplate;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.extra.HakiSkill;
import io.github.manasmods.tensura.ability.skill.extra.SacredHakiSkill;
import io.github.manasmods.tensura.ability.skill.extra.SenseSoundwaveSkill;
import io.github.manasmods.tensura.ability.skill.extra.SpatialDominationSkill;
import io.github.manasmods.tensura.ability.subclass.ISpatialMovement;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.data.TensuraBlockTags;
import io.github.manasmods.tensura.entity.magic.field.haki.HakiField;
import io.github.manasmods.tensura.entity.magic.field.haki.HakiField.HakiVariant;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.MiscEntityTypes;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.Locale;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class FortunaSkill extends Skill {
   private static final int MODE_SPACETIME_OPERATION = 0;
   private static final int MODE_MULTIDIMENSIONAL_BARRIER = 1;
   private static final int MODE_SACRED_HAKI = 2;
   private static final int MODE_SACRED_HAKI_COAT = 3;
   private static final int MODE_COUNT = 4;
   private static final String TAG_BARRIER_ACTIVE = "FortunaBarrierActive";
   private static final String TAG_BARRIER_DAMAGE_TYPES = "FortunaBarrierDamageTypes";
   private static final String TAG_PASSIVE_MASTERY_TICKS = "FortunaPassiveMasteryTicks";
   private static final String TAG_LAST_MAINTENANCE_TICK = "FortunaLastMaintenanceTick";
   private static final ResourceLocation PASSIVE_MODIFIER = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "fortuna_passive");
   private static final ResourceLocation SACRED_HAKI_MOVEMENT = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "fortuna_sacred_haki_movement");
   private static final ResourceLocation FORTUNA_WARP_SHOT = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "fortuna");
   private static final int[][] SAFE_HORIZONTAL_OFFSETS = new int[][]{
      {0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {2, 0}, {-2, 0}, {0, 2}, {0, -2}
   };
   private static final int[] SAFE_VERTICAL_OFFSETS = new int[]{0, 1, -1, 2, -2};

   public FortunaSkill() {
      super(SkillType.ULTIMATE);
      this.addHeldAttributeModifier(Attributes.MOVEMENT_SPEED, SACRED_HAKI_MOVEMENT, HakiSkill.CONFIG.speedMultiplier - 1.0, Operation.ADD_MULTIPLIED_TOTAL);
   }

   private static FortunaConfig.FortunaSettings config() {
      return FortunaConfig.get().Fortuna;
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/skill/fortuna.png");
   }

   public MutableComponent getColoredName() {
      MutableComponent name = super.getName();
      return name == null ? null : name.withStyle(ChatFormatting.GOLD);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return Double.POSITIVE_INFINITY;
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return false;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.getMastery() >= 0.0 && !instance.isTemporarySkill();
   }

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int safeMode = sanitizeMode(mode);
      if (reverse) {
         return safeMode == 0 ? 3 : safeMode - 1;
      } else {
         return safeMode == 3 ? 0 : safeMode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (sanitizeMode(mode)) {
         case 0 -> "fortuna.spacetime_operation";
         case 1 -> "fortuna.multidimensional_barrier";
         case 2 -> "fortuna.sacred_haki";
         case 3 -> "fortuna.sacred_haki_coat";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (sanitizeMode(mode)) {
         case 1 -> Math.max(0.0, config().barrierMagiculeCostPerSecond);
         case 2 -> SacredHakiSkill.CONFIG.magiculeCost;
         case 3 -> SacredHakiSkill.CONFIG.magiculeCostCoat;
         default -> 0.0;
      };
   }

   public boolean canScroll(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return sanitizeMode(mode) == 2 && instance.getMastery() >= 0.0;
   }

   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      return sanitizeMode(mode) == 2;
   }

   public double getAttributeModifierAmplifier(ManasSkillInstance instance, LivingEntity entity, Holder<Attribute> holder, AttributeTemplate template, int mode) {
      if (sanitizeMode(mode) != 2) {
         return 0.0;
      }

      if (!instance.isMastered(entity)) {
         return 1.0;
      }

      double normal = HakiSkill.CONFIG.speedMultiplier - 1.0;
      return normal == 0.0 ? 1.0 : (HakiSkill.CONFIG.speedMultiplierMastered - 1.0) / normal;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      int safeMode = sanitizeMode(mode);
      if (safeMode == 2) {
         instance.getOrCreateTag().putInt("HakiID", 0);
         instance.markDirty();
      } else if (!entity.level().isClientSide()) {
         if (safeMode == 3) {
            this.toggleSacredHakiCoat(instance, entity, safeMode);
         } else {
            switch (safeMode) {
               case 0:
                  this.useSpacetimeOperation(instance, entity, safeMode);
                  break;
               case 1:
                  this.toggleBarrier(instance, entity, safeMode);
            }
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      int safeMode = sanitizeMode(mode);
      if (safeMode != 2) {
         return false;
      }

      if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, safeMode)) {
         return false;
      }

      if (heldTicks > 0 && heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0) {
         instance.addMasteryPoint(entity);
      }

      return this.summonSacredHaki(instance, entity, heldTicks, safeMode);
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      int safeMode = sanitizeMode(mode);
      if (safeMode == 2) {
         if (this.hasAttributeApplied(entity, Attributes.MOVEMENT_SPEED, SACRED_HAKI_MOVEMENT)) {
            instance.setCoolDown(instance.isMastered(entity) ? HakiSkill.CONFIG.cooldownMastered : HakiSkill.CONFIG.cooldown, safeMode);
         }
      }
   }

   public void onScroll(ManasSkillInstance instance, LivingEntity entity, double delta, int mode) {
      if (sanitizeMode(mode) == 2) {
         HakiSkill.changeEPUsed(instance, entity, delta);
      }
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled() || isBarrierActive(instance) || this.isInSlot(entity, instance);
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      this.tickServerMaintenance(instance, entity);
   }

   public void tickServerMaintenance(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         long gameTime = entity.level().getGameTime();
         CompoundTag tag = instance.getOrCreateTag();
         if (!tag.contains("FortunaLastMaintenanceTick") || tag.getLong("FortunaLastMaintenanceTick") != gameTime) {
            tag.putLong("FortunaLastMaintenanceTick", gameTime);
            boolean dirty = false;
            this.reconcileWarpShotModifier(instance, entity);
            if (isBarrierActive(instance)) {
               if (!this.isInSlot(entity, instance)) {
                  this.closeBarrier(instance, entity, true, false);
               } else if (gameTime % 20L == 0L) {
                  double cost = Math.max(0.0, config().barrierMagiculeCostPerSecond);
                  if (cost > 0.0 && EnergyHelper.isOutOfEnergy(entity, instance, 1)) {
                     this.closeBarrier(instance, entity, true, false);
                     entity.sendSystemMessage(
                        Component.translatable("foxablazeultimate.skill.fortuna.barrier.insufficient_magicule").withStyle(ChatFormatting.RED)
                     );
                  } else {
                     instance.addMasteryPoint(entity);
                     dirty = true;
                  }
               }
            }

            if (instance.isToggled()) {
               int ticks = tag.getInt("FortunaPassiveMasteryTicks") + 1;
               int interval = Math.max(1, config().passiveMasteryIntervalTicks);
               if (ticks >= interval) {
                  ticks = 0;
                  instance.addMasteryPoint(entity);
                  dirty = true;
               }

               tag.putInt("FortunaPassiveMasteryTicks", ticks);
               if (gameTime % 20L == 0L) {
                  double cost = Math.max(0.0, config().universalPerceptionMagiculeCostPerSecond);
                  if (cost > 0.0 && EnergyHelper.isOutOfEnergy(entity, 0.0, cost)) {
                     instance.setToggled(false);
                     instance.onToggleOff(entity);
                     entity.sendSystemMessage(
                        Component.translatable("tensura.skill.lack_magicule.toggled_off", new Object[]{instance.getChatDisplayName(true)})
                           .withStyle(ChatFormatting.RED)
                     );
                     dirty = true;
                  }
               }
            }

            if (dirty) {
               instance.markDirty();
            }
         }
      }
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (instance.isToggled() && !((Float)amount.get() <= 0.0F) && !isSpatialDominationToggled(owner) && TensuraDamageHelper.isSpatialDamage(source)) {
         double multiplier = Math.max(0.0, config().spatialDamageMultiplier);
         amount.set((float)(((Float)amount.get()).floatValue() * multiplier));
         return true;
      } else {
         return true;
      }
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity owner, DamageSource source, Changeable<Float> amount) {
      if (this.isInSlot(owner, instance) && isBarrierActive(instance) && !((Float)amount.get() <= 0.0F)) {
         CompoundTag tag = instance.getOrCreateTag();
         CompoundTag damageTypes = tag.getCompound("FortunaBarrierDamageTypes");
         String damageType = source.typeHolder().unwrapKey().map(key -> key.location().toString()).orElse(source.getMsgId());
         int hits = damageTypes.getInt(damageType) + 1;
         damageTypes.putInt(damageType, hits);
         tag.put("FortunaBarrierDamageTypes", damageTypes);
         FortunaConfig.FortunaSettings settings = config();
         int hitsPerStep = Math.max(1, settings.barrierHitsPerStep);
         int learnedSteps = Math.max(0, hits - 1) / hitsPerStep;
         double reduction = Math.max(0.0, settings.barrierInitialReduction) + learnedSteps * Math.max(0.0, settings.barrierReductionPerStep);
         reduction = Math.min(Math.max(0.0, settings.barrierMaximumReduction), reduction);
         reduction = Math.min(1.0, reduction);
         amount.set((float)(((Float)amount.get()).floatValue() * (1.0 - reduction)));
         instance.markDirty();
         return true;
      } else {
         return true;
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOn(instance, entity);
      if (!instance.isTemporarySkill()) {
         applyPassiveAttributes(entity);
      }
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      if (!instance.isTemporarySkill()) {
         if (entity instanceof ServerPlayer player) {
            this.claimUniqueOwnership(player);
         }
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOff(instance, entity);
      removePassiveAttributes(entity);
      entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT));
      if (!entity.level().isClientSide() && isBarrierActive(instance)) {
         this.closeBarrier(instance, entity, true, false);
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      removePassiveAttributes(entity);
      removeWarpShotModifier(entity);
      clearBarrierState(instance);
      if (entity instanceof ServerPlayer player) {
         this.releaseUniqueOwnership(player);
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity owner, DamageSource source) {
      if (!owner.level().isClientSide() && isBarrierActive(instance)) {
         this.closeBarrier(instance, owner, true, false);
      } else {
         clearBarrierState(instance);
      }

      return true;
   }

   public void onRespawn(ManasSkillInstance instance, ServerPlayer owner, boolean conqueredEnd) {
      if (isBarrierActive(instance)) {
         instance.setCoolDown(Math.max(0, config().barrierCooldownSeconds), 1);
      }

      clearBarrierState(instance);
      removePassiveAttributes(owner);
      removeWarpShotModifier(owner);
      if (instance.isToggled()) {
         applyPassiveAttributes(owner);
      }

      if (!owner.level().isClientSide()) {
         this.reconcileWarpShotModifier(instance, owner);
      }
   }

   private void useSpacetimeOperation(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (!instance.onCoolDown(mode)) {
         if (SkillUtils.shouldCancelTeleportation(entity)) {
            entity.sendSystemMessage(Component.translatable("tensura.skill.spatial_blockade").withStyle(ChatFormatting.RED));
         } else {
            FortunaConfig.FortunaSettings settings = config();
            double range = Math.max(1.0, settings.teleportRange);
            LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, range, false);
            Vec3 desired;
            if (target != null && target.isAlive()) {
               if (SkillUtils.shouldCancelTeleportation(target)) {
                  entity.sendSystemMessage(Component.translatable("tensura.skill.spatial_blockade").withStyle(ChatFormatting.RED));
                  return;
               }

               Vec3 look = target.getLookAngle();
               Vec3 horizontal = new Vec3(look.x, 0.0, look.z);
               if (horizontal.lengthSqr() < 1.0E-6) {
                  horizontal = new Vec3(0.0, 0.0, 1.0);
               }

               desired = target.position().subtract(horizontal.normalize().scale(Math.max(0.5, settings.teleportBehindDistance)));
            } else {
               BlockHitResult hit = ObjectSelectionHelper.getPlayerPOVHitResult(entity.level(), entity, Fluid.NONE, range);
               if (hit.getType() == Type.BLOCK) {
                  BlockPos destinationBlock = hit.getBlockPos().relative(hit.getDirection());
                  if (entity.level().getBlockState(destinationBlock).is(TensuraBlockTags.SKILL_NOT_TELEPORTABLE)) {
                     entity.sendSystemMessage(Component.translatable("foxablazeultimate.skill.fortuna.teleport.blocked").withStyle(ChatFormatting.RED));
                     return;
                  }

                  desired = new Vec3(destinationBlock.getX() + 0.5, destinationBlock.getY(), destinationBlock.getZ() + 0.5);
               } else {
                  desired = entity.getEyePosition().add(entity.getLookAngle().scale(range));
               }
            }

            if (entity.position().distanceTo(desired) > range + 1.0) {
               desired = entity.position().add(entity.position().vectorTo(desired).normalize().scale(range));
            }

            Vec3 destination = findSafeDestination(entity, desired);
            if (destination == null) {
               entity.sendSystemMessage(Component.translatable("foxablazeultimate.skill.fortuna.teleport.no_safe_destination").withStyle(ChatFormatting.RED));
               entity.level()
                  .playSound(
                     null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 1.0F, 1.0F
                  );
            } else {
               ISpatialMovement.warp(entity, destination.x, destination.y, destination.z);
               entity.hasImpulse = true;
               instance.addMasteryPoint(entity);
               instance.setCoolDown(Math.max(0, settings.teleportCooldownSeconds), mode);
            }
         }
      }
   }

   private static Vec3 findSafeDestination(LivingEntity entity, Vec3 desired) {
      Level level = entity.level();

      for (int yOffset : SAFE_VERTICAL_OFFSETS) {
         for (int[] horizontal : SAFE_HORIZONTAL_OFFSETS) {
            Vec3 candidate = desired.add(horizontal[0], yOffset, horizontal[1]);
            BlockPos feet = BlockPos.containing(candidate);
            BlockPos groundPos = feet.below();
            BlockState feetState = level.getBlockState(feet);
            BlockState headState = level.getBlockState(feet.above());
            BlockState groundState = level.getBlockState(groundPos);
            if (!feetState.is(TensuraBlockTags.SKILL_NOT_TELEPORTABLE)
               && !headState.is(TensuraBlockTags.SKILL_NOT_TELEPORTABLE)
               && !groundState.is(TensuraBlockTags.SKILL_NOT_TELEPORTABLE)
               && groundState.isFaceSturdy(level, groundPos, Direction.UP)
               && level.getFluidState(feet).isEmpty()
               && level.getWorldBorder().isWithinBounds(feet)) {
               Vec3 movement = candidate.subtract(entity.position());
               AABB destinationBox = entity.getBoundingBox().move(movement);
               if (level.noCollision(entity, destinationBox) && !level.containsAnyLiquid(destinationBox)) {
                  return candidate;
               }
            }
         }
      }

      return null;
   }

   private void toggleBarrier(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (isBarrierActive(instance)) {
         this.closeBarrier(instance, entity, true, true);
      } else if (!instance.onCoolDown(mode)) {
         double required = Math.max(0.0, config().barrierMagiculeCostPerSecond);
         if (required > 0.0 && TensuraStorages.getExistenceFrom(entity).getMagicule() < required) {
            entity.sendSystemMessage(Component.translatable("foxablazeultimate.skill.fortuna.barrier.insufficient_magicule").withStyle(ChatFormatting.RED));
         } else {
            CompoundTag tag = instance.getOrCreateTag();
            tag.putBoolean("FortunaBarrierActive", true);
            tag.remove("FortunaBarrierDamageTypes");
            instance.markDirty();
            instance.addMasteryPoint(entity);
            entity.sendSystemMessage(Component.translatable("foxablazeultimate.skill.fortuna.barrier.enabled").withStyle(ChatFormatting.AQUA));
            entity.level()
               .playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.DEFENCE_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F
               );
         }
      }
   }

   private void toggleSacredHakiCoat(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT))) {
         entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT));
         entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ILLUSIONER_MIRROR_MOVE, SoundSource.PLAYERS, 1.0F, 1.0F);
      } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         entity.addEffect(
            new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT), SacredHakiSkill.CONFIG.coatDuration, 0, false, false, false)
         );
         entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ILLUSIONER_MIRROR_MOVE, SoundSource.PLAYERS, 1.0F, 1.0F);
      }
   }

   private void closeBarrier(ManasSkillInstance instance, LivingEntity entity, boolean applyCooldown, boolean showMessage) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.putBoolean("FortunaBarrierActive", false);
      tag.remove("FortunaBarrierDamageTypes");
      if (applyCooldown) {
         instance.setCoolDown(Math.max(0, config().barrierCooldownSeconds), 1);
      }

      instance.markDirty();
      if (showMessage) {
         entity.sendSystemMessage(Component.translatable("foxablazeultimate.skill.fortuna.barrier.disabled").withStyle(ChatFormatting.GRAY));
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_UNCAST.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   private static boolean isBarrierActive(ManasSkillInstance instance) {
      CompoundTag tag = instance.getTag();
      return tag != null && tag.getBoolean("FortunaBarrierActive");
   }

   private static void clearBarrierState(ManasSkillInstance instance) {
      CompoundTag tag = instance.getTag();
      if (tag != null) {
         tag.remove("FortunaBarrierActive");
         tag.remove("FortunaBarrierDamageTypes");
         instance.markDirty();
      }
   }

   private boolean summonSacredHaki(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      double scale = instance.getTag() != null && instance.getTag().contains("scale") ? instance.getTag().getDouble("scale") : 0.0;
      double multiplier = scale == 0.0 ? 1.0 : Math.min(scale, 1.0);
      double ownerEP = EnergyHelper.getMaxEP(entity) * multiplier;
      HakiField haki = HakiField.getHaki(
         (EntityType)MiscEntityTypes.SACRED_HAKI_FIELD.get(),
         HakiVariant.SACRED,
         HakiSkill.CONFIG.hakiRadius,
         SacredHakiSkill.CONFIG.epDifferenceMultiplier,
         HakiSkill.CONFIG.fearDuration,
         entity.position().add(0.0, entity.getBbHeight() / 2.0F, 0.0),
         entity,
         instance,
         this,
         mode
      );
      if (heldTicks == 1) {
         if (haki != null) {
            haki.setEp(ownerEP);
            haki.setDamage(SacredHakiSkill.CONFIG.healHP);
         }

         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.HAKI_START.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      } else {
         if (haki == null) {
            return false;
         }

         if (heldTicks >= 40) {
            entity.level()
               .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.HAKI_LOOP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
         }
      }

      return true;
   }

   private static void applyPassiveAttributes(LivingEntity entity) {
      FortunaConfig.FortunaSettings settings = config();
      addPassiveModifier(entity, TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, settings.futurePredictionDodgeChance);
      addPassiveModifier(entity, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, settings.futurePredictionDodgeChance);
      addPassiveModifier(entity, Attributes.MOVEMENT_SPEED, settings.thoughtMovementSpeed);
      addPassiveModifier(entity, Attributes.ATTACK_SPEED, settings.thoughtAttackSpeed);
      addPassiveModifier(entity, TensuraAttributes.CHANT_SPEED, settings.thoughtChantSpeed);
      addPassiveModifier(entity, TensuraAttributes.DODGE_INVULNERABILITY, settings.thoughtDodgeInvulnerability);
      addPassiveModifier(entity, TensuraAttributes.PRESENCE_SENSE, settings.universalPresenceSense);
      addPassiveModifier(entity, TensuraAttributes.PRESENCE_SENSE_RADIUS, settings.universalPresenceRadius);
      AttributeHelper.addPermanentAttributeIfHigher(
         entity, TensuraAttributes.PRESENCE_SENSE_RADIUS, SenseSoundwaveSkill.SOUND_SENSE, settings.universalSoundSense, Operation.ADD_VALUE
      );
      addPassiveModifier(entity, TensuraAttributes.HEAT_SENSE_RADIUS, settings.universalHeatRadius);
   }

   private static void removePassiveAttributes(LivingEntity entity) {
      removePassiveModifier(entity, TensuraAttributes.AUTO_MELEE_DODGE_CHANCE);
      removePassiveModifier(entity, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE);
      removePassiveModifier(entity, Attributes.MOVEMENT_SPEED);
      removePassiveModifier(entity, Attributes.ATTACK_SPEED);
      removePassiveModifier(entity, TensuraAttributes.CHANT_SPEED);
      removePassiveModifier(entity, TensuraAttributes.DODGE_INVULNERABILITY);
      removePassiveModifier(entity, TensuraAttributes.PRESENCE_SENSE);
      removePassiveModifier(entity, TensuraAttributes.PRESENCE_SENSE_RADIUS);
      AttributeHelper.removeAttributeIfCorrect(entity, TensuraAttributes.PRESENCE_SENSE_RADIUS, SenseSoundwaveSkill.SOUND_SENSE, config().universalSoundSense);
      removePassiveModifier(entity, TensuraAttributes.HEAT_SENSE_RADIUS);
   }

   private static void addPassiveModifier(LivingEntity entity, Holder<Attribute> attribute, double amount) {
      AttributeInstance instance = entity.getAttribute(attribute);
      if (instance != null) {
         instance.addOrReplacePermanentModifier(new AttributeModifier(PASSIVE_MODIFIER, amount, Operation.ADD_VALUE));
      }
   }

   private static void removePassiveModifier(LivingEntity entity, Holder<Attribute> attribute) {
      AttributeInstance instance = entity.getAttribute(attribute);
      if (instance != null) {
         instance.removeModifier(PASSIVE_MODIFIER);
      }
   }

   private void reconcileWarpShotModifier(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance attribute = entity.getAttribute(TensuraAttributes.WARP_SHOT);
      if (attribute != null) {
         boolean spatialDominationWarpShot = attribute.getModifier(SpatialDominationSkill.SPATIAL_DOMINATION) != null;
         boolean enabled = !instance.isTemporarySkill() && this.isInSlot(entity, instance) && !spatialDominationWarpShot && config().warpShotStrength > 0.0;
         if (!enabled) {
            attribute.removeModifier(FORTUNA_WARP_SHOT);
         } else {
            attribute.addOrReplacePermanentModifier(new AttributeModifier(FORTUNA_WARP_SHOT, config().warpShotStrength, Operation.ADD_VALUE));
         }
      }
   }

   private static void removeWarpShotModifier(LivingEntity entity) {
      AttributeInstance attribute = entity.getAttribute(TensuraAttributes.WARP_SHOT);
      if (attribute != null) {
         attribute.removeModifier(FORTUNA_WARP_SHOT);
      }
   }

   public static boolean hasFortunaWarpShot(LivingEntity entity) {
      AttributeInstance attribute = entity.getAttribute(TensuraAttributes.WARP_SHOT);
      return attribute != null && attribute.getModifier(FORTUNA_WARP_SHOT) != null && attribute.getModifier(SpatialDominationSkill.SPATIAL_DOMINATION) == null;
   }

   public static double getWarpShotMagiculeCost(LivingEntity entity, double fallback) {
      return hasFortunaWarpShot(entity) ? Math.max(0.0, config().warpShotMagiculeCost) : fallback;
   }

   private static boolean isSpatialDominationToggled(LivingEntity entity) {
      return SkillUtils.isSkillToggled(entity, (ManasSkill)ExtraSkills.SPATIAL_DOMINATION.get());
   }

   private void claimUniqueOwnership(ServerPlayer player) {
      if (player.level().getGameRules().getBoolean(FoxAblazeGameRules.FORTUNA_TRULY_UNIQUE)) {
         ResourceLocation skillId = this.getRegistryName();
         ServerLevel overworld = overworldOf(player);
         if (skillId != null && overworld != null) {
            ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
            UUID owner = unique.getOwner(skillId);
            if (owner == null || owner.equals(player.getUUID())) {
               unique.addSkill(skillId, player.getUUID());
               unique.markDirty();
            }
         }
      }
   }

   private void releaseUniqueOwnership(ServerPlayer player) {
      ResourceLocation skillId = this.getRegistryName();
      ServerLevel overworld = overworldOf(player);
      if (skillId != null && overworld != null) {
         ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
         if (player.getUUID().equals(unique.getOwner(skillId))) {
            unique.removeSkill(skillId);
            unique.markDirty();
         }
      }
   }

   private static ServerLevel overworldOf(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      return server == null ? null : server.overworld();
   }

   private static int sanitizeMode(int mode) {
      return Math.floorMod(mode, 4);
   }

   private static String formatNumber(double value) {
      return String.format(Locale.ROOT, "%,.1f", value);
   }
}
