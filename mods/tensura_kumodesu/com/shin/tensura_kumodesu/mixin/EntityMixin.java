package com.shin.tensura_kumodesu.mixin;

import com.shin.tensura_kumodesu.registry.attributes.KumodesuAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, priority = 2000)
public abstract class EntityMixin {
   @Shadow
   private float eyeHeight;

   @Inject(method = "refreshDimensions()V", at = @At("TAIL"))
   private void modifyEyeHeight(CallbackInfo ci) {
      if (this instanceof Player player) {
         double eyeHeight = player.getAttributeValue(KumodesuAttributes.EYE_HEIGHT);
         if (eyeHeight != 1.0) {
            this.eyeHeight = (float)(this.eyeHeight * eyeHeight);
         }
      }
   }
}
