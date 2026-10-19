package net.unusual.block_factorys_bosses.entity.boss.kraken;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticleEmitter;
import net.unusual.block_factorys_bosses.entity.IParticleAttachment;
import net.unusual.block_factorys_bosses.entity.OwnableByAllEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.goals.KrakenTentacleAttackGoal;
import net.unusual.block_factorys_bosses.entity.boss.kraken.goals.KrakenTentacleCrateGoal;
import net.unusual.block_factorys_bosses.entity.boss.kraken.goals.KrakenTentacleRunawayGoal;
import net.unusual.block_factorys_bosses.entity.boss.part.AbstractEntityPartParent;
import net.unusual.block_factorys_bosses.entity.boss.part.AbstractGeoEntityPart;
import net.unusual.block_factorys_bosses.entity.control.FollowHeadBodyRotationControl;
import net.unusual.block_factorys_bosses.entity.control.NoMoveControl;
import net.unusual.block_factorys_bosses.entity.projectile.CannonballEntity;
import net.unusual.block_factorys_bosses.geckolib.CommonPoseStack;
import net.unusual.block_factorys_bosses.geckolib.ServerAnimationPlayer;
import net.unusual.block_factorys_bosses.geckolib.boss.kraken.KrakenTentacleModel;
import net.unusual.block_factorys_bosses.geckolib.boss.kraken.KrakenTentacleRenderer;
import net.unusual.block_factorys_bosses.geckolib.util.ParticleLocator;
import net.unusual.block_factorys_bosses.geckolib.util.SoundKeyframePlayer;
import net.unusual.block_factorys_bosses.init.BossesRiseDamageTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseEntityDataSerializers;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.random.WeightedPool;
import net.unusual.block_factorys_bosses.structures.KrakenShipStructure;
import net.unusual.block_factorys_bosses.util.SpatialUtil;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.animation.AnimationController.ParticleKeyframeHandler;
import software.bernie.geckolib.animation.keyframe.event.ParticleKeyframeEvent;
import software.bernie.geckolib.animation.keyframe.event.data.ParticleKeyframeData;
import software.bernie.geckolib.util.GeckoLibUtil;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KrakenTentacleEntity
   extends Monster
   implements AbstractEntityPartParent<KrakenTentacleEntity, KrakenTentacleEntityPart>,
   GeoEntity,
   OwnableByAllEntity,
   ServerAnimationPlayer.CustomTransformationsEntity,
   IParticleAttachment {
   public static final float VERTICAL_REACH_OFFSET = 7.0F;
   public static final float VERTICAL_REACH_RANGE = 3.0F;
   private static final String TAG_OWNER = "Owner";
   private static final String TAG_TENTACLE_TYPE = "tentacle_type";
   private static final String TAG_TENTACLE_ID = "tentacle_id";
   private static final String TAG_TARGET_ROTATION = "target_rotation";
   private static final String TAG_TARGET_ROTATION_RANGE = "target_rotation_range";
   private static final EntityDataAccessor<KrakenTentacleEntity.TentacleType> DATA_TENTACLE_TYPE = SynchedEntityData.defineId(
      KrakenTentacleEntity.class, (EntityDataSerializer)BossesRiseEntityDataSerializers.TENTACLE_TYPE.get()
   );
   public static final EntityDataAccessor<Vec3> DATA_LERP_POSITION = SynchedEntityData.defineId(
      KrakenTentacleEntity.class, (EntityDataSerializer)BossesRiseEntityDataSerializers.VEC_3.get()
   );
   public static final EntityDataAccessor<Long> DATA_LERP_TIME = SynchedEntityData.defineId(KrakenTentacleEntity.class, EntityDataSerializers.LONG);
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
   private static final KrakenTentacleModel MODEL = new KrakenTentacleModel(false);
   private static final KrakenTentacleModel SLIM_MODEL = new KrakenTentacleModel(true);
   private final ServerAnimationPlayer<KrakenTentacleEntity> serverAnimationPlayer = new ServerAnimationPlayer(MODEL, this);
   private static final KrakenTentacleEntity.TentaclePartDefinition[] PART_DEFINITIONS = new KrakenTentacleEntity.TentaclePartDefinition[]{
      new KrakenTentacleEntity.TentaclePartDefinition("hitbox1", 2.5F, 2.5F),
      new KrakenTentacleEntity.TentaclePartDefinition("hitbox2", 2.5F, 2.5F),
      new KrakenTentacleEntity.TentaclePartDefinition("hitbox3", 2.5F, 2.5F),
      new KrakenTentacleEntity.TentaclePartDefinition("hitbox4", 2.5F, 2.5F),
      new KrakenTentacleEntity.TentaclePartDefinition("hitbox5", 2.5F, 2.5F),
      new KrakenTentacleEntity.TentaclePartDefinition("hitbox6", 2.5F, 2.5F),
      new KrakenTentacleEntity.TentaclePartDefinition("hitbox7", 2.5F, 2.5F),
      new KrakenTentacleEntity.TentaclePartDefinition("hitbox8", 3.0F, 2.5F),
      new KrakenTentacleEntity.TentaclePartDefinition("hitbox9", 3.0F, 2.5F),
      new KrakenTentacleEntity.TentaclePartDefinition("hitbox10", 2.5F, 2.5F),
      new KrakenTentacleEntity.TentaclePartDefinition("hitbox11", 3.0F, 2.25F),
      new KrakenTentacleEntity.TentaclePartDefinition("hitbox12", 3.0F, 2.25F),
      new KrakenTentacleEntity.TentaclePartDefinition("hitbox13", 2.0F, 1.75F),
      new KrakenTentacleEntity.TentaclePartDefinition("hitbox14", 1.75F, 1.75F)
   };
   private final KrakenTentacleEntityPart[] parts = new KrakenTentacleEntityPart[PART_DEFINITIONS.length];
   @Nullable
   private UUID ownerUUID = null;
   @Nullable
   private KrakenEntity owner;
   @Nullable
   private String tentacleId = null;
   private float targetYRot = 0.0F;
   private float targetYRotRange = 180.0F;
   private static final String MAIN_CONTROLLER = "main_controller";
   private static final int DEATH_DURATION = 115;
   private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("1st_phase_idle");
   public static final String LIGHT_ATTACK_ANIM_NAME = "light_attack";
   private static final RawAnimation LIGHT_ATTACK_ANIM = RawAnimation.begin().then("light_slam", LoopType.PLAY_ONCE);
   public static final String HEAVY_ATTACK_LEFT_ANIM_NAME = "heavy_attack_left";
   private static final RawAnimation HEAVY_ATTACK_LEFT_ANIM = RawAnimation.begin().then("strong_slam", LoopType.PLAY_ONCE);
   public static final String HEAVY_ATTACK_RIGHT_ANIM_NAME = "heavy_attack_right";
   private static final RawAnimation HEAVY_ATTACK_RIGHT_ANIM = RawAnimation.begin().then("strong_slam_mirrored", LoopType.PLAY_ONCE);
   public static final String DEATH_TENTACLE_NAME = "death_tentacle";
   private static final RawAnimation DEATH_TENTACLE_ANIM = RawAnimation.begin().thenPlayAndHold("death_tentacle");
   public static final String THROW_CRATE_NAME = "phase_2_throw_crate";
   private static final RawAnimation THROW_CRATE_ANIM = RawAnimation.begin().then("phase_2_throw_crate", LoopType.PLAY_ONCE);
   public static final String STUNNED_ANIM_NAME = "stunned";
   private static final RawAnimation STUNNED_ANIM = RawAnimation.begin()
      .then("tentacle_stun_start1", LoopType.PLAY_ONCE)
      .then("tentacle_stun_loop1", LoopType.PLAY_ONCE)
      .then("tentacle_stun_end1", LoopType.PLAY_ONCE);
   public static final String HIDDEN_1_ANIM_NAME = "hidden_1";
   private static final RawAnimation HIDDEN_1_ANIM = RawAnimation.begin()
      .then("death_tentacle", LoopType.PLAY_ONCE)
      .thenPlayXTimes("hidden", 92)
      .then("Intro_cin_tentacle_3", LoopType.PLAY_ONCE);
   public static final String HIDDEN_2_ANIM_NAME = "hidden_2";
   private static final RawAnimation HIDDEN_2_ANIM = RawAnimation.begin()
      .then("death_tentacle", LoopType.PLAY_ONCE)
      .thenPlayXTimes("hidden", 80)
      .then("Intro_cin_tentacle_4", LoopType.PLAY_ONCE);
   public static final String HIDDEN_3_ANIM_NAME = "hidden_3";
   private static final RawAnimation HIDDEN_3_ANIM = RawAnimation.begin()
      .then("death_tentacle", LoopType.PLAY_ONCE)
      .thenPlayXTimes("hidden", 86)
      .then("Intro_cin_tentacle_6", LoopType.PLAY_ONCE);
   public static final String INTRO_TENTACLE_NAME_1 = "Intro_cin_tentacle_1";
   private static final RawAnimation INTRO_TENTACLE_ANIM_1 = RawAnimation.begin().then("Intro_cin_tentacle_1", LoopType.PLAY_ONCE);
   public static final String INTRO_TENTACLE_NAME_2 = "Intro_cin_tentacle_2";
   private static final RawAnimation INTRO_TENTACLE_ANIM_2 = RawAnimation.begin().then("Intro_cin_tentacle_2", LoopType.PLAY_ONCE);
   public static final String INTRO_TENTACLE_NAME_3 = "Intro_cin_tentacle_3";
   private static final RawAnimation INTRO_TENTACLE_ANIM_3 = RawAnimation.begin().then("Intro_cin_tentacle_3", LoopType.PLAY_ONCE);
   public static final String INTRO_TENTACLE_NAME_4 = "Intro_cin_tentacle_4";
   private static final RawAnimation INTRO_TENTACLE_ANIM_4 = RawAnimation.begin().then("Intro_cin_tentacle_4", LoopType.PLAY_ONCE);
   public static final String INTRO_TENTACLE_NAME_5 = "Intro_cin_tentacle_5";
   private static final RawAnimation INTRO_TENTACLE_ANIM_5 = RawAnimation.begin().then("Intro_cin_tentacle_5", LoopType.PLAY_ONCE);
   public static final String INTRO_TENTACLE_NAME_6 = "Intro_cin_tentacle_6";
   private static final RawAnimation INTRO_TENTACLE_ANIM_6 = RawAnimation.begin().then("Intro_cin_tentacle_6", LoopType.PLAY_ONCE);
   public static final String INTRO_TENTACLE_NAME_7 = "Intro_cin_tentacle_7";
   private static final RawAnimation INTRO_TENTACLE_ANIM_7 = RawAnimation.begin().then("Intro_cin_tentacle_7", LoopType.PLAY_ONCE);
   public static final String INTRO_TENTACLE_NAME_8 = "Intro_cin_tentacle_8";
   private static final RawAnimation INTRO_TENTACLE_ANIM_8 = RawAnimation.begin().then("Intro_cin_tentacle_8", LoopType.PLAY_ONCE);
   public static final String INTRO_CANNON_NAME = "intro_cannon";
   private static final RawAnimation INTRO_CANNON_ANIM = RawAnimation.begin().then("intro_tentacle_cannon", LoopType.PLAY_ONCE);
   public static final String DEATH_CANNON_NAME = "death_cannon";
   private static final RawAnimation DEATH_CANNON_ANIM = RawAnimation.begin().thenPlayAndHold("death_tentacle_cannon");
   private static final WeightedPool<String> STUNNED_ANIMATION_POOL = new WeightedPool<>(
      List.of(WeightedPool.option(2, "stunned"), WeightedPool.option(1, "hidden_1"), WeightedPool.option(1, "hidden_2"), WeightedPool.option(1, "hidden_3"))
   );
   public static final int MAX_GRAB_TIME = 80;
   private static final KrakenTentacleEntity.TentacleAttackCollider[] HEAVY_ATTACK_COLLIDERS = new KrakenTentacleEntity.TentacleAttackCollider[]{
      new KrakenTentacleEntity.TentacleAttackCollider(1.05F, 0.1F, 0.4F),
      new KrakenTentacleEntity.TentacleAttackCollider(0.6F, 0.5F, 0.4F),
      new KrakenTentacleEntity.TentacleAttackCollider(0.9F, 0.4F, 0.4F),
      new KrakenTentacleEntity.TentacleAttackCollider(1.05F, -0.2F, 0.4F),
      new KrakenTentacleEntity.TentacleAttackCollider(1.05F, -0.4F, 0.2F)
   };

   public KrakenTentacleEntityPart getThrowPart() {
      return this.parts[this.parts.length - 3];
   }

   public KrakenTentacleEntity(EntityType<? extends KrakenTentacleEntity> entityType, Level level) {
      super(entityType, level);
      this.setNoGravity(true);
      this.setPersistenceRequired();

      for (int index = 0; index < PART_DEFINITIONS.length; index++) {
         KrakenTentacleEntity.TentaclePartDefinition def = PART_DEFINITIONS[index];
         KrakenTentacleEntityPart part = new KrakenTentacleEntityPart(this, index, def.boneName(), def.boneName(), 1.0F, 1.0F);
         this.parts[index] = part;
      }

      this.setId(ENTITY_COUNTER.getAndAdd(this.parts.length + 1) + 1);
      this.updateParts();
      this.moveControl = new NoMoveControl(this);
      this.lookControl = new KrakenTentacleEntity.KrakenTentacleLookControl(this);
   }

   public void setId(int id) {
      super.setId(id);

      for (int i = 0; i < this.parts.length; i++) {
         this.parts[i].setId(id + i + 1);
      }
   }

   protected void registerGoals() {
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Player>(this, Player.class, true, false) {
         protected AABB getTargetSearchArea(double targetDistance) {
            return this.mob.getBoundingBox().inflate(targetDistance, 4.0, targetDistance).move(0.0, 5.0, 0.0);
         }
      });
      this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
      this.goalSelector
         .addGoal(
            1, new KrakenTentacleAttackGoal(this, "heavy_attack_left", 0.4F, 0.2F, 100, 20, 40, tentacle -> tentacle.attackEntitiesCollidingBox(true, 1.0F))
         );
      this.goalSelector
         .addGoal(
            1, new KrakenTentacleAttackGoal(this, "heavy_attack_right", 0.4F, 0.2F, 100, 20, 40, tentacle -> tentacle.attackEntitiesCollidingBox(false, 1.0F))
         );
      this.goalSelector
         .addGoal(2, new KrakenTentacleAttackGoal(this, "light_attack", 0.9F, 0.2F, 75, 20, 36, tentacle -> tentacle.attackEntitiesCollidingPlane(4.0F, 0.5F)));
      this.goalSelector.addGoal(3, new KrakenTentacleCrateGoal(this, "phase_2_throw_crate", 0.8F, 250, 130));
      this.goalSelector.addGoal(4, new KrakenTentacleRunawayGoal(this, "intro_cannon", 0.8F, 154, 114));
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(this, "main_controller", state -> state.setAndContinue(IDLE_ANIM))
            .triggerableAnim("light_attack", LIGHT_ATTACK_ANIM)
            .triggerableAnim("heavy_attack_left", HEAVY_ATTACK_LEFT_ANIM)
            .triggerableAnim("heavy_attack_right", HEAVY_ATTACK_RIGHT_ANIM)
            .triggerableAnim("death_tentacle", DEATH_TENTACLE_ANIM)
            .triggerableAnim("phase_2_throw_crate", THROW_CRATE_ANIM)
            .triggerableAnim("stunned", STUNNED_ANIM)
            .triggerableAnim("hidden_1", HIDDEN_1_ANIM)
            .triggerableAnim("hidden_2", HIDDEN_2_ANIM)
            .triggerableAnim("hidden_3", HIDDEN_3_ANIM)
            .triggerableAnim("Intro_cin_tentacle_1", INTRO_TENTACLE_ANIM_1)
            .triggerableAnim("Intro_cin_tentacle_2", INTRO_TENTACLE_ANIM_2)
            .triggerableAnim("Intro_cin_tentacle_3", INTRO_TENTACLE_ANIM_3)
            .triggerableAnim("Intro_cin_tentacle_4", INTRO_TENTACLE_ANIM_4)
            .triggerableAnim("Intro_cin_tentacle_5", INTRO_TENTACLE_ANIM_5)
            .triggerableAnim("Intro_cin_tentacle_6", INTRO_TENTACLE_ANIM_6)
            .triggerableAnim("Intro_cin_tentacle_7", INTRO_TENTACLE_ANIM_7)
            .triggerableAnim("Intro_cin_tentacle_8", INTRO_TENTACLE_ANIM_8)
            .triggerableAnim("intro_cannon", INTRO_CANNON_ANIM)
            .triggerableAnim("death_cannon", DEATH_CANNON_ANIM)
            .setSoundKeyframeHandler(new SoundKeyframePlayer())
            .setParticleKeyframeHandler(
               new ParticleKeyframeHandler<KrakenTentacleEntity>() {
                  public void handle(ParticleKeyframeEvent<KrakenTentacleEntity> event) {
                     if (((KrakenTentacleEntity)event.getAnimatable()).level().isClientSide()) {
                        ParticleKeyframeData data = event.getKeyframeData();
                        ParticleType<?> particleType = (ParticleType<?>)((Registry)BossesRiseParticleTypes.REGISTRY.getRegistry().get())
                           .get(BossesRise.prefix(data.getEffect()));
                        if (particleType != null) {
                           ParticleOptions particle = (ParticleOptions)particleType.codec()
                              .codec()
                              .parse(
                                 ((KrakenTentacleEntity)event.getAnimatable()).level().registryAccess().createSerializationContext(NbtOps.INSTANCE),
                                 new CompoundTag()
                              )
                              .getOrThrow();
                           ParticleLocator locator = KrakenTentacleRenderer.BONER.get(data.getLocator());
                           SubParticleEmitter.attachNewParticle((KrakenTentacleEntity)event.getAnimatable(), particle, locator);
                        }
                     }
                  }
               }
            )
      );
   }

   public void triggerAnim(@javax.annotation.Nullable String controllerName, String animName) {
      ServerAnimationPlayer.retriggerAnim(this.serverAnimationPlayer, controllerName, animName);
   }

   public void triggerAnim(String animName) {
      this.triggerAnim("main_controller", animName);
   }

   public void stopTriggeredAnim(@javax.annotation.Nullable String controllerName, @javax.annotation.Nullable String animName) {
      ServerAnimationPlayer.stopTriggeredAnim(this.serverAnimationPlayer, controllerName, animName);
   }

   public void stopTriggeredAnim(@javax.annotation.Nullable String animName) {
      this.stopTriggeredAnim("main_controller", animName);
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }

   public void playRandomStunnedAnimation() {
      if (!this.isDeadOrDying()) {
         this.triggerAnim(STUNNED_ANIMATION_POOL.selectRandom());
      }
   }

   public int getHeadRotSpeed() {
      return 5;
   }

   @Override
   public void applyCustomWorldSpaceTransformations(ServerAnimationPlayer.ServerPoseStack poseStack) {
      this.transformCrateGrab(poseStack, 1.0F);
   }

   public void transformCrateGrab(CommonPoseStack poseStack, float partialTick) {
      if (this.getTentacleType() == KrakenTentacleEntity.TentacleType.CRATE) {
         long setTime = (Long)this.getEntityData().get(DATA_LERP_TIME);
         if (setTime == 0L) {
            return;
         }

         float time = (float)(this.level().getGameTime() - (Long)this.getEntityData().get(DATA_LERP_TIME)) + partialTick;
         if (time > 80.0F) {
            return;
         }

         float factor = time / 80.0F;
         float wave = Math.clamp((float)Math.sin(factor * (float) Math.PI) * 1.5F, 0.0F, 1.0F);
         Vec3 create = (Vec3)this.getEntityData().get(DATA_LERP_POSITION);
         Vec3 cratePos = create.subtract(this.getViewVector(partialTick).scale(6.0).add(0.0, 6.0, 0.0));
         Vec3 diff = cratePos.subtract(this.position()).scale(wave);
         poseStack.bosses_rise_java$translate(diff.x(), diff.y(), diff.z());
      }
   }

   protected BodyRotationControl createBodyControl() {
      return new FollowHeadBodyRotationControl(this);
   }

   public void setTargetRotation(float targetRotation, float targetRotationRange) {
      this.targetYRot = targetRotation;
      this.targetYRotRange = targetRotationRange;
      this.setYRot(targetRotation);
   }

   public float getTargetRotation() {
      return this.targetYRot;
   }

   public float getTargetRotationRange() {
      return this.targetYRotRange;
   }

   public void setOwner(KrakenEntity entity) {
      this.ownerUUID = entity.getUUID();
   }

   @Nullable
   public KrakenEntity getOwnerKraken() {
      return this.owner;
   }

   @Nullable
   public UUID getOwnerUUID() {
      return this.ownerUUID;
   }

   private void updateOwnerReference() {
      if (this.level() instanceof ServerLevel level) {
         UUID ownerUuid = this.getOwnerUUID();
         if (ownerUuid == null) {
            this.owner = null;
         } else if (this.owner == null || !this.owner.getUUID().equals(ownerUuid) || this.owner.isRemoved() != this.isRemoved()) {
            if (level.getEntity(ownerUuid) instanceof KrakenEntity kraken) {
               this.owner = kraken;
            } else {
               this.owner = null;
            }
         }
      }
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_TENTACLE_TYPE, KrakenTentacleEntity.TentacleType.MELEE);
      builder.define(DATA_LERP_POSITION, Vec3.ZERO);
      builder.define(DATA_LERP_TIME, 0L);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
      super.onSyncedDataUpdated(key);
      if (DATA_TENTACLE_TYPE.equals(key)) {
         KrakenTentacleEntity.TentacleType tentacleType = this.getTentacleType();
         this.serverAnimationPlayer.setModel(tentacleType.isSlim() ? SLIM_MODEL : MODEL);
         this.updateParts();
      }
   }

   public void setTentacleType(KrakenTentacleEntity.TentacleType tentacleType) {
      this.entityData.set(DATA_TENTACLE_TYPE, tentacleType);
   }

   public KrakenTentacleEntity.TentacleType getTentacleType() {
      return (KrakenTentacleEntity.TentacleType)this.entityData.get(DATA_TENTACLE_TYPE);
   }

   public void setTentacleId(String tentacleId) {
      this.tentacleId = tentacleId;
   }

   @Nullable
   public String getTentacleId() {
      return this.tentacleId;
   }

   public boolean isInWall() {
      return false;
   }

   public void baseTick() {
      this.updateOwnerReference();
      super.baseTick();
      this.serverAnimationPlayer.tickAnimations();
      AbstractGeoEntityPart.positionPartsFromBones(
         this.getParts(), this.serverAnimationPlayer, this.getTentacleType().isSlim() ? Vec3.ZERO : KrakenTentacleModel.ROOT_OFFSET
      );
   }

   public boolean isAttacking() {
      for (WrappedGoal wrappedGoal : this.goalSelector.getAvailableGoals()) {
         if (wrappedGoal.isRunning()) {
            Goal goal = wrappedGoal.getGoal();
            if (goal instanceof KrakenTentacleAttackGoal || goal instanceof KrakenTentacleCrateGoal) {
               return true;
            }
         }
      }

      return false;
   }

   private List<LivingEntity> findCollidingEntities(AABB box) {
      return this.level()
         .getEntitiesOfClass(
            LivingEntity.class,
            box,
            e -> !(e instanceof KrakenTentacleEntity) && !(e instanceof KrakenEntity) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e)
         );
   }

   public void attackEntitiesCollidingPlane(float horizontalReach, float maxDamageScale) {
      float tentacleHalfWidth = horizontalReach / 2.0F;
      double tentacleReach = this.getAttributeValue(Attributes.FOLLOW_RANGE);
      Vec3 direction = this.getLookAngle().multiply(1.0, 0.0, 1.0).normalize();
      Vec3 normal = direction.cross(new Vec3(0.0, 1.0, 0.0));
      AABB box = new AABB(this.position().add(0.0, 4.0, 0.0), this.position().add(direction.scale(tentacleReach)).add(0.0, 10.0, 0.0))
         .inflate(tentacleHalfWidth, 0.0, tentacleHalfWidth);

      for (LivingEntity entity : this.findCollidingEntities(box)) {
         Vec3 entityPosition = entity.position();
         Vec3 offset = entityPosition.subtract(this.position());
         double distanceToPlaneSigned = direction.z() * offset.x() - direction.x() * offset.z();
         double distanceToPlane = Math.abs(distanceToPlaneSigned);
         if (!(distanceToPlane > tentacleHalfWidth)) {
            double distanceModifier = (tentacleHalfWidth - distanceToPlane) / tentacleHalfWidth;
            this.attackEntityWithSlam(
               new KrakenTentacleEntity.AttackTarget(
                  entity,
                  (float)distanceModifier,
                  normal.scale(Math.signum(-distanceToPlaneSigned) * distanceModifier * 0.8).add(0.0, distanceModifier * 0.6, 0.0)
               ),
               maxDamageScale
            );
         }
      }

      this.destroyShip(box);
   }

   public void attackEntitiesCollidingBox(boolean mirrorColliders, float maxDamageScale) {
      float scale = 18.0F * this.getScale();
      Vec3 direction = this.getLookAngle().multiply(1.0, 0.0, 1.0).normalize();
      Vec3 normal = direction.cross(new Vec3(0.0, 1.0, 0.0));
      Vec3 basePosition = this.position().add(0.0, 7.0, 0.0);
      ArrayList<KrakenTentacleEntity.AttackTarget> targets = new ArrayList<>();

      for (int index = 0; index < HEAVY_ATTACK_COLLIDERS.length; index++) {
         KrakenTentacleEntity.TentacleAttackCollider collider = HEAVY_ATTACK_COLLIDERS[index];
         float distanceOffset = collider.distanceOffset() * scale;
         float sideOffset = collider.sideOffset() * scale * (mirrorColliders ? -1 : 1);
         float boxSize = collider.boxSize() * scale;
         AABB box = AABB.ofSize(basePosition.add(direction.scale(distanceOffset)).add(normal.scale(sideOffset)), boxSize, 6.0, boxSize);
         Vec3 boxCenter = box.getCenter();
         if (index == 0) {
            this.destroyShip(box);
         }

         for (LivingEntity entity : this.findCollidingEntities(box)) {
            Vec3 offsetFromCenter = entity.position().subtract(boxCenter);
            float distanceModifier = Mth.clamp(1.0F - (float)offsetFromCenter.length() / boxSize, 0.0F, 1.0F);
            Vec3 impulse = offsetFromCenter.normalize().scale(distanceModifier * 0.8).add(0.0, distanceModifier * 0.6, 0.0);
            KrakenTentacleEntity.AttackTarget existingTarget = targets.stream().filter(target -> target.entity.equals(entity)).findFirst().orElse(null);
            if (existingTarget != null) {
               targets.remove(existingTarget);
            }

            targets.add(new KrakenTentacleEntity.AttackTarget(entity, distanceModifier, impulse).combinedWith(existingTarget));
         }

         this.destroyShip(box);
      }

      targets.forEach(target -> this.attackEntityWithSlam(target, maxDamageScale));
   }

   private void attackEntityWithSlam(KrakenTentacleEntity.AttackTarget target, float maxDamageScale) {
      target.entity()
         .hurt(
            this.level().damageSources().source(BossesRiseDamageTypes.KRAKEN_TENTACLE_SMASH, this, this.getOwner()),
            (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * maxDamageScale * Mth.clamp(target.distanceModifier(), 0.5F, 1.0F)
         );
      SpatialUtil.pushEntity(target.entity(), target.impulse(), 1.5);
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      return this.level().isClientSide() ? false : this.hurt(this.parts[8], damageSource, amount);
   }

   public boolean hurt(KrakenTentacleEntityPart part, DamageSource damageSource, float amount) {
      if (CannonballEntity.hasCausedDamage(damageSource)) {
         amount *= 5.0F;
      }

      return this.reallyHurt(damageSource, amount);
   }

   public boolean reallyHurt(DamageSource damageSource, float amount) {
      return super.hurt(damageSource, amount);
   }

   public void killQuietly() {
      if (!this.isDeadOrDying()) {
         this.setHealth(0.0F);
         this.die(this.level().damageSources().genericKill());
      }
   }

   public void die(DamageSource damageSource) {
      super.die(damageSource);
      if (this.dead) {
         this.triggerAnim(null, this.getTentacleType().isSlim ? "death_cannon" : "death_tentacle");
         if (this.isPassenger()) {
            this.removeVehicle();
         }
      }
   }

   public void remove(RemovalReason reason) {
      super.remove(reason);
      this.updateOwnerReference();
      if (this.owner != null) {
         this.owner.onTentacleDestroyed(this);
      }
   }

   protected void tickDeath() {
      this.deathTime++;
      if (this.deathTime >= 115 && !this.level().isClientSide() && !this.isRemoved()) {
         this.level().broadcastEntityEvent(this, (byte)60);
         this.remove(RemovalReason.KILLED);
      }
   }

   private void destroyShip(AABB box) {
      if (this.level() instanceof ServerLevel level) {
         MutableBlockPos var7 = new MutableBlockPos();

         for (int x = (int)box.minX; x <= box.maxX; x++) {
            for (int y = (int)box.minY - 2; y <= box.maxY + 2.0; y++) {
               for (int z = (int)box.minZ; z <= box.maxZ; z++) {
                  var7.set(x, y - 6, z);
                  KrakenShipStructure.replaceWithDestroyed(level, var7);
               }
            }
         }
      }
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      if (this.getOwnerUUID() != null) {
         compound.putUUID("Owner", this.getOwnerUUID());
      }

      compound.putString("tentacle_type", this.getTentacleType().getSerializedName());
      if (this.getTentacleId() != null) {
         compound.putString("tentacle_id", this.getTentacleId());
      }

      compound.putFloat("target_rotation", this.targetYRot);
      compound.putFloat("target_rotation_range", this.targetYRotRange);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.hasUUID("Owner")) {
         this.ownerUUID = compound.getUUID("Owner");
      }

      if (compound.contains("tentacle_type")) {
         this.setTentacleType(KrakenTentacleEntity.TentacleType.NAME_LOOKUP.apply(compound.getString("tentacle_type")));
      }

      if (compound.contains("tentacle_id", 8)) {
         this.setTentacleId(compound.getString("tentacle_id"));
      }

      this.targetYRot = compound.getFloat("target_rotation");
      this.targetYRotRange = compound.getFloat("target_rotation_range");
   }

   public float rotate(Rotation transformRotation) {
      this.setTargetRotation(SpatialUtil.rotateDegrees(this.getTargetRotation(), transformRotation), this.getTargetRotationRange());
      return super.rotate(transformRotation);
   }

   public float mirror(Mirror transformMirror) {
      this.setTargetRotation(SpatialUtil.mirrorDegrees(this.getTargetRotation(), transformMirror), this.getTargetRotationRange());
      return super.mirror(transformMirror);
   }

   public boolean hasLineOfSight(Entity target) {
      return this.hasLineOfSight(target, this.getTargetRotationRange());
   }

   public boolean hasLineOfSight(Entity target, float maxRotationDelta) {
      if (target.level() != this.level()) {
         return false;
      } else {
         Vec3 offset = target.position().subtract(this.position().add(0.0, 7.0, 0.0));
         double followRange = this.getAttributeValue(Attributes.FOLLOW_RANGE);
         if (this.getTentacleType() == KrakenTentacleEntity.TentacleType.CRATE) {
            return offset.lengthSqr() <= followRange * followRange * 1.5;
         } else if (Math.abs(offset.y()) >= 3.0) {
            return false;
         } else {
            return offset.lengthSqr() > followRange * followRange ? false : !(this.getDeltaFromTargetRotation(offset) > maxRotationDelta);
         }
      }
   }

   private float getDeltaFromTargetRotation(Vec3 offset) {
      return Mth.degreesDifferenceAbs((float)Mth.atan2(-offset.x(), offset.z()) * (180.0F / (float)Math.PI), this.getTargetRotation());
   }

   public boolean isMultipartEntity() {
      return true;
   }

   public KrakenTentacleEntityPart[] getParts() {
      return this.parts;
   }

   private void updateParts() {
      boolean isSlim = this.getTentacleType().isSlim();

      for (int index = 0; index < this.parts.length; index++) {
         KrakenTentacleEntityPart part = this.parts[index];
         KrakenTentacleEntity.TentaclePartDefinition def = PART_DEFINITIONS[index];
         float size = isSlim ? def.slimSize() : def.normalSize();
         part.setBoundingBoxSize(size, size);
      }
   }

   public AABB getBoundingBoxForCulling() {
      return this.getBoundingBox().inflate(50.0);
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

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MOVEMENT_SPEED, 0.0)
         .add(Attributes.MAX_HEALTH, 50.0)
         .add(Attributes.ARMOR, 20.0)
         .add(Attributes.ATTACK_DAMAGE, 10.0)
         .add(Attributes.FOLLOW_RANGE, 24.0);
   }

   private record AttackTarget(LivingEntity entity, float distanceModifier, Vec3 impulse) {
      public KrakenTentacleEntity.AttackTarget combinedWith(@Nullable KrakenTentacleEntity.AttackTarget other) {
         return other == null
            ? this
            : new KrakenTentacleEntity.AttackTarget(
               this.entity(),
               Math.max(this.distanceModifier(), other.distanceModifier()),
               this.impulse().normalize().cross(other.impulse().normalize()).scale((this.impulse().length() + other.impulse().length()) / 2.0)
            );
      }
   }

   public class KrakenTentacleLookControl extends LookControl {
      public KrakenTentacleLookControl(Mob mob) {
         super(mob);
      }

      public void tick() {
         this.mob.setXRot(0.0F);
         if (KrakenTentacleEntity.this.getTentacleType() != KrakenTentacleEntity.TentacleType.CANNON) {
            this.getYRotD().ifPresent(targetYRot -> {
               float yRot = this.rotateTowards(this.mob.getYRot(), targetYRot, this.yMaxRotSpeed);
               yRot = Mth.rotateIfNecessary(yRot, KrakenTentacleEntity.this.targetYRot, KrakenTentacleEntity.this.targetYRotRange);
               this.mob.setYRot(yRot);
               this.mob.yHeadRot = yRot;
            });
         }
      }
   }

   private record TentacleAttackCollider(float distanceOffset, float sideOffset, float boxSize) {
   }

   public record TentaclePartDefinition(String boneName, float normalSize, float slimSize) {
   }

   public enum TentacleType implements StringRepresentable {
      MELEE("melee", false),
      CANNON("cannon", true),
      CRATE("create", false),
      RUNAWAY("runaway", true);

      public static final Function<String, KrakenTentacleEntity.TentacleType> NAME_LOOKUP = StringRepresentable.createNameLookup(values(), value -> value);
      public static final IntFunction<KrakenTentacleEntity.TentacleType> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), OutOfBoundsStrategy.ZERO);
      public static final ToIntFunction<KrakenTentacleEntity.TentacleType> FROM_ID = Enum::ordinal;
      public static final StreamCodec<ByteBuf, KrakenTentacleEntity.TentacleType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, FROM_ID);
      private final String name;
      private final boolean isSlim;

      TentacleType(String name, boolean isSlim) {
         this.name = name;
         this.isSlim = isSlim;
      }

      public String getSerializedName() {
         return this.name;
      }

      public boolean isSlim() {
         return this.isSlim;
      }
   }
}
