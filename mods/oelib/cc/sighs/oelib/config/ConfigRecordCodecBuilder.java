package cc.sighs.oelib.config;

import cc.sighs.oelib.config.field.ConfigField;
import cc.sighs.oelib.config.model.ConfigMeta;
import cc.sighs.oelib.config.model.ConfigSide;
import cc.sighs.oelib.config.model.ConfigStorageFormat;
import cc.sighs.oelib.config.model.ConfigValueMeta;
import com.google.gson.JsonObject;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;

public class ConfigRecordCodecBuilder {
   private ConfigRecordCodecBuilder() {
   }

   public static <T> ConfigUnit<T> createClient(
      ResourceLocation configId, Function<Instance<T>, ? extends App<Mu<T>, T>> builder, Consumer<ConfigMeta.Builder> metaCustomizer
   ) {
      return createInternal(configId, builder, metaCustomizer, ConfigSide.CLIENT);
   }

   public static <T> ConfigUnit<T> create(
      ResourceLocation configId, Function<Instance<T>, ? extends App<Mu<T>, T>> builder, Consumer<ConfigMeta.Builder> metaCustomizer
   ) {
      return createInternal(configId, builder, metaCustomizer, ConfigSide.SERVER);
   }

   private static <T> ConfigUnit<T> createInternal(
      ResourceLocation configId, Function<Instance<T>, ? extends App<Mu<T>, T>> builder, Consumer<ConfigMeta.Builder> metaCustomizer, ConfigSide side
   ) {
      Objects.requireNonNull(configId);
      Objects.requireNonNull(builder);
      List<ConfigValueMeta> fields = new ArrayList<>();
      ConfigField.CURRENT_FIELDS.set(fields);
      ConfigField.CURRENT_CONFIG_ID.set(configId);

      Codec<T> codec;
      try {
         codec = RecordCodecBuilder.create(builder);
      } finally {
         ConfigField.CURRENT_FIELDS.remove();
         ConfigField.CURRENT_CONFIG_ID.remove();
      }

      ConfigMeta.Builder metaBuilder = ConfigMeta.builder(configId).format(ConfigStorageFormat.TOML).side(side);
      if (metaCustomizer != null) {
         metaCustomizer.accept(metaBuilder);
      }

      ConfigMeta meta = metaBuilder.build();
      ConfigCodec<T> configCodec = new ConfigCodec<>(codec, meta, List.copyOf(fields));
      T defaultValue = deriveDefault(codec);
      return ConfigUnit.of(configCodec, defaultValue);
   }

   private static <T> T deriveDefault(Codec<T> codec) {
      JsonObject element = new JsonObject();
      DataResult<T> res = codec.parse(JsonOps.INSTANCE, element);
      return (T)res.result().orElseThrow(() -> new IllegalStateException("Missing defaults for config; please specify defaultValue for all fields"));
   }
}
