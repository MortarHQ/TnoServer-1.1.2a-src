package dev.ftb.mods.ftbxmodcompat.ftbquests.recipemod_common;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.integration.item_filtering.ItemMatchingSystem;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.loot.RewardTable;
import dev.ftb.mods.ftbquests.quest.loot.WeightedReward;
import dev.ftb.mods.ftbquests.quest.reward.RandomReward;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.task.ItemTask;
import dev.ftb.mods.ftbquests.quest.task.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class WrappedQuest {
   public final Quest quest;
   public final List<List<ItemStack>> input;
   public final List<List<ItemStack>> output;

   public WrappedQuest(Quest q, List<Reward> rewards) {
      this.quest = q;
      this.input = new ArrayList<>(5);
      this.output = new ArrayList<>(5);
      if (this.quest.getTasks().size() == 1) {
         this.input.add(Collections.emptyList());
         this.input.add(Collections.emptyList());
         this.input.add(Collections.emptyList());
         this.input.add(Collections.emptyList());
      }

      for (Task task : this.quest.getTasks()) {
         if (task instanceof ItemTask itemTask) {
            this.input.add(Collections.singletonList(itemTask.getItemStack()));
         } else {
            Object object = task.getIcon().getIngredient();
            ItemStack stack = object instanceof ItemStack ? (ItemStack)object : ItemStack.EMPTY;
            if (!stack.isEmpty()) {
               this.input.add(List.copyOf(ItemMatchingSystem.INSTANCE.getAllMatchingStacks(stack, q.getQuestFile().holderLookup())));
            } else if (task.getIcon() instanceof ItemIcon itemIcon) {
               stack = itemIcon.getStack().copy();
               stack.set(DataComponents.CUSTOM_NAME, task.getTitle());
               this.input.add(Collections.singletonList(stack));
            } else {
               stack = new ItemStack(Items.PAINTING);
               stack.set(DataComponents.CUSTOM_NAME, task.getTitle());
               this.input.add(Collections.singletonList(stack));
            }
         }
      }

      if (rewards.size() == 1) {
         this.output.add(Collections.emptyList());
         this.output.add(Collections.emptyList());
         this.output.add(Collections.emptyList());
         this.output.add(Collections.emptyList());
      }

      for (Reward reward : rewards) {
         Object object = reward.getIcon().getIngredient();
         ItemStack stack = ItemStack.EMPTY;
         if (object instanceof ItemStack s) {
            stack = s;
         }

         if (!stack.isEmpty()) {
            this.output.add(Collections.singletonList(stack.copy()));
         } else if (reward instanceof RandomReward r) {
            RewardTable table = r.getTable();
            if (table != null) {
               Builder<ItemStack> builder = ImmutableList.builder();
               if (table.shouldShowTooltip()) {
                  ItemStack unknown = new ItemStack(Items.BARRIER);
                  unknown.set(DataComponents.CUSTOM_NAME, Component.literal("Unknown Reward"));
                  builder.add(unknown);
               } else {
                  for (WeightedReward wr : table.getWeightedRewards()) {
                     if (wr.getReward().getIcon().getIngredient() instanceof ItemStack s) {
                        builder.add(s);
                     }
                  }
               }

               this.output.add(builder.build());
            }
         } else if (reward.getIcon() instanceof ItemIcon itemIcon) {
            stack = itemIcon.getStack().copy();
            stack.set(DataComponents.CUSTOM_NAME, reward.getTitle());
            this.output.add(Collections.singletonList(stack));
         } else {
            stack = new ItemStack(Items.PAINTING);
            stack.set(DataComponents.CUSTOM_NAME, reward.getTitle());
            this.output.add(Collections.singletonList(stack));
         }
      }
   }

   public List<Ingredient> inputIngredients() {
      return this.input.stream().map(items -> Ingredient.of(items.toArray(new ItemStack[0]))).toList();
   }

   public List<Ingredient> outputIngredients() {
      return this.output.stream().map(items -> Ingredient.of(items.toArray(new ItemStack[0]))).toList();
   }

   public boolean hasInput(ItemStack stack) {
      return this.input.stream().flatMap(Collection::stream).anyMatch(stack1 -> ItemStack.isSameItemSameComponents(stack1, stack));
   }

   public boolean hasOutput(ItemStack stack) {
      return this.output.stream().flatMap(Collection::stream).anyMatch(stack1 -> ItemStack.isSameItemSameComponents(stack1, stack));
   }

   public void openQuestGui() {
      ClientQuestFile.openGui(this.quest, true);
   }
}
