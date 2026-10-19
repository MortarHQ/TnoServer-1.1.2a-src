package com.illusivesoulworks.polymorph.platform;

import com.illusivesoulworks.polymorph.common.integration.AbstractCompatibilityModule;
import com.illusivesoulworks.polymorph.common.integration.fastbench.FastBenchModule;
import com.illusivesoulworks.polymorph.platform.services.IIntegrationPlatform;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NeoForgeIntegrationPlatform implements IIntegrationPlatform {
   @Override
   public Map<String, Supplier<Supplier<AbstractCompatibilityModule>>> createCompatibilityModules() {
      Map<String, Supplier<Supplier<AbstractCompatibilityModule>>> result = new HashMap<>();
      result.put("fastbench", () -> FastBenchModule::new);
      return result;
   }
}
