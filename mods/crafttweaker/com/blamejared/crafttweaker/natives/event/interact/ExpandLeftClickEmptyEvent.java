package com.blamejared.crafttweaker.natives.event.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/interact/LeftClickEmptyEvent")
@NativeTypeRegistration(value = LeftClickEmpty.class, zenCodeName = "crafttweaker.neoforge.api.event.interact.LeftClickEmptyEvent")
public class ExpandLeftClickEmptyEvent {
   @ZenEvent.Bus
   public static final IEventBus<LeftClickEmpty> BUS = IEventBus.direct(LeftClickEmpty.class, NeoForgeEventBusWire.of());
}
