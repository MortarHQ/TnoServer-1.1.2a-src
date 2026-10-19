package com.blamejared.crafttweaker.api.recipe.manager.base;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveAll;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByModid;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByName;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByOutput;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByRegex;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.op.IDataOps;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.recipe.RecipeList;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.NameUtil;
import com.blamejared.crafttweaker.api.zencode.util.PositionUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.CodePosition;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.IRecipeManager")
@Document("vanilla/api/recipe/manager/IRecipeManager")
public interface IRecipeManager<T extends Recipe<?>> extends CommandStringDisplayable, Iterable<RecipeHolder<T>> {
   @ZenCodeType.Method
   default void addJsonRecipe(String name, MapData mapData) {
      String fixedName = this.fixRecipeName(name);
      ResourceLocation recipeTypeKey = this.getBracketResourceLocation();
      IData requestedSerializer = mapData.getAt("type");
      ResourceLocation serializerKey = requestedSerializer == null ? recipeTypeKey : (ResourceLocation)Util.make(() -> {
         try {
            return ResourceLocation.parse(requestedSerializer.getAsString());
         } catch (ClassCastException | IllegalStateException | ResourceLocationException ex) {
            throw new IllegalArgumentException("Expected 'type' field to be a valid resource location", ex);
         }
      });
      RecipeSerializer<T> serializer = (RecipeSerializer<T>)BuiltInRegistries.RECIPE_SERIALIZER
         .getOptional(serializerKey)
         .map(GenericUtil::uncheck)
         .orElseThrow(
            () -> requestedSerializer == null
               ? new IllegalArgumentException(
                  "Recipe Type '%s' does not have a Recipe Serializer of the same ID. Please specify a serializer manually using the 'type' field in the JSON object"
                     .formatted(recipeTypeKey)
               )
               : new IllegalArgumentException("Recipe Serializer '%s' does not exist.".formatted(requestedSerializer))
         );
      ResourceLocation recipeName = CraftTweakerConstants.rl(fixedName);
      T recipe = (T)serializer.codec().codec().parse(IDataOps.INSTANCE.withTagAddingRegistryAccess(), mapData).getOrThrow(IllegalStateException::new);
      RecipeType<?> recipeType = recipe.getType();
      if (recipeType != this.getRecipeType()) {
         throw new IllegalArgumentException(
            "Recipe Serializer \"%s\" resulted in Recipe Type \"%s\" but expected Recipe Type \"%s\""
               .formatted(
                  BuiltInRegistries.RECIPE_SERIALIZER.getKey(recipe.getSerializer()),
                  BuiltInRegistries.RECIPE_TYPE.getKey(recipeType),
                  BuiltInRegistries.RECIPE_TYPE.getKey(this.getRecipeType())
               )
         );
      }

      RecipeHolder<T> holder = new RecipeHolder(recipeName, recipe);
      CraftTweakerAPI.apply(new ActionAddRecipe<>(this, holder));
   }

   @ZenCodeType.Method
   default @ZenCodeType.Nullable RecipeHolder<T> getRecipeByName(String name) {
      return this.getRecipeList().get(name);
   }

   @ZenCodeType.Method
   default List<RecipeHolder<T>> getRecipesByOutput(IIngredient output) {
      return this.getRecipeList().getRecipesByOutput(output);
   }

   @ZenCodeType.Method
   default List<RecipeHolder<T>> getRecipesMatching(Predicate<RecipeHolder<T>> predicate) {
      return this.getRecipeList().getRecipesMatching(predicate);
   }

   @ZenCodeType.Getter("allRecipes")
   default List<RecipeHolder<T>> getAllRecipes() {
      return new ArrayList<>(this.getRecipeList().getRecipes());
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("recipeMap")
   default Map<ResourceLocation, RecipeHolder<T>> getRecipeMap() {
      return this.getRecipeList().getByName();
   }

   @ZenCodeType.Method
   default void remove(IIngredient output) {
      CraftTweakerAPI.apply(new ActionRemoveRecipeByOutput<>(this, output));
   }

   @ZenCodeType.Method
   default void removeByInput(IItemStack input) {
      CraftTweakerAPI.apply(
         new ActionRemoveRecipe<>(this, holder -> holder.value().getIngredients().stream().anyMatch(ingredient -> ingredient.test(input.getInternal())))
      );
   }

   @ZenCodeType.Method
   default void removeByName(String... names) {
      CraftTweakerAPI.apply(new ActionRemoveRecipeByName<>(this, Arrays.stream(names).map(ResourceLocation::parse).toArray(ResourceLocation[]::new)));
   }

   @ZenCodeType.Method
   default void removeByModid(String modid, @ZenCodeType.Optional("(name as string) as bool => false") Predicate<String> exclude) {
      CraftTweakerAPI.apply(new ActionRemoveRecipeByModid<>(this, modid, exclude));
   }

   @ZenCodeType.Method
   default void removeByRegex(String regex, @ZenCodeType.Optional("(name as string) as bool => false") Predicate<String> exclude) {
      CraftTweakerAPI.apply(new ActionRemoveRecipeByRegex<>(this, regex, exclude));
   }

   @ZenCodeType.Method
   default void removeMatching(Predicate<RecipeHolder<T>> predicate) {
      CraftTweakerAPI.apply(new ActionRemoveRecipe<>(this, predicate));
   }

   @ZenCodeType.Method
   default void removeAll() {
      CraftTweakerAPI.apply(new ActionRemoveAll<>(this));
   }

   RecipeType<T> getRecipeType();

   default RecipeList<T> getRecipeList() {
      return new RecipeList<>(
         this.getRecipeType(), this.getRecipes(), CraftTweakerAPI.getAccessibleElementsProvider().accessibleRecipeManager().crafttweaker$getByName()
      );
   }

   default Collection<RecipeHolder<T>> getRecipes() {
      return GenericUtil.uncheck(CraftTweakerAPI.getAccessibleElementsProvider().accessibleRecipeManager().crafttweaker$getByType().get(this.getRecipeType()));
   }

   default ResourceLocation getBracketResourceLocation() {
      return BuiltInRegistries.RECIPE_TYPE.getKey(this.getRecipeType());
   }

   @Override
   default String getCommandString() {
      return "<recipetype:" + this.getBracketResourceLocation() + ">";
   }

   default String fixRecipeName(String name) {
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

   default ResourceLocation fixRecipeId(String name) {
      return this.fixRecipeId(name, CraftTweakerConstants::rl);
   }

   default ResourceLocation fixRecipeId(String name, Function<String, ResourceLocation> idMaker) {
      return idMaker.apply(this.fixRecipeName(name));
   }

   default RecipeHolder<T> createHolder(ResourceLocation id, T recipe) {
      return new RecipeHolder(id, recipe);
   }

   @NotNull
   @Override
   default Iterator<RecipeHolder<T>> iterator() {
      return this.getAllRecipes().iterator();
   }
}
