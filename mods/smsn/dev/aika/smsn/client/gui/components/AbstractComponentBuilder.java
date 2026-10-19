package dev.aika.smsn.client.gui.components;

import java.lang.reflect.Field;
import lombok.Generated;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractComponentBuilder<T> {
   protected final ConfigEntryBuilder entryBuilder;
   protected final Object configObject;
   protected Class<?> defaultObject;
   protected Field field;
   protected String category;
   protected String modId;

   public AbstractComponentBuilder(ConfigEntryBuilder entryBuilder, Object configObject) {
      this.entryBuilder = entryBuilder;
      this.configObject = configObject;
   }

   protected T getValue() {
      try {
         this.field.setAccessible(true);
         return (T)this.field.get(this.configObject);
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   protected void setValue(T value) {
      try {
         this.field.setAccessible(true);
         this.field.set(this.configObject, value);
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   protected T getDefaultValue() {
      try {
         Field f = this.defaultObject.getDeclaredField(this.field.getName());
         f.setAccessible(true);
         return (T)f.get(null);
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   protected String translatableKeyPrefix() {
      return String.format("config.%s.%s", this.modId, this.category);
   }

   public abstract AbstractConfigListEntry<T> build();

   @Generated
   public AbstractComponentBuilder<T> setDefaultObject(Class<?> defaultObject) {
      this.defaultObject = defaultObject;
      return this;
   }

   @Generated
   public AbstractComponentBuilder<T> setCategory(String category) {
      this.category = category;
      return this;
   }

   @Generated
   public AbstractComponentBuilder<T> setModId(String modId) {
      this.modId = modId;
      return this;
   }
}
