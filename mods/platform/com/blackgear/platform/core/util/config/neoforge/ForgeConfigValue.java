package com.blackgear.platform.core.util.config.neoforge;

import com.blackgear.platform.core.util.config.ConfigBuilder;
import java.util.List;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class ForgeConfigValue<T> implements ConfigBuilder.ConfigValue<T> {
   private final ConfigValue<T> value;

   public ForgeConfigValue(ConfigValue<T> value) {
      this.value = value;
   }

   @Override
   public List<String> getPath() {
      return this.value.getPath();
   }

   @Override
   public T get() {
      return (T)this.value.get();
   }

   @Override
   public ConfigBuilder next() {
      return new ForgeConfigBuilder(this.value.next());
   }

   @Override
   public void save() {
      this.value.save();
   }

   @Override
   public void set(T value) {
      this.value.set(value);
   }

   @Override
   public void clearCache() {
      this.value.clearCache();
   }
}
