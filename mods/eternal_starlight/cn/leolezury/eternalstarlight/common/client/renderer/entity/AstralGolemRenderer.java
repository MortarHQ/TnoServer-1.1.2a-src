package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.AstralGolemModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.AstralGolemArmorLayer;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolem;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolemMaterial;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class AstralGolemRenderer<T extends AstralGolem> extends HumanoidMobRenderer<T, AstralGolemModel<T>> {
   private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/boarwarf/golem/astral_golem_iron.png");

   public AstralGolemRenderer(Context context) {
      super(context, new AstralGolemModel(context.bakeLayer(AstralGolemModel.LAYER_LOCATION)), 0.5F);
      this.addLayer(
         new AstralGolemArmorLayer(
            this,
            new HumanoidArmorModel(context.bakeLayer(AstralGolemModel.INNER_ARMOR_LOCATION)),
            new HumanoidArmorModel(context.bakeLayer(AstralGolemModel.OUTER_ARMOR_LOCATION)),
            context.getModelManager()
         )
      );
   }

   public ResourceLocation getTextureLocation(T entity) {
      AstralGolemMaterial material = entity.getMaterial();
      return material == null ? ENTITY_TEXTURE : material.textureFull();
   }
}
