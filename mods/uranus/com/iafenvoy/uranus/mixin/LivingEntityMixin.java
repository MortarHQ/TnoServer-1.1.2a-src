package com.iafenvoy.uranus.mixin;

import com.iafenvoy.uranus.event.LivingEntityEvents;
import com.iafenvoy.uranus.object.item.ISwingable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
   protected LivingEntityMixin(EntityType<? extends Entity> entityType, Level world) {
      super(entityType, world);
   }

   @Shadow
   public abstract ItemStack getItemInHand(InteractionHand var1);

   @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"), cancellable = true)
   private void onSwingHand(InteractionHand hand, boolean fromServerPlayer, CallbackInfo ci) {
      ItemStack stack = this.getItemInHand(hand);
      if (stack.getItem() instanceof ISwingable iSwingable && iSwingable.onEntitySwing(stack, this)) {
         ci.cancel();
      }
   }

   @Inject(method = "causeFallDamage(FFLnet/minecraft/world/damagesource/DamageSource;)Z", at = @At("HEAD"))
   public void onEntityFall(float fallDistance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
      LivingEntityEvents.FALL.invoker().onFall((LivingEntity)this, fallDistance, multiplier, source);
   }
}
