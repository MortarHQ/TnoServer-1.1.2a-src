package com.blamejared.crafttweaker.impl.registry;

import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

final class ScriptRunModuleConfiguratorRegistry {
   private final Map<IScriptLoader, IScriptRunModuleConfigurator> configurators = new HashMap<>();

   void register(IScriptLoader loader, IScriptRunModuleConfigurator configurator) {
      IScriptRunModuleConfigurator other = this.configurators.get(loader);
      if (other != null) {
         throw new IllegalArgumentException("A configurator for the loader " + loader.name() + " is already registered: " + other);
      }

      this.configurators.put(loader, configurator);
   }

   void verify(Collection<IScriptLoader> loaders) {
      Collection<IScriptLoader> unregisteredLoaders = loaders.stream().filter(it -> !this.configurators.containsKey(it)).toList();
      if (!unregisteredLoaders.isEmpty()) {
         throw new IllegalStateException("Missing configurator for loaders " + unregisteredLoaders);
      }
   }

   IScriptRunModuleConfigurator find(IScriptLoader loader) {
      return Objects.requireNonNull(this.configurators.get(loader));
   }
}
