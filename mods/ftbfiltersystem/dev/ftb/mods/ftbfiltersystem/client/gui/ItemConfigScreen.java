package dev.ftb.mods.ftbfiltersystem.client.gui;

import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterConfigScreen;
import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterScreen;
import dev.ftb.mods.ftbfiltersystem.client.GuiUtil;
import dev.ftb.mods.ftbfiltersystem.client.gui.widget.ItemWidget;
import dev.ftb.mods.ftbfiltersystem.filter.ItemFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag.Default;
import org.jetbrains.annotations.Nullable;

public class ItemConfigScreen extends AbstractFilterConfigScreen<ItemFilter> implements GhostDropReceiver {
   private static final int SEARCH_ROWS = 4;
   private static final int SEARCH_COLS = 9;
   private static ItemConfigScreen.ItemSource itemSource = ItemConfigScreen.ItemSource.CREATIVE;
   private static String lastSearch = "";
   private static List<ItemConfigScreen.SearchEntry> cachedCreativeEntries;
   private static List<ItemConfigScreen.SearchEntry> cachedInventoryEntries;
   private final List<ItemStack> currentStacks = new ArrayList<>();
   private Rect2i scrollArea;
   private double currentScroll;
   private boolean isScrolling;
   private EditBox searchField;
   private final List<ItemConfigScreen.SearchItemWidget> itemWidgets = new ArrayList<>();
   private ItemWidget selectedWidget;

   public ItemConfigScreen(ItemFilter filter, AbstractFilterScreen parentScreen) {
      super(filter, parentScreen, 194, 117);
      cachedInventoryEntries = null;
   }

   @Override
   protected void init() {
      super.init();
      this.searchField = this.makeSearchEditBox(this.leftPos + 8, this.topPos + 38, () -> lastSearch, s -> lastSearch = s);
      this.addRenderableWidget(this.searchField);
      this.setFocused(this.searchField);
      this.selectedWidget = (ItemWidget)this.addRenderableWidget(new ItemWidget(this.leftPos + 8, this.topPos + 17, 18, 18, ItemStack.EMPTY));
      int btnWidth = Arrays.stream(ItemConfigScreen.ItemSource.values()).map(val -> this.font.width(val.getDisplayName())).max(Integer::compareTo).orElse(50)
         + 10;
      this.addRenderableWidget(
         CycleButton.builder(ItemConfigScreen.ItemSource::getDisplayName)
            .withValues(ItemConfigScreen.ItemSource.values())
            .displayOnlyValue()
            .withInitialValue(itemSource)
            .create(this.leftPos + this.guiWidth - btnWidth - 21, this.topPos + 5, btnWidth, 16, Component.empty(), (btn, val) -> {
               itemSource = val;
               this.updateSearchEntries();
            })
      );
      this.itemWidgets.clear();

      for (int row = 0; row < 4; row++) {
         for (int col = 0; col < 9; col++) {
            ItemConfigScreen.SearchItemWidget w = new ItemConfigScreen.SearchItemWidget(row, col);
            this.itemWidgets.add(w);
            this.addRenderableWidget(w);
         }
      }

      this.scrollArea = new Rect2i(this.leftPos + 174, this.topPos + 55, 14, 70);
      this.setSelectedStack(new ItemStack(this.filter.getMatchItem()));
      this.updateSearchEntries();
   }

   private void setSelectedStack(ItemStack stack) {
      this.selectedWidget.setStack(stack.copy());
   }

   @Override
   protected void doScheduledUpdate() {
      this.updateSearchEntries();
   }

   @Override
   public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
      int sx = this.scrollArea.getX();
      int sy1 = this.scrollArea.getY();
      int sy2 = sy1 + this.scrollArea.getHeight();
      FormattedText txt = GuiUtil.ellipsize(
         this.font,
         this.selectedWidget.getStack().getItem().getDescription(),
         this.leftPos + this.guiWidth - (this.selectedWidget.getX() + this.selectedWidget.getWidth() + 6)
      );
      guiGraphics.drawString(
         this.font,
         Language.getInstance().getVisualOrder(txt),
         this.selectedWidget.getX() + this.selectedWidget.getWidth() + 3,
         this.selectedWidget.getY() + 8,
         4210752,
         false
      );
      guiGraphics.fill(
         this.scrollArea.getX() - 2,
         this.scrollArea.getY() - 2,
         this.scrollArea.getX() + this.scrollArea.getWidth(),
         this.scrollArea.getY() + this.scrollArea.getHeight(),
         -8355712
      );
      guiGraphics.fill(
         this.scrollArea.getX() - 1,
         this.scrollArea.getY() - 1,
         this.scrollArea.getX() + this.scrollArea.getWidth() - 1,
         this.scrollArea.getY() + this.scrollArea.getHeight() - 1,
         -6250336
      );
      guiGraphics.blitSprite(
         ResourceLocation.withDefaultNamespace("container/creative_inventory/scroller"), sx, sy1 + (int)((sy2 - sy1 - 17) * this.currentScroll), 12, 15
      );
   }

   public boolean mouseScrolled(double x, double y, double dirX, double dirY) {
      if (dirY != 0.0 && this.needsScrollBars()) {
         int j = this.currentStacks.size() / 10 - 3 + 1;
         float i = dirY > 0.0 ? 1.0F : -1.0F;
         this.scrollTo(Mth.clamp(this.currentScroll - i / j, 0.0, 1.0));
         return true;
      } else {
         return super.mouseScrolled(x, y, dirX, dirY);
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      this.isScrolling = button == 0 && this.needsScrollBars() && this.scrollArea.contains((int)mouseX, (int)mouseY);
      if (this.isScrolling) {
         this.scrollToMouse(mouseY);
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      if (this.isScrolling) {
         this.scrollToMouse(mouseY);
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      this.isScrolling = false;
      return super.mouseReleased(mouseX, mouseY, button);
   }

   protected ItemFilter makeNewFilter() {
      return new ItemFilter(this.filter.getParent(), this.selectedWidget.getStack().getItem());
   }

   private boolean needsScrollBars() {
      return this.currentStacks.size() > 36;
   }

   private void scrollToMouse(double mouseY) {
      this.scrollTo(Mth.clamp((mouseY - this.scrollArea.getY()) / this.scrollArea.getHeight(), 0.0, 1.0));
   }

   private void scrollTo(double scrollPos) {
      this.currentScroll = scrollPos;
      int i = this.currentStacks.size() / 9 - 4 + 1;
      int j = Math.max(0, (int)(this.currentScroll * i + 0.5));

      for (int row = 0; row < 4; row++) {
         for (int col = 0; col < 9; col++) {
            int idx = col + (row + j) * 9;
            ItemStack stack = idx >= 0 && idx < this.currentStacks.size() ? this.currentStacks.get(idx) : ItemStack.EMPTY;
            this.itemWidgets.get(row * 9 + col).setStack(stack);
         }
      }
   }

   private void updateSearchEntries() {
      this.currentStacks.clear();
      String srch = this.searchField.getValue().toLowerCase();
      List<ItemStack> applicableEntries = itemSource.get().stream().filter(entry -> entry.test(srch)).map(entry -> entry.stack).toList();
      this.currentStacks.addAll(applicableEntries);
      this.scrollTo(0.0);
   }

   @Override
   public Rect2i getGhostDropRegion() {
      return new Rect2i(this.selectedWidget.getX(), this.selectedWidget.getY(), this.selectedWidget.getWidth(), this.selectedWidget.getHeight());
   }

   @Override
   public void receiveGhostDrop(ItemStack stack) {
      this.selectedWidget.setStack(stack);
   }

   enum ItemSource {
      CREATIVE(ItemConfigScreen.ItemSource::getCreativeItems, "creative"),
      INV(ItemConfigScreen.ItemSource::getInventoryItems, "inventory");

      private final Supplier<List<ItemConfigScreen.SearchEntry>> itemSupplier;
      private final Component name;

      ItemSource(Supplier<List<ItemConfigScreen.SearchEntry>> itemSupplier, String name) {
         this.itemSupplier = itemSupplier;
         this.name = Component.translatable("ftbfiltersystem.gui.item_source." + name);
      }

      List<ItemConfigScreen.SearchEntry> get() {
         return this.itemSupplier.get();
      }

      public Component getDisplayName() {
         return this.name;
      }

      private static List<ItemConfigScreen.SearchEntry> getCreativeItems() {
         if (ItemConfigScreen.cachedCreativeEntries == null) {
            CreativeModeTabs.tryRebuildTabContents(FeatureFlags.DEFAULT_FLAGS, false, Minecraft.getInstance().level.registryAccess());
            ItemConfigScreen.cachedCreativeEntries = CreativeModeTabs.searchTab().getDisplayItems().stream().map(ItemConfigScreen.SearchEntry::new).toList();
         }

         return ItemConfigScreen.cachedCreativeEntries;
      }

      private static List<ItemConfigScreen.SearchEntry> getInventoryItems() {
         if (ItemConfigScreen.cachedInventoryEntries == null) {
            Inventory inv = Minecraft.getInstance().player.getInventory();
            ItemConfigScreen.cachedInventoryEntries = new ArrayList<>();

            for (int i = 9; i < 36; i++) {
               ItemConfigScreen.cachedInventoryEntries.add(new ItemConfigScreen.SearchEntry((ItemStack)inv.items.get(i)));
            }

            for (int i = 0; i < 9; i++) {
               ItemConfigScreen.cachedInventoryEntries.add(new ItemConfigScreen.SearchEntry((ItemStack)inv.items.get(i)));
            }
         }

         return ItemConfigScreen.cachedInventoryEntries;
      }
   }

   static class SearchEntry implements Predicate<String> {
      public final ItemStack stack;
      private final String tooltip;

      SearchEntry(ItemStack stack) {
         this.stack = stack;

         List<String> l;
         try {
            Minecraft mc = Minecraft.getInstance();
            l = stack.getTooltipLines(TooltipContext.of(mc.level), mc.player, mc.options.advancedItemTooltips ? Default.ADVANCED : Default.NORMAL)
               .stream()
               .<String>map(Component::getString)
               .collect(Collectors.toList());
         } catch (Exception ignored) {
            l = Collections.emptyList();
         }

         this.tooltip = String.join("\n", l).toLowerCase();
      }

      public boolean test(String searchString) {
         return this.tooltip.contains(searchString);
      }
   }

   private class SearchItemWidget extends ItemWidget {
      public SearchItemWidget(int row, int col) {
         super(ItemConfigScreen.this.leftPos + 8 + 18 * col, ItemConfigScreen.this.topPos + 53 + 18 * row, 18, 18, ItemStack.EMPTY);
      }

      @Override
      protected void handleClick(boolean doubleClick) {
         if (doubleClick) {
            ItemConfigScreen.this.applyChanges();
         } else {
            ItemConfigScreen.this.setSelectedStack(this.getStack());
         }
      }

      @Nullable
      public Tooltip getTooltip() {
         return super.getTooltip();
      }
   }
}
