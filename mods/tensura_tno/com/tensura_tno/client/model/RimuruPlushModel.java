package com.tensura_tno.client.model;

import com.tensura_tno.client.renderer.RimuruPlushRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class RimuruPlushModel<T extends GeoAnimatable> extends GeoModel<T> {
   private final ResourceLocation model;
   private final ResourceLocation texture;
   private final ResourceLocation animations;

   public RimuruPlushModel() {
      this("rimuru_plush");
   }

   public RimuruPlushModel(String assetName) {
      this.model = ResourceLocation.fromNamespaceAndPath("tensura_tno", "geo/block/" + assetName + ".geo.json");
      this.texture = ResourceLocation.fromNamespaceAndPath("tensura_tno", "textures/block/" + assetName + ".png");
      this.animations = ResourceLocation.fromNamespaceAndPath("tensura_tno", "animations/block/" + assetName + ".animation.json");
   }

   public ResourceLocation getModelResource(T animatable) {
      return this.model;
   }

   public ResourceLocation getTextureResource(T animatable) {
      return this.texture;
   }

   public ResourceLocation getAnimationResource(T animatable) {
      return this.animations;
   }

   public RenderType getRenderType(T animatable, ResourceLocation texture) {
      return RimuruPlushRenderTypes.plush(texture);
   }
}
