package io.github.Memoires.mysticism.client.renderer.entity.skill;

import io.github.Memoires.mysticism.client.model.ShadowBallProjectileModel;
import io.github.Memoires.mysticism.entity.projectile.ShadowBallProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShadowBallProjectileRenderer extends GeoEntityRenderer<ShadowBallProjectile> {
   public ShadowBallProjectileRenderer(Context renderManager) {
      super(renderManager, new ShadowBallProjectileModel());
      this.shadowRadius = 0.0F;
   }

   @Nullable
   public RenderType getRenderType(ShadowBallProjectile animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucent(this.getTextureLocation(animatable));
   }
}
