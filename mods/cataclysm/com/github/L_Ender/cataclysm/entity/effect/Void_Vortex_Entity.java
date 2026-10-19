package com.github.L_Ender.cataclysm.entity.effect;

import com.github.L_Ender.cataclysm.client.particle.Options.Rising_Trail_Options;
import com.github.L_Ender.cataclysm.init.ModEntities;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Void_Vortex_Entity extends Entity {
   protected static final EntityDataAccessor<Integer> LIFESPAN = SynchedEntityData.defineId(Void_Vortex_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> CASTER = SynchedEntityData.defineId(Void_Vortex_Entity.class, EntityDataSerializers.INT);
   private boolean madeOpenNoise = false;
   private boolean madeCloseNoise = false;
   @Nullable
   private LivingEntity owner;

   public Void_Vortex_Entity(EntityType<?> entityTypeIn, Level worldIn) {
      super(entityTypeIn, worldIn);
   }

   public Void_Vortex_Entity(Level worldIn, double x, double y, double z, float p_i47276_8_, LivingEntity casterIn, int span) {
      this((EntityType<?>)ModEntities.VOID_VORTEX.get(), worldIn);
      this.setLifespan(span);
      this.setOwner(casterIn);
      this.setYRot(p_i47276_8_ * (180.0F / (float)Math.PI));
      this.setPos(x, y, z);
      if (!worldIn.isClientSide) {
         this.setCasterID(casterIn.getId());
      }
   }

   public void tick() {
      super.tick();
      if (this.tickCount == 1) {
         if (this.getLifespan() == 0) {
            this.setLifespan(60);
         }

         if (this.level().isClientSide) {
            this.owner = (LivingEntity)this.level().getEntity(this.getCasterID());
         }
      }

      if (!this.madeOpenNoise) {
         this.gameEvent(GameEvent.ENTITY_PLACE);
         this.playSound(SoundEvents.END_PORTAL_SPAWN, 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
         this.madeOpenNoise = true;
      }

      if (Math.min(this.tickCount, this.getLifespan()) >= 16) {
         if (this.level().isClientSide) {
            float r = 0.4F;
            float g = 0.1F;
            float b = 0.8F;
            this.level()
               .addParticle(
                  new Rising_Trail_Options(r, g, b, 2.5F + this.random.nextFloat() * 0.25F, 0.08F), this.getX(), this.getY() + 5.0, this.getZ(), 0.0, -0.3, 0.0
               );
         }

         AABB screamBox = new AABB(this.getX() - 3.0, this.getY(), this.getZ() - 3.0, this.getX() + 3.0, this.getY() + 15.0, this.getZ() + 3.0);

         for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, screamBox)) {
            if (!this.isAlliedTo(entity)
               && (this.owner == null || !this.owner.equals(entity) && !this.owner.isAlliedTo(entity))
               && !(entity instanceof Player player && player.getAbilities().invulnerable)) {
               Vec3 diff = entity.position().subtract(this.position().add(0.0, 0.0, 0.0));
               diff = diff.normalize().scale(0.075);
               entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, -2.0, 0.0).subtract(diff));
            }
         }
      }

      this.setLifespan(this.getLifespan() - 1);
      if (this.getLifespan() <= 16 && !this.madeCloseNoise) {
         this.gameEvent(GameEvent.ENTITY_PLACE);
         this.madeCloseNoise = true;
      }

      if (this.getLifespan() <= 0) {
         this.level().explode(this.owner, this.getX(), this.getY(), this.getZ(), 2.0F, false, ExplosionInteraction.NONE);
         this.remove(RemovalReason.DISCARDED);
      }
   }

   public int getLifespan() {
      return (Integer)this.entityData.get(LIFESPAN);
   }

   public void setLifespan(int i) {
      this.entityData.set(LIFESPAN, i);
   }

   public int getCasterID() {
      return (Integer)this.entityData.get(CASTER);
   }

   public void setCasterID(int id) {
      this.entityData.set(CASTER, id);
   }

   public void setOwner(@Nullable LivingEntity p_19719_) {
      this.owner = p_19719_;
      this.setCasterID(p_19719_ == null ? 0 : p_19719_.getId());
   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.owner == null && this.getCasterID() != 0 && this.level() instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.level()).getEntity(this.getCasterID());
         if (entity instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
         }
      }

      return this.owner;
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(LIFESPAN, 300);
      p_326229_.define(CASTER, -1);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.setLifespan(compound.getInt("Lifespan"));
      this.setCasterID(compound.getInt("CasterId"));
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("Lifespan", this.getLifespan());
      compound.putInt("CasterId", this.getCasterID());
   }
}
