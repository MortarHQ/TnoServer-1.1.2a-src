package com.blamejared.crafttweaker.natives.event;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent.Detonate;
import net.neoforged.neoforge.event.level.ExplosionEvent.Start;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/ExplosionEvent")
@NativeTypeRegistration(value = ExplosionEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.ExplosionEvent")
public class ExpandExplosionEvent {
   @ZenCodeType.Getter("level")
   public static Level getLevel(ExplosionEvent internal) {
      return internal.getLevel();
   }

   @ZenCodeType.Getter("explosion")
   public static Explosion getExplosion(ExplosionEvent internal) {
      return internal.getExplosion();
   }

   @ZenEvent
   @ZenRegister
   @Document("neoforge/api/event/DetonateExplosionEvent")
   @NativeTypeRegistration(value = Detonate.class, zenCodeName = "crafttweaker.neoforge.api.event.DetonateExplosionEvent")
   public static class ExpandDetonateExplosionEvent {
      @ZenEvent.Bus
      public static final IEventBus<Detonate> BUS = IEventBus.direct(Detonate.class, NeoForgeEventBusWire.of());

      @ZenCodeType.Getter("affectedBlocks")
      public static List<BlockPos> getAffectedBlocks(Detonate internal) {
         return internal.getAffectedBlocks();
      }

      @ZenCodeType.Getter("affectedEntities")
      public static List<Entity> getAffectedEntities(Detonate internal) {
         return internal.getAffectedEntities();
      }
   }

   @ZenEvent
   @ZenRegister
   @Document("neoforge/api/event/StartExplosionEvent")
   @NativeTypeRegistration(value = Start.class, zenCodeName = "crafttweaker.neoforge.api.event.StartExplosionEvent")
   public static class ExpandStartExplosionEvent {
      @ZenEvent.Bus
      public static final IEventBus<Start> BUS = IEventBus.cancelable(Start.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of());
   }
}
