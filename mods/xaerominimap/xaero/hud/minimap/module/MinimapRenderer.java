package xaero.hud.minimap.module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import xaero.common.HudMod;
import xaero.common.effect.Effects;
import xaero.common.minimap.render.MinimapRendererHelper;
import xaero.common.misc.Misc;
import xaero.hud.render.module.IModuleRenderer;
import xaero.hud.render.module.ModuleRenderContext;
import xaero.lib.client.gui.IScreenBase;

public class MinimapRenderer implements IModuleRenderer<MinimapSession> {
   public void render(MinimapSession session, ModuleRenderContext c, GuiGraphics guiGraphics, float partialTicks) {
      Minecraft mc = Minecraft.getInstance();
      if (!Misc.hasEffect(mc.player, Effects.NO_MINIMAP)
         && !Misc.hasEffect(mc.player, Effects.NO_MINIMAP_HARMFUL)
         && !session.getProcessor().getNoMinimapMessageReceived()) {
         if ((
               !session.getHideMinimapUnderScreen()
                  || mc.screen == null
                  || mc.screen instanceof IScreenBase
                  || mc.screen instanceof ChatScreen
                  || mc.screen instanceof DeathScreen
            )
            && (!session.getHideMinimapUnderF3() || !mc.getDebugOverlay().showDebugScreen())) {
            MinimapRendererHelper.restoreDefaultShaderBlendState();
            session.getProcessor()
               .onRender(
                  guiGraphics,
                  c.x,
                  c.y,
                  c.screenWidth,
                  c.screenHeight,
                  c.screenScale,
                  session.getConfiguredWidth(),
                  c.w,
                  partialTicks,
                  HudMod.INSTANCE.getHudRenderer().getCustomVertexConsumers()
               );
            MinimapRendererHelper.restoreDefaultShaderBlendState();
         }
      }
   }
}
