package com.blamejared.crafttweaker.api.ingredient.condition.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionCustomSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.condition.type.ConditionCustom")
@Document("vanilla/api/ingredient/condition/type/ConditionCustom")
public class ConditionCustom implements IIngredientCondition {
   private static final Map<String, Predicate<IItemStack>> KNOWN_CONDITIONS = new HashMap<>();
   private final String uid;
   private Predicate<IItemStack> function;

   public ConditionCustom(String uid) {
      this(uid, null);
   }

   public ConditionCustom(String uid, Predicate<IItemStack> function) {
      this.uid = uid;
      this.function = function;
      if (function != null) {
         KNOWN_CONDITIONS.put(uid, function);
      }
   }

   @Override
   public boolean matches(IItemStack stack) {
      if (this.function == null) {
         this.function = KNOWN_CONDITIONS.get(this.uid);
      }

      if (this.function == null) {
         throw new IllegalStateException("No condition named '" + this.uid + "' known!");
      } else {
         return this.function.test(stack);
      }
   }

   @Override
   public String getCommandString(String base) {
      return "%s.onlyIf('%s')".formatted(base, this.uid);
   }

   public String getUid() {
      return this.uid;
   }

   public ConditionCustomSerializer getSerializer() {
      return ConditionCustomSerializer.INSTANCE;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ConditionCustom that = (ConditionCustom)o;
         return Objects.equals(this.uid, that.uid);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.uid != null ? this.uid.hashCode() : 0;
   }
}
