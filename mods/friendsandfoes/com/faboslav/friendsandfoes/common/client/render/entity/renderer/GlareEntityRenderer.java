package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.GlareFlowerFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.GlareEntityModel;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public final class GlareEntityRenderer extends MobRenderer<GlareEntity, GlareEntityModel<GlareEntity>> {
   private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/glare/glare.png");

   public GlareEntityRenderer(Context context) {
      super(context, new GlareEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.GLARE_LAYER)), 0.4F);
      this.addLayer(new GlareFlowerFeatureRenderer(this));
   }

   protected void scale(GlareEntity glare, PoseStack poseStack, float partialTickTime) {
      float scale = glare.getAgeScale();
      poseStack.scale(scale, scale, scale);
   }

   public ResourceLocation getTextureLocation(GlareEntity glare) {
      return TEXTURE;
   }
}
