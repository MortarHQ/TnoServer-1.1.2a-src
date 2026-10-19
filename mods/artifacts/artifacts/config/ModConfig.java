package artifacts.config;

import java.util.List;

public class ModConfig {
   public final ClientConfig client = new ClientConfig();
   public final GeneralConfig general = new GeneralConfig();
   public final ItemConfigs items = new ItemConfigs();
   public final List<ConfigManager> configs = List.of(this.general, this.client, this.items);

   public void setup() {
      this.configs.forEach(ConfigManager::setup);
   }
}
