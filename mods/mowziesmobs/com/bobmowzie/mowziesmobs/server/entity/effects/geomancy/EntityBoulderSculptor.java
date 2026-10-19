package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import com.bobmowzie.mowziesmobs.client.model.tools.MathUtils;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.google.common.collect.Iterables;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityBoulderSculptor extends EntityBoulderProjectile {
   private static final float MAX_DIST_HORIZONTAL = 4.0F;
   private static final float MAX_DIST_VERTICAL = 2.4F;
   private static final int MAX_TRIES = 12;
   private EntityBoulderSculptor nextBoulder;
   private EntitySculptor sculptor;
   private EntityPillar pillar;
   protected boolean isMainPath = false;
   protected boolean descending = false;
   protected boolean replacementBoulder = false;
   private boolean spawnedNextBoulders = false;
   private int timeUntilActivation = -1;
   private float orbitSpeed = 0.0F;

   public EntityBoulderSculptor(EntityType<? extends EntityBoulderSculptor> type, Level world) {
      super(type, world);
   }

   public EntityBoulderSculptor(
      EntityType<? extends EntityBoulderSculptor> type,
      Level world,
      LivingEntity caster,
      BlockState blockState,
      BlockPos pos,
      EntityGeomancyBase.GeomancyTier tier
   ) {
      super(type, world, caster, blockState, pos, tier);
   }

   public EntityBoulderSculptor(EntityType<? extends EntityBoulderSculptor> type, EntityBoulderSculptor other) {
      super(type, other.level(), other.getCaster(), other.storedBlock, other.blockPosition(), other.getTier());
   }

   public void descend() {
      this.descending = true;
      if (this.sculptor != null) {
         this.sculptor.boulders.remove(this);
      }
   }

   @Override
   public boolean doRemoveTimer() {
      return false;
   }

   public void delayActivation(int delay) {
      this.timeUntilActivation = delay;
   }

   @Override
   protected boolean shouldExtendBoundsDown() {
      return false;
   }

   @Override
   public void tick() {
      super.tick();
      if ((this.sculptor == null || this.pillar == null || this.sculptor.getHealth() <= 0.0) && this.getCaster() instanceof EntitySculptor) {
         this.sculptor = (EntitySculptor)this.getCaster();
         if (!this.level().isClientSide()) {
            this.sculptor.boulders.add(this);
         }

         this.pillar = this.sculptor.getPillar();
         if (!this.sculptor.isFighting() && !this.sculptor.isTesting()) {
            this.explode();
         }
      }

      if (!this.isTravelling() && this.sculptor != null && this.sculptor.getTarget() != null) {
         if (this.orbitSpeed < 0.02) {
            this.orbitSpeed = (float)(this.orbitSpeed + 0.001);
         }

         Vec3 between = this.position().subtract(this.sculptor.position());
         between = between.yRot(this.orbitSpeed);
         this.setPos(between.add(this.sculptor.position()));
      } else if (this.orbitSpeed > 0.0F) {
         this.orbitSpeed = (float)(this.orbitSpeed - 0.001);
      }

      if (this.level().isClientSide()
         || this.tickCount <= 2
         || this.sculptor != null
            && !this.sculptor.isRemoved()
            && this.pillar != null
            && !this.pillar.isRemoved()
            && (!this.pillar.isFalling() || this.descending)) {
         if (!this.replacementBoulder && this.tickCount >= 2 && !this.spawnedNextBoulders) {
            this.nextBoulders();
         }

         if (!this.replacementBoulder && this.pillar != null && !this.level().isClientSide()) {
            if (this.pillar.getY() + this.pillar.getHeight() >= this.getY() && !this.isActive()) {
               this.activate();
            } else if (!this.pillar.isRising() && !this.isActive() && this.getY() - this.pillar.getY() < this.pillar.tickCount) {
               this.activate();
            }
         }

         if (this.descending) {
            this.move(MoverType.SELF, new Vec3(0.0, -0.25, 0.0));
            if (Iterables.size(this.level().getBlockCollisions(this, this.getBoundingBox().inflate(0.1))) > 0) {
               this.discard();
               return;
            }
         }

         if (!this.level().isClientSide() && this.replacementBoulder) {
            if (this.timeUntilActivation > 0) {
               this.timeUntilActivation--;
            } else if (this.timeUntilActivation == 0) {
               this.activate();
            }
         }
      } else {
         this.explode();
      }
   }

   public void nextBoulders() {
      if (this.getCaster() != null && this.sculptor != null && this.pillar != null) {
         this.spawnedNextBoulders = true;
         if (!this.level().isClientSide()) {
            if (!this.isMainPath && this.random.nextFloat() < MathUtils.fit(this.sculptor.numLivePaths, 3.0, 7.0, 0.0, 0.33)) {
               this.sculptor.numLivePaths--;
            } else {
               int numNextBoulders = 1;
               if (this.random.nextFloat() < MathUtils.fit(this.sculptor.numLivePaths, 1.0, 5.0, 0.27, 0.0)) {
                  numNextBoulders = 2;
               }

               for (int i = 0; i < numNextBoulders; i++) {
                  boolean success = this.nextSingleBoulder();
                  if (success && i > 0) {
                     this.sculptor.numLivePaths++;
                  }
               }
            }
         }
      }
   }

   @Override
   protected boolean startActive() {
      return false;
   }

   public EntityBoulderSculptor getNextBoulderInstance() {
      int whichTierIndex = (int)(Math.pow(this.random.nextFloat(), 2.6) * (EntityGeomancyBase.GeomancyTier.values().length - 2) + 1.0);
      if (this.getHeightFrac() > 0.75 && whichTierIndex == 3) {
         whichTierIndex = 1;
      }

      EntityGeomancyBase.GeomancyTier nextTier = EntityGeomancyBase.GeomancyTier.values()[whichTierIndex];
      return this.getHeightFrac() > 0.45 && this.random.nextFloat() < 0.15
         ? new EntityBoulderSculptor.EntityBoulderSculptorCrumbling(
            (EntityType<? extends EntityBoulderSculptor.EntityBoulderSculptorCrumbling>)EntityHandler.BOULDER_SCULPTOR_CRUMBLING.get(),
            this.level(),
            this.getCaster(),
            this.blockPosition(),
            nextTier,
            this.random.nextInt(2)
         )
         : new EntityBoulderSculptor(
            (EntityType<? extends EntityBoulderSculptor>)EntityHandler.BOULDER_SCULPTOR.get(),
            this.level(),
            this.getCaster(),
            Blocks.STONE.defaultBlockState(),
            this.blockPosition(),
            nextTier
         );
   }

   public boolean nextSingleBoulder() {
      EntityBoulderSculptor nextBoulder = this.getNextBoulderInstance();

      for (int j = 0; j < 12; j++) {
         Vec3 randomPos;
         if (this.getHeightFrac() < 1.0F) {
            randomPos = this.chooseRandomLocation(nextBoulder);
         } else {
            if (!(this.position().multiply(1.0, 0.0, 1.0).distanceTo(this.sculptor.position().multiply(1.0, 0.0, 1.0)) > 4.0)) {
               return false;
            }

            randomPos = this.chooseTowardsSculptorLocation(nextBoulder);
         }

         nextBoulder.setPos(randomPos);
         if (this.level().getEntitiesOfClass(EntityBoulderSculptor.class, nextBoulder.getBoundingBox().inflate(0.7, 1.0, 0.7), b -> b != this).isEmpty()
            && Iterables.size(this.level().getBlockCollisions(nextBoulder, nextBoulder.getBoundingBox())) == 0
            && !this.pillar.getBoundingBox().setMaxY(this.pillar.getY() + EntitySculptor.TEST_HEIGHT).intersects(nextBoulder.getBoundingBox())) {
            AABB toCheck = nextBoulder.getBoundingBox().inflate(4.0, 2.7F, 4.0).move(0.0, -2.7F, 0.0);
            List<EntityBoulderSculptor> platforms = this.level().getEntitiesOfClass(EntityBoulderSculptor.class, toCheck);
            boolean obstructsPath = false;

            for (EntityBoulderSculptor platform : platforms) {
               if (platform != nextBoulder && !nextBoulder.checkJumpPath(platform)) {
                  obstructsPath = true;
                  break;
               }
            }

            if (!obstructsPath) {
               this.level().addFreshEntity(nextBoulder);
               if (this.isMainPath && this.nextBoulder == null) {
                  this.nextBoulder = nextBoulder;
                  this.nextBoulder.setMainPath();
               }

               return true;
            }
         }
      }

      if (this.isMainPath) {
         this.spawnedNextBoulders = false;
      }

      return false;
   }

   protected Vec3 chooseRandomLocation(EntityBoulderSculptor nextBoulder) {
      EntityDimensions thisDims = SIZE_MAP.get(this.getTier());
      EntityDimensions nextDims = SIZE_MAP.get(nextBoulder.getTier());
      Vec3 startLocation = this.position();
      Vec2 fromPillarPos = new Vec2((float)(this.getCaster().getX() - startLocation.x), (float)(this.getCaster().getZ() - startLocation.z));
      float horizontalOffset = Mth.nextFloat(this.random, 2.0F, 4.0F) + thisDims.width() / 2.0F + nextDims.width() / 2.0F;
      float verticalOffset = Mth.nextFloat(this.random, 0.0F, 2.4F) - (nextDims.height() - thisDims.height());
      float baseAngle = (float)(-Math.toDegrees(Math.atan2(fromPillarPos.y, fromPillarPos.x)));
      float minRandomAngle = (float)(Math.min(Math.pow(3.0, -fromPillarPos.length() + 3.0F), 1.0) * 90.0);
      double radius = EntitySculptor.testRadiusAtHeight(startLocation.y + verticalOffset + nextDims.height() - this.pillar.getY());
      float maxRandomAngle = 180.0F - (float)(Math.min(Math.pow(3.0, fromPillarPos.length() - radius), 1.0) * 90.0);
      float randomAngle = Mth.nextFloat(this.random, minRandomAngle, maxRandomAngle);
      if (this.random.nextBoolean()) {
         randomAngle *= -1.0F;
      }

      randomAngle = (float)(randomAngle * (1.0 - Math.pow(this.getHeightFrac(), 5.0) * 0.75));
      Vec3 offset = new Vec3(horizontalOffset, verticalOffset, 0.0);
      float finalAngle = (float)Math.toRadians(Mth.wrapDegrees(baseAngle + randomAngle));
      offset = offset.yRot(finalAngle);
      Vec3 nextLocation = startLocation.add(offset);
      Vec3 atPillarHeight = new Vec3(this.getCaster().getX(), nextLocation.y(), this.getCaster().getZ());
      if (nextLocation.distanceToSqr(atPillarHeight) > radius * radius) {
         Vec3 clampedOffset = nextLocation.subtract(atPillarHeight).normalize().scale(radius);
         nextLocation = atPillarHeight.add(clampedOffset);
      }

      if (nextLocation.y() + nextDims.height() > this.pillar.getY() + EntitySculptor.TEST_HEIGHT) {
         nextLocation = new Vec3(nextLocation.x(), this.pillar.getY() + EntitySculptor.TEST_HEIGHT - nextDims.height(), nextLocation.z());
      }

      return nextLocation;
   }

   protected Vec3 chooseTowardsSculptorLocation(EntityBoulderSculptor nextBoulder) {
      EntityDimensions thisDims = SIZE_MAP.get(this.getTier());
      EntityDimensions nextDims = SIZE_MAP.get(nextBoulder.getTier());
      Vec3 startLocation = this.position();
      Vec2 fromPillarPos = new Vec2((float)(this.getCaster().getX() - startLocation.x), (float)(this.getCaster().getZ() - startLocation.z));
      float horizontalOffset = Mth.nextFloat(this.random, 1.0F, 4.0F) + thisDims.width() / 2.0F + nextDims.width() / 2.0F;
      float baseAngle = (float)(-Math.toDegrees(Math.atan2(fromPillarPos.y, fromPillarPos.x)));
      Vec3 offset = new Vec3(horizontalOffset, 0.0, 0.0);
      float finalAngle = (float)Math.toRadians(Mth.wrapDegrees(baseAngle));
      offset = offset.yRot(finalAngle);
      return startLocation.add(offset);
   }

   public EntityBoulderSculptor getNextBoulder() {
      return this.nextBoulder;
   }

   public boolean checkJumpPath(EntityBoulderSculptor platform) {
      EntityBoulderSculptor next = platform.getNextBoulder();
      if (next == null) {
         return true;
      }

      EntityDimensions platDims = SIZE_MAP.get(platform.getTier());
      EntityDimensions nextDims = SIZE_MAP.get(next.getTier());
      Vec3 toNext = next.position().subtract(platform.position());
      Vec3 startPos = platform.position().add(0.0, platDims.height(), 0.0).add(toNext.multiply(1.0, 0.0, 1.0).normalize().scale(platDims.width() / 2.0F));
      Vec3 endPos = next.position().add(0.0, nextDims.height(), 0.0).add(toNext.multiply(1.0, 0.0, 1.0).normalize().scale(-nextDims.width() / 2.0F));
      double gravity = -((Attribute)Attributes.GRAVITY.value()).getDefaultValue();
      double jumpVelY = 1.0;
      double heightDiff = endPos.y() - startPos.y();
      double totalTime = (-jumpVelY - Math.sqrt(jumpVelY * jumpVelY - 4.0 * gravity * -heightDiff)) / (2.0 * gravity);
      double jumpVelX = (endPos.x() - startPos.x()) / totalTime;
      double jumpVelZ = (endPos.z() - startPos.z()) / totalTime;
      Vec3 jumpVel = new Vec3(jumpVelX, jumpVelY, jumpVelZ);
      AABB thisBounds = SIZE_MAP.get(this.getTier()).makeBoundingBox(this.position());
      int substeps = 5;

      for (int i = 0; i < substeps; i++) {
         double time = totalTime / substeps * i;
         Vec3 jumpPosition = new Vec3(0.0, gravity * time * time, 0.0).add(jumpVel.scale(time)).add(startPos);
         AABB playerBounds = EntityType.PLAYER.getDimensions().makeBoundingBox(jumpPosition).expandTowards(0.0, 0.5, 0.0);
         if (thisBounds.intersects(playerBounds)) {
            return false;
         }
      }

      return true;
   }

   public void setMainPath() {
      this.isMainPath = true;
   }

   public float getHeightFrac() {
      if (this.getCaster() instanceof EntitySculptor) {
         EntitySculptor sculptor = (EntitySculptor)this.getCaster();
         EntityPillar pillar = sculptor.getPillar();
         if (pillar != null) {
            return (float)(this.position().y() + this.getBbHeight() - pillar.getY()) / EntitySculptor.TEST_HEIGHT;
         }
      }

      return -1.0F;
   }

   @Override
   public void remove(RemovalReason p_146834_) {
      super.remove(p_146834_);
      if (this.sculptor != null) {
         this.sculptor.boulders.remove(this);
      }
   }

   @Override
   protected float fallingBlockCountMultiplier() {
      return 0.2F;
   }

   @Override
   protected float getShootRingParticleScale() {
      return super.getShootRingParticleScale() * 4.0F;
   }

   @Override
   public void shoot(Vec3 shootDirection) {
      super.shoot(shootDirection);
      EntityBoulderSculptor boulderSculptor = new EntityBoulderSculptor((EntityType<? extends EntityBoulderSculptor>)EntityHandler.BOULDER_SCULPTOR.get(), this);
      boulderSculptor.setPos(this.position());
      boulderSculptor.replacementBoulder = true;
      boulderSculptor.delayActivation(40);
      this.level().addFreshEntity(boulderSculptor);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("SpawnedNext", this.spawnedNextBoulders);
      compound.putBoolean("Descending", this.descending);
      compound.putBoolean("MainPath", this.isMainPath);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.spawnedNextBoulders = compound.getBoolean("SpawnedNext");
      this.descending = compound.getBoolean("Descending");
      this.isMainPath = compound.getBoolean("MainPath");
   }

   public static class EntityBoulderSculptorCrumbling extends EntityBoulderSculptor {
      public static final int CRUMBLE_DURATION = 35;
      private int crumbleTick = 35;
      private int consecutiveCrumblers;
      private static final EntityDataAccessor<Boolean> CRUMBLING = SynchedEntityData.defineId(
         EntityBoulderSculptor.EntityBoulderSculptorCrumbling.class, EntityDataSerializers.BOOLEAN
      );

      public EntityBoulderSculptorCrumbling(EntityType<? extends EntityBoulderSculptor> type, Level world) {
         super(type, world);
      }

      public EntityBoulderSculptorCrumbling(
         EntityType<? extends EntityBoulderSculptor.EntityBoulderSculptorCrumbling> type,
         Level world,
         LivingEntity caster,
         BlockPos pos,
         EntityGeomancyBase.GeomancyTier tier,
         int consecutiveCrumblers
      ) {
         super(type, world, caster, Blocks.DIRT.defaultBlockState(), pos, tier);
         this.consecutiveCrumblers = consecutiveCrumblers;
      }

      public EntityBoulderSculptorCrumbling(
         EntityType<? extends EntityBoulderSculptor.EntityBoulderSculptorCrumbling> type, EntityBoulderSculptor.EntityBoulderSculptorCrumbling other
      ) {
         super(type, other.level(), other.getCaster(), other.storedBlock, other.blockPosition(), other.getTier());
      }

      @Override
      public EntityBoulderSculptor getNextBoulderInstance() {
         int whichTierIndex = (int)(Math.pow(this.random.nextFloat(), 2.0) * (EntityGeomancyBase.GeomancyTier.values().length - 2) + 1.0);
         if (this.getHeightFrac() > 0.85 && whichTierIndex == 3) {
            whichTierIndex = 1;
         }

         EntityGeomancyBase.GeomancyTier nextTier = EntityGeomancyBase.GeomancyTier.values()[whichTierIndex];
         return this.consecutiveCrumblers > 0
            ? new EntityBoulderSculptor.EntityBoulderSculptorCrumbling(
               (EntityType<? extends EntityBoulderSculptor.EntityBoulderSculptorCrumbling>)EntityHandler.BOULDER_SCULPTOR_CRUMBLING.get(),
               this.level(),
               this.getCaster(),
               this.blockPosition(),
               nextTier,
               this.consecutiveCrumblers - 1
            )
            : new EntityBoulderSculptor(
               (EntityType<? extends EntityBoulderSculptor>)EntityHandler.BOULDER_SCULPTOR.get(),
               this.level(),
               this.getCaster(),
               Blocks.STONE.defaultBlockState(),
               this.blockPosition(),
               nextTier
            );
      }

      @Override
      protected void defineSynchedData(@NotNull Builder builder) {
         super.defineSynchedData(builder);
         builder.define(CRUMBLING, false);
      }

      @Override
      public void tick() {
         super.tick();
         if (!this.level().isClientSide() && !this.isCrumbling()) {
            for (Entity entity : this.level()
               .getEntities(
                  this,
                  this.getBoundingBox()
                     .contract(0.0, this.getBbHeight() - 1.0F, 0.0)
                     .move(new Vec3(0.0, this.getBbHeight() - 0.5, 0.0))
                     .inflate(-0.1, 0.5, -0.1)
               )) {
               if (entity != null
                  && entity.isPickable()
                  && !(entity instanceof EntityBoulderProjectile)
                  && entity.getY() >= this.getY() + 0.2
                  && entity.onGround()) {
                  this.setCrumbling(true);
                  this.playSound((SoundEvent)MMSounds.ENTITY_SCULPTOR_PLATFORM_CRUMBLE.get(), 1.0F, 1.0F);
               }
            }
         }

         if (this.descending) {
            this.setCrumbling(false);
         }

         if (this.isCrumbling() && !this.descending) {
            this.crumbleTick--;
            if (this.crumbleTick == 0) {
               this.crumble();
            }
         }
      }

      private void crumble() {
         this.explode();
         if (!this.level().isClientSide()) {
            EntityBoulderSculptor.EntityBoulderSculptorCrumbling boulderSculptor = new EntityBoulderSculptor.EntityBoulderSculptorCrumbling(
               (EntityType<? extends EntityBoulderSculptor.EntityBoulderSculptorCrumbling>)EntityHandler.BOULDER_SCULPTOR_CRUMBLING.get(), this
            );
            boulderSculptor.setPos(this.position());
            boulderSculptor.replacementBoulder = true;
            boulderSculptor.delayActivation(40);
            this.level().addFreshEntity(boulderSculptor);
         }
      }

      public void setCrumbling(boolean crumbling) {
         this.getEntityData().set(CRUMBLING, crumbling);
      }

      public boolean isCrumbling() {
         return (Boolean)this.getEntityData().get(CRUMBLING);
      }

      public int getCrumbleTick() {
         return this.crumbleTick;
      }

      @Override
      public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
         compound.putInt("ConsecutiveCrumblers", this.consecutiveCrumblers);
      }

      @Override
      public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
         this.consecutiveCrumblers = compound.getInt("ConsecutiveCrumblers");
      }
   }
}
