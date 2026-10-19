package it.hurts.sskirillss.relics.client.tooltip;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.api.events.common.TooltipDisplayEvent;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.utils.data.AnimationData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RenderTooltipEvent.Color;

@EventBusSubscriber(modid = "relics", bus = Bus.GAME, value = Dist.CLIENT)
public class TooltipBorderHandler {
   @SubscribeEvent
   public static void onTooltipDisplay(TooltipDisplayEvent event) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null) {
         ItemStack stack = event.getStack();
         if (stack.getItem() instanceof IRelicItem relic) {
            TooltipData tooltip = relic.getStyleData().getTooltip().apply(player, stack);
            if (tooltip.isTextured()) {
               GuiGraphics graphics = event.getGraphics();
               PoseStack poseStack = graphics.pose();
               int width = event.getWidth();
               int height = event.getHeight();
               int x = event.getX();
               int y = event.getY();
               String id = tooltip.getIcon().isEmpty() ? BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() : tooltip.getIcon();
               ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/tooltip/frame/" + id + "_frame.png");
               RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
               RenderSystem.setShaderTexture(0, texture);
               Minecraft.getInstance().getTextureManager().getTexture(texture).bind();
               int texWidth = GlStateManager._getTexLevelParameter(3553, 0, 4096);
               int texHeight = GlStateManager._getTexLevelParameter(3553, 0, 4097);
               if (texHeight != 0 && texWidth != 0) {
                  int patternWidth = 160;
                  int patternHeight = 64;
                  int cornerWidth = 32;
                  int cornerHeight = 32;
                  int middleWidth = 96;
                  int middleHeight = cornerHeight;
                  poseStack.pushPose();
                  RenderSystem.enableBlend();
                  poseStack.translate(0.0, 0.0, 410.0);
                  int frame = (Integer)AnimationData.construct(texHeight, patternHeight, 2).getFrameByTime(player.tickCount).getKey();
                  int offset = patternHeight * frame;
                  graphics.blit(texture, x - cornerWidth / 2 - 3, y - cornerHeight / 2 - 3, 0.0F, offset, cornerWidth, cornerHeight, texWidth, texHeight);
                  graphics.blit(
                     texture,
                     x + width - cornerWidth / 2 + 3,
                     y - cornerHeight / 2 - 3,
                     patternWidth - cornerWidth,
                     offset,
                     cornerWidth,
                     cornerHeight,
                     texWidth,
                     texHeight
                  );
                  graphics.blit(
                     texture,
                     x - cornerWidth / 2 - 3,
                     y + height - cornerHeight / 2 + 3,
                     0.0F,
                     patternHeight - cornerHeight + offset,
                     cornerWidth,
                     cornerHeight,
                     texWidth,
                     texHeight
                  );
                  graphics.blit(
                     texture,
                     x + width - cornerWidth / 2 + 3,
                     y + height - cornerHeight / 2 + 3,
                     patternWidth - cornerWidth,
                     patternHeight - cornerHeight + offset,
                     cornerWidth,
                     cornerHeight,
                     texWidth,
                     texHeight
                  );
                  graphics.blit(
                     texture, x + (width - middleWidth) / 2, y - middleHeight + 1, cornerWidth, offset, middleWidth, middleHeight, texWidth, texHeight
                  );
                  graphics.blit(
                     texture, x + (width - middleWidth) / 2, y + height - 1, cornerWidth, middleHeight + offset, middleWidth, middleHeight, texWidth, texHeight
                  );
                  texture = ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/tooltip/frame/" + id + "_star.png");
                  RenderSystem.setShaderTexture(0, texture);
                  int xOff = 0;

                  for (int i = 1; i < relic.getRelicQuality(stack) + 1; i++) {
                     boolean isAliquot = i % 2 == 1;
                     float color = (float)(0.85F + Math.sin(player.tickCount * Math.ceil(i / 2.0F) * 0.075F) * 0.2F);
                     RenderSystem.blendFunc(770, 1);
                     RenderSystem.setShaderColor(color, color, color, 1.0F);
                     graphics.blit(texture, x + width / 2 - 14 + xOff, y - 10, isAliquot ? 0 : 3, 0.0F, isAliquot ? 3 : 2, 5, 5, 5);
                     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                     xOff += 3;
                  }

                  RenderSystem.disableBlend();
                  poseStack.popPose();
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onTooltipColorEvent(Color event) {
      ItemStack stack = event.getItemStack();
      if (stack.getItem() instanceof IRelicItem relic) {
         TooltipData tooltip = relic.getStyleData().getTooltip().apply(Minecraft.getInstance().player, stack);
         if (tooltip.getBorderTop() != -1) {
            event.setBorderStart(tooltip.getBorderTop());
         }

         if (tooltip.getBorderBottom() != -1) {
            event.setBorderEnd(tooltip.getBorderBottom());
         }

         if (tooltip.getBackgroundTop() != -1) {
            event.setBackgroundStart(tooltip.getBackgroundTop());
         }

         if (tooltip.getBackgroundBottom() != -1) {
            event.setBackgroundEnd(tooltip.getBackgroundBottom());
         }
      }
   }
}
