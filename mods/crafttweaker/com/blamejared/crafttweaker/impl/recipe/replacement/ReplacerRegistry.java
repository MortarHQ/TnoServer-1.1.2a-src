package com.blamejared.crafttweaker.impl.recipe.replacement;

import com.blamejared.crafttweaker.api.recipe.replacement.IReplacerRegistry;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingFilter;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

public final class ReplacerRegistry implements IReplacerRegistry {
   private final Collection<ITargetingFilter> castedFilters = new ArrayList<>();
   private final Map<ResourceLocation, ITargetingStrategy> strategies = new HashMap<>();
   private final Collection<ITargetingFilter> filtersView = Collections.unmodifiableCollection(this.castedFilters);
   private final Collection<ResourceLocation> strategyNames = Collections.unmodifiableSet(this.strategies.keySet());

   public void castedFilters(Collection<ITargetingFilter> filters) {
      if (!this.castedFilters.isEmpty()) {
         throw new IllegalStateException("Attempted double registration for recipe filters");
      }

      this.castedFilters.addAll(filters);
   }

   public void strategy(ResourceLocation id, ITargetingStrategy strategy) {
      if (this.strategies.containsKey(id)) {
         throw new IllegalStateException("Attempted double strategy registration for " + id);
      }

      this.strategies.put(id, strategy);
   }

   @Override
   public Collection<ITargetingFilter> filters() {
      return this.filtersView;
   }

   @Override
   public ITargetingStrategy findStrategy(ResourceLocation id) {
      return Objects.requireNonNull(this.strategies.get(id), () -> "Unknown strategy " + id);
   }

   @Override
   public Collection<ResourceLocation> allStrategyNames() {
      return this.strategyNames;
   }
}
