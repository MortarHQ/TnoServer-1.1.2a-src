package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.WraithModel;
import twilightforest.entity.monster.Wraith;

public class WraithRenderer extends HumanoidMobRenderer<Wraith, WraithModel> {
   private static final ResourceLocation TEXTURE = TwilightForestMod.getModelTexture("ghost.png");

   public WraithRenderer(Context context, WraithModel model, float shadowSize) {
      super(context, model, shadowSize);
   }

   public ResourceLocation getTextureLocation(Wraith wraith) {
      return TEXTURE;
   }
}
