package dev.ftb.mods.ftbxmodcompat.ftbquests.recipemod_common;

import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftbquests.quest.loot.LootCrate;
import dev.ftb.mods.ftbquests.quest.loot.WeightedReward;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class WrappedLootCrate {
   public static final int ITEMSX = 8;
   public static final int ITEMSY = 6;
   public static final int ITEMS = 48;
   public final LootCrate crate;
   public final ItemStack crateStack;
   public final List<WeightedReward> sortedRewards;
   public final List<ItemStack> outputs;
   private final List<List<ItemStack>> cycledOutputs;

   public WrappedLootCrate(LootCrate c) {
      this.crate = c;
      this.crateStack = this.crate.createStack();
      this.outputs = new ArrayList<>(c.getTable().getWeightedRewards().size());
      this.sortedRewards = c.getTable().getWeightedRewards().stream().sorted(WeightedReward::compareTo).toList();

      for (WeightedReward reward : this.sortedRewards) {
         Object object = reward.getReward().getIcon().getIngredient();
         ItemStack stack = object instanceof ItemStack ? (ItemStack)object : ItemStack.EMPTY;
         if (!stack.isEmpty()) {
            this.outputs.add(stack.copy());
         } else if (reward.getReward().getIcon() instanceof ItemIcon) {
            stack = ((ItemIcon)reward.getReward().getIcon()).getStack().copy();
            stack.set(DataComponents.CUSTOM_NAME, reward.getReward().getTitle());
            this.outputs.add(stack);
         } else {
            stack = new ItemStack(Items.PAINTING);
            stack.set(DataComponents.CUSTOM_NAME, reward.getReward().getTitle());
            this.outputs.add(stack);
         }
      }

      if (this.outputs.size() <= 48) {
         this.cycledOutputs = new ArrayList<>(this.outputs.size());

         for (ItemStack stack : this.outputs) {
            this.cycledOutputs.add(List.of(stack));
         }
      } else {
         this.cycledOutputs = new ArrayList<>(48);

         for (int i = 0; i < 48; i++) {
            this.cycledOutputs.add(new ArrayList<>());
         }

         for (int i = 0; i < this.outputs.size(); i++) {
            this.cycledOutputs.get(i % 48).add(this.outputs.get(i));
         }
      }
   }

   public List<Ingredient> inputIngredients() {
      return List.of(Ingredient.of(new ItemStack[]{this.crateStack}));
   }

   public List<Ingredient> outputIngredients() {
      return this.cycledOutputs.stream().map(items -> Ingredient.of(items.toArray(new ItemStack[0]))).toList();
   }
}
