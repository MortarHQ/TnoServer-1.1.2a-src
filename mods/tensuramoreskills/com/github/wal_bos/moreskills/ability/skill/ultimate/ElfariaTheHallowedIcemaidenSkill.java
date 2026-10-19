package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.AlbisVinaHandler;
import com.github.wal_bos.moreskills.handler.ElfariaTheHallowedIcemaidenHandler;
import com.github.wal_bos.moreskills.net.msg.ElfariaIcemaidenVfxPayload;
import com.github.wal_bos.moreskills.registry.entity.ModEntityTypes;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import com.github.wal_bos.moreskills.world.entity.ArsWeissCloneEntity;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ElfariaTheHallowedIcemaidenSkill extends Skill {
   public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "elfaria_the_hallowed_icemaiden");
   private static final ResourceLocation CHANT_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "elfaria_hallowed_icemaiden_chant_speed");
   private static final ResourceLocation PRESENCE_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "elfaria_hallowed_icemaiden_presence_sense");
   private static final ResourceLocation PRESENCE_RADIUS_ID = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "elfaria_hallowed_icemaiden_presence_radius"
   );
   public static final int MODE_ARS_WEISS = 0;
   public static final int MODE_MYRDAS_FRIDOLIETE = 1;
   public static final int MODE_STELLAS_NATEA = 2;
   public static final int MODE_FRUZEL_CARDENEIA = 3;
   public static final int MODE_GLACIA_LAST_ALBIS = 4;
   private static final int ARS_WEISS_COMMAND_COOLDOWN_FLOOR = 20;

   public ElfariaTheHallowedIcemaidenSkill() {
      super(SkillType.ULTIMATE);
   }

   private static MoreSkillsConfigs.ElfariaTheHallowedIcemaidenConfig cfg() {
      return MoreSkillsConfigs.GRAND.elfariaTheHallowedIcemaiden;
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/elfaria.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)cfg().acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return (Integer)cfg().acquirementMastery.get();
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return SkillObtainmentHelper.canAcquire(
         entity, (Double)cfg().acquirementMagiculeCost.get(), (String)cfg().requiredSkillId.get(), (Boolean)cfg().requiredSkillMustBeMastered.get()
      );
   }

   public int getMaxMastery() {
      return (Integer)cfg().maxMastery.get();
   }

   public int getModes(ManasSkillInstance instance) {
      return 5;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode <= 0 ? 4 : mode - 1;
      } else {
         return mode >= 4 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "elfaria_the_hallowed_icemaiden.ars_weiss";
         case 1 -> "elfaria_the_hallowed_icemaiden.myrdas_fridoliete";
         case 2 -> "elfaria_the_hallowed_icemaiden.stellas_natea";
         case 3 -> "elfaria_the_hallowed_icemaiden.fruzel_cardeneia";
         case 4 -> "elfaria_the_hallowed_icemaiden.glacia_last_albis";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled();
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> instance.isMastered(entity) ? (Double)cfg().arsWeissCostMastered.get() : (Double)cfg().arsWeissCost.get();
         case 1 -> instance.isMastered(entity) ? (Double)cfg().rivilleCostMastered.get() : (Double)cfg().rivilleCost.get();
         case 2 -> instance.isMastered(entity) ? (Double)cfg().stellasCostMastered.get() : (Double)cfg().stellasCost.get();
         case 3 -> instance.isMastered(entity) ? (Double)cfg().fruzelCardeneiaCostMastered.get() : (Double)cfg().fruzelCardeneiaCost.get();
         case 4 -> instance.isMastered(entity) ? (Double)cfg().glaciaLastAlbisCostMastered.get() : (Double)cfg().glaciaLastAlbisCost.get();
         default -> 0.0;
      };
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      this.addPassiveAttributes(entity, instance);
      ElfariaTheHallowedIcemaidenHandler.grantPackage(entity);
      if (entity.level() instanceof ServerLevel level && entity instanceof Player player) {
         level.playSound(null, player.blockPosition(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 0.9F, 1.2F);
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.elfaria_the_hallowed_icemaiden.toggle_on"), true);
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      removePassiveAttributes(entity);
      if (entity.level() instanceof ServerLevel level && entity instanceof Player player) {
         level.playSound(null, player.blockPosition(), (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), SoundSource.PLAYERS, 0.8F, 0.95F);
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.elfaria_the_hallowed_icemaiden.toggle_off"), true);
      }
   }

   public boolean onAbilityEquipped(
      Player player, Changeable<ManasSkillInstance> instance, Changeable<Integer> mode, Changeable<Integer> slot, Changeable<Integer> preset
   ) {
      ElfariaTheHallowedIcemaidenHandler.grantPackage(player);
      if (((ManasSkillInstance)instance.get()).isToggled()) {
         this.addPassiveAttributes(player, (ManasSkillInstance)instance.get());
      }

      return true;
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      removePassiveAttributes(entity);
      ElfariaTheHallowedIcemaidenHandler.clearPlayer(entity.getUUID());
      if (entity instanceof Player player) {
         ElfariaTheHallowedIcemaidenHandler.forceClearPossession(player);
         AlbisVinaHandler.removeAllClones(player);
         AlbisVinaHandler.clearPlayer(player.getUUID());
      }

      instance.setToggled(false);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof Player player) {
         if (mode == 0 && player instanceof ServerPlayer serverPlayer && ElfariaTheHallowedIcemaidenHandler.isPossessing(serverPlayer)) {
            ElfariaTheHallowedIcemaidenHandler.returnFromPossessedClone(serverPlayer, false);
            instance.setCoolDown(Math.max(20, (Integer)cfg().parallelBodyReturnCooldownTicks.get()), mode);
            instance.addMasteryPoint(entity);
            entity.swing(InteractionHand.MAIN_HAND, true);
         } else if (mode == 0) {
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               if (player instanceof ServerPlayer serverPlayer
                  && player.isShiftKeyDown()
                  && (Boolean)cfg().parallelBodyPossessionEnabled.get()
                  && ElfariaTheHallowedIcemaidenHandler.tryStartPossession(serverPlayer, instance)) {
                  instance.setCoolDown(Math.max(20, (Integer)cfg().parallelBodyPossessCooldownTicks.get()), mode);
               } else {
                  if (!AlbisVinaHandler.getClones(player.getUUID()).isEmpty()) {
                     AlbisVinaHandler.removeAllClones(player.getUUID());
                     if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.displayClientMessage(
                           Component.translatable("tensuramoreskills.skill.elfaria_the_hallowed_icemaiden.ars_weiss_dismiss"), true
                        );
                     }
                  } else {
                     this.summonClones(instance, player);
                  }

                  instance.setCoolDown(
                     instance.isMastered(entity) ? (Integer)cfg().arsWeissCooldownTicksMastered.get() : (Integer)cfg().arsWeissCooldownTicks.get(), mode
                  );
               }

               instance.addMasteryPoint(entity);
               entity.swing(InteractionHand.MAIN_HAND, true);
            }
         }
      }
   }

   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      return mode == 1 || mode == 2 || mode == 3 || mode == 4;
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode == 1) {
         if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            return false;
         }

         ElfariaTheHallowedIcemaidenHandler.holdMyrdasBarrier(entity, instance, heldTicks);
         if (heldTicks > 0 && heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0) {
            instance.addMasteryPoint(entity);
         }

         return true;
      } else if (mode == 2) {
         int cast = this.getCastTicks(instance, entity, mode);
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.skill.elfaria_the_hallowed_icemaiden.cast", new Object[]{Math.min(heldTicks, cast), cast}), true
            );
         }

         return true;
      } else if (mode == 3) {
         int cast = instance.isMastered(entity)
            ? Math.max(10, (Integer)cfg().fruzelCardeneiaRefreshTicks.get() / 2)
            : Math.max(20, (Integer)cfg().fruzelCardeneiaRefreshTicks.get());
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.skill.elfaria_the_hallowed_icemaiden.cast", new Object[]{Math.min(heldTicks, cast), cast}), true
            );
         }

         return true;
      } else if (mode == 4) {
         int cast = instance.isMastered(entity) ? 25 : 40;
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.skill.elfaria_the_hallowed_icemaiden.cast", new Object[]{Math.min(heldTicks, cast), cast}), true
            );
         }

         return true;
      } else {
         return false;
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 1) {
         ElfariaTheHallowedIcemaidenHandler.stopMyrdasBarrier(entity);
         if (heldTicks > 5) {
            instance.setCoolDown(
               instance.isMastered(entity) ? (Integer)cfg().rivilleCooldownTicksMastered.get() : (Integer)cfg().rivilleCooldownTicks.get(), mode
            );
            instance.addMasteryPoint(entity);
            entity.swing(InteractionHand.MAIN_HAND, true);
         }
      } else if (mode == 2) {
         int cast = this.getCastTicks(instance, entity, mode);
         if (heldTicks >= cast && !EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            this.releaseStellas(instance, entity);
            instance.setCoolDown(
               instance.isMastered(entity) ? (Integer)cfg().stellasCooldownTicksMastered.get() : (Integer)cfg().stellasCooldownTicks.get(), mode
            );
            instance.addMasteryPoint(entity);
            entity.swing(InteractionHand.MAIN_HAND, true);
         }
      } else if (mode == 3) {
         int cast = instance.isMastered(entity)
            ? Math.max(10, (Integer)cfg().fruzelCardeneiaRefreshTicks.get() / 2)
            : Math.max(20, (Integer)cfg().fruzelCardeneiaRefreshTicks.get());
         if (heldTicks >= cast && !EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            this.releaseFruzelCardeneia(instance, entity);
            instance.setCoolDown(
               instance.isMastered(entity) ? (Integer)cfg().fruzelCardeneiaCooldownTicksMastered.get() : (Integer)cfg().fruzelCardeneiaCooldownTicks.get(),
               mode
            );
            instance.addMasteryPoint(entity);
            entity.swing(InteractionHand.MAIN_HAND, true);
         }
      } else {
         if (mode == 4) {
            int cast = instance.isMastered(entity) ? 25 : 40;
            if (heldTicks < cast || EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               return;
            }

            this.releaseGlaciaLastAlbis(instance, entity);
            instance.setCoolDown(
               instance.isMastered(entity) ? (Integer)cfg().glaciaLastAlbisCooldownTicksMastered.get() : (Integer)cfg().glaciaLastAlbisCooldownTicks.get(),
               mode
            );
            instance.addMasteryPoint(entity);
            entity.swing(InteractionHand.MAIN_HAND, true);
         }
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity entity, DamageSource source) {
      return !ElfariaTheHallowedIcemaidenHandler.tryMyrdasRevive(entity, instance, source);
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (instance.isToggled()) {
         if (entity.tickCount % Math.max(1, (Integer)cfg().passiveRefreshTicks.get()) == 0) {
            this.addPassiveAttributes(entity, instance);
            ElfariaTheHallowedIcemaidenHandler.grantPackage(entity);
         }

         if (entity.tickCount % Math.max(1, (Integer)cfg().masteryTickInterval.get()) == 0) {
            instance.addMasteryPoint(entity);
         }

         ElfariaTheHallowedIcemaidenHandler.tickIcemaidenAura(entity, instance);
      }
   }

   public static void applyDiamondDust(LivingEntity owner, LivingEntity target, boolean mastered) {
      if (target != null && target.isAlive() && !target.isAlliedTo(owner)) {
         int duration = mastered ? (Integer)cfg().diamondDustDurationTicksMastered.get() : (Integer)cfg().diamondDustDurationTicks.get();
         int amp = mastered ? (Integer)cfg().diamondDustAmplifierMastered.get() : (Integer)cfg().diamondDustAmplifier.get();
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FROST), duration, amp, true, false, true));
         target.addEffect(
            new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), duration, Math.max(0, amp), true, false, true)
         );
      }
   }

   public DamageSource damageSource(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return TensuraDamageTypes.getEntityDamageSource(entity.level(), TensuraDamageTypes.ICE_ELEMENTAL, entity);
   }

   private int getCastTicks(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return instance.isMastered(entity) ? (Integer)cfg().stellasCastTicksMastered.get() : (Integer)cfg().stellasCastTicks.get();
   }

   private void summonClones(ManasSkillInstance instance, Player player) {
      if (player.level() instanceof ServerLevel level) {
         AlbisVinaHandler.removeAllClones(player);
         int count = instance.isMastered(player) ? (Integer)cfg().arsWeissCloneCountMastered.get() : (Integer)cfg().arsWeissCloneCount.get();
         count = Math.min(count, (Integer)cfg().arsWeissHardCap.get());
         int duration = -1;

         for (int i = 0; i < count; i++) {
            Vec3 pos = this.findClonePosition(player, i, count);
            ArsWeissCloneEntity clone = new ArsWeissCloneEntity((EntityType<? extends ArsWeissCloneEntity>)ModEntityTypes.ARS_WEISS_CLONE.get(), level);
            clone.setOwnerData(player, duration);
            clone.setElfariaWorkerClone();
            clone.moveTo(pos.x, pos.y, pos.z, player.getYRot(), player.getXRot());
            clone.setYHeadRot(player.getYHeadRot());
            clone.setCustomName(player.getDisplayName());
            level.addFreshEntity(clone);
            AlbisVinaHandler.addClone(player.getUUID(), clone);
            TensuraParticleHelper.spawnServerParticles(
               level,
               (ParticleOptions)TensuraParticleTypes.SNOWFLAKE_EFFECT.get(),
               clone.getX(),
               clone.getY() + 1.0,
               clone.getZ(),
               12,
               0.2,
               0.35,
               0.2,
               0.04,
               true
            );
         }

         ElfariaIcemaidenVfxPayload.sendArsWeissBloom(level, player.position(), 4.6F, true);
         level.playSound(null, player.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.8F, 1.75F);
      }
   }

   private Vec3 findClonePosition(Player player, int index, int count) {
      Level level = player.level();
      double ring = index < Math.max(1, count / 2) ? 2.4 : 4.2;
      double base = (Math.PI * 2) / Math.max(1, count) * index + player.getYRot() * 0.017453292;

      for (int i = 0; i < 10; i++) {
         double angle = base + i * 0.62831853;
         double radius = ring + i % 3 * 0.8;
         Vec3 pos = player.position().add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
         BlockPos blockPos = BlockPos.containing(pos);
         if (level.getBlockState(blockPos).isAir() && level.getBlockState(blockPos.above()).isAir()) {
            return Vec3.atBottomCenterOf(blockPos);
         }
      }

      Vec3 side = player.getLookAngle().cross(new Vec3(0.0, 1.0, 0.0));
      if (side.lengthSqr() < 0.001) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      return player.position().add(side.normalize().scale(1.8));
   }

   private void releaseStellas(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 target = this.getLookTarget(entity, instance.isMastered(entity) ? (Double)cfg().stellasRangeMastered.get() : (Double)cfg().stellasRange.get());
         int base = instance.isMastered(entity) ? (Integer)cfg().stellasSpearCountMastered.get() : (Integer)cfg().stellasSpearCount.get();
         base = Math.min(base, (Integer)cfg().stellasMaxSpears.get());
         float damage = instance.isMastered(entity)
            ? ((Double)cfg().stellasSpearDamageMastered.get()).floatValue()
            : ((Double)cfg().stellasSpearDamage.get()).floatValue();
         float area = Math.max(6.0F, (float)Math.sqrt(base) * 0.8F);
         ElfariaIcemaidenVfxPayload.sendStellasCharge(level, target, area, base, true);

         for (int i = 0; i < base; i++) {
            double angle = i * 2.399963;
            double radius = Math.sqrt((double)i / Math.max(1, base)) * area;
            Vec3 impact = target.add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
            ElfariaTheHallowedIcemaidenHandler.damageStellasNatea(entity, instance, impact, damage, true);
            ElfariaTheHallowedIcemaidenHandler.queueSpear(entity.getUUID(), impact, 8 + i / 3, 0.0F, true);
         }

         entity.level().playSound(null, entity.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_WATER.get(), SoundSource.PLAYERS, 1.0F, 0.85F);
      }
   }

   private void releaseFruzelCardeneia(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         int duration = instance.isMastered(entity)
            ? (Integer)cfg().fruzelCardeneiaDurationTicksMastered.get()
            : (Integer)cfg().fruzelCardeneiaDurationTicks.get();
         double radius = instance.isMastered(entity) ? (Double)cfg().fruzelCardeneiaRadiusMastered.get() : (Double)cfg().fruzelCardeneiaRadius.get();
         Vec3 forward = new Vec3(entity.getLookAngle().x, 0.0, entity.getLookAngle().z);
         if (forward.lengthSqr() < 0.001) {
            forward = new Vec3(0.0, 0.0, 1.0);
         }

         forward = forward.normalize();
         Vec3 center = entity.position().subtract(forward.scale(Math.max(6.0, radius * 0.55)));
         ElfariaTheHallowedIcemaidenHandler.startCathedral(entity, instance, center, duration);
         level.playSound(null, entity.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_ICE.get(), SoundSource.PLAYERS, 1.15F, 0.75F);
      }
   }

   private void releaseGlaciaLastAlbis(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 target = this.getLookTarget(
            entity, instance.isMastered(entity) ? (Double)cfg().glaciaLastAlbisRangeMastered.get() : (Double)cfg().glaciaLastAlbisRange.get()
         );
         float damage = instance.isMastered(entity)
            ? ((Double)cfg().glaciaLastAlbisDamageMastered.get()).floatValue()
            : ((Double)cfg().glaciaLastAlbisDamage.get()).floatValue();
         double radius = instance.isMastered(entity) ? (Double)cfg().glaciaLastAlbisRadiusMastered.get() : (Double)cfg().glaciaLastAlbisRadius.get();
         ElfariaTheHallowedIcemaidenHandler.damageGlaciaLastAlbis(entity, instance, target, damage, radius, true, instance.isMastered(entity));
         ElfariaIcemaidenVfxPayload.sendPhoenixDive(level, entity.getEyePosition().add(0.0, 3.0, 0.0), target, (float)radius, true);
         ElfariaIcemaidenVfxPayload.sendGlaciaBurst(level, target, (float)radius, true);
         level.playSound(null, BlockPos.containing(target), SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS, 0.9F, 1.55F);
         level.playSound(null, BlockPos.containing(target), (SoundEvent)TensuraSoundEvents.CAST_ICE.get(), SoundSource.PLAYERS, 1.4F, 0.55F);
      }
   }

   private Vec3 getLookTarget(LivingEntity entity, double range) {
      Entity target = ObjectSelectionHelper.getTargetingEntity(entity, range, false, true);
      if (target != null) {
         return target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
      }

      Vec3 start = entity.getEyePosition();
      Vec3 end = start.add(entity.getLookAngle().scale(range));
      BlockHitResult result = entity.level().clip(new ClipContext(start, end, Block.COLLIDER, Fluid.NONE, entity));
      return result.getLocation();
   }

   public static double getWaterIceMultiplier(LivingEntity entity, ManasSkillInstance instance) {
      double base = instance.isMastered(entity) ? (Double)cfg().waterIceDamageMultiplierMastered.get() : (Double)cfg().waterIceDamageMultiplier.get();
      if (ElfariaTheHallowedIcemaidenHandler.isInMyrdasMode(entity)) {
         base *= instance.isMastered(entity) ? (Double)cfg().myrdasWaterIceDamageMultiplierMastered.get() : (Double)cfg().myrdasWaterIceDamageMultiplier.get();
      }

      return base;
   }

   public static double getAllMagicMultiplier(LivingEntity entity, ManasSkillInstance instance) {
      double base = instance.isMastered(entity) ? (Double)cfg().allMagicDamageMultiplierMastered.get() : (Double)cfg().allMagicDamageMultiplier.get();
      if (ElfariaTheHallowedIcemaidenHandler.isInMyrdasMode(entity)) {
         base *= instance.isMastered(entity) ? (Double)cfg().myrdasAllMagicDamageMultiplierMastered.get() : (Double)cfg().myrdasAllMagicDamageMultiplier.get();
      }

      return base;
   }

   private void addPassiveAttributes(LivingEntity entity, ManasSkillInstance instance) {
      removePassiveAttributes(entity);
      double chant = instance.isMastered(entity) ? (Double)cfg().chantSpeedMastered.get() : (Double)cfg().chantSpeed.get();
      if (ElfariaTheHallowedIcemaidenHandler.isInMyrdasMode(entity)) {
         chant += instance.isMastered(entity) ? (Double)cfg().myrdasChantSpeedBonusMastered.get() : (Double)cfg().myrdasChantSpeedBonus.get();
      }

      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.CHANT_SPEED, CHANT_ID, chant, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.PRESENCE_SENSE, PRESENCE_ID, (Double)cfg().presenceSense.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(
         entity, TensuraAttributes.PRESENCE_SENSE_RADIUS, PRESENCE_RADIUS_ID, (Double)cfg().presenceSenseRadius.get(), Operation.ADD_VALUE
      );
   }

   public static void refreshPassiveAttributes(LivingEntity entity, ManasSkillInstance instance) {
      if (instance.getSkill() instanceof ElfariaTheHallowedIcemaidenSkill skill && instance.isToggled()) {
         skill.addPassiveAttributes(entity, instance);
      }
   }

   private static void removePassiveAttributes(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, TensuraAttributes.CHANT_SPEED, CHANT_ID);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.PRESENCE_SENSE, PRESENCE_ID);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.PRESENCE_SENSE_RADIUS, PRESENCE_RADIUS_ID);
   }

   public static void restoreExistenceResources(
      LivingEntity entity, double healthRatio, double minHealth, double magiculeRatio, double auraRatio, double spiritualRatio
   ) {
      entity.setHealth(Math.min(entity.getMaxHealth(), Math.max((float)minHealth, entity.getMaxHealth() * (float)healthRatio)));
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      existence.setMagicule(Math.max(existence.getMagicule(), EnergyHelper.getMaxMagicule(entity) * magiculeRatio));
      existence.setAura(Math.max(existence.getAura(), EnergyHelper.getMaxAura(entity) * auraRatio));
      existence.setSpiritualHealth(Math.max(existence.getSpiritualHealth(), entity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH) * spiritualRatio));
      existence.markDirty();
   }
}
