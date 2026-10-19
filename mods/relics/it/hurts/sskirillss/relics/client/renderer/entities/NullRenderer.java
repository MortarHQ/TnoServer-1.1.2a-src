package it.hurts.sskirillss.relics.client.renderer.entities;

import javax.annotation.Nonnull;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NullRenderer<T extends Entity> extends EntityRenderer<T> {
   public NullRenderer(Context manager) {
      super(manager);
   }

   public boolean shouldRender(T entity, @Nonnull Frustum clipping, double x, double y, double z) {
      return true;
   }

   @Nonnull
   public ResourceLocation getTextureLocation(@Nonnull T entity) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
