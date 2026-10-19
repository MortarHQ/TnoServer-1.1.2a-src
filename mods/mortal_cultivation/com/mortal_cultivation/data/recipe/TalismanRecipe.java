package com.mortal_cultivation.data.recipe;

import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class TalismanRecipe implements Recipe<TalismanRecipe.Input> {
   private final ItemStack output;
   private final NonNullList<Ingredient> ingredients;
   private final boolean consumesToolDurability;
   public static final int WIDTH = 7;

   public TalismanRecipe(ItemStack output, NonNullList<Ingredient> ingredients, boolean consumesToolDurability) {
      this.output = output;
      this.ingredients = ingredients;
      this.consumesToolDurability = consumesToolDurability;
      if (ingredients.size() != 7) {
         throw new JsonParseException("Talisman recipes must have exactly 7 ingredients!");
      }
   }

   public boolean matches(TalismanRecipe.Input container, Level level) {
      for (int i = 0; i < 7; i++) {
         if (!((Ingredient)this.ingredients.get(i)).test(container.getItem(i))) {
            return false;
         }
      }

      return true;
   }

   public ItemStack assemble(TalismanRecipe.Input container, Provider provider) {
      return this.output.copy();
   }

   public boolean canCraftInDimensions(int width, int height) {
      return true;
   }

   public ItemStack getResultItem(Provider provider) {
      return this.output.copy();
   }

   public RecipeSerializer<?> getSerializer() {
      return TalismanRecipe.Serializer.INSTANCE;
   }

   public RecipeType<?> getType() {
      return TalismanRecipe.Type.INSTANCE;
   }

   public NonNullList<Ingredient> getIngredients() {
      return this.ingredients;
   }

   public ItemStack getOutput() {
      return this.output;
   }

   public boolean consumesToolDurability() {
      return this.consumesToolDurability;
   }

   public record Input(Container container) implements RecipeInput {
      public ItemStack getItem(int index) {
         return this.container.getItem(index);
      }

      public int size() {
         return 7;
      }

      public boolean isEmpty() {
         for (int i = 0; i < 7; i++) {
            if (!this.container.getItem(i).isEmpty()) {
               return false;
            }
         }

         return true;
      }
   }

   public static class Serializer implements RecipeSerializer<TalismanRecipe> {
      public static final TalismanRecipe.Serializer INSTANCE = new TalismanRecipe.Serializer();
      public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "talisman_making");
      private static final MapCodec<TalismanRecipe> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               ItemStack.CODEC.fieldOf("result").forGetter(TalismanRecipe::getOutput),
               Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> List.copyOf(recipe.getIngredients())),
               Codec.BOOL.optionalFieldOf("consume_tool_durability", true).forGetter(TalismanRecipe::consumesToolDurability)
            )
            .apply(instance, (output, ingredients, consumesToolDurability) -> {
               NonNullList<Ingredient> inputs = NonNullList.create();
               inputs.addAll(ingredients);
               return new TalismanRecipe(output, inputs, consumesToolDurability);
            })
      );
      private static final StreamCodec<RegistryFriendlyByteBuf, TalismanRecipe> STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, TalismanRecipe>() {
         public TalismanRecipe decode(RegistryFriendlyByteBuf buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(7, Ingredient.EMPTY);

            for (int i = 0; i < 7; i++) {
               inputs.set(i, (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            }

            ItemStack output = (ItemStack)ItemStack.STREAM_CODEC.decode(buffer);
            boolean consumesToolDurability = buffer.readBoolean();
            return new TalismanRecipe(output, inputs, consumesToolDurability);
         }

         public void encode(RegistryFriendlyByteBuf buffer, TalismanRecipe recipe) {
            for (Ingredient ingredient : recipe.ingredients) {
               Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
            buffer.writeBoolean(recipe.consumesToolDurability);
         }
      };

      public MapCodec<TalismanRecipe> codec() {
         return CODEC;
      }

      public StreamCodec<RegistryFriendlyByteBuf, TalismanRecipe> streamCodec() {
         return STREAM_CODEC;
      }
   }

   public static class Type implements RecipeType<TalismanRecipe> {
      public static final TalismanRecipe.Type INSTANCE = new TalismanRecipe.Type();
      public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "talisman_making");

      private Type() {
      }
   }
}
