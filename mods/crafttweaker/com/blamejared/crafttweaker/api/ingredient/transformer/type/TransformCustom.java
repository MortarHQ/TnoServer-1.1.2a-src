package com.blamejared.crafttweaker.api.ingredient.transformer.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.transformer.IIngredientTransformer;
import com.blamejared.crafttweaker.api.ingredient.transformer.serializer.TransformCustomSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.transform.type.TransformCustom")
@Document("vanilla/api/ingredient/transform/type/TransformCustom")
public class TransformCustom implements IIngredientTransformer {
   public static final Map<String, Function<IItemStack, IItemStack>> KNOWN_TRANSFORMERS = new HashMap<>();
   private final String uid;
   private Function<IItemStack, IItemStack> function;

   public TransformCustom(String uid) {
      this.uid = uid;
   }

   public TransformCustom(String uid, @Nullable Function<IItemStack, IItemStack> function) {
      this(uid);
      this.function = function;
      if (function != null) {
         KNOWN_TRANSFORMERS.put(uid, function);
      }
   }

   @Override
   public IItemStack transform(IItemStack stack) {
      if (this.function == null) {
         this.function = KNOWN_TRANSFORMERS.get(this.uid);
      }

      if (this.function == null) {
         throw new IllegalStateException("No transformer named '" + this.uid + "' known!");
      } else {
         return this.function.apply(stack).copy();
      }
   }

   @Override
   public String getCommandString(String base) {
      return String.format("%s.transformCustom('%s')", base, this.uid);
   }

   public TransformCustomSerializer getSerializer() {
      return TransformCustomSerializer.INSTANCE;
   }

   public String getUid() {
      return this.uid;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TransformCustom that = (TransformCustom)o;
         return Objects.equals(this.getUid(), that.getUid()) && Objects.equals(this.function, that.function);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getUid(), this.function);
   }
}
