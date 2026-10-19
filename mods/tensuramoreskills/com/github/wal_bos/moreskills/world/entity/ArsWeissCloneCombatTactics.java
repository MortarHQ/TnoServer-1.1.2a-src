package com.github.wal_bos.moreskills.world.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public final class ArsWeissCloneCombatTactics {
   private ArsWeissCloneCombatTactics() {
   }

   public static double targetScore(ArsWeissCloneEntity clone, ServerPlayer owner, LivingEntity target, String query) {
      double score = target.distanceToSqr(clone);
      ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
      String type = id == null ? "" : id.toString().toLowerCase();
      String name = target.getName().getString().toLowerCase();
      String q = query == null ? "" : query.toLowerCase();
      if (target instanceof Player) {
         score -= 8000.0;
      }

      if (!q.isBlank() && (name.contains(q) || type.contains(q))) {
         score -= 6000.0;
      }

      if (target instanceof Mob mob && mob.getTarget() == owner) {
         score -= 2500.0;
      }

      if (target.getHealth() < target.getMaxHealth() * 0.35F) {
         score -= 900.0;
      }

      return score - ArsWeissCloneTensuraSupport.tensuraEntityQueryScore(target, q);
   }

   public static Vec3 approachPoint(ArsWeissCloneEntity clone, ServerPlayer owner, LivingEntity target) {
      boolean playerTarget = target instanceof Player;
      return tacticalPoint(clone, owner, target, playerTarget);
   }

   public static Vec3 tacticalPoint(ArsWeissCloneEntity clone, ServerPlayer owner, LivingEntity target, boolean playerTarget) {
      Vec3 targetPos = target.position();
      Vec3 targetMotion = target.getDeltaMovement();
      Vec3 predicted = targetPos.add(targetMotion.scale(playerTarget ? 7.0 : 4.0));
      Vec3 fromTarget = clone.position().subtract(targetPos);
      if (fromTarget.lengthSqr() < 0.01) {
         fromTarget = owner.position().subtract(targetPos);
      }

      if (fromTarget.lengthSqr() < 0.01) {
         fromTarget = new Vec3(1.0, 0.0, 0.0);
      }

      Vec3 side = new Vec3(-fromTarget.z, 0.0, fromTarget.x).normalize();
      double sign = (clone.getId() & 1) == 0 ? 1.0 : -1.0;
      double radius = playerTarget ? 3.6 : 2.4;
      if (target instanceof Mob mob && mob.getTarget() == clone) {
         radius += 1.25;
      }

      Vec3 point = predicted.add(side.scale(sign * radius));
      return new Vec3(point.x, target.getY(), point.z);
   }

   public static void applyCombatMotion(ArsWeissCloneEntity clone, ServerPlayer owner, LivingEntity target) {
      Vec3 toTarget = target.position().subtract(clone.position());
      Vec3 flat = new Vec3(toTarget.x, 0.0, toTarget.z);
      if (!(flat.lengthSqr() < 0.001)) {
         Vec3 dir = flat.normalize();
         Vec3 side = new Vec3(-dir.z, 0.0, dir.x).scale((clone.tickCount / 20 + clone.getId() & 1) == 0 ? 0.05 : -0.05);
         double closePush = clone.distanceToSqr(target) < 2.25 ? -0.08 : 0.035;
         clone.setDeltaMovement(clone.getDeltaMovement().add(dir.scale(closePush)).add(side));
         clone.hurtMarked = true;
      }
   }

   public static boolean shouldStrike(ArsWeissCloneEntity clone, LivingEntity target, boolean playerTarget) {
      double dist = clone.distanceToSqr(target);
      int cadence = playerTarget ? 6 : 8;
      return dist <= (playerTarget ? 12.25 : 9.0) && clone.tickCount % cadence == Math.floorMod(clone.getId(), cadence);
   }

   public static BlockPos interceptBlock(ArsWeissCloneEntity clone, ServerPlayer owner, LivingEntity target, boolean playerTarget) {
      Vec3 point = tacticalPoint(clone, owner, target, playerTarget);
      return BlockPos.containing(point.x, target.getY(), point.z);
   }
}
