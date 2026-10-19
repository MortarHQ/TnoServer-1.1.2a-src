package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.data.ESSeekerVariants;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;

public class Seeker extends Monster implements VariantHolder<Holder<SeekerVariant>> {
   private static final String TAG_VARIANT = "variant";
   public static final float TENTACLE_LENGTH = 5.0F;
   private static final byte EVENT_MOVE = 100;
   private static final byte EVENT_ATTACK = 101;
   protected static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(Seeker.class, EntityDataSerializers.STRING);
   protected static final EntityDataAccessor<Float> SEEKER_Y_ROT = SynchedEntityData.defineId(Seeker.class, EntityDataSerializers.FLOAT);
   protected static final EntityDataAccessor<Float> SEEKER_X_ROT = SynchedEntityData.defineId(Seeker.class, EntityDataSerializers.FLOAT);
   private float oldSeekerXRot;
   private float seekerXRot;
   private float oldSeekerYRot;
   private float seekerYRot;
   public AnimationState moveAnimationState = new AnimationState();
   public AnimationState attackAnimationState = new AnimationState();
   private Vec3 nextMovement = Vec3.ZERO;
   private int moveTicks;
   private int moveCooldown;
   private int attackTicks;
   private int attackCooldown;
   private float attackXRot;
   private float attackYRot;

   public ResourceLocation getVariantId() {
      return ResourceLocation.parse((String)this.getEntityData().get(VARIANT));
   }

   public void setVariantId(ResourceLocation variant) {
      this.getEntityData().set(VARIANT, variant.toString());
   }

   public void setVariant(Holder<SeekerVariant> variant) {
      if (variant.isBound()) {
         ResourceLocation key = this.level().registryAccess().registryOrThrow(ESRegistries.SEEKER_VARIANT).getKey((SeekerVariant)variant.value());
         if (key != null) {
            this.setVariantId(key);
         }
      }
   }

   public Holder<SeekerVariant> getVariant() {
      ResourceLocation key = this.getVariantId();
      Registry<SeekerVariant> variants = this.level().registryAccess().registryOrThrow(ESRegistries.SEEKER_VARIANT);
      Optional<Reference<SeekerVariant>> optional = variants.getHolder(key);
      return (Holder<SeekerVariant>)optional.orElse((Reference<SeekerVariant>)variants.getHolder(ESSeekerVariants.LUNAR).orElseThrow());
   }

   public float getSeekerYRot() {
      return (Float)this.getEntityData().get(SEEKER_Y_ROT);
   }

   public void setSeekerYRot(float yRot) {
      this.getEntityData().set(SEEKER_Y_ROT, yRot);
   }

   public float getSeekerXRot() {
      return (Float)this.getEntityData().get(SEEKER_X_ROT);
   }

   public void setSeekerXRot(float xRot) {
      this.getEntityData().set(SEEKER_X_ROT, xRot);
   }

   public Seeker(EntityType<? extends Seeker> entityType, Level level) {
      super(entityType, level);
      this.setNoGravity(true);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(VARIANT, ESSeekerVariants.LUNAR.location().toString()).define(SEEKER_X_ROT, 0.0F).define(SEEKER_Y_ROT, 0.0F);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.seeker.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.seeker.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.seeker.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.seeker.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.0)
         .add(Attributes.FLYING_SPEED, 1.0);
   }

   public float getSeekerXRot(float f) {
      return Mth.rotLerp(f, this.oldSeekerXRot, this.seekerXRot);
   }

   public float getSeekerYRot(float f) {
      return Mth.rotLerp(f, this.oldSeekerYRot, this.seekerYRot);
   }

   public void aiStep() {
      super.aiStep();
      this.setNoGravity(true);
      if (!this.level().isClientSide) {
         if (this.isAlive() && !this.isNoAi()) {
            LivingEntity target = this.getTarget();
            if (target != null && !target.isAlive()) {
               this.setTarget(null);
               target = null;
            }

            if (this.moveTicks > 0) {
               this.moveTicks--;
            }

            if (this.moveCooldown > 0) {
               this.moveCooldown--;
            }

            if (this.attackTicks > 0) {
               this.attackTicks--;
            }

            if (this.attackCooldown > 0) {
               this.attackCooldown--;
            }

            if (this.moveTicks <= 0 && this.attackTicks <= 0 && this.moveCooldown <= 0) {
               this.level().broadcastEntityEvent(this, (byte)100);
               this.moveTicks = 20;
               this.moveCooldown = target == null ? 50 : 30;
               BlockHitResult heightResult = this.level()
                  .clip(new ClipContext(this.position(), this.position().subtract(0.0, 10.0, 0.0), Block.COLLIDER, Fluid.ANY, this));
               boolean tooHigh = heightResult.getType() == Type.MISS;

               for (int i = 0; i < 32; i++) {
                  if (target != null && i == 0) {
                     Vec3 targetPos = target.position().add(0.0, target.getBbHeight() + 1.0F, 0.0);
                     Vec3 selfPos = this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0);
                     Vec3 wanted = ESMathUtil.rotationToPosition(
                        targetPos, 3.0F, (this.getRandom().nextFloat() - 0.5F) * 30.0F, ESMathUtil.positionToYaw(targetPos, selfPos) + 15.0F
                     );
                     this.nextMovement = wanted.subtract(this.position()).normalize();
                  } else {
                     this.nextMovement = new Vec3(
                           this.getRandom().nextFloat() - 0.5F,
                           tooHigh ? -Math.abs(this.getRandom().nextFloat() - 0.5F) : this.getRandom().nextFloat() - 0.5F,
                           this.getRandom().nextFloat() - 0.5F
                        )
                        .normalize();
                  }

                  BlockHitResult result = this.level()
                     .clip(
                        new ClipContext(
                           this.position(),
                           this.position().add(this.nextMovement.scale(this.getBoundingBox().getSize() + 3.0)),
                           Block.COLLIDER,
                           Fluid.ANY,
                           this
                        )
                     );
                  if (result.getType() == Type.MISS
                     && (
                        target != null && i == 0
                           || Mth.degreesDifferenceAbs(this.getSeekerXRot(), ESMathUtil.positionToPitch(this.nextMovement)) <= 90.0F
                              && Mth.degreesDifferenceAbs(this.getSeekerYRot(), ESMathUtil.positionToYaw(this.nextMovement)) <= 90.0F
                     )) {
                     break;
                  }
               }

               this.setSeekerXRot(ESMathUtil.positionToPitch(this.nextMovement));
               this.setSeekerYRot(ESMathUtil.positionToYaw(this.nextMovement));
               this.setYRot(this.getSeekerYRot() - 90.0F);
            }

            if (this.moveTicks == 15 && this.level() instanceof ServerLevel serverLevel) {
               this.hurtMarked = true;
               this.setDeltaMovement(this.nextMovement.scale(this.getAttributeValue(Attributes.FLYING_SPEED) * (target == null ? 0.2 : 0.4)));

               for (int i = 0; i < 64; i++) {
                  Vec3 particleMovement = this.nextMovement
                     .add((this.getRandom().nextDouble() - 0.5) * 0.5, (this.getRandom().nextDouble() - 0.5) * 0.5, (this.getRandom().nextDouble() - 0.5) * 0.5)
                     .normalize();
                  double particleSpeed = 0.1 + this.getRandom().nextDouble() * 0.4;
                  ESPlatform.INSTANCE
                     .sendToTrackingClients(
                        serverLevel,
                        this,
                        new ParticlePacket(
                           ColorParticleOption.create(ESParticles.COLORED_INK.get(), ((SeekerVariant)this.getVariant().value()).particleColor()),
                           this.getX() - this.nextMovement.x() * 0.5,
                           this.getY() + this.getBbHeight() / 2.0F - this.nextMovement.y() * 0.5,
                           this.getZ() - this.nextMovement.z() * 0.5,
                           -particleMovement.x() * particleSpeed,
                           -particleMovement.y() * particleSpeed,
                           -particleMovement.z() * particleSpeed
                        )
                     );
               }
            }

            if (target != null) {
               Vec3 targetPos = target.position().add(0.0, target.getBbHeight() / 2.0F, 0.0);
               Vec3 selfPos = this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0);
               if (this.attackTicks <= 0) {
                  this.attackXRot = ESMathUtil.positionToPitch(selfPos, targetPos);
                  this.attackYRot = ESMathUtil.positionToYaw(selfPos, targetPos);
               }

               AABB aabb = target.getBoundingBox().inflate(target.getPickRadius() + 0.5F);
               Vec3 endPos = ESMathUtil.rotationToPosition(selfPos, 5.0F, this.attackXRot, this.attackYRot);
               boolean canReachTarget = (aabb.contains(selfPos) || aabb.clip(selfPos, endPos).isPresent()) && this.hasLineOfSight(target);
               if (this.moveTicks <= 0 && this.attackTicks <= 0 && this.attackCooldown <= 0 && canReachTarget) {
                  this.attackTicks = 30;
                  this.attackCooldown = 50;
                  this.setDeltaMovement(Vec3.ZERO);
                  this.level().broadcastEntityEvent(this, (byte)101);
               }

               if (this.attackTicks > 10) {
                  this.setSeekerXRot(Mth.rotLerp(0.2F, this.getSeekerXRot(), -this.attackXRot));
                  this.setSeekerYRot(Mth.rotLerp(0.2F, this.getSeekerYRot(), this.attackYRot + 180.0F));
                  this.setYRot(this.getSeekerYRot() - 90.0F);
               }

               if (this.attackTicks == 10) {
                  this.setSeekerXRot(-this.attackXRot);
                  this.setSeekerYRot(this.attackYRot + 180.0F);
                  this.setYRot(this.getSeekerYRot() - 90.0F);
                  if (canReachTarget) {
                     this.doHurtTarget(target);
                  }
               }
            }
         }
      } else {
         this.oldSeekerXRot = this.seekerXRot;
         this.oldSeekerYRot = this.seekerYRot;
         this.seekerXRot = this.getSeekerXRot();
         this.seekerYRot = this.getSeekerYRot();
      }
   }

   public void handleEntityEvent(byte b) {
      if (b == 100) {
         this.moveAnimationState.start(this.tickCount);
      } else if (b == 101) {
         this.attackAnimationState.start(this.tickCount);
      } else {
         super.handleEntityEvent(b);
      }
   }

   public boolean causeFallDamage(float f, float g, DamageSource damageSource) {
      return false;
   }

   protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data) {
      this.setVariant(SeekerVariant.getSpawnVariant(level.registryAccess(), level.getBiome(this.blockPosition())));
      return super.finalizeSpawn(level, instance, spawnType, data);
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return ESSoundEvents.SEEKER_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return ESSoundEvents.SEEKER_DEATH.get();
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.setVariantId((ResourceLocation)ResourceLocation.read(compoundTag.getString("variant")).getOrThrow());
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putString("variant", this.getVariantId().toString());
   }

   public static boolean checkSeekerSpawnRules(
      EntityType<? extends Seeker> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.seeker.canSpawn();
   }
}
