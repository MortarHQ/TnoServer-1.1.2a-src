package io.github.Memoires.mysticism.item.client;

import io.github.Memoires.mysticism.item.weapon.WaltzItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;

public class WaltzModel extends GeoModel<WaltzItem> {
   public ResourceLocation getModelResource(WaltzItem animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/weapons/waltz.geo.json");
   }

   public ResourceLocation getTextureResource(WaltzItem animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/item/waltz.png");
   }

   public ResourceLocation getAnimationResource(WaltzItem animatable) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "animations/weapons/waltz.animation.json");
   }

   @Nullable
   public RenderType getRenderType(WaltzItem animatable, ResourceLocation texture) {
      return RenderType.itemEntityTranslucentCull(texture);
   }
}
