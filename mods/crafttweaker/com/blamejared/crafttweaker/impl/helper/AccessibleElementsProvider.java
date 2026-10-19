package com.blamejared.crafttweaker.impl.helper;

import com.blamejared.crafttweaker.api.registry.TagAddingRegistryLookup;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker.platform.helper.IAccessibleClientElementsProvider;
import com.blamejared.crafttweaker.platform.helper.IAccessibleElementsProvider;
import com.blamejared.crafttweaker.platform.helper.IAccessibleServerElementsProvider;
import com.google.common.base.Suppliers;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.RecipeManager;

public final class AccessibleElementsProvider implements IAccessibleElementsProvider {
   private static final Supplier<AccessibleElementsProvider> INSTANCE = Suppliers.memoize(AccessibleElementsProvider::new);
   private RecipeManager recipeManager = null;

   private AccessibleElementsProvider() {
   }

   public static IAccessibleElementsProvider get() {
      return INSTANCE.get();
   }

   @Override
   public RecipeManager recipeManager() {
      return Objects.requireNonNull(this.recipeManager, "Recipe manager is unavailable");
   }

   @Override
   public AccessRecipeManager accessibleRecipeManager() {
      return (AccessRecipeManager)this.recipeManager();
   }

   @Override
   public void recipeManager(RecipeManager manager) {
      this.recipeManager = manager;
   }

   @Override
   public RegistryAccess registryAccess() {
      if (this.server().hasRegistryAccess()) {
         return this.server().registryAccess();
      } else if (this.client().hasRegistryAccess()) {
         return this.client().registryAccess();
      } else {
         throw new NullPointerException("Registry access is unavailable");
      }
   }

   @Override
   public boolean hasRegistryAccess() {
      return this.client().hasRegistryAccess() || this.server().hasRegistryAccess();
   }

   @Override
   public IAccessibleClientElementsProvider client() {
      return AccessibleClientElementsProvider.get();
   }

   @Override
   public IAccessibleServerElementsProvider server() {
      return AccessibleServerElementsProvider.get();
   }

   @Override
   public TagAddingRegistryLookup tagAddingRegistryLookup() {
      if (this.server().hasRegistryAccess()) {
         return this.server().tagAddingRegistryLookup();
      } else if (this.client().hasRegistryAccess()) {
         return this.client().tagAddingRegistryLookup();
      } else {
         throw new NullPointerException("Registry access is unavailable");
      }
   }
}
