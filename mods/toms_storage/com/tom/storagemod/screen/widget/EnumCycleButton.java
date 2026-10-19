package com.tom.storagemod.screen.widget;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class EnumCycleButton<T extends Enum<T>> extends IconButton {
   private T state;
   public Function<T, Tooltip> tooltipFactory;
   public Function<T, ResourceLocation> icon;

   public EnumCycleButton(int x, int y, Component name, String prefix, T[] el, Consumer<T> stateUpdate) {
      super(x, y, name, null, onPress(el, stateUpdate));
      this.icon = e -> ResourceLocation.tryBuild("toms_storage", "icons/" + prefix + "_" + e.name().toLowerCase(Locale.ROOT));
   }

   protected MutableComponent createNarrationMessage() {
      return wrapDefaultNarrationMessage(
         this.name.copy().append(" ").append(Component.translatable("narrator.toms_storage.button_state." + this.state.name().toLowerCase(Locale.ROOT)))
      );
   }

   @Override
   public ResourceLocation getIcon() {
      return this.icon.apply(this.state);
   }

   public void setState(T state) {
      if (this.state != state && this.tooltipFactory != null) {
         this.setTooltip(this.tooltipFactory.apply(state));
      }

      this.state = state;
   }

   public T getState() {
      return this.state;
   }

   private static <T extends Enum<T>> OnPress onPress(T[] el, Consumer<T> stateUpdate) {
      return b -> stateUpdate.accept(el[(((EnumCycleButton)b).getState().ordinal() + el.length + (Screen.hasShiftDown() ? -1 : 1)) % el.length]);
   }
}
