package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.server.ai.NearestAttackableTargetPredicateGoal;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class EntityUmvuthanaCraneToPlayer extends EntityUmvuthanaFollowerToPlayer {
   public EntityUmvuthanaCraneToPlayer(EntityType<? extends EntityUmvuthanaCraneToPlayer> type, Level world) {
      this(type, world, null);
   }

   public EntityUmvuthanaCraneToPlayer(EntityType<? extends EntityUmvuthanaCraneToPlayer> type, Level world, Player leader) {
      super(type, world, leader);
      this.setMask(MaskType.FAITH);
      this.setWeapon(3);
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(4, new EntityUmvuthanaCrane.HealTargetGoal(this));
   }

   @Override
   protected void registerTargetGoals() {
      super.registerTargetGoals();
      this.targetSelector
         .addGoal(
            2,
            new NearestAttackableTargetPredicateGoal<Player>(
               this,
               Player.class,
               0,
               true,
               true,
               TargetingConditions.forNonCombat().range(this.getAttributeValue(Attributes.FOLLOW_RANGE)).selector(target -> {
                  if (!this.active) {
                     return false;
                  } else {
                     return target != this.getLeader() ? false : this.healAICheckTarget(target);
                  }
               }).ignoreInvisibilityTesting()
            ) {
               public boolean canContinueToUse() {
                  LivingEntity livingentity = this.mob.getTarget();
                  if (livingentity == null) {
                     livingentity = this.targetMob;
                  }

                  return super.canContinueToUse()
                     && this.mob instanceof EntityUmvuthanaCraneToPlayer
                     && ((EntityUmvuthanaCraneToPlayer)this.mob).healAICheckTarget(livingentity);
               }
            }
         );
   }

   private boolean healAICheckTarget(LivingEntity livingentity) {
      if (livingentity != this.getLeader()) {
         return false;
      }

      boolean targetHasTarget = livingentity.getLastHurtMob() != null
         && (livingentity.tickCount - livingentity.getLastHurtMobTimestamp() < 120 || livingentity.distanceToSqr(livingentity.getLastHurtMob()) < 256.0);
      if (livingentity.getLastHurtMob() instanceof EntityUmvuthanaFollowerToPlayer) {
         targetHasTarget = false;
      }

      boolean canHeal = this.canHeal(livingentity);
      boolean survivalMode = !livingentity.isSpectator() && !((Player)livingentity).isCreative();
      return (livingentity.getHealth() < livingentity.getMaxHealth() || targetHasTarget) && canHeal && survivalMode;
   }

   @Override
   public boolean canHeal(LivingEntity entity) {
      return entity == this.leader && entity != null && this.distanceToSqr(entity) < 256.0;
   }

   @Override
   protected void sunBlockTarget() {
      LivingEntity target = this.getTarget();
      if (target != null && target == this.getLeader()) {
         EffectHandler.addOrCombineEffect(target, EffectHandler.SUNBLOCK, 20, 0, true, false);
      }
   }

   @Override
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData livingData) {
      this.setMask(MaskType.FAITH);
      this.setWeapon(3);
      return super.finalizeSpawn(world, difficulty, reason, livingData);
   }
}
