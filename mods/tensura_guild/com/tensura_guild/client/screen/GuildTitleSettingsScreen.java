package com.tensura_guild.client.screen;

import com.tensura_guild.data.GuildMemberData;
import com.tensura_guild.data.GuildTitleData;
import com.tensura_guild.data.GuildTitleFormatter;
import com.tensura_guild.network.SetGuildTitlePayload;
import com.tensura_guild.registry.GuildAttachments;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

public class GuildTitleSettingsScreen extends Screen {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/other_panel.png");
   private static final int TEXTURE_SIZE = 256;
   private static final int IMAGE_WIDTH = 138;
   private static final int IMAGE_HEIGHT = 175;
   private static final GuildTitleSettingsScreen.Rect BACK_BUTTON = new GuildTitleSettingsScreen.Rect(10, 11, 28, 21);
   private static final GuildTitleSettingsScreen.Rect BACK_BUTTON_HOVER_SRC = new GuildTitleSettingsScreen.Rect(2, 177, 20, 187);
   private static final GuildTitleSettingsScreen.Rect TEXT_AREA = new GuildTitleSettingsScreen.Rect(16, 29, 122, 159);
   private static final int OPTION_COLUMNS = 2;
   private static final int OPTION_ROW_HEIGHT = 12;
   private static final int OPTION_COLUMN_GAP = 4;
   private static final int TEXT_COLOR = -13950696;
   private static final int HOVER_TEXT_COLOR = -10542058;
   private static final int SELECTED_TEXT_COLOR = -7719651;
   private final Screen parent;
   private int leftPos;
   private int topPos;

   public GuildTitleSettingsScreen(Screen parent) {
      super(Component.empty());
      this.parent = parent;
   }

   protected void init() {
      this.leftPos = (this.width - 138) / 2;
      this.topPos = (this.height - 175) / 2;
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0.0F, 0.0F, 138, 175, 256, 256);
      this.renderBackButton(graphics, mouseX, mouseY);
      this.renderTitleOptions(graphics, mouseX, mouseY);
      super.render(graphics, mouseX, mouseY, partialTick);
   }

   private void renderBackButton(GuiGraphics graphics, int mouseX, int mouseY) {
      if (this.isMouseOver(mouseX, mouseY, this.absolute(BACK_BUTTON))) {
         graphics.blit(
            TEXTURE,
            this.leftPos + BACK_BUTTON.left(),
            this.topPos + BACK_BUTTON.top(),
            BACK_BUTTON.width(),
            BACK_BUTTON.height(),
            BACK_BUTTON_HOVER_SRC.left(),
            BACK_BUTTON_HOVER_SRC.top(),
            BACK_BUTTON_HOVER_SRC.width(),
            BACK_BUTTON_HOVER_SRC.height(),
            256,
            256
         );
      }
   }

   private void renderTitleOptions(GuiGraphics graphics, int mouseX, int mouseY) {
      List<GuildTitleSettingsScreen.TitleOption> options = this.titleOptions();
      GuildTitleSettingsScreen.Rect area = this.absoluteTextArea();
      graphics.enableScissor(area.left(), area.top(), area.right() + 1, area.bottom() + 1);

      try {
         for (int i = 0; i < options.size(); i++) {
            GuildTitleSettingsScreen.Rect cell = this.optionCell(i);
            if (cell.bottom() > area.bottom()) {
               break;
            }

            this.renderOption(graphics, options.get(i), cell, mouseX, mouseY);
         }
      } finally {
         graphics.disableScissor();
      }
   }

   private void renderOption(GuiGraphics graphics, GuildTitleSettingsScreen.TitleOption option, GuildTitleSettingsScreen.Rect cell, int mouseX, int mouseY) {
      Component text = option.text();
      int textWidth = this.font.width(text);
      if (textWidth > 0) {
         boolean hovered = this.isMouseOver(mouseX, mouseY, cell);
         float fitScale = Math.min(1.0F, Math.max(0.35F, (cell.width() - 2.0F) / textWidth));
         float scale = fitScale * (hovered ? this.hoverScale() : 1.0F);
         float scaledWidth = textWidth * scale;
         float scaledHeight = 9.0F * scale;
         float drawX = cell.left() + (cell.width() - scaledWidth) / 2.0F;
         float drawY = cell.top() + (cell.height() - scaledHeight) / 2.0F;
         int color = hovered ? -10542058 : (option.selected() ? -7719651 : -13950696);
         graphics.pose().pushPose();

         try {
            graphics.pose().translate(drawX, drawY, 0.0F);
            graphics.pose().scale(scale, scale, 1.0F);
            graphics.drawString(this.font, text, 0, 0, color, false);
         } finally {
            graphics.pose().popPose();
         }
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.isMouseOver(mouseX, mouseY, this.absolute(BACK_BUTTON))) {
         this.returnToParent();
         ScreenHelper.clicked();
         return true;
      }

      if (button == 0) {
         List<GuildTitleSettingsScreen.TitleOption> options = this.titleOptions();

         for (int i = 0; i < options.size(); i++) {
            GuildTitleSettingsScreen.Rect cell = this.optionCell(i);
            if (cell.bottom() > this.absoluteTextArea().bottom()) {
               break;
            }

            if (this.isMouseOver(mouseX, mouseY, cell)) {
               GuildTitleSettingsScreen.TitleOption option = options.get(i);
               PacketDistributor.sendToServer(
                  option.title().map(SetGuildTitlePayload::new).orElseGet(SetGuildTitlePayload::disable), new CustomPacketPayload[0]
               );
               this.returnToParent();
               ScreenHelper.clicked();
               return true;
            }
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   public void onClose() {
      this.returnToParent();
   }

   private List<GuildTitleSettingsScreen.TitleOption> titleOptions() {
      Optional<String> currentTitle = Optional.empty();
      List<String> titles = new ArrayList<>();
      if (this.minecraft != null && this.minecraft.player != null) {
         GuildMemberData memberData = (GuildMemberData)this.minecraft.player.getData(GuildAttachments.GUILD_MEMBER);
         GuildTitleData titleData = (GuildTitleData)this.minecraft.player.getData(GuildAttachments.GUILD_TITLES);
         currentTitle = memberData.title().flatMap(GuildTitleData::normalizeTitle);
         titles.addAll(titleData.titles());
         currentTitle.ifPresent(titlex -> GuildTitleData.normalizeTitle(titlex).filter(value -> !titles.contains(value)).ifPresent(titles::add));
      }

      List<GuildTitleSettingsScreen.TitleOption> options = new ArrayList<>();
      options.add(
         new GuildTitleSettingsScreen.TitleOption(Component.translatable("screen.tensura_guild.title.disable"), Optional.empty(), currentTitle.isEmpty())
      );

      for (String title : titles) {
         options.add(
            new GuildTitleSettingsScreen.TitleOption(GuildTitleFormatter.format(title), Optional.of(title), currentTitle.filter(title::equals).isPresent())
         );
      }

      return options;
   }

   private GuildTitleSettingsScreen.Rect optionCell(int index) {
      GuildTitleSettingsScreen.Rect area = this.absoluteTextArea();
      int cellWidth = (area.width() - 4) / 2;
      int column = index % 2;
      int row = index / 2;
      int x = area.left() + column * (cellWidth + 4);
      int y = area.top() + row * 12;
      return new GuildTitleSettingsScreen.Rect(x, y, x + cellWidth - 1, y + 12 - 1);
   }

   private GuildTitleSettingsScreen.Rect absoluteTextArea() {
      return this.absolute(TEXT_AREA);
   }

   private GuildTitleSettingsScreen.Rect absolute(GuildTitleSettingsScreen.Rect rect) {
      return new GuildTitleSettingsScreen.Rect(this.leftPos + rect.left(), this.topPos + rect.top(), this.leftPos + rect.right(), this.topPos + rect.bottom());
   }

   private float hoverScale() {
      double seconds = Util.getMillis() / 1000.0;
      return 1.0F + (float)(Math.sin(seconds * Math.PI * 4.0) * 0.08);
   }

   private void returnToParent() {
      if (this.minecraft != null) {
         this.minecraft.setScreen(this.parent);
      }
   }

   public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
   }

   protected void renderBlurredBackground(float partialTick) {
   }

   public boolean isPauseScreen() {
      return false;
   }

   private boolean isMouseOver(double mouseX, double mouseY, GuildTitleSettingsScreen.Rect rect) {
      return mouseX >= rect.left() && mouseX <= rect.right() && mouseY >= rect.top() && mouseY <= rect.bottom();
   }

   private record Rect(int left, int top, int right, int bottom) {
      int width() {
         return this.right - this.left + 1;
      }

      int height() {
         return this.bottom - this.top + 1;
      }
   }

   private record TitleOption(Component text, Optional<String> title, boolean selected) {
   }
}
