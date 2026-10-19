package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.block.EnergyBlock;
import cn.leolezury.eternalstarlight.common.block.WeatheringGolemSteel;
import cn.leolezury.eternalstarlight.common.block.entity.LootChestBlockEntity;
import cn.leolezury.eternalstarlight.common.block.spawner.StarlightGolemSpawnerBlock;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESCrests;
import cn.leolezury.eternalstarlight.common.entity.attack.EnergizedFlame;
import cn.leolezury.eternalstarlight.common.entity.interfaces.RayAttackUser;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESServerBossEvent;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.GatheringTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESPoiTypes;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESBlockUtil;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.util.ModelSnapshot;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiManager.Occupancy;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class StarlightGolem extends ESBoss implements RayAttackUser {
   private final ESServerBossEvent bossEvent = new ESServerBossEvent(this, this.getUUID(), BossBarColor.BLUE, false);
   private final BehaviorManager<StarlightGolem> behaviorManager = new BehaviorManager(
      this,
      List.of(
         new StarlightGolemLaserBeamPhase(),
         new StarlightGolemSummonFlamePhase(),
         new StarlightGolemSmashPhase(),
         new StarlightGolemChargeStartPhase(),
         new StarlightGolemChargePhase(),
         new StarlightGolemChargeEndPhase()
      )
   );
   public AnimationState laserBeamAnimationState = new AnimationState();
   public AnimationState summonFlameAnimationState = new AnimationState();
   public AnimationState smashAnimationState = new AnimationState();
   public AnimationState chargeStartAnimationState = new AnimationState();
   public AnimationState chargeAnimationState = new AnimationState();
   public AnimationState chargeEndAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   public int oldDeathAnimationTime;
   public int deathAnimationTime;
   private final Vec3[] deathParticlePos = new Vec3[4];
   public final List<Pair<Vec3, ModelSnapshot>> trailSnapshots = new ArrayList<>(50);
   public float lastTrailTick = 0.0F;
   private int attackEnergy;
   private int lastHurtCount;
   private int chargeHurtCount;
   private float chargeHurtAmount;
   private int lastHurtSound;
   private boolean hasProtection;

   public StarlightGolem(EntityType<? extends StarlightGolem> entityType, Level level) {
      super(entityType, level);
      this.noCulling = true;
   }

   public boolean shouldAddTrailSnapshot() {
      return Mth.degreesDifferenceAbs(this.getYRot(), this.yBodyRot) < 45.0F
         && Mth.degreesDifferenceAbs(this.getYRot(), this.yBodyRotO) < 45.0F
         && Mth.degreesDifferenceAbs(this.yBodyRot, this.yBodyRotO) < 45.0F
         && this.getPhase() == 1
         && this.getBehaviorState() == 3
         && this.getBehaviorTicks() >= 37;
   }

   public void clearChargeHurtCountAndAmount() {
      this.chargeHurtCount = 0;
      this.chargeHurtAmount = 0.0F;
   }

   public int getChargeHurtCount() {
      return this.chargeHurtCount;
   }

   public float getChargeHurtAmount() {
      return this.chargeHurtAmount;
   }

   public int getAttackEnergy() {
      return this.attackEnergy;
   }

   public void setAttackEnergy(int energy) {
      this.attackEnergy = energy;
   }

   public boolean hasProtection() {
      return this.hasProtection;
   }

   public BehaviorManager<StarlightGolem> getBehaviorManager() {
      return this.behaviorManager;
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.bossEvent.setId(this.getUUID());
   }

   public void startSeenByPlayer(ServerPlayer serverPlayer) {
      super.startSeenByPlayer(serverPlayer);
      this.bossEvent.addPlayer(serverPlayer);
   }

   public void stopSeenByPlayer(ServerPlayer serverPlayer) {
      super.stopSeenByPlayer(serverPlayer);
      this.bossEvent.removePlayer(serverPlayer);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new StarlightGolem.GolemLookAtTargetGoal());
      this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[]{StarlightGolem.class}).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, AbstractVillager.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
   }

   @Override
   public boolean isRayFollowingHeadRotation() {
      return false;
   }

   @Override
   public Vec3 getRayRotationTarget() {
      return this.getTarget() == null
         ? this.position()
            .add(
               this.getBbWidth() * (this.getRandom().nextFloat() - 0.5F),
               this.getBbHeight() * this.getRandom().nextFloat(),
               this.getBbWidth() * (this.getRandom().nextFloat() - 0.5F)
            )
         : this.getTarget().position().add(0.0, this.getTarget().getBbHeight() / 2.0F, 0.0);
   }

   @Override
   public void updateRayEnd(Vec3 endPos) {
      ESEntityUtil.instantLook(this, endPos);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.starlightGolem.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.starlightGolem.armor())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.starlightGolem.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.0)
         .add(Attributes.ATTACK_DAMAGE, 0.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return super.isInvulnerableTo(source) || source.getEntity() == this || source.is(DamageTypes.FALLING_BLOCK);
   }

   @Override
   public boolean hurt(DamageSource source, float amount) {
      if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         if (this.hasProtection()) {
            if (source.getDirectEntity() instanceof LivingEntity && this.tickCount - this.lastHurtSound > 20) {
               this.playSound(ESSoundEvents.STARLIGHT_GOLEM_BLOCK.get(), this.getSoundVolume(), this.getVoicePitch());
               this.lastHurtSound = this.tickCount;
            }

            return false;
         }

         if (source.getEntity() != null && source.getEntity() != this) {
            if (this.tickCount - this.lastHurtCount > 10) {
               this.chargeHurtCount++;
               this.lastHurtCount = this.tickCount;
            }

            this.chargeHurtAmount += amount;
         }
      }

      boolean success = super.hurt(source, amount);
      if (this.getPhase() == 0 && this.getHealth() / this.getMaxHealth() < 0.2) {
         this.setPhase(1);
         ESBlockUtil.getBlocksInBoundingBox(this.getBoundingBox().inflate(2.0, 0.0, 2.0))
            .forEach(
               pos -> {
                  if (this.level().getBlockState(pos).getBlock() instanceof WeatheringGolemSteel
                     && ESPlatform.INSTANCE.postEntityDestroyBlockEvent(this.level(), pos, this)) {
                     this.level().destroyBlock(pos, true);
                  }
               }
            );
      }

      return success;
   }

   protected void tickDeath() {
      if (this.deathAnimationTime == 0) {
         this.stopAllAnimStates();
         this.deathAnimationState.start(this.tickCount);
         this.setBehaviorState(0);

         for (int i = 0; i < 4; i++) {
            this.deathParticlePos[i] = this.position().add(0.0, this.getBbHeight() / 4.0F, 0.0);
         }
      }

      Optional<BlockPos> chestPos = this.getLootChestPos();
      if (chestPos.isPresent()) {
         for (int i = 0; i < 4; i++) {
            if (this.deathAnimationTime < 90) {
               this.deathParticlePos[i] = ESMathUtil.lerpVec(
                  1.0F / (90.0F - this.deathAnimationTime),
                  this.deathParticlePos[i],
                  ESMathUtil.rotationToPosition(chestPos.get().getCenter(), 5.0F, 0.0F, 30 + i * 90)
               );
            } else {
               float currentYaw = ESMathUtil.positionToYaw(chestPos.get().getCenter(), this.deathParticlePos[i]);
               this.deathParticlePos[i] = ESMathUtil.rotationToPosition(
                  chestPos.get().getCenter(), (110 - this.deathAnimationTime) / 20.0F * 5.0F, 0.0F, currentYaw + 2.0F
               );
            }

            if (this.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(
                  (SimpleParticleType)ESParticles.ENERGY.get(),
                  this.deathParticlePos[i].x(),
                  this.deathParticlePos[i].y(),
                  this.deathParticlePos[i].z(),
                  3,
                  0.1,
                  0.1,
                  0.1,
                  0.0
               );
            }
         }
      }

      this.oldDeathAnimationTime = this.deathAnimationTime++;
      if (this.deathAnimationTime == 110 && !this.level().isClientSide()) {
         this.level().broadcastEntityEvent(this, (byte)60);
         this.playSound((SoundEvent)SoundEvents.GENERIC_EXPLODE.value());
         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
               ESExplosionParticleOptions.ENERGY,
               this.getX(),
               this.getY() + this.getBbHeight() / 2.0F,
               this.getZ(),
               20,
               this.getBbWidth() / 2.0F,
               this.getBbHeight() / 2.0F,
               this.getBbWidth() / 2.0F,
               0.0
            );
            serverLevel.sendParticles(
               ESExplosionParticleOptions.ENERGY_BLAST,
               this.getX(),
               this.getY() + this.getBbHeight() / 2.0F,
               this.getZ(),
               5,
               this.getBbWidth() / 2.0F,
               this.getBbHeight() / 2.0F,
               this.getBbWidth() / 2.0F,
               0.0
            );

            for (int i = 0; i < 25; i++) {
               Vec3 speed = new Vec3(
                     (this.random.nextFloat() - this.random.nextFloat()) * 0.1F,
                     this.random.nextFloat() * 0.05F,
                     (this.random.nextFloat() - this.random.nextFloat()) * 0.1F
                  )
                  .normalize();
               ESPlatform.INSTANCE
                  .sendToAllClients(
                     serverLevel,
                     new ParticlePacket(
                        ExplosionShockParticleOptions.ENERGY,
                        this.position().x + speed.x * 1.2,
                        this.position().y + speed.y * 1.2,
                        this.position().z + speed.z * 1.2,
                        speed.x,
                        speed.y,
                        speed.z
                     )
                  );
            }

            ScreenShakeVfx.createInstance(this.level().dimension(), this.position(), 40.0F, 50, 0.5F, 0.5F, 3.0F, 5.5F).send(serverLevel);
         }

         this.remove(RemovalReason.KILLED);
      }
   }

   public void stopAllAnimStates() {
      this.laserBeamAnimationState.stop();
      this.summonFlameAnimationState.stop();
      this.smashAnimationState.stop();
      this.chargeStartAnimationState.stop();
      this.chargeAnimationState.stop();
      this.chargeEndAnimationState.stop();
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
      if (accessor.equals(BEHAVIOR_STATE)) {
         this.stopAllAnimStates();
         switch (this.getBehaviorState()) {
            case 1:
               this.laserBeamAnimationState.start(this.tickCount);
               break;
            case 2:
               this.summonFlameAnimationState.start(this.tickCount);
               break;
            case 3:
               this.smashAnimationState.start(this.tickCount);
               break;
            case 4:
               this.chargeStartAnimationState.start(this.tickCount);
               break;
            case 5:
               this.chargeAnimationState.start(this.tickCount);
               break;
            case 6:
               this.chargeEndAnimationState.start(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(accessor);
   }

   public boolean isPushable() {
      return false;
   }

   public boolean isAlliedTo(Entity entity) {
      return super.isAlliedTo(entity) || entity.getType().is(ESTags.EntityTypes.STARLIGHT_GOLEM_ALLIES);
   }

   public boolean canStandOnFluid(FluidState fluidState) {
      return super.canStandOnFluid(fluidState) || fluidState.is(FluidTags.LAVA);
   }

   @Override
   public boolean canBossMove() {
      return this.getPhase() != 0;
   }

   private List<BlockPos> getNearbyEnergyBlocks(boolean lit) {
      if (this.level() instanceof ServerLevel serverLevel) {
         PoiManager poiManager = serverLevel.getPoiManager();
         return poiManager.findAllClosestFirstWithType(poi -> poi.is(ESPoiTypes.ENERGY_BLOCK.getResourceKey()), pos -> {
            BlockState state = serverLevel.getBlockState(pos);
            return !lit || state.hasProperty(EnergyBlock.LIT) && (Boolean)state.getValue(EnergyBlock.LIT);
         }, this.blockPosition(), 48, Occupancy.ANY).limit(5L).<BlockPos>map(Pair::getSecond).toList();
      } else {
         return List.of();
      }
   }

   public void turnOnEnergyBlocks() {
      for (BlockPos pos : this.getNearbyEnergyBlocks(false)) {
         BlockState state = this.level().getBlockState(pos);
         if (state.is(ESBlocks.ENERGY_BLOCK.get()) && !(Boolean)state.getValue(BlockStateProperties.LIT)) {
            this.level().setBlockAndUpdate(pos, (BlockState)state.setValue(BlockStateProperties.LIT, true));
            if (this.level() instanceof ServerLevel serverLevel) {
               Vec3 center = pos.getCenter();
               ESPlatform.INSTANCE
                  .sendToAllClients(serverLevel, new ParticlePacket(RingExplosionParticleOptions.ENERGY, center.x(), center.y(), center.z(), 0.0, 0.2, 0.0));
            }
         }
      }
   }

   public void spawnEnergizedFlame(int maxNum, int scanRadius, boolean trackTarget) {
      int left = maxNum;
      LivingEntity target = this.getTarget();
      if (trackTarget && target != null) {
         EnergizedFlame energizedFlame = (EnergizedFlame)ESEntities.ENERGIZED_FLAME.get().create(this.level());
         if (energizedFlame != null) {
            energizedFlame.setPos(target.position().add(ESDataAttachments.MOVEMENT.getData(target).scale(20.0)));
            energizedFlame.setOwner(this);
            this.level().addFreshEntity(energizedFlame);
            left--;
         }
      }

      List<BlockPos> possiblePositions = new ArrayList<>();

      for (int x = -scanRadius; x <= scanRadius; x++) {
         for (int z = -scanRadius; z <= scanRadius; z++) {
            for (int y = -5; y <= 5; y++) {
               BlockPos firePos = this.blockPosition().offset(x, y, z);
               if (this.level().isEmptyBlock(firePos) && this.level().getBlockState(firePos.below()).isFaceSturdy(this.level(), firePos.below(), Direction.UP)) {
                  possiblePositions.add(firePos);
               }
            }
         }
      }

      for (int i = 0; i < left; i++) {
         if (!possiblePositions.isEmpty()) {
            BlockPos firePos = possiblePositions.get(this.getRandom().nextInt(possiblePositions.size()));
            EnergizedFlame energizedFlame = (EnergizedFlame)ESEntities.ENERGIZED_FLAME.get().create(this.level());
            energizedFlame.setPos(firePos.getBottomCenter());
            energizedFlame.setOwner(this);
            this.level().addFreshEntity(energizedFlame);
            possiblePositions.remove(firePos);
         }
      }
   }

   public void aiStep() {
      super.aiStep();
      this.bossEvent.update();
      if (!this.level().isClientSide) {
         if (this.getTarget() != null && !this.getTarget().isAlive()) {
            this.setTarget(null);
         }

         if (!this.isNoAi() && this.isAlive()) {
            this.behaviorManager.tick();
         }

         List<BlockPos> nearbyEnergyBlocks = this.getNearbyEnergyBlocks(true);
         if (this.level() instanceof ServerLevel serverLevel && this.tickCount % 10 == 0 && this.getBehaviorState() == 5 && !nearbyEnergyBlocks.isEmpty()) {
            for (BlockPos pos : nearbyEnergyBlocks) {
               Vec3 energySource = pos.getCenter();
               Vec3 energyMotion = this.position().add(0.0, this.getBbHeight() / 4.0F, 0.0).subtract(energySource);
               ESPlatform.INSTANCE
                  .sendToTrackingClients(
                     serverLevel,
                     this,
                     new ParticlePacket(
                        GatheringTrailParticleOptions.ENERGY, energySource.x, energySource.y, energySource.z, energyMotion.x, energyMotion.y, energyMotion.z
                     )
                  );
            }
         }

         if (this.getBehaviorState() == 5) {
            this.hasProtection = !nearbyEnergyBlocks.isEmpty();
         } else {
            this.hasProtection = true;
         }

         if (this.getPhase() == 1) {
            this.hasProtection = false;
            if (this.tickCount % 60 == 0) {
               this.hurt(this.damageSources().generic(), 0.0F);
               this.invulnerableTime = 0;
            }

            for (BlockPos pos : ESBlockUtil.getBlocksInBoundingBox(this.getBoundingBox().inflate(3.0))) {
               if (pos.distToCenterSqr(this.position()) < this.getBbWidth() * this.getBbWidth() * 2.0F
                  && this.level().getBlockState(pos).is(Blocks.LAVA)
                  && ESPlatform.INSTANCE.postEntityDestroyBlockEvent(this.level(), pos, this)) {
                  this.level().setBlockAndUpdate(pos, Blocks.MAGMA_BLOCK.defaultBlockState());
                  if (this.level() instanceof ServerLevel serverLevel) {
                     serverLevel.sendParticles(
                        ESExplosionParticleOptions.LAVA, pos.getCenter().x, pos.getCenter().y + 0.6, pos.getCenter().z, 1, 0.0, 0.0, 0.0, 0.0
                     );
                  }
               }
            }
         }
      } else {
         if (this.getRandom().nextInt(Mth.clamp(Math.round(this.getHealth() / this.getMaxHealth() * 30.0F), 1, 30)) == 0) {
            Vec3 smokePos = this.position()
               .add(
                  this.getBbWidth() * (this.getRandom().nextFloat() - 0.5F),
                  this.getBbHeight() * this.getRandom().nextFloat(),
                  this.getBbWidth() * (this.getRandom().nextFloat() - 0.5F)
               );
            this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, smokePos.x, smokePos.y, smokePos.z, 0.0, this.getRandom().nextFloat() * 0.15, 0.0);
         }

         if (this.getRandom().nextInt(3) == 0 && this.getPhase() == 1) {
            Vec3 sparkPos = this.position()
               .add(
                  this.getBbWidth() * (this.getRandom().nextFloat() - 0.5F),
                  this.getBbHeight() * this.getRandom().nextFloat(),
                  this.getBbWidth() * (this.getRandom().nextFloat() - 0.5F)
               );

            for (int i = 0; i < 25; i++) {
               Vec3 speed = new Vec3(
                     (this.random.nextFloat() - this.random.nextFloat()) * 0.1F,
                     this.random.nextFloat() * 0.05F,
                     (this.random.nextFloat() - this.random.nextFloat()) * 0.1F
                  )
                  .normalize();
               this.level().addParticle(ExplosionShockParticleOptions.ENERGY_SMALL, sparkPos.x, sparkPos.y, sparkPos.z, speed.x, speed.y, speed.z);
            }
         }
      }
   }

   @Override
   protected BlockState getBossSpawner() {
      return ((StarlightGolemSpawnerBlock)ESBlocks.STARLIGHT_GOLEM_SPAWNER.get()).defaultBlockState();
   }

   @Override
   protected void grantSpecialLoot(ServerPlayer player) {
      ESCrestUtil.upgradeCrest(player, ESCrests.BLAZING_BEAM);
   }

   @Override
   protected void modifyBossLootChest(LootChestBlockEntity blockEntity) {
      blockEntity.setColor(7763315);
      blockEntity.setOutlineColor(65524);
      blockEntity.setFlashColor(13172724);
      blockEntity.setRareFlashColor(65524);
   }

   @Override
   public SoundEvent getBossMusic() {
      return ESSoundEvents.MUSIC_BOSS_STARLIGHT_GOLEM.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return ESSoundEvents.STARLIGHT_GOLEM_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return ESSoundEvents.STARLIGHT_GOLEM_DEATH.get();
   }

   private class GolemLookAtTargetGoal extends LookAtTargetGoal {
      public GolemLookAtTargetGoal() {
         super(StarlightGolem.this);
      }

      @Override
      public void tick() {
         boolean affectsLook = StarlightGolem.this.getBehaviorState() == 1
            || StarlightGolem.this.getBehaviorState() == 3 && StarlightGolem.this.getBehaviorTicks() > 30;
         if (!affectsLook) {
            super.tick();
         }
      }
   }
}
