package com.blamejared.crafttweaker.natives.event.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemSmeltedEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/item/ItemSmeltedEvent")
@NativeTypeRegistration(value = ItemSmeltedEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.item.ItemSmeltedEvent")
public class ExpandItemSmeltedEvent {
   @ZenEvent.Bus
   public static final IEventBus<ItemSmeltedEvent> BUS = IEventBus.direct(ItemSmeltedEvent.class, NeoForgeEventBusWire.of());

   @ZenCodeType.Getter("smelting")
   public static IItemStack getSmelting(ItemSmeltedEvent internal) {
      return IItemStack.of(internal.getSmelting());
   }
}
