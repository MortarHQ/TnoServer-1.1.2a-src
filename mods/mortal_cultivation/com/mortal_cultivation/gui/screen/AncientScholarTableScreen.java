package com.mortal_cultivation.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Axis;
import com.mortal_cultivation.block.entity.AncientScholarTableBlockEntity;
import com.mortal_cultivation.gui.menu.AncientScholarTableMenu;
import java.util.ArrayDeque;
import java.util.Arrays;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.joml.Matrix4f;

public class AncientScholarTableScreen extends AbstractContainerScreen<AncientScholarTableMenu> {
   private static final ResourceLocation PAGE_ONE_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "mortal_cultivation", "textures/gui/ancient_scholar_table.png"
   );
   private static final ResourceLocation DECIPHER_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "mortal_cultivation", "textures/gui/ancient_scholar_table_decipher.png"
   );
   private static final ResourceLocation TALISMAN_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "mortal_cultivation", "textures/gui/ancient_scholar_table_talisman.png"
   );
   private static final ResourceLocation DECIPHER_RUNES_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "mortal_cultivation", "textures/gui/ancient_scholar_table_runes.png"
   );
   private static final ResourceLocation DECIPHER_RUNE_FRAME_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "mortal_cultivation", "textures/gui/ancient_scholar_table_rune_frame.png"
   );
   private static final Component DECIPHER_PAGE_NAME = Component.translatable("gui.mortal_cultivation.ancient_scholar_table.decipher");
   private static final Component ANALYSIS_PAGE_NAME = Component.translatable("gui.mortal_cultivation.ancient_scholar_table.analysis");
   private static final Component TALISMAN_PAGE_NAME = Component.translatable("gui.mortal_cultivation.ancient_scholar_table.talisman_making");
   private static final Component TALISMAN_PAGE_UNLEARNED = Component.translatable("gui.mortal_cultivation.ancient_scholar_table.talisman_making.unlearned");
   private static final Component INSUFFICIENT_RUNES = Component.translatable("gui.mortal_cultivation.ancient_scholar_table.status.insufficient_runes");
   private static final int ANALYSIS_PAGE_BUTTON_X = 23;
   private static final int ANALYSIS_PAGE_BUTTON_Y = 26;
   private static final int ANALYSIS_PAGE_BUTTON_WIDTH = 21;
   private static final int DECIPHER_PAGE_BUTTON_X = 23;
   private static final int DECIPHER_PAGE_BUTTON_Y = 41;
   private static final int DECIPHER_PAGE_BUTTON_WIDTH = 21;
   private static final int TALISMAN_PAGE_BUTTON_X = 31;
   private static final int TALISMAN_PAGE_BUTTON_Y = 56;
   private static final int TALISMAN_PAGE_BUTTON_WIDTH = 13;
   private static final int PAGE_BUTTON_HEIGHT = 14;
   private static final int PROGRESS_X = 81;
   private static final int PROGRESS_Y = 39;
   private static final int PROGRESS_U = 5;
   private static final int PROGRESS_V = 214;
   private static final int PROGRESS_WIDTH = 32;
   private static final int PROGRESS_HEIGHT = 42;
   private static final int DECIPHER_CANVAS_X = 44;
   private static final int DECIPHER_CANVAS_Y = 21;
   private static final int DECIPHER_CANVAS_WIDTH = 126;
   private static final int DECIPHER_CANVAS_HEIGHT = 81;
   private static final int GRID_X = 47;
   private static final int GRID_Y = 21;
   private static final int GRID_CELL_SIZE = 20;
   private static final int RUNE_ATLAS_WIDTH = 80;
   private static final int RUNE_ATLAS_HEIGHT = 480;
   private static final int RUNE_BLOCK_HEIGHT = 80;
   private static final int RUNE_DORMANT_BLOCK = 0;
   private static final int RUNE_ACTIVE_BLOCK_START = 1;
   private static final int RUNE_BROKEN_BLOCK = 5;
   private static final int RUNE_FRAME_COUNT = 4;
   private static final long RUNE_FRAME_MILLIS = 80L;
   private static final long CLICK_FEEDBACK_MILLIS = 360L;
   private static final long ROTATION_ANIMATION_MILLIS = 260L;
   private static final long CONNECTION_FLOW_STEP_MILLIS = 55L;
   private static final long CONNECTION_FADE_MILLIS = 220L;
   private static final long NO_ANIMATION = Long.MIN_VALUE;
   private int clickedCell = -1;
   private long clickedCellAt = Long.MIN_VALUE;
   private int pendingCell = -1;
   private int pendingRotation;
   private long pendingCellAt = Long.MIN_VALUE;
   private final int[] observedSolutionMasks = new int[24];
   private final int[] observedRotations = new int[24];
   private final int[] rotationFrom = new int[24];
   private final int[] rotationTurns = new int[24];
   private final int[] requestedDirections = new int[24];
   private final long[] rotationStartedAt = new long[24];
   private final boolean[] energizedCells = new boolean[24];
   private final long[] energizeStartedAt = new long[24];
   private boolean boardAnimationInitialized;

   public AncientScholarTableScreen(AncientScholarTableMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
      this.imageWidth = 186;
      this.imageHeight = 210;
      this.titleLabelY = -10000;
      this.inventoryLabelY = -10000;
      Arrays.fill(this.observedSolutionMasks, -1);
      Arrays.fill(this.observedRotations, -1);
      Arrays.fill(this.rotationStartedAt, Long.MIN_VALUE);
      Arrays.fill(this.energizeStartedAt, Long.MIN_VALUE);
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      if (!((AncientScholarTableMenu)this.menu).hasAncientBook()) {
         this.resetBoardAnimationState();
      }

      this.updateClickFeedback();
      super.render(guiGraphics, mouseX, mouseY, partialTick);
      if (((AncientScholarTableMenu)this.menu).isDecipherPage() && ((AncientScholarTableMenu)this.menu).isDecipherActive()) {
         this.renderClickFeedback(guiGraphics);
      }

      this.renderDecipherStatus(guiGraphics);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      boolean decipherPage = ((AncientScholarTableMenu)this.menu).isDecipherPage();
      boolean talismanPage = ((AncientScholarTableMenu)this.menu).isTalismanPage();
      ResourceLocation texture = talismanPage ? TALISMAN_TEXTURE : (decipherPage ? DECIPHER_TEXTURE : PAGE_ONE_TEXTURE);
      guiGraphics.blit(texture, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
      int progressHeight = !decipherPage && !talismanPage ? ((AncientScholarTableMenu)this.menu).getProgressScaled(42) : 0;
      if (progressHeight > 0) {
         guiGraphics.blit(PAGE_ONE_TEXTURE, this.leftPos + 81, this.topPos + 39 + 42 - progressHeight, 5.0F, 256 - progressHeight, 32, progressHeight, 256, 256);
      }

      if (decipherPage
         && ((AncientScholarTableMenu)this.menu).hasAncientBook()
         && ((AncientScholarTableMenu)this.menu).isDecipherActive()
         && !((AncientScholarTableMenu)this.menu).isDecipherCompleted()) {
         this.renderDecipherBoard(guiGraphics, mouseX, mouseY);
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      boolean decipherPage = ((AncientScholarTableMenu)this.menu).isDecipherPage();
      if (button == 0) {
         int pageButton = this.getPageButtonAt(mouseX, mouseY);
         if (pageButton >= 0) {
            if (pageButton == this.getCurrentPageButton()) {
               return true;
            }

            if (pageButton == 2 && !((AncientScholarTableMenu)this.menu).canAccessTalismanMaking()) {
               return true;
            }

            if (this.minecraft != null
               && this.minecraft.player != null
               && this.minecraft.gameMode != null
               && ((AncientScholarTableMenu)this.menu).clickMenuButton(this.minecraft.player, pageButton)) {
               this.minecraft.gameMode.handleInventoryButtonClick(((AncientScholarTableMenu)this.menu).containerId, pageButton);
               return true;
            }
         }
      }

      if ((button == 0 || button == 1)
         && decipherPage
         && this.isWithinGui(mouseX, mouseY, 44, 21, 126, 81)
         && ((AncientScholarTableMenu)this.menu).isDecipherActive()
         && !((AncientScholarTableMenu)this.menu).isDecipherCompleted()) {
         int cell = this.getCellAt(mouseX, mouseY);
         if (cell >= 0 && ((AncientScholarTableMenu)this.menu).isDecipherCellRotatable(cell)) {
            int direction = button == 0 ? -1 : 1;
            this.pendingCell = cell;
            this.pendingRotation = ((AncientScholarTableMenu)this.menu).getDecipherRotation(cell);
            this.pendingCellAt = Util.getMillis();
            this.requestedDirections[cell] = direction;
            return this.sendMenuButton(AncientScholarTableMenu.decipherButtonForCell(cell, direction));
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      super.renderTooltip(guiGraphics, mouseX, mouseY);
      Component pageName = this.getPageNameAt(mouseX, mouseY);
      if (pageName != null) {
         guiGraphics.renderTooltip(this.font, pageName, mouseX, mouseY);
      }
   }

   private int getCurrentPageButton() {
      if (((AncientScholarTableMenu)this.menu).isTalismanPage()) {
         return 2;
      } else {
         return ((AncientScholarTableMenu)this.menu).isDecipherPage() ? 1 : 0;
      }
   }

   private int getPageButtonAt(double mouseX, double mouseY) {
      if (this.isWithinGui(mouseX, mouseY, 23, 26, 21, 14)) {
         return 0;
      } else if (this.isWithinGui(mouseX, mouseY, 23, 41, 21, 14)) {
         return 1;
      } else {
         return this.isWithinGui(mouseX, mouseY, 31, 56, 13, 14) ? 2 : -1;
      }
   }

   private Component getPageNameAt(double mouseX, double mouseY) {
      return switch (this.getPageButtonAt(mouseX, mouseY)) {
         case 0 -> ANALYSIS_PAGE_NAME;
         case 1 -> DECIPHER_PAGE_NAME;
         case 2 -> ((AncientScholarTableMenu)this.menu).canAccessTalismanMaking() ? TALISMAN_PAGE_NAME : TALISMAN_PAGE_UNLEARNED;
         default -> null;
      };
   }

   private boolean sendMenuButton(int buttonId) {
      if (this.minecraft != null && this.minecraft.gameMode != null) {
         this.minecraft.gameMode.handleInventoryButtonClick(((AncientScholarTableMenu)this.menu).containerId, buttonId);
         return true;
      } else {
         return false;
      }
   }

   private int getCellAt(double mouseX, double mouseY) {
      int localX = (int)Math.floor(mouseX - this.leftPos - 47.0);
      int localY = (int)Math.floor(mouseY - this.topPos - 21.0);
      int gridWidth = 120;
      int gridHeight = 80;
      if (localX >= 0 && localY >= 0 && localX < gridWidth && localY < gridHeight) {
         int column = localX / 20;
         int row = localY / 20;
         return row * 6 + column;
      } else {
         return -1;
      }
   }

   private void renderDecipherBoard(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      long now = Util.getMillis();
      int[] distances = this.getReachableDistances();
      boolean[] currentEnergized = new boolean[24];

      for (int cell = 0; cell < currentEnergized.length; cell++) {
         int mask = ((AncientScholarTableMenu)this.menu).getDecipherMask(cell);
         currentEnergized[cell] = distances[cell] >= 0 && !this.hasBrokenConnection(cell, mask);
      }

      this.updateBoardAnimationState(now, currentEnergized, distances);
      int gridColumns = 6;
      int animationFrame = (int)(now / 80L % 4L);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

      for (int cell = 0; cell < 24; cell++) {
         this.renderRuneFrame(guiGraphics, cell);
      }

      for (int cell = 0; cell < 24; cell++) {
         int mask = ((AncientScholarTableMenu)this.menu).getDecipherMask(cell);
         int block = this.hasBrokenConnection(cell, mask) ? 5 : 0;
         this.renderRuneTile(guiGraphics, cell, block, now);
      }

      float pulse = 0.9F + 0.1F * (float)Math.sin(now / 170.0);

      for (int cell = 0; cell < 24; cell++) {
         int mask = ((AncientScholarTableMenu)this.menu).getDecipherMask(cell);
         if (currentEnergized[cell]) {
            float alpha = this.getEnergizeAlpha(cell, now);
            if (!(alpha <= 0.0F)) {
               int phase = Math.floorMod(animationFrame - distances[cell], 4);
               this.renderRuneTile(guiGraphics, cell, 1 + phase, now, 1.0F, pulse, 0.72F * pulse, alpha);
            }
         }
      }

      int hoveredCell = this.getCellAt(mouseX, mouseY);
      if (hoveredCell >= 0 && ((AncientScholarTableMenu)this.menu).isDecipherCellRotatable(hoveredCell) && this.isWithinGui(mouseX, mouseY, 44, 21, 126, 81)) {
         int row = hoveredCell / gridColumns;
         int column = hoveredCell % gridColumns;
         int x = this.leftPos + 47 + column * 20;
         int y = this.topPos + 21 + row * 20;
         guiGraphics.fill(x, y, x + 20, y + 1, -399451);
         guiGraphics.fill(x, y + 20 - 1, x + 20, y + 20, -399451);
         guiGraphics.fill(x, y, x + 1, y + 20, -399451);
         guiGraphics.fill(x + 20 - 1, y, x + 20, y + 20, -399451);
      }

      RenderSystem.disableBlend();
   }

   private void renderRuneFrame(GuiGraphics guiGraphics, int cell) {
      int row = cell / 6;
      int column = cell % 6;
      guiGraphics.blit(DECIPHER_RUNE_FRAME_TEXTURE, this.leftPos + 47 + column * 20, this.topPos + 21 + row * 20, 0.0F, 0.0F, 20, 20, 20, 20);
   }

   private void renderRuneTile(GuiGraphics guiGraphics, int cell, int block, long now) {
      this.renderRuneTile(guiGraphics, cell, block, now, 1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderRuneTile(GuiGraphics guiGraphics, int cell, int block, long now, float red, float green, float blue, float alpha) {
      int gridColumns = 6;
      int row = cell / gridColumns;
      int column = cell % gridColumns;
      int x = this.leftPos + 47 + column * 20;
      int y = this.topPos + 21 + row * 20;
      int solutionMask = ((AncientScholarTableMenu)this.menu).getDecipherSolutionMask(cell);
      int mask = getCanonicalMask(solutionMask);
      int solutionTurns = getTurnsFromCanonical(mask, solutionMask);
      float angle = (solutionTurns + ((AncientScholarTableMenu)this.menu).getDecipherRotation(cell)) * 90.0F;
      long rotationStart = this.rotationStartedAt[cell];
      if (rotationStart != Long.MIN_VALUE) {
         float progress = Mth.clamp((float)(now - rotationStart) / 260.0F, 0.0F, 1.0F);
         if (progress >= 1.0F) {
            this.rotationStartedAt[cell] = Long.MIN_VALUE;
         } else {
            float eased = smoothStep(progress);
            angle = (solutionTurns + this.rotationFrom[cell] + this.rotationTurns[cell] * eased) * 90.0F;
         }
      }

      int u = mask % 4 * 20;
      int v = block * 80 + mask / 4 * 20;
      if (Math.abs(angle) < 0.01F) {
         blitRuneTile(guiGraphics, x, y, u, v, red, green, blue, alpha);
      } else {
         guiGraphics.pose().pushPose();
         guiGraphics.pose().translate(x + 10.0F, y + 10.0F, 0.0F);
         guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(angle));
         guiGraphics.pose().translate(-x - 10.0F, -y - 10.0F, 0.0F);
         blitRuneTile(guiGraphics, x, y, u, v, red, green, blue, alpha);
         guiGraphics.pose().popPose();
      }
   }

   private static void blitRuneTile(GuiGraphics guiGraphics, int x, int y, int u, int v, float red, float green, float blue, float alpha) {
      RenderSystem.setShaderTexture(0, DECIPHER_RUNES_TEXTURE);
      RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
      float minU = u / 80.0F;
      float maxU = (u + 20) / 80.0F;
      float minV = v / 480.0F;
      float maxV = (v + 20) / 480.0F;
      Matrix4f pose = guiGraphics.pose().last().pose();
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
      buffer.addVertex(pose, x, y, 0.0F).setUv(minU, minV).setColor(red, green, blue, alpha);
      buffer.addVertex(pose, x, y + 20, 0.0F).setUv(minU, maxV).setColor(red, green, blue, alpha);
      buffer.addVertex(pose, x + 20, y + 20, 0.0F).setUv(maxU, maxV).setColor(red, green, blue, alpha);
      buffer.addVertex(pose, x + 20, y, 0.0F).setUv(maxU, minV).setColor(red, green, blue, alpha);
      BufferUploader.drawWithShader(buffer.buildOrThrow());
   }

   private static int getCanonicalMask(int mask) {
      int canonical = mask & 15;

      for (int turns = 1; turns < 4; turns++) {
         canonical = Math.min(canonical, AncientScholarTableBlockEntity.rotateMask(mask, turns));
      }

      return canonical;
   }

   private static int getTurnsFromCanonical(int canonicalMask, int targetMask) {
      for (int turns = 0; turns < 4; turns++) {
         if (AncientScholarTableBlockEntity.rotateMask(canonicalMask, turns) == (targetMask & 15)) {
            return turns;
         }
      }

      return 0;
   }

   private boolean hasBrokenConnection(int cell, int mask) {
      for (int direction : new int[]{1, 2, 4, 8}) {
         if ((mask & direction) != 0) {
            int neighbor = this.getNeighbor(cell, direction);
            if (neighbor < 0 || (((AncientScholarTableMenu)this.menu).getDecipherMask(neighbor) & opposite(direction)) == 0) {
               return true;
            }
         }
      }

      return false;
   }

   private int[] getReachableDistances() {
      int count = 24;
      int[] distances = new int[count];
      Arrays.fill(distances, -1);
      int source = Math.min(8, count - 1);
      ArrayDeque<Integer> queue = new ArrayDeque<>();
      distances[source] = 0;
      queue.add(source);

      while (!queue.isEmpty()) {
         int cell = queue.removeFirst();
         int mask = ((AncientScholarTableMenu)this.menu).getDecipherMask(cell);

         for (int direction : new int[]{1, 2, 4, 8}) {
            if ((mask & direction) != 0) {
               int neighbor = this.getNeighbor(cell, direction);
               if (neighbor >= 0 && (((AncientScholarTableMenu)this.menu).getDecipherMask(neighbor) & opposite(direction)) != 0 && distances[neighbor] < 0) {
                  distances[neighbor] = distances[cell] + 1;
                  queue.addLast(neighbor);
               }
            }
         }
      }

      return distances;
   }

   private int getNeighbor(int cell, int direction) {
      int gridColumns = 6;
      int gridRows = 4;
      int row = cell / gridColumns;
      int column = cell % gridColumns;

      return switch (direction) {
         case 1 -> row > 0 ? cell - gridColumns : -1;
         case 2 -> column < gridColumns - 1 ? cell + 1 : -1;
         default -> -1;
         case 4 -> row < gridRows - 1 ? cell + gridColumns : -1;
         case 8 -> column > 0 ? cell - 1 : -1;
      };
   }

   private static int opposite(int direction) {
      return switch (direction) {
         case 1 -> 4;
         case 2 -> 8;
         default -> 0;
         case 4 -> 1;
         case 8 -> 2;
      };
   }

   private void updateBoardAnimationState(long now, boolean[] currentEnergized, int[] distances) {
      boolean solutionChanged = false;
      if (this.boardAnimationInitialized) {
         for (int cell = 0; cell < 24; cell++) {
            if (this.observedSolutionMasks[cell] != ((AncientScholarTableMenu)this.menu).getDecipherSolutionMask(cell)) {
               solutionChanged = true;
               break;
            }
         }
      }

      if (this.boardAnimationInitialized && !solutionChanged) {
         boolean boardChanged = false;

         for (int cell = 0; cell < 24; cell++) {
            int currentRotation = ((AncientScholarTableMenu)this.menu).getDecipherRotation(cell);
            int previousRotation = this.observedRotations[cell];
            if (currentRotation != previousRotation) {
               int clockwiseTurns = currentRotation - previousRotation & 3;

               int turns = switch (clockwiseTurns) {
                  case 1 -> 1;
                  case 2 -> this.requestedDirections[cell] < 0 ? -2 : 2;
                  case 3 -> -1;
                  default -> 0;
               };
               this.rotationFrom[cell] = previousRotation;
               this.rotationTurns[cell] = turns;
               this.rotationStartedAt[cell] = now;
               this.observedRotations[cell] = currentRotation;
               this.requestedDirections[cell] = 0;
               boardChanged = true;
            }
         }

         if (boardChanged) {
            int firstNewDistance = Integer.MAX_VALUE;

            for (int cell = 0; cell < currentEnergized.length; cell++) {
               if (currentEnergized[cell] && !this.energizedCells[cell]) {
                  firstNewDistance = Math.min(firstNewDistance, Math.max(0, distances[cell]));
               }
            }

            for (int cell = 0; cell < currentEnergized.length; cell++) {
               if (currentEnergized[cell] && !this.energizedCells[cell]) {
                  int relativeDistance = Math.max(0, distances[cell] - firstNewDistance);
                  this.energizeStartedAt[cell] = now + 260L + relativeDistance * 55L;
               } else if (!currentEnergized[cell]) {
                  this.energizeStartedAt[cell] = Long.MIN_VALUE;
               }

               this.energizedCells[cell] = currentEnergized[cell];
            }
         }
      } else {
         for (int cell = 0; cell < 24; cell++) {
            this.observedSolutionMasks[cell] = ((AncientScholarTableMenu)this.menu).getDecipherSolutionMask(cell);
            this.observedRotations[cell] = ((AncientScholarTableMenu)this.menu).getDecipherRotation(cell);
            this.energizedCells[cell] = currentEnergized[cell];
         }

         Arrays.fill(this.rotationStartedAt, Long.MIN_VALUE);
         Arrays.fill(this.energizeStartedAt, Long.MIN_VALUE);
         Arrays.fill(this.requestedDirections, 0);
         this.boardAnimationInitialized = true;
      }
   }

   private float getEnergizeAlpha(int cell, long now) {
      long startedAt = this.energizeStartedAt[cell];
      if (startedAt == Long.MIN_VALUE) {
         return 1.0F;
      } else {
         float progress = Mth.clamp((float)(now - startedAt) / 220.0F, 0.0F, 1.0F);
         if (progress >= 1.0F) {
            this.energizeStartedAt[cell] = Long.MIN_VALUE;
            return 1.0F;
         } else {
            return smoothStep(progress);
         }
      }
   }

   private static float smoothStep(float value) {
      return value * value * (3.0F - 2.0F * value);
   }

   private void resetBoardAnimationState() {
      if (this.boardAnimationInitialized || this.pendingCell >= 0 || this.clickedCell >= 0) {
         this.boardAnimationInitialized = false;
         Arrays.fill(this.observedSolutionMasks, -1);
         Arrays.fill(this.observedRotations, -1);
         Arrays.fill(this.rotationStartedAt, Long.MIN_VALUE);
         Arrays.fill(this.energizeStartedAt, Long.MIN_VALUE);
         Arrays.fill(this.energizedCells, false);
         Arrays.fill(this.requestedDirections, 0);
         this.pendingCell = -1;
         this.clickedCell = -1;
      }
   }

   private void updateClickFeedback() {
      if (this.pendingCell >= 0) {
         long now = Util.getMillis();
         if (((AncientScholarTableMenu)this.menu).getDecipherRotation(this.pendingCell) != this.pendingRotation) {
            this.clickedCell = this.pendingCell;
            this.clickedCellAt = now;
            this.pendingCell = -1;
         } else if (now - this.pendingCellAt >= 1000L) {
            this.requestedDirections[this.pendingCell] = 0;
            this.pendingCell = -1;
         }
      }
   }

   private void renderClickFeedback(GuiGraphics guiGraphics) {
      if (this.clickedCell >= 0) {
         long elapsed = Util.getMillis() - this.clickedCellAt;
         if (elapsed >= 0L && elapsed < 360L) {
            float progress = (float)elapsed / 360.0F;
            int alpha = Math.max(0, Math.min(255, (int)((1.0F - progress) * 235.0F)));
            int color = alpha << 24 | 16767338;
            int gridColumns = 6;
            int centerX = this.leftPos + 47 + this.clickedCell % gridColumns * 20 + 10;
            int centerY = this.topPos + 21 + this.clickedCell / gridColumns * 20 + 10;
            double radius = 3.0 + progress * 12.0;

            for (int spark = 0; spark < 8; spark++) {
               double angle = spark * Math.PI / 4.0 + this.clickedCell * 0.37;
               int x = centerX + (int)Math.round(Math.cos(angle) * radius);
               int y = centerY + (int)Math.round(Math.sin(angle) * radius);
               int size = spark % 3 == 0 ? 2 : 1;
               guiGraphics.fill(x, y, x + size, y + size, color);
            }
         } else {
            this.clickedCell = -1;
         }
      }
   }

   private void renderDecipherStatus(GuiGraphics guiGraphics) {
      if (((AncientScholarTableMenu)this.menu).isDecipherPage() && ((AncientScholarTableMenu)this.menu).getDecipherStatus() == 1) {
         int y = Math.max(4, this.topPos - 9 - 4);
         guiGraphics.drawCenteredString(this.font, INSUFFICIENT_RUNES, this.leftPos + this.imageWidth / 2, y, -43691);
      }
   }

   private boolean isWithinGui(double mouseX, double mouseY, int x, int y, int width, int height) {
      return mouseX >= this.leftPos + x && mouseX < this.leftPos + x + width && mouseY >= this.topPos + y && mouseY < this.topPos + y + height;
   }

   protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
   }
}
