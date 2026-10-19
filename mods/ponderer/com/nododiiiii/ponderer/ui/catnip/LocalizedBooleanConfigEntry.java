package com.nododiiiii.ponderer.ui.catnip;

import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigHelper;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.ValueSpec;

public class LocalizedBooleanConfigEntry extends ToggleListEntry {
   public LocalizedBooleanConfigEntry(String labelKey, @Nullable String tooltipKey, ConfigValue<Boolean> value, ValueSpec spec) {
      this(labelKey, tooltipKey, value, ConfigEntrySupport.metadataOf(value, spec));
   }

   private LocalizedBooleanConfigEntry(String labelKey, @Nullable String tooltipKey, ConfigValue<Boolean> value, ConfigEntrySupport.Metadata metadata) {
      super(
         labelKey,
         tooltipKey,
         () -> ConfigEntrySupport.<Boolean>currentValue(metadata, value),
         () -> ConfigHelper.setValue(metadata.path(), value, !ConfigEntrySupport.<Boolean>currentValue(metadata, value), metadata.annotations())
      );
      this.path = metadata.path();
      this.annotations.putAll(metadata.annotations());
   }
}
