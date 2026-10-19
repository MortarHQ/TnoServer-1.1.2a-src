package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.ClockworkSentinelModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.ClockworkSentinelEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ClockworkSentinelRenderer extends MobRenderer<ClockworkSentinelEntity, ClockworkSentinelModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/clockwork_sentinel.png");

   public ClockworkSentinelRenderer(Context context) {
      super(context, new ClockworkSentinelModel(context.bakeLayer(ClockworkSentinelModel.LAYER_LOCATION)), 0.5F);
      this.addLayer(new ClockworkSentinelEmissiveLayer(this));
   }

   public ResourceLocation getTextureLocation(ClockworkSentinelEntity entity) {
      return TEXTURE;
   }
}
