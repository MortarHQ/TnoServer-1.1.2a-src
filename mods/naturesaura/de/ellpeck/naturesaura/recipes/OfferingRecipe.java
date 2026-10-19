package de.ellpeck.naturesaura.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class OfferingRecipe extends ModRecipe {
   public final Ingredient input;
   public final Ingredient startItem;
   public final ItemStack output;

   public OfferingRecipe(Ingredient input, Ingredient startItem, ItemStack output) {
      this.input = input;
      this.startItem = startItem;
      this.output = output;
   }

   public ItemStack getResultItem(Provider registries) {
      return this.output;
   }

   public RecipeSerializer<?> getSerializer() {
      return ModRecipes.OFFERING_SERIALIZER;
   }

   public RecipeType<?> getType() {
      return ModRecipes.OFFERING_TYPE;
   }

   public static class Serializer implements RecipeSerializer<OfferingRecipe> {
      private static final MapCodec<OfferingRecipe> CODEC = RecordCodecBuilder.mapCodec(
         i -> i.group(
               Ingredient.CODEC.fieldOf("input").forGetter(r -> r.input),
               Ingredient.CODEC.fieldOf("start_item").forGetter(r -> r.startItem),
               ItemStack.CODEC.fieldOf("output").forGetter(r -> r.output)
            )
            .apply(i, OfferingRecipe::new)
      );
      private static final StreamCodec<RegistryFriendlyByteBuf, OfferingRecipe> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

      public MapCodec<OfferingRecipe> codec() {
         return CODEC;
      }

      public StreamCodec<RegistryFriendlyByteBuf, OfferingRecipe> streamCodec() {
         return STREAM_CODEC;
      }
   }
}
