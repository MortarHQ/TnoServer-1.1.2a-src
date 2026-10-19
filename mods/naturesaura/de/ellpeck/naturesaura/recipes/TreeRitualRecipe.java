package de.ellpeck.naturesaura.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class TreeRitualRecipe extends ModRecipe {
   public final Ingredient saplingType;
   public final List<Ingredient> ingredients;
   public final ItemStack output;
   public final int time;

   public TreeRitualRecipe(Ingredient saplingType, ItemStack output, int time, List<Ingredient> ingredients) {
      this.saplingType = saplingType;
      this.ingredients = ingredients;
      this.output = output;
      this.time = time;
   }

   public ItemStack getResultItem(Provider registries) {
      return this.output;
   }

   public RecipeSerializer<?> getSerializer() {
      return ModRecipes.TREE_RITUAL_SERIALIZER;
   }

   public RecipeType<?> getType() {
      return ModRecipes.TREE_RITUAL_TYPE;
   }

   public static class Serializer implements RecipeSerializer<TreeRitualRecipe> {
      private static final MapCodec<TreeRitualRecipe> CODEC = RecordCodecBuilder.mapCodec(
         i -> i.group(
               Ingredient.CODEC.fieldOf("sapling").forGetter(r -> r.saplingType),
               ItemStack.CODEC.fieldOf("output").forGetter(r -> r.output),
               Codec.INT.fieldOf("time").forGetter(r -> r.time),
               Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.ingredients)
            )
            .apply(i, TreeRitualRecipe::new)
      );
      private static final StreamCodec<RegistryFriendlyByteBuf, TreeRitualRecipe> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

      public MapCodec<TreeRitualRecipe> codec() {
         return CODEC;
      }

      public StreamCodec<RegistryFriendlyByteBuf, TreeRitualRecipe> streamCodec() {
         return STREAM_CODEC;
      }
   }
}
