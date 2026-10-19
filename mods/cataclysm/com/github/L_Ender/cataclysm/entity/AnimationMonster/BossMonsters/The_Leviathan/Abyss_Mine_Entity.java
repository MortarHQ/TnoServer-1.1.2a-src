package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan;

import com.github.L_Ender.cataclysm.client.particle.Options.LightningParticleOptions;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class Abyss_Mine_Entity extends Entity {
   private int warmupDelayTicks;
   private boolean sentSpikeEvent;
   private int lifeTicks = 800;
   private boolean clientSideAttackStarted;
   private LivingEntity caster;
   private UUID casterUuid;
   private static final EntityDataAccessor<Boolean> ACTIVATE = SynchedEntityData.defineId(Abyss_Mine_Entity.class, EntityDataSerializers.BOOLEAN);
   public float activateProgress;
   public float prevactivateProgress;
   public int time;

   public Abyss_Mine_Entity(EntityType<? extends Abyss_Mine_Entity> p_i50170_1_, Level p_i50170_2_) {
      super(p_i50170_1_, p_i50170_2_);
   }

   public Abyss_Mine_Entity(Level worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_, LivingEntity casterIn) {
      this((EntityType<? extends Abyss_Mine_Entity>)ModEntities.ABYSS_MINE.get(), worldIn);
      this.warmupDelayTicks = p_i47276_9_;
      this.setCaster(casterIn);
      this.setYRot(p_i47276_8_ * (180.0F / (float)Math.PI));
      this.setPos(x, y, z);
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(ACTIVATE, false);
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

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.warmupDelayTicks = compound.getInt("Warmup");
      if (compound.hasUUID("Owner")) {
         this.casterUuid = compound.getUUID("Owner");
      }
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("Warmup", this.warmupDelayTicks);
      if (this.casterUuid != null) {
         compound.putUUID("Owner", this.casterUuid);
      }
   }

   public void tick() {
      super.tick();
      this.time++;
      this.prevactivateProgress = this.activateProgress;
      if (this.isActivate() && this.activateProgress > 0.0F) {
         this.activateProgress--;
      }

      if (this.level().isClientSide) {
         if (this.clientSideAttackStarted) {
            this.lifeTicks--;
            if (!this.isActivate() && this.activateProgress < 10.0F) {
               this.activateProgress++;
            }

            for (int i = 0; i < 2; i++) {
               double d0 = this.getX() + (this.random.nextDouble() * 2.0 - 1.0) * this.getBbWidth() * 0.5;
               double d1 = this.getY() + this.getBbHeight() * 1.0F / 2.0F;
               double d2 = this.getZ() + (this.random.nextDouble() * 2.0 - 1.0) * this.getBbWidth() * 0.5;
               double d3 = this.random.nextGaussian() * 0.3;
               double d4 = this.random.nextGaussian() * 0.3;
               double d5 = this.random.nextGaussian() * 0.3;
               this.level().addParticle(new LightningParticleOptions(102, 26, 204), d0, d1, d2, d3, d4, d5);
            }

            if (this.lifeTicks == 14) {
               this.setActivate(true);
            }
         }
      } else if (--this.warmupDelayTicks < 0) {
         if (this.warmupDelayTicks == -10 && this.isActivate()) {
            this.setActivate(false);
         }

         if (this.warmupDelayTicks < -20) {
            for (LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2, 0.0, 0.2))) {
               this.explode(livingentity);
            }
         }

         if (!this.sentSpikeEvent) {
            this.level().broadcastEntityEvent(this, (byte)4);
            this.clientSideAttackStarted = true;
            this.sentSpikeEvent = true;
         }

         if (--this.lifeTicks < 0) {
            this.discard();
         }
      }
   }

   public boolean isActivate() {
      return (Boolean)this.entityData.get(ACTIVATE);
   }

   public void setActivate(boolean Activate) {
      this.entityData.set(ACTIVATE, Activate);
   }

   protected void explode(LivingEntity livingentity) {
      LivingEntity Caster = this.getCaster();
      if (livingentity.isAlive()) {
         if (Caster != null) {
            if (!Caster.isAlliedTo(livingentity) && livingentity != Caster && livingentity.isAlive()) {
               this.level().explode(Caster, this.getX(), this.getY(0.0625), this.getZ(), 1.0F, ExplosionInteraction.NONE);
               livingentity.addEffect(new MobEffectInstance(ModEffect.EFFECTABYSSAL_FEAR, 200, 0));
               this.remove(RemovalReason.DISCARDED);
            }
         } else {
            this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), 1.0F, ExplosionInteraction.NONE);
            livingentity.addEffect(new MobEffectInstance(ModEffect.EFFECTABYSSAL_FEAR, 200, 0));
            this.remove(RemovalReason.DISCARDED);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void handleEntityEvent(byte id) {
      super.handleEntityEvent(id);
      if (id == 4) {
         this.clientSideAttackStarted = true;
      }
   }

   public float getLightLevelDependentMagicValue() {
      return 1.0F;
   }
}
