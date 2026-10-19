package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IReplacerComponentRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingFilter;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;

final class ReplacerComponentsRegistrationHandler implements IReplacerComponentRegistrationHandler {
   private final Collection<ITargetingFilter> filters = new ArrayList<>();
   private final Map<ResourceLocation, ITargetingStrategy> strategies = new HashMap<>();

   static ReplacerComponentsRegistrationHandler of(Consumer<IReplacerComponentRegistrationHandler> consumer) {
      ReplacerComponentsRegistrationHandler handler = new ReplacerComponentsRegistrationHandler();
      consumer.accept(handler);
      return handler;
   }

   @Override
   public void registerTargetingFilter(ITargetingFilter filter) {
      this.filters.add(Objects.requireNonNull(filter));
   }

   @Override
   public void registerTargetingStrategy(ResourceLocation id, ITargetingStrategy strategy) {
      if (this.strategies.containsKey(id)) {
         throw new IllegalArgumentException("A targeting strategy with id " + id + " already exists");
      }

      this.strategies.put(id, strategy);
   }

   Collection<ITargetingFilter> filters() {
      return Collections.unmodifiableCollection(this.filters);
   }

   Map<ResourceLocation, ITargetingStrategy> strategies() {
      return Collections.unmodifiableMap(this.strategies);
   }
}
