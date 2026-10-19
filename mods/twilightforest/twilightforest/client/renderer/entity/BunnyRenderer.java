package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import twilightforest.client.model.entity.BunnyModel;
import twilightforest.entity.passive.DwarfRabbit;
import twilightforest.entity.passive.DwarfRabbitVariant;

public class BunnyRenderer extends MobRenderer<DwarfRabbit, BunnyModel> {
   public BunnyRenderer(Context context, BunnyModel model, float shadowSize) {
      super(context, model, shadowSize);
   }

   public ResourceLocation getTextureLocation(DwarfRabbit entity) {
      return ((DwarfRabbitVariant)entity.getVariant().value()).texture();
   }
}
