package xaero.hud.module;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public final class ModuleManager {
   private final Map<ResourceLocation, HudModule<?>> modules = new LinkedHashMap<>();

   public void register(HudModule<?> hudModule) {
      this.modules.put(hudModule.getId(), hudModule);
   }

   public HudModule<?> get(ResourceLocation id) {
      return this.modules.get(id);
   }

   public Iterable<HudModule<?>> getModules() {
      return this.modules.values();
   }
}
