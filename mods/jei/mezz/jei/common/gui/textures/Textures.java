package mezz.jei.common.gui.textures;

import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.common.gui.elements.DrawableNineSliceTexture;
import mezz.jei.common.gui.elements.DrawableSprite;
import mezz.jei.common.gui.elements.HighResolutionDrawable;
import net.minecraft.resources.ResourceLocation;

public class Textures {
   private final JeiSpriteUploader spriteUploader;
   private final IDrawableStatic slot;
   private final IDrawableStatic outputSlot;
   private final DrawableNineSliceTexture recipeCatalystSlotBackground;
   private final DrawableNineSliceTexture ingredientListSlotBackground;
   private final DrawableNineSliceTexture bookmarkListSlotBackground;
   private final IDrawableStatic tabSelected;
   private final IDrawableStatic tabUnselected;
   private final DrawableNineSliceTexture buttonDisabled;
   private final DrawableNineSliceTexture buttonEnabled;
   private final DrawableNineSliceTexture buttonHighlight;
   private final DrawableNineSliceTexture buttonPressed;
   private final DrawableNineSliceTexture buttonPressedHighlight;
   private final DrawableNineSliceTexture recipeGuiBackground;
   private final DrawableNineSliceTexture ingredientListBackground;
   private final DrawableNineSliceTexture bookmarkListBackground;
   private final DrawableNineSliceTexture recipeBackground;
   private final DrawableNineSliceTexture recipePreviewBackground;
   private final DrawableNineSliceTexture searchBackground;
   private final DrawableNineSliceTexture scrollbarBackground;
   private final DrawableNineSliceTexture scrollbarMarker;
   private final HighResolutionDrawable shapelessIcon;
   private final IDrawableStatic arrowPrevious;
   private final IDrawableStatic arrowNext;
   private final IDrawableStatic recipeTransfer;
   private final IDrawableStatic recipeBookmark;
   private final IDrawableStatic configButtonIcon;
   private final IDrawableStatic configButtonCheatIcon;
   private final IDrawableStatic bookmarkButtonDisabledIcon;
   private final IDrawableStatic bookmarkButtonEnabledIcon;
   private final IDrawableStatic historyButtonDisabledIcon;
   private final IDrawableStatic historyButtonEnabledIcon;
   private final IDrawableStatic infoIcon;
   private final DrawableNineSliceTexture catalystTab;
   private final DrawableNineSliceTexture recipeOptionsTab;
   private final IDrawableStatic flameIcon;
   private final IDrawableStatic flameEmptyIcon;
   private final IDrawableStatic recipeArrow;
   private final IDrawableStatic recipeArrowFilled;
   private final IDrawableStatic recipePlusSign;
   private final IDrawableStatic bookmarksFirst;
   private final IDrawableStatic craftableFirst;
   private final IDrawableStatic brewingStandBackground;
   private final IDrawableStatic brewingStandBlazeHeat;
   private final IDrawableStatic brewingStandBubbles;
   private final IDrawableStatic brewingStandArrow;

   public Textures(JeiSpriteUploader spriteUploader) {
      this.spriteUploader = spriteUploader;
      this.slot = this.createGuiSprite("slot", 18, 18);
      this.outputSlot = this.createGuiSprite("output_slot", 26, 26);
      this.recipeCatalystSlotBackground = this.createNineSliceGuiSprite("recipe_catalyst_slot_background", 18, 18, 4, 4, 4, 4);
      this.ingredientListSlotBackground = this.createNineSliceGuiSprite("ingredient_list_slot_background", 18, 18, 4, 4, 4, 4);
      this.bookmarkListSlotBackground = this.createNineSliceGuiSprite("bookmark_list_slot_background", 18, 18, 4, 4, 4, 4);
      this.tabSelected = this.createGuiSprite("tab_selected", 24, 24);
      this.tabUnselected = this.createGuiSprite("tab_unselected", 24, 24);
      this.buttonDisabled = this.createNineSliceGuiSprite("button_disabled", 20, 20, 6, 6, 6, 6);
      this.buttonEnabled = this.createNineSliceGuiSprite("button_enabled", 20, 20, 6, 6, 6, 6);
      this.buttonHighlight = this.createNineSliceGuiSprite("button_highlight", 20, 20, 6, 6, 6, 6);
      this.buttonPressed = this.createNineSliceGuiSprite("button_pressed", 20, 20, 6, 6, 6, 6);
      this.buttonPressedHighlight = this.createNineSliceGuiSprite("button_pressed_highlight", 20, 20, 6, 6, 6, 6);
      this.recipeGuiBackground = this.createNineSliceGuiSprite("gui_background", 64, 64, 16, 16, 16, 16);
      this.ingredientListBackground = this.createNineSliceGuiSprite("ingredient_list_background", 64, 64, 16, 16, 16, 16);
      this.bookmarkListBackground = this.createNineSliceGuiSprite("bookmark_list_background", 64, 64, 16, 16, 16, 16);
      this.recipeBackground = this.createNineSliceGuiSprite("single_recipe_background", 64, 64, 16, 16, 16, 16);
      this.recipePreviewBackground = this.createNineSliceGuiSprite("recipe_preview_background", 64, 64, 16, 16, 16, 16);
      this.searchBackground = this.createNineSliceGuiSprite("search_background", 20, 20, 6, 6, 6, 6);
      this.scrollbarBackground = this.createNineSliceGuiSprite("scrollbar_background", 14, 50, 6, 6, 6, 6);
      this.scrollbarMarker = this.createNineSliceGuiSprite("scrollbar_marker", 12, 15, 2, 2, 2, 1);
      this.catalystTab = this.createNineSliceGuiSprite("catalyst_tab", 28, 28, 8, 9, 8, 8);
      this.recipeOptionsTab = this.createNineSliceGuiSprite("recipe_options_tab", 28, 28, 8, 9, 8, 8);
      this.recipeArrow = this.createGuiSprite("recipe_arrow", 22, 16);
      this.recipeArrowFilled = this.createGuiSprite("recipe_arrow_filled", 22, 16);
      this.recipePlusSign = this.createGuiSprite("recipe_plus_sign", 13, 13);
      this.brewingStandBackground = this.createGuiSprite("brewing_stand_background", 64, 60);
      this.brewingStandBlazeHeat = this.createGuiSprite("brewing_stand_blaze_heat", 18, 4);
      this.brewingStandBubbles = this.createGuiSprite("brewing_stand_bubbles", 11, 28);
      this.brewingStandArrow = this.createGuiSprite("brewing_stand_arrow", 7, 27);
      DrawableSprite rawShapelessIcon = this.createGuiSprite("icons/shapeless_icon", 36, 36).trim(1, 2, 1, 1);
      this.shapelessIcon = new HighResolutionDrawable(rawShapelessIcon, 4);
      this.arrowPrevious = this.createGuiSprite("icons/arrow_previous", 9, 9).trim(0, 0, 1, 1);
      this.arrowNext = this.createGuiSprite("icons/arrow_next", 9, 9).trim(0, 0, 1, 1);
      this.recipeTransfer = this.createGuiSprite("icons/recipe_transfer", 7, 7);
      this.recipeBookmark = this.createGuiSprite("icons/recipe_bookmark", 9, 9);
      this.configButtonIcon = this.createGuiSprite("icons/config_button", 16, 16);
      this.configButtonCheatIcon = this.createGuiSprite("icons/config_button_cheat", 16, 16);
      this.bookmarkButtonDisabledIcon = this.createGuiSprite("icons/bookmark_button_disabled", 16, 16);
      this.bookmarkButtonEnabledIcon = this.createGuiSprite("icons/bookmark_button_enabled", 16, 16);
      this.historyButtonDisabledIcon = this.createGuiSprite("icons/history_button_disabled", 16, 16);
      this.historyButtonEnabledIcon = this.createGuiSprite("icons/history_button_enabled", 16, 16);
      this.infoIcon = this.createGuiSprite("icons/info", 16, 16);
      this.flameIcon = this.createGuiSprite("icons/flame", 14, 14);
      this.flameEmptyIcon = this.createGuiSprite("icons/flame_empty", 14, 14);
      this.bookmarksFirst = this.createGuiSprite("icons/bookmarks_first", 16, 16);
      this.craftableFirst = this.createGuiSprite("icons/craftable_first", 16, 16);
   }

   private ResourceLocation createSprite(String name) {
      return ResourceLocation.fromNamespaceAndPath("jei", name);
   }

   private DrawableSprite createGuiSprite(String name, int width, int height) {
      ResourceLocation location = this.createSprite(name);
      return new DrawableSprite(this.spriteUploader, location, width, height);
   }

   private DrawableNineSliceTexture createNineSliceGuiSprite(String name, int width, int height, int left, int right, int top, int bottom) {
      ResourceLocation location = this.createSprite(name);
      return new DrawableNineSliceTexture(this.spriteUploader, location, width, height, left, right, top, bottom);
   }

   public IDrawableStatic getSlot() {
      return this.slot;
   }

   public IDrawableStatic getOutputSlot() {
      return this.outputSlot;
   }

   public IDrawableStatic getTabSelected() {
      return this.tabSelected;
   }

   public IDrawableStatic getTabUnselected() {
      return this.tabUnselected;
   }

   public HighResolutionDrawable getShapelessIcon() {
      return this.shapelessIcon;
   }

   public IDrawableStatic getArrowPrevious() {
      return this.arrowPrevious;
   }

   public IDrawableStatic getArrowNext() {
      return this.arrowNext;
   }

   public IDrawableStatic getRecipeTransfer() {
      return this.recipeTransfer;
   }

   public IDrawableStatic getRecipeBookmark() {
      return this.recipeBookmark;
   }

   public IDrawableStatic getBookmarksFirst() {
      return this.bookmarksFirst;
   }

   public IDrawableStatic getCraftableFirst() {
      return this.craftableFirst;
   }

   public IDrawableStatic getConfigButtonIcon() {
      return this.configButtonIcon;
   }

   public IDrawableStatic getConfigButtonCheatIcon() {
      return this.configButtonCheatIcon;
   }

   public IDrawableStatic getBookmarkButtonDisabledIcon() {
      return this.bookmarkButtonDisabledIcon;
   }

   public IDrawableStatic getHistoryButtonDisabledIcon() {
      return this.historyButtonDisabledIcon;
   }

   public IDrawableStatic getHistoryButtonEnabledIcon() {
      return this.historyButtonEnabledIcon;
   }

   public IDrawableStatic getBookmarkButtonEnabledIcon() {
      return this.bookmarkButtonEnabledIcon;
   }

   public DrawableNineSliceTexture getButtonForState(boolean pressed, boolean enabled, boolean hovered) {
      if (!enabled) {
         return this.buttonDisabled;
      } else if (hovered) {
         return pressed ? this.buttonPressedHighlight : this.buttonHighlight;
      } else {
         return pressed ? this.buttonPressed : this.buttonEnabled;
      }
   }

   public DrawableNineSliceTexture getRecipeGuiBackground() {
      return this.recipeGuiBackground;
   }

   public DrawableNineSliceTexture getIngredientListBackground() {
      return this.ingredientListBackground;
   }

   public DrawableNineSliceTexture getBookmarkListBackground() {
      return this.bookmarkListBackground;
   }

   public DrawableNineSliceTexture getRecipeBackground() {
      return this.recipeBackground;
   }

   public DrawableNineSliceTexture getRecipePreviewBackground() {
      return this.recipePreviewBackground;
   }

   public DrawableNineSliceTexture getSearchBackground() {
      return this.searchBackground;
   }

   public IDrawableStatic getInfoIcon() {
      return this.infoIcon;
   }

   public DrawableNineSliceTexture getCatalystTab() {
      return this.catalystTab;
   }

   public DrawableNineSliceTexture getRecipeOptionsTab() {
      return this.recipeOptionsTab;
   }

   public IDrawableStatic getRecipeArrow() {
      return this.recipeArrow;
   }

   public IDrawableStatic getRecipeArrowFilled() {
      return this.recipeArrowFilled;
   }

   public IDrawableStatic getRecipePlusSign() {
      return this.recipePlusSign;
   }

   public DrawableNineSliceTexture getRecipeCatalystSlotBackground() {
      return this.recipeCatalystSlotBackground;
   }

   public DrawableNineSliceTexture getIngredientListSlotBackground() {
      return this.ingredientListSlotBackground;
   }

   public DrawableNineSliceTexture getBookmarkListSlotBackground() {
      return this.bookmarkListSlotBackground;
   }

   public IDrawableStatic getFlameIcon() {
      return this.flameIcon;
   }

   public IDrawableStatic getFlameEmptyIcon() {
      return this.flameEmptyIcon;
   }

   public DrawableNineSliceTexture getScrollbarMarker() {
      return this.scrollbarMarker;
   }

   public DrawableNineSliceTexture getScrollbarBackground() {
      return this.scrollbarBackground;
   }

   public IDrawableStatic getBrewingStandBackground() {
      return this.brewingStandBackground;
   }

   public IDrawableStatic getBrewingStandBlazeHeat() {
      return this.brewingStandBlazeHeat;
   }

   public IDrawableStatic getBrewingStandBubbles() {
      return this.brewingStandBubbles;
   }

   public IDrawableStatic getBrewingStandArrow() {
      return this.brewingStandArrow;
   }

   public JeiSpriteUploader getSpriteUploader() {
      return this.spriteUploader;
   }
}
