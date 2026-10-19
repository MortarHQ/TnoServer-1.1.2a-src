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
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.research.PacketResearchHint;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.data.AnimationData;
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

public class HintWidget extends AbstractDescriptionWidget implements IHoverableWidget, ITickingWidget {
   private final AbilityResearchScreen screen;

   public HintWidget(int x, int y, AbilityResearchScreen screen) {
      super(x, y, 87, 22);
      this.screen = screen;
   }

   @Override
   public void onPress() {
      if (this.screen.stack.getItem() instanceof IRelicItem relic && !relic.isAbilityResearched(this.screen.stack, this.screen.ability)) {
         int links = relic.getResearchData(this.screen.ability).getLinks().size();
         int requiredExperience = relic.getResearchHintPlayerExperienceCost(this.screen.ability) * (Screen.hasShiftDown() ? links : 1);
         long experience = EntityUtils.getPlayerTotalExperience(this.minecraft.player);
         if (experience >= requiredExperience) {
            NetworkHandler.sendToServer(new PacketResearchHint(this.screen.container, this.screen.slot, this.screen.ability, Screen.hasShiftDown() ? links : 1));
         }
      }
   }

   public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && this.screen.stack.getItem() instanceof IRelicItem relic) {
         PoseStack poseStack = guiGraphics.pose();
         poseStack.pushPose();
         GUIRenderer.begin(DescriptionTextures.HINT_BACKGROUND, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.getX(), this.getY() - 10).end();
         if (relic.isAbilityResearched(this.screen.stack, this.screen.ability)) {
            GUIRenderer.begin(DescriptionTextures.BULB_BROKEN, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.getX() + 34, this.getY() - 3).end();
         } else if (this.isHovered()) {
            float color = (float)(1.025F + Math.sin(player.tickCount + pPartialTick) * 0.05F);
            if (Screen.hasShiftDown()) {
               GUIRenderer.begin(DescriptionTextures.BULB_BURNING, poseStack)
                  .anchor(SpriteAnchor.TOP_LEFT)
                  .pos(this.getX() + 34, this.getY() - 17)
                  .patternSize(16, 34)
                  .color(color, color, color, 1.0F)
                  .animation(AnimationData.builder().frame(0, 2).frame(1, 2).frame(2, 2).frame(3, 2).frame(4, 2).frame(5, 2).frame(6, 2).frame(7, 2))
                  .end();
            } else {
               GUIRenderer.begin(DescriptionTextures.BULB_GLOWING, poseStack)
                  .anchor(SpriteAnchor.TOP_LEFT)
                  .pos(this.getX() + 34, this.getY() - 3)
                  .color(color, color, color, 1.0F)
                  .end();
            }
         } else {
            GUIRenderer.begin(DescriptionTextures.BULB, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.getX() + 34, this.getY() - 3).end();
         }

         if (this.isHovered()) {
            GUIRenderer.begin(DescriptionTextures.HINT_OUTLINE, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.getX() - 1, this.getY() - 6).end();
         }

         poseStack.popPose();
      }
   }

   @Override
   public void onTick() {
      if (this.isHovered() && this.screen.stack.getItem() instanceof IRelicItem relic && !relic.isAbilityResearched(this.screen.stack, this.screen.ability)) {
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
         PoseStack var19 = guiGraphics.pose();
         ArrayList tooltip = Lists.newArrayList();
         short maxWidth = 150;
         int renderWidth = 0;
         int requiredExperience = relic.getResearchHintPlayerExperienceCost(this.screen.ability)
            * (Screen.hasShiftDown() ? relic.getResearchData(this.screen.ability).getLinks().size() : 1);
         long experience = EntityUtils.getPlayerTotalExperience(this.minecraft.player);
         MutableComponent negativeStatus = Component.translatable("tooltip.relics.relic.status.negative");
         MutableComponent positiveStatus = Component.translatable("tooltip.relics.relic.status.positive");
         List<MutableComponent> entries = Lists.newArrayList(
            new MutableComponent[]{
               Component.translatable("tooltip.relics.researching.research.hint.description")
                  .withStyle(ChatFormatting.BOLD)
                  .withStyle(ChatFormatting.UNDERLINE),
               Component.literal(" ")
            }
         );
         boolean hasExperience = requiredExperience <= experience;
         if (relic.isAbilityResearched(this.screen.stack, this.screen.ability)) {
            entries.add(Component.translatable("tooltip.relics.researching.research.hint.locked"));
         } else {
            entries.add(
               Component.translatable(
                  "tooltip.relics.researching.research.hint.cost",
                  new Object[]{
                     requiredExperience,
                     hasExperience
                        ? EntityUtils.calculateExperienceLevelLoss(this.minecraft.player, requiredExperience)
                        : EntityUtils.getLevelFromTotalExperience(requiredExperience),
                     hasExperience ? positiveStatus : negativeStatus
                  }
               )
            );
            entries.add(Component.literal(" "));
            entries.add(Component.literal("▶ ").append(Component.translatable("tooltip.relics.researching.research.hint.quick")));
         }

         for (MutableComponent entry : entries) {
            int entryWidth = this.minecraft.font.width(entry) / 2;
            if (entryWidth > renderWidth) {
               renderWidth = Math.min(entryWidth + 2, maxWidth);
            }

            tooltip.addAll(this.minecraft.font.split(entry, maxWidth * 2));
         }

         var19.pushPose();
         var19.translate(0.0F, 0.0F, 100.0F);
         DescriptionUtils.drawTooltipBackground(guiGraphics, renderWidth, tooltip.size() * 5, mouseX - 9 - renderWidth / 2, mouseY);
         var19.scale(0.5F, 0.5F, 0.5F);
         int yOff = 0;

         for (FormattedCharSequence entry : tooltip) {
            guiGraphics.drawString(this.minecraft.font, entry, (mouseX - renderWidth / 2 + 1) * 2, (mouseY + yOff + 9) * 2, 6696723, false);
            yOff += 5;
         }

         var19.popPose();
      }
   }

   @Override
   public void playDownSound(SoundManager handler) {
   }
}
