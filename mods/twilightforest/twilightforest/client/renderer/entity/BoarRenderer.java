package twilightforest.client.renderer.entity;

import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.passive.Boar;

public class BoarRenderer<T extends Boar, M extends PigModel<T>> extends MobRenderer<T, M> {
   private static final ResourceLocation TEXTURE = TwilightForestMod.getModelTexture("wildboar.png");

   public BoarRenderer(Context context, M model) {
      super(context, model, 0.7F);
   }

   public ResourceLocation getTextureLocation(T entity) {
      return TEXTURE;
   }
}
