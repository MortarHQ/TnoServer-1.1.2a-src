package com.blamejared.crafttweaker.natives.event.entity.living.effect;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Added;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/living/effect/MobEffectAddedEvent")
@NativeTypeRegistration(value = Added.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.effect.MobEffectAddedEvent")
public class ExpandMobEffectAddedEvent {
   @ZenEvent.Bus
   public static final IEventBus<Added> BUS = IEventBus.direct(Added.class, NeoForgeEventBusWire.of());

   @ZenCodeType.Getter("effectInstance")
   public static MobEffectInstance getEffectInstance(Added internal) {
      return internal.getEffectInstance();
   }

   @ZenCodeType.Getter("oldEffectInstance")
   public static @ZenCodeType.Nullable MobEffectInstance getOldEffectInstance(Added internal) {
      return internal.getOldEffectInstance();
   }

   @ZenCodeType.Getter("effectSource")
   public static @ZenCodeType.Nullable Entity getEffectSource(Added internal) {
      return internal.getEffectSource();
   }
}
