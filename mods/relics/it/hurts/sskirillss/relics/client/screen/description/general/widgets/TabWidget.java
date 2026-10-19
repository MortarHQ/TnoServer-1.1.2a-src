package it.hurts.sskirillss.relics.client.screen.description.general.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.base.IHoverableWidget;
import it.hurts.sskirillss.relics.client.screen.base.IRelicScreenProvider;
import it.hurts.sskirillss.relics.client.screen.base.ITabbedDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.general.misc.DescriptionTab;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractDescriptionWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.GUIScissors;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import java.util.List;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class TabWidget extends AbstractDescriptionWidget implements IHoverableWidget {
   private IRelicScreenProvider source;
   private IRelicScreenProvider target;
   private DescriptionTab tab;

   public TabWidget(int x, int y, IRelicScreenProvider source, DescriptionTab tab, IRelicScreenProvider target) {
      super(x, y, 17, 19);
      this.source = source;
      this.target = target;
      this.tab = tab;
   }

   @Override
   public void onPress() {
      if (!this.isLocked()) {
         this.minecraft.setScreen((Screen)this.target);
      }
   }

   public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      PoseStack poseStack = guiGraphics.pose();
      LocalPlayer player = this.minecraft.player;
      poseStack.pushPose();
      if (this.isLocked()) {
         GUIScissors.begin(this.getX(), this.getY(), this.width, 19);
         GUIRenderer.begin(DescriptionTextures.TAB, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.getX(), this.getY() + 11).end();
         GUIScissors.end();
      } else {
         GUIRenderer.begin(DescriptionTextures.TAB, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.getX(), this.getY()).end();
         GUIRenderer.begin(
               ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/general/tabs/" + this.tab.name().toLowerCase(Locale.ROOT) + ".png"),
               poseStack
            )
            .anchor(SpriteAnchor.TOP_LEFT)
            .pos(this.getX() + 2, this.getY() + 5)
            .end();
         if (this.isHovered()) {
            GUIRenderer.begin(DescriptionTextures.TAB_OUTLINE, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.getX() - 1, this.getY() - 1).end();
         }
      }

      poseStack.popPose();
   }

   @Override
   public boolean isLocked() {
      return this.minecraft.screen instanceof ITabbedDescriptionScreen screen && screen.getTab() == this.tab;
   }

   @Override
   public void playDownSound(SoundManager handler) {
      if (!this.isLocked()) {
         super.playDownSound(handler);
      }
   }

   @Override
   public void onHovered(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      PoseStack poseStack = guiGraphics.pose();
      List<FormattedCharSequence> tooltip = Lists.newArrayList();
      int maxWidth = 100;
      int renderWidth = 0;

      for (MutableComponent entry : Lists.newArrayList(
         new MutableComponent[]{
            Component.translatable("tooltip.relics.researching.tab." + this.tab.name().toLowerCase(Locale.ROOT)).withStyle(ChatFormatting.BOLD)
         }
      )) {
         int entryWidth = (this.minecraft.font.width(entry) + 4) / 2;
         if (entryWidth > renderWidth) {
            renderWidth = Math.min(entryWidth, maxWidth);
         }

         tooltip.addAll(this.minecraft.font.split(entry, maxWidth * 2));
      }

      poseStack.pushPose();
      poseStack.translate(0.0F, 0.0F, 100.0F);
      DescriptionUtils.drawTooltipBackground(guiGraphics, renderWidth, tooltip.size() * 5, mouseX - 9 - renderWidth / 2, mouseY);
      poseStack.scale(0.5F, 0.5F, 0.5F);
      int yOff = 0;

      for (FormattedCharSequence entry : tooltip) {
         guiGraphics.drawString(this.minecraft.font, entry, (mouseX - renderWidth / 2 + 1) * 2, (mouseY + yOff + 9) * 2, 6696723, false);
         yOff += 5;
      }

      poseStack.popPose();
   }

   public IRelicScreenProvider getSource() {
      return this.source;
   }

   public IRelicScreenProvider getTarget() {
      return this.target;
   }

   public DescriptionTab getTab() {
      return this.tab;
   }
}
