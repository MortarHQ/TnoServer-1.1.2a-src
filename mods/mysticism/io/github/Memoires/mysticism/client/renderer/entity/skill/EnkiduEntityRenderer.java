package io.github.Memoires.mysticism.client.renderer.entity.skill;

import io.github.Memoires.mysticism.client.model.EnkiduEntityModel;
import io.github.Memoires.mysticism.entity.skill.EnkiduEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EnkiduEntityRenderer extends GeoEntityRenderer<EnkiduEntity> {
   public EnkiduEntityRenderer(Context renderManager) {
      super(renderManager, new EnkiduEntityModel());
   }
}
