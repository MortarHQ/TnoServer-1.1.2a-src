package com.blamejared.crafttweaker.api.action.recipe;

import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import java.util.function.Predicate;
import net.minecraft.world.item.crafting.Recipe;

public class ActionRemoveRecipeByModid<T extends Recipe<?>> extends ActionRecipeBase<T> {
   private final String modid;
   private final Predicate<String> exclude;

   public ActionRemoveRecipeByModid(IRecipeManager<T> manager, String modid, Predicate<String> exclude) {
      super(manager);
      this.modid = modid;
      this.exclude = exclude;
   }

   @Override
   public void apply() {
      this.getRecipeMutator().removeByIdTest(resourceLocation -> resourceLocation.getNamespace().equals(this.modid), this.exclude);
   }

   @Override
   public String describe() {
      return "Removing \"" + this.getRecipeTypeName() + "\" recipes with modid: \"" + this.modid + "\"";
   }
}
