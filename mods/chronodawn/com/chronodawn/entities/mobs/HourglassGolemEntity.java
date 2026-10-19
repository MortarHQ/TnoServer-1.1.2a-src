package com.chronodawn.entities.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class HourglassGolemEntity extends Monster {
   private int targetChangeTime;

   public HourglassGolemEntity(EntityType<? extends HourglassGolemEntity> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 10;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 80.0)
         .add(Attributes.ATTACK_DAMAGE, 12.0)
         .add(Attributes.MOVEMENT_SPEED, 0.25)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   private boolean isLookingAtMe(Player player) {
      Vec3 viewVec = player.getViewVector(1.0F).normalize();
      Vec3 toEntity = new Vec3(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
      double distance = toEntity.length();
      toEntity = toEntity.normalize();
      double dot = viewVec.dot(toEntity);
      return dot > 1.0 - 0.025 / distance && player.hasLineOfSight(this);
   }

   public void aiStep() {
      super.aiStep();
      if (!this.level().isClientSide) {
         Player nearestPlayer = this.level().getNearestPlayer(this, 64.0);
         if (nearestPlayer != null && this.isLookingAtMe(nearestPlayer) && this.getTarget() == null) {
            this.setTarget(nearestPlayer);
            this.targetChangeTime = this.tickCount;
         }
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      boolean result = super.hurt(source, amount);
      if (result && this.isAlive()) {
         this.teleportRandomly();
      }

      return result;
   }

   private boolean teleportRandomly() {
      if (!this.level().isClientSide() && this.isAlive()) {
         for (int i = 0; i < 16; i++) {
            double x = this.getX() + (this.random.nextDouble() - 0.5) * 32.0;
            double y = this.getY() + (this.random.nextInt(16) - 8);
            double z = this.getZ() + (this.random.nextDouble() - 0.5) * 32.0;
            if (this.tryTeleportTo(x, y, z)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean tryTeleportTo(double x, double y, double z) {
      MutableBlockPos pos = new MutableBlockPos(x, y, z);

      while (pos.getY() > this.level().getMinBuildHeight() && !this.level().getBlockState(pos).blocksMotion()) {
         pos.move(0, -1, 0);
      }

      if (!this.level().getBlockState(pos).blocksMotion()) {
         return false;
      } else {
         pos.move(0, 1, 0);
         if (this.level().getBlockState(pos).isAir() && this.level().getBlockState(pos.above()).isAir()) {
            this.teleportTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            this.level().playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            return true;
         } else {
            return false;
         }
      }
   }

   public static boolean checkHourglassGolemSpawnRules(
      EntityType<HourglassGolemEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return level.getDifficulty() == Difficulty.PEACEFUL ? false : Mob.checkMobSpawnRules(entityType, level, spawnType, pos, random);
   }
}
