package com.blamejared.crafttweaker.impl.script;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

@ParametersAreNonnullByDefault
public class ScriptRecipe implements Recipe<RecipeInput> {
   private static final Function<ScriptRecipe, ResourceLocation> ID_GENERATOR = Util.memoize(scriptRecipe -> {
      String sanitizedFileName = scriptRecipe.getFileName().toLowerCase(Locale.ENGLISH).replaceAll("[^a-z0-9_.-]", "_");
      return CraftTweakerConstants.rl(sanitizedFileName);
   });
   private final String fileName;
   private final String content;

   public ScriptRecipe(String fileName, String content) {
      this.fileName = fileName;
      this.content = content;
   }

   public ResourceLocation getId() {
      return ID_GENERATOR.apply(this);
   }

   public boolean matches(RecipeInput container, Level level) {
      return false;
   }

   public ItemStack assemble(RecipeInput var1, Provider var2) {
      return ItemStack.EMPTY;
   }

   public boolean canCraftInDimensions(int i, int i1) {
      return false;
   }

   public ItemStack getResultItem(Provider var1) {
      return ItemStack.EMPTY;
   }

   public RecipeSerializer<?> getSerializer() {
      return ScriptSerializer.INSTANCE;
   }

   public RecipeType<?> getType() {
      return ScriptRecipeType.INSTANCE;
   }

   public String getContent() {
      return this.content;
   }

   public String getFileName() {
      return this.fileName;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ScriptRecipe that = (ScriptRecipe)o;
         return Objects.equals(this.getFileName(), that.getFileName()) && Objects.equals(this.getContent(), that.getContent());
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getFileName(), this.getContent());
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("ScriptRecipe{");
      sb.append("fileName='").append(this.fileName).append('\'');
      sb.append(", content='").append(this.content).append('\'');
      sb.append('}');
      return sb.toString();
   }
}
