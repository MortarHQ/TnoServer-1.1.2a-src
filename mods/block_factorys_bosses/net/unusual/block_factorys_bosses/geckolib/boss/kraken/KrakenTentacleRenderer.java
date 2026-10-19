package net.unusual.block_factorys_bosses.geckolib.boss.kraken;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;
import net.unusual.block_factorys_bosses.entity.decoration.CannonEntity;
import net.unusual.block_factorys_bosses.geckolib.CommonPoseStack;
import net.unusual.block_factorys_bosses.geckolib.util.ParticleLocator;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class KrakenTentacleRenderer extends GeoEntityRenderer<KrakenTentacleEntity> {
   public static final Map<String, ParticleLocator> BONER = new HashMap<>();
   private static final KrakenTentacleModel MODEL = new KrakenTentacleModel(false);
   private static final KrakenTentacleModel SLIM_MODEL = new KrakenTentacleModel(true);

   public KrakenTentacleRenderer(Context context) {
      super(context, MODEL);
   }

   protected int getBlockLightLevel(KrakenTentacleEntity tentacle, BlockPos pos) {
      return tentacle.getVehicle() instanceof CannonEntity cannon
         ? super.getBlockLightLevel(tentacle, cannon.getPos())
         : super.getBlockLightLevel(tentacle, pos.above(5));
   }

   protected int getSkyLightLevel(KrakenTentacleEntity tentacle, BlockPos pos) {
      return tentacle.getVehicle() instanceof CannonEntity cannon
         ? super.getSkyLightLevel(tentacle, cannon.getPos())
         : super.getSkyLightLevel(tentacle, pos.above(5));
   }

   protected float getDeathMaxRotation(KrakenTentacleEntity tentacle) {
      return 0.0F;
   }

   public int getPackedOverlay(KrakenTentacleEntity tentacle, float u, float partialTick) {
      return OverlayTexture.pack(OverlayTexture.u(u), OverlayTexture.v(tentacle.hurtTime > 0));
   }

   public GeoModel<KrakenTentacleEntity> getGeoModel() {
      return ((KrakenTentacleEntity)this.animatable).getTentacleType().isSlim() ? SLIM_MODEL : MODEL;
   }

   public void preRender(
      PoseStack poseStack,
      KrakenTentacleEntity tentacle,
      BakedGeoModel model,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      super.preRender(poseStack, tentacle, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      tentacle.transformCrateGrab((CommonPoseStack)poseStack, partialTick);
   }

   public void renderRecursively(
      PoseStack poseStack,
      KrakenTentacleEntity animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      boolean shouldOffset = !animatable.getTentacleType().isSlim() && bone.getName().equals("bone_correction3");
      if (shouldOffset) {
         poseStack.pushPose();
         poseStack.translate(KrakenTentacleModel.ROOT_OFFSET.x(), KrakenTentacleModel.ROOT_OFFSET.y(), KrakenTentacleModel.ROOT_OFFSET.z());
      }

      super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      if (shouldOffset) {
         poseStack.popPose();
      }
   }
}
