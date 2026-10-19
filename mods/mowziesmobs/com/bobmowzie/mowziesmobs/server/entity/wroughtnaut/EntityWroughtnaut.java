package com.bobmowzie.mowziesmobs.server.entity.wroughtnaut;

import com.bobmowzie.mowziesmobs.client.model.tools.ControlledAnimation;
import com.bobmowzie.mowziesmobs.client.sound.BossMusic;
import com.bobmowzie.mowziesmobs.client.sound.BossMusicPlayer;
import com.bobmowzie.mowziesmobs.server.ai.MMPathNavigateGround;
import com.bobmowzie.mowziesmobs.server.ai.WroughtnautAttackAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationActivateAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationDeactivateAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationDieAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationFWNAttackAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationFWNStompAttackAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationFWNVerticalAttackAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationTakeDamage;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.entity.SmartBodyHelper;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityWroughtnaut extends MowzieLLibraryEntity implements Enemy {
   public static final Animation DIE_ANIMATION = Animation.create(130);
   public static final Animation HURT_ANIMATION = Animation.create(15);
   public static final Animation ATTACK_ANIMATION = Animation.create(50);
   public static final Animation ATTACK_TWICE_ANIMATION = Animation.create(36);
   public static final Animation ATTACK_THRICE_ANIMATION = Animation.create(59);
   public static final Animation VERTICAL_ATTACK_ANIMATION = Animation.create(105);
   public static final Animation STOMP_ATTACK_ANIMATION = Animation.create(40);
   public static final Animation ACTIVATE_ANIMATION = Animation.create(45);
   public static final Animation DEACTIVATE_ANIMATION = Animation.create(15);
   private static final Animation[] ANIMATIONS = new Animation[]{
      DIE_ANIMATION,
      HURT_ANIMATION,
      ATTACK_ANIMATION,
      ATTACK_TWICE_ANIMATION,
      ATTACK_THRICE_ANIMATION,
      VERTICAL_ATTACK_ANIMATION,
      STOMP_ATTACK_ANIMATION,
      ACTIVATE_ANIMATION,
      DEACTIVATE_ANIMATION
   };
   private static final float[][] VERTICAL_ATTACK_BLOCK_OFFSETS = new float[][]{{-0.1F, -0.1F}, {-0.1F, 0.1F}, {0.1F, 0.1F}, {0.1F, -0.1F}};
   private static final EntityDataAccessor<Optional<BlockPos>> REST_POSITION = SynchedEntityData.defineId(
      EntityWroughtnaut.class, EntityDataSerializers.OPTIONAL_BLOCK_POS
   );
   private static final EntityDataAccessor<Boolean> ACTIVE = SynchedEntityData.defineId(EntityWroughtnaut.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> ALWAYS_ACTIVE = SynchedEntityData.defineId(EntityWroughtnaut.class, EntityDataSerializers.BOOLEAN);
   public ControlledAnimation walkAnim = new ControlledAnimation(10);
   public boolean swingDirection;
   public boolean vulnerable;
   private EntityWroughtnaut.CeilingDisturbance disturbance;
   public Vec3 leftEyePos;
   public Vec3 rightEyePos;
   public Vec3 leftEyeRot;
   public Vec3 rightEyeRot;

   public EntityWroughtnaut(EntityType<? extends EntityWroughtnaut> type, Level world) {
      super(type, world);
      this.xpReward = 30;
      this.active = false;
      this.dropAfterDeathAnim = true;
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(1, new AnimationFWNAttackAI(this, 4.0F, 5.0F, 100.0F));
      this.goalSelector
         .addGoal(1, new AnimationFWNVerticalAttackAI(this, VERTICAL_ATTACK_ANIMATION, (SoundEvent)MMSounds.ENTITY_WROUGHT_WHOOSH.get(), 1.0F, 5.0F, 40.0F));
      this.goalSelector.addGoal(1, new AnimationFWNStompAttackAI(this, STOMP_ATTACK_ANIMATION));
      this.goalSelector.addGoal(1, new AnimationTakeDamage<>(this));
      this.goalSelector.addGoal(1, new AnimationDieAI<>(this));
      this.goalSelector.addGoal(1, new AnimationActivateAI<>(this, ACTIVATE_ANIMATION));
      this.goalSelector.addGoal(1, new AnimationDeactivateAI<>(this, DEACTIVATE_ANIMATION));
      this.goalSelector.addGoal(2, new WroughtnautAttackAI(this));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, 0, true, false, null));
   }

   protected PathNavigation createNavigation(Level world) {
      return new MMPathNavigateGround(this, world);
   }

   protected BodyRotationControl createBodyControl() {
      return new SmartBodyHelper(this);
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)MMSounds.ENTITY_WROUGHT_HURT_1.get();
   }

   public SoundEvent getDeathSound() {
      this.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_SCREAM.get(), 1.0F, 1.0F);
      return null;
   }

   protected SoundEvent getAmbientSound() {
      return this.getAnimation() == NO_ANIMATION && this.isActive() ? (SoundEvent)MMSounds.ENTITY_WROUGHT_AMBIENT.get() : null;
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes()
         .add(Attributes.ATTACK_DAMAGE, 30.0)
         .add(Attributes.MAX_HEALTH, 40.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.STEP_HEIGHT, 1.0)
         .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.9);
   }

   @Override
   public boolean hurt(DamageSource source, float amount) {
      Entity entitySource = source.getEntity();
      if (entitySource != null) {
         if ((!this.active || this.getTarget() == null)
            && entitySource instanceof LivingEntity
            && (!(entitySource instanceof Player) || !((Player)entitySource).isCreative())
            && !(entitySource instanceof EntityWroughtnaut)) {
            this.setTarget((LivingEntity)entitySource);
         }

         if (this.vulnerable) {
            int arc = 220;
            float entityHitAngle = (float)(
               (Math.atan2(entitySource.getZ() - this.getZ(), entitySource.getX() - this.getX()) * (180.0 / Math.PI) - 90.0) % 360.0
            );
            float entityAttackingAngle = this.yBodyRot % 360.0F;
            if (entityHitAngle < 0.0F) {
               entityHitAngle += 360.0F;
            }

            if (entityAttackingAngle < 0.0F) {
               entityAttackingAngle += 360.0F;
            }

            float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
            if ((!(entityRelativeAngle <= arc / 2.0F) || !(entityRelativeAngle >= -arc / 2.0F))
               && !(entityRelativeAngle >= 360.0F - arc / 2.0F)
               && !(entityRelativeAngle <= -arc + 45.0F)) {
               this.setAnimation(NO_ANIMATION);
               return super.hurt(source, amount);
            }

            this.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_UNDAMAGED.get(), 0.4F, 2.0F);
            return false;
         }

         this.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_UNDAMAGED.get(), 0.4F, 2.0F);
      } else if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return super.hurt(source, amount);
      }

      return false;
   }

   @Override
   public Animation getDeathAnimation() {
      return DIE_ANIMATION;
   }

   @Override
   public Animation getHurtAnimation() {
      return HURT_ANIMATION;
   }

   public boolean displayFireAnimation() {
      return false;
   }

   public boolean isPushedByFluid() {
      return false;
   }

   @Override
   public void tick() {
      super.tick();
      this.walkAnim.updatePrevTimer();
      if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget().getHealth() <= 0.0F)) {
         this.setTarget(null);
      }

      if (!this.level().isClientSide) {
         if (this.isAlwaysActive()) {
            this.setActive(true);
            this.active = true;
         } else if (this.getAnimation() == NO_ANIMATION && !this.isNoAi()) {
            if (this.isActive()) {
               if (this.getTarget() == null && this.zza == 0.0F && this.isAtRestPos()) {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this, DEACTIVATE_ANIMATION);
                  this.setActive(false);
               }
            } else if (this.getTarget() != null && this.targetDistance <= 4.5) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_ANIMATION);
               this.setActive(true);
            }
         }
      }

      if (!this.isActive()) {
         this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
         this.setYRot(this.yRotO);
      }

      if (this.getAnimation() != NO_ANIMATION || !this.isActive()) {
         this.yHeadRot = this.yBodyRot = this.getYRot();
      }

      if (!this.isAlwaysActive() && this.getTarget() == null && this.getNavigation().isDone() && !this.isAtRestPos() && this.isActive()) {
         this.updateRestPos();
      }

      if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 1) {
         this.swingDirection = this.random.nextBoolean();
      } else if (this.getAnimation() == ACTIVATE_ANIMATION) {
         int tick = this.getAnimationTick();
         if (tick == 1) {
            this.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_GRUNT_2.get(), 1.0F, 1.0F);
         } else if (tick == 27 || tick == 44) {
            this.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_STEP.get(), 0.5F, 0.5F);
         }
      } else if (this.getAnimation() == VERTICAL_ATTACK_ANIMATION && this.getAnimationTick() == 29) {
         this.doVerticalAttackHitFX();
      } else if (this.getAnimation() == STOMP_ATTACK_ANIMATION) {
         this.doStompFX();
      }

      float moveX = (float)(this.getX() - this.xo);
      float moveZ = (float)(this.getZ() - this.zo);
      float speed = Mth.sqrt(moveX * moveX + moveZ * moveZ);
      if (speed > 0.01) {
         if (this.getAnimation() == NO_ANIMATION) {
            this.walkAnim.increaseTimer();
         }
      } else {
         this.walkAnim.decreaseTimer();
      }

      if (this.getAnimation() != NO_ANIMATION) {
         this.walkAnim.decreaseTimer(2);
      }

      if (this.level().isClientSide && this.frame % 20 == 1 && speed > 0.03 && this.getAnimation() == NO_ANIMATION && this.isActive()) {
         this.level()
            .playLocalSound(this.getX(), this.getY(), this.getZ(), (SoundEvent)MMSounds.ENTITY_WROUGHT_STEP.get(), this.getSoundSource(), 0.5F, 0.5F, false);
      }

      this.repelEntities(1.7F, 4.0F, 1.7F, 1.7F);
      if (!this.active
         && !this.level().isClientSide
         && this.getAnimation() != ACTIVATE_ANIMATION
         && (Boolean)ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.healsOutOfBattle.get()) {
         this.heal(0.3F);
      }

      if (this.disturbance != null && this.disturbance.update()) {
         this.disturbance = null;
      }
   }

   @Override
   protected void repelEntities(float x, float y, float z, float radius) {
      super.repelEntities(x, y, z, radius);
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   private boolean isAtRestPos() {
      Optional<BlockPos> restPos = this.getRestPos();
      return restPos.isPresent() ? restPos.get().distSqr(this.blockPosition()) < 36.0 : false;
   }

   private void updateRestPos() {
      boolean reassign = true;
      if (this.getRestPos().isPresent()) {
         BlockPos pos = this.getRestPos().get();
         if (this.getNavigation().moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.2)) {
            reassign = false;
         }
      }

      if (reassign) {
         this.setRestPos(this.blockPosition());
      }
   }

   private void doVerticalAttackHitFX() {
      double theta = (this.yBodyRot - 4.0F) * (Math.PI / 180.0);
      double perpX = Math.cos(theta);
      double perpZ = Math.sin(theta);
      theta += Math.PI / 2;
      double vecX = Math.cos(theta);
      double vecZ = Math.sin(theta);
      double x = this.getX() + 4.2 * vecX;
      double y = this.getBoundingBox().minY + 0.1;
      double z = this.getZ() + 4.2 * vecZ;
      int hitY = Mth.floor(this.getY() - 0.2);

      for (int t = 0; t < VERTICAL_ATTACK_BLOCK_OFFSETS.length; t++) {
         float ox = VERTICAL_ATTACK_BLOCK_OFFSETS[t][0];
         float oy = VERTICAL_ATTACK_BLOCK_OFFSETS[t][1];
         int hitX = Mth.floor(x + ox);
         int hitZ = Mth.floor(z + oy);
         BlockPos hit = new BlockPos(hitX, hitY, hitZ);
         BlockState block = this.level().getBlockState(hit);
         if (block.getRenderShape() != RenderShape.INVISIBLE) {
            for (int n = 0; n < 6; n++) {
               double pa = this.random.nextDouble() * 2.0 * Math.PI;
               double pd = this.random.nextDouble() * 0.6 + 0.1;
               double px = x + Math.cos(pa) * pd;
               double pz = z + Math.sin(pa) * pd;
               double magnitude = this.random.nextDouble() * 4.0 + 5.0;
               double velX = perpX * magnitude;
               double velY = this.random.nextDouble() * 3.0 + 6.0;
               double velZ = perpZ * magnitude;
               if (vecX * (pz - this.getZ()) - vecZ * (px - this.getX()) > 0.0) {
                  velX = -velX;
                  velZ = -velZ;
               }

               this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, block), px, y, pz, velX, velY, velZ);
            }
         }
      }

      int hitX = Mth.floor(x);
      int ceilY = Mth.floor(this.getBoundingBox().maxY);
      int hitZ = Mth.floor(z);
      int maxHeight = 5;
      int height = 5;
      MutableBlockPos pos = new MutableBlockPos();

      while (height-- > 0) {
         pos.set(hitX, ceilY, hitZ);
         if (this.level().getBlockState(pos).blocksMotion()) {
            break;
         }

         ceilY++;
      }

      float strength = height / 5.0F;
      if (strength >= 0.0F) {
         int radius = Mth.ceil(Mth.sqrt(1.0F - strength * strength) * 5.0F);
         this.disturbance = new EntityWroughtnaut.CeilingDisturbance(hitX, ceilY, hitZ, radius, this.random.nextInt(5) + 3, this.random.nextInt(60) + 20);
      }
   }

   private void doStompFX() {
      double perpFacing = this.yBodyRot * (Math.PI / 180.0);
      double facingAngle = perpFacing + (Math.PI / 2);
      int tick = this.getAnimationTick();
      int maxDistance = 6;
      if (tick > 9 && tick < 17) {
         if (tick == 12) {
            double infront = 1.47;
            double side = -0.21;
            double vx = Math.cos(facingAngle) * 1.47;
            double vz = Math.sin(facingAngle) * 1.47;
            double perpX = Math.cos(perpFacing);
            double perpZ = Math.sin(perpFacing);
            double fx = this.getX() + vx + perpX * -0.21;
            double fy = this.getBoundingBox().minY + 0.1;
            double fz = this.getZ() + vz + perpZ * -0.21;
            int amount = 16 + this.level().random.nextInt(8);

            while (amount-- > 0) {
               double theta = this.level().random.nextDouble() * Math.PI * 2.0;
               double dist = this.level().random.nextDouble() * 0.1 + 0.25;
               double sx = Math.cos(theta);
               double sz = Math.sin(theta);
               double px = fx + sx * dist;
               double py = fy + this.level().random.nextDouble() * 0.1;
               double pz = fz + sz * dist;
               this.level().addParticle(ParticleTypes.SMOKE, px, py, pz, sx * 0.065, 0.0, sz * 0.065);
            }
         }

         if (tick % 2 == 0) {
            int distance = tick / 2 - 2;
            double spread = Math.PI * 2;
            int arcLen = Mth.ceil(distance * spread);

            for (int i = 0; i < arcLen; i++) {
               double theta = (i / (arcLen - 1.0) - 0.5) * spread + facingAngle;
               double vx = Math.cos(theta);
               double vz = Math.sin(theta);
               double px = this.getX() + vx * distance;
               double pz = this.getZ() + vz * distance;
               float factor = 1.0F - distance / 6.0F;
               if (this.level().random.nextBoolean()) {
                  int amount = this.level().random.nextInt(5);

                  while (amount-- > 0) {
                     double velX = vx * 0.075;
                     double velY = factor * 0.3 + 0.025;
                     double velZ = vz * 0.075;
                     this.level()
                        .addParticle(
                           ParticleTypes.CLOUD,
                           px + this.level().random.nextFloat() * 2.0F - 1.0,
                           this.getBoundingBox().minY + 0.1 + this.level().random.nextFloat() * 1.5,
                           pz + this.level().random.nextFloat() * 2.0F - 1.0,
                           velX,
                           velY,
                           velZ
                        );
                  }
               }
            }
         }
      }
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingData) {
      this.setRestPos(this.blockPosition());
      return super.finalizeSpawn(world, difficulty, reason, livingData);
   }

   public boolean requiresCustomPersistence() {
      return true;
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(REST_POSITION, Optional.empty());
      builder.define(ACTIVE, false);
      builder.define(ALWAYS_ACTIVE, false);
   }

   public Optional<BlockPos> getRestPos() {
      return (Optional<BlockPos>)this.getEntityData().get(REST_POSITION);
   }

   public void setRestPos(BlockPos pos) {
      this.getEntityData().set(REST_POSITION, Optional.of(pos));
   }

   public boolean isActive() {
      return (Boolean)this.getEntityData().get(ACTIVE);
   }

   public void setActive(boolean isActive) {
      this.getEntityData().set(ACTIVE, isActive);
   }

   public boolean isAlwaysActive() {
      return (Boolean)this.getEntityData().get(ALWAYS_ACTIVE);
   }

   public void setAlwaysActive(boolean isAlwaysActive) {
      this.getEntityData().set(ALWAYS_ACTIVE, isAlwaysActive);
      if (isAlwaysActive) {
         this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.2));
         this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      }
   }

   @Override
   public void writeSpawnData(@NotNull RegistryFriendlyByteBuf buffer) {
      super.writeSpawnData(buffer);
   }

   @Override
   public void addAdditionalSaveData(@NotNull CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      Optional<BlockPos> restPos = this.getRestPos();
      restPos.ifPresent(pos -> compound.put("restPos", NbtUtils.writeBlockPos(pos)));
      compound.putBoolean("active", this.isActive());
      compound.putBoolean("alwaysActive", this.isAlwaysActive());
   }

   @Override
   public void readAdditionalSaveData(@NotNull CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      NbtUtils.readBlockPos(compound, "restPos").ifPresent(this::setRestPos);
      this.setActive(compound.getBoolean("active"));
      this.active = this.isActive();
      this.setAlwaysActive(compound.getBoolean("alwaysActive"));
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.combatConfig;
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
   }

   @Override
   public Animation[] getAnimations() {
      return ANIMATIONS;
   }

   protected ResourceKey<LootTable> getDefaultLootTable() {
      return LootTableHandler.FERROUS_WROUGHTNAUT;
   }

   @Override
   public boolean hasBossBar() {
      return (Boolean)ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.hasBossBar.get();
   }

   @Override
   public BossBarColor bossBarColor() {
      return BossBarColor.RED;
   }

   @Override
   public boolean hasBossMusic() {
      return true;
   }

   @Override
   public BossMusic<?> getBossMusic() {
      return BossMusicPlayer.FERROUS_WROUGHTNAUT_MUSIC;
   }

   @Override
   protected boolean canPlayMusic() {
      return super.canPlayMusic() && (this.active || this.getAnimation() == ACTIVATE_ANIMATION);
   }

   @Override
   public boolean resetHealthOnPlayerRespawn() {
      return (Boolean)ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.resetHealthWhenRespawn.get();
   }

   private class CeilingDisturbance {
      private final int ceilX;
      private final int ceilY;
      private final int ceilZ;
      private final int radius;
      private int delay;
      private int remainingTicks;
      private final int duration;

      public CeilingDisturbance(int x, int y, int z, int radius, int delay, int remainingTicks) {
         this.ceilX = x;
         this.ceilY = y;
         this.ceilZ = z;
         this.radius = radius;
         this.delay = delay;
         this.remainingTicks = remainingTicks;
         this.duration = remainingTicks;
      }

      public boolean update() {
         if (--this.delay > 0) {
            return false;
         }

         float t = (float)this.remainingTicks / this.duration;
         int amount = Mth.ceil((1.0F - Mth.sqrt(1.0F - t * t)) * this.radius * this.radius * 0.15F);
         boolean playSound = true;
         MutableBlockPos pos = new MutableBlockPos();

         while (amount-- > 0) {
            double theta = EntityWroughtnaut.this.random.nextDouble() * Math.PI * 2.0;
            double dist = EntityWroughtnaut.this.random.nextDouble() * this.radius;
            double x = this.ceilX + Math.cos(theta) * dist;
            double y = this.ceilY - 0.1 - EntityWroughtnaut.this.random.nextDouble() * 0.3;
            double z = this.ceilZ + Math.sin(theta) * dist;
            int blockX = Mth.floor(x);
            int blockZ = Mth.floor(z);
            pos.set(blockX, this.ceilY, blockZ);
            BlockState block = EntityWroughtnaut.this.level().getBlockState(pos);
            if (block.getRenderShape() != RenderShape.INVISIBLE) {
               EntityWroughtnaut.this.level().addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, block), x, y, z, 0.0, 0.0, 0.0);
               if (playSound && EntityWroughtnaut.this.random.nextFloat() < 0.075F) {
                  SoundType sound = block.getBlock().getSoundType(block, EntityWroughtnaut.this.level(), pos, null);
                  EntityWroughtnaut.this.level()
                     .playLocalSound(
                        EntityWroughtnaut.this.getX(),
                        EntityWroughtnaut.this.getY(),
                        EntityWroughtnaut.this.getZ(),
                        sound.getBreakSound(),
                        SoundSource.BLOCKS,
                        sound.getVolume() * 2.0F,
                        sound.getPitch() * 0.6F,
                        false
                     );
                  playSound = false;
               }
            }
         }

         return --this.remainingTicks <= 0;
      }
   }
}
