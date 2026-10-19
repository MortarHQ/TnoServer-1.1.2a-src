package mezz.jei.neoforge.startup;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import mezz.jei.neoforge.events.PermanentEventSubscriptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.ScreenEvent.Init.Pre;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class StartEventObserver implements ResourceManagerReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Set<Class<? extends Event>> requiredEvents = Set.of(TagsUpdatedEvent.class, RecipesUpdatedEvent.class);
   private final Set<Class<? extends Event>> observedEvents = new HashSet<>();
   private final Runnable startRunnable;
   private final Runnable stopRunnable;
   private WeakReference<Connection> currentConnection = new WeakReference<>(null);
   private StartEventObserver.State state = StartEventObserver.State.LISTENING;

   public StartEventObserver(Runnable startRunnable, Runnable stopRunnable) {
      this.startRunnable = startRunnable;
      this.stopRunnable = stopRunnable;
   }

   public void register(PermanentEventSubscriptions subscriptions) {
      requiredEvents.forEach(eventClass -> subscriptions.register((Class<? extends Event>)eventClass, this::onEvent));
      subscriptions.register(LoggingOut.class, event -> {
         if (event.getPlayer() != null) {
            logReceivedEvent(event);
            this.transitionState(StartEventObserver.State.LISTENING);
         }
      });
      subscriptions.register(
         Pre.class,
         event -> {
            if (this.state != StartEventObserver.State.JEI_STARTED) {
               Screen screen = event.getScreen();
               Minecraft minecraft = screen.getMinecraft();
               if (screen instanceof AbstractContainerScreen && minecraft != null && minecraft.player != null) {
                  String missingEventsString = requiredEvents.stream()
                     .filter(e -> !this.observedEvents.contains(e))
                     .map(Class::getName)
                     .sorted()
                     .collect(Collectors.joining(", ", "[", "]"));
                  String requiredEventsString = requiredEvents.stream().map(Class::getName).sorted().collect(Collectors.joining(", ", "[", "]"));
                  LOGGER.error(
                     "A Screen is opening but JEI hasn't started yet.\nNormally, JEI is started after these event have fired: {}.\nSomething has caused one or more of these events to fail, so JEI is starting very late.\nMissing events: {}",
                     requiredEventsString,
                     missingEventsString
                  );
                  this.transitionState(StartEventObserver.State.LISTENING);
                  this.transitionState(StartEventObserver.State.JEI_STARTED);
               }
            }
         }
      );
   }

   private <T extends Event> void onEvent(T event) {
      Connection observingConnection = this.currentConnection.get();
      Connection currentConnection = getCurrentConnection();
      if (currentConnection != observingConnection) {
         this.observedEvents.clear();
         this.currentConnection = new WeakReference<>(currentConnection);
      }

      if (currentConnection == null) {
         LOGGER.debug("JEI StartEventObserver received {} too early, ignoring", event.getClass());
      } else {
         logReceivedEvent(event);
         Class<? extends Event> eventClass = (Class<? extends Event>)event.getClass();
         if (requiredEvents.contains(eventClass) && this.observedEvents.add(eventClass) && this.observedEvents.containsAll(requiredEvents)) {
            if (this.state == StartEventObserver.State.JEI_STARTED) {
               this.restart();
            } else {
               this.transitionState(StartEventObserver.State.JEI_STARTED);
            }
         }
      }
   }

   private static <T extends Event> void logReceivedEvent(T event) {
      LOGGER.debug("JEI StartEventObserver received event: {}", event.getClass());
   }

   @Nullable
   private static Connection getCurrentConnection() {
      Minecraft minecraft = Minecraft.getInstance();
      ClientPacketListener packetListener = minecraft.getConnection();
      if (packetListener != null) {
         return packetListener.getConnection();
      } else if (minecraft.pendingConnection != null) {
         return minecraft.pendingConnection;
      } else {
         return minecraft.screen instanceof ConnectScreen connectScreen ? connectScreen.connection : null;
      }
   }

   public void onResourceManagerReload(ResourceManager pResourceManager) {
      LOGGER.debug("JEI StartEventObserver detected resource manager reload.");
      this.restart();
   }

   private void restart() {
      if (this.state == StartEventObserver.State.JEI_STARTED) {
         this.transitionState(StartEventObserver.State.LISTENING);
         this.transitionState(StartEventObserver.State.JEI_STARTED);
      }
   }

   private void transitionState(StartEventObserver.State newState) {
      LOGGER.debug("JEI StartEventObserver transitioning state from {} to {}", this.state, newState);
      switch (newState) {
         case LISTENING:
            if (this.state == StartEventObserver.State.JEI_STARTED) {
               this.stopRunnable.run();
            }
            break;
         case JEI_STARTED:
            if (this.state != StartEventObserver.State.LISTENING) {
               throw new IllegalStateException("Attempted Illegal state transition from " + this.state + " to " + newState);
            }

            this.startRunnable.run();
      }

      this.state = newState;
      this.observedEvents.clear();
   }

   private enum State {
      LISTENING,
      JEI_STARTED;
   }
}
