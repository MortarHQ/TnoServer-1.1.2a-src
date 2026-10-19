package io.github.Memoires.mysticism.item.client;

import io.github.Memoires.mysticism.item.weapon.RitualScytheItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;

public class RitualScytheModel extends GeoModel<RitualScytheItem> {
   public ResourceLocation getModelResource(RitualScytheItem animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/weapons/ritual_scythe.geo.json");
   }

   public ResourceLocation getTextureResource(RitualScytheItem animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/item/ritual_scythe.png");
   }

   public ResourceLocation getAnimationResource(RitualScytheItem animatable) {
      return null;
   }

   @Nullable
   public RenderType getRenderType(RitualScytheItem animatable, ResourceLocation texture) {
      return RenderType.entityTranslucent(this.getTextureResource(animatable));
   }
}
