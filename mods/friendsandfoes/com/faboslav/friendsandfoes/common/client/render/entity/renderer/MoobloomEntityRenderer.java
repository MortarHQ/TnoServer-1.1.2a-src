package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.MoobloomFlowerFeatureRenderer;
import com.faboslav.friendsandfoes.common.entity.MoobloomEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public final class MoobloomEntityRenderer extends MobRenderer<MoobloomEntity, CowModel<MoobloomEntity>> {
   public MoobloomEntityRenderer(Context context) {
      super(context, new CowModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.MOOBLOOM_LAYER)), 0.7F);
      this.addLayer(new MoobloomFlowerFeatureRenderer(this));
   }

   public ResourceLocation getTextureLocation(MoobloomEntity moobloom) {
      return FriendsAndFoes.makeID("textures/entity/moobloom/moobloom_" + moobloom.getVariant().getName() + ".png");
   }
}
