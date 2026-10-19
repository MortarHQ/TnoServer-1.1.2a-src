package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.ThirstWalkerModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.ThirstWalkerEyesLayer;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ThirstWalker;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ThirstWalkerRenderer<T extends ThirstWalker> extends MobRenderer<T, ThirstWalkerModel<T>> {
   private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/thirst_walker.png");

   public ThirstWalkerRenderer(Context context) {
      super(context, new ThirstWalkerModel(context.bakeLayer(ThirstWalkerModel.LAYER_LOCATION)), 0.3F);
      this.addLayer(new ThirstWalkerEyesLayer(this));
   }

   public ResourceLocation getTextureLocation(T entity) {
      return ENTITY_TEXTURE;
   }
}
