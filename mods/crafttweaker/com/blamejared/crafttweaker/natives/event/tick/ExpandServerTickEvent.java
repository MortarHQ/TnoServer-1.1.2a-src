package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Pre;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/tick/ServerTickEvent")
@NativeTypeRegistration(value = ServerTickEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.ServerTickEvent")
public class ExpandServerTickEvent {
   @ZenCodeType.Getter("hasTime")
   public static boolean hasTime(ServerTickEvent internal) {
      return internal.hasTime();
   }

   @ZenCodeType.Getter("server")
   public static MinecraftServer getServer(ServerTickEvent internal) {
      return internal.getServer();
   }

   @ZenRegister
   @ZenEvent
   @Document("neoforge/api/event/tick/ServerTickPostEvent")
   @NativeTypeRegistration(value = Post.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.ServerTickPostEvent")
   public static class ExpandServerTickPostEvent {
      @ZenEvent.Bus
      public static final IEventBus<Post> BUS = IEventBus.direct(Post.class, NeoForgeEventBusWire.of());
   }

   @ZenRegister
   @ZenEvent
   @Document("neoforge/api/event/tick/ServerTickPreEvent")
   @NativeTypeRegistration(value = Pre.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.ServerTickPreEvent")
   public static class ExpandServerTickPreEvent {
      @ZenEvent.Bus
      public static final IEventBus<Pre> BUS = IEventBus.direct(Pre.class, NeoForgeEventBusWire.of());
   }
}
