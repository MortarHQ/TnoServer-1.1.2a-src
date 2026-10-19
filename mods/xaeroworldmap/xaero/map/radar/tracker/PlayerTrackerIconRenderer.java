package xaero.map.radar.tracker;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import xaero.lib.client.graphics.XaeroRenderType;
import xaero.lib.client.graphics.shader.LibShaders;
import xaero.map.graphics.MapRenderHelper;

public class PlayerTrackerIconRenderer {
   public void renderIcon(GuiGraphics guiGraphics, Player player, ResourceLocation skinTextureLocation) {
      PoseStack matrixStack = guiGraphics.pose();
      boolean upsideDown = player != null && LivingEntityRenderer.isEntityUpsideDown(player);
      int textureY = 8 + (upsideDown ? 8 : 0);
      int textureH = 8 * (upsideDown ? -1 : 1);
      RenderSystem.setShaderTexture(0, skinTextureLocation);
      RenderSystem.setShader(() -> LibShaders.POSITION_COLOR_TEX);
      BufferBuilder bufferbuilder = Tesselator.getInstance().begin(Mode.QUADS, XaeroRenderType.POSITION_COLOR_TEX);
      MapRenderHelper.blitIntoExistingBuffer(
         matrixStack.last().pose(), bufferbuilder, -4.0F, -4.0F, 8, textureY, 8, 8, 8, textureH, 1.0F, 1.0F, 1.0F, 1.0F, 64, 64
      );
      if (player != null && player.isModelPartShown(PlayerModelPart.HAT)) {
         textureY = 8 + (upsideDown ? 8 : 0);
         textureH = 8 * (upsideDown ? -1 : 1);
         MapRenderHelper.blitIntoExistingBuffer(
            matrixStack.last().pose(), bufferbuilder, -4.0F, -4.0F, 40, textureY, 8, 8, 8, textureH, 1.0F, 1.0F, 1.0F, 1.0F, 64, 64
         );
      }

      BufferUploader.drawWithShader(bufferbuilder.build());
   }
}
