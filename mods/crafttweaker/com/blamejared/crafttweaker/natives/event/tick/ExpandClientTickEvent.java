package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;

@ZenRegister
@Document("neoforge/api/event/tick/ClientTickEvent")
@NativeTypeRegistration(value = ClientTickEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.ClientTickEvent")
public class ExpandClientTickEvent {
   @ZenRegister
   @ZenEvent
   @Document("neoforge/api/event/tick/ClientTickPostEvent")
   @NativeTypeRegistration(value = Post.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.ClientTickPostEvent")
   public static class ExpandServerTickPostEvent {
      @ZenEvent.Bus
      public static final IEventBus<Post> BUS = IEventBus.direct(Post.class, NeoForgeEventBusWire.of());
   }

   @ZenRegister
   @ZenEvent
   @Document("neoforge/api/event/tick/ClientTickPreEvent")
   @NativeTypeRegistration(value = Pre.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.ClientTickPreEvent")
   public static class ExpandServerTickPreEvent {
      @ZenEvent.Bus
      public static final IEventBus<Pre> BUS = IEventBus.direct(Pre.class, NeoForgeEventBusWire.of());
   }
}
