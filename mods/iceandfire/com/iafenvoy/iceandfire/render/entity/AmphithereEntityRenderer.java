package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.AmphithereEntity;
import com.iafenvoy.iceandfire.render.model.AmphithereModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class AmphithereEntityRenderer extends MobRenderer<AmphithereEntity, AmphithereModel> {
   public static final ResourceLocation TEXTURE_BLUE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/amphithere/amphithere_blue.png");
   public static final ResourceLocation TEXTURE_BLUE_BLINK = ResourceLocation.fromNamespaceAndPath(
      "iceandfire", "textures/entity/amphithere/amphithere_blue_blink.png"
   );
   public static final ResourceLocation TEXTURE_GREEN = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/amphithere/amphithere_green.png");
   public static final ResourceLocation TEXTURE_GREEN_BLINK = ResourceLocation.fromNamespaceAndPath(
      "iceandfire", "textures/entity/amphithere/amphithere_green_blink.png"
   );
   public static final ResourceLocation TEXTURE_OLIVE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/amphithere/amphithere_olive.png");
   public static final ResourceLocation TEXTURE_OLIVE_BLINK = ResourceLocation.fromNamespaceAndPath(
      "iceandfire", "textures/entity/amphithere/amphithere_olive_blink.png"
   );
   public static final ResourceLocation TEXTURE_RED = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/amphithere/amphithere_red.png");
   public static final ResourceLocation TEXTURE_RED_BLINK = ResourceLocation.fromNamespaceAndPath(
      "iceandfire", "textures/entity/amphithere/amphithere_red_blink.png"
   );
   public static final ResourceLocation TEXTURE_YELLOW = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/amphithere/amphithere_yellow.png");
   public static final ResourceLocation TEXTURE_YELLOW_BLINK = ResourceLocation.fromNamespaceAndPath(
      "iceandfire", "textures/entity/amphithere/amphithere_yellow_blink.png"
   );

   public AmphithereEntityRenderer(Context context) {
      super(context, new AmphithereModel(), 1.6F);
   }

   protected void scale(AmphithereEntity entity, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.scale(2.0F, 2.0F, 2.0F);
   }

   public ResourceLocation getTexture(AmphithereEntity amphithere) {
      switch (amphithere.getVariant()) {
         case 0:
            if (amphithere.isBlinking()) {
               return TEXTURE_BLUE_BLINK;
            }

            return TEXTURE_BLUE;
         case 1:
            if (amphithere.isBlinking()) {
               return TEXTURE_GREEN_BLINK;
            }

            return TEXTURE_GREEN;
         case 2:
            if (amphithere.isBlinking()) {
               return TEXTURE_OLIVE_BLINK;
            }

            return TEXTURE_OLIVE;
         case 3:
            if (amphithere.isBlinking()) {
               return TEXTURE_RED_BLINK;
            }

            return TEXTURE_RED;
         case 4:
            if (amphithere.isBlinking()) {
               return TEXTURE_YELLOW_BLINK;
            }

            return TEXTURE_YELLOW;
         default:
            return TEXTURE_GREEN;
      }
   }
}
