package com.almostreliable.lootjs.core.filters;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.common.ItemAbility;

public class ItemFilterImpl {
   public record AllOf(ItemFilter[] itemFilters, ItemFilter composite) implements ItemFilter {
      public static ItemFilter compose(ItemFilter[] itemFilters) {
         return switch (itemFilters.length) {
            case 0 -> ItemFilter.ANY;
            case 1 -> itemFilters[0];
            case 2 -> {
               ItemFilter itemFilter1 = itemFilters[0];
               ItemFilter itemFilter2 = itemFilters[1];
               yield itemStack -> itemFilter1.test(itemStack) && itemFilter2.test(itemStack);
            }
            default -> itemStack -> {
               for (ItemFilter itemFilter : itemFilters) {
                  if (!itemFilter.test(itemStack)) {
                     return false;
                  }
               }

               return true;
            };
         };
      }

      @Override
      public boolean test(ItemStack itemStack) {
         return this.composite.test(itemStack);
      }

      public ItemFilter[] itemFilters() {
         return (ItemFilter[])this.itemFilters.clone();
      }
   }

   public record AllOfToolAction(List<ItemAbility> toolActions, Predicate<ItemStack> composite) implements ItemFilter {
      public static Predicate<ItemStack> compose(List<ItemAbility> toolActions) {
         if (toolActions.size() == 1) {
            ItemAbility action = toolActions.getFirst();
            return itemStack -> itemStack.canPerformAction(action);
         } else if (toolActions.isEmpty()) {
            return itemStack -> true;
         } else if (toolActions.size() == 2) {
            ItemAbility action1 = toolActions.get(0);
            ItemAbility action2 = toolActions.get(1);
            return itemStack -> itemStack.canPerformAction(action1) && itemStack.canPerformAction(action2);
         } else {
            return itemStack -> {
               for (ItemAbility action : toolActions) {
                  if (!itemStack.canPerformAction(action)) {
                     return false;
                  }
               }

               return true;
            };
         }
      }

      @Override
      public boolean test(ItemStack itemStack) {
         return this.composite.test(itemStack);
      }
   }

   public record AnyOf(ItemFilter[] itemFilters, ItemFilter composite) implements ItemFilter {
      public static ItemFilter compose(ItemFilter[] itemFilters) {
         return switch (itemFilters.length) {
            case 0 -> ItemFilter.NONE;
            case 1 -> itemFilters[0];
            case 2 -> {
               ItemFilter itemFilter1 = itemFilters[0];
               ItemFilter itemFilter2 = itemFilters[1];
               yield itemStack -> itemFilter1.test(itemStack) || itemFilter2.test(itemStack);
            }
            default -> itemStack -> {
               for (ItemFilter itemFilter : itemFilters) {
                  if (itemFilter.test(itemStack)) {
                     return true;
                  }
               }

               return false;
            };
         };
      }

      @Override
      public boolean test(ItemStack itemStack) {
         return this.composite.test(itemStack);
      }

      public ItemFilter[] itemFilters() {
         return (ItemFilter[])this.itemFilters.clone();
      }
   }

   public record AnyOfToolAction(List<ItemAbility> toolActions, Predicate<ItemStack> composite) implements ItemFilter {
      public static Predicate<ItemStack> compose(List<ItemAbility> toolActions) {
         if (toolActions.isEmpty()) {
            return itemStack -> true;
         } else if (toolActions.size() == 1) {
            ItemAbility action = toolActions.getFirst();
            return itemStack -> itemStack.canPerformAction(action);
         } else if (toolActions.size() == 2) {
            ItemAbility action1 = toolActions.get(0);
            ItemAbility action2 = toolActions.get(1);
            return itemStack -> itemStack.canPerformAction(action1) || itemStack.canPerformAction(action2);
         } else {
            return itemStack -> {
               for (ItemAbility action : toolActions) {
                  if (itemStack.canPerformAction(action)) {
                     return true;
                  }
               }

               return false;
            };
         }
      }

      @Override
      public boolean test(ItemStack itemStack) {
         return this.composite.test(itemStack);
      }
   }

   public record ByIngredient(Ingredient ingredient) implements ItemFilter {
      @Override
      public boolean test(ItemStack itemStack) {
         return this.ingredient.test(itemStack);
      }
   }

   public record ByItem(ItemStack filter, boolean checkComponents) implements ItemFilter {
      @Override
      public boolean test(ItemStack itemStack) {
         return this.checkComponents ? ItemStack.isSameItemSameComponents(itemStack, this.filter) : itemStack.getItem() == this.filter.getItem();
      }
   }

   public record ByTag(TagKey<Item> tag) implements ItemFilter {
      @Override
      public boolean test(ItemStack itemStack) {
         return itemStack.is(this.tag);
      }
   }

   public record Custom(Predicate<ItemStack> predicate, @Nullable String description) implements ItemFilter {
      @Override
      public boolean test(ItemStack itemStack) {
         return this.predicate.test(itemStack);
      }
   }

   public record HasComponent(DataComponentType<?>[] types) implements ItemFilter {
      @Override
      public boolean test(ItemStack itemStack) {
         for (DataComponentType<?> type : this.types) {
            if (!itemStack.has(type)) {
               return false;
            }
         }

         return true;
      }
   }

   public record HasEnchantment(IdFilter filter, Ints levelBounds, DataComponentType<ItemEnchantments> type) implements ItemFilter {
      @Override
      public boolean test(ItemStack itemStack) {
         ItemEnchantments enchantments = (ItemEnchantments)itemStack.get(this.type);
         return enchantments == null ? false : this.hasEnchantmentsInComponent(enchantments);
      }

      public boolean hasEnchantmentsInComponent(ItemEnchantments enchantments) {
         for (Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
            boolean matches = ((Holder)entry.getKey()).unwrapKey().filter(key -> this.filter.test(key.location())).isPresent();
            if (matches && this.levelBounds.matches(entry.getIntValue())) {
               return true;
            }
         }

         return false;
      }
   }

   public record IsEquipmentSlot(EquipmentSlot equipmentSlot) implements ItemFilter {
      @Override
      public boolean test(ItemStack itemStack) {
         return itemStack.getEquipmentSlot() == this.equipmentSlot;
      }
   }

   public record IsEquipmentSlotGroup(EquipmentSlotGroup equipmentSlotGroup) implements ItemFilter {
      @Override
      public boolean test(ItemStack itemStack) {
         EquipmentSlot equipmentSlot = itemStack.getEquipmentSlot();
         return equipmentSlot == null ? false : this.equipmentSlotGroup.test(equipmentSlot);
      }
   }

   public record Not(ItemFilter itemFilter) implements ItemFilter {
      @Override
      public boolean test(ItemStack itemStack) {
         return !this.itemFilter.test(itemStack);
      }
   }
}
