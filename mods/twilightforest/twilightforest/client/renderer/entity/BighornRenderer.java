package twilightforest.client.renderer.entity;

import net.minecraft.client.model.SheepModel;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.SheepFurLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import twilightforest.TwilightForestMod;

public class BighornRenderer extends SheepRenderer {
   public static final ResourceLocation TEXTURE = TwilightForestMod.getModelTexture("bighorn.png");

   public BighornRenderer(Context context, SheepModel<? extends Sheep> baseModel, float shadowSize) {
      super(context);
      this.shadowRadius = shadowSize;
      this.model = baseModel;
      this.addLayer(new SheepFurLayer(this, context.getModelSet()));
   }

   public ResourceLocation getTextureLocation(Sheep entity) {
      return TEXTURE;
   }
}
