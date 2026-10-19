package io.github.Memoires.mysticism.item.client;

import io.github.Memoires.mysticism.item.weapon.WaltzItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class WaltzRenderer extends GeoItemRenderer<WaltzItem> {
   public WaltzRenderer() {
      super(new WaltzModel());
   }
}
