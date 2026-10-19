package it.hurts.sskirillss.relics.client.screen.description.experience.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.description.experience.ExperienceDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.experience.widgets.base.AbstractExperienceActionWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.network.packets.leveling.PacketRelicTweak;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;

public class UpgradeExperienceActionWidget extends AbstractExperienceActionWidget {
   public UpgradeExperienceActionWidget(int x, int y, ExperienceDescriptionScreen screen) {
      super(x, y, PacketRelicTweak.Operation.UPGRADE, screen);
   }

   @Override
   public boolean isLocked() {
      return true;
   }

   @Override
   public void playDownSound(SoundManager handler) {
   }

   @Override
   public void onHovered(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      if (this.getScreen().getStack().getItem() instanceof IRelicItem) {
         PoseStack poseStack = guiGraphics.pose();
         List<FormattedCharSequence> tooltip = Lists.newArrayList();
         int maxWidth = 100;
         int renderWidth = 0;

         for (MutableComponent entry : Lists.newArrayList(
            new MutableComponent[]{Component.literal("To avoid issues with existing worlds, this feature will be added with the update to Minecraft 1.22.")}
         )) {
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
