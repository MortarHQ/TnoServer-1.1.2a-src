package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.misc.ESBoat;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ChestRaftModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import org.joml.Quaternionf;

public class ESBoatRenderer extends EntityRenderer<ESBoat> {
   private final Map<ESBoat.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

   public ESBoatRenderer(Context context, boolean chest) {
      super(context);
      this.shadowRadius = 0.8F;
      this.boatResources = Stream.of(ESBoat.Type.values())
         .collect(
            ImmutableMap.toImmutableMap(
               type -> type, type -> Pair.of(EternalStarlight.id(getTextureLocation(type, chest)), this.createBoatModel(context, type, chest))
            )
         );
   }

   private static ModelLayerLocation createLocation(String path, String model) {
      return new ModelLayerLocation(EternalStarlight.id(path), model);
   }

   public static ModelLayerLocation createBoatModelName(ESBoat.Type pType) {
      return createLocation("boat/" + pType.getName(), "main");
   }

   public static ModelLayerLocation createChestBoatModelName(ESBoat.Type type) {
      return createLocation("chest_boat/" + type.getName(), "main");
   }

   private ListModel<Boat> createBoatModel(Context context, ESBoat.Type type, boolean chest) {
      ModelLayerLocation modellayerlocation = chest ? createChestBoatModelName(type) : createBoatModelName(type);
      if (type == ESBoat.Type.JINGLESTEM) {
         return (ListModel<Boat>)(chest ? new ChestRaftModel(context.bakeLayer(modellayerlocation)) : new RaftModel(context.bakeLayer(modellayerlocation)));
      } else {
         return (ListModel<Boat>)(chest ? new ChestBoatModel(context.bakeLayer(modellayerlocation)) : new BoatModel(context.bakeLayer(modellayerlocation)));
      }
   }

   private static String getTextureLocation(ESBoat.Type type, boolean chest) {
      return chest ? "textures/entity/chest_boat/" + type.getName() + ".png" : "textures/entity/boat/" + type.getName() + ".png";
   }

   public void render(ESBoat boat, float boatYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
      stack.pushPose();
      stack.translate(0.0F, 0.375F, 0.0F);
      stack.mulPose(Axis.YP.rotationDegrees(180.0F - boatYaw));
      float f = boat.getHurtTime() - partialTicks;
      float f1 = boat.getDamage() - partialTicks;
      if (f1 < 0.0F) {
         f1 = 0.0F;
      }

      if (f > 0.0F) {
         stack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * boat.getHurtDir()));
      }

      float f2 = boat.getBubbleAngle(partialTicks);
      if (!Mth.equal(f2, 0.0F)) {
         stack.mulPose(new Quaternionf().setAngleAxis(boat.getBubbleAngle(partialTicks) * (float) (Math.PI / 180.0), 1.0F, 0.0F, 1.0F));
      }

      Pair<ResourceLocation, ListModel<Boat>> pair = this.getModelWithLocation(boat);
      ResourceLocation resourcelocation = (ResourceLocation)pair.getFirst();
      ListModel<Boat> model = (ListModel<Boat>)pair.getSecond();
      stack.scale(-1.0F, -1.0F, 1.0F);
      stack.mulPose(Axis.YP.rotationDegrees(90.0F));
      model.setupAnim(boat, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
      VertexConsumer vertexconsumer = buffer.getBuffer(model.renderType(resourcelocation));
      model.renderToBuffer(stack, vertexconsumer, light, OverlayTexture.NO_OVERLAY);
      if (model instanceof BoatModel boatModel && !boat.isUnderWater()) {
         VertexConsumer vertexconsumer1 = buffer.getBuffer(RenderType.waterMask());
         boatModel.waterPatch().render(stack, vertexconsumer1, light, OverlayTexture.NO_OVERLAY);
      }

      stack.popPose();
      super.render(boat, boatYaw, partialTicks, stack, buffer, light);
   }

   public ResourceLocation getTextureLocation(ESBoat entity) {
      return (ResourceLocation)this.boatResources.get(entity.getESBoatType()).getFirst();
   }

   public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(ESBoat boat) {
      return this.boatResources.get(boat.getESBoatType());
   }
}
