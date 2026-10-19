package com.mongoose.royalvariations.mixin;

import com.mongoose.royalvariations.common.effects.RVEffects;
import com.mongoose.royalvariations.common.entities.IRoyalInterface;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements IRoyalInterface {
   @Unique
   private static final EntityDataAccessor<Integer> FLASH_TICK = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
   @Unique
   private static final EntityDataAccessor<Integer> MARKED_TICK = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
   @Unique
   private static final EntityDataAccessor<Integer> GAZED_TICK = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);

   @Shadow
   public abstract boolean hasEffect(Holder<MobEffect> var1);

   public LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
      super(p_19870_, p_19871_);
   }

   @Inject(method = "defineSynchedData", at = @At("TAIL"))
   private void onDefineSynchedData(Builder builder, CallbackInfo ci) {
      builder.define(FLASH_TICK, 0);
      builder.define(MARKED_TICK, 0);
      builder.define(GAZED_TICK, 0);
   }

   @Override
   public int getFlashTick() {
      return (Integer)((LivingEntity)this).getEntityData().get(FLASH_TICK);
   }

   @Override
   public void setFlashTick(int tick) {
      ((LivingEntity)this).getEntityData().set(FLASH_TICK, tick);
   }

   @Override
   public int getMarkedTick() {
      return (Integer)((LivingEntity)this).getEntityData().get(MARKED_TICK);
   }

   @Override
   public void setMarkedTick(int marked) {
      ((LivingEntity)this).getEntityData().set(MARKED_TICK, marked);
   }

   @Override
   public int getGazeTick() {
      return (Integer)((LivingEntity)this).getEntityData().get(GAZED_TICK);
   }

   @Override
   public void setGazeTick(int tick) {
      ((LivingEntity)this).getEntityData().set(GAZED_TICK, tick);
   }

   @Inject(method = "isCurrentlyGlowing()Z", at = @At("HEAD"), cancellable = true)
   public void isCurrentlyGlowing(CallbackInfoReturnable<Boolean> cir) {
      if (this.hasEffect(RVEffects.MARKED)) {
         cir.setReturnValue(true);
      }
   }

   @Inject(method = "getJumpPower()F", at = @At("TAIL"), cancellable = true)
   public void getJumpPower(CallbackInfoReturnable<Float> cir) {
      float original = (Float)cir.getReturnValue();
      if (this.hasEffect(RVEffects.HEAVINESS_OF_THE_END)) {
         cir.setReturnValue(original * 0.75F);
      }
   }

   @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
   public void jumpFromGround(CallbackInfo callbackInfo) {
      if (this.hasEffect(RVEffects.TRAPPED)) {
         callbackInfo.cancel();
      }
   }
}
