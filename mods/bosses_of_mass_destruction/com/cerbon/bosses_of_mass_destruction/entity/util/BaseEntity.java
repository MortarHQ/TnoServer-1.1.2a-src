package com.cerbon.bosses_of_mass_destruction.entity.util;

import com.cerbon.bosses_of_mass_destruction.entity.damage.IDamageHandler;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class BaseEntity extends PathfinderMob implements GeoEntity {
   private AnimatableInstanceCache animationFactory;
   public Vec3 idlePosition = Vec3.ZERO;
   protected ServerBossEvent bossBar;
   protected IDamageHandler damageHandler;
   protected IEntityEventHandler entityEventHandler;
   protected IEntityTick<Level> clientTick;
   protected IEntityTick<ServerLevel> serverTick;
   protected IDataAccessorHandler dataAccessorHandler;
   protected IMobEffectFilter mobEffectHandler;
   protected IMoveHandler moveHandler;
   protected INbtHandler nbtHandler;
   protected IEntityTick<Level> deathClientTick;
   protected IEntityTick<ServerLevel> deathServerTick;
   protected final EventScheduler preTickEvents = new EventScheduler();
   protected final EventScheduler postTickEvents = new EventScheduler();

   public BaseEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
      super(entityType, level);
   }

   public void tick() {
      this.preTickEvents.updateEvents();
      if (this.idlePosition == Vec3.ZERO) {
         this.idlePosition = this.position();
      }

      if (this.level().isClientSide()) {
         this.clientTick();
         if (this.clientTick != null) {
            this.clientTick.tick(this.level());
         }
      } else if (this.level() instanceof ServerLevel serverLevel) {
         this.serverTick(serverLevel);
         if (this.serverTick != null) {
            this.serverTick.tick(serverLevel);
         }
      }

      super.tick();
      this.postTickEvents.updateEvents();
   }

   protected void tickDeath() {
      if (this.level().isClientSide() && this.deathClientTick != null) {
         this.deathClientTick.tick(this.level());
      } else if (this.level() instanceof ServerLevel serverLevel && this.deathServerTick != null) {
         this.deathServerTick.tick(serverLevel);
      } else {
         super.tickDeath();
      }
   }

   public void clientTick() {
   }

   public void serverTick(ServerLevel serverLevel) {
   }

   public void aiStep() {
      super.aiStep();
      this.getSensing().tick();
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.bossBar != null) {
         this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
      }
   }

   public void load(@NotNull CompoundTag compound) {
      super.load(compound);
      if (this.hasCustomName() && this.bossBar != null) {
         this.bossBar.setName(this.getDisplayName());
      }

      if (this.nbtHandler != null) {
         this.nbtHandler.fromTag(compound);
      }
   }

   public void setCustomName(@Nullable Component name) {
      super.setCustomName(name);
      if (this.bossBar != null) {
         this.bossBar.setName(this.getDisplayName());
      }
   }

   public void startSeenByPlayer(@NotNull ServerPlayer serverPlayer) {
      super.startSeenByPlayer(serverPlayer);
      if (this.bossBar != null) {
         this.bossBar.addPlayer(serverPlayer);
      }
   }

   public void stopSeenByPlayer(@NotNull ServerPlayer serverPlayer) {
      super.stopSeenByPlayer(serverPlayer);
      if (this.bossBar != null) {
         this.bossBar.removePlayer(serverPlayer);
      }
   }

   public void readAdditionalSaveData(@NotNull CompoundTag compound) {
      super.readAdditionalSaveData(compound);
   }

   public void handleEntityEvent(byte id) {
      if (this.entityEventHandler != null) {
         this.entityEventHandler.handleEntityEvent(id);
      }

      super.handleEntityEvent(id);
   }

   public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> data) {
      super.onSyncedDataUpdated(data);
      if (this.dataAccessorHandler != null) {
         this.dataAccessorHandler.onSyncedDataUpdated(data);
      }
   }

   public boolean canBeAffected(@NotNull MobEffectInstance effectInstance) {
      return this.mobEffectHandler != null ? this.mobEffectHandler.canBeAffected(effectInstance) : super.canBeAffected(effectInstance);
   }

   public boolean hurt(@NotNull DamageSource source, float amount) {
      EntityStats stats = new EntityStats(this);
      IDamageHandler handler = this.damageHandler;
      if (!this.level().isClientSide() && handler != null) {
         handler.beforeDamage(stats, source, amount);
      }

      boolean result = handler != null ? handler.shouldDamage(this, source, amount) && super.hurt(source, amount) : super.hurt(source, amount);
      if (!this.level().isClientSide() && handler != null) {
         handler.afterDamage(stats, source, amount, result);
      }

      return result;
   }

   public void setTarget(@Nullable LivingEntity target) {
      if (target == null) {
         this.idlePosition = this.position();
      }

      super.setTarget(target);
   }

   public void move(@NotNull MoverType type, @NotNull Vec3 movement) {
      boolean shouldDoDefault = this.moveHandler != null && this.moveHandler.canMove(type, movement);
      if (this.moveHandler == null || shouldDoDefault) {
         super.move(type, movement);
      }
   }

   @NotNull
   public CompoundTag saveWithoutId(@NotNull CompoundTag compound) {
      CompoundTag superCompound = super.saveWithoutId(compound);
      return this.nbtHandler != null ? this.nbtHandler.toTag(superCompound) : superCompound;
   }

   public Vec3 safeGetTargetPos() {
      LivingEntity target = this.getTarget();
      return target == null ? Vec3.ZERO : target.position();
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      if (this.animationFactory == null) {
         this.animationFactory = GeckoLibUtil.createInstanceCache(this);
      }

      return this.animationFactory;
   }
}
