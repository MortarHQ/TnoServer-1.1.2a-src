package mezz.jei.neoforge.startup;

import mezz.jei.gui.events.GuiEventHandler;
import mezz.jei.gui.input.ClientInputHandler;
import mezz.jei.gui.input.UserInput;
import mezz.jei.gui.startup.JeiEventHandlers;
import mezz.jei.neoforge.events.RuntimeEventSubscriptions;
import mezz.jei.neoforge.input.ForgeUserInput;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.neoforged.neoforge.client.event.ContainerScreenEvent.Render.Foreground;
import net.neoforged.neoforge.client.event.ScreenEvent.Opening;
import net.neoforged.neoforge.client.event.ScreenEvent.RenderInventoryMobEffects;
import net.neoforged.neoforge.client.event.ScreenEvent.Init.Post;
import net.neoforged.neoforge.client.event.ScreenEvent.KeyPressed.Pre;

public class EventRegistration {
   public static void registerEvents(RuntimeEventSubscriptions subscriptions, JeiEventHandlers eventHandlers) {
      ClientInputHandler clientInputHandler = eventHandlers.clientInputHandler();
      registerClientInputHandler(subscriptions, clientInputHandler);
      GuiEventHandler guiEventHandler = eventHandlers.guiEventHandler();
      registerGuiHandler(subscriptions, guiEventHandler);
   }

   private static void registerClientInputHandler(RuntimeEventSubscriptions subscriptions, ClientInputHandler handler) {
      subscriptions.register(Post.class, event -> handler.onInitGui());
      subscriptions.register(Pre.class, event -> {
         Screen screen = event.getScreen();
         UserInput input = ForgeUserInput.fromEvent(event);
         if (handler.onKeyboardKeyPressedPre(screen, input)) {
            event.setCanceled(true);
         }
      });
      subscriptions.register(net.neoforged.neoforge.client.event.ScreenEvent.KeyPressed.Post.class, event -> {
         Screen screen = event.getScreen();
         UserInput input = ForgeUserInput.fromEvent(event);
         if (handler.onKeyboardKeyPressedPost(screen, input)) {
            event.setCanceled(true);
         }
      });
      subscriptions.register(net.neoforged.neoforge.client.event.ScreenEvent.CharacterTyped.Pre.class, event -> {
         Screen screen = event.getScreen();
         char codePoint = event.getCodePoint();
         int modifiers = event.getModifiers();
         if (handler.onKeyboardCharTypedPre(screen, codePoint, modifiers)) {
            event.setCanceled(true);
         }
      });
      subscriptions.register(net.neoforged.neoforge.client.event.ScreenEvent.CharacterTyped.Post.class, event -> {
         Screen screen = event.getScreen();
         char codePoint = event.getCodePoint();
         int modifiers = event.getModifiers();
         handler.onKeyboardCharTypedPost(screen, codePoint, modifiers);
      });
      subscriptions.register(
         net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonPressed.Pre.class, event -> ForgeUserInput.fromEvent(event).ifPresent(input -> {
            Screen screen = event.getScreen();
            if (handler.onGuiMouseClicked(screen, input)) {
               event.setCanceled(true);
            }
         })
      );
      subscriptions.register(
         net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonReleased.Pre.class, event -> ForgeUserInput.fromEvent(event).ifPresent(input -> {
            Screen screen = event.getScreen();
            if (handler.onGuiMouseReleased(screen, input)) {
               event.setCanceled(true);
            }
         })
      );
      subscriptions.register(net.neoforged.neoforge.client.event.ScreenEvent.MouseScrolled.Pre.class, event -> {
         double mouseX = event.getMouseX();
         double mouseY = event.getMouseY();
         double scrollDeltaX = event.getScrollDeltaX();
         double scrollDeltaY = event.getScrollDeltaY();
         if (handler.onGuiMouseScroll(mouseX, mouseY, scrollDeltaX, scrollDeltaY)) {
            event.setCanceled(true);
         }
      });
   }

   public static void registerGuiHandler(RuntimeEventSubscriptions subscriptions, GuiEventHandler guiEventHandler) {
      subscriptions.register(Post.class, event -> {
         Screen screen = event.getScreen();
         guiEventHandler.onGuiInit(screen);
      });
      subscriptions.register(Opening.class, event -> {
         Screen screen = event.getScreen();
         guiEventHandler.onGuiOpen(screen);
      });
      subscriptions.register(Foreground.class, event -> {
         AbstractContainerScreen<?> containerScreen = event.getContainerScreen();
         GuiGraphics guiGraphics = event.getGuiGraphics();
         int mouseX = event.getMouseX();
         int mouseY = event.getMouseY();
         guiEventHandler.onDrawForeground(containerScreen, guiGraphics, mouseX, mouseY);
      });
      subscriptions.register(net.neoforged.neoforge.client.event.ScreenEvent.Render.Post.class, event -> {
         Screen screen = event.getScreen();
         GuiGraphics guiGraphics = event.getGuiGraphics();
         int mouseX = event.getMouseX();
         int mouseY = event.getMouseY();
         guiEventHandler.onDrawScreenPost(screen, guiGraphics, mouseX, mouseY);
      });
      subscriptions.register(RenderInventoryMobEffects.class, event -> {
         if (guiEventHandler.renderCompactPotionIndicators()) {
            event.setCompact(true);
         }
      });
   }
}
