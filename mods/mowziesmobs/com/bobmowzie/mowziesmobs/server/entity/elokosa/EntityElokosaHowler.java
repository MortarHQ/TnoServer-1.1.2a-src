package com.bobmowzie.mowziesmobs.server.entity.elokosa;

import com.bobmowzie.mowziesmobs.server.ai.ElokosaHurtByTargetGoal;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
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
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.EventHooks;

public class EntityElokosaHowler extends EntityElokosa {
   private final List<EntityElokosaFollowerToHowler> pack = new ArrayList<>();

   public EntityElokosaHowler(EntityType<? extends MowzieEntity> type, Level world) {
      super(type, world);
      this.xpReward = 8;
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(3, new ElokosaHurtByTargetGoal(this, true));
   }

   @Override
   protected void registerTargetGoals() {
      super.registerTargetGoals();
      this.registerHuntingTargetGoals();
   }

   public static Builder createAttributes() {
      return EntityElokosa.createAttributes().add(Attributes.ATTACK_DAMAGE, 6.0).add(Attributes.MAX_HEALTH, 20.0);
   }

   @Override
   public boolean isHowler() {
      return true;
   }

   @Override
   public void tick() {
      super.tick();
      int i = 0;

      while (i < this.pack.size()) {
         this.pack.get(i).index = i++;
      }

      if (!this.level().isClientSide && this.level().getDifficulty() == Difficulty.PEACEFUL) {
         this.discard();
      }
   }

   public void remove(RemovalReason reason) {
      if (this.tickCount == 0) {
         this.pack.forEach(EntityElokosaFollower::setShouldSetDead);
      }

      this.pack.forEach(EntityElokosaFollowerToHowler::removeLeader);
      super.remove(reason);
   }

   public boolean checkSpawnObstruction(LevelReader worldReader) {
      return this.tickCount == 0
         ? !worldReader.containsAnyLiquid(this.getBoundingBox()) && worldReader.noCollision(this)
         : !worldReader.containsAnyLiquid(this.getBoundingBox()) && worldReader.noCollision(this) && this.level().isUnobstructed(this);
   }

   public boolean checkSpawnRules(LevelAccessor world, MobSpawnType reason) {
      for (LivingEntity nearbyEntity : this.getEntityLivingBaseNearby(20.0, 10.0, 20.0, 20.0)) {
         if (nearbyEntity instanceof EntityElokosaHowler
            || nearbyEntity instanceof Villager
            || nearbyEntity instanceof EntityUmvuthi
            || nearbyEntity instanceof Animal) {
            return false;
         }
      }

      return super.checkSpawnRules(world, reason)
         && world.getDifficulty() != Difficulty.PEACEFUL
         && (world.getTimeOfDay(0.0F) >= 0.7609 || world.getTimeOfDay(0.0F) < 0.23918849);
   }

   public int getMaxSpawnClusterSize() {
      return 1;
   }

   public void removePackMember(EntityElokosaFollowerToHowler member) {
      this.pack.remove(member);
   }

   public void addPackMember(EntityElokosaFollowerToHowler member) {
      this.pack.add(member);
   }

   public int getPackSize() {
      return this.pack.size();
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingData) {
      int size = this.random.nextInt(2) + 2;
      float theta = (float) (Math.PI * 2) / size;

      for (int i = 0; i <= size; i++) {
         EntityElokosaFollowerToHowler packMember = new EntityElokosaFollowerToHowler(
            (EntityType<? extends EntityElokosaFollowerToHowler>)EntityHandler.ELOKOSA_FOLLOWER_TO_HOWLER.get(), this.level(), this
         );
         packMember.setPos(this.getX() + 0.1 * Mth.cos(theta * i), this.getY(), this.getZ() + 0.1 * Mth.sin(theta * i));
         world.addFreshEntity(packMember);
         packMember.finalizeSpawn(world, difficulty, reason, livingData);
      }

      return super.finalizeSpawn(world, difficulty, reason, livingData);
   }

   @Override
   public void die(DamageSource source) {
      super.die(source);
      this.pack.forEach(EntityElokosaFollowerToHowler::removeLeader);
   }

   public ItemStack getPickedResult(HitResult target) {
      return new ItemStack((ItemLike)ItemHandler.ELOKOSA_HOWLER_SPAWN_EGG.get());
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
                     this.pack.forEach(EntityElokosaFollower::setShouldSetDead);
                  }

                  this.discard();
               }

               int noDespawnDistance = this.getNoDespawnDistance();
               int noDespawnRadius = noDespawnDistance * noDespawnDistance;
               if (this.noActionTime > 600 && this.random.nextInt(800) == 0 && distance > noDespawnRadius && this.removeWhenFarAway(distance)) {
                  if (this.pack != null) {
                     this.pack.forEach(EntityElokosaFollower::setShouldSetDead);
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
