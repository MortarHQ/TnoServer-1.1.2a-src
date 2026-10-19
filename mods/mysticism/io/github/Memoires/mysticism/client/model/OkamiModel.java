package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.monster.OkamiEntity;
import io.github.Memoires.mysticism.entity.variant.OkamiVariant;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class OkamiModel extends DefaultedEntityGeoModel<OkamiEntity> {
   public OkamiModel() {
      super(ResourceLocation.fromNamespaceAndPath("mysticism", "okami"), true);
   }

   public ResourceLocation getModelResource(OkamiEntity animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/okami.geo.json");
   }

   public ResourceLocation getTextureResource(OkamiEntity instance) {
      return OkamiVariant.LOCATION_BY_VARIANT.get(instance.getVariant()) == null
         ? ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/monster/okami_grey.png")
         : OkamiVariant.LOCATION_BY_VARIANT.get(instance.getVariant());
   }

   public ResourceLocation getAnimationResource(OkamiEntity animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "animations/okami.animation.json");
   }

   @Nullable
   public RenderType getRenderType(OkamiEntity animatable, ResourceLocation texture) {
      return RenderType.entityCutoutNoCull(texture, false);
   }
}
