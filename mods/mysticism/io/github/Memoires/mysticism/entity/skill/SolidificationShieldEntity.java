package io.github.Memoires.mysticism.entity.skill;

import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import io.github.manasmods.tensura.entity.magic.barrier.BarrierEntity;
import io.github.manasmods.tensura.entity.magic.barrier.BarrierPart;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SolidificationShieldEntity extends BarrierEntity {
   protected static final EntityDataAccessor<Direction> DIRECTION = SynchedEntityData.defineId(
      SolidificationShieldEntity.class, EntityDataSerializers.DIRECTION
   );

   public SolidificationShieldEntity(EntityType<? extends SolidificationShieldEntity> entityType, Level level, @Nullable LivingEntity entity) {
      super(entityType, level);
      this.setSize(2.0F);
      this.setInvulnerable(true);
      if (entity != null) {
         this.setOwner(entity);
      }
   }

   public SolidificationShieldEntity(EntityType<? extends SolidificationShieldEntity> entityType, Level level) {
      this(entityType, level, null);
   }

   public SolidificationShieldEntity(Level level, LivingEntity entity) {
      this((EntityType<? extends SolidificationShieldEntity>)MysticismSkillEntityTypes.SOLIDIFICATION_SHIELD.get(), level, entity);
   }

   @NotNull
   protected AABB makeBoundingBox() {
      return this.getBoundingBox();
   }

   public Direction getFacingDirection() {
      return (Direction)this.entityData.get(DIRECTION);
   }

   public void setFacingDirection(Direction direction) {
      this.entityData.set(DIRECTION, direction, true);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DIRECTION, Direction.UP);
   }

   protected void addAdditionalSaveData(@NotNull CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("Direction", this.getFacingDirection().get3DDataValue());
   }

   protected void readAdditionalSaveData(@NotNull CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setFacingDirection(Direction.from3DDataValue(compound.getInt("Direction")));
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
      if (DIRECTION.equals(key)) {
         BarrierPart matchingPart = null;
         Direction direction = this.getFacingDirection();

         for (BarrierPart part : this.parts) {
            if (part != null) {
               if (part.getBarrierDirection() != direction) {
                  part.discard();
               } else {
                  matchingPart = part;
               }
            }
         }

         if (matchingPart == null) {
            this.discard();
         } else {
            Vec3 position = this.position();
            matchingPart.setPos(position);
            matchingPart.setBoundingBox(matchingPart.getDirectionalBoundingBox());
            matchingPart.xo = position.x;
            matchingPart.yo = position.y;
            matchingPart.zo = position.z;
            matchingPart.xOld = position.x;
            matchingPart.yOld = position.y;
            matchingPart.zOld = position.z;
            this.parts = new BarrierPart[]{matchingPart};
            this.refreshDimensions();
            this.setBoundingBox(matchingPart.getBoundingBox());
         }
      }
   }

   public void spawnParticle() {
      Level level = this.level();
      Vec3 position = this.position();
      AABB boundingBox = this.getBoundingBox();
      double xSize = boundingBox.getXsize() / 4.0;
      double ySize = boundingBox.getYsize() / 4.0;
      double zSize = boundingBox.getZsize() / 4.0;

      for (int i = 0; i < 5; i++) {
         level.addParticle(
            ParticleTypes.END_ROD,
            position.x + this.random.nextGaussian() * xSize,
            position.y + this.random.nextGaussian() * ySize,
            position.z + this.random.nextGaussian() * zSize,
            0.0,
            0.0,
            0.0
         );
      }
   }

   public boolean shouldCreateParts() {
      return false;
   }

   public boolean canWalkThrough() {
      return false;
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean canWalkThrough(Entity entity) {
      return false;
   }

   public boolean canCollideWith(Entity entity) {
      return true;
   }

   public boolean canHitEntity(Entity pTarget) {
      return false;
   }

   public boolean hurt(DamageSource pSource, float pAmount) {
      return this.isInvulnerableTo(pSource) ? false : !this.level().isClientSide() && !this.isRemoved();
   }
}
