package mezz.jei.common.config.file;

import java.util.List;
import java.util.function.Supplier;
import mezz.jei.api.runtime.config.IJeiConfigValue;
import mezz.jei.api.runtime.config.IJeiConfigValueSerializer;
import mezz.jei.core.util.WeakList;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class ConfigValue<T> implements IJeiConfigValue<T>, Supplier<T> {
   private static final Logger LOGGER = LogManager.getLogger();
   private final String name;
   private final Component localizedName;
   private final Component description;
   private final T defaultValue;
   private final IJeiConfigValueSerializer<T> serializer;
   private final WeakList<IConfigListener<T>> listeners = new WeakList<>();
   private volatile T currentValue;
   @Nullable
   private IConfigSchema schema;

   public ConfigValue(String localizationPath, String name, T defaultValue, IJeiConfigValueSerializer<T> serializer) {
      this.name = name;
      String nameKey = localizationPath + "." + name;
      String descriptionKey = nameKey + ".description";
      this.localizedName = Component.translatable(nameKey);
      this.description = Component.translatable(descriptionKey);
      this.defaultValue = defaultValue;
      this.currentValue = defaultValue;
      this.serializer = serializer;
   }

   public void setSchema(IConfigSchema schema) {
      this.schema = schema;
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String getDescription() {
      return this.description.getString();
   }

   @Override
   public Component getLocalizedDescription() {
      return this.description;
   }

   @Override
   public Component getLocalizedName() {
      return this.localizedName;
   }

   @Override
   public T getDefaultValue() {
      return this.defaultValue;
   }

   @Override
   public T getValue() {
      if (this.schema != null) {
         this.schema.loadIfNeeded();
      }

      return this.currentValue;
   }

   @Override
   public T get() {
      return this.getValue();
   }

   @Override
   public IJeiConfigValueSerializer<T> getSerializer() {
      return this.serializer;
   }

   public List<String> setFromSerializedValue(String value) {
      IJeiConfigValueSerializer.IDeserializeResult<T> deserializeResult = this.serializer.deserialize(value);
      deserializeResult.getResult().ifPresent(t -> {
         if (this.currentValue != t) {
            this.currentValue = (T)t;
            this.listeners.forEach(c -> c.onConfigValueChanged(this.currentValue));
         }
      });
      return deserializeResult.getErrors();
   }

   @Override
   public boolean set(T value) {
      if (!this.serializer.isValid(value)) {
         LOGGER.error("Tried to set invalid value : {}\n{}", value, this.serializer.getValidValuesDescription());
         return false;
      }

      if (!this.currentValue.equals(value)) {
         this.currentValue = value;
         this.listeners.forEach(c -> c.onConfigValueChanged(this.currentValue));
         if (this.schema != null) {
            this.schema.markDirty();
         }

         return true;
      } else {
         return false;
      }
   }

   public void addListener(IConfigListener<T> listener) {
      this.listeners.add(listener);
   }
}
