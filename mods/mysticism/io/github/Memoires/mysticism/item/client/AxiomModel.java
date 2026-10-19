package io.github.Memoires.mysticism.item.client;

import io.github.Memoires.mysticism.item.weapon.AxiomItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;

public class AxiomModel extends GeoModel<AxiomItem> {
   public ResourceLocation getModelResource(AxiomItem animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/weapons/axiom.geo.json");
   }

   public ResourceLocation getTextureResource(AxiomItem animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/item/axiom.png");
   }

   public ResourceLocation getAnimationResource(AxiomItem animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "animations/weapons/axiom.animation.json");
   }

   @Nullable
   public RenderType getRenderType(AxiomItem animatable, ResourceLocation texture) {
      return RenderType.entityTranslucent(texture);
   }
}
