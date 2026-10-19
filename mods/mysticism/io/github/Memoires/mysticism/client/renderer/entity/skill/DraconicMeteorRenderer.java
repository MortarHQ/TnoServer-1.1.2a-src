package io.github.Memoires.mysticism.client.renderer.entity.skill;

import io.github.Memoires.mysticism.client.model.DraconicMeteorModel;
import io.github.Memoires.mysticism.entity.projectile.DraconicMeteorProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DraconicMeteorRenderer extends GeoEntityRenderer<DraconicMeteorProjectile> {
   public DraconicMeteorRenderer(Context renderManager) {
      super(renderManager, new DraconicMeteorModel());
   }
}
