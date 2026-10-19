package com.blamejared.crafttweaker.api.action.recipe;

import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import java.util.Arrays;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.apache.logging.log4j.Logger;

public class ActionRemoveRecipeByName<T extends Recipe<?>> extends ActionRecipeBase<T> {
   private final ResourceLocation[] names;

   public ActionRemoveRecipeByName(IRecipeManager<T> manager, ResourceLocation name) {
      super(manager);
      this.names = new ResourceLocation[]{name};
   }

   public ActionRemoveRecipeByName(IRecipeManager<T> manager, ResourceLocation... names) {
      super(manager);
      this.names = names;
   }

   @Override
   public void apply() {
      for (ResourceLocation name : this.names) {
         this.getRecipeMutator().remove(name);
      }
   }

   @Override
   public String describe() {
      return "Removing '"
         + this.getRecipeTypeName()
         + "' recipe(s) with name(s): '"
         + Arrays.stream(this.names).<CharSequence>map(ResourceLocation::toString).collect(Collectors.joining(", ", "[", "]"))
         + "'";
   }

   @Override
   public boolean validate(Logger logger) {
      boolean containsKey = true;

      for (ResourceLocation name : this.names) {
         boolean contains = this.getRecipeMutator().has(name);
         if (!contains) {
            logger.warn("No recipe with type: '{}' and name: '{}'", this.getRecipeTypeName(), name);
            containsKey = false;
         }
      }

      return containsKey;
   }
}
