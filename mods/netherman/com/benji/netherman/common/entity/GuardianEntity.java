package com.benji.netherman.common.entity;

import com.benji.netherman.common.block.entity.GrandDoorBlockEntity;
import com.benji.netherman.init.ModSounds;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GuardianEntity extends Monster implements GeoEntity {
   private UUID ownerUUID = null;
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(GuardianEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> IS_BUFFED = SynchedEntityData.defineId(GuardianEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> GREETING_PHASE = SynchedEntityData.defineId(GuardianEntity.class, EntityDataSerializers.INT);
   private int greetingCooldown = 0;
   public static final int STATE_NEUTRAL = 0;
   public static final int STATE_ANGRY = 1;
   public static final int STATE_WALK = 2;
   public static final int STATE_MELEE = 3;
   public static final int STATE_ATTACK_CHANGE = 4;
   public static final int STATE_ATTACK_SPEC = 5;
   public static final int STATE_SPAWNING = 6;
   public static final int STATE_GREETING = 7;
   private int spawnTicks = 0;
   private int greetingTicks = 0;
   private int greetingLoops = 0;

   public void setOwnerUUID(UUID uuid) {
      this.ownerUUID = uuid;
   }

   public UUID getOwnerUUID() {
      return this.ownerUUID;
   }

   public GuardianEntity(EntityType<? extends Monster> entityType, Level level) {
      super(entityType, level);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 100.0).add(Attributes.MOVEMENT_SPEED, 0.2).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(STATE, 0);
      builder.define(IS_BUFFED, false);
      builder.define(GREETING_PHASE, 0);
   }

   public int getGreetingPhase() {
      return (Integer)this.entityData.get(GREETING_PHASE);
   }

   public void setGreetingPhase(int phase) {
      this.entityData.set(GREETING_PHASE, phase);
   }

   public void startSpawning() {
      this.setEntityState(6);
      this.spawnTicks = 65;
   }

   public boolean isBuffed() {
      return (Boolean)this.entityData.get(IS_BUFFED);
   }

   public void setBuffed(boolean buffed) {
      this.entityData.set(IS_BUFFED, buffed);
   }

   public int getEntityState() {
      return (Integer)this.entityData.get(STATE);
   }

   public void setEntityState(int state) {
      this.entityData.set(STATE, state);
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      if (this.getEntityState() == 0) {
         int rand = this.random.nextInt(3);
         return rand == 0
            ? (SoundEvent)ModSounds.GUARDIAN_NEUTRAL_1.get()
            : (rand == 1 ? (SoundEvent)ModSounds.GUARDIAN_NEUTRAL_2.get() : (SoundEvent)ModSounds.GUARDIAN_NEUTRAL_3.get());
      }

      if (this.getEntityState() != 2 && this.getEntityState() != 1) {
         return null;
      }

      int rand = this.random.nextInt(3);
      return rand == 0
         ? (SoundEvent)ModSounds.GUARDIAN_IDLE_1.get()
         : (rand == 1 ? (SoundEvent)ModSounds.GUARDIAN_IDLE_2.get() : (SoundEvent)ModSounds.GUARDIAN_IDLE_3.get());
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return this.random.nextBoolean() ? (SoundEvent)ModSounds.GUARDIAN_DAMAGE_1.get() : (SoundEvent)ModSounds.GUARDIAN_DAMAGE_2.get();
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound((SoundEvent)ModSounds.GUARDIAN_WALK.get(), 1.0F, 1.0F);
   }

   public float getVoicePitch() {
      return 0.8F + this.random.nextFloat() * 0.4F;
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("GreetingCooldown", this.greetingCooldown);
      if (this.ownerUUID != null) {
         compound.putUUID("SummonerUUID", this.ownerUUID);
      }
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.greetingCooldown = compound.getInt("GreetingCooldown");
      if (compound.hasUUID("SummonerUUID")) {
         this.ownerUUID = compound.getUUID("SummonerUUID");
      }
   }

   public void die(DamageSource cause) {
      if (!this.level().isClientSide()) {
         AABB searchBox = this.getBoundingBox().inflate(30.0);

         for (GuardianEntity guardian : this.level().getEntitiesOfClass(GuardianEntity.class, searchBox, e -> e != this && e.isAlive())) {
            guardian.playSound((SoundEvent)ModSounds.WEAKNESS.get(), 5.0F, guardian.getVoicePitch());
         }
      }

      super.die(cause);
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide()) {
         if (this.getEntityState() == 6) {
            BlockState state = Blocks.POLISHED_BLACKSTONE_BRICKS.defaultBlockState();

            for (int i = 0; i < 4; i++) {
               double dx = (this.random.nextDouble() - 0.5) * 6.0;
               double dz = (this.random.nextDouble() - 0.5) * 6.0;
               this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), this.getX() + dx, this.getY(), this.getZ() + dz, 0.0, 0.3, 0.0);
            }
         }
      } else {
         if (this.spawnTicks > 0) {
            if (this.spawnTicks == 65) {
               this.playSound(SoundEvents.WARDEN_EMERGE, 1.0F, 1.0F);
            }

            this.spawnTicks--;
            if (this.spawnTicks == 0) {
               this.setEntityState(0);
            }

            return;
         }

         if (this.tickCount % 10 == 0) {
            AABB searchBox = this.getBoundingBox().inflate(30.0);
            List<WelcomerEntity> welcomers = this.level().getEntitiesOfClass(WelcomerEntity.class, searchBox);
            boolean hasBuff = false;

            for (WelcomerEntity welcomer : welcomers) {
               if (welcomer.isCasting()) {
                  hasBuff = true;
                  break;
               }
            }

            this.setBuffed(hasBuff);
            if (this.getEntityState() == 0 && this.getTarget() == null && this.greetingCooldown == 0) {
               List<GhastlyEntity> ghastlies = this.level().getEntitiesOfClass(GhastlyEntity.class, searchBox);
               boolean hasTamedGhastly = false;

               for (GhastlyEntity g : ghastlies) {
                  if (g.isTame()) {
                     hasTamedGhastly = true;
                     break;
                  }
               }

               if (hasTamedGhastly) {
                  this.setEntityState(7);
                  this.setGreetingPhase(1);
                  this.greetingTicks = 20;
                  this.greetingLoops = 0;
                  this.greetingCooldown = 24000;
               }
            }
         }

         if (this.getEntityState() == 7) {
            if (this.getTarget() != null) {
               this.setEntityState(0);
               this.setGreetingPhase(0);
               return;
            }

            for (GhastlyEntity g : this.level().getEntitiesOfClass(GhastlyEntity.class, this.getBoundingBox().inflate(15.0))) {
               if (g.isTame()) {
                  this.getLookControl().setLookAt(g, 30.0F, 30.0F);
                  this.setYRot(this.getYHeadRot());
                  this.yBodyRot = this.getYHeadRot();
                  break;
               }
            }

            if (this.greetingTicks > 0) {
               this.greetingTicks--;
               if (this.getGreetingPhase() == 2 && this.greetingTicks == 59) {
                  SoundEvent roarSound = switch (this.greetingLoops) {
                     case 0 -> (SoundEvent)ModSounds.GUARDIAN_ROAR_1.get();
                     case 1 -> (SoundEvent)ModSounds.GUARDIAN_ROAR_2.get();
                     default -> {
                        if (!this.level().isClientSide()) {
                           BlockPos center = this.blockPosition();
                           BlockPos.betweenClosedStream(center.offset(-20, -20, -20), center.offset(20, 20, 20)).forEach(pos -> {
                              if (this.level().getBlockEntity(pos) instanceof GrandDoorBlockEntity door && door.bossCooldown <= 0) {
                                 door.togglePermanent();
                              }
                           });
                        }

                        yield (SoundEvent)ModSounds.GUARDIAN_ROAR_3.get();
                     }
                  };
                  this.playSound(roarSound, 1.5F, this.getVoicePitch());
               }

               if (this.greetingTicks == 0) {
                  switch (this.getGreetingPhase()) {
                     case 1:
                        this.setGreetingPhase(2);
                        this.greetingTicks = 60;
                        break;
                     case 2:
                        this.setGreetingPhase(3);
                        this.greetingTicks = 20;
                        break;
                     case 3:
                        this.greetingLoops++;
                        if (this.greetingLoops >= 3) {
                           this.setEntityState(0);
                           this.setGreetingPhase(0);
                        } else {
                           this.setGreetingPhase(4);
                           this.greetingTicks = 20;
                        }
                        break;
                     case 4:
                        this.setGreetingPhase(1);
                        this.greetingTicks = 20;
                  }
               }
            }
         }
      }
   }

   protected void registerGoals() {
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector
         .addGoal(
            2,
            new NearestAttackableTargetGoal(
               this,
               Mob.class,
               0,
               false,
               false,
               entity -> {
                  if (this.ownerUUID == null) {
                     return false;
                  } else if (entity instanceof Player) {
                     return false;
                  } else {
                     return entity instanceof GuardianEntity otherGuardian
                           && otherGuardian.getOwnerUUID() != null
                           && otherGuardian.getOwnerUUID().equals(this.ownerUUID)
                        ? false
                        : entity instanceof Enemy;
                  }
               }
            )
         );
      this.goalSelector.addGoal(1, new GuardianBehaviorGoal(this));
   }

   public void registerControllers(ControllerRegistrar controllers) {
      AnimationController<GuardianEntity> controller = new AnimationController(this, "controller", 5, event -> {
         switch (this.getEntityState()) {
            case 1:
               return event.setAndContinue(RawAnimation.begin().thenPlay("angry_mode"));
            case 2:
               return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            case 3:
               return event.setAndContinue(RawAnimation.begin().thenLoop("attack_loop"));
            case 4:
               return event.setAndContinue(RawAnimation.begin().thenPlay("attack_change"));
            case 5:
               return event.setAndContinue(RawAnimation.begin().thenPlay("attack_spec"));
            case 6:
               return event.setAndContinue(RawAnimation.begin().thenPlay("spawn"));
            case 7:
               return switch (this.getGreetingPhase()) {
                  case 1 -> event.setAndContinue(RawAnimation.begin().thenPlay("roar_open"));
                  case 2 -> event.setAndContinue(RawAnimation.begin().thenLoop("roar_loop"));
                  case 3 -> event.setAndContinue(RawAnimation.begin().thenPlay("roar_close"));
                  default -> event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
               };
            default:
               return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
         }
      });
      if (this.getEntityState() == 6) {
         controller.setAnimation(RawAnimation.begin().thenPlay("spawn"));
      }

      controllers.add(controller);
   }

   public void performMeleeAttack() {
      LivingEntity target = this.getTarget();
      double attackRadiusSq = this.isBuffed() ? 64.0 : 16.0;
      float damage = this.isBuffed() ? 8.0F : 3.0F;
      if (target != null && this.distanceToSqr(target) <= attackRadiusSq) {
         target.invulnerableTime = 0;
         target.hurt(this.damageSources().mobAttack(this), damage);
      }
   }

   public void performMegaPunch() {
      int rand = this.random.nextInt(3);
      SoundEvent specSound = rand == 0
         ? (SoundEvent)ModSounds.SPEC_ATTACK_1.get()
         : (rand == 1 ? (SoundEvent)ModSounds.SPEC_ATTACK_2.get() : (SoundEvent)ModSounds.SPEC_ATTACK_3.get());
      this.playSound(specSound, 1.5F, 1.0F);
      double inflateSize = this.isBuffed() ? 20.0 : 10.0;
      double attackRadiusSq = this.isBuffed() ? 400.0 : 100.0;
      float damage = this.isBuffed() ? 15.0F : 10.0F;
      AABB hitBox = this.getBoundingBox().inflate(inflateSize);

      for (LivingEntity e : this.level()
         .getEntitiesOfClass(
            LivingEntity.class,
            hitBox,
            ex -> ex != this
               && !(ex instanceof WelcomerEntity)
               && !(ex instanceof ManipulatorEntity)
               && !(ex instanceof BelieverEntity)
               && !(ex instanceof GuardianEntity)
               && !(ex instanceof StatueBossunitEntity)
               && !(ex instanceof StatueEntity)
               && !(ex instanceof AzazelEntity)
         )) {
         if (this.distanceToSqr(e) <= attackRadiusSq) {
            e.hurt(this.damageSources().mobAttack(this), damage);
            e.setDeltaMovement(e.getDeltaMovement().add(0.0, 0.9, 0.0));
            e.hurtMarked = true;
         }
      }

      this.level().broadcastEntityEvent(this, (byte)100);
   }

   public void handleEntityEvent(byte id) {
      if (id == 100) {
         for (int i = 0; i < 360; i += 10) {
            double rad = Math.toRadians(i);
            double x = Math.cos(rad);
            double z = Math.sin(rad);
            this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY() + 0.2, this.getZ(), x * 0.7, 0.05, z * 0.7);
         }
      } else {
         super.handleEntityEvent(id);
      }
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
