package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.CreteorModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.CreteorPowerLayer;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Creteor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CreteorRenderer<T extends Creteor> extends MobRenderer<T, CreteorModel<T>> {
   private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/creteor.png");

   public CreteorRenderer(Context context) {
      super(context, new CreteorModel(context.bakeLayer(CreteorModel.LAYER_LOCATION)), 0.5F);
      this.addLayer(new CreteorPowerLayer(this, context.getModelSet()));
   }

   protected void scale(T entity, PoseStack poseStack, float f) {
      float swell = entity.getSwellProgress(f);
      float factor = 1.0F + Mth.sin(swell * 600.0F) * swell * 0.1F;
      swell = Mth.clamp(swell, 0.0F, 1.0F);
      swell *= swell;
      swell *= swell;
      float xz = (1.0F + swell * 0.4F) * factor;
      float y = (1.0F + swell * 0.1F) / factor;
      poseStack.scale(xz, y, xz);
   }

   protected float getWhiteOverlayProgress(T entity, float f) {
      float swell = entity.getSwellProgress(f);
      return (int)(swell * 45.0F) % 2 == 0 ? 0.0F : Mth.clamp(swell, 0.2F, 1.0F);
   }

   public ResourceLocation getTextureLocation(T entity) {
      return ENTITY_TEXTURE;
   }
}
