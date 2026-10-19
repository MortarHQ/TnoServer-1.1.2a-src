package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/player/PlayerChangedDimensionEvent")
@NativeTypeRegistration(value = PlayerChangedDimensionEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.PlayerChangedDimensionEvent")
public class ExpandPlayerChangedDimensionEvent {
   @ZenEvent.Bus
   public static final IEventBus<PlayerChangedDimensionEvent> BUS = IEventBus.direct(PlayerChangedDimensionEvent.class, NeoForgeEventBusWire.of());

   @ZenCodeType.Getter("from")
   public static ResourceLocation getFrom(PlayerChangedDimensionEvent internal) {
      return internal.getFrom().location();
   }

   @ZenCodeType.Getter("to")
   public static ResourceLocation getTo(PlayerChangedDimensionEvent internal) {
      return internal.getTo().location();
   }
}
