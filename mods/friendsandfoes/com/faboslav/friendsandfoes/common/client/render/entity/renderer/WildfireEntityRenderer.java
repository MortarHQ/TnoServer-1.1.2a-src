package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.WildfireEntityModel;
import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public final class WildfireEntityRenderer extends MobRenderer<WildfireEntity, WildfireEntityModel<WildfireEntity>> {
   private static final ResourceLocation WILDFIRE_TEXTURE = FriendsAndFoes.makeID("textures/entity/wildfire/wildfire.png");
   public static final float SCALE = 1.5F;

   public WildfireEntityRenderer(Context context) {
      super(context, new WildfireEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.WILDFIRE_CROWN_LAYER)), 0.35F);
   }

   protected int getBlockLightLevel(WildfireEntity wildfire, BlockPos blockPos) {
      return 15;
   }

   protected void scale(WildfireEntity wildfire, PoseStack poseStack, float partialTickTime) {
      poseStack.scale(1.5F, 1.5F, 1.5F);
   }

   public ResourceLocation getTextureLocation(WildfireEntity wildfire) {
      return WILDFIRE_TEXTURE;
   }
}
