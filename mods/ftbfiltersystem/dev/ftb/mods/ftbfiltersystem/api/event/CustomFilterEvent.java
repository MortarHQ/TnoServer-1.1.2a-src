package dev.ftb.mods.ftbfiltersystem.api.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface CustomFilterEvent {
   Event<CustomFilterEvent> MATCH_ITEM = EventFactory.createEventResult(new CustomFilterEvent[0]);

   EventResult matchItem(ItemStack var1, @NotNull String var2, String var3);
}
