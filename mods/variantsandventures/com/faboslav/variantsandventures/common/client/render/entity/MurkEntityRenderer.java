package com.faboslav.variantsandventures.common.client.render.entity;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.client.model.MurkEntityModel;
import com.faboslav.variantsandventures.common.entity.mob.MurkEntity;
import com.faboslav.variantsandventures.common.init.VariantsAndVenturesModelLayers;
import com.google.common.collect.Maps;
import java.util.Locale;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class MurkEntityRenderer extends HumanoidMobRenderer<MurkEntity, MurkEntityModel> {
   public static final Map<MurkEntity.Variant, ResourceLocation> TEXTURES = (Map<MurkEntity.Variant, ResourceLocation>)Util.make(
      Maps.newHashMap(), textures -> {
         for (MurkEntity.Variant variant : MurkEntity.Variant.VARIANTS) {
            textures.put(variant, VariantsAndVentures.makeID(String.format(Locale.ROOT, "textures/entity/murk/murk_%s.png", variant.getName())));
         }
      }
   );

   public MurkEntityRenderer(Context context) {
      super(context, new MurkEntityModel(context.bakeLayer(VariantsAndVenturesModelLayers.MURK)), 0.5F);
      this.addLayer(
         new HumanoidArmorLayer(
            this,
            new MurkEntityModel(context.bakeLayer(VariantsAndVenturesModelLayers.MURK_INNER_ARMOR)),
            new MurkEntityModel(context.bakeLayer(VariantsAndVenturesModelLayers.MURK_OUTER_ARMOR)),
            context.getModelManager()
         )
      );
   }

   public ResourceLocation getTextureLocation(MurkEntity murk) {
      return TEXTURES.get(murk.getVariant());
   }
}
