package net.blay09.mods.waystones.requirement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ItemContainerContents;

public class InventoryItemResolver {
   private static final int MAX_NESTING_DEPTH = 32;
   public static final List<InventoryItemResolver.NestedInventoryComponentHandler> NESTED_COMPONENT_HANDLERS = List.of(
      new InventoryItemResolver.NestedInventoryComponentHandler(DataComponents.CONTAINER, parentStack -> {
         ItemContainerContents containerContents = (ItemContainerContents)parentStack.get(DataComponents.CONTAINER);
         return containerContents != null ? containerContents.nonEmptyItems() : List.of();
      }, parentStack -> {
         ItemContainerContents containerContents = (ItemContainerContents)parentStack.get(DataComponents.CONTAINER);
         return containerContents != null ? containerContents.stream().collect(Collectors.toCollection(ArrayList::new)) : List.of();
      }, (parentStack, items) -> parentStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items))),
      new InventoryItemResolver.NestedInventoryComponentHandler(DataComponents.BUNDLE_CONTENTS, parentStack -> {
         BundleContents bundleContents = (BundleContents)parentStack.get(DataComponents.BUNDLE_CONTENTS);
         return bundleContents != null ? bundleContents.items() : List.of();
      }, parentStack -> {
         BundleContents bundleContents = (BundleContents)parentStack.get(DataComponents.BUNDLE_CONTENTS);
         return bundleContents != null ? bundleContents.itemCopyStream().collect(Collectors.toCollection(ArrayList::new)) : List.of();
      }, (parentStack, items) -> {
         List<ItemStack> compactedBundleItems = items.stream().filter(it -> !it.isEmpty()).toList();
         parentStack.set(DataComponents.BUNDLE_CONTENTS, new BundleContents(compactedBundleItems));
      })
   );

   public static int countMatchingInPlayerInventory(Player player, Predicate<ItemStack> matcher) {
      int count = 0;

      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         ItemStack slotStack = player.getInventory().getItem(i);
         if (!slotStack.isEmpty()) {
            if (matcher.test(slotStack)) {
               count += slotStack.getCount();
            }

            count += countMatchingInNestedContainers(slotStack, matcher, 0);
         }
      }

      return count;
   }

   public static int consumeFromPlayerInventory(Player player, Predicate<ItemStack> matcher, int count) {
      if (count <= 0) {
         return 0;
      }

      int consumed = 0;

      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         ItemStack slotStack = player.getInventory().getItem(i);
         if (!slotStack.isEmpty() && matcher.test(slotStack)) {
            int toConsume = Math.min(slotStack.getCount(), count - consumed);
            slotStack.shrink(toConsume);
            consumed += toConsume;
            if (consumed >= count) {
               player.getInventory().setChanged();
               return consumed;
            }
         }
      }

      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         ItemStack slotStack = player.getInventory().getItem(i);
         if (!slotStack.isEmpty()) {
            consumed += consumeFromNestedContainers(slotStack, matcher, count - consumed, 0);
            if (consumed >= count) {
               player.getInventory().setChanged();
               return consumed;
            }
         }
      }

      player.getInventory().setChanged();
      return consumed;
   }

   private static int countMatchingInNestedContainers(ItemStack parentStack, Predicate<ItemStack> matcher, int depth) {
      if (depth < 32 && !parentStack.isEmpty()) {
         int count = 0;

         for (InventoryItemResolver.NestedInventoryComponentHandler componentHandler : NESTED_COMPONENT_HANDLERS) {
            for (ItemStack childStack : componentHandler.nonEmptyItems().apply(parentStack)) {
               if (!childStack.isEmpty()) {
                  if (matcher.test(childStack)) {
                     count += childStack.getCount();
                  }

                  count += countMatchingInNestedContainers(childStack, matcher, depth + 1);
               }
            }
         }

         return count;
      } else {
         return 0;
      }
   }

   private static int consumeFromNestedContainers(ItemStack parentStack, Predicate<ItemStack> matcher, int count, int depth) {
      if (count > 0 && depth < 32 && !parentStack.isEmpty()) {
         int consumed = 0;

         for (InventoryItemResolver.NestedInventoryComponentHandler componentHandler : NESTED_COMPONENT_HANDLERS) {
            List<ItemStack> componentItems = componentHandler.mutableItems().apply(parentStack);
            boolean changed = false;

            for (ItemStack childStack : componentItems) {
               if (!childStack.isEmpty() && matcher.test(childStack)) {
                  int toConsume = Math.min(childStack.getCount(), count - consumed);
                  childStack.shrink(toConsume);
                  consumed += toConsume;
                  changed = true;
                  if (consumed >= count) {
                     break;
                  }
               }
            }

            if (consumed < count) {
               for (ItemStack childStack : componentItems) {
                  if (!childStack.isEmpty()) {
                     int nestedConsumed = consumeFromNestedContainers(childStack, matcher, count - consumed, depth + 1);
                     if (nestedConsumed > 0) {
                        changed = true;
                        consumed += nestedConsumed;
                     }

                     if (consumed >= count) {
                        break;
                     }
                  }
               }
            }

            if (changed) {
               componentHandler.writeBack().accept(parentStack, componentItems);
            }

            if (consumed >= count) {
               return consumed;
            }
         }

         return consumed;
      } else {
         return 0;
      }
   }

   public record NestedInventoryComponentHandler(
      DataComponentType<?> componentType,
      Function<ItemStack, Iterable<ItemStack>> nonEmptyItems,
      Function<ItemStack, List<ItemStack>> mutableItems,
      BiConsumer<ItemStack, List<ItemStack>> writeBack
   ) {
   }
}
