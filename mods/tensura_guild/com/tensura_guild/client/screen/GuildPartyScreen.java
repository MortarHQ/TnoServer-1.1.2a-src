package com.tensura_guild.client.screen;

import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import com.tensura_guild.client.GuildPartyClient;
import com.tensura_guild.data.GuildPartyState;
import com.tensura_guild.network.GuildPartyActionPayload;
import com.tensura_guild.network.GuildPartyChatPayload;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.network.PacketDistributor;
import org.slf4j.Logger;

public class GuildPartyScreen extends Screen {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final ResourceLocation EMPTY_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/party_gui.png");
   private static final ResourceLocation CREATED_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/party_gui_created.png");
   private static final ResourceLocation BUTTON_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/party_button.png");
   private static final ResourceLocation GUILD_TAB_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/guild_tab.png");
   private static final ResourceLocation TASK_TAB_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/task_system_tab.png");
   private static final ResourceLocation PARTY_TAB_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/party_tab.png");
   private static final int TEXTURE_SIZE = 256;
   private static final int TAB_TEXTURE_WIDTH = 48;
   private static final int TAB_TEXTURE_HEIGHT = 21;
   private static final int TAB_WIDTH = 24;
   private static final int TAB_HEIGHT = 21;
   private static final int IMAGE_WIDTH = 256;
   private static final int IMAGE_HEIGHT = 163;
   private static final GuildPartyScreen.Rect GUILD_BUTTON = new GuildPartyScreen.Rect(7, 2, 31, 23);
   private static final GuildPartyScreen.Rect TASK_BUTTON = new GuildPartyScreen.Rect(33, 2, 57, 23);
   private static final GuildPartyScreen.Rect PARTY_BUTTON = new GuildPartyScreen.Rect(59, 2, 83, 23);
   private static final GuildPartyScreen.Rect PARTY_NAME_INPUT = new GuildPartyScreen.Rect(52, 73, 191, 82);
   private static final GuildPartyScreen.Rect CREATE_BUTTON = new GuildPartyScreen.Rect(59, 104, 118, 119);
   private static final GuildPartyScreen.Rect JOIN_BUTTON = new GuildPartyScreen.Rect(122, 104, 181, 119);
   private static final GuildPartyScreen.Rect CHAT_AREA = new GuildPartyScreen.Rect(17, 40, 164, 133);
   private static final GuildPartyScreen.Rect CHAT_INPUT = new GuildPartyScreen.Rect(19, 137, 162, 145);
   private static final GuildPartyScreen.Rect LEAVE_BUTTON = new GuildPartyScreen.Rect(176, 134, 225, 149);
   private static final GuildPartyScreen.Rect[] MEMBER_ROWS = new GuildPartyScreen.Rect[]{
      new GuildPartyScreen.Rect(176, 39, 225, 48),
      new GuildPartyScreen.Rect(176, 50, 225, 59),
      new GuildPartyScreen.Rect(176, 61, 225, 70),
      new GuildPartyScreen.Rect(176, 72, 225, 81)
   };
   private static final GuildPartyScreen.Rect[] KICK_BUTTONS = new GuildPartyScreen.Rect[]{
      new GuildPartyScreen.Rect(219, 51, 224, 58), new GuildPartyScreen.Rect(219, 62, 224, 69), new GuildPartyScreen.Rect(219, 73, 224, 80)
   };
   private static final int BUTTON_TEXTURE_WIDTH = 59;
   private static final int BUTTON_TEXTURE_HEIGHT = 30;
   private static final int BUTTON_SRC_HEIGHT = 15;
   private static final GuildPartyScreen.Rect LEAVE_BUTTON_SRC = new GuildPartyScreen.Rect(3, 166, 52, 180);
   private static final GuildPartyScreen.Rect LEAVE_BUTTON_HOVER_SRC = new GuildPartyScreen.Rect(3, 181, 52, 195);
   private static final int TEXT_COLOR = -13950696;
   private static final int MUTED_TEXT_COLOR = -10858168;
   private static final int SCROLL_THUMB_COLOR = -2008402138;
   private static final float MEMBER_NAME_SCALE = 0.72F;
   private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ROOT);
   private GuildPartyState state = GuildPartyState.EMPTY;
   private EditBox partyNameBox;
   private EditBox chatBox;
   private int leftPos;
   private int topPos;
   private int chatScroll;
   private boolean awaitingSync = true;
   private boolean cachedPartyBeforeSync;

   public GuildPartyScreen() {
      super(Component.empty());
      this.state = GuildPartyClient.currentState();
      this.cachedPartyBeforeSync = this.state.inParty();
   }

   protected void init() {
      this.state = GuildPartyClient.currentState();
      this.cachedPartyBeforeSync = this.state.inParty();
      this.awaitingSync = true;
      this.leftPos = (this.width - 256) / 2;
      this.topPos = (this.height - 163) / 2;
      this.partyNameBox = new EditBox(
         this.font,
         this.leftPos + PARTY_NAME_INPUT.left(),
         this.topPos + PARTY_NAME_INPUT.top(),
         PARTY_NAME_INPUT.width(),
         PARTY_NAME_INPUT.height(),
         Component.empty()
      );
      this.partyNameBox.setBordered(false);
      this.partyNameBox.setMaxLength(32);
      this.partyNameBox.setTextColor(-13950696);
      this.partyNameBox.setTextColorUneditable(-10858168);
      this.partyNameBox.setTextShadow(false);
      this.addRenderableWidget(this.partyNameBox);
      this.chatBox = new EditBox(
         this.font, this.leftPos + CHAT_INPUT.left(), this.topPos + CHAT_INPUT.top(), CHAT_INPUT.width(), CHAT_INPUT.height(), Component.empty()
      );
      this.chatBox.setBordered(false);
      this.chatBox.setMaxLength(160);
      this.chatBox.setTextColor(-13950696);
      this.chatBox.setTextColorUneditable(-10858168);
      this.chatBox.setTextShadow(false);
      this.addRenderableWidget(this.chatBox);
      this.updateWidgetVisibility();
      GuildPartyClient.requestSync();
   }

   public void updateState(GuildPartyState state) {
      GuildPartyState nextState = state == null ? GuildPartyState.EMPTY : state;
      if (this.awaitingSync && this.cachedPartyBeforeSync && !nextState.inParty()) {
         LOGGER.warn(
            "Guild party sync returned empty after cached party '{}'. Check GuildPartyService saved data/load path if the player should still be in a party.",
            this.state.partyName()
         );
      }

      this.state = nextState;
      this.awaitingSync = false;
      this.updateWidgetVisibility();
      this.chatScroll = Mth.clamp(this.chatScroll, 0, this.maxChatScroll());
   }

   private void updateWidgetVisibility() {
      if (this.partyNameBox != null) {
         this.partyNameBox.visible = !this.awaitingSync && !this.state.inParty();
      }

      if (this.chatBox != null) {
         this.chatBox.visible = this.state.inParty();
      }
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      ResourceLocation texture = this.state.inParty() ? CREATED_TEXTURE : EMPTY_TEXTURE;
      graphics.blit(texture, this.leftPos, this.topPos, 0, 0.0F, 0.0F, 256, 163, 256, 256);
      this.renderTopTabs(graphics, mouseX, mouseY);
      if (this.state.inParty()) {
         this.renderPartyMembers(graphics, mouseX, mouseY);
         this.renderPartyChat(graphics);
         this.renderLeaveButton(graphics, mouseX, mouseY);
      } else if (!this.awaitingSync) {
         this.renderActionButton(graphics, CREATE_BUTTON, Component.translatable("screen.tensura_guild.party.create"), mouseX, mouseY);
         this.renderActionButton(graphics, JOIN_BUTTON, Component.translatable("screen.tensura_guild.party.join"), mouseX, mouseY);
      }

      super.render(graphics, mouseX, mouseY, partialTick);
   }

   private void renderTopTabs(GuiGraphics graphics, int mouseX, int mouseY) {
      this.renderTab(graphics, GUILD_TAB_TEXTURE, GUILD_BUTTON, mouseX, mouseY);
      this.renderTab(graphics, TASK_TAB_TEXTURE, TASK_BUTTON, mouseX, mouseY);
      if (!this.state.inParty()) {
         this.renderTab(graphics, PARTY_TAB_TEXTURE, PARTY_BUTTON, mouseX, mouseY);
      }
   }

   private void renderTab(GuiGraphics graphics, ResourceLocation texture, GuildPartyScreen.Rect rect, int mouseX, int mouseY) {
      int uOffset = this.isMouseOver(mouseX, mouseY, rect) ? 24 : 0;
      graphics.blit(texture, this.leftPos + rect.left(), this.topPos + rect.top(), uOffset, 0.0F, 24, 21, 48, 21);
   }

   private void renderActionButton(GuiGraphics graphics, GuildPartyScreen.Rect rect, Component label, int mouseX, int mouseY) {
      boolean hovered = this.isMouseOver(mouseX, mouseY, rect);
      int sourceY = hovered ? 15 : 0;
      graphics.blit(BUTTON_TEXTURE, this.leftPos + rect.left(), this.topPos + rect.top(), rect.width(), rect.height(), 0.0F, sourceY, 59, 15, 59, 30);
      this.drawCenteredText(graphics, label, rect, -13950696);
   }

   private void renderLeaveButton(GuiGraphics graphics, int mouseX, int mouseY) {
      boolean hovered = this.isMouseOver(mouseX, mouseY, LEAVE_BUTTON);
      GuildPartyScreen.Rect source = hovered ? LEAVE_BUTTON_HOVER_SRC : LEAVE_BUTTON_SRC;
      graphics.blit(
         CREATED_TEXTURE,
         this.leftPos + LEAVE_BUTTON.left(),
         this.topPos + LEAVE_BUTTON.top(),
         LEAVE_BUTTON.width(),
         LEAVE_BUTTON.height(),
         source.left(),
         source.top(),
         source.width(),
         source.height(),
         256,
         256
      );
      this.drawCenteredText(
         graphics, Component.translatable(this.isLeader() ? "screen.tensura_guild.party.disband" : "screen.tensura_guild.party.leave"), LEAVE_BUTTON, -13950696
      );
   }

   private void renderPartyMembers(GuiGraphics graphics, int mouseX, int mouseY) {
      List<GuildPartyState.Member> members = this.state.members();

      for (int i = 0; i < Math.min(MEMBER_ROWS.length, members.size()); i++) {
         GuildPartyState.Member member = members.get(i);
         GuildPartyScreen.Rect row = MEMBER_ROWS[i];
         this.renderPlayerHead(graphics, member.id(), member.name(), this.leftPos + row.left(), this.topPos + row.top(), 9);
         Component name = this.shorten(Component.literal(member.name()), Math.round((row.width() - 12) / 0.72F));
         this.drawScaledText(graphics, name, this.leftPos + row.left() + 11, this.topPos + row.top() + 1, 0.72F, -13950696);
      }
   }

   private void renderPartyChat(GuiGraphics graphics) {
      int left = this.leftPos + CHAT_AREA.left();
      int top = this.topPos + CHAT_AREA.top();
      int right = this.leftPos + CHAT_AREA.right() + 1;
      int bottom = this.topPos + CHAT_AREA.bottom() + 1;
      graphics.enableScissor(left, top, right, bottom);

      try {
         int y = bottom - 9;
         List<GuildPartyState.Message> messages = this.state.messages();
         if (!messages.isEmpty()) {
            this.chatScroll = Mth.clamp(this.chatScroll, 0, this.maxChatScroll());
            int start = Math.max(0, messages.size() - 1 - this.chatScroll);

            for (int i = start; i >= 0 && y >= top; i--) {
               GuildPartyState.Message message = messages.get(i);
               Component time = Component.literal(this.timeFormat.format(new Date(message.sentAtMillis())));
               y -= 9;
               if (y < top) {
                  break;
               }

               graphics.drawString(this.font, time, left + 11, y, -10858168, false);
               Component line = Component.literal(message.senderName() + ": " + message.content());
               Iterator var13 = this.font.split(line, CHAT_AREA.width() - 14).reversed().iterator();

               while (true) {
                  if (var13.hasNext()) {
                     FormattedCharSequence wrapped = (FormattedCharSequence)var13.next();
                     y -= 9 + 1;
                     if (y >= top) {
                        graphics.drawString(this.font, wrapped, left + 11, y, -13950696, false);
                        continue;
                     }
                  }

                  this.renderPlayerHead(graphics, message.senderId(), message.senderName(), left, y, 9);
                  y -= 3;
                  break;
               }
            }

            this.renderChatScrollBar(graphics, messages.size());
            return;
         }
      } finally {
         graphics.disableScissor();
      }
   }

   private void renderChatScrollBar(GuiGraphics graphics, int messageCount) {
      if (messageCount > 4) {
         int trackX = this.leftPos + CHAT_AREA.right() - 2;
         int trackTop = this.topPos + CHAT_AREA.top();
         int trackHeight = CHAT_AREA.height();
         int thumbHeight = Math.max(12, trackHeight / Math.max(2, messageCount));
         int maxScroll = this.maxChatScroll();
         int available = Math.max(1, trackHeight - thumbHeight);
         int thumbY = trackTop + Math.round(available * (maxScroll == 0 ? 1.0F : 1.0F - (float)this.chatScroll / maxScroll));
         graphics.fill(trackX, thumbY, trackX + 2, thumbY + thumbHeight, -2008402138);
      }
   }

   private void renderPlayerHead(GuiGraphics graphics, UUID id, String name, int x, int y, int size) {
      ResourceLocation skin = Minecraft.getInstance().getSkinManager().getInsecureSkin(new GameProfile(id, name)).texture();
      graphics.blit(skin, x, y, size, size, 8.0F, 8.0F, 8, 8, 64, 64);
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.isMouseOver(mouseX, mouseY, GUILD_BUTTON)) {
         if (this.minecraft != null) {
            this.minecraft.setScreen(new AdventurerGuildScreen());
            ScreenHelper.clicked();
         }

         return true;
      } else if (button == 0 && this.isMouseOver(mouseX, mouseY, TASK_BUTTON)) {
         if (this.minecraft != null) {
            this.minecraft.setScreen(new GuildTaskScreen());
            ScreenHelper.clicked();
         }

         return true;
      } else {
         if (button == 0 && this.state.inParty()) {
            if (this.isMouseOver(mouseX, mouseY, LEAVE_BUTTON)) {
               PacketDistributor.sendToServer(GuildPartyActionPayload.leave(), new CustomPacketPayload[0]);
               ScreenHelper.clicked();
               return true;
            }

            if (this.isLeader()) {
               for (int i = 0; i < KICK_BUTTONS.length; i++) {
                  if (this.isMouseOver(mouseX, mouseY, KICK_BUTTONS[i])) {
                     int memberIndex = i + 1;
                     if (memberIndex < this.state.members().size()) {
                        PacketDistributor.sendToServer(GuildPartyActionPayload.kick(this.state.members().get(memberIndex).id()), new CustomPacketPayload[0]);
                        ScreenHelper.clicked();
                        return true;
                     }
                  }
               }
            }
         } else if (button == 0 && !this.awaitingSync) {
            if (this.isMouseOver(mouseX, mouseY, CREATE_BUTTON)) {
               String partyName = this.partyNameBox.getValue().trim();
               if (!partyName.isBlank()) {
                  PacketDistributor.sendToServer(GuildPartyActionPayload.create(partyName), new CustomPacketPayload[0]);
                  ScreenHelper.clicked();
               }

               return true;
            }

            if (this.isMouseOver(mouseX, mouseY, JOIN_BUTTON)) {
               PacketDistributor.sendToServer(GuildPartyActionPayload.join(this.partyNameBox.getValue()), new CustomPacketPayload[0]);
               ScreenHelper.clicked();
               return true;
            }
         }

         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
      if (this.state.inParty() && this.isMouseOver(mouseX, mouseY, CHAT_AREA)) {
         int delta = deltaY > 0.0 ? 1 : -1;
         this.chatScroll = Mth.clamp(this.chatScroll + delta, 0, this.maxChatScroll());
         return true;
      } else {
         return super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 257 && this.state.inParty() && this.chatBox != null && this.chatBox.isFocused()) {
         String value = this.chatBox.getValue().trim();
         if (!value.isBlank()) {
            PacketDistributor.sendToServer(new GuildPartyChatPayload(value), new CustomPacketPayload[0]);
            this.chatBox.setValue("");
         }

         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
   }

   protected void renderBlurredBackground(float partialTick) {
   }

   public boolean isPauseScreen() {
      return false;
   }

   private boolean isLeader() {
      return this.minecraft != null && this.minecraft.player != null && this.state.leaderId().equals(this.minecraft.player.getUUID());
   }

   private int maxChatScroll() {
      return Math.max(0, this.state.messages().size() - 1);
   }

   private void drawCenteredText(GuiGraphics graphics, Component text, GuildPartyScreen.Rect area, int color) {
      int textWidth = this.font.width(text);
      int x = this.leftPos + area.left() + (area.width() - textWidth) / 2;
      int y = this.topPos + area.top() + (area.height() - 9) / 2;
      graphics.drawString(this.font, text, x, y, color, false);
   }

   private void drawScaledText(GuiGraphics graphics, Component text, int x, int y, float scale, int color) {
      graphics.pose().pushPose();

      try {
         graphics.pose().translate(x, y, 0.0F);
         graphics.pose().scale(scale, scale, 1.0F);
         graphics.drawString(this.font, text, 0, 0, color, false);
      } finally {
         graphics.pose().popPose();
      }
   }

   private Component shorten(Component text, int maxWidth) {
      if (this.font.width(text) <= maxWidth) {
         return text;
      }

      String value = text.getString();
      String suffix = "...";
      int suffixWidth = this.font.width(suffix);
      int stopAt = 0;
      int i = 1;

      while (i <= value.length() && this.font.width(value.substring(0, i)) + suffixWidth <= maxWidth) {
         stopAt = i++;
      }

      return Component.literal(value.substring(0, stopAt) + suffix).setStyle(text.getStyle());
   }

   private boolean isMouseOver(double mouseX, double mouseY, GuildPartyScreen.Rect rect) {
      return mouseX >= this.leftPos + rect.left()
         && mouseX <= this.leftPos + rect.right()
         && mouseY >= this.topPos + rect.top()
         && mouseY <= this.topPos + rect.bottom();
   }

   private record Rect(int left, int top, int right, int bottom) {
      private int width() {
         return this.right - this.left + 1;
      }

      private int height() {
         return this.bottom - this.top + 1;
      }
   }
}
