package it.hurts.sskirillss.relics.client.screen.description.relic.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.base.IHoverableWidget;
import it.hurts.sskirillss.relics.client.screen.base.IRelicScreenProvider;
import it.hurts.sskirillss.relics.client.screen.base.ITickingWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractDescriptionWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.client.screen.description.relic.particles.ExperienceParticleData;
import it.hurts.sskirillss.relics.client.screen.utils.ParticleStorage;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.RandomSource;

public class RelicExperienceWidget extends AbstractDescriptionWidget implements IHoverableWidget, ITickingWidget {
   private static final int FILLER_WIDTH = 125;
   private final IRelicScreenProvider screen;

   public RelicExperienceWidget(int x, int y, IRelicScreenProvider screen) {
      super(x, y, 139, 15);
      this.screen = screen;
   }

   public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && this.screen.getStack().getItem() instanceof IRelicItem relic) {
         PoseStack poseStack = guiGraphics.pose();
         poseStack.pushPose();
         float color = (float)(1.025F + Math.sin(player.tickCount * 0.5F) * 0.05F);
         GUIRenderer.begin(DescriptionTextures.RELIC_EXPERIENCE_BACKGROUND, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.getX(), this.getY() - 10).end();
         RenderSystem.enableBlend();
         GUIRenderer.begin(DescriptionTextures.RELIC_EXPERIENCE_FILLER, poseStack)
            .patternSize(this.calculateFillerWidth(relic), 11)
            .anchor(SpriteAnchor.TOP_LEFT)
            .pos(this.getX() + 3, this.getY() + 2)
            .color(color, color, color, 1.0F)
            .texSize(125, 11)
            .end();
         RenderSystem.disableBlend();
         if (this.isHovered()) {
            GUIRenderer.begin(DescriptionTextures.RELIC_EXPERIENCE_OUTLINE, poseStack)
               .anchor(SpriteAnchor.TOP_LEFT)
               .pos(this.getX() - 1, this.getY() - 6)
               .end();
         }

         poseStack.scale(0.5F, 0.5F, 0.5F);
         MutableComponent percentage = Component.literal(
               relic.isRelicMaxLevel(this.screen.getStack()) ? "MAX" : MathUtils.round(this.calculateFillerPercentage(relic), 1) + "%"
            )
            .withStyle(ChatFormatting.BOLD);
         guiGraphics.drawString(
            this.minecraft.font, percentage, (this.getX() + 67) * 2 - this.minecraft.font.width(percentage) / 2, (this.getY() + 6) * 2, 6696723, false
         );
         poseStack.popPose();
      }
   }

   @Override
   public void onTick() {
      if (this.screen.getStack().getItem() instanceof IRelicItem relic && this.minecraft.player != null) {
         RandomSource random = this.minecraft.player.getRandom();
         int fillerWidth = this.calculateFillerWidth(relic);
         if (this.minecraft.player.tickCount % 5 == 0) {
            for (float i = 0.0F; i < fillerWidth / 40.0F; i++) {
               ParticleStorage.addParticle(
                  (Screen)this.screen,
                  new ExperienceParticleData(
                     new Color(200, 255, 0),
                     this.getX() + 5 + random.nextInt(fillerWidth),
                     this.getY() + random.nextInt(2),
                     1.0F + random.nextFloat() * 0.25F,
                     50 + random.nextInt(50)
                  )
               );
            }
         }
      }
   }

   @Override
   public void onHovered(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      if (this.screen.getStack().getItem() instanceof IRelicItem relic) {
         PoseStack var14 = guiGraphics.pose();
         ArrayList tooltip = Lists.newArrayList();
         short maxWidth = 150;
         int renderWidth = 0;
         int level = relic.getRelicLevel(this.screen.getStack());
         List<MutableComponent> entries = Lists.newArrayList(
            new MutableComponent[]{
               Component.literal("")
                  .append(
                     Component.translatable("tooltip.relics.researching.relic.experience.title")
                        .withStyle(ChatFormatting.BOLD)
                        .withStyle(ChatFormatting.UNDERLINE)
                  )
                  .append(
                     " "
                        + (
                           relic.isRelicMaxLevel(this.screen.getStack())
                              ? "MAX"
                              : relic.getRelicExperience(this.screen.getStack()) + "/" + relic.getTotalRelicExperienceBetweenLevels(level, level + 1)
                        )
                  ),
               Component.literal(" ")
            }
         );
         if (Screen.hasShiftDown()) {
            entries.add(Component.translatable("tooltip.relics.researching.relic.experience.extra_info").withStyle(ChatFormatting.ITALIC));
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

         var14.pushPose();
         var14.translate(0.0F, 0.0F, 100.0F);
         DescriptionUtils.drawTooltipBackground(guiGraphics, renderWidth, tooltip.size() * 5, mouseX - 9 - renderWidth / 2, mouseY);
         var14.scale(0.5F, 0.5F, 0.5F);
         int yOff = 0;

         for (FormattedCharSequence entry : tooltip) {
            guiGraphics.drawString(this.minecraft.font, entry, (mouseX - renderWidth / 2 + 1) * 2, (mouseY + yOff + 9) * 2, 6696723, false);
            yOff += 5;
         }

         var14.popPose();
      }
   }

   @Override
   public void playDownSound(SoundManager handler) {
   }

   private float calculateFillerPercentage(IRelicItem relic) {
      int level = relic.getRelicLevel(this.screen.getStack());
      return relic.getRelicExperience(this.screen.getStack()) / (relic.getTotalRelicExperienceBetweenLevels(level, level + 1) / 100.0F);
   }

   private int calculateFillerWidth(IRelicItem relic) {
      return relic.isRelicMaxLevel(this.screen.getStack()) ? 125 : (int)Math.ceil(this.calculateFillerPercentage(relic) / 100.0F * 125.0F);
   }
}
