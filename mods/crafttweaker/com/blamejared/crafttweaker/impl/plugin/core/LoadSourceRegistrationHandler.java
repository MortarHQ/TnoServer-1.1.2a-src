package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IScriptLoadSourceRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;

final class LoadSourceRegistrationHandler implements IScriptLoadSourceRegistrationHandler {
   private final Map<ResourceLocation, IScriptLoadSource> sources = new HashMap<>();

   private LoadSourceRegistrationHandler() {
   }

   static Collection<IScriptLoadSource> gather(Consumer<IScriptLoadSourceRegistrationHandler> populatingConsumer) {
      LoadSourceRegistrationHandler handler = new LoadSourceRegistrationHandler();
      populatingConsumer.accept(handler);
      return ImmutableList.copyOf(handler.sources.values());
   }

   @Override
   public void registerLoadSource(ResourceLocation id) {
      if (this.sources.containsKey(id)) {
         throw new IllegalArgumentException("Load source '" + id + "' was already registered");
      }

      this.sources.put(id, new LoadSource(id));
   }
}
