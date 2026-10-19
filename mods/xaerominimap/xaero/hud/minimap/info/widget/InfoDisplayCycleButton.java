package xaero.hud.minimap.info.widget;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import xaero.common.gui.GuiInfoDisplayEdit;
import xaero.lib.common.config.util.ConfigConstants;

public final class InfoDisplayCycleButton extends Button {
   private int currentIndex;

   private InfoDisplayCycleButton(int currentIndex, int x, int y, int w, int h, Component component, OnPress onPress) {
      super(x, y, w, h, component, onPress, DEFAULT_NARRATION);
      this.currentIndex = currentIndex;
   }

   public static final class Builder<T> {
      private int x;
      private int y;
      private int w;
      private int h;
      private List<T> values;
      private List<Component> valueNames;
      private GuiInfoDisplayEdit.MoveableEntry<T> entry;
      private Runnable onChange;
      private boolean includeNull;

      private Builder() {
      }

      public InfoDisplayCycleButton.Builder<T> setDefault() {
         this.setBounds(0, 0, 0, 0);
         this.setValues(null, null);
         this.setEntry(null);
         this.setIncludeNull(false);
         return this;
      }

      public InfoDisplayCycleButton.Builder<T> setBounds(int x, int y, int w, int h) {
         this.x = x;
         this.y = y;
         this.w = w;
         this.h = h;
         return this;
      }

      public InfoDisplayCycleButton.Builder<T> setValues(List<T> values, List<Component> valueNames) {
         if (values == null != (valueNames == null)) {
            throw new IllegalArgumentException();
         }

         if (values != null && values.size() != valueNames.size()) {
            throw new IllegalArgumentException();
         }

         this.values = values;
         this.valueNames = valueNames;
         return this;
      }

      public InfoDisplayCycleButton.Builder<T> setEntry(GuiInfoDisplayEdit.MoveableEntry<T> entry) {
         this.entry = entry;
         return this;
      }

      public InfoDisplayCycleButton.Builder<T> setOnChange(Runnable onChange) {
         this.onChange = onChange;
         return this;
      }

      public InfoDisplayCycleButton.Builder<T> setIncludeNull(boolean includeNull) {
         this.includeNull = includeNull;
         return this;
      }

      public InfoDisplayCycleButton build() {
         if (this.w != 0 && this.h != 0 && this.values != null && this.entry != null) {
            List<T> allValues = this.values;
            List<Component> allValueNames = this.valueNames;
            if (this.includeNull) {
               allValues = new ArrayList<>(this.values);
               allValueNames = new ArrayList<>(this.valueNames);
               allValues.add(0, null);
               allValueNames.add(0, ConfigConstants.UNSPECIFIED);
            }

            int currentStateIndex = allValues.indexOf(this.entry.getState());
            if (currentStateIndex < 0) {
               this.entry.setState(allValues.get(0));
               currentStateIndex = 0;
            }

            List<T> finalValues = allValues;
            List<Component> finalValueNames = allValueNames;
            GuiInfoDisplayEdit.MoveableEntry<T> finalEntry = this.entry;
            Runnable finalOnChange = this.onChange;
            OnPress action = b -> {
               InfoDisplayCycleButton cycleButton = (InfoDisplayCycleButton)b;
               if (Screen.hasShiftDown()) {
                  cycleButton.currentIndex--;
                  if (cycleButton.currentIndex < 0) {
                     cycleButton.currentIndex = finalValues.size() - 1;
                  }
               } else {
                  cycleButton.currentIndex = (cycleButton.currentIndex + 1) % finalValues.size();
               }

               finalEntry.setState(finalValues.get(cycleButton.currentIndex));
               cycleButton.setMessage(finalValueNames.get(cycleButton.currentIndex));
               if (finalOnChange != null) {
                  finalOnChange.run();
               }
            };
            return new InfoDisplayCycleButton(currentStateIndex, this.x, this.y, this.w, this.h, allValueNames.get(currentStateIndex), action);
         } else {
            throw new IllegalStateException();
         }
      }

      public static <T> InfoDisplayCycleButton.Builder<T> begin() {
         return new InfoDisplayCycleButton.Builder<T>().setDefault();
      }
   }
}
