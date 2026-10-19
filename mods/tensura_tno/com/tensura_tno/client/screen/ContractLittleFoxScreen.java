package com.tensura_tno.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tensura_tno.entity.spirit.FoxSpiritEntity;
import com.tensura_tno.network.ContractLittleFoxPackets;
import com.tensura_tno.registry.TensuraTNOEntities;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.client.RenderHelper;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class ContractLittleFoxScreen extends Screen {
   private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("tensura_tno", "textures/gui/contract_little_fox.png");
   private static final ResourceLocation ABILITY_BAR = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/ability_button.png");
   private static final int IMG_W = 233;
   private static final int IMG_H = 140;
   private static final int BUTTON_SIZE = 20;
   private static final int CLICK_COOLDOWN_TICKS = 60;
   private static final int STATE_NONE = 0;
   private static final int STATE_ALIVE = 1;
   private static final int STATE_RECALLED = 2;
   private ContractLittleFoxPackets.OpenPanelPayload initial;
   private int leftPos;
   private int topPos;
   private long cooldownBaselineGameTime;
   private final List<ContractLittleFoxScreen.ListEntry> entries = List.of(
      new ContractLittleFoxScreen.ListEntry("tensura_tno:fox_spirit", Component.translatable("entity.tensura_tno.fox_spirit"))
   );
   private int selectedIndex = 0;
   private FoxSpiritEntity previewFox;
   private boolean foxPreviouslyFound = false;
   private long foxDeathDetectedGameTime = -1L;
   private static final int CLIENT_DEATH_COOLDOWN_SECONDS = 60;
   private static long buttonClickGameTime = -1L;
   private long payloadTrustUntilGameTime = -1L;
   private static final int PAYLOAD_TRUST_TICKS = 10;

   public ContractLittleFoxScreen(ContractLittleFoxPackets.OpenPanelPayload initial) {
      super(Component.translatable("tensura_tno.contract_little_fox.title"));
      this.initial = initial;
      Minecraft mc = Minecraft.getInstance();
      long now = mc.level != null ? mc.level.getGameTime() : 0L;
      this.payloadTrustUntilGameTime = now + 10L;
   }

   public void updatePayload(ContractLittleFoxPackets.OpenPanelPayload newPayload) {
      this.initial = newPayload;
      Minecraft mc = Minecraft.getInstance();
      long now = mc.level != null ? mc.level.getGameTime() : 0L;
      this.cooldownBaselineGameTime = now;
      this.foxPreviouslyFound = false;
      this.foxDeathDetectedGameTime = -1L;
      this.payloadTrustUntilGameTime = now + 10L;
   }

   protected void init() {
      super.init();
      this.leftPos = (this.width - 233) / 2;
      this.topPos = (this.height - 140) / 2;
      Minecraft mc = Minecraft.getInstance();
      this.cooldownBaselineGameTime = mc.level != null ? mc.level.getGameTime() : 0L;
   }

   public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
      super.render(gfx, mouseX, mouseY, partialTick);
      int x = this.leftPos;
      int y = this.topPos;
      gfx.blit(BACKGROUND, x, y, 0, 0, 233, 140);
      RenderHelper.drawCenteredText(gfx, this.font, this.title, this.leftPos + 56, this.topPos + 5, 16777215, false);
      this.renderListRows(gfx, mouseX, mouseY);
      this.renderToggleButton(gfx, mouseX, mouseY);
      this.renderFoxPreview(gfx);
      this.renderStatPanel(gfx);
   }

   private void renderListRows(GuiGraphics gfx, int mX, int mY) {
      for (int i = 0; i < this.entries.size(); i++) {
         int rx = this.leftPos + 6;
         int ry = this.topPos + 43 + i * 13;
         boolean hovered = mX >= rx && mY >= ry && mX < rx + 89 && mY < ry + 13;
         boolean selected = i == this.selectedIndex;
         int vOff = !hovered && !selected ? 0 : 13;
         gfx.blit(ABILITY_BAR, rx, ry, 0.0F, vOff, 89, 13, 89, 26);
         ContractLittleFoxScreen.ListEntry entry = this.entries.get(i);
         MutableComponent name = (MutableComponent)entry.name;
         int color = selected ? 16776960 : 16777215;
         RenderHelper.drawShortenedTextWithTooltip(gfx, this.font, name, name, rx, ry, 3, 3, 88, 13, mX, mY, color, true, this);
      }
   }

   private void renderFoxPreview(GuiGraphics gfx) {
      LivingEntity entity = this.getOrCreatePreviewFox();
      if (entity != null) {
         entity.setYBodyRot(150.0F);
         entity.setYRot(150.0F);
         entity.setXRot(0.0F);
         float scale = 20.0F / Math.max(entity.getBbWidth(), entity.getBbHeight());
         scale = Math.min(scale, 18.0F);
         RenderHelper.renderEntityInInventoryFollowsMouse(
            gfx, this.leftPos + 146, this.topPos + 4, this.leftPos + 196, this.topPos + 42, scale, this.leftPos + 168, this.topPos + 23, false, entity
         );
      }
   }

   private LivingEntity getOrCreatePreviewFox() {
      if (this.previewFox != null) {
         return this.previewFox;
      }

      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         return null;
      }

      try {
         this.previewFox = (FoxSpiritEntity)((EntityType)TensuraTNOEntities.FOX_SPIRIT.get()).create(mc.level);
      } catch (Exception ignored) {
         this.previewFox = null;
      }

      return this.previewFox;
   }

   private int getButtonX() {
      return this.leftPos + 162;
   }

   private int getButtonY() {
      return this.topPos + 116;
   }

   private void renderToggleButton(GuiGraphics gfx, int mX, int mY) {
      int bx = this.getButtonX();
      int by = this.getButtonY();
      boolean hovered = mX >= bx && mX < bx + 20 && mY >= by && mY < by + 20;
      boolean onCooldown = this.isButtonOnCooldown();
      if (hovered && !onCooldown) {
         gfx.blit(BACKGROUND, bx, by, 0, 140, 20, 20);
      }

      if (onCooldown) {
         gfx.fill(bx, by, bx + 20, by + 20, Integer.MIN_VALUE);
      }

      if (hovered) {
         Component tooltip = this.isFoxAlive()
            ? Component.translatable("tensura_tno.contract_little_fox.recall")
            : Component.translatable("tensura_tno.contract_little_fox.summon");
         this.setTooltipForNextRenderPass(tooltip);
      }
   }

   private boolean isButtonOnCooldown() {
      if (buttonClickGameTime < 0L) {
         return false;
      }

      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         return false;
      }

      long elapsed = mc.level.getGameTime() - buttonClickGameTime;
      return elapsed < 60L;
   }

   private boolean isFoxAlive() {
      return this.getCurrentState() == 1;
   }

   private boolean canSummon() {
      return this.getCurrentState() != 1 && this.getLiveCooldownSeconds() <= 0;
   }

   private boolean canRecall() {
      return this.getCurrentState() == 1 && this.findLocalFox() != null;
   }

   private int getCurrentState() {
      if (this.initial.state() == 1) {
         FoxSpiritEntity fox = this.findLocalFox();
         if (fox != null) {
            this.foxPreviouslyFound = true;
            return 1;
         }

         if (this.foxPreviouslyFound && this.foxDeathDetectedGameTime < 0L) {
            Minecraft mc = Minecraft.getInstance();
            this.foxDeathDetectedGameTime = mc.level != null ? mc.level.getGameTime() : this.cooldownBaselineGameTime;
         }

         return 0;
      } else {
         return this.initial.state();
      }
   }

   private void renderStatPanel(GuiGraphics gfx) {
      int px = this.leftPos + 125;
      int py = this.topPos + 48;
      int cd = this.getLiveCooldownSeconds();
      Component title;
      if (cd > 0) {
         title = Component.translatable("tensura_tno.contract_little_fox.status.cooldown", new Object[]{cd}).withStyle(ChatFormatting.RED);
      } else {
         title = switch (this.getCurrentState()) {
            case 1 -> Component.translatable("tensura_tno.contract_little_fox.status.alive").withStyle(ChatFormatting.GREEN);
            case 2 -> Component.translatable("tensura_tno.contract_little_fox.status.recalled").withStyle(ChatFormatting.AQUA);
            default -> Component.translatable("tensura_tno.contract_little_fox.status.none").withStyle(ChatFormatting.GRAY);
         };
      }

      gfx.drawString(this.font, title, px, py, 16777215, false);
      ContractLittleFoxScreen.Stats s = this.readDisplayStats();
      this.drawStatBar(gfx, px, py + 12, 90, "HP", s.hp, s.maxHp, -44976);
      this.drawStatBar(gfx, px, py + 26, 90, "EP", s.ep(), s.maxEp(), -8090);
      this.drawStatBar(gfx, px, py + 40, 90, "MP", s.mp, s.maxMp, -10053121);
      if (this.getCurrentState() == 1) {
         FoxSpiritEntity fox = this.findLocalFox();
         if (fox != null) {
            IExistence ex = TensuraStorages.getExistenceFrom(fox);
            this.drawStatBar(
               gfx, px, py + 54, 90, "SHP", (float)ex.getSpiritualHealth(), (float)fox.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH), -5736961
            );
         }
      }
   }

   private ContractLittleFoxScreen.Stats readDisplayStats() {
      if (this.getCurrentState() == 1) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && mc.level.getGameTime() < this.payloadTrustUntilGameTime) {
            return new ContractLittleFoxScreen.Stats(
               this.initial.foxHp(),
               this.initial.foxMaxHp(),
               this.initial.foxAura(),
               this.initial.foxMaxAura(),
               this.initial.foxMagic(),
               this.initial.foxMaxMagic()
            );
         }

         FoxSpiritEntity fox = this.findLocalFox();
         if (fox != null) {
            IExistence ex = TensuraStorages.getExistenceFrom(fox);
            return new ContractLittleFoxScreen.Stats(
               fox.getHealth(),
               fox.getMaxHealth(),
               (float)ex.getAura(),
               (float)EnergyHelper.getBaseMaxAura(fox),
               (float)ex.getMagicule(),
               (float)EnergyHelper.getBaseMaxMagicule(fox)
            );
         }
      }

      if (this.getCurrentState() == 2) {
         float hp = this.initial.foxHp();
         float maxHp = this.initial.foxMaxHp();
         float aura = this.initial.foxAura();
         float maxAura = this.initial.foxMaxAura();
         float magic = this.initial.foxMagic();
         float maxMagic = this.initial.foxMaxMagic();
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null) {
            long elapsed = mc.level.getGameTime() - this.cooldownBaselineGameTime;
            float secondsSinceOpen = (float)elapsed / 20.0F;
            float regenRate = 0.02F * secondsSinceOpen;
            if (hp < maxHp) {
               hp = Math.min(maxHp, hp + maxHp * regenRate);
            }

            if (aura < maxAura) {
               aura = Math.min(maxAura, aura + maxAura * regenRate);
            }

            if (magic < maxMagic) {
               magic = Math.min(maxMagic, magic + maxMagic * regenRate);
            }
         }

         return new ContractLittleFoxScreen.Stats(hp, maxHp, aura, maxAura, magic, maxMagic);
      } else {
         LocalPlayer p = Minecraft.getInstance().player;
         if (p == null) {
            return new ContractLittleFoxScreen.Stats(
               this.initial.foxHp(),
               this.initial.foxMaxHp(),
               this.initial.foxAura(),
               this.initial.foxMaxAura(),
               this.initial.foxMagic(),
               this.initial.foxMaxMagic()
            );
         }

         IExistence pe = TensuraStorages.getExistenceFrom(p);
         return new ContractLittleFoxScreen.Stats(
            p.getHealth() * 0.5F,
            p.getMaxHealth() * 0.5F,
            (float)pe.getAura() * 0.5F,
            (float)EnergyHelper.getBaseMaxAura(p) * 0.5F,
            (float)pe.getMagicule() * 0.5F,
            (float)EnergyHelper.getBaseMaxMagicule(p) * 0.5F
         );
      }
   }

   private void drawStatBar(GuiGraphics gfx, int x, int y, int width, String label, float value, float max, int barColorARGB) {
      gfx.drawString(this.font, label + ":", x, y, -1, false);
      int barX = x + 22;
      int barY = y;
      int barW = width - 22;
      gfx.fill(barX, barY, barX + barW, barY + 7, -13421773);
      if (max > 0.0F) {
         int filled = Math.round(barW * Math.min(1.0F, value / max));
         if (filled > 0) {
            gfx.fill(barX, barY, barX + filled, barY + 7, barColorARGB);
         }
      }

      String text = String.format("%s / %s", fmt(value), fmt(max));
      gfx.drawString(this.font, text, barX + 1, barY - 1, -1, true);
      RenderSystem.disableBlend();
   }

   private static String fmt(float v) {
      if (v >= 1.0E9F) {
         return stripTrailingZero(v / 1.0E9F) + "G";
      }

      if (v >= 1000000.0F) {
         return stripTrailingZero(v / 1000000.0F) + "M";
      }

      if (v >= 1000.0F) {
         return stripTrailingZero(v / 1000.0F) + "k";
      }

      long lv = (long)v;
      return (float)lv == v ? String.valueOf(lv) : String.format("%.1f", v);
   }

   private static String stripTrailingZero(double v) {
      long lv = (long)v;
      return lv == v ? String.valueOf(lv) : String.format("%.1f", v);
   }

   public boolean mouseClicked(double x, double y, int button) {
      int bx = this.getButtonX();
      int by = this.getButtonY();
      if (button == 0 && x >= bx && y >= by && x < bx + 20 && y < by + 20 && !this.isButtonOnCooldown()) {
         if (this.isFoxAlive() && this.canRecall()) {
            this.sendAction(1);
            this.startButtonCooldown();
            return true;
         }

         if (!this.isFoxAlive() && this.canSummon()) {
            this.sendAction(0);
            this.startButtonCooldown();
            return true;
         }
      }

      for (int i = 0; i < this.entries.size(); i++) {
         int rx = this.leftPos + 6;
         int ry = this.topPos + 43 + i * 13;
         if (x >= rx && y >= ry && x < rx + 89 && y < ry + 13) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            this.selectedIndex = i;
            return true;
         }
      }

      return super.mouseClicked(x, y, button);
   }

   private void startButtonCooldown() {
      Minecraft mc = Minecraft.getInstance();
      buttonClickGameTime = mc.level != null ? mc.level.getGameTime() : 0L;
   }

   private void sendAction(int action) {
      Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      NetworkManager.sendToServer(new ContractLittleFoxPackets.ActionPayload(action));
   }

   public boolean isPauseScreen() {
      return false;
   }

   private int getLiveCooldownSeconds() {
      if (this.foxDeathDetectedGameTime >= 0L) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level == null) {
            return 60;
         }

         long elapsed = mc.level.getGameTime() - this.foxDeathDetectedGameTime;
         int elapsedSec = (int)(elapsed / 20L);
         return Math.max(0, 60 - elapsedSec);
      } else {
         int start = this.initial.summonCooldownSeconds();
         if (start <= 0) {
            return 0;
         }

         Minecraft mc = Minecraft.getInstance();
         if (mc.level == null) {
            return start;
         }

         long elapsedTicks = mc.level.getGameTime() - this.cooldownBaselineGameTime;
         int elapsedSec = (int)(elapsedTicks / 20L);
         return Math.max(0, start - elapsedSec);
      }
   }

   private FoxSpiritEntity findLocalFox() {
      UUID id = this.initial.foxUuid();
      if (id != null && (id.getMostSignificantBits() != 0L || id.getLeastSignificantBits() != 0L)) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level == null) {
            return null;
         }

         for (Entity e : mc.level.entitiesForRendering()) {
            if (e instanceof FoxSpiritEntity fox && fox.getUUID().equals(id)) {
               return fox;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private record ListEntry(String id, Component name) {
   }

   private record Stats(float hp, float maxHp, float aura, float maxAura, float mp, float maxMp) {
      float ep() {
         return this.aura + this.mp;
      }

      float maxEp() {
         return this.maxAura + this.maxMp;
      }
   }
}
