package me.decce.gnetum;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.function.Predicate;
import me.decce.gnetum.compat.immediatelyfast.ImmediatelyFastCompat;
import me.decce.gnetum.mixins.GuiAccessor;
import me.decce.gnetum.mixins.GuiLayerManagerAccessor;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.bus.ConsumerEventHandler;
import net.neoforged.bus.SubscribeEventListener;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventListener;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Post;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Pre;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.client.gui.GuiLayerManager.NamedLayer;
import net.neoforged.neoforge.common.NeoForge;

public class GuiHelper {
   public static int lastVanillaOverlayIndex = -1;

   public static int getLastVanillaOverlayIndex() {
      if (lastVanillaOverlayIndex == -1) {
         List<NamedLayer> layers = getGuiLayerManagerAccessor().getLayers();

         for (int i = 0; i < layers.size(); i++) {
            if (VanillaGuiLayers.SAVING_INDICATOR.equals(layers.get(i).name())) {
               lastVanillaOverlayIndex = i;
               break;
            }
         }
      }

      return lastVanillaOverlayIndex;
   }

   public static GuiAccessor getGuiAccessor() {
      return (GuiAccessor)Minecraft.getInstance().gui;
   }

   public static GuiLayerManagerAccessor getGuiLayerManagerAccessor() {
      return (GuiLayerManagerAccessor)getGuiAccessor().getLayerManager();
   }

   public static void renderLayers(List<NamedLayer> list, GuiGraphics guiGraphics, DeltaTracker partialTick, Predicate<String> check) {
      renderLayers(list, guiGraphics, partialTick, check, 0, -1);
   }

   public static void renderLayers(
      List<NamedLayer> list, GuiGraphics guiGraphics, DeltaTracker partialTick, Predicate<String> check, int startIndex, int endIndex
   ) {
      guiGraphics.pose().pushPose();

      for (int i = startIndex; i < list.size() && (endIndex == -1 || i <= endIndex); i++) {
         NamedLayer layer = list.get(i);
         String id = layer.name().toString();
         if (check.test(id)) {
            if (Gnetum.rendering) {
               Gnetum.currentElement = id;
               Gnetum.currentElementType = ElementType.VANILLA;
            }

            PoseStackHelper.checked(guiGraphics.pose(), () -> {
               if (!pre(layer, guiGraphics, partialTick)) {
                  layer.layer().render(guiGraphics, partialTick);
                  post(layer, guiGraphics, partialTick);
               }
            });
            ImmediatelyFastCompat.flushIfInstalledAndUsingHudBatching(guiGraphics);
            RenderSystem.enableDepthTest();
         }

         guiGraphics.pose().translate(0.0F, 0.0F, 200.0F);
      }

      guiGraphics.pose().popPose();
   }

   public static boolean pre(NamedLayer layer, GuiGraphics guiGraphics, DeltaTracker partialTick) {
      return ((Pre)postEvent(new Pre(guiGraphics, partialTick, layer.name(), layer.layer()), guiGraphics.pose())).isCanceled();
   }

   public static void post(NamedLayer layer, GuiGraphics guiGraphics, DeltaTracker partialTick) {
      postEvent(new Post(guiGraphics, partialTick, layer.name(), layer.layer()), guiGraphics.pose());
   }

   public static <T extends Event> T postEvent(Event event, PoseStack poseStack) {
      return postEvent((T)event, poseStack, null);
   }

   public static <T extends Event> T postEvent(T event, PoseStack poseStack, Predicate<String> check) {
      if (EventBusHelper.isShutdown()) {
         return event;
      }

      EventBusHelper.doPostChecks(event);
      EventListener[] listeners = EventBusHelper.getListenerList(event.getClass()).getListeners();
      int index = 0;

      try {
         for (; index < listeners.length; index++) {
            EventListener listener = listeners[index];
            String modid = null;
            if (listener instanceof SubscribeEventListener || listener instanceof ConsumerEventHandler) {
               modid = EventListenerHelper.tryGetModId(listener);
            }

            if (modid == null && event instanceof RenderGuiEvent) {
               modid = "gnetum_unknown";
            }

            if (modid != null) {
               if (event instanceof net.neoforged.neoforge.client.event.RenderGuiEvent.Pre) {
                  Gnetum.currentElement = modid;
                  Gnetum.currentElementType = ElementType.PRE;
                  if (check == null || check.test(modid)) {
                     invokeListenerSafe(listener, event, poseStack);
                  }
               } else if (event instanceof net.neoforged.neoforge.client.event.RenderGuiEvent.Post) {
                  Gnetum.currentElement = modid;
                  Gnetum.currentElementType = ElementType.POST;
                  if (check == null || check.test(modid)) {
                     invokeListenerSafe(listener, event, poseStack);
                  }
               } else {
                  listener.invoke(event);
               }
            } else if (!Gnetum.rendering || event instanceof RenderGuiLayerEvent) {
               listener.invoke(event);
            }
         }

         return event;
      } catch (Throwable throwable) {
         EventBusHelper.getExceptionHandler().handleException(NeoForge.EVENT_BUS, event, listeners, index, throwable);
         throw throwable;
      }
   }

   private static void invokeListenerSafe(EventListener listener, Event event, PoseStack stack) {
      PoseStackHelper.checked(stack, () -> listener.invoke(event));
   }
}
