package com.bobmowzie.mowziesmobs.mixin.client;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

@Mixin(value = GeoModel.class, remap = false)
public abstract class GeoModelMixin {
   @Shadow
   private long lastRenderedInstance;

   @WrapOperation(
      method = "handleAnimations",
      at = @At(value = "FIELD", target = "Lsoftware/bernie/geckolib/model/GeoModel;lastRenderedInstance:J", ordinal = 0)
   )
   private <T extends GeoAnimatable> long test(GeoModel<?> instance, Operation<Long> original, @Local(argsOnly = true) T animatable) {
      return animatable instanceof MowzieEntity entity && entity.renderingInGUI ? -this.lastRenderedInstance : (Long)original.call(new Object[]{instance});
   }
}
