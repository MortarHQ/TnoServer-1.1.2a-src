package it.hurts.sskirillss.relics.client.screen.description.ability.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.description.ability.AbilityDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.ability.widgets.base.AbstractAbilityActionWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.init.SoundRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.network.packets.leveling.PacketRelicTweak;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.FormattedCharSequence;

public class UpgradeAbilityActionWidget extends AbstractAbilityActionWidget {
   public UpgradeAbilityActionWidget(int x, int y, AbilityDescriptionScreen screen) {
      super(x, y, PacketRelicTweak.Operation.UPGRADE, screen);
   }

   @Override
   public boolean isLocked() {
      return !(
         this.getScreen().getStack().getItem() instanceof IRelicItem relic
            && relic.mayPlayerUpgrade(this.minecraft.player, this.getScreen().getStack(), this.getAbility())
      );
   }

   @Override
   public void playDownSound(SoundManager handler) {
      if (this.getScreen().getStack().getItem() instanceof IRelicItem relic && !this.isLocked()) {
         int level = relic.getAbilityLevel(this.getScreen().getStack(), this.getAbility());
         int maxLevel = relic.getAbilityMaxLevel(this.getScreen().getStack(), this.getAbility());
         handler.play(
            SimpleSoundInstance.forUI(
               (SoundEvent)SoundRegistry.TABLE_UPGRADE.get(),
               Screen.hasShiftDown() && relic.mayPlayerUpgrade(this.minecraft.player, this.getScreen().getStack(), this.getAbility())
                  ? 2.0F
                  : 1.0F + (float)level / maxLevel
            )
         );
      }
   }

   @Override
   public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      if (this.getScreen().getStack().getItem() instanceof IRelicItem relic) {
         boolean isQuick = Screen.hasShiftDown() && relic.mayPlayerUpgrade(this.minecraft.player, this.getScreen().getStack(), this.getAbility());
         float color = isQuick ? (float)(1.05F + Math.sin((this.minecraft.player.tickCount + this.getAbility().length() * 10) * 0.5F) * 0.1F) : 1.0F;
         RenderSystem.setShaderColor(color, color, color, 1.0F);
         guiGraphics.blit(
            ResourceLocation.fromNamespaceAndPath(
               "relics", "textures/gui/description/ability/upgrade_button_" + (this.isLocked() ? "inactive" : "active") + (isQuick ? "_quick" : "") + ".png"
            ),
            this.getX(),
            this.getY(),
            0.0F,
            0.0F,
            this.width,
            this.height,
            this.width,
            this.height
         );
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         if (this.isHovered) {
            guiGraphics.blit(DescriptionTextures.ACTION_BUTTON_OUTLINE, this.getX(), this.getY(), 0.0F, 0.0F, this.width, this.height, this.width, this.height);
         }
      }
   }

   @Override
   public void onHovered(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      if (this.getScreen().getStack().getItem() instanceof IRelicItem relic && relic.isAbilityUnlocked(this.getScreen().getStack(), this.getAbility())) {
         AbilityData data = relic.getAbilityData(this.getAbility());
         if (!data.getStats().isEmpty()) {
            List<FormattedCharSequence> tooltip = Lists.newArrayList();
            PoseStack poseStack = guiGraphics.pose();
            int maxWidth = 120;
            int renderWidth = 0;
            int requiredPoints = data.getRequiredPoints();
            int requiredExperience = relic.getUpgradePlayerExperienceCost(this.getScreen().getStack(), this.getAbility());
            int points = relic.getRelicLevelingPoints(this.getScreen().getStack());
            long experience = EntityUtils.getPlayerTotalExperience(this.minecraft.player);
            MutableComponent negativeStatus = Component.translatable("tooltip.relics.relic.status.negative");
            MutableComponent positiveStatus = Component.translatable("tooltip.relics.relic.status.positive");
            List<MutableComponent> entries = Lists.newArrayList(
               new MutableComponent[]{
                  Component.translatable("tooltip.relics.relic.upgrade.description").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.UNDERLINE)
               }
            );
            boolean isMaxLevel = relic.isAbilityMaxLevel(this.getScreen().getStack(), this.getAbility());
            boolean isQuick = Screen.hasShiftDown() && relic.mayPlayerUpgrade(this.minecraft.player, this.getScreen().getStack(), this.getAbility());
            boolean hasExperience = requiredExperience <= experience;
            Component obfuscated = Component.literal("XXX").withStyle(ChatFormatting.OBFUSCATED);
            if (!isMaxLevel) {
               entries.add(Component.literal(" "));
               entries.add(
                  Component.translatable(
                     "tooltip.relics.relic.upgrade.cost",
                     new Object[]{
                        isQuick ? obfuscated : requiredPoints,
                        requiredPoints > points ? negativeStatus : positiveStatus,
                        isQuick ? obfuscated : requiredExperience,
                        isQuick
                           ? obfuscated
                           : hasExperience
                              ? EntityUtils.calculateExperienceLevelLoss(this.minecraft.player, requiredExperience)
                              : EntityUtils.getLevelFromTotalExperience(requiredExperience),
                        hasExperience ? positiveStatus : negativeStatus
                     }
                  )
               );
            }

            if (!this.isLocked()) {
               entries.add(Component.literal(" "));
               entries.add(Component.literal("▶ ").append(Component.translatable("tooltip.relics.relic.upgrade.quick")));
            }

            if (isMaxLevel) {
               entries.add(Component.literal(" "));
               entries.add(Component.literal("▶ ").append(Component.translatable("tooltip.relics.relic.upgrade.locked")));
            }

            for (MutableComponent entry : entries) {
               int entryWidth = (this.minecraft.font.width(entry) + 4) / 2;
               if (entryWidth > renderWidth) {
                  renderWidth = Math.min(entryWidth, maxWidth);
               }

               tooltip.addAll(this.minecraft.font.split(entry, maxWidth * 2));
            }

            int height = Math.round(tooltip.size() * 5.0F);
            int renderX = this.getX() + this.width + 1;
            int renderY = mouseY - height / 2 - 9;
            DescriptionUtils.drawTooltipBackground(guiGraphics, renderWidth, height, renderX, renderY);
            int yOff = 0;
            poseStack.scale(0.5F, 0.5F, 0.5F);

            for (FormattedCharSequence entry : tooltip) {
               guiGraphics.drawString(this.minecraft.font, entry, (renderX + 10) * 2, (renderY + 9 + yOff) * 2, 6696723, false);
               yOff += 5;
            }

            poseStack.scale(1.0F, 1.0F, 1.0F);
         }
      }
   }
}
