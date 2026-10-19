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

public record ExplosionShockParticleOptions(Vector3f fromColor, Vector3f toColor, float lengthScale, float width, float lifeScale) implements ParticleOptions {
   public static final ExplosionShockParticleOptions BLAST = fromIntColor(
      new Vector3f(255.0F, 255.0F, 255.0F), new Vector3f(255.0F, 255.0F, 255.0F), 0.3F, 0.06F, 0.5F
   );
   public static final ExplosionShockParticleOptions AETHERSENT = fromIntColor(
      new Vector3f(255.0F, 255.0F, 255.0F), new Vector3f(233.0F, 173.0F, 237.0F), 1.0F, 0.06F, 1.0F
   );
   public static final ExplosionShockParticleOptions CRESCENT_SPEAR = fromIntColor(
      new Vector3f(161.0F, 223.0F, 255.0F), new Vector3f(124.0F, 164.0F, 213.0F), 1.0F, 0.06F, 1.0F
   );
   public static final ExplosionShockParticleOptions FROZEN = fromIntColor(
      new Vector3f(121.0F, 178.0F, 209.0F), new Vector3f(192.0F, 251.0F, 255.0F), 1.0F, 0.06F, 1.0F
   );
   public static final ExplosionShockParticleOptions CRYSTAL = fromIntColor(
      new Vector3f(196.0F, 80.0F, 132.0F), new Vector3f(126.0F, 197.0F, 203.0F), 1.0F, 0.06F, 1.0F
   );
   public static final ExplosionShockParticleOptions FLARE = fromIntColor(
      new Vector3f(222.0F, 112.0F, 255.0F), new Vector3f(255.0F, 255.0F, 116.0F), 1.0F, 0.06F, 1.0F
   );
   public static final ExplosionShockParticleOptions DEATH = fromIntColor(
      new Vector3f(220.0F, 53.0F, 69.0F), new Vector3f(237.0F, 38.0F, 85.0F), 1.0F, 0.06F, 1.0F
   );
   public static final ExplosionShockParticleOptions ENERGY = fromIntColor(
      new Vector3f(255.0F, 255.0F, 255.0F), new Vector3f(129.0F, 212.0F, 250.0F), 0.5F, 0.1F, 0.6F
   );
   public static final ExplosionShockParticleOptions ENERGY_SMALL = fromIntColor(
      new Vector3f(255.0F, 255.0F, 255.0F), new Vector3f(129.0F, 212.0F, 250.0F), 0.07F, 0.02F, 0.6F
   );
   public static final ExplosionShockParticleOptions ETHER = fromIntColor(
      new Vector3f(209.0F, 255.0F, 225.0F), new Vector3f(255.0F, 255.0F, 255.0F), 1.0F, 0.06F, 1.0F
   );
   public static final MapCodec<ExplosionShockParticleOptions> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(ExplosionShockParticleOptions::fromColor),
            ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(ExplosionShockParticleOptions::toColor),
            Codec.FLOAT.fieldOf("length_scale").forGetter(ExplosionShockParticleOptions::lengthScale),
            Codec.FLOAT.fieldOf("width").forGetter(ExplosionShockParticleOptions::width),
            Codec.FLOAT.fieldOf("life_scale").forGetter(ExplosionShockParticleOptions::lifeScale)
         )
         .apply(instance, ExplosionShockParticleOptions::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, ExplosionShockParticleOptions> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

   public static ExplosionShockParticleOptions fromIntColor(Vector3f fromColor, Vector3f toColor, float lengthScale, float width, float lifeScale) {
      return new ExplosionShockParticleOptions(new Vector3f(fromColor).div(255.0F), new Vector3f(toColor).div(255.0F), lengthScale, width, lifeScale);
   }

   public ParticleType<ExplosionShockParticleOptions> getType() {
      return ESParticles.EXPLOSION_SHOCK.get();
   }
}
