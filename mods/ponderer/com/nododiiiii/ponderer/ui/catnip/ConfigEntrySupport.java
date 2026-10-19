package com.nododiiiii.ponderer.ui.catnip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigHelper;
import net.createmod.catnip.data.Pair;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.ValueSpec;

final class ConfigEntrySupport {
   private ConfigEntrySupport() {
   }

   static ConfigEntrySupport.Metadata metadataOf(ConfigValue<?> value, ValueSpec spec) {
      String path = String.join(".", value.getPath());
      String unit = null;
      Map<String, String> annotations = new HashMap<>();
      String comment = spec.getComment();
      if (comment != null && !comment.isBlank()) {
         Pair<String, Map<String, String>> metadata = ConfigHelper.readMetadataFromComment(new ArrayList<>(Arrays.asList(comment.split("\n"))));
         unit = (String)metadata.getFirst();
         if (metadata.getSecond() != null) {
            annotations.putAll((Map<? extends String, ? extends String>)metadata.getSecond());
         }
      }

      return new ConfigEntrySupport.Metadata(path, unit, Map.copyOf(annotations));
   }

   static <T> T currentValue(ConfigEntrySupport.Metadata metadata, ConfigValue<T> value) {
      return (T)ConfigHelper.getValue(metadata.path(), value);
   }

   record Metadata(String path, @Nullable String unit, Map<String, String> annotations) {
   }
}
