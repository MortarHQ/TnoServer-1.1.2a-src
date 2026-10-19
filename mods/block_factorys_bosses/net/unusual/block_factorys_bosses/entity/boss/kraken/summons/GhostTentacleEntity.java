package net.unusual.block_factorys_bosses.entity.boss.kraken.summons;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;
import net.unusual.block_factorys_bosses.block.entity.KrakenSpawnerBlockEntity;
import net.unusual.block_factorys_bosses.entity.OwnableByAllEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GhostTentacleEntity extends Monster implements GeoEntity, OwnableByAllEntity, KrakenSpawnerBlockEntity.SpawnerTied {
   public static final EntityDataAccessor<Integer> ATTACK_COOLDOWN = SynchedEntityData.defineId(GhostTentacleEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> ATTACK_ANIMTIME = SynchedEntityData.defineId(GhostTentacleEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(
      GhostTentacleEntity.class, EntityDataSerializers.OPTIONAL_UUID
   );
   public static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.bf_br_ghost_tentacle.idle");
   public static final RawAnimation IDLE_ANIM_TWO = RawAnimation.begin().thenLoop("animation.bf_br_ghost_tentacle.idle2");
   public static final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenLoop("animation.bf_br_ghost_tentacle.attack");
   public static final RawAnimation SPAWN_ANIM = RawAnimation.begin().thenPlay("animation.bf_br_ghost_tentacle.spawn");
   public static final RawAnimation DEATH_ANIM = RawAnimation.begin().thenPlay("animation.bf_br_ghost_tentacle.despawn");
   private static final int DEATH_DURATION = 30;
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

   public GhostTentacleEntity(EntityType<GhostTentacleEntity> type, Level world) {
      super(type, world);
      this.setPersistenceRequired();
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ATTACK_COOLDOWN, 30);
      builder.define(ATTACK_ANIMTIME, 0);
      builder.define(OWNER_UUID, Optional.empty());
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.9, false) {
         protected boolean canPerformAttack(LivingEntity entity) {
            return false;
         }
      });
      this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new OwnableByAllEntity.OwnerHurtByTargetGoal(this));
      this.targetSelector.addGoal(2, new OwnableByAllEntity.OwnerHurtTargetGoal(this));
      this.targetSelector.addGoal(3, new OwnableByAllEntity.DoNotAttackOwnerGoal(this, Player.class, false, false));
      this.targetSelector.addGoal(4, new HurtByTargetGoal(this, new Class[0]));
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("attack_cooldown", (Integer)this.entityData.get(ATTACK_COOLDOWN));
      compound.putInt("attack_animtime", (Integer)this.entityData.get(ATTACK_ANIMTIME));
      if (this.getOwnerUUID() != null) {
         compound.putUUID("Owner", this.getOwnerUUID());
      }

      compound.putInt("ghost_age", this.tickCount);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("attack_cooldown")) {
         this.entityData.set(ATTACK_COOLDOWN, compound.getInt("Dataattack_cooldown"));
      }

      if (compound.contains("attack_animtime")) {
         this.entityData.set(ATTACK_ANIMTIME, compound.getInt("attack_animtime"));
      }

      if (compound.hasUUID("Owner")) {
         this.setOwnerUUID(compound.getUUID("Owner"));
      }

      this.tickCount = compound.getInt("ghost_age");
   }

   protected void tickDeath() {
      this.deathTime++;
      if (this.deathTime >= 30 && !this.level().isClientSide() && !this.isRemoved()) {
         this.level().broadcastEntityEvent(this, (byte)60);
         this.remove(RemovalReason.KILLED);
      }
   }

   public void baseTick() {
      super.baseTick();
      if (this.level() instanceof ServerLevel && this.tickCount > 200) {
         if (!this.isDeadOrDying()) {
            this.kill();
            this.playSound((SoundEvent)BossesRiseSounds.UNDYING_TENTACLE_DISAPPEARING.value());
            this.triggerAnim("main_controller", "death");
         }
      } else {
         LivingEntity target = this.getTarget();
         if ((Integer)this.getEntityData().get(ATTACK_ANIMTIME) > 0) {
            this.getEntityData().set(ATTACK_ANIMTIME, (Integer)this.getEntityData().get(ATTACK_ANIMTIME) - 1);
            if ((Integer)this.getEntityData().get(ATTACK_ANIMTIME) != 4) {
               return;
            }

            if (target == null || !(target.distanceTo(this) < 6.0)) {
               return;
            }

            this.lookAt(Anchor.EYES, target.getEyePosition());
            if (this.isAlive()) {
               if (target.isBlocking()) {
                  if (target instanceof Player player) {
                     player.getCooldowns().addCooldown(player.getUseItem().getItem(), 100);
                  }

                  return;
               }

               this.playSound((SoundEvent)BossesRiseSounds.UNDYING_TENTACLE_SMASH_GROUND.value());
               target.hurt(new DamageSource(this.level().holderOrThrow(DamageTypes.MOB_ATTACK), this), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
            }

            target.setDeltaMovement(
               new Vec3(
                  target.getDeltaMovement().x() + this.getLookAngle().x * 0.2,
                  target.getDeltaMovement().y() + 0.1,
                  target.getDeltaMovement().z() + this.getLookAngle().z * 0.2
               )
            );
         } else if ((Integer)this.getEntityData().get(ATTACK_COOLDOWN) >= 40) {
            if (target != null && target.distanceTo(this) < 4.5) {
               this.getEntityData().set(ATTACK_COOLDOWN, 0);
               this.getEntityData().set(ATTACK_ANIMTIME, 20);
            }
         } else if (target != null && target.distanceTo(this) < 16.0) {
            this.getEntityData().set(ATTACK_COOLDOWN, (Integer)this.getEntityData().get(ATTACK_COOLDOWN) + 1);
         }
      }
   }

   public static void init(RegisterSpawnPlacementsEvent event) {
      event.register(
         (EntityType)BossesRiseEntities.GHOST_TENTACLE.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         (entityType, world, reason, pos, random) -> world.getDifficulty() != Difficulty.PEACEFUL,
         Operation.REPLACE
      );
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder builder = Mob.createMobAttributes();
      builder = builder.add(Attributes.MOVEMENT_SPEED, 0.0);
      builder = builder.add(Attributes.MAX_HEALTH, 20.0);
      builder = builder.add(Attributes.ARMOR, 6.0);
      builder = builder.add(Attributes.ATTACK_DAMAGE, 5.0);
      builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
      builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
      return builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.1);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      AnimationController<GhostTentacleEntity> controller = new AnimationController(
         this,
         "main_controller",
         5,
         state -> this.getEntityData().get(ATTACK_ANIMTIME) > 0
            ? state.setAndContinue(ATTACK_ANIM)
            : (this.getId() % 2 == 0 ? state.setAndContinue(IDLE_ANIM_TWO) : state.setAndContinue(IDLE_ANIM))
      );
      controller.triggerableAnim("spawn", SPAWN_ANIM);
      controller.triggerableAnim("death", DEATH_ANIM);
      controllers.add(controller);
   }

   public boolean isPushable() {
      return false;
   }

   public void setDeltaMovement(Vec3 vec) {
   }

   protected void pushEntities() {
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }

   @Nullable
   public UUID getOwnerUUID() {
      return (UUID)((Optional)this.entityData.get(OWNER_UUID)).orElse(null);
   }

   public void setOwnerUUID(@Nullable UUID uuid) {
      this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
   }

   protected boolean shouldDropLoot() {
      return false;
   }
}
