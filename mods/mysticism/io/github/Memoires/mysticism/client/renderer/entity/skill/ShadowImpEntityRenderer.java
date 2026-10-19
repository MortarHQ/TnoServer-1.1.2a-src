package io.github.Memoires.mysticism.client.renderer.entity.skill;

import io.github.Memoires.mysticism.client.model.ShadowImpEntityModel;
import io.github.Memoires.mysticism.entity.monster.ShadowImpEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShadowImpEntityRenderer extends GeoEntityRenderer<ShadowImpEntity> {
   public ShadowImpEntityRenderer(Context renderManager) {
      super(renderManager, new ShadowImpEntityModel());
   }
}
