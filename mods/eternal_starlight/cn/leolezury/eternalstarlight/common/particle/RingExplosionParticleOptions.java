package cn.leolezury.eternalstarlight.common.particle;

import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

public record RingExplosionParticleOptions(Vector3f fromColor, Vector3f toColor, float scale) implements ParticleOptions {
   public static final RingExplosionParticleOptions SHOCKWAVE = fromIntColor(new Vector3f(255.0F, 255.0F, 255.0F), new Vector3f(255.0F, 255.0F, 255.0F), 4.0F);
   public static final RingExplosionParticleOptions ENERGY = fromIntColor(new Vector3f(0.0F, 234.0F, 255.0F), new Vector3f(255.0F, 255.0F, 255.0F), 4.0F);
   public static final RingExplosionParticleOptions ENERGY_SMALL = fromIntColor(new Vector3f(0.0F, 234.0F, 255.0F), new Vector3f(255.0F, 255.0F, 255.0F), 1.5F);
   public static final RingExplosionParticleOptions LUNAR = fromIntColor(new Vector3f(66.0F, 66.0F, 115.0F), new Vector3f(32.0F, 32.0F, 64.0F), 10.0F);
   public static final RingExplosionParticleOptions SOUL = fromIntColor(new Vector3f(96.0F, 245.0F, 250.0F), new Vector3f(131.0F, 140.0F, 141.0F), 3.0F);
   public static final MapCodec<RingExplosionParticleOptions> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(RingExplosionParticleOptions::fromColor),
            ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(RingExplosionParticleOptions::toColor),
            Codec.FLOAT.fieldOf("scale").forGetter(RingExplosionParticleOptions::scale)
         )
         .apply(instance, RingExplosionParticleOptions::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, RingExplosionParticleOptions> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

   public static RingExplosionParticleOptions fromIntColor(Vector3f fromColor, Vector3f toColor, float scale) {
      return new RingExplosionParticleOptions(new Vector3f(fromColor).div(255.0F), new Vector3f(toColor).div(255.0F), scale);
   }

   public ParticleType<RingExplosionParticleOptions> getType() {
      return ESParticles.RING_EXPLOSION.get();
   }
}
