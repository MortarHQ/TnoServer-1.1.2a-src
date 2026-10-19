package mezz.jei.gui.overlay.bookmarks.history;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import mezz.jei.api.helpers.IColorHelper;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IScreenHelper;
import mezz.jei.common.config.HistoryDisplaySide;
import mezz.jei.common.config.IClientConfig;
import mezz.jei.common.config.IClientToggleState;
import mezz.jei.common.config.IIngredientFilterConfig;
import mezz.jei.common.config.IIngredientGridConfig;
import mezz.jei.common.input.IInternalKeyMappings;
import mezz.jei.common.network.IConnectionToServer;
import mezz.jei.common.util.ImmutablePoint2i;
import mezz.jei.common.util.ImmutableRect2i;
import mezz.jei.gui.ghost.GhostIngredientDragManager;
import mezz.jei.gui.ingredients.GuiIngredientProperties;
import mezz.jei.gui.input.IClickableIngredientInternal;
import mezz.jei.gui.input.IDragHandler;
import mezz.jei.gui.input.IDraggableIngredientInternal;
import mezz.jei.gui.input.IRecipeFocusSource;
import mezz.jei.gui.overlay.IIngredientGridSource;
import mezz.jei.gui.overlay.IngredientGrid;
import mezz.jei.gui.overlay.elements.IElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public class LookupHistoryOverlay implements IRecipeFocusSource {
   private static final int INGREDIENT_PADDING = 1;
   public static final int SLOT_HEIGHT = GuiIngredientProperties.getHeight(1);
   private final IngredientGrid contents;
   private final IIngredientGridSource lookupHistory;
   private final IClientConfig clientConfig;
   private final HistoryDisplaySide ownerDisplaySide;
   private final GhostIngredientDragManager ghostIngredientDragManager;
   private int rows;

   public LookupHistoryOverlay(
      IIngredientManager ingredientManager,
      IIngredientGridSource lookupHistory,
      IInternalKeyMappings keyMappings,
      IIngredientGridConfig historyListConfig,
      IIngredientFilterConfig ingredientFilterConfig,
      IClientConfig clientConfig,
      HistoryDisplaySide ownerDisplaySide,
      IClientToggleState toggleState,
      IScreenHelper screenHelper,
      IConnectionToServer serverConnection,
      IColorHelper colorHelper
   ) {
      this.clientConfig = clientConfig;
      this.lookupHistory = lookupHistory;
      this.contents = new IngredientGrid(
         ingredientManager, historyListConfig, ingredientFilterConfig, clientConfig, toggleState, serverConnection, keyMappings, colorHelper, false
      );
      this.ghostIngredientDragManager = new GhostIngredientDragManager(this.contents, screenHelper, ingredientManager, toggleState);
      this.ownerDisplaySide = ownerDisplaySide;
      lookupHistory.addSourceListChangedListener(this::updateLayout);
   }

   public boolean isListDisplayed() {
      return this.clientConfig.isLookupHistoryEnabled() && this.isOnSide() && this.contents.hasRoom();
   }

   public boolean isOnSide() {
      return this.ownerDisplaySide.equals(this.clientConfig.getLookupHistoryDisplaySide());
   }

   public IIngredientGridSource getLookupHistory() {
      return this.lookupHistory;
   }

   public void updateBounds(ImmutableRect2i availableArea, Set<ImmutableRect2i> guiExclusionAreas, @Nullable ImmutablePoint2i mouseExclusionPoint) {
      this.contents.updateBounds(availableArea, guiExclusionAreas, mouseExclusionPoint);
      int rows = this.contents.getArea().getHeight() / SLOT_HEIGHT;
      this.rows = Math.min(rows, this.clientConfig.getMaxLookupHistoryRows());
   }

   public void updateLayout() {
      List<IElement<?>> ingredientList = this.lookupHistory.getElements();
      this.contents.set(0, ingredientList);
   }

   private void drawLine(PoseStack poseStack, int x1, int x2, int y, int argbColor) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      Tesselator tesselator = Tesselator.getInstance();
      BufferBuilder builder = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      float a = (argbColor >> 24 & 0xFF) / 255.0F;
      float r = (argbColor >> 16 & 0xFF) / 255.0F;
      float g = (argbColor >> 8 & 0xFF) / 255.0F;
      float b = (argbColor & 0xFF) / 255.0F;
      Matrix4f pose = poseStack.last().pose();
      int availableWidth = x2 - x1;
      if (availableWidth > 0) {
         int dashWidth = 8;
         int dashHeight = 1;
         int spacing = 6;
         int interval = 14;
         int dashCount = availableWidth / 14;
         float floatInterval = (float)(availableWidth - 8) / dashCount;

         for (float x = x1; x < x2; x += floatInterval) {
            builder.addVertex(pose, Mth.clamp(x + 8.0F, x1, x2), y, 0.0F).setColor(r, g, b, a);
            builder.addVertex(pose, Mth.clamp(x, x1, x2), y, 0.0F).setColor(r, g, b, a);
            builder.addVertex(pose, Mth.clamp(x, x1, x2), y + 1, 0.0F).setColor(r, g, b, a);
            builder.addVertex(pose, Mth.clamp(x + 8.0F, x1, x2), y + 1, 0.0F).setColor(r, g, b, a);
         }

         BufferUploader.drawWithShader(builder.buildOrThrow());
         RenderSystem.disableBlend();
      }
   }

   public void draw(Minecraft minecraft, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      if (this.isListDisplayed()) {
         this.contents.draw(minecraft, guiGraphics, mouseX, mouseY);
         ImmutableRect2i area = this.contents.getArea();
         int endX = area.getX() + area.getWidth();
         int startY = area.getY() + area.getHeight() - this.rows * SLOT_HEIGHT - 3;
         int color = -6974059;
         this.drawLine(guiGraphics.pose(), area.getX(), endX, startY, color);
      }
   }

   public void drawTooltips(Minecraft minecraft, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      if (this.isListDisplayed()) {
         this.ghostIngredientDragManager.drawTooltips(minecraft, guiGraphics, mouseX, mouseY);
         this.contents.drawTooltips(minecraft, guiGraphics, mouseX, mouseY);
      }
   }

   public ImmutableRect2i getArea() {
      return this.contents.getArea();
   }

   public void close() {
      this.ghostIngredientDragManager.stopDrag();
   }

   public void drawOnForeground(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      if (this.isListDisplayed()) {
         this.ghostIngredientDragManager.drawOnForeground(guiGraphics, mouseX, mouseY);
      }
   }

   @Override
   public Stream<IClickableIngredientInternal<?>> getIngredientUnderMouse(double mouseX, double mouseY) {
      return this.isListDisplayed() ? this.contents.getIngredientUnderMouse(mouseX, mouseY) : Stream.empty();
   }

   @Override
   public Stream<IDraggableIngredientInternal<?>> getDraggableIngredientUnderMouse(double mouseX, double mouseY) {
      return this.isListDisplayed() ? this.contents.getDraggableIngredientUnderMouse(mouseX, mouseY) : Stream.empty();
   }

   public IDragHandler createDragHandler() {
      return this.ghostIngredientDragManager.createDragHandler();
   }
}
