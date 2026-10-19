package com.chronodawn.worldgen.runtime;

import com.chronodawn.config.ChronoDawnConfig;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OverlayPackBootstrap {
   private static final Logger LOGGER = LoggerFactory.getLogger(OverlayPackBootstrap.class);
   public static final String OVERLAY_DIR_NAME = "chronodawn-runtime-overlay";
   public static final String PACK_ID = "chronodawn-runtime-overlay";
   private static final byte[] PACK_MCMETA_BYTES = "{\n  \"pack\": {\n    \"description\": \"ChronoDawn runtime config overlay\",\n    \"pack_format\": 41\n  }\n}\n"
      .getBytes(StandardCharsets.UTF_8);
   private static volatile Path overlayPath;

   private OverlayPackBootstrap() {
   }

   public static Path getOverlayPath() {
      return overlayPath;
   }

   public static Path writeOverlay(Path configDir, ChronoDawnConfig config) {
      Path overlayRoot = configDir.resolve("chronodawn-runtime-overlay");

      try {
         Files.createDirectories(overlayRoot);
         Files.write(overlayRoot.resolve("pack.mcmeta"), PACK_MCMETA_BYTES);
         Map<String, byte[]> overlay = new LinkedHashMap<>();
         overlay.putAll(RuntimeStructureOverlay.generate(config));
         overlay.putAll(RuntimePlacedFeatureOverlay.generate(config));

         for (Entry<String, byte[]> entry : overlay.entrySet()) {
            Path target = overlayRoot.resolve(entry.getKey());
            Files.createDirectories(target.getParent());
            Files.write(target, entry.getValue());
         }

         LOGGER.info("Wrote runtime config overlay datapack to {}", overlayRoot);
         overlayPath = overlayRoot;
      } catch (IOException e) {
         LOGGER.error("Failed to write runtime config overlay to {}; config-driven worldgen will be inactive", overlayRoot, e);
      }

      return overlayRoot;
   }
}
