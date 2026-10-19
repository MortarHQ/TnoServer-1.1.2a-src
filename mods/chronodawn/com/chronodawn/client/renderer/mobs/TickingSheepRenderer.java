package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.TickingSheepBodyModel;
import com.chronodawn.client.model.TickingSheepWoolModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.TickingSheepEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class TickingSheepRenderer extends MobRenderer<TickingSheepEntity, TickingSheepBodyModel> {
   private static final ResourceLocation BODY_TEXTURE = CompatResourceLocation.create("minecraft", "textures/entity/sheep/sheep.png");

   public TickingSheepRenderer(Context context) {
      super(context, new TickingSheepBodyModel(context.bakeLayer(TickingSheepBodyModel.LAYER_LOCATION)), 0.7F);
      this.addLayer(new TickingSheepWoolLayer(this, new TickingSheepWoolModel(context.bakeLayer(TickingSheepWoolModel.LAYER_LOCATION))));
   }

   public ResourceLocation getTextureLocation(TickingSheepEntity entity) {
      return BODY_TEXTURE;
   }
}
