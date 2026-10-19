package com.tom.storagemod.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class PopupMenuManager {
   private final Screen screen;
   private int x;
   private int y;
   private List<PopupMenuManager.PopupElement> menu;
   private int selected;

   public PopupMenuManager(Screen screen) {
      this.screen = screen;
   }

   public void open(double x, double y, PopupMenuManager.PopupElement... menu) {
      this.menu = Arrays.asList(menu);
      this.x = (int)x;
      this.y = (int)y;
      this.selected = -1;
   }

   public void replace(PopupMenuManager.PopupElement... menu) {
      this.menu = Arrays.asList(menu);
      this.selected = -1;
   }

   public boolean render(GuiGraphics g, Font font, int pMouseX, int pMouseY) {
      if (this.menu != null) {
         g.renderTooltip(
            font,
            IntStream.range(0, this.menu.size())
               .mapToObj(i -> this.menu.get(i).getHoveredText(pMouseX - this.x, pMouseY - this.y - i * 10, this.selected == i))
               .map(Component::getVisualOrderText)
               .toList(),
            this.x - 12,
            this.y + 12
         );
      }

      return this.menu == null;
   }

   private static boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
      return pMouseX >= pX - 1 && pMouseX < pX + pWidth + 1 && pMouseY >= pY - 1 && pMouseY < pY + pHeight + 1;
   }

   public boolean mouseClick(double pMouseX, double pMouseY, int pButton) {
      if (this.menu != null) {
         for (int i = 0; i < this.menu.size(); i++) {
            if (isHovering(this.x, this.y + i * 10, 100, 8, pMouseX, pMouseY)) {
               this.menu.get(i).onClick(pMouseX - this.x, pMouseY - this.y - i * 10, pButton);
               this.selected = i;
               return true;
            }
         }

         this.menu = null;
         return true;
      } else {
         return false;
      }
   }

   public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
      if (this.menu != null) {
         if (this.selected != -1 && this.menu.get(this.selected).keyPressed(pKeyCode, pScanCode, pModifiers)) {
            return true;
         }

         if (pKeyCode == 264) {
            this.selected = (this.selected + 1) % this.menu.size();
         } else if (pKeyCode == 265) {
            this.selected = (this.selected + this.menu.size() - 1) % this.menu.size();
         } else if (pKeyCode == 256) {
            this.menu = null;
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean charTyped(char pCodePoint, int pModifiers) {
      return this.menu != null && this.selected != -1 && this.menu.get(this.selected).charTyped(pCodePoint, pModifiers);
   }

   public void close() {
      this.menu = null;
   }

   public static class ButtonElement implements PopupMenuManager.PopupElement {
      private final Supplier<MutableComponent> text;
      private final Runnable action;

      public ButtonElement(Supplier<MutableComponent> text, Runnable action) {
         this.text = text;
         this.action = action;
      }

      @Override
      public MutableComponent getText() {
         return this.text.get();
      }

      @Override
      public void activate() {
         this.action.run();
      }
   }

   public interface PopupElement {
      MutableComponent getText();

      void activate();

      default void onClick(double pMouseX, double pMouseY, int pButton) {
         this.activate();
      }

      default boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
         if (pKeyCode != 257 && pKeyCode != 335) {
            return false;
         }

         this.activate();
         return true;
      }

      default boolean charTyped(char pCodePoint, int pModifiers) {
         return false;
      }

      default MutableComponent getHoveredText(int pMouseX, int pMouseY, boolean selected) {
         return this.getText().withStyle(!PopupMenuManager.isHovering(0, 0, 100, 8, pMouseX, pMouseY) && !selected ? ChatFormatting.GRAY : ChatFormatting.AQUA);
      }
   }

   public static class TextFieldElement implements PopupMenuManager.PopupElement {
      private final Supplier<MutableComponent> text;
      private final Consumer<String> action;
      private boolean activated;
      private EditBox box;
      private String value;

      public TextFieldElement(Supplier<MutableComponent> text, Consumer<String> action, Font font, String value) {
         this.text = text;
         this.action = action;
         this.box = new EditBox(font, 0, 0, 0, 0, null);
         this.value = value;
         this.box.setValue(value);
         this.box.setCanLoseFocus(false);
         this.box.setFocused(true);
      }

      @Override
      public MutableComponent getText() {
         if (this.activated) {
            StringBuilder sb = new StringBuilder(this.box.getValue());
            sb.insert(this.box.getCursorPosition(), '_');
            return Component.translatable(
               "tooltip.toms_trading_network.textfield", new Object[]{this.text.get(), Component.literal(sb.toString()).withStyle(ChatFormatting.YELLOW)}
            );
         } else {
            return Component.translatable("tooltip.toms_trading_network.textfield", new Object[]{this.text.get(), Component.literal(this.box.getValue())});
         }
      }

      @Override
      public void activate() {
         if (this.activated) {
            this.value = this.box.getValue();
            this.action.accept(this.value);
         }

         this.activated = !this.activated;
      }

      @Override
      public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
         if (this.activated && pKeyCode == 256) {
            this.box.setValue(this.value);
            this.activated = false;
            return true;
         } else if (PopupMenuManager.PopupElement.super.keyPressed(pKeyCode, pScanCode, pModifiers)) {
            return true;
         } else {
            return this.activated ? this.box.keyPressed(pKeyCode, pScanCode, pModifiers) : false;
         }
      }

      @Override
      public boolean charTyped(char pCodePoint, int pModifiers) {
         return this.activated ? this.box.charTyped(pCodePoint, pModifiers) : false;
      }
   }
}
