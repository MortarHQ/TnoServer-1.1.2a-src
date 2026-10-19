package dev.ftb.mods.ftblibrary.ui;

import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class ToggleableButton extends SimpleButton {
   private Component enabledText;
   private Component disabledText;
   private boolean state;

   public ToggleableButton(Panel panel, boolean defaultState, Icon enabled, Icon disabled, ToggleableButton.ToggleableCallback toggleableCallback) {
      super(panel, Component.empty(), defaultState ? enabled : disabled, null);
      this.state = defaultState;
      this.setConsumer((widget, button) -> {
         this.state = !this.state;
         widget.setIcon(this.state ? enabled : disabled);
         this.updateTitle();
         toggleableCallback.onClicked(widget, this.state);
      });
      this.enabledText = Component.translatable("ftblibrary.gui.enabled").withStyle(ChatFormatting.GREEN);
      this.disabledText = Component.translatable("ftblibrary.gui.disabled").withStyle(ChatFormatting.RED);
      this.updateTitle();
   }

   public ToggleableButton(Panel panel, boolean defaultState, ToggleableButton.ToggleableCallback toggleableCallback) {
      this(panel, defaultState, Icons.VISIBILITY_SHOW, Icons.VISIBILITY_HIDE, toggleableCallback);
   }

   public Component getEnabledText() {
      return this.enabledText;
   }

   public ToggleableButton setEnabledText(Component enabledText) {
      this.enabledText = enabledText;
      this.updateTitle();
      return this;
   }

   public Component getDisabledText() {
      return this.disabledText;
   }

   public ToggleableButton setDisabledText(Component disabledText) {
      this.disabledText = disabledText;
      this.updateTitle();
      return this;
   }

   private void updateTitle() {
      this.setTitle(this.state ? this.enabledText : this.disabledText);
   }

   public interface ToggleableCallback {
      void onClicked(SimpleButton var1, boolean var2);
   }
}
