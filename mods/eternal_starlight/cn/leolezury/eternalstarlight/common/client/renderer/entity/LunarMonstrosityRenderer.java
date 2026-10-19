package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.LunarMonstrosityModel;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class LunarMonstrosityRenderer<T extends LunarMonstrosity> extends MobRenderer<T, LunarMonstrosityModel<T>> {
   private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/lunar_monstrosity.png");

   public LunarMonstrosityRenderer(Context context) {
      super(context, new LunarMonstrosityModel(context.bakeLayer(LunarMonstrosityModel.LAYER_LOCATION)), 0.5F);
   }

   protected float getFlipDegrees(T livingEntity) {
      return 0.0F;
   }

   public ResourceLocation getTextureLocation(T entity) {
      return ENTITY_TEXTURE;
   }
}
