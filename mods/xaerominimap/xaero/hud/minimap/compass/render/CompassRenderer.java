package xaero.hud.minimap.compass.render;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.network.chat.Component;
import xaero.common.HudMod;
import xaero.common.minimap.render.MinimapRendererHelper;
import xaero.common.misc.Misc;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.element.render.over.MinimapElementOverMapRendererHandler;
import xaero.hud.minimap.waypoint.WaypointColor;
import xaero.hud.render.util.RenderBufferUtil;
import xaero.lib.client.config.ClientConfigManager;

public class CompassRenderer {
   private final HudMod modMain;
   private final Minecraft mc;
   private double[] partialDest;

   public CompassRenderer(HudMod modMain, Minecraft mc) {
      this.modMain = modMain;
      this.mc = mc;
      this.partialDest = new double[2];
   }

   public void drawCompass(
      PoseStack matrixStack,
      int specW,
      int specH,
      double ps,
      double pc,
      double zoom,
      boolean circle,
      float minimapScale,
      boolean background,
      BufferSource textRenderTypeBuffer,
      VertexConsumer nameBgBuilder
   ) {
      ClientConfigManager configManager = this.modMain.getHudConfigs().getClientConfigManager();
      int compassLocationConfig = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.COMPASS_LOCATION);
      if (compassLocationConfig != 0) {
         int shadowColorConfig = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.COMPASS_SHADOW_COLOR);
         WaypointColor defaultColor = WaypointColor.fromIndex(shadowColorConfig);
         MinimapRendererHelper rendererHelper = this.modMain.getMinimap().getMinimapFBORenderer().getHelper();
         int northShadowColorConfig = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.COMPASS_NORTH_SHADOW_COLOR);
         int effectiveNorthShadowColorConfig = northShadowColorConfig == -1 ? shadowColorConfig : northShadowColorConfig;

         for (int i = 0; i < 4; i++) {
            double offX = (i & 1) * (i == 1 ? 10000 : -10000);
            double offY = (i + 1 & 1) * (i == 2 ? 10000 : -10000);
            matrixStack.pushPose();
            MinimapElementOverMapRendererHandler.translatePosition(matrixStack, specW, specH, specW, specH, ps, pc, offX, offY, zoom, circle, this.partialDest);
            matrixStack.translate(-1.0F, -1.0F, 0.0F);
            matrixStack.scale(minimapScale, minimapScale, 1.0F);
            Component initials = CardinalDirection.values()[i].getInitials();
            int halfW = this.mc.font.width(initials) / 2 - 1;
            WaypointColor effectiveColor = i == 0 ? WaypointColor.fromIndex(effectiveNorthShadowColorConfig) : defaultColor;
            if (background) {
               RenderSystem.enableBlend();
               RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ZERO, DestFactor.ONE);
               int addedFrame = halfW > 3 ? halfW - 3 : 0;
               RenderBufferUtil.addColoredRect(
                  matrixStack.last().pose(),
                  nameBgBuilder,
                  -4 - addedFrame,
                  -4 - addedFrame,
                  9 + 2 * addedFrame,
                  9 + 2 * addedFrame,
                  -1879048192 | effectiveColor.getHex() & 16777215
               );
               RenderSystem.defaultBlendFunc();
            }

            Misc.drawNormalText(matrixStack, initials, -halfW + 1, -2.0F, effectiveColor.getHex(), false, textRenderTypeBuffer);
            matrixStack.translate(0.0F, 0.0F, 1.0F);
            Misc.drawNormalText(matrixStack, initials, -halfW, -3.0F, -1, false, textRenderTypeBuffer);
            matrixStack.popPose();
         }

         matrixStack.translate(0.0F, 0.0F, 2.0F);
      }
   }
}
