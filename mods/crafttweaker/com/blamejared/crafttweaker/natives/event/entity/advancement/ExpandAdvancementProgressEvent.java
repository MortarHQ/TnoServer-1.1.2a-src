package com.blamejared.crafttweaker.natives.event.entity.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.AdvancementProgress;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent.AdvancementProgressEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent.AdvancementProgressEvent.ProgressType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/advancement/AdvancementProgressEvent")
@NativeTypeRegistration(value = AdvancementProgressEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.advancement.AdvancementProgressEvent")
public class ExpandAdvancementProgressEvent {
   @ZenEvent.Bus
   public static final IEventBus<AdvancementProgressEvent> BUS = IEventBus.direct(AdvancementProgressEvent.class, NeoForgeEventBusWire.of());

   @ZenCodeType.Getter("advancementProgress")
   public static AdvancementProgress getAdvancementProgress(AdvancementProgressEvent internal) {
      return internal.getAdvancementProgress();
   }

   @ZenCodeType.Getter("criterionName")
   public static String getCriterionName(AdvancementProgressEvent internal) {
      return internal.getCriterionName();
   }

   @ZenCodeType.Getter("progressType")
   public static ProgressType getProgressType(AdvancementProgressEvent internal) {
      return internal.getProgressType();
   }
}
