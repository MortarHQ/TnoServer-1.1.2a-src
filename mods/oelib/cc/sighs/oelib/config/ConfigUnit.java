package cc.sighs.oelib.config;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.config.api.ConfigEvents;
import cc.sighs.oelib.config.datafix.ConfigFixRegistry;
import cc.sighs.oelib.config.model.ConfigMeta;
import cc.sighs.oelib.config.model.ConfigSide;
import cc.sighs.oelib.config.util.ConfigIOUtil;
import cc.sighs.oelib.config.util.ConfigSerializationUtil;
import cc.sighs.oelib.platform.Platform;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.resources.ResourceLocation;

public class ConfigUnit<T> {
   private final ConfigCodec<T> configCodec;
   private final T defaultValue;
   private final AtomicBoolean loaded = new AtomicBoolean();
   private volatile T currentValue;

   private ConfigUnit(ConfigCodec<T> configCodec, T defaultValue) {
      this.configCodec = configCodec;
      this.defaultValue = defaultValue;
   }

   public static <T> ConfigUnit<T> of(ConfigCodec<T> codec, T defaultValue) {
      Objects.requireNonNull(codec);
      Objects.requireNonNull(defaultValue);
      return new ConfigUnit<>(codec, defaultValue);
   }

   public T get() {
      if (this.loaded.compareAndSet(false, true)) {
         try {
            this.currentValue = this.load();
         } catch (Exception e) {
            OELib.LOGGER.error("Failed to load config {}", this.configCodec.meta().id(), e);
            this.currentValue = this.defaultValue;
         }
      }

      return this.currentValue;
   }

   public void reload() {
      try {
         this.currentValue = this.load();
      } catch (Exception e) {
         OELib.LOGGER.error("Failed to reload config {}", this.configCodec.meta().id(), e);
      }
   }

   public void applyAutoMigrationOnRegister() {
      ConfigIOUtil.applyAutoMigrationOnRegister(this);
   }

   private T load() {
      ConfigMeta meta = this.configCodec.meta();
      Path path = ConfigIOUtil.resolveLoadPath(meta);
      Optional<T> loaded = ConfigSerializationUtil.loadFromFile(path, meta.format(), this.configCodec.codec(), this.defaultValue);
      T value = loaded.orElse(this.defaultValue);
      OELib.LOGGER.info("Loaded config {} from {}", meta.id(), path);
      ConfigEvents.onLoad(this, value);
      return value;
   }

   public void save() {
      T value = this.currentValue != null ? this.currentValue : this.defaultValue;
      ConfigMeta meta = this.configCodec.meta();
      Path path = ConfigIOUtil.resolveSavePath(meta);

      try {
         ConfigEvents.beforeSave(this, value);
         int version = ConfigFixRegistry.get(meta.id()).map(ConfigFixRegistry.Chain::currentVersion).orElse(0);
         Optional<String> content = ConfigSerializationUtil.encodeToStringWithVersion(
            value, version, meta.format(), this.configCodec.codec(), this.configCodec.fields()
         );
         if (content.isEmpty()) {
            throw new IllegalStateException("Failed to save config: " + meta.id());
         }

         Path parent = path.getParent();
         if (parent != null) {
            Files.createDirectories(parent);
         }

         Files.writeString(path, content.get(), StandardCharsets.UTF_8);
         OELib.LOGGER.info("Saved config {} to {}", meta.id(), path);
         ConfigEvents.afterSave(this, value);
      } catch (Exception e) {
         OELib.LOGGER.error("Failed to save config {}", meta.id(), e);
      }
   }

   public T getDefaultValue() {
      return this.defaultValue;
   }

   public ConfigMeta meta() {
      return this.configCodec.meta();
   }

   public ResourceLocation id() {
      return this.configCodec.meta().id();
   }

   public ConfigCodec<T> codec() {
      return this.configCodec;
   }

   public void setValue(T value) {
      T old = this.currentValue;
      this.currentValue = value;
      if (!Platform.isClient() || this.configCodec.meta().side() != ConfigSide.SERVER || !ConfigManager.isUpdatingFromServer()) {
         OELib.LOGGER.info("Config {} changed", this.configCodec.meta().id());
         ConfigEvents.onChanged(this, old, value);
      }
   }
}
