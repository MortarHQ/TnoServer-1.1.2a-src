package cn.star.structblend;

import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public final class Config {
   private static final Builder BUILDER = new Builder();
   public static final ConfigValue<List<? extends String>> EXCLUDED_STRUCTURES = BUILDER.comment(
         "Structure registry IDs excluded from automatic terrain blending."
      )
      .defineListAllowEmpty("excludedStructures", List.of(), () -> "", value -> value instanceof String name && ResourceLocation.tryParse(name) != null);
   public static final ModConfigSpec SPEC = BUILDER.build();

   private Config() {
   }
}
