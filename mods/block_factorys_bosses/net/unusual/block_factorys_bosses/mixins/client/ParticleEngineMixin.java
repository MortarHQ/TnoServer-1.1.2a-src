package net.unusual.block_factorys_bosses.mixins.client;

import com.llamalad7.mixinextras.sugar.Local;
import java.util.function.Predicate;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.culling.Frustum;
import net.unusual.block_factorys_bosses.client.particle.BRParticleRenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {
   @Inject(
      method = "render(Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/culling/Frustum;Ljava/util/function/Predicate;)V",
      at = @At(
         value = "INVOKE",
         target = "Lcom/mojang/blaze3d/vertex/BufferUploader;drawWithShader(Lcom/mojang/blaze3d/vertex/MeshData;)V",
         shift = Shift.BY,
         by = 2
      )
   )
   public void render(
      LightTexture lightTexture,
      Camera camera,
      float tickDelta,
      Frustum frustum,
      Predicate<ParticleRenderType> renderTypePredicate,
      CallbackInfo ci,
      @Local ParticleRenderType particlerendertype
   ) {
      if (particlerendertype instanceof BRParticleRenderType brParticleRenderType) {
         brParticleRenderType.end();
      }
   }
}
