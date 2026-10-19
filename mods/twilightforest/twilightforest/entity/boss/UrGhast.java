package twilightforest.entity.boss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiManager.Occupancy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.client.renderer.TFWeatherRenderer;
import twilightforest.entity.ai.control.NoClipMoveControl;
import twilightforest.entity.ai.goal.UrGhastAttackGoal;
import twilightforest.entity.ai.goal.UrGhastFlightGoal;
import twilightforest.entity.ai.goal.UrGhastLookGoal;
import twilightforest.entity.monster.CarminiteGhastguard;
import twilightforest.entity.monster.CarminiteGhastling;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFEntities;
import twilightforest.init.TFPOITypes;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStructures;
import twilightforest.util.entities.EntityUtil;

public class UrGhast extends BaseTFBoss {
   private static final Vec3 DYING_DECENT = new Vec3(0.0, -0.03, 0.0);
   public static final int DEATH_ANIMATION_DURATION = 90;
   private static final EntityDataAccessor<Byte> ATTACK_STATUS = SynchedEntityData.defineId(UrGhast.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Byte> ATTACK_TIMER = SynchedEntityData.defineId(UrGhast.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Byte> ATTACK_PREVTIMER = SynchedEntityData.defineId(UrGhast.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING = SynchedEntityData.defineId(UrGhast.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DATA_TANTRUM = SynchedEntityData.defineId(UrGhast.class, EntityDataSerializers.BOOLEAN);
   private final List<BlockPos> trapLocations = new ArrayList<>();
   private int nextTantrumCry;
   private UrGhastAttackGoal attackGoal;
   private int inTrapCounter;
   private float damageUntilNextPhase = 10.0F;

   public UrGhast(EntityType<? extends UrGhast> type, Level level) {
      super(type, level);
      this.noPhysics = true;
      this.setInTantrum(false);
      this.xpReward = 317;
      this.moveControl = new NoClipMoveControl(this);
   }

   public void setInTrap() {
      this.inTrapCounter = 20;
   }

   public static Builder registerAttributes() {
      return CarminiteGhastguard.registerAttributes()
         .add(Attributes.MAX_HEALTH, 250.0)
         .add(Attributes.FOLLOW_RANGE, 128.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ATTACK_STATUS, (byte)0);
      builder.define(ATTACK_TIMER, (byte)0);
      builder.define(ATTACK_PREVTIMER, (byte)0);
      builder.define(DATA_IS_CHARGING, false);
      builder.define(DATA_TANTRUM, false);
   }

   public List<BlockPos> getTrapLocations() {
      return this.trapLocations;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(5, new UrGhastFlightGoal(this));
      this.goalSelector.addGoal(7, new UrGhastLookGoal(this));
      this.goalSelector.addGoal(7, this.attackGoal = new UrGhastAttackGoal(this));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.UR_GHAST_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.UR_GHAST_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.UR_GHAST_DEATH.get();
   }

   public void aiStep() {
      super.aiStep();
      if (this.level().isClientSide()) {
         if (this.getRandom().nextBoolean()) {
            this.level()
               .addParticle(
                  DustParticleOptions.REDSTONE,
                  this.getX() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(),
                  this.getY() + this.getRandom().nextDouble() * this.getBbHeight() - 0.25,
                  this.getZ() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(),
                  0.0,
                  0.0,
                  0.0
               );
         }

         if (this.isInTantrum() && !this.isDeadOrDying()) {
            this.level()
               .addParticle(
                  (ParticleOptions)TFParticleType.BOSS_TEAR.get(),
                  this.getX() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth() * 0.75,
                  this.getY() + this.getRandom().nextDouble() * this.getBbHeight() * 0.5,
                  this.getZ() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth() * 0.75,
                  0.0,
                  0.0,
                  0.0
               );
         }
      }
   }

   private boolean isReflectedFireball(DamageSource source) {
      return source.getDirectEntity() instanceof LargeFireball && source.getEntity() instanceof Player;
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return !this.isReflectedFireball(source) && (source.is(DamageTypes.IN_WALL) || source.is(DamageTypeTags.IS_FIRE) || super.isInvulnerableTo(source));
   }

   public void knockback(double strength, double xRatio, double zRatio) {
   }

   public boolean hurt(DamageSource source, float damage) {
      if (this.isInTantrum()) {
         damage /= 10.0F;
      }

      float oldHealth = this.getHealth();
      boolean hurt = super.hurt(source, damage);
      float lastDamage = oldHealth - this.getHealth();
      if (!this.level().isClientSide() && this.hurtTime == this.hurtDuration && !this.isDeadOrDying() && this.inTrapCounter <= 0) {
         this.damageUntilNextPhase -= lastDamage;
         if (this.damageUntilNextPhase <= 0.0F) {
            this.switchPhase();
         }
      }

      return hurt;
   }

   private void switchPhase() {
      if (this.isInTantrum()) {
         this.setInTantrum(false);
      } else {
         this.startTantrum();
      }

      this.resetDamageUntilNextPhase();
   }

   public void resetDamageUntilNextPhase() {
      this.damageUntilNextPhase = 18.0F;
   }

   private void startTantrum() {
      this.setInTantrum(true);
      if (this.level() instanceof ServerLevel serverLevel) {
         LightningBolt lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverLevel);
         if (lightningbolt != null) {
            BlockPos blockpos = serverLevel.findLightningTargetAround(
               BlockPos.containing(this.position().add(new Vec3(18.0, 0.0, 0.0).yRot((float)Math.toRadians(this.getRandom().nextInt(360)))))
            );
            lightningbolt.moveTo(Vec3.atBottomCenterOf(blockpos));
            lightningbolt.setVisualOnly(true);
            serverLevel.addFreshEntity(lightningbolt);
         }

         this.spawnGhastsAtTraps(serverLevel);
      }
   }

   public void tick() {
      if (this.level().isClientSide() && !this.isDeadOrDying() && this.isInTantrum()) {
         TFWeatherRenderer.urGhastAlive = true;
      }

      super.tick();
   }

   public void spawnGhastsAtTraps(ServerLevel level) {
      List<BlockPos> ghastSpawns = new ArrayList<>(this.trapLocations);
      Collections.shuffle(ghastSpawns);
      int numSpawns = Math.min(2, ghastSpawns.size());

      for (int i = 0; i < numSpawns; i++) {
         BlockPos spawnCoord = ghastSpawns.get(i);
         this.spawnMinionGhastsAt(level, spawnCoord.getX(), spawnCoord.getY(), spawnCoord.getZ());
      }
   }

   private void spawnMinionGhastsAt(ServerLevel level, int x, int y, int z) {
      int tries = 24;
      int spawns = 0;
      int maxSpawns = 6;
      int rangeXZ = 4;
      int rangeY = 8;
      LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
      bolt.setPos(x, y + 4, z);
      bolt.setVisualOnly(true);
      level.addFreshEntity(bolt);

      for (int i = 0; i < tries; i++) {
         CarminiteGhastling minion = (CarminiteGhastling)((EntityType)TFEntities.CARMINITE_GHASTLING.get()).create(level);
         double sx = x + (this.getRandom().nextDouble() - this.getRandom().nextDouble()) * rangeXZ;
         double sy = y + this.getRandom().nextDouble() * rangeY;
         double sz = z + (this.getRandom().nextDouble() - this.getRandom().nextDouble()) * rangeXZ;
         minion.moveTo(sx, sy, sz, level.getRandom().nextFloat() * 360.0F, 0.0F);
         minion.makeBossMinion();
         EventHooks.finalizeMobSpawn(minion, level, level.getCurrentDifficultyAt(minion.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
         if (minion.checkSpawnRules(level, MobSpawnType.MOB_SUMMONED)) {
            level.addFreshEntity(minion);
            minion.spawnAnim();
         }

         if (++spawns >= maxSpawns) {
            break;
         }
      }
   }

   @Override
   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.inTrapCounter > 0) {
         this.inTrapCounter--;
         this.setTarget(null);
      }

      int status = this.getTarget() != null && !this.isInTantrum() ? 1 : 0;
      this.getEntityData().set(ATTACK_STATUS, (byte)status);
      this.getEntityData().set(ATTACK_TIMER, (byte)this.attackGoal.attackTimer);
      this.getEntityData().set(ATTACK_PREVTIMER, (byte)this.attackGoal.prevAttackTimer);

      for (CarminiteGhastling ghast : this.level().getEntitiesOfClass(CarminiteGhastling.class, this.getBoundingBox().inflate(1.0))) {
         ghast.spawnAnim();
         ghast.discard();
         this.heal(2.0F);
      }

      if (this.tickCount % 60 == 0 && !this.getTrapLocations().isEmpty()) {
         this.getTrapLocations().removeIf(pos -> !this.level().getBlockState(pos).is(TFBlocks.GHAST_TRAP) || !this.level().canSeeSky(pos.above()));
      }

      if (this.firstTick || this.tickCount % 100 == 0) {
         List<BlockPos> addedPositions = this.scanForTraps((ServerLevel)this.level());
         addedPositions.removeIf(pos -> this.getTrapLocations().contains(pos));
         if (!addedPositions.isEmpty()) {
            this.getTrapLocations().addAll(addedPositions);
         }
      }

      if (this.isInTantrum()) {
         this.setTarget(null);
         if (--this.nextTantrumCry <= 0) {
            this.playSound((SoundEvent)TFSounds.UR_GHAST_TANTRUM.get(), this.getSoundVolume(), this.getVoicePitch());
            this.ambientSoundTime = -this.getAmbientSoundInterval();
            this.nextTantrumCry = 20 + this.getRandom().nextInt(30);
         }

         if (this.tickCount % 10 == 0) {
            this.doTantrumDamageEffects();
         }
      }
   }

   public int getAttackStatus() {
      return (Byte)this.getEntityData().get(ATTACK_STATUS);
   }

   public int getAttackTimer() {
      return (Byte)this.getEntityData().get(ATTACK_TIMER);
   }

   public int getPrevAttackTimer() {
      return (Byte)this.getEntityData().get(ATTACK_PREVTIMER);
   }

   public boolean isCharging() {
      return (Boolean)this.getEntityData().get(DATA_IS_CHARGING);
   }

   public void setCharging(boolean charging) {
      this.getEntityData().set(DATA_IS_CHARGING, charging);
   }

   public BlockPos getLogicalScanPoint() {
      return !this.isRestrictionPointValid(this.level().dimension()) ? this.blockPosition() : this.getRestrictionPoint().pos();
   }

   private List<BlockPos> scanForTraps(ServerLevel level) {
      PoiManager poimanager = level.getPoiManager();
      Stream<PoiRecord> stream = poimanager.getInRange(
         type -> type.is(TFPOITypes.GHAST_TRAP.getKey()), this.getLogicalScanPoint(), this.getHomeRadius(), Occupancy.ANY
      );
      return stream.<BlockPos>map(PoiRecord::getPos)
         .filter(trapPos -> level.canSeeSky(trapPos.above()))
         .sorted(Comparator.comparingDouble(trapPos -> trapPos.distSqr(this.getLogicalScanPoint())))
         .collect(Collectors.toList());
   }

   private void doTantrumDamageEffects() {
      AABB below = this.getBoundingBox().move(0.0, -16.0, 0.0).inflate(0.0, 16.0, 0.0);

      for (Player player : this.level().getEntitiesOfClass(Player.class, below)) {
         if (this.level().canSeeSkyFromBelowWater(player.blockPosition())) {
            player.hurt(TFDamageTypes.getEntityDamageSource(this.level(), TFDamageTypes.GHAST_TEAR, this, (EntityType)TFEntities.UR_GHAST.get()), 3.0F);
         }
      }

      for (CarminiteGhastling ghast : this.level().getEntitiesOfClass(CarminiteGhastling.class, below)) {
         ghast.push(0.0, 1.0, 0.0);
      }
   }

   public boolean checkGhastsAtTraps() {
      int trapsWithEnoughGhasts = 0;

      for (BlockPos trap : this.getTrapLocations()) {
         AABB aabb = new AABB(trap.getCenter(), trap.offset(1, 1, 1).getCenter()).inflate(8.0, 16.0, 8.0);
         List<CarminiteGhastling> nearbyGhasts = this.level().getEntitiesOfClass(CarminiteGhastling.class, aabb);
         if (nearbyGhasts.size() >= 4) {
            trapsWithEnoughGhasts++;
         }
      }

      return trapsWithEnoughGhasts >= 1;
   }

   public boolean isOnFire() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   public boolean isInTantrum() {
      return (Boolean)this.getEntityData().get(DATA_TANTRUM);
   }

   public void setInTantrum(boolean inTantrum) {
      this.getEntityData().set(DATA_TANTRUM, inTantrum);
      this.resetDamageUntilNextPhase();
   }

   protected float getSoundVolume() {
      return 16.0F;
   }

   public float getVoicePitch() {
      return (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 0.5F;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("inTantrum", this.isInTantrum());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setInTantrum(compound.getBoolean("inTantrum"));
   }

   @Override
   public void die(DamageSource cause) {
      super.die(cause);
      if (this.level() instanceof ServerLevel serverLevel) {
         LightningBolt lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverLevel);
         if (lightningbolt != null) {
            lightningbolt.moveTo(this.position().add(0.0, this.getBbHeight() * 0.5F, 0.0));
            lightningbolt.setVisualOnly(true);
            serverLevel.addFreshEntity(lightningbolt);
         }
      }
   }

   public Vec3 getDeltaMovement() {
      return this.isDeadOrDying() ? DYING_DECENT : super.getDeltaMovement();
   }

   public int getMaxHeadXRot() {
      return 500;
   }

   protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
   }

   public void travel(Vec3 vec3) {
      if (this.isControlledByLocalInstance()) {
         if (this.isInWater()) {
            this.moveRelative(0.02F, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
         } else if (this.isInLava()) {
            this.moveRelative(0.02F, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
         } else {
            BlockPos ground = this.getBlockPosBelowThatAffectsMyMovement();
            float f = 0.91F;
            if (this.onGround()) {
               f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
            }

            float f1 = 0.16277137F / (f * f * f);
            f = 0.91F;
            if (this.onGround()) {
               f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
            }

            this.moveRelative(this.onGround() ? 0.1F * f1 : 0.02F, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(f));
         }
      }

      this.calculateEntityAnimation(false);
   }

   public boolean onClimbable() {
      return false;
   }

   @Override
   public int getHomeRadius() {
      return 64;
   }

   @Override
   public ResourceKey<Structure> getHomeStructure() {
      return TFStructures.DARK_TOWER;
   }

   @Override
   public Block getDeathContainer(RandomSource random) {
      return (Block)TFBlocks.DARK_CHEST.get();
   }

   @Override
   public Block getBossSpawner() {
      return (Block)TFBlocks.UR_GHAST_BOSS_SPAWNER.get();
   }

   @Override
   public boolean isDeathAnimationFinished() {
      return this.deathTime >= 90;
   }

   @Override
   public void tickDeathAnimation() {
      int third = 30;
      if (this.deathTime <= third) {
         float bbWidth = this.getBbWidth();
         float bbHeight = this.getBbHeight();

         for (int k = 0; k < 12; k++) {
            double d = this.random.nextGaussian() * 0.02;
            double d1 = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            this.level()
               .addParticle(
                  (ParticleOptions)(this.random.nextBoolean()
                     ? (this.random.nextBoolean() ? ParticleTypes.POOF : ParticleTypes.EXPLOSION)
                     : DustParticleOptions.REDSTONE),
                  this.getX() + this.random.nextFloat() * bbWidth * 1.8F - bbWidth,
                  this.getY() + this.random.nextFloat() * bbHeight,
                  this.getZ() + this.random.nextFloat() * bbWidth * 1.8F - bbWidth,
                  d,
                  d1,
                  d2
               );
         }
      } else {
         Vec3 start = this.position().add(0.0, this.getBbHeight() * 0.5F, 0.0);
         Vec3 end = Vec3.atCenterOf(EntityUtil.bossChestLocation(this));
         Vec3 diff = end.subtract(start);
         int deathTime2 = this.deathTime - third + 1;
         double factor = (double)deathTime2 / (third * 2);
         Vec3 particlePos = start.add(diff.scale(factor))
            .add(Math.sin(deathTime2 * Math.PI * 0.1), Math.sin(deathTime2 * Math.PI * 0.05), Math.cos(deathTime2 * Math.PI * 0.1125));

         for (int i = 0; i < 40; i++) {
            double x = (this.random.nextDouble() - 0.5) * 0.05 * i;
            double y = (this.random.nextDouble() - 0.5) * 0.05 * i;
            double z = (this.random.nextDouble() - 0.5) * 0.05 * i;
            this.level().addParticle(DustParticleOptions.REDSTONE, false, particlePos.x() + x, particlePos.y() + y, particlePos.z() + z, 0.0, 0.0, 0.0);
         }
      }
   }

   public void makePoofParticles() {
   }

   @Override
   public int getBossBarColor() {
      return 16711680;
   }
}
