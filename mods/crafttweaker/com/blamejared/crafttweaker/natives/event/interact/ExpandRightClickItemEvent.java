package com.blamejared.crafttweaker.natives.event.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.InteractionResult;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/interact/RightClickItemEvent")
@NativeTypeRegistration(value = RightClickItem.class, zenCodeName = "crafttweaker.neoforge.api.event.interact.RightClickItemEvent")
public class ExpandRightClickItemEvent {
   @ZenEvent.Bus
   public static final IEventBus<RightClickItem> BUS = IEventBus.cancelable(
      RightClickItem.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of()
   );

   @ZenCodeType.Getter("cancellationResult")
   public static InteractionResult getCancellationResult(RightClickItem internal) {
      return internal.getCancellationResult();
   }

   @ZenCodeType.Setter("cancellationResult")
   public static void setCancellationResult(RightClickItem internal, InteractionResult result) {
      internal.setCancellationResult(result);
   }
}
