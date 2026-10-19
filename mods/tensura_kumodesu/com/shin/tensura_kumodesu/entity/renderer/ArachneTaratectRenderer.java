package com.shin.tensura_kumodesu.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.shin.tensura_kumodesu.config.race.TaratectConfig;
import com.shin.tensura_kumodesu.entity.model.geo.ArachneTaratectModel;
import com.shin.tensura_kumodesu.entity.taratect.ArachneTaratectEntity;
import io.github.manasmods.manascore.config.ConfigRegistry;
import java.util.List;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.renderer.layer.FastBoneFilterGeoLayer;

public class ArachneTaratectRenderer extends GeoEntityRenderer<ArachneTaratectEntity> {
   public ArachneTaratectRenderer(Context context) {
      super(context, new ArachneTaratectModel());
      this.addRenderLayer(new FastBoneFilterGeoLayer(this, () -> List.of("HAT", "OUTFIT"), (bone, entity, partialTick) -> bone.setHidden(true)));
      this.addRenderLayer(
         new FastBoneFilterGeoLayer(this, () -> List.of("Biped"), (bone, entity, partialTick) -> bone.setHidden(entity.getOwner() instanceof Player))
      );
      this.addRenderLayer(
         new AutoGlowingGeoLayer<ArachneTaratectEntity>(this) {
            public void render(
               PoseStack poseStack,
               ArachneTaratectEntity entity,
               BakedGeoModel bakedModel,
               @Nullable RenderType renderType,
               MultiBufferSource bufferSource,
               @Nullable VertexConsumer buffer,
               float partialTick,
               int packedLight,
               int packedOverlay
            ) {
               super.render(poseStack, entity, bakedModel, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
            }
         }
      );
   }

   public void actuallyRender(
      PoseStack poseStack,
      ArachneTaratectEntity animatable,
      BakedGeoModel model,
      @Nullable RenderType renderType,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      float scale = (float)(1.0 + ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).Arachne.getSize());
      poseStack.pushPose();
      poseStack.scale(scale, scale, scale);
      super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      poseStack.popPose();
   }
}
