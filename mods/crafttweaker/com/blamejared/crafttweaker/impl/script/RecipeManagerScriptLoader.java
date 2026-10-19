package com.blamejared.crafttweaker.impl.script;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.util.sequence.SequenceManager;
import com.blamejared.crafttweaker.api.util.sequence.SequenceType;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeManagerScriptLoader {
   private static RecipeManagerScriptLoader.UpdatedState currentState = RecipeManagerScriptLoader.UpdatedState.NONE;
   private static RecipeManager manager = null;

   public static void updateState(RecipeManagerScriptLoader.UpdatedState state, @Nullable Supplier<RecipeManager> managerSupplier) {
      if (currentState.hasAll()) {
         currentState = RecipeManagerScriptLoader.UpdatedState.NONE;
         manager = null;
         throw new IllegalArgumentException("Invalid state found in RecipeManagerScriptLoader!");
      }

      currentState = currentState.merge(state);
      if (managerSupplier != null) {
         manager = managerSupplier.get();
      }

      if (currentState.hasAll()) {
         if (manager == null) {
            throw new RuntimeException("State is ready, but manager is null!");
         }

         loadScriptsFromManager(manager);
         currentState = RecipeManagerScriptLoader.UpdatedState.NONE;
         manager = null;
      }
   }

   public static void loadScriptsFromManager(RecipeManager manager) {
      SequenceManager.clearSequences(SequenceType.CLIENT_THREAD_LEVEL);
      if (!Services.CLIENT.isSingleplayer()) {
         fixRecipeManager(manager);
         Multimap<RecipeType<?>, RecipeHolder<?>> allRecipes = ((AccessRecipeManager)manager).crafttweaker$getByType();
         Collection<RecipeHolder<?>> recipes = allRecipes.removeAll(ScriptRecipeType.INSTANCE);
         if (!recipes.isEmpty()) {
            Collection<ScriptRecipe> scriptRecipes = recipes.stream()
               .<Object>map(RecipeHolder::value)
               .map(ScriptRecipe.class::cast)
               .collect(Collectors.toCollection(ArrayList::new));
            ScriptRunConfiguration configuration = new ScriptRunConfiguration(
               "crafttweaker", CraftTweakerConstants.CLIENT_RECIPES_UPDATED_SOURCE_ID, ScriptRunConfiguration.RunKind.EXECUTE
            );

            try {
               executeScriptRecipes(scriptRecipes, configuration);
            } catch (Throwable e) {
               CraftTweakerCommon.logger().error("Unable to execute script run", e);
            }
         }
      }
   }

   private static void fixRecipeManager(RecipeManager manager) {
      AccessRecipeManager accessRecipeManager = (AccessRecipeManager)manager;
      accessRecipeManager.crafttweaker$setByType(HashMultimap.create(accessRecipeManager.crafttweaker$getByType()));
      accessRecipeManager.crafttweaker$setByName(new HashMap<>(accessRecipeManager.crafttweaker$getByName()));
      CraftTweakerAPI.getAccessibleElementsProvider().recipeManager(manager);
   }

   private static void executeScriptRecipes(Collection<ScriptRecipe> scriptRecipes, ScriptRunConfiguration configuration) throws Throwable {
      URI uri = new URI("crt-recipe-fs:recipes");
      Map<String, ?> env = Map.of("recipes", scriptRecipes);

      try (FileSystem fs = FileSystems.newFileSystem(uri, env)) {
         Path root = fs.getRootDirectories().iterator().next();
         CraftTweakerAPI.getScriptRunManager().createScriptRun(root, configuration).execute();
      }
   }

   public enum UpdatedState {
      NONE(false, false),
      RECIPES(true, false),
      TAGS(false, true),
      ALL(true, true);

      private final boolean hasRecipes;
      private final boolean hasTags;

      UpdatedState(boolean hasRecipes, boolean hasTags) {
         this.hasRecipes = hasRecipes;
         this.hasTags = hasTags;
      }

      public boolean hasRecipes() {
         return this.hasRecipes;
      }

      public boolean hasTags() {
         return this.hasTags;
      }

      public boolean hasAll() {
         return this.hasRecipes && this.hasTags;
      }

      public static RecipeManagerScriptLoader.UpdatedState of(boolean hasRecipes, boolean hasTags) {
         if (hasRecipes) {
            return hasTags ? ALL : RECIPES;
         } else {
            return hasTags ? TAGS : NONE;
         }
      }

      public RecipeManagerScriptLoader.UpdatedState merge(RecipeManagerScriptLoader.UpdatedState other) {
         return of(this.hasRecipes || other.hasRecipes, this.hasTags || other.hasTags);
      }
   }
}
