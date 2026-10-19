package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.menu.VoidArchiveForgeMenu;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class VoidArchiveForgeScreen extends AbstractContainerScreen<VoidArchiveForgeMenu> {
   private static final int SHAPE_VISIBLE_ROWS = 3;
   private final List<Button> dynamicButtons = new ArrayList<>();
   private int lastUiState = Integer.MIN_VALUE;
   private int shapeScroll = 0;

   public VoidArchiveForgeScreen(VoidArchiveForgeMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
      this.imageWidth = 448;
      this.imageHeight = 270;
      this.inventoryLabelY = 10000;
   }

   protected void init() {
      super.init();
      this.clampShapeScroll();
      this.rebuildWidgets();
      this.lastUiState = this.captureUiState();
   }

   public void containerTick() {
      super.containerTick();
      this.clampShapeScroll();
      int currentState = this.captureUiState();
      if (currentState != this.lastUiState) {
         this.rebuildWidgets();
         this.lastUiState = currentState;
      }
   }

   protected void rebuildWidgets() {
      this.clearDynamicButtons();
      int x = this.leftPos;
      int y = this.topPos;
      if (((VoidArchiveForgeMenu)this.menu).getPage() == 0) {
         this.buildHomePage(x, y);
      } else {
         this.addButton("Back", x + 14, y + 12, 54, 20, 19);
         this.addButton("Reset", x + this.imageWidth - 74, y + 12, 60, 20, 20);
         switch (((VoidArchiveForgeMenu)this.menu).getPage()) {
            case 1:
               this.buildShapePage(x, y);
               break;
            case 2:
               this.buildCastPage(x, y);
               break;
            case 3:
               this.buildDamagePage(x, y);
               break;
            case 4:
               this.buildEffectPage(x, y);
               break;
            case 5:
               this.buildParticlePage(x, y);
               break;
            case 6:
               this.buildSummonPage(x, y);
               break;
            case 7:
               this.buildSpecialPage(x, y);
               break;
            case 8:
               this.buildMeleePage(x, y);
               break;
            case 9:
               this.buildReviewPage(x, y);
               break;
            default:
               this.buildHomePage(x, y);
         }
      }
   }

   private void buildHomePage(int x, int y) {
      this.addButton("Reset", x + this.imageWidth - 74, y + 12, 60, 20, 20);
      this.addButton("Next Name", x + 24, y + 48, 128, 22, 600);
      this.addButton("Shape", x + 24, y + 86, 120, 24, 10);
      this.addButton("Cast", x + 152, y + 86, 120, 24, 11);
      this.addButton("Damage", x + 280, y + 86, 120, 24, 12);
      this.addButton("Effect", x + 24, y + 118, 120, 24, 13);
      this.addButton("Particle", x + 152, y + 118, 120, 24, 14);
      this.addButton("Summon", x + 280, y + 118, 120, 24, 15);
      this.addButton("Special", x + 24, y + 150, 120, 24, 16);
      this.addButton("Melee", x + 152, y + 150, 120, 24, 17);
      this.addButton("Review", x + 280, y + 150, 120, 24, 18);
      this.addButton("Forge Move", x + 24, y + 230, 128, 24, 601);
      this.addButton("Forge Melee", x + 160, y + 230, 128, 24, 604);
   }

   private void buildShapePage(int x, int y) {
      int startX = x + 20;
      int startY = y + 52;
      int totalShapes = 14;
      int maxScroll = Math.max(0, totalShapes - 3);
      this.addButton("▲", x + 250, y + 52, 22, 18, -2001);
      this.addButton("▼", x + 250, y + 154, 22, 18, -2002);

      for (int row = 0; row < 3; row++) {
         int actual = this.shapeScroll + row;
         if (actual >= totalShapes) {
            break;
         }

         int by = startY + row * 22;
         this.addSelectButton(this.shapeName(actual), ((VoidArchiveForgeMenu)this.menu).getShape().ordinal() == actual, startX, by, 230, 20, 100 + actual);
      }

      if (this.shapeScroll > 0) {
         this.drawQueuedHint("More above", x + 286, y + 56);
      }

      if (this.shapeScroll < maxScroll) {
         this.drawQueuedHint("More below", x + 286, y + 157);
      }

      this.addStatRow(x, y);
   }

   private void buildCastPage(int x, int y) {
      int startX = x + 20;
      int startY = y + 52;

      for (int i = 0; i < 4; i++) {
         int bx = startX + i % 2 * 122;
         int by = startY + i / 2 * 24;
         this.addSelectButton(this.castName(i), ((VoidArchiveForgeMenu)this.menu).getCastMode().ordinal() == i, bx, by, 114, 20, 140 + i);
      }

      this.addToggleButton("Projectile", ((VoidArchiveForgeMenu)this.menu).isProjectileCast(), x + 20, y + 118, 114, 515);
      this.addToggleButton("Self", ((VoidArchiveForgeMenu)this.menu).isSelfCast(), x + 142, y + 118, 96, 516);
      this.addToggleButton("AOE", ((VoidArchiveForgeMenu)this.menu).isAoeCast(), x + 246, y + 118, 96, 517);
   }

   private void buildDamagePage(int x, int y) {
      this.buildScrollableDamageList(x + 20, y + 52);
      this.addButton("Barrier -", x + 20, y + 180, 86, 20, 520);
      this.addButton("Barrier +", x + 110, y + 180, 86, 20, 521);
      this.addButton("Resist -", x + 200, y + 180, 86, 20, 522);
      this.addButton("Resist +", x + 290, y + 180, 86, 20, 523);
      this.addButton("Steal -", x + 20, y + 204, 86, 20, 524);
      this.addButton("Steal +", x + 110, y + 204, 86, 20, 525);
   }

   private void buildEffectPage(int x, int y) {
      this.buildScrollableEffectList(x + 20, y + 52);
      this.addToggleButton("Ignite", ((VoidArchiveForgeMenu)this.menu).isIgnite(), x + 20, y + 180, 90, 506);
      this.addToggleButton("Shock", ((VoidArchiveForgeMenu)this.menu).isShockSlow(), x + 114, y + 180, 90, 507);
      this.addToggleButton("Guarded", ((VoidArchiveForgeMenu)this.menu).isGiveGuarded(), x + 208, y + 180, 94, 509);
      this.addToggleButton("Invuln", ((VoidArchiveForgeMenu)this.menu).isMakeInvulnerable(), x + 306, y + 180, 94, 508);
   }

   private void buildParticlePage(int x, int y) {
      int startX = x + 20;
      int startY = y + 52;

      for (int i = 0; i < 16; i++) {
         int bx = startX + i % 2 * 122;
         int by = startY + i / 2 * 24;
         this.addSelectButton(this.particleName(i), ((VoidArchiveForgeMenu)this.menu).getParticle().ordinal() == i, bx, by, 114, 20, 180 + i);
      }
   }

   private void buildSummonPage(int x, int y) {
      this.buildScrollableEntityList(x + 20, y + 52);
      this.addButton("Count -", x + 20, y + 180, 86, 20, 416);
      this.addButton("Count +", x + 110, y + 180, 86, 20, 417);
      this.addButton("Forge Summon", x + 20, y + 208, 116, 22, 602);
      this.addButton("Forge Revive", x + 142, y + 208, 116, 22, 603);
   }

   private void buildSpecialPage(int x, int y) {
      this.addToggleButton("Pull", ((VoidArchiveForgeMenu)this.menu).isPullTarget(), x + 20, y + 52, 86, 502);
      this.addToggleButton("Launch", ((VoidArchiveForgeMenu)this.menu).isLaunchTarget(), x + 110, y + 52, 86, 503);
      this.addToggleButton("Trap", ((VoidArchiveForgeMenu)this.menu).isTrapTargets(), x + 200, y + 52, 86, 510);
      this.addToggleButton("Follow", ((VoidArchiveForgeMenu)this.menu).isFollowAim(), x + 290, y + 52, 86, 504);
      this.addToggleButton("Multi", ((VoidArchiveForgeMenu)this.menu).isMultiHit(), x + 20, y + 78, 86, 500);
      this.addToggleButton("Heal", ((VoidArchiveForgeMenu)this.menu).isHealCaster(), x + 110, y + 78, 86, 501);
      this.addToggleButton("Break", ((VoidArchiveForgeMenu)this.menu).isGuardBreak(), x + 200, y + 78, 86, 505);

      for (int i = 0; i < 7; i++) {
         int bx = x + 20 + i % 2 * 122;
         int by = y + 114 + i / 2 * 24;
         this.addSelectButton(this.blockActionName(i), ((VoidArchiveForgeMenu)this.menu).getBlockAction().ordinal() == i, bx, by, 114, 20, 300 + i);
      }
   }

   private void buildMeleePage(int x, int y) {
      this.addToggleButton("Enable", ((VoidArchiveForgeMenu)this.menu).isMeleeEnabled(), x + 20, y + 52, 90, 511);
      this.addToggleButton("Damage", ((VoidArchiveForgeMenu)this.menu).isMeleeApplyDamage(), x + 114, y + 52, 90, 512);
      this.addToggleButton("Effect", ((VoidArchiveForgeMenu)this.menu).isMeleeApplyEffect(), x + 208, y + 52, 90, 513);
      this.addToggleButton("Steal", ((VoidArchiveForgeMenu)this.menu).isMeleeLifeSteal(), x + 302, y + 52, 90, 514);
      this.addButton("Power -", x + 20, y + 86, 86, 20, 412);
      this.addButton("Power +", x + 110, y + 86, 86, 20, 413);
      this.addButton("Time -", x + 200, y + 86, 86, 20, 414);
      this.addButton("Time +", x + 290, y + 86, 86, 20, 415);
      this.addButton("Forge Melee", x + 20, y + 208, 116, 22, 604);
   }

   private void buildReviewPage(int x, int y) {
      this.addButton("Next Name", x + 20, y + 52, 116, 22, 600);
      this.addButton("Forge Move", x + 20, y + 208, 116, 22, 601);
   }

   private void buildScrollableDamageList(int x, int y) {
      this.addButton("▲", x + 304, y, 22, 18, 700);
      this.addButton("▼", x + 304, y + 112, 22, 18, 701);
      int visible = Math.min(
         ((VoidArchiveForgeMenu)this.menu).getSelectorRows(),
         Math.max(0, ((VoidArchiveForgeMenu)this.menu).getArchivedDamageCount() - ((VoidArchiveForgeMenu)this.menu).getDamageScroll())
      );

      for (int i = 0; i < visible; i++) {
         int actual = ((VoidArchiveForgeMenu)this.menu).getDamageScroll() + i;
         this.addSelectButton(
            this.trim(((VoidArchiveForgeMenu)this.menu).getArchivedDamageName(actual).getString(), 22),
            actual == ((VoidArchiveForgeMenu)this.menu).getSelectedArchivedDamageIndex(),
            x,
            y + i * 19,
            300,
            18,
            800 + actual
         );
      }
   }

   private void buildScrollableEffectList(int x, int y) {
      this.addButton("▲", x + 304, y, 22, 18, 702);
      this.addButton("▼", x + 304, y + 112, 22, 18, 703);
      int visible = Math.min(
         ((VoidArchiveForgeMenu)this.menu).getSelectorRows(),
         Math.max(0, ((VoidArchiveForgeMenu)this.menu).getArchivedEffectCount() - ((VoidArchiveForgeMenu)this.menu).getEffectScroll())
      );

      for (int i = 0; i < visible; i++) {
         int actual = ((VoidArchiveForgeMenu)this.menu).getEffectScroll() + i;
         this.addSelectButton(
            this.trim(((VoidArchiveForgeMenu)this.menu).getArchivedEffectName(actual).getString(), 22),
            actual == ((VoidArchiveForgeMenu)this.menu).getSelectedArchivedEffectIndex(),
            x,
            y + i * 19,
            300,
            18,
            900 + actual
         );
      }
   }

   private void buildScrollableEntityList(int x, int y) {
      this.addButton("▲", x + 304, y, 22, 18, 704);
      this.addButton("▼", x + 304, y + 112, 22, 18, 705);
      int visible = Math.min(
         ((VoidArchiveForgeMenu)this.menu).getSelectorRows(),
         Math.max(0, ((VoidArchiveForgeMenu)this.menu).getRecordedEntityCount() - ((VoidArchiveForgeMenu)this.menu).getEntityScroll())
      );

      for (int i = 0; i < visible; i++) {
         int actual = ((VoidArchiveForgeMenu)this.menu).getEntityScroll() + i;
         this.addSelectButton(
            this.trim(((VoidArchiveForgeMenu)this.menu).getRecordedEntityName(actual).getString(), 16)
               + " x"
               + ((VoidArchiveForgeMenu)this.menu).getRecordedEntityKills(actual),
            actual == ((VoidArchiveForgeMenu)this.menu).getSelectedRecordedEntityIndex(),
            x,
            y + i * 19,
            300,
            18,
            1000 + actual
         );
      }
   }

   private void addStatRow(int x, int y) {
      this.addButton("Range -", x + 20, y + 162, 94, 20, 400);
      this.addButton("Range +", x + 120, y + 162, 94, 20, 401);
      this.addButton("Radius -", x + 220, y + 162, 94, 20, 402);
      this.addButton("Radius +", x + 320, y + 162, 94, 20, 403);
      this.addButton("Power -", x + 20, y + 186, 94, 20, 404);
      this.addButton("Power +", x + 120, y + 186, 94, 20, 405);
      this.addButton("Speed -", x + 220, y + 186, 94, 20, 406);
      this.addButton("Speed +", x + 320, y + 186, 94, 20, 407);
      this.addButton("Burst -", x + 20, y + 210, 94, 20, 408);
      this.addButton("Burst +", x + 120, y + 210, 94, 20, 409);
      this.addButton("Buff -", x + 220, y + 210, 94, 20, 410);
      this.addButton("Buff +", x + 320, y + 210, 94, 20, 411);
   }

   private void addButton(String label, int x, int y, int width, int height, int id) {
      Button button = Button.builder(Component.literal(label), b -> {
         if (id == -2001) {
            this.shapeScroll = Math.max(0, this.shapeScroll - 1);
            this.rebuildWidgets();
            this.lastUiState = this.captureUiState();
         } else if (id == -2002) {
            this.shapeScroll = Math.min(this.getMaxShapeScroll(), this.shapeScroll + 1);
            this.rebuildWidgets();
            this.lastUiState = this.captureUiState();
         } else {
            if (this.minecraft != null && this.minecraft.gameMode != null) {
               this.minecraft.gameMode.handleInventoryButtonClick(((VoidArchiveForgeMenu)this.menu).containerId, id);
            }
         }
      }).bounds(x, y, width, height).build();
      this.dynamicButtons.add((Button)this.addRenderableWidget(button));
   }

   private void addSelectButton(String label, boolean selected, int x, int y, int width, int height, int id) {
      String shown = selected ? "◆ " + label : label;
      Button button = Button.builder(Component.literal(shown), b -> {
         if (this.minecraft != null && this.minecraft.gameMode != null) {
            this.minecraft.gameMode.handleInventoryButtonClick(((VoidArchiveForgeMenu)this.menu).containerId, id);
         }
      }).bounds(x, y, width, height).build();
      this.dynamicButtons.add((Button)this.addRenderableWidget(button));
   }

   private void addToggleButton(String label, boolean on, int x, int y, int width, int id) {
      this.addButton(label + ": " + (on ? "On" : "Off"), x, y, width, 20, id);
   }

   private void clearDynamicButtons() {
      for (Button button : this.dynamicButtons) {
         this.removeWidget(button);
      }

      this.dynamicButtons.clear();
   }

   private void clampShapeScroll() {
      this.shapeScroll = Mth.clamp(this.shapeScroll, 0, this.getMaxShapeScroll());
   }

   private int getMaxShapeScroll() {
      return Math.max(0, 11);
   }

   private void drawQueuedHint(String text, int x, int y) {
   }

   private int captureUiState() {
      return Objects.hash(
         ((VoidArchiveForgeMenu)this.menu).getPage(),
         ((VoidArchiveForgeMenu)this.menu).getDamageScroll(),
         ((VoidArchiveForgeMenu)this.menu).getEffectScroll(),
         ((VoidArchiveForgeMenu)this.menu).getEntityScroll(),
         ((VoidArchiveForgeMenu)this.menu).getSelectedArchivedDamageIndex(),
         ((VoidArchiveForgeMenu)this.menu).getSelectedArchivedEffectIndex(),
         ((VoidArchiveForgeMenu)this.menu).getSelectedRecordedEntityIndex(),
         ((VoidArchiveForgeMenu)this.menu).getShape().ordinal(),
         ((VoidArchiveForgeMenu)this.menu).getCastMode().ordinal(),
         ((VoidArchiveForgeMenu)this.menu).getParticle().ordinal(),
         ((VoidArchiveForgeMenu)this.menu).getBlockAction().ordinal(),
         ((VoidArchiveForgeMenu)this.menu).getRangeValue(),
         Float.floatToIntBits(((VoidArchiveForgeMenu)this.menu).getRadius()),
         Float.floatToIntBits(((VoidArchiveForgeMenu)this.menu).getPower()),
         Float.floatToIntBits(((VoidArchiveForgeMenu)this.menu).getSpeed()),
         ((VoidArchiveForgeMenu)this.menu).getBurstCount(),
         Float.floatToIntBits(((VoidArchiveForgeMenu)this.menu).getBarrierBypass()),
         Float.floatToIntBits(((VoidArchiveForgeMenu)this.menu).getResistanceBypass()),
         Float.floatToIntBits(((VoidArchiveForgeMenu)this.menu).getMagiculeSteal()),
         ((VoidArchiveForgeMenu)this.menu).getSelfBuffDurationTicks(),
         ((VoidArchiveForgeMenu)this.menu).getSummonCount(),
         Float.floatToIntBits(((VoidArchiveForgeMenu)this.menu).getMeleePower()),
         ((VoidArchiveForgeMenu)this.menu).getMeleeDurationSeconds(),
         ((VoidArchiveForgeMenu)this.menu).isMultiHit(),
         ((VoidArchiveForgeMenu)this.menu).isHealCaster(),
         ((VoidArchiveForgeMenu)this.menu).isPullTarget(),
         ((VoidArchiveForgeMenu)this.menu).isLaunchTarget(),
         ((VoidArchiveForgeMenu)this.menu).isFollowAim(),
         ((VoidArchiveForgeMenu)this.menu).isGuardBreak(),
         ((VoidArchiveForgeMenu)this.menu).isIgnite(),
         ((VoidArchiveForgeMenu)this.menu).isShockSlow(),
         ((VoidArchiveForgeMenu)this.menu).isMakeInvulnerable(),
         ((VoidArchiveForgeMenu)this.menu).isGiveGuarded(),
         ((VoidArchiveForgeMenu)this.menu).isTrapTargets(),
         ((VoidArchiveForgeMenu)this.menu).isMeleeEnabled(),
         ((VoidArchiveForgeMenu)this.menu).isMeleeApplyDamage(),
         ((VoidArchiveForgeMenu)this.menu).isMeleeApplyEffect(),
         ((VoidArchiveForgeMenu)this.menu).isMeleeLifeSteal(),
         ((VoidArchiveForgeMenu)this.menu).isProjectileCast(),
         ((VoidArchiveForgeMenu)this.menu).isSelfCast(),
         ((VoidArchiveForgeMenu)this.menu).isAoeCast(),
         ((VoidArchiveForgeMenu)this.menu).getArchivedDamageCount(),
         ((VoidArchiveForgeMenu)this.menu).getArchivedEffectCount(),
         ((VoidArchiveForgeMenu)this.menu).getRecordedEntityCount(),
         ((VoidArchiveForgeMenu)this.menu).getCurrentMoveName(),
         ((VoidArchiveForgeMenu)this.menu).getSelectedDamageName().getString(),
         ((VoidArchiveForgeMenu)this.menu).getSelectedEffectName().getString(),
         ((VoidArchiveForgeMenu)this.menu).getSelectedRecordedEntityName().getString(),
         this.shapeScroll
      );
   }

   protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
      int x1 = this.leftPos;
      int y1 = this.topPos;
      int x2 = x1 + this.imageWidth;
      int y2 = y1 + this.imageHeight;
      g.fillGradient(x1, y1, x2, y2, -267450598, -266072001);
      g.fill(x1 + 4, y1 + 4, x2 - 4, y2 - 4, -803992795);
      g.fill(x1 + 10, y1 + 40, x1 + 338, y2 - 14, -1726542547);
      g.fill(x1 + 14, y1 + 44, x1 + 334, y2 - 18, 1427114012);
      g.fill(x1 + 344, y1 + 40, x2 - 10, y2 - 14, -1441001420);
      g.fill(x1 + 348, y1 + 44, x2 - 14, y2 - 18, 1712458018);
      this.renderPreviewPanel(g, x1 + 344, y1 + 40, x2 - 10, y2 - 14);
   }

   private void renderPreviewPanel(GuiGraphics g, int x1, int y1, int x2, int y2) {
      int lineY = y1 + 8;
      int normal = 14536959;
      int accent = 11462399;
      int warn = 16773320;
      int trap = 16758518;
      g.drawString(this.font, "Preview", x1 + 8, lineY, 15982079, false);
      lineY += 16;
      this.drawPreviewLine(g, this.trim(((VoidArchiveForgeMenu)this.menu).getCurrentMoveName(), 14), x1, lineY, normal, y2);
      lineY += 11;
      this.drawPreviewLine(g, "Shape: " + this.trim(((VoidArchiveForgeMenu)this.menu).getShape().name(), 10), x1, lineY, normal, y2);
      lineY += 11;
      this.drawPreviewLine(g, "Cast: " + this.trim(((VoidArchiveForgeMenu)this.menu).getCastMode().name(), 10), x1, lineY, normal, y2);
      lineY += 11;
      this.drawPreviewLine(
         g,
         "Mode: " + (((VoidArchiveForgeMenu)this.menu).isTrapTargets() ? "Trap" : "Instant"),
         x1,
         lineY,
         ((VoidArchiveForgeMenu)this.menu).isTrapTargets() ? trap : accent,
         y2
      );
      lineY += 11;
      this.drawPreviewLine(g, "Dmg: " + this.trim(((VoidArchiveForgeMenu)this.menu).getSelectedDamageName().getString(), 12), x1, lineY, warn, y2);
      lineY += 11;
      this.drawPreviewLine(g, "Eff: " + this.trim(((VoidArchiveForgeMenu)this.menu).getSelectedEffectName().getString(), 12), x1, lineY, accent, y2);
      lineY += 11;
      this.drawPreviewLine(g, "Part: " + this.trim(((VoidArchiveForgeMenu)this.menu).getParticle().name(), 10), x1, lineY, normal, y2);
      lineY += 11;
      this.drawPreviewLine(g, "Block: " + this.trim(((VoidArchiveForgeMenu)this.menu).getBlockAction().name(), 10), x1, lineY, normal, y2);
      lineY += 13;
      this.drawPreviewLine(g, "Rng " + ((VoidArchiveForgeMenu)this.menu).getRangeValue(), x1, lineY, normal, y2);
      lineY += 11;
      this.drawPreviewLine(g, "Rad " + this.formatFloat(((VoidArchiveForgeMenu)this.menu).getRadius()), x1, lineY, normal, y2);
      lineY += 11;
      this.drawPreviewLine(g, "Pow " + this.formatFloat(((VoidArchiveForgeMenu)this.menu).getPower()), x1, lineY, normal, y2);
      lineY += 11;
      this.drawPreviewLine(g, "Spd " + this.formatFloat(((VoidArchiveForgeMenu)this.menu).getSpeed()), x1, lineY, normal, y2);
      lineY += 11;
      this.drawPreviewLine(g, "Burst " + ((VoidArchiveForgeMenu)this.menu).getBurstCount(), x1, lineY, normal, y2);
      lineY += 11;
      this.drawPreviewLine(g, "Buff " + ((VoidArchiveForgeMenu)this.menu).getSelfBuffDurationTicks() / 20 + "s", x1, lineY, normal, y2);
      lineY += 13;
      this.drawPreviewLine(g, "Barrier " + this.formatFloat(((VoidArchiveForgeMenu)this.menu).getBarrierBypass()), x1, lineY, normal, y2);
      lineY += 11;
      this.drawPreviewLine(g, "Resist " + this.formatFloat(((VoidArchiveForgeMenu)this.menu).getResistanceBypass()), x1, lineY, normal, y2);
      lineY += 11;
      this.drawPreviewLine(g, "Steal " + this.formatFloat(((VoidArchiveForgeMenu)this.menu).getMagiculeSteal()), x1, lineY, normal, y2);
      lineY += 13;
      this.drawPreviewLine(g, "Soul: " + this.trim(((VoidArchiveForgeMenu)this.menu).getSelectedRecordedEntityName().getString(), 12), x1, lineY, warn, y2);
      lineY += 11;
      this.drawPreviewLine(g, "Count " + ((VoidArchiveForgeMenu)this.menu).getSummonCount(), x1, lineY, normal, y2);
   }

   private void drawPreviewLine(GuiGraphics g, String text, int panelX, int y, int color, int panelBottom) {
      if (y <= panelBottom - 12) {
         g.drawString(this.font, text, panelX + 8, y, color, false);
      }
   }

   protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
      String mainTitle = this.trim(this.title.getString(), 18);
      String subTitle = this.trim(this.pageTitle(), 24);
      g.drawString(this.font, mainTitle, 14, 12, 15982079, false);
      g.drawString(this.font, subTitle, 76, 25, 13216255, false);
      if (((VoidArchiveForgeMenu)this.menu).getPage() == 1) {
         if (this.shapeScroll > 0) {
            g.drawString(this.font, "More above", 286, 56, 13216255, false);
         }

         if (this.shapeScroll < this.getMaxShapeScroll()) {
            g.drawString(this.font, "More below", 286, 157, 13216255, false);
         }
      }
   }

   private String pageTitle() {
      return switch (((VoidArchiveForgeMenu)this.menu).getPage()) {
         case 0 -> "Forge Home";
         case 1 -> "Shape Builder";
         case 2 -> "Cast Builder";
         case 3 -> "Damage Builder";
         case 4 -> "Effect Builder";
         case 5 -> "Particle Builder";
         case 6 -> "Summon Builder";
         case 7 -> "Special Builder";
         case 8 -> "Melee Builder";
         case 9 -> "Final Review";
         default -> "Forge";
      };
   }

   private String shapeName(int i) {
      return switch (i) {
         case 0 -> "Touch";
         case 1 -> "AOE";
         case 2 -> "Beam";
         case 3 -> "Orb";
         case 4 -> "Aura";
         case 5 -> "Summon";
         case 6 -> "Nova";
         case 7 -> "Wall";
         case 8 -> "Line";
         case 9 -> "Ring";
         case 10 -> "Dome";
         case 11 -> "Rain";
         case 12 -> "Sigil";
         default -> "Pillar";
      };
   }

   private String castName(int i) {
      return switch (i) {
         case 0 -> "Default";
         case 1 -> "Self";
         case 2 -> "Projectile";
         default -> "Target AOE";
      };
   }

   private String particleName(int i) {
      return switch (i) {
         case 0 -> "Portal";
         case 1 -> "Flame";
         case 2 -> "Soul";
         case 3 -> "Lightning";
         case 4 -> "Water";
         case 5 -> "Wind";
         case 6 -> "Dark";
         case 7 -> "Light";
         case 8 -> "Enchant";
         case 9 -> "Smoke";
         case 10 -> "Block";
         case 11 -> "Shockwave";
         case 12 -> "Blossom";
         case 13 -> "Black Fire";
         case 14 -> "Snow";
         default -> "Arcane";
      };
   }

   private String blockActionName(int i) {
      return switch (i) {
         case 0 -> "None";
         case 1 -> "Obsidian Wall";
         case 2 -> "Ice Field";
         case 3 -> "Crystal Ring";
         case 4 -> "Glass Dome";
         case 5 -> "Spike Field";
         default -> "Void Crater";
      };
   }

   private String trim(String value, int max) {
      if (value != null && value.length() > max) {
         return value.substring(0, Math.max(0, max - 3)) + "...";
      } else {
         return value == null ? "" : value;
      }
   }

   private String formatFloat(float value) {
      float rounded = Math.round(value * 10.0F) / 10.0F;
      return Math.abs(rounded - Math.round(rounded)) < 0.001F ? Integer.toString(Mth.floor(rounded)) : Float.toString(rounded);
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
      if (((VoidArchiveForgeMenu)this.menu).getPage() == 1) {
         if (deltaY > 0.0) {
            this.shapeScroll = Math.max(0, this.shapeScroll - 1);
            this.rebuildWidgets();
            this.lastUiState = this.captureUiState();
            return true;
         }

         if (deltaY < 0.0) {
            this.shapeScroll = Math.min(this.getMaxShapeScroll(), this.shapeScroll + 1);
            this.rebuildWidgets();
            this.lastUiState = this.captureUiState();
            return true;
         }
      }

      return super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
   }

   public void onClose() {
      this.clearDynamicButtons();
      super.onClose();
   }

   public void removed() {
      this.clearDynamicButtons();
      super.removed();
   }

   public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(g, mouseX, mouseY, partialTick);
      super.render(g, mouseX, mouseY, partialTick);
      this.renderTooltip(g, mouseX, mouseY);
   }
}
