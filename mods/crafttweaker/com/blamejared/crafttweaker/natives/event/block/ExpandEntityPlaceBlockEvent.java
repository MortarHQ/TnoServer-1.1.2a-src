package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/block/EntityPlaceBlockEvent")
@NativeTypeRegistration(value = EntityPlaceEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.block.EntityPlaceBlockEvent")
public class ExpandEntityPlaceBlockEvent {
   @ZenEvent.Bus
   public static final IEventBus<EntityPlaceEvent> BUS = IEventBus.cancelable(
      EntityPlaceEvent.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of()
   );

   @ZenCodeType.Getter("entity")
   public static @ZenCodeType.Nullable Entity getEntity(EntityPlaceEvent internal) {
      return internal.getEntity();
   }

   @ZenCodeType.Getter("placedBlock")
   public static BlockState getPlacedBlock(EntityPlaceEvent internal) {
      return internal.getPlacedBlock();
   }

   @ZenCodeType.Getter("placedAgainst")
   public static BlockState getPlacedAgainst(EntityPlaceEvent internal) {
      return internal.getPlacedAgainst();
   }
}
