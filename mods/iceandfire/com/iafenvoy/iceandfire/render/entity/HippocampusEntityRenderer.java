package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.HippocampusEntity;
import com.iafenvoy.iceandfire.render.model.HippocampusModel;
import com.iafenvoy.iceandfire.util.Color4i;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Locale;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class HippocampusEntityRenderer extends MobRenderer<HippocampusEntity, HippocampusModel> {
   private static final ResourceLocation VARIANT_0 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hippocampus/hippocampus_0.png");
   private static final ResourceLocation VARIANT_0_BLINK = ResourceLocation.fromNamespaceAndPath(
      "iceandfire", "textures/entity/hippocampus/hippocampus_0_blinking.png"
   );
   private static final ResourceLocation VARIANT_1 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hippocampus/hippocampus_1.png");
   private static final ResourceLocation VARIANT_1_BLINK = ResourceLocation.fromNamespaceAndPath(
      "iceandfire", "textures/entity/hippocampus/hippocampus_1_blinking.png"
   );
   private static final ResourceLocation VARIANT_2 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hippocampus/hippocampus_2.png");
   private static final ResourceLocation VARIANT_2_BLINK = ResourceLocation.fromNamespaceAndPath(
      "iceandfire", "textures/entity/hippocampus/hippocampus_2_blinking.png"
   );
   private static final ResourceLocation VARIANT_3 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hippocampus/hippocampus_3.png");
   private static final ResourceLocation VARIANT_3_BLINK = ResourceLocation.fromNamespaceAndPath(
      "iceandfire", "textures/entity/hippocampus/hippocampus_3_blinking.png"
   );
   private static final ResourceLocation VARIANT_4 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hippocampus/hippocampus_4.png");
   private static final ResourceLocation VARIANT_4_BLINK = ResourceLocation.fromNamespaceAndPath(
      "iceandfire", "textures/entity/hippocampus/hippocampus_4_blinking.png"
   );
   private static final ResourceLocation VARIANT_5 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hippocampus/hippocampus_5.png");
   private static final ResourceLocation VARIANT_5_BLINK = ResourceLocation.fromNamespaceAndPath(
      "iceandfire", "textures/entity/hippocampus/hippocampus_5_blinking.png"
   );

   public HippocampusEntityRenderer(Context context) {
      super(context, new HippocampusModel(), 0.8F);
      this.layers.add(new HippocampusEntityRenderer.LayerHippocampusRainbow(this));
      this.layers.add(new HippocampusEntityRenderer.LayerHippocampusSaddle(this));
   }

   public ResourceLocation getTexture(HippocampusEntity entity) {
      return switch (entity.getVariant()) {
         case 1 -> entity.isBlinking() ? VARIANT_1_BLINK : VARIANT_1;
         case 2 -> entity.isBlinking() ? VARIANT_2_BLINK : VARIANT_2;
         case 3 -> entity.isBlinking() ? VARIANT_3_BLINK : VARIANT_3;
         case 4 -> entity.isBlinking() ? VARIANT_4_BLINK : VARIANT_4;
         case 5 -> entity.isBlinking() ? VARIANT_5_BLINK : VARIANT_5;
         default -> entity.isBlinking() ? VARIANT_0_BLINK : VARIANT_0;
      };
   }

   private static class LayerHippocampusRainbow extends RenderLayer<HippocampusEntity, HippocampusModel> {
      private final RenderType TEXTURE = RenderType.entityNoOutline(
         ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hippocampus/rainbow.png")
      );
      private final RenderType TEXTURE_BLINK = RenderType.entityNoOutline(
         ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hippocampus/rainbow_blink.png")
      );

      public LayerHippocampusRainbow(HippocampusEntityRenderer renderer) {
         super(renderer);
      }

      public void render(
         PoseStack matrixStackIn,
         MultiBufferSource bufferIn,
         int packedLightIn,
         HippocampusEntity hippo,
         float limbSwing,
         float limbSwingAmount,
         float partialTicks,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         assert hippo.getCustomName() != null;
         if (hippo.hasCustomName() && hippo.getCustomName().toString().toLowerCase(Locale.ROOT).contains("rainbow")) {
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(hippo.isBlinking() ? this.TEXTURE_BLINK : this.TEXTURE);
            int i = hippo.tickCount / 25 + hippo.getId();
            int j = DyeColor.values().length;
            int k = i % j;
            int l = (i + 1) % j;
            float f = (hippo.tickCount % 25 + partialTicks) / 25.0F;
            Color4i afloat1 = new Color4i(Sheep.getColor(DyeColor.byId(k)));
            Color4i afloat2 = new Color4i(Sheep.getColor(DyeColor.byId(l)));
            ((HippocampusModel)this.getParentModel())
               .renderToBuffer(
                  matrixStackIn,
                  ivertexbuilder,
                  packedLightIn,
                  LivingEntityRenderer.getOverlayCoords(hippo, 0.0F),
                  new Color4i(afloat1.r * (1.0F - f) + afloat2.r * f, afloat1.g * (1.0F - f) + afloat2.g * f, afloat1.b * (1.0F - f) + afloat2.b * f, 1.0)
                     .getIntValue()
               );
         }
      }
   }

   private static class LayerHippocampusSaddle extends RenderLayer<HippocampusEntity, HippocampusModel> {
      private final RenderType SADDLE_TEXTURE = RenderType.entityNoOutline(
         ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hippocampus/saddle.png")
      );
      private final RenderType BRIDLE = RenderType.entityNoOutline(
         ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hippocampus/bridle.png")
      );
      private final RenderType CHEST = RenderType.entityTranslucent(
         ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hippocampus/chest.png")
      );
      private final RenderType TEXTURE_DIAMOND = RenderType.entityCutout(
         ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hippocampus/armor_diamond.png")
      );
      private final RenderType TEXTURE_GOLD = RenderType.entityCutout(
         ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hippocampus/armor_gold.png")
      );
      private final RenderType TEXTURE_IRON = RenderType.entityCutout(
         ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hippocampus/armor_iron.png")
      );

      public LayerHippocampusSaddle(HippocampusEntityRenderer renderer) {
         super(renderer);
      }

      public void render(
         PoseStack matrixStackIn,
         MultiBufferSource bufferIn,
         int packedLightIn,
         HippocampusEntity hippo,
         float limbSwing,
         float limbSwingAmount,
         float partialTicks,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         if (hippo.isSaddled()) {
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.SADDLE_TEXTURE);
            ((HippocampusModel)this.getParentModel()).renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, -1);
         }

         if (hippo.isSaddled() && hippo.getControllingPassenger() != null) {
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.BRIDLE);
            ((HippocampusModel)this.getParentModel()).renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, -1);
         }

         if (hippo.isChested()) {
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.CHEST);
            ((HippocampusModel)this.getParentModel()).renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, -1);
         }

         if (hippo.getArmorValue() != 0) {
            RenderType type = switch (hippo.getArmorValue()) {
               case 1 -> this.TEXTURE_IRON;
               case 2 -> this.TEXTURE_GOLD;
               case 3 -> this.TEXTURE_DIAMOND;
               default -> null;
            };
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(type);
            ((HippocampusModel)this.getParentModel()).renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, -1);
         }
      }
   }
}
