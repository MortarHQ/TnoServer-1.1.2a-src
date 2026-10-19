package com.tensura_guild.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.tensura_guild.data.GuildTaskTime;
import com.tensura_guild.network.CloseDialoguePayload;
import com.tensura_guild.network.DwarfTaskActionPayload;
import com.tensura_guild.network.OpenDwarfTaskDialoguePayload;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Matrix4f;

public class DwarfTaskDialogueScreen extends Screen {
   private static final int CHARS_PER_TICK = 1;
   private static final int PANEL_COLOR = 1712723482;
   private static final int BORDER_COLOR = -2144193992;
   private static final int NAME_COLOR = -2052029;
   private static final int TEXT_COLOR = -1513240;
   private static final int HINT_COLOR = -7697782;
   private static final int LETTERBOX_COLOR = -16777216;
   private static final int LETTERBOX_ANIMATION_TICKS = 12;
   private static final int OPTION_PANEL_COLOR = -1441393126;
   private static final int OPTION_HOVER_COLOR = 1714565688;
   private static final int OPTION_WIDTH = 112;
   private static final int OPTION_HEIGHT = 18;
   private static final int OPTION_GAP = 5;
   private final OpenDwarfTaskDialoguePayload payload;
   private int revealed = 0;
   private int screenTicks = 0;
   private boolean capturedHideGui = false;
   private boolean previousHideGui = false;

   public DwarfTaskDialogueScreen(OpenDwarfTaskDialoguePayload payload) {
      super(Component.literal(payload.speakerName()));
      this.payload = payload;
   }

   protected void init() {
      super.init();
      this.applyCinematicHideGui();
   }

   public void tick() {
      super.tick();
      this.screenTicks++;
      String text = this.dialogueText().getString();
      if (this.revealed < text.length()) {
         this.revealed = Math.min(text.length(), this.revealed + 1);
      }
   }

   public void removed() {
      PacketDistributor.sendToServer(new CloseDialoguePayload(this.payload.entityId()), new CustomPacketPayload[0]);
      this.restoreHideGui();
      super.removed();
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button != 0) {
         return super.mouseClicked(mouseX, mouseY, button);
      } else if (!this.isLineFullyRevealed()) {
         this.revealed = this.dialogueText().getString().length();
         return true;
      } else {
         return this.handleOptionClick(mouseX, mouseY) ? true : true;
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if ((keyCode == 32 || keyCode == 257 || keyCode == 335) && !this.isLineFullyRevealed()) {
         this.revealed = this.dialogueText().getString().length();
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      this.renderLetterbox(graphics);
      Font font = this.font;
      int panelW = Math.min(340, this.width - 32);
      int panelH = 90;
      int panelX = (this.width - panelW) / 2;
      int panelY = this.height - panelH - 18;
      int textX = panelX + 12;
      int textW = panelW - 24;
      int textY = panelY + 10;
      fillTransparent(graphics, panelX - 1, panelY - 1, panelX + panelW + 1, panelY + panelH + 1, -2144193992);
      fillTransparent(graphics, panelX, panelY, panelX + panelW, panelY + panelH, 1712723482);
      graphics.drawString(font, Component.literal(this.payload.speakerName()), textX, textY, -2052029, false);
      String text = this.dialogueText().getString();
      String shown = text.substring(0, Math.min(this.revealed, text.length()));
      List<FormattedCharSequence> wrapped = font.split(Component.literal(shown), textW);
      int ty = textY + 9 + 4;

      for (FormattedCharSequence seq : wrapped) {
         graphics.drawString(font, seq, textX, ty, -1513240, false);
         ty += 9 + 1;
      }

      if (this.isLineFullyRevealed()) {
         this.renderOptions(graphics, mouseX, mouseY);
      } else {
         Component hint = Component.translatable("screen.tensura_guild.dialogue.skip");
         graphics.drawString(font, hint, panelX + panelW - font.width(hint) - 8, panelY + panelH - 9 - 6, -7697782, false);
      }
   }

   private Component dialogueText() {
      String remaining = GuildTaskTime.formatCommissionDayDurationText(this.payload.remainingTicks());

      return switch (this.payload.mode()) {
         case OFFER -> Component.translatable(this.payload.offerKey(), new Object[]{this.payload.requiredCount(), remaining});
         case ACTIVE -> Component.translatable(this.payload.activeKey(), new Object[]{this.payload.heldCount(), this.payload.requiredCount(), remaining});
         case READY -> Component.translatable(this.payload.readyKey(), new Object[]{this.payload.requiredCount(), remaining});
         case GUILD_MAP_REWARD -> Component.translatable(this.payload.offerKey());
      };
   }

   private void renderOptions(GuiGraphics graphics, int mouseX, int mouseY) {
      int panelX = this.optionPanelX();
      int panelY = this.optionPanelY();

      Component primary = switch (this.payload.mode()) {
         case OFFER -> Component.translatable("screen.tensura_guild.dwarf_task.option.accept");
         case ACTIVE -> Component.translatable("screen.tensura_guild.dwarf_task.option.wait");
         case READY -> Component.translatable("screen.tensura_guild.dwarf_task.option.submit");
         case GUILD_MAP_REWARD -> Component.translatable("screen.tensura_guild.dwarf_task.option.claim_map");
      };
      this.renderOption(graphics, mouseX, mouseY, panelX, panelY, primary, this.payload.mode() != OpenDwarfTaskDialoguePayload.Mode.ACTIVE);
      this.renderOption(graphics, mouseX, mouseY, panelX, panelY + 18 + 5, Component.translatable("screen.tensura_guild.dialogue.option.none"), true);
   }

   private void renderOption(GuiGraphics graphics, int mouseX, int mouseY, int x, int y, Component text, boolean enabled) {
      boolean hovered = enabled && this.isMouseOver(mouseX, mouseY, x, y, 112, 18);
      fillTransparent(graphics, x, y, x + 112, y + 18, hovered ? 1714565688 : -1441393126);
      graphics.drawString(this.font, text, x + 7, y + (18 - 9) / 2, enabled ? -1513240 : -7697782, false);
   }

   private boolean handleOptionClick(double mouseX, double mouseY) {
      int panelX = this.optionPanelX();
      int panelY = this.optionPanelY();
      if (this.payload.mode() != OpenDwarfTaskDialoguePayload.Mode.ACTIVE && this.isMouseOver(mouseX, mouseY, panelX, panelY, 112, 18)) {
         DwarfTaskActionPayload.Action action = switch (this.payload.mode()) {
            case OFFER -> DwarfTaskActionPayload.Action.ACCEPT;
            case ACTIVE -> throw new IllegalStateException("Active dwarf task option should be disabled");
            case READY -> DwarfTaskActionPayload.Action.SUBMIT;
            case GUILD_MAP_REWARD -> DwarfTaskActionPayload.Action.CLAIM_GUILD_MAP;
         };
         PacketDistributor.sendToServer(new DwarfTaskActionPayload(action, this.payload.entityId()), new CustomPacketPayload[0]);
         if (this.minecraft != null) {
            this.minecraft.setScreen(null);
         }

         return true;
      } else if (this.isMouseOver(mouseX, mouseY, panelX, panelY + 18 + 5, 112, 18)) {
         if (this.minecraft != null) {
            this.minecraft.setScreen(null);
         }

         return true;
      } else {
         return false;
      }
   }

   private int optionPanelX() {
      return this.width - 112 - 18;
   }

   private int optionPanelY() {
      return (this.height - 41) / 2;
   }

   private boolean isLineFullyRevealed() {
      return this.revealed >= this.dialogueText().getString().length();
   }

   private void renderLetterbox(GuiGraphics graphics) {
      int barHeight = Math.max(28, Math.round(this.height * 0.12F));
      float progress = Mth.clamp((this.screenTicks + this.minecraft.getTimer().getGameTimeDeltaPartialTick(false)) / 12.0F, 0.0F, 1.0F);
      float eased = 1.0F - (1.0F - progress) * (1.0F - progress);
      int animatedHeight = Math.max(1, Math.round(barHeight * eased));
      fillTransparent(graphics, 0, 0, this.width, animatedHeight, -16777216);
      fillTransparent(graphics, 0, this.height - animatedHeight, this.width, this.height, -16777216);
   }

   private void applyCinematicHideGui() {
      if (this.minecraft != null && !this.capturedHideGui) {
         this.previousHideGui = this.minecraft.options.hideGui;
         this.capturedHideGui = true;
         this.minecraft.options.hideGui = true;
      }
   }

   private void restoreHideGui() {
      if (this.minecraft != null && this.capturedHideGui) {
         this.minecraft.options.hideGui = this.previousHideGui;
         this.capturedHideGui = false;
      }
   }

   public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
   }

   protected void renderBlurredBackground(float partialTick) {
   }

   private static void fillTransparent(GuiGraphics graphics, int minX, int minY, int maxX, int maxY, int color) {
      float alpha = (color >>> 24) / 255.0F;
      float red = (color >> 16 & 0xFF) / 255.0F;
      float green = (color >> 8 & 0xFF) / 255.0F;
      float blue = (color & 0xFF) / 255.0F;
      Matrix4f matrix = graphics.pose().last().pose();
      Tesselator tesselator = RenderSystem.renderThreadTesselator();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      BufferBuilder builder = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      builder.addVertex(matrix, minX, maxY, 0.0F).setColor(red, green, blue, alpha);
      builder.addVertex(matrix, maxX, maxY, 0.0F).setColor(red, green, blue, alpha);
      builder.addVertex(matrix, maxX, minY, 0.0F).setColor(red, green, blue, alpha);
      builder.addVertex(matrix, minX, minY, 0.0F).setColor(red, green, blue, alpha);
      BufferUploader.drawWithShader(builder.build());
      RenderSystem.disableBlend();
   }

   private boolean isMouseOver(double mouseX, double mouseY, int x, int y, int width, int height) {
      return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
   }

   public boolean isPauseScreen() {
      return false;
   }
}
