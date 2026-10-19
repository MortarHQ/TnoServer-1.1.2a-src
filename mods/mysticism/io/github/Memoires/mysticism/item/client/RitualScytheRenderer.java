package io.github.Memoires.mysticism.item.client;

import io.github.Memoires.mysticism.item.weapon.RitualScytheItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class RitualScytheRenderer extends GeoItemRenderer<RitualScytheItem> {
   public RitualScytheRenderer() {
      super(new RitualScytheModel());
      this.addRenderLayer(new AutoGlowingGeoLayer(this));
   }
}
