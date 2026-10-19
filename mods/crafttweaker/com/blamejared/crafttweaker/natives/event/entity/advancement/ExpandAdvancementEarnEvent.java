package com.blamejared.crafttweaker.natives.event.entity.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent.AdvancementEarnEvent;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/advancement/AdvancementEarnEvent")
@NativeTypeRegistration(value = AdvancementEarnEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.advancement.AdvancementEarnEvent")
public class ExpandAdvancementEarnEvent {
   @ZenEvent.Bus
   public static final IEventBus<AdvancementEarnEvent> BUS = IEventBus.direct(AdvancementEarnEvent.class, NeoForgeEventBusWire.of());
}
