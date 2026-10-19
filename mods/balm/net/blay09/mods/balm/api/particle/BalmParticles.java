package net.blay09.mods.balm.api.particle;

import java.util.function.Function;
import net.blay09.mods.balm.api.DeferredObject;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;

public interface BalmParticles {
   <T extends ParticleOptions> DeferredObject<ParticleType<T>> registerParticle(Function<ResourceLocation, ParticleType<T>> var1, ResourceLocation var2);

   SimpleParticleType createSimple(boolean var1);
}
