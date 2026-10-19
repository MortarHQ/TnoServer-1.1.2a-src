package cn.leolezury.eternalstarlight.common.item.recipe;

import cn.leolezury.eternalstarlight.common.item.component.Accessory;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESRecipeSerializers;
import cn.leolezury.eternalstarlight.common.util.ESAccessoryUtil;
import java.util.List;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class AccessoryCombinationRecipe extends CustomRecipe {
   public AccessoryCombinationRecipe(CraftingBookCategory category) {
      super(category);
   }

   public boolean matches(CraftingInput recipeInput, Level level) {
      if (recipeInput.items().stream().filter(stack -> !stack.isEmpty()).count() > 2L) {
         return false;
      }

      List<ItemStack> accessory = recipeInput.items().stream().filter(stack -> stack.has(ESDataComponents.ACCESSORY.get())).toList();
      if (accessory.size() != 1) {
         return false;
      }

      List<ItemStack> equipment = recipeInput.items()
         .stream()
         .filter(
            stack -> {
               Accessory data = (Accessory)accessory.getFirst().get(ESDataComponents.ACCESSORY.get());
               if (data == null) {
                  return false;
               }

               List<ItemStack> accessories = (List<ItemStack>)stack.getOrDefault(ESDataComponents.ACCESSORIES.get(), List.of());
               return stack.is(data.combinationTarget())
                  && ESAccessoryUtil.getAccessorySlotCount(stack) > accessories.size()
                  && accessories.stream().noneMatch(s -> s.is(accessory.getFirst().getItem()));
            }
         )
         .toList();
      return equipment.size() == 1;
   }

   public ItemStack assemble(CraftingInput recipeInput, Provider provider) {
      List<ItemStack> accessory = recipeInput.items().stream().filter(stack -> stack.has(ESDataComponents.ACCESSORY.get())).toList();
      List<ItemStack> equipment = recipeInput.items()
         .stream()
         .filter(
            stack -> {
               Accessory data = (Accessory)accessory.getFirst().get(ESDataComponents.ACCESSORY.get());
               if (data == null) {
                  return false;
               }

               List<ItemStack> accessories = (List<ItemStack>)stack.getOrDefault(ESDataComponents.ACCESSORIES.get(), List.of());
               return stack.is(data.combinationTarget())
                  && ESAccessoryUtil.getAccessorySlotCount(stack) > accessories.size()
                  && accessories.stream().noneMatch(s -> s.is(accessory.getFirst().getItem()));
            }
         )
         .toList();
      if (accessory.size() == 1 && equipment.size() == 1) {
         ItemStack result = equipment.getFirst().copy();
         ESAccessoryUtil.applyAccessory(result, accessory.getFirst());
         return result;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public boolean canCraftInDimensions(int i, int j) {
      return true;
   }

   public RecipeSerializer<?> getSerializer() {
      return ESRecipeSerializers.ACCESSORY_COMBINATION.get();
   }
}
