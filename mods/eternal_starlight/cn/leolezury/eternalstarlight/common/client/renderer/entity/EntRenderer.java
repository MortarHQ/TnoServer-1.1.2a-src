package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.client.model.entity.EntModel;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Ent;
import cn.leolezury.eternalstarlight.common.entity.living.animal.EntVariant;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class EntRenderer<T extends Ent> extends MobRenderer<T, EntModel<T>> {
   public EntRenderer(Context context) {
      super(context, new EntModel(context.bakeLayer(EntModel.LAYER_LOCATION)), 0.15F);
   }

   public ResourceLocation getTextureLocation(T entity) {
      return ((EntVariant)entity.getVariant().value()).textureFull();
   }
}
