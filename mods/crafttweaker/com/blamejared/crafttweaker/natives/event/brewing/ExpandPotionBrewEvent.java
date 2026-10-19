package com.blamejared.crafttweaker.natives.event.brewing;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.brewing.PotionBrewEvent;
import net.neoforged.neoforge.event.brewing.PotionBrewEvent.Post;
import net.neoforged.neoforge.event.brewing.PotionBrewEvent.Pre;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/brewing/PotionBrewEvent")
@NativeTypeRegistration(value = PotionBrewEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.brewing.PotionBrewEvent")
public class ExpandPotionBrewEvent {
   @ZenCodeType.Method
   public static IItemStack getItem(PotionBrewEvent internal, int index) {
      return IItemStack.of(internal.getItem(index));
   }

   @ZenCodeType.Method
   public static void setItem(PotionBrewEvent internal, int index, IItemStack stack) {
      internal.setItem(index, stack.getInternal());
   }

   @ZenCodeType.Getter("length")
   public static int getLength(PotionBrewEvent internal) {
      return internal.getLength();
   }

   @ZenRegister
   @ZenEvent
   @Document("neoforge/api/event/brewing/PotionBrewEventPost")
   @NativeTypeRegistration(value = Post.class, zenCodeName = "crafttweaker.neoforge.api.event.brewing.PotionBrewEventPost")
   public static class ExpandPotionBrewPostEvent {
      @ZenEvent.Bus
      public static final IEventBus<Post> BUS = IEventBus.direct(Post.class, NeoForgeEventBusWire.of());
   }

   @ZenRegister
   @ZenEvent
   @Document("neoforge/api/event/brewing/PotionBrewEventPre")
   @NativeTypeRegistration(value = Pre.class, zenCodeName = "crafttweaker.neoforge.api.event.brewing.PotionBrewEventPre")
   public static class ExpandPotionBrewPreEvent {
      @ZenEvent.Bus
      public static final IEventBus<Pre> BUS = IEventBus.cancelable(Pre.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of());
   }
}
