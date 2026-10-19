package com.github.wal_bos.moreskills.handler.asmoday;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;

public final class AsmodayActions {
   private static final List<AsmodayAttack> ATTACKS = List.of(
      new AsmodaySpaceEmbodiment(),
      new AsmodaySpatialCubeBarrage(),
      new AsmodayCubeJudgement(),
      new AsmodayPrisonRealm(),
      new AsmodayEndBlackHole(),
      new AsmodayAvatarBeam()
   );

   private AsmodayActions() {
   }

   public static int getModes(ManasSkillInstance instance) {
      return ATTACKS.size();
   }

   public static int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (ATTACKS.isEmpty()) {
         return -1;
      }

      AsmodayAttack current = getAttack(mode);
      if (current != null) {
         current.cleanup(entity);
      }

      if (reverse) {
         return mode <= 0 ? ATTACKS.size() - 1 : mode - 1;
      } else {
         return mode >= ATTACKS.size() - 1 ? 0 : mode + 1;
      }
   }

   public static String getModeId(ManasSkillInstance instance, int mode, String fallback) {
      AsmodayAttack attack = getAttack(mode);
      return attack == null ? fallback : attack.modeId();
   }

   public static double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      AsmodayAttack attack = getAttack(mode);
      return attack == null ? 0.0 : attack.magiculeCost(entity, instance);
   }

   public static void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      AsmodayAttack attack = getAttack(mode);
      if (attack != null) {
         attack.activate(instance, entity, keyNumber, mode);
      }
   }

   public static boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      AsmodayAttack attack = getAttack(mode);
      return attack != null && attack.onHeld(instance, entity, heldTicks, mode);
   }

   public static void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      AsmodayAttack attack = getAttack(mode);
      if (attack != null) {
         attack.onRelease(instance, entity, heldTicks, keyNumber, mode);
      }
   }

   public static void cleanup(LivingEntity entity) {
      for (AsmodayAttack attack : ATTACKS) {
         attack.cleanup(entity);
      }
   }

   private static AsmodayAttack getAttack(int mode) {
      return mode >= 0 && mode < ATTACKS.size() ? ATTACKS.get(mode) : null;
   }
}
