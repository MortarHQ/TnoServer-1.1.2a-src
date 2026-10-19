package com.github.b4ndithelps.tennogamenolife.ability.skill.extra;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import java.util.List;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BarrierSkill extends Skill {
   private final double skillCastCost = 100.0;
   private final double learnCost = 2.0;
   private final double skillRange = 15.0;

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/skill/extra/umweg.png");
   }

   public BarrierSkill() {
      super(SkillType.EXTRA);
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return 100.0;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 2.0;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof Player player) {
         player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EVOKER_PREPARE_ATTACK, SoundSource.PLAYERS, 1.0F, 1.0F);
         if (this.isOutOfEnergy(entity, instance, mode, 1.0F)) {
            return;
         }

         LivingEntity target = this.findTargetByRaytrace(player, 15.0);
         if (target == null) {
            target = this.findNearestEntityInLine(player, 15.0);
         }

         if (target != null) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 255, false, true, true));
            target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 255, false, true, true));
            player.level().playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1.0F, 0.5F);
            instance.setCoolDown(0, 5);
            this.addMasteryPoint(instance, entity);
         }
      }
   }

   private LivingEntity findTargetByRaytrace(Player player, double maxDistance) {
      Level level = player.level();
      Vec3 startPos = player.getEyePosition(1.0F);
      Vec3 lookVec = player.getViewVector(1.0F);
      Vec3 endPos = startPos.add(lookVec.scale(maxDistance));
      AABB boundingBox = player.getBoundingBox().expandTowards(lookVec.scale(maxDistance)).inflate(1.0);
      List<Entity> possibleEntities = level.getEntities(
         player, boundingBox, entityx -> entityx instanceof LivingEntity && !entityx.isSpectator() && entityx.isPickable()
      );
      Entity nearestEntity = null;
      double nearestDistance = maxDistance;
      Vec3 nearestIntersection = null;

      for (Entity entity : possibleEntities) {
         AABB entityBox = entity.getBoundingBox().inflate(0.3);
         Optional<Vec3> hitPosition = entityBox.clip(startPos, endPos);
         if (hitPosition.isPresent()) {
            double distance = startPos.distanceTo(hitPosition.get());
            if (distance < nearestDistance) {
               nearestEntity = entity;
               nearestDistance = distance;
               nearestIntersection = hitPosition.get();
            }
         }
      }

      return nearestEntity instanceof LivingEntity living ? living : null;
   }

   private LivingEntity findNearestEntityInLine(Player player, double maxDistance) {
      Level level = player.level();
      Vec3 eyePos = player.getEyePosition(1.0F);
      Vec3 viewVector = player.getViewVector(1.0F);
      Vec3 targetPos = eyePos.add(viewVector.x * maxDistance, viewVector.y * maxDistance, viewVector.z * maxDistance);
      double boxSize = 2.0;
      AABB detectionBox = new AABB(
         eyePos.x - boxSize, eyePos.y - boxSize, eyePos.z - boxSize, targetPos.x + boxSize, targetPos.y + boxSize, targetPos.z + boxSize
      );
      List<Entity> entities = level.getEntities(player, detectionBox, entityx -> entityx instanceof LivingEntity && entityx != player);
      Entity closestEntity = null;
      double smallestDeviation = Double.MAX_VALUE;

      for (Entity entity : entities) {
         double distance = player.distanceTo(entity);
         if (distance <= maxDistance) {
            Vec3 entityPos = entity.position().add(0.0, entity.getEyeHeight() / 2.0F, 0.0);
            Vec3 playerToEntity = entityPos.subtract(eyePos);
            double length = playerToEntity.length();
            Vec3 projection = eyePos.add(viewVector.scale(length * viewVector.dot(playerToEntity) / viewVector.lengthSqr()));
            double deviation = projection.distanceTo(entityPos);
            if (deviation < smallestDeviation && deviation < 2.0) {
               closestEntity = entity;
               smallestDeviation = deviation;
            }
         }
      }

      return closestEntity instanceof LivingEntity living ? living : null;
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      return false;
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
   }
}
