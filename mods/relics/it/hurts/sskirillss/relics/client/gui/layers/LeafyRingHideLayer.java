package it.hurts.sskirillss.relics.client.gui.layers;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.init.ItemRegistry;
import it.hurts.sskirillss.relics.items.relics.ring.LeafyRingItem;
import it.hurts.sskirillss.relics.utils.Easing;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import it.hurts.sskirillss.relics.utils.data.SpriteMirror;
import java.awt.Color;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw.Layer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LeafyRingHideLayer implements Layer {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("relics", "textures/hud/leafy_ring_hide.png");
   private static final ResourceLocation VIGNETTE = ResourceLocation.withDefaultNamespace("textures/misc/vignette.png");

   public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
      Minecraft MC = Minecraft.getInstance();
      LocalPlayer player = MC.player;
      if (player != null) {
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.LEAFY_RING.get());
         if (stack.getItem() instanceof LeafyRingItem relic) {
            int progress = relic.getCurrentProgress(stack);
            if (progress > 0) {
               int maxProgress = relic.getMaxProgress();
               float partialTick = deltaTracker.getGameTimeDeltaPartialTick(false);
               float progressRatio = Easing.easeOutQuad(Math.min(1.0F, (progress + partialTick * (relic.isHiding(stack) ? 1 : -1)) / maxProgress));
               PoseStack poseStack = guiGraphics.pose();
               poseStack.pushPose();
               Window window = MC.getWindow();
               int width = window.getGuiScaledWidth();
               int height = window.getGuiScaledHeight();
               int texWidth = 89;
               int texHeight = 62;
               float alpha = progressRatio * 0.75F;
               float offsetX = texWidth * (1.0F - progressRatio);
               float offsetY = texHeight * (1.0F - progressRatio);
               Color color = new Color(BiomeColors.getAverageGrassColor(player.level(), player.blockPosition())).brighter();
               RenderSystem.enableBlend();
               GUIRenderer.begin(VIGNETTE, poseStack)
                  .anchor(SpriteAnchor.TOP_LEFT)
                  .patternSize(width, height)
                  .texSize(width, height)
                  .color(color)
                  .alpha(alpha * 0.25F)
                  .end();
               GUIRenderer.begin(TEXTURE, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(-offsetX, -offsetY).color(color).alpha(alpha).scale(2.0F).end();
               GUIRenderer.begin(TEXTURE, poseStack)
                  .pos(width + offsetX, -offsetY)
                  .mirror(SpriteMirror.HORIZONTAL)
                  .anchor(SpriteAnchor.TOP_RIGHT)
                  .color(color)
                  .alpha(alpha)
                  .scale(2.0F)
                  .end();
               GUIRenderer.begin(TEXTURE, poseStack)
                  .pos(-offsetX, height + offsetY)
                  .anchor(SpriteAnchor.BOTTOM_LEFT)
                  .mirror(SpriteMirror.VERTICAL)
                  .color(color)
                  .alpha(alpha)
                  .scale(2.0F)
                  .end();
               GUIRenderer.begin(TEXTURE, poseStack)
                  .pos(width + offsetX, height + offsetY)
                  .mirror(SpriteMirror.HORIZONTAL, SpriteMirror.VERTICAL)
                  .anchor(SpriteAnchor.BOTTOM_RIGHT)
                  .color(color)
                  .alpha(alpha)
                  .scale(2.0F)
                  .end();
               RenderSystem.disableBlend();
               poseStack.popPose();
            }
         }
      }
   }
}
