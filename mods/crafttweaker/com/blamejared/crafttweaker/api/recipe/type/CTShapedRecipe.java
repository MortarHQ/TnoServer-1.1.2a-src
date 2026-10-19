package com.blamejared.crafttweaker.api.recipe.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction2D;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapedRecipeSerializer;
import com.blamejared.crafttweaker.api.util.ArrayUtil;
import com.blamejared.crafttweaker.api.util.RecipeUtil;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class CTShapedRecipe extends ShapedRecipe {
   private static final Pair<Integer, Integer> INVALID = Pair.of(-1, -1);
   private final IIngredient[][] ingredients;
   private final IIngredient[][][] mirroredIngredients;
   private final IItemStack output;
   private final MirrorAxis mirrorAxis;
   @Nullable
   private final RecipeFunction2D function;

   public CTShapedRecipe(IItemStack output, IIngredient[][] ingredients, MirrorAxis mirrorAxis) {
      this(CraftingBookCategory.MISC, output, ingredients, mirrorAxis, null);
   }

   public CTShapedRecipe(IItemStack output, IIngredient[][] ingredients, MirrorAxis mirrorAxis, @Nullable RecipeFunction2D function) {
      this(CraftingBookCategory.MISC, output, ingredients, mirrorAxis, function);
   }

   public CTShapedRecipe(
      CraftingBookCategory category, IItemStack output, IIngredient[][] ingredients, MirrorAxis mirrorAxis, @Nullable RecipeFunction2D function
   ) {
      super("", category, RecipeUtil.createPattern(ingredients), output.getInternal());
      this.output = output;
      this.ingredients = RecipeUtil.shrink(ingredients);
      this.mirrorAxis = mirrorAxis;
      this.function = function;
      this.mirroredIngredients = new IIngredient[MirrorAxis.values().length][][];

      for (int index = 0; index < this.ingredients.length; index++) {
         if (this.ingredients[index].length < this.getWidth()) {
            this.ingredients[index] = ArrayUtil.copyOf(this.ingredients[index], this.getWidth(), IItemStack.empty());
         }
      }

      this.initMirroredIngredients();
   }

   private void initMirroredIngredients() {
      this.mirroredIngredients[MirrorAxis.NONE.ordinal()] = this.ingredients;
      if (this.mirrorAxis.isMirrored()) {
         if (this.mirrorAxis.isVertical()) {
            this.mirroredIngredients[MirrorAxis.VERTICAL.ordinal()] = ArrayUtil.mirror(this.ingredients);
         }

         if (this.mirrorAxis.isHorizontal()) {
            IIngredient[][] workingIngredients = ArrayUtil.copy(this.ingredients);

            for (int i = 0; i < workingIngredients.length; i++) {
               workingIngredients[i] = ArrayUtil.mirror(workingIngredients[i]);
            }

            this.mirroredIngredients[MirrorAxis.HORIZONTAL.ordinal()] = workingIngredients;
         }

         if (this.mirrorAxis.isDiagonal()) {
            IIngredient[][] workingIngredients = ArrayUtil.mirror(this.ingredients);

            for (int i = 0; i < workingIngredients.length; i++) {
               workingIngredients[i] = ArrayUtil.mirror(workingIngredients[i]);
            }

            this.mirroredIngredients[MirrorAxis.DIAGONAL.ordinal()] = workingIngredients;
         }
      }
   }

   private Pair<Integer, Integer> calculateOffset(CraftingInput inv) {
      Pair<Integer, Integer> offset = this.calculateOffset(this.mirroredIngredients[MirrorAxis.NONE.ordinal()], inv);
      if (!this.isValidOffset(offset) && this.mirrorAxis.isMirrored()) {
         if (this.mirrorAxis.isVertical()) {
            offset = this.calculateOffset(this.mirroredIngredients[MirrorAxis.VERTICAL.ordinal()], inv);
            if (this.isValidOffset(offset)) {
               return offset;
            }
         }

         if (this.mirrorAxis.isHorizontal()) {
            offset = this.calculateOffset(this.mirroredIngredients[MirrorAxis.HORIZONTAL.ordinal()], inv);
            if (this.isValidOffset(offset)) {
               return offset;
            }
         }

         if (this.mirrorAxis.isDiagonal()) {
            offset = this.calculateOffset(this.mirroredIngredients[MirrorAxis.DIAGONAL.ordinal()], inv);
            if (this.isValidOffset(offset)) {
               return offset;
            }
         }

         return INVALID;
      } else {
         return offset;
      }
   }

   private Pair<Integer, Integer> calculateOffset(IIngredient[][] test, CraftingInput inv) {
      for (int rowOffset = 0; rowOffset <= inv.height() - test.length; rowOffset++) {
         label59:
         for (int columnOffset = 0; columnOffset <= inv.width() - test[0].length; columnOffset++) {
            boolean[] visited = new boolean[inv.size()];

            for (int rowIndex = 0; rowIndex < test.length; rowIndex++) {
               IIngredient[] row = test[rowIndex];

               for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                  IIngredient item = row[columnIndex];
                  int slotNumber = (rowIndex + rowOffset) * inv.width() + columnIndex + columnOffset;
                  ItemStack stackInSlot = inv.getItem(slotNumber);
                  if (item == null && !stackInSlot.isEmpty() || item != null && !item.matches(IItemStack.ofMutable(stackInSlot))) {
                     continue label59;
                  }

                  visited[slotNumber] = true;
               }
            }

            for (int i = 0; i < visited.length; i++) {
               if (!visited[i] && !inv.getItem(i).isEmpty()) {
                  continue label59;
               }
            }

            return Pair.of(rowOffset, columnOffset);
         }
      }

      return INVALID;
   }

   public boolean matches(CraftingInput inv, @Nullable Level worldIn) {
      return this.isValidOffset(this.calculateOffset(inv));
   }

   public ItemStack assemble(CraftingInput container, Provider lookup) {
      Pair<Integer, Integer> offset = this.calculateOffset(container);
      if (offset == INVALID) {
         return ItemStack.EMPTY;
      }

      if (this.function == null) {
         return this.getResultItem(lookup);
      }

      int rowOffset = (Integer)offset.getFirst();
      int columnOffset = (Integer)offset.getSecond();
      IItemStack[][] stacks = new IItemStack[this.getHeight()][this.getWidth()];

      for (int rowIndex = 0; rowIndex < this.ingredients.length; rowIndex++) {
         IIngredient[] row = this.ingredients[rowIndex];

         for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
            IIngredient ingredient = row[columnIndex];
            if (ingredient != null) {
               int slotIndex = (rowIndex + rowOffset) * container.width() + columnIndex + columnOffset;
               stacks[rowIndex][columnIndex] = IItemStack.of(container.getItem(slotIndex)).withAmount(1);
            }
         }
      }

      return this.function.process(this.output, stacks).getImmutableInternal();
   }

   public ItemStack getResultItem(Provider lookup) {
      return this.output.getInternal().copy();
   }

   public NonNullList<ItemStack> getRemainingItems(CraftingInput inv) {
      IIngredient[][] workingIngredients = this.mirroredIngredients[MirrorAxis.NONE.ordinal()];
      Pair<Integer, Integer> offset = this.calculateOffset(workingIngredients, inv);
      if (offset == INVALID && this.mirrorAxis.isMirrored()) {
         if (this.mirrorAxis.isVertical()) {
            workingIngredients = this.mirroredIngredients[MirrorAxis.VERTICAL.ordinal()];
            offset = this.calculateOffset(workingIngredients, inv);
            if (offset != INVALID) {
               return this.getRemainingItems(inv, offset, workingIngredients);
            }
         }

         if (this.mirrorAxis.isHorizontal()) {
            workingIngredients = this.mirroredIngredients[MirrorAxis.HORIZONTAL.ordinal()];
            offset = this.calculateOffset(workingIngredients, inv);
            if (offset != INVALID) {
               return this.getRemainingItems(inv, offset, workingIngredients);
            }
         }

         if (this.mirrorAxis.isDiagonal()) {
            workingIngredients = this.mirroredIngredients[MirrorAxis.DIAGONAL.ordinal()];
            offset = this.calculateOffset(workingIngredients, inv);
         }

         return this.getRemainingItems(inv, offset, workingIngredients);
      } else {
         return this.getRemainingItems(inv, offset, workingIngredients);
      }
   }

   public NonNullList<ItemStack> getRemainingItems(CraftingInput inv, Pair<Integer, Integer> offsetPair, IIngredient[][] ingredients) {
      NonNullList<ItemStack> result = NonNullList.withSize(inv.size(), ItemStack.EMPTY);
      if (offsetPair == INVALID) {
         return result;
      }

      int rowOffset = (Integer)offsetPair.getFirst();
      int columnOffset = (Integer)offsetPair.getSecond();

      for (int rowIndex = 0; rowIndex < ingredients.length; rowIndex++) {
         IIngredient[] row = ingredients[rowIndex];

         for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
            IIngredient ingredient = row[columnIndex];
            if (ingredient != null) {
               int slotIndex = (rowIndex + rowOffset) * inv.width() + columnIndex + columnOffset;
               result.set(slotIndex, ingredient.getRemainingItem(IItemStack.ofMutable(inv.getItem(slotIndex))).getInternal());
            }
         }
      }

      return result;
   }

   public RecipeSerializer<CTShapedRecipe> getSerializer() {
      return CTShapedRecipeSerializer.INSTANCE;
   }

   public IIngredient[][] getCtIngredients() {
      return this.ingredients;
   }

   public NonNullList<IIngredient> getFlatCtIngredients() {
      NonNullList<IIngredient> ingredients = NonNullList.create();

      for (IIngredient[] ctIngredient : this.getCtIngredients()) {
         ingredients.addAll(Arrays.asList(ctIngredient));
      }

      return ingredients;
   }

   public IItemStack getCtOutput() {
      return this.output;
   }

   @Nullable
   public RecipeFunction2D getFunction() {
      return this.function;
   }

   public boolean isMirrored() {
      return this.mirrorAxis.isMirrored();
   }

   public MirrorAxis getMirrorAxis() {
      return this.mirrorAxis;
   }

   private boolean isValidOffset(Pair<Integer, Integer> pair) {
      return !pair.equals(INVALID);
   }

   public boolean isIncomplete() {
      NonNullList<Ingredient> ingredients = this.getIngredients();
      return ingredients.isEmpty()
         || ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).anyMatch(ingredient -> ingredient.getItems().length == 0);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }

      if (o != null && this.getClass() == o.getClass()) {
         CTShapedRecipe that = (CTShapedRecipe)o;
         if (!Arrays.deepEquals(this.ingredients, that.ingredients)) {
            return false;
         } else if (!Arrays.deepEquals(this.mirroredIngredients, that.mirroredIngredients)) {
            return false;
         } else if (!Objects.equals(this.output, that.output)) {
            return false;
         } else {
            return this.mirrorAxis != that.mirrorAxis ? false : Objects.equals(this.function, that.function);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = Arrays.deepHashCode(this.ingredients);
      result = 31 * result + Arrays.deepHashCode(this.mirroredIngredients);
      result = 31 * result + (this.output != null ? this.output.hashCode() : 0);
      result = 31 * result + (this.mirrorAxis != null ? this.mirrorAxis.hashCode() : 0);
      return 31 * result + (this.function != null ? this.function.hashCode() : 0);
   }
}
