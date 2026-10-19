package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelDynamicsTester;
import com.bobmowzie.mowziesmobs.server.entity.EntityDynamicsTester;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class RenderDynamicsTester extends MobRenderer<EntityDynamicsTester, ModelDynamicsTester<EntityDynamicsTester>> {
   private static final ResourceLocation TEXTURE_STONE = ResourceLocation.withDefaultNamespace("textures/blocks/stone.png");

   public RenderDynamicsTester(Context mgr) {
      super(mgr, new ModelDynamicsTester(), 0.5F);
   }

   protected float getFlipDegrees(EntityDynamicsTester entity) {
      return 0.0F;
   }

   public ResourceLocation getTextureLocation(EntityDynamicsTester entity) {
      return TEXTURE_STONE;
   }
}
