package com.nododiiiii.ponderer.ui.catnip;

import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigHelper;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.ValueSpec;

public class LocalizedStringConfigEntry extends PlainTextListEntry {
   public LocalizedStringConfigEntry(String labelKey, @Nullable String hintKey, @Nullable String tooltipKey, ConfigValue<String> value, ValueSpec spec) {
      this(labelKey, hintKey, tooltipKey, value, ConfigEntrySupport.metadataOf(value, spec));
   }

   private LocalizedStringConfigEntry(
      String labelKey, @Nullable String hintKey, @Nullable String tooltipKey, ConfigValue<String> value, ConfigEntrySupport.Metadata metadata
   ) {
      super(labelKey, tooltipKey, hintKey, ConfigEntrySupport.currentValue(metadata, value), newValue -> {});
      this.path = metadata.path();
      this.annotations.putAll(metadata.annotations());
      this.field().setResponder(newValue -> ConfigHelper.setValue(metadata.path(), value, newValue, metadata.annotations()));
   }
}
