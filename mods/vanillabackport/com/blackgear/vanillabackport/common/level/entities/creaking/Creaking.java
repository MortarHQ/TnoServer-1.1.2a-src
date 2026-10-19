package com.blackgear.vanillabackport.common.level.entities.creaking;

import com.blackgear.vanillabackport.client.registries.ModParticles;
import com.blackgear.vanillabackport.client.registries.ModSoundEvents;
import com.blackgear.vanillabackport.common.level.blockentities.CreakingHeartBlockEntity;
import com.blackgear.vanillabackport.common.level.blocks.CreakingHeartBlock;
import com.blackgear.vanillabackport.common.level.blocks.states.CreakingHeartState;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;

public class Creaking extends Monster {
   private static final EntityDataAccessor<Boolean> CAN_MOVE = SynchedEntityData.defineId(Creaking.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IS_ACTIVE = SynchedEntityData.defineId(Creaking.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IS_TEARING_DOWN = SynchedEntityData.defineId(Creaking.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Optional<BlockPos>> HOME_POS = SynchedEntityData.defineId(Creaking.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
   public static final byte CREAKING_ATTACK = 4;
   public static final byte CREAKING_HURT = 66;
   private int attackAnimationRemainingTicks;
   public final AnimationState attackAnimationState = new AnimationState();
   public final AnimationState invulnerabilityAnimationState = new AnimationState();
   public final AnimationState deathAnimationState = new AnimationState();
   private int invulnerabilityAnimationRemainingTicks;
   private boolean eyesGlowing;
   private int nextFlickerTime;
   private int playerStuckCounter;
   private int creakingDeathTime;

   public Creaking(EntityType<? extends Monster> entityType, Level level) {
      super(entityType, level);
      this.lookControl = new Creaking.CreakingLookControl(this);
      this.moveControl = new Creaking.CreakingMoveControl(this);
      this.jumpControl = new Creaking.CreakingJumpControl(this);
      GroundPathNavigation navigation = (GroundPathNavigation)this.getNavigation();
      navigation.setCanFloat(true);
      this.xpReward = 0;
   }

   public void setTransient(BlockPos pos) {
      this.setHomePos(pos);
      this.setPathfindingMalus(PathType.DAMAGE_OTHER, 8.0F);
      this.setPathfindingMalus(PathType.POWDER_SNOW, 8.0F);
      this.setPathfindingMalus(PathType.LAVA, 8.0F);
      this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
      this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
   }

   public boolean isHeartBound() {
      return this.getHomePos() != null;
   }

   protected BodyRotationControl createBodyControl() {
      return new Creaking.CreakingBodyRotationControl(this);
   }

   protected Provider<Creaking> brainProvider() {
      return CreakingAi.brainProvider();
   }

   protected Brain<?> makeBrain(Dynamic<?> dynamic) {
      return CreakingAi.makeBrain(this.brainProvider().makeBrain(dynamic));
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(CAN_MOVE, true);
      builder.define(IS_ACTIVE, false);
      builder.define(IS_TEARING_DOWN, false);
      builder.define(HOME_POS, Optional.empty());
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 1.0)
         .add(Attributes.MOVEMENT_SPEED, 0.4)
         .add(Attributes.ATTACK_DAMAGE, 3.0)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.STEP_HEIGHT, 1.0625);
   }

   public boolean canMove() {
      return (Boolean)this.entityData.get(CAN_MOVE);
   }

   public boolean doHurtTarget(Entity target) {
      if (!(target instanceof LivingEntity)) {
         return false;
      }

      this.attackAnimationRemainingTicks = 15;
      this.level().broadcastEntityEvent(this, (byte)4);
      return super.doHurtTarget(target);
   }

   public boolean hurt(DamageSource source, float amount) {
      BlockPos home = this.getHomePos();
      if (home != null && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         if (!this.isInvulnerableTo(source) && this.invulnerabilityAnimationRemainingTicks <= 0 && !this.isDeadOrDying()) {
            Player player = this.blameSourceForDamage(source);
            Entity entity = source.getDirectEntity();
            if (entity instanceof LivingEntity || entity instanceof Projectile || player != null) {
               this.invulnerabilityAnimationRemainingTicks = 8;
               this.level().broadcastEntityEvent(this, (byte)66);
               if (this.level().getBlockEntity(home) instanceof CreakingHeartBlockEntity heart && heart.isProtector(this)) {
                  if (player != null) {
                     heart.creakingHurt();
                  }

                  this.playHurtSound(source);
               }

               return true;
            }
         }

         return false;
      } else {
         return super.hurt(source, amount);
      }
   }

   @Nullable
   public Player blameSourceForDamage(DamageSource source) {
      Entity entity = source.getEntity();
      if (entity instanceof LivingEntity living && !source.is(DamageTypeTags.NO_ANGER)) {
         this.setLastHurtByMob(living);
         if (entity instanceof Player player) {
            this.lastHurtByPlayerTime = 100;
            this.lastHurtByPlayer = player;
         } else if (entity instanceof Wolf wolf && wolf.isTame()) {
            this.lastHurtByPlayerTime = 100;
            if (wolf.getOwner() instanceof Player player) {
               this.lastHurtByPlayer = player;
            } else {
               this.lastHurtByPlayer = null;
            }
         }
      }

      return this.lastHurtByPlayer;
   }

   public boolean isPushable() {
      return super.isPushable() && this.canMove();
   }

   public void push(double x, double y, double z) {
      if (this.canMove()) {
         super.push(x, y, z);
      }
   }

   public Brain<Creaking> getBrain() {
      return super.getBrain();
   }

   protected void customServerAiStep() {
      ProfilerFiller profiler = this.level().getProfiler();
      profiler.push("creakingBrain");
      this.getBrain().tick((ServerLevel)this.level(), this);
      profiler.pop();
      CreakingAi.updateActivity(this);
   }

   public void aiStep() {
      if (this.invulnerabilityAnimationRemainingTicks > 0) {
         this.invulnerabilityAnimationRemainingTicks--;
      }

      if (this.attackAnimationRemainingTicks > 0) {
         this.attackAnimationRemainingTicks--;
      }

      if (!this.level().isClientSide()) {
         boolean canMove = (Boolean)this.entityData.get(CAN_MOVE);
         boolean checkCanMove = this.checkCanMove();
         if (checkCanMove != canMove) {
            if (checkCanMove) {
               this.playSound(ModSoundEvents.CREAKING_UNFREEZE.get());
            } else {
               this.getNavigation().stop();
               this.setXxa(0.0F);
               this.setYya(0.0F);
               this.setSpeed(0.0F);
               this.playSound(ModSoundEvents.CREAKING_FREEZE.get());
            }
         }

         this.entityData.set(CAN_MOVE, checkCanMove);
      }

      super.aiStep();
   }

   public void tick() {
      if (!this.level().isClientSide()) {
         BlockPos pos = this.getHomePos();
         if (pos != null) {
            boolean isProtector = this.level().getBlockEntity(pos) instanceof CreakingHeartBlockEntity heart && heart.isProtector(this);
            if (!isProtector) {
               this.setHealth(0.0F);
            }
         }
      }

      super.tick();
      if (this.level().isClientSide()) {
         if (this.isTearingDown() && this.deathTime != 0) {
            this.deathTime = 0;
         }

         this.setupAnimationStates();
         this.checkEyeBlink();
      }
   }

   protected void tickDeath() {
      if (this.isHeartBound() && this.isTearingDown()) {
         this.creakingDeathTime++;
         if (!this.level().isClientSide() && this.creakingDeathTime > 45 && !this.isRemoved()) {
            this.tearDown();
         }
      } else {
         super.tickDeath();
      }
   }

   protected void updateWalkAnimation(float partialTick) {
      float speed = Math.min(partialTick * 25.0F, 3.0F);
      this.walkAnimation.update(speed, 0.4F);
   }

   private void setupAnimationStates() {
      this.attackAnimationState.animateWhen(this.attackAnimationRemainingTicks > 0, this.tickCount);
      this.invulnerabilityAnimationState.animateWhen(this.invulnerabilityAnimationRemainingTicks > 0, this.tickCount);
      this.deathAnimationState.animateWhen(this.isTearingDown(), this.tickCount);
   }

   public void tearDown() {
      if (this.level() instanceof ServerLevel server) {
         AABB aabb = this.getBoundingBox();
         Vec3 center = aabb.getCenter();
         double x = aabb.getXsize() * 0.3;
         double y = aabb.getYsize() * 0.3;
         double z = aabb.getZsize() * 0.3;
         ModParticles.sendParticles(
            server,
            new BlockParticleOption(ParticleTypes.BLOCK, ModBlocks.PALE_OAK_WOOD.get().defaultBlockState()),
            center.x,
            center.y,
            center.z,
            100,
            x,
            y,
            z,
            0.0
         );
         ModParticles.sendParticles(
            server,
            new BlockParticleOption(
               ParticleTypes.BLOCK, (BlockState)ModBlocks.CREAKING_HEART.get().defaultBlockState().setValue(CreakingHeartBlock.STATE, CreakingHeartState.AWAKE)
            ),
            center.x,
            center.y,
            center.z,
            10,
            x,
            y,
            z,
            0.0
         );
      }

      this.playSound(this.getDeathSound());
      this.discard();
   }

   public void creakingDeathEffects(DamageSource source) {
      this.blameSourceForDamage(source);
      this.die(source);
      this.playSound(ModSoundEvents.CREAKING_TWITCH.get());
   }

   public void handleEntityEvent(byte id) {
      if (id == 66) {
         this.invulnerabilityAnimationRemainingTicks = 8;
         this.playHurtSound(this.damageSources().generic());
      } else if (id == 4) {
         this.attackAnimationRemainingTicks = 15;
         this.playAttackSound();
      } else {
         super.handleEntityEvent(id);
      }
   }

   public boolean fireImmune() {
      return this.isHeartBound() || super.fireImmune();
   }

   protected boolean canAddPassenger(Entity passenger) {
      return !this.isHeartBound() && super.canAddPassenger(passenger);
   }

   protected boolean couldAcceptPassenger() {
      return !this.isHeartBound() && super.couldAcceptPassenger();
   }

   protected void addPassenger(Entity passenger) {
      if (!this.isHeartBound()) {
         super.addPassenger(passenger);
      }
   }

   public boolean canUsePortal(boolean allowPassengers) {
      return !this.isHeartBound() && super.canUsePortal(allowPassengers);
   }

   protected PathNavigation createNavigation(Level level) {
      return new Creaking.CreakingPathNavigation(this, level);
   }

   public boolean playerIsStuckInYou() {
      List<Player> players = this.brain.getMemory(MemoryModuleType.NEAREST_PLAYERS).orElse(List.of());
      if (!players.isEmpty()) {
         AABB aabb = this.getBoundingBox();

         for (Player player : players) {
            if (aabb.contains(player.getEyePosition())) {
               this.playerStuckCounter++;
               return this.playerStuckCounter > 4;
            }
         }
      }

      this.playerStuckCounter = 0;
      return false;
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.contains("home_pos")) {
         this.setTransient((BlockPos)NbtUtils.readBlockPos(tag, "home_pos").get());
      }
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      if (this.getHomePos() != null) {
         tag.put("home_pos", NbtUtils.writeBlockPos(this.getHomePos()));
      }
   }

   public void setHomePos(BlockPos pos) {
      this.entityData.set(HOME_POS, Optional.of(pos));
   }

   public BlockPos getHomePos() {
      return (BlockPos)((Optional)this.entityData.get(HOME_POS)).orElse(null);
   }

   public void setTearingDown() {
      this.entityData.set(IS_TEARING_DOWN, true);
   }

   public boolean isTearingDown() {
      return (Boolean)this.entityData.get(IS_TEARING_DOWN);
   }

   public boolean hasGlowingEyes() {
      return this.eyesGlowing;
   }

   public boolean shouldEyesGlow() {
      return this.isTearingDown() ? this.hasGlowingEyes() : this.isActive();
   }

   public void checkEyeBlink() {
      if (this.creakingDeathTime > this.nextFlickerTime) {
         this.nextFlickerTime = this.creakingDeathTime
            + this.getRandom().nextIntBetweenInclusive(this.eyesGlowing ? 2 : this.creakingDeathTime / 4, this.eyesGlowing ? 8 : this.creakingDeathTime / 2);
         this.eyesGlowing = !this.eyesGlowing;
      }
   }

   public void playAttackSound() {
      this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), ModSoundEvents.CREAKING_ATTACK.get(), this.getSoundSource(), 1.0F, 1.0F, false);
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return this.isActive() ? null : ModSoundEvents.CREAKING_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return this.isHeartBound() ? ModSoundEvents.CREAKING_SWAY.get() : super.getHurtSound(source);
   }

   protected SoundEvent getDeathSound() {
      return ModSoundEvents.CREAKING_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(ModSoundEvents.CREAKING_STEP.get(), 0.15F, 1.0F);
   }

   @Nullable
   public LivingEntity getTarget() {
      return (LivingEntity)this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
   }

   public void knockback(double strength, double x, double z) {
      if (this.canMove()) {
         super.knockback(strength, x, z);
      }
   }

   public boolean checkCanMove() {
      List<Player> players = this.brain.getMemory(MemoryModuleType.NEAREST_PLAYERS).orElse(List.of());
      boolean isActive = this.isActive();
      if (players.isEmpty()) {
         if (isActive) {
            this.deactivate();
         }
      } else {
         boolean canMove = false;

         for (Player player : players) {
            if (this.canAttack(player) && !this.isAlliedTo(player)) {
               canMove = true;
               if ((!isActive || !player.getItemBySlot(EquipmentSlot.HEAD).is(Blocks.CARVED_PUMPKIN.asItem()))
                  && this.isLookingAtMe(player, 0.5, false, true, this.getEyeY(), this.getY() + 0.5 * this.getScale(), (this.getEyeY() + this.getY()) / 2.0)) {
                  if (isActive) {
                     return false;
                  }

                  if (player.distanceToSqr(this) < 144.0) {
                     this.activate(player);
                     return false;
                  }
               }
            }
         }

         if (!canMove && isActive) {
            this.deactivate();
         }
      }

      return true;
   }

   public boolean isLookingAtMe(LivingEntity entity, double tolerance, boolean scaleWithDistance, boolean checkVisibility, double... heightTargets) {
      Vec3 viewVector = entity.getViewVector(1.0F).normalize();

      for (double heightTarget : heightTargets) {
         Vec3 directionToMe = new Vec3(this.getX() - entity.getX(), heightTarget - entity.getEyeY(), this.getZ() - entity.getZ());
         double distance = directionToMe.length();
         directionToMe = directionToMe.normalize();
         double dotProduct = viewVector.dot(directionToMe);
         double lookThreshold = 1.0 - tolerance / (scaleWithDistance ? distance : 1.0);
         if (dotProduct > lookThreshold && this.hasLineOfSight(entity, this, checkVisibility ? Block.VISUAL : Block.COLLIDER, Fluid.NONE, heightTarget)) {
            return true;
         }
      }

      return false;
   }

   public boolean hasLineOfSight(LivingEntity stalker, Entity me, Block block, Fluid fluid, double targetHeight) {
      if (me.level() != stalker.level()) {
         return false;
      }

      Vec3 stalkerPosition = new Vec3(stalker.getX(), stalker.getEyeY(), stalker.getZ());
      Vec3 myPosition = new Vec3(me.getX(), targetHeight, me.getZ());
      return myPosition.distanceTo(stalkerPosition) <= 128.0
         && stalker.level().clip(new ClipContext(stalkerPosition, myPosition, block, fluid, stalker)).getType() == Type.MISS;
   }

   public void activate(Player player) {
      this.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, player);
      this.playSound(ModSoundEvents.CREAKING_ACTIVATE.get());
      this.setIsActive(true);
   }

   public void deactivate() {
      this.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
      this.playSound(ModSoundEvents.CREAKING_DEACTIVATE.get());
      this.setIsActive(false);
   }

   public void setIsActive(boolean active) {
      this.entityData.set(IS_ACTIVE, active);
   }

   public boolean isActive() {
      return (Boolean)this.entityData.get(IS_ACTIVE);
   }

   public float getWalkTargetValue(BlockPos pos, LevelReader level) {
      return 0.0F;
   }

   class CreakingBodyRotationControl extends BodyRotationControl {
      public CreakingBodyRotationControl(Mob mob) {
         super(mob);
      }

      public void clientTick() {
         if (Creaking.this.canMove()) {
            super.clientTick();
         }
      }
   }

   class CreakingJumpControl extends JumpControl {
      public CreakingJumpControl(Mob mob) {
         super(mob);
      }

      public void tick() {
         if (Creaking.this.canMove()) {
            super.tick();
         } else {
            Creaking.this.setJumping(false);
         }
      }
   }

   class CreakingLookControl extends LookControl {
      public CreakingLookControl(Mob mob) {
         super(mob);
      }

      public void tick() {
         if (Creaking.this.canMove()) {
            super.tick();
         }
      }
   }

   class CreakingMoveControl extends MoveControl {
      public CreakingMoveControl(Mob mob) {
         super(mob);
      }

      public void tick() {
         if (Creaking.this.canMove()) {
            super.tick();
         }
      }
   }

   class CreakingPathNavigation extends GroundPathNavigation {
      CreakingPathNavigation(final Creaking creaking2, final Level level) {
         super(creaking2, level);
      }

      public void tick() {
         if (Creaking.this.canMove()) {
            super.tick();
         }
      }

      protected PathFinder createPathFinder(int i) {
         this.nodeEvaluator = Creaking.this.new HomeNodeEvaluator();
         this.nodeEvaluator.setCanPassDoors(true);
         return new PathFinder(this.nodeEvaluator, i);
      }
   }

   class HomeNodeEvaluator extends WalkNodeEvaluator {
      public PathType getPathType(PathfindingContext context, int x, int y, int z) {
         BlockPos pos = Creaking.this.getHomePos();
         if (pos == null) {
            return super.getPathType(context, x, y, z);
         }

         double distance = pos.distSqr(new Vec3i(x, y, z));
         return distance > 1024.0 && distance >= pos.distSqr(context.mobPosition()) ? PathType.BLOCKED : super.getPathType(context, x, y, z);
      }
   }
}
