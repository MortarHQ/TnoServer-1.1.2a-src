package io.github.Memoires.mysticism.client.renderer.entity.skill;

import io.github.Memoires.mysticism.client.model.DraconicPulseModel;
import io.github.Memoires.mysticism.entity.projectile.DraconicPulseProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DraconicPulseRenderer extends GeoEntityRenderer<DraconicPulseProjectile> {
   public DraconicPulseRenderer(Context renderManager) {
      super(renderManager, new DraconicPulseModel());
   }
}
