package it.hurts.sskirillss.relics.client.screen.description.general.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.base.IRelicScreenProvider;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractPlateWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import java.util.ArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

public class LuckPlateWidget extends AbstractPlateWidget {
   public LuckPlateWidget(int x, int y, IRelicScreenProvider provider) {
      super(x, y, provider, "luck");
   }

   @Override
   public void onHovered(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      ItemStack stack = this.getProvider().getStack();
      if (stack.getItem() instanceof IRelicItem relic) {
         PoseStack var14 = guiGraphics.pose();
         ArrayList tooltip = Lists.newArrayList();
         short maxWidth = 150;
         int renderWidth = 0;
         ArrayList entries = Lists.newArrayList(
            new MutableComponent[]{
               Component.literal("")
                  .append(
                     Component.translatable("tooltip.relics.researching.general.luck.title").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.UNDERLINE)
                  )
                  .append(" " + relic.getRelicLuck(stack) + "%"),
               Component.literal(" ")
            }
         );
         if (Screen.hasShiftDown()) {
            entries.add(Component.translatable("tooltip.relics.researching.general.luck.extra_info").withStyle(ChatFormatting.ITALIC));
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
   public void onTick() {
   }

   @Override
   public String getValue(ItemStack stack) {
      return (stack.getItem() instanceof IRelicItem relic ? relic.getRelicLuck(stack) : 0) + "%";
   }
}
