package com.blamejared.crafttweaker.natives.event.entity.use;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/use/LivingEntityUseItemFinishEvent")
@NativeTypeRegistration(value = Finish.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.use.LivingEntityUseItemFinishEvent")
public class ExpandLivingEntityUseItemFinishEvent {
   @ZenEvent.Bus
   public static final IEventBus<Finish> BUS = IEventBus.direct(Finish.class, NeoForgeEventBusWire.of());

   @ZenCodeType.Getter("resultStack")
   public static IItemStack getResultStack(Finish internal) {
      return IItemStack.of(internal.getResultStack());
   }

   @ZenCodeType.Setter("resultStack")
   public static void setResultStack(Finish internal, IItemStack stack) {
      internal.setResultStack(stack.getInternal());
   }
}
