package com.mortal_cultivation.data.recipe;

import com.mojang.serialization.MapCodec;
import com.mortal_cultivation.registry.item.CultivationItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public final class AncientScholarTableRecipe implements CraftingRecipe {
   private final ShapedRecipe delegate;

   public AncientScholarTableRecipe(ShapedRecipe delegate) {
      this.delegate = delegate;
   }

   public boolean matches(CraftingInput input, Level level) {
      return this.delegate.matches(input, level);
   }

   public ItemStack assemble(CraftingInput input, Provider registries) {
      return this.delegate.assemble(input, registries);
   }

   public boolean canCraftInDimensions(int width, int height) {
      return this.delegate.canCraftInDimensions(width, height);
   }

   public ItemStack getResultItem(Provider registries) {
      return this.delegate.getResultItem(registries);
   }

   public NonNullList<Ingredient> getIngredients() {
      return this.delegate.getIngredients();
   }

   public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
      NonNullList<ItemStack> remainingItems = this.delegate.getRemainingItems(input);

      for (int slot = 0; slot < input.size(); slot++) {
         ItemStack ingredient = input.getItem(slot);
         if (ingredient.is((Item)CultivationItems.BRUSH.get()) || ingredient.is((Item)CultivationItems.CINNABAR_BLOOD_INK.get())) {
            remainingItems.set(slot, ingredient.copyWithCount(1));
         }
      }

      return remainingItems;
   }

   public String getGroup() {
      return this.delegate.getGroup();
   }

   public CraftingBookCategory category() {
      return this.delegate.category();
   }

   public boolean showNotification() {
      return this.delegate.showNotification();
   }

   public boolean isIncomplete() {
      return this.delegate.isIncomplete();
   }

   public RecipeSerializer<?> getSerializer() {
      return AncientScholarTableRecipe.Serializer.INSTANCE;
   }

   private ShapedRecipe delegate() {
      return this.delegate;
   }

   public static final class Serializer implements RecipeSerializer<AncientScholarTableRecipe> {
      public static final AncientScholarTableRecipe.Serializer INSTANCE = new AncientScholarTableRecipe.Serializer();
      private static final MapCodec<AncientScholarTableRecipe> CODEC = net.minecraft.world.item.crafting.ShapedRecipe.Serializer.CODEC
         .xmap(AncientScholarTableRecipe::new, AncientScholarTableRecipe::delegate);
      private static final StreamCodec<RegistryFriendlyByteBuf, AncientScholarTableRecipe> STREAM_CODEC = net.minecraft.world.item.crafting.ShapedRecipe.Serializer.STREAM_CODEC
         .map(AncientScholarTableRecipe::new, AncientScholarTableRecipe::delegate);

      private Serializer() {
      }

      public MapCodec<AncientScholarTableRecipe> codec() {
         return CODEC;
      }

      public StreamCodec<RegistryFriendlyByteBuf, AncientScholarTableRecipe> streamCodec() {
         return STREAM_CODEC;
      }
   }
}
