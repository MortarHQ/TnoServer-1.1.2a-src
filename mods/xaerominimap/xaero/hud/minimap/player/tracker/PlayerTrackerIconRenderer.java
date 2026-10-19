package xaero.hud.minimap.player.tracker;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRenderer;
import xaero.hud.render.util.RenderBufferUtil;

public class PlayerTrackerIconRenderer {
   public void renderIcon(
      Minecraft mc, MultiTextureRenderTypeRenderer renderer, PoseStack matrixStack, Player player, ResourceLocation skinTextureLocation, float alpha
   ) {
      boolean upsideDown = player != null && LivingEntityRenderer.isEntityUpsideDown(player);
      int textureY = 8 + (!upsideDown ? 8 : 0);
      int textureH = 8 * (!upsideDown ? -1 : 1);
      AbstractTexture texture = mc.getTextureManager().getTexture(skinTextureLocation);
      if (texture != null) {
         int textureId = texture.getId();
         BufferBuilder bufferbuilder = renderer.begin(textureId);
         RenderBufferUtil.addTexturedColoredRect(
            matrixStack.last().pose(), bufferbuilder, -4.0F, -4.0F, 8, textureY, 8, 8, 8, textureH, 1.0F, 1.0F, 1.0F, alpha, 64.0F
         );
         if (player != null) {
            if (player.isModelPartShown(PlayerModelPart.HAT)) {
               textureY = 8 + (!upsideDown ? 8 : 0);
               textureH = 8 * (!upsideDown ? -1 : 1);
               RenderBufferUtil.addTexturedColoredRect(
                  matrixStack.last().pose(), bufferbuilder, -4.0F, -4.0F, 40, textureY, 8, 8, 8, textureH, 1.0F, 1.0F, 1.0F, alpha, 64.0F
               );
            }
         }
      }
   }
}
