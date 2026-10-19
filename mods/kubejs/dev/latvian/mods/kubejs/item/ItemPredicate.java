package dev.latvian.mods.kubejs.item;

import dev.latvian.mods.kubejs.core.IngredientSupplierKJS;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.IngredientWrapper;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.ItemWrapper;
import dev.latvian.mods.rhino.BaseFunction;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

@RemapPrefixForJS("kjs$")
public interface ItemPredicate extends Predicate<ItemStack>, IngredientSupplierKJS {
   TypeInfo TYPE_INFO = TypeInfo.of(ItemPredicate.class);
   ItemPredicate NONE = stack -> false;
   ItemPredicate ALL = stack -> true;

   boolean test(ItemStack itemStack);

   private static ItemPredicate simplify(Ingredient in) {
      return (ItemPredicate)(in.isEmpty() ? NONE : (in.kjs$isWildcard() ? ALL : in));
   }

   static ItemPredicate wrap(Context cx, Object from) {
      return switch (from) {
         case null -> NONE;
         case BaseFunction func -> (ItemPredicate)cx.createInterfaceAdapter(TYPE_INFO, func);
         case String s -> {
            switch (s) {
               case "*":
                  yield ALL;
               case "":
               case "-":
                  yield NONE;
               case String s1 when s1.isBlank():
                  yield NONE;
               default:
                  yield simplify(IngredientWrapper.wrap(cx, from));
            }
         }
         default -> simplify(IngredientWrapper.wrap(cx, from));
      };
   }

   default boolean kjs$testItem(Item item) {
      return this.test(item.getDefaultInstance());
   }

   default ItemStack[] kjs$getStackArray() {
      return ItemWrapper.getList().stream().filter(this).toArray(ItemStack[]::new);
   }

   default ItemStackSet kjs$getStacks() {
      return new ItemStackSet(this.kjs$getStackArray());
   }

   default ItemStackSet kjs$getDisplayStacks() {
      ItemStackSet set = new ItemStackSet();

      for (ItemStack stack : ItemWrapper.getList()) {
         if (this.test(stack)) {
            set.add(stack);
         }
      }

      return set;
   }

   default boolean kjs$isWildcard() {
      return this == ALL;
   }

   default Stream<Item> kjs$getItemStream() {
      return Arrays.stream(this.kjs$getStackArray()).map(ItemStack::getItem);
   }

   default Set<Item> kjs$getItemTypes() {
      ItemStack[] items = this.kjs$getStackArray();
      if (items.length == 1 && !items[0].isEmpty()) {
         return Set.of(items[0].getItem());
      }

      LinkedHashSet<Item> set = new LinkedHashSet<>(items.length);

      for (ItemStack stack : items) {
         if (!stack.isEmpty()) {
            set.add(stack.getItem());
         }
      }

      return set;
   }

   default Set<String> kjs$getItemIds() {
      ItemStack[] items = this.kjs$getStackArray();
      if (items.length == 1 && !items[0].isEmpty()) {
         return Set.of(items[0].kjs$getId());
      }

      LinkedHashSet<String> ids = new LinkedHashSet<>(items.length);

      for (ItemStack item : items) {
         if (!item.isEmpty()) {
            ids.add(item.kjs$getId());
         }
      }

      return ids;
   }

   default ItemStack kjs$getFirst() {
      for (ItemStack stack : this.kjs$getStackArray()) {
         if (!stack.isEmpty()) {
            return stack;
         }
      }

      return ItemStack.EMPTY;
   }

   default boolean kjs$canBeUsedForMatching() {
      return true;
   }

   @Override
   default Ingredient kjs$asIngredient() {
      return Ingredient.of(this.kjs$getStackArray());
   }
}
