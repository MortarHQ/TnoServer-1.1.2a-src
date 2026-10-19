package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Pre;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/tick/LevelTickEvent")
@NativeTypeRegistration(value = LevelTickEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.LevelTickEvent")
public class ExpandLevelTickEvent {
   @ZenCodeType.Getter("hasTime")
   public static boolean hasTime(LevelTickEvent internal) {
      return internal.hasTime();
   }

   @ZenCodeType.Getter("level")
   public static Level getLevel(LevelTickEvent internal) {
      return internal.getLevel();
   }

   @ZenRegister
   @ZenEvent
   @Document("neoforge/api/event/tick/LevelTickPostEvent")
   @NativeTypeRegistration(value = Post.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.LevelTickPostEvent")
   public static class ExpandServerTickPostEvent {
      @ZenEvent.Bus
      public static final IEventBus<Post> BUS = IEventBus.direct(Post.class, NeoForgeEventBusWire.of());
   }

   @ZenRegister
   @ZenEvent
   @Document("neoforge/api/event/tick/LevelTickPreEvent")
   @NativeTypeRegistration(value = Pre.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.LevelTickPreEvent")
   public static class ExpandServerTickPreEvent {
      @ZenEvent.Bus
      public static final IEventBus<Pre> BUS = IEventBus.direct(Pre.class, NeoForgeEventBusWire.of());
   }
}
