package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.limbo.IWantLimboData;
import com.github.wal_bos.moreskills.limbo.IWantLimboMenu;
import com.github.wal_bos.moreskills.net.msg.IWantLimboRerollPayload;
import com.github.wal_bos.moreskills.net.msg.IWantLimboSelectPayload;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class IWantLimboScreen extends AbstractContainerScreen<IWantLimboMenu> {
   private static final int TEXT = -1186049;
   private static final int MUTED = -6450499;
   private static final int VOID = -100663296;
   private static IWantLimboScreen active;
   private static int pendingShuffleTicks;
   private static final int SHUFFLE_MAX_TICKS = 42;
   private float time;
   private boolean picked;
   private int pickedCard = -1;
   private int revealTicks;
   private int shuffleTicks;
   private ResourceLocation revealedSkill;

   public IWantLimboScreen(IWantLimboMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
      this.imageWidth = 466;
      this.imageHeight = 268;
      this.inventoryLabelY = 10000;
      this.titleLabelY = 10000;
   }

   public static void reveal(int cardId, ResourceLocation skill) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.screen instanceof IWantLimboScreen screen) {
         screen.picked = true;
         screen.pickedCard = cardId;
         screen.revealedSkill = skill;
         screen.revealTicks = 1;
         active = screen;
      } else if (active != null) {
         active.picked = true;
         active.pickedCard = cardId;
         active.revealedSkill = skill;
         active.revealTicks = 1;
      }
   }

   protected void init() {
      super.init();
      active = this;
      if (pendingShuffleTicks > 0) {
         this.shuffleTicks = pendingShuffleTicks;
         pendingShuffleTicks = 0;
      }
   }

   public boolean isPauseScreen() {
      return false;
   }

   protected void containerTick() {
      super.containerTick();
      if (this.revealTicks > 0) {
         this.revealTicks++;
      }

      if (this.shuffleTicks > 0) {
         this.shuffleTicks--;
      }
   }

   protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
      this.time += partialTick * 0.025F + 0.032F;
      graphics.fill(0, 0, this.width, this.height, -100663296);
      this.drawVoid(graphics);
      int x = this.leftPos;
      int y = this.topPos;
      graphics.fill(x, y, x + this.imageWidth, y + this.imageHeight, -670759920);
      graphics.fill(x + 5, y + 5, x + this.imageWidth - 5, y + this.imageHeight - 5, 1779107608);
      int pulse = 116 + (int)(Mth.sin(this.time * 1.35F) * 38.0F);
      graphics.fill(x + 10, y + 9, x + this.imageWidth - 10, y + 11, pulse << 24 | 7034344);
      graphics.fill(x + 10, y + this.imageHeight - 11, x + this.imageWidth - 10, y + this.imageHeight - 9, pulse << 24 | 7034344);
      graphics.drawCenteredString(this.font, Component.literal("LIMBO"), x + this.imageWidth / 2, y + 17, -1186049);
      graphics.drawCenteredString(
         this.font,
         Component.literal(
            this.revealTicks > 0
               ? "The chosen fate unveils itself."
               : (this.shuffleTicks > 0 ? "The unseen fates are rearranging." : "Choose only by colour. The truth is hidden.")
         ),
         x + this.imageWidth / 2,
         y + 33,
         -6450499
      );
      this.drawCards(graphics, mouseX, mouseY);
      this.drawRerollButton(graphics, mouseX, mouseY);
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      super.render(graphics, mouseX, mouseY, partialTick);
      this.renderTooltip(graphics, mouseX, mouseY);
   }

   private void drawVoid(GuiGraphics graphics) {
      for (int i = 0; i < 64; i++) {
         float wave = Mth.sin(this.time * 0.72F + i * 0.47F);
         int x = (int)((i * 71 + wave * 38.0F + this.width) % Math.max(1, this.width));
         int y = (int)((i * 43 + Mth.cos(this.time * 0.55F + i) * 26.0F + this.height) % Math.max(1, this.height));
         int a = 18 + (int)((wave * 0.5F + 0.5F) * 42.0F);
         graphics.fill(x - 1, y - 34, x + 1, y + 34, a << 24 | 2823756);
         graphics.fill(x - 34, y - 1, x + 34, y + 1, a << 24 | 2823756);
      }

      for (int i = 0; i < 18; i++) {
         float wave = Mth.sin(this.time * 0.95F + i);
         int cx = this.width / 2 + (int)(Mth.cos(this.time * 0.18F + i * 0.9F) * (62.0F + i * 4.0F));
         int cy = this.height / 2 + (int)(Mth.sin(this.time * 0.2F + i * 0.7F) * (34.0F + i * 2.0F));
         int a = 14 + (int)((wave * 0.5F + 0.5F) * 24.0F);
         graphics.fill(cx - 18, cy - 18, cx + 18, cy + 18, a << 24 | 1507362);
      }
   }

   private void drawCards(GuiGraphics graphics, int mouseX, int mouseY) {
      List<IWantLimboData.StoredCard> cards = ((IWantLimboMenu)this.menu).getCards();
      if (cards.isEmpty()) {
         graphics.drawCenteredString(this.font, Component.literal("No cards remain."), this.leftPos + this.imageWidth / 2, this.topPos + 108, -1186049);
      } else {
         int cardW = 64;
         int cardH = 104;
         int gap = 15;
         int total = cards.size() * cardW + Math.max(0, cards.size() - 1) * gap;
         int start = this.leftPos + (this.imageWidth - total) / 2;
         int y = this.topPos + 84;
         float shuffleProgress = this.shuffleTicks > 0 ? 1.0F - Mth.clamp(this.shuffleTicks / 42.0F, 0.0F, 1.0F) : 1.0F;
         boolean shuffling = this.shuffleTicks > 0;
         int centerX = this.leftPos + this.imageWidth / 2 - cardW / 2;
         int centerY = y + 8;

         for (int i = 0; i < cards.size(); i++) {
            IWantLimboData.StoredCard card = cards.get(i);
            if (this.revealTicks <= 0 || card.id() != this.pickedCard) {
               int x = start + i * (cardW + gap);
               boolean hover = !this.picked && this.shuffleTicks <= 0 && inside(mouseX, mouseY, x - 8, y - 10, cardW + 16, cardH + 20);
               float fade = this.revealTicks > 0 ? Mth.clamp(1.0F - this.revealTicks / 32.0F, 0.0F, 1.0F) : 1.0F;
               float scatter = this.revealTicks > 0 ? Mth.clamp(this.revealTicks / 32.0F, 0.0F, 1.0F) : 0.0F;
               float floatLift = Mth.sin(this.time * 0.95F + i * 0.62F) * (hover ? 2.2F : 1.15F);
               int targetX = x + (int)((i - cards.size() / 2.0F) * 22.0F * scatter);
               int targetY = y + (int)floatLift + (int)(32.0F * scatter);
               int xx = targetX;
               int yy = targetY;
               float cardFade = fade;
               if (shuffling) {
                  float p = ease(shuffleProgress);
                  float gather = Mth.clamp(p / 0.3F, 0.0F, 1.0F);
                  float spin = Mth.clamp((p - 0.22F) / 0.48F, 0.0F, 1.0F);
                  float deal = Mth.clamp((p - 0.58F) / 0.42F, 0.0F, 1.0F);
                  float stackX = Mth.lerp(gather, targetX, centerX);
                  float stackY = Mth.lerp(gather, targetY, centerY);
                  float angle = this.time * 5.2F + i * ((float) (Math.PI * 2) / Math.max(1, cards.size()));
                  float radius = Mth.sin(spin * (float) Math.PI) * (42.0F + i * 5.5F);
                  float swirlX = centerX + Mth.cos(angle) * radius;
                  float swirlY = centerY + Mth.sin(angle * 0.82F) * radius * 0.42F;
                  float mixedX = Mth.lerp(spin, stackX, swirlX);
                  float mixedY = Mth.lerp(spin, stackY, swirlY);
                  xx = (int)Mth.lerp(deal, mixedX, targetX);
                  yy = (int)Mth.lerp(deal, mixedY, targetY);
                  cardFade = fade * (0.7F + 0.3F * deal);
               }

               this.drawCardBack(graphics, card, xx, yy, cardW, cardH, hover, cardFade);
            }
         }

         if (this.revealTicks > 0) {
            for (IWantLimboData.StoredCard card : cards) {
               if (card.id() == this.pickedCard) {
                  this.drawRevealCard(graphics, card);
                  break;
               }
            }
         }
      }
   }

   private void drawRerollButton(GuiGraphics graphics, int mouseX, int mouseY) {
      if (!this.picked && this.revealTicks <= 0) {
         int left = this.leftPos + this.imageWidth / 2 - 54;
         int top = this.topPos + 214;
         int width = 108;
         int height = 22;
         boolean enabled = ((IWantLimboMenu)this.menu).getRerollsLeft() > 0 && this.shuffleTicks <= 0;
         boolean hover = enabled && inside(mouseX, mouseY, left, top, width, height);
         int border = hover ? -423647233 : -1435806232;
         int inner = enabled ? (hover ? -870903764 : -1207236840) : 1997540120;
         graphics.fill(left - 2, top - 2, left + width + 2, top + height + 2, border);
         graphics.fill(left, top, left + width, top + height, inner);
         graphics.drawCenteredString(
            this.font, Component.literal("Reroll: " + ((IWantLimboMenu)this.menu).getRerollsLeft()), left + width / 2, top + 7, enabled ? -1186049 : -6450499
         );
      }
   }

   private void drawCardBack(GuiGraphics graphics, IWantLimboData.StoredCard card, int x, int y, int cardW, int cardH, boolean hover, float fade) {
      int alpha = (int)(255.0F * Mth.clamp(fade, 0.0F, 1.0F));
      int glowAlpha = (int)((hover ? 172 : 74) * fade);
      int rarity = card.rarity().color();
      int glowColor = glowAlpha << 24 | rarity & 16777215;
      graphics.fill(x - 7, y - 7, x + cardW + 7, y + cardH + 7, glowColor);
      graphics.fill(x - 3, y - 3, x + cardW + 3, y + cardH + 3, (int)(alpha * 0.34F) << 24 | rarity & 16777215);
      graphics.fill(x, y, x + cardW, y + cardH, (int)(alpha * 0.92F) << 24 | (hover ? 1511468 : 526097));
      graphics.fill(x + 3, y + 3, x + cardW - 3, y + 20, alpha << 24 | rarity & 16777215);
      graphics.fill(x + 8, y + 32, x + cardW - 8, y + 34, (int)(alpha * 0.58F) << 24 | 16777215);
      graphics.fill(x + 14, y + 50, x + cardW - 14, y + 52, (int)(alpha * 0.39F) << 24 | 16777215);
      graphics.fill(x + 21, y + 68, x + cardW - 21, y + 70, (int)(alpha * 0.24F) << 24 | 16777215);
      graphics.drawCenteredString(this.font, Component.literal("?"), x + cardW / 2, y + 81, alpha << 24 | 15591167);
   }

   private void drawRevealCard(GuiGraphics graphics, IWantLimboData.StoredCard card) {
      float progress = Mth.clamp(this.revealTicks / 52.0F, 0.0F, 1.0F);
      float eased = ease(progress);
      float flip = Mth.sin(eased * (float) Math.PI);
      boolean face = progress > 0.52F;
      int cardW = 162;
      int cardH = 188;
      int cx = this.leftPos + this.imageWidth / 2;
      int cy = this.topPos + 137;
      int w = Math.max(12, (int)(cardW * Math.abs(Mth.cos(eased * (float) Math.PI))));
      int x = cx - w / 2;
      int y = cy - cardH / 2 + (int)(Mth.sin(this.time * 1.1F) * 1.25F);
      int glow = 104 + (int)(Mth.sin(this.time * 1.8F) * 34.0F) << 24 | card.rarity().color() & 16777215;
      graphics.fill(cx - cardW / 2 - 18, y - 18, cx + cardW / 2 + 18, y + cardH + 18, glow);

      for (int i = 0; i < 18; i++) {
         float a = this.time * 0.82F + i * 0.349F;
         int sx = cx + (int)(Mth.cos(a) * (94.0F + flip * 18.0F));
         int sy = cy + (int)(Mth.sin(a) * (78.0F + flip * 18.0F));
         int alpha = 46 + (int)(Mth.sin(a + this.time) * 18.0F);
         graphics.fill(sx - 2, sy - 2, sx + 2, sy + 2, alpha << 24 | card.rarity().color() & 16777215);
      }

      if (!face) {
         this.drawCardBack(graphics, card, x, y, w, cardH, false, 1.0F);
      } else {
         graphics.fill(x, y, x + w, y + cardH, -234158317);
         graphics.fill(x + 5, y + 5, x + w - 5, y + 27, card.rarity().color());
         graphics.fill(x + 8, y + 32, x + w - 8, y + cardH - 8, -804254947);
         ManasSkill skill = this.revealedSkill == null ? null : (ManasSkill)SkillAPI.getSkillRegistry().get(this.revealedSkill);
         if (skill != null && w > 90) {
            ResourceLocation icon = skill.getSkillIcon();
            if (icon != null) {
               graphics.blit(icon, cx - 26, y + 40, 0.0F, 0.0F, 52, 52, 52, 52);
            }

            graphics.drawCenteredString(this.font, skill.getName(), cx, y + 98, -1186049);
            Component desc = Component.translatable(this.revealedSkill.getNamespace() + ".skill." + this.revealedSkill.getPath() + ".description");
            int lineY = y + 114;

            for (FormattedCharSequence line : this.font.split(desc, 136)) {
               graphics.drawCenteredString(this.font, line, cx, lineY, -6450499);
               lineY += 10;
               if (lineY > y + cardH - 18) {
                  break;
               }
            }
         } else if (w > 90) {
            graphics.drawCenteredString(this.font, Component.literal("Unknown Skill"), cx, y + 82, -1186049);
            graphics.drawCenteredString(this.font, Component.literal("The card reveals nothing."), cx, y + 102, -6450499);
         }
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && !this.picked) {
         int rerollX = this.leftPos + this.imageWidth / 2 - 54;
         int rerollY = this.topPos + 214;
         if (((IWantLimboMenu)this.menu).getRerollsLeft() > 0 && this.shuffleTicks <= 0 && inside(mouseX, mouseY, rerollX, rerollY, 108, 22)) {
            this.shuffleTicks = 42;
            pendingShuffleTicks = 42;
            NetworkManager.sendToServer(new IWantLimboRerollPayload());
            return true;
         }

         if (this.shuffleTicks <= 0) {
            List<IWantLimboData.StoredCard> cards = ((IWantLimboMenu)this.menu).getCards();
            int cardW = 64;
            int cardH = 104;
            int gap = 15;
            int total = cards.size() * cardW + Math.max(0, cards.size() - 1) * gap;
            int start = this.leftPos + (this.imageWidth - total) / 2;
            int y = this.topPos + 84;

            for (int i = 0; i < cards.size(); i++) {
               int x = start + i * (cardW + gap);
               if (inside(mouseX, mouseY, x - 10, y - 14, cardW + 20, cardH + 28)) {
                  this.picked = true;
                  this.pickedCard = cards.get(i).id();
                  this.revealTicks = 1;
                  NetworkManager.sendToServer(new IWantLimboSelectPayload(cards.get(i).id()));
                  return true;
               }
            }
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   public void onClose() {
   }

   private static float ease(float value) {
      return value * value * (3.0F - 2.0F * value);
   }

   private static boolean inside(double mouseX, double mouseY, int x, int y, int w, int h) {
      return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
   }
}
