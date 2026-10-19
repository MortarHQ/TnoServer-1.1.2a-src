package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelSculptor;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.GeckoSunblockLayer;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.ItemLayerSculptorStaff;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Optional;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.util.Color;

public class RenderSculptor extends MowzieGeoEntityRenderer<EntitySculptor> {
   public final ResourceLocation staff_geo_location = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "geo/sculptor_staff.geo.json");
   public final ResourceLocation staff_tex_location = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/item/sculptor_staff.png");

   public RenderSculptor(Context renderManager) {
      super(renderManager, new ModelSculptor());
      this.addRenderLayer(new FrozenRenderHandler.GeckoLayerFrozen(this, renderManager));
      this.addRenderLayer(new GeckoSunblockLayer(this, renderManager));
      this.addRenderLayer(new ItemLayerSculptorStaff(this, "backItem"));
      this.addRenderLayer(new ItemLayerSculptorStaff(this, "itemHandLeft"));
      this.addRenderLayer(new ItemLayerSculptorStaff(this, "itemHandRight"));
      this.shadowRadius = 0.7F;
   }

   public void preRender(
      PoseStack poseStack,
      EntitySculptor animatable,
      BakedGeoModel model,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int color
   ) {
      super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
   }

   public void render(EntitySculptor entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      Optional<GeoBone> frontCloth = this.model.getBone("clothFront");
      frontCloth.ifPresent(geoBone -> entity.frontClothRot = geoBone instanceof MowzieGeoBone ? ((MowzieGeoBone)geoBone).getModelRotationMat() : null);
   }

   public void renderUpdates(EntitySculptor entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      super.renderUpdates(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      Optional<GeoBone> disappearControllerBone = this.model.getBone("disappearController");
      disappearControllerBone.ifPresent(geoBone -> entity.disappearController = geoBone.getPosX());
      Optional<GeoBone> calfRight = this.model.getBone("calfRight");
      Optional<GeoBone> calfLeft = this.model.getBone("calfLeft");
      Optional<GeoBone> thighRight = this.model.getBone("thighRight");
      Optional<GeoBone> thighLeft = this.model.getBone("thighLeft");
      Optional<GeoBone> skirtEndRight = this.model.getBone("skirtEndRight");
      Optional<GeoBone> skirtEndLeft = this.model.getBone("skirtEndLeft");
      Optional<GeoBone> skirtFrontLocRight = this.model.getBone("skirtFrontLocRight");
      Optional<GeoBone> skirtFrontLocLeft = this.model.getBone("skirtFrontLocLeft");
      Optional<GeoBone> skirtBackLocRight = this.model.getBone("skirtBackLocRight");
      Optional<GeoBone> skirtBackLocLeft = this.model.getBone("skirtBackLocLeft");
      calfRight.ifPresent(geoBone -> entity.calfRPos = geoBone.getModelPosition());
      calfLeft.ifPresent(geoBone -> entity.calfLPos = geoBone.getModelPosition());
      thighRight.ifPresent(geoBone -> entity.thighRPos = geoBone.getModelPosition());
      thighLeft.ifPresent(geoBone -> entity.thighLPos = geoBone.getModelPosition());
      skirtEndRight.ifPresent(geoBone -> entity.skirtEndRPos = geoBone.getModelPosition());
      skirtEndLeft.ifPresent(geoBone -> entity.skirtEndLPos = geoBone.getModelPosition());
      skirtFrontLocRight.ifPresent(geoBone -> entity.skirtLocFrontRPos = geoBone.getModelPosition());
      skirtFrontLocLeft.ifPresent(geoBone -> entity.skirtLocFrontLPos = geoBone.getModelPosition());
      skirtBackLocRight.ifPresent(geoBone -> entity.skirtLocBackRPos = geoBone.getModelPosition());
      skirtBackLocLeft.ifPresent(geoBone -> entity.skirtLocBackLPos = geoBone.getModelPosition());
      this.shadowRadius = 0.7F * (1.0F - entity.disappearController);
   }

   public Color getRenderColor(EntitySculptor animatable, float partialTick, int packedLight) {
      return Color.ofRGBA(1.0F, 1.0F, 1.0F, 1.0F - animatable.disappearController);
   }

   public RenderType getRenderType(EntitySculptor animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
      return animatable.disappearController > 0.0F
         ? RenderType.entityTranslucent(texture)
         : super.getRenderType(animatable, texture, bufferSource, partialTick);
   }
}
