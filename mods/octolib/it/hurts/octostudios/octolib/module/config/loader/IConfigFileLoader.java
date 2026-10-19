package it.hurts.octostudios.octolib.module.config.loader;

import it.hurts.octostudios.octolib.module.config.cfgbuilder.ConfigEntry;
import it.hurts.octostudios.octolib.module.config.provider.ConfigProvider;

public interface IConfigFileLoader<U1, U2> {
   SolidConfigLoader<?> SOLID = new SolidConfigLoader();

   void saveToFiles(String var1, U1 var2, ConfigProvider var3);

   U2 loadFiles(String var1, ConfigEntry var2, ConfigProvider var3);
}
