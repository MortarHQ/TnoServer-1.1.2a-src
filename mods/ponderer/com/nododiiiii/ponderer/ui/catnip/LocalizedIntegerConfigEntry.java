package com.nododiiiii.ponderer.ui.catnip;

import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigHelper;
import net.createmod.catnip.gui.UIRenderHelper;
import net.createmod.catnip.gui.widget.AbstractSimiWidget;
import net.createmod.catnip.theme.Color;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.ValueSpec;

public class LocalizedIntegerConfigEntry extends PlainTextListEntry {
   private final int textColor = ((Color)UIRenderHelper.COLOR_TEXT.getFirst()).getRGB();
   private final int failTextColor = ((Color)AbstractSimiWidget.COLOR_FAIL.getFirst()).getRGB();

   public LocalizedIntegerConfigEntry(String labelKey, @Nullable String hintKey, @Nullable String tooltipKey, ConfigValue<Integer> value, ValueSpec spec) {
      this(labelKey, hintKey, tooltipKey, value, spec, ConfigEntrySupport.metadataOf(value, spec));
   }

   private LocalizedIntegerConfigEntry(
      String labelKey, @Nullable String hintKey, @Nullable String tooltipKey, ConfigValue<Integer> value, ValueSpec spec, ConfigEntrySupport.Metadata metadata
   ) {
      super(labelKey, tooltipKey, hintKey, formatValue(ConfigEntrySupport.currentValue(metadata, value), metadata.annotations()), newValue -> {});
      this.path = metadata.path();
      this.annotations.putAll(metadata.annotations());
      if (metadata.unit() != null && !metadata.unit().isBlank()) {
         this.setUnitText(metadata::unit);
      }

      this.field().setTextColor(this.textColor);
      this.field().setResponder(rawValue -> this.applyValue(rawValue, value, spec, metadata));
   }

   private void applyValue(String rawValue, ConfigValue<Integer> value, ValueSpec spec, ConfigEntrySupport.Metadata metadata) {
      try {
         Integer parsed = parseInteger(rawValue);
         if (!spec.test(parsed)) {
            throw new IllegalArgumentException();
         }

         this.field().setTextColor(this.textColor);
         ConfigHelper.setValue(metadata.path(), value, parsed, metadata.annotations());
      } catch (IllegalArgumentException ignored) {
         this.field().setTextColor(this.failTextColor);
      }
   }

   private static String formatValue(Integer value, Map<String, String> annotations) {
      if (annotations.containsKey("IntDisplay")) {
         String intDisplay = annotations.get("IntDisplay");

         return switch (intDisplay) {
            case "#" -> "#" + Integer.toHexString(value).toUpperCase(Locale.ROOT);
            case "0x" -> "0x" + Integer.toHexString(value).toUpperCase(Locale.ROOT);
            case "0b" -> "0b" + Integer.toBinaryString(value);
            default -> String.valueOf(value);
         };
      } else {
         return String.valueOf(value);
      }
   }

   private static Integer parseInteger(String string) {
      if (string.startsWith("#")) {
         return Integer.parseUnsignedInt(string.substring(1), 16);
      } else if (string.startsWith("0x")) {
         return Integer.parseUnsignedInt(string.substring(2), 16);
      } else {
         return string.startsWith("0b") ? Integer.parseUnsignedInt(string.substring(2), 2) : Integer.parseInt(string);
      }
   }
}
