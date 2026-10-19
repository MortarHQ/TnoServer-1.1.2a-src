package com.github.L_Ender.cataclysm.entity.effect;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.client.particle.Options.StormParticleOptions;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant.Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Sandstorm_Entity extends Entity {
   protected static final EntityDataAccessor<Integer> LIFESPAN = SynchedEntityData.defineId(Sandstorm_Entity.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Float> OFFSET = SynchedEntityData.defineId(Sandstorm_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(Sandstorm_Entity.class, EntityDataSerializers.INT);
   public AnimationState SpawnAnimationState = new AnimationState();
   public AnimationState DespawnAnimationState = new AnimationState();
   private LivingEntity caster;
   private UUID casterUuid;

   public Sandstorm_Entity(EntityType<?> entityTypeIn, Level worldIn) {
      super(entityTypeIn, worldIn);
   }

   public Sandstorm_Entity(Level worldIn, double x, double y, double z, int lifespan, float offset, LivingEntity casterIn) {
      this((EntityType<?>)ModEntities.SANDSTORM.get(), worldIn);
      this.setLifespan(lifespan);
      this.setPos(x, y, z);
      this.setState(1);
      this.setOffset(offset);
      this.setCaster(casterIn);
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
      return new ClientboundAddEntityPacket(this, entity);
   }

   public void tick() {
      super.tick();
      this.updateMotion();
      LivingEntity owner = this.getCaster();
      if (owner != null && !owner.isAlive()) {
         this.discard();
      }

      if (this.level().isClientSide) {
         float ran = 0.04F;
         float r = 0.89F + this.random.nextFloat() * ran;
         float g = 0.85F + this.random.nextFloat() * ran;
         float b = 0.69F + this.random.nextFloat() * ran * 1.5F;
         this.level()
            .addParticle(
               new StormParticleOptions(r, g, b, 2.75F + this.random.nextFloat() * 0.6F, 3.75F + this.random.nextFloat() * 0.6F, this.getId()),
               this.getX(),
               this.getY(),
               this.getZ(),
               0.0,
               0.0,
               0.0
            );
         this.level()
            .addParticle(
               new StormParticleOptions(r, g, b, 2.5F + this.random.nextFloat() * 0.45F, 3.0F + this.random.nextFloat() * 0.45F, this.getId()),
               this.getX(),
               this.getY(),
               this.getZ(),
               0.0,
               0.0,
               0.0
            );
         this.level()
            .addParticle(
               new StormParticleOptions(r, g, b, 2.25F + this.random.nextFloat() * 0.45F, 2.25F + this.random.nextFloat() * 0.45F, this.getId()),
               this.getX(),
               this.getY(),
               this.getZ(),
               0.0,
               0.0,
               0.0
            );
         this.level()
            .addParticle(
               new StormParticleOptions(r, g, b, 1.25F + this.random.nextFloat() * 0.45F, 1.25F + this.random.nextFloat() * 0.45F, this.getId()),
               this.getX(),
               this.getY(),
               this.getZ(),
               0.0,
               0.0,
               0.0
            );
         if (this.getState() == 1 && this.getLifespan() < 295) {
            this.setState(0);
         }

         if (this.getState() == 0 && this.getLifespan() < 10) {
            this.setState(2);
         }
      }

      if (!this.isSilent() && this.level().isClientSide) {
         Cataclysm.PROXY.playWorldSound(this, (byte)2);
      }

      for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2, 0.0, 0.2))) {
         this.damage(entity);
      }

      this.setLifespan(this.getLifespan() - 1);
      if (this.getLifespan() <= 0) {
         Cataclysm.PROXY.clearSoundCacheFor(this);
         this.remove(RemovalReason.DISCARDED);
      }
   }

   private void damage(LivingEntity Hitentity) {
      LivingEntity livingentity = this.getCaster();
      if (Hitentity.isAlive() && !Hitentity.isInvulnerable() && Hitentity != livingentity && this.tickCount % 3 == 0) {
         if (livingentity == null) {
            boolean flag = Hitentity.hurt(this.damageSources().magic(), 7.0F);
            if (flag) {
               MobEffectInstance effectinstance = new MobEffectInstance(ModEffect.EFFECTCURSE_OF_DESERT, 200, 0);
               Hitentity.addEffect(effectinstance);
            }
         } else if (!livingentity.isAlliedTo(Hitentity) && !Hitentity.isAlliedTo(livingentity)) {
            boolean flag = Hitentity.hurt(this.damageSources().indirectMagic(this, livingentity), 7.0F);
            if (flag) {
               MobEffectInstance effectinstance = new MobEffectInstance(ModEffect.EFFECTCURSE_OF_DESERT, 200, 0);
               Hitentity.addEffect(effectinstance);
            }
         }
      }
   }

   public int getLifespan() {
      return (Integer)this.entityData.get(LIFESPAN);
   }

   public void setLifespan(int i) {
      this.entityData.set(LIFESPAN, i);
   }

   public float getOffset() {
      return (Float)this.entityData.get(OFFSET);
   }

   public void setOffset(float i) {
      this.entityData.set(OFFSET, i);
   }

   public void setCaster(@Nullable LivingEntity p_190549_1_) {
      this.caster = p_190549_1_;
      this.casterUuid = p_190549_1_ == null ? null : p_190549_1_.getUUID();
   }

   @Nullable
   public LivingEntity getCaster() {
      if (this.caster == null && this.casterUuid != null && this.level() instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.level()).getEntity(this.casterUuid);
         if (entity instanceof LivingEntity) {
            this.caster = (LivingEntity)entity;
         }
      }

      return this.caster;
   }

   protected void updateMotion() {
      LivingEntity owner = this.getCaster();
      if (owner != null) {
         if (owner instanceof Ancient_Remnant_Entity) {
            Vec3 center = owner.position().add(0.0, 0.0, 0.0);
            float radius = 8.0F;
            float speed = this.tickCount * 0.04F;
            float offset = this.getOffset();
            Vec3 orbit = new Vec3(center.x + Math.cos(speed + offset) * radius, center.y, center.z + Math.sin(speed + offset) * radius);
            this.moveTo(orbit);
         }

         if (owner instanceof Player) {
            Vec3 center = owner.position().add(0.0, 0.0, 0.0);
            float radius = 6.0F;
            float speed = this.tickCount * 0.04F;
            float offset = this.getOffset();
            Vec3 orbit = new Vec3(center.x + Math.cos(speed + offset) * radius, center.y, center.z + Math.sin(speed + offset) * radius);
            this.moveTo(orbit);
         }
      }
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(LIFESPAN, 300);
      p_326229_.define(OFFSET, 0.0F);
      p_326229_.define(STATE, 0);
   }

   public AnimationState getAnimationState(String input) {
      if (input == "spawn") {
         return this.SpawnAnimationState;
      } else {
         return input == "despawn" ? this.DespawnAnimationState : new AnimationState();
      }
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
      if (STATE.equals(p_21104_) && this.level().isClientSide) {
         switch (this.getState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.SpawnAnimationState.startIfStopped(this.tickCount);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.DespawnAnimationState.startIfStopped(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.DespawnAnimationState.stop();
      this.SpawnAnimationState.stop();
   }

   public int getState() {
      return (Integer)this.entityData.get(STATE);
   }

   public void setState(int state) {
      this.entityData.set(STATE, state);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.setLifespan(compound.getInt("Lifespan"));
      if (compound.hasUUID("Owner")) {
         this.casterUuid = compound.getUUID("Owner");
      }
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("Lifespan", this.getLifespan());
      if (this.casterUuid != null) {
         compound.putUUID("Owner", this.casterUuid);
      }
   }
}
