package com.tensura_tno.mixin.client;

import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "com.bobmowzie.mowziesmobs.server.item.ItemElokosaPaw", remap = false)
public class MowzieElokosaPawServerCrashFixMixin {
   @Redirect(
      method = "use",
      at = @At(
         value = "INVOKE",
         target = "Lcom/bobmowzie/mowziesmobs/client/particle/util/AdvancedParticleBase;spawnParticle(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/Holder;DDDDDDZDDDDDDDDDDDZZ[Lcom/bobmowzie/mowziesmobs/client/particle/util/ParticleComponent;)V",
         remap = false
      ),
      require = 0,
      remap = false
   )
   private void tno$guardElokosaPawParticle(
      Level world,
      Holder<ParticleType<?>> particle,
      double x,
      double y,
      double z,
      double motionX,
      double motionY,
      double motionZ,
      boolean faceCamera,
      double yaw,
      double pitch,
      double roll,
      double faceCameraAngle,
      double scale,
      double red,
      double green,
      double blue,
      double alpha,
      double airDrag,
      double duration,
      boolean emissive,
      boolean canCollide,
      ParticleComponent[] components
   ) {
      if (world.isClientSide()) {
         AdvancedParticleBase.spawnParticle(
            world,
            particle,
            x,
            y,
            z,
            motionX,
            motionY,
            motionZ,
            faceCamera,
            yaw,
            pitch,
            roll,
            faceCameraAngle,
            scale,
            red,
            green,
            blue,
            alpha,
            airDrag,
            duration,
            emissive,
            canCollide,
            components
         );
      }
   }
}
