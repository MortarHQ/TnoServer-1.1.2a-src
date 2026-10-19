package com.tensura_guild.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Axis;
import com.tensura_guild.data.GuildSignatureData;
import com.tensura_guild.dialogue.GuildDialogue;
import com.tensura_guild.network.CloseDialoguePayload;
import com.tensura_guild.network.SaveGuildSignaturePayload;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Matrix4f;

public class AdventurerGuildJoinListScreen extends Screen {
   private static final ResourceLocation ZH_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/adventurer_guild_join_list.png");
   private static final ResourceLocation EN_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/adventurer_guild_join_list_en.png");
   private static final int TEXTURE_SIZE = 256;
   private static final int FORM_WIDTH = 136;
   private static final int FORM_HEIGHT = 180;
   private static final float SHEET_ALPHA = 0.9F;
   private static final float BUTTON_IDLE_ALPHA_MIN = 0.52F;
   private static final float BUTTON_IDLE_ALPHA_MAX = 1.0F;
   private static final int SIGNATURE_ANIMATION_TICKS = 14;
   private static final int POST_SIGN_WAIT_TICKS = 20;
   private static final int EXIT_GROW_TICKS = 4;
   private static final int EXIT_SHAKE_TICKS = 10;
   private static final int EXIT_SHRINK_TICKS = 6;
   private static final float EXIT_GROW_SCALE = 1.1F;
   private static final float EXIT_SHAKE_ANGLE_DEGREES = 5.0F;
   private static final float SIGNATURE_PULSE_ALPHA_LOW = 0.45F;
   private static final float SIGNATURE_PULSE_ALPHA_HIGH = 1.0F;
   private static final int SIGNATURE_PULSE_CYCLE_TICKS = 16;
   private static final float SIGNATURE_BOX_PULSE_ALPHA_LOW = 0.3F;
   private static final float SIGNATURE_BOX_PULSE_ALPHA_HIGH = 1.0F;
   private static final int SIGNATURE_BOX_PULSE_CYCLE_TICKS = 56;
   private static final int SIGNATURE_BOX_FILL_RGB = 15786936;
   private static final int SIGNATURE_BOX_BORDER_RGB = 6044192;
   private static final int SIGNATURE_BOX_HIGHLIGHT_RGB = 16313801;
   private static final AdventurerGuildJoinListScreen.Rect PLAYER_SIGNATURE = new AdventurerGuildJoinListScreen.Rect(5, 76, 56, 101);
   private static final AdventurerGuildJoinListScreen.Rect CONFIRM_BUTTON = new AdventurerGuildJoinListScreen.Rect(55, 116, 81, 128);
   private static final AdventurerGuildJoinListScreen.Rect GUILDMASTER_CLICK = new AdventurerGuildJoinListScreen.Rect(99, 148, 134, 159);
   private static final AdventurerGuildJoinListScreen.Rect GUILDMASTER_SIGNATURE_SRC = new AdventurerGuildJoinListScreen.Rect(79, 189, 134, 206);
   private static final AdventurerGuildJoinListScreen.Rect SEAL_SRC = new AdventurerGuildJoinListScreen.Rect(2, 189, 72, 247);
   private static final AdventurerGuildJoinListScreen.Rect SEAL_DST = new AdventurerGuildJoinListScreen.Rect(9, 62, 79, 120);
   private static final AdventurerGuildJoinListScreen.Rect BUTTON_SRC = new AdventurerGuildJoinListScreen.Rect(86, 214, 112, 226);
   private final List<AdventurerGuildJoinListScreen.Stroke> strokes = new ArrayList<>();
   private final ResourceLocation dialogueId;
   private final int entityId;
   private AdventurerGuildJoinListScreen.Stroke activeStroke;
   private boolean playerSignatureCompleted;
   private boolean guildmasterSigning;
   private boolean guildmasterSigned;
   private int guildmasterSigningTicks;
   private AdventurerGuildJoinListScreen.Stage stage = AdventurerGuildJoinListScreen.Stage.SIGN;
   private int waitTicks;
   private int exitTicks;
   private int leftPos;
   private int topPos;
   private boolean closingTransitionStarted;
   private boolean enteredFinalDialogue;
   private boolean signatureSaved;
   private int screenTick;

   public AdventurerGuildJoinListScreen(ResourceLocation dialogueId, int entityId) {
      super(Component.literal("Adventurer Guild Join List"));
      this.dialogueId = dialogueId;
      this.entityId = entityId;
   }

   protected void init() {
      this.leftPos = (this.width - 136) / 2;
      this.topPos = (this.height - 180) / 2;
   }

   public void tick() {
      super.tick();
      this.screenTick++;
      if (this.guildmasterSigning && !this.guildmasterSigned) {
         this.guildmasterSigningTicks++;
         if (this.guildmasterSigningTicks >= 14) {
            this.guildmasterSigningTicks = 14;
            this.guildmasterSigning = false;
            this.guildmasterSigned = true;
            this.savePlayerSignature();
            this.stage = AdventurerGuildJoinListScreen.Stage.WAIT;
            this.waitTicks = 0;
            this.exitTicks = 0;
         }
      }

      if (this.stage == AdventurerGuildJoinListScreen.Stage.WAIT && this.guildmasterSigned) {
         this.waitTicks++;
         if (this.waitTicks >= 20) {
            this.stage = AdventurerGuildJoinListScreen.Stage.EXIT_GROW;
            this.exitTicks = 0;
         }
      } else if (this.stage == AdventurerGuildJoinListScreen.Stage.EXIT_GROW) {
         this.exitTicks++;
         if (this.exitTicks >= 4) {
            this.stage = AdventurerGuildJoinListScreen.Stage.EXIT_SHAKE;
            this.exitTicks = 0;
         }
      } else if (this.stage == AdventurerGuildJoinListScreen.Stage.EXIT_SHAKE) {
         this.exitTicks++;
         if (this.exitTicks >= 10) {
            this.stage = AdventurerGuildJoinListScreen.Stage.EXIT_SHRINK;
            this.exitTicks = 0;
            this.playExitDing();
         }
      } else if (this.stage == AdventurerGuildJoinListScreen.Stage.EXIT_SHRINK) {
         this.exitTicks++;
         if (this.exitTicks >= 6 && !this.enteredFinalDialogue) {
            this.enteredFinalDialogue = true;
            this.closingTransitionStarted = true;
            if (this.minecraft != null) {
               this.minecraft.setScreen(new YuukiDialogueScreen(this.dialogueId, this.entityId, createFinalDialogue(), 0, false));
            }
         }
      }
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      float panelScale = 1.0F;
      float panelAlpha = 1.0F;
      float centerX = this.leftPos + 68.0F;
      float centerY = this.topPos + 90.0F;
      float lift = 0.0F;
      float rotationDegrees = 0.0F;
      if (this.stage == AdventurerGuildJoinListScreen.Stage.EXIT_GROW) {
         float progress = Mth.clamp((this.exitTicks + partialTick) / 4.0F, 0.0F, 1.0F);
         float eased = 1.0F - (1.0F - progress) * (1.0F - progress);
         panelScale = Mth.lerp(eased, 1.0F, 1.1F);
         lift = 8.0F * eased;
      } else if (this.stage == AdventurerGuildJoinListScreen.Stage.EXIT_SHAKE) {
         float progress = Mth.clamp((this.exitTicks + partialTick) / 10.0F, 0.0F, 1.0F);
         panelScale = 1.1F;
         lift = 8.0F;
         rotationDegrees = (float)Math.sin(progress * Math.PI * 4.0) * 5.0F * (1.0F - progress * 0.25F);
      } else if (this.stage == AdventurerGuildJoinListScreen.Stage.EXIT_SHRINK) {
         float progress = Mth.clamp((this.exitTicks + partialTick) / 6.0F, 0.0F, 1.0F);
         float eased = progress * progress;
         panelScale = Mth.lerp(eased, 1.1F, 0.0F);
         panelAlpha = 1.0F - progress;
         lift = 8.0F + 18.0F * eased;
      }

      graphics.pose().pushPose();

      try {
         float pivotX = centerX;
         float pivotY = centerY - lift - panelScale * 180.0F / 2.0F;
         graphics.pose().translate(pivotX, pivotY, 0.0F);
         graphics.pose().mulPose(Axis.ZP.rotationDegrees(rotationDegrees));
         graphics.pose().translate(-pivotX, -pivotY, 0.0F);
         graphics.pose().translate(centerX, centerY - lift, 0.0F);
         graphics.pose().scale(panelScale, panelScale, 1.0F);
         graphics.pose().translate(-centerX, -centerY, 0.0F);
         this.renderPanel(graphics, panelAlpha);
         this.renderSignatureBox(graphics, panelAlpha, partialTick);
         this.renderSignature(graphics, panelAlpha);
         this.renderGuildmasterSignature(graphics, mouseX, mouseY, panelAlpha, partialTick);
         this.renderConfirmButton(graphics, mouseX, mouseY, panelAlpha, partialTick);
      } finally {
         graphics.pose().popPose();
      }
   }

   private void renderPanel(GuiGraphics graphics, float alpha) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.9F * alpha);
      graphics.blit(this.currentTexture(), this.leftPos, this.topPos, 0, 0.0F, 0.0F, 136, 180, 256, 256);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderSignatureBox(GuiGraphics graphics, float panelAlpha, float partialTick) {
      if (!this.guildmasterSigning && !this.guildmasterSigned) {
         int left = this.leftPos + PLAYER_SIGNATURE.left();
         int top = this.topPos + PLAYER_SIGNATURE.top();
         int right = this.leftPos + PLAYER_SIGNATURE.right() + 1;
         int bottom = this.topPos + PLAYER_SIGNATURE.bottom() + 1;
         float pulseAlpha = this.getSignatureBoxPulseAlpha(partialTick);
         float idleAlpha = this.playerSignatureCompleted ? 0.48F : pulseAlpha;
         int fillAlpha = Math.round(34.0F * panelAlpha * idleAlpha);
         int borderAlpha = Math.round((this.playerSignatureCompleted ? 124 : 210) * panelAlpha * idleAlpha);
         int highlightAlpha = Math.round(86.0F * panelAlpha * idleAlpha);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         graphics.fill(left + 1, top + 1, right - 1, bottom - 1, argb(fillAlpha, 15786936));
         graphics.fill(left, top, right, top + 1, argb(borderAlpha, 6044192));
         graphics.fill(left, bottom - 1, right, bottom, argb(borderAlpha, 6044192));
         graphics.fill(left, top, left + 1, bottom, argb(borderAlpha, 6044192));
         graphics.fill(right - 1, top, right, bottom, argb(borderAlpha, 6044192));
         graphics.fill(left + 2, top + 2, right - 2, top + 3, argb(highlightAlpha, 16313801));
         RenderSystem.disableBlend();
      }
   }

   private void renderConfirmButton(GuiGraphics graphics, int mouseX, int mouseY, float panelAlpha, float partialTick) {
      if (this.playerSignatureCompleted && !this.guildmasterSigning && !this.guildmasterSigned && this.stage == AdventurerGuildJoinListScreen.Stage.SIGN) {
         int buttonX = this.leftPos + CONFIRM_BUTTON.left();
         int buttonY = this.topPos + CONFIRM_BUTTON.top();
         int buttonWidth = CONFIRM_BUTTON.width();
         int buttonHeight = CONFIRM_BUTTON.height();
         boolean hovered = this.isMouseOver(mouseX, mouseY, buttonX, buttonY, buttonWidth, buttonHeight);
         float blinkAlpha = hovered ? 1.0F : Mth.lerp((float)((Math.sin((this.screenTick + partialTick) * 0.45F) + 1.0) * 0.5), 0.52F, 1.0F);
         float scale = hovered ? 1.08F : 1.0F;
         int drawWidth = Math.round(buttonWidth * scale);
         int drawHeight = Math.round(buttonHeight * scale);
         int drawX = buttonX + (buttonWidth - drawWidth) / 2;
         int drawY = buttonY + (buttonHeight - drawHeight) / 2;
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, blinkAlpha * panelAlpha);
         graphics.blit(
            this.currentTexture(), drawX, drawY, drawWidth, drawHeight, BUTTON_SRC.left(), BUTTON_SRC.top(), BUTTON_SRC.width(), BUTTON_SRC.height(), 256, 256
         );
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   private void renderSignature(GuiGraphics graphics, float panelAlpha) {
      for (AdventurerGuildJoinListScreen.Stroke stroke : this.strokes) {
         List<AdventurerGuildJoinListScreen.Point> points = stroke.points();

         for (int i = 1; i < points.size(); i++) {
            AdventurerGuildJoinListScreen.Point prev = points.get(i - 1);
            AdventurerGuildJoinListScreen.Point current = points.get(i);
            this.drawLine(
               graphics,
               this.leftPos + prev.x(),
               this.topPos + prev.y(),
               this.leftPos + current.x(),
               this.topPos + current.y(),
               argb(Math.round(255.0F * panelAlpha), 29, 23, 18)
            );
         }
      }
   }

   private void renderGuildmasterSignature(GuiGraphics graphics, int mouseX, int mouseY, float panelAlpha, float partialTick) {
      if (this.guildmasterSigning || this.guildmasterSigned) {
         int targetX = this.leftPos + GUILDMASTER_CLICK.left();
         int targetY = this.topPos + GUILDMASTER_CLICK.top();
         int targetWidth = GUILDMASTER_CLICK.width();
         int targetHeight = GUILDMASTER_CLICK.height();
         int sourceWidth = GUILDMASTER_SIGNATURE_SRC.width();
         int sourceHeight = GUILDMASTER_SIGNATURE_SRC.height();
         int visibleWidth = this.guildmasterSigned
            ? targetWidth
            : Math.max(1, Math.min(targetWidth, (int)Math.ceil(targetWidth * (this.guildmasterSigningTicks / 14.0))));
         float scale = 1.0F;
         int drawWidth = Math.max(1, Math.round(targetWidth * scale));
         int drawHeight = Math.max(1, Math.round(targetHeight * scale));
         int drawX = targetX + (targetWidth - drawWidth) / 2;
         int drawY = targetY + (targetHeight - drawHeight) / 2;
         boolean needsScissor = !this.guildmasterSigned;
         if (needsScissor) {
            graphics.enableScissor(targetX, targetY, targetX + visibleWidth, targetY + targetHeight);
         }

         try {
            float signatureAlpha = this.guildmasterSigned ? this.getGuildmasterSignatureAlpha(partialTick) * panelAlpha : panelAlpha;
            this.renderScaledGuildmasterSignature(graphics, drawX, drawY, drawWidth, drawHeight, sourceWidth, sourceHeight, signatureAlpha);
         } finally {
            if (needsScissor) {
               graphics.disableScissor();
            }
         }

         if (this.guildmasterSigned) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, panelAlpha);
            graphics.blit(
               this.currentTexture(),
               this.leftPos + SEAL_DST.left(),
               this.topPos + SEAL_DST.top(),
               SEAL_DST.width(),
               SEAL_DST.height(),
               SEAL_SRC.left(),
               SEAL_SRC.top(),
               SEAL_SRC.width(),
               SEAL_SRC.height(),
               256,
               256
            );
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         }
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button != 0 || this.stage != AdventurerGuildJoinListScreen.Stage.SIGN) {
         return super.mouseClicked(mouseX, mouseY, button);
      }

      if (!this.guildmasterSigning && !this.guildmasterSigned) {
         int localX = this.toLocalX(mouseX);
         int localY = this.toLocalY(mouseY);
         if (PLAYER_SIGNATURE.contains(localX, localY)) {
            this.activeStroke = new AdventurerGuildJoinListScreen.Stroke();
            this.activeStroke.add(localX, localY);
            this.strokes.add(this.activeStroke);
            return true;
         } else if (this.playerSignatureCompleted && this.isConfirmButtonHovered(mouseX, mouseY)) {
            this.guildmasterSigning = true;
            this.guildmasterSigningTicks = 0;
            return true;
         } else {
            return super.mouseClicked(mouseX, mouseY, button);
         }
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      if (button == 0
         && this.activeStroke != null
         && this.stage == AdventurerGuildJoinListScreen.Stage.SIGN
         && !this.guildmasterSigning
         && !this.guildmasterSigned) {
         int localX = this.toLocalX(mouseX);
         int localY = this.toLocalY(mouseY);
         if (PLAYER_SIGNATURE.contains(localX, localY)) {
            this.activeStroke.add(localX, localY);
         }

         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (button == 0 && this.activeStroke != null) {
         this.activeStroke = null;
         this.playerSignatureCompleted = this.hasPlayerSignature();
         return true;
      } else {
         return super.mouseReleased(mouseX, mouseY, button);
      }
   }

   public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
   }

   protected void renderBlurredBackground(float partialTick) {
   }

   public void onClose() {
      if (!this.closingTransitionStarted) {
         this.closingTransitionStarted = true;
         PacketDistributor.sendToServer(new CloseDialoguePayload(this.entityId), new CustomPacketPayload[0]);
      }

      super.onClose();
   }

   public boolean isPauseScreen() {
      return false;
   }

   private boolean hasPlayerSignature() {
      return this.strokes.stream().mapToInt(stroke -> stroke.points().size()).sum() >= 2;
   }

   private void savePlayerSignature() {
      if (!this.signatureSaved) {
         this.signatureSaved = true;
         PacketDistributor.sendToServer(new SaveGuildSignaturePayload(this.createSignatureData()), new CustomPacketPayload[0]);
      }
   }

   private GuildSignatureData createSignatureData() {
      List<Integer> points = new ArrayList<>();

      for (AdventurerGuildJoinListScreen.Stroke stroke : this.strokes) {
         if (!stroke.points().isEmpty()) {
            if (!points.isEmpty()) {
               points.add(-1);
               points.add(-1);
            }

            for (AdventurerGuildJoinListScreen.Point point : stroke.points()) {
               points.add(point.x());
               points.add(point.y());
            }
         }
      }

      return new GuildSignatureData(points);
   }

   private boolean isConfirmButtonHovered(double mouseX, double mouseY) {
      int buttonX = this.leftPos + CONFIRM_BUTTON.left();
      int buttonY = this.topPos + CONFIRM_BUTTON.top();
      return this.isMouseOver(mouseX, mouseY, buttonX, buttonY, CONFIRM_BUTTON.width(), CONFIRM_BUTTON.height());
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

   private int toLocalX(double mouseX) {
      return (int)Math.floor(mouseX) - this.leftPos;
   }

   private int toLocalY(double mouseY) {
      return (int)Math.floor(mouseY) - this.topPos;
   }

   private boolean isMouseOver(double mouseX, double mouseY, int x, int y, int width, int height) {
      return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
   }

   private void playExitDing() {
      if (this.minecraft != null) {
         this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0F));
      }
   }

   private void renderScaledGuildmasterSignature(
      GuiGraphics graphics, int drawX, int drawY, int drawWidth, int drawHeight, int sourceWidth, int sourceHeight, float alpha
   ) {
      graphics.pose().pushPose();

      try {
         graphics.pose().translate(drawX, drawY, 0.0F);
         graphics.pose().scale((float)drawWidth / sourceWidth, (float)drawHeight / sourceHeight, 1.0F);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
         graphics.blit(this.currentTexture(), 0, 0, GUILDMASTER_SIGNATURE_SRC.left(), GUILDMASTER_SIGNATURE_SRC.top(), sourceWidth, sourceHeight, 256, 256);
      } finally {
         graphics.pose().popPose();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   private ResourceLocation currentTexture() {
      if (this.minecraft == null) {
         return ZH_TEXTURE;
      }

      String language = this.minecraft.getLanguageManager().getSelected().toLowerCase(Locale.ROOT);
      return language.startsWith("zh_") ? ZH_TEXTURE : EN_TEXTURE;
   }

   private float getGuildmasterSignatureAlpha(float partialTick) {
      float cycleProgress = (this.screenTick + partialTick) % 16.0F;
      float segment = 4.0F;
      if (cycleProgress < segment) {
         return Mth.lerp(cycleProgress / segment, 0.45F, 1.0F);
      } else if (cycleProgress < segment * 2.0F) {
         return Mth.lerp((cycleProgress - segment) / segment, 1.0F, 0.45F);
      } else {
         return cycleProgress < segment * 3.0F
            ? Mth.lerp((cycleProgress - segment * 2.0F) / segment, 0.45F, 1.0F)
            : Mth.lerp((cycleProgress - segment * 3.0F) / segment, 1.0F, 0.45F);
      }
   }

   private float getSignatureBoxPulseAlpha(float partialTick) {
      float progress = (this.screenTick + partialTick) % 56.0F / 56.0F;
      float wave = (float)((Math.sin(progress * Math.PI * 2.0 - (Math.PI / 2)) + 1.0) * 0.5);
      return Mth.lerp(wave, 0.3F, 1.0F);
   }

   private static GuildDialogue createFinalDialogue() {
      return new GuildDialogue(
         List.of(new GuildDialogue.Line("dialogue.tensura_guild.speaker.yuuki", "dialogue.tensura_guild.yuuki_intro.final_thanks", true, true))
      );
   }

   private static int argb(int alpha, int red, int green, int blue) {
      return (alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
   }

   private static int argb(int alpha, int rgb) {
      return (alpha & 0xFF) << 24 | rgb & 16777215;
   }

   private record Point(int x, int y) {
   }

   private record Rect(int left, int top, int right, int bottom) {
      private boolean contains(int x, int y) {
         return x >= this.left && x <= this.right && y >= this.top && y <= this.bottom;
      }

      private int width() {
         return this.right - this.left + 1;
      }

      private int height() {
         return this.bottom - this.top + 1;
      }
   }

   private enum Stage {
      SIGN,
      WAIT,
      EXIT_GROW,
      EXIT_SHAKE,
      EXIT_SHRINK;
   }

   private record Stroke(List<AdventurerGuildJoinListScreen.Point> points) {
      private Stroke() {
         this(new ArrayList<>());
      }

      private void add(int x, int y) {
         if (this.points.isEmpty() || !this.points.getLast().equals(new AdventurerGuildJoinListScreen.Point(x, y))) {
            this.points.add(new AdventurerGuildJoinListScreen.Point(x, y));
         }
      }
   }
}
