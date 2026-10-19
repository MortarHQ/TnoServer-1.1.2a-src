package net.unusual.block_factorys_bosses.data.util;

import java.util.concurrent.CompletableFuture;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class BossesRiseCraftingProvider extends RecipeProvider {
   public BossesRiseCraftingProvider(PackOutput output, CompletableFuture<Provider> registries) {
      super(output, registries);
   }

   protected abstract void buildRecipes(RecipeOutput var1, Provider var2);

   protected ItemStack enchantedStack(ItemLike item, ResourceKey<Enchantment> enchantment, int level, Provider registries) {
      ItemStack stack = new ItemStack(item);
      stack.enchant((Holder)((RegistryLookup)registries.lookup(Registries.ENCHANTMENT).orElseThrow()).get(enchantment).orElseThrow(), level);
      return stack;
   }
}
