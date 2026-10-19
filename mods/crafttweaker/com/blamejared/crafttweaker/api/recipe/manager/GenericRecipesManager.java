package com.blamejared.crafttweaker.api.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.generic.ActionRemoveAllGenericRecipes;
import com.blamejared.crafttweaker.api.action.recipe.generic.ActionRemoveGenericRecipe;
import com.blamejared.crafttweaker.api.action.recipe.generic.ActionRemoveGenericRecipeByModId;
import com.blamejared.crafttweaker.api.action.recipe.generic.ActionRemoveGenericRecipeByName;
import com.blamejared.crafttweaker.api.action.recipe.generic.ActionRemoveGenericRecipeByOutput;
import com.blamejared.crafttweaker.api.action.recipe.generic.ActionRemoveGenericRecipeByRegex;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.op.IDataOps;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.NameUtil;
import com.blamejared.crafttweaker.api.zencode.util.PositionUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.CodePosition;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.GenericRecipesManager")
@Document("vanilla/api/recipe/manager/GenericRecipesManager")
public class GenericRecipesManager {
   @ZenCodeGlobals.Global("recipes")
   public static final GenericRecipesManager INSTANCE = new GenericRecipesManager();
   private static final Set<String> FORBIDDEN_MANAGERS = Set.of(CraftTweakerConstants.rl("scripts").toString());

   private GenericRecipesManager() {
   }

   @ZenCodeType.Method
   public void addJsonRecipe(String name, MapData data) {
      String fixedName = (String)Util.make(
         () -> {
            CodePosition position = PositionUtil.getZCScriptPositionFromStackTrace();
            return NameUtil.fixing(
               name,
               (fixed, mistakes) -> CommonLoggers.api()
                  .warn(
                     "{}Invalid recipe name '{}', mistakes:\n{}\nNew recipe name: {}",
                     position == CodePosition.UNKNOWN ? "" : position + ": ",
                     name,
                     String.join("\n", mistakes),
                     fixed
                  )
            );
         }
      );
      IData requestedSerializer = data.getAt("type");
      if (requestedSerializer == null) {
         throw new IllegalArgumentException("Serializer type missing!");
      }

      if (FORBIDDEN_MANAGERS.contains(requestedSerializer.getAsString().toLowerCase(Locale.ENGLISH))) {
         throw new IllegalArgumentException("Cannot add a recipe to the recipe type " + requestedSerializer.asString() + "!");
      }

      ResourceLocation serializerKey = (ResourceLocation)Util.make(() -> {
         try {
            return ResourceLocation.parse(requestedSerializer.getAsString());
         } catch (ClassCastException | IllegalStateException | ResourceLocationException ex) {
            throw new IllegalArgumentException("Expected 'type' field to be a valid resource location", ex);
         }
      });
      RecipeSerializer<?> serializer = (RecipeSerializer<?>)BuiltInRegistries.RECIPE_SERIALIZER
         .getOptional(serializerKey)
         .orElseThrow(() -> new IllegalArgumentException("Recipe Serializer '%s' does not exist.".formatted(requestedSerializer)));
      ResourceLocation recipeName = CraftTweakerConstants.rl(fixedName);
      Recipe<?> recipe = (Recipe<?>)serializer.codec()
         .codec()
         .parse(IDataOps.INSTANCE.withTagAddingRegistryAccess(), data)
         .getOrThrow(IllegalArgumentException::new);
      IRecipeManager<?> manager = RecipeTypeBracketHandler.getOrDefault(recipe.getType());
      RecipeHolder<?> holder = new RecipeHolder(recipeName, recipe);
      CraftTweakerAPI.apply(new ActionAddRecipe<>(manager, GenericUtil.uncheck(holder)));
   }

   @ZenCodeType.Method
   public RecipeHolder<Recipe<RecipeInput>> getRecipeByName(String name) {
      RecipeHolder<Recipe<RecipeInput>> recipe = this.getRecipeMap().get(ResourceLocation.parse(name));
      if (recipe == null) {
         throw new IllegalArgumentException("No recipe found with name: \"" + name + "\"");
      } else {
         return recipe;
      }
   }

   @ZenCodeType.Method
   public List<RecipeHolder<Recipe<RecipeInput>>> getRecipesByOutput(IIngredient output) {
      return this.getAllRecipes()
         .stream()
         .filter(recipe -> output.matches(IItemStack.of(AccessibleElementsProvider.get().registryAccess(recipe.value()::getResultItem))))
         .collect(Collectors.toList());
   }

   @ZenCodeType.Method
   public List<RecipeHolder<Recipe<RecipeInput>>> getRecipesMatching(Predicate<RecipeHolder<Recipe<RecipeInput>>> predicate) {
      return this.getAllRecipes().stream().filter(predicate).collect(Collectors.toList());
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("allRecipes")
   public List<RecipeHolder<Recipe<RecipeInput>>> getAllRecipes() {
      return GenericUtil.uncheck(this.getAllRecipesRaw());
   }

   public List<RecipeHolder<?>> getAllRecipesRaw() {
      return new ArrayList<>(this.getRecipeMap().values());
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("recipeMap")
   public Map<ResourceLocation, RecipeHolder<Recipe<RecipeInput>>> getRecipeMap() {
      return GenericUtil.uncheck(CraftTweakerAPI.getAccessibleElementsProvider().accessibleRecipeManager().crafttweaker$getByName());
   }

   @ZenCodeType.Method
   public void remove(IIngredient output) {
      CraftTweakerAPI.apply(new ActionRemoveGenericRecipeByOutput(output));
   }

   @ZenCodeType.Method
   public void removeByInput(IItemStack input) {
      CraftTweakerAPI.apply(
         new ActionRemoveGenericRecipe(holder -> holder.value().getIngredients().stream().anyMatch(ingredient -> ingredient.test(input.getInternal())))
      );
   }

   @ZenCodeType.Method
   public void removeByName(String... names) {
      CraftTweakerAPI.apply(new ActionRemoveGenericRecipeByName(Arrays.stream(names).map(ResourceLocation::parse).toArray(ResourceLocation[]::new)));
   }

   @ZenCodeType.Method
   public void removeByModid(String modId) {
      this.removeByModid(modId, null);
   }

   @ZenCodeType.Method
   public void removeByModid(String modId, Predicate<String> exclude) {
      CraftTweakerAPI.apply(new ActionRemoveGenericRecipeByModId(modId, exclude));
   }

   @ZenCodeType.Method
   public void removeByRegex(String regex) {
      CraftTweakerAPI.apply(new ActionRemoveGenericRecipeByRegex(regex));
   }

   @ZenCodeType.Method
   public void removeMatching(Predicate<RecipeHolder<Recipe<RecipeInput>>> predicate) {
      CraftTweakerAPI.apply(new ActionRemoveGenericRecipe(predicate));
   }

   @ZenCodeType.Method
   public void removeAll() {
      CraftTweakerAPI.apply(new ActionRemoveAllGenericRecipes());
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("allManagers")
   public List<IRecipeManager<?>> getAllManagers() {
      return new ArrayList<>(RecipeTypeBracketHandler.getManagerInstances());
   }
}
