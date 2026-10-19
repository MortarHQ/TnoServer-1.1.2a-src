package com.tensura_tno.mixin.client;

import java.lang.reflect.Method;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.TamableAnimal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "io.github.manasmods.tensura.entity.human.ShizuEntity", remap = false)
public class ShizuTamedNoIfritMixin {
   @Inject(method = "die", at = @At("HEAD"), remap = true, require = 0)
   private void tensuraTno$preventIfritForTamed(DamageSource source, CallbackInfo ci) {
      if (this instanceof TamableAnimal ta) {
         if (ta.isTame()) {
            try {
               Method setTransformTick = ta.getClass().getMethod("setTransformTick", int.class);
               setTransformTick.invoke(ta, 100);
            } catch (Exception var5) {
            }
         }
      }
   }
}
