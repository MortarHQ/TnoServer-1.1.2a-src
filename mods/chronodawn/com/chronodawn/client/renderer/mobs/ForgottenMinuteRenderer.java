package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.ForgottenMinuteModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.ForgottenMinuteEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ForgottenMinuteRenderer extends MobRenderer<ForgottenMinuteEntity, ForgottenMinuteModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/forgotten_minute.png");

   public ForgottenMinuteRenderer(Context context) {
      super(context, new ForgottenMinuteModel(context.bakeLayer(ForgottenMinuteModel.LAYER_LOCATION)), 0.3F);
   }

   public ResourceLocation getTextureLocation(ForgottenMinuteEntity entity) {
      return TEXTURE;
   }
}
