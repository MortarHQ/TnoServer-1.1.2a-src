package com.tensura_guild.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.tensura_guild.dialogue.GuildDialogue;
import com.tensura_guild.network.CloseDialoguePayload;
import com.tensura_guild.network.CompleteDialoguePayload;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Matrix4f;

public class YuukiDialogueScreen extends Screen {
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
   private final ResourceLocation dialogueId;
   private final int entityId;
   private final GuildDialogue dialogue;
   private final boolean handoffToGuildScreen;
   private final boolean showGuildOptions;
   private final Supplier<Screen> guildTaskBoardFactory;
   private int lineIndex = 0;
   private int revealed = 0;
   private int screenTicks = 0;
   private boolean completed = false;
   private boolean released = false;
   private boolean capturedHideGui = false;
   private boolean previousHideGui = false;

   public YuukiDialogueScreen(ResourceLocation dialogueId, int entityId, GuildDialogue dialogue) {
      this(dialogueId, entityId, dialogue, 0, true);
   }

   public YuukiDialogueScreen(ResourceLocation dialogueId, int entityId, GuildDialogue dialogue, int lineIndex, boolean handoffToGuildScreen) {
      this(dialogueId, entityId, dialogue, lineIndex, handoffToGuildScreen, false, null);
   }

   private YuukiDialogueScreen(
      ResourceLocation dialogueId,
      int entityId,
      GuildDialogue dialogue,
      int lineIndex,
      boolean handoffToGuildScreen,
      boolean showGuildOptions,
      Supplier<Screen> guildTaskBoardFactory
   ) {
      super(Component.literal("Yuuki Kagurazaka"));
      this.dialogueId = dialogueId;
      this.entityId = entityId;
      this.dialogue = dialogue;
      this.handoffToGuildScreen = handoffToGuildScreen;
      this.showGuildOptions = showGuildOptions;
      this.guildTaskBoardFactory = guildTaskBoardFactory;
      this.lineIndex = dialogue.lines().isEmpty() ? 0 : Mth.clamp(lineIndex, 0, dialogue.lines().size() - 1);
   }

   public static YuukiDialogueScreen guildMenu(ResourceLocation dialogueId, int entityId, GuildDialogue dialogue, Supplier<Screen> guildTaskBoardFactory) {
      return new YuukiDialogueScreen(dialogueId, entityId, dialogue, 0, false, true, guildTaskBoardFactory);
   }

   protected void init() {
      super.init();
      this.applyCinematicHideGui();
   }

   private GuildDialogue.Line currentLine() {
      return this.dialogue.lines().get(this.lineIndex);
   }

   private boolean isLineFullyRevealed() {
      return this.dialogue.lines().isEmpty() || this.revealed >= this.currentText().length();
   }

   public void tick() {
      super.tick();
      this.screenTicks++;
      if (!this.dialogue.lines().isEmpty() && !this.isLineFullyRevealed()) {
         this.revealed = Math.min(this.currentText().length(), this.revealed + 1);
      }
   }

   private void advance() {
      if (this.dialogue.lines().isEmpty()) {
         this.finishDialogue();
      } else {
         if (!this.isLineFullyRevealed()) {
            this.revealed = this.currentText().length();
         } else {
            if (this.showGuildOptions) {
               return;
            }

            if (this.lineIndex < this.dialogue.lines().size() - 1) {
               this.lineIndex++;
               this.revealed = 0;
            } else {
               this.finishDialogue();
            }
         }
      }
   }

   private void finishDialogue() {
      if (this.handoffToGuildScreen) {
         this.released = true;
         this.minecraft.setScreen(new AdventurerGuildJoinListScreen(this.dialogueId, this.entityId));
      } else {
         if (!this.completed) {
            this.completed = true;
            this.released = true;
            PacketDistributor.sendToServer(new CompleteDialoguePayload(this.dialogueId, this.entityId), new CustomPacketPayload[0]);
         }

         this.minecraft.setScreen(null);
      }
   }

   public void onClose() {
      if (!this.completed && !this.released) {
         this.released = true;
         PacketDistributor.sendToServer(new CloseDialoguePayload(this.entityId), new CustomPacketPayload[0]);
      }

      super.onClose();
   }

   public void removed() {
      this.restoreHideGui();
      super.removed();
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0) {
         if (this.showGuildOptions && this.isLineFullyRevealed() && this.handleGuildOptionClick(mouseX, mouseY)) {
            return true;
         }

         this.advance();
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode != 32 && keyCode != 257 && keyCode != 335) {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }

      this.advance();
      return true;
   }

   public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
      this.renderLetterbox(g);
      Font font = this.font;
      int panelW = Math.min(320, this.width - 32);
      int panelH = 78;
      int panelX = (this.width - panelW) / 2;
      int panelY = this.height - panelH - 18;
      int textX = panelX + 12;
      int textW = panelW - 24;
      int textY = panelY + 10;
      fillTransparent(g, panelX - 1, panelY - 1, panelX + panelW + 1, panelY + panelH + 1, -2144193992);
      fillTransparent(g, panelX, panelY, panelX + panelW, panelY + panelH, 1712723482);
      if (!this.dialogue.lines().isEmpty()) {
         GuildDialogue.Line line = this.currentLine();
         g.drawString(font, this.speakerComponent(line), textX, textY, -2052029, false);
         String text = this.textComponent(line).getString();
         String shown = text.substring(0, Math.min(this.revealed, text.length()));
         List<FormattedCharSequence> wrapped = font.split(Component.literal(shown), textW);
         int ty = textY + 9 + 4;

         for (FormattedCharSequence seq : wrapped) {
            g.drawString(font, seq, textX, ty, -1513240, false);
            ty += 9 + 1;
         }
      }

      Component hint = Component.translatable(this.isLineFullyRevealed() ? "screen.tensura_guild.dialogue.next" : "screen.tensura_guild.dialogue.skip");
      if (!this.showGuildOptions) {
         g.drawString(font, hint, panelX + panelW - font.width(hint) - 8, panelY + panelH - 9 - 6, -7697782, false);
      }

      if (this.showGuildOptions && this.isLineFullyRevealed()) {
         this.renderGuildOptions(g, mouseX, mouseY);
      }
   }

   private void renderGuildOptions(GuiGraphics graphics, int mouseX, int mouseY) {
      int panelX = this.guildOptionPanelX();
      int panelY = this.guildOptionPanelY();
      this.renderGuildOption(graphics, mouseX, mouseY, panelX, panelY, Component.translatable("screen.tensura_guild.dialogue.option.shop"));
      this.renderGuildOption(graphics, mouseX, mouseY, panelX, panelY + 18 + 5, Component.translatable("screen.tensura_guild.dialogue.option.tasks"));
      this.renderGuildOption(graphics, mouseX, mouseY, panelX, panelY + 46, Component.translatable("screen.tensura_guild.dialogue.option.none"));
   }

   private void renderGuildOption(GuiGraphics graphics, int mouseX, int mouseY, int x, int y, Component text) {
      boolean hovered = this.isMouseOver(mouseX, mouseY, x, y, 112, 18);
      fillTransparent(graphics, x, y, x + 112, y + 18, hovered ? 1714565688 : -1441393126);
      graphics.drawString(this.font, text, x + 7, y + (18 - 9) / 2, -1513240, false);
   }

   private boolean handleGuildOptionClick(double mouseX, double mouseY) {
      int panelX = this.guildOptionPanelX();
      int panelY = this.guildOptionPanelY();
      if (this.isMouseOver(mouseX, mouseY, panelX, panelY, 112, 18)) {
         this.released = true;
         if (this.minecraft != null) {
            this.minecraft.setScreen(new GuildShopScreen(this.entityId));
         }

         return true;
      } else if (this.isMouseOver(mouseX, mouseY, panelX, panelY + 18 + 5, 112, 18)) {
         this.released = true;
         if (this.minecraft != null && this.guildTaskBoardFactory != null) {
            this.minecraft.setScreen(this.guildTaskBoardFactory.get());
         }

         return true;
      } else if (this.isMouseOver(mouseX, mouseY, panelX, panelY + 46, 112, 18)) {
         this.releaseDialogueLock();
         if (this.minecraft != null) {
            this.minecraft.setScreen(null);
         }

         return true;
      } else {
         return false;
      }
   }

   private int guildOptionPanelX() {
      return this.width - 112 - 18;
   }

   private int guildOptionPanelY() {
      return (this.height - 64) / 2;
   }

   private void releaseDialogueLock() {
      if (!this.released) {
         this.released = true;
         PacketDistributor.sendToServer(new CloseDialoguePayload(this.entityId), new CustomPacketPayload[0]);
      }
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

   public void renderBackground(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
   }

   protected void renderBlurredBackground(float partialTick) {
   }

   private String currentText() {
      return this.textComponent(this.currentLine()).getString();
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

   private Component speakerComponent(GuildDialogue.Line line) {
      return line.speakerTranslatable() ? Component.translatable(line.speaker()) : Component.literal(line.speaker());
   }

   private Component textComponent(GuildDialogue.Line line) {
      return line.textTranslatable() ? Component.translatable(line.text()) : Component.literal(line.text());
   }

   private boolean isMouseOver(double mouseX, double mouseY, int x, int y, int width, int height) {
      return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
   }

   public boolean isPauseScreen() {
      return false;
   }
}
