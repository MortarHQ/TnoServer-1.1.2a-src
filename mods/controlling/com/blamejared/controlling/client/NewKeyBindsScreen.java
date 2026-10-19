package com.blamejared.controlling.client;

import com.blamejared.controlling.ControllingConstants;
import com.blamejared.controlling.api.DisplayMode;
import com.blamejared.controlling.api.SortOrder;
import com.blamejared.controlling.mixin.AccessKeyBindsScreen;
import com.blamejared.controlling.platform.Services;
import com.blamejared.searchables.api.autcomplete.AutoCompletingEditBox;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.layouts.GridLayout.RowHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList.Entry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class NewKeyBindsScreen extends KeyBindsScreen {
   private AutoCompletingEditBox<Entry> search;
   private DisplayMode displayMode;
   private SortOrder sortOrder = SortOrder.NONE;
   private Button buttonNone;
   private Button buttonConflicting;
   private Button buttonSort;
   private final DisplayableBoolean confirmingReset = new DisplayableBoolean(
      false, ControllingConstants.COMPONENT_OPTIONS_CONFIRM_RESET, ControllingConstants.COMPONENT_CONTROLS_RESET_ALL
   );
   private boolean showFree;
   private Supplier<NewKeyBindsList> newKeyList;
   private Supplier<FreeKeysList> freeKeyList;
   private final OnPress PRESS_RESET = btn -> {
      NewKeyBindsScreen screenx = this;
      Minecraft minecraft = Objects.requireNonNull(screenx.minecraft);
      if (!this.confirmingReset.toggle()) {
         for (KeyMapping keybinding : minecraft.options.keyMappings) {
            Services.PLATFORM.setToDefault(minecraft.options, keybinding);
         }

         this.getKeyBindsList().resetMappingAndUpdateButtons();
      }

      btn.setMessage(this.confirmingReset.currentDisplay());
   };
   private final OnPress PRESS_NONE = btn -> {
      if (this.displayMode == DisplayMode.NONE) {
         this.buttonNone.setMessage(ControllingConstants.COMPONENT_OPTIONS_SHOW_NONE);
         this.displayMode = DisplayMode.ALL;
      } else {
         this.displayMode = DisplayMode.NONE;
         this.buttonNone.setMessage(ControllingConstants.COMPONENT_OPTIONS_SHOW_ALL);
         this.buttonConflicting.setMessage(ControllingConstants.COMPONENT_OPTIONS_SHOW_CONFLICTS);
      }

      this.filterKeys();
   };
   private final OnPress PRESS_SORT = btn -> {
      this.sortOrder = this.sortOrder.cycle();
      btn.setMessage(this.sortOrder.getDisplay());
      this.filterKeys();
   };
   private final OnPress PRESS_CONFLICTING = btn -> {
      if (this.displayMode == DisplayMode.CONFLICTING) {
         this.buttonConflicting.setMessage(ControllingConstants.COMPONENT_OPTIONS_SHOW_CONFLICTS);
         this.displayMode = DisplayMode.ALL;
      } else {
         this.displayMode = DisplayMode.CONFLICTING;
         this.buttonConflicting.setMessage(ControllingConstants.COMPONENT_OPTIONS_SHOW_ALL);
         this.buttonNone.setMessage(ControllingConstants.COMPONENT_OPTIONS_SHOW_NONE);
      }

      this.filterKeys();
   };
   private final OnPress PRESS_FREE = btn -> {
      this.removeWidget(this.getKeyBindsList());
      if (this.showFree) {
         this.buttonSort.active = true;
         this.buttonNone.active = true;
         this.buttonConflicting.active = true;
         this.resetButton().active = this.canReset();
         this.setKeyBindsList(this.newKeyList.get());
      } else {
         this.freeKeyList.get().recalculate();
         this.buttonSort.active = false;
         this.buttonNone.active = false;
         this.buttonConflicting.active = false;
         this.resetButton().active = false;
         this.setKeyBindsList(this.freeKeyList.get());
      }

      this.filterKeys();
      this.addRenderableWidget(this.getKeyBindsList());
      this.setFocused(this.getKeyBindsList());
      this.showFree = !this.showFree;
   };

   public NewKeyBindsScreen(Screen screen, Options settings) {
      super(screen, settings);
      this.layout.setHeaderHeight(48);
      this.layout.setFooterHeight(56);
   }

   protected void init() {
      super.init();
      this.search.moveCursor(0, false);
   }

   protected void addTitle() {
      int searchX = 340;
      int centerX = this.width / 2;
      Supplier<List<Entry>> listSupplier = () -> this.getCustomList().getAllEntries();
      this.search = new AutoCompletingEditBox(
         this.font,
         centerX - searchX / 2,
         20,
         searchX,
         20,
         this.search,
         Component.translatable("selectWorld.search"),
         ControllingConstants.SEARCHABLE_KEYBINDINGS,
         listSupplier
      );
      this.search.addResponder(this::filterKeys);
      LinearLayout header = (LinearLayout)this.layout.addToHeader(LinearLayout.vertical(), layoutSettings -> layoutSettings.paddingVertical(8));
      header.addChild(new StringWidget(this.title, this.font), LayoutSettings::alignHorizontallyCenter);
      header.addChild(this.search, layoutSettings -> layoutSettings.paddingVertical(4));
      this.setInitialFocus(this.search);
   }

   protected void addContents() {
      this.newKeyList = Suppliers.memoize(() -> new NewKeyBindsList(this, this.minecraft));
      this.freeKeyList = Suppliers.memoize(() -> new FreeKeysList(this, this.minecraft));
      this.getAccess().controlling$setKeyBindsList(this.showFree ? this.freeKeyList.get() : this.newKeyList.get());
      this.layout.addToContents(this.getKeyBindsList());
      this.displayMode = DisplayMode.ALL;
   }

   protected void addFooter() {
      int btnWidth = 74;
      this.resetButton(Button.builder(this.confirmingReset.currentDisplay(), this.PRESS_RESET).build());
      this.resetButton().active = this.canReset();
      Button toggleFreeButton = Button.builder(ControllingConstants.COMPONENT_OPTIONS_TOGGLE_FREE, this.PRESS_FREE).size(btnWidth, 20).build();
      this.buttonSort = Button.builder(this.sortOrder.getDisplay(), this.PRESS_SORT).size(btnWidth, 20).build();
      this.buttonNone = Button.builder(ControllingConstants.COMPONENT_OPTIONS_SHOW_NONE, this.PRESS_NONE).size(btnWidth, 20).build();
      this.buttonConflicting = Button.builder(ControllingConstants.COMPONENT_OPTIONS_SHOW_CONFLICTS, this.PRESS_CONFLICTING).size(btnWidth, 20).build();
      GridLayout grid = (GridLayout)this.layout.addToFooter(new GridLayout());
      grid.rowSpacing(4);
      grid.columnSpacing(8);
      RowHelper rowHelper = grid.createRowHelper(2);
      LinearLayout topLeft = (LinearLayout)rowHelper.addChild(LinearLayout.horizontal());
      topLeft.spacing(4);
      topLeft.addChild(toggleFreeButton);
      topLeft.addChild(this.buttonSort);
      LinearLayout topRight = (LinearLayout)rowHelper.addChild(LinearLayout.horizontal());
      topRight.spacing(4);
      topRight.addChild(this.buttonNone);
      topRight.addChild(this.buttonConflicting);
      rowHelper.addChild(this.resetButton());
      rowHelper.addChild(Button.builder(CommonComponents.GUI_DONE, $$0x -> this.onClose()).build());
   }

   protected void repositionElements() {
      super.repositionElements();
      this.resetButton().active = this.canReset();
   }

   public void render(GuiGraphics guiGraphics, int mxPos, int myPos, float partialTicks) {
      super.render(guiGraphics, mxPos, myPos, partialTicks);
      this.search.autoComplete().render(guiGraphics, mxPos, myPos, partialTicks);
   }

   public Button resetButton() {
      return this.getAccess().controlling$getResetButton();
   }

   public void resetButton(Button button) {
      this.getAccess().controlling$setResetButton(button);
   }

   public void filterKeys() {
      this.filterKeys(this.search.getValue());
   }

   public void filterKeys(String lastSearch) {
      this.getKeyBindsList().children().clear();
      this.getKeyBindsList().setScrollAmount(0.0);
      if (lastSearch.isEmpty() && this.displayMode == DisplayMode.ALL && this.sortOrder == SortOrder.NONE) {
         this.getKeyBindsList().children().addAll(this.getCustomList().getAllEntries());
      } else {
         Predicate<Entry> extraPredicate = entry -> true;
         Consumer<List<Entry>> postConsumer = entries -> {};
         CustomList list = this.getCustomList();
         if (list instanceof NewKeyBindsList) {
            extraPredicate = this.displayMode.getPredicate();
            postConsumer = entries -> this.sortOrder.sort(entries);
         }

         list.children().addAll(ControllingConstants.SEARCHABLE_KEYBINDINGS.filterEntries(list.getAllEntries(), lastSearch, extraPredicate));
         postConsumer.accept(list.children());
      }
   }

   public boolean mouseClicked(double xpos, double ypos, int buttonId) {
      boolean b = super.mouseClicked(xpos, ypos, buttonId);
      if (!b && this.search.isFocused() && !this.search.autoComplete().mouseClicked(xpos, ypos, buttonId)) {
         this.search.setFocused(false);
         this.clearFocus();
         b = true;
      }

      return b;
   }

   public boolean mouseScrolled(double xpos, double ypos, double xDelta, double yDelta) {
      return this.search.autoComplete().mouseScrolled(xpos, ypos, xDelta, yDelta) ? true : super.mouseScrolled(xpos, ypos, xDelta, yDelta);
   }

   public boolean keyPressed(int key, int scancode, int mods) {
      if (!this.search.isFocused()
         && this.selectedKey == null
         && hasControlDown()
         && InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 70)) {
         this.search.setFocused(true);
         return true;
      } else if (this.search.isFocused() && key == 256) {
         this.search.setFocused(false);
         return true;
      } else if (this.selectedKey != null) {
         Services.PLATFORM.handleKeyPress(this, this.options, key, scancode, mods);
         return true;
      } else {
         return super.keyPressed(key, scancode, mods);
      }
   }

   private CustomList getCustomList() {
      if (this.getKeyBindsList() instanceof CustomList cl) {
         return cl;
      } else {
         throw new IllegalStateException(
            "keyBindsList('%s') was not an instance of CustomList! You're either too early or another mod is messing with things."
               .formatted(this.getKeyBindsList().getClass())
         );
      }
   }

   public KeyBindsList getKeyBindsList() {
      return this.getAccess().controlling$getKeyBindsList();
   }

   private void setKeyBindsList(KeyBindsList newList) {
      this.getAccess().controlling$setKeyBindsList(newList);
      this.repositionElements();
   }

   private AccessKeyBindsScreen getAccess() {
      return (AccessKeyBindsScreen)this;
   }

   private boolean canReset() {
      for (KeyMapping key : this.options.keyMappings) {
         if (!key.isDefault()) {
            return true;
         }
      }

      return false;
   }
}
