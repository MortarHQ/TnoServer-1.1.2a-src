package com.tensura_tno.world.spawn;

import com.mojang.logging.LogUtils;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.config.ModConfigEvent.Loading;
import net.neoforged.fml.event.config.ModConfigEvent.Reloading;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import org.slf4j.Logger;

public final class BiomeBlacklistConfig {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final String LOG_PREFIX = "[TensuraTNO][BiomeBlacklistConfig]";
   public static final boolean DEFAULT_ENABLED = true;
   public static final List<String> DEFAULT_BLACKLIST = List.of("tensura:ancient_forest", "tensura:desert_of_death");
   public static final int DEFAULT_SEARCH_RADIUS = 1024;
   public static final int MIN_SEARCH_RADIUS = 64;
   public static final int MAX_SEARCH_RADIUS = 8192;
   public static final int DEFAULT_SEARCH_STEP = 64;
   public static final int MIN_SEARCH_STEP = 16;
   public static final int MAX_SEARCH_STEP = 512;
   public static final ModConfigSpec COMMON_SPEC;
   private static final BooleanValue ENABLED;
   private static final ConfigValue<List<? extends String>> BLACKLIST;
   private static final IntValue SEARCH_RADIUS;
   private static final IntValue SEARCH_STEP;
   private static volatile Set<ResourceLocation> cachedBlacklist = Set.of();

   private BiomeBlacklistConfig() {
   }

   public static boolean isEnabled() {
      try {
         return (Boolean)ENABLED.get();
      } catch (IllegalStateException ignored) {
         return true;
      }
   }

   public static Set<ResourceLocation> getBlacklist() {
      return cachedBlacklist;
   }

   public static int getSearchRadius() {
      try {
         return (Integer)SEARCH_RADIUS.get();
      } catch (IllegalStateException ignored) {
         return 1024;
      }
   }

   public static int getSearchStep() {
      try {
         return (Integer)SEARCH_STEP.get();
      } catch (IllegalStateException ignored) {
         return 64;
      }
   }

   public static void onConfigLoad(ModConfigEvent event) {
      if (event != null && event.getConfig() != null && event.getConfig().getSpec() == COMMON_SPEC) {
         if (event instanceof Loading || event instanceof Reloading) {
            List<? extends String> raw;
            try {
               raw = (List<? extends String>)BLACKLIST.get();
            } catch (IllegalStateException ignored) {
               raw = DEFAULT_BLACKLIST;
            }

            Set<ResourceLocation> rebuilt = parseBlacklist(raw);
            cachedBlacklist = rebuilt;

            boolean enabled;
            try {
               enabled = (Boolean)ENABLED.get();
            } catch (IllegalStateException ignored) {
               enabled = true;
            }

            LOGGER.info("{} loaded; entries={}, enabled={}", new Object[]{"[TensuraTNO][BiomeBlacklistConfig]", rebuilt.size(), enabled});
         }
      }
   }

   private static Set<ResourceLocation> parseBlacklist(List<? extends String> raw) {
      if (raw != null && !raw.isEmpty()) {
         LinkedHashSet<ResourceLocation> out = new LinkedHashSet<>(raw.size());

         for (String entry : raw) {
            if (entry != null && !entry.isEmpty()) {
               ResourceLocation parsed = ResourceLocation.tryParse(entry);
               if (parsed == null) {
                  LOGGER.warn("{} invalid biome id in spawnBiomeBlacklist: {}", "[TensuraTNO][BiomeBlacklistConfig]", entry);
               } else {
                  out.add(parsed);
               }
            } else {
               LOGGER.warn("{} invalid biome id in spawnBiomeBlacklist: <empty>", "[TensuraTNO][BiomeBlacklistConfig]");
            }
         }

         return Set.copyOf(out);
      } else {
         return Set.of();
      }
   }

   private static boolean isStringEntry(Object obj) {
      return Objects.nonNull(obj) && obj instanceof String;
   }

   static {
      Builder builder = new Builder();
      builder.push("spawn");
      ENABLED = builder.comment(
            "Master toggle for the spawn-biome-blacklist feature.\nWhen false, the world initial spawn point and player first-login spawn are\nleft untouched regardless of the configured blacklist."
         )
         .define("spawnBiomeBlacklistEnabled", true);
      BLACKLIST = builder.comment(
            "Biomes that must NOT host the world's initial spawn point or a new player's\nfirst-login spawn position. Each entry is a ResourceLocation in 'namespace:path'\nform. Invalid entries are logged at WARN and skipped at runtime.\nDefault targets the Tensura overworld biomes that apply heavy magicule\npoisoning to unprotected new players."
         )
         .defineListAllowEmpty("spawnBiomeBlacklist", DEFAULT_BLACKLIST, () -> "tensura:ancient_forest", BiomeBlacklistConfig::isStringEntry);
      SEARCH_RADIUS = builder.comment(
            "Maximum horizontal radius (in blocks) that the safe-spawn finder will scan\noutward from the original spawn point when relocating.\nRange: [64, 8192]."
         )
         .defineInRange("spawnSafeSearchRadius", 1024, 64, 8192);
      SEARCH_STEP = builder.comment(
            "Sampling step (in blocks) used by the safe-spawn finder while spiral-scanning.\nSmaller values produce a denser grid (more accurate but slower); larger values\nproduce a coarser grid.\nRange: [16, 512]."
         )
         .defineInRange("spawnSafeSearchStep", 64, 16, 512);
      builder.pop();
      COMMON_SPEC = builder.build();
   }
}
