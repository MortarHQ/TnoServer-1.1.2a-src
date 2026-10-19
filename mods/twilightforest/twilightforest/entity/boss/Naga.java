package twilightforest.entity.boss;

import java.util.Objects;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.entity.TFPart;
import twilightforest.entity.ai.control.NagaMoveControl;
import twilightforest.entity.ai.goal.AttemptToGoHomeGoal;
import twilightforest.entity.ai.goal.NagaMovementPattern;
import twilightforest.entity.ai.goal.NagaSmashGoal;
import twilightforest.entity.ai.goal.SimplifiedAttackGoal;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStructures;
import twilightforest.network.MovePlayerPacket;
import twilightforest.util.entities.EntityUtil;

public class Naga extends BaseTFBoss {
   private static final int DEATH_ANIMATION_DURATION = 24;
   private static final int DEATH_PARTICLES_DURATION = 100;
   private static final int TICKS_BEFORE_HEALING = 600;
   private static final int MAX_SEGMENTS = 12;
   private static final int XZ_HOME_BOUNDS = 46;
   private static final int Y_HOME_BOUNDS = 7;
   private static final double DEFAULT_SPEED = 0.5;
   private int currentSegmentCount = 0;
   private final float healthPerSegment;
   private final NagaSegment[] bodySegments = new NagaSegment[12];
   private NagaMovementPattern movementPattern;
   private int ticksSinceDamaged = 0;
   private int damageDuringCurrentStun = 0;
   public float stunlessRedOverlayProgress = 0.0F;
   private static final UUID MOVEMENT_SPEED_UUID = UUID.fromString("1fe84ad2-3b63-4922-ade7-546aae84a9e1");
   private static final EntityDataAccessor<Boolean> DATA_DAZE = SynchedEntityData.defineId(Naga.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DATA_CHARGE = SynchedEntityData.defineId(Naga.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DATA_STUNLESS = SynchedEntityData.defineId(Naga.class, EntityDataSerializers.BOOLEAN);

   public Naga(EntityType<? extends Naga> type, Level level) {
      super(type, level);
      this.xpReward = 217;
      this.noCulling = true;

      for (int i = 0; i < this.bodySegments.length; i++) {
         this.bodySegments[i] = new NagaSegment(this);
      }

      this.healthPerSegment = this.getMaxHealth() / 10.0F;
      this.moveControl = new NagaMoveControl(this);
   }

   @Override
   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_DAZE, false);
      builder.define(DATA_CHARGE, false);
      builder.define(DATA_STUNLESS, false);
   }

   public boolean isDazed() {
      return (Boolean)this.getEntityData().get(DATA_DAZE);
   }

   public void setDazed(boolean daze) {
      this.getEntityData().set(DATA_DAZE, daze);
   }

   public boolean isCharging() {
      return (Boolean)this.getEntityData().get(DATA_CHARGE);
   }

   public void setCharging(boolean charge) {
      this.getEntityData().set(DATA_CHARGE, charge);
      if (!charge) {
         this.getEntityData().set(DATA_STUNLESS, false);
      }
   }

   public boolean isStunlessCharging() {
      return (Boolean)this.getEntityData().get(DATA_STUNLESS);
   }

   public void setStunlessCharging(boolean charge) {
      this.getEntityData().set(DATA_STUNLESS, charge);
   }

   public NagaMovementPattern getMovementPattern() {
      return this.movementPattern;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new SimplifiedAttackGoal(this));
      this.goalSelector.addGoal(3, new NagaSmashGoal(this));
      this.goalSelector.addGoal(4, this.movementPattern = new NagaMovementPattern(this));
      this.goalSelector.addGoal(5, new AttemptToGoHomeGoal<Naga>(this, 1.0) {
         @Override
         public void start() {
            Naga.this.setTarget(null);
            super.start();
         }
      });
      this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0, 1) {
         public boolean canUse() {
            return Naga.this.isMobWithinHomeArea(Naga.this) && Naga.this.getTarget() == null && super.canUse();
         }

         protected Vec3 getPosition() {
            return DefaultRandomPos.getPos(this.mob, 30, 7);
         }
      });
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, false) {
         public boolean canUse() {
            return super.canUse() && Naga.this.areSelfAndTargetInHome(Naga.this.getTarget());
         }
      });
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder registerAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 120.0)
         .add(Attributes.MOVEMENT_SPEED, 0.5)
         .add(Attributes.ATTACK_DAMAGE, 5.0)
         .add(Attributes.FOLLOW_RANGE, 80.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.25)
         .add(Attributes.STEP_HEIGHT, 2.0);
   }

   private void setSegmentsPerHealth() {
      int oldSegments = this.currentSegmentCount;
      int newSegments = Mth.clamp((int)(this.getHealth() / this.healthPerSegment + (this.getHealth() > 0.0F ? 2 : 0)), 0, 12);
      this.currentSegmentCount = newSegments;
      if (newSegments < oldSegments) {
         for (int i = newSegments; i < oldSegments; i++) {
            this.bodySegments[i].selfDestruct((oldSegments - i) * 12);
         }
      } else if (newSegments > oldSegments) {
         this.activateBodySegments();
      }

      if (!this.level().isClientSide() && oldSegments != newSegments) {
         double speedMod = 12.0F / newSegments * 0.02F;
         AttributeModifier modifier = new AttributeModifier(TwilightForestMod.prefix("segment_speed_boost"), speedMod, Operation.ADD_VALUE);
         Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(modifier.id());
         Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).addTransientModifier(modifier);
      }
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType type, @Nullable SpawnGroupData data) {
      if (this.level().getDifficulty() != Difficulty.EASY && this.getAttribute(Attributes.MAX_HEALTH) != null) {
         boolean hard = this.level().getDifficulty() == Difficulty.HARD;
         AttributeModifier modifier = new AttributeModifier(TwilightForestMod.prefix("difficulty_health_boost"), hard ? 130.0 : 80.0, Operation.ADD_VALUE);
         if (!Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).hasModifier(modifier.id())) {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).addPermanentModifier(modifier);
            this.setHealth(this.getMaxHealth());
         }
      }

      return data;
   }

   public boolean isSteppingCarefully() {
      return false;
   }

   public boolean isInLava() {
      return false;
   }

   public void tick() {
      if (this.level().isClientSide()) {
         if (this.isDazed() && this.deathTime < 10) {
            for (int i = 0; i < 5; i++) {
               Vec3 pos = new Vec3(this.getX(), this.getY() + 2.15, this.getZ())
                  .add(new Vec3(1.5, 0.0, 0.0).yRot((float)Math.toRadians(this.getRandom().nextInt(360))));
               this.level().addParticle(ParticleTypes.CRIT, pos.x(), pos.y(), pos.z(), 0.0, 0.0, 0.0);
            }
         }

         if (this.isStunlessCharging() && this.deathTime <= 0) {
            this.level()
               .addParticle(ParticleTypes.ANGRY_VILLAGER, this.getRandomX(0.85F), this.blockPosition().getY() + 2.25F, this.getRandomZ(0.85F), 0.0, 0.0, 0.0);
         }

         if (this.isStunlessCharging()) {
            this.stunlessRedOverlayProgress = Math.min(0.65F, this.stunlessRedOverlayProgress + 0.01F);
         } else {
            this.stunlessRedOverlayProgress = Math.max(0.0F, this.stunlessRedOverlayProgress - 0.1F);
         }
      }

      this.setSegmentsPerHealth();
      super.tick();
      this.moveSegments();
   }

   @Override
   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.getTarget() != null && (this.distanceToSqr(this.getTarget()) > 6400.0 || !this.areSelfAndTargetInHome(this.getTarget()))) {
         this.setTarget(null);
      }

      if (EventHooks.canEntityGrief(this.level(), this)) {
         AABB bb = this.getBoundingBox();
         int minx = Mth.floor(bb.minX - 0.75);
         int miny = Mth.floor(bb.minY + (this.shouldDestroyAllBlocks() ? 1.01F : 0.5F));
         int minz = Mth.floor(bb.minZ - 0.75);
         int maxx = Mth.floor(bb.maxX + 0.75);
         int maxy = Mth.floor(bb.maxY + 1.0);
         int maxz = Mth.floor(bb.maxZ + 0.75);
         BlockPos min = new BlockPos(minx, miny, minz);
         BlockPos max = new BlockPos(maxx, maxy, maxz);
         if (this.level().hasChunksAt(min, max)) {
            for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
               BlockState state = this.level().getBlockState(pos);
               if (state.is(BlockTags.LEAVES) || this.shouldDestroyAllBlocks() && EntityUtil.canDestroyBlock(this.level(), pos, this)) {
                  this.level().destroyBlock(pos, !state.is(BlockTags.LEAVES));
               }
            }
         }
      }

      if (this.tickCount % 20 == 0 && this.isRestrictionPointValid(this.level().dimension()) && this.getY() < this.getRestrictionPoint().pos().getY() - 5) {
         this.teleportTo(this.getRestrictionPoint().pos().getX(), this.getRestrictionPoint().pos().getY(), this.getRestrictionPoint().pos().getZ());
         this.getNavigation().stop();
      }

      double d = this.getBbWidth() * 4.0F;
      Vec3 vec3d = this.isPathFinding() ? Objects.requireNonNull(this.getNavigation().getPath()).getNextEntityPos(this) : null;

      while (vec3d != null && vec3d.distanceToSqr(this.getX(), vec3d.y(), this.getZ()) < d * d) {
         this.getNavigation().getPath().advance();
         if (this.getNavigation().getPath().isDone()) {
            vec3d = null;
         } else {
            vec3d = this.getNavigation().getPath().getNextEntityPos(this);
         }
      }

      this.ticksSinceDamaged++;
      if (this.ticksSinceDamaged > 600 && this.ticksSinceDamaged % 20 == 0) {
         this.heal(1.0F);
      }

      if (this.damageDuringCurrentStun > 15) {
         this.getMovementPattern().forceCircle();
         this.damageDuringCurrentStun = 0;
      }
   }

   public boolean shouldDestroyAllBlocks() {
      return this.isCharging() || !this.isMobWithinHomeArea(this);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.NAGA_HISS.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.NAGA_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.NAGA_HURT.get();
   }

   public boolean isPushable() {
      return false;
   }

   public boolean isInvulnerableTo(DamageSource src) {
      return src.getEntity() != null && !this.isOtherEntityWithinHomeArea(src.getEntity())
         || src.getDirectEntity() != null && !this.isOtherEntityWithinHomeArea(src.getDirectEntity())
         || src.is(DamageTypeTags.IS_EXPLOSION)
         || super.isInvulnerableTo(src);
   }

   public boolean hurt(DamageSource source, float amount) {
      if (super.hurt(source, amount)) {
         this.ticksSinceDamaged = 0;
         if (this.isDazed()) {
            this.damageDuringCurrentStun += (int)amount;
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean doHurtTarget(Entity toAttack) {
      if (toAttack instanceof LivingEntity living && living.isBlocking()) {
         if (this.getMovementPattern().getState() == NagaMovementPattern.MovementState.CHARGE) {
            Vec3 motion = this.getDeltaMovement();
            toAttack.push(motion.x() * 1.5, 0.5, motion.z() * 1.5);
            this.push(motion.x() * -1.25, 0.5, motion.z() * -1.25);
            if (toAttack instanceof ServerPlayer player) {
               player.getUseItem().hurtAndBreak(5, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
               PacketDistributor.sendToPlayer(player, new MovePlayerPacket(motion.x() * 3.0, motion.y() + 0.75, motion.z() * 3.0), new CustomPacketPayload[0]);
            }

            this.hurt(this.damageSources().generic(), 2.0F);
            this.level()
               .playSound(
                  null, toAttack.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 0.8F + this.level().getRandom().nextFloat() * 0.4F
               );
            this.getMovementPattern().doDaze();
            return false;
         }

         if (this.getMovementPattern().getState() == NagaMovementPattern.MovementState.STUNLESS_CHARGE) {
            if (toAttack instanceof ServerPlayer player) {
               player.getUseItem().hurtAndBreak(10, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
               player.getCooldowns().addCooldown(player.getUseItem().getItem(), 200);
               player.stopUsingItem();
               this.level().broadcastEntityEvent(player, (byte)30);
            }

            living.hurt(this.damageSources().mobAttack(this), 4.0F);
            this.playSound(SoundEvents.FOX_BITE, 2.0F, 0.5F);
            this.getMovementPattern().doCircle();
            return false;
         }
      }

      if (!this.isDazed()) {
         boolean result = super.doHurtTarget(toAttack);
         if (result) {
            toAttack.push(-Mth.sin(this.getYRot() * (float) Math.PI / 180.0F) * 2.0F, 0.4F, Mth.cos(this.getYRot() * (float) Math.PI / 180.0F) * 2.0F);
         }

         return result;
      } else {
         return false;
      }
   }

   public float getWalkTargetValue(BlockPos pos) {
      return !this.isMobWithinHomeArea(this) ? Float.MIN_VALUE : 0.0F;
   }

   @Override
   public void remove(RemovalReason reason) {
      super.remove(reason);
      if (this.level() instanceof ServerLevel) {
         for (NagaSegment seg : this.bodySegments) {
            seg.kill();
         }
      }
   }

   @Override
   public boolean isMobWithinHomeArea(Entity entity) {
      if (!this.isRestrictionPointValid(this.level().dimension())) {
         return true;
      }

      double distX = Math.abs(this.getRestrictionPoint().pos().getX() - entity.blockPosition().getX());
      double distY = Math.abs(this.getRestrictionPoint().pos().getY() - entity.blockPosition().getY());
      double distZ = Math.abs(this.getRestrictionPoint().pos().getZ() - entity.blockPosition().getZ());
      return distX <= 46.0 && distY <= 7.0 && distZ <= 46.0;
   }

   public boolean isOtherEntityWithinHomeArea(Entity entity) {
      return this.isMobWithinHomeArea(entity);
   }

   public boolean areSelfAndTargetInHome(@Nullable Entity entity) {
      return this.isMobWithinHomeArea(this) && (entity == null || this.isOtherEntityWithinHomeArea(entity));
   }

   private void activateBodySegments() {
      for (int i = 0; i < this.currentSegmentCount; i++) {
         NagaSegment segment = this.bodySegments[i];
         segment.activate();
         segment.moveTo(this.getX() + 0.1 * i, this.getY() + 0.5, this.getZ() + 0.1 * i, this.getRandom().nextFloat() * 360.0F, 0.0F);

         for (int j = 0; j < 20; j++) {
            double d0 = this.getRandom().nextGaussian() * 0.02;
            double d1 = this.getRandom().nextGaussian() * 0.02;
            double d2 = this.getRandom().nextGaussian() * 0.02;
            this.level()
               .addParticle(
                  ParticleTypes.EXPLOSION,
                  segment.getX() + this.getRandom().nextFloat() * segment.getBbWidth() * 2.0F - segment.getBbWidth() - d0 * 10.0,
                  segment.getY() + this.getRandom().nextFloat() * segment.getBbHeight() - d1 * 10.0,
                  segment.getZ() + this.getRandom().nextFloat() * segment.getBbWidth() * 2.0F - segment.getBbWidth() - d2 * 10.0,
                  d0,
                  d1,
                  d2
               );
         }
      }
   }

   private void moveSegments() {
      for (int i = 0; i < this.bodySegments.length; i++) {
         this.bodySegments[i].tick();
         Entity leader = (Entity)(i == 0 ? this : this.bodySegments[i - 1]);
         double followX = leader.getX();
         double followY = leader.getY();
         double followZ = leader.getZ();
         float angle = (leader.getYRot() + 180.0F) * (float) Math.PI / 180.0F;
         double straightenForce = 0.05 + 1.0 / (i + 1) * 0.5;
         if (this.isDeadOrDying()) {
            straightenForce = 0.0;
         }

         double idealX = -Mth.sin(angle) * straightenForce;
         double idealZ = Mth.cos(angle) * straightenForce;
         double groundY = this.bodySegments[i].isInWall() ? followY + 2.0 : followY;
         double idealY = (groundY - followY) * straightenForce;
         Vec3 diff = new Vec3(this.bodySegments[i].getX() - followX, this.bodySegments[i].getY() - followY, this.bodySegments[i].getZ() - followZ);
         diff = diff.normalize();
         diff = diff.add(idealX, idealY, idealZ).normalize();
         double f = 2.0;
         double destX = followX + f * diff.x();
         double destY = followY + f * diff.y();
         double destZ = followZ + f * diff.z();
         this.bodySegments[i].setPos(destX, destY, destZ);
         double distance = Mth.sqrt((float)(diff.x() * diff.x() + diff.z() * diff.z()));
         this.bodySegments[i]
            .setRot((float)(Math.atan2(diff.z(), diff.x()) * 180.0 / Math.PI) + 90.0F, -((float)(Math.atan2(diff.y(), distance) * 180.0 / Math.PI)));
      }
   }

   public boolean isMultipartEntity() {
      return true;
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      TFPart.assignPartIDs(this);
   }

   @Nullable
   public PartEntity<?>[] getParts() {
      return this.bodySegments;
   }

   @Override
   public int getHomeRadius() {
      return 40;
   }

   @Override
   public ResourceKey<Structure> getHomeStructure() {
      return TFStructures.NAGA_COURTYARD;
   }

   @Override
   public Block getDeathContainer(RandomSource random) {
      return random.nextBoolean() ? (Block)TFBlocks.TWILIGHT_OAK_CHEST.get() : (Block)TFBlocks.CANOPY_CHEST.get();
   }

   @Override
   public Block getBossSpawner() {
      return (Block)TFBlocks.NAGA_BOSS_SPAWNER.get();
   }

   @Override
   public boolean isDeathAnimationFinished() {
      return this.deathTime >= 124;
   }

   @Override
   public void tickDeathAnimation() {
      if (this.deathTime >= 24) {
         Vec3 start = this.position().add(0.0, this.getBbHeight() * 0.5, 0.0);
         Vec3 end = EntityUtil.bossChestLocation(this).getCenter();
         Vec3 diff = end.subtract(start);
         double angle = Math.atan2(end.z - start.z, end.x - start.x) * 180.0F / (float)Math.PI + 180.0;
         double xMul = angle % 180.0;
         xMul = Math.min(xMul, 180.0 - xMul);
         xMul = Math.pow(xMul / 90.0, 1.5) * 2.0;
         double zMul = (angle + 90.0) % 180.0;
         zMul = Math.min(zMul, 180.0 - zMul);
         zMul = Math.pow(zMul / 90.0, 1.5) * 2.0;

         for (int p = 1; p <= 4; p++) {
            int trailTime = this.deathTime - 24 - p;
            if (trailTime >= 0) {
               for (double d = 0.0; d < 1.0; d += 0.25) {
                  double preciseTime = trailTime - d;
                  if (!(preciseTime < 0.0)) {
                     double factor = preciseTime / 100.0;
                     Vec3 particlePos = start.add(diff.scale(factor))
                        .add(
                           Math.sin(preciseTime * Math.PI * 0.075) * xMul,
                           Math.sin(preciseTime * Math.PI * 0.025) * 0.1,
                           Math.cos(preciseTime * Math.PI * 0.0625) * zMul
                        );
                     BlockHitResult blockhitresult = this.level()
                        .clip(
                           new ClipContext(
                              particlePos.add(0.0, 2.0, 0.0),
                              particlePos.subtract(0.0, 3.0, 0.0),
                              net.minecraft.world.level.ClipContext.Block.COLLIDER,
                              Fluid.WATER,
                              CollisionContext.empty()
                           )
                        );
                     particlePos = blockhitresult.getLocation().add(0.0, 0.15, 0.0);
                     this.level().addParticle(ParticleTypes.COMPOSTER, false, particlePos.x(), particlePos.y(), particlePos.z(), 0.0, 0.0, 0.0);
                  }
               }
            }
         }
      }
   }

   public void makePoofParticles() {
      if (this.getDeathSound() != null) {
         this.playSound(this.getDeathSound(), this.getSoundVolume() * 1.25F, this.getVoicePitch() * 0.25F);
      }

      this.makePoofAt(this.position());
   }

   public void makePoofAt(Vec3 pos) {
      float width = this.getBbWidth();
      float height = this.getBbHeight();

      for (int k = 0; k < 20; k++) {
         this.level()
            .addParticle(
               ParticleTypes.EXPLOSION,
               pos.x() + this.getRandom().nextFloat() * width * 2.0F - width,
               pos.y() + this.getRandom().nextFloat() * height,
               pos.z() + this.getRandom().nextFloat() * width * 2.0F - width,
               this.getRandom().nextGaussian() * 0.02,
               this.getRandom().nextGaussian() * 0.02,
               this.getRandom().nextGaussian() * 0.02
            );
      }
   }

   @Override
   public BossBarOverlay getBossBarOverlay() {
      return BossBarOverlay.NOTCHED_10;
   }

   @Override
   public int getBossBarColor() {
      return 6199574;
   }
}
