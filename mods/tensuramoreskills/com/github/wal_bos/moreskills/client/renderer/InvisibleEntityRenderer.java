package com.github.wal_bos.moreskills.client.renderer;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class InvisibleEntityRenderer<T extends Entity> extends EntityRenderer<T> {
   public InvisibleEntityRenderer(Context context) {
      super(context);
   }

   @Nullable
   public ResourceLocation getTextureLocation(T entity) {
      return null;
   }
}
