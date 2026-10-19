package mezz.jei.gui.overlay;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IScreenHelper;
import mezz.jei.common.config.IClientConfig;
import mezz.jei.common.config.IClientToggleState;
import mezz.jei.common.config.IIngredientGridConfig;
import mezz.jei.common.gui.elements.DrawableNineSliceTexture;
import mezz.jei.common.input.IInternalKeyMappings;
import mezz.jei.common.network.IConnectionToServer;
import mezz.jei.common.util.ImmutablePoint2i;
import mezz.jei.common.util.ImmutableRect2i;
import mezz.jei.common.util.MathUtil;
import mezz.jei.gui.PageNavigation;
import mezz.jei.gui.ghost.GhostIngredientDragManager;
import mezz.jei.gui.input.IClickableIngredientInternal;
import mezz.jei.gui.input.IDragHandler;
import mezz.jei.gui.input.IDraggableIngredientInternal;
import mezz.jei.gui.input.IMouseOverable;
import mezz.jei.gui.input.IPaged;
import mezz.jei.gui.input.IRecipeFocusSource;
import mezz.jei.gui.input.IUserInputHandler;
import mezz.jei.gui.input.UserInput;
import mezz.jei.gui.input.handlers.CombinedInputHandler;
import mezz.jei.gui.input.handlers.SameElementInputHandler;
import mezz.jei.gui.overlay.elements.IElement;
import mezz.jei.gui.recipes.RecipesGui;
import mezz.jei.gui.util.CommandUtil;
import mezz.jei.gui.util.MaximalRectangle;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class IngredientGridWithNavigation implements IRecipeFocusSource {
   private static final int NAVIGATION_HEIGHT = 20;
   private static final int BORDER_MARGIN = 6;
   private static final int BORDER_PADDING = 5;
   private static final int INNER_PADDING = 2;
   private int firstItemIndex = 0;
   private final IngredientGridWithNavigation.IngredientGridPaged pageDelegate;
   private final PageNavigation navigation;
   private final IIngredientGridConfig gridConfig;
   private final IIngredientManager ingredientManager;
   private final String debugName;
   private final IClientToggleState toggleState;
   private final IClientConfig clientConfig;
   private final IngredientGrid ingredientGrid;
   private final IIngredientGridSource ingredientSource;
   private final DrawableNineSliceTexture background;
   private final DrawableNineSliceTexture slotBackground;
   private final CommandUtil commandUtil;
   private final GhostIngredientDragManager ghostIngredientDragManager;
   private ImmutableRect2i backgroundArea = ImmutableRect2i.EMPTY;
   private ImmutableRect2i slotBackgroundArea = ImmutableRect2i.EMPTY;
   private Set<ImmutableRect2i> guiExclusionAreas = Set.of();
   private boolean active;

   public IngredientGridWithNavigation(
      String debugName,
      IIngredientGridSource ingredientSource,
      IngredientGrid ingredientGrid,
      IClientToggleState toggleState,
      IClientConfig clientConfig,
      IConnectionToServer serverConnection,
      IIngredientGridConfig gridConfig,
      DrawableNineSliceTexture background,
      DrawableNineSliceTexture slotBackground,
      IScreenHelper screenHelper,
      IIngredientManager ingredientManager
   ) {
      this.debugName = debugName;
      this.toggleState = toggleState;
      this.clientConfig = clientConfig;
      this.ingredientGrid = ingredientGrid;
      this.ingredientSource = ingredientSource;
      this.gridConfig = gridConfig;
      this.ingredientManager = ingredientManager;
      this.pageDelegate = new IngredientGridWithNavigation.IngredientGridPaged();
      this.navigation = new PageNavigation(this.pageDelegate, false);
      this.background = background;
      this.slotBackground = slotBackground;
      this.commandUtil = new CommandUtil(clientConfig, serverConnection);
      this.ghostIngredientDragManager = new GhostIngredientDragManager(this.ingredientGrid, screenHelper, ingredientManager, toggleState);
      this.ingredientSource.addSourceListChangedListener(() -> {
         if (this.isActive()) {
            boolean resetToFirstPage = clientConfig.isAddingBookmarksToFrontEnabled();
            this.updateLayout(resetToFirstPage);
         }
      });
   }

   private boolean isActive() {
      return this.active;
   }

   public boolean hasRoom() {
      return this.ingredientGrid.hasRoom();
   }

   public void updateLayout(boolean resetToFirstPage) {
      if (resetToFirstPage) {
         this.firstItemIndex = 0;
      }

      List<IElement<?>> ingredientList = this.ingredientSource.getElements();
      if (this.firstItemIndex >= ingredientList.size()) {
         this.firstItemIndex = 0;
      }

      this.ingredientGrid.set(this.firstItemIndex, ingredientList);
      this.navigation.updatePageNumber();
   }

   private static ImmutableRect2i avoidExclusionAreas(
      ImmutableRect2i availableArea, ImmutableRect2i estimatedNavigationArea, Set<ImmutableRect2i> guiExclusionAreas, IIngredientGridConfig gridConfig
   ) {
      int maxDimension = Math.max(availableArea.getWidth(), availableArea.getHeight());
      int samplingScale = Math.max(IngredientGrid.INGREDIENT_HEIGHT / 2, maxDimension / 25);
      ImmutableRect2i largestSafeArea = MaximalRectangle.getLargestRectangles(availableArea, guiExclusionAreas, samplingScale)
         .max(
            Comparator.<ImmutableRect2i>comparingInt(rect -> IngredientGrid.calculateSize(gridConfig, rect).getArea())
               .thenComparing(r -> r.getWidth() * r.getHeight())
         )
         .orElse(ImmutableRect2i.EMPTY);
      boolean intersectsNavigationArea = guiExclusionAreas.stream().anyMatch(estimatedNavigationArea::intersects);
      if (intersectsNavigationArea) {
         return largestSafeArea;
      }

      IngredientGrid.SlotInfo slotInfo = IngredientGrid.calculateBlockedSlotPercentage(gridConfig, availableArea, guiExclusionAreas);
      IngredientGrid.SlotInfo safeSlotInfo = IngredientGrid.calculateBlockedSlotPercentage(gridConfig, largestSafeArea, guiExclusionAreas);
      return !(slotInfo.percentBlocked() > 0.25) && safeSlotInfo.total() <= slotInfo.total() ? availableArea : largestSafeArea;
   }

   private void updateGridBounds(ImmutableRect2i availableArea, @Nullable ImmutablePoint2i mouseExclusionPoint, boolean navigationEnabled) {
      ImmutableRect2i availableGridArea = availableArea.insetBy(6);
      if (this.gridConfig.drawBackground()) {
         availableGridArea = availableGridArea.insetBy(7);
      }

      ImmutableRect2i estimatedGridArea = IngredientGrid.calculateBounds(this.gridConfig, availableGridArea);
      if (!estimatedGridArea.isEmpty()) {
         ImmutableRect2i slotBackgroundArea = calculateSlotBackgroundArea(estimatedGridArea, this.gridConfig);
         ImmutableRect2i estimatedNavigationArea = calculateNavigationArea(slotBackgroundArea, navigationEnabled);
         if (this.gridConfig.drawBackground()) {
            estimatedNavigationArea.expandBy(7);
         }

         availableGridArea = avoidExclusionAreas(availableArea, estimatedNavigationArea, this.guiExclusionAreas, this.gridConfig).insetBy(6).cropTop(22);
         if (this.gridConfig.drawBackground()) {
            availableGridArea = availableGridArea.insetBy(7);
         }
      }

      this.ingredientGrid.updateBounds(availableGridArea, this.guiExclusionAreas, mouseExclusionPoint);
   }

   public void updateBounds(ImmutableRect2i availableArea, Set<ImmutableRect2i> guiExclusionAreas, @Nullable ImmutablePoint2i mouseExclusionPoint) {
      this.active = true;
      this.guiExclusionAreas = guiExclusionAreas;

      boolean navigationEnabled = switch (this.gridConfig.getButtonNavigationVisibility()) {
         case ENABLED -> true;
         case DISABLED -> false;
         case AUTO_HIDE -> {
            this.updateGridBounds(availableArea, mouseExclusionPoint, false);
            yield this.hasRoom() && this.pageDelegate.getPageCount() > 1;
         }
      };
      if (navigationEnabled) {
         this.updateGridBounds(availableArea, mouseExclusionPoint, true);
      }

      if (!this.hasRoom()) {
         this.active = false;
      } else {
         this.slotBackgroundArea = calculateSlotBackgroundArea(this.ingredientGrid.getArea(), this.gridConfig);
         ImmutableRect2i navigationArea = calculateNavigationArea(this.slotBackgroundArea, navigationEnabled);
         this.navigation.updateBounds(navigationArea);
         this.backgroundArea = MathUtil.union(this.slotBackgroundArea, navigationArea);
         if (this.gridConfig.drawBackground()) {
            this.backgroundArea = this.backgroundArea.expandBy(5);
         }
      }
   }

   private static ImmutableRect2i calculateSlotBackgroundArea(ImmutableRect2i ingredientGridArea, IIngredientGridConfig gridConfig) {
      return gridConfig.drawBackground() ? ingredientGridArea.expandBy(2) : ingredientGridArea;
   }

   private static ImmutableRect2i calculateNavigationArea(ImmutableRect2i slotBackgroundArea, boolean navigationEnabled) {
      return !navigationEnabled ? ImmutableRect2i.EMPTY : slotBackgroundArea.keepTop(20).moveUp(22);
   }

   public ImmutableRect2i getBackgroundArea() {
      return this.backgroundArea;
   }

   public ImmutableRect2i getSlotBackgroundArea() {
      return this.slotBackgroundArea;
   }

   public ImmutableRect2i getNextPageButtonArea() {
      return this.navigation.getNextButtonArea();
   }

   public ImmutableRect2i getBackButtonArea() {
      return this.navigation.getBackButtonArea();
   }

   public IPaged getPageDelegate() {
      return this.pageDelegate;
   }

   public void draw(Minecraft minecraft, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      if (this.gridConfig.drawBackground()) {
         this.background.draw(guiGraphics, this.backgroundArea);
         this.slotBackground.draw(guiGraphics, this.slotBackgroundArea);
      }

      this.ingredientGrid.draw(minecraft, guiGraphics, mouseX, mouseY);
      this.navigation.draw(minecraft, guiGraphics, mouseX, mouseY, partialTicks);
   }

   public void drawTooltips(Minecraft minecraft, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      this.ghostIngredientDragManager.drawTooltips(minecraft, guiGraphics, mouseX, mouseY);
      this.ingredientGrid.drawTooltips(minecraft, guiGraphics, mouseX, mouseY);
   }

   public boolean isMouseOver(double mouseX, double mouseY) {
      return this.backgroundArea.contains(mouseX, mouseY) && this.guiExclusionAreas.stream().noneMatch(area -> area.contains(mouseX, mouseY));
   }

   public IUserInputHandler createInputHandler() {
      return new CombinedInputHandler(
         this.debugName,
         new IngredientGridWithNavigation.UserInputHandler(
            this.pageDelegate, this.ingredientGrid, this.toggleState, this.clientConfig, this.commandUtil, this.ingredientManager, this::isMouseOver
         ),
         this.ingredientGrid.getInputHandler(),
         this.navigation.createInputHandler()
      );
   }

   @Override
   public Stream<IClickableIngredientInternal<?>> getIngredientUnderMouse(double mouseX, double mouseY) {
      return this.ingredientGrid.getIngredientUnderMouse(mouseX, mouseY);
   }

   @Override
   public Stream<IDraggableIngredientInternal<?>> getDraggableIngredientUnderMouse(double mouseX, double mouseY) {
      return this.ingredientGrid.getDraggableIngredientUnderMouse(mouseX, mouseY);
   }

   public <T> Stream<T> getVisibleIngredients(IIngredientType<T> ingredientType) {
      return this.ingredientGrid.getVisibleIngredients(ingredientType);
   }

   public boolean isEmpty() {
      return this.ingredientSource.getElements().isEmpty();
   }

   public void close() {
      this.active = false;
      this.ghostIngredientDragManager.stopDrag();
   }

   public void drawOnForeground(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      this.ghostIngredientDragManager.drawOnForeground(guiGraphics, mouseX, mouseY);
   }

   public IDragHandler createDragHandler() {
      return this.ghostIngredientDragManager.createDragHandler();
   }

   public int size() {
      return this.ingredientGrid.size();
   }

   public Stream<IngredientListSlot> getSlots() {
      return this.ingredientGrid.getSlots();
   }

   private class IngredientGridPaged implements IPaged {
      @Override
      public boolean nextPage() {
         if (this.getPageCount() <= 1) {
            return false;
         }

         int itemsCount = IngredientGridWithNavigation.this.ingredientSource.getElements().size();
         if (itemsCount > 0) {
            IngredientGridWithNavigation.this.firstItemIndex = IngredientGridWithNavigation.this.firstItemIndex
               + IngredientGridWithNavigation.this.ingredientGrid.size();
            if (IngredientGridWithNavigation.this.firstItemIndex >= itemsCount) {
               IngredientGridWithNavigation.this.firstItemIndex = 0;
            }

            IngredientGridWithNavigation.this.updateLayout(false);
            return true;
         } else {
            IngredientGridWithNavigation.this.firstItemIndex = 0;
            IngredientGridWithNavigation.this.updateLayout(false);
            return false;
         }
      }

      @Override
      public boolean previousPage() {
         if (this.getPageCount() <= 1) {
            return false;
         }

         int itemsPerPage = IngredientGridWithNavigation.this.ingredientGrid.size();
         if (itemsPerPage == 0) {
            IngredientGridWithNavigation.this.firstItemIndex = 0;
            IngredientGridWithNavigation.this.updateLayout(false);
            return false;
         }

         int itemsCount = IngredientGridWithNavigation.this.ingredientSource.getElements().size();
         int pageNum = IngredientGridWithNavigation.this.firstItemIndex / itemsPerPage;
         if (pageNum == 0) {
            pageNum = itemsCount / itemsPerPage;
         } else {
            pageNum--;
         }

         IngredientGridWithNavigation.this.firstItemIndex = itemsPerPage * pageNum;
         if (IngredientGridWithNavigation.this.firstItemIndex > 0 && IngredientGridWithNavigation.this.firstItemIndex == itemsCount) {
            IngredientGridWithNavigation.this.firstItemIndex = itemsPerPage * --pageNum;
         }

         IngredientGridWithNavigation.this.updateLayout(false);
         return true;
      }

      @Override
      public boolean hasNext() {
         return this.getPageCount() > 1;
      }

      @Override
      public boolean hasPrevious() {
         return this.getPageCount() > 1;
      }

      @Override
      public int getPageCount() {
         int itemCount = IngredientGridWithNavigation.this.ingredientSource.getElements().size();
         int stacksPerPage = IngredientGridWithNavigation.this.ingredientGrid.size();
         if (stacksPerPage == 0) {
            return 1;
         }

         int pageCount = MathUtil.divideCeil(itemCount, stacksPerPage);
         return Math.max(1, pageCount);
      }

      @Override
      public int getPageNumber() {
         int stacksPerPage = IngredientGridWithNavigation.this.ingredientGrid.size();
         return stacksPerPage == 0 ? 0 : IngredientGridWithNavigation.this.firstItemIndex / stacksPerPage;
      }
   }

   private static class UserInputHandler implements IUserInputHandler {
      private final IngredientGridWithNavigation.IngredientGridPaged paged;
      private final IRecipeFocusSource focusSource;
      private final IClientToggleState toggleState;
      private final IClientConfig clientConfig;
      private final IMouseOverable mouseOverable;
      private final CommandUtil commandUtil;
      private final IIngredientManager ingredientManager;

      private UserInputHandler(
         IngredientGridWithNavigation.IngredientGridPaged paged,
         IRecipeFocusSource focusSource,
         IClientToggleState toggleState,
         IClientConfig clientConfig,
         CommandUtil commandUtil,
         IIngredientManager ingredientManager,
         IMouseOverable mouseOverable
      ) {
         this.paged = paged;
         this.focusSource = focusSource;
         this.toggleState = toggleState;
         this.clientConfig = clientConfig;
         this.mouseOverable = mouseOverable;
         this.commandUtil = commandUtil;
         this.ingredientManager = ingredientManager;
      }

      @Override
      public Optional<IUserInputHandler> handleMouseScrolled(double mouseX, double mouseY, double scrollDeltaX, double scrollDeltaY) {
         if (!this.mouseOverable.isMouseOver(mouseX, mouseY)) {
            return Optional.empty();
         }

         if (scrollDeltaY < 0.0) {
            if (this.paged.nextPage()) {
               return Optional.of(this);
            }
         } else if (scrollDeltaY > 0.0 && this.paged.previousPage()) {
            return Optional.of(this);
         }

         return Optional.empty();
      }

      @Override
      public Optional<IUserInputHandler> handleUserInput(Screen screen, UserInput input, IInternalKeyMappings keyBindings) {
         if (input.is(keyBindings.getNextPage())) {
            this.paged.nextPage();
            return Optional.of(this);
         } else if (input.is(keyBindings.getPreviousPage())) {
            this.paged.previousPage();
            return Optional.of(this);
         } else {
            return this.checkHotbarKeys(screen, input);
         }
      }

      private Optional<IUserInputHandler> checkHotbarKeys(Screen screen, UserInput input) {
         if (this.clientConfig.isCheatToHotbarUsingHotkeysEnabled() && this.toggleState.isCheatItemsEnabled() && !(screen instanceof RecipesGui)) {
            double mouseX = input.getMouseX();
            double mouseY = input.getMouseY();
            if (!this.mouseOverable.isMouseOver(mouseX, mouseY)) {
               return Optional.empty();
            }

            Minecraft minecraft = Minecraft.getInstance();
            Options gameSettings = minecraft.options;
            int hotbarSlot = getHotbarSlotForInput(input, gameSettings);
            return hotbarSlot < 0 ? Optional.empty() : this.focusSource.getIngredientUnderMouse(mouseX, mouseY).flatMap(clickedIngredient -> {
               ItemStack cheatItemStack = clickedIngredient.getCheatItemStack(this.ingredientManager);
               if (!cheatItemStack.isEmpty()) {
                  this.commandUtil.setHotbarStack(cheatItemStack, hotbarSlot);
                  return Stream.of(new SameElementInputHandler(this, clickedIngredient::isMouseOver));
               } else {
                  return Stream.empty();
               }
            }).findFirst();
         } else {
            return Optional.empty();
         }
      }

      private static int getHotbarSlotForInput(UserInput input, Options gameSettings) {
         for (int hotbarSlot = 0; hotbarSlot < gameSettings.keyHotbarSlots.length; hotbarSlot++) {
            KeyMapping keyHotbarSlot = gameSettings.keyHotbarSlots[hotbarSlot];
            if (input.is(keyHotbarSlot)) {
               return hotbarSlot;
            }
         }

         return -1;
      }
   }
}
