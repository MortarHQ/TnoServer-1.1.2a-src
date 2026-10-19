package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Queue;

public interface IScriptLoader {
   static IScriptLoader find(String name) {
      return CraftTweakerAPI.getRegistry().findLoader(name);
   }

   String name();

   Collection<IScriptLoader> inheritedLoaders();

   default Collection<IScriptLoader> allInheritedLoaders() {
      Collection<IScriptLoader> flattened = new LinkedHashSet<>();
      Queue<IScriptLoader> workingQueue = new ArrayDeque<>();
      this.inheritedLoaders().forEach(workingQueue::offer);

      while (!workingQueue.isEmpty()) {
         IScriptLoader loader = workingQueue.poll();
         flattened.add(loader);
         loader.inheritedLoaders().forEach(workingQueue::offer);
      }

      return flattened;
   }
}
