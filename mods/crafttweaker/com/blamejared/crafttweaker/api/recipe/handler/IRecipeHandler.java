package com.blamejared.crafttweaker.api.recipe.handler;

import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public interface IRecipeHandler<T extends Recipe<?>> {
   String dumpToCommandString(IRecipeManager<? super T> var1, RegistryAccess var2, RecipeHolder<T> var3);

   <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super T> var1, T var2, U var3);

   Optional<IDecomposedRecipe> decompose(IRecipeManager<? super T> var1, RegistryAccess var2, T var3);

   Optional<T> recompose(IRecipeManager<? super T> var1, RegistryAccess var2, IDecomposedRecipe var3);

   @Documented
   @Repeatable(IRecipeHandler.For.Container.class)
   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE)
   @interface For {
      Class<? extends Recipe<?>> value();

      @Documented
      @Retention(RetentionPolicy.RUNTIME)
      @Target(ElementType.TYPE)
      @interface Container {
         IRecipeHandler.For[] value();
      }
   }
}
