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

public record GatheringTrailParticleOptions(
   ParticleType<GatheringTrailParticleOptions> type, float trailWidth, float trailLength, float speedScale, float rotSpeedScale
) implements ParticleOptions {
   public static final GatheringTrailParticleOptions ENERGY = new GatheringTrailParticleOptions(ESParticles.GATHERING_ENERGY.get(), 0.125F, 5.0F, 1.25F, 0.25F);
   public static final GatheringTrailParticleOptions SOUL = new GatheringTrailParticleOptions(ESParticles.GATHERING_SOUL.get(), 0.125F, 3.0F, 1.25F, 1.25F);
   public static final GatheringTrailParticleOptions SOUL_THIN = new GatheringTrailParticleOptions(ESParticles.GATHERING_SOUL.get(), 0.06F, 2.0F, 1.5F, 0.35F);
   public static final GatheringTrailParticleOptions FLARE = new GatheringTrailParticleOptions(ESParticles.GATHERING_FLARE.get(), 0.125F, 2.0F, 1.0F, 1.0F);

   public static MapCodec<GatheringTrailParticleOptions> codec(ParticleType<GatheringTrailParticleOptions> type) {
      return RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Codec.FLOAT.fieldOf("trail_width").forGetter(GatheringTrailParticleOptions::trailWidth),
               Codec.FLOAT.fieldOf("trail_length").forGetter(GatheringTrailParticleOptions::trailLength),
               Codec.FLOAT.fieldOf("speed_scale").forGetter(GatheringTrailParticleOptions::speedScale),
               Codec.FLOAT.fieldOf("rot_speed_scale").forGetter(GatheringTrailParticleOptions::rotSpeedScale)
            )
            .apply(
               instance,
               (trailWidth, trailLength, speedScale, rotSpeedScale) -> new GatheringTrailParticleOptions(
                  type, trailWidth, trailLength, speedScale, rotSpeedScale
               )
            )
      );
   }

   public static StreamCodec<? super ByteBuf, GatheringTrailParticleOptions> streamCodec(ParticleType<GatheringTrailParticleOptions> type) {
      return StreamCodec.composite(
         ByteBufCodecs.FLOAT,
         GatheringTrailParticleOptions::trailWidth,
         ByteBufCodecs.FLOAT,
         GatheringTrailParticleOptions::trailLength,
         ByteBufCodecs.FLOAT,
         GatheringTrailParticleOptions::speedScale,
         ByteBufCodecs.FLOAT,
         GatheringTrailParticleOptions::rotSpeedScale,
         (trailWidth, trailLength, speedScale, rotSpeedScale) -> new GatheringTrailParticleOptions(type, trailWidth, trailLength, speedScale, rotSpeedScale)
      );
   }

   public ParticleType<GatheringTrailParticleOptions> getType() {
      return this.type();
   }
}
