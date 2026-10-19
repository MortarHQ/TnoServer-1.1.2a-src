package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.jei.subtypes;

import javax.annotation.Nullable;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common.subtypes.PropertyBasedSubtypeInterpreter;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common.subtypes.PropertyBasedSubtypeInterpreterWrapper;

public class JeiSubtypeInterpreter extends PropertyBasedSubtypeInterpreterWrapper implements ISubtypeInterpreter<ItemStack> {
   public static JeiSubtypeInterpreter of(PropertyBasedSubtypeInterpreter wrapped) {
      return new JeiSubtypeInterpreter(wrapped);
   }

   private JeiSubtypeInterpreter(PropertyBasedSubtypeInterpreter wrapped) {
      super(wrapped);
   }

   @Nullable
   public final Object getSubtypeData(ItemStack ingredient, UidContext context) {
      return this.getComparableData(ingredient);
   }

   public String getLegacyStringSubtypeInfo(ItemStack itemStack, UidContext context) {
      StringBuilder result = new StringBuilder();

      for (PropertyBasedSubtypeInterpreter.IPropertyDefinition<?> definition : this.getPropertyDefinitions()) {
         Object value = definition.getPropertyValue(itemStack);
         if (value != null) {
            String serializedValue = this.getSerializedPropertyValue(definition, value);
            if (!result.isEmpty()) {
               result.append(',');
            }

            result.append(definition.getPropertyName()).append(':').append(serializedValue);
         }
      }

      return "{" + result + "}";
   }

   private <T> String getSerializedPropertyValue(PropertyBasedSubtypeInterpreter.IPropertyDefinition<T> definition, Object value) {
      return definition.serializePropertyValue((T)value);
   }
}
