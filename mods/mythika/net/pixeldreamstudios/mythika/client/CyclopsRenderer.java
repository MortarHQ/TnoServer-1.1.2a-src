package net.pixeldreamstudios.mythika.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.pixeldreamstudios.mythika.entity.CyclopsEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CyclopsRenderer extends GeoEntityRenderer<CyclopsEntity> {
   public CyclopsRenderer(Context context) {
      super(context, new CyclopsModel());
      this.shadowRadius = 1.4F;
   }
}
