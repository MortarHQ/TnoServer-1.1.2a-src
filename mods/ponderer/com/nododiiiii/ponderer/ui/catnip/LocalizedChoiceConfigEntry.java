package com.nododiiiii.ponderer.ui.catnip;

import com.nododiiiii.ponderer.ui.UIText;
import java.util.List;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigHelper;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.ValueSpec;

public class LocalizedChoiceConfigEntry extends ButtonListEntry {
   public LocalizedChoiceConfigEntry(
      String labelKey,
      @Nullable String tooltipKey,
      int buttonWidth,
      ConfigValue<String> value,
      ValueSpec spec,
      List<String> optionLabelKeys,
      List<String> optionValues
   ) {
      this(labelKey, tooltipKey, buttonWidth, value, optionLabelKeys, optionValues, ConfigEntrySupport.metadataOf(value, spec));
   }

   private LocalizedChoiceConfigEntry(
      String labelKey,
      @Nullable String tooltipKey,
      int buttonWidth,
      ConfigValue<String> value,
      List<String> optionLabelKeys,
      List<String> optionValues,
      ConfigEntrySupport.Metadata metadata
   ) {
      super(
         labelKey,
         tooltipKey,
         buttonWidth,
         () -> cycleValue(value, optionValues, metadata),
         () -> currentLabel(value, optionLabelKeys, optionValues, metadata),
         () -> 16777215,
         (String)null
      );
      this.path = metadata.path();
      this.annotations.putAll(metadata.annotations());
      this.setHalfWidthControl(buttonWidth);
   }

   private static void cycleValue(ConfigValue<String> value, List<String> optionValues, ConfigEntrySupport.Metadata metadata) {
      String current = ConfigEntrySupport.currentValue(metadata, value);
      int currentIndex = optionValues.indexOf(current);
      int nextIndex = (Math.max(currentIndex, 0) + 1) % optionValues.size();
      ConfigHelper.setValue(metadata.path(), value, optionValues.get(nextIndex), metadata.annotations());
   }

   private static String currentLabel(ConfigValue<String> value, List<String> optionLabelKeys, List<String> optionValues, ConfigEntrySupport.Metadata metadata) {
      String current = ConfigEntrySupport.currentValue(metadata, value);
      int index = optionValues.indexOf(current);
      return index >= 0 && index < optionLabelKeys.size() ? UIText.of(optionLabelKeys.get(index)) : current;
   }
}
