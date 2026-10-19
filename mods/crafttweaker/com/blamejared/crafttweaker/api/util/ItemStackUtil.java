package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.op.IDataOps;
import com.blamejared.crafttweaker.api.data.visitor.DataToStringVisitor;
import com.blamejared.crafttweaker.api.ingredient.condition.IngredientConditions;
import com.blamejared.crafttweaker.natives.component.ExpandDataComponentType;
import java.util.function.Predicate;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentPatch.SplitResult;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public final class ItemStackUtil {
   public static String getCommandString(ItemLike itemLike) {
      return getCommandString(itemLike.asItem().getDefaultInstance());
   }

   public static String getCommandString(ItemStack stack) {
      return getCommandString(stack, false);
   }

   public static String getCommandString(ItemStack stack, boolean mutable) {
      StringBuilder sb = new StringBuilder("<item:").append(BuiltInRegistries.ITEM.getKey(stack.getItem())).append('>');
      SplitResult split = stack.getComponentsPatch().split();
      split.added()
         .filter(Predicate.not(DataComponentType::isTransient))
         .forEach(
            typedDataComponent -> sb.append(".withJsonComponent(")
               .append(ExpandDataComponentType.getCommandString(typedDataComponent.type()))
               .append(", ")
               .append(((IData)typedDataComponent.encodeValue(IDataOps.INSTANCE.withRegistryAccess()).getOrThrow()).accept(DataToStringVisitor.ESCAPE))
               .append(")")
         );
      split.removed().forEach(dataComponentType -> sb.append(".without(").append(ExpandDataComponentType.getCommandString(dataComponentType)).append(")"));
      if (!stack.isEmpty() && stack.getCount() != 1) {
         sb.append(" * ").append(stack.getCount());
      }

      if (mutable) {
         sb.insert(0, '(').append(").mutable()");
      }

      return sb.toString();
   }

   public static boolean areStacksTheSame(ItemStack first, ItemStack second) {
      return areStacksTheSame(first, second, IngredientConditions.EMPTY);
   }

   public static boolean areStacksTheSame(ItemStack first, ItemStack second, IngredientConditions conditions) {
      if (first != null && second != null) {
         if (first.isEmpty() != second.isEmpty()) {
            return false;
         } else if (first.getItem() != second.getItem()) {
            return false;
         } else {
            return first.getCount() > second.getCount()
               ? false
               : DataComponentPredicate.allOf(first.getComponents().filter(conditions.componentFilter())).test(second);
         }
      } else {
         return first == second;
      }
   }
}
