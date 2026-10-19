package artifacts.config.screen;

import artifacts.config.ConfigManager;
import artifacts.config.value.Value;
import artifacts.config.value.type.EnumValueType;
import artifacts.config.value.type.NumberValueType;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.DoubleFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.FieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntFieldBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public class ConfigEntries {
   public static ConfigEntries.ConfigEntryFactory<Boolean> booleanConfigEntryFactory() {
      return (config, entryBuilder, title, value) -> ((BooleanToggleBuilder)entryBuilder.startBooleanToggle(title, config.read(value.type(), value.getId()))
            .setDefaultValue(value.getDefaultValue()))
         .setSaveConsumer(v -> config.write(value.type(), value.getId(), v));
   }

   public static ConfigEntries.ConfigEntryFactory<Integer> integerConfigEntryFactory(NumberValueType<Integer> type) {
      return (config, entryBuilder, title, value) -> ((IntFieldBuilder)((IntFieldBuilder)entryBuilder.startIntField(
                  title, config.read(value.type(), value.getId())
               )
               .setDefaultValue(value.getDefaultValue()))
            .setSaveConsumer(v -> config.write(value.type(), value.getId(), v))
            .setMin(type.getMin()))
         .setMax(type.getMax());
   }

   public static ConfigEntries.ConfigEntryFactory<Double> doubleConfigEntryFactory(NumberValueType<Double> type) {
      return (config, entryBuilder, title, value) -> ((DoubleFieldBuilder)((DoubleFieldBuilder)entryBuilder.startDoubleField(
                  title, config.read(value.type(), value.getId())
               )
               .setDefaultValue(value.getDefaultValue()))
            .setSaveConsumer(v -> config.write(value.type(), value.getId(), v))
            .setMin(type.getMin()))
         .setMax(type.getMax());
   }

   public static <T extends Enum<T> & StringRepresentable> ConfigEntries.ConfigEntryFactory<T> enumConfigEntryFactory(EnumValueType<T> type) {
      return (config, entryBuilder, title, value) -> entryBuilder.startEnumSelector(
            title, value.getDefaultValue().getClass(), config.read(value.type(), value.getId())
         )
         .setEnumNameProvider(e -> type.getAsComponent((T)e))
         .setDefaultValue(value.getDefaultValue())
         .setSaveConsumer(v -> config.write(value.type(), value.getId(), v));
   }

   public interface ConfigEntryFactory<T> {
      FieldBuilder<?, ?, ?> createConfigEntry(ConfigManager var1, ConfigEntryBuilder var2, Component var3, Value.ConfigValue<T> var4);
   }
}
