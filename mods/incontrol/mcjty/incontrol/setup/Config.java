package mcjty.incontrol.setup;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class Config {
   public static IntValue CACHE_RETENTION_TICKS;
   public static IntValue PERPLAYER_RADIUS;

   public static void register(ModContainer container) {
      Builder builder = new Builder();
      builder.comment("General settings").push("general");
      CACHE_RETENTION_TICKS = builder.comment(
            "How many ticks that the count cache is kept alive. This is used to avoid recalculating the cache every tick. Use 0 to recalculate every tick."
         )
         .defineInRange("cacheRetentionTicks", 10, 0, 10000);
      PERPLAYER_RADIUS = builder.comment("The radius that is used for player specific conditions (to find the nearest player)")
         .defineInRange("perPlayerRadius", 100, 1, 10000);
      builder.pop();
      container.registerConfig(Type.SERVER, builder.build());
   }
}
