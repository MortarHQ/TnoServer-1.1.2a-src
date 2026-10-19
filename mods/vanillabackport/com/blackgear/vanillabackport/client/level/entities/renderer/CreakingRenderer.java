package com.blackgear.vanillabackport.client.level.entities.renderer;

import com.blackgear.vanillabackport.client.level.entities.layer.LivingEntityEmissiveLayer;
import com.blackgear.vanillabackport.client.level.entities.model.CreakingModel;
import com.blackgear.vanillabackport.client.registries.ModModelLayers;
import com.blackgear.vanillabackport.common.level.entities.creaking.Creaking;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreakingRenderer<T extends Creaking> extends MobRenderer<T, CreakingModel<T>> {
   private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/creaking/creaking.png");
   private static final ResourceLocation EYES_TEXTURE_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/creaking/creaking_eyes.png");

   public CreakingRenderer(Context context) {
      super(context, new CreakingModel(context.bakeLayer(ModModelLayers.CREAKING)), 0.7F);
      this.addLayer(
         new LivingEntityEmissiveLayer(
            this,
            creaking -> EYES_TEXTURE_LOCATION,
            (creaking, ageInTicks) -> creaking.shouldEyesGlow() ? 1.0F : 0.0F,
            new CreakingModel(context.bakeLayer(ModModelLayers.CREAKING)),
            RenderType::eyes,
            true
         )
      );
   }

   public ResourceLocation getTextureLocation(Creaking entity) {
      return TEXTURE_LOCATION;
   }
}
