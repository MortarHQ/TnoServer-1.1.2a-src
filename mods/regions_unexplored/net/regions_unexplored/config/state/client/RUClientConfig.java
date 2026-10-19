package net.regions_unexplored.config.state.client;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.regions_unexplored.config.json5.CommentedMapCodec;

public class RUClientConfig {
   public static final RUClientConfig DEFAULT = new RUClientConfig();
   public static final Codec<RUClientConfig> CODEC = RecordCodecBuilder.create(
      i -> i.group(
            CommentedMapCodec.commented(
                  RUClientConfig.ParticleRates.CODEC, "particle_rates", "Controls the spawn rate of various particles. Default value for all is 1."
               )
               .forGetter(c -> c.particleRates),
            CommentedMapCodec.commented(RUClientConfig.EucalyptusColors.CODEC, "eucalyptus_colors", "Controls the colors of the rainbow Eucalyptus logs")
               .forGetter(c -> c.eucalyptusColors)
         )
         .apply(i, RUClientConfig::new)
   );
   public RUClientConfig.ParticleRates particleRates = new RUClientConfig.ParticleRates();
   public RUClientConfig.EucalyptusColors eucalyptusColors = new RUClientConfig.EucalyptusColors();

   private RUClientConfig() {
   }

   public RUClientConfig(RUClientConfig.ParticleRates particleRates, RUClientConfig.EucalyptusColors eucalyptusColors) {
      this.particleRates = particleRates;
      this.eucalyptusColors = eucalyptusColors;
   }

   public static class EucalyptusColors {
      public static final Codec<RUClientConfig.EucalyptusColors> CODEC = RecordCodecBuilder.create(
         i -> i.group(
               Codec.doubleRange(0.0, 200.0).fieldOf("transition_size").orElse(25.0).forGetter(c -> c.transitionSize),
               Codec.doubleRange(0.0, 1.0).fieldOf("saturation").orElse(0.5).forGetter(c -> c.saturation),
               Codec.doubleRange(0.0, 1.0).fieldOf("brightness").orElse(0.8).forGetter(c -> c.brightness)
            )
            .apply(i, RUClientConfig.EucalyptusColors::new)
      );
      public double transitionSize = 25.0;
      public double saturation = 0.5;
      public double brightness = 0.8;

      public EucalyptusColors() {
      }

      public EucalyptusColors(double transitionSize, double saturation, double brightness) {
         this.transitionSize = transitionSize;
         this.saturation = saturation;
         this.brightness = brightness;
      }
   }

   public static class ParticleRates {
      public static final Codec<RUClientConfig.ParticleRates> CODEC = RecordCodecBuilder.create(
         i -> i.group(
               Codec.doubleRange(0.0, 5.0).fieldOf("leaves").orElse(1.0).forGetter(r -> r.leaves),
               Codec.doubleRange(0.0, 5.0).fieldOf("prismarite").orElse(1.0).forGetter(r -> r.prismarite)
            )
            .apply(i, RUClientConfig.ParticleRates::new)
      );
      public double leaves = 1.0;
      public double prismarite = 1.0;

      public ParticleRates() {
      }

      public ParticleRates(double leaves, double prismarite) {
         this.leaves = leaves;
         this.prismarite = prismarite;
      }
   }
}
