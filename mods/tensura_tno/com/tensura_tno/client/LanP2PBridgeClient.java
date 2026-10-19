package com.tensura_tno.client;

import java.lang.reflect.Method;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.level.GameType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent.Init.Post;

@EventBusSubscriber(modid = "tensura_tno", value = Dist.CLIENT)
public final class LanP2PBridgeClient {
   private static boolean p2pModeRequested = false;

   private LanP2PBridgeClient() {
   }

   @SubscribeEvent(priority = EventPriority.LOW)
   public static void onPauseScreenInit(Post event) {
      if (event.getScreen() instanceof PauseScreen) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.hasSingleplayerServer()) {
            if (p2pModeRequested) {
               IntegratedServer server = mc.getSingleplayerServer();
               if (server != null && server.isPublished()) {
                  p2pModeRequested = false;
                  activateP2P();
               }
            }

            Button lanSettingsBtn = findButtonByKey(event.getListenersList(), "lanserverproperties.button.lan_server_options");
            if (lanSettingsBtn != null) {
               event.removeListener(lanSettingsBtn);
            }

            Button shareToLanBtn = findButtonByKey(event.getListenersList(), "menu.shareToLan");
            if (shareToLanBtn != null) {
               int x = shareToLanBtn.getX();
               int y = shareToLanBtn.getY();
               int w = shareToLanBtn.getWidth();
               int h = shareToLanBtn.getHeight();
               event.removeListener(shareToLanBtn);
               PauseScreen pause = (PauseScreen)event.getScreen();
               event.addListener(
                  Button.builder(Component.translatable("menu.shareToLan"), b -> mc.setScreen(new ShareToLanScreen(pause))).bounds(x, y, w, h).build()
               );
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.LOW)
   public static void onShareToLanScreenInit(Post event) {
      if (event.getScreen() instanceof ShareToLanScreen) {
         p2pModeRequested = false;
         IntegratedServer spServer = Minecraft.getInstance().getSingleplayerServer();
         boolean published = spServer != null && spServer.isPublished();
         if (published) {
            configurePostPublishScreen(event);
         } else if (ModList.get().isLoaded("flistp2p")) {
            configurePrePublishScreen(event);
         }
      }
   }

   private static void configurePrePublishScreen(Post event) {
      CycleButton<?> onlineModeBtn = findOnlineModeButton(event.getListenersList());
      EditBox maxPlayerBox = findMaxPlayerEditBox(event.getListenersList());
      if (onlineModeBtn != null && maxPlayerBox != null) {
         boolean[] origOnlineActive = new boolean[]{onlineModeBtn.active};
         String[] origMaxPlayer = new String[]{maxPlayerBox.getValue()};
         int bx = onlineModeBtn.getX();
         int by = onlineModeBtn.getY() + onlineModeBtn.getHeight() + 24;
         int bw = onlineModeBtn.getWidth();
         Component label = Component.translatable("tensura_tno.gui.lan_p2p_mode");
         CycleButton<LanP2PBridgeClient.P2PMode> p2pToggle = CycleButton.builder(LanP2PBridgeClient.P2PMode::label)
            .withValues(new LanP2PBridgeClient.P2PMode[]{LanP2PBridgeClient.P2PMode.LAN, LanP2PBridgeClient.P2PMode.FRIENDS})
            .withInitialValue(LanP2PBridgeClient.P2PMode.LAN)
            .withTooltip(LanP2PBridgeClient.P2PMode::tooltip)
            .create(bx, by, bw, 20, label, (btn, mode) -> {
               if (mode == LanP2PBridgeClient.P2PMode.FRIENDS) {
                  p2pModeRequested = true;

                  for (int i = 0; i < 3 && !isOnlineModeEnabled(onlineModeBtn.getMessage()); i++) {
                     onlineModeBtn.onPress();
                  }

                  onlineModeBtn.active = false;
                  maxPlayerBox.setValue("8");
                  maxPlayerBox.setEditable(false);
               } else {
                  p2pModeRequested = false;
                  onlineModeBtn.active = origOnlineActive[0];
                  maxPlayerBox.setEditable(true);
                  maxPlayerBox.setValue(origMaxPlayer[0]);
               }
            });
         event.addListener(p2pToggle);
      }
   }

   private static void configurePostPublishScreen(Post event) {
      List<GuiEventListener> list = event.getListenersList();
      IntegratedServer srv = Minecraft.getInstance().getSingleplayerServer();
      if (srv != null) {
         for (GuiEventListener w : list) {
            if (w instanceof EditBox eb) {
               eb.setEditable(false);
            }
         }

         String[] cycleKeyPrefixes = new String[]{
            "lanserverproperties.options.online_mode", "lanserverproperties.gui.pvp_allowed", "lanserverproperties.options.preference_enabled"
         };

         for (GuiEventListener w : list) {
            if (w instanceof CycleButton<?> cb) {
               Component msg = cb.getMessage();
               if (msg.getContents() instanceof TranslatableContents tc) {
                  String key = tc.getKey();

                  for (String prefix : cycleKeyPrefixes) {
                     if (key.startsWith(prefix)) {
                        cb.active = false;
                        break;
                     }
                  }
               }
            }
         }

         String[] btnKeysToLock = new String[]{
            "lanserverproperties.button.preference_save", "lanserverproperties.button.preference_load", "lanserverproperties.gui.always_offline"
         };

         for (GuiEventListener w : list) {
            if (w instanceof Button btn) {
               Component msg = btn.getMessage();
               if (msg.getContents() instanceof TranslatableContents tc) {
                  String key = tc.getKey();

                  for (String lockKey : btnKeysToLock) {
                     if (key.equals(lockKey)) {
                        btn.active = false;
                        break;
                     }
                  }
               }
            }
         }

         Button startBtn = findButtonByKey(list, "lanServer.start");
         if (startBtn != null) {
            CycleButton<?> gameModeBtn = findCycleButtonByKey(list, "selectWorld.gameMode");
            CycleButton<?> allowCmdsBtn = findCycleButtonByKey(list, "selectWorld.allowCommands.new");
            if (allowCmdsBtn == null) {
               allowCmdsBtn = findCycleButtonByKey(list, "selectWorld.allowCommands");
            }

            int sx = startBtn.getX();
            int sy = startBtn.getY();
            int sw = startBtn.getWidth();
            int sh = startBtn.getHeight();
            event.removeListener(startBtn);
            CycleButton<?> finalGmBtn = gameModeBtn;
            CycleButton<?> finalAcBtn = allowCmdsBtn;
            IntegratedServer finalSrv = srv;
            ShareToLanScreen capturedScreen = (ShareToLanScreen)event.getScreen();
            event.addListener(Button.builder(CommonComponents.GUI_DONE, b -> {
               if (finalGmBtn != null && finalGmBtn.getValue() instanceof GameType gt) {
                  finalSrv.setDefaultGameType(gt);
               }

               if (finalAcBtn != null && finalAcBtn.getValue() instanceof Boolean ac) {
                  finalSrv.getPlayerList().setAllowCommandsForAllPlayers(ac);
               }

               capturedScreen.onClose();
            }).bounds(sx, sy, sw, sh).build());
         }
      }
   }

   private static void activateP2P() {
      try {
         Class<?> cls = Class.forName("com.juanmuscaria.flistp2p.client.FlistP2PClient");
         Method m = cls.getMethod("setHostingP2P", boolean.class);
         m.invoke(null, true);
      } catch (Exception var2) {
      }
   }

   private static Button findButtonByKey(List<? extends GuiEventListener> list, String key) {
      for (GuiEventListener w : list) {
         if (w instanceof Button btn) {
            Component msg = btn.getMessage();
            if (msg.getContents() instanceof TranslatableContents tc && tc.getKey().equals(key)) {
               return btn;
            }
         }
      }

      return null;
   }

   private static CycleButton<?> findOnlineModeButton(List<? extends GuiEventListener> list) {
      for (GuiEventListener w : list) {
         if (w instanceof CycleButton<?> cb) {
            Component msg = cb.getMessage();
            if (msg.getContents() instanceof TranslatableContents tc && tc.getKey().startsWith("lanserverproperties.options.online_mode")) {
               return cb;
            }
         }
      }

      return null;
   }

   private static EditBox findMaxPlayerEditBox(List<? extends GuiEventListener> list) {
      for (GuiEventListener w : list) {
         if (w instanceof EditBox eb && eb.getClass().getSimpleName().equals("IntegerEditBox")) {
            return eb;
         }
      }

      return null;
   }

   private static CycleButton<?> findCycleButtonByKey(List<? extends GuiEventListener> list, String key) {
      for (GuiEventListener w : list) {
         if (w instanceof CycleButton<?> cb) {
            Component msg = cb.getMessage();
            if (msg.getContents() instanceof TranslatableContents tc && tc.getKey().equals(key)) {
               return cb;
            }
         }
      }

      return null;
   }

   private static boolean isOnlineModeEnabled(Component msg) {
      return msg.getContents() instanceof TranslatableContents tc && tc.getKey().equals("lanserverproperties.options.online_mode.on");
   }

   private enum P2PMode {
      LAN("gui.flistp2p.multiplayer_options.visibility.lan"),
      FRIENDS("gui.flistp2p.multiplayer_options.visibility.friends");

      final String key;

      P2PMode(String key) {
         this.key = key;
      }

      Component label() {
         return Component.translatable(this.key);
      }

      Tooltip tooltip() {
         return Tooltip.create(Component.translatable(this.key + ".tooltip"));
      }
   }
}
