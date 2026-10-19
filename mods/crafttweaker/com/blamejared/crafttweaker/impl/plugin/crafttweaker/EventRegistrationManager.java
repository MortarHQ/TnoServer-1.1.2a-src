package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.plugin.IEventRegistrationHandler;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.google.common.reflect.TypeToken;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.openzen.zencode.java.ZenCodeType;

final class EventRegistrationManager {
   private static final Lookup PUBLIC_LOOKUP = MethodHandles.publicLookup();

   void attemptRegistration(Class<?> clazz, IEventRegistrationHandler handler) {
      if (clazz.isAnnotationPresent(ZenEvent.class) || clazz.isAnnotationPresent(ZenEvent.BusCarrier.class)) {
         this.attemptEventRegistration(clazz, handler);
      }
   }

   private void attemptEventRegistration(Class<?> clazz, IEventRegistrationHandler handler) {
      List<Field> busCandidates = this.findBuses(clazz);
      if (busCandidates.isEmpty()) {
         throw new NoSuchElementException("Class " + clazz.getName() + " is annotated with @ZenEvent or @ZenEvent.BusCarrier, but carries no buses");
      }

      this.attemptEventRegistration(clazz, busCandidates, handler);
   }

   private List<Field> findBuses(Class<?> clazz) {
      return Arrays.stream(clazz.getFields())
         .filter(it -> Modifier.isStatic(it.getModifiers()) && Modifier.isFinal(it.getModifiers()))
         .filter(it -> it.getType() == IEventBus.class)
         .filter(it -> it.isAnnotationPresent(ZenEvent.Bus.class))
         .toList();
   }

   private void attemptEventRegistration(Class<?> clazz, List<Field> candidates, IEventRegistrationHandler handler) {
      candidates.forEach(candidate -> this.attemptEventRegistration(clazz, candidate, handler));
   }

   private void attemptEventRegistration(Class<?> clazz, Field candidate, IEventRegistrationHandler handler) {
      ZenEvent.Bus busAnnotation = candidate.getAnnotation(ZenEvent.Bus.class);
      Class<?> potentialTarget = busAnnotation.value();
      Class<?> target = this.determineCandidate(clazz, potentialTarget);
      VarHandle handle = this.varHandle(candidate);
      this.register(target, handle, handler);
   }

   private Class<?> determineCandidate(Class<?> owner, Class<?> potentialTarget) {
      if (potentialTarget != ZenEvent.Bus.Auto.class) {
         return potentialTarget;
      }

      if (owner.isAnnotationPresent(ZenEvent.class)) {
         if (owner.isAnnotationPresent(ZenCodeType.Name.class)) {
            return owner;
         }

         if (owner.isAnnotationPresent(NativeTypeRegistration.class)) {
            NativeTypeRegistration registration = owner.getAnnotation(NativeTypeRegistration.class);
            return registration.value();
         }
      }

      throw new IllegalStateException("Automatic determination of target failed");
   }

   private <T> void register(Class<T> target, VarHandle candidate, IEventRegistrationHandler handler) {
      IEventBus<T> bus = GenericUtil.uncheck((IEventBus)candidate.get());
      TypeToken<T> token = TypeToken.of(target);
      handler.registerEventMapping(token, bus);
   }

   private VarHandle varHandle(Field field) {
      try {
         return PUBLIC_LOOKUP.unreflectVarHandle(field);
      } catch (IllegalAccessException e) {
         throw new RuntimeException(e);
      }
   }
}
