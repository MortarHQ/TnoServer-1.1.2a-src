package dev.latvian.mods.kubejs.recipe.match;

import dev.latvian.mods.kubejs.plugin.builtin.wrapper.IngredientWrapper;
import dev.latvian.mods.rhino.Context;
import net.minecraft.world.item.crafting.Ingredient;

public interface ReplacementMatch {
   ReplacementMatch NONE = new ReplacementMatch() {
      @Override
      public String toString() {
         return "NONE";
      }
   };

   static ReplacementMatch wrap(Context cx, Object o) {
      return (ReplacementMatch)(switch (o) {
         case null -> NONE;
         case ReplacementMatch m -> m;
         default -> {
            Ingredient in = IngredientWrapper.wrap(cx, o);
            yield in.isEmpty() ? NONE : in;
         }
      });
   }
}
