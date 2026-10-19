package artifacts.config;

import artifacts.Artifacts;
import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.config.value.type.EnumValueType;
import artifacts.config.value.type.NumberValueType;
import artifacts.config.value.type.ValueType;
import artifacts.platform.PlatformServices;
import com.electronwill.nightconfig.core.ConfigFormat;
import com.electronwill.nightconfig.core.ConfigSpec;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.file.FileWatcher;
import com.electronwill.nightconfig.core.io.ParsingException;
import com.electronwill.nightconfig.core.io.WritingMode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.util.StringRepresentable;
import org.apache.commons.io.FilenameUtils;

public abstract class ConfigManager {
   protected CommentedFileConfig config;
   protected final ConfigSpec spec = new ConfigSpec();
   private final Path configPath;
   private final String name;
   private final Map<String, Value.ConfigValue<?>> values = new HashMap<>();
   private final Map<String, List<String>> tooltips = new HashMap<>();
   private final Map<ValueType<?, ?>, ConfigManager.ValueMap<?>> typeToValues = new HashMap<>();

   protected ConfigManager(String fileName) {
      this.name = fileName;
      this.configPath = Path.of("artifacts", "%s.toml".formatted(fileName));
   }

   protected void setup() {
      this.config = (CommentedFileConfig)CommentedFileConfig.builder(PlatformServices.platformHelper.getConfigDir().resolve(this.configPath))
         .sync()
         .preserveInsertionOrder()
         .autosave()
         .onFileNotFound(this::createNewConfigFile)
         .writingMode(WritingMode.REPLACE)
         .build();
      Path path = PlatformServices.platformHelper.getConfigDir().resolve(this.configPath);

      try {
         this.config.load();
      } catch (ParsingException exception) {
         Artifacts.LOGGER.warn("Failed to load config file {}, attempting to recreate", this.configPath);

         try {
            backUpConfig(this.config.getNioPath(), 5);
            Files.delete(this.config.getNioPath());
            this.config.load();
         } catch (Throwable t) {
            exception.addSuppressed(t);
            throw new RuntimeException("Failed to load config file " + this.configPath, exception);
         }
      }

      if (!this.spec.isCorrect(this.config)) {
         this.correctConfigAndSave();
      }

      Artifacts.LOGGER.debug("Loaded config file {}", this.configPath);
      FileWatcher.defaultInstance().addWatch(path, new ConfigManager.ConfigWatcher());
      Artifacts.LOGGER.debug("Watching config file {} for changes", this.configPath);
   }

   private boolean createNewConfigFile(Path file, ConfigFormat<?> conf) throws IOException {
      Files.createDirectories(file.getParent());
      Files.createFile(file);
      conf.initEmptyFile(file);
      return true;
   }

   private void correctConfigAndSave() {
      this.addMissingKeys();
      this.spec.correct(this.config);
      this.config.save();
   }

   public String getName() {
      return this.name;
   }

   public Map<String, Value.ConfigValue<?>> getValues() {
      return this.values;
   }

   public <T> Map<String, Value.ConfigValue<T>> getValues(ValueType<T, ?> type) {
      ConfigManager.ValueMap<T> valueMap = (ConfigManager.ValueMap<T>)this.typeToValues.get(type);
      return valueMap.getMap();
   }

   public List<String> getDescription(String key) {
      return this.tooltips.get(key);
   }

   public <T, C> T read(ValueType<T, C> type, String key) {
      return type.read((C)this.config.get(key));
   }

   public <T, C> void write(ValueType<T, C> type, String key, T value) {
      this.config.set(key, type.write(value));
   }

   private <T> void reset(String key, Value.ConfigValue<T> value) {
      this.config.add(key, value.type().write(value.getDefaultValue()));
   }

   protected <T> void readValueFromConfig(String key, Value.ConfigValue<T> value) {
      value.set(this.read(value.type(), key));
   }

   public void readValuesFromConfig() {
      this.getValues().forEach(this::readValueFromConfig);
   }

   protected void addMissingKeys() {
      Map<String, Value.ConfigValue<?>> values = this.getValues();
      List<String> keys = new ArrayList<>(values.keySet());
      Collections.sort(keys);

      for (String key : keys) {
         if (!this.config.contains(key)) {
            Value.ConfigValue<?> value = values.get(key);
            this.reset(key, value);
            StringBuilder builder = new StringBuilder();

            for (String tooltip : this.getDescription(key)) {
               builder.append(tooltip).append('\n');
            }

            builder.append(value.type().getAllowedValuesComment());
            this.config.setComment(key, builder.toString());
         }
      }
   }

   public void onConfigChanged() {
      this.readValuesFromConfig();
   }

   public static void backUpConfig(Path commentedFileConfig, int maxBackups) {
      Path bakFileLocation = commentedFileConfig.getParent();
      String bakFileName = FilenameUtils.removeExtension(commentedFileConfig.getFileName().toString());
      String bakFileExtension = FilenameUtils.getExtension(commentedFileConfig.getFileName().toString()) + ".bak";
      Path bakFile = bakFileLocation.resolve(bakFileName + "-1." + bakFileExtension);

      try {
         for (int i = maxBackups; i > 0; i--) {
            Path oldBak = bakFileLocation.resolve(bakFileName + "-" + i + "." + bakFileExtension);
            if (Files.exists(oldBak)) {
               if (i >= maxBackups) {
                  Files.delete(oldBak);
               } else {
                  Files.move(oldBak, bakFileLocation.resolve(bakFileName + "-" + (i + 1) + "." + bakFileExtension));
               }
            }
         }

         Files.copy(commentedFileConfig, bakFile);
      } catch (IOException exception) {
         Artifacts.LOGGER.warn("Failed to back up config file {}", commentedFileConfig, exception);
      }
   }

   protected Value.ConfigValue<Boolean> defineBool(String key, String... tooltips) {
      return this.defineBool(key, true, false, tooltips);
   }

   protected Value.ConfigValue<Boolean> defineBool(String key, boolean defaultValue, boolean requiresRestart, String... tooltips) {
      Value.ConfigValue<Boolean> value = this.createValue(key, ValueTypes.BOOLEAN, defaultValue, requiresRestart, tooltips);
      this.spec.define(key, defaultValue);
      return value;
   }

   protected Value.ConfigValue<Integer> defineNonNegativeInt(String key, int defaultValue, String... tooltips) {
      return this.defineNumber(key, ValueTypes.NON_NEGATIVE_INT, defaultValue, false, tooltips);
   }

   protected Value.ConfigValue<Integer> defineInt(String key, int defaultValue, String... tooltips) {
      return this.defineNumber(key, ValueTypes.INT, defaultValue, false, tooltips);
   }

   protected Value.ConfigValue<Double> defineAttributeModifier(String key, double defaultValue, String... tooltips) {
      return this.defineNumber(key, ValueTypes.ATTRIBUTE_MODIFIER_AMOUNT, defaultValue, false, tooltips);
   }

   protected Value.ConfigValue<Double> defineNonNegativeDouble(String key, double defaultValue, String... tooltips) {
      return this.defineNumber(key, ValueTypes.NON_NEGATIVE_DOUBLE, defaultValue, false, tooltips);
   }

   protected Value.ConfigValue<Double> defineFraction(String key, double defaultValue, String... tooltips) {
      return this.defineNumber(key, ValueTypes.FRACTION, defaultValue, false, tooltips);
   }

   protected Value.ConfigValue<Integer> defineDuration(String key, int defaultValue, String... tooltips) {
      return this.defineNumber(key, ValueTypes.DURATION, defaultValue, false, tooltips);
   }

   protected Value.ConfigValue<Integer> defineEnchantmentLevel(String key, int defaultValue, String... tooltips) {
      return this.defineNumber(key, ValueTypes.ENCHANTMENT_LEVEL, defaultValue, false, tooltips);
   }

   protected Value.ConfigValue<Integer> defineMobEffectLevel(String key, int defaultValue, String... tooltips) {
      return this.defineNumber(key, ValueTypes.MOB_EFFECT_LEVEL, defaultValue, false, tooltips);
   }

   private <T extends Number & Comparable<T>> Value.ConfigValue<T> defineNumber(
      String key, NumberValueType<T> type, T defaultValue, boolean requiresRestart, String... tooltips
   ) {
      Value.ConfigValue<T> value = this.createValue(key, type, defaultValue, requiresRestart, tooltips);
      this.spec.defineInRange(key, defaultValue, type.getMin(), type.getMax());
      return value;
   }

   protected <T extends Enum<T> & StringRepresentable> Value.ConfigValue<T> defineEnum(
      String key, EnumValueType<T> type, T defaultValue, boolean requiresRestart, String... tooltips
   ) {
      Value.ConfigValue<T> value = this.createValue(key, type, defaultValue, requiresRestart, tooltips);
      List<String> allowedValues = new ArrayList<>();
      allowedValues.addAll(type.getValues().stream().map(rec$ -> rec$.getSerializedName()).toList());
      allowedValues.addAll(type.getValues().stream().map(rec$ -> rec$.getSerializedName()).map(String::toUpperCase).toList());
      this.spec.defineInList(key, value.getDefaultValue().getSerializedName(), allowedValues);
      return value;
   }

   protected <T> Value.ConfigValue<T> createValue(String key, ValueType<T, ?> type, T defaultValue, boolean requiresRestart, String... tooltips) {
      Value.ConfigValue<T> value = new Value.ConfigValue<>(type, key, defaultValue, requiresRestart);
      this.values.put(key, value);
      this.tooltips.put(key, List.of(tooltips));
      if (!this.typeToValues.containsKey(type)) {
         this.typeToValues.put(type, new ConfigManager.ValueMap());
      }

      this.typeToValues.get(type).getMap().put(key, value);
      return value;
   }

   private class ConfigWatcher implements Runnable {
      @Override
      public void run() {
         try {
            ConfigManager.this.config.load();
            if (!ConfigManager.this.spec.isCorrect(ConfigManager.this.config)) {
               Artifacts.LOGGER.warn("Configuration file {} is not correct. Correcting", ConfigManager.this.configPath);
               ConfigManager.this.correctConfigAndSave();
            }
         } catch (ParsingException exception) {
            throw new RuntimeException("Failed to load config file " + ConfigManager.this.configPath, exception);
         }

         Artifacts.LOGGER.info("Config file {} changed", ConfigManager.this.configPath);
         ConfigManager.this.onConfigChanged();
      }
   }

   private static class ValueMap<T> {
      private final Map<String, Value.ConfigValue<T>> map = new HashMap<>();

      public Map<String, Value.ConfigValue<T>> getMap() {
         return this.map;
      }
   }
}
