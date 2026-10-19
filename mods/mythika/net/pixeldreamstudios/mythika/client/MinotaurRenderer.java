package net.pixeldreamstudios.mythika.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.pixeldreamstudios.mythika.entity.MinotaurEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MinotaurRenderer extends GeoEntityRenderer<MinotaurEntity> {
   public MinotaurRenderer(Context context) {
      super(context, new MinotaurModel());
      this.shadowRadius = 1.0F;
   }
}
