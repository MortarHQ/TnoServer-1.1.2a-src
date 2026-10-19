package team.lodestar.lodestone.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import org.apache.commons.lang3.tuple.Pair;
import team.lodestar.lodestone.systems.config.LodestoneConfig;

public class ClientConfig extends LodestoneConfig {
   public static LodestoneConfig.ConfigValueHolder<Boolean> DELAYED_PARTICLE_RENDERING = new LodestoneConfig.ConfigValueHolder<>(
      "lodestone",
      "client/graphics/particle",
      builder -> builder.comment(
            "Should particles render on the delayed buffer? This means they will properly render after clouds & water do, but could cause issues with mods like sodium."
         )
         .define("buffer_particles", true)
   );
   public static LodestoneConfig.ConfigValueHolder<Boolean> ENABLE_SCREEN_PARTICLES = new LodestoneConfig.ConfigValueHolder<>(
      "lodestone", "client/graphics/particle", builder -> builder.comment("Should screen particles be visible?").define("screen_particles", true)
   );
   public static LodestoneConfig.ConfigValueHolder<Double> FIRE_OVERLAY_OFFSET = new LodestoneConfig.ConfigValueHolder<>(
      "lodestone",
      "client/graphics/fire",
      builder -> builder.comment(
            "Downwards offset of Minecraft's first-person fire overlay. Higher numbers cause it to visually display lower and free up more screen space."
         )
         .defineInRange("fire_overlay_offset", 0.0, 0.0, 1.0)
   );
   public static LodestoneConfig.ConfigValueHolder<Double> SCREENSHAKE_INTENSITY = new LodestoneConfig.ConfigValueHolder<>(
      "lodestone",
      "client/screenshake",
      builder -> builder.comment("Intensity of screenshake. Higher numbers increase amplitude. Disable to turn off screenshake.")
         .defineInRange("screenshake_intensity", 1.0, 0.0, 5.0)
   );
   public static LodestoneConfig.ConfigValueHolder<Double> LOD_DISTANCE_MULTIPLIER = new LodestoneConfig.ConfigValueHolder<>(
      "lodestone",
      "client/model",
      builder -> builder.comment(
            "Multiplier for the distance at which model levels of detail are rendered. Higher numbers increase the distance at which detailed models are rendered."
         )
         .defineInRange("lod_distance_multiplier", 1.0, 0.1, 10.0)
   );
   public static final ClientConfig INSTANCE;
   public static final ModConfigSpec SPEC;

   public ClientConfig(Builder builder) {
      super("lodestone", "client", builder);
   }

   static {
      Pair<ClientConfig, ModConfigSpec> specPair = new Builder().configure(ClientConfig::new);
      SPEC = (ModConfigSpec)specPair.getRight();
      INSTANCE = (ClientConfig)specPair.getLeft();
   }
}
