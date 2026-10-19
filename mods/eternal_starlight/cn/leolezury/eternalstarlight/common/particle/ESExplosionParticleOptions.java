package cn.leolezury.eternalstarlight.common.particle;

import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

public record ESExplosionParticleOptions(ParticleType<ESExplosionParticleOptions> type, Vector3f fromColor, Vector3f toColor, float lifeScale)
   implements ParticleOptions {
   public static final ESExplosionParticleOptions BLAST = fromIntColor(
      ESParticles.BLAST.get(), new Vector3f(255.0F, 255.0F, 255.0F), new Vector3f(255.0F, 255.0F, 255.0F), 0.5F
   );
   public static final ESExplosionParticleOptions ENERGY = fromIntColor(
      ESParticles.EXPLOSION.get(), new Vector3f(255.0F, 255.0F, 255.0F), new Vector3f(129.0F, 212.0F, 250.0F), 1.0F
   );
   public static final ESExplosionParticleOptions ENERGY_BLAST = fromIntColor(
      ESParticles.BLAST.get(), new Vector3f(255.0F, 255.0F, 255.0F), new Vector3f(129.0F, 212.0F, 250.0F), 0.5F
   );
   public static final ESExplosionParticleOptions LAVA = fromIntColor(
      ESParticles.EXPLOSION.get(), new Vector3f(217.0F, 168.0F, 74.0F), new Vector3f(174.0F, 76.0F, 18.0F), 1.0F
   );
   public static final ESExplosionParticleOptions AETHERSENT = fromIntColor(
      ESParticles.EXPLOSION.get(), new Vector3f(255.0F, 255.0F, 255.0F), new Vector3f(233.0F, 173.0F, 237.0F), 1.0F
   );
   public static final ESExplosionParticleOptions LUNAR = fromIntColor(
      ESParticles.EXPLOSION.get(), new Vector3f(66.0F, 66.0F, 115.0F), new Vector3f(32.0F, 32.0F, 64.0F), 1.0F
   );
   public static final ESExplosionParticleOptions FROZEN = fromIntColor(
      ESParticles.EXPLOSION.get(), new Vector3f(121.0F, 178.0F, 209.0F), new Vector3f(192.0F, 251.0F, 255.0F), 1.0F
   );
   public static final ESExplosionParticleOptions FROZEN_BLAST = fromIntColor(
      ESParticles.BLAST.get(), new Vector3f(121.0F, 178.0F, 209.0F), new Vector3f(192.0F, 251.0F, 255.0F), 0.5F
   );

   public static ESExplosionParticleOptions fromIntColor(ParticleType<ESExplosionParticleOptions> type, Vector3f fromColor, Vector3f toColor, float lifeScale) {
      return new ESExplosionParticleOptions(type, new Vector3f(fromColor).div(255.0F), new Vector3f(toColor).div(255.0F), lifeScale);
   }

   public static MapCodec<ESExplosionParticleOptions> codec(ParticleType<ESExplosionParticleOptions> type) {
      return RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(ESExplosionParticleOptions::fromColor),
               ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(ESExplosionParticleOptions::toColor),
               Codec.FLOAT.fieldOf("life_scale").forGetter(ESExplosionParticleOptions::lifeScale)
            )
            .apply(instance, (from, to, lifeScale) -> new ESExplosionParticleOptions(type, from, to, lifeScale))
      );
   }

   public static StreamCodec<? super ByteBuf, ESExplosionParticleOptions> streamCodec(ParticleType<ESExplosionParticleOptions> type) {
      return StreamCodec.composite(
         ByteBufCodecs.VECTOR3F,
         ESExplosionParticleOptions::fromColor,
         ByteBufCodecs.VECTOR3F,
         ESExplosionParticleOptions::toColor,
         ByteBufCodecs.FLOAT,
         ESExplosionParticleOptions::lifeScale,
         (from, to, lifeScale) -> new ESExplosionParticleOptions(type, from, to, lifeScale)
      );
   }

   public ParticleType<ESExplosionParticleOptions> getType() {
      return this.type();
   }
}
