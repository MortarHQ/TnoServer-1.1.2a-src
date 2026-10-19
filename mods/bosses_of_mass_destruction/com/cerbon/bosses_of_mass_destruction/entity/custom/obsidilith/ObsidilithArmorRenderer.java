package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.IRendererWithModel;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Random;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ObsidilithArmorRenderer implements IRendererWithModel, IRenderer<ObsidilithEntity> {
   private final GeoModel<ObsidilithEntity> geoModel;
   private final Context context;
   private final ResourceLocation armorTexture = ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "textures/entity/obsidilith_armor.png");
   private ObsidilithArmorRenderer.RenderHelper geoModelProvider;
   private ObsidilithEntity obsidilithEntity;
   private RenderType type;

   public ObsidilithArmorRenderer(GeoModel<ObsidilithEntity> geoModel, Context context) {
      this.geoModel = geoModel;
      this.context = context;
   }

   public void render(ObsidilithEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      float renderAge = entity.tickCount + partialTicks;
      float textureOffset = renderAge * new Random().nextFloat();
      if (this.geoModelProvider == null) {
         this.geoModelProvider = new ObsidilithArmorRenderer.RenderHelper(entity, this.geoModel, this.context);
      }

      this.obsidilithEntity = entity;
      this.type = RenderType.energySwirl(this.armorTexture, textureOffset, textureOffset);
   }

   @Override
   public void render(
      BakedGeoModel model,
      float partialTicks,
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      VertexConsumer energyBuffer = buffer.getBuffer(this.type);
      if (this.obsidilithEntity != null) {
         if (this.type != null) {
            if (this.obsidilithEntity.isShielded()) {
               Vec3 color = this.getColor().add(VecUtils.unit).normalize().scale(0.6);
               int colour = ARGB32.colorFromFloat(1.0F, (float)color.x, (float)color.y, (float)color.z);
               if (this.geoModelProvider == null) {
                  return;
               }

               this.geoModelProvider
                  .actuallyRender(
                     poseStack,
                     this.obsidilithEntity,
                     model,
                     this.type,
                     buffer,
                     energyBuffer,
                     false,
                     partialTicks,
                     packedLightIn,
                     OverlayTexture.NO_OVERLAY,
                     colour
                  );
            }
         }
      }
   }

   private Vec3 getColor() {
      return switch (this.obsidilithEntity.currentAttack) {
         case 5 -> Vec3Colors.ORANGE;
         case 6 -> Vec3Colors.RED;
         case 7 -> Vec3Colors.COMET_BLUE;
         case 8 -> Vec3Colors.ENDER_PURPLE;
         case 9 -> Vec3Colors.WHITE;
         default -> Vec3Colors.WHITE;
      };
   }

   private static class RenderHelper extends GeoEntityRenderer<ObsidilithEntity> {
      private final ObsidilithEntity entity;

      public RenderHelper(ObsidilithEntity entity, GeoModel<ObsidilithEntity> parentModel, Context context) {
         super(context, parentModel);
         this.entity = entity;
      }

      public void renderCube(PoseStack poseStack, GeoCube cube, VertexConsumer buffer, int packedLight, int packedOverlay, int colour) {
         poseStack.pushPose();
         poseStack.scale(1.08F, 1.05F, 1.08F);
         super.renderCube(poseStack, cube, buffer, 15728880, packedOverlay, colour);
         poseStack.popPose();
      }

      public ObsidilithEntity getAnimatable() {
         return this.entity;
      }
   }
}
