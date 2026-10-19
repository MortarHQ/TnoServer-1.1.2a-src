package net.p3pp3rf1y.sophisticatedcore.crafting;

import java.util.Optional;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.p3pp3rf1y.sophisticatedcore.init.ModRecipes;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem;

public class UpgradeNextTierRecipe extends ShapedRecipe implements IWrapperRecipe<ShapedRecipe> {
   private final ShapedRecipe compose;

   public UpgradeNextTierRecipe(ShapedRecipe compose) {
      super(compose.getGroup(), compose.category(), compose.pattern, compose.result);
      this.compose = compose;
   }

   public ShapedRecipe getCompose() {
      return this.compose;
   }

   public ItemStack assemble(CraftingInput inv, Provider registries) {
      ItemStack nextTier = super.assemble(inv, registries);
      this.getUpgrade(inv).ifPresent(upgrade -> nextTier.components.setAll(upgrade.getComponents()));
      return nextTier;
   }

   private Optional<ItemStack> getUpgrade(CraftingInput inv) {
      for (int slot = 0; slot < inv.size(); slot++) {
         ItemStack slotStack = inv.getItem(slot);
         if (slotStack.getItem() instanceof IUpgradeItem) {
            return Optional.of(slotStack);
         }
      }

      return Optional.empty();
   }

   public boolean isSpecial() {
      return true;
   }

   public RecipeSerializer<?> getSerializer() {
      return ModRecipes.UPGRADE_NEXT_TIER_SERIALIZER.get();
   }

   public static class Serializer extends RecipeWrapperSerializer<ShapedRecipe, UpgradeNextTierRecipe> {
      public Serializer() {
         super(UpgradeNextTierRecipe::new, RecipeSerializer.SHAPED_RECIPE);
      }
   }
}
