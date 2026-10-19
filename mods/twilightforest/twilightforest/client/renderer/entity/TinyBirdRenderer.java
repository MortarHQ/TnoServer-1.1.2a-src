package twilightforest.client.renderer.entity;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import twilightforest.entity.passive.TinyBird;
import twilightforest.entity.passive.TinyBirdVariant;

public class TinyBirdRenderer<T extends TinyBird, M extends AgeableListModel<T>> extends BirdRenderer<T, M> {
   public TinyBirdRenderer(Context context, M model, float shadowSize) {
      super(context, model, shadowSize, "");
   }

   public ResourceLocation getTextureLocation(T entity) {
      return ((TinyBirdVariant)entity.getVariant().value()).texture();
   }
}
