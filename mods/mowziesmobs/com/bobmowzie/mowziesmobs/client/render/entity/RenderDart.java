package com.bobmowzie.mowziesmobs.client.render.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class RenderDart extends ArrowRenderer {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/entity/dart.png");

   public RenderDart(Context mgr) {
      super(mgr);
   }

   public ResourceLocation getTextureLocation(Entity entity) {
      return TEXTURE;
   }
}
