package it.hurts.sskirillss.relics.config;

import it.hurts.octostudios.octolib.module.config.annotation.Prop;
import it.hurts.octostudios.octolib.module.config.impl.OctoConfig;

public class RelicsConfigData implements OctoConfig {
   @Prop(
      comment = "Toggles advanced configuration files, allowing customization of most of the mod's functionality. May contain WIP content that may change in the future.\n\nActivating this feature may lead to unintended consequences, so use it only if you know what you're doing. If any part of the mod update involves changes to the configuration file values, these changes will not be applied automatically. You will need to manually update the necessary sections or reset them to their original state.\n"
   )
   private boolean enabledExtendedConfigs = false;

   public boolean isEnabledExtendedConfigs() {
      return this.enabledExtendedConfigs;
   }

   public void setEnabledExtendedConfigs(boolean enabledExtendedConfigs) {
      this.enabledExtendedConfigs = enabledExtendedConfigs;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RelicsConfigData other)) {
         return false;
      } else {
         return !other.canEqual(this) ? false : this.isEnabledExtendedConfigs() == other.isEnabledExtendedConfigs();
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof RelicsConfigData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      return result * 59 + (this.isEnabledExtendedConfigs() ? 79 : 97);
   }

   @Override
   public String toString() {
      return "RelicsConfigData(enabledExtendedConfigs=" + this.isEnabledExtendedConfigs() + ")";
   }
}
