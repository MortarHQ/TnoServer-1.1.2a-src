package com.blamejared.crafttweaker.natives.event.entity.use;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Tick;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/use/LivingEntityUseItemTickEvent")
@NativeTypeRegistration(value = Tick.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.use.LivingEntityUseItemTickEvent")
public class ExpandLivingEntityUseItemTickEvent {
   @ZenEvent.Bus
   public static final IEventBus<Tick> BUS = IEventBus.cancelable(Tick.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of());
}
