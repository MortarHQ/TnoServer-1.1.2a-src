package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.client.fx.FeldwayRewardClientFx;
import com.github.wal_bos.moreskills.net.msg.ClaimFeldwayRewardPayload;
import dev.architectury.networking.NetworkManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public final class FeldwayRewardScreen extends Screen {
   private static final ResourceLocation CANCEL_REWARD_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "cancel_feldway_reward");
   private final List<ResourceLocation> skills;
   private final List<FeldwayRewardScreen.CardState> cards = new ArrayList<>();
   private int tick;
   private ResourceLocation selectedSkill;
   private int selectedIndex = -1;
   private int claimTicks;
   private boolean cancelled;

   public FeldwayRewardScreen(List<ResourceLocation> skills) {
      super(Component.literal("Feldway's Reward"));
      this.skills = new ArrayList<>(skills);
   }

   protected void init() {
      this.cards.clear();
      int count = this.skills.size();
      if (count > 0) {
         int headerHeight = 92;
         int sideMargin = 14;
         int bottomMargin = 14;
         int gap = 8;
         int columns = count;
         int availableWidth = this.width - sideMargin * 2 - gap * Math.max(0, columns - 1);
         int availableHeight = this.height - headerHeight - bottomMargin;
         int cardWidth = availableWidth / columns;
         int cardHeight = availableHeight;
         cardWidth = Mth.clamp(cardWidth, 82, 210);
         cardHeight = Mth.clamp(cardHeight, 132, 275);
         int totalWidth = columns * cardWidth + (columns - 1) * gap;
         int startX = this.width / 2 - totalWidth / 2;
         int startY = headerHeight + Math.max(0, (this.height - headerHeight - bottomMargin - cardHeight) / 2);
         if (startY + cardHeight > this.height - bottomMargin) {
            startY = this.height - bottomMargin - cardHeight;
         }

         for (int i = 0; i < count; i++) {
            ResourceLocation id = this.skills.get(i);
            int x = startX + i * (cardWidth + gap);
            int y = startY;
            float centerOffset = i - (count - 1) / 2.0F;
            float startDx = centerOffset * 85.0F;
            float startDy = -150.0F - Math.abs(centerOffset) * 22.0F;
            this.cards.add(new FeldwayRewardScreen.CardState(id, x, y, cardWidth, cardHeight, i, startDx, startDy));
         }
      }
   }

   public void tick() {
      super.tick();
      this.tick++;
      if (!this.cancelled) {
         if (this.selectedSkill != null) {
            this.claimTicks++;
            if (this.claimTicks >= 14) {
               NetworkManager.sendToServer(new ClaimFeldwayRewardPayload(this.selectedSkill));
               if (this.minecraft != null) {
                  this.minecraft.setScreen(null);
               }
            }
         }
      }
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      float time = this.tick + partialTick;
      FeldwayRewardClientFx.renderBackground(graphics, this.width, this.height, time);
      graphics.drawCenteredString(this.font, Component.literal("FELDWAY HAS FALLEN").withStyle(ChatFormatting.GOLD), this.width / 2, 14, -11170);
      graphics.drawCenteredString(this.font, Component.literal("Choose one sovereign authority").withStyle(ChatFormatting.WHITE), this.width / 2, 30, -1);
      graphics.drawCenteredString(
         this.font, Component.literal("Death · Life · Time · Space · War").withStyle(ChatFormatting.GRAY), this.width / 2, 46, -2634576
      );
      graphics.drawCenteredString(
         this.font, Component.literal("Only one reward may ever be claimed").withStyle(ChatFormatting.GRAY), this.width / 2, 60, -3424353
      );
      FeldwayRewardScreen.CardState hoveredCard = null;

      for (FeldwayRewardScreen.CardState card : this.cards) {
         if (this.renderCard(graphics, card, mouseX, mouseY, time)) {
            hoveredCard = card;
         }
      }

      if (this.selectedIndex >= 0 && this.selectedIndex < this.cards.size()) {
         this.renderSelectedBurst(graphics, this.cards.get(this.selectedIndex), time);
      }

      FeldwayRewardClientFx.renderForeground(graphics, this.width, this.height, time);
      this.renderCloseButton(graphics, mouseX, mouseY);
      if (hoveredCard != null && this.selectedSkill == null) {
         this.renderTooltip(graphics, hoveredCard, mouseX, mouseY);
      }

      if (this.cards.isEmpty()) {
         graphics.drawCenteredString(
            this.font, Component.literal("No authorities available").withStyle(ChatFormatting.RED), this.width / 2, this.height / 2, -43691
         );
      }
   }

   private void renderCloseButton(GuiGraphics graphics, int mouseX, int mouseY) {
      int size = this.closeButtonSize();
      int x = this.closeButtonX(size);
      int y = this.closeButtonY(size);
      boolean hovered = this.isMouseOver(mouseX, mouseY, x, y, size, size);
      int outer = hovered ? -38050 : -10899;
      int inner = hovered ? -649457391 : -1189473780;
      int cross = hovered ? -1 : -5968;
      graphics.fill(x, y, x + size, y + size, outer);
      graphics.fill(x + 2, y + 2, x + size - 2, y + size - 2, inner);
      int pad = Math.max(4, size / 4);
      this.drawLine(graphics, x + pad, y + pad, x + size - pad, y + size - pad, cross);
      this.drawLine(graphics, x + size - pad, y + pad, x + pad, y + size - pad, cross);
   }

   private int closeButtonSize() {
      int smallest = Math.max(1, Math.min(this.width, this.height));
      return Math.max(1, Math.min(20, smallest - Math.min(4, smallest - 1)));
   }

   private int closeButtonX(int size) {
      int maxX = Math.max(0, this.width - size);
      return Math.max(0, Math.min(maxX, this.width - size - 6));
   }

   private int closeButtonY(int size) {
      int maxY = Math.max(0, this.height - size);
      return Math.max(0, Math.min(maxY, 6));
   }

   private boolean renderCard(GuiGraphics graphics, FeldwayRewardScreen.CardState card, int mouseX, int mouseY, float time) {
      FeldwayRewardScreen.SkillStyle style = this.getStyle(card.id);
      float appearBase = Mth.clamp((time - card.index * 3.0F) / 14.0F, 0.0F, 1.0F);
      float appear = this.easeOutBack(appearBase);
      float realX = card.x + card.startDx * (1.0F - appear);
      float realY = card.y + card.startDy * (1.0F - appear);
      boolean hovered = this.selectedSkill == null && this.isMouseOver(mouseX, mouseY, realX, realY, card.w, card.h);
      boolean selected = this.selectedIndex == card.index;
      card.hover = Mth.lerp(0.22F, card.hover, hovered ? 1.0F : 0.0F);
      float selectedPulse = 0.0F;
      if (selected) {
         float p = Mth.clamp(this.claimTicks / 14.0F, 0.0F, 1.0F);
         selectedPulse = Mth.sin(p * (float) Math.PI) * 0.1F;
      }

      float widthScale = card.w < 120 ? 0.82F : (card.w < 150 ? 0.9F : 1.0F);
      float scale = (0.94F + 0.06F * appear) * (1.0F + card.hover * 0.025F + selectedPulse) * widthScale;
      graphics.pose().pushPose();
      graphics.pose().translate(realX + card.w / 2.0F, realY + card.h / 2.0F, 0.0F);
      graphics.pose().scale(scale, scale, 1.0F);
      int visualW = Math.round(card.w / widthScale);
      int visualH = card.h;
      int left = -visualW / 2;
      int top = -visualH / 2;
      int right = visualW / 2;
      int bottom = visualH / 2;
      int glowAlpha = 35 + (int)(card.hover * 88.0F) + (selected ? 80 : 0);
      graphics.fill(left - 8, top - 8, right + 8, bottom + 8, glowAlpha << 24 | style.glow & 16777215);
      graphics.fill(left - 4, top - 4, right + 4, bottom + 4, 1157625295);
      graphics.fill(left, top, right, bottom, style.cardBase);
      graphics.fill(left + 3, top + 3, right - 3, bottom - 3, style.cardInner);
      graphics.fill(left, top, right, top + 3, style.border);
      graphics.fill(left, bottom - 3, right, bottom, style.border);
      graphics.fill(left, top, left + 3, bottom, style.border);
      graphics.fill(right - 3, top, right, bottom, style.border);
      this.renderBackMotif(graphics, left, top, right, bottom, time, style);
      this.renderShineSweep(graphics, left + 5, top + 5, visualW - 10, visualH - 10, time, card.index);
      int iconY = top + Math.max(24, visualH / 6);
      int iconSize = Math.max(16, Math.min(24, visualH / 7));
      this.renderDomainAura(graphics, 0, iconY, visualW, visualH, time, style, card.hover);
      this.renderHaloGlyph(graphics, 0, iconY, Math.max(24, Math.min(38, visualW / 5)), time, style);
      this.renderSkillSigil(graphics, 0, iconY, iconSize, time, style, card.hover);
      int nameY = iconY + 28;
      int domainY = nameY + 14;
      int descY = domainY + 19;
      this.drawScaledCenteredString(graphics, this.font, Component.literal(style.name), 0, nameY, style.nameColor, visualW < 135 ? 0.78F : style.titleScale);
      this.drawScaledCenteredString(graphics, this.font, Component.literal(style.domain), 0, domainY, style.domainColor, visualW < 135 ? 0.7F : 0.82F);
      List<FormattedCharSequence> lines = this.font.split(Component.literal(style.shortDescription), visualW - 24);
      int maxLines = visualH < 170 ? 1 : 2;

      for (int i = 0; i < Math.min(maxLines, lines.size()); i++) {
         graphics.drawCenteredString(this.font, lines.get(i), 0, descY + i * 10, style.textColor);
      }

      this.renderClaimRibbon(graphics, left, right, bottom, hovered, selected, style, time);
      if (hovered) {
         this.renderHoverFeathers(graphics, left, top, right, bottom, time, style);
         int edgeAlpha = 105 + (int)(Mth.sin(time * 0.25F) * 28.0F + 28.0F);
         int edgeColor = edgeAlpha << 24 | style.border & 16777215;
         graphics.fill(left - 2, top - 2, right + 2, top, edgeColor);
         graphics.fill(left - 2, bottom, right + 2, bottom + 2, edgeColor);
         graphics.fill(left - 2, top - 2, left, bottom + 2, edgeColor);
         graphics.fill(right, top - 2, right + 2, bottom + 2, edgeColor);
      }

      graphics.pose().popPose();
      return hovered;
   }

   private void renderBackMotif(GuiGraphics graphics, int left, int top, int right, int bottom, float time, FeldwayRewardScreen.SkillStyle style) {
      int motifBottom = top + Math.min(48, Math.max(36, (bottom - top) / 4));
      graphics.fill(left + 8, top + 8, right - 8, motifBottom, 570425344 | style.glow & 16777215);
      graphics.fill(left + 13, top + 13, right - 13, motifBottom - 4, 771751935);
      if (style.sigilType == 0) {
         for (int i = 0; i < 7; i++) {
            int x = left + 13 + i * Math.max(8, (right - left - 26) / 7);
            int y = top + 31 + (int)(Mth.sin(time * 0.12F + i) * 3.0F);
            graphics.fill(x - 1, y - 7, x + 1, y + 7, -2009327019);
            graphics.fill(x - 3, y + 5, x + 3, y + 7, 1715214933);
         }
      } else if (style.sigilType == 1) {
         for (int i = 0; i < 7; i++) {
            int x = left + 13 + i * Math.max(8, (right - left - 26) / 7);
            int y = top + 32 + (int)(Mth.sin(time * 0.08F + i) * 3.0F);
            graphics.fill(x - 2, y - 5, x + 2, y + 5, 2000009323);
            graphics.fill(x - 1, y - 9, x + 1, y - 4, -2001141808);
         }
      } else if (style.sigilType == 2) {
         int cx = 0;
         int cy = top + 28;

         for (int i = 0; i < 12; i++) {
            float a = i * (float) (Math.PI / 6) + time * 0.015F;
            int x = cx + (int)(Math.cos(a) * 28.0);
            int y = cy + (int)(Math.sin(a) * 14.0);
            graphics.fill(x - 1, y - 1, x + 1, y + 1, -1427221163);
         }
      } else if (style.sigilType == 3) {
         int cx = 0;
         int cy = top + 28;

         for (int i = 0; i < 5; i++) {
            int r = 7 + i * 5;
            int color = 32 - i * 4 << 24 | 3954416;
            this.drawRectRing(graphics, cx, cy, r, r / 2, color);
         }
      } else {
         int y = top + 34;
         graphics.fill(left + 14, y - 2, right - 14, y + 2, -2001660394);
         graphics.fill(left + 24, y - 8, left + 30, y + 8, -1426074259);
         graphics.fill(right - 30, y - 8, right - 24, y + 8, -1426074259);
         graphics.fill(left + 38, y - 5, right - 38, y - 2, 1728053247);
      }
   }

   private void renderDomainAura(GuiGraphics graphics, int cx, int cy, int visualW, int visualH, float time, FeldwayRewardScreen.SkillStyle style, float hover) {
      int radiusX = Math.max(24, Math.min(42, visualW / 4));
      int radiusY = Math.max(12, Math.min(22, visualH / 10));
      int alpha = 28 + (int)(hover * 42.0F);

      for (int i = 0; i < 3; i++) {
         int rx = radiusX + i * 8;
         int ry = radiusY + i * 4;
         int color = alpha - i * 7 << 24 | style.accent & 16777215;
         this.drawRectRing(graphics, cx, cy, rx, ry, color);
      }

      if (style.sigilType == 0) {
         for (int i = 0; i < 10; i++) {
            float a = time * 0.045F + i * (float) (Math.PI / 5);
            int x = cx + (int)(Math.cos(a) * (radiusX + 10));
            int y = cy + (int)(Math.sin(a) * (radiusY + 6));
            graphics.fill(x - 1, y - 4, x + 1, y + 4, -2008407708);
            graphics.fill(x - 3, y + 3, x + 3, y + 5, 1716134244);
         }
      } else if (style.sigilType == 1) {
         for (int i = 0; i < 12; i++) {
            float a = -time * 0.035F + i * (float) (Math.PI / 6);
            int x = cx + (int)(Math.cos(a) * (radiusX + 9));
            int y = cy + (int)(Math.sin(a) * (radiusY + 7));
            graphics.fill(x - 2, y - 1, x + 2, y + 1, -1439315851);
            graphics.fill(x, y - 3, x + 1, y + 3, 2008612816);
         }
      } else if (style.sigilType == 2) {
         for (int i = 0; i < 16; i++) {
            float a = time * 0.06F + i * (float) (Math.PI / 8);
            int x = cx + (int)(Math.cos(a) * (radiusX + 8));
            int y = cy + (int)(Math.sin(a) * (radiusY + 8));
            graphics.fill(x - 1, y - 1, x + 1, y + 1, -855649852);
         }
      } else if (style.sigilType == 3) {
         for (int i = 0; i < 9; i++) {
            float a = time * 0.08F + i * (float) (Math.PI * 2.0 / 9.0);
            int x = cx + (int)(Math.cos(a) * (radiusX + 11));
            int y = cy + (int)(Math.sin(a) * (radiusY + 9));
            graphics.fill(x - 2, y - 2, x + 2, y + 2, -1439477290);
            this.drawLine(graphics, cx, cy, x, y, 573788630);
         }
      } else {
         for (int i = 0; i < 8; i++) {
            int x = cx - radiusX + i * Math.max(6, radiusX * 2 / 7);
            int y = cy + radiusY + (int)(Mth.sin(time * 0.1F + i) * 3.0F);
            graphics.fill(x - 4, y, x + 4, y + 2, -1431235050);
            graphics.fill(x - 1, y - 8, x + 1, y + 8, -1426074259);
         }
      }
   }

   private void renderClaimRibbon(
      GuiGraphics graphics, int left, int right, int bottom, boolean hovered, boolean selected, FeldwayRewardScreen.SkillStyle style, float time
   ) {
      int ribbonHeight = 17;
      int ribbonY = bottom - ribbonHeight - 8;
      int ribbonLeft = left + 9;
      int ribbonRight = right - 9;
      int fillColor = selected ? -1 : (hovered ? -4694 : -859478);
      int borderColor = selected ? -1 : (hovered ? style.border : -2709696);
      graphics.fill(ribbonLeft, ribbonY, ribbonRight, ribbonY + ribbonHeight, 587202559);
      graphics.fill(ribbonLeft + 2, ribbonY + 2, ribbonRight - 2, ribbonY + ribbonHeight - 2, fillColor);
      graphics.fill(ribbonLeft, ribbonY, ribbonRight, ribbonY + 2, borderColor);
      graphics.fill(ribbonLeft, ribbonY + ribbonHeight - 2, ribbonRight, ribbonY + ribbonHeight, borderColor);
      graphics.fill(ribbonLeft, ribbonY, ribbonLeft + 2, ribbonY + ribbonHeight, borderColor);
      graphics.fill(ribbonRight - 2, ribbonY, ribbonRight, ribbonY + ribbonHeight, borderColor);
      String buttonText = selected ? "ACCEPTING" : "CLAIM";
      int textColor = selected ? -9420032 : (hovered ? -10733312 : -8562169);
      graphics.drawCenteredString(this.font, Component.literal(buttonText), 0, ribbonY + 5, textColor);
      if (hovered && !selected) {
         int sweep = (int)(time * 7.0F % Math.max(1, ribbonRight - ribbonLeft + 40)) - 20;
         graphics.fill(ribbonLeft + sweep, ribbonY + 2, ribbonLeft + sweep + 10, ribbonY + ribbonHeight - 2, 1442840575);
      }
   }

   private void renderHaloGlyph(GuiGraphics graphics, int cx, int cy, int radius, float time, FeldwayRewardScreen.SkillStyle style) {
      int lineColor = 922745057;

      for (int i = 0; i < 3; i++) {
         int r = radius - i * 5;
         this.drawRectRing(graphics, cx, cy, r, r / 2, lineColor);
      }

      for (int i = 0; i < 8; i++) {
         float a = time * style.orbitSpeed + i * (float) (Math.PI / 4);
         int x = cx + (int)(Math.cos(a) * (radius + 5));
         int y = cy + (int)(Math.sin(a) * (radius * 0.58F));
         graphics.fill(x - 2, y - 2, x + 2, y + 2, style.holyOrb);
         graphics.fill(x - 5, y, x + 5, y + 1, 905969663);
         graphics.fill(x, y - 5, x + 1, y + 5, 905969663);
      }

      graphics.fill(cx - 1, cy - radius / 2 - 6, cx + 1, cy - radius / 2 + 6, style.accent);
      graphics.fill(cx - radius - 5, cy, cx + radius + 5, cy + 1, style.accent);
   }

   private void renderSkillSigil(GuiGraphics graphics, int cx, int cy, int size, float time, FeldwayRewardScreen.SkillStyle style, float hover) {
      switch (style.sigilType) {
         case 0:
            this.renderRonovaSigil(graphics, cx, cy, size, time, style, hover);
            break;
         case 1:
            this.renderNaberiusSigil(graphics, cx, cy, size, time, style, hover);
            break;
         case 2:
            this.renderIstarothSigil(graphics, cx, cy, size, time, style, hover);
            break;
         case 3:
            this.renderAsmodaySigil(graphics, cx, cy, size, time, style, hover);
            break;
         default:
            this.renderCaedrosSigil(graphics, cx, cy, size, time, style, hover);
      }
   }

   private void renderRonovaSigil(GuiGraphics graphics, int cx, int cy, int size, float time, FeldwayRewardScreen.SkillStyle style, float hover) {
      int pulse = 40 + (int)(hover * 80.0F);
      int soulColor = pulse << 24 | 15456511;

      for (int i = 0; i < 5; i++) {
         float a = time * 0.06F + i * (float) (Math.PI * 2.0 / 5.0);
         int sx = cx + (int)(Math.cos(a) * (size + 9));
         int sy = cy + (int)(Math.sin(a) * (size * 0.65F + 5.0F));
         graphics.fill(sx - 1, sy - 3, sx + 1, sy + 3, soulColor);
         graphics.fill(sx - 3, sy + 2, sx + 3, sy + 4, soulColor);
      }

      graphics.fill(cx - 2, cy - size - 2, cx + 2, cy + size + 4, style.accent);
      graphics.fill(cx - size - 6, cy - 2, cx + size + 6, cy + 1, style.accent);
      this.drawLine(graphics, cx, cy - size - 2, cx - size + 2, cy - size / 2, style.accentSoft);
      this.drawLine(graphics, cx, cy - size - 2, cx + size - 2, cy - size / 2, style.accentSoft);
      this.drawLine(graphics, cx - size + 2, cy - size / 2, cx - size - 5, cy + 1, style.accentSoft);
      this.drawLine(graphics, cx + size - 2, cy - size / 2, cx + size + 5, cy + 1, style.accentSoft);
      graphics.fill(cx - 6, cy + size - 4, cx + 6, cy + size - 1, style.accent);
      graphics.fill(cx - 9, cy + size - 1, cx + 9, cy + size + 2, style.accentSoft);
      int skullY = cy + 2;
      graphics.fill(cx - 7, skullY - 5, cx + 7, skullY + 6, -1451777);
      graphics.fill(cx - 9, skullY - 2, cx + 9, skullY + 4, -1451777);
      graphics.fill(cx - 5, skullY - 1, cx - 2, skullY + 2, -14151117);
      graphics.fill(cx + 2, skullY - 1, cx + 5, skullY + 2, -14151117);
      graphics.fill(cx - 1, skullY + 3, cx + 1, skullY + 6, -14151117);

      for (int i = -2; i <= 2; i++) {
         graphics.fill(cx + i * 3 - 1, skullY + 7, cx + i * 3 + 1, skullY + 10, -1451777);
      }
   }

   private void renderNaberiusSigil(GuiGraphics graphics, int cx, int cy, int size, float time, FeldwayRewardScreen.SkillStyle style, float hover) {
      for (int i = 0; i < 10; i++) {
         float a = -time * 0.04F + i * (float) (Math.PI / 5);
         int lx = cx + (int)(Math.cos(a) * (size + 10));
         int ly = cy + (int)(Math.sin(a) * (size * 0.7F + 7.0F));
         graphics.fill(lx - 2, ly - 1, lx + 2, ly + 1, -1434648652);
         graphics.fill(lx, ly - 3, lx + 1, ly + 3, 2011693033);
      }

      graphics.fill(cx - 3, cy - size + 2, cx + 3, cy + size + 6, style.accent);
      graphics.fill(cx - size - 6, cy - 4, cx + size + 6, cy + 4, style.accent);
      this.drawLine(graphics, cx, cy - 2, cx - size - 4, cy - size + 1, style.accentSoft);
      this.drawLine(graphics, cx, cy - 2, cx + size + 4, cy - size + 1, style.accentSoft);
      this.drawLine(graphics, cx, cy + 3, cx - size - 2, cy + size - 1, style.accentSoft);
      this.drawLine(graphics, cx, cy + 3, cx + size + 2, cy + size - 1, style.accentSoft);
      int heartY = cy - 1;
      graphics.fill(cx - 5, heartY - 4, cx - 1, heartY, -2347);
      graphics.fill(cx + 1, heartY - 4, cx + 5, heartY, -2347);
      graphics.fill(cx - 7, heartY, cx + 7, heartY + 5, -2347);
      graphics.fill(cx - 5, heartY + 5, cx + 5, heartY + 8, -2347);
      graphics.fill(cx - 2, heartY + 8, cx + 2, heartY + 11, -2347);
      graphics.fill(cx - 1, heartY - size - 4, cx + 1, heartY - size + 8, -4653104);
      graphics.fill(cx - 7, heartY - size + 1, cx + 7, heartY - size + 4, -8585292);

      for (int i = 0; i < 4; i++) {
         int rootX = cx - 12 + i * 8;
         this.drawLine(graphics, cx, cy + size, rootX, cy + size + 9, style.accentSoft);
      }
   }

   private void renderIstarothSigil(GuiGraphics graphics, int cx, int cy, int size, float time, FeldwayRewardScreen.SkillStyle style, float hover) {
      for (int ring = 0; ring < 3; ring++) {
         int r = size + 3 + ring * 5;
         int color = 70 - ring * 15 + (int)(hover * 35.0F) << 24 | style.accent & 16777215;
         this.drawRectRing(graphics, cx, cy, r, r / 2, color);
      }

      for (int i = 0; i < 12; i++) {
         float a = i * (float) (Math.PI / 6);
         int tx = cx + (int)(Math.cos(a) * (size + 9));
         int ty = cy + (int)(Math.sin(a) * ((size + 9) * 0.55F));
         graphics.fill(tx - 1, ty - 3, tx + 1, ty + 3, -855649852);
         graphics.fill(tx - 3, ty - 1, tx + 3, ty + 1, -1711287868);
      }

      int gearA = size + 2;

      for (int i = 0; i < 8; i++) {
         float a = time * 0.055F + i * (float) (Math.PI / 4);
         int gx = cx + (int)(Math.cos(a) * gearA);
         int gy = cy + (int)(Math.sin(a) * gearA * 0.55F);
         graphics.fill(gx - 2, gy - 2, gx + 2, gy + 2, style.accentSoft);
      }

      graphics.fill(cx - 1, cy - size - 3, cx + 1, cy + size + 3, style.accent);
      graphics.fill(cx - size - 3, cy - 1, cx + size + 3, cy + 1, style.accent);
      float handA = time * 0.07F;
      float handB = -time * 0.115F;
      float handC = time * 0.018F;
      this.drawLine(graphics, cx, cy, cx + (int)(Math.cos(handA) * (size * 0.65F)), cy + (int)(Math.sin(handA) * (size * 0.65F)), -1);
      this.drawLine(graphics, cx, cy, cx + (int)(Math.cos(handB) * (size * 0.95F)), cy + (int)(Math.sin(handB) * (size * 0.95F)), style.accent);
      this.drawLine(graphics, cx, cy, cx + (int)(Math.cos(handC) * (size * 1.15F)), cy + (int)(Math.sin(handC) * (size * 0.65F)), -6945);
      graphics.fill(cx - 3, cy - 3, cx + 3, cy + 3, -1);
      graphics.fill(cx - 1, cy - 1, cx + 1, cy + 1, style.accent);
   }

   private void renderAsmodaySigil(GuiGraphics graphics, int cx, int cy, int size, float time, FeldwayRewardScreen.SkillStyle style, float hover) {
      int corePulse = 5 + (int)(Mth.sin(time * 0.15F) * 2.0F + hover * 3.0F);
      graphics.fill(cx - corePulse, cy - corePulse, cx + corePulse, cy + corePulse, -16250344);
      graphics.fill(cx - corePulse + 2, cy - 1, cx + corePulse - 2, cy + 1, -1446401);
      graphics.fill(cx - 1, cy - corePulse + 2, cx + 1, cy + corePulse - 2, -1446401);

      for (int i = 0; i < 6; i++) {
         int r = size + i * 5;
         int color = 44 - i * 5 + (int)(hover * 20.0F) << 24 | style.accent & 16777215;
         this.drawRectRing(graphics, cx, cy, r, r / 2, color);
      }

      for (int i = 0; i < 12; i++) {
         float a = time * (0.065F + i * 0.002F) + i * (float) (Math.PI / 6);
         int ox = cx + (int)(Math.cos(a) * (size + 12 + i % 3 * 4));
         int oy = cy + (int)(Math.sin(a) * (size * 0.55F + 7.0F + i % 2 * 3));
         graphics.fill(ox - 2, oy - 2, ox + 2, oy + 2, i % 2 == 0 ? -2432001 : style.accent);
         this.drawLine(graphics, ox, oy, cx, cy, 573788630);
      }

      this.drawLine(graphics, cx, cy - size - 2, cx - size + 2, cy + 6, style.accent);
      this.drawLine(graphics, cx, cy - size - 2, cx + size - 2, cy + 6, style.accent);
      this.drawLine(graphics, cx - size + 2, cy + 6, cx + size - 2, cy + 6, style.accent);
      this.drawLine(graphics, cx, cy + size + 2, cx - size + 2, cy + 6, style.accentSoft);
      this.drawLine(graphics, cx, cy + size + 2, cx + size - 2, cy + 6, style.accentSoft);

      for (int i = 0; i < 4; i++) {
         int ray = size + 11 + i * 3;
         graphics.fill(cx - ray, cy - 1, cx - ray + 6, cy + 1, 2011819519);
         graphics.fill(cx + ray - 6, cy - 1, cx + ray, cy + 1, 2011819519);
      }
   }

   private void renderCaedrosSigil(GuiGraphics graphics, int cx, int cy, int size, float time, FeldwayRewardScreen.SkillStyle style, float hover) {
      graphics.fill(cx - 2, cy - size - 5, cx + 2, cy + size + 6, style.accent);
      graphics.fill(cx - size - 8, cy - 2, cx + size + 8, cy + 2, style.accentSoft);
      this.drawLine(graphics, cx - size - 2, cy + size + 4, cx, cy - 2, style.accent);
      this.drawLine(graphics, cx + size + 2, cy + size + 4, cx, cy - 2, style.accent);
      graphics.fill(cx - size - 4, cy + size + 1, cx - size + 6, cy + size + 5, -5171690);
      graphics.fill(cx + size - 6, cy + size + 1, cx + size + 4, cy + size + 5, -5171690);
      int crownY = cy - size - 2;
      graphics.fill(cx - 15, crownY + 9, cx + 15, crownY + 13, -10899);
      graphics.fill(cx - 12, crownY + 5, cx - 7, crownY + 10, -10899);
      graphics.fill(cx - 3, crownY, cx + 3, crownY + 12, -10899);
      graphics.fill(cx + 7, crownY + 5, cx + 12, crownY + 10, -10899);
      graphics.fill(cx - 2, crownY - 3, cx + 2, crownY + 1, -1);

      for (int i = 0; i < 6; i++) {
         int bx = cx - 20 + i * 8;
         int by = cy + 4 + (int)(Mth.sin(time * 0.1F + i) * 3.0F);
         graphics.fill(bx - 4, by, bx + 4, by + 2, -1431235050);
         graphics.fill(bx - 1, by - 7, bx + 1, by + 7, -1426074259);
      }

      for (int i = 0; i < 8; i++) {
         float a = time * 0.035F + i * (float) (Math.PI / 4);
         int sx = cx + (int)(Math.cos(a) * (size + 13));
         int sy = cy + (int)(Math.sin(a) * (size * 0.55F + 8.0F));
         graphics.fill(sx - 2, sy - 2, sx + 2, sy + 2, i % 2 == 0 ? -1426074259 : -1431235050);
      }
   }

   private void renderHoverFeathers(GuiGraphics graphics, int left, int top, int right, int bottom, float time, FeldwayRewardScreen.SkillStyle style) {
      for (int i = 0; i < 10; i++) {
         float p = (time * 0.04F + i * 0.17F) % 1.0F;
         int x = left + 10 + Math.floorMod(i * 22, Math.max(1, right - left - 20));
         int y = bottom - 14 - (int)(p * (bottom - top - 24));
         int alpha = (int)((1.0F - p) * 78.0F);
         int color = alpha << 24 | style.holyOrb & 16777215;
         this.drawLine(graphics, x, y, x + 4, y - 6, color);
         this.drawLine(graphics, x + 1, y - 2, x + 5, y - 4, color);
      }
   }

   private void renderSelectedBurst(GuiGraphics graphics, FeldwayRewardScreen.CardState card, float time) {
      float p = Mth.clamp(this.claimTicks / 14.0F, 0.0F, 1.0F);
      float appearBase = Mth.clamp((time - card.index * 3.0F) / 14.0F, 0.0F, 1.0F);
      float appear = this.easeOutBack(appearBase);
      int cx = (int)(card.x + card.startDx * (1.0F - appear) + card.w / 2.0F);
      int cy = (int)(card.y + card.startDy * (1.0F - appear) + card.h / 2.0F);
      int alpha = (int)((1.0F - Math.abs(p - 0.5F) * 1.6F) * 180.0F);
      int radius = 18 + (int)(p * 170.0F);
      graphics.fill(cx - radius, cy - 2, cx + radius, cy + 2, alpha << 24 | 16773557);
      graphics.fill(cx - 2, cy - radius, cx + 2, cy + radius, alpha << 24 | 16777215);

      for (int i = 0; i < 16; i++) {
         float a = i * (float) (Math.PI / 8) + time * 0.12F;
         int x = cx + (int)(Math.cos(a) * radius);
         int y = cy + (int)(Math.sin(a) * radius * 0.55F);
         graphics.fill(x - 2, y - 2, x + 2, y + 2, alpha << 24 | 16769696);
      }
   }

   private void renderTooltip(GuiGraphics graphics, FeldwayRewardScreen.CardState card, int mouseX, int mouseY) {
      FeldwayRewardScreen.SkillStyle style = this.getStyle(card.id);
      List<Component> tooltip = new ArrayList<>();
      tooltip.add(Component.literal(style.name + " — " + style.trueDomain).withStyle(ChatFormatting.GOLD));
      tooltip.add(Component.literal(style.tooltip).withStyle(ChatFormatting.WHITE));
      tooltip.add(Component.literal("Click to claim this authority.").withStyle(ChatFormatting.YELLOW));
      int maxWidth = 0;
      List<FormattedCharSequence> tooltipLines = new ArrayList<>();

      for (Component line : tooltip) {
         List<FormattedCharSequence> split = this.font.split(line, 220);
         tooltipLines.addAll(split);

         for (FormattedCharSequence seq : split) {
            maxWidth = Math.max(maxWidth, this.font.width(seq));
         }
      }

      int x = Math.min(mouseX + 12, this.width - maxWidth - 14);
      int y = Math.min(mouseY + 12, this.height - tooltipLines.size() * 11 - 10);
      int boxHeight = tooltipLines.size() * 11 + 6;
      graphics.fill(x - 4, y - 4, x + maxWidth + 8, y + boxHeight, -300610040);
      graphics.fill(x - 3, y - 3, x + maxWidth + 7, y - 1, style.border);
      graphics.fill(x - 3, y + boxHeight - 2, x + maxWidth + 7, y + boxHeight, style.border);
      graphics.fill(x - 3, y - 3, x - 1, y + boxHeight, style.border);
      graphics.fill(x + maxWidth + 5, y - 3, x + maxWidth + 7, y + boxHeight, style.border);
      int yy = y;

      for (FormattedCharSequence line : tooltipLines) {
         graphics.drawString(this.font, line, x, yy, -1, false);
         yy += 11;
      }
   }

   private void renderShineSweep(GuiGraphics graphics, int x, int y, int w, int h, float time, int index) {
      int sweep = (int)((time * 7.5F + index * 25.0F) % (w + h + 60)) - 30;

      for (int i = -8; i <= 8; i++) {
         int alpha = 10 - Math.abs(i) / 2;
         int color = Math.max(0, alpha) * 10 << 24 | 16777215;
         int sx = x + sweep + i * 3;
         this.drawLine(graphics, sx, y, sx + h / 2, y + h, color);
      }
   }

   private void drawScaledCenteredString(GuiGraphics graphics, Font font, Component text, int x, int y, int color, float scale) {
      graphics.pose().pushPose();
      graphics.pose().translate(x, y, 0.0F);
      graphics.pose().scale(scale, scale, 1.0F);
      int realX = Math.round(-font.width(text) / 2.0F);
      graphics.drawString(font, text, realX, 0, color, false);
      graphics.pose().popPose();
   }

   private boolean isMouseOver(int mouseX, int mouseY, float x, float y, int w, int h) {
      return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
   }

   private float easeOutBack(float x) {
      float c1 = 1.70158F;
      float c3 = c1 + 1.0F;
      return 1.0F + c3 * (float)Math.pow(x - 1.0F, 3.0) + c1 * (float)Math.pow(x - 1.0F, 2.0);
   }

   private void drawRectRing(GuiGraphics graphics, int cx, int cy, int rx, int ry, int color) {
      graphics.hLine(cx - rx, cx + rx, cy - ry, color);
      graphics.hLine(cx - rx, cx + rx, cy + ry, color);
      graphics.vLine(cx - rx, cy - ry, cy + ry, color);
      graphics.vLine(cx + rx, cy - ry, cy + ry, color);
   }

   private void drawLine(GuiGraphics graphics, int x1, int y1, int x2, int y2, int color) {
      int steps = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
      if (steps <= 0) {
         graphics.fill(x1, y1, x1 + 1, y1 + 1, color);
      } else {
         for (int i = 0; i <= steps; i++) {
            float t = (float)i / steps;
            int x = Mth.lerpInt(t, x1, x2);
            int y = Mth.lerpInt(t, y1, y2);
            graphics.fill(x, y, x + 1, y + 1, color);
         }
      }
   }

   private FeldwayRewardScreen.SkillStyle getStyle(ResourceLocation id) {
      String path = id.getPath();
      if (path.equals("ronova")) {
         return new FeldwayRewardScreen.SkillStyle(
            "Ronova",
            "Ruler of Death",
            "DEATH",
            "Death is due.",
            "Brings the target to its appointed end.",
            -10899,
            -11919004,
            -2008407708,
            -990977,
            -11919004,
            -14279892,
            -989481,
            -594211,
            -1997481729,
            0.95F,
            0,
            0.038F
         );
      } else if (path.equals("naberius")) {
         return new FeldwayRewardScreen.SkillStyle(
            "Naberius",
            "Ruler of Life",
            "LIFE",
            "Life takes form.",
            "Restores the body to a living state.",
            -10899,
            -13850518,
            -2010339222,
            -1572887,
            -13921443,
            -14996959,
            -989481,
            -594211,
            -1999634464,
            0.95F,
            1,
            0.049F
         );
      } else if (path.equals("istaroth")) {
         return new FeldwayRewardScreen.SkillStyle(
            "Istaroth",
            "Ruler of Time",
            "TIME",
            "Time resumes.",
            "Sets the target back into the chosen moment.",
            -10899,
            -3392709,
            -1999881413,
            -6945,
            -5625302,
            -14018022,
            -989481,
            -594211,
            -1996500540,
            0.95F,
            2,
            0.058F
         );
      } else {
         return path.equals("asmoday")
            ? new FeldwayRewardScreen.SkillStyle(
               "Asmoday",
               "Ruler of Space",
               "SPACE",
               "Space closes.",
               "Locks the target beyond reach.",
               -10899,
               -13413930,
               -2009902634,
               -1446401,
               -14072907,
               -14999248,
               -989481,
               -594211,
               -1999578113,
               0.95F,
               3,
               0.071F
            )
            : new FeldwayRewardScreen.SkillStyle(
               "Caedros",
               "Ruler of War",
               "WAR",
               "War is declared.",
               "Marks the target as the enemy to be overcome.",
               -10899,
               -5171690,
               -2001660394,
               -7203,
               -6544862,
               -13952743,
               -989481,
               -594211,
               -1996499603,
               0.9F,
               4,
               0.044F
            );
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && !this.cancelled) {
         int size = this.closeButtonSize();
         int x = this.closeButtonX(size);
         int y = this.closeButtonY(size);
         if (this.isMouseOver((int)mouseX, (int)mouseY, x, y, size, size)) {
            this.cancelRewardAndClose();
            return true;
         }
      }

      if (button == 0 && this.selectedSkill == null && !this.cancelled) {
         float time = this.tick;

         for (FeldwayRewardScreen.CardState card : this.cards) {
            float appearBase = Mth.clamp((time - card.index * 3.0F) / 14.0F, 0.0F, 1.0F);
            float appear = this.easeOutBack(appearBase);
            float realX = card.x + card.startDx * (1.0F - appear);
            float realY = card.y + card.startDy * (1.0F - appear);
            if (this.isMouseOver((int)mouseX, (int)mouseY, realX, realY, card.w, card.h)) {
               this.selectedSkill = card.id;
               this.selectedIndex = card.index;
               this.claimTicks = 0;
               return true;
            }
         }

         return super.mouseClicked(mouseX, mouseY, button);
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   private void cancelRewardAndClose() {
      if (!this.cancelled) {
         this.cancelled = true;
         this.selectedSkill = null;
         this.selectedIndex = -1;
         this.claimTicks = 0;
         NetworkManager.sendToServer(new ClaimFeldwayRewardPayload(CANCEL_REWARD_ID));
         if (this.minecraft != null) {
            this.minecraft.setScreen(null);
         }
      }
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   public void onClose() {
   }

   public boolean isPauseScreen() {
      return false;
   }

   private static final class CardState {
      private final ResourceLocation id;
      private final int x;
      private final int y;
      private final int w;
      private final int h;
      private final int index;
      private final float startDx;
      private final float startDy;
      private float hover;

      private CardState(ResourceLocation id, int x, int y, int w, int h, int index, float startDx, float startDy) {
         this.id = id;
         this.x = x;
         this.y = y;
         this.w = w;
         this.h = h;
         this.index = index;
         this.startDx = startDx;
         this.startDy = startDy;
      }
   }

   private static final class SkillStyle {
      private final String name;
      private final String domain;
      private final String trueDomain;
      private final String shortDescription;
      private final String tooltip;
      private final int border;
      private final int accent;
      private final int glow;
      private final int nameColor;
      private final int domainColor;
      private final int textColor;
      private final int cardBase;
      private final int cardInner;
      private final int holyOrb;
      private final float titleScale;
      private final int sigilType;
      private final float orbitSpeed;
      private final int accentSoft;

      private SkillStyle(
         String name,
         String domain,
         String trueDomain,
         String shortDescription,
         String tooltip,
         int border,
         int accent,
         int glow,
         int nameColor,
         int domainColor,
         int textColor,
         int cardBase,
         int cardInner,
         int holyOrb,
         float titleScale,
         int sigilType,
         float orbitSpeed
      ) {
         this.name = name;
         this.domain = domain;
         this.trueDomain = trueDomain;
         this.shortDescription = shortDescription;
         this.tooltip = tooltip;
         this.border = border;
         this.accent = accent;
         this.glow = glow;
         this.nameColor = nameColor;
         this.domainColor = domainColor;
         this.textColor = textColor;
         this.cardBase = cardBase;
         this.cardInner = cardInner;
         this.holyOrb = holyOrb;
         this.titleScale = titleScale;
         this.sigilType = sigilType;
         this.orbitSpeed = orbitSpeed;
         this.accentSoft = 0xFF000000 | (accent & 16711422) >> 1;
      }
   }
}
