package com.benji.netherman.common.entity;

import java.util.List;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GildedGolemEntity extends IronGolem implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   public static final EntityDataAccessor<Integer> TEXTURE_STATE = SynchedEntityData.defineId(GildedGolemEntity.class, EntityDataSerializers.INT);
   @Nullable
   private UUID creatorUUID;
   private int attackTimer = 0;
   private boolean isHealingPhase = false;
   private static final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenPlay("attack");
   private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
   private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");

   public GildedGolemEntity(EntityType<? extends IronGolem> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 100.0)
         .add(Attributes.MOVEMENT_SPEED, 0.25)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.ATTACK_DAMAGE, 15.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(TEXTURE_STATE, 0);
   }

   public void setCreatorUUID(@Nullable UUID uuid) {
      this.creatorUUID = uuid;
   }

   @Nullable
   public UUID getCreatorUUID() {
      return this.creatorUUID;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
      this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
      this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector
         .addGoal(
            2,
            new NearestAttackableTargetGoal(
               this,
               Mob.class,
               5,
               false,
               false,
               entity -> entity instanceof Animal
                  || entity instanceof IronGolem && !(entity instanceof GildedGolemEntity)
                  || entity instanceof AbstractVillager
            )
         );
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         if (this.attackTimer > 0) {
            this.attackTimer--;
         }
      } else {
         float healthPct = this.getHealth() / this.getMaxHealth();
         if (this.getHealth() >= this.getMaxHealth()) {
            this.isHealingPhase = false;
         }

         if (healthPct <= 0.25F && !this.isHealingPhase && this.getHealth() > 0.0F) {
            this.isHealingPhase = true;
         }

         if (this.isHealingPhase) {
            this.entityData.set(TEXTURE_STATE, 2);
            boolean stillSucking = this.performHealingSuck();
            if (!stillSucking) {
               this.isHealingPhase = false;
            }
         } else if (healthPct <= 0.5F) {
            this.entityData.set(TEXTURE_STATE, 1);
         } else {
            this.entityData.set(TEXTURE_STATE, 0);
         }

         if (this.tickCount % 10 == 0 && this.getTarget() == null && !this.isHealingPhase) {
            for (BelieverEntity believer : this.level().getEntitiesOfClass(BelieverEntity.class, this.getBoundingBox().inflate(15.0))) {
               LivingEntity attacker = believer.getLastHurtByMob();
               if (attacker != null && attacker.isAlive() && !attacker.getUUID().equals(this.creatorUUID)) {
                  this.setTarget(attacker);
                  break;
               }
            }
         }
      }
   }

   private boolean performHealingSuck() {
      if (this.getHealth() >= this.getMaxHealth()) {
         return false;
      }

      List<ItemEntity> items = this.level().getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(10.0));
      List<Player> players = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(10.0));
      players.removeIf(p -> p.isCreative() || p.isSpectator() || !p.isAlive() || p.getUUID().equals(this.creatorUUID));
      if (items.isEmpty() && players.isEmpty()) {
         return false;
      }

      if (this.tickCount % 20 == 0) {
         this.playSound(SoundEvents.BLAZE_AMBIENT, 1.0F, 0.5F);
         this.playSound(SoundEvents.ENDER_DRAGON_FLAP, 1.0F, 1.5F);
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 3, 0.5, 0.5, 0.5, 0.05);
      }

      boolean ateItem = false;

      for (ItemEntity item : items) {
         this.pullEntity(item);
         if (this.distanceToSqr(item) < 2.5) {
            item.discard();
            this.heal(4.0F);
            this.playSound(SoundEvents.WITCH_DRINK, 1.0F, 1.0F);
            ateItem = true;
            break;
         }
      }

      if (!ateItem && !players.isEmpty()) {
         for (Player player : players) {
            this.pullEntity(player);
            if (this.distanceToSqr(player) < 3.5 && this.tickCount % 20 == 0) {
               player.hurt(this.damageSources().magic(), 2.0F);
               this.heal(6.0F);
               this.playSound(SoundEvents.WITCH_DRINK, 1.0F, 1.0F);
            }
         }
      }

      return !(this.getHealth() >= this.getMaxHealth());
   }

   private void pullEntity(Entity entity) {
      Vec3 pullVec = this.position().subtract(entity.position()).normalize().scale(0.15);
      entity.setDeltaMovement(entity.getDeltaMovement().add(pullVec.x, 0.0, pullVec.z));
      entity.hurtMarked = true;
   }

   public boolean doHurtTarget(Entity entity) {
      this.attackTimer = 10;
      this.level().broadcastEntityEvent(this, (byte)4);
      boolean flag = super.doHurtTarget(entity);
      if (flag) {
         float upKnockback = 0.4F;
         entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, upKnockback, 0.0));
         this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
      }

      return flag;
   }

   public void handleEntityEvent(byte id) {
      if (id == 4) {
         this.attackTimer = 10;
      } else {
         super.handleEntityEvent(id);
      }
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.BLAZE_HURT;
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return SoundEvents.BLAZE_DEATH;
   }

   protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
      super.dropCustomDeathLoot(level, damageSource, recentlyHit);
      this.spawnAtLocation(Items.GOLD_BLOCK);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      if (this.creatorUUID != null) {
         tag.putUUID("Creator", this.creatorUUID);
      }

      tag.putBoolean("IsHealingPhase", this.isHealingPhase);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.hasUUID("Creator")) {
         this.creatorUUID = tag.getUUID("Creator");
      }

      this.isHealingPhase = tag.getBoolean("IsHealingPhase");
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 5, event -> {
         if (this.attackTimer > 0) {
            return event.setAndContinue(ATTACK_ANIM);
         } else {
            return event.isMoving() ? event.setAndContinue(WALK_ANIM) : event.setAndContinue(IDLE_ANIM);
         }
      }));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
