package twilightforest.data.custom;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

abstract class AbstractCartesianRecipeBuilder<R> {
   protected final Predicate<Ingredient> shouldSplit;
   private final List<List<Ingredient>> slots = new ArrayList<>();

   protected AbstractCartesianRecipeBuilder(Predicate<Ingredient> shouldSplit) {
      this.shouldSplit = shouldSplit;
   }

   protected List<Ingredient> wrap(Ingredient ingredient) {
      return this.shouldSplit.test(ingredient)
         ? Arrays.stream(ingredient.getItems()).map(xva$0 -> Ingredient.of(new ItemStack[]{xva$0})).toList()
         : List.of(ingredient);
   }

   protected void addSlot(Ingredient ingredient) {
      this.slots.add(this.wrap(ingredient));
   }

   protected List<List<Ingredient>> getSlots() {
      return this.slots;
   }

   protected abstract R assemble(List<Ingredient> var1);

   public Iterable<R> build() {
      return () -> Iterators.transform(Lists.cartesianProduct(this.getSlots()).iterator(), this::assemble);
   }
}
