package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.TimeKeeperModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.TimeKeeperEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class TimeKeeperRenderer extends MobRenderer<TimeKeeperEntity, TimeKeeperModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/time_keeper.png");

   public TimeKeeperRenderer(Context context) {
      super(context, new TimeKeeperModel(context.bakeLayer(TimeKeeperModel.LAYER_LOCATION)), 0.5F);
   }

   public ResourceLocation getTextureLocation(TimeKeeperEntity entity) {
      return TEXTURE;
   }
}
