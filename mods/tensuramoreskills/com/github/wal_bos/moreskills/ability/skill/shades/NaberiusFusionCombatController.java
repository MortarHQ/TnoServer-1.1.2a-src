package com.github.wal_bos.moreskills.ability.skill.shades;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class NaberiusFusionCombatController {
   private static final String TAG_AI = "tms_naberius_fusion_ai";
   private static final String TAG_GLOBAL_CD = "global_cd";
   private static final String TAG_CAST_INDEX = "cast_index";
   private static final String TAG_FAILS = "fails";
   private static final String TAG_BANNED = "banned";
   private static final String TAG_SKILL_CDS = "skill_cds";
   private static final String TAG_LAST_TARGET = "last_target";
   private static final String TAG_FATALS = "fatals";
   private static final int TICK_RATE = 5;
   private static final int GLOBAL_CAST_DELAY = 5;
   private static final int FAILED_SKILL_CD = 240;
   private static final int SUCCESS_SKILL_CD = 40;
   private static final int SOFT_FAIL_CD = 15;
   private static final int HARD_FAIL_LIMIT = 6;
   private static final int HARD_FATAL_LIMIT = 8;
   private static final int MAX_CAST_ATTEMPTS_PER_TICK = 24;
   private static final int MAX_SKILLS_SCANNED_PER_TICK = 64;
   private static final int MAX_MODES_PER_SKILL = 12;
   private static final double TARGET_RANGE = 96.0;
   private static final double EMERGENCY_MELEE_RANGE = 6.0;

   private NaberiusFusionCombatController() {
   }

   public static void tick(ServerPlayer owner, LivingEntity fusion) {
      try {
         tickSafe(owner, fusion);
      } catch (Throwable ignored) {
         try {
            CompoundTag ai = getAi(fusion);
            int fatals = ai.getInt("fatals") + 1;
            ai.putInt("fatals", fatals);
            fusion.getPersistentData().put("tms_naberius_fusion_ai", ai);
            if (fatals >= 8) {
               fusion.discard();
            }
         } catch (Throwable var5) {
         }
      }
   }

   private static void tickSafe(ServerPlayer owner, LivingEntity fusion) {
      if (fusion.level() instanceof ServerLevel level) {
         if (fusion.isAlive()) {
            if (fusion.tickCount % 5 == 0) {
               CompoundTag ai = getAi(fusion);
               tickDownCooldowns(ai);
               int globalCd = ai.getInt("global_cd");
               if (globalCd > 0) {
                  ai.putInt("global_cd", Math.max(0, globalCd - 5));
                  fusion.getPersistentData().put("tms_naberius_fusion_ai", ai);
               } else {
                  LivingEntity target = resolveTarget(owner, fusion, level, ai);
                  if (target != null && target.isAlive()) {
                     prepareCaster(fusion, target);
                     if (fusion.distanceTo(target) <= 6.0 && fusion.tickCount % 10 == 0) {
                        safeMelee(fusion, target);
                     }

                     boolean casted = tryCastActiveSkill(fusion, target, ai);
                     if (casted) {
                        ai.putInt("global_cd", 5);
                        ai.putInt("fatals", 0);
                     }

                     fusion.getPersistentData().put("tms_naberius_fusion_ai", ai);
                  } else {
                     fusion.getPersistentData().put("tms_naberius_fusion_ai", ai);
                  }
               }
            }
         }
      }
   }

   private static CompoundTag getAi(LivingEntity fusion) {
      CompoundTag data = fusion.getPersistentData();
      CompoundTag ai = data.getCompound("tms_naberius_fusion_ai");
      data.put("tms_naberius_fusion_ai", ai);
      return ai;
   }

   private static LivingEntity resolveTarget(ServerPlayer owner, LivingEntity fusion, ServerLevel level, CompoundTag ai) {
      LivingEntity target = null;
      if (fusion instanceof Mob mob) {
         target = mob.getTarget();
         if (target != null && isValidTarget(owner, fusion, target)) {
            ai.putUUID("last_target", target.getUUID());
            return target;
         }
      }

      if (ai.hasUUID("last_target") && level.getEntity(ai.getUUID("last_target")) instanceof LivingEntity living && isValidTarget(owner, fusion, living)) {
         target = living;
      }

      if (target == null) {
         LivingEntity hurtBy = owner.getLastHurtByMob();
         if (hurtBy != null && isValidTarget(owner, fusion, hurtBy)) {
            target = hurtBy;
         }
      }

      if (target == null) {
         LivingEntity ownerTarget = getOwnerLookTarget(owner, level);
         if (ownerTarget != null && isValidTarget(owner, fusion, ownerTarget)) {
            target = ownerTarget;
         }
      }

      if (target == null) {
         AABB box = fusion.getBoundingBox().inflate(96.0);
         List<LivingEntity> candidates = level.getEntitiesOfClass(LivingEntity.class, box, e -> isValidTarget(owner, fusion, e));
         candidates.sort(Comparator.comparingDouble(fusion::distanceToSqr));
         if (!candidates.isEmpty()) {
            target = candidates.get(0);
         }
      }

      if (target != null) {
         ai.putUUID("last_target", target.getUUID());
         if (fusion instanceof Mob mob) {
            try {
               mob.setTarget(target);
               mob.setLastHurtByMob(target);
               mob.getNavigation().moveTo(target, 1.45);
            } catch (Throwable var7) {
            }
         }
      }

      return target;
   }

   private static LivingEntity getOwnerLookTarget(ServerPlayer owner, ServerLevel level) {
      try {
         LivingEntity target = ObjectSelectionHelper.getTargetingEntity(owner, 96.0, false);
         if (target != null && target.isAlive()) {
            return target;
         }
      } catch (Throwable var17) {
      }

      try {
         Vec3 start = owner.getEyePosition();
         Vec3 look = owner.getLookAngle().normalize();
         Vec3 end = start.add(look.scale(96.0));
         AABB box = owner.getBoundingBox().expandTowards(look.scale(96.0)).inflate(5.0);
         LivingEntity best = null;
         double bestDist = Double.MAX_VALUE;

         for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, box, LivingEntity::isAlive)) {
            if (entity != owner) {
               Vec3 center = entity.position().add(0.0, entity.getBbHeight() * 0.5, 0.0);
               double dist = distancePointToSegment(center, start, end);
               double ownerDist = owner.distanceToSqr(entity);
               if (dist <= 5.0 && ownerDist < bestDist) {
                  best = entity;
                  bestDist = ownerDist;
               }
            }
         }

         return best;
      } catch (Throwable ignored) {
         return null;
      }
   }

   private static double distancePointToSegment(Vec3 point, Vec3 start, Vec3 end) {
      Vec3 line = end.subtract(start);
      double lenSq = line.lengthSqr();
      if (lenSq <= 1.0E-5) {
         return point.distanceTo(start);
      }

      double t = point.subtract(start).dot(line) / lenSq;
      t = Math.max(0.0, Math.min(1.0, t));
      Vec3 projection = start.add(line.scale(t));
      return point.distanceTo(projection);
   }

   private static boolean isValidTarget(ServerPlayer owner, LivingEntity fusion, LivingEntity target) {
      if (target == null) {
         return false;
      }

      if (!target.isAlive()) {
         return false;
      }

      if (target == owner) {
         return false;
      }

      if (target == fusion) {
         return false;
      }

      if (NaberiusSkill.isNaberiusSummon(target)) {
         return false;
      }

      if (!target.isAlliedTo(owner) && !owner.isAlliedTo(target)) {
         if (target.distanceTo(fusion) > 96.0) {
            return false;
         }

         try {
            if (TensuraStorages.getExistenceFrom(target).getSummoner() != null
               && owner.getUUID().equals(TensuraStorages.getExistenceFrom(target).getSummoner())) {
               return false;
            }
         } catch (Throwable var4) {
         }

         return true;
      } else {
         return false;
      }
   }

   private static void prepareCaster(LivingEntity fusion, LivingEntity target) {
      try {
         fusion.lookAt(Anchor.EYES, target.position().add(0.0, target.getBbHeight() * 0.55, 0.0));
         fusion.setYHeadRot(fusion.getYRot());
         fusion.setYBodyRot(fusion.getYRot());
      } catch (Throwable var5) {
      }

      if (fusion instanceof Mob mob) {
         try {
            mob.setTarget(target);
            mob.setLastHurtByMob(target);
            mob.getNavigation().moveTo(target, 1.45);
         } catch (Throwable var4) {
         }
      }
   }

   private static void safeMelee(LivingEntity fusion, LivingEntity target) {
      try {
         double base = fusion.getAttributeValue(Attributes.ATTACK_DAMAGE);
         float damage = (float)Math.max(12.0, base * 1.35);
         target.hurt(fusion.damageSources().mobAttack(fusion), damage);
         fusion.swing(InteractionHand.MAIN_HAND, true);
         fusion.level().playSound(null, fusion.blockPosition(), SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.HOSTILE, 1.0F, 0.65F);
      } catch (Throwable var5) {
      }
   }

   private static boolean tryCastActiveSkill(LivingEntity fusion, LivingEntity target, CompoundTag ai) {
      Skills skills;
      try {
         skills = SkillAPI.getSkillsFrom(fusion);
      } catch (Throwable ignored) {
         return false;
      }

      if (skills == null) {
         return false;
      }

      List<ManasSkillInstance> learned = new ArrayList<>();

      try {
         for (ManasSkillInstance instance : skills.getLearnedSkills()) {
            if (instance != null && instance.getSkill() != null && !(instance.getSkill() instanceof NaberiusSkill)) {
               learned.add(instance);
            }
         }
      } catch (Throwable ignored) {
         return false;
      }

      if (learned.isEmpty()) {
         return false;
      }

      int cursor = Math.max(0, ai.getInt("cast_index"));
      int scanned = 0;
      int attempts = 0;

      for (int offset = 0; offset < learned.size() && scanned < 64; offset++) {
         int index = (cursor + offset) % learned.size();
         ManasSkillInstance instance = learned.get(index);
         scanned++;
         ManasSkill skill = instance.getSkill();
         ResourceLocation id = getSkillId(instance, skill);
         if (id != null) {
            String key = id.toString();
            if (!isBanned(ai, key) && getSkillCd(ai, key) <= 0 && isCombatCandidate(instance, key)) {
               int modes = safeModes(skill, instance);
               if (modes <= 0) {
                  modes = 1;
               }

               modes = Math.min(modes, 12);

               for (int mode : buildModeOrder(modes, ai.getInt("cast_index"))) {
                  if (attempts >= 24) {
                     ai.putInt("cast_index", (index + 1) % learned.size());
                     return false;
                  }

                  attempts++;
                  if (canAttemptMode(fusion, instance, mode)) {
                     NaberiusFusionCombatController.CastResult result = safeCastAggressive(fusion, target, instance, mode);
                     if (result == NaberiusFusionCombatController.CastResult.SUCCESS) {
                        setSkillCd(ai, key, 40 + fusion.getRandom().nextInt(25));
                        ai.putInt("cast_index", (index + 1) % learned.size());
                        resetFails(ai, key);
                        return true;
                     }

                     if (result == NaberiusFusionCombatController.CastResult.CRASH) {
                        addFail(ai, key);
                        if (getFails(ai, key) >= 6) {
                           ban(ai, key);
                           setSkillCd(ai, key, 240);
                        } else {
                           setSkillCd(ai, key, 40);
                        }
                     } else {
                        setSkillCd(ai, key, 15);
                     }
                  }
               }
            }
         }
      }

      ai.putInt("cast_index", (cursor + scanned + 1) % Math.max(1, learned.size()));
      return false;
   }

   private static List<Integer> buildModeOrder(int modes, int seed) {
      List<Integer> out = new ArrayList<>();
      if (modes <= 0) {
         out.add(0);
         return out;
      }

      int first = Math.floorMod(seed, modes);
      out.add(first);

      for (int i = 0; i < modes; i++) {
         if (!out.contains(i)) {
            out.add(i);
         }
      }

      return out;
   }

   private static NaberiusFusionCombatController.CastResult safeCastAggressive(LivingEntity fusion, LivingEntity target, ManasSkillInstance instance, int mode) {
      try {
         if (target != null && target.isAlive()) {
            if (fusion.distanceTo(target) > 96.0) {
               return NaberiusFusionCombatController.CastResult.FAIL;
            } else {
               prepareCaster(fusion, target);
               if (isCooldownBlocked(instance, mode)) {
                  return NaberiusFusionCombatController.CastResult.FAIL;
               } else {
                  float beforeHealth = target.getHealth();
                  int beforeHurtTime = target.hurtTime;
                  int beforeCd = getCooldownSafe(instance, mode);
                  NaberiusFusionCombatController.CastResult press = tryPress(fusion, target, instance, mode);
                  if (press == NaberiusFusionCombatController.CastResult.CRASH) {
                     return NaberiusFusionCombatController.CastResult.CRASH;
                  } else if (looksSuccessful(target, beforeHealth, beforeHurtTime, beforeCd, instance, mode)) {
                     return NaberiusFusionCombatController.CastResult.SUCCESS;
                  } else {
                     NaberiusFusionCombatController.CastResult shortHold = tryHoldRelease(fusion, target, instance, mode, 10);
                     if (shortHold == NaberiusFusionCombatController.CastResult.CRASH) {
                        return NaberiusFusionCombatController.CastResult.CRASH;
                     } else if (looksSuccessful(target, beforeHealth, beforeHurtTime, beforeCd, instance, mode)) {
                        return NaberiusFusionCombatController.CastResult.SUCCESS;
                     } else {
                        NaberiusFusionCombatController.CastResult longHold = tryHoldRelease(fusion, target, instance, mode, 25);
                        if (longHold == NaberiusFusionCombatController.CastResult.CRASH) {
                           return NaberiusFusionCombatController.CastResult.CRASH;
                        } else {
                           return looksSuccessful(target, beforeHealth, beforeHurtTime, beforeCd, instance, mode)
                              ? NaberiusFusionCombatController.CastResult.SUCCESS
                              : NaberiusFusionCombatController.CastResult.FAIL;
                        }
                     }
                  }
               }
            }
         } else {
            return NaberiusFusionCombatController.CastResult.FAIL;
         }
      } catch (Throwable ignored) {
         return NaberiusFusionCombatController.CastResult.CRASH;
      }
   }

   private static NaberiusFusionCombatController.CastResult tryPress(LivingEntity fusion, LivingEntity target, ManasSkillInstance instance, int mode) {
      try {
         prepareCaster(fusion, target);
         fusion.swing(InteractionHand.MAIN_HAND, true);
         instance.onPressed(fusion, 0, mode);
         afterCast(fusion, instance);
         return NaberiusFusionCombatController.CastResult.SUCCESS;
      } catch (Throwable ignored) {
         return NaberiusFusionCombatController.CastResult.CRASH;
      }
   }

   private static NaberiusFusionCombatController.CastResult tryHoldRelease(
      LivingEntity fusion, LivingEntity target, ManasSkillInstance instance, int mode, int heldTicks
   ) {
      try {
         prepareCaster(fusion, target);
         boolean heldOk = false;

         for (int t = 1; t <= heldTicks; t += 5) {
            prepareCaster(fusion, target);

            try {
               if (instance.onHeld(fusion, t, mode)) {
                  heldOk = true;
               }
            } catch (Throwable crash) {
               return NaberiusFusionCombatController.CastResult.CRASH;
            }
         }

         if (!heldOk && heldTicks >= 10) {
            try {
               instance.onHeld(fusion, heldTicks, mode);
            } catch (Throwable crash) {
               return NaberiusFusionCombatController.CastResult.CRASH;
            }
         }

         prepareCaster(fusion, target);
         fusion.swing(InteractionHand.MAIN_HAND, true);

         try {
            instance.onRelease(fusion, heldTicks, 0, mode);
         } catch (Throwable crash) {
            return NaberiusFusionCombatController.CastResult.CRASH;
         }

         afterCast(fusion, instance);
         return NaberiusFusionCombatController.CastResult.SUCCESS;
      } catch (Throwable ignored) {
         return NaberiusFusionCombatController.CastResult.CRASH;
      }
   }

   private static void afterCast(LivingEntity fusion, ManasSkillInstance instance) {
      try {
         instance.addMasteryPoint(fusion);
      } catch (Throwable var6) {
      }

      try {
         instance.markDirty();
      } catch (Throwable var5) {
      }

      try {
         if (fusion.level() instanceof ServerLevel level) {
            level.playSound(null, fusion.blockPosition(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.HOSTILE, 1.2F, 0.75F);
         }
      } catch (Throwable var4) {
      }
   }

   private static boolean looksSuccessful(LivingEntity target, float beforeHealth, int beforeHurtTime, int beforeCd, ManasSkillInstance instance, int mode) {
      try {
         if (target.getHealth() < beforeHealth) {
            return true;
         }

         if (target.hurtTime > beforeHurtTime) {
            return true;
         }

         if (getCooldownSafe(instance, mode) > beforeCd) {
            return true;
         }

         if (instance.onCoolDown(mode)) {
            return true;
         }
      } catch (Throwable var7) {
      }

      return true;
   }

   private static boolean canAttemptMode(LivingEntity fusion, ManasSkillInstance instance, int mode) {
      if (isCooldownBlocked(instance, mode)) {
         return false;
      }

      try {
         if (EnergyHelper.isOutOfEnergy(fusion, instance, mode)) {
            return false;
         }
      } catch (Throwable var4) {
      }

      return true;
   }

   private static boolean isCooldownBlocked(ManasSkillInstance instance, int mode) {
      try {
         if (instance.getCoolDown(mode) > 0) {
            return true;
         }
      } catch (Throwable var4) {
      }

      try {
         return instance.onCoolDown(mode);
      } catch (Throwable ignored) {
         return false;
      }
   }

   private static int getCooldownSafe(ManasSkillInstance instance, int mode) {
      try {
         return instance.getCoolDown(mode);
      } catch (Throwable ignored) {
         return 0;
      }
   }

   private static boolean isCombatCandidate(ManasSkillInstance instance, String key) {
      String lower = key.toLowerCase();
      if (lower.contains("menu")) {
         return false;
      }

      if (lower.contains("archive")) {
         return false;
      }

      if (lower.contains("creation")) {
         return false;
      }

      if (lower.contains("craft")) {
         return false;
      }

      if (lower.contains("storage")) {
         return false;
      }

      if (lower.contains("recipe")) {
         return false;
      }

      if (lower.contains("config")) {
         return false;
      }

      try {
         if (instance.isTemporarySkill()) {
            return false;
         }
      } catch (Throwable var5) {
      }

      try {
         if (instance.getMastery() < 0.0) {
            return false;
         }
      } catch (Throwable var4) {
      }

      return true;
   }

   private static int safeModes(ManasSkill skill, ManasSkillInstance instance) {
      try {
         return skill.getModes(instance);
      } catch (Throwable ignored) {
         return 1;
      }
   }

   private static ResourceLocation getSkillId(ManasSkillInstance instance, ManasSkill skill) {
      try {
         ResourceLocation id = instance.getSkillId();
         if (id != null) {
            return id;
         }
      } catch (Throwable var5) {
      }

      try {
         ResourceLocation id = SkillAPI.getSkillRegistry().getId(skill);
         if (id != null) {
            return id;
         }
      } catch (Throwable var4) {
      }

      try {
         return skill.getRegistryName();
      } catch (Throwable ignored) {
         return null;
      }
   }

   private static void tickDownCooldowns(CompoundTag ai) {
      CompoundTag cds = ai.getCompound("skill_cds");

      for (String key : new ArrayList(cds.getAllKeys())) {
         int cd = cds.getInt(key);
         if (cd <= 5) {
            cds.remove(key);
         } else {
            cds.putInt(key, cd - 5);
         }
      }

      ai.put("skill_cds", cds);
   }

   private static int getSkillCd(CompoundTag ai, String key) {
      return ai.getCompound("skill_cds").getInt(key);
   }

   private static void setSkillCd(CompoundTag ai, String key, int cd) {
      CompoundTag cds = ai.getCompound("skill_cds");
      cds.putInt(key, Math.max(1, cd));
      ai.put("skill_cds", cds);
   }

   private static int getFails(CompoundTag ai, String key) {
      return ai.getCompound("fails").getInt(key);
   }

   private static void addFail(CompoundTag ai, String key) {
      CompoundTag fails = ai.getCompound("fails");
      fails.putInt(key, fails.getInt(key) + 1);
      ai.put("fails", fails);
   }

   private static void resetFails(CompoundTag ai, String key) {
      CompoundTag fails = ai.getCompound("fails");
      fails.remove(key);
      ai.put("fails", fails);
   }

   private static boolean isBanned(CompoundTag ai, String key) {
      return ai.getCompound("banned").getBoolean(key);
   }

   private static void ban(CompoundTag ai, String key) {
      CompoundTag banned = ai.getCompound("banned");
      banned.putBoolean(key, true);
      ai.put("banned", banned);
   }

   private enum CastResult {
      SUCCESS,
      FAIL,
      CRASH;
   }
}
