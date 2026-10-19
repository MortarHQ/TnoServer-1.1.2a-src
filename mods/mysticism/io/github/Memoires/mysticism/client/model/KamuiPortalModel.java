package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.skill.KamuiPortalEntity;
import io.github.manasmods.tensura.util.client.ClientHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class KamuiPortalModel extends DefaultedEntityGeoModel<KamuiPortalEntity> {
   public KamuiPortalModel() {
      super(ResourceLocation.fromNamespaceAndPath("mysticism", "kamui_portal"), false);
   }

   protected String subtype() {
      return "entity/skill";
   }

   public RenderType getRenderType(KamuiPortalEntity animatable, ResourceLocation texture) {
      return ClientHelper.getFirstViewRenderType(animatable, 2.5, texture);
   }

   public ResourceLocation getTextureResource(KamuiPortalEntity instance) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/skill/kamui_portal.png");
   }

   public ResourceLocation getModelResource(KamuiPortalEntity animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/kamui_portal.geo.json");
   }

   public ResourceLocation getAnimationResource(KamuiPortalEntity animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "animations/kamui_portal.animation.json");
   }
}
