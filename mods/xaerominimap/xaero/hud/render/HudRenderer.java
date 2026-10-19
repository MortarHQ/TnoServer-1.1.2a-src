package xaero.hud.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.lwjgl.opengl.GL11;
import xaero.common.graphics.CustomVertexConsumers;
import xaero.hud.Hud;
import xaero.hud.module.HudModule;
import xaero.hud.module.ModuleSession;
import xaero.hud.preset.HudPreset;
import xaero.hud.pushbox.PushboxHandler;
import xaero.hud.render.module.IModuleRenderer;
import xaero.hud.render.module.ModuleRenderContext;
import xaero.lib.client.graphics.shader.LibShaders;

public final class HudRenderer {
   private final PushboxHandler pushboxHandler;
   private final CustomVertexConsumers customVertexConsumers;

   public HudRenderer(PushboxHandler pushboxHandler) {
      this.pushboxHandler = pushboxHandler;
      this.customVertexConsumers = new CustomVertexConsumers();
   }

   public void render(Hud hud, GuiGraphics guiGraphics, float partialTicks) {
      guiGraphics.flush();

      while (GL11.glGetError() != 0) {
      }

      GlStateManager._clearColor(0.0F, 0.0F, 0.0F, 0.0F);
      GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      LibShaders.ensureShaders();
      Minecraft mc = Minecraft.getInstance();
      int screenWidth = mc.getWindow().getGuiScaledWidth();
      int screenHeight = mc.getWindow().getGuiScaledHeight();
      double screenScale = mc.getWindow().getGuiScale();
      ModuleRenderContext renderContext = new ModuleRenderContext(screenWidth, screenHeight, screenScale);
      this.pushboxHandler.updateAll(hud.getPushboxManager());
      if (mc.screen == null) {
         for (HudPreset preset : hud.getPresetManager().getPresets()) {
            preset.cancel();
         }
      }

      for (HudModule<?> module : hud.getModuleManager().getModules()) {
         this.renderModule(module, hud, renderContext, guiGraphics, partialTicks);
      }

      this.pushboxHandler.postUpdateAll(hud.getPushboxManager());
      RenderSystem.enableDepthTest();
   }

   private <MS extends ModuleSession<MS>> void renderModule(HudModule<MS> module, Hud hud, ModuleRenderContext c, GuiGraphics guiGraphics, float partialTicks) {
      MS session = module.getCurrentSession();
      session.prePotentialRender();
      if (session.isActive()) {
         if (module.getUsedTransform().fromOldSystem) {
            hud.getOldSystemCompatibility().convertTransform(module.getUsedTransform(), session, c);
         }

         IModuleRenderer<MS> renderer = module.getRenderer();
         PushboxHandler.State currentPushState = module.getPushState();
         currentPushState.resetForModule(session, c.screenWidth, c.screenHeight, c.screenScale);
         this.pushboxHandler.applyScreenEdges(currentPushState, c.screenWidth, c.screenHeight, c.screenScale);
         this.pushboxHandler.applyPushboxes(hud.getPushboxManager(), currentPushState, c.screenWidth, c.screenHeight, c.screenScale);
         c.x = currentPushState.x;
         c.y = currentPushState.y;
         c.w = session.getWidth(c.screenScale);
         c.h = session.getHeight(c.screenScale);
         c.flippedVertically = session.shouldFlipVertically(c.screenHeight, c.screenScale);
         c.flippedHorizontally = session.shouldFlipHorizontally(c.screenWidth, c.screenScale);
         renderer.render(session, c, guiGraphics, partialTicks);
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(770, 771);
      }
   }

   public PushboxHandler getPushboxHandler() {
      return this.pushboxHandler;
   }

   public CustomVertexConsumers getCustomVertexConsumers() {
      return this.customVertexConsumers;
   }
}
