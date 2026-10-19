package xaero.map.radar.tracker;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import xaero.map.graphics.ImprovedFramebuffer;
import xaero.map.icon.XaeroIcon;
import xaero.map.icon.XaeroIconAtlas;
import xaero.map.misc.Misc;

public class TrackedPlayerIconPrerenderer {
   private ImprovedFramebuffer renderFramebuffer;
   private XaeroIconAtlas lastAtlas;
   private final PlayerTrackerIconRenderer renderer = new PlayerTrackerIconRenderer();

   public void prerender(
      GuiGraphics guiGraphics, XaeroIcon icon, Player player, int iconWidth, ResourceLocation skinTextureLocation, PlayerTrackerMapElement<?> mapElement
   ) {
      if (this.renderFramebuffer == null) {
         this.renderFramebuffer = new ImprovedFramebuffer(icon.getTextureAtlas().getWidth(), icon.getTextureAtlas().getWidth(), false);
         GlStateManager._deleteTexture(this.renderFramebuffer.getColorTextureId());
         this.renderFramebuffer.setFramebufferTexture(0);
      }

      this.renderFramebuffer.bindAsMainTarget(false);
      GlStateManager._viewport(icon.getOffsetX(), icon.getOffsetY(), iconWidth, iconWidth);
      this.renderFramebuffer.setFramebufferTexture(icon.getTextureAtlas().getTextureId());
      this.renderFramebuffer.checkStatus();
      if (this.lastAtlas != icon.getTextureAtlas()) {
         RenderSystem.clearColor(0.0F, 0.0F, 0.0F, 0.0F);
         RenderSystem.clear(16384, Minecraft.ON_OSX);
         this.lastAtlas = icon.getTextureAtlas();
      }

      Matrix4f ortho = new Matrix4f().setOrtho(0.0F, iconWidth, iconWidth, 0.0F, -1.0F, 1000.0F);
      RenderSystem.setProjectionMatrix(ortho, VertexSorting.ORTHOGRAPHIC_Z);
      Matrix4fStack shaderMatrixStack = RenderSystem.getModelViewStack();
      shaderMatrixStack.pushMatrix();
      shaderMatrixStack.identity();
      RenderSystem.applyModelViewMatrix();
      PoseStack matrixStack = guiGraphics.pose();
      matrixStack.pushPose();
      matrixStack.setIdentity();
      matrixStack.translate(iconWidth / 2, iconWidth / 2, 0.0F);
      matrixStack.scale(3.0F, 3.0F, 1.0F);
      guiGraphics.fill(-5, -5, 5, 5, -1);
      this.renderer.renderIcon(guiGraphics, player, skinTextureLocation);
      matrixStack.popPose();
      Minecraft mc = Minecraft.getInstance();
      Misc.minecraftOrtho(mc, false);
      shaderMatrixStack.popMatrix();
      RenderSystem.applyModelViewMatrix();
      this.renderFramebuffer.unbindWrite();
      this.renderFramebuffer.bindDefaultFramebuffer(mc);
      GlStateManager._viewport(0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight());
   }
}
