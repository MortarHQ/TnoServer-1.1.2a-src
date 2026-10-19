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

public record ESSmokeParticleOptions(Vector3f fromColor, Vector3f toColor, float alpha, float lifeScale, float motionScale, boolean rise)
   implements ParticleOptions {
   public static final ESSmokeParticleOptions FLAME = fromIntColor(
      new Vector3f(255.0F, 147.0F, 25.0F), new Vector3f(49.0F, 10.0F, 2.0F), 1.0F, 1.0F, 1.0F, true
   );
   public static final ESSmokeParticleOptions AETHERSENT = fromIntColor(
      new Vector3f(115.0F, 51.0F, 153.0F), new Vector3f(46.0F, 14.0F, 64.0F), 1.0F, 3.0F, 2.5F, false
   );
   public static final ESSmokeParticleOptions LUNAR = fromIntColor(
      new Vector3f(66.0F, 66.0F, 115.0F), new Vector3f(32.0F, 32.0F, 64.0F), 1.0F, 3.0F, 0.0F, false
   );
   public static final ESSmokeParticleOptions LUNAR_SHORT = fromIntColor(
      new Vector3f(66.0F, 66.0F, 115.0F), new Vector3f(32.0F, 32.0F, 64.0F), 1.0F, 0.8F, 0.0F, false
   );
   public static final ESSmokeParticleOptions LUNAR_BREATH = fromIntColor(
      new Vector3f(66.0F, 66.0F, 115.0F), new Vector3f(32.0F, 32.0F, 64.0F), 1.0F, 0.6F, 4.0F, false
   );
   public static final ESSmokeParticleOptions LUNAR_ATTACK = fromIntColor(
      new Vector3f(66.0F, 66.0F, 115.0F), new Vector3f(32.0F, 32.0F, 64.0F), 0.5F, 0.6F, 0.4F, false
   );
   public static final ESSmokeParticleOptions PUNGENCY_FRUIT = fromIntColor(
      new Vector3f(87.0F, 58.0F, 69.0F), new Vector3f(179.0F, 116.0F, 116.0F), 1.0F, 1.0F, 0.3F, true
   );
   public static final MapCodec<ESSmokeParticleOptions> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(ESSmokeParticleOptions::fromColor),
            ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(ESSmokeParticleOptions::toColor),
            Codec.FLOAT.fieldOf("alpha").forGetter(ESSmokeParticleOptions::alpha),
            Codec.FLOAT.fieldOf("life_scale").forGetter(ESSmokeParticleOptions::lifeScale),
            Codec.FLOAT.fieldOf("motion_scale").forGetter(ESSmokeParticleOptions::motionScale),
            Codec.BOOL.fieldOf("rise").forGetter(ESSmokeParticleOptions::rise)
         )
         .apply(instance, ESSmokeParticleOptions::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, ESSmokeParticleOptions> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

   public static ESSmokeParticleOptions fromIntColor(Vector3f fromColor, Vector3f toColor, float alpha, float lifeScale, float motionScale, boolean rise) {
      return new ESSmokeParticleOptions(new Vector3f(fromColor).div(255.0F), new Vector3f(toColor).div(255.0F), alpha, lifeScale, motionScale, rise);
   }

   public ParticleType<ESSmokeParticleOptions> getType() {
      return ESParticles.SMOKE.get();
   }
}
