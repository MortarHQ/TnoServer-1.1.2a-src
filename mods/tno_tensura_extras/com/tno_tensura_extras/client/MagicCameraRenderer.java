package com.tno_tensura_extras.client;

import com.tno_tensura_extras.item.MagicCameraItem;
import net.minecraft.world.item.ItemDisplayContext;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class MagicCameraRenderer extends GeoItemRenderer<MagicCameraItem> {
   public MagicCameraRenderer() {
      super(new MagicCameraModel());
      this.addRenderLayer(new MagicCameraScreenLayer(this));
   }

   boolean isHandPerspective() {
      return this.renderPerspective != null
         && (
            this.renderPerspective.firstPerson()
               || this.renderPerspective == ItemDisplayContext.THIRD_PERSON_LEFT_HAND
               || this.renderPerspective == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
         );
   }
}
