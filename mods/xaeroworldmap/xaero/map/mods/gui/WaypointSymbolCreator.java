package xaero.map.mods.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import xaero.map.exception.OpenGLException;
import xaero.map.graphics.ImprovedFramebuffer;
import xaero.map.icon.XaeroIcon;
import xaero.map.icon.XaeroIconAtlas;
import xaero.map.icon.XaeroIconAtlasManager;
import xaero.map.misc.Misc;

public class WaypointSymbolCreator {
   private static final int PREFERRED_ATLAS_WIDTH = 1024;
   private static final int ICON_WIDTH = 64;
   public static final ResourceLocation minimapTextures = ResourceLocation.fromNamespaceAndPath("xaerobetterpvp", "gui/guis.png");
   public static final int white = -1;
   private Minecraft mc = Minecraft.getInstance();
   private XaeroIcon deathSymbolTexture;
   private final Map<String, XaeroIcon> charSymbols = new HashMap<>();
   private XaeroIconAtlasManager iconManager;
   private ImprovedFramebuffer atlasRenderFramebuffer;
   private XaeroIconAtlas lastAtlas;

   public XaeroIcon getDeathSymbolTexture(GuiGraphics guiGraphics) {
      if (this.deathSymbolTexture == null) {
         this.createDeathSymbolTexture(guiGraphics);
      }

      return this.deathSymbolTexture;
   }

   private void createDeathSymbolTexture(GuiGraphics guiGraphics) {
      this.deathSymbolTexture = this.createCharSymbol(guiGraphics, true, null);
   }

   public XaeroIcon getSymbolTexture(GuiGraphics guiGraphics, String c) {
      XaeroIcon icon;
      synchronized (this.charSymbols) {
         icon = this.charSymbols.get(c);
      }

      if (icon == null) {
         icon = this.createCharSymbol(guiGraphics, false, c);
      }

      return icon;
   }

   private XaeroIcon createCharSymbol(GuiGraphics guiGraphics, boolean death, String c) {
      if (this.iconManager == null) {
         OpenGLException.checkGLError();
         int maxTextureSize = GlStateManager._getInteger(3379);
         OpenGLException.checkGLError();
         int atlasTextureSize = Math.min(maxTextureSize, 1024) / 64 * 64;
         this.atlasRenderFramebuffer = new ImprovedFramebuffer(atlasTextureSize, atlasTextureSize, false);
         OpenGLException.checkGLError();
         GlStateManager._deleteTexture(this.atlasRenderFramebuffer.getFramebufferTexture());
         OpenGLException.checkGLError();
         this.atlasRenderFramebuffer.setFramebufferTexture(0);
         this.iconManager = new XaeroIconAtlasManager(64, atlasTextureSize, new ArrayList<>());
      }

      XaeroIconAtlas atlas = this.iconManager.getCurrentAtlas();
      XaeroIcon icon = atlas.createIcon();
      this.atlasRenderFramebuffer.bindAsMainTarget(false);
      GlStateManager._viewport(icon.getOffsetX(), icon.getOffsetY(), 64, 64);
      this.atlasRenderFramebuffer.setFramebufferTexture(atlas.getTextureId());
      this.atlasRenderFramebuffer.checkStatus();
      if (this.lastAtlas != atlas) {
         RenderSystem.clearColor(0.0F, 0.0F, 0.0F, 0.0F);
         RenderSystem.clear(16384, Minecraft.ON_OSX);
         this.lastAtlas = atlas;
      }

      Matrix4f ortho = new Matrix4f().setOrtho(0.0F, 64.0F, 64.0F, 0.0F, -1.0F, 1000.0F);
      RenderSystem.setProjectionMatrix(ortho, VertexSorting.ORTHOGRAPHIC_Z);
      Matrix4fStack shaderMatrixStack = RenderSystem.getModelViewStack();
      shaderMatrixStack.pushMatrix();
      shaderMatrixStack.identity();
      RenderSystem.applyModelViewMatrix();
      PoseStack matrixStack = guiGraphics.pose();
      matrixStack.pushPose();
      matrixStack.setIdentity();
      matrixStack.translate(2.0F, 2.0F, 0.0F);
      if (!death) {
         matrixStack.scale(3.0F, 3.0F, 1.0F);
         guiGraphics.drawString(this.mc.font, c, 0, 0, -1);
      } else {
         matrixStack.scale(3.0F, 3.0F, 1.0F);
         AbstractTexture texture = this.mc.getTextureManager().getTexture(minimapTextures);
         texture.setFilter(false, false);
         RenderSystem.setShaderColor(0.2431F, 0.2431F, 0.2431F, 1.0F);
         guiGraphics.blit(minimapTextures, 1, 1, 0, 0.0F, 78.0F, 9, 9, 256, 256);
         RenderSystem.setShaderColor(0.9882F, 0.9882F, 0.9882F, 1.0F);
         guiGraphics.blit(minimapTextures, 0, 0, 0, 0.0F, 78.0F, 9, 9, 256, 256);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      }

      matrixStack.popPose();
      Misc.minecraftOrtho(this.mc, false);
      shaderMatrixStack.popMatrix();
      RenderSystem.applyModelViewMatrix();
      this.atlasRenderFramebuffer.unbindWrite();
      this.atlasRenderFramebuffer.bindDefaultFramebuffer(this.mc);
      GlStateManager._viewport(0, 0, this.mc.getWindow().getWidth(), this.mc.getWindow().getHeight());
      if (death) {
         this.deathSymbolTexture = icon;
      } else {
         synchronized (this.charSymbols) {
            this.charSymbols.put(c, icon);
         }
      }

      return icon;
   }

   public void resetChars() {
      synchronized (this.charSymbols) {
         this.charSymbols.clear();
      }

      this.lastAtlas = null;
      this.deathSymbolTexture = null;
      if (this.iconManager != null) {
         this.iconManager.clearAtlases();
         this.atlasRenderFramebuffer.setFramebufferTexture(0);
      }
   }
}
