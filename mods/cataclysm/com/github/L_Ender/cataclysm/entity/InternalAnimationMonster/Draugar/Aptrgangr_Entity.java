package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar;

import com.github.L_Ender.cataclysm.blocks.PointedIcicleBlock;
import com.github.L_Ender.cataclysm.client.particle.Options.RingParticleOptions;
import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.AI.EntityAINearestTarget3D;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Internal_Animation_Monster;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalMoveGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.IHoldEntity;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Axe_Blade_Entity;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.message.MessageEntityCameraSwitch;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import com.github.L_Ender.cataclysm.util.EntityUtil;
import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public class Aptrgangr_Entity extends Internal_Animation_Monster implements IHoldEntity {
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState swingrightAnimationState = new AnimationState();
   public AnimationState smashAnimationState = new AnimationState();
   public AnimationState chargestartAnimationState = new AnimationState();
   public AnimationState chargeAnimationState = new AnimationState();
   public AnimationState chargeendAnimationState = new AnimationState();
   public AnimationState chargehitAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   private int earthquake_cooldown = 0;
   public static final int EARTHQUAKE_COOLDOWN = 80;
   private boolean chubu = false;
   private int charge_cooldown = 0;
   public static final int CHARGE_COOLDOWN = 160;
   protected final int NATURE_HEAL_COOLDOWN = 80;
   private int self_regen;

   public Aptrgangr_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.xpReward = 60;
      this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setConfigattribute(this, CMCommonConfig.Aptrgangr.healthMultiplier, CMCommonConfig.Aptrgangr.attackMultiplier);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0, 80));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new EntityAINearestTarget3D(this, Player.class, false));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, false));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, SnowGolem.class, false));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
      this.goalSelector.addGoal(4, new InternalMoveGoal(this, false, 1.0));
      this.goalSelector.addGoal(3, new InternalAttackGoal(this, 0, 1, 0, 40, 15, 4.5F) {
         @Override
         public boolean canUse() {
            return super.canUse() && Aptrgangr_Entity.this.getRandom().nextFloat() * 100.0F < 22.0F;
         }
      });
      this.goalSelector.addGoal(3, new InternalAttackGoal(this, 0, 2, 0, 40, 10, 6.0F) {
         @Override
         public boolean canUse() {
            return super.canUse() && Aptrgangr_Entity.this.getRandom().nextFloat() * 100.0F < 16.0F && Aptrgangr_Entity.this.earthquake_cooldown <= 0;
         }

         @Override
         public void stop() {
            super.stop();
            Aptrgangr_Entity.this.earthquake_cooldown = 80;
         }
      });
      this.goalSelector
         .addGoal(
            3,
            new InternalAttackGoal(this, 0, 2, 0, 40, 10, 12.0F) {
               @Override
               public boolean canUse() {
                  LivingEntity target = this.entity.getTarget();
                  return super.canUse()
                     && target != null
                     && Aptrgangr_Entity.this.getRandom().nextFloat() * 100.0F < 22.0F
                     && this.entity.distanceTo(target) > 6.0F
                     && Aptrgangr_Entity.this.earthquake_cooldown <= 0;
               }

               @Override
               public void stop() {
                  super.stop();
                  Aptrgangr_Entity.this.earthquake_cooldown = 80;
               }
            }
         );
      this.goalSelector.addGoal(3, new InternalAttackGoal(this, 0, 3, 4, 24, 24, 15.0F) {
         @Override
         public boolean canUse() {
            return super.canUse() && Aptrgangr_Entity.this.getRandom().nextFloat() * 100.0F < 8.0F && Aptrgangr_Entity.this.charge_cooldown <= 0;
         }
      });
      this.goalSelector.addGoal(2, new InternalStateGoal(this, 4, 4, 5, 40, 0) {
         @Override
         public void tick() {
            if (this.entity.onGround()) {
               Vec3 vector3d = this.entity.getDeltaMovement();
               float f = this.entity.getYRot() * (float) (Math.PI / 180.0);
               Vec3 vector3d1 = new Vec3(-Mth.sin(f), this.entity.getDeltaMovement().y, Mth.cos(f)).scale(1.0).add(vector3d.scale(0.5));
               this.entity.setDeltaMovement(vector3d1.x, this.entity.getDeltaMovement().y, vector3d1.z);
            }
         }

         @Override
         public boolean canContinueToUse() {
            return super.canContinueToUse() && !Aptrgangr_Entity.this.chubu;
         }

         @Override
         public void stop() {
            if (Aptrgangr_Entity.this.chubu) {
               this.entity.setAttackState(6);
               Aptrgangr_Entity.this.chubu = false;
            } else {
               super.stop();
            }
         }
      });
      this.goalSelector.addGoal(1, new InternalStateGoal(this, 5, 5, 0, 23, 0) {
         @Override
         public void stop() {
            super.stop();
            Aptrgangr_Entity.this.charge_cooldown = 160;
         }
      });
      this.goalSelector.addGoal(0, new InternalStateGoal(this, 6, 6, 0, 18, 0) {
         @Override
         public void stop() {
            super.stop();
            Aptrgangr_Entity.this.charge_cooldown = 160;
         }
      });
   }

   public static Builder aptrgangr() {
      return Monster.createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 30.0)
         .add(Attributes.MOVEMENT_SPEED, 0.28F)
         .add(Attributes.ATTACK_DAMAGE, 18.0)
         .add(Attributes.MAX_HEALTH, 160.0)
         .add(Attributes.ARMOR, 10.0)
         .add(Attributes.STEP_HEIGHT, 1.25)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   public ItemEntity spawnAtLocation(ItemStack stack) {
      ItemEntity itementity = this.spawnAtLocation(stack, 0.0F);
      if (itementity != null) {
         itementity.setGlowingTag(true);
         itementity.setExtendedLifetime();
      }

      return itementity;
   }

   public boolean hurt(DamageSource source, float damage) {
      if (!this.getPassengers().isEmpty() && this.getAttackState() == 4 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return false;
      }

      if (source.is(ModTag.BLOCK_SELF_REGEN)) {
         this.self_regen = 80;
      }

      return super.hurt(source, damage);
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   public AnimationState getAnimationState(String input) {
      if (input == "swing_right") {
         return this.swingrightAnimationState;
      } else if (input == "smash") {
         return this.smashAnimationState;
      } else if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "charge_start") {
         return this.chargestartAnimationState;
      } else if (input == "charge") {
         return this.chargeAnimationState;
      } else if (input == "charge_end") {
         return this.chargeendAnimationState;
      } else if (input == "charge_hit") {
         return this.chargehitAnimationState;
      } else {
         return input == "death" ? this.deathAnimationState : new AnimationState();
      }
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
      if (ATTACK_STATE.equals(p_21104_)) {
         switch (this.getAttackState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.swingrightAnimationState.startIfStopped(this.tickCount);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.smashAnimationState.startIfStopped(this.tickCount);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.chargestartAnimationState.startIfStopped(this.tickCount);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.chargeAnimationState.startIfStopped(this.tickCount);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.chargeendAnimationState.startIfStopped(this.tickCount);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.chargehitAnimationState.startIfStopped(this.tickCount);
               break;
            case 7:
               this.stopAllAnimationStates();
               this.deathAnimationState.startIfStopped(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.swingrightAnimationState.stop();
      this.smashAnimationState.stop();
      this.chargestartAnimationState.stop();
      this.chargeAnimationState.stop();
      this.chargeendAnimationState.stop();
      this.chargehitAnimationState.stop();
      this.deathAnimationState.stop();
   }

   @Override
   public void die(DamageSource p_21014_) {
      super.die(p_21014_);
      this.setAttackState(7);
   }

   protected void dropCustomDeathLoot(ServerLevel p_348503_, DamageSource p_34697_, boolean p_34699_) {
      super.dropCustomDeathLoot(p_348503_, p_34697_, p_34699_);
      if (p_34697_.getEntity() instanceof Creeper creeper && creeper.canDropMobsSkull()) {
         ItemStack itemstack = new ItemStack((ItemLike)ModItems.APTRGANGR_HEAD.get());
         creeper.increaseDroppedSkulls();
         this.spawnAtLocation(itemstack);
      }
   }

   @Override
   public int deathtimer() {
      return 60;
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
   }

   public float NatureRegen() {
      return (float)CMCommonConfig.Aptrgangr.natureHeal;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level().isClientSide()) {
         this.idleAnimationState.animateWhen(!this.walkAnimation.isMoving() && this.getAttackState() == 0, this.tickCount);
      } else {
         LivingEntity target = this.getTarget();
         if (!this.isNoAi() && this.self_regen <= 0 && !this.isNoAi() && this.NatureRegen() > 0.0F && target == null && this.tickCount % 20 == 0) {
            this.heal(this.NatureRegen());
         }
      }

      if (this.self_regen > 0) {
         this.self_regen--;
      }

      if (this.earthquake_cooldown > 0) {
         this.earthquake_cooldown--;
      }

      if (this.charge_cooldown > 0) {
         this.charge_cooldown--;
      }

      if (!this.getPassengers().isEmpty() && ((Entity)this.getPassengers().get(0)).isShiftKeyDown() && this.getAttackState() == 4) {
         ((Entity)this.getPassengers().get(0)).setShiftKeyDown(false);
      }
   }

   public void aiStep() {
      super.aiStep();
      if (this.getAttackState() == 1 && this.attackTicks == 15) {
         this.playSound((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 0.7F);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.06F, 0, 20);
         this.AreaAttack(5.75F, 5.75F, 120.0F, 1.0F, 120, true);
      }

      if (this.getAttackState() == 2) {
         if (this.attackTicks == 11) {
            this.playSound((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 0.7F);
         }

         if (this.attackTicks == 15) {
            this.AreaAttack(6.5F, 6.5F, 60.0F, 1.0F, 120, false);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.15F, 0, 20);
            this.playSound(SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.8F);
            this.Makeparticle(0.6F, 5.0F, 0.0F);
            double theta = this.yBodyRot * (Math.PI / 180.0);
            theta += Math.PI / 2;
            double vecX = Math.cos(theta);
            double vecZ = Math.sin(theta);
            int numberOfSkulls = 5;
            float angleStep = 30.0F;

            for (int i = 0; i < numberOfSkulls; i++) {
               float angle = this.yBodyRot + (i - numberOfSkulls / 2.0F) * angleStep;
               float rad = (float)Math.toRadians(angle);
               double dx = -Math.sin(rad);
               double dz = Math.cos(rad);
               Axe_Blade_Entity witherskull = new Axe_Blade_Entity(this, dx, 0.0, dz, this.level(), (float)CMCommonConfig.Aptrgangr.AxeBladeDamage, angle);
               double spawnX = this.getX() + vecX * 5.0;
               double spawnY = this.getY(0.15);
               double spawnZ = this.getZ() + vecZ * 5.0;
               witherskull.setPos(spawnX, spawnY, spawnZ);
               this.level().addFreshEntity(witherskull);
            }
         }
      }

      if (this.getAttackState() == 4) {
         this.ChargeGrab(0.0, 0.0, 0.5, 0.1F, 0, true);
         if (this.horizontalCollision) {
            this.chubu = true;
            if (!this.level().isClientSide) {
               this.Icicle_Crash();
            }
         }

         if (this.level().isClientSide) {
            double x = this.getX();
            double y = this.getY() + this.getBbHeight() / 2.0F;
            double z = this.getZ();
            float yaw = (float)Math.toRadians(-this.getYRot());
            float yaw2 = (float)Math.toRadians(-this.getYRot() + 180.0F);
            float pitch = (float)Math.toRadians(-this.getXRot());
            this.level().addParticle(new RingParticleOptions(yaw, pitch, 40, 86, 236, 204, 1.0F, 50.0F, false, 2), x, y, z, 0.0, 0.0, 0.0);
            this.level().addParticle(new RingParticleOptions(yaw2, pitch, 40, 86, 236, 204, 1.0F, 50.0F, false, 2), x, y, z, 0.0, 0.0, 0.0);
         }
      }

      if (this.getAttackState() == 5 && this.attackTicks == 4) {
         this.playSound((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 0.7F);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.06F, 0, 20);
         this.UpperAreaAttack(6.5F, 6.5F, 60.0F, 1.0F, 120, true);
      }

      if (this.getAttackState() == 6 && this.attackTicks == 1) {
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.1F, 0, 20);
         this.playSound(SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.9F);
      }
   }

   private void Makeparticle(float size, float vec, float math) {
      if (this.level().isClientSide) {
         float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
         float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
         double theta = this.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);

         for (int i1 = 0; i1 < 80 + this.random.nextInt(12); i1++) {
            double DeltaMovementX = this.getRandom().nextGaussian() * 0.07;
            double DeltaMovementY = this.getRandom().nextGaussian() * 0.07;
            double DeltaMovementZ = this.getRandom().nextGaussian() * 0.07;
            float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
            double extraX = size * Mth.sin((float)(Math.PI + angle));
            double extraY = 0.3F;
            double extraZ = size * Mth.cos(angle);
            int hitX = Mth.floor(this.getX() + vec * vecX + extraX);
            int hitY = Mth.floor(this.getY());
            int hitZ = Mth.floor(this.getZ() + vec * vecZ + extraZ);
            BlockPos hit = new BlockPos(hitX, hitY, hitZ);
            BlockState block = this.level().getBlockState(hit.below());
            if (block.getRenderShape() != RenderShape.INVISIBLE) {
               this.level()
                  .addParticle(
                     new BlockParticleOption(ParticleTypes.BLOCK, block),
                     this.getX() + vec * vecX + extraX + f * math,
                     this.getY() + extraY,
                     this.getZ() + vec * vecZ + extraZ + f1 * math,
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            }
         }

         this.level()
            .addParticle(
               new RingParticleOptions(0.0F, (float) (Math.PI / 2), 30, 255, 255, 255, 1.0F, 20.0F, false, 2),
               this.getX() + vec * vecX + f * math,
               this.getY() + 0.2F,
               this.getZ() + vec * vecZ + f1 * math,
               0.0,
               0.0,
               0.0
            );
      }
   }

   private void AreaAttack(float range, float height, float arc, float damage, int shieldbreakticks, boolean knockback) {
      List<LivingEntity> entitiesHit = this.getEntityLivingBaseNearby(range, height, range, range);
      if (!this.level().isClientSide) {
         DamageSource damagesource = this.damageSources().mobAttack(this);

         for (LivingEntity entityHit : entitiesHit) {
            float entityHitAngle = (float)((Math.atan2(entityHit.getZ() - this.getZ(), entityHit.getX() - this.getX()) * (180.0 / Math.PI) - 90.0) % 360.0);
            float entityAttackingAngle = this.yBodyRot % 360.0F;
            if (entityHitAngle < 0.0F) {
               entityHitAngle += 360.0F;
            }

            if (entityAttackingAngle < 0.0F) {
               entityAttackingAngle += 360.0F;
            }

            float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
            float entityHitDistance = (float)Math.sqrt(
               (entityHit.getZ() - this.getZ()) * (entityHit.getZ() - this.getZ()) + (entityHit.getX() - this.getX()) * (entityHit.getX() - this.getX())
            );
            if ((
                  entityHitDistance <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F
                     || entityRelativeAngle >= 360.0F - arc / 2.0F
                     || entityRelativeAngle <= -360.0F + arc / 2.0F
               )
               && !this.isAlliedTo(entityHit)
               && !(entityHit instanceof Aptrgangr_Entity)
               && entityHit != this) {
               boolean hurt = entityHit.hurt(damagesource, (float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage));
               if (entityHit.isDamageSourceBlocked(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
                  EntityUtil.disableShield(player, shieldbreakticks);
               }

               double d0 = entityHit.getX() - this.getX();
               double d1 = entityHit.getZ() - this.getZ();
               double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
               if (hurt && knockback) {
                  entityHit.push(d0 / d2 * 2.75, 0.15, d1 / d2 * 2.75);
               }
            }
         }
      }
   }

   private void UpperAreaAttack(float range, float height, float arc, float damage, int shieldbreakticks, boolean knockback) {
      List<LivingEntity> entitiesHit = this.getEntityLivingBaseNearby(range, height, range, range);
      if (!this.level().isClientSide) {
         for (LivingEntity entityHit : entitiesHit) {
            float entityHitAngle = (float)((Math.atan2(entityHit.getZ() - this.getZ(), entityHit.getX() - this.getX()) * (180.0 / Math.PI) - 90.0) % 360.0);
            float entityAttackingAngle = this.yBodyRot % 360.0F;
            if (entityHitAngle < 0.0F) {
               entityHitAngle += 360.0F;
            }

            if (entityAttackingAngle < 0.0F) {
               entityAttackingAngle += 360.0F;
            }

            float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
            float entityHitDistance = (float)Math.sqrt(
               (entityHit.getZ() - this.getZ()) * (entityHit.getZ() - this.getZ()) + (entityHit.getX() - this.getX()) * (entityHit.getX() - this.getX())
            );
            if ((
                  entityHitDistance <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F
                     || entityRelativeAngle >= 360.0F - arc / 2.0F
                     || entityRelativeAngle <= -360.0F + arc / 2.0F
               )
               && !this.isAlliedTo(entityHit)
               && !(entityHit instanceof Aptrgangr_Entity)
               && entityHit != this) {
               DamageSource damagesource = this.damageSources().mobAttack(this);
               boolean hurt = entityHit.hurt(damagesource, (float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage));
               if (entityHit.isDamageSourceBlocked(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
                  EntityUtil.disableShield(player, shieldbreakticks);
               }

               double d0 = entityHit.getX() - this.getX();
               double d1 = entityHit.getZ() - this.getZ();
               double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
               if (hurt && knockback) {
                  entityHit.setDeltaMovement(entityHit.getDeltaMovement().add(0.0, 0.8F, 0.0));
               }
            }
         }
      }
   }

   private void ChargeGrab(double inflateXZ, double inflateY, double range, float damage, int shieldbreakticks, boolean maledictio) {
      double yaw = Math.toRadians(this.getYRot() + 90.0F);
      double xExpand = range * Math.cos(yaw);
      double zExpand = range * Math.sin(yaw);
      AABB attackRange = this.getBoundingBox().inflate(inflateXZ, inflateY, inflateXZ).expandTowards(xExpand, 0.0, zExpand);

      for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, attackRange)) {
         if (!this.isAlliedTo(entity) && entity != this && this.getPassengers().isEmpty()) {
            DamageSource damagesource = maledictio ? CMDamageTypes.causeMaledictioDamage(this) : this.damageSources().mobAttack(this);
            boolean flag = entity.hurt(damagesource, damage);
            if (entity.isDamageSourceBlocked(damagesource) && entity instanceof Player player && shieldbreakticks > 0) {
               EntityUtil.disableShield(player, shieldbreakticks);
            }

            if (flag && !entity.getType().is(ModTag.IGNIS_CANT_POKE) && entity.isAlive()) {
               if (entity.isShiftKeyDown()) {
                  entity.setShiftKeyDown(false);
               }

               if (!this.level().isClientSide) {
                  entity.startRiding(this, true);
                  PacketDistributor.sendToPlayersTrackingEntityAndSelf(
                     entity, new MessageEntityCameraSwitch.ThridPerson(entity.getId()), new CustomPacketPayload[0]
                  );
               }
            }
         }
      }
   }

   private void Icicle_Crash() {
      if (this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
         BlockPos ceil = this.blockPosition().offset(0, 5, 0);

         while (
            (!this.level().getBlockState(ceil).isSolid() || this.level().getBlockState(ceil).getBlock() == ModBlocks.POINTED_ICICLE.get())
               && ceil.getY() < this.level().getMaxBuildHeight()
         ) {
            ceil = ceil.above();
         }

         int i = 8;
         int j = 8;
         int k = 8;

         for (BlockPos blockpos1 : BlockPos.betweenClosed(ceil.offset(-8, -8, -8), ceil.offset(8, 8, 8))) {
            if (this.level().getBlockState(blockpos1).getBlock() instanceof Fallable) {
               if (this.isHangingIcicle(blockpos1)) {
                  while (this.isHangingIcicle(blockpos1.above()) && blockpos1.getY() < this.level().getMaxBuildHeight()) {
                     blockpos1 = blockpos1.above();
                  }

                  if (this.isHangingIcicle(blockpos1)) {
                     Vec3 vec3 = Vec3.atBottomCenterOf(blockpos1);
                     FallingBlockEntity.fall(this.level(), BlockPos.containing(vec3.x, vec3.y, vec3.z), this.level().getBlockState(blockpos1));
                  }
               } else {
                  this.level().scheduleTick(blockpos1, this.level().getBlockState(blockpos1).getBlock(), 2);
               }
            }
         }
      }
   }

   private boolean isHangingIcicle(BlockPos pos) {
      return this.level().getBlockState(pos).getBlock() instanceof PointedIcicleBlock
         && this.level().getBlockState(pos).getValue(PointedIcicleBlock.TIP_DIRECTION) == Direction.DOWN;
   }

   public void travel(Vec3 travelVector) {
      super.travel(travelVector);
   }

   @Nullable
   public LivingEntity getControllingPassenger() {
      return null;
   }

   public boolean canRiderInteract() {
      return true;
   }

   public void positionRider(Entity passenger, MoveFunction moveFunc) {
      double theta = this.yBodyRot * (Math.PI / 180.0);
      theta += Math.PI / 2;
      double vecX = Math.cos(theta);
      double vecZ = Math.sin(theta);
      Vec3 vec3 = this.getPassengerRidingPosition(passenger);
      Vec3 vec31 = passenger.getVehicleAttachmentPoint(this);
      double px = vec3.x - vec31.x + 0.7F * vecX;
      double pz = vec3.z - vec31.z + 0.7F * vecZ;
      double y = this.getY() + 0.6;
      if (this.hasPassenger(passenger)) {
         if (this.getAttackState() == 6) {
            if (this.attackTicks == 1) {
               if (passenger instanceof LivingEntity living) {
                  DamageSource damagesource = CMDamageTypes.causeMaledictioDamage(this);
                  boolean flag = living.hurt(damagesource, (float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.5));
                  if (flag) {
                     this.playSound(SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.9F);
                  }
               }

               if (!this.level().isClientSide) {
                  PacketDistributor.sendToPlayersTrackingEntityAndSelf(
                     passenger, new MessageEntityCameraSwitch.FirstPerson(passenger.getId()), new CustomPacketPayload[0]
                  );
                  passenger.stopRiding();
               }
            }
         } else if (this.getAttackState() == 5) {
            if (this.attackTicks == 1 && !this.level().isClientSide) {
               PacketDistributor.sendToPlayersTrackingEntityAndSelf(
                  passenger, new MessageEntityCameraSwitch.FirstPerson(passenger.getId()), new CustomPacketPayload[0]
               );
               passenger.stopRiding();
            }
         } else if (this.getAttackState() != 4 && !this.level().isClientSide) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(
               passenger, new MessageEntityCameraSwitch.FirstPerson(passenger.getId()), new CustomPacketPayload[0]
            );
            passenger.stopRiding();
         }

         moveFunc.accept(passenger, px, y, pz);
      }
   }

   public Vec3 getDismountLocationForPassenger(LivingEntity p_29487_) {
      Direction direction = this.getMotionDirection();
      if (direction.getAxis() == Axis.Y) {
         return super.getDismountLocationForPassenger(p_29487_);
      }

      int[][] aint = DismountHelper.offsetsForDirection(direction);
      BlockPos blockpos = this.blockPosition();
      MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos();
      UnmodifiableIterator var6 = p_29487_.getDismountPoses().iterator();

      while (var6.hasNext()) {
         Pose pose = (Pose)var6.next();
         AABB aabb = p_29487_.getLocalBoundsForPose(pose);

         for (int[] aint1 : aint) {
            blockpos$mutableblockpos.set(blockpos.getX() + aint1[0], blockpos.getY(), blockpos.getZ() + aint1[1]);
            double d0 = this.level().getBlockFloorHeight(blockpos$mutableblockpos);
            if (DismountHelper.isBlockFloorValid(d0)) {
               Vec3 vec3 = Vec3.upFromBottomCenterOf(blockpos$mutableblockpos, d0);
               if (DismountHelper.canDismountTo(this.level(), p_29487_, aabb.move(vec3))) {
                  p_29487_.setPose(pose);
                  return vec3;
               }
            }
         }
      }

      return super.getDismountLocationForPassenger(p_29487_);
   }

   public boolean shouldRiderSit() {
      return false;
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.isAlliedTo(entityIn)) {
         return true;
      } else {
         return !entityIn.getType().is(ModTag.TEAM_MALEDICTUS) ? false : this.getTeam() == null && entityIn.getTeam() == null;
      }
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.APTRGANGR_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.APTRGANGR_DEATH.get();
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)ModSounds.APTRGANGR_IDLE.get();
   }

   protected BodyRotationControl createBodyControl() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation createNavigation(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   public boolean canBeAffected(MobEffectInstance p_34192_) {
      return p_34192_.getEffect() != ModEffect.EFFECTSTUN && p_34192_.getEffect() != ModEffect.EFFECTABYSSAL_CURSE.get() && super.canBeAffected(p_34192_);
   }

   public boolean removeWhenFarAway(double p_21542_) {
      return false;
   }

   protected boolean shouldDespawnInPeaceful() {
      return false;
   }

   protected boolean canRide(Entity p_31508_) {
      return false;
   }
}
