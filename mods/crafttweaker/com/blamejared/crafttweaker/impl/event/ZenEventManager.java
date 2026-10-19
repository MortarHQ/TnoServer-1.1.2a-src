package com.blamejared.crafttweaker.impl.event;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.event.ActionRegisterEvent;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.IHandlerToken;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.reflect.TypeToken;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/EventManager")
@ZenCodeType.Name("crafttweaker.api.events.EventManager")
public final class ZenEventManager {
   @ZenCodeGlobals.Global("events")
   public static final ZenEventManager EVENTS = new ZenEventManager();

   private ZenEventManager() {
   }

   @ZenCodeType.Method
   public <T> void register(Class<T> typeOfT, Consumer<T> consumer) {
      this.registerOnBus(typeOfT, consumer, IEventBus::registerHandler);
   }

   @ZenCodeType.Method
   public <T> void register(Class<T> typeOfT, ZenEventPhase phase, Consumer<T> consumer) {
      this.registerOnBus(typeOfT, consumer, (bus, event) -> bus.registerHandler(phase.phase(), event));
   }

   @ZenCodeType.Method
   public <T> void register(Class<T> typeOfT, boolean listenToCanceled, Consumer<T> consumer) {
      this.registerOnBus(typeOfT, consumer, (bus, event) -> bus.registerHandler(listenToCanceled, event));
   }

   @ZenCodeType.Method
   public <T> void register(Class<T> typeOfT, ZenEventPhase phase, boolean listenToCanceled, Consumer<T> consumer) {
      this.registerOnBus(typeOfT, consumer, (bus, event) -> bus.registerHandler(phase.phase(), listenToCanceled, event));
   }

   private <T> void registerOnBus(Class<T> typeOfT, Consumer<T> consumer, BiFunction<IEventBus<T>, Consumer<T>, IHandlerToken<T>> regFun) {
      CraftTweakerAPI.apply(ActionRegisterEvent.of(TypeToken.of(typeOfT), consumer, regFun));
   }
}
