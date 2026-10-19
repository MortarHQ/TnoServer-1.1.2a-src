package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelGrottol;
import com.bobmowzie.mowziesmobs.server.entity.grottol.EntityGrottol;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class RenderGrottol extends MobRenderer<EntityGrottol, ModelGrottol<EntityGrottol>> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/entity/grottol.png");
   private static final ResourceLocation TEXTURE_DEEPSLATE = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/entity/grottol_deepslate.png");
   private static final ResourceLocation TEXTURE_BLACKPINK = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/entity/grottol_blackpink.png");
   private static final ResourceLocation TEXTURE_DEEPSLATE_BLACKPINK = ResourceLocation.fromNamespaceAndPath(
      "mowziesmobs", "textures/entity/grottol_deepslate_blackpink.png"
   );

   public RenderGrottol(Context mgr) {
      super(mgr, new ModelGrottol(), 0.6F);
   }

   protected float getFlipDegrees(EntityGrottol entity) {
      return 0.0F;
   }

   public ResourceLocation getTextureLocation(EntityGrottol entity) {
      if (entity.getBlackpink()) {
         return entity.getDeepslate() ? TEXTURE_DEEPSLATE_BLACKPINK : TEXTURE_BLACKPINK;
      } else {
         return entity.getDeepslate() ? TEXTURE_DEEPSLATE : TEXTURE;
      }
   }
}
