package it.hurts.octostudios.reliquified_twilight_forest.config;

import it.hurts.octostudios.octolib.module.config.annotation.Prop;
import it.hurts.octostudios.octolib.module.config.impl.OctoConfig;

public class RTFConfig implements OctoConfig {
   @Prop(
      comment = "Temporarily disabled due to lag caused by mechanisms that frequently place other blocks. Enable it only if you're sure such mechanisms aren't present in your modpack or won't appear in your field of view too often."
   )
   private boolean enabledOreScanner = false;

   public boolean isEnabledOreScanner() {
      return this.enabledOreScanner;
   }

   public void setEnabledOreScanner(boolean enabledOreScanner) {
      this.enabledOreScanner = enabledOreScanner;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RTFConfig other)) {
         return false;
      } else {
         return !other.canEqual(this) ? false : this.isEnabledOreScanner() == other.isEnabledOreScanner();
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof RTFConfig;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      return result * 59 + (this.isEnabledOreScanner() ? 79 : 97);
   }

   @Override
   public String toString() {
      return "RTFConfig(enabledOreScanner=" + this.isEnabledOreScanner() + ")";
   }
}
