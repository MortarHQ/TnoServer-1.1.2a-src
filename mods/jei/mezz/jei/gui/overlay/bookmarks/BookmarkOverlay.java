package mezz.jei.gui.overlay.bookmarks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import mezz.jei.api.gui.handlers.IGuiProperties;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IBookmarkOverlay;
import mezz.jei.api.runtime.IScreenHelper;
import mezz.jei.common.config.HistoryDisplaySide;
import mezz.jei.common.config.IClientConfig;
import mezz.jei.common.config.IClientToggleState;
import mezz.jei.common.config.file.IConfigListener;
import mezz.jei.common.input.IInternalKeyMappings;
import mezz.jei.common.util.ImmutablePoint2i;
import mezz.jei.common.util.ImmutableRect2i;
import mezz.jei.gui.bookmarks.BookmarkList;
import mezz.jei.gui.bookmarks.IBookmark;
import mezz.jei.gui.elements.IconButton;
import mezz.jei.gui.input.IClickableIngredientInternal;
import mezz.jei.gui.input.IDragHandler;
import mezz.jei.gui.input.IDraggableIngredientInternal;
import mezz.jei.gui.input.IPaged;
import mezz.jei.gui.input.IRecipeFocusSource;
import mezz.jei.gui.input.IUserInputHandler;
import mezz.jei.gui.input.MouseUtil;
import mezz.jei.gui.input.handlers.CombinedDragHandler;
import mezz.jei.gui.input.handlers.CombinedInputHandler;
import mezz.jei.gui.input.handlers.NullDragHandler;
import mezz.jei.gui.input.handlers.ProxyDragHandler;
import mezz.jei.gui.input.handlers.ProxyInputHandler;
import mezz.jei.gui.overlay.IngredientGridWithNavigation;
import mezz.jei.gui.overlay.IngredientListSlot;
import mezz.jei.gui.overlay.ScreenPropertiesCache;
import mezz.jei.gui.overlay.bookmarks.history.LookupHistoryButtonController;
import mezz.jei.gui.overlay.bookmarks.history.LookupHistoryOverlay;
import mezz.jei.gui.overlay.elements.IElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.Nullable;

public class BookmarkOverlay implements IRecipeFocusSource, IBookmarkOverlay {
   private static final int BORDER_MARGIN = 6;
   private static final int INNER_PADDING = 2;
   private static final int BUTTON_SIZE = 20;
   private final BookmarkDragManager bookmarkDragManager;
   private final ScreenPropertiesCache screenPropertiesCache;
   private final IngredientGridWithNavigation contents;
   private final LookupHistoryOverlay lookupHistoryOverlay;
   private final IconButton bookmarkButton;
   private final IconButton historyButton;
   private final BookmarkList bookmarkList;
   private final IClientToggleState toggleState;
   private final IClientConfig clientConfig;
   private final IConfigListener<Boolean> lookupHistoryEnabledListener;
   private final IConfigListener<HistoryDisplaySide> lookupHistoryViewSideListener;

   public BookmarkOverlay(
      BookmarkList bookmarkList,
      IngredientGridWithNavigation contents,
      LookupHistoryOverlay lookupHistoryOverlay,
      IClientToggleState toggleState,
      IClientConfig clientConfig,
      IScreenHelper screenHelper,
      IInternalKeyMappings keyBindings
   ) {
      this.bookmarkList = bookmarkList;
      this.toggleState = toggleState;
      this.clientConfig = clientConfig;
      this.bookmarkButton = new IconButton(new BookmarkButtonController(this, bookmarkList, toggleState, keyBindings));
      this.historyButton = new IconButton(new LookupHistoryButtonController(clientConfig));
      this.contents = contents;
      this.lookupHistoryOverlay = lookupHistoryOverlay;
      this.screenPropertiesCache = new ScreenPropertiesCache(screenHelper);
      this.bookmarkDragManager = new BookmarkDragManager(this);
      bookmarkList.addSourceListChangedListener(() -> {
         toggleState.setBookmarkEnabled(!bookmarkList.isEmpty());
         Minecraft minecraft = Minecraft.getInstance();
         this.getScreenPropertiesUpdater().updateScreen(minecraft.screen).update();
      });
      lookupHistoryOverlay.getLookupHistory().addSourceListChangedListener(() -> {
         Minecraft minecraft = Minecraft.getInstance();
         this.getScreenPropertiesUpdater().updateScreen(minecraft.screen).update();
      });
      this.lookupHistoryEnabledListener = v -> this.onScreenPropertiesChanged();
      this.lookupHistoryViewSideListener = v -> this.onScreenPropertiesChanged();
      clientConfig.addLookupHistoryEnabledListener(this.lookupHistoryEnabledListener);
      clientConfig.addLookupHistoryDisplaySideListener(this.lookupHistoryViewSideListener);
   }

   public boolean isListDisplayed() {
      return this.toggleState.isBookmarkOverlayEnabled()
         && this.screenPropertiesCache.hasValidScreen()
         && this.contents.hasRoom()
         && !this.bookmarkList.isEmpty();
   }

   public boolean hasRoom() {
      return this.contents.hasRoom();
   }

   public ScreenPropertiesCache.Updater getScreenPropertiesUpdater() {
      return this.screenPropertiesCache.getUpdater(this::onScreenPropertiesChanged);
   }

   private void onScreenPropertiesChanged() {
      this.screenPropertiesCache.getGuiProperties().ifPresentOrElse(this::updateBounds, () -> {
         this.contents.close();
         this.lookupHistoryOverlay.close();
      });
   }

   private void updateBounds(IGuiProperties guiProperties) {
      ImmutableRect2i displayArea = getDisplayArea(guiProperties);
      Set<ImmutableRect2i> guiExclusionAreas = this.screenPropertiesCache.getGuiExclusionAreas();
      ImmutablePoint2i mouseExclusionArea = this.screenPropertiesCache.getMouseExclusionArea();
      ImmutableRect2i availableContentsArea = displayArea.cropBottom(22);
      if (this.clientConfig.isLookupHistoryEnabled() && this.lookupHistoryOverlay.isOnSide()) {
         int historyRows = this.clientConfig.getMaxLookupHistoryRows();
         availableContentsArea = availableContentsArea.cropBottom(historyRows * LookupHistoryOverlay.SLOT_HEIGHT);
         ImmutableRect2i historyArea = displayArea.insetBy(6).moveUp(22).keepBottom(historyRows * LookupHistoryOverlay.SLOT_HEIGHT);
         this.lookupHistoryOverlay.updateBounds(historyArea, guiExclusionAreas, mouseExclusionArea);
         this.lookupHistoryOverlay.updateLayout();
      }

      int legacySize = this.contents.size();
      this.contents.updateBounds(availableContentsArea, guiExclusionAreas, mouseExclusionArea);
      boolean resetToFirstPage = legacySize != this.contents.size();
      this.contents.updateLayout(resetToFirstPage);
      if (this.contents.hasRoom()) {
         ImmutableRect2i contentsArea = this.contents.getBackgroundArea();
         ImmutableRect2i bookmarkButtonArea = displayArea.insetBy(6).matchWidthAndX(contentsArea).keepBottom(20).keepLeft(20);
         this.bookmarkButton.updateBounds(bookmarkButtonArea);
         ImmutableRect2i historyButtonArea = bookmarkButtonArea.moveRight(22);
         this.historyButton.updateBounds(historyButtonArea);
      } else {
         ImmutableRect2i bookmarkButtonArea = displayArea.insetBy(6).keepBottom(20).keepLeft(20);
         this.bookmarkButton.updateBounds(bookmarkButtonArea);
         ImmutableRect2i historyButtonArea = bookmarkButtonArea.moveRight(22);
         this.historyButton.updateBounds(historyButtonArea);
      }
   }

   private static ImmutableRect2i getDisplayArea(IGuiProperties guiProperties) {
      int width = guiProperties.guiLeft();
      if (width <= 0) {
         width = 0;
      }

      int screenHeight = guiProperties.screenHeight();
      return new ImmutableRect2i(0, 0, width, screenHeight);
   }

   public void drawScreen(Minecraft minecraft, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      if (this.isListDisplayed()) {
         this.bookmarkDragManager.updateDrag(mouseX, mouseY);
         this.contents.draw(minecraft, guiGraphics, mouseX, mouseY, partialTicks);
      }

      if (this.screenPropertiesCache.hasValidScreen() && this.toggleState.isOverlayEnabled()) {
         this.lookupHistoryOverlay.draw(minecraft, guiGraphics, mouseX, mouseY, partialTicks);
      }

      if (this.screenPropertiesCache.hasValidScreen()) {
         this.bookmarkButton.draw(guiGraphics, mouseX, mouseY, partialTicks);
         this.historyButton.draw(guiGraphics, mouseX, mouseY, partialTicks);
      }
   }

   public void drawTooltips(Minecraft minecraft, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      if (!this.bookmarkDragManager.drawDraggedItem(guiGraphics, mouseX, mouseY)) {
         if (this.isListDisplayed()) {
            this.contents.drawTooltips(minecraft, guiGraphics, mouseX, mouseY);
         }

         if (this.screenPropertiesCache.hasValidScreen() && this.toggleState.isOverlayEnabled()) {
            this.lookupHistoryOverlay.drawTooltips(minecraft, guiGraphics, mouseX, mouseY);
         }
      }

      if (this.screenPropertiesCache.hasValidScreen()) {
         this.bookmarkButton.drawTooltips(guiGraphics, mouseX, mouseY);
         this.historyButton.drawTooltips(guiGraphics, mouseX, mouseY);
      }
   }

   @Override
   public Stream<IClickableIngredientInternal<?>> getIngredientUnderMouse(double mouseX, double mouseY) {
      if (this.isListDisplayed()) {
         return Stream.concat(this.contents.getIngredientUnderMouse(mouseX, mouseY), this.lookupHistoryOverlay.getIngredientUnderMouse(mouseX, mouseY));
      } else {
         return this.lookupHistoryOverlay.isListDisplayed() ? this.lookupHistoryOverlay.getIngredientUnderMouse(mouseX, mouseY) : Stream.empty();
      }
   }

   @Override
   public Stream<IDraggableIngredientInternal<?>> getDraggableIngredientUnderMouse(double mouseX, double mouseY) {
      if (this.isListDisplayed()) {
         return Stream.concat(
            this.contents.getDraggableIngredientUnderMouse(mouseX, mouseY), this.lookupHistoryOverlay.getDraggableIngredientUnderMouse(mouseX, mouseY)
         );
      } else {
         return this.lookupHistoryOverlay.isListDisplayed() ? this.lookupHistoryOverlay.getDraggableIngredientUnderMouse(mouseX, mouseY) : Stream.empty();
      }
   }

   @Override
   public Optional<ITypedIngredient<?>> getIngredientUnderMouse() {
      double mouseX = MouseUtil.getX();
      double mouseY = MouseUtil.getY();
      return this.getIngredientUnderMouse(mouseX, mouseY).map(IClickableIngredientInternal::getTypedIngredient).findFirst();
   }

   @Nullable
   @Override
   public <T> T getIngredientUnderMouse(IIngredientType<T> ingredientType) {
      double mouseX = MouseUtil.getX();
      double mouseY = MouseUtil.getY();
      return this.getIngredientUnderMouse(mouseX, mouseY)
         .map(IClickableIngredientInternal::getTypedIngredient)
         .map(i -> i.getIngredient(ingredientType))
         .flatMap(Optional::stream)
         .findFirst()
         .orElse(null);
   }

   public IUserInputHandler createInputHandler() {
      IUserInputHandler bookmarkButtonInputHandler = this.bookmarkButton.createInputHandler();
      IUserInputHandler historyButtonInputHandler = this.historyButton.createInputHandler();
      IUserInputHandler buttonInputHandler = new CombinedInputHandler("BookmarkOverlayButton", bookmarkButtonInputHandler, historyButtonInputHandler);
      IUserInputHandler displayedInputHandler = new CombinedInputHandler("BookmarkOverlay", this.contents.createInputHandler(), buttonInputHandler);
      return new ProxyInputHandler(() -> this.isListDisplayed() ? displayedInputHandler : buttonInputHandler);
   }

   public IDragHandler createDragHandler() {
      IDragHandler lookupHistoryDragHandler = this.lookupHistoryOverlay.createDragHandler();
      IDragHandler combinedDragHandlers = new CombinedDragHandler(
         this.contents.createDragHandler(), lookupHistoryDragHandler, this.bookmarkDragManager.createDragHandler()
      );
      return new ProxyDragHandler(() -> {
         if (this.isListDisplayed()) {
            return combinedDragHandlers;
         } else {
            return this.lookupHistoryOverlay.isListDisplayed() ? lookupHistoryDragHandler : NullDragHandler.INSTANCE;
         }
      });
   }

   public void drawOnForeground(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      if (this.isListDisplayed()) {
         this.contents.drawOnForeground(guiGraphics, mouseX, mouseY);
      }

      this.lookupHistoryOverlay.drawOnForeground(guiGraphics, mouseX, mouseY);
   }

   public List<IBookmarkDragTarget> createBookmarkDragTargets() {
      List<BookmarkOverlay.DragTarget> slotTargets = this.contents
         .getSlots()
         .map(this::createDragTarget)
         .filter(Optional::isPresent)
         .map(Optional::get)
         .toList();
      IBookmark firstBookmark = slotTargets.getFirst().bookmark;
      IBookmark lastBookmark = slotTargets.getLast().bookmark;
      List<IBookmarkDragTarget> bookmarkDragTargets = new ArrayList<>(slotTargets);
      IPaged pageDelegate = this.contents.getPageDelegate();
      if (pageDelegate.getPageCount() > 1) {
         bookmarkDragTargets.add(
            new BookmarkOverlay.ActionDragTarget(this.contents.getNextPageButtonArea(), lastBookmark, this.bookmarkList, 1, pageDelegate::nextPage)
         );
         bookmarkDragTargets.add(
            new BookmarkOverlay.ActionDragTarget(this.contents.getBackButtonArea(), firstBookmark, this.bookmarkList, -1, pageDelegate::previousPage)
         );
      }

      bookmarkDragTargets.add(new BookmarkOverlay.DragTarget(this.contents.getSlotBackgroundArea(), lastBookmark, this.bookmarkList, 0));
      return bookmarkDragTargets;
   }

   private Optional<BookmarkOverlay.DragTarget> createDragTarget(IngredientListSlot ingredientListSlot) {
      return ingredientListSlot.getOptionalElement()
         .flatMap(IElement::getBookmark)
         .map(bookmark -> new BookmarkOverlay.DragTarget(ingredientListSlot.getArea(), bookmark, this.bookmarkList, 0));
   }

   public boolean isMouseOver(double mouseX, double mouseY) {
      return this.contents.isMouseOver(mouseX, mouseY);
   }

   public static class ActionDragTarget extends BookmarkOverlay.DragTarget {
      private final Runnable action;

      public ActionDragTarget(ImmutableRect2i area, IBookmark bookmark, BookmarkList bookmarkList, int offset, Runnable action) {
         super(area, bookmark, bookmarkList, offset);
         this.action = action;
      }

      @Override
      public void accept(IBookmark bookmark) {
         super.accept(bookmark);
         this.action.run();
      }
   }

   public static class DragTarget implements IBookmarkDragTarget {
      private final ImmutableRect2i area;
      private final IBookmark bookmark;
      private final BookmarkList bookmarkList;
      private final int offset;

      public DragTarget(ImmutableRect2i area, IBookmark bookmark, BookmarkList bookmarkList, int offset) {
         this.area = area;
         this.bookmark = bookmark;
         this.bookmarkList = bookmarkList;
         this.offset = offset;
      }

      @Override
      public ImmutableRect2i getArea() {
         return this.area;
      }

      @Override
      public void accept(IBookmark bookmark) {
         this.bookmarkList.moveBookmark(this.bookmark, bookmark, this.offset);
      }
   }
}
