package com.trbeyond.tensura_better_subs.client.screen;

import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import io.github.manasmods.tensura.client.screen.widgets.SimpleButton;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.client.RenderHelper;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.awt.Color;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket.Action;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class MainScreenSubordinate extends SimpleScreen {
   public static final Component TITLE = ScreenHelperSubordinates.getMenuName(4);
   protected final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/main/main_gui.png");
   protected IExistence existenceData;
   protected Races raceData;
   LivingEntity targetSubordinate;

   public MainScreenSubordinate(LivingEntity targetSubordinate) {
      super(TITLE, 256, 163);
      this.targetSubordinate = targetSubordinate;
      this.existenceData = TensuraStorages.getExistenceFrom(this.targetSubordinate);
      this.raceData = RaceAPI.getRaceFrom(this.targetSubordinate);
   }

   protected void init() {
      super.init();
      if (this.minecraft != null && this.minecraft.getConnection() != null) {
         this.minecraft.getConnection().send(new ServerboundClientCommandPacket(Action.REQUEST_STATS));
      }

      List<SimpleButton> tabs = ScreenHelperSubordinates.getMenuTabs(4, guiLeft, guiTop, this.targetSubordinate);
      tabs.forEach(x$0 -> {
         SimpleButton button = (SimpleButton)this.addRenderableWidget(x$0);
      });
   }

   public void renderBackground(GuiGraphics graphics, int mX, int mY, float partialTick) {
      if (!ScreenHelperSubordinates.isScreenValid(this.targetSubordinate, this.player)) {
         this.minecraft.setScreen(null);
      }

      RenderHelper.drawCenteredText(graphics, this.font, TITLE, guiLeft + 80, guiTop + 10, 122, 16777215);
      graphics.blit(this.BACKGROUND, guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);
      super.renderWidgets(graphics, mX, mY, partialTick);
      ScreenHelper.renderTabIcon(graphics, this, 4, guiLeft, guiTop, mX, mY);
      float sizeMultiplier = (float)Math.max(this.targetSubordinate.getBbHeight() / 1.8, this.targetSubordinate.getBbWidth() / 0.6);
      sizeMultiplier = sizeMultiplier > 1.0F ? 1.0F / sizeMultiplier : 1.0F;
      RenderHelper.renderEntityInInventoryFollowsMouse(
         graphics, guiLeft + 6, guiTop + 55, guiLeft + 54, guiTop + 126, 30.0F * sizeMultiplier, mX, mY, false, this.targetSubordinate
      );
      this.renderPlayerStats(graphics, mX, mY);
      this.renderRaceStats(graphics, mX, mY);
   }

   protected void renderPlayerStats(GuiGraphics graphics, int mX, int mY) {
      Component name = (Component)(this.existenceData.getName() == null ? this.targetSubordinate.getName() : Component.literal(this.existenceData.getName()));
      int nameColor = Color.WHITE.getRGB();
      if (this.existenceData.isTrueDemonLord()) {
         nameColor = 11141375;
      } else if (this.existenceData.isTrueHero()) {
         nameColor = 16766720;
      }

      RenderHelper.drawCenteredText(graphics, this.font, name, guiLeft + 6, guiTop + 35, 72, nameColor, true);
   }

   protected void renderRaceStats(GuiGraphics graphics, int mX, int mY) {
      Color color = Color.white;
      double mana = this.existenceData.getMagicule();
      double aura = this.existenceData.getAura();
      double health = this.targetSubordinate.getHealth();
      double spiritualHP = this.existenceData.getSpiritualHealth();
      double maxMana = EnergyHelper.getMaxMagicule(this.targetSubordinate);
      double maxAura = EnergyHelper.getMaxAura(this.targetSubordinate);
      double maxHealth = this.targetSubordinate.getMaxHealth();
      double maxSpiritualHP = this.targetSubordinate.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
      graphics.drawString(this.font, this.targetSubordinate.getType().getDescription(), guiLeft + 95, guiTop + 36, color.getRGB());
      graphics.drawString(
         this.font,
         Component.translatable("tensura.main_menu.souls", new Object[]{this.existenceData.getSoulPoints() / 100 / 10.0}),
         guiLeft + 95,
         guiTop + 52,
         Color.WHITE.getRGB()
      );
      RenderHelper.drawTextWithTooltip(
         graphics,
         this.font,
         Component.translatable("tensura.main_menu.existence_points", new Object[]{RenderHelper.getShortenedNumber(maxMana + maxAura)}).getString(),
         guiLeft + 13,
         guiTop + 144,
         Color.ORANGE.getRGB(),
         mX,
         mY,
         Component.translatable("tensura.main_menu.existence_points", new Object[]{Math.round(maxMana + maxAura)}),
         this
      );
      RenderHelper.drawTextWithTooltip(
         graphics,
         this.font,
         Component.translatable("tensura.main_menu.magicule", new Object[]{RenderHelper.getShortenedNumber(mana), RenderHelper.getShortenedNumber(maxMana)})
            .getString(),
         guiLeft + 95,
         guiTop + 78,
         Color.WHITE.getRGB(),
         mX,
         mY,
         Component.translatable("tensura.main_menu.magicule", new Object[]{Math.round(mana), Math.round(maxMana)}),
         this
      );
      RenderHelper.drawTextWithTooltip(
         graphics,
         this.font,
         Component.translatable("tensura.main_menu.aura", new Object[]{RenderHelper.getShortenedNumber(aura), RenderHelper.getShortenedNumber(maxAura)})
            .getString(),
         guiLeft + 95,
         guiTop + 99,
         Color.WHITE.getRGB(),
         mX,
         mY,
         Component.translatable("tensura.main_menu.aura", new Object[]{Math.round(aura), Math.round(maxAura)}),
         this
      );
      RenderHelper.drawTextWithTooltip(
         graphics,
         this.font,
         Component.translatable(
               "tensura_better_subs.main_menu.health", new Object[]{RenderHelper.getShortenedNumber(health), RenderHelper.getShortenedNumber(maxHealth)}
            )
            .getString(),
         guiLeft + 95,
         guiTop + 120,
         Color.WHITE.getRGB(),
         mX,
         mY,
         Component.translatable("tensura_better_subs.main_menu.health", new Object[]{Math.round(health), Math.round(maxHealth)}),
         this
      );
      RenderHelper.drawTextWithTooltip(
         graphics,
         this.font,
         Component.translatable(
               "tensura_better_subs.main_menu.spiritual_health.short",
               new Object[]{RenderHelper.getShortenedNumber(spiritualHP), RenderHelper.getShortenedNumber(maxSpiritualHP)}
            )
            .getString(),
         guiLeft + 95,
         guiTop + 141,
         Color.WHITE.getRGB(),
         mX,
         mY,
         Component.translatable("tensura_better_subs.main_menu.spiritual_health", new Object[]{Math.round(spiritualHP), Math.round(maxSpiritualHP)}),
         this
      );
   }
}
