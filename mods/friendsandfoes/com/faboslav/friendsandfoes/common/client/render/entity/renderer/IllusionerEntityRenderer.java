package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.SpellcasterIllager;

public final class IllusionerEntityRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T> {
   private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/illusioner/illusioner.png");

   public IllusionerEntityRenderer(Context context) {
      super(context, new IllagerModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.ILLUSIONER_LAYER)), 0.5F);
      this.addLayer(
         new ItemInHandLayer<T, IllagerModel<T>>(this, context.getItemInHandRenderer()) {
            public void render(
               PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, T illusioner, float f, float g, float h, float j, float k, float l
            ) {
               if (illusioner.isCastingSpell() || illusioner.isAggressive()) {
                  super.render(matrixStack, vertexConsumerProvider, i, illusioner, f, g, h, j, k, l);
               }
            }
         }
      );
      ((IllagerModel)this.model).getHat().visible = true;
   }

   public ResourceLocation getTextureLocation(T illusioner) {
      return TEXTURE;
   }
}
