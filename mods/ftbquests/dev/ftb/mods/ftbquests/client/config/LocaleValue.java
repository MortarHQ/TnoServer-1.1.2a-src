package dev.ftb.mods.ftbquests.client.config;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import dev.ftb.mods.ftblibrary.snbt.config.StringValue;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class LocaleValue extends StringValue {
   public LocaleValue(SNBTConfig config, String value, String def) {
      super(config, value, def);
   }

   @OnlyIn(Dist.CLIENT)
   public void createClientConfig(ConfigGroup group) {
      group.add(this.key, new LocaleConfig(), (String)this.get(), this::set, (String)this.defaultValue);
   }
}
