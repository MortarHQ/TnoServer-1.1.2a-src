package twilightforest.item.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import twilightforest.init.TFRecipes;

public class ScepterRepairRecipe extends CustomRecipe {
   private final Item scepter;
   private final List<Ingredient> repairItems;
   private final int durability;

   public ScepterRepairRecipe(Item scepter, List<Ingredient> repairItems, int repairDurability, CraftingBookCategory category) {
      super(category);
      this.scepter = scepter;
      this.repairItems = repairItems;
      this.durability = repairDurability;
   }

   public boolean matches(CraftingInput input, Level level) {
      ItemStack scepter = null;
      if (this.repairItems.size() == 1) {
         int ingredients = 0;

         for (int i = 0; i < input.size(); i++) {
            ItemStack stackInQuestion = input.getItem(i);
            if (!stackInQuestion.isEmpty()) {
               if (stackInQuestion.is(this.scepter) && stackInQuestion.getDamageValue() > 0) {
                  if (scepter != null) {
                     return false;
                  }

                  scepter = stackInQuestion;
               } else {
                  if (!this.repairItems.getFirst().test(stackInQuestion)) {
                     return false;
                  }

                  ingredients++;
               }
            }
         }

         int duraRes = ingredients * this.getRepairDurability();
         return scepter != null && ingredients > 0 && scepter.getDamageValue() + this.getRepairDurability() - duraRes > 0;
      } else {
         for (int i = 0; i < input.size(); i++) {
            ItemStack stackInQuestion = input.getItem(i);
            if (!stackInQuestion.isEmpty() && stackInQuestion.is(this.scepter) && stackInQuestion.getDamageValue() > 0) {
               if (scepter != null) {
                  return false;
               }

               scepter = stackInQuestion;
            }
         }

         return scepter != null && this.repairItems.size() == input.ingredientCount() - 1 && input.stackedContents().canCraft(this, null);
      }
   }

   public ItemStack assemble(CraftingInput input, Provider registries) {
      ItemStack scepter = null;
      int ingredients = 0;

      for (int i = 0; i < input.size(); i++) {
         ItemStack stackInQuestion = input.getItem(i);
         if (!stackInQuestion.isEmpty()) {
            if (stackInQuestion.is(this.scepter) && stackInQuestion.getDamageValue() > 0) {
               scepter = stackInQuestion;
            } else if (this.repairItems.size() == 1 && this.repairItems.getFirst().test(stackInQuestion)) {
               ingredients++;
            }
         }
      }

      if (scepter != null) {
         ItemStack copy = new ItemStack(this.scepter);
         copy.applyComponents(scepter.getComponents());
         if (ingredients > 0) {
            copy.setDamageValue(scepter.getDamageValue() - this.getRepairDurability() * ingredients);
         } else {
            copy.setDamageValue(scepter.getDamageValue() - this.durability);
         }

         return copy;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public Item getScepter() {
      return this.scepter;
   }

   public List<Ingredient> getRepairItems() {
      return this.repairItems;
   }

   public int getRepairDurability() {
      return this.durability;
   }

   public NonNullList<Ingredient> getIngredients() {
      return NonNullList.copyOf(this.repairItems);
   }

   public boolean canCraftInDimensions(int width, int height) {
      return this.repairItems.size() + 1 > width * height;
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)TFRecipes.SCEPTER_REPAIR_RECIPE.get();
   }

   public static class Serializer implements RecipeSerializer<ScepterRepairRecipe> {
      public static final MapCodec<ScepterRepairRecipe> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               BuiltInRegistries.ITEM.byNameCodec().fieldOf("scepter").forGetter(o -> o.scepter),
               Ingredient.CODEC_NONEMPTY.listOf().fieldOf("repair_ingredients").forGetter(o -> o.repairItems),
               Codec.INT.fieldOf("durability").forGetter(o -> o.durability),
               CraftingBookCategory.CODEC.optionalFieldOf("category", CraftingBookCategory.MISC).forGetter(CustomRecipe::category)
            )
            .apply(instance, ScepterRepairRecipe::new)
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, ScepterRepairRecipe> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.registry(Registries.ITEM),
         o -> o.scepter,
         Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
         o -> o.repairItems,
         ByteBufCodecs.INT,
         o -> o.durability,
         CraftingBookCategory.STREAM_CODEC,
         CustomRecipe::category,
         ScepterRepairRecipe::new
      );

      public MapCodec<ScepterRepairRecipe> codec() {
         return CODEC;
      }

      public StreamCodec<RegistryFriendlyByteBuf, ScepterRepairRecipe> streamCodec() {
         return STREAM_CODEC;
      }
   }
}
