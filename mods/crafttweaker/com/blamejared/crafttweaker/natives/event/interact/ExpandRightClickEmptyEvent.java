package com.blamejared.crafttweaker.natives.event.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/interact/RightClickEmptyEvent")
@NativeTypeRegistration(value = RightClickEmpty.class, zenCodeName = "crafttweaker.neoforge.api.event.interact.RightClickEmptyEvent")
public class ExpandRightClickEmptyEvent {
   @ZenEvent.Bus
   public static final IEventBus<RightClickEmpty> BUS = IEventBus.direct(RightClickEmpty.class, NeoForgeEventBusWire.of());
}
