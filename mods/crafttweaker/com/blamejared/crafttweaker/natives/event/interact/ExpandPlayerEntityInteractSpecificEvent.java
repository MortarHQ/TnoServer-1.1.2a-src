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
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/interact/EntityInteractSpecificEvent")
@NativeTypeRegistration(value = EntityInteractSpecific.class, zenCodeName = "crafttweaker.neoforge.api.event.interact.EntityInteractSpecificEvent")
public class ExpandPlayerEntityInteractSpecificEvent {
   @ZenEvent.Bus
   public static final IEventBus<EntityInteractSpecific> BUS = IEventBus.cancelable(
      EntityInteractSpecific.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of()
   );

   @ZenCodeType.Getter("target")
   public static Entity getTarget(EntityInteractSpecific internal) {
      return internal.getTarget();
   }

   @ZenCodeType.Getter("localPos")
   public static Vec3 getLocalPos(EntityInteractSpecific internal) {
      return internal.getLocalPos();
   }

   @ZenCodeType.Getter("cancellationResult")
   public static InteractionResult getCancellationResult(EntityInteractSpecific internal) {
      return internal.getCancellationResult();
   }

   @ZenCodeType.Setter("cancellationResult")
   public static void setCancellationResult(EntityInteractSpecific internal, InteractionResult result) {
      internal.setCancellationResult(result);
   }
}
