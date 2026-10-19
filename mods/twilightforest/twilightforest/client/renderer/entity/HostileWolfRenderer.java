package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.entity.HostileWolfModel;
import twilightforest.entity.monster.HostileWolf;

public class HostileWolfRenderer extends MobRenderer<HostileWolf, HostileWolfModel<HostileWolf>> {
   public HostileWolfRenderer(Context context) {
      super(context, new HostileWolfModel(context.bakeLayer(TFModelLayers.HOSTILE_WOLF)), 0.5F);
   }

   protected float getBob(HostileWolf entity, float partialTicks) {
      return entity.getTailAngle();
   }

   public ResourceLocation getTextureLocation(HostileWolf entity) {
      return entity.getTexture();
   }
}
