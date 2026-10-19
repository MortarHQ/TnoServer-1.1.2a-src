package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.ILoaderRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class LoaderRegistrationHandler implements ILoaderRegistrationHandler {
   private final Map<String, LoaderRegistrationHandler.LoaderRequest> requests = new HashMap<>();

   private LoaderRegistrationHandler() {
   }

   static Map<String, IScriptLoader> gather(Consumer<ILoaderRegistrationHandler> populatingConsumer) {
      LoaderRegistrationHandler handler = new LoaderRegistrationHandler();
      handler.requests.put("*", new LoaderRegistrationHandler.LoaderRequest("*", Collections.emptySet()));
      populatingConsumer.accept(handler);
      return handler.build();
   }

   @Override
   public void registerLoader(String name, String... inheritedLoaders) {
      if (this.requests.containsKey(name)) {
         throw new IllegalArgumentException("Loader '" + name + "' was already registered");
      }

      Stream<String> loaders = Stream.concat(Stream.of("*"), Arrays.stream(inheritedLoaders));
      this.requests.put(name, new LoaderRegistrationHandler.LoaderRequest(name, loaders.collect(Collectors.toUnmodifiableSet())));
   }

   Map<String, IScriptLoader> build() {
      Map<String, LoaderRegistrationHandler.LoaderRequest> unsatisfiedRequests = new HashMap<>(this.requests);
      Map<String, IScriptLoader> loaders = new HashMap<>();

      while (!unsatisfiedRequests.isEmpty()) {
         Map<String, LoaderRegistrationHandler.LoaderRequest> satisfiableRequests = this.findValidRequests(unsatisfiedRequests, loaders.keySet());
         if (satisfiableRequests.isEmpty()) {
            throw new IllegalStateException("Unable to create all loaders due to unsatisfied dependency linkage: " + unsatisfiedRequests);
         }

         this.satisfyRequests(satisfiableRequests, loaders);
         satisfiableRequests.keySet().forEach(unsatisfiedRequests::remove);
      }

      return ImmutableMap.copyOf(loaders);
   }

   private Map<String, LoaderRegistrationHandler.LoaderRequest> findValidRequests(
      Map<String, LoaderRegistrationHandler.LoaderRequest> requests, Set<String> availableIds
   ) {
      return requests.entrySet()
         .stream()
         .filter(it -> availableIds.containsAll(it.getValue().inheritedLoaders()))
         .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
   }

   private void satisfyRequests(Map<String, LoaderRegistrationHandler.LoaderRequest> requests, Map<String, IScriptLoader> output) {
      requests.forEach((id, request) -> {
         IScriptLoader loader = new LoaderData(id, Collections2.transform(request.inheritedLoaders(), output::get));
         output.put(id, loader);
      });
   }

   private record LoaderRequest(String name, Set<String> inheritedLoaders) {
   }
}
