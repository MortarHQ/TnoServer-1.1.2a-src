package io.github.Memoires.mysticism.client.renderer.entity.skill;

import io.github.Memoires.mysticism.client.model.SentryModel;
import io.github.Memoires.mysticism.entity.skill.SentryEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SentryRenderer extends GeoEntityRenderer<SentryEntity> {
   public SentryRenderer(Context renderManager) {
      super(renderManager, new SentryModel());
   }
}
