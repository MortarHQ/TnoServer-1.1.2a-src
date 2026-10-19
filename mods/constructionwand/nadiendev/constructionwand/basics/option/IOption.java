package nadiendev.constructionwand.basics.option;

import net.minecraft.core.component.DataComponentType;

public interface IOption<T> {
   DataComponentType<?> getComponentType();

   String getKey();

   String getValueString();

   void setValueString(String var1);

   default String getKeyTranslation() {
      return "constructionwand.option." + this.getKey();
   }

   default String getValueTranslation() {
      return "constructionwand.option." + this.getKey() + "." + this.getValueString();
   }

   default String getDescTranslation() {
      return "constructionwand.option." + this.getKey() + "." + this.getValueString() + ".desc";
   }

   boolean isEnabled();

   void set(T var1);

   T get();

   T next(boolean var1);

   default T next() {
      return this.next(true);
   }
}
