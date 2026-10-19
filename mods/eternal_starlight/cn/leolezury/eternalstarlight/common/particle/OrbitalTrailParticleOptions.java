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
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector3f;

public record OrbitalTrailParticleOptions(Vector3f fromColor, Vector3f toColor, float radius, float rotSpeed, float alpha, int lifetime, int owner)
   implements ParticleOptions {
   public static final MapCodec<OrbitalTrailParticleOptions> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(OrbitalTrailParticleOptions::fromColor),
            ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(OrbitalTrailParticleOptions::toColor),
            Codec.FLOAT.fieldOf("radius").forGetter(OrbitalTrailParticleOptions::radius),
            Codec.FLOAT.fieldOf("rot_speed").forGetter(OrbitalTrailParticleOptions::rotSpeed),
            Codec.FLOAT.fieldOf("alpha").forGetter(OrbitalTrailParticleOptions::alpha),
            Codec.INT.fieldOf("lifetime").forGetter(OrbitalTrailParticleOptions::lifetime),
            Codec.INT.fieldOf("owner").forGetter(OrbitalTrailParticleOptions::owner)
         )
         .apply(instance, OrbitalTrailParticleOptions::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, OrbitalTrailParticleOptions> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

   public static OrbitalTrailParticleOptions fromIntColor(
      Vector3f fromColor, Vector3f toColor, float radius, float rotSpeed, float alpha, int lifetime, int owner
   ) {
      return new OrbitalTrailParticleOptions(new Vector3f(fromColor).div(255.0F), new Vector3f(toColor).div(255.0F), radius, rotSpeed, alpha, lifetime, owner);
   }

   public static OrbitalTrailParticleOptions magic(Player player) {
      RandomSource random = player.getRandom();
      return fromIntColor(
         new Vector3f(182.0F, 48.0F, 112.0F),
         new Vector3f(99.0F, 224.0F, 235.0F),
         0.8F + (random.nextFloat() - 0.5F) * 0.2F,
         (random.nextBoolean() ? -1 : 1) * (float)(15.0 + (random.nextFloat() - 0.5) * 7.0),
         0.9F + (random.nextFloat() - 0.5F) * 0.1F,
         (int)(75.0 + (random.nextFloat() - 0.5) * 10.0),
         player.getId()
      );
   }

   public ParticleType<OrbitalTrailParticleOptions> getType() {
      return ESParticles.ORBITAL_TRAIL.get();
   }
}
