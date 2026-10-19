package bigchadguys.sellingbin.util;

import bigchadguys.sellingbin.init.ModBlocks;
import bigchadguys.sellingbin.trade.Trade;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class TradeExecutor {
   public static TradeExecutor.Result test(Trade trade, AbstractContainerMenu handler) {
      TradeExecutor.Result result = new TradeExecutor.Result();
      result.setExpired(false);
      Map<Integer, ItemStack> slots = new LinkedHashMap<>();

      for (Slot slot : handler.slots) {
         slots.put(slot.index, slot.getItem().copy());
      }

      for (int i = 1; i == 1; i++) {
         Trade.Input input = trade.getInput();
         int left = input.getCount();
         List<Integer> affectedSlots = new ArrayList<>();

         for (Entry<Integer, ItemStack> entry : slots.entrySet()) {
            ItemStack stack = entry.getValue();
            if (left <= 0) {
               break;
            }

            if (input.getFilter().test(stack)) {
               int difference = Math.min(stack.getCount(), left);
               stack.shrink(difference);
               left -= difference;
               affectedSlots.add(entry.getKey());
            }
         }

         if (left <= 0) {
            result.addUsedSlots(affectedSlots);
            result.setInput(i, true);
         } else {
            result.addIncompleteSlots(affectedSlots);
            result.setInput(i, false);
         }
      }

      ItemStack output = trade.getOutput().generate().orElse(new ItemStack((ItemLike)ModBlocks.ERROR.get()));
      int left = output.getCount();

      for (Entry<Integer, ItemStack> entry : slots.entrySet()) {
         ItemStack stack = entry.getValue();
         if (left <= 0) {
            break;
         }

         if (stack.isEmpty() || ItemStack.isSameItemSameComponents(stack, output)) {
            int difference = Math.min(output.getMaxStackSize() - stack.getCount(), left);
            ItemStack copy = output.copy();
            copy.setCount(stack.getCount() + difference);
            left -= difference;
         }
      }

      result.setOutput(left <= 0);
      return result;
   }

   public static void execute(Trade trade, AbstractContainerMenu handler) {
      for (int i = 1; i == 1; i++) {
         Trade.Input input = trade.getInput();
         int left = input.getCount();

         for (Slot slot : handler.slots) {
            ItemStack stack = slot.getItem();
            if (left <= 0) {
               break;
            }

            if (input.getFilter().test(stack)) {
               int difference = Math.min(stack.getCount(), left);
               stack.shrink(difference);
               slot.setByPlayer(stack);
               left -= difference;
            }
         }
      }

      ItemStack output = trade.getOutput().generate().orElse(new ItemStack((ItemLike)ModBlocks.ERROR.get()));
      int left = output.getCount();

      for (Slot slot : handler.slots) {
         ItemStack stack = slot.getItem();
         if (left <= 0) {
            break;
         }

         if (stack.isEmpty() || ItemStack.isSameItemSameComponents(stack, output)) {
            int difference = Math.min(output.getMaxStackSize() - stack.getCount(), left);
            ItemStack copy = output.copy();
            copy.setCount(stack.getCount() + difference);
            slot.setByPlayer(copy);
            left -= difference;
         }
      }
   }

   public static class Result {
      private boolean[] inputs = new boolean[3];
      private boolean output;
      private boolean expired;
      private List<Integer> usedSlots = new ArrayList<>();
      private List<Integer> incompleteSlots = new ArrayList<>();

      public boolean checkInput(int index) {
         return this.inputs[index - 1];
      }

      public void setInput(int index, boolean completion) {
         this.inputs[index - 1] = completion;
      }

      public boolean checkOutput() {
         return this.output;
      }

      public void setOutput(boolean completion) {
         this.output = completion;
      }

      public boolean isExpired() {
         return this.expired;
      }

      public void setExpired(boolean expired) {
         this.expired = expired;
      }

      public List<Integer> getUsedSlots() {
         return this.usedSlots;
      }

      public void addUsedSlots(Collection<Integer> slots) {
         this.usedSlots.addAll(slots);
      }

      public List<Integer> getIncompleteSlots() {
         return this.incompleteSlots;
      }

      public void addIncompleteSlots(Collection<Integer> slots) {
         this.incompleteSlots.addAll(slots);
      }

      public boolean canTrade() {
         return this.inputs[0] && this.inputs[1] && this.inputs[2] && this.output && !this.expired;
      }
   }
}
