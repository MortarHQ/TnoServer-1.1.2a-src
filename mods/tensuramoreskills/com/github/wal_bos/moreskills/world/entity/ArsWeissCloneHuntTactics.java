package com.github.wal_bos.moreskills.world.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public final class ArsWeissCloneHuntTactics {
   private ArsWeissCloneHuntTactics() {
   }

   public static double targetScore(ArsWeissCloneEntity clone, ServerPlayer owner, LivingEntity target, String query) {
      return ArsWeissCloneCombatTactics.targetScore(clone, owner, target, query);
   }

   public static Vec3 approachPoint(ArsWeissCloneEntity clone, ServerPlayer owner, LivingEntity target) {
      return ArsWeissCloneCombatTactics.approachPoint(clone, owner, target);
   }

   public static void applyCombatMotion(ArsWeissCloneEntity clone, ServerPlayer owner, LivingEntity target) {
      ArsWeissCloneCombatTactics.applyCombatMotion(clone, owner, target);
   }
}
