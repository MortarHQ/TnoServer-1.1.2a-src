package net.pixeldreamstudios.mythika.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.pixeldreamstudios.mythika.client.ScreenShake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
   @Shadow
   private float xRot;
   @Shadow
   private float yRot;

   @Shadow
   protected abstract void setRotation(float var1, float var2);

   @Inject(method = "setup", at = @At("RETURN"))
   private void mythika$shake(BlockGetter level, Entity entity, boolean detached, boolean thirdPersonReverse, float partialTick, CallbackInfo ci) {
      float amplitude = ScreenShake.amplitude(partialTick);
      if (!(amplitude <= 0.0F)) {
         float time = (float)(entity.level().getGameTime() % 100000L) + partialTick;
         this.setRotation(this.yRot + Mth.sin(time * 1.9F) * amplitude, this.xRot + Mth.cos(time * 2.7F) * amplitude);
      }
   }
}
