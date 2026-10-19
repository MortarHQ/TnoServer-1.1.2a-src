package io.github.Memoires.mysticism.item.client;

import io.github.Memoires.mysticism.item.weapon.AxiomItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class AxiomRenderer extends GeoItemRenderer<AxiomItem> {
   public AxiomRenderer() {
      super(new AxiomModel());
      this.addRenderLayer(new AutoGlowingGeoLayer(this));
   }
}
