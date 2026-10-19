package net.unusual.block_factorys_bosses.entity.boss.sandworm;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerVariables;
import net.unusual.block_factorys_bosses.client.camera.client.CinematicAnimationController;
import net.unusual.block_factorys_bosses.configuration.ServerConfiguration;
import net.unusual.block_factorys_bosses.entity.SandColumnEntity;
import net.unusual.block_factorys_bosses.entity.boss.AbstractStateBossEntity;
import net.unusual.block_factorys_bosses.entity.boss.part.AbstractEntityPartParent;
import net.unusual.block_factorys_bosses.entity.boss.part.AbstractGeoEntityPart;
import net.unusual.block_factorys_bosses.entity.projectile.PoisonSpitPrEntity;
import net.unusual.block_factorys_bosses.geckolib.DangerZonesProvider;
import net.unusual.block_factorys_bosses.geckolib.ServerAnimationPlayer;
import net.unusual.block_factorys_bosses.geckolib.boss.worm.SandwormModel;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.random.WeightedPool;
import net.unusual.block_factorys_bosses.state.LambdaDataState;
import net.unusual.block_factorys_bosses.state.State;
import net.unusual.block_factorys_bosses.state.StateController;
import net.unusual.block_factorys_bosses.state.StateData;
import net.unusual.block_factorys_bosses.state.StateGoal;
import net.unusual.block_factorys_bosses.state.StateRef;
import net.unusual.block_factorys_bosses.state.StateRegistry;
import net.unusual.block_factorys_bosses.state.StateSequence;
import net.unusual.block_factorys_bosses.util.SpatialUtil;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.loading.math.MathParser;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SandwormEntity extends AbstractStateBossEntity implements AbstractEntityPartParent<SandwormEntity, SandwormEntityPart>, DangerZonesProvider {
   private static final String TAG_RECENT_DAMAGE = "block_factorys_bosses:recent_damage";
   private static final String TAG_RECENT_DAMAGE_TIMER = "block_factorys_bosses:recent_damage_timer";
   private static final String TAG_HOME_POSITION = "block_factorys_bosses:home_position";
   private final ServerAnimationPlayer<SandwormEntity> serverAnimationPlayer = new ServerAnimationPlayer(new SandwormModel(), this);
   private static final String[] PART_ANCHOR_BONE_NAMES = new String[]{
      "body1_fx",
      "body2_fx",
      "body3_fx",
      "body4_fx",
      "body5_fx",
      "body6_fx",
      "body7_fx",
      "body8_fx",
      "body9_fx",
      "body10_fx",
      "body11_fx",
      "body12_fx",
      "body13_fx",
      "body14_fx",
      "body15_fx",
      "body16_fx"
   };
   private static final String[] PART_BODY_BONE_NAMES = new String[]{
      "body1", "body2", "body3", "body4", "body5", "body6", "body7", "body8", "body9", "body10", "body11", "body12", "body13", "body14", "body15", "body16"
   };
   private final SandwormEntityPart[] parts = new SandwormEntityPart[PART_ANCHOR_BONE_NAMES.length];
   public static final int FIGHT_PHASE = 0;
   public static final int DYING_PHASE = 1;
   public static final EntityDataAccessor<Boolean> DATA_IS_HIDDEN = SynchedEntityData.defineId(SandwormEntity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Boolean> DATA_DANGER_ZONE_VISIBLE = SynchedEntityData.defineId(SandwormEntity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Integer> DATA_DAMAGED_SEGMENTS = SynchedEntityData.defineId(SandwormEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_CAMERA_SHAKE_TICKS = SynchedEntityData.defineId(SandwormEntity.class, EntityDataSerializers.INT);
   private BlockPos homePosition;
   private boolean hpGate75 = true;
   private boolean hpGate50 = true;
   private boolean hpGate25 = true;
   private float recentDamage = 0.0F;
   private int recentDamageResetTimer = 0;
   private static final String MAIN_CONTROLLER = "main_controller";
   private static final String ANIM_PREFIX = "animation.block_factory.bosses_rise.sandworm.";
   public static final RawAnimation EMERGENCE_ANIM = RawAnimation.begin().thenPlay("animation.block_factory.bosses_rise.sandworm.emergence");
   public static final RawAnimation EMERGED_IDLE_ANIM = RawAnimation.begin().thenLoop("animation.block_factory.bosses_rise.sandworm.emerged_idle");
   public static final RawAnimation ROAR_DIVE_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.roar_dive");
   public static final RawAnimation EARTHQUAKE_START_ANIM = RawAnimation.begin()
      .thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.earthquake_start");
   public static final RawAnimation EARTHQUAKE_SLAM_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.earthquake");
   public static final RawAnimation EARTHQUAKE_END_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.earthquake_end");
   public static final RawAnimation DIVE_START_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.dive_start");
   public static final RawAnimation DIVE_END_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.dive_end");
   public static final RawAnimation SCREECH_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.screech");
   public static final RawAnimation BITE_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.bite");
   public static final RawAnimation POISON_SPIT_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.poison_spit");
   public static final RawAnimation DEATH_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.death");
   private static final StateRef<SandwormEntity> IDLE_STATE = State.named("idle");
   private static final StateRef<SandwormEntity> PATROL_STATE = State.named("patrol");
   private static final StateRef<SandwormEntity> PATROL_WANDER_STATE = State.named("patrol_wander");
   private static final StateRef<SandwormEntity> INTRO_STATE = State.named("intro");
   private static final StateRef<SandwormEntity> DIVE_ATTACK_STATE = State.named("dive_attack");
   private static final StateRef<SandwormEntity> DIVE_START_STATE = State.named("dive_start");
   private static final StateRef<SandwormEntity> DIVE_HIDDEN_STATE = State.named("dive_hidden");
   private static final StateRef<SandwormEntity> DIVE_EMERGE_WARNING_STATE = State.named("dive_emerge_warning");
   private static final StateRef<SandwormEntity> DIVE_EMERGE_STATE = State.named("dive_emerge");
   private static final StateRef<SandwormEntity> BITE_ATTACK_STATE = State.named("bite_attack");
   private static final StateRef<SandwormEntity> BITE_STATE = State.named("bite");
   private static final StateRef<SandwormEntity> CHASE_ATTACK_STATE = State.named("chase_attack");
   private static final StateRef<SandwormEntity> CHASE_CIRCLE_STATE = State.named("chase_circle");
   private static final StateRef<SandwormEntity> CHASE_CHARGE_STATE = State.named("chase_charge");
   private static final StateRef<SandwormEntity> EARTHQUAKE_ATTACK_STATE = State.named("earthquake_attack");
   private static final StateRef<SandwormEntity> EARTHQUAKE_START_STATE = State.named("earthquake_start");
   private static final StateRef<SandwormEntity> EARTHQUAKE_APPROACH_STATE = State.named("earthquake_approach");
   private static final StateRef<SandwormEntity> EARTHQUAKE_SLAM_EMERGE_STATE = State.named("earthquake_slam_emerge");
   private static final StateRef<SandwormEntity> EARTHQUAKE_SLAM_STATE = State.named("earthquake_slam");
   private static final StateRef<SandwormEntity> EARTHQUAKE_SLAM_END_STATE = State.named("earthquake_slam_end");
   private static final StateRef<SandwormEntity> POISON_SPIT_ATTACK_STATE = State.named("poison_spit_attack");
   private static final StateRef<SandwormEntity> POISON_SPIT_STATE = State.named("poison_spit");
   private static final StateRef<SandwormEntity> SCREECH_ATTACK_STATE = State.named("screech_attack");
   private static final StateRef<SandwormEntity> SCREECH_STATE = State.named("screech");
   private static final StateRef<SandwormEntity> DYING_STATE = State.named("dying");
   private static final StateRef<SandwormEntity> DEAD_STATE = State.named("dead");
   private static final StateRef<SandwormEntity> PHASE_1_PATTERN_1_STATE = State.named("phase_1_pattern_1");
   private static final StateRef<SandwormEntity> PHASE_1_PATTERN_2_STATE = State.named("phase_1_pattern_2");
   private static final StateRef<SandwormEntity> PHASE_1_PATTERN_3_STATE = State.named("phase_1_pattern_3");
   private static final StateRef<SandwormEntity> PHASE_1_PATTERN_4_STATE = State.named("phase_1_pattern_4");
   private static final StateRef<SandwormEntity> PHASE_2_PATTERN_1_STATE = State.named("phase_2_pattern_1");
   private static final StateRef<SandwormEntity> PHASE_2_PATTERN_2_STATE = State.named("phase_2_pattern_2");
   private static final StateRef<SandwormEntity> PHASE_3_PATTERN_1_STATE = State.named("phase_3_pattern_1");
   private static final StateRef<SandwormEntity> PHASE_3_PATTERN_2_STATE = State.named("phase_3_pattern_2");
   private static final StateRef<SandwormEntity> PHASE_3_PATTERN_3_STATE = State.named("phase_3_pattern_3");
   private static final StateRef<SandwormEntity> PHASE_3_PATTERN_4_STATE = State.named("phase_3_pattern_4");
   private static final StateRef<SandwormEntity> PHASE_4_PATTERN_1_STATE = State.named("phase_4_pattern_1");
   private static final StateRef<SandwormEntity> PHASE_4_PATTERN_2_STATE = State.named("phase_4_pattern_2");
   private static final StateRef<SandwormEntity> PHASE_4_PATTERN_3_STATE = State.named("phase_4_pattern_3");
   private static final StateRef<SandwormEntity> GATE_1_PATTERN_STATE = State.named("gate_1_pattern");
   private static final StateRef<SandwormEntity> GATE_2_PATTERN_STATE = State.named("gate_2_pattern");
   private static final StateRef<SandwormEntity> GATE_3_PATTERN_STATE = State.named("gate_3_pattern");
   private static final StateRegistry<SandwormEntity> STATE_REGISTRY = new StateRegistry();
   private final StateController<SandwormEntity> stateController = new StateController(
      this, STATE_REGISTRY, state -> this.getEntityData().get(DATA_BOSS_PHASE) == -1 ? PATROL_STATE : IDLE_STATE
   );
   private static final WeightedPool<StateRef<SandwormEntity>> PHASE_1_ATTACK_STATES = WeightedPool.of(
      WeightedPool.option(1, PHASE_1_PATTERN_1_STATE),
      WeightedPool.option(1, PHASE_1_PATTERN_2_STATE),
      WeightedPool.option(1, PHASE_1_PATTERN_3_STATE),
      WeightedPool.option(1, PHASE_1_PATTERN_4_STATE)
   );
   private static final WeightedPool<StateRef<SandwormEntity>> PHASE_2_ATTACK_STATES = WeightedPool.of(
      WeightedPool.option(18, PHASE_1_PATTERN_1_STATE),
      WeightedPool.option(18, PHASE_1_PATTERN_2_STATE),
      WeightedPool.option(10, PHASE_1_PATTERN_3_STATE),
      WeightedPool.option(7, PHASE_1_PATTERN_4_STATE),
      WeightedPool.option(27, PHASE_2_PATTERN_1_STATE),
      WeightedPool.option(20, PHASE_2_PATTERN_2_STATE)
   );
   private static final WeightedPool<StateRef<SandwormEntity>> PHASE_3_ATTACK_STATES = WeightedPool.of(
      WeightedPool.option(8, PHASE_1_PATTERN_1_STATE),
      WeightedPool.option(8, PHASE_1_PATTERN_2_STATE),
      WeightedPool.option(4, PHASE_1_PATTERN_3_STATE),
      WeightedPool.option(8, PHASE_1_PATTERN_4_STATE),
      WeightedPool.option(18, PHASE_3_PATTERN_1_STATE),
      WeightedPool.option(22, PHASE_3_PATTERN_2_STATE),
      WeightedPool.option(14, PHASE_3_PATTERN_3_STATE),
      WeightedPool.option(16, PHASE_3_PATTERN_4_STATE)
   );
   private static final WeightedPool<StateRef<SandwormEntity>> PHASE_4_ATTACK_STATES = WeightedPool.of(
      WeightedPool.option(6, PHASE_1_PATTERN_1_STATE),
      WeightedPool.option(6, PHASE_1_PATTERN_2_STATE),
      WeightedPool.option(3, PHASE_1_PATTERN_3_STATE),
      WeightedPool.option(5, PHASE_1_PATTERN_4_STATE),
      WeightedPool.option(35, PHASE_4_PATTERN_1_STATE),
      WeightedPool.option(22, PHASE_4_PATTERN_2_STATE),
      WeightedPool.option(23, PHASE_4_PATTERN_3_STATE)
   );

   public SandwormEntity(EntityType<SandwormEntity> type, Level world) {
      super(type, world);

      for (int index = 0; index < this.parts.length; index++) {
         SandwormEntityPart part = new SandwormEntityPart(
            this, index, PART_ANCHOR_BONE_NAMES[index], PART_BODY_BONE_NAMES[index], index == 0 ? 4.5F : 4.0F, index == 0 ? 4.5F : 4.0F
         );
         this.parts[index] = part;
      }

      this.setId(ENTITY_COUNTER.getAndAdd(this.parts.length + 1) + 1);
      this.lookControl = new SandwormEntity.SandwormLookControl();
      this.moveControl = new SandwormEntity.SandwormMoveControl();
      this.noPhysics = true;
      this.setNoGravity(true);
   }

   @Override
   public StateController<SandwormEntity> getStateController() {
      return this.stateController;
   }

   @NotNull
   @Override
   protected ServerBossEvent createBossEvent() {
      return (ServerBossEvent)Util.make(
         new ServerBossEvent(this.getDisplayName(), BossBarColor.YELLOW, BossBarOverlay.NOTCHED_6), bossEvent -> bossEvent.setVisible(false)
      );
   }

   @Override
   protected int getBossType() {
      return 5;
   }

   public void setId(int id) {
      super.setId(id);

      for (int i = 0; i < this.parts.length; i++) {
         this.parts[i].setId(id + i + 1);
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      AnimationController<SandwormEntity> controller = new CinematicAnimationController(
            this, "main_controller", state -> state.setAndContinue(EMERGED_IDLE_ANIM)
         )
         .triggerableAnim(INTRO_STATE.getName(), EMERGENCE_ANIM)
         .triggerableAnim(DIVE_START_STATE.getName(), DIVE_START_ANIM)
         .triggerableAnim(DIVE_EMERGE_STATE.getName(), DIVE_END_ANIM)
         .triggerableAnim(BITE_STATE.getName(), BITE_ANIM)
         .triggerableAnim(POISON_SPIT_STATE.getName(), POISON_SPIT_ANIM)
         .triggerableAnim(SCREECH_STATE.getName(), SCREECH_ANIM)
         .triggerableAnim(EARTHQUAKE_START_STATE.getName(), ROAR_DIVE_ANIM)
         .triggerableAnim(EARTHQUAKE_SLAM_EMERGE_STATE.getName(), EARTHQUAKE_START_ANIM)
         .triggerableAnim(EARTHQUAKE_SLAM_STATE.getName(), EARTHQUAKE_SLAM_ANIM)
         .triggerableAnim(EARTHQUAKE_SLAM_END_STATE.getName(), EARTHQUAKE_END_ANIM)
         .triggerableAnim(DYING_STATE.getName(), DEATH_ANIM);
      controllers.add(controller);
   }

   public void setRendererVariables() {
      for (int index = 1; index <= 16; index++) {
         int value = 20 * index;
         MathParser.setVariable("v.delay" + index, () -> value);
      }

      int baseShift = 0;
      MathParser.setVariable("v.shift", () -> 0.0);

      for (int index = 2; index <= 8; index++) {
         int value = 0 + (index - 1);
         MathParser.setVariable("v.shift" + index, () -> value);
      }

      MathParser.setVariable("v.idle", () -> 100.0);
      MathParser.setVariable("v.swim", () -> 360.0);
   }

   @Override
   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_IS_HIDDEN, false);
      builder.define(DATA_DANGER_ZONE_VISIBLE, false);
      builder.define(DATA_DAMAGED_SEGMENTS, 0);
      builder.define(DATA_CAMERA_SHAKE_TICKS, 0);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
      super.onSyncedDataUpdated(key);
      if (DATA_IS_HIDDEN.equals(key)) {
         boolean isHidden = this.isHiddenUnderground();
         this.blocksBuilding = !isHidden;

         for (SandwormEntityPart part : this.parts) {
            part.blocksBuilding = !isHidden;
         }
      }
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new StateGoal<SandwormEntity>(this) {
         @Override
         public boolean isInterruptable() {
            return SandwormEntity.this.getStateController().isStateActive(SandwormEntity.IDLE_STATE);
         }
      });
      this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, false, false));
   }

   public SoundEvent getAmbientSound() {
      return (SoundEvent)BossesRiseSounds.SANDWORM_AMBIENT.value();
   }

   public SoundEvent getHurtSound(DamageSource ds) {
      return (SoundEvent)BossesRiseSounds.SANDWORM_HURT.value();
   }

   public boolean isDiveMoving() {
      StateRef<SandwormEntity> state = this.stateController.getActiveStateRef();
      return state == DIVE_HIDDEN_STATE
         || state == CHASE_CHARGE_STATE
         || state == CHASE_CIRCLE_STATE
         || state == EARTHQUAKE_APPROACH_STATE
         || state == PATROL_WANDER_STATE;
   }

   public boolean isDying() {
      return (Integer)this.getEntityData().get(DATA_BOSS_PHASE) == 1;
   }

   public boolean isAllowedTurning() {
      StateRef<SandwormEntity> state = this.stateController.getActiveStateRef();
      if (state == DIVE_START_STATE) {
         return false;
      } else {
         return state != EARTHQUAKE_START_STATE
               && state != EARTHQUAKE_SLAM_EMERGE_STATE
               && state != EARTHQUAKE_SLAM_STATE
               && state != EARTHQUAKE_SLAM_END_STATE
            ? state != DYING_STATE && state != DEAD_STATE
            : false;
      }
   }

   @Override
   public void tick() {
      super.tick();
      this.setNoGravity(true);
      if (this.hasEffect(MobEffects.POISON)) {
         this.removeEffect(MobEffects.POISON);
      }

      if (this.level().isClientSide() && this.isHiddenUnderground()) {
         Vec3 pos = this.findGroundPosition().add(0.0, 0.1, 0.0);

         for (int i = 0; i < 3; i++) {
            this.level()
               .addParticle(
                  (ParticleOptions)BossesRiseParticleTypes.DUST_CLOUD.get(),
                  pos.x(),
                  pos.y(),
                  pos.z(),
                  (Math.random() - 0.5) * 2.0,
                  Math.random() * 0.25,
                  (Math.random() - 0.5) * 2.0
               );
            this.level()
               .addParticle(
                  (ParticleOptions)BossesRiseParticleTypes.GROUND_DUST.get(),
                  pos.x(),
                  pos.y(),
                  pos.z(),
                  (Math.random() - 0.5) * 2.0,
                  Math.random() * 0.025,
                  (Math.random() - 0.5) * 2.0
               );
         }
      }

      int bossPhase = (Integer)this.entityData.get(DATA_BOSS_PHASE);
      if (!this.level().isClientSide()) {
         if (bossPhase == 0) {
            this.entityData.set(AbstractStateBossEntity.DATA_BATTLE_TIME, (Integer)this.entityData.get(AbstractStateBossEntity.DATA_BATTLE_TIME) + 1);
            this.setPlayingMusic(true);
         } else {
            this.setPlayingMusic(false);
         }

         if (this.getPersistentData().getDouble("soundCooldown") >= 80.0) {
            this.playSound(this.getAmbientSound());
            this.getPersistentData().putDouble("soundCooldown", 0.0);
         } else {
            this.getPersistentData().putDouble("soundCooldown", this.getPersistentData().getDouble("soundCooldown") + 1.0);
         }
      }
   }

   public void baseTick() {
      super.baseTick();
      this.setRendererVariables();
      this.serverAnimationPlayer.tickAnimations();
      AbstractGeoEntityPart.positionPartsFromBones(this.getParts(), this.serverAnimationPlayer, Vec3.ZERO);
      if (!this.isHiddenUnderground()) {
         this.pushCollidingEntities();
      }

      if (!this.level().isClientSide()) {
         this.serverBaseTick();
      }
   }

   private void serverBaseTick() {
      if (this.recentDamageResetTimer > 0) {
         this.recentDamageResetTimer--;
         if (this.recentDamageResetTimer == 0) {
            this.recentDamage = 0.0F;
         }
      }

      int cameraShakeTicks = (Integer)this.getEntityData().get(DATA_CAMERA_SHAKE_TICKS);
      if (cameraShakeTicks > 0) {
         this.getEntityData().set(DATA_CAMERA_SHAKE_TICKS, cameraShakeTicks - 1);
      }
   }

   private void startFight() {
      this.setYRot((float)this.getPersistentData().getDouble("angle"));
      this.setXRot(0.0F);
      this.setYBodyRot(this.getYRot());
      this.setYHeadRot(this.getYRot());
      this.yRotO = this.getYRot();
      this.xRotO = this.getXRot();
      this.yBodyRotO = this.getYRot();
      this.yHeadRotO = this.getYRot();
      this.forEachNearbyPlayer(32.0, player -> {
         PlayerVariables playerVars = (PlayerVariables)player.getData(BossesRiseDataAttachments.PLAYER_VARIABLES);
         playerVars.boss_no_hit = true;
         playerVars.syncPlayerVariables(player);
      });
      this.setBarVisible(true);
      this.stateController.replaceAll(INTRO_STATE);
      this.stateController.push(EARTHQUAKE_APPROACH_STATE);
      this.stateController.push(DIVE_START_STATE);
   }

   public StateRef<SandwormEntity> chooseAttackState() {
      double ratio = this.getHealthRatio();
      if (ratio <= 0.75 && this.hpGate75) {
         this.hpGate75 = false;
         return GATE_1_PATTERN_STATE;
      }

      if (ratio <= 0.5 && this.hpGate50) {
         this.hpGate50 = false;
         return GATE_2_PATTERN_STATE;
      }

      if (ratio <= 0.25 && this.hpGate25) {
         this.hpGate25 = false;
         return GATE_3_PATTERN_STATE;
      }

      LivingEntity target = this.getTarget();
      if (target == null || !(this.distanceTo(target) < 8.0 + Math.random() * 8.0)) {
         WeightedPool<StateRef<SandwormEntity>> attackPool = switch ((int)(ratio * 4.0)) {
            case 0 -> PHASE_4_ATTACK_STATES;
            case 1 -> PHASE_3_ATTACK_STATES;
            case 2 -> PHASE_2_ATTACK_STATES;
            default -> PHASE_1_ATTACK_STATES;
         };
         return attackPool.selectRandom();
      } else {
         return this.getRandom().nextFloat() < 0.4F ? SCREECH_ATTACK_STATE : DIVE_ATTACK_STATE;
      }
   }

   public void shootPoisonSpit(SandwormEntityPart sourcePart, @Nullable Entity target, boolean towardsTarget) {
      if (this.level() instanceof ServerLevel level) {
         PoisonSpitPrEntity spit = new PoisonSpitPrEntity((EntityType<? extends PoisonSpitPrEntity>)BossesRiseEntities.POISON_SPIT_PR.get(), level);
         spit.setOwner(this);
         spit.setBaseDamage(5.0);
         spit.setSilent(true);
         float distance = target == null ? 3.0F : this.distanceTo(target) * 0.045F;
         Vector3f bonePosition = this.getBoneWorldPosition(sourcePart.getAnchorBoneName());
         spit.setPos(bonePosition.x(), bonePosition.y(), bonePosition.z());
         Vec3 lookDirection = towardsTarget && target != null ? target.position().subtract(new Vec3(bonePosition)).normalize() : this.getLookAngle();
         spit.shoot(lookDirection.x * 0.3, 0.2, lookDirection.z * 0.3, distance, 20.0F);
         level.addFreshEntity(spit);
      }
   }

   public boolean hurt(@Nonnull DamageSource damageSource, float amount) {
      return this.level().isClientSide() ? false : this.hurt(this.parts[1], damageSource, amount);
   }

   public boolean hurt(SandwormEntityPart part, DamageSource damageSource, float damage) {
      float multiplier = 1.0F;
      if (this.isSegmentDamaged(part.getPartIndex())) {
         multiplier += 0.3F;
      }

      damage *= multiplier;
      if (damage < 0.01F) {
         return false;
      }

      float previousHealth = this.getHealth();
      boolean gotDamaged = this.reallyHurt(damageSource, damage);
      if (gotDamaged) {
         this.recentDamage = this.recentDamage + Math.max(previousHealth - this.getHealth(), 0.0F);
         if (this.recentDamage >= 25.0F) {
            this.makeSegmentDamaged(part);
            this.recentDamage = 0.0F;
         } else {
            this.recentDamageResetTimer = 60;
         }

         if (this.isSegmentDamaged(part.getPartIndex()) && damageSource.isDirect() && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            this.shootPoisonSpit(part, damageSource.getDirectEntity(), true);
         }
      }

      return gotDamaged;
   }

   public boolean reallyHurt(DamageSource damageSource, float amount) {
      return super.hurt(damageSource, amount);
   }

   public boolean isInvulnerable() {
      return this.stateController.isStateActive(DYING_STATE) ? true : super.isInvulnerable();
   }

   public boolean isInvulnerableTo(DamageSource damageSource) {
      if (damageSource.is(DamageTypes.FALL)) {
         return true;
      } else if (damageSource.is(DamageTypes.CACTUS)) {
         return true;
      } else if (damageSource.is(NeoForgeMod.POISON_DAMAGE)) {
         return true;
      } else {
         return damageSource.is(DamageTypes.LAVA) ? true : super.isInvulnerableTo(damageSource);
      }
   }

   public void lavaHurt() {
   }

   public int getSegmentIndexFromBoneName(String bodyBoneName) {
      for (int index = 0; index < this.parts.length; index++) {
         if (this.parts[index].getBodyBoneName().equals(bodyBoneName)) {
            return index;
         }
      }

      return -1;
   }

   public boolean isSegmentDamaged(int index) {
      return ((Integer)this.getEntityData().get(DATA_DAMAGED_SEGMENTS) & 1 << index) != 0;
   }

   public void setSegmentDamaged(int index, boolean damaged) {
      this.getEntityData().set(DATA_DAMAGED_SEGMENTS, (Integer)this.getEntityData().get(DATA_DAMAGED_SEGMENTS) & ~(1 << index) | (damaged ? 1 : 0) << index);
   }

   public void healAllSegments() {
      for (SandwormEntityPart part : this.parts) {
         this.setSegmentDamaged(part.getPartIndex(), false);
      }
   }

   public void makeSegmentDamaged(SandwormEntityPart part) {
      if (!this.isSegmentDamaged(part.getPartIndex())) {
         this.setSegmentDamaged(part.getPartIndex(), true);
         if (this.level() instanceof ServerLevel level) {
            Vector3f bonePosition = this.getBoneWorldPosition(part.getAnchorBoneName());
            level.sendParticles(
               (SimpleParticleType)BossesRiseParticleTypes.POISON_SPIT.get(), bonePosition.x(), bonePosition.y(), bonePosition.z(), 15, 0.4, 0.4, 0.4, 0.1
            );
         }
      }
   }

   private void applyUndergroundEffects() {
      this.healAllSegments();
      this.clearFire();
      this.removeFrost();
      this.setAirSupply(this.getMaxAirSupply());
   }

   public Vector3f getBoneWorldPosition(String boneName) {
      return this.serverAnimationPlayer.getBoneWorldPosition(boneName);
   }

   private void pushCollidingEntities() {
      for (SandwormEntity.PartCollision collision : this.findPartCollisions(0.0)) {
         LivingEntity entity = collision.entity();
         if (!this.level().isClientSide() ^ entity instanceof Player) {
            Vec3 force = entity.position().subtract(collision.part().position());
            SpatialUtil.pushEntity(entity, force, 0.3F);
         }
      }
   }

   private void attackCollidingEntities(Vec3 lineStart, Vec3 lineEnd, double maxDistance, float maxDamageScale) {
      this.attackCollidingEntities(lineStart, lineEnd, maxDistance, maxDamageScale, (entity, collision) -> {});
   }

   private void attackCollidingEntities(
      Vec3 lineStart, Vec3 lineEnd, double maxDistance, float maxDamageScale, BiConsumer<LivingEntity, SandwormEntity.LineCollision> callback
   ) {
      this.attackCollidingEntities(List.of(Pair.of(lineStart, lineEnd)), maxDistance, maxDamageScale, callback);
   }

   private void attackCollidingEntities(
      List<Pair<Vec3, Vec3>> lines, double maxDistance, float maxDamageScale, BiConsumer<LivingEntity, SandwormEntity.LineCollision> callback
   ) {
      float halfPartSize = 2.0F;
      HashMap<LivingEntity, SandwormEntity.LineCollision> lineCollisions = new HashMap<>();

      for (Pair<Vec3, Vec3> line : lines) {
         Vec3 lineStart = (Vec3)line.getFirst();
         Vec3 lineEnd = (Vec3)line.getSecond();
         List<LivingEntity> collisions = this.level()
            .getEntitiesOfClass(
               LivingEntity.class, new AABB(lineStart, lineEnd).inflate(maxDistance + 2.0), e -> e != this && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e)
            );
         Vec3 lineVector = lineEnd.subtract(lineStart);
         double lineLength = lineVector.length();
         Vec3 lineDirection = lineVector.normalize();

         for (LivingEntity entity : collisions) {
            Vec3 entityCenter = entity.getBoundingBox().getCenter();
            double lineDistance = Math.clamp(lineDirection.dot(entityCenter.subtract(lineStart)), 0.0, lineLength);
            Vec3 forceOrigin = lineStart.add(lineDirection.scale(lineDistance));
            Vec3 forceOriginLocalEntityPos = entityCenter.subtract(forceOrigin);
            double forceOriginDistance = forceOriginLocalEntityPos.length();
            double distanceModifier = 1.0 - Math.max((forceOriginDistance - 2.0) / maxDistance, 0.0);
            if (!(distanceModifier <= 0.0)) {
               SandwormEntity.LineCollision existingCollision = lineCollisions.get(entity);
               if (existingCollision == null || !(existingCollision.distanceModifier > distanceModifier)) {
                  lineCollisions.put(entity, new SandwormEntity.LineCollision(forceOrigin, forceOriginLocalEntityPos.normalize(), (float)distanceModifier));
               }
            }
         }
      }

      for (Entry<LivingEntity, SandwormEntity.LineCollision> lineCollision : lineCollisions.entrySet()) {
         LivingEntity entity = lineCollision.getKey();
         SandwormEntity.LineCollision collision = lineCollision.getValue();
         SpatialUtil.pushEntity(entity, collision.forceDirection.scale(maxDistance * collision.distanceModifier), 2.0);
         this.attackEntity(entity, maxDamageScale * collision.distanceModifier());
         callback.accept(entity, collision);
      }
   }

   private List<SandwormEntity.PartCollision> findPartCollisions(double inflateCollider) {
      ArrayList<SandwormEntity.PartCollision> allCollisions = new ArrayList<>(4);

      for (SandwormEntityPart part : this.parts) {
         for (LivingEntity collidingEntity : this.level()
            .getEntitiesOfClass(
               LivingEntity.class, part.getBoundingBox().inflate(inflateCollider), e -> e != this && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e)
            )) {
            AABB overlapBBox = collidingEntity.getBoundingBox().intersect(part.getBoundingBox());
            float overlapVolume = (float)(overlapBBox.getXsize() * overlapBBox.getYsize() * overlapBBox.getZsize());
            SandwormEntity.PartCollision previousCollision = allCollisions.stream().filter(it -> it.entity == collidingEntity).findFirst().orElse(null);
            if (previousCollision != null) {
               if (previousCollision.overlapVolume >= overlapVolume) {
                  continue;
               }

               allCollisions.remove(previousCollision);
            }

            allCollisions.add(new SandwormEntity.PartCollision(collidingEntity, part, overlapVolume));
         }
      }

      return allCollisions;
   }

   public void triggerAnim(@Nullable String controllerName, String animName) {
      ServerAnimationPlayer.retriggerAnim(this.serverAnimationPlayer, controllerName, animName);
   }

   public void triggerAnim(String animName) {
      this.triggerAnim("main_controller", animName);
   }

   public void stopTriggeredAnim(@Nullable String controllerName, @Nullable String animName) {
      ServerAnimationPlayer.stopTriggeredAnim(this.serverAnimationPlayer, controllerName, animName);
   }

   public void stopTriggeredAnim(@Nullable String animName) {
      this.stopTriggeredAnim("main_controller", animName);
   }

   @Override
   public Collection<DangerZonesProvider.DangerZone> getDangerZones() {
      if (!this.isDangerZoneVisible()) {
         return List.of();
      }

      Vec3 offset = this.findGroundPosition().subtract(this.position());
      return List.of(new DangerZonesProvider.DangerZone().setSize(5.0F, 5.0F).setOffset(offset.toVector3f()));
   }

   public void setDangerZoneVisible(boolean visible) {
      this.getEntityData().set(DATA_DANGER_ZONE_VISIBLE, visible);
   }

   public boolean isDangerZoneVisible() {
      return (Boolean)this.getEntityData().get(DATA_DANGER_ZONE_VISIBLE);
   }

   public void setHiddenUnderground(boolean hidden) {
      this.getEntityData().set(DATA_IS_HIDDEN, hidden);
   }

   public boolean isHiddenUnderground() {
      return (Boolean)this.getEntityData().get(DATA_IS_HIDDEN);
   }

   private void setCameraShakeTicks(int ticks) {
      this.getEntityData().set(DATA_CAMERA_SHAKE_TICKS, ticks);
   }

   public float getCameraShakeStrength() {
      return this.getEntityData().get(DATA_CAMERA_SHAKE_TICKS) > 0 ? 12.0F : 0.0F;
   }

   @Override
   protected ResourceLocation getNoHitAdvancement() {
      return BossesRise.prefix("no_hit_sandworm");
   }

   @Override
   protected ResourceLocation getKillAdvancement() {
      return BossesRise.prefix("kill_sandworm");
   }

   public boolean isPushable() {
      return false;
   }

   public void knockback(double strength, double x, double z) {
   }

   public boolean isAttackable() {
      return !this.isHiddenUnderground() && super.isAttackable();
   }

   public boolean isPickable() {
      return super.isPickable() && !this.isHiddenUnderground();
   }

   public boolean isInWall() {
      return false;
   }

   protected boolean isAffectedByFluids() {
      return false;
   }

   public boolean displayFireAnimation() {
      return false;
   }

   public boolean realDisplayFireAnimation() {
      return super.displayFireAnimation();
   }

   @Nonnull
   public AABB getBoundingBoxForCulling() {
      return AABB.ofSize(this.position(), 70.0, 70.0, 70.0);
   }

   protected float getMaxHeadRotationRelativeToBody() {
      return 0.0F;
   }

   public int getMaxHeadYRot() {
      return 0;
   }

   public int getMaxHeadXRot() {
      return this.getHeadRotSpeed();
   }

   public int getHeadRotSpeed() {
      if (!this.isAllowedTurning()) {
         return 0;
      } else {
         StateRef<SandwormEntity> state = this.stateController.getActiveStateRef();
         if (state == CHASE_CIRCLE_STATE) {
            return 9;
         } else {
            return state == DIVE_HIDDEN_STATE ? 10 : 4;
         }
      }
   }

   protected float tickHeadTurn(float yRot, float animStep) {
      this.yBodyRot = Mth.rotLerp(animStep, this.yHeadRotO, this.yHeadRot);
      return animStep;
   }

   @Nonnull
   protected PathNavigation createNavigation(@Nonnull Level level) {
      return new SandwormEntity.SandwormPathNavigation(level);
   }

   public boolean canStandOnFluid(@NotNull FluidState fluidState) {
      return true;
   }

   public Vec3 findGroundPosition() {
      Vec3 position = this.position();
      if (this.level().getBlockState(this.blockPosition()).isEmpty()) {
         BlockHitResult hitResult = this.level()
            .clip(new ClipContext(position.add(0.0, 2.0, 0.0), position.add(0.0, -60.0, 0.0), Block.COLLIDER, Fluid.ANY, CollisionContext.empty()));
         return hitResult.getLocation();
      }

      for (int y = (int)position.y; y < this.level().getMaxBuildHeight(); y += 3) {
         BlockHitResult hitResult = this.level()
            .clip(
               new ClipContext(
                  new Vec3(position.x, y + 3, position.z), new Vec3(position.x, y, position.z), Block.COLLIDER, Fluid.ANY, CollisionContext.empty()
               )
            );
         if (hitResult.getType() != Type.MISS && !hitResult.isInside()) {
            return hitResult.getLocation();
         }
      }

      return position;
   }

   private void snapToGround() {
      if (this.level() instanceof ServerLevel level) {
         Vec3 var3 = this.findGroundPosition();
         this.teleportTo(level, var3.x(), var3.y(), var3.z(), Set.of(), this.getYRot(), this.getXRot());
      }
   }

   @Override
   public void lookAtTarget() {
      Entity target = this.getTarget();
      if (target != null) {
         this.getLookControl().setLookAt(target);
      }
   }

   private void circleAroundPosition(Vec3 centerPosition, float radius, float speed) {
      this.circleAroundPosition(centerPosition, radius, speed, 20.0F);
   }

   private void circleAroundPosition(Vec3 centerPosition, float radius, float speed, float degreeOffset) {
      Vec3 directionToCenter = centerPosition.subtract(this.position()).normalize();
      Vec3 directionToRightOfCenter = directionToCenter.cross(new Vec3(0.0, 1.0, 0.0));
      boolean invertCirclingDirection = this.getLookAngle().dot(directionToRightOfCenter) < 0.0;
      float angleToCenter = (float)Mth.atan2(directionToCenter.z, -directionToCenter.x);
      Vec3 moveTarget = new Vec3(radius, 0.0, 0.0)
         .yRot(angleToCenter + (invertCirclingDirection ? -degreeOffset : degreeOffset) * (float) (Math.PI / 180.0))
         .add(centerPosition);
      this.getMoveControl().setWantedPosition(moveTarget.x(), moveTarget.y(), moveTarget.z(), speed);
   }

   public boolean isMultipartEntity() {
      return true;
   }

   @Nonnull
   public SandwormEntityPart[] getParts() {
      return this.parts;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      if (this.recentDamage > 0.0F) {
         compound.putFloat("block_factorys_bosses:recent_damage", this.recentDamage);
      }

      if (this.recentDamageResetTimer > 0) {
         compound.putInt("block_factorys_bosses:recent_damage_timer", this.recentDamageResetTimer);
      }

      if (this.homePosition != null) {
         compound.put("block_factorys_bosses:home_position", NbtUtils.writeBlockPos(this.homePosition));
      }

      compound.putBoolean("HpGate75", this.hpGate75);
      compound.putBoolean("HpGate50", this.hpGate50);
      compound.putBoolean("HpGate25", this.hpGate25);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("block_factorys_bosses:recent_damage_timer", 3) && compound.contains("block_factorys_bosses:recent_damage", 5)) {
         this.recentDamageResetTimer = compound.getInt("block_factorys_bosses:recent_damage_timer");
         this.recentDamage = compound.getFloat("block_factorys_bosses:recent_damage");
      }

      if (compound.contains("block_factorys_bosses:home_position")) {
         NbtUtils.readBlockPos(compound, "block_factorys_bosses:home_position").ifPresent(pos -> this.homePosition = pos);
      }

      if (compound.contains("BossPhase")) {
         this.hpGate75 = compound.getBoolean("HpGate75");
         this.hpGate50 = compound.getBoolean("HpGate50");
         this.hpGate25 = compound.getBoolean("HpGate25");
      }
   }

   public void onAddedToLevel() {
      super.onAddedToLevel();
      if (this.homePosition == null && !this.level().isClientSide()) {
         this.homePosition = this.blockPosition();
      }
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata) {
      SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
      this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((Double)ServerConfiguration.SANDWORM_HEALTH.get());
      this.getAttribute(Attributes.ARMOR).setBaseValue((Double)ServerConfiguration.SANDWORM_ARMOR.get());
      this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((Double)ServerConfiguration.SANDWORM_ATK.get());
      this.setHealth((float)((Double)ServerConfiguration.SANDWORM_HEALTH.get()).doubleValue());
      if (reason != MobSpawnType.STRUCTURE) {
         this.setBarVisible(true);
      }

      return retval;
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MOVEMENT_SPEED, 0.3)
         .add(Attributes.MAX_HEALTH, 150.0)
         .add(Attributes.ARMOR, 20.0)
         .add(Attributes.ATTACK_DAMAGE, 10.0)
         .add(Attributes.FOLLOW_RANGE, 96.0)
         .add(Attributes.STEP_HEIGHT, 0.6);
   }

   private void replaceStateAfterIdle(StateRef<SandwormEntity> newState) {
      if (this.isHiddenUnderground()) {
         this.stateController.replaceAll(newState);
         this.stateController.push(DIVE_EMERGE_STATE);
      } else {
         StateRef<SandwormEntity> state = this.stateController.getActiveStateRef();
         if (state == DIVE_EMERGE_STATE) {
            this.stateController.pushBefore(Objects.requireNonNull(this.stateController.getActiveState()), newState);
         } else if (state == EARTHQUAKE_SLAM_STATE) {
            this.stateController.replaceAll(newState);
            this.stateController.push(DIVE_EMERGE_STATE);
            this.stateController.push(EARTHQUAKE_SLAM_END_STATE);
         } else if (state != DIVE_START_STATE && state != EARTHQUAKE_START_STATE && state != EARTHQUAKE_SLAM_END_STATE) {
            this.stateController.replaceAll(newState);
         } else {
            this.stateController.pushBefore(Objects.requireNonNull(this.stateController.getActiveState()), newState);
            this.stateController.pushBefore(Objects.requireNonNull(this.stateController.getActiveState()), DIVE_EMERGE_STATE);
         }
      }
   }

   public void startDeathSequence() {
      if (!this.isDying()) {
         assert !this.stateController.hasActiveStateRef(it -> it == DYING_STATE || it == DEAD_STATE);
         this.replaceStateAfterIdle(DYING_STATE);
         this.getEntityData().set(DATA_BOSS_PHASE, 1);
      }
   }

   @Override
   public boolean shouldCancelDeath() {
      if (!this.isDying()) {
         this.startDeathSequence();
         return true;
      } else {
         return !this.stateController.isStateActive(DEAD_STATE);
      }
   }

   static <E extends Entity, D extends StateData> LambdaDataState.LambdaDataStateBuilder<E, D> stateBuilderWithData(
      Codec<D> dataCodec, Supplier<D> defaultDataSupplier
   ) {
      return new LambdaDataState.LambdaDataStateBuilder<E, D>().withData(dataCodec, defaultDataSupplier);
   }

   static {
      STATE_REGISTRY.register(IDLE_STATE, State.builder().endAfter(10).onEnd(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         entity.stopTriggeredAnim(null);
         if (entity.isDying()) {
            entity.stateController.replaceAll(DEAD_STATE);
         } else if (entity.getTarget() != null && (Integer)entity.entityData.get(DATA_BOSS_PHASE) != -1) {
            state.getController().push(entity.chooseAttackState());
         }
      }));
      STATE_REGISTRY.register(PATROL_STATE, State.builder().onTick(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         if (entity.getTarget() instanceof Player) {
            entity.startFight();
            return State.Result.END;
         }

         RandomSource random = entity.getRandom();
         if (random.nextInt(60) == 0) {
            entity.getLookControl().setLookAt(new Vec3(random.nextGaussian(), 0.0, random.nextGaussian()).normalize());
         }

         int timer = state.getTimer();
         if (timer <= 400 && (timer <= 120 || random.nextInt(20) != 0)) {
            return State.Result.CONTINUE;
         }

         entity.stateController.replaceActive(DIVE_EMERGE_STATE);
         entity.stateController.push(PATROL_WANDER_STATE);
         entity.stateController.push(DIVE_START_STATE);
         return State.Result.END;
      }));
      STATE_REGISTRY.register(PATROL_WANDER_STATE, State.builder().onTick(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         RandomSource random = entity.getRandom();
         int timer = state.getTimer();
         entity.circleAroundPosition(entity.homePosition.getBottomCenter(), 40.0F + random.nextFloat() * Math.min(timer / 5, 15), 1.5F, 50.0F);
         return timer <= 200 && (timer <= 80 || random.nextInt(80) != 0) ? State.Result.CONTINUE : State.Result.END;
      }));
      STATE_REGISTRY.register(
         INTRO_STATE,
         State.builder()
            .onStart(state -> {
               SandwormEntity entity = (SandwormEntity)state.getEntity();
               entity.snapToGround();
               entity.triggerAnim(INTRO_STATE.getName());
               entity.setHiddenUnderground(false);
               entity.applyUndergroundEffects();
               entity.getEntityData().set(AbstractStateBossEntity.DATA_BOSS_PHASE, 0);
            })
            .onTickFor(
               200,
               state -> {
                  SandwormEntity entity = (SandwormEntity)state.getEntity();
                  entity.setDeltaMovement(Vec3.ZERO);
                  entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue((Double)ServerConfiguration.SANDWORM_HEALTH.get());
                  entity.getAttribute(Attributes.ARMOR).setBaseValue((Double)ServerConfiguration.SANDWORM_ARMOR.get());
                  entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((Double)ServerConfiguration.SANDWORM_ATK.get());
                  int timer = state.getTimer();
                  if (timer == 10) {
                     entity.setCameraShakeTicks(6);
                     ServerLevel level = (ServerLevel)entity.level();

                     for (int i = 0; i < 26; i++) {
                        level.sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.DUST_CLOUD.get(),
                           entity.getX(),
                           entity.getY() + 0.1,
                           entity.getZ(),
                           0,
                           (Math.random() - 0.5) * 4.0,
                           Math.random() * 0.3,
                           (Math.random() - 0.5) * 4.0,
                           1.0
                        );
                        level.sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.GROUND_DUST.get(),
                           entity.getX(),
                           entity.getY() + 0.1,
                           entity.getZ(),
                           0,
                           (Math.random() - 0.5) * 5.0,
                           Math.random() * 0.4,
                           (Math.random() - 0.5) * 5.0,
                           1.0
                        );
                     }
                  } else if (timer == 13) {
                     entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_EMERGE.value());
                  } else if (timer == 63) {
                     entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_SCREECH.value());
                  }
               }
            )
      );
      STATE_REGISTRY.register(
         DIVE_START_STATE,
         State.builder()
            .onStart(state -> {
               SandwormEntity entity = (SandwormEntity)state.getEntity();
               if (!entity.isHiddenUnderground()) {
                  entity.triggerAnim(DIVE_START_STATE.getName());
                  entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_DIVE.value());
               }
            })
            .onTick(
               state -> {
                  SandwormEntity entity = (SandwormEntity)state.getEntity();
                  if (entity.isHiddenUnderground()) {
                     return State.Result.END;
                  }

                  int timer = state.getTimer();
                  if (timer == 9) {
                     entity.attackCollidingEntities(
                        new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[0])),
                        new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[3])),
                        2.0,
                        0.8F
                     );
                     return State.Result.CONTINUE;
                  }

                  if (timer < 30) {
                     return State.Result.CONTINUE;
                  }

                  entity.setHiddenUnderground(true);
                  entity.applyUndergroundEffects();
                  Vec3 eyePosition = entity.position().add(0.0, 9.0, 0.0);
                  Vec3 diveDirection = entity.calculateViewVector(45.0F, entity.getYRot());
                  BlockHitResult hitResult = entity.level()
                     .clip(
                        new ClipContext(
                           eyePosition.add(diveDirection.scale(5.0)),
                           eyePosition.add(diveDirection.scale(20.0)),
                           Block.COLLIDER,
                           Fluid.NONE,
                           CollisionContext.empty()
                        )
                     );
                  if (hitResult.getType() == Type.BLOCK) {
                     Vec3 teleportTarget = hitResult.getBlockPos().above().getBottomCenter();
                     entity.teleportTo(teleportTarget.x, teleportTarget.y, teleportTarget.z);
                  }

                  if (entity.level() instanceof ServerLevel level) {
                     for (int i = 0; i < 26; i++) {
                        level.sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.DUST_CLOUD.get(),
                           entity.getX(),
                           entity.getY() + 0.1,
                           entity.getZ(),
                           0,
                           (Math.random() - 0.5) * 4.0,
                           Math.random() * 0.3,
                           (Math.random() - 0.5) * 4.0,
                           1.0
                        );
                        level.sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.GROUND_DUST.get(),
                           entity.getX(),
                           entity.getY() + 0.1,
                           entity.getZ(),
                           0,
                           (Math.random() - 0.5) * 5.0,
                           Math.random() * 0.4,
                           (Math.random() - 0.5) * 5.0,
                           1.0
                        );
                     }
                  }

                  return State.Result.END;
               }
            )
      );
      STATE_REGISTRY.register(DIVE_HIDDEN_STATE, State.builder().onStart(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         entity.triggerAnim(DIVE_HIDDEN_STATE.getName());
      }).onTickFor(140, state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         LivingEntity target = entity.getTarget();
         if (target != null) {
            entity.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 2.0);
            if (entity.distanceToSqr(target) < 4.0) {
               entity.teleportTo(target.getX(), target.getY(), target.getZ());
               state.getController().endActive();
            }
         }
      }).onEnd(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         entity.snapToGround();
      }));
      STATE_REGISTRY.register(DIVE_EMERGE_WARNING_STATE, State.builder().onStart(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         if (entity.isHiddenUnderground()) {
            entity.setDangerZoneVisible(true);
         }
      }).onTick(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         if (!entity.isHiddenUnderground()) {
            return State.Result.END;
         } else if (state.getTimer() >= 5) {
            state.getController().replaceActive(DIVE_EMERGE_STATE);
            return State.Result.END;
         } else {
            return State.Result.CONTINUE;
         }
      }).onEnd(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         entity.setDangerZoneVisible(false);
      }));
      STATE_REGISTRY.register(
         DIVE_EMERGE_STATE,
         State.builder()
            .onStart(state -> {
               SandwormEntity entity = (SandwormEntity)state.getEntity();
               if (entity.isHiddenUnderground()) {
                  entity.snapToGround();
                  entity.triggerAnim(DIVE_EMERGE_STATE.getName());
                  entity.applyUndergroundEffects();
                  entity.setHiddenUnderground(false);
               }
            })
            .onTick(
               state -> {
                  SandwormEntity entity = (SandwormEntity)state.getEntity();
                  if (entity.isHiddenUnderground()) {
                     return State.Result.END;
                  }

                  int timer = state.getTimer();
                  if (timer == 3) {
                     entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_EMERGE.value());
                     entity.setCameraShakeTicks(2);
                     Vec3 position = entity.position();
                     entity.attackCollidingEntities(
                        position.add(0.0, -2.0, 0.0),
                        position.add(0.0, 4.0, 0.0),
                        1.5,
                        1.5F,
                        (collidingEntity, collision) -> SpatialUtil.pushEntity(collidingEntity, new Vec3(0.0, 1.6, 0.0).scale(collision.distanceModifier), 2.0)
                     );
                     if (entity.level() instanceof ServerLevel level) {
                        for (int i = 0; i < 26; i++) {
                           level.sendParticles(
                              (SimpleParticleType)BossesRiseParticleTypes.DUST_CLOUD.get(),
                              entity.getX(),
                              entity.getY() + 0.1,
                              entity.getZ(),
                              0,
                              (Math.random() - 0.5) * 4.0,
                              Math.random() * 0.3,
                              (Math.random() - 0.5) * 4.0,
                              1.0
                           );
                           level.sendParticles(
                              (SimpleParticleType)BossesRiseParticleTypes.GROUND_DUST.get(),
                              entity.getX(),
                              entity.getY() + 0.1,
                              entity.getZ(),
                              0,
                              (Math.random() - 0.5) * 5.0,
                              Math.random() * 0.4,
                              (Math.random() - 0.5) * 5.0,
                              1.0
                           );
                        }
                     }
                  }

                  return timer >= 30 ? State.Result.END : State.Result.CONTINUE;
               }
            )
      );
      STATE_REGISTRY.register(
         BITE_STATE,
         State.builder()
            .onStart(state -> {
               SandwormEntity entity = (SandwormEntity)state.getEntity();
               entity.triggerAnim(BITE_STATE.getName());
               entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_BITE.value(), 8.0F, 1.0F);
            })
            .onTickFor(
               60,
               state -> {
                  SandwormEntity entity = (SandwormEntity)state.getEntity();
                  int timer = state.getTimer();
                  if (timer < 30) {
                     entity.lookAtTarget();
                  }

                  if (timer == 33) {
                     entity.attackCollidingEntities(
                        List.of(
                           new Pair(
                              new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[0])),
                              new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[2]))
                           ),
                           new Pair(
                              new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[2])),
                              new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[5]))
                           ),
                           new Pair(
                              new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[5])),
                              new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[8]))
                           )
                        ),
                        2.0,
                        1.0F,
                        (hitEntity, collision) -> {
                           hitEntity.addEffect(new MobEffectInstance(MobEffects.POISON, (int)(collision.distanceModifier * 200.0F), 1));
                           SpatialUtil.pushEntity(
                              hitEntity,
                              hitEntity.position().subtract(entity.position()).multiply(1.0, 0.0, 1.0).normalize().scale(1.5 * collision.distanceModifier),
                              2.0
                           );
                        }
                     );
                  }
               }
            )
      );
      STATE_REGISTRY.register(CHASE_CIRCLE_STATE, State.builder().onTick(state -> {
         int timer = state.getTimer();
         if (timer >= 300) {
            return State.Result.END;
         }

         SandwormEntity entity = (SandwormEntity)state.getEntity();
         LivingEntity target = entity.getTarget();
         if (target == null) {
            return State.Result.CONTINUE;
         }

         float minimumDistanceToAttack = 5.0F;
         float circlingDistance = minimumDistanceToAttack + 15.0F;
         Vec3 targetPosition = target.position();
         Vec3 deltaToTarget = targetPosition.subtract(entity.position());
         double distanceToTarget = deltaToTarget.length();
         double angleToTarget = Math.acos(deltaToTarget.normalize().dot(entity.getLookAngle())) * 180.0F / (float)Math.PI;
         if (distanceToTarget >= minimumDistanceToAttack && angleToTarget < 20.0) {
            return State.Result.END;
         }

         if (angleToTarget < 110.0 && timer >= 30) {
            boolean isAttackLineSuitable = true;
            if (isAttackLineSuitable) {
               entity.getMoveControl().setWantedPosition(targetPosition.x(), targetPosition.y(), targetPosition.z(), 2.5);
               return State.Result.CONTINUE;
            }
         }

         entity.circleAroundPosition(target.position(), circlingDistance, 3.0F, distanceToTarget < minimumDistanceToAttack ? 1.0F : 20.0F);
         return State.Result.CONTINUE;
      }));
      STATE_REGISTRY.register(
         CHASE_CHARGE_STATE,
         State.builder()
            .onStart(state -> {
               SandwormEntity entity = (SandwormEntity)state.getEntity();
               entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_CHARGE.value());
            })
            .onTick(
               state -> {
                  SandwormEntity entity = (SandwormEntity)state.getEntity();
                  LivingEntity target = entity.getTarget();
                  int timer = state.getTimer();
                  if (timer > 40) {
                     return State.Result.END;
                  }

                  Vec3 lookDirection = entity.getLookAngle();
                  Vec3 positionAhead = lookDirection.multiply(1.0, 0.0, 1.0).normalize().scale(20.0).add(entity.position());
                  Vec3 targetPosition = target != null ? target.position() : positionAhead;
                  double distanceToTarget = lookDirection.dot(targetPosition.subtract(entity.position()));
                  double previousDistanceToTarget = distanceToTarget
                     + new Vec3(entity.getX() - entity.xOld, entity.getY() - entity.yOld, entity.getZ() - entity.zOld).length();
                  double forcedColumnAt = 0.5;
                  Vec3 sandColumnOffset = null;
                  if (timer % 6 != 0
                     || !(distanceToTarget < 15.0)
                     || !(distanceToTarget > -10.0)
                     || target != null && !(Math.abs(distanceToTarget - forcedColumnAt) > 2.0)) {
                     if (previousDistanceToTarget >= forcedColumnAt && distanceToTarget < forcedColumnAt) {
                        if (target == null) {
                           sandColumnOffset = new Vec3(0.0, 0.0, 0.0);
                        } else {
                           Vec3 offsetToTarget = target.position().subtract(entity.position()).multiply(1.0, 0.0, 1.0);
                           sandColumnOffset = offsetToTarget.normalize().scale(Math.min(1.0, offsetToTarget.length()));
                        }
                     }
                  } else {
                     sandColumnOffset = lookDirection.cross(new Vec3(0.0, 1.0, 0.0)).normalize().scale(entity.getRandom().nextFloat() - 0.5F);
                  }

                  if (sandColumnOffset != null) {
                     SandColumnEntity.spawnSandColumn((ServerLevel)entity.level(), entity.position().add(sandColumnOffset), 0, entity);
                  }

                  Vec3 wantedPosition;
                  if (distanceToTarget > 0.0) {
                     wantedPosition = targetPosition;
                  } else {
                     if (!(distanceToTarget > -20.0)) {
                        return State.Result.END;
                     }

                     wantedPosition = positionAhead;
                  }

                  entity.getMoveControl().setWantedPosition(wantedPosition.x(), wantedPosition.y(), wantedPosition.z(), 4.0);
                  return State.Result.CONTINUE;
               }
            )
      );
      STATE_REGISTRY.register(EARTHQUAKE_START_STATE, State.builder().onStart(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         entity.triggerAnim(EARTHQUAKE_START_STATE.getName());
      }).onTickFor(210, state -> {
         int timer = state.getTimer();
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         ServerLevel level = (ServerLevel)entity.level();

         String anchor = switch (timer) {
            case 124 -> "body1_fx";
            case 131 -> "body3_fx";
            case 136 -> "body5_fx";
            case 148 -> "body7_fx";
            case 161 -> "body9_fx";
            case 169 -> "body11_fx";
            case 184 -> "body13_fx";
            case 192 -> "body15_fx";
            default -> null;
         };
         if (anchor != null) {
            Vector3f position = entity.getBoneWorldPosition(anchor);
            level.sendParticles((SimpleParticleType)BossesRiseParticleTypes.DUST_CLOUD.get(), position.x(), position.y(), position.z(), 10, 0.1, 0.1, 0.1, 1.0);
            level.sendParticles((SimpleParticleType)BossesRiseParticleTypes.SAND_ROCK.get(), position.x(), position.y(), position.z(), 10, 0.1, 0.2, 0.1, 1.0);
         }

         if (timer == 24) {
            entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_SCREECH.value());
         } else if (timer == 121) {
            entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_DIVE_LONG.value());
         }
      }).onEnd(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         entity.applyUndergroundEffects();
      }));
      STATE_REGISTRY.register(EARTHQUAKE_APPROACH_STATE, State.builder().onTick(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         int timer = state.getTimer();
         if (timer >= 200) {
            return State.Result.END;
         }

         LivingEntity target = entity.getTarget();
         if (target != null) {
            float circleRadius = 10.0F;
            if (timer > 50 && entity.closerThan(target, circleRadius + 3.0F)) {
               return State.Result.END;
            }

            entity.circleAroundPosition(target.position(), circleRadius, 2.0F);
         }

         return State.Result.CONTINUE;
      }).onEnd(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         entity.snapToGround();
      }));
      STATE_REGISTRY.register(
         EARTHQUAKE_SLAM_EMERGE_STATE,
         State.builder()
            .onStart(state -> {
               SandwormEntity entity = (SandwormEntity)state.getEntity();
               entity.triggerAnim(EARTHQUAKE_SLAM_EMERGE_STATE.getName());
               entity.setHiddenUnderground(false);
               entity.applyUndergroundEffects();
            })
            .onTickFor(
               70,
               state -> {
                  SandwormEntity entity = (SandwormEntity)state.getEntity();
                  int timer = state.getTimer();
                  if (timer == 18) {
                     entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_EMERGE.value());
                  } else if (timer == 22) {
                     ServerLevel level = (ServerLevel)entity.level();
                     Vec3 position = entity.position();
                     level.sendParticles(
                        (SimpleParticleType)BossesRiseParticleTypes.DUST_CLOUD.get(), position.x(), position.y() - 1.0, position.z(), 40, 0.3, 0.2, 0.3, 0.3
                     );
                     level.sendParticles(
                        (SimpleParticleType)BossesRiseParticleTypes.GROUND_DUST.get(), position.x(), position.y() - 1.0, position.z(), 20, 0.3, 0.2, 0.3, 0.5
                     );
                     level.sendParticles(
                        (SimpleParticleType)BossesRiseParticleTypes.SAND_ROCK.get(), position.x(), position.y() - 1.0, position.z(), 10, 0.1, 0.2, 0.1, 0.6
                     );
                     entity.attackCollidingEntities(position.add(0.0, 0.0, 0.0), position.add(0.0, 2.0, 0.0), 1.5, 0.25F);
                  }
               }
            )
      );
      STATE_REGISTRY.register(
         EARTHQUAKE_SLAM_STATE,
         State.builder()
            .onStart(state -> {
               SandwormEntity entity = (SandwormEntity)state.getEntity();
               entity.triggerAnim(EARTHQUAKE_SLAM_STATE.getName());
            })
            .onTickFor(
               176,
               state -> {
                  int ERUPTION_DELAY = 20;
                  int timer = state.getTimer();
                  SandwormEntity entity = (SandwormEntity)state.getEntity();
                  ServerLevel level = (ServerLevel)entity.level();

                  String anchor = switch (timer) {
                     case 26, 82, 127, 151 -> "body13_fx";
                     case 27, 83, 128, 152 -> "body14_fx";
                     case 28, 84, 129, 153 -> "body15_fx";
                     case 29, 85, 130, 154 -> "body16_fx";
                     default -> null;
                  };
                  if (anchor != null) {
                     Vector3f position = entity.getBoneWorldPosition(anchor);
                     level.sendParticles(
                        (SimpleParticleType)BossesRiseParticleTypes.DUST_CLOUD.get(), position.x(), position.y(), position.z(), 10, 0.1, 0.1, 0.1, 1.0
                     );
                     level.sendParticles(
                        (SimpleParticleType)BossesRiseParticleTypes.SAND_ROCK.get(), position.x(), position.y(), position.z(), 10, 0.1, 0.2, 0.1, 1.0
                     );
                  }

                  if (timer == 28 || timer == 85 || timer == 127 || timer == 151) {
                     RandomSource random = entity.getRandom();
                     LivingEntity target = entity.getTarget();
                     Vec3 centerPos = target != null ? target.position() : entity.position().offsetRandom(random, 30.0F);
                     int squareSize = target == null ? 10 : 4;
                     int squareHalfSize = target == null ? 4 : 1;

                     for (int gridX = -2; gridX <= 2; gridX++) {
                        for (int gridZ = -2; gridZ <= 2; gridZ++) {
                           int gridDistanceSquared = gridX * gridX + gridZ * gridZ;
                           if (gridDistanceSquared <= 5) {
                              double columnX = centerPos.x;
                              double columnY = centerPos.y;
                              double columnZ = centerPos.z;
                              if (gridDistanceSquared != 0) {
                                 columnX += gridX * squareSize + random.nextIntBetweenInclusive(-squareHalfSize, squareHalfSize);
                                 columnZ += gridZ * squareSize + random.nextIntBetweenInclusive(-squareHalfSize, squareHalfSize);
                              }

                              SandColumnEntity sandColumn = SandColumnEntity.spawnSandColumn(
                                 level, new Vec3(columnX, columnY, columnZ), 20 + gridDistanceSquared, entity
                              );
                              if (sandColumn != null && gridDistanceSquared != 0) {
                                 sandColumn.setSilent(true);
                              }
                           }
                        }
                     }

                     entity.attackCollidingEntities(
                        new Vec3(entity.getBoneWorldPosition("body12_fx")), new Vec3(entity.getBoneWorldPosition("body16_fx")), 8.0, 1.0F
                     );
                     entity.setCameraShakeTicks(4);
                     entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_TAIL_IMPACT.value());
                  }
               }
            )
      );
      STATE_REGISTRY.register(EARTHQUAKE_SLAM_END_STATE, State.builder().onStart(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         entity.triggerAnim(EARTHQUAKE_SLAM_END_STATE.getName());
         entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_DIVE_SHORT.value());
      }).endAfter(50).onEnd(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         entity.applyUndergroundEffects();
         entity.setHiddenUnderground(true);
      }));
      STATE_REGISTRY.register(POISON_SPIT_STATE, State.builder().onStart(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         entity.triggerAnim(POISON_SPIT_STATE.getName());
         entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_POISON_SPIT.value());
      }).onTickFor(40, state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         int timer = state.getTimer();
         if (timer < 20) {
            entity.lookAtTarget();
         }

         if (timer >= 17 && timer <= 20 && entity.getRandom().nextDouble() < 0.75) {
            entity.shootPoisonSpit(entity.getParts()[0], entity.getTarget(), false);
         }
      }));
      STATE_REGISTRY.register(SCREECH_STATE, State.builder().onStart(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         entity.triggerAnim(SCREECH_STATE.getName());
         entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_SCREECH.value());
      }).onTickFor(100, state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         entity.lookAtTarget();
         int timer = state.getTimer();
         if (timer >= 17 && timer <= 79) {
            entity.forEachNearbyEntity(64.0, Vec3.ZERO, EntitySelector.NO_SPECTATORS, otherEntity -> {
               Vec3 delta = otherEntity.position().subtract(entity.position());
               double distance = delta.length();
               if (!(distance > 64.0)) {
                  Vec3 pushForce = delta.normalize().scale(Mth.clampedMap(distance, 0.0, 20.0, 0.3F, 0.15F));
                  SpatialUtil.pushEntity(otherEntity, new Vec3(pushForce.x, 0.0, pushForce.z), 2.0);
               }
            });
         }
      }));
      STATE_REGISTRY.register(
         DYING_STATE,
         State.builder()
            .onStart(state -> {
               SandwormEntity entity = (SandwormEntity)state.getEntity();
               entity.triggerAnim(DYING_STATE.getName());
               entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_DEATH.value());
            })
            .onTickFor(
               98,
               state -> {
                  SandwormEntity entity = (SandwormEntity)state.getEntity();
                  int timer = state.getTimer();

                  String[] anchors = switch (timer) {
                     case 91 -> new String[]{"body8_fx", "body7_fx", "body6_fx", "body5_fx"};
                     case 92 -> new String[]{"body4_fx", "body3_fx", "body2_fx", "body1_fx"};
                     default -> null;
                     case 95 -> new String[]{"body6_fx", "body5_fx", "body4_fx", "body3_fx"};
                     case 96 -> new String[]{"body8_fx", "body7_fx", "body2_fx", "body1_fx"};
                  };
                  if (anchors != null) {
                     ServerLevel level = (ServerLevel)entity.level();

                     for (String anchor : anchors) {
                        Vector3f position = entity.getBoneWorldPosition(anchor);
                        level.sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.DUST_CLOUD.get(),
                           position.x(),
                           position.y() - 2.0F,
                           position.z(),
                           10,
                           0.1,
                           0.1,
                           0.1,
                           1.0
                        );
                        level.sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.SAND_ROCK.get(), position.x(), position.y() - 2.0F, position.z(), 10, 0.1, 0.2, 0.1, 1.0
                        );
                     }
                  }

                  if (timer == 91) {
                     entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_BODY_IMPACT.value());
                  }
               }
            )
            .onEnd(state -> state.getController().replaceAll(DEAD_STATE))
      );
      STATE_REGISTRY.register(DEAD_STATE, State.builder().endAfter(300).onEnd(state -> {
         SandwormEntity entity = (SandwormEntity)state.getEntity();
         entity.simulatePlayerKill();
      }));
      STATE_REGISTRY.register(DIVE_ATTACK_STATE, StateSequence.of(DIVE_START_STATE, DIVE_HIDDEN_STATE, DIVE_EMERGE_WARNING_STATE));
      STATE_REGISTRY.register(BITE_ATTACK_STATE, StateSequence.of(DIVE_EMERGE_WARNING_STATE, BITE_STATE));
      STATE_REGISTRY.register(CHASE_ATTACK_STATE, StateSequence.of(DIVE_START_STATE, CHASE_CIRCLE_STATE, CHASE_CHARGE_STATE));
      STATE_REGISTRY.register(
         EARTHQUAKE_ATTACK_STATE,
         StateSequence.of(
            DIVE_START_STATE,
            EARTHQUAKE_APPROACH_STATE,
            EARTHQUAKE_SLAM_EMERGE_STATE,
            EARTHQUAKE_SLAM_STATE,
            EARTHQUAKE_SLAM_END_STATE,
            DIVE_HIDDEN_STATE,
            DIVE_EMERGE_WARNING_STATE
         )
      );
      STATE_REGISTRY.register(POISON_SPIT_ATTACK_STATE, StateSequence.of(DIVE_EMERGE_WARNING_STATE, POISON_SPIT_STATE));
      STATE_REGISTRY.register(SCREECH_ATTACK_STATE, StateSequence.of(DIVE_EMERGE_WARNING_STATE, SCREECH_STATE));
      STATE_REGISTRY.register(PHASE_1_PATTERN_1_STATE, StateSequence.of(DIVE_ATTACK_STATE, BITE_ATTACK_STATE));
      STATE_REGISTRY.register(PHASE_1_PATTERN_2_STATE, StateSequence.of(DIVE_ATTACK_STATE, POISON_SPIT_ATTACK_STATE));
      STATE_REGISTRY.register(PHASE_1_PATTERN_3_STATE, StateSequence.of(BITE_ATTACK_STATE, BITE_ATTACK_STATE));
      STATE_REGISTRY.register(PHASE_1_PATTERN_4_STATE, StateSequence.of(POISON_SPIT_ATTACK_STATE, DIVE_ATTACK_STATE));
      STATE_REGISTRY.register(PHASE_2_PATTERN_1_STATE, StateSequence.of(CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, DIVE_ATTACK_STATE));
      STATE_REGISTRY.register(PHASE_2_PATTERN_2_STATE, StateSequence.of(CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, POISON_SPIT_ATTACK_STATE));
      STATE_REGISTRY.register(PHASE_3_PATTERN_1_STATE, StateSequence.of(CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE));
      STATE_REGISTRY.register(PHASE_3_PATTERN_2_STATE, StateSequence.of(CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, DIVE_ATTACK_STATE));
      STATE_REGISTRY.register(
         PHASE_3_PATTERN_3_STATE,
         StateSequence.of(SCREECH_ATTACK_STATE, CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, DIVE_ATTACK_STATE, POISON_SPIT_ATTACK_STATE)
      );
      STATE_REGISTRY.register(
         PHASE_3_PATTERN_4_STATE,
         StateSequence.of(
            SCREECH_ATTACK_STATE,
            DIVE_ATTACK_STATE,
            CHASE_ATTACK_STATE,
            DIVE_ATTACK_STATE,
            DIVE_ATTACK_STATE,
            CHASE_ATTACK_STATE,
            DIVE_ATTACK_STATE,
            BITE_ATTACK_STATE
         )
      );
      STATE_REGISTRY.register(
         PHASE_4_PATTERN_1_STATE, StateSequence.of(CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, DIVE_ATTACK_STATE)
      );
      STATE_REGISTRY.register(
         PHASE_4_PATTERN_2_STATE,
         StateSequence.of(CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE)
      );
      STATE_REGISTRY.register(
         PHASE_4_PATTERN_3_STATE,
         StateSequence.of(SCREECH_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE, DIVE_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE, DIVE_ATTACK_STATE)
      );
      STATE_REGISTRY.register(GATE_1_PATTERN_STATE, StateSequence.of(SCREECH_ATTACK_STATE, CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE));
      STATE_REGISTRY.register(
         GATE_2_PATTERN_STATE,
         StateSequence.of(SCREECH_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE, CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE)
      );
      STATE_REGISTRY.register(
         GATE_3_PATTERN_STATE,
         StateSequence.of(
            SCREECH_ATTACK_STATE,
            EARTHQUAKE_ATTACK_STATE,
            EARTHQUAKE_ATTACK_STATE,
            CHASE_ATTACK_STATE,
            CHASE_ATTACK_STATE,
            CHASE_ATTACK_STATE,
            EARTHQUAKE_ATTACK_STATE,
            DIVE_ATTACK_STATE
         )
      );
   }

   private record LineCollision(Vec3 forceOrigin, Vec3 forceDirection, float distanceModifier) {
   }

   private record PartCollision(LivingEntity entity, SandwormEntityPart part, float overlapVolume) {
   }

   public class SandwormLookControl extends LookControl {
      public SandwormLookControl() {
         super(SandwormEntity.this);
      }

      public void tick() {
         this.getXRotD().ifPresent(targetXRot -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), targetXRot, this.mob.getHeadRotSpeed())));
         this.getYRotD().ifPresent(targetYRot -> {
            float differenceToTarget = Mth.degreesDifferenceAbs(targetYRot, this.mob.yHeadRot);
            int headRotSpeed = this.mob.getHeadRotSpeed();
            float smoothBelow = Math.min(headRotSpeed, 10.0F);
            float yRotSpeedLimit = differenceToTarget < smoothBelow ? differenceToTarget / 4.0F : Mth.clamp(differenceToTarget, 1.0F, headRotSpeed);
            this.mob.setYHeadRot(this.rotateTowards(this.mob.yHeadRot, targetYRot, yRotSpeedLimit));
            this.mob.setYRot(this.mob.yHeadRot);
         });
         this.mob.yBodyRot = this.mob.getYHeadRot();
      }
   }

   public class SandwormMoveControl extends MoveControl {
      public SandwormMoveControl() {
         super(SandwormEntity.this);
      }

      public void tick() {
         if (!SandwormEntity.this.isDiveMoving()) {
            this.operation = Operation.WAIT;
            this.mob.setDeltaMovement(0.0, 0.0, 0.0);
            Vec3 fallDistance = Entity.collideBoundingBox(this.mob, new Vec3(0.0, -0.025, 0.0), this.mob.getBoundingBox(), this.mob.level(), List.of());
            if (fallDistance.y() < 0.0) {
               this.mob.addDeltaMovement(fallDistance);
            }
         } else {
            this.mob.setDeltaMovement(this.mob.getDeltaMovement().scale(0.4F));
            if (this.operation == Operation.MOVE_TO) {
               this.operation = Operation.WAIT;
               this.mob.getLookControl().setLookAt(this.wantedX, this.wantedY, this.wantedZ);
               Vec3 targetDelta = this.mob.position().subtract(this.wantedX, this.wantedY, this.wantedZ);
               float distanceToTargetSquared = (float)targetDelta.lengthSqr();
               if (!(distanceToTargetSquared <= 0.25F)) {
                  double speed = this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.speedModifier;
                  double limitedSpeed = Mth.clamp(speed, 0.0, Mth.sqrt(distanceToTargetSquared) * 0.9F);
                  this.mob.setDeltaMovement(this.mob.getLookAngle().scale(limitedSpeed));
               }
            }
         }
      }
   }

   @FieldsAreNonnullByDefault
   @MethodsReturnNonnullByDefault
   @ParametersAreNonnullByDefault
   public static class SandwormPathFinder extends PathFinder {
      public SandwormPathFinder(int maxVisitedNodes) {
         super(new FlyNodeEvaluator(), maxVisitedNodes);
      }

      @Nullable
      public Path findPath(PathNavigationRegion region, Mob mob, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
         if (targetPositions.isEmpty()) {
            return null;
         }

         BlockPos mobPosition = mob.blockPosition();
         BlockPos targetPosition = targetPositions.iterator().next();
         return new Path(
            List.of(
               new Node(mobPosition.getX(), mobPosition.getY(), mobPosition.getZ()),
               new Node(targetPosition.getX(), targetPosition.getY(), targetPosition.getZ())
            ),
            targetPosition,
            true
         );
      }
   }

   @FieldsAreNonnullByDefault
   @MethodsReturnNonnullByDefault
   @ParametersAreNonnullByDefault
   public class SandwormPathNavigation extends PathNavigation {
      public SandwormPathNavigation(Level level) {
         super(SandwormEntity.this, level);
      }

      protected PathFinder createPathFinder(int maxVisitedNodes) {
         return new SandwormEntity.SandwormPathFinder(maxVisitedNodes);
      }

      protected Vec3 getTempMobPos() {
         return SandwormEntity.this.position();
      }

      protected boolean canUpdatePath() {
         return false;
      }

      protected double getGroundY(Vec3 vec) {
         return vec.y;
      }
   }
}
