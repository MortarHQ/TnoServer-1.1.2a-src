package xaero.hud.minimap.radar.icon.creator.render.form.model.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import xaero.hud.minimap.radar.icon.creator.render.form.model.part.RadarIconModelPartPrerenderer;
import xaero.hud.minimap.radar.icon.creator.render.trace.ModelRenderTrace;
import xaero.hud.minimap.radar.icon.definition.form.model.config.RadarIconModelConfig;

public abstract class RenderTypeIconCustomPrerenderer extends RadarIconCustomPrerenderer {
   @Override
   public <T extends Entity> ModelPart render(
      PoseStack matrixStack,
      BufferSource bufferSource,
      EntityRenderer<? super T> entityRenderer,
      T e,
      EntityModel<T> defaultModel,
      RadarIconModelPartPrerenderer partPrerenderer,
      List<ModelPart> rendered,
      ModelPart mainPart,
      RadarIconModelConfig config,
      ModelRenderTrace mrt
   ) {
      RenderType renderType = this.getRenderType(entityRenderer, e);
      if (renderType == null) {
         return mainPart;
      }

      VertexConsumer vertexBuilder = bufferSource.getBuffer(renderType);
      Iterable<ModelPart> modelParts = this.getModelParts(partPrerenderer, rendered, e, defaultModel);
      List<ModelPart> renderedDest = this.getRenderedDest(rendered);
      RadarIconModelPartPrerenderer.Parameters parameters = new RadarIconModelPartPrerenderer.Parameters(config, mrt, renderedDest);
      mainPart = partPrerenderer.renderPartsIterable(modelParts, matrixStack, vertexBuilder, mainPart, parameters);
      bufferSource.endBatch();
      return mainPart;
   }

   protected <T extends Entity> Iterable<ModelPart> getModelParts(
      RadarIconModelPartPrerenderer partPrerenderer, List<ModelPart> rendered, T entity, EntityModel<T> defaultModel
   ) {
      return rendered;
   }

   protected List<ModelPart> getRenderedDest(List<ModelPart> rendered) {
      return new ArrayList<>();
   }

   protected abstract <T extends Entity> RenderType getRenderType(EntityRenderer<? super T> var1, T var2);
}
