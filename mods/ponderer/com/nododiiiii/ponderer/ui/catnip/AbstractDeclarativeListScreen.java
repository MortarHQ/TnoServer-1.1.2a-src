package com.nododiiiii.ponderer.ui.catnip;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList;
import net.createmod.catnip.config.ui.ConfigTextField;
import net.createmod.catnip.config.ui.ConfigScreenList.Entry;
import net.createmod.catnip.gui.ConfirmationScreen;
import net.createmod.catnip.gui.element.FadableScreenElement;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.createmod.catnip.lang.FontHelper;
import net.createmod.catnip.lang.FontHelper.Palette;
import net.createmod.ponder.enums.PonderGuiTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class AbstractDeclarativeListScreen extends AbstractDeclarativeScreen {
   protected static final int DEFAULT_LIST_WIDTH = 300;
   protected static final int CONTENT_TOP = 35;
   protected static final int CONTENT_BOTTOM_MARGIN = 45;
   private static final int LIST_HEADER_HEIGHT = 3;
   private static final int LIST_VERTICAL_PADDING = 8;
   @Nullable
   protected BoxWidget saveChanges;
   @Nullable
   protected BoxWidget discardChanges;
   @Nullable
   protected BoxWidget goBack;
   @Nullable
   protected ConfigTextField search;
   @Nullable
   protected ConfigScreenList list;
   @Nullable
   protected ConfigScreenList headerList;
   private final int preferredListWidth;
   private final List<Entry> headerEntries = new ArrayList<>();
   private final ListScrollPreserver scrollPreserver = new ListScrollPreserver();
   private String searchQuery = "";
   private int listWidth;

   protected AbstractDeclarativeListScreen(@Nullable Screen parent, String scopeKey, String titleKey) {
      this(parent, scopeKey, titleKey, 300);
   }

   protected AbstractDeclarativeListScreen(@Nullable Screen parent, String scopeKey, String titleKey, int preferredListWidth) {
      super(parent, scopeKey, titleKey);
      this.preferredListWidth = preferredListWidth;
   }

   protected void init() {
      super.init();
      this.headerList = null;
      this.listWidth = Math.min(this.width - 80, this.preferredListWidth);
      int yCenter = this.height / 2;
      int listLeft = this.width / 2 - this.listWidth / 2;
      int actionLeft = listLeft + this.listWidth + 10;
      this.saveChanges = (BoxWidget)new BoxWidget(actionLeft, yCenter - 25, 20, 20).withPadding(2.0F, 2.0F).withCallback(this::saveEdits);
      this.saveChanges
         .showingElement(
            PonderGuiTextures.ICON_CONFIG_SAVE.asStencil().withElementRenderer((FadableScreenElement)BoxWidget.gradientFactory.apply(this.saveChanges))
         );
      this.saveChanges.getToolTip().add(Component.translatable("catnip.ui.save_changes_button"));
      this.saveChanges.getToolTip().addAll(FontHelper.cutTextComponent(Component.translatable("catnip.ui.save_changes_button_tooltip"), Palette.ALL_GRAY));
      this.addRenderableWidget(this.saveChanges);
      this.discardChanges = (BoxWidget)new BoxWidget(actionLeft, yCenter + 5, 20, 20).withPadding(2.0F, 2.0F).withCallback(this::confirmDiscardChanges);
      this.discardChanges
         .showingElement(
            PonderGuiTextures.ICON_CONFIG_DISCARD.asStencil().withElementRenderer((FadableScreenElement)BoxWidget.gradientFactory.apply(this.discardChanges))
         );
      this.discardChanges.getToolTip().add(Component.translatable("catnip.ui.discard_changes_button"));
      this.discardChanges
         .getToolTip()
         .addAll(FontHelper.cutTextComponent(Component.translatable("catnip.ui.discard_changes_button_tooltip"), Palette.ALL_GRAY));
      this.addRenderableWidget(this.discardChanges);
      this.goBack = (BoxWidget)new BoxWidget(actionLeft, yCenter + 65, 20, 20).withPadding(2.0F, 2.0F).withCallback(this::attemptBackToParent);
      this.goBack
         .showingElement(PonderGuiTextures.ICON_CONFIG_BACK.asStencil().withElementRenderer((FadableScreenElement)BoxWidget.gradientFactory.apply(this.goBack)));
      this.goBack.getToolTip().add(Component.translatable("catnip.ui.go_back_button"));
      this.addRenderableWidget(this.goBack);
      this.list = new ConfigScreenList(this.minecraft, this.listWidth, this.contentAreaHeight(), this.contentAreaTop(), this.getEntryHeight());
      this.list.setX(this.width / 2 - this.list.getWidth() / 2);
      this.addRenderableWidget(this.list);
      this.search = new ClippedConfigTextField(this.font, this.width / 2 - this.listWidth / 2, this.height - 35, this.listWidth, 20);
      this.search.setResponder(this::updateFilter);
      this.search.setHint(Component.translatable("catnip.ui.search_hint"));
      this.search.moveCursorToStart(false);
      this.addRenderableWidget(this.search);
      this.rebuildEntries(null);
      if (!this.searchQuery.isEmpty() && this.search != null) {
         this.search.setValue(this.searchQuery);
         this.updateFilter(this.searchQuery);
      }

      this.refreshActionButtons();
   }

   public void tick() {
      super.tick();
      this.refreshActionButtons();
   }

   public void resize(Minecraft client, int width, int height) {
      double scroll = this.scrollPreserver.capture(this.list);
      this.init(client, width, height);
      this.scrollPreserver.remember(scroll);
      this.scrollPreserver.restoreRemembered(this.list);
      if (this.search != null && !this.searchQuery.isEmpty()) {
         this.search.setValue(this.searchQuery);
         this.updateFilter(this.searchQuery);
      }
   }

   @Nullable
   public GuiEventListener getFocused() {
      return (GuiEventListener)(ConfigScreenList.currentText != null ? ConfigScreenList.currentText : super.getFocused());
   }

   protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.renderBreadcrumb(graphics, this.width / 2, 15);
      this.renderStatusMessage(graphics, this.width / 2 - this.listWidth / 2, this.height - 48, this.listWidth);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (super.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else if (this.search != null && hasControlDown() && keyCode == 70) {
         this.search.setFocused(true);
         return true;
      } else if (keyCode == 259) {
         this.attemptBackToParent();
         return true;
      } else {
         return false;
      }
   }

   protected final void rebuildEntries() {
      this.rebuildEntries(null);
   }

   protected final void rebuildEntries(@Nullable Double preservedScroll) {
      if (this.list != null) {
         this.rebuildHeaderEntries();
         this.list.children().clear();
         this.collectEntries(this.list.children());
         this.relayoutListViewport();
         if (preservedScroll != null) {
            this.list.setScrollAmount(preservedScroll);
         } else {
            this.list.setScrollAmount(0.0);
         }

         this.updateFilter(this.searchQuery);
         ListScrollPreserver.restoreAfterSearch(this.list, preservedScroll, this.searchQuery);
      }
   }

   protected final double currentListScroll() {
      return ListScrollPreserver.current(this.list);
   }

   protected final void rememberCurrentListScroll() {
      this.scrollPreserver.capture(this.list);
   }

   protected final void rememberListScroll(double scroll) {
      this.scrollPreserver.remember(scroll);
   }

   protected final void restoreRememberedListScroll() {
      this.scrollPreserver.restoreRemembered(this.list);
   }

   protected final void rebuildListPreservingScroll() {
      this.rebuildEntries(this.scrollPreserver.capture(this.list));
   }

   protected final void rebuildListWithRememberedScroll() {
      this.rebuildEntries(this.scrollPreserver.remembered());
   }

   protected final void rebuildListAtTop() {
      this.scrollPreserver.remember(0.0);
      this.rebuildEntries(0.0);
   }

   protected final PlainTextListEntry textEntry(
      String labelKey, @Nullable String tooltipKey, @Nullable String hintKey, String initialValue, Consumer<String> responder
   ) {
      return new PlainTextListEntry(labelKey, tooltipKey, hintKey, initialValue, responder);
   }

   protected final LocalizedTextListEntry localizedTextEntry(
      String labelKey,
      @Nullable String tooltipKey,
      @Nullable String hintKey,
      String initialValue,
      Supplier<String> langGetter,
      Runnable onToggle,
      Consumer<String> responder
   ) {
      return new LocalizedTextListEntry(labelKey, tooltipKey, hintKey, initialValue, langGetter, onToggle, responder);
   }

   protected int getEntryHeight() {
      return 40;
   }

   protected final int currentListWidthValue() {
      return this.listWidth;
   }

   protected final int contentAreaTop() {
      return 35;
   }

   protected final int contentAreaHeight() {
      return Math.max(0, this.height - 35 - 45);
   }

   protected final int centeredContentTop(int contentHeight) {
      return this.contentAreaTop() + Math.max(0, (this.contentAreaHeight() - contentHeight) / 2);
   }

   protected final int bodyContentTop() {
      return !this.hasHeaderEntries()
         ? this.contentAreaTop()
         : Math.min(this.contentAreaTop() + this.contentAreaHeight(), this.contentAreaTop() + this.currentHeaderHeight() + this.headerListGap());
   }

   protected final int bodyContentHeight() {
      return Math.max(0, this.contentAreaTop() + this.contentAreaHeight() - this.bodyContentTop());
   }

   protected final int centeredBodyContentTop(int contentHeight) {
      return this.bodyContentTop() + Math.max(0, (this.bodyContentHeight() - contentHeight) / 2);
   }

   protected int fixedVisibleRowCount() {
      return -1;
   }

   protected void collectHeaderEntries(List<Entry> entries) {
   }

   protected int headerListGap() {
      return 8;
   }

   protected boolean fillRemainingBodyWhenHeaderPresent() {
      return true;
   }

   protected abstract void collectEntries(List<Entry> var1);

   @Override
   protected abstract boolean hasUnsavedChanges();

   @Override
   protected abstract int getUnsavedChangeCount();

   @Override
   protected abstract boolean saveEdits();

   @Override
   protected abstract void discardEdits();

   private void updateFilter(String query) {
      this.searchQuery = query == null ? "" : query;
      ListSearchHelper.applySearchFilter(this.list, this.search, this.searchQuery, this.getEntryHeight());
   }

   private void confirmDiscardChanges() {
      if (this.hasUnsavedChanges()) {
         int dirtyFields = this.getUnsavedChangeCount();
         new ConfirmationScreen()
            .centered()
            .withText(
               Component.translatable(
                  "catnip.ui.discarding_changes_message",
                  new Object[]{dirtyFields, Component.translatable(dirtyFields != 1 ? "catnip.ui.value_changes_plural" : "catnip.ui.value_changes_singular")}
               )
            )
            .withAction(success -> {
               if (success) {
                  this.discardEdits();
               }
            })
            .open(this);
      }
   }

   private void refreshActionButtons() {
      this.updateButtonState(this.saveChanges, this.isSaveButtonActive());
      this.updateButtonState(this.discardChanges, this.isDiscardButtonActive());
   }

   protected boolean isSaveButtonActive() {
      return this.hasUnsavedChanges();
   }

   protected boolean isDiscardButtonActive() {
      return this.hasUnsavedChanges();
   }

   private void relayoutListViewport() {
      if (this.list != null) {
         int left = this.width / 2 - this.listWidth / 2;
         if (this.hasHeaderEntries()) {
            int headerHeight = this.currentHeaderHeight();
            int headerTop = this.contentAreaTop();
            if (this.headerList != null) {
               this.headerList.updateSizeAndPosition(this.listWidth, headerHeight, headerTop);
               this.headerList.setX(left);
            }

            int bodyTop = this.bodyContentTop();
            int availableBodyHeight = this.bodyContentHeight();
            int bodyHeight = this.fillRemainingBodyWhenHeaderPresent() ? availableBodyHeight : this.desiredListViewportHeight(availableBodyHeight);
            this.list.updateSizeAndPosition(this.listWidth, bodyHeight, bodyTop);
            this.list.setX(left);
         } else {
            int viewportHeight = this.desiredListViewportHeight(this.contentAreaHeight());
            int top = this.centeredContentTop(viewportHeight);
            this.list.updateSizeAndPosition(this.listWidth, viewportHeight, top);
            this.list.setX(left);
         }
      }
   }

   private void rebuildHeaderEntries() {
      this.headerEntries.clear();
      this.collectHeaderEntries(this.headerEntries);
      if (this.headerEntries.isEmpty()) {
         if (this.headerList != null) {
            this.removeWidget(this.headerList);
            this.headerList = null;
         }
      } else {
         if (this.headerList == null) {
            this.headerList = new ConfigScreenList(this.minecraft, this.listWidth, this.currentHeaderHeight(), this.contentAreaTop(), this.getEntryHeight());
            this.headerList.setX(this.width / 2 - this.headerList.getWidth() / 2);
            this.addRenderableWidget(this.headerList);
         }

         this.headerList.children().clear();
         this.headerList.children().addAll(this.headerEntries);
         this.headerList.setScrollAmount(0.0);
      }
   }

   private int desiredListViewportHeight(int availableHeight) {
      if (this.list == null) {
         return availableHeight;
      }

      int fixedRows = this.fixedVisibleRowCount();
      if (fixedRows > 0) {
         return Math.min(availableHeight, fixedRows * this.getEntryHeight() + 3 + 8);
      }

      int contentHeight = this.list.children().size() * this.getEntryHeight() + 3 + 8;
      int minimumHeight = this.getEntryHeight() + 3 + 8;
      return Math.min(availableHeight, Math.max(minimumHeight, contentHeight));
   }

   private boolean hasHeaderEntries() {
      return !this.headerEntries.isEmpty();
   }

   private int currentHeaderHeight() {
      return !this.hasHeaderEntries() ? 0 : this.headerEntries.size() * this.getEntryHeight() + 3 + 8;
   }

   protected final void updateButtonState(@Nullable BoxWidget button, boolean active) {
      if (button != null && button.active != active) {
         button.active = active;
         button.animateGradientFromState();
      }
   }
}
