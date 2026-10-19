package nadiendev.constructionwand.basics.option;

import com.google.common.base.Enums;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

public class OptionEnum<E extends Enum<E>> implements IOption<E> {
   private final ItemStack stack;
   private final DataComponentType<E> componentType;
   private final String key;
   private final Class<E> enumClass;
   private final boolean enabled;
   private final E dval;
   private E value;

   public OptionEnum(ItemStack stack, DataComponentType<E> componentType, String key, Class<E> enumClass, E dval, boolean enabled) {
      this.stack = stack;
      this.componentType = componentType;
      this.key = key;
      this.enumClass = enumClass;
      this.enabled = enabled;
      this.dval = dval;
      this.value = (E)stack.getOrDefault(componentType, dval);
   }

   public OptionEnum(ItemStack stack, DataComponentType<E> componentType, String key, Class<E> enumClass, E dval) {
      this(stack, componentType, key, enumClass, dval, true);
   }

   @Override
   public String getKey() {
      return this.key;
   }

   @Override
   public DataComponentType<?> getComponentType() {
      return this.componentType;
   }

   @Override
   public String getValueString() {
      return this.value.name().toLowerCase();
   }

   @Override
   public void setValueString(String val) {
      this.set((E)Enums.getIfPresent(this.enumClass, val.toUpperCase()).or(this.dval));
   }

   @Override
   public boolean isEnabled() {
      return this.enabled;
   }

   public void set(E val) {
      if (this.enabled) {
         this.value = val;
         this.stack.set(this.componentType, val);
      }
   }

   public E get() {
      return this.value;
   }

   public E next(boolean dir) {
      E[] enumValues = this.enumClass.getEnumConstants();
      int i = this.value.ordinal() + (dir ? 1 : -1);
      if (i < 0) {
         i += enumValues.length;
      }

      this.set(enumValues[i % enumValues.length]);
      return this.value;
   }
}
