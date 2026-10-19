package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan;

import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class Abyss_Portal_Entity extends Entity {
   protected static final EntityDataAccessor<Integer> LIFESPAN = SynchedEntityData.defineId(Abyss_Portal_Entity.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Boolean> ENTRANCE = SynchedEntityData.defineId(Abyss_Portal_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Optional<BlockPos>> DESTINATION = SynchedEntityData.defineId(
      Abyss_Portal_Entity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS
   );
   private static final EntityDataAccessor<Optional<UUID>> SISTER_UUID = SynchedEntityData.defineId(
      Abyss_Portal_Entity.class, EntityDataSerializers.OPTIONAL_UUID
   );
   private boolean madeOpenNoise = false;
   private boolean madeCloseNoise = false;
   private boolean isDummy = false;
   private boolean hasClearedObstructions;

   public Abyss_Portal_Entity(EntityType<?> entityTypeIn, Level worldIn) {
      super(entityTypeIn, worldIn);
   }

   public void tick() {
      super.tick();
      if (this.tickCount == 1 && this.getLifespan() == 0) {
         this.setLifespan(2000);
      }

      if (!this.madeOpenNoise) {
         this.gameEvent(GameEvent.ENTITY_PLACE);
         this.playSound(SoundEvents.END_PORTAL_SPAWN, 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
         this.madeOpenNoise = true;
      }

      if (this.random.nextFloat() < 0.5F && this.level().isClientSide && Math.min(this.tickCount, this.getLifespan()) >= 20) {
         double particleX = this.getBoundingBox().minX + this.random.nextFloat() * (this.getBoundingBox().maxX - this.getBoundingBox().minX);
         double particleY = this.getBoundingBox().minY + this.random.nextFloat() * (this.getBoundingBox().maxY - this.getBoundingBox().minY);
         double particleZ = this.getBoundingBox().minZ + this.random.nextFloat() * (this.getBoundingBox().maxZ - this.getBoundingBox().minZ);
         this.level()
            .addParticle(
               ParticleTypes.PORTAL,
               particleX,
               particleY,
               particleZ,
               0.1 * this.random.nextGaussian(),
               0.1 * this.random.nextGaussian(),
               0.1 * this.random.nextGaussian()
            );
      }

      List<Entity> entities = new ArrayList<>();
      entities.addAll(this.level().getEntities(this, this.getBoundingBox().deflate(0.2F)));
      entities.addAll(this.level().getEntitiesOfClass(The_Leviathan_Entity.class, this.getBoundingBox().inflate(3.0)));
      if (!this.level().isClientSide && this.getDestination() != null && this.getLifespan() > 20 && this.tickCount > 20 && this.getEntrance()) {
         for (Entity e : entities) {
            if (!e.isOnPortalCooldown() && !e.isShiftKeyDown() && !(e instanceof Abyss_Portal_Entity)) {
               if (e instanceof The_Leviathan_Entity) {
                  ((The_Leviathan_Entity)e).teleportTo(Vec3.atCenterOf(this.getDestination()));
                  e.setPortalCooldown();
                  ((The_Leviathan_Entity)e).resetPortalLogic();
               } else {
                  e.teleportTo(this.getDestination().getX() + 0.5F, this.getDestination().getY() + 0.5F, this.getDestination().getZ() + 0.5F);
                  e.setPortalCooldown();
               }
            }
         }
      }

      this.setLifespan(this.getLifespan() - 1);
      if (this.getLifespan() <= 20 && !this.madeCloseNoise) {
         this.gameEvent(GameEvent.ENTITY_PLACE);
         this.madeCloseNoise = true;
      }

      if (this.getLifespan() <= 0) {
         this.remove(RemovalReason.DISCARDED);
      }
   }

   public int getLifespan() {
      return (Integer)this.entityData.get(LIFESPAN);
   }

   public void setLifespan(int i) {
      this.entityData.set(LIFESPAN, i);
   }

   public boolean getEntrance() {
      return (Boolean)this.entityData.get(ENTRANCE);
   }

   public void setEntrance(boolean entrance) {
      this.entityData.set(ENTRANCE, entrance);
   }

   public void setDestination(@Nullable BlockPos destination) {
      this.entityData.set(DESTINATION, Optional.ofNullable(destination));
      if (this.getSisterId() == null) {
         this.createAndSetSister(this.level(), null);
      }
   }

   @Nullable
   public BlockPos getDestination() {
      return (BlockPos)((Optional)this.getEntityData().get(DESTINATION)).orElse(null);
   }

   public void createAndSetSister(Level world, Direction dir) {
      Abyss_Portal_Entity portal = (Abyss_Portal_Entity)((EntityType)ModEntities.ABYSS_PORTAL.get()).create(world);
      BlockPos safeDestination = this.getDestination();
      portal.teleportTo(safeDestination.getX() + 0.5F, safeDestination.getY() + 0.5F, safeDestination.getZ() + 0.5F);
      portal.link(this);
      portal.setEntrance(false);
      world.addFreshEntity(portal);
   }

   public void setDestination(BlockPos destination, Direction dir) {
      this.entityData.set(DESTINATION, Optional.ofNullable(destination));
      if (this.getSisterId() == null) {
         this.createAndSetSister(this.level(), dir);
      }
   }

   public void link(Abyss_Portal_Entity portal) {
      this.setSisterId(portal.getUUID());
      portal.setSisterId(this.getUUID());
      portal.setLifespan(this.getLifespan());
      this.setDestination(portal.blockPosition());
      portal.setDestination(this.blockPosition());
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(LIFESPAN, 300);
      p_326229_.define(SISTER_UUID, Optional.empty());
      p_326229_.define(DESTINATION, Optional.empty());
      p_326229_.define(ENTRANCE, true);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.setLifespan(compound.getInt("Lifespan"));
      NbtUtils.readBlockPos(compound, "Destination").ifPresent(this::setDestination);
      if (compound.hasUUID("SisterUUID")) {
         this.setSisterId(compound.getUUID("SisterUUID"));
      }
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("Lifespan", this.getLifespan());
      if (this.getDestination() != null) {
         compound.put("Destination", NbtUtils.writeBlockPos(this.getDestination()));
      }

      if (this.getSisterId() != null) {
         compound.putUUID("SisterUUID", this.getSisterId());
      }
   }

   public Entity getSister() {
      UUID id = this.getSisterId();
      return id != null && !this.level().isClientSide ? ((ServerLevel)this.level()).getEntity(id) : null;
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      return distance < 1024.0;
   }

   @Nullable
   public UUID getSisterId() {
      return (UUID)((Optional)this.entityData.get(SISTER_UUID)).orElse(null);
   }

   public void setSisterId(@Nullable UUID uniqueId) {
      this.entityData.set(SISTER_UUID, Optional.ofNullable(uniqueId));
   }
}
