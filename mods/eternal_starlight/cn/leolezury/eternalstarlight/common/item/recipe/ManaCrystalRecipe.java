package cn.leolezury.eternalstarlight.common.item.recipe;

import cn.leolezury.eternalstarlight.common.registry.ESRecipeSerializers;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
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

public class ManaCrystalRecipe extends CustomRecipe {
   private final ManaType manaType;
   private final Item manaCrystal;

   public ManaCrystalRecipe(CraftingBookCategory craftingBookCategory, ManaType type, Item item) {
      super(craftingBookCategory);
      this.manaType = type;
      this.manaCrystal = item;
   }

   public boolean matches(CraftingInput recipeInput, Level level) {
      int day = (int)(level.getDayTime() / 24000L);
      if (recipeInput.width() == 3 && recipeInput.height() == 3) {
         boolean checkDay = day % 6 == List.of(ManaType.values()).indexOf(this.manaType) - 1;
         boolean checkEmpty = recipeInput.getItem(0).isEmpty()
            && recipeInput.getItem(2).isEmpty()
            && recipeInput.getItem(6).isEmpty()
            && recipeInput.getItem(8).isEmpty();
         boolean checkIngredients = recipeInput.getItem(1).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS)
            && recipeInput.getItem(3).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS)
            && recipeInput.getItem(4).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS)
            && recipeInput.getItem(5).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS)
            && recipeInput.getItem(7).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS);
         return checkDay && checkEmpty && checkIngredients;
      } else {
         return false;
      }
   }

   public ItemStack assemble(CraftingInput recipeInput, Provider provider) {
      return this.manaCrystal.getDefaultInstance();
   }

   public boolean canCraftInDimensions(int i, int j) {
      return i == 3 && j == 3;
   }

   public RecipeSerializer<?> getSerializer() {
      return ESRecipeSerializers.MANA_CRYSTAL.get();
   }

   public static class Serializer implements RecipeSerializer<ManaCrystalRecipe> {
      private static final MapCodec<ManaCrystalRecipe> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(CraftingRecipe::category),
               ManaType.CODEC.fieldOf("mana_type").forGetter(recipe -> recipe.manaType),
               BuiltInRegistries.ITEM.byNameCodec().fieldOf("crystal").forGetter(recipe -> recipe.manaCrystal)
            )
            .apply(instance, ManaCrystalRecipe::new)
      );

      public MapCodec<ManaCrystalRecipe> codec() {
         return CODEC;
      }

      public StreamCodec<RegistryFriendlyByteBuf, ManaCrystalRecipe> streamCodec() {
         return new StreamCodec<RegistryFriendlyByteBuf, ManaCrystalRecipe>() {
            public ManaCrystalRecipe decode(RegistryFriendlyByteBuf friendlyByteBuf) {
               CraftingBookCategory category = (CraftingBookCategory)friendlyByteBuf.readEnum(CraftingBookCategory.class);
               ManaType type = (ManaType)friendlyByteBuf.readEnum(ManaType.class);
               Item output = (Item)friendlyByteBuf.readById(BuiltInRegistries.ITEM::byId);
               return new ManaCrystalRecipe(category, type, output);
            }

            public void encode(RegistryFriendlyByteBuf friendlyByteBuf, ManaCrystalRecipe recipe) {
               friendlyByteBuf.writeEnum(recipe.category());
               friendlyByteBuf.writeEnum(recipe.manaType);
               friendlyByteBuf.writeById(BuiltInRegistries.ITEM::getId, recipe.manaCrystal);
            }
         };
      }
   }
}
