package team.creative.creativecore.common.config.premade;

import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.api.ICreativeConfig;

public class ToggleableConfig<T> implements ICreativeConfig {
   public final T value;
   private boolean enabled;

   public ToggleableConfig(T value) {
      this.value = value;
   }

   public ToggleableConfig(T value, boolean enabled) {
      this.value = value;
      this.enabled = enabled;
   }

   @Override
   public void configured(Side side) {
      if (this.value instanceof ICreativeConfig config) {
         config.configured(side);
      }
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof ToggleableConfig config) ? false : config.enabled == this.enabled && config.value.equals(this.value);
   }
}
