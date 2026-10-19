package dev.ftb.mods.ftblibrary.snbt.config;

import dev.architectury.platform.Platform;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import java.nio.file.Path;
import net.minecraft.Util;
import net.minecraft.world.level.storage.LevelResource;

public interface ConfigUtil {
   Path ROOT_DIR = Platform.getGameFolder();
   Path DEFAULT_CONFIG_DIR = ROOT_DIR.resolve("defaultconfigs");
   Path CONFIG_DIR = Platform.getConfigFolder();
   Path LOCAL_DIR = ROOT_DIR.resolve("local");
   LevelResource SERVER_CONFIG_DIR = new LevelResource("serverconfig");

   static ConfigGroup makeConfigEditGroup(SNBTConfig config, String groupName, boolean isServerConfig) {
      return (ConfigGroup)Util.make(ConfigGroup.createEditable(config, groupName, isServerConfig), config::createClientConfig);
   }

   @Deprecated(forRemoval = true)
   static void loadDefaulted(SNBTConfig config, Path configDir, String namespace) {
      loadDefaulted(config, configDir, namespace, config.key + ".snbt");
   }

   @Deprecated(forRemoval = true)
   static void loadDefaulted(SNBTConfig config, Path configDir, String namespace, String filename) {
      Path configPath = configDir.resolve(filename).toAbsolutePath();
      Path defaultPath = DEFAULT_CONFIG_DIR.resolve(namespace).resolve(filename);
      config.load(
         configPath,
         defaultPath,
         () -> new String[]{
            "Default config file that will be copied to " + ROOT_DIR.relativize(configPath) + " if it doesn't exist!",
            "Just copy any values you wish to override in here!"
         }
      );
   }
}
