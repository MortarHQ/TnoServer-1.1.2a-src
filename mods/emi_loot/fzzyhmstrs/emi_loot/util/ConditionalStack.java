package fzzyhmstrs.emi_loot.util;

import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;

public record ConditionalStack(List<Tuple<Integer, Component>> conditions, float weight, ConditionalStack.ListProvider ingredient) {
   public ConditionalStack(List<Tuple<Integer, Component>> conditions, float weight, List<ItemStack> list) {
      this(conditions, weight, new ConditionalStack.ListProvider(list));
   }

   public EmiIngredient getIngredient() {
      return this.ingredient.getList().size() == 1 ? (EmiIngredient)this.ingredient.getList().get(0) : new QuantityListEmiIngredient(this.ingredient.getList());
   }

   public List<EmiStack> getStacks() {
      return this.ingredient.getList();
   }

   public List<ItemStack> getRawStacks() {
      return this.ingredient.getRawList();
   }

   static class ListProvider {
      private List<ItemStack> list;
      private List<EmiStack> builtList;

      public ListProvider(List<ItemStack> list) {
         this.list = list;
      }

      public List<EmiStack> getList() {
         if (this.builtList == null) {
            this.builtList = this.list.stream().<EmiStack>map(EmiStack::of).toList();
            this.list = null;
         }

         return this.builtList;
      }

      public List<ItemStack> getRawList() {
         return this.list == null ? this.builtList.stream().<ItemStack>map(EmiStack::getItemStack).toList() : this.list;
      }
   }
}
