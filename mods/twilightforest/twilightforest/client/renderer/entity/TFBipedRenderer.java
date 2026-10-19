package twilightforest.client.renderer.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import twilightforest.TwilightForestMod;

public class TFBipedRenderer<T extends Mob, M extends HumanoidModel<T>> extends HumanoidMobRenderer<T, M> {
   private final ResourceLocation texture;

   public TFBipedRenderer(Context context, M model, float shadowSize, String textureName) {
      super(context, model, shadowSize);
      if (textureName.startsWith("textures")) {
         this.texture = ResourceLocation.withDefaultNamespace(textureName);
      } else {
         this.texture = TwilightForestMod.getModelTexture(textureName);
      }
   }

   public TFBipedRenderer(Context context, M model, M innerArmor, M outerArmor, float shadowSize, String textureName) {
      this(context, model, shadowSize, textureName);
      this.addLayer(new HumanoidArmorLayer(this, innerArmor, outerArmor, context.getModelManager()));
   }

   public ResourceLocation getTextureLocation(T entity) {
      return this.texture;
   }
}
