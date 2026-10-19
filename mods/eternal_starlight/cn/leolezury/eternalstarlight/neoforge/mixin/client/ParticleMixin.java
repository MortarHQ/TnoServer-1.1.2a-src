package cn.leolezury.eternalstarlight.neoforge.mixin.client;

import cn.leolezury.eternalstarlight.common.client.particle.effect.ElectricSparkParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.ExplosionShockParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.GatheringTrailParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.LunarSlashParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.OrbitalTrailParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.RingExplosionParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.RingParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.TrailParticle;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Particle.class)
public abstract class ParticleMixin {
   @ModifyReturnValue(method = "getRenderBoundingBox(F)Lnet/minecraft/world/phys/AABB;", at = @At("RETURN"), remap = false)
   public AABB getRenderBoundingBox(AABB original) {
      Particle particle = (Particle)this;
      return !(particle instanceof ElectricSparkParticle)
            && !(particle instanceof ExplosionShockParticle)
            && !(particle instanceof GatheringTrailParticle)
            && !(particle instanceof LunarSlashParticle)
            && !(particle instanceof OrbitalTrailParticle)
            && !(particle instanceof RingExplosionParticle)
            && !(particle instanceof RingParticle)
            && !(particle instanceof TrailParticle)
         ? original
         : AABB.INFINITE;
   }
}
