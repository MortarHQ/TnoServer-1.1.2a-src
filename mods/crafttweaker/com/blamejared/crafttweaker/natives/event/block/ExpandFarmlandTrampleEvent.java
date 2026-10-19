package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.level.BlockEvent.FarmlandTrampleEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/block/FarmlandTrampleEvent")
@NativeTypeRegistration(value = FarmlandTrampleEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.block.FarmlandTrampleEvent")
public class ExpandFarmlandTrampleEvent {
   @ZenEvent.Bus
   public static final IEventBus<FarmlandTrampleEvent> BUS = IEventBus.cancelable(
      FarmlandTrampleEvent.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of()
   );

   @ZenCodeType.Getter("entity")
   public static Entity getEntity(FarmlandTrampleEvent internal) {
      return internal.getEntity();
   }

   @ZenCodeType.Getter("fallDistance")
   public static float getFallDistance(FarmlandTrampleEvent internal) {
      return internal.getFallDistance();
   }
}
