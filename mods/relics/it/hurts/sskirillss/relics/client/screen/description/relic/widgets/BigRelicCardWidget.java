package it.hurts.sskirillss.relics.client.screen.description.relic.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.hurts.sskirillss.relics.client.screen.base.IHoverableWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractDescriptionWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.client.screen.description.relic.RelicDescriptionScreen;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

public class BigRelicCardWidget extends AbstractDescriptionWidget implements IHoverableWidget {
   private RelicDescriptionScreen screen;

   public BigRelicCardWidget(int x, int y, RelicDescriptionScreen screen) {
      super(x, y, 48, 74);
      this.screen = screen;
   }

   public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      ItemStack stack = this.screen.getStack();
      if (stack.getItem() instanceof IRelicItem relic) {
         LocalPlayer var13 = this.minecraft.player;
         PoseStack poseStack = guiGraphics.pose();
         poseStack.pushPose();
         GUIRenderer.begin(DescriptionTextures.BIG_CARD_FRAME_UNLOCKED_ACTIVE, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.getX(), this.getY()).end();
         byte xOff = 0;
         if (relic.hasUnlockedUpgradeableAbility(stack)) {
            for (int i = 0; i < 5; i++) {
               GUIRenderer.begin(DescriptionTextures.BIG_STAR_HOLE, poseStack)
                  .anchor(SpriteAnchor.TOP_LEFT)
                  .pos(this.getX() + xOff + 4, this.getY() + 63)
                  .end();
               xOff += 8;
            }

            xOff = 0;
            int quality = relic.getRelicQuality(stack);
            boolean isAliquot = quality % 2 == 1;

            for (int i = 0; i < Math.floor(quality / 2.0); i++) {
               GUIRenderer.begin(DescriptionTextures.BIG_STAR_ACTIVE, poseStack)
                  .anchor(SpriteAnchor.TOP_LEFT)
                  .pos(this.getX() + xOff + 4, this.getY() + 63)
                  .end();
               xOff += 8;
            }

            if (isAliquot) {
               GUIRenderer.begin(DescriptionTextures.BIG_STAR_ACTIVE, poseStack)
                  .anchor(SpriteAnchor.TOP_LEFT)
                  .pos(this.getX() + xOff + 4, this.getY() + 63)
                  .patternSize(4, 7)
                  .texSize(8, 7)
                  .end();
            }
         }

         poseStack.pushPose();
         float scale = 1.75F;
         poseStack.translate(this.getX() + 10 + 8.0F * scale, this.getY() + 21 + Math.sin((var13.tickCount + pPartialTick) * 0.1F) * 2.0 + 8.0F * scale, 0.0);
         poseStack.mulPose(Axis.ZP.rotationDegrees((float)Math.cos((var13.tickCount + pPartialTick) * 0.05F) * 5.0F));
         poseStack.mulPose(Axis.YP.rotationDegrees((float)Math.cos((var13.tickCount + pPartialTick) * 0.075F) * 25.0F));
         poseStack.translate(-8.0F * scale, -8.0F * scale, -150.0F * scale);
         poseStack.scale(scale, scale, scale);
         guiGraphics.renderItem(stack, 0, 0);
         poseStack.popPose();
         poseStack.pushPose();
         poseStack.scale(0.75F, 0.75F, 1.0F);
         MutableComponent levelComponent = Component.literal(String.valueOf(relic.getRelicLevel(stack))).withStyle(ChatFormatting.BOLD);
         guiGraphics.drawString(
            this.minecraft.font,
            levelComponent,
            (int)((this.getX() + 25.5F) * 1.33F - this.minecraft.font.width(levelComponent) / 2.0F),
            (int)((this.getY() + 4) * 1.33F),
            16769656,
            true
         );
         poseStack.popPose();
         if (this.isHovered() && relic.hasUnlockedUpgradeableAbility(stack)) {
            GUIRenderer.begin(DescriptionTextures.BIG_CARD_FRAME_OUTLINE, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.getX() - 1, this.getY() - 1).end();
         }

         poseStack.popPose();
      }
   }

   @Override
   public void onHovered(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      ItemStack stack = this.screen.getStack();
      if (stack.getItem() instanceof IRelicItem relic && relic.hasUnlockedUpgradeableAbility(stack)) {
         PoseStack poseStack = guiGraphics.pose();
         List<FormattedCharSequence> tooltip = Lists.newArrayList();
         int maxWidth = 150;
         int renderWidth = 0;
         List<MutableComponent> entries = Lists.newArrayList(
            new MutableComponent[]{
               Component.literal("")
                  .append(
                     Component.translatable("tooltip.relics.researching.relic.info.level").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.UNDERLINE)
                  )
                  .append(" " + relic.getRelicLevel(stack) + "/" + relic.getLevelingData().getMaxLevel()),
               Component.literal("")
                  .append(
                     Component.translatable("tooltip.relics.researching.relic.info.quality").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.UNDERLINE)
                  )
                  .append(" " + MathUtils.round(relic.getRelicQuality(stack) / 2.0F, 1) + "/" + relic.getMaxQuality() / 2),
               Component.literal(" ")
            }
         );
         if (Screen.hasShiftDown()) {
            entries.add(Component.translatable("tooltip.relics.researching.relic.info.extra_info").withStyle(ChatFormatting.ITALIC));
         } else {
            entries.add(Component.translatable("tooltip.relics.researching.general.extra_info"));
         }

         for (MutableComponent entry : entries) {
            int entryWidth = this.minecraft.font.width(entry) / 2;
            if (entryWidth > renderWidth) {
               renderWidth = Math.min(entryWidth + 2, maxWidth);
            }

            tooltip.addAll(this.minecraft.font.split(entry, maxWidth * 2));
         }

         poseStack.pushPose();
         poseStack.translate(0.0F, 0.0F, 400.0F);
         DescriptionUtils.drawTooltipBackground(guiGraphics, renderWidth, tooltip.size() * 5, mouseX - 9 - renderWidth / 2, mouseY);
         poseStack.scale(0.5F, 0.5F, 0.5F);
         int yOff = 0;

         for (FormattedCharSequence entry : tooltip) {
            guiGraphics.drawString(this.minecraft.font, entry, (mouseX - renderWidth / 2 + 1) * 2, (mouseY + yOff + 9) * 2, 6696723, false);
            yOff += 5;
         }

         poseStack.popPose();
      }
   }

   @Override
   public void playDownSound(SoundManager handler) {
   }
}
