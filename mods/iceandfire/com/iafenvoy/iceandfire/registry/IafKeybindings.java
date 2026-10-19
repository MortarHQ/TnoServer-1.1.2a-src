package com.iafenvoy.iceandfire.registry;

import com.iafenvoy.iceandfire.event.ClientEvents;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.client.ClientTickEvent.Client;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;

public final class IafKeybindings {
   public static final KeyMapping DRAGON_BREATH = new KeyMapping("key.dragon_fireAttack", 82, "key.categories.gameplay");
   public static final KeyMapping DRAGON_STRIKE = new KeyMapping("key.dragon_strike", 71, "key.categories.gameplay");
   public static final KeyMapping DRAGON_DOWN = new KeyMapping("key.dragon_down", 88, "key.categories.gameplay");
   public static final KeyMapping DRAGON_CHANGE_VIEW = new KeyMapping("key.dragon_change_view", 296, "key.categories.gameplay");

   public static void init() {
      KeyMappingRegistry.register(DRAGON_BREATH);
      KeyMappingRegistry.register(DRAGON_STRIKE);
      KeyMappingRegistry.register(DRAGON_DOWN);
      KeyMappingRegistry.register(DRAGON_CHANGE_VIEW);
      ClientTickEvent.CLIENT_POST.register((Client)client -> {
         if (DRAGON_CHANGE_VIEW.consumeClick()) {
            if (ClientEvents.currentView + 1 > 3) {
               ClientEvents.currentView = 0;
            } else {
               ClientEvents.currentView++;
            }
         }
      });
   }
}
