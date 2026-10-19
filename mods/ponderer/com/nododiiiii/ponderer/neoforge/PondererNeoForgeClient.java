package com.nododiiiii.ponderer.neoforge;

import com.nododiiiii.ponderer.Config;
import com.nododiiiii.ponderer.ModKeyBindings;
import com.nododiiiii.ponderer.blueprint.BlueprintHandler;
import com.nododiiiii.ponderer.compat.jei.JeiCompat;
import com.nododiiiii.ponderer.compat.jei.PondererJeiPlugin;
import com.nododiiiii.ponderer.neoforge.sticksnapshot.StickSnapshotFeature;
import com.nododiiiii.ponderer.ponder.DynamicPonderPlugin;
import com.nododiiiii.ponderer.ponder.PondererClientCommands;
import com.nododiiiii.ponderer.ponder.SceneStore;
import com.nododiiiii.ponderer.ponder.TriggerManager;
import com.nododiiiii.ponderer.ui.FunctionScreen;
import com.nododiiiii.ponderer.ui.NbtPickState;
import java.util.ArrayList;
import java.util.List;
import net.createmod.catnip.gui.ScreenOpener;
import net.createmod.ponder.enums.PonderConfig;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent.Action;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.InputEvent.MouseScrollingEvent;
import net.neoforged.neoforge.client.event.InputEvent.MouseButton.Pre;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;

public class PondererNeoForgeClient {
   private static final BlueprintHandler blueprintHandler = new BlueprintHandler();
   private static boolean triggerKeyWasDown = false;

   static void init(IEventBus modEventBus) {
      BlueprintHandler.INSTANCE = blueprintHandler;
      modEventBus.addListener(PondererNeoForgeClient::onClientSetup);
      modEventBus.addListener(PondererNeoForgeClient::onRegisterKeyMappings);
      NeoForge.EVENT_BUS.addListener(PondererNeoForgeClient::onRegisterClientCommands);
      NeoForge.EVENT_BUS.addListener(PondererNeoForgeClient::onClientTick);
      NeoForge.EVENT_BUS.addListener(PondererNeoForgeClient::onBlueprintClientTick);
      NeoForge.EVENT_BUS.addListener(PondererNeoForgeClient::onMouseScrolled);
      NeoForge.EVENT_BUS.addListener(PondererNeoForgeClient::onMouseInput);
      NeoForge.EVENT_BUS.addListener(PondererNeoForgeClient::onPlayerLoggedIn);
      NeoForge.EVENT_BUS.addListener(EventPriority.HIGH, PondererNeoForgeClient::onScreenMouseClick);
      StickSnapshotFeature.onClientInit();
   }

   private static void onClientSetup(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
         SceneStore.extractDefaultsIfNeeded();
         SceneStore.AutoLoadResult result = SceneStore.autoLoadPonderPacks();
         if (!result.orphanedPacks.isEmpty()) {
            PondererNeoForge.pendingOrphanedPacks.addAll(result.orphanedPacks);
         }

         if (!result.updatedPacks.isEmpty()) {
            PondererNeoForge.pendingPackUpdates.addAll(result.updatedPacks);
         }

         SceneStore.reloadFromDisk();
         PonderIndex.addPlugin(new DynamicPonderPlugin());
         PonderConfig.client().editingMode.set(false);
      });
   }

   private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
      for (KeyMapping keyMapping : ModKeyBindings.all()) {
         event.register(keyMapping);
      }
   }

   private static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
      PondererClientCommands.register(event.getDispatcher());
   }

   private static void onClientTick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player != null && mc.screen == null) {
         if (NbtPickState.isActive()) {
            mc.player.displayClientMessage(Component.translatable("ponderer.ui.nbt_pick.middle_prompt"), true);
         }

         if (ModKeyBindings.OPEN_FUNCTION_PAGE.consumeClick()) {
            ScreenOpener.open(new FunctionScreen());
         }

         boolean triggerPressed = ModKeyBindings.TRIGGER_PONDER.isDown();
         if (triggerPressed && !triggerKeyWasDown) {
            TriggerManager.onTriggerKeyPressed();
         }

         triggerKeyWasDown = triggerPressed;
      }
   }

   private static void onBlueprintClientTick(Post event) {
      blueprintHandler.tick();
      TriggerManager.tick();
   }

   private static void onMouseScrolled(MouseScrollingEvent event) {
      if (blueprintHandler.mouseScrolled(event.getScrollDeltaY())) {
         event.setCanceled(true);
      }
   }

   private static void onMouseInput(Pre event) {
      if (NbtPickState.isActive() && event.getButton() == 2 && event.getAction() == 1) {
         NbtPickState.handleUseClick();
         event.setCanceled(true);
      } else {
         if (blueprintHandler.onMouseInput(event.getButton(), event.getAction() == 1)) {
            event.setCanceled(true);
         }
      }
   }

   private static void onScreenMouseClick(net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonPressed.Pre event) {
      if (JeiCompat.isAvailable() && PondererJeiPlugin.handleMouseClick(event.getScreen(), event.getMouseX(), event.getMouseY(), event.getButton())) {
         event.setCanceled(true);
      }
   }

   private static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      List<SceneStore.PackUpdateInfo> updates = new ArrayList<>(PondererNeoForge.pendingPackUpdates);
      PondererNeoForge.pendingPackUpdates.clear();
      List<String> orphaned = new ArrayList<>(PondererNeoForge.pendingOrphanedPacks);
      PondererNeoForge.pendingOrphanedPacks.clear();
      if (!updates.isEmpty() || !orphaned.isEmpty()) {
         if ((Boolean)Config.PACK_ORPHAN_PROMPT.get() || !orphaned.isEmpty() || !updates.isEmpty()) {
            Minecraft.getInstance()
               .execute(
                  () -> {
                     LocalPlayer player = Minecraft.getInstance().player;
                     if (player != null) {
                        for (SceneStore.PackUpdateInfo info : updates) {
                           String versionChange = info.oldVersion != null ? "v" + info.oldVersion + " → v" + info.newVersion : "v" + info.newVersion;
                           MutableComponent msg = Component.literal("[Ponderer] ")
                              .withStyle(Style.EMPTY.withColor(16753920))
                              .append(Component.literal(info.packName).withStyle(Style.EMPTY.withColor(16777215)))
                              .append(Component.literal(": ").withStyle(Style.EMPTY.withColor(11184810)));
                           if (info.oldVersion != null) {
                              msg.append(
                                 Component.translatable("ponderer.pack.update.version", new Object[]{versionChange}).withStyle(Style.EMPTY.withColor(5635925))
                              );
                           } else {
                              msg.append(
                                 Component.translatable("ponderer.pack.update.loaded", new Object[]{info.newVersion}).withStyle(Style.EMPTY.withColor(5635925))
                              );
                           }

                           if (info.conflictCount > 0) {
                              msg.append(
                                 Component.literal(" ")
                                    .append(
                                       Component.translatable("ponderer.pack.update.conflicts", new Object[]{info.conflictCount})
                                          .withStyle(Style.EMPTY.withColor(16755200))
                                    )
                              );
                           }

                           player.displayClientMessage(msg, false);
                        }

                        if ((Boolean)Config.PACK_ORPHAN_PROMPT.get()) {
                           for (String packName : orphaned) {
                              MutableComponent msg = Component.literal("[Ponderer] ")
                                 .withStyle(Style.EMPTY.withColor(16753920))
                                 .append(Component.literal(packName).withStyle(Style.EMPTY.withColor(16777215)))
                                 .append(Component.literal(": ").withStyle(Style.EMPTY.withColor(11184810)))
                                 .append(Component.translatable("ponderer.pack.orphan.message").withStyle(Style.EMPTY.withColor(11184810)));
                              player.displayClientMessage(msg, false);
                              MutableComponent removeBtn = Component.literal("  [")
                                 .withStyle(Style.EMPTY.withColor(8947848))
                                 .append(
                                    Component.translatable("ponderer.pack.orphan.remove")
                                       .withStyle(
                                          Style.EMPTY
                                             .withColor(16737894)
                                             .withClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/ponderer unregister_pack " + packName))
                                             .withUnderlined(true)
                                       )
                                 )
                                 .append(Component.literal("]").withStyle(Style.EMPTY.withColor(8947848)));
                              player.displayClientMessage(removeBtn, false);
                           }
                        }
                     }
                  }
               );
         }
      }
   }
}
