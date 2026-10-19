package com.tensura_guild.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.tensura_guild.client.GuildPartyClient;
import com.tensura_guild.data.GuildLevelData;
import com.tensura_guild.data.GuildMemberData;
import com.tensura_guild.data.GuildSignatureData;
import com.tensura_guild.data.GuildTitleFormatter;
import com.tensura_guild.registry.GuildAttachments;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.client.screen.MainScreen;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.entity.HumanEntityTypes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.client.RenderHelper;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.joml.Matrix4f;

public class AdventurerGuildScreen extends Screen {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/adventurer_guild_gui.png");
   private static final ResourceLocation TASK_SYSTEM_TAB_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/task_system_tab.png");
   private static final ResourceLocation PARTY_TAB_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/party_tab.png");
   private static final int TEXTURE_SIZE = 256;
   private static final int TASK_SYSTEM_TAB_TEXTURE_WIDTH = 48;
   private static final int TASK_SYSTEM_TAB_TEXTURE_HEIGHT = 21;
   private static final int TASK_SYSTEM_TAB_WIDTH = 24;
   private static final int TASK_SYSTEM_TAB_HEIGHT = 21;
   private static final int IMAGE_WIDTH = 256;
   private static final int IMAGE_HEIGHT = 163;
   private static final AdventurerGuildScreen.Rect PLAYER_MODEL = new AdventurerGuildScreen.Rect(7, 57, 62, 136);
   private static final AdventurerGuildScreen.Rect BACK_BUTTON = new AdventurerGuildScreen.Rect(9, 33, 27, 43);
   private static final AdventurerGuildScreen.Rect BACK_BUTTON_HOVER_SRC = new AdventurerGuildScreen.Rect(2, 165, 20, 175);
   private static final AdventurerGuildScreen.Rect TASK_BUTTON = new AdventurerGuildScreen.Rect(33, 2, 57, 23);
   private static final AdventurerGuildScreen.Rect PARTY_BUTTON = new AdventurerGuildScreen.Rect(59, 2, 83, 23);
   private static final AdventurerGuildScreen.Rect GUILD_NAME = new AdventurerGuildScreen.Rect(168, 29, 232, 45);
   private static final AdventurerGuildScreen.Rect GUILD_LEVEL_BAR = new AdventurerGuildScreen.Rect(77, 147, 230, 152);
   private static final AdventurerGuildScreen.Rect GUILD_LEVEL_BAR_FULL = new AdventurerGuildScreen.Rect(77, 166, 230, 171);
   private static final AdventurerGuildScreen.Rect GUILD_LEVEL_TEXT = new AdventurerGuildScreen.Rect(74, 156, 232, 163);
   private static final AdventurerGuildScreen.Rect PLAYER_SIGNATURE = new AdventurerGuildScreen.Rect(9, 142, 62, 153);
   private static final AdventurerGuildScreen.Rect INFO_PANEL = new AdventurerGuildScreen.Rect(73, 33, 232, 143);
   private static final int PANEL_PADDING_X = 4;
   private static final int PANEL_PADDING_TOP = 15;
   private static final int PANEL_COLUMN_GAP = 6;
   private static final int PANEL_SECTION_GAP = 4;
   private static final int PANEL_TITLE_LINE_HEIGHT = 11;
   private static final int PANEL_BODY_LINE_HEIGHT = 10;
   private static final int BASIC_INFO_Y_OFFSET = -10;
   private static final int BASIC_INFO_ORIGINAL_BODY_LINES = 3;
   private static final int PANEL_LABEL_VALUE_GAP = 0;
   private static final int PANEL_MIN_VALUE_WIDTH = 18;
   private static final float PANEL_TITLE_SCALE = 1.08F;
   private static final float PANEL_VALUE_MIN_SCALE = 0.35F;
   private static final float PANEL_NAME_TITLE_MIN_SCALE = 0.2F;
   private static final long COMPACT_DISPLAY_LIMIT = 2000L;
   private static final String OVER_COMPACT_DISPLAY_LIMIT = "＞2000";
   private int leftPos;
   private int topPos;
   private AdventurerGuildScreen.ClickableArea titleLabelClickArea = AdventurerGuildScreen.ClickableArea.EMPTY;

   public AdventurerGuildScreen() {
      super(Component.literal("Adventurer Guild"));
   }

   protected void init() {
      this.leftPos = (this.width - 256) / 2;
      this.topPos = (this.height - 163) / 2;
      GuildPartyClient.requestSync();
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0.0F, 0.0F, 256, 163, 256, 256);
      this.renderPlayerModel(graphics, mouseX, mouseY);
      this.renderGuildName(graphics);
      this.renderGuildInfoPanel(graphics);
      this.renderGuildLevel(graphics);
      this.renderBackButton(graphics, mouseX, mouseY);
      this.renderTaskButton(graphics, mouseX, mouseY);
      this.renderPartyButton(graphics, mouseX, mouseY);
      this.renderPlayerSignature(graphics);
   }

   private void renderPlayerModel(GuiGraphics graphics, int mouseX, int mouseY) {
      if (this.minecraft != null && this.minecraft.player != null) {
         float sizeMultiplier = this.minecraft.player.getScale();
         sizeMultiplier = sizeMultiplier > 1.0F ? 1.0F / sizeMultiplier : 1.0F;
         RenderHelper.renderEntityInInventoryFollowsMouse(
            graphics,
            this.leftPos + PLAYER_MODEL.left(),
            this.topPos + PLAYER_MODEL.top(),
            this.leftPos + PLAYER_MODEL.right(),
            this.topPos + PLAYER_MODEL.bottom(),
            30.0F * sizeMultiplier,
            mouseX,
            mouseY,
            true,
            this.minecraft.player
         );
      }
   }

   private void renderGuildName(GuiGraphics graphics) {
      Component guildName = this.getGuildName();
      Component text = Component.translatable("screen.tensura_guild.guild_membership", new Object[]{guildName});
      this.drawCenteredScaledText(graphics, text, GUILD_NAME, -16777216);
   }

   private void renderBackButton(GuiGraphics graphics, int mouseX, int mouseY) {
      if (this.isMouseOver(mouseX, mouseY, this.leftPos + BACK_BUTTON.left(), this.topPos + BACK_BUTTON.top(), BACK_BUTTON.width(), BACK_BUTTON.height())) {
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

   private void renderTaskButton(GuiGraphics graphics, int mouseX, int mouseY) {
      boolean hovered = this.isMouseOver(
         mouseX, mouseY, this.leftPos + TASK_BUTTON.left(), this.topPos + TASK_BUTTON.top(), TASK_BUTTON.width(), TASK_BUTTON.height()
      );
      int uOffset = hovered ? 24 : 0;
      graphics.blit(TASK_SYSTEM_TAB_TEXTURE, this.leftPos + TASK_BUTTON.left(), this.topPos + TASK_BUTTON.top(), uOffset, 0.0F, 24, 21, 48, 21);
   }

   private void renderPartyButton(GuiGraphics graphics, int mouseX, int mouseY) {
      boolean hovered = this.isMouseOver(
         mouseX, mouseY, this.leftPos + PARTY_BUTTON.left(), this.topPos + PARTY_BUTTON.top(), PARTY_BUTTON.width(), PARTY_BUTTON.height()
      );
      int uOffset = hovered ? 24 : 0;
      graphics.blit(PARTY_TAB_TEXTURE, this.leftPos + PARTY_BUTTON.left(), this.topPos + PARTY_BUTTON.top(), uOffset, 0.0F, 24, 21, 48, 21);
   }

   private void renderGuildInfoPanel(GuiGraphics graphics) {
      if (this.minecraft != null && this.minecraft.player != null) {
         GuildMemberData memberData = (GuildMemberData)this.minecraft.player.getData(GuildAttachments.GUILD_MEMBER);
         GuildLevelData levelData = (GuildLevelData)this.minecraft.player.getData(GuildAttachments.GUILD_LEVEL);
         int contentLeft = this.leftPos + INFO_PANEL.left() + 4;
         int contentTop = this.topPos + INFO_PANEL.top() + 15;
         int contentWidth = INFO_PANEL.width() - 8;
         int columnWidth = (contentWidth - 6) / 2;
         int leftColumnX = contentLeft;
         int rightColumnX = contentLeft + columnWidth + 6;
         int basicInfoY = contentTop + -10;
         int abilityInfoY = contentTop + 11 + 30 + 4;
         int currentRightY = contentTop;
         List<AdventurerGuildScreen.InfoEntry> basicInfo = List.of(
            this.infoLine("screen.tensura_guild.info.name_title", this.buildPlayerName(), 0.2F),
            this.infoLine(
               "screen.tensura_guild.info.title",
               memberData.title()
                  .map(GuildTitleFormatter::format)
                  .map(Component.class::cast)
                  .orElseGet(() -> Component.translatable("screen.tensura_guild.none")),
               0.2F
            ),
            this.infoLine("screen.tensura_guild.info.member_number", Component.literal(memberData.formattedMemberNumber())),
            this.infoLine("screen.tensura_guild.info.squad", Component.literal(this.currentSquadName(memberData)))
         );
         List<AdventurerGuildScreen.InfoEntry> abilityInfo = List.of(
            this.infoLine("screen.tensura_guild.info.level", Component.literal(this.levelSummary(levelData))),
            this.infoLine(
               "screen.tensura_guild.info.max_health", Component.literal(this.formatNumber(this.minecraft.player.getAttributeValue(Attributes.MAX_HEALTH)))
            ),
            this.infoLine("screen.tensura_guild.info.max_existence", Component.literal(this.formatNumber(EnergyHelper.getMaxEP(this.minecraft.player)))),
            this.infoLine(
               "screen.tensura_guild.info.max_spiritual_health",
               Component.literal(this.formatNumber(this.minecraft.player.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH)))
            )
         );
         List<AdventurerGuildScreen.InfoEntry> taskRecord = List.of(
            this.infoLine("screen.tensura_guild.info.completed_tasks", Component.literal(String.valueOf(memberData.completedTasks()))),
            this.infoLine("screen.tensura_guild.info.active_tasks", Component.literal(String.valueOf(memberData.activeTasks()))),
            this.infoLine("screen.tensura_guild.info.success_rate", Component.literal(memberData.successRateText())),
            this.infoLine("screen.tensura_guild.info.failure_record", Component.literal(String.valueOf(memberData.failureCount())))
         );
         List<AdventurerGuildScreen.InfoEntry> profileInfo = List.of(
            this.infoLine("screen.tensura_guild.info.current_race", this.currentRaceName()),
            this.infoLine("screen.tensura_guild.info.origin", Component.literal(this.originText(memberData))),
            this.infoLine("screen.tensura_guild.info.dwarf_reputation", Component.literal(this.formatReputation(this.dwarfReputation())))
         );
         this.updateTitleLabelClickArea(leftColumnX, basicInfoY + 11 + 10, columnWidth);
         this.drawSection(graphics, leftColumnX, basicInfoY, columnWidth, Component.translatable("screen.tensura_guild.section.basic_info"), basicInfo);
         this.drawSection(graphics, leftColumnX, abilityInfoY, columnWidth, Component.translatable("screen.tensura_guild.section.ability_info"), abilityInfo);
         currentRightY = this.drawSection(
            graphics, rightColumnX, currentRightY, columnWidth, Component.translatable("screen.tensura_guild.section.task_record"), taskRecord
         );
         this.drawSection(graphics, rightColumnX, currentRightY + 4, columnWidth, Component.translatable("screen.tensura_guild.section.profile"), profileInfo);
      } else {
         this.titleLabelClickArea = AdventurerGuildScreen.ClickableArea.EMPTY;
      }
   }

   private int drawSection(GuiGraphics graphics, int x, int y, int width, Component title, List<AdventurerGuildScreen.InfoEntry> lines) {
      int drawY = y;
      this.drawSectionTitle(graphics, title.copy().withStyle(ChatFormatting.DARK_GRAY), x, drawY, -14540254);
      drawY += 11;

      for (AdventurerGuildScreen.InfoEntry line : lines) {
         this.drawBodyLine(graphics, line, x, drawY, width, -14671840);
         drawY += 10;
      }

      return drawY;
   }

   private void updateTitleLabelClickArea(int x, int y, int width) {
      Component label = this.localizedInfoLabel("screen.tensura_guild.info.title");
      int maxLabelWidth = Math.max(24, width - 18 - 0);
      int labelWidth = Math.min(this.font.width(label), maxLabelWidth);
      this.titleLabelClickArea = new AdventurerGuildScreen.ClickableArea(x, y, labelWidth, 10);
   }

   private AdventurerGuildScreen.InfoEntry infoLine(String key, Component value) {
      return this.infoLine(key, value, 0.35F);
   }

   private AdventurerGuildScreen.InfoEntry infoLine(String key, Component value, float valueMinScale) {
      return new AdventurerGuildScreen.InfoEntry(this.localizedInfoLabel(key), value, valueMinScale);
   }

   private Component buildPlayerName() {
      return this.minecraft != null && this.minecraft.player != null
         ? this.minecraft.player.getName().copy()
         : Component.translatable("screen.tensura_guild.none");
   }

   private Component currentRaceName() {
      if (this.minecraft != null && this.minecraft.player != null) {
         Optional<ManasRaceInstance> race = RaceAPI.getRaceFrom(this.minecraft.player).getRace();
         return (Component)race.<MutableComponent>map(ManasRaceInstance::getDisplayName).orElseGet(() -> Component.translatable("screen.tensura_guild.none"));
      } else {
         return Component.translatable("screen.tensura_guild.none");
      }
   }

   private String currentSquadName(GuildMemberData memberData) {
      return GuildPartyClient.currentPartyName()
         .orElseGet(() -> memberData.squad().orElseGet(() -> Component.translatable("screen.tensura_guild.none").getString()));
   }

   private String originText(GuildMemberData memberData) {
      return this.minecraft != null && this.minecraft.player != null
         ? memberData.originBiome()
            .<ResourceLocation>map(ResourceLocation::parse)
            .map(id -> AdventurerGuildScreen.BiomeLabelHelper.biomeName(this.minecraft, id))
            .orElse(Component.translatable("screen.tensura_guild.none").getString())
         : Component.translatable("screen.tensura_guild.none").getString();
   }

   private double dwarfReputation() {
      return this.minecraft != null && this.minecraft.player != null
         ? TensuraStorages.getPlayerDataFrom(this.minecraft.player).getReputation((EntityType)HumanEntityTypes.DWARF.get())
         : 0.0;
   }

   private void renderGuildLevel(GuiGraphics graphics) {
      if (this.minecraft != null && this.minecraft.player != null) {
         GuildLevelData levelData = (GuildLevelData)this.minecraft.player.getData(GuildAttachments.GUILD_LEVEL);
         int barX = this.leftPos + GUILD_LEVEL_BAR.left();
         int barY = this.topPos + GUILD_LEVEL_BAR.top();
         int barWidth = GUILD_LEVEL_BAR.width();
         int barHeight = GUILD_LEVEL_BAR.height();
         graphics.blit(TEXTURE, barX, barY, GUILD_LEVEL_BAR.left(), GUILD_LEVEL_BAR.top(), barWidth, barHeight, 256, 256);
         int fillWidth = Math.max(0, Math.min(barWidth, Math.round(barWidth * levelData.progress())));
         if (fillWidth > 0) {
            graphics.blit(
               TEXTURE,
               barX,
               barY,
               fillWidth,
               barHeight,
               GUILD_LEVEL_BAR_FULL.left(),
               GUILD_LEVEL_BAR_FULL.top(),
               fillWidth,
               GUILD_LEVEL_BAR_FULL.height(),
               256,
               256
            );
         }
      }
   }

   private void renderPlayerSignature(GuiGraphics graphics) {
      if (this.minecraft != null && this.minecraft.player != null) {
         GuildSignatureData signature = (GuildSignatureData)this.minecraft.player.getData(GuildAttachments.GUILD_SIGNATURE);
         if (signature.hasSignature()) {
            AdventurerGuildScreen.SignatureBounds bounds = AdventurerGuildScreen.SignatureBounds.create(signature.points());
            if (bounds != null) {
               int dstLeft = this.leftPos + PLAYER_SIGNATURE.left();
               int dstTop = this.topPos + PLAYER_SIGNATURE.top();
               int dstWidth = PLAYER_SIGNATURE.width();
               int dstHeight = PLAYER_SIGNATURE.height();
               float fitScale = Math.min(dstWidth / Math.max(1.0F, bounds.width()), dstHeight / Math.max(1.0F, bounds.height()));
               float scale = Math.min(1.0F, fitScale);
               float drawWidth = bounds.width() * scale;
               float drawHeight = bounds.height() * scale;
               float offsetX = dstLeft + (dstWidth - drawWidth) / 2.0F;
               float offsetY = dstTop + (dstHeight - drawHeight) / 2.0F;
               List<Integer> points = signature.points();
               Integer previousX = null;
               Integer previousY = null;

               for (int i = 0; i + 1 < points.size(); i += 2) {
                  int x = points.get(i);
                  int y = points.get(i + 1);
                  if (GuildSignatureData.isSeparator(x, y)) {
                     previousX = null;
                     previousY = null;
                  } else {
                     int drawX = clamp(Math.round(offsetX + (x - bounds.minX()) * scale), dstLeft, dstLeft + dstWidth - 1);
                     int drawY = clamp(Math.round(offsetY + (y - bounds.minY()) * scale), dstTop, dstTop + dstHeight - 1);
                     if (previousX != null && previousY != null) {
                        this.drawLine(graphics, previousX, previousY, drawX, drawY, -1);
                     }

                     previousX = drawX;
                     previousY = drawY;
                  }
               }
            }
         }
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.titleLabelClickArea.contains(mouseX, mouseY)) {
         if (this.minecraft != null) {
            this.minecraft.setScreen(new GuildTitleSettingsScreen(this));
            ScreenHelper.clicked();
         }

         return true;
      } else if (button == 0
         && this.isMouseOver(mouseX, mouseY, this.leftPos + TASK_BUTTON.left(), this.topPos + TASK_BUTTON.top(), TASK_BUTTON.width(), TASK_BUTTON.height())) {
         if (this.minecraft != null) {
            this.minecraft.setScreen(new GuildTaskScreen());
            ScreenHelper.clicked();
         }

         return true;
      } else if (button == 0
         && this.isMouseOver(mouseX, mouseY, this.leftPos + PARTY_BUTTON.left(), this.topPos + PARTY_BUTTON.top(), PARTY_BUTTON.width(), PARTY_BUTTON.height())
         )
       {
         if (this.minecraft != null) {
            this.minecraft.setScreen(new GuildPartyScreen());
            ScreenHelper.clicked();
         }

         return true;
      } else if (button == 0
         && this.isMouseOver(mouseX, mouseY, this.leftPos + BACK_BUTTON.left(), this.topPos + BACK_BUTTON.top(), BACK_BUTTON.width(), BACK_BUTTON.height())) {
         if (this.minecraft != null) {
            this.minecraft.setScreen(new MainScreen());
            ScreenHelper.clicked();
         }

         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.minecraft != null && keyCode == 66) {
         this.minecraft.setScreen(null);
         this.minecraft.mouseHandler.grabMouse();
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

   private void drawSectionTitle(GuiGraphics graphics, Component text, int x, int y, int color) {
      graphics.pose().pushPose();

      try {
         graphics.pose().translate(x, y, 0.0F);
         graphics.pose().scale(1.08F, 1.08F, 1.0F);
         graphics.drawString(this.font, text, 0, 0, color, false);
      } finally {
         graphics.pose().popPose();
      }
   }

   private void drawBodyLine(GuiGraphics graphics, AdventurerGuildScreen.InfoEntry line, int x, int y, int width, int color) {
      int maxLabelWidth = Math.max(24, width - 18 - 0);
      int labelWidth = Math.min(this.font.width(line.label()), maxLabelWidth);
      int valueX = x + labelWidth + 0;
      int valueWidth = Math.max(18, width - labelWidth - 0);
      graphics.drawString(this.font, this.shortenText(line.label(), labelWidth), x, y, color, false);
      this.drawFittedValueText(graphics, line.value(), valueX, y, valueWidth, color, line.valueMinScale());
   }

   private void drawLine(GuiGraphics graphics, int x1, int y1, int x2, int y2, int color) {
      int dx = Math.abs(x2 - x1);
      int dy = Math.abs(y2 - y1);
      int steps = Math.max(dx, dy);
      if (steps == 0) {
         this.fillPixel(graphics, x1, y1, color);
      } else {
         for (int i = 0; i <= steps; i++) {
            float progress = (float)i / steps;
            int x = Math.round(x1 + (x2 - x1) * progress);
            int y = Math.round(y1 + (y2 - y1) * progress);
            this.fillPixel(graphics, x, y, color);
         }
      }
   }

   private void fillPixel(GuiGraphics graphics, int x, int y, int color) {
      Matrix4f matrix = graphics.pose().last().pose();
      float alpha = (color >>> 24) / 255.0F;
      float red = (color >> 16 & 0xFF) / 255.0F;
      float green = (color >> 8 & 0xFF) / 255.0F;
      float blue = (color & 0xFF) / 255.0F;
      Tesselator tesselator = RenderSystem.renderThreadTesselator();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      BufferBuilder builder = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      builder.addVertex(matrix, x, y + 1, 0.0F).setColor(red, green, blue, alpha);
      builder.addVertex(matrix, x + 1, y + 1, 0.0F).setColor(red, green, blue, alpha);
      builder.addVertex(matrix, x + 1, y, 0.0F).setColor(red, green, blue, alpha);
      builder.addVertex(matrix, x, y, 0.0F).setColor(red, green, blue, alpha);
      BufferUploader.drawWithShader(builder.build());
      RenderSystem.disableBlend();
   }

   private boolean isMouseOver(double mouseX, double mouseY, int x, int y, int width, int height) {
      return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
   }

   private Component getGuildName() {
      return Component.translatable("screen.tensura_guild.guild.free_guild");
   }

   private void drawCenteredScaledText(GuiGraphics graphics, Component text, AdventurerGuildScreen.Rect area, int color) {
      int areaLeft = this.leftPos + area.left();
      int areaTop = this.topPos + area.top();
      int textWidth = this.font.width(text);
      if (textWidth > 0) {
         float scale = Math.min(1.0F, Math.min((float)area.width() / textWidth, area.height() / 9.0F));
         float drawX = areaLeft + (area.width() - textWidth * scale) / 2.0F;
         float drawY = areaTop + (area.height() - 9.0F * scale) / 2.0F;
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

   private Component shortenText(Component text, int maxWidth) {
      if (this.font.width(text) <= maxWidth) {
         return text;
      }

      String string = text.getString();
      int stopAt = 0;

      for (int i = 1; i <= string.length(); stopAt = i++) {
         String substr = string.substring(0, i);
         if (this.font.width(substr) > maxWidth) {
            break;
         }
      }

      return Component.literal(string.substring(0, stopAt)).setStyle(text.getStyle());
   }

   private void drawFittedValueText(GuiGraphics graphics, Component text, int x, int y, int width, int color, float minScale) {
      int textWidth = this.font.width(text);
      if (textWidth <= width) {
         graphics.drawString(this.font, text, x, y, color, false);
      } else {
         float scale = (float)width / textWidth;
         if (scale >= minScale) {
            this.drawScaledTextNoShadow(graphics, text, x, y, scale, color);
         } else {
            this.drawScrollingTextNoShadow(graphics, text, x, y, width, color);
         }
      }
   }

   private void drawScaledTextNoShadow(GuiGraphics graphics, Component text, int x, int y, float scale, int color) {
      float drawY = y + (10.0F - 9.0F * scale) / 2.0F;
      graphics.pose().pushPose();

      try {
         graphics.pose().translate(x, drawY, 0.0F);
         graphics.pose().scale(scale, scale, 1.0F);
         graphics.drawString(this.font, text, 0, 0, color, false);
      } finally {
         graphics.pose().popPose();
      }
   }

   private void drawScrollingTextNoShadow(GuiGraphics graphics, Component text, int x, int y, int width, int color) {
      int textWidth = this.font.width(text);
      if (textWidth <= width) {
         graphics.drawString(this.font, text, x, y, color, false);
      } else {
         int scrollDistance = textWidth - width;
         double timeSeconds = Util.getMillis() / 1000.0;
         double scrollPeriod = Math.max(scrollDistance * 0.5, 3.0);
         double scrollPhase = Math.cos(timeSeconds / scrollPeriod * 2.0 * Math.PI);
         double scrollFactor = (Math.sin(scrollPhase * (Math.PI / 2)) + 1.0) / 2.0;
         int offset = (int)Math.round(scrollFactor * scrollDistance);
         graphics.enableScissor(x, y, x + width, y + 9);

         try {
            graphics.drawString(this.font, text, x - offset, y, color, false);
         } finally {
            graphics.disableScissor();
         }
      }
   }

   private String formatNumber(double value) {
      long rounded = Math.round(value);
      if (rounded >= 1000000000L) {
         return this.formatCompactNumber(rounded, 1.0E9, "b");
      } else if (rounded >= 1000000L) {
         return this.formatCompactNumber(rounded, 1000000.0, "m");
      } else {
         return rounded >= 1000L ? this.formatCompactNumber(rounded, 1000.0, "k") : String.valueOf(rounded);
      }
   }

   private String formatReputation(double value) {
      DecimalFormat format = new DecimalFormat(value == Math.rint(value) ? "0" : "0.#", DecimalFormatSymbols.getInstance(Locale.ROOT));
      return format.format(value);
   }

   private String formatCompactNumber(long value, double divisor, String suffix) {
      double scaled = value / divisor;
      double floored = Math.floor(scaled * 10.0) / 10.0;
      DecimalFormat format = new DecimalFormat(
         !(floored >= 10.0) && floored != Math.floor(floored) ? "0.#" : "0", DecimalFormatSymbols.getInstance(Locale.ROOT)
      );
      return format.format(floored) + suffix;
   }

   private Component localizedInfoLabel(String key) {
      return (Component)(switch (key) {
         case "screen.tensura_guild.info.max_health" -> this.preferredLabel(key, "最大生命值：", "Max Health: ");
         case "screen.tensura_guild.info.max_existence" -> this.preferredLabel(key, "最大存在值：", "Max Existence: ");
         case "screen.tensura_guild.info.max_spiritual_health" -> this.preferredLabel(key, "最大精神值：", "Max Spiritual: ");
         case "screen.tensura_guild.info.title" -> this.preferredLabel(key, "称号：", "Title: ");
         case "screen.tensura_guild.info.active_tasks" -> this.preferredLabel(key, "接取任务：", "Active: ");
         case "screen.tensura_guild.info.current_race" -> this.preferredLabel(key, "种族：", "Race: ");
         default -> Component.translatable(key);
      });
   }

   private Component preferredLabel(String key, String zhCn, String fallback) {
      String language = this.minecraft != null && this.minecraft.getLanguageManager() != null
         ? this.minecraft.getLanguageManager().getSelected().toLowerCase(Locale.ROOT)
         : "";
      if ("zh_cn".equals(language)) {
         return Component.literal(zhCn);
      } else {
         return I18n.exists(key) ? Component.translatable(key) : Component.literal(fallback);
      }
   }

   private String levelSummary(GuildLevelData levelData) {
      int totalExperience = levelData.totalExperience();
      int rankMaxExperience = levelData.displayMaxExperience();
      return totalExperience <= 2000L && rankMaxExperience <= 2000L
         ? levelData.rank() + " " + totalExperience + "/" + rankMaxExperience
         : levelData.rank() + " ＞2000";
   }

   private static int clamp(int value, int min, int max) {
      return Math.max(min, Math.min(max, value));
   }

   private static final class BiomeLabelHelper {
      private static String biomeName(Minecraft minecraft, ResourceLocation biomeId) {
         String key = "biome." + biomeId.getNamespace() + "." + biomeId.getPath().replace('/', '.');
         return I18n.exists(key) ? I18n.get(key, new Object[0]) : biomeId.getPath();
      }
   }

   private record ClickableArea(int x, int y, int width, int height) {
      private static final AdventurerGuildScreen.ClickableArea EMPTY = new AdventurerGuildScreen.ClickableArea(0, 0, 0, 0);

      private boolean contains(double mouseX, double mouseY) {
         return this.width > 0 && this.height > 0 && mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
      }
   }

   private record InfoEntry(Component label, Component value, float valueMinScale) {
   }

   private record Rect(int left, int top, int right, int bottom) {
      private int width() {
         return this.right - this.left + 1;
      }

      private int height() {
         return this.bottom - this.top + 1;
      }
   }

   private record SignatureBounds(int minX, int minY, int maxX, int maxY) {
      private static AdventurerGuildScreen.SignatureBounds create(List<Integer> points) {
         Integer minX = null;
         Integer minY = null;
         int maxX = 0;
         int maxY = 0;

         for (int i = 0; i + 1 < points.size(); i += 2) {
            int x = points.get(i);
            int y = points.get(i + 1);
            if (!GuildSignatureData.isSeparator(x, y)) {
               minX = minX == null ? x : Math.min(minX, x);
               minY = minY == null ? y : Math.min(minY, y);
               maxX = Math.max(maxX, x);
               maxY = Math.max(maxY, y);
            }
         }

         return minX != null && minY != null ? new AdventurerGuildScreen.SignatureBounds(minX, minY, maxX, maxY) : null;
      }

      private int width() {
         return this.maxX - this.minX + 1;
      }

      private int height() {
         return this.maxY - this.minY + 1;
      }
   }
}
