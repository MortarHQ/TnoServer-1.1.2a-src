package com.nododiiiii.ponderer.ui;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import com.nododiiiii.ponderer.ModKeyBindings;
import com.nododiiiii.ponderer.ui.catnip.AbstractDeclarativeListScreen;
import com.nododiiiii.ponderer.ui.catnip.EntryTextSupport;
import com.nododiiiii.ponderer.ui.catnip.PonderIconStencils;
import com.nododiiiii.ponderer.ui.catnip.SearchableListEntry;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList.Entry;
import net.createmod.catnip.config.ui.ConfigScreenList.LabeledEntry;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.createmod.ponder.enums.PonderGuiTextures;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class PondererKeyBindingsScreen extends AbstractDeclarativeListScreen {
   private static final int BINDING_BUTTON_MIN_WIDTH = 110;
   private static final int RESET_BUTTON_WIDTH = 20;
   @Nullable
   private ModKeyBindings.ManagedBinding listeningBinding;

   public PondererKeyBindingsScreen(@Nullable Screen parent) {
      super(parent, "ponderer.ui.scope.editor", "ponderer.ui.function_page.keybindings", 300);
   }

   @Override
   protected void init() {
      super.init();
      this.configureSidebarButtons();
      if (this.listeningBinding == null) {
         this.setInfoMessage(UIText.of("ponderer.ui.keybindings.instructions"));
      }
   }

   @Override
   protected void collectEntries(List<Entry> entries) {
      for (ModKeyBindings.ManagedBinding binding : ModKeyBindings.managedBindings()) {
         entries.add(new PondererKeyBindingsScreen.KeyBindingEntry(binding));
      }
   }

   @Override
   protected boolean hasUnsavedChanges() {
      return false;
   }

   @Override
   protected int getUnsavedChangeCount() {
      return 0;
   }

   @Override
   protected boolean saveEdits() {
      return false;
   }

   @Override
   protected void discardEdits() {
   }

   @Override
   protected boolean isSaveButtonActive() {
      return false;
   }

   @Override
   protected boolean isDiscardButtonActive() {
      return this.hasAnyNonDefaultBinding();
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.listeningBinding != null) {
         if (keyCode == 256) {
            this.listeningBinding = null;
            this.setInfoMessage(UIText.of("ponderer.ui.keybindings.status.cancelled"));
            return true;
         } else if (keyCode != 259 && keyCode != 261) {
            this.applyBinding(this.listeningBinding, InputConstants.getKey(keyCode, scanCode));
            return true;
         } else {
            this.applyBinding(this.listeningBinding, InputConstants.UNKNOWN);
            return true;
         }
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public boolean charTyped(char codePoint, int modifiers) {
      return this.listeningBinding != null ? true : super.charTyped(codePoint, modifiers);
   }

   private void beginListening(ModKeyBindings.ManagedBinding binding) {
      this.listeningBinding = binding;
      this.setInfoMessage(UIText.of("ponderer.ui.keybindings.status.listening", actionText(binding)));
   }

   private void applyBinding(ModKeyBindings.ManagedBinding binding, Key key) {
      binding.mapping().setKey(key);
      this.saveBindings();
      this.listeningBinding = null;
      if (InputConstants.UNKNOWN.equals(key)) {
         this.setInfoMessage(UIText.of("ponderer.ui.keybindings.status.cleared", actionText(binding)));
      } else {
         this.setInfoMessage(UIText.of("ponderer.ui.keybindings.status.bound", actionText(binding), keyText(binding.mapping())));
      }
   }

   private void resetAllBindings() {
      boolean changed = false;

      for (ModKeyBindings.ManagedBinding binding : ModKeyBindings.managedBindings()) {
         if (!binding.isDefault()) {
            binding.resetToDefault();
            changed = true;
         }
      }

      if (!changed) {
         this.setInfoMessage(UIText.of("ponderer.ui.keybindings.instructions"));
      } else {
         this.saveBindings();
         this.listeningBinding = null;
         this.setInfoMessage(UIText.of("ponderer.ui.keybindings.status.reset_all"));
      }
   }

   private boolean hasAnyNonDefaultBinding() {
      for (ModKeyBindings.ManagedBinding binding : ModKeyBindings.managedBindings()) {
         if (!binding.isDefault()) {
            return true;
         }
      }

      return false;
   }

   private void resetBinding(ModKeyBindings.ManagedBinding binding) {
      if (!binding.isDefault()) {
         binding.resetToDefault();
         this.saveBindings();
         this.listeningBinding = null;
         this.setInfoMessage(UIText.of("ponderer.ui.keybindings.status.reset_one", actionText(binding), defaultKeyText(binding)));
      }
   }

   private void configureSidebarButtons() {
      if (this.saveChanges != null) {
         this.saveChanges.visible = false;
         this.saveChanges.active = false;
         this.saveChanges.updateGradientFromState();
      }

      if (this.discardChanges != null) {
         this.discardChanges.visible = true;
         this.discardChanges.withCallback(this::resetAllBindings);
         PonderIconStencils.attach(this.discardChanges, PonderIconStencils.centered(PonderGuiTextures.ICON_CONFIG_RESET));
         this.discardChanges.getToolTip().clear();
         this.discardChanges.getToolTip().add(Component.literal(UIText.of("ponderer.ui.keybindings.reset_all")));
         this.discardChanges.getToolTip().add(Component.literal(UIText.of("ponderer.ui.keybindings.reset_all.tooltip")));
         this.discardChanges.active = this.hasAnyNonDefaultBinding();
         this.discardChanges.updateGradientFromState();
      }
   }

   private String bindingButtonLabel(ModKeyBindings.ManagedBinding binding) {
      return binding.equals(this.listeningBinding) ? UIText.of("ponderer.ui.keybindings.listening_button") : keyText(binding.mapping());
   }

   private String bindingButtonTooltip(ModKeyBindings.ManagedBinding binding) {
      return binding.equals(this.listeningBinding)
         ? UIText.of("ponderer.ui.keybindings.rebind.tooltip_listening")
         : UIText.of("ponderer.ui.keybindings.rebind.tooltip", defaultKeyText(binding));
   }

   private int bindingButtonColor(ModKeyBindings.ManagedBinding binding) {
      if (binding.equals(this.listeningBinding)) {
         return -8323073;
      } else {
         return this.hasConflict(binding) ? -16288 : 16777215;
      }
   }

   private boolean hasConflict(ModKeyBindings.ManagedBinding binding) {
      if (binding.mapping().isUnbound()) {
         return false;
      }

      for (ModKeyBindings.ManagedBinding other : ModKeyBindings.managedBindings()) {
         if (other != binding && binding.mapping().same(other.mapping())) {
            return true;
         }
      }

      return false;
   }

   private static String actionText(ModKeyBindings.ManagedBinding binding) {
      return Component.translatable(binding.translationKey()).getString();
   }

   private static String keyText(KeyMapping mapping) {
      return mapping.getTranslatedKeyMessage().getString();
   }

   private static String defaultKeyText(ModKeyBindings.ManagedBinding binding) {
      return binding.defaultKey().getDisplayName().getString();
   }

   private void saveBindings() {
      KeyMapping.resetMapping();
      Minecraft.getInstance().options.save();
   }

   private final class KeyBindingEntry extends LabeledEntry implements SearchableListEntry {
      private static final int BINDING_VISUAL_INSET = 4;
      private static final int CONTROL_GAP = EntryTextSupport.rightControlGap();
      private final ModKeyBindings.ManagedBinding binding;
      private final String searchText;
      private final BoxWidget bindingButton;
      private final BoxWidget resetButton;

      private KeyBindingEntry(ModKeyBindings.ManagedBinding binding) {
         super(PondererKeyBindingsScreen.actionText(binding));
         this.binding = binding;
         this.searchText = PondererKeyBindingsScreen.actionText(binding).toLowerCase(Locale.ROOT);
         this.getLabelTooltip().add(Component.literal(PondererKeyBindingsScreen.actionText(binding)));
         this.getLabelTooltip().add(Component.literal(UIText.of("ponderer.ui.keybindings.rebind.tooltip", PondererKeyBindingsScreen.defaultKeyText(binding))));
         this.bindingButton = (BoxWidget)new BoxWidget(0, 0, 120, 16).withCallback(() -> PondererKeyBindingsScreen.this.beginListening(binding));
         this.resetButton = (BoxWidget)new BoxWidget(0, 0, 20, 16)
            .withPadding(2.0F, 2.0F)
            .withCallback(() -> PondererKeyBindingsScreen.this.resetBinding(binding));
         PonderIconStencils.attach(this.resetButton, PonderIconStencils.centered(PonderGuiTextures.ICON_CONFIG_RESET));
         this.resetButton.active = !binding.isDefault();
         this.resetButton.updateGradientFromState();
         this.listeners.add(this.bindingButton);
         this.listeners.add(this.resetButton);
      }

      @Override
      public boolean matchesQuery(String query) {
         return this.searchText.contains(query);
      }

      @Override
      public void highlightEntry() {
         this.annotations.put("highlight", ":)");
      }

      protected int getLabelWidth(int totalWidth) {
         return EntryTextSupport.compactLabelWidth(totalWidth);
      }

      public void tick() {
         super.tick();
         this.bindingButton.tick();
         PondererKeyBindingsScreen.this.updateButtonState(this.resetButton, !this.binding.isDefault());
         this.resetButton.tick();
      }

      public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
         super.render(graphics, index, y, x, width, height, mouseX, mouseY, hovered, partialTicks);
         this.refreshButtonTooltips();
         int labelWidth = this.getLabelWidth(width);
         EntryTextSupport.AlignedControlBounds controlBounds = EntryTextSupport.rightAlignedControlBounds(
            x, width, labelWidth, EntryTextSupport.halfWidthControlScale(), 110 + CONTROL_GAP + 20
         );
         int buttonY = y + 10;
         int buttonHeight = Math.max(16, height - 20);
         int resetX = controlBounds.right() - 20;
         this.resetButton.setX(resetX);
         this.resetButton.setY(buttonY);
         this.resetButton.setWidth(20);
         this.resetButton.setHeight(buttonHeight);
         PondererKeyBindingsScreen.this.updateButtonState(this.resetButton, !this.binding.isDefault());
         this.resetButton.render(graphics, mouseX, mouseY, partialTicks);
         int bindingWidth = Math.max(110, resetX - CONTROL_GAP - controlBounds.x());
         int bindingVisualX = controlBounds.x();
         int bindingWidgetX = bindingVisualX + 4;
         int bindingWidgetWidth = Math.max(1, bindingWidth - 8);
         this.bindingButton.setX(bindingWidgetX);
         this.bindingButton.setY(buttonY);
         this.bindingButton.setWidth(bindingWidgetWidth);
         this.bindingButton.setHeight(buttonHeight);
         this.bindingButton.render(graphics, mouseX, mouseY, partialTicks);
         graphics.drawCenteredString(
            Minecraft.getInstance().font,
            PondererKeyBindingsScreen.this.bindingButtonLabel(this.binding),
            this.bindingButton.getX() + this.bindingButton.getWidth() / 2,
            this.bindingButton.getY() + (this.bindingButton.getHeight() - 8) / 2,
            PondererKeyBindingsScreen.this.bindingButtonColor(this.binding)
         );
      }

      private void refreshButtonTooltips() {
         this.bindingButton.getToolTip().clear();
         this.bindingButton.getToolTip().add(Component.literal(PondererKeyBindingsScreen.this.bindingButtonTooltip(this.binding)));
         this.resetButton.getToolTip().clear();
         this.resetButton
            .getToolTip()
            .add(Component.literal(UIText.of("ponderer.ui.keybindings.reset_one.tooltip", PondererKeyBindingsScreen.defaultKeyText(this.binding))));
      }
   }
}
