package net.p3pp3rf1y.sophisticatedcore.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ContainerScreenEvent.Render.Background;
import net.neoforged.neoforge.client.event.ContainerScreenEvent.Render.Foreground;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.Config;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.Button;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinitions;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.InventoryScrollPanel;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.Label;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.TextBox;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.WidgetBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SortBy;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageBackgroundProperties;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageInventorySlot;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.network.TransferFullSlotPayload;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.crafting.ICraftingUIPart;
import net.p3pp3rf1y.sophisticatedcore.util.CountAbbreviator;
import org.joml.Matrix4f;

public abstract class StorageScreenBase<S extends StorageContainerMenuBase<?>>
   extends AbstractContainerScreen<S>
   implements InventoryScrollPanel.IInventoryScreen {
   public static final int ERROR_BACKGROUND_COLOR = -267386864;
   public static final int ERROR_BORDER_COLOR = DyeColor.RED.getTextureDiffuseColor();
   private static final int DISABLED_SLOT_COLOR = -1072689136;
   private static final int UPGRADE_TOP_HEIGHT = 7;
   private static final int UPGRADE_SLOT_HEIGHT = 16;
   private static final int UPGRADE_BOTTOM_HEIGHT = 6;
   public static final int UPGRADE_INVENTORY_OFFSET = 21;
   public static final int DISABLED_SLOT_X_POS = -2000;
   static final int SLOTS_Y_OFFSET = 17;
   static final int SLOTS_X_OFFSET = 7;
   public static final int ERROR_SLOT_COLOR = DyeColor.RED.getTextureDiffuseColor() & 16777215 | -1442840576;
   private static final int ERROR_TEXT_COLOR = DyeColor.RED.getTextureDiffuseColor();
   public static final int HEIGHT_WITHOUT_STORAGE_SLOTS = 114;
   private UpgradeSettingsTabControl settingsTabControl;
   private final int numberOfUpgradeSlots;
   @Nullable
   private Button sortButton = null;
   @Nullable
   private ToggleButton<SortBy> sortByButton = null;
   private InventoryScrollPanel inventoryScrollPanel = null;
   private final Set<ToggleButton<Boolean>> upgradeSwitches = new HashSet<>();
   private final Map<Integer, UpgradeInventoryPartBase<?>> inventoryParts = new LinkedHashMap<>();
   private static ICraftingUIPart craftingUIPart = ICraftingUIPart.NOOP;
   private static ISlotDecorationRenderer slotDecorationRenderer = (guiGraphics, slot) -> {};
   protected StorageBackgroundProperties storageBackgroundProperties;
   @Nullable
   private Button transferToStorageButton;
   @Nullable
   private Button transferToInventoryButton;
   private TextBox searchBox;
   private Label noResultsLabel;
   private Predicate<ItemStack> stackFilter = stack -> this.searchBox == null
      || this.searchBox.getValue().isEmpty()
      || !stack.isEmpty() && stack.getHoverName().getString().toLowerCase().contains(this.searchBox.getValue().toLowerCase());
   private int visibleSlotsCount;
   private boolean initializing = true;

   public static void setCraftingUIPart(ICraftingUIPart part) {
      craftingUIPart = part;
   }

   public static void setSlotDecorationRenderer(ISlotDecorationRenderer renderer) {
      slotDecorationRenderer = renderer;
   }

   protected StorageScreenBase(S menu, Inventory playerInventory, Component title) {
      super(menu, playerInventory, title);
      this.numberOfUpgradeSlots = ((StorageContainerMenuBase)this.getMenu()).getNumberOfUpgradeSlots();
      this.visibleSlotsCount = ((StorageContainerMenuBase)this.getMenu()).getNumberOfStorageInventorySlots();
      this.updateDimensionsAndSlotPositions(Minecraft.getInstance().getWindow().getGuiScaledHeight());
   }

   public ICraftingUIPart getCraftingUIAddition() {
      return craftingUIPart;
   }

   public void resize(Minecraft minecraft, int width, int height) {
      this.updateDimensionsAndSlotPositions(height);
      super.resize(minecraft, width, height);
   }

   private void updateDimensionsAndSlotPositions(int height) {
      int displayableNumberOfRows = Math.min((height - 114) / 18, ((StorageContainerMenuBase)this.getMenu()).getNumberOfRows());
      int newImageHeight = 114 + this.getStorageInventoryHeight(displayableNumberOfRows);
      this.storageBackgroundProperties = ((StorageContainerMenuBase)this.getMenu()).getNumberOfStorageInventorySlots()
               + ((StorageContainerMenuBase)this.getMenu()).getColumnsTaken() * ((StorageContainerMenuBase)this.getMenu()).getNumberOfRows()
            <= 81
         ? StorageBackgroundProperties.REGULAR_9_SLOT
         : StorageBackgroundProperties.REGULAR_12_SLOT;
      this.imageWidth = this.storageBackgroundProperties.getSlotsOnLine() * 18 + 14;
      this.updateStorageSlotsPositions();
      this.updateNoResultsLabel();
      if (displayableNumberOfRows < ((StorageContainerMenuBase)this.getMenu()).getNumberOfRows()) {
         this.storageBackgroundProperties = this.storageBackgroundProperties == StorageBackgroundProperties.REGULAR_9_SLOT
            ? StorageBackgroundProperties.WIDER_9_SLOT
            : StorageBackgroundProperties.WIDER_12_SLOT;
         this.imageWidth += 6;
      }

      this.imageHeight = newImageHeight;
      this.inventoryLabelY = this.imageHeight - 94;
      this.inventoryLabelX = 8 + this.storageBackgroundProperties.getPlayerInventoryXOffset();
      this.updatePlayerSlotsPositions();
      this.updateExtraSlotsPositions();
      this.updateUpgradeSlotsPositions();
      this.updateTransferButtonsPositions();
   }

   public int getInventoryLabelX() {
      return this.inventoryLabelX;
   }

   protected void updateExtraSlotsPositions() {
   }

   protected int getStorageInventoryHeight(int displayableNumberOfRows) {
      return displayableNumberOfRows * 18;
   }

   @Override
   public Slot getSlot(int slotIndex) {
      return ((StorageContainerMenuBase)this.getMenu()).getSlot(slotIndex);
   }

   protected void updateUpgradeSlotsPositions() {
      int yPosition = 6;

      for (int slotIndex = 0; slotIndex < this.numberOfUpgradeSlots; slotIndex++) {
         Slot slot = ((StorageContainerMenuBase)this.getMenu()).getSlot(((StorageContainerMenuBase)this.getMenu()).getFirstUpgradeSlot() + slotIndex);
         slot.y = yPosition;
         yPosition += 16;
      }
   }

   private void updateNoResultsLabel() {
      if (this.noResultsLabel != null) {
         if (this.visibleSlotsCount == 0) {
            if (!this.renderables.contains(this.noResultsLabel)) {
               this.addRenderableWidget(this.noResultsLabel);
            }
         } else {
            this.removeWidget(this.noResultsLabel);
         }
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

   protected void updateStorageSlotsPositions() {
      int yPosition = 18;
      this.visibleSlotsCount = 0;
      int slotIndex = 0;

      while (slotIndex < ((StorageContainerMenuBase)this.getMenu()).getNumberOfStorageInventorySlots()) {
         Slot slot = ((StorageContainerMenuBase)this.getMenu()).getSlot(slotIndex);
         int lineIndex = this.visibleSlotsCount % this.getSlotsOnLine();
         slotIndex++;
         if (this.stackFilter.test(slot.getItem())) {
            slot.x = 8 + lineIndex * 18;
            slot.y = yPosition;
            this.visibleSlotsCount++;
            if (this.visibleSlotsCount % this.getSlotsOnLine() == 0) {
               yPosition += 18;
            }
         } else {
            slot.x = -2000;
         }
      }
   }

   @Override
   public Predicate<ItemStack> getStackFilter() {
      return this.stackFilter;
   }

   protected void updatePlayerSlotsPositions() {
      int playerInventoryXOffset = this.storageBackgroundProperties.getPlayerInventoryXOffset();
      int yPosition = this.inventoryLabelY + 12;

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 9; j++) {
            int slotIndex = j + i * 9;
            int xPosition = playerInventoryXOffset + 8 + j * 18;
            Slot slot = ((StorageContainerMenuBase)this.getMenu()).getSlot(((StorageContainerMenuBase)this.getMenu()).getInventorySlotsSize() - 36 + slotIndex);
            slot.x = xPosition;
            slot.y = yPosition;
         }

         yPosition += 18;
      }

      yPosition += 4;

      for (int slotIndex = 0; slotIndex < 9; slotIndex++) {
         int xPosition = playerInventoryXOffset + 8 + slotIndex * 18;
         Slot slot = ((StorageContainerMenuBase)this.getMenu())
            .getSlot(((StorageContainerMenuBase)this.getMenu()).getInventorySlotsSize() - 36 + 27 + slotIndex);
         slot.x = xPosition;
         slot.y = yPosition;
      }
   }

   protected void init() {
      super.init();
      this.updateInventoryScrollPanel();
      craftingUIPart.setStorageScreen(this);
      this.initUpgradeSettingsControl();
      this.initUpgradeInventoryParts();
      this.addUpgradeSwitches();
      ((StorageContainerMenuBase)this.getMenu()).setUpgradeChangeListener(c -> {
         this.updateStorageSlotsPositions();
         this.updatePlayerSlotsPositions();
         this.updateExtraSlotsPositions();
         this.updateUpgradeSlotsPositions();
         this.updateInventoryScrollPanel();
         this.updateNoResultsLabel();
         this.children().remove(this.settingsTabControl);
         craftingUIPart.onCraftingSlotsHidden();
         this.initUpgradeSettingsControl();
         this.initUpgradeInventoryParts();
         this.addUpgradeSwitches();
      });
      if (this.shouldShowSortButtons()) {
         this.addSortButtons();
      }

      this.addTransferButtons();
      this.addSearchBox();
      this.initializing = false;
   }

   protected void addSearchBox() {
      SortButtonsPosition sortButtonsPosition = (SortButtonsPosition)Config.CLIENT.sortButtonsPosition.get();
      int x = 7;
      int xEnd = sortButtonsPosition == SortButtonsPosition.TITLE_LINE_RIGHT
         ? this.getSortButtonsPosition(sortButtonsPosition).x() - 1 - this.leftPos
         : this.imageWidth - 7;
      int width = xEnd - x;
      this.searchBox = new SearchBox(new Position(this.leftPos + x, this.topPos + 5), new Dimension(width, 10), this);
      this.searchBox.setResponder(this::onSearchPhraseChange);
      if (((StorageContainerMenuBase)this.getMenu()).shouldKeepSearchPhrase()) {
         this.searchBox.setValue(((StorageContainerMenuBase)this.getMenu()).getSearchPhrase());
      }

      this.addRenderableWidget(this.searchBox);
      if (this.noResultsLabel != null) {
         this.removeWidget(this.noResultsLabel);
      }

      this.noResultsLabel = new Label(
         new Position(this.leftPos + 7, this.topPos + 18), Component.translatable(TranslationHelper.INSTANCE.translGui("label.no_search_results"))
      );
      if (this.visibleSlotsCount == 0) {
         this.addRenderableWidget(this.noResultsLabel);
      }
   }

   private void onSearchPhraseChange(String searchPhrase) {
      if (!this.initializing) {
         ((StorageContainerMenuBase)this.getMenu()).setSearchPhrase(searchPhrase);
      }

      this.updateSearchFilter(searchPhrase);
      if (this.inventoryScrollPanel != null) {
         this.inventoryScrollPanel.resetScrollDistance();
         this.inventoryScrollPanel.updateSlotsPosition();
      } else {
         this.updateStorageSlotsPositions();
      }

      this.updateNoResultsLabel();
   }

   private void updateSearchFilter(String searchPhrase) {
      if (searchPhrase.trim().isEmpty()) {
         this.stackFilter = stack -> true;
      } else {
         String[] searchTerms = searchPhrase.trim().split(" ");
         List<Predicate<ItemStack>> filters = new ArrayList<>();

         for (String searchTerm : searchTerms) {
            if (searchTerm.startsWith("@")) {
               String modName = searchTerm.substring(1).toLowerCase();
               filters.add(stack -> modName.isEmpty() || BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace().contains(modName));
            } else if (searchTerm.startsWith("#")) {
               String tooltipKeyword = searchTerm.substring(1).toLowerCase();
               filters.add(
                  stack -> getTooltipFromItem(this.minecraft, stack).stream().anyMatch(line -> line.getString().toLowerCase().contains(tooltipKeyword))
               );
            } else {
               filters.add(stack -> stack.getHoverName().getString().toLowerCase().contains(searchTerm.toLowerCase()));
            }
         }

         this.stackFilter = stack -> !stack.isEmpty() && filters.stream().allMatch(f -> f.test(stack));
      }
   }

   private void addTransferButtons() {
      this.transferToStorageButton = new StorageScreenBase.TransferButton(
         filterByContents -> ((StorageContainerMenuBase)this.getMenu()).transferItemsToStorage(filterByContents),
         ButtonDefinitions.TRANSFER_TO_STORAGE,
         ButtonDefinitions.TRANSFER_TO_STORAGE_FILTERED
      );
      this.addRenderableWidget(this.transferToStorageButton);
      this.transferToInventoryButton = new StorageScreenBase.TransferButton(
         filterByContents -> ((StorageContainerMenuBase)this.getMenu()).transferItemsToPlayerInventory(filterByContents),
         ButtonDefinitions.TRANSFER_TO_INVENTORY,
         ButtonDefinitions.TRANSFER_TO_INVENTORY_FILTERED
      );
      this.addRenderableWidget(this.transferToInventoryButton);
      this.updateTransferButtonsPositions();
   }

   protected boolean shouldShowSortButtons() {
      return true;
   }

   private void updateInventoryScrollPanel() {
      if (this.inventoryScrollPanel != null) {
         this.removeWidget(this.inventoryScrollPanel);
      }

      int numberOfVisibleRows = this.getNumberOfVisibleRows();
      if (numberOfVisibleRows < ((StorageContainerMenuBase)this.getMenu()).getNumberOfRows()) {
         this.inventoryScrollPanel = new InventoryScrollPanel(
            Minecraft.getInstance(),
            this,
            0,
            ((StorageContainerMenuBase)this.getMenu()).getNumberOfStorageInventorySlots(),
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

   private void updateTransferButtonsPositions() {
      if (this.transferToStorageButton != null && this.transferToInventoryButton != null) {
         this.transferToStorageButton.setPosition(new Position(this.leftPos + this.inventoryLabelX + 137, this.topPos + this.inventoryLabelY - 2));
         this.transferToInventoryButton.setPosition(new Position(this.leftPos + this.inventoryLabelX + 149, this.topPos + this.inventoryLabelY - 2));
      }
   }

   public Optional<Position> getTransferToInventoryButtonPosition() {
      return this.transferToInventoryButton == null
         ? Optional.empty()
         : Optional.of(new Position(this.transferToInventoryButton.getX(), this.transferToInventoryButton.getY()));
   }

   public void shiftTransferButtons(int shiftX) {
      if (this.transferToStorageButton != null && this.transferToInventoryButton != null && shiftX != 0) {
         this.transferToStorageButton.setPosition(new Position(this.transferToStorageButton.getX() + shiftX, this.transferToStorageButton.getY()));
         this.transferToInventoryButton.setPosition(new Position(this.transferToInventoryButton.getX() + shiftX, this.transferToInventoryButton.getY()));
      }
   }

   public void setExternalSearchPhrase(String searchPhrase) {
      String phrase = searchPhrase == null ? "" : searchPhrase;
      if (this.searchBox != null) {
         this.searchBox.setValue(phrase);
      } else {
         ((StorageContainerMenuBase)this.getMenu()).setSearchPhrase(phrase);
         this.updateSearchFilter(phrase);
      }
   }

   private int getNumberOfVisibleRows() {
      return Math.min((this.imageHeight - 114) / 18, ((StorageContainerMenuBase)this.getMenu()).getNumberOfRows());
   }

   public int getSlotsOnLine() {
      return this.storageBackgroundProperties.getSlotsOnLine() - ((StorageContainerMenuBase)this.getMenu()).getColumnsTaken();
   }

   private void initUpgradeInventoryParts() {
      this.inventoryParts.clear();
      if (((StorageContainerMenuBase)this.getMenu()).getColumnsTaken() != 0) {
         int numberOfVisibleRows = this.getNumberOfVisibleRows();
         int scrollBarOffset = numberOfVisibleRows < ((StorageContainerMenuBase)this.getMenu()).getNumberOfRows() ? 6 : 0;
         AtomicReference<Position> pos = new AtomicReference<>(new Position(7 + this.getSlotsOnLine() * 18 + scrollBarOffset, 17));
         int height = numberOfVisibleRows * 18;

         for (Entry<Integer, UpgradeContainerBase<?, ?>> entry : ((StorageContainerMenuBase)this.getMenu()).getUpgradeContainers().entrySet()) {
            UpgradeContainerBase<?, ?> container = entry.getValue();
            UpgradeGuiManager.getInventoryPart(entry.getKey(), container, pos.get(), height, this).ifPresent(part -> {
               this.inventoryParts.put(entry.getKey(), (UpgradeInventoryPartBase<?>)part);
               pos.set(new Position(pos.get().x() + 36, pos.get().y()));
            });
         }
      }
   }

   private void addUpgradeSwitches() {
      this.upgradeSwitches.clear();
      int switchTop = this.topPos + 8;

      for (int slot = 0; slot < this.numberOfUpgradeSlots; slot++) {
         if (((StorageContainerMenuBase)this.menu).canDisableUpgrade(slot)) {
            final int finalSlot = slot;
            ToggleButton<Boolean> upgradeSwitch = new ToggleButton<Boolean>(
               new Position(this.leftPos - 22, switchTop),
               ButtonDefinitions.UPGRADE_SWITCH,
               button -> ((StorageContainerMenuBase)this.getMenu())
                  .setUpgradeEnabled(finalSlot, !((StorageContainerMenuBase)this.getMenu()).getUpgradeEnabled(finalSlot)),
               () -> ((StorageContainerMenuBase)this.getMenu()).getUpgradeEnabled(finalSlot)
            ) {
               @Override
               protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                  if (((StorageContainerMenuBase)StorageScreenBase.this.menu).isUpgradeRunnable(finalSlot)) {
                     super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
                  } else {
                     GuiHelper.blit(guiGraphics, this.x, this.y, ButtonDefinitions.UPGRADE_SWITCH_INACTIVE.getForegroundTexture());
                  }
               }

               @Override
               public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
                  if (((StorageContainerMenuBase)StorageScreenBase.this.menu).isUpgradeRunnable(finalSlot)) {
                     super.renderTooltip(screen, guiGraphics, mouseX, mouseY);
                  } else if (this.isMouseOver(mouseX, mouseY)) {
                     GuiHelper.renderTooltip(screen, guiGraphics, ButtonDefinitions.UPGRADE_SWITCH_INACTIVE.getTooltip(), mouseX, mouseY);
                  }
               }

               @Override
               protected void renderHoveredBackground(GuiGraphics guiGraphics) {
                  if (((StorageContainerMenuBase)StorageScreenBase.this.menu).isUpgradeRunnable(finalSlot)) {
                     super.renderHoveredBackground(guiGraphics);
                  } else {
                     GuiHelper.blit(guiGraphics, this.x, this.y, ButtonDefinitions.UPGRADE_SWITCH_INACTIVE.getBackgroundTexture());
                  }
               }
            };
            this.addWidget(upgradeSwitch);
            this.upgradeSwitches.add(upgradeSwitch);
         }

         switchTop += 16;
      }
   }

   private void addSortButtons() {
      SortButtonsPosition sortButtonsPosition = (SortButtonsPosition)Config.CLIENT.sortButtonsPosition.get();
      if (sortButtonsPosition != SortButtonsPosition.HIDDEN) {
         Position pos = this.getSortButtonsPosition(sortButtonsPosition);
         this.sortButton = new Button(new Position(pos.x(), pos.y()), ButtonDefinitions.SORT, button -> {
            if (button == 0) {
               ((StorageContainerMenuBase)this.getMenu()).sort();
            }
         });
         this.addWidget(this.sortButton);
         this.sortByButton = new ToggleButton<>(new Position(pos.x() + 12, pos.y()), ButtonDefinitions.SORT_BY, button -> {
            if (button == 0) {
               ((StorageContainerMenuBase)this.getMenu()).setSortBy(((StorageContainerMenuBase)this.getMenu()).getSortBy().next());
            }
         }, () -> ((StorageContainerMenuBase)this.getMenu()).getSortBy());
         this.addWidget(this.sortByButton);
      }
   }

   private Position getSortButtonsPosition(SortButtonsPosition sortButtonsPosition) {
      return switch (sortButtonsPosition) {
         case BELOW_UPGRADES -> new Position(this.leftPos - 21 - 2, this.topPos + this.getUpgradeHeightWithoutBottom() + 6 + 2);
         case BELOW_UPGRADE_TABS -> new Position(
            this.settingsTabControl.getX() + 2, this.settingsTabControl.getY() + Math.max(0, this.settingsTabControl.getHeight() + 2)
         );
         default -> new Position(this.leftPos + this.imageWidth - 31, this.topPos + 4);
      };
   }

   private void initUpgradeSettingsControl() {
      this.settingsTabControl = new UpgradeSettingsTabControl(
         new Position(this.leftPos + this.imageWidth, this.topPos + 4), this, this.getStorageSettingsTabTooltip()
      );
      this.addWidget(this.settingsTabControl);
   }

   protected abstract String getStorageSettingsTabTooltip();

   public int getUpgradeHeight() {
      return this.getUpgradeHeightWithoutBottom() + 7;
   }

   protected int getUpgradeHeightWithoutBottom() {
      return 6 + this.numberOfUpgradeSlots * 16;
   }

   public Optional<Rect2i> getSortButtonsRectangle() {
      return this.sortButton != null && this.sortByButton != null
         ? GuiHelper.getPositiveRectangle(
            this.sortButton.getX(),
            this.sortButton.getY(),
            this.sortByButton.getX() + this.sortByButton.getWidth() - this.sortButton.getX(),
            this.sortByButton.getY() + this.sortByButton.getHeight() - this.sortButton.getY()
         )
         : Optional.empty();
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      if (((StorageContainerMenuBase)this.menu).detectSettingsChangeAndReload()) {
         this.updateStorageSlotsPositions();
         this.updatePlayerSlotsPositions();
         this.updateExtraSlotsPositions();
         this.updateInventoryScrollPanel();
         this.updateNoResultsLabel();
         this.updateTransferButtonsPositions();
      }

      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(0.0F, 0.0F, -20.0F);
      this.renderTransparentBackground(guiGraphics);
      poseStack.popPose();
      this.settingsTabControl.render(guiGraphics, mouseX, mouseY, partialTicks);
      this.renderSuper(guiGraphics, mouseX, mouseY, partialTicks);
      this.settingsTabControl.renderTooltip(this, guiGraphics, mouseX, mouseY);
      if (this.sortButton != null && this.sortByButton != null) {
         this.sortButton.render(guiGraphics, mouseX, mouseY, partialTicks);
         this.sortByButton.render(guiGraphics, mouseX, mouseY, partialTicks);
      }

      this.upgradeSwitches.forEach(us -> us.render(guiGraphics, mouseX, mouseY, partialTicks));
      this.renderErrorOverlay(guiGraphics);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
   }

   private void renderSuper(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      int i = this.leftPos;
      int j = this.topPos;
      this.renderBg(guiGraphics, partialTick, mouseX, mouseY);
      NeoForge.EVENT_BUS.post(new Background(this, guiGraphics, mouseX, mouseY));
      RenderSystem.disableDepthTest();
      this.hoveredSlot = null;

      for (Renderable widget : this.renderables) {
         widget.render(guiGraphics, mouseX, mouseY, partialTick);
      }

      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(i, j, 0.0);

      for (int k = 0; k < 36; k++) {
         Slot slot = ((StorageContainerMenuBase)this.getMenu()).getSlot(((StorageContainerMenuBase)this.getMenu()).getInventorySlotsSize() - 36 + k);
         if (slot.isActive()) {
            this.renderSlot(guiGraphics, slot);
         }

         if (this.isHovering(slot, mouseX, mouseY) && slot.isActive()) {
            this.hoveredSlot = slot;
            int l = slot.x;
            int i1 = slot.y;
            renderSlotHighlight(guiGraphics, l, i1, 0, this.getSlotColor(k));
         }
      }

      this.renderLabels(guiGraphics, mouseX, mouseY);
      NeoForge.EVENT_BUS.post(new Foreground(this, guiGraphics, mouseX, mouseY));
      ItemStack itemstack = this.draggingItem.isEmpty() ? ((StorageContainerMenuBase)this.menu).getCarried() : this.draggingItem;
      if (!itemstack.isEmpty()) {
         int i2 = this.draggingItem.isEmpty() ? 8 : 16;
         String s = null;
         if (!this.draggingItem.isEmpty() && this.isSplittingStack) {
            itemstack = itemstack.copy();
            itemstack.setCount(Mth.ceil(itemstack.getCount() / 2.0F));
         } else if (this.isQuickCrafting && this.quickCraftSlots.size() > 1) {
            itemstack = itemstack.copy();
            itemstack.setCount(this.quickCraftingRemainder);
            if (itemstack.isEmpty()) {
               s = ChatFormatting.YELLOW + "0";
            }
         }

         this.renderFloatingItem(guiGraphics, itemstack, mouseX - i - 8, mouseY - j - i2, s);
      }

      poseStack.popPose();
      RenderSystem.enableDepthTest();
   }

   protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      super.renderLabels(guiGraphics, mouseX, mouseY);
      this.renderUpgradeInventoryParts(guiGraphics, mouseX, mouseY);
      this.renderUpgradeSlots(guiGraphics, mouseX, mouseY);
      if (this.inventoryScrollPanel == null) {
         this.renderStorageInventorySlots(guiGraphics, mouseX, mouseY);
      }
   }

   private void renderUpgradeInventoryParts(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      this.inventoryParts.values().forEach(ip -> ip.render(guiGraphics, mouseX, mouseY));
   }

   private void renderStorageInventorySlots(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      this.renderStorageInventorySlots(guiGraphics, mouseX, mouseY, true);
   }

   private void renderStorageInventorySlots(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean canShowHover) {
      for (int slotId = 0;
         slotId < ((StorageContainerMenuBase)this.menu).realInventorySlots.size()
            && slotId < ((StorageContainerMenuBase)this.getMenu()).getNumberOfStorageInventorySlots();
         slotId++
      ) {
         Slot slot = ((StorageContainerMenuBase)this.menu).realInventorySlots.get(slotId);
         this.renderSlot(guiGraphics, slot);
         if (canShowHover && this.isHovering(slot, mouseX, mouseY) && slot.isActive()) {
            this.hoveredSlot = slot;
            renderSlotHighlight(guiGraphics, slot.x, slot.y, 0, this.getSlotColor(slotId));
         }
      }
   }

   private void renderUpgradeSlots(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      for (int slotId = 0; slotId < ((StorageContainerMenuBase)this.menu).upgradeSlots.size(); slotId++) {
         Slot slot = ((StorageContainerMenuBase)this.menu).upgradeSlots.get(slotId);
         if (slot.x != -2000) {
            this.renderSlot(guiGraphics, slot);
            if (!slot.isActive()) {
               this.renderSlotOverlay(guiGraphics, slot, -1072689136);
            }
         }

         if (this.isHovering(slot, mouseX, mouseY) && slot.isActive()) {
            this.hoveredSlot = slot;
            renderSlotHighlight(guiGraphics, slot.x, slot.y, 0, this.getSlotColor(slotId));
         }
      }
   }

   protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
      int i = slot.x;
      int j = slot.y;
      ItemStack stackToRender = slot.getItem();
      boolean flag = false;
      boolean rightClickDragging = slot == this.clickedSlot && !this.draggingItem.isEmpty() && !this.isSplittingStack;
      ItemStack carriedStack = ((StorageContainerMenuBase)this.getMenu()).getCarried();
      String stackCountText = null;
      if (((StorageContainerMenuBase)this.getMenu()).isInfiniteSlot(slot.index)) {
         stackCountText = "∞";
      }

      if (slot == this.clickedSlot && !this.draggingItem.isEmpty() && this.isSplittingStack && !stackToRender.isEmpty()) {
         stackToRender = stackToRender.copy();
         stackToRender.setCount(stackToRender.getCount() / 2);
      } else if (this.isQuickCrafting && this.quickCraftSlots.contains(slot) && !carriedStack.isEmpty()) {
         if (this.quickCraftSlots.size() == 1) {
            return;
         }

         if (StorageContainerMenuBase.canItemQuickReplace(slot, carriedStack) && ((StorageContainerMenuBase)this.menu).canDragTo(slot)) {
            flag = true;
            int slotStackCount = stackToRender.isEmpty() ? 0 : stackToRender.getCount();
            int renderCount = StorageContainerMenuBase.getQuickCraftPlaceCount(slot, this.quickCraftSlots.size(), this.quickCraftingType, carriedStack)
               + slotStackCount;
            int slotLimit = stackToRender.isEmpty() ? 64 : slot.getMaxStackSize(stackToRender);
            if (renderCount > slotLimit) {
               stackCountText = ChatFormatting.YELLOW + CountAbbreviator.abbreviate(slotLimit);
            }

            stackToRender = carriedStack.copyWithCount(renderCount);
         } else {
            this.quickCraftSlots.remove(slot);
            this.recalculateQuickCraftRemaining();
         }
      }

      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(0.0F, 0.0F, 100.0F);
      if (stackToRender.isEmpty() && slot.isActive()) {
         this.renderSlotBackground(guiGraphics, slot, i, j);
      } else if (!rightClickDragging) {
         this.renderStack(guiGraphics, i, j, stackToRender, flag, stackCountText);
         slotDecorationRenderer.renderDecoration(guiGraphics, slot);
      }

      poseStack.popPose();
   }

   private void renderStack(GuiGraphics guiGraphics, int x, int y, ItemStack itemstack, boolean flag, @Nullable String stackCountText) {
      if (flag) {
         guiGraphics.fill(x, y, x + 16, y + 16, -2130706433);
      }

      RenderSystem.enableDepthTest();
      guiGraphics.renderItem(itemstack, x, y);
      if (this.shouldUseSpecialCountRender(itemstack)) {
         guiGraphics.renderItemDecorations(this.font, itemstack, x, y, "");
         if (stackCountText == null) {
            stackCountText = CountAbbreviator.abbreviate(itemstack.getCount());
         }

         this.renderStackCount(guiGraphics, stackCountText, x, y);
      } else {
         guiGraphics.renderItemDecorations(this.font, itemstack, x, y, stackCountText);
      }

      RenderSystem.disableDepthTest();
   }

   private void renderSlotBackground(GuiGraphics guiGraphics, Slot slot, int i, int j) {
      Optional<ItemStack> memorizedStack = ((StorageContainerMenuBase)this.getMenu()).getMemorizedStackInSlot(slot.index);
      if (slot instanceof StorageInventorySlot && ((StorageContainerMenuBase)this.getMenu()).isStorageInventorySlot(slot.index)) {
         if (memorizedStack.isPresent()) {
            guiGraphics.renderItem(memorizedStack.get(), i, j);
            this.drawStackOverlay(guiGraphics, i, j);
            return;
         }

         if (!((StorageContainerMenuBase)this.getMenu()).getSlotFilterItem(slot.index).isEmpty()) {
            guiGraphics.renderItem(((StorageContainerMenuBase)this.getMenu()).getSlotFilterItem(slot.index), i, j);
            this.drawStackOverlay(guiGraphics, i, j);
            return;
         }
      }

      Pair<ResourceLocation, ResourceLocation> pair = slot.getNoItemIcon();
      if (pair != null) {
         TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)this.minecraft
            .getTextureAtlas((ResourceLocation)pair.getFirst())
            .apply((ResourceLocation)pair.getSecond());
         guiGraphics.blit(i, j, 0, 16, 16, textureatlassprite);
      }
   }

   private void drawStackOverlay(GuiGraphics guiGraphics, int x, int y) {
      guiGraphics.pose().pushPose();
      RenderSystem.enableBlend();
      RenderSystem.disableDepthTest();
      guiGraphics.blit(GuiHelper.GUI_CONTROLS, x, y, 77, 0, 16, 16);
      RenderSystem.enableDepthTest();
      RenderSystem.disableBlend();
      guiGraphics.pose().popPose();
   }

   private boolean shouldUseSpecialCountRender(ItemStack itemstack) {
      return itemstack.getCount() > 99;
   }

   private void renderSlotOverlay(GuiGraphics guiGraphics, Slot slot, int slotColor) {
      this.renderSlotOverlay(guiGraphics, slot, slotColor, 0, 16);
   }

   private void renderSlotOverlay(GuiGraphics guiGraphics, Slot slot, int slotColor, int yOffset, int height) {
      this.renderOverlay(guiGraphics, slotColor, slot.x, slot.y + yOffset, 16, height);
   }

   public void renderOverlay(GuiGraphics guiGraphics, int slotColor, int xPos, int yPos, int width, int height) {
      RenderSystem.disableDepthTest();
      RenderSystem.colorMask(true, true, true, false);
      guiGraphics.fillGradient(xPos, yPos, xPos + width, yPos + height, 0, slotColor, slotColor);
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.enableDepthTest();
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
      int x = (this.width - this.imageWidth) / 2;
      int y = (this.height - this.imageHeight) / 2;
      this.drawInventoryBg(guiGraphics, x, y, this.storageBackgroundProperties.getTextureName());
      if (this.inventoryScrollPanel == null) {
         this.drawSlotBg(guiGraphics, x, y, this.visibleSlotsCount);
         this.drawSlotOverlays(guiGraphics);
      }

      this.drawUpgradeBackground(guiGraphics);
   }

   protected void drawSlotBg(GuiGraphics guiGraphics, int x, int y, int visibleSlotsCount) {
      int slotsOnLine = this.getSlotsOnLine();
      int slotRows = visibleSlotsCount / slotsOnLine;
      int remainingSlots = visibleSlotsCount % slotsOnLine;
      GuiHelper.renderSlotsBackground(guiGraphics, x + 7, y + 17, slotsOnLine, slotRows, remainingSlots);
   }

   private void drawSlotOverlays(GuiGraphics guiGraphics) {
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(this.getGuiLeft(), this.getGuiTop(), 0.0F);

      for (int slotNumber = 0; slotNumber < ((StorageContainerMenuBase)this.menu).getNumberOfStorageInventorySlots(); slotNumber++) {
         List<Integer> colors = ((StorageContainerMenuBase)this.menu).getSlotOverlayColors(slotNumber);
         if (!colors.isEmpty()) {
            int stripeHeight = 16 / colors.size();
            int i = 0;

            for (int slotColor : colors) {
               int yOffset = i * stripeHeight;
               this.renderSlotOverlay(
                  guiGraphics,
                  ((StorageContainerMenuBase)this.menu).getSlot(slotNumber),
                  slotColor & 16777215 | 1342177280,
                  yOffset,
                  i == colors.size() - 1 ? 16 - yOffset : stripeHeight
               );
               i++;
            }
         }
      }

      poseStack.popPose();
   }

   protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
      this.inventoryParts.values().forEach(part -> part.renderTooltip(this, guiGraphics, x, y));
      if (((StorageContainerMenuBase)this.getMenu()).getCarried().isEmpty() && this.hoveredSlot != null) {
         if (this.hoveredSlot.hasItem()) {
            super.renderTooltip(guiGraphics, x, y);
         } else if (this.hoveredSlot instanceof INameableEmptySlot emptySlot && emptySlot.hasEmptyTooltip()) {
            guiGraphics.renderComponentTooltip(this.font, Collections.singletonList(emptySlot.getEmptyTooltip()), x, y);
         }
      }

      if (this.sortButton != null) {
         this.sortButton.renderTooltip(this, guiGraphics, x, y);
      }

      if (this.sortByButton != null) {
         this.sortByButton.renderTooltip(this, guiGraphics, x, y);
      }

      if (this.transferToStorageButton != null) {
         this.transferToStorageButton.renderTooltip(this, guiGraphics, x, y);
      }

      if (this.transferToInventoryButton != null) {
         this.transferToInventoryButton.renderTooltip(this, guiGraphics, x, y);
      }

      if (this.searchBox != null) {
         this.searchBox.renderTooltip(this, guiGraphics, x, y);
      }

      this.upgradeSwitches.forEach(us -> us.renderTooltip(this, guiGraphics, x, y));
   }

   protected List<Component> getTooltipFromContainerItem(ItemStack itemStack) {
      List<Component> ret = getTooltipFromItem(this.minecraft, itemStack);
      if (this.hoveredSlot != null && this.hoveredSlot instanceof StorageInventorySlot && this.hoveredSlot.getMaxStackSize() != itemStack.getMaxStackSize()) {
         ret.add(
            Component.translatable(
                  TranslationHelper.INSTANCE.translGuiTooltip("stack_count"),
                  new Object[]{
                     Component.literal(NumberFormat.getNumberInstance().format(itemStack.getCount()))
                        .withStyle(ChatFormatting.DARK_AQUA)
                        .append(Component.literal(" / ").withStyle(ChatFormatting.GRAY))
                        .append(
                           Component.literal(NumberFormat.getNumberInstance().format(this.hoveredSlot.getMaxStackSize(itemStack)))
                              .withStyle(ChatFormatting.DARK_AQUA)
                        )
                  }
               )
               .withStyle(ChatFormatting.GRAY)
         );
      }

      return ret;
   }

   public void drawInventoryBg(GuiGraphics guiGraphics, int x, int y, ResourceLocation textureName) {
      StorageGuiHelper.renderStorageBackground(new Position(x, y), guiGraphics, textureName, this.imageWidth, this.imageHeight - 114);
   }

   private void drawUpgradeBackground(GuiGraphics guiGraphics) {
      if (this.numberOfUpgradeSlots != 0) {
         int heightWithoutBottom = this.getUpgradeHeightWithoutBottom();
         guiGraphics.blit(GuiHelper.GUI_CONTROLS, this.leftPos - 21, this.topPos, 0.0F, 0.0F, 26, 4, 256, 256);
         guiGraphics.blit(GuiHelper.GUI_CONTROLS, this.leftPos - 21, this.topPos + 4, 0.0F, 4.0F, 25, heightWithoutBottom - 4, 256, 256);
         guiGraphics.blit(GuiHelper.GUI_CONTROLS, this.leftPos - 21, this.topPos + heightWithoutBottom, 0.0F, 198.0F, 25, 6, 256, 256);
         boolean previousHasSwitch = false;

         for (int slot = 0; slot < this.numberOfUpgradeSlots; slot++) {
            if (((StorageContainerMenuBase)this.menu).canDisableUpgrade(slot)) {
               int y = this.topPos + 5 + slot * 16 + (previousHasSwitch ? 1 : 0);
               guiGraphics.blit(
                  GuiHelper.GUI_CONTROLS, this.leftPos - 21 - 4, y, 0.0F, 204 + (previousHasSwitch ? 1 : 0), 7, 18 - (previousHasSwitch ? 1 : 0), 256, 256
               );
               previousHasSwitch = true;
            } else {
               previousHasSwitch = false;
            }
         }
      }
   }

   public UpgradeSettingsTabControl getUpgradeSettingsControl() {
      return this.settingsTabControl;
   }

   @Nullable
   public Slot findSlot(double mouseX, double mouseY) {
      for (int i = 0; i < ((StorageContainerMenuBase)this.menu).upgradeSlots.size(); i++) {
         Slot slot = ((StorageContainerMenuBase)this.menu).upgradeSlots.get(i);
         if (this.isHovering(slot, mouseX, mouseY) && slot.isActive()) {
            return slot;
         }
      }

      if (this.inventoryScrollPanel != null) {
         Optional<Slot> result = this.inventoryScrollPanel.findSlot(mouseX, mouseY);
         if (result.isPresent()) {
            return result.get();
         }

         Slot slot = super.findSlot(mouseX, mouseY);
         return slot != null && !((StorageContainerMenuBase)this.menu).isStorageInventorySlot(slot.index) ? slot : null;
      } else {
         for (int i = 0; i < ((StorageContainerMenuBase)this.menu).realInventorySlots.size(); i++) {
            Slot slot = ((StorageContainerMenuBase)this.menu).realInventorySlots.get(i);
            if (this.isHovering(slot, mouseX, mouseY) && slot.isActive()) {
               return slot;
            }
         }

         return super.findSlot(mouseX, mouseY);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      for (UpgradeInventoryPartBase<?> inventoryPart : this.inventoryParts.values()) {
         if (inventoryPart.handleMouseReleased(mouseX, mouseY, button)) {
            return true;
         }
      }

      this.handleQuickMoveAll(mouseX, mouseY, button);
      return super.mouseReleased(mouseX, mouseY, button);
   }

   private void handleQuickMoveAll(double mouseX, double mouseY, int button) {
      Slot slot = this.findSlot(mouseX, mouseY);
      if (this.doubleclick
         && !((StorageContainerMenuBase)this.getMenu()).getCarried().isEmpty()
         && slot != null
         && button == 0
         && ((StorageContainerMenuBase)this.menu).canTakeItemForPickAll(ItemStack.EMPTY, slot)
         && hasShiftDown()
         && !this.lastQuickMoved.isEmpty()) {
         for (Slot slot2 : ((StorageContainerMenuBase)this.menu).realInventorySlots) {
            this.tryQuickMoveSlot(button, slot, slot2);
         }
      }
   }

   private void tryQuickMoveSlot(int button, Slot slot, Slot slot2) {
      if (slot2.mayPickup(this.minecraft.player) && slot2.hasItem() && slot2.isSameInventory(slot)) {
         ItemStack slotItem = slot2.getItem();
         if (ItemStack.isSameItemSameComponents(this.lastQuickMoved, slotItem)) {
            if (slotItem.getCount() > slotItem.getMaxStackSize()) {
               PacketDistributor.sendToServer(new TransferFullSlotPayload(slot2.index), new CustomPacketPayload[0]);
            } else {
               this.slotClicked(slot2, slot2.index, button, ClickType.QUICK_MOVE);
            }
         }
      }
   }

   protected void slotClicked(Slot slot, int slotNumber, int mouseButton, ClickType type) {
      if (type == ClickType.PICKUP_ALL && !((StorageContainerMenuBase)this.menu).getSlotUpgradeContainer(slot).map(c -> c.allowsPickupAll(slot)).orElse(true)) {
         type = ClickType.PICKUP;
      }

      this.handleInventoryMouseClick(slotNumber, mouseButton, type);
   }

   private void handleInventoryMouseClick(int slotNumber, int mouseButton, ClickType type) {
      StorageContainerMenuBase<?> menu = (StorageContainerMenuBase<?>)this.getMenu();
      List<ItemStack> realInventoryItems = new ArrayList<>(menu.realInventorySlots.size());
      menu.realInventorySlots.forEach(slot -> realInventoryItems.add(slot.getItem().copy()));
      List<ItemStack> upgradeItems = new ArrayList<>(menu.upgradeSlots.size());
      menu.upgradeSlots.forEach(slot -> upgradeItems.add(slot.getItem().copy()));
      menu.clicked(slotNumber, mouseButton, type, this.minecraft.player);
      Int2ObjectMap<ItemStack> changedSlotIndexes = new Int2ObjectOpenHashMap();
      int inventorySlotsToCheck = Math.min(realInventoryItems.size() - 36, menu.getInventorySlotsSize() - 36);

      for (int i = 0; i < inventorySlotsToCheck; i++) {
         ItemStack itemstack = realInventoryItems.get(i);
         ItemStack slotStack = menu.getSlot(i).getItem();
         if (!ItemStack.matches(itemstack, slotStack)) {
            changedSlotIndexes.put(i, slotStack.copy());
         }
      }

      for (int i = 0; i < 36; i++) {
         ItemStack itemstack = realInventoryItems.get(realInventoryItems.size() - 36 + i);
         int slotIndex = menu.getInventorySlotsSize() - 36 + i;
         ItemStack slotStack = menu.getSlot(slotIndex).getItem();
         if (!ItemStack.matches(itemstack, slotStack)) {
            changedSlotIndexes.put(slotIndex, slotStack.copy());
         }
      }

      int lastChecked = 0;

      int upgradeSlotsToCheck;
      for (upgradeSlotsToCheck = Math.min(menu.getUpgradeSlotsSize(), upgradeItems.size()); lastChecked < upgradeSlotsToCheck; lastChecked++) {
         ItemStack itemstack = upgradeItems.get(lastChecked);
         ItemStack slotStack = menu.getSlot(menu.getInventorySlotsSize() + lastChecked).getItem();
         if (!ItemStack.matches(itemstack, slotStack)) {
            break;
         }
      }

      for (int i = upgradeSlotsToCheck - 1; i >= lastChecked; i--) {
         ItemStack itemstack = upgradeItems.get(i);
         int slotIndex = menu.getInventorySlotsSize() + i;
         ItemStack slotStack = menu.getSlot(slotIndex).getItem();
         if (!ItemStack.matches(itemstack, slotStack)) {
            changedSlotIndexes.put(slotIndex, slotStack.copy());
         }
      }

      this.minecraft
         .player
         .connection
         .send(
            new ServerboundContainerClickPacket(
               menu.containerId, menu.getStateId(), slotNumber, mouseButton, type, menu.getCarried().copy(), changedSlotIndexes
            )
         );
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      Slot slot = this.findSlot(mouseX, mouseY);
      if (hasShiftDown() && hasControlDown() && slot instanceof StorageInventorySlot && button == 0) {
         PacketDistributor.sendToServer(new TransferFullSlotPayload(slot.index), new CustomPacketPayload[0]);
         return true;
      }

      GuiEventListener focused = this.getFocused();
      if (focused != null && !focused.isMouseOver(mouseX, mouseY) && focused instanceof WidgetBase widgetBase) {
         widgetBase.setFocused(false);
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      for (GuiEventListener child : this.children()) {
         if (child.isMouseOver(mouseX, mouseY) && child.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
            return true;
         }
      }

      Slot slot = this.findSlot(mouseX, mouseY);
      ItemStack itemstack = ((StorageContainerMenuBase)this.getMenu()).getCarried();
      if (!this.isQuickCrafting) {
         return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
      }

      if (slot != null
         && !itemstack.isEmpty()
         && (itemstack.getCount() > this.quickCraftSlots.size() || this.quickCraftingType == 2)
         && StorageContainerMenuBase.canItemQuickReplace(slot, itemstack)
         && slot.mayPlace(itemstack)
         && ((StorageContainerMenuBase)this.menu).canDragTo(slot)
         && this.isAllowedSlotCombination(slot, itemstack)) {
         this.quickCraftSlots.add(slot);
         this.recalculateQuickCraftRemaining();
      }

      return true;
   }

   private boolean isAllowedSlotCombination(Slot slot, ItemStack carried) {
      return !this.quickCraftSlots.isEmpty() && carried.getItem() instanceof UpgradeItemBase<?> upgradeItem && upgradeItem.getInventoryColumnsTaken() != 0
         ? this.quickCraftSlots.contains(slot)
            || !(this.quickCraftSlots.iterator().next() instanceof StorageContainerMenuBase.StorageUpgradeSlot)
               && !(slot instanceof StorageContainerMenuBase.StorageUpgradeSlot)
         : true;
   }

   protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
      return super.hasClickedOutside(mouseX, mouseY, guiLeftIn, guiTopIn, mouseButton)
         && this.hasClickedOutsideOfUpgradeSlots(mouseX, mouseY)
         && this.hasClickedOutsideOfUpgradeSettings(mouseX, mouseY);
   }

   private boolean hasClickedOutsideOfUpgradeSettings(double mouseX, double mouseY) {
      return this.settingsTabControl.getTabRectangles().stream().noneMatch(r -> r.contains((int)mouseX, (int)mouseY));
   }

   private boolean hasClickedOutsideOfUpgradeSlots(double mouseX, double mouseY) {
      return !this.getUpgradeSlotsRectangle().map(r -> r.contains((int)mouseX, (int)mouseY)).orElse(false);
   }

   public Optional<Rect2i> getUpgradeSlotsRectangle() {
      return this.numberOfUpgradeSlots == 0
         ? Optional.empty()
         : GuiHelper.getPositiveRectangle(this.leftPos - 21 - (!this.upgradeSwitches.isEmpty() ? 4 : 0), this.topPos, 25, this.getUpgradeHeight());
   }

   private void renderStackCount(GuiGraphics guiGraphics, String count, int x, int y) {
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(0.0, 0.0, 200.0);
      float scale = Math.min(1.0F, 16.0F / this.font.width(count));
      if (scale < 1.0F) {
         poseStack.scale(scale, scale, 1.0F);
      }

      guiGraphics.drawString(
         this.font, count, (x + 19 - 2 - this.font.width(count) * scale) / scale, (y + 6 + 3 + (1.0F / (scale * scale) - 1.0F)) / scale, 16777215, true
      );
      poseStack.popPose();
   }

   protected void recalculateQuickCraftRemaining() {
      ItemStack carriedStack = ((StorageContainerMenuBase)this.getMenu()).getCarried();
      if (!carriedStack.isEmpty() && this.isQuickCrafting) {
         if (this.quickCraftingType == 2) {
            this.quickCraftingRemainder = carriedStack.getMaxStackSize();
         } else {
            this.quickCraftingRemainder = carriedStack.getCount();

            for (Slot slot : this.quickCraftSlots) {
               ItemStack slotStack = slot.getItem();
               int slotStackCount = slotStack.isEmpty() ? 0 : slotStack.getCount();
               int maxStackSize = slot.getMaxStackSize(carriedStack);
               int quickCraftPlaceCount = Math.min(
                  StorageContainerMenuBase.getQuickCraftPlaceCount(slot, this.quickCraftSlots.size(), this.quickCraftingType, carriedStack) + slotStackCount,
                  maxStackSize
               );
               this.quickCraftingRemainder -= quickCraftPlaceCount - slotStackCount;
            }
         }
      }
   }

   private void renderErrorOverlay(GuiGraphics guiGraphics) {
      ((StorageContainerMenuBase)this.menu)
         .getErrorUpgradeSlotChangeResult()
         .ifPresent(upgradeSlotChangeResult -> upgradeSlotChangeResult.getErrorMessage().ifPresent(overlayErrorMessage -> {
            RenderSystem.disableDepthTest();
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(this.getGuiLeft(), this.getGuiTop(), 0.0F);
            upgradeSlotChangeResult.errorUpgradeSlots().forEach(slotIndex -> {
               Slot upgradeSlot = ((StorageContainerMenuBase)this.menu).getSlot(((StorageContainerMenuBase)this.menu).getFirstUpgradeSlot() + slotIndex);
               renderSlotHighlight(guiGraphics, upgradeSlot.x, upgradeSlot.y, 0, ERROR_SLOT_COLOR);
            });
            upgradeSlotChangeResult.errorInventorySlots().forEach(slotIndex -> {
               Slot slot = ((StorageContainerMenuBase)this.menu).getSlot(slotIndex);
               if (slot != null) {
                  renderSlotHighlight(guiGraphics, slot.x, slot.y, 0, ERROR_SLOT_COLOR);
               }
            });
            upgradeSlotChangeResult.errorInventoryParts().forEach(partIndex -> {
               UpgradeInventoryPartBase<?> inventoryPart = this.inventoryParts.get(partIndex);
               if (inventoryPart != null) {
                  inventoryPart.renderErrorOverlay(guiGraphics);
               }
            });
            poseStack.popPose();
            this.renderErrorMessage(guiGraphics, poseStack, overlayErrorMessage);
         }));
   }

   private void renderErrorMessage(GuiGraphics guiGraphics, PoseStack matrixStack, Component overlayErrorMessage) {
      matrixStack.pushPose();
      matrixStack.translate(this.width / 2.0F, (double)this.topPos + this.inventoryLabelY + 4.0, 300.0);
      Font fontrenderer = Minecraft.getInstance().font;
      int tooltipWidth = this.font.width(overlayErrorMessage);
      List<FormattedCharSequence> wrappedTextLines = new ArrayList<>();
      int maxLineWidth = 260;
      if (tooltipWidth > maxLineWidth) {
         int wrappedTooltipWidth = 0;

         for (FormattedCharSequence line : this.font.split(overlayErrorMessage, maxLineWidth)) {
            int lineWidth = this.font.width(line);
            if (lineWidth > wrappedTooltipWidth) {
               wrappedTooltipWidth = lineWidth;
            }

            wrappedTextLines.add(line);
         }

         tooltipWidth = wrappedTooltipWidth;
      } else {
         wrappedTextLines.add(overlayErrorMessage.getVisualOrderText());
      }

      int tooltipHeight = 8;
      if (wrappedTextLines.size() > 1) {
         tooltipHeight += 2 + (wrappedTextLines.size() - 1) * 10;
      }

      Matrix4f matrix4f = matrixStack.last().pose();
      float leftX = -tooltipWidth / 2.0F;
      GuiHelper.renderTooltipBackground(matrix4f, tooltipWidth, (int)leftX, 0, tooltipHeight, -267386864, ERROR_BORDER_COLOR, ERROR_BORDER_COLOR);
      BufferSource renderTypeBuffer = MultiBufferSource.immediate(new ByteBufferBuilder(1536));
      matrixStack.translate(0.0, 0.0, 400.0);
      GuiHelper.writeTooltipLines(guiGraphics, wrappedTextLines, fontrenderer, leftX, 0, matrix4f, renderTypeBuffer, ERROR_TEXT_COLOR);
      renderTypeBuffer.endBatch();
      matrixStack.popPose();
   }

   @Override
   public void renderInventorySlots(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean canShowHover) {
      this.renderStorageInventorySlots(guiGraphics, mouseX, mouseY, canShowHover);
   }

   @Override
   public boolean isMouseOverSlot(Slot slot, double mouseX, double mouseY) {
      return this.isHovering(slot, mouseX, mouseY);
   }

   protected boolean isHovering(Slot slot, double mouseX, double mouseY) {
      return super.isHovering(slot, mouseX, mouseY) && this.getUpgradeSettingsControl().slotIsNotCoveredAt(slot, mouseX, mouseY);
   }

   @Override
   public int getTopY() {
      return this.getGuiTop();
   }

   @Override
   public void drawSlotBg(GuiGraphics guiGraphics, int visibleSlotsCount) {
      this.drawSlotBg(guiGraphics, (this.width - this.imageWidth) / 2, (this.height - this.imageHeight) / 2, visibleSlotsCount);
      this.drawSlotOverlays(guiGraphics);
   }

   @Override
   public int getLeftX() {
      return this.getGuiLeft();
   }

   protected void containerTick() {
      super.containerTick();
      this.settingsTabControl.tick();
   }

   private class TransferButton extends Button {
      private final ButtonDefinition shiftDefinition;
      private final ButtonDefinition definition;

      public TransferButton(Consumer<Boolean> transferItems, ButtonDefinition shiftDefinition, ButtonDefinition definition) {
         super(new Position(StorageScreenBase.this.leftPos, StorageScreenBase.this.topPos), definition, button -> {
            if (button == 0) {
               transferItems.accept(!Screen.hasShiftDown());
            }
         });
         this.shiftDefinition = shiftDefinition;
         this.definition = definition;
      }

      @Override
      protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
         if (Screen.hasShiftDown()) {
            GuiHelper.blit(guiGraphics, this.x, this.y, this.shiftDefinition.getForegroundTexture());
         } else {
            GuiHelper.blit(guiGraphics, this.x, this.y, this.definition.getForegroundTexture());
         }
      }

      @Override
      protected List<Component> getTooltip() {
         return Screen.hasShiftDown() ? this.shiftDefinition.getTooltip() : this.definition.getTooltip();
      }
   }
}
