package cc.sighs.oelib.config.model;

import net.minecraft.resources.ResourceLocation;

public final class ConfigMeta {
   private final ResourceLocation id;
   private final String fileName;
   private final ConfigStorageFormat format;
   private final ConfigSide side;
   private final String directory;

   private ConfigMeta(ConfigMeta.Builder builder) {
      this.id = builder.id;
      this.fileName = builder.fileName;
      this.format = builder.format;
      this.side = builder.side;
      this.directory = builder.directory;
   }

   public static ConfigMeta.Builder builder(ResourceLocation id) {
      return new ConfigMeta.Builder(id);
   }

   public ResourceLocation id() {
      return this.id;
   }

   public String fileName() {
      return this.fileName;
   }

   public ConfigStorageFormat format() {
      return this.format;
   }

   public ConfigSide side() {
      return this.side;
   }

   public String directory() {
      return this.directory;
   }

   public static final class Builder {
      private final ResourceLocation id;
      private String fileName;
      private ConfigStorageFormat format = ConfigStorageFormat.TOML;
      private ConfigSide side = ConfigSide.SERVER;
      private String directory;

      private Builder(ResourceLocation id) {
         this.id = id;
         this.fileName = id.getPath();
      }

      public ConfigMeta.Builder fileName(String fileName) {
         this.fileName = fileName;
         return this;
      }

      public ConfigMeta.Builder format(ConfigStorageFormat format) {
         this.format = format;
         return this;
      }

      public ConfigMeta.Builder side(ConfigSide side) {
         this.side = side;
         return this;
      }

      public ConfigMeta.Builder directory(String directory) {
         this.directory = directory;
         return this;
      }

      public ConfigMeta build() {
         return new ConfigMeta(this);
      }
   }
}
