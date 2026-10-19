package io.github.Memoires.mysticism.client.renderer.entity.skill;

import io.github.Memoires.mysticism.client.model.SentryMissileModel;
import io.github.Memoires.mysticism.entity.projectile.SentryMissileProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SentryMissileProjectileRenderer extends GeoEntityRenderer<SentryMissileProjectile> {
   public SentryMissileProjectileRenderer(Context renderManager) {
      super(renderManager, new SentryMissileModel());
   }
}
