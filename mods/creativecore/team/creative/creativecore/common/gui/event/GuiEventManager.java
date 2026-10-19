package team.creative.creativecore.common.gui.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GuiEventManager {
   private final List<GuiEventManager.GuiEventHandler> handlers = new ArrayList<>();

   public void raiseEvent(GuiEvent event) {
      for (GuiEventManager.GuiEventHandler handler : this.handlers) {
         handler.react(event);
         if (event.isCanceled()) {
            return;
         }
      }
   }

   public <T extends GuiEvent> void registerEvent(Class<T> clazz, Consumer<T> action) {
      this.handlers.add(new GuiEventManager.GuiEventHandler<>(clazz, action));
   }

   public void clear() {
      this.handlers.clear();
   }

   public static class GuiEventHandler<T extends GuiEvent> {
      private final Class<T> clazz;
      private final Consumer<T> action;

      public GuiEventHandler(Class<T> clazz, Consumer<T> action) {
         this.clazz = clazz;
         this.action = action;
      }

      public void react(GuiEvent event) {
         if (this.clazz.isInstance(event)) {
            this.action.accept((T)event);
         }
      }
   }
}
