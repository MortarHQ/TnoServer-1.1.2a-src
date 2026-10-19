package team.lodestar.lodestone.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.Nonnull;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import team.lodestar.lodestone.registry.common.LodestoneRecipeSerializers;

public class NBTCarryRecipe extends ShapedRecipe {
   public static final String NAME = "nbt_carry";
   public final Ingredient copyFrom;

   public NBTCarryRecipe(ShapedRecipe recipe, Ingredient copyFrom) {
      super(recipe.getGroup(), recipe.category(), recipe.pattern, recipe.result, recipe.showNotification());
      this.copyFrom = copyFrom;
   }

   public ItemStack assemble(CraftingInput craftingInput, Provider provider) {
      ItemStack output = super.assemble(craftingInput, provider);

      for (int i = 0; i < craftingInput.size(); i++) {
         ItemStack stack = craftingInput.getItem(i);
         if (!stack.isEmpty() && this.copyFrom.test(stack) && !stack.getComponents().isEmpty()) {
            for (TypedDataComponent<?> component : stack.getComponents()) {
               output.copyFrom(stack, new DataComponentType[]{component.type()});
            }
            break;
         }
      }

      return output;
   }

   @Nonnull
   public RecipeSerializer<?> getSerializer() {
      return LodestoneRecipeSerializers.NBT_CARRY_RECIPE_SERIALIZER.get();
   }

   public static class Serializer implements RecipeSerializer<NBTCarryRecipe> {
      public static final MapCodec<NBTCarryRecipe> CODEC = RecordCodecBuilder.mapCodec(
         obj -> obj.group(
               net.minecraft.world.item.crafting.ShapedRecipe.Serializer.CODEC.fieldOf("recipe").forGetter(recipe -> recipe),
               Ingredient.CODEC.fieldOf("copyFrom").forGetter(recipe -> recipe.copyFrom)
            )
            .apply(obj, NBTCarryRecipe::new)
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, NBTCarryRecipe> STREAM_CODEC = StreamCodec.of(
         NBTCarryRecipe.Serializer::toNetwork, NBTCarryRecipe.Serializer::fromNetwork
      );

      public MapCodec<NBTCarryRecipe> codec() {
         return CODEC;
      }

      public StreamCodec<RegistryFriendlyByteBuf, NBTCarryRecipe> streamCodec() {
         return STREAM_CODEC;
      }

      public static NBTCarryRecipe fromNetwork(RegistryFriendlyByteBuf byteBuf) {
         ShapedRecipe recipe = (ShapedRecipe)net.minecraft.world.item.crafting.ShapedRecipe.Serializer.STREAM_CODEC.decode(byteBuf);
         Ingredient copyFrom = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(byteBuf);
         return new NBTCarryRecipe(recipe, copyFrom);
      }

      public static void toNetwork(RegistryFriendlyByteBuf byteBuf, @Nonnull NBTCarryRecipe recipe) {
         net.minecraft.world.item.crafting.ShapedRecipe.Serializer.STREAM_CODEC.encode(byteBuf, recipe);
         Ingredient.CONTENTS_STREAM_CODEC.encode(byteBuf, recipe.copyFrom);
      }
   }
}
