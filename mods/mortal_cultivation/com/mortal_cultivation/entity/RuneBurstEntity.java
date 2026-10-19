package com.mortal_cultivation.entity;

import com.mortal_cultivation.registry.CultivationEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class RuneBurstEntity extends Entity implements GeoEntity {
   public static final int LIFETIME_TICKS = 13;
   private static final RawAnimation BURST_ANIMATION = RawAnimation.begin().thenPlay("animation.bei_wen.rune_burst");
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public RuneBurstEntity(EntityType<? extends RuneBurstEntity> type, Level level) {
      super(type, level);
      this.noPhysics = true;
      this.noCulling = true;
      this.setNoGravity(true);
      this.setInvulnerable(true);
   }

   public static void spawn(ServerLevel level, BlockPos pos) {
      RuneBurstEntity burst = (RuneBurstEntity)((EntityType)CultivationEntities.RUNE_BURST.get()).create(level);
      if (burst != null) {
         burst.setPos(pos.getX() + 0.5, pos.getY() + 0.45, pos.getZ() + 0.5);
         burst.setYRot(level.random.nextFloat() * 360.0F);
         level.addFreshEntity(burst);
      }
   }

   protected void defineSynchedData(Builder builder) {
   }

   public void tick() {
      super.tick();
      this.setDeltaMovement(0.0, 0.0, 0.0);
      if (!this.level().isClientSide && this.tickCount >= 13) {
         this.discard();
      }
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
      return new ClientboundAddEntityPacket(this, entity);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "rune_burst", 0, state -> {
         state.getController().setAnimation(BURST_ANIMATION);
         return PlayState.CONTINUE;
      }));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public boolean isPushable() {
      return false;
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public boolean isPickable() {
      return false;
   }

   public boolean isAttackable() {
      return false;
   }
}
