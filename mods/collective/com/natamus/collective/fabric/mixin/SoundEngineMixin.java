package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveSoundEvents;
import net.minecraft.class_1113;
import net.minecraft.class_1140;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = class_1140.class, priority = 1001)
public class SoundEngineMixin {
   @Inject(
      method = "play",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/sounds/SoundInstance;canPlaySound()Z", ordinal = 0),
      cancellable = true
   )
   public void SoundEngine_play(class_1113 soundInstance, CallbackInfo ci) {
      class_1140 soundEngine = (class_1140)this;
      if (!((CollectiveSoundEvents.Sound_Play)CollectiveSoundEvents.SOUND_PLAY.invoker()).onSoundPlay(soundEngine, soundInstance)) {
         ci.cancel();
      }
   }
}
