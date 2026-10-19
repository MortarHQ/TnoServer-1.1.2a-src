package com.github.wal_bos.moreskills.handler.michael;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.MichaelSegvaVfxPayload;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class MichaelSegvaSealing implements MichaelAttack {
   private static final String ROOT = "MichaelSealMarks";
   private static final String MARKS = "Marks";
   private static final String EXPIRE = "Expire";
   private static final String MARK_OWNER = "Owner";
   private static final String OWNER_ACTIVE_EXPIRE = "MichaelSegvaOwnerActiveExpire";
   private static final String WATCH_TARGET = "MichaelWatchedSealTarget";
   private static final String WATCH_TICKS = "MichaelWatchedSealTicks";
   private static final String WATCH_LAST_TICK = "MichaelWatchedSealLastTick";
   private static final String ABILITY_LOCK_EXPIRE = "MichaelAbilityLockExpire";
   private static final int REQUIRED_MARKS = 3;
   private static final int GAZE_TICKS = 100;
   private static final int APPLIED_COOLDOWN_TICKS = 2600;
   private static final int SEGVA_MODE = 1;
   private static final Map<UUID, MichaelSegvaSealing.MarkState> MARKED_TARGETS = new ConcurrentHashMap<>();
   private static final Map<UUID, MichaelSegvaSealing.LockState> LOCKED_TARGETS = new ConcurrentHashMap<>();

   @Override
   public String modeId() {
      return "michael_angel_of_locks.segva";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return ((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.segvaCost.get()).intValue();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (!MichaelActions.isHoldingAnyKey(entity)) {
            MichaelActions.fail(entity, "Michael requires the Key of Sealing.");
         } else if (instance.getCoolDown(mode) <= 0) {
            Entity selected = ObjectSelectionHelper.getTargetingEntity(
               entity, (Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.segvaRange.get(), false, true
            );
            if (!(selected instanceof LivingEntity target && MichaelActions.validEnemy(entity, target))) {
               instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.segvaMissCooldownTicks.get(), mode);
               MichaelActions.fail(entity, "No valid lock in sight.");
            } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               int duration = (Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.sealMarkDurationTicks.get();
               int marks = addMark(entity, target, duration);
               target.addEffect(
                  new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE), duration, 0, true, false, true), entity
               );
               applySpeedLock(target);
               MichaelSegvaVfxPayload.sendMark(entity, target, marks, duration);
               if (entity instanceof Player player) {
                  Component message = marks >= 3
                     ? Component.literal("Seal Marks: 3/3  Look directly at the target").withStyle(ChatFormatting.GOLD)
                     : Component.literal("Seal Marks: " + marks + "/3").withStyle(ChatFormatting.GOLD);
                  player.displayClientMessage(message, true);
               }

               if (target instanceof Player player) {
                  player.displayClientMessage(Component.literal("Sealing Marks: " + marks + "/3").withStyle(ChatFormatting.LIGHT_PURPLE), true);
               }

               instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.segvaHitCooldownTicks.get(), mode);
               entity.swing(InteractionHand.MAIN_HAND, true);
               instance.addMasteryPoint(entity);
            }
         }
      }
   }

   public static int addMark(LivingEntity owner, LivingEntity target, int duration) {
      long now = target.level().getGameTime();
      long expire = now + duration;
      CompoundTag existing = target.getPersistentData().getCompound("MichaelSealMarks");
      boolean sameOwner = owner.getUUID().toString().equals(existing.getString("Owner"));
      boolean active = !existing.isEmpty() && now <= existing.getLong("Expire");
      CompoundTag tag = sameOwner && active ? existing : new CompoundTag();
      int marks = Math.min(3, tag.getInt("Marks") + 1);
      tag.putInt("Marks", marks);
      tag.putLong("Expire", expire);
      tag.putString("Owner", owner.getUUID().toString());
      target.getPersistentData().put("MichaelSealMarks", tag);
      CompoundTag ownerData = owner.getPersistentData();
      ownerData.putLong("MichaelSegvaOwnerActiveExpire", Math.max(ownerData.getLong("MichaelSegvaOwnerActiveExpire"), expire));
      MARKED_TARGETS.put(target.getUUID(), new MichaelSegvaSealing.MarkState(owner.getUUID(), target.level().dimension(), expire));
      resetWatcher(owner);
      return marks;
   }

   public static int getMarks(LivingEntity target) {
      CompoundTag tag = target.getPersistentData().getCompound("MichaelSealMarks");
      return !tag.isEmpty() && target.level().getGameTime() <= tag.getLong("Expire") ? Math.min(3, Math.max(0, tag.getInt("Marks"))) : 0;
   }

   public static int getMarks(LivingEntity owner, LivingEntity target) {
      CompoundTag tag = target.getPersistentData().getCompound("MichaelSealMarks");
      if (tag.isEmpty() || target.level().getGameTime() > tag.getLong("Expire")) {
         return 0;
      } else {
         return !owner.getUUID().toString().equals(tag.getString("Owner")) ? 0 : Math.min(3, Math.max(0, tag.getInt("Marks")));
      }
   }

   public static boolean hasMarks(LivingEntity target) {
      return getMarks(target) > 0;
   }

   public static boolean hasOwnerState(LivingEntity owner) {
      CompoundTag data = owner.getPersistentData();
      long expire = data.getLong("MichaelSegvaOwnerActiveExpire");
      if (expire >= owner.level().getGameTime()) {
         return true;
      }

      data.remove("MichaelSegvaOwnerActiveExpire");
      resetWatcher(owner);
      return false;
   }

   public static void tickMarks(LivingEntity owner) {
      if (!hasOwnerState(owner)) {
         resetWatcher(owner);
      }
   }

   public static void tickWatcher(LivingEntity owner) {
      if (!owner.level().isClientSide()) {
         long now = owner.level().getGameTime();
         CompoundTag data = owner.getPersistentData();
         if (data.getLong("MichaelWatchedSealLastTick") != now) {
            data.putLong("MichaelWatchedSealLastTick", now);
            if (hasOwnerState(owner)) {
               LivingEntity target = findGazedMarkedTarget(owner);
               if (target == null) {
                  resetWatcher(owner);
               } else {
                  String id = target.getUUID().toString();
                  int ticks = id.equals(data.getString("MichaelWatchedSealTarget")) ? data.getInt("MichaelWatchedSealTicks") + 1 : 1;
                  data.putString("MichaelWatchedSealTarget", id);
                  data.putInt("MichaelWatchedSealTicks", ticks);
                  if (owner instanceof Player player) {
                     int seconds = Math.min(5, Math.max(1, (ticks + 19) / 20));
                     player.displayClientMessage(
                        Component.translatable("tensura.skill.time_held.max", new Object[]{seconds, 5}).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)),
                        true
                     );
                  }

                  if (ticks >= 100) {
                     completeSeal(owner, target);
                     resetWatcher(owner);
                  }
               }
            }
         }
      }
   }

   public static void tick(ServerLevel level) {
      long now = level.getGameTime();
      Set<UUID> owners = new HashSet<>();

      for (Entry<UUID, MichaelSegvaSealing.MarkState> entry : MARKED_TARGETS.entrySet()) {
         MichaelSegvaSealing.MarkState state = entry.getValue();
         if (state.dimension().equals(level.dimension())) {
            if (level.getEntity(entry.getKey()) instanceof LivingEntity target) {
               CompoundTag tag = target.getPersistentData().getCompound("MichaelSealMarks");
               boolean ownerMatches = state.owner().toString().equals(tag.getString("Owner"));
               if (target.isAlive() && now <= state.expire() && !tag.isEmpty() && ownerMatches) {
                  owners.add(state.owner());
               } else {
                  clearMarks(target, state.owner());
               }
            } else if (now > state.expire()) {
               MARKED_TARGETS.remove(entry.getKey(), state);
            }
         }
      }

      for (UUID ownerId : owners) {
         if (level.getEntity(ownerId) instanceof LivingEntity owner && owner.isAlive()) {
            tickWatcher(owner);
         }
      }

      for (Entry<UUID, MichaelSegvaSealing.LockState> entry : LOCKED_TARGETS.entrySet()) {
         MichaelSegvaSealing.LockState state = entry.getValue();
         if (state.dimension().equals(level.dimension())) {
            if (level.getEntity(entry.getKey()) instanceof LivingEntity target) {
               if (target.isAlive() && now < state.expire()) {
                  enforceAbilityShutdown(target);
               } else {
                  target.getPersistentData().remove("MichaelAbilityLockExpire");
                  LOCKED_TARGETS.remove(entry.getKey(), state);
               }
            } else if (now >= state.expire()) {
               LOCKED_TARGETS.remove(entry.getKey(), state);
            }
         }
      }
   }

   public static boolean isAbilityBlocked(LivingEntity entity, ManasSkillInstance instance) {
      return instance != null && isAbilityLockActive(entity);
   }

   public static boolean isAbilityLockActive(LivingEntity entity) {
      CompoundTag data = entity.getPersistentData();
      long expire = data.getLong("MichaelAbilityLockExpire");
      if (expire > entity.level().getGameTime()) {
         return true;
      }

      data.remove("MichaelAbilityLockExpire");
      return false;
   }

   private static LivingEntity findGazedMarkedTarget(LivingEntity owner) {
      if (owner.level() instanceof ServerLevel level) {
         double range = (Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.segvaRange.get();
         double bestDistance = range * range;
         LivingEntity best = null;

         for (Entry<UUID, MichaelSegvaSealing.MarkState> entry : MARKED_TARGETS.entrySet()) {
            MichaelSegvaSealing.MarkState state = entry.getValue();
            if (state.owner().equals(owner.getUUID())
               && state.dimension().equals(level.dimension())
               && level.getEntity(entry.getKey()) instanceof LivingEntity target
               && MichaelActions.validEnemy(owner, target)
               && getMarks(owner, target) >= 3) {
               double distance = owner.getEyePosition().distanceToSqr(target.getEyePosition());
               if (!(distance > bestDistance) && isLookingAtLikeEnderman(owner, target)) {
                  bestDistance = distance;
                  best = target;
               }
            }
         }

         return best;
      } else {
         return null;
      }
   }

   private static boolean isLookingAtLikeEnderman(LivingEntity owner, LivingEntity target) {
      Vec3 look = owner.getViewVector(1.0F).normalize();
      Vec3 direction = new Vec3(target.getX() - owner.getX(), target.getEyeY() - owner.getEyeY(), target.getZ() - owner.getZ());
      double distance = direction.length();
      if (distance < 0.001) {
         return false;
      }

      direction = direction.normalize();
      double dot = look.dot(direction);
      return dot > 1.0 - 0.025 / distance && owner.hasLineOfSight(target);
   }

   private static void completeSeal(LivingEntity owner, LivingEntity target) {
      int duration = Math.max(1, (Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.segvaAbilityOffTicks.get());
      applyAbilityLock(owner, target, duration);
      clearMarks(target, owner.getUUID());
      MichaelSegvaVfxPayload.sendLock(owner, target, duration);
      SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)AllSkills.MICHAEL_ANGEL_OF_LOCKS.get()).ifPresent(instance -> {
         instance.setCoolDown(2600, 1);
         instance.markDirty();
      });
      if (owner instanceof Player player) {
         player.displayClientMessage(Component.literal("Seal complete. All abilities disabled for 5 seconds.").withStyle(ChatFormatting.GOLD), true);
      }

      if (target instanceof Player player) {
         player.displayClientMessage(Component.literal("All of your abilities have been sealed.").withStyle(ChatFormatting.RED), true);
      }
   }

   private static void applyAbilityLock(LivingEntity owner, LivingEntity target, int duration) {
      long expire = target.level().getGameTime() + duration;
      target.getPersistentData().putLong("MichaelAbilityLockExpire", expire);
      LOCKED_TARGETS.put(target.getUUID(), new MichaelSegvaSealing.LockState(target.level().dimension(), expire));
      disableAllAbilities(target, duration);
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), duration, 0, true, false, true), owner);
   }

   private static void disableAllAbilities(LivingEntity target, int duration) {
      Skills skills = SkillAPI.getSkillsFrom(target);
      boolean changed = false;

      for (ManasSkillInstance learned : List.copyOf(skills.getLearnedSkills())) {
         if (learned != null) {
            if (learned.isToggled()) {
               learned.setToggled(false);
               learned.onToggleOff(target);
               changed = true;
            }

            int modes = Math.max(1, learned.getModes());

            for (int mode = 0; mode < modes; mode++) {
               if (learned.getCoolDown(mode) < duration) {
                  learned.setCoolDown(duration, mode);
                  changed = true;
               }
            }

            learned.markDirty();
         }
      }

      if (changed) {
         skills.markDirty();
      }
   }

   private static void enforceAbilityShutdown(LivingEntity target) {
      Skills skills = SkillAPI.getSkillsFrom(target);
      boolean changed = false;

      for (ManasSkillInstance learned : List.copyOf(skills.getLearnedSkills())) {
         if (learned != null && learned.isToggled()) {
            learned.setToggled(false);
            learned.onToggleOff(target);
            learned.markDirty();
            changed = true;
         }
      }

      if (changed) {
         skills.markDirty();
      }
   }

   private static void clearMarks(LivingEntity target, UUID ownerId) {
      target.getPersistentData().remove("MichaelSealMarks");
      removeSpeedLock(target);
      MARKED_TARGETS.remove(target.getUUID());
      MichaelSegvaVfxPayload.sendClear(target);
      clearOwnerStateIfUnused(target, ownerId);
   }

   private static void clearOwnerStateIfUnused(LivingEntity source, UUID ownerId) {
      boolean stillActive = MARKED_TARGETS.values().stream().anyMatch(state -> state.owner().equals(ownerId));
      if (!stillActive && source.level() instanceof ServerLevel level) {
         if (level.getEntity(ownerId) instanceof LivingEntity owner) {
            owner.getPersistentData().remove("MichaelSegvaOwnerActiveExpire");
            resetWatcher(owner);
         }
      }
   }

   private static void resetWatcher(LivingEntity owner) {
      CompoundTag data = owner.getPersistentData();
      data.remove("MichaelWatchedSealTarget");
      data.putInt("MichaelWatchedSealTicks", 0);
   }

   private static void applySpeedLock(LivingEntity target) {
      AttributeInstance attribute = target.getAttribute(Attributes.MOVEMENT_SPEED);
      if (attribute != null) {
         attribute.removeModifier(MichaelActions.KEY_MARK);
         attribute.addTransientModifier(new AttributeModifier(MichaelActions.KEY_MARK, -0.1, Operation.ADD_MULTIPLIED_TOTAL));
      }
   }

   private static void removeSpeedLock(LivingEntity target) {
      AttributeInstance attribute = target.getAttribute(Attributes.MOVEMENT_SPEED);
      if (attribute != null) {
         attribute.removeModifier(MichaelActions.KEY_MARK);
      }
   }

   private record LockState(ResourceKey<Level> dimension, long expire) {
   }

   private record MarkState(UUID owner, ResourceKey<Level> dimension, long expire) {
   }
}
