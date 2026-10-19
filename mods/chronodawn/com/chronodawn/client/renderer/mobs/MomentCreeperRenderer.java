package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.MomentCreeperModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.MomentCreeperEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class MomentCreeperRenderer extends MobRenderer<MomentCreeperEntity, MomentCreeperModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/moment_creeper.png");

   public MomentCreeperRenderer(Context context) {
      super(context, new MomentCreeperModel(context.bakeLayer(MomentCreeperModel.LAYER_LOCATION)), 0.5F);
   }

   public ResourceLocation getTextureLocation(MomentCreeperEntity entity) {
      return TEXTURE;
   }

   protected float getWhiteOverlayProgress(MomentCreeperEntity entity, float partialTick) {
      float swelling = entity.getSwelling(partialTick);
      return swelling > 0.5F ? (swelling - 0.5F) * 2.0F : 0.0F;
   }
}
