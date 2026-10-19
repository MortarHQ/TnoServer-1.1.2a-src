package com.blamejared.crafttweaker.natives.event.xp;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent.LevelChange;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/xp/PlayerXpLevelChangeEvent")
@NativeTypeRegistration(value = LevelChange.class, zenCodeName = "crafttweaker.neoforge.api.event.xp.PlayerXpLevelChangeEvent")
public class ExpandPlayerXpLevelChangeEvent {
   @ZenEvent.Bus
   public static final IEventBus<LevelChange> BUS = IEventBus.cancelable(LevelChange.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of());

   @ZenCodeType.Getter("levels")
   public static int getLevels(LevelChange internal) {
      return internal.getLevels();
   }

   @ZenCodeType.Setter("levels")
   public static void setLevels(LevelChange internal, int levels) {
      internal.setLevels(levels);
   }
}
