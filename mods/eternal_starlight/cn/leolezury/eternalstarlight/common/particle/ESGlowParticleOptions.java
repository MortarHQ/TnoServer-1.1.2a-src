package cn.leolezury.eternalstarlight.common.particle;

import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;

public record ESGlowParticleOptions(Vector3f fromColor, Vector3f toColor, float alpha, float lifeScale) implements ParticleOptions {
   private static final List<Vector3f> SEEK_COLORS = List.of(
      new Vector3f(49.0F, 177.0F, 204.0F),
      new Vector3f(89.0F, 47.0F, 108.0F),
      new Vector3f(22.0F, 7.0F, 78.0F),
      new Vector3f(209.0F, 107.0F, 187.0F),
      new Vector3f(107.0F, 194.0F, 209.0F),
      new Vector3f(67.0F, 113.0F, 145.0F),
      new Vector3f(107.0F, 101.0F, 155.0F)
   );
   public static final MapCodec<ESGlowParticleOptions> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(ESGlowParticleOptions::fromColor),
            ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(ESGlowParticleOptions::toColor),
            Codec.FLOAT.fieldOf("alpha").forGetter(ESGlowParticleOptions::alpha),
            Codec.FLOAT.fieldOf("life_scale").forGetter(ESGlowParticleOptions::lifeScale)
         )
         .apply(instance, ESGlowParticleOptions::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, ESGlowParticleOptions> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

   public static ESGlowParticleOptions fromIntColor(Vector3f fromColor, Vector3f toColor, float alpha, float lifeScale) {
      return new ESGlowParticleOptions(new Vector3f(fromColor).div(255.0F), new Vector3f(toColor).div(255.0F), alpha, lifeScale);
   }

   public static ESGlowParticleOptions getSeek(RandomSource random, boolean lowTransparency, boolean extendedLife) {
      return fromIntColor(
         SEEK_COLORS.get(random.nextInt(SEEK_COLORS.size())),
         SEEK_COLORS.get(random.nextInt(SEEK_COLORS.size())),
         lowTransparency ? 1.0F : 0.5F + (random.nextFloat() - 0.5F) * 0.2F,
         extendedLife ? 1.5F : 0.4F
      );
   }

   public ParticleType<ESGlowParticleOptions> getType() {
      return ESParticles.GLOW.get();
   }
}
