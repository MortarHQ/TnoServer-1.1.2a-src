package com.almostreliable.lootjs.kube.wrappers;

import com.almostreliable.lootjs.core.filters.ItemFilter;
import com.almostreliable.lootjs.core.filters.ItemFilterImpl;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.IngredientWrapper;
import dev.latvian.mods.rhino.Context;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public class ItemFilterWrapper {
   private static ItemFilter ofItemFilterSingle(Context cx, @Nullable Object o) {
      if (o instanceof ItemFilter i) {
         return i;
      } else {
         if (o instanceof String str && !str.isEmpty()) {
            String first = str.substring(0, 1);
            String remaining = str.substring(1);
            switch (first) {
               case "*":
                  return ItemFilter.ANY;
               case "#":
                  ResourceLocation location = ResourceLocation.parse(remaining);
                  TagKey<Item> tag = TagKey.create(Registries.ITEM, location);
                  return new ItemFilterImpl.ByTag(tag);
               case "@":
                  return itemStack -> {
                     ResourceLocation key = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
                     return key.getNamespace().equals(remaining);
                  };
               case "!":
                  return ofItemFilterSingle(cx, remaining.trim()).negate();
            }
         }

         Ingredient ingredient = IngredientWrapper.wrap(cx, o);
         return ingredient.isEmpty() ? ItemFilter.EMPTY : new ItemFilterImpl.ByIngredient(ingredient);
      }
   }

   public static ItemFilter ofItemFilter(Context cx, Object o) {
      if (!(o instanceof List<?> list)) {
         return ofItemFilterSingle(cx, o);
      } else {
         List<ItemFilter> filters = new ArrayList<>(list.size());

         for (Object entry : list) {
            ItemFilter filter = ofItemFilter(cx, entry);
            filters.add(filter);
         }

         return itemStack -> {
            for (ItemFilter filterx : filters) {
               if (filterx.test(itemStack)) {
                  return true;
               }
            }

            return false;
         };
      }
   }
}
