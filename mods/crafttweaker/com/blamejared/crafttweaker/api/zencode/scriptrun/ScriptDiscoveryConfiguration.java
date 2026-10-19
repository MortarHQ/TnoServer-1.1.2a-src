package com.blamejared.crafttweaker.api.zencode.scriptrun;

import java.nio.file.Path;
import java.util.List;

public record ScriptDiscoveryConfiguration(
   ScriptDiscoveryConfiguration.SuspiciousNamesBehavior suspiciousNamesBehavior, ScriptDiscoveryConfiguration.DiscoveryRetainer retainer
) {
   public ScriptDiscoveryConfiguration(ScriptDiscoveryConfiguration.SuspiciousNamesBehavior suspiciousNamesBehavior) {
      this(suspiciousNamesBehavior, (root, files) -> {});
   }

   @FunctionalInterface
   public interface DiscoveryRetainer {
      void retain(Path var1, List<Path> var2);
   }

   public enum SuspiciousNamesBehavior {
      IGNORE,
      WARN;
   }
}
