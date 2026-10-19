package it.hurts.sskirillss.relics.client.screen.description.research.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.base.IHoverableWidget;
import it.hurts.sskirillss.relics.client.screen.base.ITickingWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractDescriptionWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.client.screen.description.relic.particles.ExperienceParticleData;
import it.hurts.sskirillss.relics.client.screen.description.research.AbilityResearchScreen;
import it.hurts.sskirillss.relics.client.screen.utils.ParticleStorage;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.RandomSource;

public class TipWidget extends AbstractDescriptionWidget implements IHoverableWidget, ITickingWidget {
   private final AbilityResearchScreen screen;

   public TipWidget(int x, int y, AbilityResearchScreen screen) {
      super(x, y, 12, 14);
      this.screen = screen;
   }

   public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && this.screen.stack.getItem() instanceof IRelicItem) {
         PoseStack poseStack = guiGraphics.pose();
         poseStack.pushPose();
         float color = (float)(1.05F + Math.sin((player.tickCount + pPartialTick) * 0.5F) * 0.1F);
         GUIRenderer.begin(DescriptionTextures.TIP_BACKGROUND, poseStack)
            .anchor(SpriteAnchor.TOP_LEFT)
            .color(color, color, color, 1.0F)
            .pos(this.getX() + 2, this.getY() + 1)
            .end();
         if (this.isHovered()) {
            GUIRenderer.begin(DescriptionTextures.TIP_OUTLINE, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.getX(), this.getY() - 1).end();
         }

         poseStack.popPose();
      }
   }

   @Override
   public void onTick() {
      if (this.isHovered() && this.screen.stack.getItem() instanceof IRelicItem && this.minecraft.player != null) {
         RandomSource random = this.minecraft.player.getRandom();
         if (this.minecraft.player.tickCount % 5 == 0) {
            ParticleStorage.addParticle(
               this.screen,
               new ExperienceParticleData(
                  new Color(255, 200 + random.nextInt(50), random.nextInt(50)),
                  this.getX() + 34 + random.nextInt(16),
                  this.getY() - 6 + random.nextInt(16),
                  1.0F + random.nextFloat() * 0.25F,
                  50 + random.nextInt(50)
               )
            );
         }
      }
   }

   @Override
   public void onHovered(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      if (this.screen.stack.getItem() instanceof IRelicItem relic) {
         PoseStack var13 = guiGraphics.pose();
         ArrayList tooltip = Lists.newArrayList();
         short maxWidth = 180;
         int renderWidth = 0;

         for (MutableComponent entry : Lists.newArrayList(new MutableComponent[]{Component.translatable("tooltip.relics.researching.research.tip")})) {
            int entryWidth = this.minecraft.font.width(entry) / 2;
            if (entryWidth > renderWidth) {
               renderWidth = Math.min(entryWidth + 2, maxWidth);
            }

            tooltip.addAll(this.minecraft.font.split(entry, maxWidth * 2));
         }

         var13.pushPose();
         var13.translate(0.0F, 0.0F, 100.0F);
         DescriptionUtils.drawTooltipBackground(guiGraphics, renderWidth, tooltip.size() * 5, mouseX - 9 - renderWidth / 2, mouseY);
         var13.scale(0.5F, 0.5F, 0.5F);
         int yOff = 0;

         for (FormattedCharSequence entry : tooltip) {
            guiGraphics.drawString(this.minecraft.font, entry, (mouseX - renderWidth / 2 + 1) * 2, (mouseY + yOff + 9) * 2, 6696723, false);
            yOff += 5;
         }

         var13.popPose();
      }
   }

   @Override
   public void playDownSound(SoundManager handler) {
   }
}
