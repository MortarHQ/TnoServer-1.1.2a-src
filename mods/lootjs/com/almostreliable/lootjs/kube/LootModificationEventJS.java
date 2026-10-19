package com.almostreliable.lootjs.kube;

import com.almostreliable.lootjs.loot.LootModificationEvent;
import dev.latvian.mods.kubejs.event.EventResult;
import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.script.ConsoleJS;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

public class LootModificationEventJS extends LootModificationEvent implements KubeEvent {
   public LootModificationEventJS(Map<ResourceLocation, IGlobalLootModifier> modifiers) {
      super(modifiers);
   }

   public void afterPosted(EventResult result) {
      if (!this.removedGlobalModifiers.isEmpty()) {
         ConsoleJS.SERVER
            .info(
               "[LootJS] Removed "
                  + this.removedGlobalModifiers.size()
                  + " global loot modifiers: "
                  + this.removedGlobalModifiers.stream().<CharSequence>map(ResourceLocation::toString).collect(Collectors.joining(", "))
            );
      }

      this.storeModifiers(throwable -> ConsoleJS.SERVER.error(throwable));
   }
}
