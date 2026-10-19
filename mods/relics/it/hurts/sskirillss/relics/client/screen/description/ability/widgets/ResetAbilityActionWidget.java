package it.hurts.sskirillss.relics.client.screen.description.ability.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.description.ability.AbilityDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.ability.widgets.base.AbstractAbilityActionWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.init.SoundRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.network.packets.leveling.PacketRelicTweak;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.FormattedCharSequence;

public class ResetAbilityActionWidget extends AbstractAbilityActionWidget {
   public ResetAbilityActionWidget(int x, int y, AbilityDescriptionScreen screen) {
      super(x, y, PacketRelicTweak.Operation.RESET, screen);
   }

   @Override
   public boolean isLocked() {
      return !(
         this.getScreen().getStack().getItem() instanceof IRelicItem relic
            && relic.mayPlayerReset(this.minecraft.player, this.getScreen().getStack(), this.getAbility())
      );
   }

   @Override
   public void playDownSound(SoundManager handler) {
      if (!this.isLocked()) {
         handler.play(SimpleSoundInstance.forUI((SoundEvent)SoundRegistry.TABLE_RESET.get(), 1.0F));
      }
   }

   @Override
   public void onHovered(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      if (this.getScreen().getStack().getItem() instanceof IRelicItem relic && relic.isAbilityUnlocked(this.getScreen().getStack(), this.getAbility())) {
         AbilityData data = relic.getAbilityData(this.getAbility());
         if (!data.getStats().isEmpty()) {
            PoseStack poseStack = guiGraphics.pose();
            List<FormattedCharSequence> tooltip = Lists.newArrayList();
            int maxWidth = 120;
            int renderWidth = 0;
            int requiredExperience = relic.getResetPlayerExperienceCost(this.getScreen().getStack(), this.getAbility());
            long experience = EntityUtils.getPlayerTotalExperience(this.minecraft.player);
            boolean hasExperience = requiredExperience <= experience;
            MutableComponent negativeStatus = Component.translatable("tooltip.relics.relic.status.negative");
            MutableComponent positiveStatus = Component.translatable("tooltip.relics.relic.status.positive");
            List<MutableComponent> entries = Lists.newArrayList(
               new MutableComponent[]{
                  Component.translatable("tooltip.relics.relic.reset.description").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.UNDERLINE),
                  Component.literal(" ")
               }
            );
            if (relic.getAbilityLevel(this.getScreen().getStack(), this.getAbility()) > 0) {
               entries.add(
                  Component.translatable(
                     "tooltip.relics.relic.reset.cost",
                     new Object[]{
                        requiredExperience,
                        hasExperience
                           ? EntityUtils.calculateExperienceLevelLoss(this.minecraft.player, requiredExperience)
                           : EntityUtils.getLevelFromTotalExperience(requiredExperience),
                        hasExperience ? positiveStatus : negativeStatus
                     }
                  )
               );
            } else {
               entries.add(Component.translatable("tooltip.relics.relic.reset.locked"));
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
