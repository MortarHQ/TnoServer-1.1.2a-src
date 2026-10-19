package it.hurts.sskirillss.relics.client.screen.description.general.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.badges.base.RelicBadge;
import it.hurts.sskirillss.relics.client.screen.base.IRelicScreenProvider;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractBadgeWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

public class RelicBadgeWidget extends AbstractBadgeWidget {
   private final RelicBadge badge;

   public RelicBadgeWidget(int x, int y, IRelicScreenProvider provider, RelicBadge badge) {
      super(x, y, provider, badge);
      this.badge = badge;
   }

   public RelicBadge getBadge() {
      return this.badge;
   }

   @Override
   public void onHovered(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      ItemStack stack = this.getProvider().getStack();
      if (stack.getItem() instanceof IRelicItem relic) {
         PoseStack var15 = guiGraphics.pose();
         ArrayList tooltip = Lists.newArrayList();
         byte maxWidth = 100;
         int renderWidth = 0;
         List<MutableComponent> entries = Lists.newArrayList(
            new MutableComponent[]{this.getBadge().getTitle(stack).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.UNDERLINE), Component.literal(" ")}
         );
         entries.addAll(this.getBadge().getDescription(stack));
         List<MutableComponent> hint = this.getBadge().getHint(stack);
         if (!hint.isEmpty()) {
            entries.add(Component.literal(" "));
            if (Screen.hasShiftDown()) {
               entries.addAll(hint.stream().map(entryx -> entryx.withStyle(ChatFormatting.ITALIC)).toList());
            } else {
               entries.add(Component.translatable("tooltip.relics.researching.general.extra_info"));
            }
         }

         for (MutableComponent entry : entries) {
            int entryWidth = this.minecraft.font.width(entry) / 2;
            if (entryWidth > renderWidth) {
               renderWidth = Math.min(entryWidth + 2, maxWidth);
            }

            tooltip.addAll(this.minecraft.font.split(entry, maxWidth * 2));
         }

         var15.pushPose();
         var15.translate(0.0F, 0.0F, 400.0F);
         DescriptionUtils.drawTooltipBackground(guiGraphics, renderWidth, tooltip.size() * 5, mouseX - 9 - renderWidth / 2, mouseY);
         var15.scale(0.5F, 0.5F, 0.5F);
         int yOff = 0;

         for (FormattedCharSequence entry : tooltip) {
            guiGraphics.drawString(this.minecraft.font, entry, (mouseX - renderWidth / 2 + 1) * 2, (mouseY + yOff + 9) * 2, 6696723, false);
            yOff += 5;
         }

         var15.popPose();
      }
   }
}
