package cn.star.rhenium;

import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public final class Config {
   private static final Builder BUILDER = new Builder();
   private static final BooleanValue LIMIT_TEMPLATE_CACHE = BUILDER.comment(
         "Evict cached StructureTemplates after the total cache grows beyond the configured limit."
      )
      .define("limitTemplateCache", true);
   private static final IntValue TEMPLATE_CACHE_SIZE = BUILDER.comment(
         "Maximum number of StructureTemplates kept in StructureTemplateManager across all namespaces."
      )
      .defineInRange("templateCacheSize", 128, 16, 1024);
   static final ModConfigSpec SPEC = BUILDER.build();
   public static boolean limitTemplateCache;
   public static int templateCacheSize;

   private Config() {
   }

   static void onLoad(ModConfigEvent event) {
      limitTemplateCache = (Boolean)LIMIT_TEMPLATE_CACHE.get();
      templateCacheSize = (Integer)TEMPLATE_CACHE_SIZE.get();
   }
}
