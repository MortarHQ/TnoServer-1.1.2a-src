package com.blackgear.platform.core.util.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.FileConfig;
import java.nio.file.Path;
import java.util.Locale;

public class ModConfig {
   private final ModConfig.Type type;
   private final IConfigSpec<?> spec;
   private final String fileName;
   private final String modId;
   private final ConfigFileTypeHandler configHandler = ConfigFileTypeHandler.TOML;
   private CommentedConfig configData;

   public ModConfig(ModConfig.Type type, IConfigSpec<?> spec, String modId, String fileName) {
      this.type = type;
      this.spec = spec;
      this.fileName = fileName;
      this.modId = modId;
      ConfigTracker.INSTANCE.trackConfig(this);
   }

   public ModConfig.Type getType() {
      return this.type;
   }

   public String getFileName() {
      return this.fileName;
   }

   public ConfigFileTypeHandler getHandler() {
      return this.configHandler;
   }

   public <T extends IConfigSpec<T>> IConfigSpec<T> getSpec() {
      return (IConfigSpec<T>)this.spec;
   }

   public String getModId() {
      return this.modId;
   }

   public CommentedConfig getConfigData() {
      return this.configData;
   }

   void setConfigData(CommentedConfig configData) {
      this.configData = configData;
      this.spec.setConfig(this.configData);
   }

   public void save() {
      if (this.configData instanceof FileConfig config) {
         config.save();
      }
   }

   public Path getFullPath() {
      return this.configData instanceof FileConfig config ? config.getNioPath() : null;
   }

   public enum Type {
      COMMON,
      CLIENT,
      SERVER;

      public String extension() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }
}
