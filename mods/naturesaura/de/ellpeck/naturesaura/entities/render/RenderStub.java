package de.ellpeck.naturesaura.entities.render;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;

public class RenderStub<T extends Entity> extends EntityRenderer<T> {
   public RenderStub(Context ctx) {
      super(ctx);
   }

   public boolean shouldRender(T livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
      return false;
   }

   public ResourceLocation getTextureLocation(T t) {
      return InventoryMenu.BLOCK_ATLAS;
   }
}
