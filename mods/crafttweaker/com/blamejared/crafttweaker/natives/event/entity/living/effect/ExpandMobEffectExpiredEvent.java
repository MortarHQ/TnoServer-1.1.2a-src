package com.blamejared.crafttweaker.natives.event.entity.living.effect;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Expired;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/living/effect/MobEffectExpiredEvent")
@NativeTypeRegistration(value = Expired.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.effect.MobEffectExpiredEvent")
public class ExpandMobEffectExpiredEvent {
   @ZenEvent.Bus
   public static final IEventBus<Expired> BUS = IEventBus.direct(Expired.class, NeoForgeEventBusWire.of());
}
