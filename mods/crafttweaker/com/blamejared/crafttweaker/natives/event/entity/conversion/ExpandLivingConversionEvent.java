package com.blamejared.crafttweaker.natives.event.entity.conversion;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingConversionEvent;
import net.neoforged.neoforge.event.entity.living.LivingConversionEvent.Post;
import net.neoforged.neoforge.event.entity.living.LivingConversionEvent.Pre;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/entity/living/conversion/LivingConversionEvent")
@NativeTypeRegistration(value = LivingConversionEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.conversion.LivingConversionEvent")
public class ExpandLivingConversionEvent {
   @ZenRegister
   @ZenEvent
   @Document("neoforge/api/event/entity/conversion/LivingConversionPostEvent")
   @NativeTypeRegistration(value = Post.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.conversion.LivingConversionPostEvent")
   public static class ExpandLivingConversionPostEvent {
      @ZenEvent.Bus
      public static final IEventBus<Post> BUS = IEventBus.direct(Post.class, NeoForgeEventBusWire.of());

      @ZenCodeType.Getter("outcome")
      public static LivingEntity getOutcome(Post internal) {
         return internal.getOutcome();
      }
   }

   @ZenRegister
   @ZenEvent
   @Document("neoforge/api/event/entity/conversion/LivingConversionPreEvent")
   @NativeTypeRegistration(value = Pre.class, zenCodeName = "crafttweaker.neoforge.api.event.conversion.LivingConversionPreEvent")
   public static class ExpandLivingConversionPreEvent {
      @ZenEvent.Bus
      public static final IEventBus<Pre> BUS = IEventBus.cancelable(Pre.class, NeoForgeEventBusWire.of(), NeoForgeEventCancellationCarrier.of());

      @ZenCodeType.Getter("outcome")
      public static EntityType<Entity> getOutcome(Pre internal) {
         return GenericUtil.uncheck(internal.getOutcome());
      }

      @ZenCodeType.Setter("conversionTimer")
      public static void setConversionTimer(Pre internal, int ticks) {
         internal.setConversionTimer(ticks);
      }
   }
}
