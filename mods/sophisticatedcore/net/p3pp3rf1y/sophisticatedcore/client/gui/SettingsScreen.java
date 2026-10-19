package net.p3pp3rf1y.sophisticatedcore.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.InventoryScrollPanel;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SettingsContainerMenu;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageBackgroundProperties;
import net.p3pp3rf1y.sophisticatedcore.settings.StorageSettingsTabControlBase;

public abstract class SettingsScreen extends AbstractContainerScreen<SettingsContainerMenu<?>> implements InventoryScrollPanel.IInventoryScreen {
   public static final int HEIGHT_WITHOUT_STORAGE_SLOTS = 114;
   public static final Predicate<ItemStack> MATCH_ALL_FILTER = stack -> true;
   private StorageSettingsTabControlBase settingsTabControl;
   private InventoryScrollPanel inventoryScrollPanel = null;
   private TemplatePersistanceControl templatePersistanceControl = null;
   private StorageBackgroundProperties storageBackgroundProperties;
   private boolean mouseDragHandledByOther = false;
   private int visibleSlotsCount;

   protected SettingsScreen(SettingsContainerMenu<?> screenContainer, Inventory inv, Component titleIn) {
      super(screenContainer, inv, titleIn);
      this.updateDimensionsAndSlotPositions(Minecraft.getInstance().getWindow().getGuiScaledHeight());
      this.settingsTabControl = this.initializeTabControl();
   }

   public void resize(Minecraft minecraft, int width, int height) {
      this.updateDimensionsAndSlotPositions(height);
      super.resize(minecraft, width, height);
   }

   private void updateDimensionsAndSlotPositions(int height) {
      int displayableNumberOfRows = Math.min((height - 114) / 18, ((SettingsContainerMenu)this.getMenu()).getNumberOfRows());
      int newImageHeight = 114 + this.getStorageInventoryHeight(displayableNumberOfRows);
      this.storageBackgroundProperties = ((SettingsContainerMenu)this.getMenu()).getNumberOfStorageInventorySlots()
               + ((SettingsContainerMenu)this.getMenu()).getColumnsTaken() * ((SettingsContainerMenu)this.getMenu()).getNumberOfRows()
            <= 81
         ? StorageBackgroundProperties.REGULAR_9_SLOT
         : StorageBackgroundProperties.REGULAR_12_SLOT;
      this.imageWidth = this.storageBackgroundProperties.getSlotsOnLine() * 18 + 14;
      this.updateStorageSlotsPositions();
      if (displayableNumberOfRows < ((SettingsContainerMenu)this.getMenu()).getNumberOfRows()) {
         this.storageBackgroundProperties = this.storageBackgroundProperties == StorageBackgroundProperties.REGULAR_9_SLOT
            ? StorageBackgroundProperties.WIDER_9_SLOT
            : StorageBackgroundProperties.WIDER_12_SLOT;
         this.imageWidth += 6;
      }

      this.imageHeight = newImageHeight;
      this.inventoryLabelY = this.imageHeight - 94;
      this.inventoryLabelX = 8 + this.storageBackgroundProperties.getPlayerInventoryXOffset();
   }

   protected int getStorageInventoryHeight(int displayableNumberOfRows) {
      return displayableNumberOfRows * 18;
   }

   private void updateInventoryScrollPanel() {
      if (this.inventoryScrollPanel != null) {
         this.removeWidget(this.inventoryScrollPanel);
      }

      int numberOfVisibleRows = this.getNumberOfVisibleRows();
      if (numberOfVisibleRows < ((SettingsContainerMenu)this.getMenu()).getNumberOfRows()) {
         this.inventoryScrollPanel = new InventoryScrollPanel(
            Minecraft.getInstance(),
            this,
            0,
            ((SettingsContainerMenu)this.getMenu()).getNumberOfStorageInventorySlots(),
            this.getSlotsOnLine(),
            numberOfVisibleRows * 18,
            this.getGuiTop() + 17,
            this.getGuiLeft() + 7
         );
         this.addRenderableWidget(this.inventoryScrollPanel);
         this.inventoryScrollPanel.updateSlotsPosition();
      } else {
         this.inventoryScrollPanel = null;
      }
   }

   @Override
   public int getVisibleSlotsCount() {
      return this.visibleSlotsCount;
   }

   @Override
   public void setVisibleSlotsCount(int visibleSlotsCount) {
      this.visibleSlotsCount = visibleSlotsCount;
   }

   private int getNumberOfVisibleRows() {
      return Math.min((this.imageHeight - 114) / 18, ((SettingsContainerMenu)this.getMenu()).getNumberOfRows());
   }

   protected void updateStorageSlotsPositions() {
      int yPosition = 18;
      int slotIndex = 0;

      while (slotIndex < ((SettingsContainerMenu)this.getMenu()).getNumberOfStorageInventorySlots()) {
         Slot slot = ((SettingsContainerMenu)this.getMenu()).getSlot(slotIndex);
         int lineIndex = slotIndex % this.getSlotsOnLine();
         slot.x = 8 + lineIndex * 18;
         slot.y = yPosition;
         if (++slotIndex % this.getSlotsOnLine() == 0) {
            yPosition += 18;
         }
      }
   }

   public int getSlotsOnLine() {
      return this.storageBackgroundProperties.getSlotsOnLine() - ((SettingsContainerMenu)this.getMenu()).getColumnsTaken();
   }

   protected void init() {
      super.init();
      this.updateInventoryScrollPanel();
      this.settingsTabControl = this.initializeTabControl();
      this.templatePersistanceControl = this.initializeTemplatePersistanceControl();
      this.addWidget(this.settingsTabControl);
      this.addWidget(this.templatePersistanceControl);
   }

   private TemplatePersistanceControl initializeTemplatePersistanceControl() {
      return new TemplatePersistanceControl(
         new Position(this.leftPos + this.inventoryLabelX - 29, this.topPos + this.inventoryLabelY + 29),
         ((SettingsContainerMenu)this.getMenu()).getTemplatePersistanceContainer()
      );
   }

   protected abstract StorageSettingsTabControlBase initializeTabControl();

   protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
      int x = (this.width - this.imageWidth) / 2;
      int y = (this.height - this.imageHeight) / 2;
      StorageGuiHelper.renderStorageBackground(
         new Position(x, y),
         guiGraphics,
         this.storageBackgroundProperties.getTextureName(),
         this.imageWidth,
         this.getStorageInventoryHeight(this.getNumberOfVisibleRows())
      );
      if (this.inventoryScrollPanel == null) {
         this.drawSlotBg(guiGraphics, x, y, ((SettingsContainerMenu)this.getMenu()).getStorageInventorySlots().size());
      }
   }

   protected void drawSlotBg(GuiGraphics guiGraphics, int x, int y, int visibleSlotsCount) {
      int slotsOnLine = this.getSlotsOnLine();
      int slotRows = visibleSlotsCount / slotsOnLine;
      int remainingSlots = visibleSlotsCount % slotsOnLine;
      GuiHelper.renderSlotsBackground(guiGraphics, x + 7, y + 17, slotsOnLine, slotRows, remainingSlots);
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      ((SettingsContainerMenu)this.menu).detectSettingsChangeAndReload();
      this.settingsTabControl.render(guiGraphics, mouseX, mouseY, partialTicks);
      this.templatePersistanceControl.render(guiGraphics, mouseX, mouseY, partialTicks);
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      this.settingsTabControl.renderTooltip(this, guiGraphics, mouseX, mouseY);
      this.templatePersistanceControl.renderTooltip(this, guiGraphics, mouseX, mouseY);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
   }

   public void renderTransparentBackground(GuiGraphics guiGraphics) {
      PoseStack pose = guiGraphics.pose();
      pose.pushPose();
      pose.translate(0.0F, 0.0F, -12.0F);
      super.renderTransparentBackground(guiGraphics);
      pose.popPose();
   }

   protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      super.renderLabels(guiGraphics, mouseX, mouseY);
      if (this.inventoryScrollPanel == null) {
         this.renderInventorySlots(guiGraphics, mouseX, mouseY, true);
      }
   }

   @Override
   public void renderInventorySlots(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean canShowHover) {
      for (int slotId = 0; slotId < ((SettingsContainerMenu)this.menu).ghostSlots.size(); slotId++) {
         Slot slot = ((SettingsContainerMenu)this.menu).ghostSlots.get(slotId);
         this.renderSlot(guiGraphics, slot);
         this.settingsTabControl.renderSlotOverlays(guiGraphics, slot, this::renderSlotOverlay, this.isTemplateLoadHovered());
         if (canShowHover && this.isHovering(slot, mouseX, mouseY) && slot.isActive()) {
            this.hoveredSlot = slot;
            renderSlotHighlight(guiGraphics, slot.x, slot.y, 0, this.getSlotColor(slotId));
         }

         this.settingsTabControl.renderSlotExtra(guiGraphics, slot);
      }
   }

   protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
      ItemStack itemstack = slot.getItem() != ItemStack.EMPTY
         ? slot.getItem()
         : this.settingsTabControl.getSlotStackDisplayOverride(slot.getSlotIndex(), this.isTemplateLoadHovered());
      RenderSystem.enableDepthTest();
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(0.0F, 0.0F, 100.0F);
      if (!this.settingsTabControl.renderGuiItem(guiGraphics, this.minecraft.getItemRenderer(), itemstack, slot, this.isTemplateLoadHovered())) {
         if (!((SettingsContainerMenu)this.getMenu()).getSlotFilterItem(slot.index).isEmpty()) {
            guiGraphics.renderItem(((SettingsContainerMenu)this.getMenu()).getSlotFilterItem(slot.index), slot.x, slot.y);
         } else {
            Pair<ResourceLocation, ResourceLocation> pair = slot.getNoItemIcon();
            if (pair != null) {
               TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)this.minecraft
                  .getTextureAtlas((ResourceLocation)pair.getFirst())
                  .apply((ResourceLocation)pair.getSecond());
               guiGraphics.blit(slot.x, slot.y, 0, 16, 16, textureatlassprite);
            }
         }
      }

      poseStack.popPose();
      this.settingsTabControl.drawSlotStackOverlay(guiGraphics, slot, this.isTemplateLoadHovered());
   }

   private boolean isTemplateLoadHovered() {
      return this.templatePersistanceControl.isTemplateLoadHovered();
   }

   protected void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type) {
      if (slot != null) {
         this.settingsTabControl.handleSlotClick(slot, mouseButton);
      }
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      if (this.mouseDragHandledByOther) {
         return false;
      }

      Slot slot = this.findSlot(mouseX, mouseY);
      if (slot != null) {
         this.settingsTabControl.handleSlotClick(slot, button);
      }

      for (GuiEventListener child : this.children()) {
         if (child.isMouseOver(mouseX, mouseY) && child.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
            return true;
         }
      }

      return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
   }

   @Nullable
   protected Slot findSlot(double mouseX, double mouseY) {
      for (int i = 0; i < ((SettingsContainerMenu)this.menu).ghostSlots.size(); i++) {
         Slot slot = ((SettingsContainerMenu)this.menu).ghostSlots.get(i);
         if (this.isHovering(slot, mouseX, mouseY) && slot.isActive()) {
            return slot;
         }
      }

      return null;
   }

   protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
      return super.hasClickedOutside(mouseX, mouseY, guiLeftIn, guiTopIn, mouseButton) && this.hasClickedOutsideOfSettings(mouseX, mouseY);
   }

   private boolean hasClickedOutsideOfSettings(double mouseX, double mouseY) {
      return this.settingsTabControl.getTabRectangles().stream().noneMatch(r -> r.contains((int)mouseX, (int)mouseY));
   }

   private void renderSlotOverlay(GuiGraphics guiGraphics, int xPos, int yPos, int height, int slotColor) {
      RenderSystem.disableDepthTest();
      RenderSystem.colorMask(true, true, true, false);
      guiGraphics.fillGradient(xPos, yPos, xPos + 16, yPos + height, slotColor, slotColor);
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.enableDepthTest();
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.sendStorageInventoryScreenOpenMessage();
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   protected abstract void sendStorageInventoryScreenOpenMessage();

   public StorageSettingsTabControlBase getSettingsTabControl() {
      return this.settingsTabControl;
   }

   private Rect2i getTemplatePersistanceControlRectangle() {
      return new Rect2i(
         this.templatePersistanceControl.getX(),
         this.templatePersistanceControl.getY(),
         this.templatePersistanceControl.getWidth(),
         this.templatePersistanceControl.getHeight()
      );
   }

   public List<Rect2i> getExtendedControlsRectangles() {
      if (this.settingsTabControl != null && this.templatePersistanceControl != null) {
         List<Rect2i> rectangles = this.settingsTabControl.getTabRectangles();
         rectangles.add(this.getTemplatePersistanceControlRectangle());
         return rectangles;
      } else {
         return Collections.emptyList();
      }
   }

   @Override
   public boolean isMouseOverSlot(Slot slot, double mouseX, double mouseY) {
      return this.isHovering(slot, mouseX, mouseY);
   }

   @Override
   public void drawSlotBg(GuiGraphics guiGraphics, int visibleSlotsCount) {
      this.drawSlotBg(guiGraphics, (this.width - this.imageWidth) / 2, (this.height - this.imageHeight) / 2, visibleSlotsCount);
   }

   @Override
   public int getTopY() {
      return this.getGuiTop();
   }

   @Override
   public int getLeftX() {
      return this.getGuiLeft();
   }

   @Override
   public Slot getSlot(int slotIndex) {
      return ((SettingsContainerMenu)this.getMenu()).getSlot(slotIndex);
   }

   public void startMouseDragHandledByOther() {
      this.mouseDragHandledByOther = true;
   }

   public void stopMouseDragHandledByOther() {
      this.mouseDragHandledByOther = false;
   }

   @Override
   public Predicate<ItemStack> getStackFilter() {
      return MATCH_ALL_FILTER;
   }
}
