package cn.leolezury.eternalstarlight.common.item.recipe;

import cn.leolezury.eternalstarlight.common.registry.ESRecipeSerializers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class ToolModificationRecipe extends CustomRecipe {
   private final Item tool;
   private final Item input;
   private final ItemStack output;

   public ToolModificationRecipe(CraftingBookCategory category, Item tool, Item input, ItemStack output) {
      super(category);
      this.tool = tool;
      this.input = input;
      this.output = output;
   }

   public boolean matches(CraftingInput recipeInput, Level level) {
      if (recipeInput.items().stream().anyMatch(stack -> !stack.isEmpty() && !stack.is(this.tool) && !stack.is(this.input))) {
         return false;
      } else {
         return recipeInput.items().stream().filter(stack -> stack.is(this.tool)).count() != 1L
            ? false
            : recipeInput.items().stream().filter(stack -> stack.is(this.input)).count() == 1L;
      }
   }

   public ItemStack assemble(CraftingInput recipeInput, Provider provider) {
      return this.output.copy();
   }

   public NonNullList<ItemStack> getRemainingItems(CraftingInput recipeInput) {
      NonNullList<ItemStack> items = NonNullList.withSize(recipeInput.size(), ItemStack.EMPTY);

      for (int i = 0; i < items.size(); i++) {
         if (recipeInput.getItem(i).is(this.tool)) {
            ItemStack remaining = recipeInput.getItem(i).copy();
            if (remaining.getDamageValue() + 1 < remaining.getMaxDamage()) {
               remaining.setDamageValue(remaining.getDamageValue() + 1);
               items.set(i, remaining);
            }
         }
      }

      return items;
   }

   public boolean canCraftInDimensions(int i, int j) {
      return true;
   }

   public RecipeSerializer<?> getSerializer() {
      return ESRecipeSerializers.TOOL_MODIFICATION.get();
   }

   public static class Serializer implements RecipeSerializer<ToolModificationRecipe> {
      private static final MapCodec<ToolModificationRecipe> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(CraftingRecipe::category),
               BuiltInRegistries.ITEM.byNameCodec().fieldOf("tool").forGetter(o -> o.tool),
               BuiltInRegistries.ITEM.byNameCodec().fieldOf("input").forGetter(o -> o.input),
               ItemStack.OPTIONAL_CODEC.fieldOf("output").forGetter(o -> o.output)
            )
            .apply(instance, ToolModificationRecipe::new)
      );

      public MapCodec<ToolModificationRecipe> codec() {
         return CODEC;
      }

      public StreamCodec<RegistryFriendlyByteBuf, ToolModificationRecipe> streamCodec() {
         return new StreamCodec<RegistryFriendlyByteBuf, ToolModificationRecipe>() {
            public ToolModificationRecipe decode(RegistryFriendlyByteBuf friendlyByteBuf) {
               CraftingBookCategory category = (CraftingBookCategory)friendlyByteBuf.readEnum(CraftingBookCategory.class);
               Item tool = (Item)friendlyByteBuf.readById(BuiltInRegistries.ITEM::byId);
               Item input = (Item)friendlyByteBuf.readById(BuiltInRegistries.ITEM::byId);
               ItemStack output = (ItemStack)ItemStack.STREAM_CODEC.decode(friendlyByteBuf);
               return new ToolModificationRecipe(category, tool, input, output);
            }

            public void encode(RegistryFriendlyByteBuf friendlyByteBuf, ToolModificationRecipe recipe) {
               friendlyByteBuf.writeEnum(recipe.category());
               friendlyByteBuf.writeById(BuiltInRegistries.ITEM::getId, recipe.tool);
               friendlyByteBuf.writeById(BuiltInRegistries.ITEM::getId, recipe.input);
               ItemStack.STREAM_CODEC.encode(friendlyByteBuf, recipe.output);
            }
         };
      }
   }
}
