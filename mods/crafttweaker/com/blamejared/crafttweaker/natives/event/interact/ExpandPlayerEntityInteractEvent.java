package com.blamejared.crafttweaker.natives.event.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/interact/EntityInteractEvent")
@NativeTypeRegistration(value = EntityInteract.class, zenCodeName = "crafttweaker.neoforge.api.event.interact.EntityInteractEvent")
public class ExpandPlayerEntityInteractEvent {
   @ZenEvent.Bus
   public static final IEventBus<EntityInteract> BUS = IEventBus.cancelable(
      EntityInteract.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of()
   );

   @ZenCodeType.Getter("target")
   public static Entity getTarget(EntityInteract internal) {
      return internal.getTarget();
   }

   @ZenCodeType.Getter("cancellationResult")
   public static InteractionResult getCancellationResult(EntityInteract internal) {
      return internal.getCancellationResult();
   }

   @ZenCodeType.Setter("cancellationResult")
   public static void setCancellationResult(EntityInteract internal, InteractionResult result) {
      internal.setCancellationResult(result);
   }
}
