package com.blamejared.crafttweaker.natives.event.entity.living.effect;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/living/effect/MobEffectApplicableEvent")
@NativeTypeRegistration(value = Applicable.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.effect.MobEffectApplicableEvent")
public class ExpandMobEffectApplicableEvent {
   @ZenEvent.Bus
   public static final IEventBus<Applicable> BUS = IEventBus.direct(Applicable.class, NeoForgeEventBusWire.of());
}
