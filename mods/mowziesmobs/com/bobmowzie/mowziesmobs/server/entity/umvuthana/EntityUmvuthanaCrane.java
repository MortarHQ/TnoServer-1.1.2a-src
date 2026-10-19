package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ai.AvoidProjectilesGoal;
import com.bobmowzie.mowziesmobs.server.ai.NearestAttackableTargetPredicateGoal;
import com.bobmowzie.mowziesmobs.server.ai.UmvuthanaHurtByTargetAI;
import com.bobmowzie.mowziesmobs.server.item.UmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class EntityUmvuthanaCrane extends EntityUmvuthanaMinion {
   public boolean hasTriedOrSucceededTeleport = true;
   private int teleportAttempts = 0;

   public EntityUmvuthanaCrane(EntityType<? extends EntityUmvuthanaMinion> type, Level world) {
      super(type, world);
      this.setWeapon(3);
      this.setMask(MaskType.FAITH);
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(1, new EntityUmvuthanaCrane.TeleportToSafeSpotGoal(this));
      this.goalSelector.addGoal(1, new AvoidProjectilesGoal(this, Projectile.class, target -> this.getActiveAbilityType() == HEAL_ABILITY, 5.0F, 0.8, 0.6));
      this.goalSelector.addGoal(4, new EntityUmvuthanaCrane.HealTargetGoal(this));
      this.goalSelector.addGoal(6, new AvoidEntityGoal<Player>(this, Player.class, 7.0F, 0.8, 0.6, target -> {
         if (!(target instanceof Player)) {
            return true;
         }

         if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
            return false;
         }

         if (this.getTarget() == target) {
            return true;
         }

         if (this.getTarget() instanceof EntityUmvuthi) {
            return false;
         }

         if (this.getActiveAbilityType() != null) {
            return false;
         }

         ItemStack headArmorStack = (ItemStack)((Player)target).getInventory().armor.get(3);
         return !(headArmorStack.getItem() instanceof UmvuthanaMask) || target == this.getMisbehavedPlayer();
      }) {
         public void stop() {
            super.stop();
            EntityUmvuthanaCrane.this.setMisbehavedPlayerId(null);
         }
      });
      this.goalSelector.addGoal(6, new AvoidEntityGoal(this, Zombie.class, 7.0F, 0.8, 0.6));
      this.goalSelector.addGoal(6, new AvoidEntityGoal(this, AbstractSkeleton.class, 7.0F, 0.8, 0.6));
   }

   @Override
   protected void registerTargetGoals() {
      this.targetSelector.addGoal(3, new UmvuthanaHurtByTargetAI(this, true));
      this.targetSelector
         .addGoal(
            2,
            new NearestAttackableTargetPredicateGoal<EntityUmvuthi>(
               this,
               EntityUmvuthi.class,
               0,
               false,
               false,
               TargetingConditions.forNonCombat().range(this.getAttributeValue(Attributes.FOLLOW_RANGE) * 2.0).selector(target -> {
                  if (!this.active) {
                     return false;
                  } else {
                     return !(target instanceof Mob) ? false : ((Mob)target).getTarget() != null || target.getHealth() < target.getMaxHealth();
                  }
               }).ignoreLineOfSight().ignoreInvisibilityTesting()
            ) {
               public boolean canContinueToUse() {
                  LivingEntity livingentity = this.mob.getTarget();
                  if (livingentity == null) {
                     livingentity = this.targetMob;
                  }

                  boolean targetHasTarget = false;
                  if (livingentity instanceof Mob) {
                     targetHasTarget = ((Mob)livingentity).getTarget() != null;
                  }

                  boolean canHeal = true;
                  if (this.mob instanceof EntityUmvuthana) {
                     canHeal = ((EntityUmvuthana)this.mob).canHeal(livingentity);
                  }

                  return super.canContinueToUse() && (livingentity.getHealth() < livingentity.getMaxHealth() || targetHasTarget) && canHeal;
               }

               protected double getFollowDistance() {
                  return super.getFollowDistance() * 2.0;
               }
            }
         );
   }

   @Override
   public void tick() {
      super.tick();
      if (this.active && this.teleportAttempts > 3 && (this.getTarget() == null || !this.getTarget().isAlive())) {
         this.hasTriedOrSucceededTeleport = true;
      }
   }

   @Override
   public boolean canHeal(LivingEntity entity) {
      return entity instanceof EntityUmvuthi;
   }

   @Override
   public void die(DamageSource cause) {
      if (this.getTarget() instanceof EntityUmvuthi) {
         List<Mob> targetingMobs = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(30.0), e -> e.getTarget() == this);
         if (cause.getEntity() instanceof Mob) {
            Mob damagingMob = (Mob)cause.getEntity();
            if (damagingMob.getTarget() == this && !targetingMobs.contains(damagingMob)) {
               targetingMobs.add(damagingMob);
            }
         }

         for (Mob mob : targetingMobs) {
            mob.setTarget(this.getTarget());
         }
      }

      super.die(cause);
   }

   @Override
   protected void sunBlockTarget() {
      LivingEntity target = this.getTarget();
      if (target != null) {
         EffectHandler.addOrCombineEffect(target, EffectHandler.SUNBLOCK, 20, 0, true, false);
         if (target.tickCount % 20 == 0) {
            target.heal(0.15F);
         }
      }
   }

   public boolean isInvulnerableTo(DamageSource source) {
      boolean teleporting = this.getActiveAbilityType() == TELEPORT_ABILITY && this.getActiveAbility().getTicksInUse() <= 16;
      return super.isInvulnerableTo(source)
         || (!this.active || teleporting || !this.hasTriedOrSucceededTeleport) && !source.is(DamageTypes.FELL_OUT_OF_WORLD) && this.timeUntilDeath != 0;
   }

   public void igniteForTicks(int ticks) {
      boolean teleporting = this.getActiveAbilityType() == TELEPORT_ABILITY && this.getActiveAbility().getTicksInUse() <= 16;
      if (this.active && !teleporting && this.hasTriedOrSucceededTeleport) {
         super.igniteForTicks(ticks);
      }
   }

   @Override
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData livingData) {
      this.setMask(MaskType.FAITH);
      this.setWeapon(3);
      return super.finalizeSpawn(world, difficulty, reason, livingData);
   }

   public static class HealTargetGoal extends Goal {
      private final EntityUmvuthana entity;

      public HealTargetGoal(EntityUmvuthana entityIn) {
         this.entity = entityIn;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canContinueToUse() {
         return this.entity.canHeal(this.entity.getTarget());
      }

      public boolean canUse() {
         return !this.entity.active ? false : this.entity.canHeal(this.entity.getTarget());
      }

      public void start() {
         super.start();
         AbilityHandler.INSTANCE.sendAbilityMessage(this.entity, EntityUmvuthana.HEAL_ABILITY);
      }
   }

   public class TeleportToSafeSpotGoal extends Goal {
      private final EntityUmvuthanaCrane entity;

      public TeleportToSafeSpotGoal(EntityUmvuthanaCrane entityIn) {
         this.entity = entityIn;
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         if (!this.entity.active) {
            return false;
         } else if (this.entity.getActiveAbilityType() == EntityUmvuthana.TELEPORT_ABILITY) {
            return false;
         } else {
            return this.entity.getTarget() == null
                  || !this.entity.canHeal(this.entity.getTarget())
                  || (!(this.entity.targetDistance >= 0.0F) || !(this.entity.targetDistance < 11.0F)) && EntityUmvuthanaCrane.this.hasTriedOrSucceededTeleport
               ? false
               : this.findTeleportLocation();
         }
      }

      public void start() {
         super.start();
         EntityUmvuthanaCrane.this.hasTriedOrSucceededTeleport = true;
         AbilityHandler.INSTANCE.sendAbilityMessage(this.entity, EntityUmvuthana.TELEPORT_ABILITY);
      }

      private boolean findTeleportLocation() {
         int i;
         int j;
         int k;
         if (this.entity.getRestrictRadius() > -1.0F) {
            i = Mth.floor(this.entity.getRestrictCenter().getX());
            j = Mth.floor(this.entity.getRestrictCenter().getY());
            k = Mth.floor(this.entity.getRestrictCenter().getZ());
         } else if (this.entity.getTarget() != null) {
            i = Mth.floor(this.entity.getTarget().getX());
            j = Mth.floor(this.entity.getTarget().getY());
            k = Mth.floor(this.entity.getTarget().getZ());
         } else {
            i = Mth.floor(this.entity.getX());
            j = Mth.floor(this.entity.getY());
            k = Mth.floor(this.entity.getZ());
         }

         boolean foundPosition = false;

         for (int l = 0; l < 50; l++) {
            double radius = Math.pow(EntityUmvuthanaCrane.this.random.nextFloat(), 1.35) * 25.0;
            double angle = EntityUmvuthanaCrane.this.random.nextFloat() * Math.PI * 2.0;
            int i1 = i + (int)(Math.cos(angle) * radius);
            int j1 = j + Mth.nextInt(this.entity.random, 0, 15) * Mth.nextInt(this.entity.random, -1, 1);
            int k1 = k + (int)(Math.sin(angle) * radius);
            BlockPos blockpos = new BlockPos(i1, j1, k1);
            Vec3 newPos = new Vec3(i1, j1, k1);
            Vec3 offset = newPos.subtract(this.entity.position());
            AABB newBB = this.entity.getBoundingBox().move(offset);
            if (this.testBlock(blockpos, newBB) && this.entity.level().getEntitiesOfClass(EntityUmvuthi.class, newBB.inflate(7.0)).isEmpty()) {
               this.entity.teleportDestination = newPos.add(0.0, 0.0, 0.0);
               if (this.entity.teleportAttempts >= 3) {
                  foundPosition = true;
               }

               if (this.entity.level().getEntitiesOfClass(EntityUmvuthanaCrane.class, newBB.inflate(5.0)).isEmpty()) {
                  if (this.entity.teleportAttempts >= 2) {
                     foundPosition = true;
                  }

                  if (!this.entity.level().hasNearbyAlivePlayer(i1, j1, k1, 5.0) && !this.entity.level().containsAnyLiquid(newBB)) {
                     if (this.entity.teleportAttempts >= 1) {
                        foundPosition = true;
                     }

                     LivingEntity target = EntityUmvuthanaCrane.this.getTarget();
                     if (!(target instanceof Mob) || ((Mob)target).getTarget() == null) {
                        return true;
                     }

                     if (!this.canEntityBeSeenFromLocation(((Mob)target).getTarget(), newPos)) {
                        return true;
                     }
                  }
               }
            }
         }

         this.entity.teleportAttempts++;
         if (this.entity.teleportAttempts > 3) {
            EntityUmvuthanaCrane.this.hasTriedOrSucceededTeleport = true;
         }

         return foundPosition;
      }

      public boolean canEntityBeSeenFromLocation(Entity entityIn, Vec3 location) {
         Vec3 vector3d = new Vec3(location.x(), location.y() + this.entity.getEyeHeight(), location.z());
         Vec3 vector3d1 = new Vec3(entityIn.getX(), entityIn.getEyeY(), entityIn.getZ());
         return this.entity.level().clip(new ClipContext(vector3d, vector3d1, Block.COLLIDER, Fluid.NONE, this.entity)).getType() != Type.BLOCK;
      }

      public boolean testBlock(BlockPos blockpos, AABB aabb) {
         Level world = this.entity.level();
         if (!world.hasChunkAt(blockpos)) {
            return false;
         }

         BlockPos blockpos1 = blockpos.below();
         BlockState blockstate = world.getBlockState(blockpos1);
         return blockstate.isSolid() && blockstate.blocksMotion() && world.noCollision(aabb);
      }
   }
}
