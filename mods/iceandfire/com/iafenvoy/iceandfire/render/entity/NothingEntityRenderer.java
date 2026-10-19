package com.iafenvoy.iceandfire.render.entity;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class NothingEntityRenderer<T extends Entity> extends EntityRenderer<T> {
   public NothingEntityRenderer(Context context) {
      super(context);
   }

   public boolean shouldRender(T livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
      return !this.entityRenderDispatcher.shouldRenderHitBoxes() ? false : super.shouldRender(livingEntityIn, camera, camX, camY, camZ);
   }

   public ResourceLocation getTextureLocation(Entity entity) {
      return null;
   }
}
