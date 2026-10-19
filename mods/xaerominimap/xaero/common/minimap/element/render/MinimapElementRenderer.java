package xaero.common.minimap.element.render;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import xaero.common.HudMod;
import xaero.common.IXaeroMinimap;
import xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.common.minimap.render.MinimapRendererHelper;
import xaero.hud.minimap.element.render.MinimapElementRenderInfo;

@Deprecated
public abstract class MinimapElementRenderer<E, RC> extends xaero.hud.minimap.element.render.MinimapElementRenderer<E, RC> {
   @Deprecated
   protected final MinimapElementReader<E, RC> elementReader = this.getElementReader();
   @Deprecated
   protected final MinimapElementRenderProvider<E, RC> provider = this.getProvider();

   @Deprecated
   public MinimapElementRenderer(MinimapElementReader<E, RC> elementReader, MinimapElementRenderProvider<E, RC> provider, RC context) {
      super(elementReader, provider, context);
   }

   @Deprecated
   public MinimapElementRenderProvider<E, RC> getProvider() {
      return (MinimapElementRenderProvider<E, RC>)super.getProvider();
   }

   @Deprecated
   public MinimapElementReader<E, RC> getElementReader() {
      return (MinimapElementReader<E, RC>)super.getElementReader();
   }

   @Deprecated
   public abstract boolean renderElement(
      int var1,
      boolean var2,
      boolean var3,
      GuiGraphics var4,
      BufferSource var5,
      Font var6,
      RenderTarget var7,
      MinimapRendererHelper var8,
      Entity var9,
      Player var10,
      double var11,
      double var13,
      double var15,
      int var17,
      double var18,
      float var20,
      E var21,
      double var22,
      double var24,
      boolean var26,
      float var27
   );

   @Deprecated
   public abstract void preRender(
      int var1,
      Entity var2,
      Player var3,
      double var4,
      double var6,
      double var8,
      IXaeroMinimap var10,
      BufferSource var11,
      MultiTextureRenderTypeRendererProvider var12
   );

   @Deprecated
   public abstract void postRender(
      int var1,
      Entity var2,
      Player var3,
      double var4,
      double var6,
      double var8,
      IXaeroMinimap var10,
      BufferSource var11,
      MultiTextureRenderTypeRendererProvider var12
   );

   @Deprecated
   public abstract boolean shouldRender(int var1);

   @Deprecated
   protected MinimapElementRenderInfo getPreInfo(int location, Entity renderEntity, Player player, double renderX, double renderY, double renderZ) {
      return new MinimapElementRenderInfo(
         xaero.hud.minimap.element.render.MinimapElementRenderLocation.fromIndex(location),
         renderEntity,
         player,
         new Vec3(renderX, renderY, renderZ),
         false,
         1.0F,
         null,
         Minecraft.getInstance().level.dimensionType().coordinateScale(),
         Minecraft.getInstance().level.dimension()
      );
   }

   @Override
   public boolean renderElement(
      E element,
      boolean highlighted,
      boolean outOfBounds,
      double optionalDepth,
      float optionalScale,
      double partialX,
      double partialY,
      MinimapElementRenderInfo renderInfo,
      GuiGraphics guiGraphics,
      BufferSource vanillaBufferSource
   ) {
      return this.renderElement(
         renderInfo.location.getIndex(),
         highlighted,
         outOfBounds,
         guiGraphics,
         vanillaBufferSource,
         Minecraft.getInstance().font,
         renderInfo.framebuffer,
         HudMod.INSTANCE.getMinimap().getMinimapFBORenderer().getHelper(),
         renderInfo.renderEntity,
         renderInfo.player,
         renderInfo.renderPos.x,
         renderInfo.renderPos.y,
         renderInfo.renderPos.z,
         0,
         optionalDepth,
         optionalScale,
         element,
         partialX,
         partialY,
         renderInfo.cave,
         renderInfo.partialTicks
      );
   }

   @Override
   public void preRender(
      MinimapElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers
   ) {
      this.preRender(
         renderInfo.location.getIndex(),
         renderInfo.renderEntity,
         renderInfo.player,
         renderInfo.renderPos.x,
         renderInfo.renderPos.y,
         renderInfo.renderPos.z,
         HudMod.INSTANCE,
         vanillaBufferSource,
         multiTextureRenderTypeRenderers
      );
   }

   @Override
   public void postRender(
      MinimapElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers
   ) {
      this.postRender(
         renderInfo.location.getIndex(),
         renderInfo.renderEntity,
         renderInfo.player,
         renderInfo.renderPos.x,
         renderInfo.renderPos.y,
         renderInfo.renderPos.z,
         HudMod.INSTANCE,
         vanillaBufferSource,
         multiTextureRenderTypeRenderers
      );
   }

   @Override
   public boolean shouldRender(xaero.hud.minimap.element.render.MinimapElementRenderLocation location) {
      return this.shouldRender(location.getIndex());
   }
}
