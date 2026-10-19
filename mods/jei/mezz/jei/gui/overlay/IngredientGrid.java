package mezz.jei.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.helpers.IColorHelper;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.common.Internal;
import mezz.jei.common.config.IClientConfig;
import mezz.jei.common.config.IClientToggleState;
import mezz.jei.common.config.IIngredientFilterConfig;
import mezz.jei.common.config.IIngredientGridConfig;
import mezz.jei.common.gui.JeiTooltip;
import mezz.jei.common.input.IInternalKeyMappings;
import mezz.jei.common.network.IConnectionToServer;
import mezz.jei.common.util.ImmutablePoint2i;
import mezz.jei.common.util.ImmutableRect2i;
import mezz.jei.common.util.ImmutableSize2i;
import mezz.jei.common.util.MathUtil;
import mezz.jei.gui.ingredients.GuiIngredientProperties;
import mezz.jei.gui.input.IClickableIngredientInternal;
import mezz.jei.gui.input.IDraggableIngredientInternal;
import mezz.jei.gui.input.IRecipeFocusSource;
import mezz.jei.gui.input.IUserInputHandler;
import mezz.jei.gui.input.handlers.DeleteItemInputHandler;
import mezz.jei.gui.overlay.elements.IElement;
import mezz.jei.gui.util.AlignmentUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.Type;
import org.jetbrains.annotations.Nullable;

public class IngredientGrid implements IRecipeFocusSource, IIngredientGrid {
   private static final int INGREDIENT_PADDING = 1;
   public static final int INGREDIENT_WIDTH = GuiIngredientProperties.getWidth(1);
   public static final int INGREDIENT_HEIGHT = GuiIngredientProperties.getHeight(1);
   private final IIngredientManager ingredientManager;
   private final IIngredientGridConfig gridConfig;
   private final boolean searchable;
   private final IngredientListRenderer ingredientListRenderer;
   private final DeleteItemInputHandler deleteItemHandler;
   private final IngredientGridTooltipHelper tooltipHelper;
   private Set<ImmutableRect2i> guiExclusionAreas = Set.of();
   private ImmutableRect2i area = ImmutableRect2i.EMPTY;

   public IngredientGrid(
      IIngredientManager ingredientManager,
      IIngredientGridConfig gridConfig,
      IIngredientFilterConfig ingredientFilterConfig,
      IClientConfig clientConfig,
      IClientToggleState toggleState,
      IConnectionToServer serverConnection,
      IInternalKeyMappings keyBindings,
      IColorHelper colorHelper,
      boolean searchable
   ) {
      this.ingredientManager = ingredientManager;
      this.gridConfig = gridConfig;
      this.searchable = searchable;
      this.ingredientListRenderer = new IngredientListRenderer(ingredientManager, searchable);
      this.tooltipHelper = new IngredientGridTooltipHelper(ingredientManager, ingredientFilterConfig, toggleState, keyBindings, colorHelper);
      this.deleteItemHandler = new DeleteItemInputHandler(this, toggleState, clientConfig, serverConnection, ingredientManager);
   }

   public IUserInputHandler getInputHandler() {
      return this.deleteItemHandler;
   }

   public int size() {
      return this.ingredientListRenderer.size();
   }

   public void updateBounds(ImmutableRect2i availableArea, Set<ImmutableRect2i> guiExclusionAreas, @Nullable ImmutablePoint2i mouseExclusionPoint) {
      this.ingredientListRenderer.clear();
      this.area = calculateBounds(this.gridConfig, availableArea);
      this.guiExclusionAreas = guiExclusionAreas;

      for (int y = this.area.getY(); y < this.area.getY() + this.area.getHeight(); y += INGREDIENT_HEIGHT) {
         for (int x = this.area.getX(); x < this.area.getX() + this.area.getWidth(); x += INGREDIENT_WIDTH) {
            IngredientListSlot ingredientListSlot = new IngredientListSlot(x, y, INGREDIENT_WIDTH, INGREDIENT_HEIGHT, 1);
            ImmutableRect2i stackArea = ingredientListSlot.getArea();
            boolean blocked = MathUtil.intersects(guiExclusionAreas, stackArea.expandBy(2))
               || mouseExclusionPoint != null && stackArea.contains(mouseExclusionPoint);
            ingredientListSlot.setBlocked(blocked);
            this.ingredientListRenderer.add(ingredientListSlot);
         }
      }
   }

   public static ImmutableSize2i calculateSize(IIngredientGridConfig config, ImmutableRect2i availableArea) {
      int columns = Math.min(availableArea.getWidth() / INGREDIENT_WIDTH, config.getMaxColumns());
      int rows = Math.min(availableArea.getHeight() / INGREDIENT_HEIGHT, config.getMaxRows());
      return rows >= config.getMinRows() && columns >= config.getMinColumns()
         ? new ImmutableSize2i(columns * INGREDIENT_WIDTH, rows * INGREDIENT_HEIGHT)
         : ImmutableSize2i.EMPTY;
   }

   public static ImmutableRect2i calculateBounds(IIngredientGridConfig config, ImmutableRect2i availableArea) {
      ImmutableSize2i size = calculateSize(config, availableArea);
      return AlignmentUtil.align(size, availableArea, config.getHorizontalAlignment(), config.getVerticalAlignment());
   }

   public static IngredientGrid.SlotInfo calculateBlockedSlotPercentage(
      IIngredientGridConfig config, ImmutableRect2i availableArea, Set<ImmutableRect2i> exclusionAreas
   ) {
      ImmutableRect2i area = calculateBounds(config, availableArea);
      int total = 0;
      int blocked = 0;

      for (int y = area.getY(); y < area.getY() + area.getHeight(); y += INGREDIENT_HEIGHT) {
         for (int x = area.getX(); x < area.getX() + area.getWidth(); x += INGREDIENT_WIDTH) {
            IngredientListSlot ingredientListSlot = new IngredientListSlot(x, y, INGREDIENT_WIDTH, INGREDIENT_HEIGHT, 1);
            ImmutableRect2i stackArea = ingredientListSlot.getArea();
            if (MathUtil.intersects(exclusionAreas, stackArea.expandBy(2))) {
               blocked++;
            }

            total++;
         }
      }

      return new IngredientGrid.SlotInfo(total, blocked);
   }

   public ImmutableRect2i getArea() {
      return this.area;
   }

   public void draw(Minecraft minecraft, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      RenderSystem.disableBlend();
      this.ingredientListRenderer.render(guiGraphics);
      if (this.isMouseOver(mouseX, mouseY) && !this.deleteItemHandler.shouldDeleteItemOnClick(minecraft, mouseX, mouseY)) {
         this.ingredientListRenderer
            .getSlots()
            .filter(s -> s.getArea().contains(mouseX, mouseY))
            .filter(s -> s.getOptionalElement().isPresent())
            .findFirst()
            .ifPresent(s -> drawHighlight(guiGraphics, s.getArea()));
      }
   }

   public static void drawHighlight(GuiGraphics guiGraphics, ImmutableRect2i area) {
      guiGraphics.fillGradient(
         RenderType.guiOverlay(), area.getX(), area.getY(), area.getX() + area.getWidth(), area.getY() + area.getHeight(), -2130706433, -2130706433, 0
      );
   }

   public void drawTooltips(Minecraft minecraft, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      if (this.isMouseOver(mouseX, mouseY)) {
         if (this.deleteItemHandler.shouldDeleteItemOnClick(minecraft, mouseX, mouseY)) {
            this.deleteItemHandler.drawTooltips(guiGraphics, mouseX, mouseY);
         } else {
            this.ingredientListRenderer
               .getSlots()
               .filter(s -> s.isMouseOver(mouseX, mouseY))
               .map(IngredientListSlot::getOptionalElement)
               .flatMap(Optional::stream)
               .findFirst()
               .ifPresent(element -> this.drawTooltip(guiGraphics, mouseX, mouseY, element));
         }
      }
   }

   private <T> void drawTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY, IElement<T> element) {
      ITypedIngredient<T> typedIngredient = element.getTypedIngredient();
      IIngredientType<T> ingredientType = typedIngredient.getType();
      IIngredientRenderer<T> ingredientRenderer = this.ingredientManager.getIngredientRenderer(ingredientType);
      IIngredientHelper<T> ingredientHelper = this.ingredientManager.getIngredientHelper(ingredientType);
      JeiTooltip tooltip = new JeiTooltip();
      element.getTooltip(tooltip, this.tooltipHelper, ingredientRenderer, ingredientHelper);
      if (this.searchable) {
         this.addCreativeTabs(tooltip, typedIngredient);
      }

      tooltip.draw(guiGraphics, mouseX, mouseY, typedIngredient, ingredientRenderer, this.ingredientManager);
   }

   private <T> void addCreativeTabs(ITooltipBuilder tooltipBuilder, ITypedIngredient<T> typedIngredient) {
      IClientConfig clientConfig = Internal.getJeiClientConfigs().getClientConfig();
      if (clientConfig.isShowCreativeTabNamesEnabled()) {
         ItemStack itemStack = typedIngredient.getItemStack().orElse(ItemStack.EMPTY);
         if (!itemStack.isEmpty()) {
            for (CreativeModeTab itemGroup : CreativeModeTabs.allTabs()) {
               if (itemGroup.shouldDisplay() && itemGroup.getType() == Type.CATEGORY && itemGroup.contains(itemStack)) {
                  Component displayName = itemGroup.getDisplayName();
                  tooltipBuilder.add(displayName.copy().withStyle(ChatFormatting.BLUE));
               }
            }
         }
      }
   }

   @Override
   public boolean isMouseOver(double mouseX, double mouseY) {
      return this.area.contains(mouseX, mouseY) && this.guiExclusionAreas.stream().noneMatch(area -> area.contains(mouseX, mouseY));
   }

   @Override
   public Stream<IClickableIngredientInternal<?>> getIngredientUnderMouse(double mouseX, double mouseY) {
      return this.ingredientListRenderer
         .getSlots()
         .filter(s -> s.isMouseOver(mouseX, mouseY))
         .map(IngredientListSlot::getClickableIngredient)
         .flatMap(Optional::stream);
   }

   @Override
   public Stream<IDraggableIngredientInternal<?>> getDraggableIngredientUnderMouse(double mouseX, double mouseY) {
      return this.ingredientListRenderer
         .getSlots()
         .filter(s -> s.isMouseOver(mouseX, mouseY))
         .map(IngredientListSlot::getDraggableIngredient)
         .flatMap(Optional::stream);
   }

   public Stream<IngredientListSlot> getSlots() {
      return this.ingredientListRenderer.getSlots();
   }

   public <T> Stream<T> getVisibleIngredients(IIngredientType<T> ingredientType) {
      return this.ingredientListRenderer
         .getSlots()
         .map(IngredientListSlot::getOptionalElement)
         .flatMap(Optional::stream)
         .map(IElement::getTypedIngredient)
         .map(i -> i.getIngredient(ingredientType))
         .flatMap(Optional::stream);
   }

   public void set(int firstItemIndex, List<IElement<?>> ingredientList) {
      this.ingredientListRenderer.set(firstItemIndex, ingredientList);
   }

   public boolean hasRoom() {
      return !this.area.isEmpty();
   }

   public record SlotInfo(int total, int blocked) {
      public float percentBlocked() {
         return (float)this.blocked / this.total;
      }
   }
}
