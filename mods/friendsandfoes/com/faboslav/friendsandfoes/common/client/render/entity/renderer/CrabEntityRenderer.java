package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.CrabEntityModel;
import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public final class CrabEntityRenderer extends MobRenderer<CrabEntity, CrabEntityModel<CrabEntity>> {
   private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/crab/crab.png");

   public CrabEntityRenderer(Context context) {
      super(context, new CrabEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.CRAB_LAYER)), 0.5F);
   }

   protected float getShadowRadius(CrabEntity crab) {
      float shadowRadius = super.getShadowRadius(crab);
      boolean isBaby = crab.isBaby();
      shadowRadius *= crab.getSize().getScaleModifier();
      if (isBaby) {
         shadowRadius *= 0.5F;
      }

      return shadowRadius;
   }

   protected void scale(CrabEntity crab, PoseStack poseStack, float partialTickTime) {
      float scale = crab.getAgeScale();
      poseStack.scale(scale, scale, scale);
   }

   public ResourceLocation getTextureLocation(CrabEntity crab) {
      return TEXTURE;
   }
}
