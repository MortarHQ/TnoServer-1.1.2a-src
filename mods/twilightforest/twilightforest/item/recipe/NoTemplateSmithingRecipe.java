package twilightforest.item.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;
import twilightforest.init.TFRecipes;

public class NoTemplateSmithingRecipe implements SmithingRecipe {
   private final Ingredient base;
   private final Ingredient addition;
   private final List<TypedDataComponent<?>> additionalData;
   private static final Codec<List<TypedDataComponent<?>>> DATA_COMPONENT_CODEC = DataComponentMap.CODEC
      .xmap(typedDataComponents -> typedDataComponents.stream().toList(), typedDataComponents -> {
         Builder builder = DataComponentMap.builder();

         for (TypedDataComponent<?> typedDataComponent : typedDataComponents) {
            setComponent(typedDataComponent, builder);
         }

         return builder.build();
      });

   public NoTemplateSmithingRecipe(Ingredient base, Ingredient addition, List<TypedDataComponent<?>> additionalData) {
      this.base = base;
      this.addition = addition;
      this.additionalData = additionalData;
   }

   public boolean matches(SmithingRecipeInput input, Level level) {
      if (input.getItem(0).isEmpty() && this.base.test(input.getItem(1)) && this.addition.test(input.getItem(2))) {
         ItemStack armor = input.getItem(1);

         for (TypedDataComponent<?> data : this.additionalData) {
            if (armor.has(data.type())) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public ItemStack assemble(SmithingRecipeInput input, Provider access) {
      return (ItemStack)Util.make(input.getItem(1).copy(), this::setComponents);
   }

   public ItemStack getResultItem(Provider access) {
      return (ItemStack)Util.make(new ItemStack(Items.IRON_CHESTPLATE), this::setComponents);
   }

   public boolean isTemplateIngredient(ItemStack stack) {
      return stack.isEmpty();
   }

   public boolean isBaseIngredient(ItemStack stack) {
      return this.base.test(stack);
   }

   public boolean isAdditionIngredient(ItemStack stack) {
      return this.addition.test(stack);
   }

   public Ingredient getBase() {
      return this.base;
   }

   public Ingredient getAddition() {
      return this.addition;
   }

   private List<TypedDataComponent<?>> additionalData() {
      return this.additionalData;
   }

   private void setComponents(ItemStack itemstack) {
      for (TypedDataComponent<?> data : this.additionalData) {
         setComponent(data, itemstack);
      }
   }

   private static <T> void setComponent(TypedDataComponent<T> data, ItemStack stack) {
      stack.set(data.type(), data.value());
   }

   private static <T> void setComponent(TypedDataComponent<T> data, Builder builder) {
      builder.set(data.type(), data.value());
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)TFRecipes.NO_TEMPLATE_SMITHING_SERIALIZER.get();
   }

   public boolean isIncomplete() {
      return Stream.of(this.base, this.addition).anyMatch(Ingredient::hasNoItems);
   }

   public static class Serializer implements RecipeSerializer<NoTemplateSmithingRecipe> {
      private static final MapCodec<NoTemplateSmithingRecipe> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Ingredient.CODEC.fieldOf("base").forGetter(NoTemplateSmithingRecipe::getBase),
               Ingredient.CODEC.fieldOf("addition").forGetter(NoTemplateSmithingRecipe::getAddition),
               NoTemplateSmithingRecipe.DATA_COMPONENT_CODEC.optionalFieldOf("additional_data", List.of()).forGetter(NoTemplateSmithingRecipe::additionalData)
            )
            .apply(instance, NoTemplateSmithingRecipe::new)
      );
      private static final StreamCodec<RegistryFriendlyByteBuf, NoTemplateSmithingRecipe> STREAM_CODEC = StreamCodec.composite(
         Ingredient.CONTENTS_STREAM_CODEC,
         NoTemplateSmithingRecipe::getBase,
         Ingredient.CONTENTS_STREAM_CODEC,
         NoTemplateSmithingRecipe::getAddition,
         TypedDataComponent.STREAM_CODEC.apply(ByteBufCodecs.list()),
         NoTemplateSmithingRecipe::additionalData,
         NoTemplateSmithingRecipe::new
      );

      public MapCodec<NoTemplateSmithingRecipe> codec() {
         return CODEC;
      }

      public StreamCodec<RegistryFriendlyByteBuf, NoTemplateSmithingRecipe> streamCodec() {
         return STREAM_CODEC;
      }
   }
}
