package cc.sighs.oelib.config.field;

import cc.sighs.oelib.config.model.ConfigValueMeta;
import cc.sighs.oelib.config.ui.ConfigUiHint;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;

public final class ConfigField {
   public static final ThreadLocal<List<ConfigValueMeta>> CURRENT_FIELDS = new ThreadLocal<>();
   public static final ThreadLocal<ResourceLocation> CURRENT_CONFIG_ID = new ThreadLocal<>();

   private ConfigField() {
   }

   public static void recordMeta(ConfigValueMeta meta) {
      List<ConfigValueMeta> list = CURRENT_FIELDS.get();
      if (list != null) {
         list.add(meta);
      }
   }

   public static ConfigField.IntBuilder intRange(String key, int min, int max) {
      return new ConfigField.IntBuilder(key, Codec.intRange(min, max), min, max, 1.0);
   }

   public static ConfigField.DoubleBuilder doubleRange(String key, double min, double max) {
      return new ConfigField.DoubleBuilder(key, Codec.doubleRange(min, max), min, max, 0.01);
   }

   public static ConfigField.BoolBuilder bool(String key) {
      return new ConfigField.BoolBuilder(key, Codec.BOOL);
   }

   public static ConfigField.StringBuilder string(String key) {
      return new ConfigField.StringBuilder(key, Codec.STRING);
   }

   public static <E extends Enum<E>> ConfigField.EnumBuilder<E> enumValue(String key, Class<E> enumClass) {
      Codec<E> codec = Codec.STRING.xmap(name -> Enum.valueOf(enumClass, name), Enum::name);
      return new ConfigField.EnumBuilder<>(key, codec, enumClass);
   }

   public static ConfigField.DynamicBuilder dynamic(String key) {
      return new ConfigField.DynamicBuilder(key, Codec.PASSTHROUGH);
   }

   public static <T> ConfigField.ListBuilder<T> list(String key, Codec<T> elementCodec) {
      return new ConfigField.ListBuilder<>(key, elementCodec);
   }

   public static <K, V> ConfigField.MapBuilder<K, V> map(String key, Codec<K> keyCodec, Codec<V> valueCodec) {
      return new ConfigField.MapBuilder<>(key, keyCodec, valueCodec);
   }

   public static <T> ConfigField.OptionalBuilder<T> optional(String key, Codec<T> elementCodec) {
      return new ConfigField.OptionalBuilder<>(key, elementCodec);
   }

   public static final class BoolBuilder extends BaseFieldBuilder<Boolean, ConfigField.BoolBuilder> {
      public BoolBuilder(String key, Codec<Boolean> codec) {
         super(key, codec);
      }
   }

   public static final class DoubleBuilder extends BaseFieldBuilder<Double, ConfigField.DoubleBuilder> {
      private final double min;
      private final double max;
      private final double step;

      public DoubleBuilder(String key, Codec<Double> codec, double min, double max, double step) {
         super(key, codec);
         this.min = min;
         this.max = max;
         this.step = step;
         this.metaBuilder.uiHint(ConfigUiHint.slider(min, max, step));
      }

      public ConfigField.DoubleBuilder text() {
         this.metaBuilder.uiHint(ConfigUiHint.text());
         return this;
      }

      public ConfigField.DoubleBuilder slider() {
         this.metaBuilder.uiHint(ConfigUiHint.slider(this.min, this.max, this.step));
         return this;
      }
   }

   public static final class DynamicBuilder extends BaseFieldBuilder<Dynamic<?>, ConfigField.DynamicBuilder> {
      public DynamicBuilder(String key, Codec<Dynamic<?>> codec) {
         super(key, codec);
      }
   }

   public static final class EnumBuilder<E extends Enum<E>> extends BaseFieldBuilder<E, ConfigField.EnumBuilder<E>> {
      private final Class<E> enumClass;

      public EnumBuilder(String key, Codec<E> codec, Class<E> enumClass) {
         super(key, codec);
         this.enumClass = enumClass;
         List<String> names = Arrays.stream(enumClass.getEnumConstants()).map(Enum::name).toList();
         this.metaBuilder.uiHint(ConfigUiHint.dropdown(names));
      }
   }

   public static final class IntBuilder extends BaseFieldBuilder<Integer, ConfigField.IntBuilder> {
      private final int min;
      private final int max;
      private final double step;

      public IntBuilder(String key, Codec<Integer> codec, int min, int max, double step) {
         super(key, codec);
         this.min = min;
         this.max = max;
         this.step = step;
         this.metaBuilder.uiHint(ConfigUiHint.slider(min, max, step));
      }

      public ConfigField.IntBuilder text() {
         this.metaBuilder.uiHint(ConfigUiHint.text());
         return this;
      }

      public ConfigField.IntBuilder slider() {
         this.metaBuilder.uiHint(ConfigUiHint.slider(this.min, this.max, this.step));
         return this;
      }
   }

   public static final class ListBuilder<T> extends BaseFieldBuilder<List<T>, ConfigField.ListBuilder<T>> {
      private final Codec<T> elementCodec;

      ListBuilder(String key, Codec<T> elementCodec) {
         super(key, Codec.list(elementCodec));
         this.elementCodec = elementCodec;
      }

      @Override
      public <O> RecordCodecBuilder<O, List<T>> forGetter(Function<O, List<T>> getter) {
         ResourceLocation cfgId = ConfigField.CURRENT_CONFIG_ID.get();
         if (cfgId != null) {
            String autoKey = "config." + cfgId.getNamespace() + "." + cfgId.getPath() + "." + this.key;
            this.metaBuilder.translationKey(autoKey);
            if (this.tooltipEnabled) {
               this.metaBuilder.tooltip(autoKey + ".tooltip");
            }
         }

         ConfigValueMeta meta = this.metaBuilder.build();
         ConfigField.recordMeta(meta);
         MapCodec<List<T>> field = Codec.list(this.elementCodec).fieldOf(meta.key());
         if (this.defaultValue != null) {
            field = field.orElse(this.defaultValue);
         }

         return field.forGetter(getter);
      }
   }

   public static final class MapBuilder<K, V> extends BaseFieldBuilder<Map<K, V>, ConfigField.MapBuilder<K, V>> {
      private final Codec<K> keyCodec;
      private final Codec<V> valueCodec;

      MapBuilder(String key, Codec<K> keyCodec, Codec<V> valueCodec) {
         super(key, Codec.unboundedMap(keyCodec, valueCodec).xmap(HashMap::new, m -> m));
         this.keyCodec = keyCodec;
         this.valueCodec = valueCodec;
      }

      @Override
      public <O> RecordCodecBuilder<O, Map<K, V>> forGetter(Function<O, Map<K, V>> getter) {
         ResourceLocation cfgId = ConfigField.CURRENT_CONFIG_ID.get();
         if (cfgId != null) {
            String autoKey = "config." + cfgId.getNamespace() + "." + cfgId.getPath() + "." + this.key;
            this.metaBuilder.translationKey(autoKey);
            if (this.tooltipEnabled) {
               this.metaBuilder.tooltip(autoKey + ".tooltip");
            }
         }

         ConfigValueMeta meta = this.metaBuilder.build();
         ConfigField.recordMeta(meta);
         MapCodec<Map<K, V>> field = Codec.unboundedMap(this.keyCodec, this.valueCodec).fieldOf(meta.key());
         if (this.defaultValue != null) {
            field = field.orElse(this.defaultValue);
         }

         return field.forGetter(getter);
      }
   }

   public static final class OptionalBuilder<T> extends BaseFieldBuilder<Optional<T>, ConfigField.OptionalBuilder<T>> {
      private final Codec<T> elementCodec;

      OptionalBuilder(String key, Codec<T> elementCodec) {
         super(key, elementCodec.optionalFieldOf(key).codec());
         this.elementCodec = elementCodec;
         this.defaultValue = Optional.empty();
      }

      @Override
      public <O> RecordCodecBuilder<O, Optional<T>> forGetter(Function<O, Optional<T>> getter) {
         Objects.requireNonNull(getter);
         if (this.beforeMetaHook != null) {
            this.beforeMetaHook.accept(this.metaBuilder);
         }

         ResourceLocation cfgId = ConfigField.CURRENT_CONFIG_ID.get();
         if (cfgId != null) {
            String autoKey = "config." + cfgId.getNamespace() + "." + cfgId.getPath() + "." + this.key;
            this.metaBuilder.translationKey(autoKey);
            if (this.tooltipEnabled) {
               this.metaBuilder.tooltip(autoKey + ".tooltip");
            }
         }

         ConfigValueMeta meta = this.metaBuilder.build();
         ConfigField.recordMeta(meta);
         MapCodec<Optional<T>> mc = this.elementCodec.optionalFieldOf(meta.key());
         if (this.defaultValue.isPresent()) {
            mc = mc.orElse(this.defaultValue);
         }

         if (this.afterMetaHook != null) {
            this.afterMetaHook.accept(meta);
         }

         return mc.forGetter(getter);
      }
   }

   public static final class StringBuilder extends BaseFieldBuilder<String, ConfigField.StringBuilder> {
      public StringBuilder(String key, Codec<String> codec) {
         super(key, codec);
      }
   }
}
