package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.RascalEntityModel;
import com.faboslav.friendsandfoes.common.entity.RascalEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public final class RascalEntityRenderer extends MobRenderer<RascalEntity, RascalEntityModel<RascalEntity>> {
   private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/rascal/rascal.png");

   public RascalEntityRenderer(Context context) {
      super(context, new RascalEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.RASCAL_LAYER)), 0.5F);
   }

   public ResourceLocation getTextureLocation(RascalEntity rascal) {
      return TEXTURE;
   }
}
