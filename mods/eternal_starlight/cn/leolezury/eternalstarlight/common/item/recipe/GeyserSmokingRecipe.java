package cn.leolezury.eternalstarlight.common.item.recipe;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESRecipeSerializers;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public record GeyserSmokingRecipe(Item input, int inputCount, ItemStack output) implements Recipe<SingleRecipeInput> {
   public boolean matches(SingleRecipeInput container, Level level) {
      return container.getItem(0).is(this.input()) && container.getItem(0).getCount() >= this.inputCount();
   }

   public ItemStack assemble(SingleRecipeInput container, Provider provider) {
      return this.output().copy();
   }

   public boolean canCraftInDimensions(int i, int j) {
      return true;
   }

   public ItemStack getResultItem(Provider provider) {
      return this.output();
   }

   public RecipeSerializer<?> getSerializer() {
      return ESRecipeSerializers.GEYSER_SMOKING.get();
   }

   public RecipeType<?> getType() {
      return ESRecipes.GEYSER_SMOKING.get();
   }

   public static class Serializer implements RecipeSerializer<GeyserSmokingRecipe> {
      private static final MapCodec<GeyserSmokingRecipe> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               BuiltInRegistries.ITEM.byNameCodec().fieldOf("input").forGetter(GeyserSmokingRecipe::input),
               Codec.INT.fieldOf("input_count").forGetter(GeyserSmokingRecipe::inputCount),
               ItemStack.OPTIONAL_CODEC.fieldOf("output").forGetter(GeyserSmokingRecipe::output)
            )
            .apply(instance, GeyserSmokingRecipe::new)
      );

      public MapCodec<GeyserSmokingRecipe> codec() {
         return CODEC;
      }

      public StreamCodec<RegistryFriendlyByteBuf, GeyserSmokingRecipe> streamCodec() {
         return new StreamCodec<RegistryFriendlyByteBuf, GeyserSmokingRecipe>() {
            public GeyserSmokingRecipe decode(RegistryFriendlyByteBuf friendlyByteBuf) {
               Item input = (Item)friendlyByteBuf.readById(BuiltInRegistries.ITEM::byId);
               int inputCount = friendlyByteBuf.readInt();
               ItemStack output = (ItemStack)ItemStack.STREAM_CODEC.decode(friendlyByteBuf);
               return new GeyserSmokingRecipe(input, inputCount, output);
            }

            public void encode(RegistryFriendlyByteBuf friendlyByteBuf, GeyserSmokingRecipe recipe) {
               friendlyByteBuf.writeById(BuiltInRegistries.ITEM::getId, recipe.input());
               friendlyByteBuf.writeInt(recipe.inputCount());
               ItemStack.STREAM_CODEC.encode(friendlyByteBuf, recipe.output());
            }
         };
      }
   }

   public static class Type implements RecipeType<GeyserSmokingRecipe> {
      public static final ResourceLocation ID = EternalStarlight.id("geyser_smoking");

      @Override
      public String toString() {
         return ID.toString();
      }
   }
}
