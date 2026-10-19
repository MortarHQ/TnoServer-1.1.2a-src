package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.event.level.BlockEvent.NeighborNotifyEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/block/NeighborBlockNotifyEvent")
@NativeTypeRegistration(value = NeighborNotifyEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.block.NeighborBlockNotifyEvent")
public class ExpandNeighborBlockNotifyEvent {
   @ZenEvent.Bus
   public static final IEventBus<NeighborNotifyEvent> BUS = IEventBus.cancelable(
      NeighborNotifyEvent.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of()
   );

   @ZenCodeType.Getter("notifiedSides")
   public static Set<Direction> getNotifiedSides(NeighborNotifyEvent internal) {
      return new HashSet<>(internal.getNotifiedSides());
   }

   @ZenCodeType.Getter("forceRedstoneUpdate")
   public static boolean getForceRedstoneUpdate(NeighborNotifyEvent internal) {
      return internal.getForceRedstoneUpdate();
   }
}
