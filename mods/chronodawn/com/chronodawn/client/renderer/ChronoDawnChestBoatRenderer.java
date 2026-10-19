package com.chronodawn.client.renderer;

import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.boats.ChronoDawnBoatType;
import com.chronodawn.entities.boats.ChronoDawnChestBoat;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import java.util.Map;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat.Type;

public class ChronoDawnChestBoatRenderer extends EntityRenderer<ChronoDawnChestBoat> {
   private final Map<ChronoDawnBoatType, Pair<ResourceLocation, ChestBoatModel>> boatResources;

   public ChronoDawnChestBoatRenderer(Context context) {
      super(context);
      this.shadowRadius = 0.8F;
      this.boatResources = createBoatResources(context);
   }

   private static Map<ChronoDawnBoatType, Pair<ResourceLocation, ChestBoatModel>> createBoatResources(Context context) {
      Builder<ChronoDawnBoatType, Pair<ResourceLocation, ChestBoatModel>> builder = ImmutableMap.builder();

      for (ChronoDawnBoatType type : ChronoDawnBoatType.values()) {
         String texturePath = "textures/entity/chest_boat/" + type.getName() + ".png";
         ResourceLocation texture = CompatResourceLocation.create("chronodawn", texturePath);
         ModelLayerLocation modelLayer = ModelLayers.createChestBoatModelName(Type.OAK);
         ModelPart modelPart = context.bakeLayer(modelLayer);
         ChestBoatModel model = new ChestBoatModel(modelPart);
         builder.put(type, Pair.of(texture, model));
      }

      return builder.build();
   }

   public void render(ChronoDawnChestBoat entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      poseStack.pushPose();
      poseStack.translate(0.0F, 0.375F, 0.0F);
      poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
      float hurtTime = entity.getHurtTime() - partialTicks;
      float damage = entity.getDamage() - partialTicks;
      if (damage < 0.0F) {
         damage = 0.0F;
      }

      if (hurtTime > 0.0F) {
         poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(hurtTime) * hurtTime * damage / 10.0F * entity.getHurtDir()));
      }

      float bubbleAngle = entity.getBubbleAngle(partialTicks);
      if (!Mth.equal(bubbleAngle, 0.0F)) {
         poseStack.mulPose(Axis.XP.rotationDegrees(entity.getBubbleAngle(partialTicks)));
      }

      Pair<ResourceLocation, ChestBoatModel> pair = this.getModelWithLocation(entity);
      ResourceLocation texture = (ResourceLocation)pair.getFirst();
      ChestBoatModel model = (ChestBoatModel)pair.getSecond();
      poseStack.scale(-1.0F, -1.0F, 1.0F);
      poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
      model.setupAnim(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
      VertexConsumer vertexConsumer = buffer.getBuffer(model.renderType(texture));
      model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
      if (!entity.isUnderWater()) {
         VertexConsumer waterVertexConsumer = buffer.getBuffer(RenderType.waterMask());
         model.waterPatch().render(poseStack, waterVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
      }

      poseStack.popPose();
      super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
   }

   private Pair<ResourceLocation, ChestBoatModel> getModelWithLocation(ChronoDawnChestBoat boat) {
      return this.boatResources.get(boat.getChronoDawnBoatType());
   }

   public ResourceLocation getTextureLocation(ChronoDawnChestBoat entity) {
      return (ResourceLocation)this.getModelWithLocation(entity).getFirst();
   }
}
