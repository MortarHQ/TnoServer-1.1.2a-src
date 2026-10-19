package org.anti_ad.mc.common.forge;

import net.minecraft.client.gui.screens.Screen;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.event.InputEvent.Key;
import net.neoforged.neoforge.client.event.InputEvent.MouseScrollingEvent;
import net.neoforged.neoforge.client.event.ScreenEvent.KeyPressed.Post;
import net.neoforged.neoforge.client.event.ScreenEvent.KeyPressed.Pre;
import org.anti_ad.mc.common.input.GlobalInputHandler;
import org.anti_ad.mc.common.input.GlobalScreenEventListener;
import org.anti_ad.mc.common.vanilla.Vanilla;

public class CommonForgeEventHandler {
   private int lastMods = 0;

   @SubscribeEvent
   public void onKeyLast(Key event) {
      if (Vanilla.INSTANCE.screen() == null) {
         GlobalInputHandler.INSTANCE.onKey(event.getKey(), event.getScanCode(), event.getAction(), event.getModifiers(), true, 0L);
      }
   }

   @SubscribeEvent
   public void onKeyPressed(Pre event) {
      this.onScreenKey(event.getKeyCode(), event.getScanCode(), 1, event.getModifiers(), event);
      if (!event.isCanceled()) {
         event.setCanceled(GlobalScreenEventListener.INSTANCE.onKeyPressed(event.getKeyCode(), event.getScanCode(), event.getModifiers(), true));
      }
   }

   @SubscribeEvent
   public void onKeyRelease(net.neoforged.neoforge.client.event.ScreenEvent.KeyReleased.Pre event) {
      this.onScreenKey(event.getKeyCode(), event.getScanCode(), 0, event.getModifiers(), event);
      if (!event.isCanceled()) {
         event.setCanceled(GlobalScreenEventListener.INSTANCE.onKeyReleased(event.getKeyCode(), event.getScanCode(), event.getModifiers(), true));
      }
   }

   @SubscribeEvent
   public void onKeyPressedPost(Post event) {
      event.setCanceled(GlobalScreenEventListener.INSTANCE.onKeyPressed(event.getKeyCode(), event.getScanCode(), event.getModifiers(), false));
   }

   @SubscribeEvent
   public void onKeyReleasePost(net.neoforged.neoforge.client.event.ScreenEvent.KeyReleased.Post event) {
      event.setCanceled(GlobalScreenEventListener.INSTANCE.onKeyReleased(event.getKeyCode(), event.getScanCode(), event.getModifiers(), false));
   }

   private void onScreenKey(int key, int scanCode, int action, int modifiers, ScreenEvent event) {
      Screen lastScreen = Vanilla.INSTANCE.screen();
      long handle = Vanilla.INSTANCE.window().getWindow();
      boolean result = GlobalInputHandler.INSTANCE.onKey(key, scanCode, action, modifiers, true, handle);
   }

   @SubscribeEvent
   public void onMouseButtonLast(net.neoforged.neoforge.client.event.InputEvent.MouseButton.Post event) {
      if (Vanilla.INSTANCE.screen() == null) {
         GlobalInputHandler.INSTANCE.onMouseButton(event.getButton(), event.getAction(), event.getModifiers());
      }
   }

   @SubscribeEvent
   public void onMouseClicked(net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonPressed.Pre event) {
      this.onScreenMouseButton(event.getButton(), 1, this.lastMods, event);
      if (!event.isCanceled()) {
         event.setCanceled(GlobalScreenEventListener.INSTANCE.onMouseClicked(event.getMouseX(), event.getMouseY(), event.getButton(), true));
      }
   }

   @SubscribeEvent
   public void onMouseReleased(net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonReleased.Pre event) {
      this.onScreenMouseButton(event.getButton(), 0, this.lastMods, event);
      if (!event.isCanceled()) {
         event.setCanceled(GlobalScreenEventListener.INSTANCE.onMouseReleased(event.getMouseX(), event.getMouseY(), event.getButton(), true));
      }
   }

   @SubscribeEvent
   public void onMouseClickedPost(net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonPressed.Post event) {
      GlobalScreenEventListener.INSTANCE.onMouseClicked(event.getMouseX(), event.getMouseY(), event.getButton(), false);
   }

   @SubscribeEvent
   public void onMouseReleasedPost(net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonReleased.Post event) {
      GlobalScreenEventListener.INSTANCE.onMouseReleased(event.getMouseX(), event.getMouseY(), event.getButton(), false);
   }

   private void onScreenMouseButton(int button, int action, int mods, ICancellableEvent event) {
      Screen lastScreen = Vanilla.INSTANCE.screen();
      boolean result = GlobalInputHandler.INSTANCE.onMouseButton(button, action, mods);
      event.setCanceled(result || lastScreen != Vanilla.INSTANCE.screen());
   }

   @SubscribeEvent
   public void onRawMouse(net.neoforged.neoforge.client.event.InputEvent.MouseButton.Pre event) {
      this.lastMods = event.getModifiers();
   }

   @SubscribeEvent
   public void onMouseScroll(MouseScrollingEvent event) {
      double deltaX = event.getScrollDeltaX();
      double deltaY = event.getScrollDeltaY();
      if (deltaX != 0.0) {
         event.setCanceled(processHorizontalScrollEvent(deltaX));
      }

      if (deltaY != 0.0) {
         event.setCanceled(processVerticalScrollEvent(deltaY));
      }
   }

   @SubscribeEvent
   public void onGuiMouseScroll(net.neoforged.neoforge.client.event.ScreenEvent.MouseScrolled.Pre event) {
      double deltaX = event.getScrollDeltaX();
      double deltaY = event.getScrollDeltaY();
      if (deltaX != 0.0) {
         Screen lastScreen = Vanilla.INSTANCE.screen();
         boolean result = processHorizontalScrollEvent(deltaX);
         result = processHorizontalScrollGUIEvent(deltaX, event.getMouseX(), event.getMouseY()) || result;
         event.setCanceled(result || lastScreen != Vanilla.INSTANCE.screen());
      }

      if (deltaY != 0.0) {
         Screen lastScreen = Vanilla.INSTANCE.screen();
         boolean result = processVerticalScrollEvent(deltaY);
         result = processVerticalScrollGUIEvent(deltaY, event.getMouseX(), event.getMouseY()) || result;
         event.setCanceled(result || lastScreen != Vanilla.INSTANCE.screen());
      }
   }

   private static boolean processVerticalScrollGUIEvent(double delta, double mouseX, double mouseY) {
      int button;
      if (delta > 0.0) {
         button = -10000;
      } else {
         button = -10001;
      }

      boolean result = GlobalScreenEventListener.INSTANCE.onMouseClicked(mouseX, mouseY, button, true);
      return GlobalScreenEventListener.INSTANCE.onMouseReleased(mouseX, mouseY, button, true) || result;
   }

   private static boolean processHorizontalScrollGUIEvent(double delta, double mouseX, double mouseY) {
      int button;
      if (delta > 0.0) {
         button = -10002;
      } else {
         button = -10003;
      }

      boolean result = GlobalScreenEventListener.INSTANCE.onMouseClicked(mouseX, mouseY, button, true);
      return GlobalScreenEventListener.INSTANCE.onMouseReleased(mouseX, mouseY, button, true) || result;
   }

   private static boolean processVerticalScrollEvent(double delta) {
      int button;
      if (delta > 0.0) {
         button = -10000;
      } else {
         button = -10001;
      }

      boolean result = GlobalInputHandler.INSTANCE.onMouseButton(button, 1, 0);
      return GlobalInputHandler.INSTANCE.onMouseButton(button, 0, 0) | result;
   }

   private static boolean processHorizontalScrollEvent(double delta) {
      int button;
      if (delta > 0.0) {
         button = -10002;
      } else {
         button = -10003;
      }

      boolean result = GlobalInputHandler.INSTANCE.onMouseButton(button, 1, 0);
      return GlobalInputHandler.INSTANCE.onMouseButton(button, 0, 0) | result;
   }
}
