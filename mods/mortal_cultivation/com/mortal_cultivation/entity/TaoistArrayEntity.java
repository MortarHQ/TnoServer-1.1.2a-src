package com.mortal_cultivation.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TaoistArrayEntity extends Entity implements GeoEntity {
   private static final EntityDataAccessor<Boolean> EXPANDING = SynchedEntityData.defineId(TaoistArrayEntity.class, EntityDataSerializers.BOOLEAN);
   private static final RawAnimation EXPAND_ANIMATION = RawAnimation.begin().thenPlay("Expand");
   private static final RawAnimation ROTATE_ANIMATION = RawAnimation.begin().thenLoop("rotate");
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private boolean hasExpanded = false;

   public TaoistArrayEntity(EntityType<?> entityType, Level level) {
      super(entityType, level);
      this.noCulling = true;
      this.noPhysics = true;
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(EXPANDING, false);
   }

   public void startExpanding() {
      this.entityData.set(EXPANDING, true);
      this.hasExpanded = true;
   }

   public boolean isExpanding() {
      return (Boolean)this.entityData.get(EXPANDING);
   }

   public void tick() {
      super.tick();
      if (!this.hasExpanded && this.tickCount > 1) {
         this.startExpanding();
      }

      if (this.isExpanding() && this.tickCount > 8) {
         this.hasExpanded = true;
      }

      if (this.tickCount % 20 == 0 && !this.hasNearbyMeditatingPlayer()) {
         this.discard();
      }
   }

   private boolean hasNearbyMeditatingPlayer() {
      return this.level().players().stream().filter(player -> player.distanceTo(this) < 5.0).anyMatch(player -> true);
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      this.hasExpanded = tag.getBoolean("HasExpanded");
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      tag.putBoolean("HasExpanded", this.hasExpanded);
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
      return new ClientboundAddEntityPacket(this, entity);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, this::predicate));
   }

   private PlayState predicate(AnimationState<TaoistArrayEntity> event) {
      if (this.isExpanding() && !this.hasExpanded) {
         event.getController().setAnimation(EXPAND_ANIMATION);
         return PlayState.CONTINUE;
      } else if (this.hasExpanded) {
         event.getController().setAnimation(ROTATE_ANIMATION);
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   protected boolean canRide(Entity entity) {
      return false;
   }

   public void forceRemove() {
      this.discard();
   }
}
