package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.client.model.entity.ShimmerLacewingModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.ShimmerLacewingGlowLayer;
import cn.leolezury.eternalstarlight.common.entity.living.animal.ShimmerLacewing;
import cn.leolezury.eternalstarlight.common.entity.living.animal.ShimmerLacewingVariant;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ShimmerLacewingRenderer<T extends ShimmerLacewing> extends MobRenderer<T, ShimmerLacewingModel<T>> {
   public ShimmerLacewingRenderer(Context context) {
      super(context, new ShimmerLacewingModel(context.bakeLayer(ShimmerLacewingModel.LAYER_LOCATION)), 0.3F);
      this.addLayer(new ShimmerLacewingGlowLayer(this, context.getModelSet()));
   }

   public ResourceLocation getTextureLocation(T entity) {
      return ((ShimmerLacewingVariant)entity.getVariant().value()).textureFull();
   }
}
