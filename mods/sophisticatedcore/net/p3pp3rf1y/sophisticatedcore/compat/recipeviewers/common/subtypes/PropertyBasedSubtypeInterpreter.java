package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common.subtypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

public abstract class PropertyBasedSubtypeInterpreter {
   private final List<PropertyBasedSubtypeInterpreter.IPropertyDefinition<?>> propertyDefinitions = new ArrayList<>();

   protected List<PropertyBasedSubtypeInterpreter.IPropertyDefinition<?>> getPropertyDefinitions() {
      return this.propertyDefinitions;
   }

   protected <T> void addOptionalProperty(Function<ItemStack, Optional<T>> propertyGetter, String propertyName, Function<T, String> propertyValueSerializer) {
      this.addDefinition(s -> propertyGetter.<T>andThen(i -> i.orElse(null)).apply(s), propertyName, propertyValueSerializer);
   }

   private <T> void addDefinition(
      final PropertyBasedSubtypeInterpreter.IPropertyValueGetter<T> getter, final String propertyName, final Function<T, String> propertyValueSerializer
   ) {
      this.getPropertyDefinitions().add(new PropertyBasedSubtypeInterpreter.IPropertyDefinition<T>() {
         @Nullable
         @Override
         public T getPropertyValue(ItemStack itemStack) {
            return getter.getPropertyValue(itemStack);
         }

         @Override
         public String getPropertyName() {
            return propertyName;
         }

         @Override
         public String serializePropertyValue(@Nullable T property) {
            return property != null ? propertyValueSerializer.apply(property) : "";
         }
      });
   }

   protected <T> void addProperty(
      PropertyBasedSubtypeInterpreter.IPropertyValueGetter<T> propertyGetter, String propertyName, Function<T, String> propertyValueSerializer
   ) {
      this.addDefinition(propertyGetter, propertyName, propertyValueSerializer);
   }

   @Nullable
   public final Object getComparableData(ItemStack stack) {
      boolean allNulls = true;
      List<Object> results = new ArrayList<>(this.getPropertyDefinitions().size());

      for (PropertyBasedSubtypeInterpreter.IPropertyDefinition<?> definition : this.getPropertyDefinitions()) {
         Object value = definition.getPropertyValue(stack);
         if (value != null) {
            allNulls = false;
         }

         results.add(value);
      }

      return allNulls ? null : results;
   }

   private <T> String getSerializedPropertyValue(PropertyBasedSubtypeInterpreter.IPropertyDefinition<T> definition, Object value) {
      return definition.serializePropertyValue((T)value);
   }

   public String getRegistrySanitizedItemString(ItemStack stack) {
      StringBuilder result = new StringBuilder();

      for (PropertyBasedSubtypeInterpreter.IPropertyDefinition<?> definition : this.getPropertyDefinitions()) {
         Object value = definition.getPropertyValue(stack);
         if (value != null) {
            String serializedValue = this.sanitize(this.getSerializedPropertyValue(definition, value));
            if (!result.isEmpty()) {
               result.append('_');
            }

            result.append(definition.getPropertyName().toLowerCase(Locale.ROOT)).append('_').append(serializedValue);
         }
      }

      return getItemPath(stack) + "_" + result;
   }

   private String sanitize(String value) {
      return value.replaceAll(":", "_").toLowerCase(Locale.ROOT);
   }

   private static String getItemPath(ItemStack stack) {
      return BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
   }

   public interface IPropertyDefinition<T> {
      @Nullable
      T getPropertyValue(ItemStack var1);

      String getPropertyName();

      String serializePropertyValue(@Nullable T var1);
   }

   public interface IPropertyValueGetter<T> {
      @Nullable
      T getPropertyValue(ItemStack var1);
   }
}
