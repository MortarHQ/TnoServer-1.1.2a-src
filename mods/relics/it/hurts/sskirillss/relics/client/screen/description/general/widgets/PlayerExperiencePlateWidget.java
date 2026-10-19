package it.hurts.sskirillss.relics.client.screen.description.general.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.base.IRelicScreenProvider;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractPlateWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import java.util.ArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

public class PlayerExperiencePlateWidget extends AbstractPlateWidget {
   public PlayerExperiencePlateWidget(int x, int y, IRelicScreenProvider provider) {
      super(x, y, provider, "player_experience");
   }

   @Override
   public void renderContent(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      LocalPlayer player = this.minecraft.player;
      int barWidth = 52;
      int barHeight = 2;
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      GUIRenderer.begin(DescriptionTextures.PLATE_PLAYER_EXPERIENCE_BACKGROUND, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(1.0F, this.height - 3).end();
      GUIRenderer.begin(DescriptionTextures.PLATE_PLAYER_EXPERIENCE_FILLER, poseStack)
         .pos(1.0F, this.height - 3)
         .texSize(barWidth, barHeight)
         .anchor(SpriteAnchor.TOP_LEFT)
         .patternSize((int)(barWidth * this.minecraft.player.experienceProgress), barHeight)
         .end();
      poseStack.popPose();
   }

   @Override
   public void onHovered(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      ItemStack stack = this.getProvider().getStack();
      if (stack.getItem() instanceof IRelicItem relic) {
         PoseStack var16 = guiGraphics.pose();
         ArrayList tooltip = Lists.newArrayList();
         short maxWidth = 150;
         int renderWidth = 0;
         long experience = EntityUtils.getPlayerTotalExperience(this.minecraft.player);
         ArrayList entries = Lists.newArrayList(
            new MutableComponent[]{
               Component.literal("")
                  .append(
                     Component.translatable("tooltip.relics.researching.general.player_experience.title")
                        .withStyle(ChatFormatting.BOLD)
                        .withStyle(ChatFormatting.UNDERLINE)
                  )
                  .append(" " + experience + " (" + EntityUtils.getLevelFromTotalExperience(experience) + ")"),
               Component.literal(" ")
            }
         );
         if (Screen.hasShiftDown()) {
            entries.add(Component.translatable("tooltip.relics.researching.general.player_experience.extra_info").withStyle(ChatFormatting.ITALIC));
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

         var16.pushPose();
         var16.translate(0.0F, 0.0F, 100.0F);
         DescriptionUtils.drawTooltipBackground(guiGraphics, renderWidth, tooltip.size() * 5, mouseX - 9 - renderWidth / 2, mouseY);
         var16.scale(0.5F, 0.5F, 0.5F);
         int yOff = 0;

         for (FormattedCharSequence entry : tooltip) {
            guiGraphics.drawString(this.minecraft.font, entry, (mouseX - renderWidth / 2 + 1) * 2, (mouseY + yOff + 9) * 2, 6696723, false);
            yOff += 5;
         }

         var16.popPose();
      }
   }

   @Override
   public void onTick() {
   }

   @Override
   public String getValue(ItemStack stack) {
      return String.valueOf(this.minecraft.player.experienceLevel);
   }
}
