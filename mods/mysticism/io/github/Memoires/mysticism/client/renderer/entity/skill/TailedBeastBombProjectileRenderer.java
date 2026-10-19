package io.github.Memoires.mysticism.client.renderer.entity.skill;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.Memoires.mysticism.client.model.TailedBeastBombProjectileModel;
import io.github.Memoires.mysticism.entity.projectile.TailedBeastBombProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TailedBeastBombProjectileRenderer extends GeoEntityRenderer<TailedBeastBombProjectile> {
   public TailedBeastBombProjectileRenderer(Context renderManager) {
      super(renderManager, new TailedBeastBombProjectileModel());
      this.shadowRadius = 0.0F;
   }

   @Nullable
   public RenderType getRenderType(TailedBeastBombProjectile animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucent(this.getTextureLocation(animatable));
   }

   public void render(
      TailedBeastBombProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight
   ) {
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, 15728880);
   }
}
