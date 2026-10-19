package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelBluff;
import com.bobmowzie.mowziesmobs.server.entity.bluff.EntityBluff;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderBluff extends MowzieGeoEntityRenderer<EntityBluff> {
   public RenderBluff(Context mgr) {
      super(mgr, new ModelBluff());
      this.shadowRadius = 0.5F;
   }

   @NotNull
   public ResourceLocation getTextureLocation(EntityBluff entity) {
      return this.getMowzieGeoModel().getTextureResource(entity);
   }
}
