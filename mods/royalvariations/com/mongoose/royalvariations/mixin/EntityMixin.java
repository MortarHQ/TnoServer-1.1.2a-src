package com.mongoose.royalvariations.mixin;

import com.mongoose.royalvariations.common.attachments.RVCapHelper;
import com.mongoose.royalvariations.common.effects.RVEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
   @Inject(method = "getTeamColor()I", at = @At("HEAD"), cancellable = true)
   public void getTeamColor(CallbackInfoReturnable<Integer> cir) {
      Entity entity = (Entity)this;
      if (entity instanceof LivingEntity livingEntity) {
         if (RVCapHelper.isMarked(livingEntity)) {
            cir.setReturnValue(ChatFormatting.DARK_RED.getColor());
         } else if (RVCapHelper.isGazed(livingEntity)) {
            cir.setReturnValue(ChatFormatting.DARK_PURPLE.getColor());
         }
      }
   }

   @Inject(method = "push(DDD)V", at = @At("HEAD"), cancellable = true)
   public void push(double p_20286_, double p_20287_, double p_20288_, CallbackInfo callbackInfo) {
      Entity var9 = (Entity)this;
      if (var9 instanceof LivingEntity livingEntity && livingEntity.hasEffect(RVEffects.TRAPPED)) {
         callbackInfo.cancel();
      }
   }
}
