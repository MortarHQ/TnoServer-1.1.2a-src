package xaero.map.controls;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xaero.lib.client.controls.util.KeyMappingUtils;
import xaero.lib.client.gui.config.context.BuiltInEditConfigScreenContexts;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.WorldMapSession;
import xaero.map.gui.GuiMap;
import xaero.map.gui.GuiWorldMapSettings;

public class ControlsHandler {
   private MapProcessor mapProcessor;
   private ArrayList<KeyEvent> keyEvents = new ArrayList<>();
   private ArrayList<KeyEvent> oldKeyEvents = new ArrayList<>();

   public ControlsHandler(MapProcessor mapProcessor) {
      this.mapProcessor = mapProcessor;
   }

   private boolean eventExists(KeyMapping kb) {
      for (KeyEvent o : this.keyEvents) {
         if (o.getKb() == kb) {
            return true;
         }
      }

      return this.oldEventExists(kb);
   }

   private boolean oldEventExists(KeyMapping kb) {
      for (KeyEvent o : this.oldKeyEvents) {
         if (o.getKb() == kb) {
            return true;
         }
      }

      return false;
   }

   public static boolean isKeyRepeat(KeyMapping kb) {
      return kb != ControlsRegister.keyOpenMap
         && kb != ControlsRegister.keyOpenSettings
         && kb != ControlsRegister.keyOpenServerSettings
         && kb != ControlsRegister.keyToggleDimension;
   }

   public void keyDown(KeyMapping kb, boolean tickEnd, boolean isRepeat) {
      Minecraft mc = Minecraft.getInstance();
      if (!tickEnd) {
         if (kb == ControlsRegister.keyOpenMap) {
            mc.setScreen(new GuiMap(null, null, this.mapProcessor, mc.getCameraEntity()));
         } else if (kb == ControlsRegister.keyOpenSettings) {
            mc.setScreen(new GuiWorldMapSettings(BuiltInEditConfigScreenContexts.CLIENT));
         } else if (kb == ControlsRegister.keyOpenServerSettings) {
            mc.setScreen(new GuiWorldMapSettings(BuiltInEditConfigScreenContexts.SERVER));
         } else if (kb == ControlsRegister.keyQuickConfirm) {
            WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
            MapProcessor mapProcessor = worldmapSession.getMapProcessor();
            synchronized (mapProcessor.uiPauseSync) {
               if (!mapProcessor.isUIPaused()) {
                  mapProcessor.quickConfirmMultiworld();
               }
            }
         } else if (kb == ControlsRegister.keyToggleDimension) {
            this.mapProcessor.getMapWorld().toggleDimension(!Screen.hasShiftDown());
            String messageType = this.mapProcessor.getMapWorld().getCustomDimensionId() == null
               ? "gui.xaero_switched_to_current_dimension"
               : "gui.xaero_switched_to_dimension";
            ResourceLocation messageDimLoc = this.mapProcessor.getMapWorld().getFutureDimensionId() == null
               ? null
               : this.mapProcessor.getMapWorld().getFutureDimensionId().location();
            mc.gui.getChat().addMessage(Component.translatable(messageType, new Object[]{messageDimLoc.toString()}));
         }
      }
   }

   public void keyUp(KeyMapping kb, boolean tickEnd) {
      if (!tickEnd) {
      }
   }

   public void handleKeyEvents() {
      Minecraft mc = Minecraft.getInstance();
      this.onKeyInput(mc);

      for (int i = 0; i < this.keyEvents.size(); i++) {
         KeyEvent ke = this.keyEvents.get(i);
         if (mc.screen == null) {
            this.keyDown(ke.getKb(), ke.isTickEnd(), ke.isRepeat());
         }

         if (!ke.isRepeat()) {
            if (!this.oldEventExists(ke.getKb())) {
               this.oldKeyEvents.add(ke);
            }

            this.keyEvents.remove(i);
            i--;
         } else if (!KeyMappingUtils.isPhysicallyDown(ke.getKb())) {
            this.keyUp(ke.getKb(), ke.isTickEnd());
            this.keyEvents.remove(i);
            i--;
         }
      }

      for (int i = 0; i < this.oldKeyEvents.size(); i++) {
         KeyEvent ke = this.oldKeyEvents.get(i);
         if (!KeyMappingUtils.isPhysicallyDown(ke.getKb())) {
            this.keyUp(ke.getKb(), ke.isTickEnd());
            this.oldKeyEvents.remove(i);
            i--;
         }
      }
   }

   public void onKeyInput(Minecraft mc) {
      List<KeyMapping> kbs = WorldMap.controlsRegister.keybindings;

      for (int i = 0; i < kbs.size(); i++) {
         KeyMapping kb = kbs.get(i);

         try {
            boolean pressed = kb.consumeClick();

            while (kb.consumeClick()) {
            }

            if (mc.screen == null && !this.eventExists(kb) && pressed) {
               this.keyEvents.add(new KeyEvent(kb, false, isKeyRepeat(kb), true));
            }
         } catch (Exception var6) {
         }
      }
   }
}
