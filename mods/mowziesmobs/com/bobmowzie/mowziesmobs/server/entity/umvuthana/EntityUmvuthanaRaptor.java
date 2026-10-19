package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.server.ai.UmvuthanaHurtByTargetAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.event.EventHooks;

public class EntityUmvuthanaRaptor extends EntityUmvuthana implements LeaderSunstrikeImmune, Enemy {
   private final List<EntityUmvuthanaFollowerToRaptor> pack = new ArrayList<>();
   private final int packRadius = 3;

   public EntityUmvuthanaRaptor(EntityType<? extends EntityUmvuthanaRaptor> type, Level world) {
      super(type, world);
      this.setMask(MaskType.FURY);
      this.xpReward = 8;
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(3, new UmvuthanaHurtByTargetAI(this, true));
   }

   @Override
   protected void registerTargetGoals() {
      this.registerHuntingTargetGoals();
   }

   @Override
   protected boolean canHoldVaryingWeapons() {
      return false;
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes().add(Attributes.ATTACK_DAMAGE, 6.0).add(Attributes.MAX_HEALTH, 10.0).add(Attributes.STEP_HEIGHT, 1.0);
   }

   @Override
   public void tick() {
      super.tick();
      int i = 0;

      while (i < this.pack.size()) {
         this.pack.get(i).index = i++;
      }

      if (!this.level().isClientSide && this.pack != null) {
         float theta = (float) (Math.PI * 2) / this.pack.size();

         for (int ix = 0; ix < this.pack.size(); ix++) {
            EntityUmvuthanaFollowerToRaptor hunter = this.pack.get(ix);
            if (hunter.getTarget() == null) {
               hunter.getNavigation().moveTo(this.getX() + 3.0F * Mth.cos(theta * ix), this.getY(), this.getZ() + 3.0F * Mth.sin(theta * ix), 0.45);
               if (this.distanceTo(hunter) > 20.0F && this.onGround()) {
                  hunter.setPos(this.getX() + 3.0F * Mth.cos(theta * ix), this.getY(), this.getZ() + 3.0F * Mth.sin(theta * ix));
               }
            }
         }
      }

      if (!this.level().isClientSide && this.level().getDifficulty() == Difficulty.PEACEFUL) {
         this.discard();
      }
   }

   public void remove(RemovalReason reason) {
      if (this.tickCount == 0) {
         this.pack.forEach(EntityUmvuthanaFollower::setShouldSetDead);
      }

      this.pack.forEach(EntityUmvuthanaFollowerToRaptor::removeLeader);
      super.remove(reason);
   }

   public boolean checkSpawnObstruction(LevelReader worldReader) {
      return this.tickCount == 0
         ? !worldReader.containsAnyLiquid(this.getBoundingBox()) && worldReader.noCollision(this)
         : !worldReader.containsAnyLiquid(this.getBoundingBox()) && worldReader.noCollision(this) && this.level().isUnobstructed(this);
   }

   public void removePackMember(EntityUmvuthanaFollowerToRaptor member) {
      this.pack.remove(member);
      this.sortPackMembers();
   }

   public void addPackMember(EntityUmvuthanaFollowerToRaptor member) {
      this.pack.add(member);
      this.sortPackMembers();
   }

   private void sortPackMembers() {
      double theta = (Math.PI * 2) / this.pack.size();

      for (int i = 0; i < this.pack.size(); i++) {
         int nearestIndex = -1;
         double smallestDiffSq = Double.MAX_VALUE;
         double targetTheta = theta * i;
         double x = this.getX() + 3.0 * Math.cos(targetTheta);
         double z = this.getZ() + 3.0 * Math.sin(targetTheta);

         for (int n = 0; n < this.pack.size(); n++) {
            EntityUmvuthanaFollowerToRaptor packMember = this.pack.get(n);
            if (packMember != null) {
               double diffSq = (x - packMember.getX()) * (x - packMember.getX()) + (z - packMember.getZ()) * (z - packMember.getZ());
               if (diffSq < smallestDiffSq) {
                  smallestDiffSq = diffSq;
                  nearestIndex = n;
               }
            }
         }

         if (nearestIndex == -1) {
            throw new ArithmeticException("All pack members have NaN x and z?");
         }

         this.pack.add(i, this.pack.remove(nearestIndex));
      }
   }

   public int getPackSize() {
      return this.pack.size();
   }

   @Override
   public int getAttackChance() {
      return this.getPackSize() < 0 ? super.getAttackChance() : 8 * this.getPackSize() + 40;
   }

   @Override
   public int getAttackCooldown() {
      return this.getPackSize() < 0 ? super.getAttackCooldown() : 8 * this.getPackSize() + 40;
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingData) {
      int size = this.random.nextInt(2) + 3;
      float theta = (float) (Math.PI * 2) / size;

      for (int i = 0; i <= size; i++) {
         EntityUmvuthanaFollowerToRaptor packMember = new EntityUmvuthanaFollowerToRaptor(
            (EntityType<? extends EntityUmvuthanaFollowerToRaptor>)EntityHandler.UMVUTHANA_FOLLOWER_TO_RAPTOR.get(), this.level(), this
         );
         packMember.setPos(this.getX() + 0.1 * Mth.cos(theta * i), this.getY(), this.getZ() + 0.1 * Mth.sin(theta * i));
         int weapon = this.random.nextInt(3) == 0 ? 1 : 0;
         packMember.setWeapon(weapon);
         world.addFreshEntity(packMember);
      }

      return super.finalizeSpawn(world, difficulty, reason, livingData);
   }

   @Override
   public void die(DamageSource source) {
      super.die(source);
      this.pack.forEach(EntityUmvuthanaFollowerToRaptor::removeLeader);
   }

   @Override
   protected ConfigHandler.SpawnConfig getSpawnConfig() {
      return ConfigHandler.COMMON.MOBS.UMVUTHANA.spawnConfig;
   }

   public boolean checkSpawnRules(LevelAccessor world, MobSpawnType reason) {
      for (LivingEntity nearbyEntity : this.getEntityLivingBaseNearby(30.0, 10.0, 30.0, 30.0)) {
         if (nearbyEntity instanceof EntityUmvuthanaRaptor
            || nearbyEntity instanceof Villager
            || nearbyEntity instanceof EntityUmvuthi
            || nearbyEntity instanceof Animal) {
            return false;
         }
      }

      return super.checkSpawnRules(world, reason) && world.getDifficulty() != Difficulty.PEACEFUL;
   }

   public int getMaxSpawnClusterSize() {
      return 1;
   }

   @Override
   public void checkDespawn() {
      if (!EventHooks.checkMobDespawn(this)) {
         if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
         } else if (!this.isPersistenceRequired() && !this.requiresCustomPersistence()) {
            Entity entity = this.level().getNearestPlayer(this, -1.0);
            if (entity != null) {
               double distance = entity.distanceToSqr(this);
               int despawnDistance = this.getDespawnDistance();
               int despawnRadius = despawnDistance * despawnDistance;
               if (distance > despawnRadius && this.removeWhenFarAway(distance)) {
                  if (this.pack != null) {
                     this.pack.forEach(EntityUmvuthanaFollower::setShouldSetDead);
                  }

                  this.discard();
               }

               int noDespawnDistance = this.getNoDespawnDistance();
               int noDespawnRadius = noDespawnDistance * noDespawnDistance;
               if (this.noActionTime > 600 && this.random.nextInt(800) == 0 && distance > noDespawnRadius && this.removeWhenFarAway(distance)) {
                  if (this.pack != null) {
                     this.pack.forEach(EntityUmvuthanaFollower::setShouldSetDead);
                  }

                  this.discard();
               } else if (distance < noDespawnRadius) {
                  this.noActionTime = 0;
               }
            }
         } else {
            this.noActionTime = 0;
         }
      }
   }
}
