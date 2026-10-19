package xaero.hud.minimap.radar.icon.creator.render.form.model;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xaero.common.exception.OpenGLException;
import xaero.common.graphics.CustomRenderTypes;
import xaero.common.misc.OptimizedMath;
import xaero.hud.minimap.MinimapLogs;
import xaero.hud.minimap.radar.icon.creator.RadarIconCreator;
import xaero.hud.minimap.radar.icon.creator.entity.LivingEntityPoseResetter;
import xaero.hud.minimap.radar.icon.creator.render.form.IRadarIconFormPrerenderer;
import xaero.hud.minimap.radar.icon.creator.render.form.model.custom.RadarIconCustomPrerenderer;
import xaero.hud.minimap.radar.icon.creator.render.form.model.part.RadarIconModelPartPrerenderer;
import xaero.hud.minimap.radar.icon.creator.render.trace.ModelRenderTrace;
import xaero.hud.minimap.radar.icon.definition.BuiltInRadarIconDefinitions;
import xaero.hud.minimap.radar.icon.definition.form.model.RadarIconModelForm;
import xaero.hud.minimap.radar.icon.definition.form.model.config.RadarIconModelConfig;
import xaero.lib.client.graphics.util.ImmediateRenderUtil;

public class RadarIconModelFormPrerenderer implements IRadarIconFormPrerenderer {
   private final LivingEntityPoseResetter livingEntityPoseResetter;
   private ModelRenderTrace mainModelTrace;
   private final ArrayList<ModelPart> mainRenderedModels;
   private final RadarIconModelPrerenderer modelPrerenderer;
   private ModelPart mainPart;
   private List<String> hardcodedMainPartAliases;
   private List<String> hardcodedModelPartsFields;
   private boolean forceFieldCheck;
   private boolean fullModelIcon;
   private BufferSource entityIconRenderTypeBuffer = MultiBufferSource.immediate(new ByteBufferBuilder(256));

   public RadarIconModelFormPrerenderer() {
      this.livingEntityPoseResetter = new LivingEntityPoseResetter();
      this.mainRenderedModels = new ArrayList<>();
      this.modelPrerenderer = new RadarIconModelPrerenderer();
   }

   @Override
   public boolean requiresEntityModel() {
      return true;
   }

   @Override
   public boolean isFlipped() {
      return false;
   }

   @Override
   public boolean isOutlined() {
      return true;
   }

   @Override
   public <T extends Entity> boolean prerender(
      GuiGraphics guiGraphics,
      EntityRenderer<? super T> entityRenderer,
      @Nullable EntityModel<T> entityModel,
      T entity,
      @Nullable List<ModelRenderTrace> traceList,
      RadarIconCreator.Parameters parameters
   ) {
      PoseStack matrixStack = guiGraphics.pose();
      RadarIconModelForm modelForm = (RadarIconModelForm)parameters.form;
      GlStateManager._enableCull();
      BufferSource renderTypeBuffer = this.entityIconRenderTypeBuffer;
      GlStateManager._disableBlend();
      GlStateManager._enableBlend();
      GlStateManager._disableDepthTest();
      GlStateManager._enableCull();
      GlStateManager._depthFunc(515);
      Lighting.setupForFlatItems();
      if (parameters.debug) {
         matrixStack.pushPose();
         matrixStack.translate(0.0F, 10.0F, -10.0F);
         matrixStack.scale(1.0F, 1.0F, 1.0F);
         ImmediateRenderUtil.coloredRectangle(matrixStack, 0.0F, 0.0F, 9.0F, 9.0F, -65536);
         matrixStack.popPose();
         GlStateManager._enableBlend();
         RenderSystem.blendFuncSeparate(770, 771, 1, 771);
      }

      RadarIconModelConfig config = parameters.defaultModelConfig;
      RadarIconModelConfig variantModelConfig = modelForm.getConfig();
      if (variantModelConfig != null) {
         config = variantModelConfig;
      }

      matrixStack.pushPose();
      matrixStack.translate(32.0F, 32.0F, -450.0F);
      matrixStack.translate(config.offsetX, config.offsetY, 0.0F);
      int mainScale = 32;
      matrixStack.scale(mainScale, mainScale, -mainScale);
      float scale = parameters.scale;
      if (scale < 1.0F) {
         matrixStack.scale(scale, scale, scale);
      }

      matrixStack.scale(config.baseScale, config.baseScale, config.baseScale);
      OptimizedMath.rotatePose(matrixStack, config.rotationY, OptimizedMath.YP);
      OptimizedMath.rotatePose(matrixStack, config.rotationX, OptimizedMath.XP);
      OptimizedMath.rotatePose(matrixStack, config.rotationZ, OptimizedMath.ZP);
      BuiltInRadarIconDefinitions.defaultTransformation(matrixStack, entityModel, entity);
      LivingEntity livingEntity = entity instanceof LivingEntity ? (LivingEntity)entity : null;
      if (livingEntity != null) {
         this.livingEntityPoseResetter.storeAndReset(livingEntity);
      }

      boolean isChildBU = entityModel.young;
      entityModel.young = false;
      boolean result = this.renderLayers(matrixStack, renderTypeBuffer, entityRenderer, entityModel, traceList, entity, config, parameters.defaultModelConfig);
      entityModel.young = isChildBU;
      if (livingEntity != null) {
         this.livingEntityPoseResetter.restore(livingEntity);
      }

      BuiltInRadarIconDefinitions.defaultPostIconModelRender(matrixStack, entityModel, entity);
      matrixStack.popPose();
      if (parameters.debug) {
         matrixStack.pushPose();
         matrixStack.translate(9.0F, 10.0F, -10.0F);
         matrixStack.scale(1.0F, 1.0F, 1.0F);
         ImmediateRenderUtil.coloredRectangle(matrixStack, 0.0F, 0.0F, 9.0F, 9.0F, -16711936);
         matrixStack.popPose();
         RenderSystem.blendFuncSeparate(770, 771, 1, 771);
      }

      return result;
   }

   private <T extends Entity> boolean renderLayers(
      PoseStack matrixStack,
      BufferSource bufferSource,
      EntityRenderer<? super T> entityRenderer,
      EntityModel<T> mainEntityModel,
      List<ModelRenderTrace> traceList,
      T entity,
      RadarIconModelConfig config,
      RadarIconModelConfig defaultConfig
   ) {
      this.forceFieldCheck = (config.renderingFullModel == null || !config.renderingFullModel)
         && (config.modelPartsFields != null || BuiltInRadarIconDefinitions.forceFieldCheck(mainEntityModel));
      this.fullModelIcon = config.renderingFullModel == null
         ? !this.forceFieldCheck && BuiltInRadarIconDefinitions.fullModelIcon(mainEntityModel)
         : config.renderingFullModel;
      boolean renderedSomething = false;
      if (traceList.isEmpty()) {
         this.addDefaultLayer(traceList, entityRenderer, mainEntityModel, entity);
      }

      boolean allEmpty = true;

      for (ModelRenderTrace mrt : traceList) {
         if (!mrt.isEmpty()) {
            allEmpty = false;
            break;
         }
      }

      if (allEmpty) {
         for (ModelRenderTrace mrt : traceList) {
            mrt.allVisible = true;
         }
      }

      this.mainPart = null;
      this.mainModelTrace = null;
      this.hardcodedMainPartAliases = BuiltInRadarIconDefinitions.getMainModelPartFields(entityRenderer, mainEntityModel, entity);
      this.hardcodedModelPartsFields = BuiltInRadarIconDefinitions.getSecondaryModelPartsFields(entityRenderer, mainEntityModel, entity);
      this.mainRenderedModels.clear();

      for (ModelRenderTrace mrt : traceList) {
         if (!mrt.isEmpty() && (!renderedSomething || config.layersAllowed)) {
            int result = this.renderLayer(matrixStack, bufferSource, mrt, mainEntityModel, entity, config, defaultConfig);
            if (result == -1) {
               break;
            }

            if (result == 1) {
               renderedSomething = true;
            }
         }
      }

      this.hardcodedMainPartAliases = null;
      this.hardcodedModelPartsFields = null;
      if (!this.mainRenderedModels.isEmpty() && config.layersAllowed) {
         RadarIconCustomPrerenderer extraLayer = BuiltInRadarIconDefinitions.getCustomLayer(entityRenderer, entity);
         RadarIconModelPartPrerenderer partPrerenderer = this.modelPrerenderer.getPartPrerenderer();
         if (extraLayer != null) {
            this.mainPart = extraLayer.render(
               matrixStack,
               bufferSource,
               entityRenderer,
               entity,
               mainEntityModel,
               partPrerenderer,
               this.mainRenderedModels,
               this.mainPart,
               config,
               this.mainModelTrace
            );
         }

         return renderedSomething;
      } else {
         return renderedSomething;
      }
   }

   private <T extends Entity> void addDefaultLayer(
      List<ModelRenderTrace> traceList, EntityRenderer<? super T> entityRenderer, EntityModel<T> mainEntityModel, T entity
   ) {
      ResourceLocation mainEntityTexture = null;

      try {
         mainEntityTexture = entityRenderer.getTextureLocation(entity);
      } catch (Throwable t) {
         MinimapLogs.LOGGER.error("Couldn't fetch main entity texture when prerendering an icon with nothing detected!", t);
      }

      if (mainEntityTexture != null) {
         traceList.add(new ModelRenderTrace(mainEntityModel, mainEntityTexture, null, CustomRenderTypes.getBasicEntityIconLayerPhases(mainEntityTexture), -1));
      }
   }

   private <T extends Entity> int renderLayer(
      PoseStack matrixStack,
      BufferSource bufferSource,
      ModelRenderTrace mrt,
      EntityModel<T> mainEntityModel,
      T entity,
      RadarIconModelConfig config,
      RadarIconModelConfig defaultConfig
   ) {
      EntityModel<?> traceModel = mrt.model;
      if (entity instanceof Player && traceModel instanceof HumanoidArmorModel) {
         return 0;
      }

      ResourceLocation traceTexture = mrt.renderTexture;
      TextureAtlasSprite traceAtlasSprite = mrt.renderAtlasSprite;
      boolean mainModel = traceModel == mainEntityModel;
      boolean mainPartsVisibility = mainModel && this.mainModelTrace != null && mrt.sameVisibility(this.mainModelTrace);
      if (mainModel && !mainPartsVisibility) {
         if (traceTexture == null) {
            return 0;
         }

         if (!this.resetModelRotations(entity, (EntityModel<T>)traceModel)) {
            return -1;
         }

         this.mainRenderedModels.clear();
         RadarIconModelPrerenderer.Parameters parameters = new RadarIconModelPrerenderer.Parameters(
            config,
            defaultConfig,
            traceTexture,
            traceAtlasSprite,
            mrt,
            this.forceFieldCheck,
            this.fullModelIcon,
            this.hardcodedMainPartAliases,
            this.hardcodedModelPartsFields,
            this.mainRenderedModels
         );
         this.mainPart = this.modelPrerenderer.renderModel(matrixStack, bufferSource, traceModel, entity, this.mainPart, parameters);
         this.mainModelTrace = mrt;
         return !this.mainRenderedModels.isEmpty() ? 1 : 0;
      } else if (!mainModel) {
         if (traceTexture == null) {
            return 0;
         }

         if (!this.resetModelRotations(entity, (EntityModel<T>)traceModel)) {
            return -1;
         }

         ArrayList<ModelPart> renderedModels = new ArrayList<>();
         RadarIconModelPrerenderer.Parameters parameters = new RadarIconModelPrerenderer.Parameters(
            config,
            defaultConfig,
            traceTexture,
            traceAtlasSprite,
            mrt,
            this.forceFieldCheck,
            this.fullModelIcon,
            this.hardcodedMainPartAliases,
            this.hardcodedModelPartsFields,
            renderedModels
         );
         this.mainPart = this.modelPrerenderer.renderModel(matrixStack, bufferSource, traceModel, entity, this.mainPart, parameters);
         return !renderedModels.isEmpty() ? 1 : 0;
      } else {
         if (this.mainRenderedModels.isEmpty()) {
            return 0;
         }

         VertexConsumer vertexConsumer = this.modelPrerenderer.getLayerModelVertexConsumer(bufferSource, traceTexture, traceAtlasSprite, mrt);
         RadarIconModelPartPrerenderer.Parameters parameters = new RadarIconModelPartPrerenderer.Parameters(config, mrt, new ArrayList<>());
         this.modelPrerenderer.getPartPrerenderer().renderPartsIterable(this.mainRenderedModels, matrixStack, vertexConsumer, this.mainPart, parameters);
         bufferSource.endBatch();
         return 0;
      }
   }

   private <T extends Entity> boolean resetModelRotations(Entity entity, EntityModel<T> model) {
      try {
         model.prepareMobModel(entity, 0.0F, 0.0F, 1.0F);
         model.setupAnim(entity, 0.0F, 0.0F, entity.tickCount, 0.0F, 0.0F);
         OpenGLException.checkGLError();
         return true;
      } catch (Throwable t) {
         MinimapLogs.LOGGER.error("suppressed exception", t);
         return false;
      }
   }
}
