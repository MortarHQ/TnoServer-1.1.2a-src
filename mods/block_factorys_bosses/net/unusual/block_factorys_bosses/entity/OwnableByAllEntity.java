package net.unusual.block_factorys_bosses.entity;

import java.util.EnumSet;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface OwnableByAllEntity extends OwnableEntity, TraceableEntity {
   @Nullable
   default LivingEntity getOwner() {
      UUID uuid = this.getOwnerUUID();
      if (uuid == null) {
         return null;
      } else if (this.level() instanceof ServerLevel level) {
         return level.getEntity(uuid) instanceof LivingEntity living ? living : null;
      } else {
         return this.level().getPlayerByUUID(uuid);
      }
   }

   class DoNotAttackOwnerGoal<T extends Mob & OwnableEntity, R extends LivingEntity> extends NearestAttackableTargetGoal<R> {
      public DoNotAttackOwnerGoal(T mob, Class<R> targetType, boolean mustSee, boolean mustReach) {
         super(mob, targetType, mustSee, mustReach);
      }

      protected void findTarget() {
         if (this.targetType != Player.class && this.targetType != ServerPlayer.class) {
            this.target = this.mob
               .level()
               .getNearestEntity(
                  this.mob
                     .level()
                     .getEntitiesOfClass(
                        this.targetType, this.getTargetSearchArea(this.getFollowDistance()), entity -> this.canAttack(entity, this.targetConditions)
                     ),
                  this.targetConditions,
                  this.mob,
                  this.mob.getX(),
                  this.mob.getEyeY(),
                  this.mob.getZ()
               );
         } else {
            this.target = this.mob
               .level()
               .getNearestPlayer(
                  this.mob.getX(),
                  this.mob.getEyeY(),
                  this.mob.getZ(),
                  this.getFollowDistance(),
                  entity -> this.canAttack((Player)entity, this.targetConditions)
               );
         }
      }

      protected boolean canAttack(@Nullable LivingEntity potentialTarget, TargetingConditions targetPredicate) {
         if (potentialTarget == ((OwnableEntity)this.mob).getOwner()) {
            return false;
         } else {
            return potentialTarget instanceof OwnableEntity ownable && ownable.getOwner() == ((OwnableEntity)this.mob).getOwner()
               ? false
               : super.canAttack(potentialTarget, targetPredicate);
         }
      }
   }

   class OwnerHurtByTargetGoal<T extends Mob & OwnableEntity> extends TargetGoal {
      private final T ownable;
      private LivingEntity ownerLastHurtBy;
      private int timestamp;

      public OwnerHurtByTargetGoal(T ownable) {
         super(ownable, false);
         this.ownable = ownable;
         this.setFlags(EnumSet.of(Flag.TARGET));
      }

      public boolean canUse() {
         LivingEntity owner = this.ownable.getOwner();
         if (owner == null) {
            return false;
         }

         this.ownerLastHurtBy = owner.getLastHurtByMob();
         int i = owner.getLastHurtByMobTimestamp();
         return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT);
      }

      public void start() {
         this.mob.setTarget(this.ownerLastHurtBy);
         LivingEntity owner = this.ownable.getOwner();
         if (owner != null) {
            this.timestamp = owner.getLastHurtByMobTimestamp();
         }

         super.start();
      }

      protected boolean canAttack(@Nullable LivingEntity potentialTarget, TargetingConditions targetPredicate) {
         if (potentialTarget == this.ownable.getOwner()) {
            return false;
         } else {
            return potentialTarget instanceof OwnableEntity ownableTarget && ownableTarget.getOwner() == this.ownable.getOwner()
               ? false
               : super.canAttack(potentialTarget, targetPredicate);
         }
      }
   }

   class OwnerHurtTargetGoal<T extends Mob & OwnableEntity> extends TargetGoal {
      private final T ownable;
      private LivingEntity ownerLastHurt;
      private int timestamp;

      public OwnerHurtTargetGoal(T ownable) {
         super(ownable, false);
         this.ownable = ownable;
         this.setFlags(EnumSet.of(Flag.TARGET));
      }

      public boolean canUse() {
         LivingEntity owner = this.ownable.getOwner();
         if (owner == null) {
            return false;
         }

         this.ownerLastHurt = owner.getLastHurtMob();
         int i = owner.getLastHurtMobTimestamp();
         return i != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT);
      }

      public void start() {
         this.mob.setTarget(this.ownerLastHurt);
         LivingEntity owner = this.ownable.getOwner();
         if (owner != null) {
            this.timestamp = owner.getLastHurtMobTimestamp();
         }

         super.start();
      }

      protected boolean canAttack(@Nullable LivingEntity potentialTarget, TargetingConditions targetPredicate) {
         if (potentialTarget == this.ownable.getOwner()) {
            return false;
         } else {
            return potentialTarget instanceof OwnableEntity ownableTarget && ownableTarget.getOwner() == this.ownable.getOwner()
               ? false
               : super.canAttack(potentialTarget, targetPredicate);
         }
      }
   }
}
