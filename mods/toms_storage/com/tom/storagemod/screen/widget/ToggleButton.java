package com.tom.storagemod.screen.widget;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ToggleButton extends IconButton {
   private ResourceLocation iconOn;
   private boolean state;
   private Tooltip ttTrue;
   private Tooltip ttFalse;

   public static ToggleButton.Builder builder(int x, int y) {
      return new ToggleButton.Builder(x, y);
   }

   protected ToggleButton(int x, int y, Component name, ResourceLocation iconOff, ResourceLocation iconOn, BooleanConsumer pressable) {
      super(x, y, name, iconOff, onPress(pressable));
      this.iconOn = iconOn;
   }

   public void setState(boolean state) {
      this.state = state;
      super.setTooltip(state ? this.ttTrue : this.ttFalse);
   }

   public boolean getState() {
      return this.state;
   }

   @Override
   public ResourceLocation getIcon() {
      return this.state ? this.iconOn : this.icon;
   }

   private static OnPress onPress(BooleanConsumer stateUpdate) {
      return b -> stateUpdate.accept(!((ToggleButton)b).state);
   }

   public void setTooltip(Tooltip p_259796_) {
      this.setTooltip(p_259796_, p_259796_);
   }

   public void setTooltip(Tooltip ttFalse, Tooltip ttTrue) {
      this.ttFalse = ttFalse;
      this.ttTrue = ttTrue;
      super.setTooltip(this.state ? ttTrue : ttFalse);
   }

   public static class Builder {
      private int x;
      private int y;
      private Component name = Component.empty();
      private ResourceLocation on;
      private ResourceLocation off;

      public Builder(int x, int y) {
         this.x = x;
         this.y = y;
      }

      public ToggleButton.Builder name(Component name) {
         this.name = name;
         return this;
      }

      public ToggleButton.Builder iconOn(ResourceLocation icon) {
         this.on = icon;
         return this;
      }

      public ToggleButton.Builder iconOff(ResourceLocation icon) {
         this.off = icon;
         return this;
      }

      public ToggleButton build(BooleanConsumer pressable) {
         return new ToggleButton(this.x, this.y, this.name, this.off, this.on, pressable);
      }
   }
}
