package fuzs.illagerinvasion.client.render.entity.layers;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.init.ClientModRegistry;
import fuzs.illagerinvasion.world.entity.monster.Necromancer;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class NecromancerShieldLayer extends EnergySwirlLayer<Necromancer, IllagerModel<Necromancer>> {
   private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/necromancer_armor1.png");
   private final IllagerModel<Necromancer> model;

   public NecromancerShieldLayer(RenderLayerParent<Necromancer, IllagerModel<Necromancer>> context, EntityModelSet loader) {
      super(context);
      this.model = new IllagerModel(loader.bakeLayer(ClientModRegistry.NECROMANCER_SHIELD));
   }

   protected float xOffset(float partialAge) {
      return Mth.cos(partialAge * 0.2F) * 0.2F;
   }

   protected ResourceLocation getTextureLocation() {
      return TEXTURE_LOCATION;
   }

   protected IllagerModel<Necromancer> model() {
      return this.model;
   }
}
