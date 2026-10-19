package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.casino.GreedCasinoMenu;
import com.github.wal_bos.moreskills.net.msg.GreedCasinoGamblePayload;
import com.github.wal_bos.moreskills.net.msg.GreedCasinoResultPayload;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class GreedCasinoScreen extends AbstractContainerScreen<GreedCasinoMenu> {
   private static final int SPIN_DURATION = 60;
   private int bet = 50;
   private boolean spinning = false;
   private int spinTicks = 0;
   private String spinText = "---";
   private int lastSentGame = -1;
   private Button bet50;
   private Button bet250;
   private Button bet1000;
   private Button slots;
   private Button roulette;
   private Button dbl;

   public GreedCasinoScreen(GreedCasinoMenu menu, Inventory inv, Component title) {
      super(menu, inv, title);
      this.imageWidth = 282;
      this.imageHeight = 176;
      this.inventoryLabelY = 10000;
      this.titleLabelY = 10000;
   }

   protected void init() {
      super.init();
      int pad = 12;
      int gap = 10;
      int topBarH = 36;
      int betY = this.topPos + topBarH + 2;
      int betH = 20;
      int totalW = this.imageWidth - pad * 2 - gap * 2;
      int btnW = totalW / 3;
      int x1 = this.leftPos + pad;
      int x2 = x1 + btnW + gap;
      int x3 = x2 + btnW + gap;
      this.bet50 = (Button)this.addRenderableWidget(
         Button.builder(Component.translatable("tensuramoreskills.gui.mammon_casino.bet.50"), b -> this.bet = 50).bounds(x1, betY, btnW, betH).build()
      );
      this.bet250 = (Button)this.addRenderableWidget(
         Button.builder(Component.translatable("tensuramoreskills.gui.mammon_casino.bet.250"), b -> this.bet = 250).bounds(x2, betY, btnW, betH).build()
      );
      this.bet1000 = (Button)this.addRenderableWidget(
         Button.builder(Component.translatable("tensuramoreskills.gui.mammon_casino.bet.1000"), b -> this.bet = 1000).bounds(x3, betY, btnW, betH).build()
      );
      int gameY = betY + betH + 34;
      int gameH = 20;
      this.slots = (Button)this.addRenderableWidget(
         Button.builder(Component.translatable("tensuramoreskills.gui.mammon_casino.game.slots"), b -> this.send(0)).bounds(x1, gameY, btnW, gameH).build()
      );
      this.roulette = (Button)this.addRenderableWidget(
         Button.builder(Component.translatable("tensuramoreskills.gui.mammon_casino.game.roulette"), b -> this.send(1)).bounds(x2, gameY, btnW, gameH).build()
      );
      this.dbl = (Button)this.addRenderableWidget(
         Button.builder(Component.translatable("tensuramoreskills.gui.mammon_casino.game.double"), b -> this.send(2)).bounds(x3, gameY, btnW, gameH).build()
      );
   }

   private void send(int game) {
      if (!this.spinning) {
         this.spinning = true;
         this.spinTicks = 0;
         this.lastSentGame = game;
         this.spinText = "---";
         GreedCasinoClientState.clear();
         NetworkManager.sendToServer(new GreedCasinoGamblePayload(game, this.bet));
      }
   }

   public void containerTick() {
      super.containerTick();
      if (this.spinning) {
         this.spinTicks++;
         if (this.spinTicks % 2 == 0) {
            this.spinText = this.randomSpinLine(this.lastSentGame);
         }

         if (this.spinTicks >= 60) {
            this.spinning = false;
            this.spinTicks = 0;
            if (GreedCasinoClientState.getLast() == null) {
               GreedCasinoClientState.setLast(new GreedCasinoResultPayload(false, "NO RESULT", ((GreedCasinoMenu)this.menu).getChipsClient(), 0, 0, ""));
            }
         }
      }
   }

   private String randomSpinLine(int game) {
      if (this.minecraft != null && this.minecraft.player != null) {
         int a = this.minecraft.player.getRandom().nextInt(10);
         int b = this.minecraft.player.getRandom().nextInt(10);
         int c = this.minecraft.player.getRandom().nextInt(10);
         if (game == 0) {
            return Component.translatable("tensuramoreskills.gui.mammon_casino.spin.slots", new Object[]{a, b, c}).getString();
         } else {
            return game == 1
               ? Component.translatable("tensuramoreskills.gui.mammon_casino.spin.roulette", new Object[]{this.minecraft.player.getRandom().nextInt(36) + 1})
                  .getString()
               : Component.translatable(
                     "tensuramoreskills.gui.mammon_casino.spin.double", new Object[]{this.minecraft.player.getRandom().nextBoolean() ? "WIN?" : "LOSE?"}
                  )
                  .getString();
         }
      } else {
         return "---";
      }
   }

   private double ultChance() {
      if (this.bet == 50) {
         return 0.002;
      } else {
         return this.bet == 250 ? 0.01 : 0.03;
      }
   }

   private double uniqueChance() {
      if (this.bet == 50) {
         return 0.04;
      } else {
         return this.bet == 250 ? 0.12 : 0.3;
      }
   }

   private double extraChance() {
      if (this.bet == 50) {
         return 3.0;
      } else {
         return this.bet == 250 ? 4.5 : 6.0;
      }
   }

   private double epChance() {
      if (this.bet == 50) {
         return 16.0;
      } else {
         return this.bet == 250 ? 18.0 : 20.0;
      }
   }

   private double mpChance() {
      if (this.bet == 50) {
         return 18.0;
      } else {
         return this.bet == 250 ? 19.0 : 20.0;
      }
   }

   private double loseChance() {
      if (this.bet == 50) {
         return 14.0;
      } else {
         return this.bet == 250 ? 12.5 : 11.0;
      }
   }

   private double commonSkillChance() {
      if (this.bet == 50) {
         return 7.0;
      } else {
         return this.bet == 250 ? 8.5 : 10.0;
      }
   }

   private double tempSkillChance() {
      if (this.bet == 50) {
         return 2.5;
      } else {
         return this.bet == 250 ? 3.5 : 4.0;
      }
   }

   private double itemChance() {
      double used = this.ultChance()
         + this.uniqueChance()
         + this.extraChance()
         + this.epChance()
         + this.mpChance()
         + this.commonSkillChance()
         + this.tempSkillChance()
         + this.loseChance();
      return Math.max(0.0, 100.0 - used);
   }

   private long epFromBet() {
      if (this.bet == 50) {
         return 8000L;
      } else {
         return this.bet == 250 ? 40000L : 175000L;
      }
   }

   private long mpFromBet() {
      if (this.bet == 50) {
         return 20000L;
      } else {
         return this.bet == 250 ? 100000L : 450000L;
      }
   }

   private int itemsFromBet() {
      if (this.bet == 50) {
         return 3;
      } else {
         return this.bet == 250 ? 15 : 65;
      }
   }

   private String pct(double v) {
      if (v < 0.05) {
         return "<0.1%";
      } else {
         return v < 10.0 ? String.format("%.1f%%", v) : String.format("%.0f%%", v);
      }
   }

   private static int mix(int c1, int c2, float t) {
      t = Mth.clamp(t, 0.0F, 1.0F);
      int a1 = c1 >>> 24 & 0xFF;
      int r1 = c1 >>> 16 & 0xFF;
      int g1 = c1 >>> 8 & 0xFF;
      int b1 = c1 & 0xFF;
      int a2 = c2 >>> 24 & 0xFF;
      int r2 = c2 >>> 16 & 0xFF;
      int g2 = c2 >>> 8 & 0xFF;
      int b2 = c2 & 0xFF;
      int a = (int)(a1 + (a2 - a1) * t);
      int r = (int)(r1 + (r2 - r1) * t);
      int g = (int)(g1 + (g2 - g1) * t);
      int b = (int)(b1 + (b2 - b1) * t);
      return a << 24 | r << 16 | g << 8 | b;
   }

   private void marquee(GuiGraphics g, int x1, int y1, int x2, int y2, int tick, int colorA, int colorB) {
      int step = 6;
      int phase = tick % (step * 2);

      for (int x = x1; x < x2; x += step) {
         boolean on = (x + phase) / step % 2 == 0;
         g.fill(x, y1, Math.min(x + step - 1, x2), y1 + 1, on ? colorA : colorB);
         g.fill(x, y2 - 1, Math.min(x + step - 1, x2), y2, on ? colorA : colorB);
      }

      for (int y = y1; y < y2; y += step) {
         boolean on = (y + phase) / step % 2 == 0;
         g.fill(x1, y, x1 + 1, Math.min(y + step - 1, y2), on ? colorA : colorB);
         g.fill(x2 - 1, y, x2, Math.min(y + step - 1, y2), on ? colorA : colorB);
      }
   }

   private void drawSidePanel(GuiGraphics g, int px, int py, int pw, int ph, float t, boolean left) {
      float s1 = 0.5F + 0.5F * Mth.sin(t * 0.08F + (left ? 0.0F : 1.1F));
      float s2 = 0.5F + 0.5F * Mth.sin(t * 0.13F + (left ? 2.1F : 0.4F));
      int bg = -871954672;
      int inner = mix(-871362523, -871753188, s2);
      int neon1 = mix(-14227752, -5157633, s1);
      int neon2 = mix(-15954040, -9819480, s1);
      g.fill(px, py, px + pw, py + ph, bg);
      g.fill(px + 2, py + 2, px + pw - 2, py + ph - 2, inner);
      g.fill(px + 1, py + 1, px + pw - 1, py + 2, neon2);
      g.fill(px + 1, py + ph - 2, px + pw - 1, py + ph - 1, neon2);
      g.fill(px + 1, py + 1, px + 2, py + ph - 1, neon2);
      g.fill(px + pw - 2, py + 1, px + pw - 1, py + ph - 1, neon2);
      int dotsA = mix(-16711696, -11414, s1);
      int dotsB = -15724520;
      int tick = this.minecraft != null && this.minecraft.player != null ? this.minecraft.player.tickCount : 0;
      this.marquee(g, px + 2, py + 2, px + pw - 2, py + ph - 2, tick, dotsA, dotsB);
      String head = left
         ? Component.translatable("tensuramoreskills.gui.mammon_casino.panel.odds").getString()
         : Component.translatable("tensuramoreskills.gui.mammon_casino.panel.payout").getString();
      int hx = px + (pw - this.font.width(head)) / 2;
      g.drawString(this.font, head, hx, py + 8, mix(-1, neon1, 0.35F), false);
      int y = py + 24;
      if (left) {
         g.drawString(this.font, Component.translatable("tensuramoreskills.gui.mammon_casino.label.ult").getString(), px + 8, y, -10040065, false);
         g.drawString(this.font, this.pct(this.ultChance()), px + pw - 8 - this.font.width(this.pct(this.ultChance())), y, -10040065, false);
         y += 12;
         g.drawString(this.font, Component.translatable("tensuramoreskills.gui.mammon_casino.label.unique").getString(), px + 8, y, -4718726, false);
         g.drawString(this.font, this.pct(this.uniqueChance()), px + pw - 8 - this.font.width(this.pct(this.uniqueChance())), y, -4718726, false);
         y += 12;
         g.drawString(this.font, Component.translatable("tensuramoreskills.gui.mammon_casino.label.extra").getString(), px + 8, y, -13974, false);
         g.drawString(this.font, this.pct(this.extraChance()), px + pw - 8 - this.font.width(this.pct(this.extraChance())), y, -13974, false);
         y += 18;
         g.drawString(this.font, Component.translatable("tensuramoreskills.gui.mammon_casino.label.temp").getString(), px + 8, y, -6572033, false);
         g.drawString(this.font, this.pct(this.tempSkillChance()), px + pw - 8 - this.font.width(this.pct(this.tempSkillChance())), y, -6572033, false);
         y += 12;
         g.drawString(this.font, Component.translatable("tensuramoreskills.gui.mammon_casino.label.common").getString(), px + 8, y, -5197648, false);
         g.drawString(this.font, this.pct(this.commonSkillChance()), px + pw - 8 - this.font.width(this.pct(this.commonSkillChance())), y, -5197648, false);
         y += 12;
         g.drawString(this.font, Component.translatable("tensuramoreskills.gui.mammon_casino.label.lose").getString(), px + 8, y, -38037, false);
         g.drawString(this.font, this.pct(this.loseChance()), px + pw - 8 - this.font.width(this.pct(this.loseChance())), y, -38037, false);
      } else {
         String ep = "+" + this.epFromBet();
         String mp = "+" + this.mpFromBet();
         String it = "x" + this.itemsFromBet();
         g.drawString(this.font, Component.translatable("tensuramoreskills.gui.mammon_casino.label.ep").getString(), px + 8, y, -8585289, false);
         g.drawString(this.font, this.pct(this.epChance()), px + pw - 8 - this.font.width(this.pct(this.epChance())), y, -8585289, false);
         y += 12;
         g.drawString(this.font, ep, px + pw - 8 - this.font.width(ep), y, -11678577, false);
         y += 16;
         g.drawString(this.font, Component.translatable("tensuramoreskills.gui.mammon_casino.label.mp").getString(), px + 8, y, -2643713, false);
         g.drawString(this.font, this.pct(this.mpChance()), px + pw - 8 - this.font.width(this.pct(this.mpChance())), y, -2643713, false);
         y += 12;
         g.drawString(this.font, mp, px + pw - 8 - this.font.width(mp), y, -4752385, false);
         y += 16;
         g.drawString(this.font, Component.translatable("tensuramoreskills.gui.mammon_casino.label.items").getString(), px + 8, y, -11414, false);
         g.drawString(this.font, this.pct(this.itemChance()), px + pw - 8 - this.font.width(this.pct(this.itemChance())), y, -11414, false);
         y += 12;
         g.drawString(this.font, it, px + pw - 8 - this.font.width(it), y, -13974, false);
      }
   }

   protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
      int x1 = this.leftPos;
      int y1 = this.topPos;
      int x2 = this.leftPos + this.imageWidth;
      int y2 = this.topPos + this.imageHeight;
      float t = this.minecraft != null && this.minecraft.player != null ? this.minecraft.player.tickCount + partialTick : partialTick;
      g.fill(x1, y1, x2, y2, -16382451);
      float glow = 0.5F + 0.5F * Mth.sin(t * 0.1F);
      float glow2 = 0.5F + 0.5F * Mth.sin(t * 0.17F + 1.1F);
      int goldA = (int)(110.0F + glow * 120.0F);
      int gold = goldA << 24 | 14069866;
      int neonA = (int)(70.0F + glow2 * 140.0F);
      int neon = neonA << 24 | 2549464;
      g.fill(x1 + 6, y1 + 6, x2 - 6, y1 + 7, gold);
      g.fill(x1 + 6, y2 - 7, x2 - 6, y2 - 6, gold);
      g.fill(x1 + 6, y1 + 6, x1 + 7, y2 - 6, gold);
      g.fill(x2 - 7, y1 + 6, x2 - 6, y2 - 6, gold);
      g.fill(x1 + 10, y1 + 10, x2 - 10, y1 + 58, -15790312);
      g.fill(x1 + 10, y1 + 64, x2 - 10, y2 - 10, -16119277);
      g.fill(x1 + 12, y1 + 60, x2 - 12, y1 + 61, neon);
      int tick = this.minecraft != null && this.minecraft.player != null ? this.minecraft.player.tickCount : 0;
      this.marquee(g, x1 + 10, y1 + 10, x2 - 10, y2 - 10, tick, mix(-16711696, -11414, glow), -15987694);
      int panelW = 108;
      int panelH = this.imageHeight - 16;
      int panelY = this.topPos + 8;
      int leftX = this.leftPos - panelW - 14;
      int rightX = this.leftPos + this.imageWidth + 14;
      leftX = Mth.clamp(leftX, 6, this.width - panelW - 6);
      rightX = Mth.clamp(rightX, 6, this.width - panelW - 6);
      boolean panelsFit = this.leftPos - panelW - 14 >= 0 && this.leftPos + this.imageWidth + 14 + panelW <= this.width;
      if (panelsFit || leftX + panelW + 6 < this.leftPos) {
         this.drawSidePanel(g, leftX, panelY, panelW, panelH, t, true);
      }

      if (panelsFit || rightX > this.leftPos + this.imageWidth - 6) {
         this.drawSidePanel(g, rightX, panelY, panelW, panelH, t, false);
      }

      int chips = ((GreedCasinoMenu)this.menu).getChipsClient();
      String title = Component.translatable("tensuramoreskills.gui.mammon_casino.title").getString();
      float titleWobble = 0.5F + 0.5F * Mth.sin(t * 0.2F);
      int titleCol = mix(-11414, -1, titleWobble * 0.25F);
      int tx = this.leftPos + (this.imageWidth - this.font.width(title)) / 2;
      g.drawString(this.font, title, tx, this.topPos + 14, titleCol, false);
      String chipsStr = Component.translatable("tensuramoreskills.gui.mammon_casino.chips", new Object[]{chips}).getString();
      g.drawString(this.font, chipsStr, this.leftPos + 14, this.topPos + 30, mix(-11414, -14227752, glow * 0.18F), false);
      String betStr = Component.translatable("tensuramoreskills.gui.mammon_casino.bet", new Object[]{this.bet}).getString();
      g.drawString(this.font, betStr, this.leftPos + this.imageWidth - 14 - this.font.width(betStr), this.topPos + 30, -5197648, false);
      int resY = this.topPos + 124;
      if (this.spinning) {
         g.drawString(
            this.font,
            Component.translatable("tensuramoreskills.gui.mammon_casino.spinning").getString(),
            this.leftPos + 14,
            resY,
            mix(-8913033, -1, glow * 0.25F),
            false
         );
         g.drawString(this.font, this.spinText, this.leftPos + 14, resY + 14, mix(-4718726, -14227752, glow2 * 0.25F), false);
      } else {
         GreedCasinoResultPayload last = GreedCasinoClientState.getLast();
         g.drawString(this.font, Component.translatable("tensuramoreskills.gui.mammon_casino.result").getString(), this.leftPos + 14, resY, -5197648, false);
         if (last != null) {
            int c = last.ok() ? -1 : -38037;
            g.drawString(this.font, last.title(), this.leftPos + 14, resY + 14, c, false);
            int y = resY + 28;
            if (last.epGain() > 0) {
               String s = Component.translatable("tensuramoreskills.gui.mammon_casino.gain.ep", new Object[]{last.epGain()}).getString();
               g.drawString(this.font, s, this.leftPos + 14, y, -8585289, false);
               y += 12;
            }

            if (last.mpGain() > 0) {
               String s = Component.translatable("tensuramoreskills.gui.mammon_casino.gain.mp", new Object[]{last.mpGain()}).getString();
               g.drawString(this.font, s, this.leftPos + 14, y, -2643713, false);
               y += 12;
            }

            if (!last.skillName().isEmpty()) {
               String s = Component.translatable("tensuramoreskills.gui.mammon_casino.gain.skill", new Object[]{last.skillName()}).getString();
               g.drawString(this.font, s, this.leftPos + 14, y, -10040065, false);
            }
         } else {
            g.drawString(this.font, "---", this.leftPos + 14, resY + 14, -8947849, false);
         }
      }
   }

   public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(g, mouseX, mouseY, partialTick);
      super.render(g, mouseX, mouseY, partialTick);
      this.renderTooltip(g, mouseX, mouseY);
      boolean active = !this.spinning;
      if (this.bet50 != null) {
         this.bet50.active = active;
      }

      if (this.bet250 != null) {
         this.bet250.active = active;
      }

      if (this.bet1000 != null) {
         this.bet1000.active = active;
      }

      if (this.slots != null) {
         this.slots.active = active;
      }

      if (this.roulette != null) {
         this.roulette.active = active;
      }

      if (this.dbl != null) {
         this.dbl.active = active;
      }
   }
}
