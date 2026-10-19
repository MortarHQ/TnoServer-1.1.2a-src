package com.github.b4ndithelps.tennogamenolife.client.model;

import com.github.b4ndithelps.tennogamenolife.item.SickleItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;

public final class SickleModel extends GeoModel<SickleItem> {
   private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "geo/sickle.geo.json");
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/item/sickle.png");

   public ResourceLocation getModelResource(SickleItem animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(SickleItem animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(SickleItem animatable) {
      return null;
   }

   @Nullable
   public RenderType getRenderType(SickleItem animatable, ResourceLocation texture) {
      return RenderType.entityCutoutNoCull(texture);
   }
}
