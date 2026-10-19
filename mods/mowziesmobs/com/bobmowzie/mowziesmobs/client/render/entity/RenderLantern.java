package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelLantern;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.LanternGelLayer;
import com.bobmowzie.mowziesmobs.server.entity.lantern.EntityLantern;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class RenderLantern extends MobRenderer<EntityLantern, ModelLantern<EntityLantern>> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/entity/mmlantern.png");

   public RenderLantern(Context mgr) {
      super(mgr, new ModelLantern(), 0.6F);
      this.addLayer(new LanternGelLayer(this));
   }

   protected float getFlipDegrees(EntityLantern entity) {
      return 0.0F;
   }

   public ResourceLocation getTextureLocation(EntityLantern entity) {
      return TEXTURE;
   }

   protected int getBlockLightLevel(EntityLantern p_114496_, BlockPos p_114497_) {
      return 15;
   }
}
