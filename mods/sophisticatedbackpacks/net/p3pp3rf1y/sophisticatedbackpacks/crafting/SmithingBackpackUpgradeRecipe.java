package net.p3pp3rf1y.sophisticatedbackpacks.crafting;

import java.util.Optional;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.crafting.IWrapperRecipe;
import net.p3pp3rf1y.sophisticatedcore.crafting.RecipeWrapperSerializer;

public class SmithingBackpackUpgradeRecipe extends SmithingTransformRecipe implements IWrapperRecipe<SmithingTransformRecipe> {
   private final SmithingTransformRecipe compose;

   public SmithingBackpackUpgradeRecipe(SmithingTransformRecipe compose) {
      super(compose.template, compose.base, compose.addition, compose.result);
      this.compose = compose;
   }

   public boolean isSpecial() {
      return true;
   }

   public ItemStack assemble(SmithingRecipeInput inv, Provider registryAccess) {
      ItemStack upgradedBackpack = this.result.copy();
      if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
         this.getBackpack(inv).map(ItemStack::getComponents).ifPresent(upgradedBackpack::applyComponents);
         IBackpackWrapper wrapper = BackpackWrapper.fromStack(upgradedBackpack);
         BackpackItem backpackItem = (BackpackItem)upgradedBackpack.getItem();
         wrapper.setSlotNumbers(backpackItem.getNumberOfSlots(), backpackItem.getNumberOfUpgradeSlots());
      }

      return upgradedBackpack;
   }

   private Optional<ItemStack> getBackpack(SmithingRecipeInput inv) {
      ItemStack slotStack = inv.getItem(1);
      return slotStack.getItem() instanceof BackpackItem ? Optional.of(slotStack) : Optional.empty();
   }

   public RecipeSerializer<?> getSerializer() {
      return ModItems.SMITHING_BACKPACK_UPGRADE_RECIPE_SERIALIZER.get();
   }

   public SmithingTransformRecipe getCompose() {
      return this.compose;
   }

   public static class Serializer extends RecipeWrapperSerializer<SmithingTransformRecipe, SmithingBackpackUpgradeRecipe> {
      public Serializer() {
         super(SmithingBackpackUpgradeRecipe::new, RecipeSerializer.SMITHING_TRANSFORM);
      }
   }
}
