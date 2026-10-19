package xaero.map.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.opengl.GL11;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.WorldMapSession;
import xaero.map.element.render.ElementReader;
import xaero.map.element.render.ElementRenderInfo;
import xaero.map.element.render.ElementRenderLocation;
import xaero.map.element.render.ElementRenderProvider;
import xaero.map.element.render.ElementRenderer;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.map.gui.GuiMap;
import xaero.map.mods.SupportMods;
import xaero.map.world.MapDimension;

public class MapElementRenderHandler {
   private final List<ElementRenderer<?, ?, ?>> renderers;
   protected final ElementRenderLocation location;
   private HoveredMapElementHolder<?, ?> previousHovered;
   private boolean previousHoveredPresent;
   private boolean renderingHovered;
   private Object workingHovered;
   private ElementRenderer<?, ?, ?> workingHoveredRenderer;

   private MapElementRenderHandler(List<ElementRenderer<?, ?, ?>> renderers, ElementRenderLocation location) {
      this.renderers = renderers;
      this.location = location;
   }

   public void add(ElementRenderer<?, ?, ?> renderer) {
      this.renderers.add(renderer);
   }

   public static <E, C> HoveredMapElementHolder<E, C> createResult(E hovered, ElementRenderer<?, ?, ?> hoveredRenderer) {
      ElementRenderer<E, C, ?> rendererCast = (ElementRenderer<E, C, ?>)hoveredRenderer;
      return new HoveredMapElementHolder<>(hovered, rendererCast);
   }

   private <E> ElementRenderer<E, ?, ?> getRenderer(HoveredMapElementHolder<E, ?> holder) {
      return holder.getRenderer();
   }

   public HoveredMapElementHolder<?, ?> render(
      GuiMap mapScreen,
      GuiGraphics guiGraphics,
      BufferSource renderTypeBuffers,
      MultiTextureRenderTypeRendererProvider rendererProvider,
      double cameraX,
      double cameraZ,
      int width,
      int height,
      double screenSizeBasedScale,
      double scale,
      double playerDimDiv,
      double mouseX,
      double mouseZ,
      float brightness,
      boolean cave,
      HoveredMapElementHolder<?, ?> oldHovered,
      Minecraft mc,
      float partialTicks
   ) {
      MapProcessor mapProcessor = WorldMapSession.getCurrentSession().getMapProcessor();
      MapDimension mapDimension = mapProcessor.getMapWorld().getCurrentDimension();
      double mapDimScale = mapDimension.calculateDimScale(mapProcessor.getWorldDimensionTypeRegistry());
      PoseStack matrixStack = guiGraphics.pose();
      TextureManager textureManager = mc.getTextureManager();
      Font fontRenderer = mc.font;
      textureManager.bindForSetup(WorldMap.guiTextures);
      RenderSystem.setShaderTexture(0, WorldMap.guiTextures);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      double baseScale = 1.0 / scale;
      Collections.sort(this.renderers);
      if (this.previousHovered == null) {
         this.previousHovered = oldHovered;
      }

      this.workingHovered = null;
      this.workingHoveredRenderer = null;
      this.previousHoveredPresent = false;
      ElementRenderInfo renderInfo = new ElementRenderInfo(
         this.location,
         mc.getCameraEntity(),
         mc.player,
         new Vec3(cameraX, -1.0, cameraZ),
         mouseX,
         mouseZ,
         scale,
         cave,
         partialTicks,
         brightness,
         screenSizeBasedScale,
         null,
         mapDimScale,
         mapDimension.getDimId()
      );
      matrixStack.pushPose();
      matrixStack.translate(0.0F, 0.0F, -980.0F);
      matrixStack.scale((float)baseScale, (float)baseScale, 1.0F);

      for (ElementRenderer<?, ?, ?> renderer : this.renderers) {
         this.renderWithRenderer(renderer, guiGraphics, renderInfo, renderTypeBuffers, rendererProvider, width, height, baseScale, playerDimDiv, true, 0, 0);
      }

      if (this.previousHoveredPresent) {
         this.renderHoveredWithRenderer(this.previousHovered, guiGraphics, renderTypeBuffers, rendererProvider, renderInfo, baseScale, playerDimDiv, true, 0, 0);
      }

      this.previousHoveredPresent = false;
      int indexLimit = 19490;

      for (ElementRenderer<?, ?, ?> renderer : this.renderers) {
         int elementIndex = 0;
         elementIndex = this.renderWithRenderer(
            renderer, guiGraphics, renderInfo, renderTypeBuffers, rendererProvider, width, height, baseScale, playerDimDiv, false, elementIndex, indexLimit
         );
         matrixStack.translate(0.0, 0.0, this.getElementIndexDepth(elementIndex, indexLimit));
         indexLimit -= elementIndex;
         if (indexLimit < 0) {
            indexLimit = 0;
         }
      }

      if (this.previousHoveredPresent) {
         this.renderHoveredWithRenderer(
            this.previousHovered, guiGraphics, renderTypeBuffers, rendererProvider, renderInfo, baseScale, playerDimDiv, false, 0, indexLimit
         );
      }

      matrixStack.popPose();
      textureManager.bindForSetup(WorldMap.guiTextures);
      RenderSystem.setShaderTexture(0, WorldMap.guiTextures);
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
      this.previousHovered = this.previousHovered != null && this.previousHovered.is(this.workingHovered)
         ? this.previousHovered
         : (this.workingHovered == null ? null : createResult(this.workingHovered, this.workingHoveredRenderer));
      return this.previousHovered;
   }

   private <E, C> int renderHoveredWithRenderer(
      HoveredMapElementHolder<E, C> hoveredHolder,
      GuiGraphics guiGraphics,
      BufferSource renderTypeBuffers,
      MultiTextureRenderTypeRendererProvider rendererProvider,
      ElementRenderInfo renderInfo,
      double baseScale,
      double playerDimDiv,
      boolean pre,
      int elementIndex,
      int indexLimit
   ) {
      ElementRenderer<E, C, ?> renderer = hoveredHolder.getRenderer();
      if (!renderer.shouldRenderHovered(pre)) {
         return elementIndex;
      }

      PoseStack matrixStack = guiGraphics.pose();
      ElementReader<E, C, ?> reader = renderer.getReader();
      E hoveredCast = hoveredHolder.getElement();
      renderer.preRender(renderInfo, renderTypeBuffers, rendererProvider, pre);
      matrixStack.pushPose();
      if (!pre) {
         matrixStack.translate(0.0F, 0.0F, 1.0F);
      }

      double rendererDimDiv = renderer.shouldBeDimScaled() ? playerDimDiv : 1.0;
      this.renderingHovered = true;
      if (!reader.isHidden(hoveredCast, renderer.getContext())
         && this.transformAndRenderElement(
            renderer, hoveredCast, true, guiGraphics, renderInfo, renderTypeBuffers, rendererProvider, baseScale, rendererDimDiv, pre, elementIndex, indexLimit
         )
         && !pre) {
         elementIndex++;
      }

      this.renderingHovered = false;
      matrixStack.popPose();
      renderer.postRender(renderInfo, renderTypeBuffers, rendererProvider, pre);
      return elementIndex;
   }

   private <E, C, R extends ElementRenderer<E, C, R>> int renderWithRenderer(
      ElementRenderer<E, C, R> renderer,
      GuiGraphics guiGraphics,
      ElementRenderInfo renderInfo,
      BufferSource renderTypeBuffers,
      MultiTextureRenderTypeRendererProvider rendererProvider,
      int width,
      int height,
      double baseScale,
      double playerDimDiv,
      boolean pre,
      int elementIndex,
      int indexLimit
   ) {
      ElementRenderLocation location = this.location;
      if (!renderer.shouldRender(location, pre)) {
         return elementIndex;
      }

      ElementReader<E, C, R> reader = renderer.getReader();
      ElementRenderProvider<E, C> provider = renderer.getProvider();
      C context = renderer.getContext();
      double rendererDimDiv = renderer.shouldBeDimScaled() ? playerDimDiv : 1.0;
      renderer.preRender(renderInfo, renderTypeBuffers, rendererProvider, pre);
      provider.begin(location, context);

      while (provider.hasNext(location, context)) {
         E e = provider.setupContextAndGetNext(location, context);
         if (e != null
            && !reader.isHidden(e, context)
            && reader.isOnScreen(
               e,
               renderInfo.renderPos.x,
               renderInfo.renderPos.z,
               width,
               height,
               renderInfo.scale,
               renderInfo.screenSizeBasedScale,
               rendererDimDiv,
               context,
               renderInfo.partialTicks
            )
            && this.transformAndRenderElement(
               renderer, e, false, guiGraphics, renderInfo, renderTypeBuffers, rendererProvider, baseScale, rendererDimDiv, pre, elementIndex, indexLimit
            )
            && !pre) {
            elementIndex++;
         }
      }

      provider.end(location, context);
      renderer.postRender(renderInfo, renderTypeBuffers, rendererProvider, pre);
      return elementIndex;
   }

   private <E, C, R extends ElementRenderer<E, C, R>> boolean transformAndRenderElement(
      ElementRenderer<E, C, R> renderer,
      E e,
      boolean highlighted,
      GuiGraphics guiGraphics,
      ElementRenderInfo renderInfo,
      BufferSource renderTypeBuffers,
      MultiTextureRenderTypeRendererProvider rendererProvider,
      double baseScale,
      double rendererDimDiv,
      boolean pre,
      int elementIndex,
      int indexLimit
   ) {
      PoseStack matrixStack = guiGraphics.pose();
      ElementReader<E, C, R> reader = renderer.getReader();
      C context = renderer.getContext();
      if (!this.renderingHovered) {
         if (reader.isInteractable(renderInfo.location, e)
            && reader.isHoveredOnMap(
               this.location,
               e,
               renderInfo.mouseX,
               renderInfo.mouseZ,
               renderInfo.scale,
               renderInfo.screenSizeBasedScale,
               rendererDimDiv,
               context,
               renderInfo.partialTicks
            )) {
            this.workingHovered = e;
            this.workingHoveredRenderer = renderer;
         }

         if (!this.previousHoveredPresent && this.previousHovered != null && this.previousHovered.is(e)) {
            this.previousHoveredPresent = true;
            return false;
         }
      }

      matrixStack.pushPose();
      double offX = (reader.getRenderX(e, context, renderInfo.partialTicks) / rendererDimDiv - renderInfo.renderPos.x) / baseScale;
      double offZ = (reader.getRenderZ(e, context, renderInfo.partialTicks) / rendererDimDiv - renderInfo.renderPos.z) / baseScale;
      long roundedOffX = Math.round(offX);
      long roundedOffZ = Math.round(offZ);
      double partialX = offX - roundedOffX;
      double partialY = offZ - roundedOffZ;
      matrixStack.translate((float)roundedOffX, (float)roundedOffZ, 0.0F);
      boolean result = false;
      if (pre) {
         renderer.renderElementShadow(
            e, highlighted, (float)renderInfo.screenSizeBasedScale, partialX, partialY, renderInfo, guiGraphics, renderTypeBuffers, rendererProvider
         );
      } else {
         double optionalDepth = this.getElementIndexDepth(elementIndex, indexLimit);
         result = renderer.renderElement(
            e,
            highlighted,
            optionalDepth,
            (float)renderInfo.screenSizeBasedScale,
            partialX,
            partialY,
            renderInfo,
            guiGraphics,
            renderTypeBuffers,
            rendererProvider
         );
      }

      matrixStack.popPose();
      return result;
   }

   private double getElementIndexDepth(int elementIndex, int indexLimit) {
      return (elementIndex >= indexLimit ? indexLimit : elementIndex) * 0.1;
   }

   public static final class Builder {
      private Builder() {
      }

      public MapElementRenderHandler build() {
         List<ElementRenderer<?, ?, ?>> renderers = new ArrayList<>();
         if (SupportMods.minimap()) {
            renderers.add(SupportMods.xaeroMinimap.getWaypointRenderer());
         }

         renderers.add(WorldMap.trackedPlayerRenderer);
         if (SupportMods.pac()) {
            renderers.add(SupportMods.xaeroPac.getCaimResultElementRenderer());
         }

         return new MapElementRenderHandler(renderers, ElementRenderLocation.WORLD_MAP);
      }

      public static MapElementRenderHandler.Builder begin() {
         return new MapElementRenderHandler.Builder();
      }
   }
}
