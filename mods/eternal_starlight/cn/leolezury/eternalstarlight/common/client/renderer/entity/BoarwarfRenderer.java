package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.BoarwarfModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.boarwarf.BoarwarfBiomeLayer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.boarwarf.profession.BoarwarfBlacksmithLayer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.boarwarf.profession.BoarwarfChefLayer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.boarwarf.profession.BoarwarfDruidLayer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.boarwarf.profession.BoarwarfDyerLayer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.boarwarf.profession.BoarwarfSilversmithLayer;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class BoarwarfRenderer<T extends Boarwarf> extends MobRenderer<T, BoarwarfModel<T>> {
   private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/boarwarf/boarwarf.png");

   public BoarwarfRenderer(Context context) {
      super(context, new BoarwarfModel(context.bakeLayer(BoarwarfModel.LAYER_LOCATION)), 0.5F);
      this.addLayer(new BoarwarfBiomeLayer(this, context.getModelSet()));
      this.addLayer(new BoarwarfBlacksmithLayer(this, context.getModelSet()));
      this.addLayer(new BoarwarfChefLayer(this, context.getModelSet()));
      this.addLayer(new BoarwarfDruidLayer(this, context.getModelSet()));
      this.addLayer(new BoarwarfDyerLayer(this, context.getModelSet()));
      this.addLayer(new BoarwarfSilversmithLayer(this, context.getModelSet()));
   }

   public ResourceLocation getTextureLocation(T entity) {
      return ENTITY_TEXTURE;
   }
}
