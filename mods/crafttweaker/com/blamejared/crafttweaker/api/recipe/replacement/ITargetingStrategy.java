package com.blamejared.crafttweaker.api.recipe.replacement;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/recipe/replacement/ITargetingStrategy")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.ITargetingStrategy")
@ZenRegister
public interface ITargetingStrategy extends CommandStringDisplayable {
   ResourceLocation DEFAULT_STRATEGY_ID = CraftTweakerConstants.rl("shallow");

   static ITargetingStrategy find(ResourceLocation id) {
      return CraftTweakerAPI.getRegistry().getReplacerRegistry().findStrategy(id);
   }

   <T> @ZenCodeType.Nullable T castStrategy(IRecipeComponent<T> var1, T var2, Function<T, @ZenCodeType.Nullable T> var3);

   @Override
   default String getCommandString() {
      IReplacerRegistry registry = CraftTweakerAPI.getRegistry().getReplacerRegistry();
      return registry.allStrategyNames()
         .stream()
         .filter(it -> registry.findStrategy(it) == this)
         .findFirst()
         .<String>map(ResourceLocation::toString)
         .map(xva$0 -> "<targetingstrategy:%s>".formatted(xva$0))
         .orElseThrow();
   }
}
