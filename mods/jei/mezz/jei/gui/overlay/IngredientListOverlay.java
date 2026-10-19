package mezz.jei.gui.overlay;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import mezz.jei.api.gui.handlers.IGuiProperties;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IIngredientListOverlay;
import mezz.jei.api.runtime.IScreenHelper;
import mezz.jei.common.config.HistoryDisplaySide;
import mezz.jei.common.config.IClientConfig;
import mezz.jei.common.config.IClientToggleState;
import mezz.jei.common.config.file.IConfigListener;
import mezz.jei.common.input.IInternalKeyMappings;
import mezz.jei.common.util.ImmutableRect2i;
import mezz.jei.gui.GuiProperties;
import mezz.jei.gui.elements.IconButton;
import mezz.jei.gui.filter.IFilterTextSource;
import mezz.jei.gui.input.GuiTextFieldFilter;
import mezz.jei.gui.input.ICharTypedHandler;
import mezz.jei.gui.input.IClickableIngredientInternal;
import mezz.jei.gui.input.IDragHandler;
import mezz.jei.gui.input.IDraggableIngredientInternal;
import mezz.jei.gui.input.IRecipeFocusSource;
import mezz.jei.gui.input.IUserInputHandler;
import mezz.jei.gui.input.MouseUtil;
import mezz.jei.gui.input.handlers.CombinedDragHandler;
import mezz.jei.gui.input.handlers.CombinedInputHandler;
import mezz.jei.gui.input.handlers.NullDragHandler;
import mezz.jei.gui.input.handlers.NullInputHandler;
import mezz.jei.gui.input.handlers.ProxyDragHandler;
import mezz.jei.gui.input.handlers.ProxyInputHandler;
import mezz.jei.gui.overlay.bookmarks.history.LookupHistoryOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class IngredientListOverlay implements IIngredientListOverlay, IRecipeFocusSource, ICharTypedHandler {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int BORDER_MARGIN = 6;
   private static final int INNER_PADDING = 2;
   private static final int BUTTON_SIZE = 20;
   private static final int SEARCH_HEIGHT = 20;
   private final IconButton configButton;
   private final IngredientGridWithNavigation contents;
   private final LookupHistoryOverlay lookupHistoryOverlay;
   private final IClientConfig clientConfig;
   private final IClientToggleState toggleState;
   private final GuiTextFieldFilter searchField;
   private final IInternalKeyMappings keyBindings;
   private final ScreenPropertiesCache screenPropertiesCache;
   private final IFilterTextSource filterTextSource;
   private final IConfigListener<Boolean> lookupHistoryEnabledListener;
   private final IConfigListener<HistoryDisplaySide> lookupHistoryViewSideListener;

   public IngredientListOverlay(
      IIngredientGridSource ingredientGridSource,
      IFilterTextSource filterTextSource,
      IScreenHelper screenHelper,
      IngredientGridWithNavigation contents,
      LookupHistoryOverlay lookupHistoryOverlay,
      IClientConfig clientConfig,
      IClientToggleState toggleState,
      IInternalKeyMappings keyBindings
   ) {
      this.screenPropertiesCache = new ScreenPropertiesCache(screenHelper);
      this.contents = contents;
      this.lookupHistoryOverlay = lookupHistoryOverlay;
      this.clientConfig = clientConfig;
      this.toggleState = toggleState;
      this.searchField = new GuiTextFieldFilter(contents::isEmpty);
      this.keyBindings = keyBindings;
      this.filterTextSource = filterTextSource;
      this.searchField.setValue(filterTextSource.getFilterText());
      this.searchField.setFocused(false);
      this.searchField.setResponder(filterTextSource::setFilterText);
      filterTextSource.addListener(this.searchField::setValue);
      ingredientGridSource.addSourceListChangedListener(() -> {
         Minecraft minecraft = Minecraft.getInstance();
         this.getScreenPropertiesUpdater().updateScreen(minecraft.screen).update();
      });
      this.configButton = new IconButton(new ConfigButtonController(this::isListDisplayed, toggleState, keyBindings));
      this.lookupHistoryEnabledListener = v -> this.onScreenPropertiesChanged();
      this.lookupHistoryViewSideListener = v -> this.onScreenPropertiesChanged();
      clientConfig.addLookupHistoryEnabledListener(this.lookupHistoryEnabledListener);
      clientConfig.addLookupHistoryDisplaySideListener(this.lookupHistoryViewSideListener);
   }

   @Override
   public boolean isListDisplayed() {
      return (this.toggleState.isOverlayEnabled() || this.keyBindings.getToggleOverlay().isUnbound())
         && this.screenPropertiesCache.hasValidScreen()
         && this.contents.hasRoom();
   }

   private static ImmutableRect2i createDisplayArea(IGuiProperties guiProperties) {
      ImmutableRect2i screenRectangle = GuiProperties.getScreenRectangle(guiProperties);
      int guiRight = GuiProperties.getGuiRight(guiProperties);
      return screenRectangle.cropLeft(guiRight);
   }

   public ScreenPropertiesCache.Updater getScreenPropertiesUpdater() {
      return this.screenPropertiesCache.getUpdater(this::onScreenPropertiesChanged);
   }

   private void onScreenPropertiesChanged() {
      this.screenPropertiesCache.getGuiProperties().ifPresentOrElse(guiProperties -> {
         try {
            ImmutableRect2i displayArea = createDisplayArea(guiProperties);
            Set<ImmutableRect2i> guiExclusionAreas = this.screenPropertiesCache.getGuiExclusionAreas();
            this.updateBounds(guiProperties, displayArea, guiExclusionAreas);
         } catch (RuntimeException e) {
            LOGGER.error("Failed to update JEI bounds for screen with properties : {}", guiProperties, e);
            this.contents.close();
            this.lookupHistoryOverlay.close();
            this.searchField.setFocused(false);
         }
      }, () -> {
         this.contents.close();
         this.searchField.setFocused(false);
      });
   }

   private void updateBounds(IGuiProperties guiProperties, ImmutableRect2i displayArea, Set<ImmutableRect2i> guiExclusionAreas) {
      boolean searchBarCentered = isSearchBarCentered(this.clientConfig, guiProperties);
      ImmutableRect2i availableContentsArea = this.getAvailableContentsArea(displayArea, searchBarCentered);
      if (this.clientConfig.isLookupHistoryEnabled() && this.lookupHistoryOverlay.isOnSide()) {
         int historyRows = this.clientConfig.getMaxLookupHistoryRows();
         availableContentsArea = availableContentsArea.cropBottom(historyRows * LookupHistoryOverlay.SLOT_HEIGHT);
         ImmutableRect2i historyArea = displayArea.insetBy(6).moveUp(22).keepBottom(historyRows * LookupHistoryOverlay.SLOT_HEIGHT);
         this.lookupHistoryOverlay.updateBounds(historyArea, guiExclusionAreas, null);
         this.lookupHistoryOverlay.updateLayout();
      }

      int legacySize = this.contents.size();
      this.contents.updateBounds(availableContentsArea, guiExclusionAreas, null);
      boolean resetToFirstPage = legacySize != this.contents.size();
      this.contents.updateLayout(resetToFirstPage);
      ImmutableRect2i searchAndConfigArea = this.getSearchAndConfigArea(displayArea, searchBarCentered, guiProperties);
      ImmutableRect2i searchArea = searchAndConfigArea.cropRight(20);
      ImmutableRect2i configButtonArea = searchAndConfigArea.keepRight(20);
      this.searchField.setValue(this.filterTextSource.getFilterText());
      this.searchField.updateBounds(searchArea);
      this.configButton.updateBounds(configButtonArea);
   }

   private static boolean isSearchBarCentered(IClientConfig clientConfig, IGuiProperties guiProperties) {
      return clientConfig.isCenterSearchBarEnabled() && GuiProperties.getGuiBottom(guiProperties) + 20 < guiProperties.screenHeight();
   }

   private ImmutableRect2i getAvailableContentsArea(ImmutableRect2i displayArea, boolean searchBarCentered) {
      return searchBarCentered ? displayArea : displayArea.cropBottom(22);
   }

   private ImmutableRect2i getSearchAndConfigArea(ImmutableRect2i displayArea, boolean searchBarCentered, IGuiProperties guiProperties) {
      displayArea = displayArea.insetBy(6);
      if (searchBarCentered) {
         ImmutableRect2i guiRectangle = GuiProperties.getGuiRectangle(guiProperties);
         return displayArea.keepBottom(20).matchWidthAndX(guiRectangle);
      } else if (this.contents.hasRoom()) {
         ImmutableRect2i contentsArea = this.contents.getBackgroundArea();
         return displayArea.keepBottom(20).matchWidthAndX(contentsArea);
      } else {
         return displayArea.keepBottom(20);
      }
   }

   public void drawScreen(Minecraft minecraft, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      if (this.isListDisplayed()) {
         this.searchField.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
         this.contents.draw(minecraft, guiGraphics, mouseX, mouseY, partialTicks);
      }

      if (this.screenPropertiesCache.hasValidScreen()) {
         this.configButton.draw(guiGraphics, mouseX, mouseY, partialTicks);
      }

      if (this.screenPropertiesCache.hasValidScreen() && this.toggleState.isOverlayEnabled()) {
         this.lookupHistoryOverlay.draw(minecraft, guiGraphics, mouseX, mouseY, partialTicks);
      }
   }

   public void drawTooltips(Minecraft minecraft, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      if (this.isListDisplayed()) {
         this.contents.drawTooltips(minecraft, guiGraphics, mouseX, mouseY);
      }

      if (this.screenPropertiesCache.hasValidScreen()) {
         this.configButton.drawTooltips(guiGraphics, mouseX, mouseY);
      }

      if (this.screenPropertiesCache.hasValidScreen() && this.toggleState.isOverlayEnabled()) {
         this.lookupHistoryOverlay.drawTooltips(minecraft, guiGraphics, mouseX, mouseY);
      }
   }

   public void drawOnForeground(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      if (this.isListDisplayed()) {
         this.contents.drawOnForeground(guiGraphics, mouseX, mouseY);
      }

      this.lookupHistoryOverlay.drawOnForeground(guiGraphics, mouseX, mouseY);
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

   public IUserInputHandler createInputHandler() {
      IUserInputHandler displayedInputHandler = new CombinedInputHandler(
         "IngredientListOverlay", this.searchField.createInputHandler(), this.configButton.createInputHandler(), this.contents.createInputHandler()
      );
      IUserInputHandler configButtonInputHandler = this.configButton.createInputHandler();
      return new ProxyInputHandler(() -> {
         if (this.isListDisplayed()) {
            return displayedInputHandler;
         } else {
            return this.screenPropertiesCache.hasValidScreen() ? configButtonInputHandler : NullInputHandler.INSTANCE;
         }
      });
   }

   public IDragHandler createDragHandler() {
      IDragHandler combinedDragHandlers = new CombinedDragHandler(this.contents.createDragHandler(), this.lookupHistoryOverlay.createDragHandler());
      return new ProxyDragHandler(() -> this.isListDisplayed() ? combinedDragHandlers : NullDragHandler.INSTANCE);
   }

   @Override
   public boolean hasKeyboardFocus() {
      return this.isListDisplayed() && this.searchField.isFocused();
   }

   @Override
   public boolean onCharTyped(char codePoint, int modifiers) {
      return this.searchField.charTyped(codePoint, modifiers);
   }

   @Override
   public Optional<ITypedIngredient<?>> getIngredientUnderMouse() {
      if (this.isListDisplayed()) {
         double mouseX = MouseUtil.getX();
         double mouseY = MouseUtil.getY();
         return this.contents.getIngredientUnderMouse(mouseX, mouseY).map(IClickableIngredientInternal::getTypedIngredient).findFirst();
      } else {
         return Optional.empty();
      }
   }

   @Nullable
   @Override
   public <T> T getIngredientUnderMouse(IIngredientType<T> ingredientType) {
      if (this.isListDisplayed()) {
         double mouseX = MouseUtil.getX();
         double mouseY = MouseUtil.getY();
         return this.contents
            .getIngredientUnderMouse(mouseX, mouseY)
            .map(IClickableIngredientInternal::getTypedIngredient)
            .map(i -> i.getIngredient(ingredientType))
            .flatMap(Optional::stream)
            .findFirst()
            .orElse(null);
      } else {
         return null;
      }
   }

   @Override
   public <T> List<T> getVisibleIngredients(IIngredientType<T> ingredientType) {
      return this.isListDisplayed() ? this.contents.getVisibleIngredients(ingredientType).toList() : Collections.emptyList();
   }
}
