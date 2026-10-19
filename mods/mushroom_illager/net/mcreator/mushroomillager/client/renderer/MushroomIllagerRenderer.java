package net.mcreator.mushroomillager.client.renderer;

import net.mcreator.mushroomillager.client.model.Modelunknown;
import net.mcreator.mushroomillager.entity.MushroomIllagerEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class MushroomIllagerRenderer extends MobRenderer<MushroomIllagerEntity, Modelunknown<MushroomIllagerEntity>> {
   public MushroomIllagerRenderer(Context context) {
      super(context, new Modelunknown(context.bakeLayer(Modelunknown.LAYER_LOCATION)), 0.5F);
   }

   public ResourceLocation getTextureLocation(MushroomIllagerEntity entity) {
      return ResourceLocation.parse("mushroom_illager:textures/entities/vindicator.png");
   }
}
