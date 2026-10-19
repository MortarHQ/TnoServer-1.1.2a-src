package com.shin.tensura_kumodesu.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.shin.tensura_kumodesu.config.race.TaratectConfig;
import com.shin.tensura_kumodesu.entity.model.geo.LesserPoisonTaratectModel;
import com.shin.tensura_kumodesu.entity.taratect.LesserPoisonTaratectEntity;
import io.github.manasmods.manascore.config.ConfigRegistry;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.renderer.layer.FastBoneFilterGeoLayer;

public class LesserPoisonTaratectRenderer extends GeoEntityRenderer<LesserPoisonTaratectEntity> {
   public LesserPoisonTaratectRenderer(Context context) {
      super(context, new LesserPoisonTaratectModel());
      this.addRenderLayer(new FastBoneFilterGeoLayer(this, () -> List.of("HAT", "OUTFIT"), (bone, entity, partialTick) -> bone.setHidden(true)));
      this.addRenderLayer(
         new AutoGlowingGeoLayer<LesserPoisonTaratectEntity>(this) {
            public void render(
               PoseStack poseStack,
               LesserPoisonTaratectEntity entity,
               BakedGeoModel bakedModel,
               @Nullable RenderType renderType,
               MultiBufferSource bufferSource,
               @Nullable VertexConsumer buffer,
               float partialTick,
               int packedLight,
               int packedOverlay
            ) {
               if (entity.getOwner() != Minecraft.getInstance().player) {
                  super.render(poseStack, entity, bakedModel, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
               }
            }
         }
      );
   }

   public void actuallyRender(
      PoseStack poseStack,
      LesserPoisonTaratectEntity animatable,
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
      float scale = (float)(1.0 + ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).LesserPoisonTaratect.getSize());
      poseStack.pushPose();
      poseStack.scale(scale, scale, scale);
      super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      poseStack.popPose();
   }
}
