package com.blamejared.crafttweaker.natives.event.xp;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent.XpChange;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/xp/PlayerXpChangeEvent")
@NativeTypeRegistration(value = XpChange.class, zenCodeName = "crafttweaker.neoforge.api.event.xp.PlayerXpChangeEvent")
public class ExpandPlayerXpChangeEvent {
   @ZenEvent.Bus
   public static final IEventBus<XpChange> BUS = IEventBus.cancelable(XpChange.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of());

   @ZenCodeType.Getter("amount")
   public static int getAmount(XpChange internal) {
      return internal.getAmount();
   }

   @ZenCodeType.Setter("amount")
   public static void setAmount(XpChange internal, int amount) {
      internal.setAmount(amount);
   }
}
