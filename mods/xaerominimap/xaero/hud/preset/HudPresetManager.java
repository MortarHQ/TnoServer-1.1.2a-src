package xaero.hud.preset;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public class HudPresetManager {
   private final Map<ResourceLocation, HudPreset> presets = new LinkedHashMap<>();

   public void register(HudPreset preset) {
      this.presets.put(preset.getId(), preset);
   }

   public Iterable<HudPreset> getPresets() {
      return this.presets.values();
   }
}
