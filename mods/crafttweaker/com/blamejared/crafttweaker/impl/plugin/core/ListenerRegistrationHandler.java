package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IListenerRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

final class ListenerRegistrationHandler implements IListenerRegistrationHandler {
   private final List<Runnable> endListeners = new ArrayList<>();
   private final List<Runnable> zenListeners = new ArrayList<>();
   private final List<Consumer<ScriptRunConfiguration>> executeRunListeners = new LinkedList<>();

   private ListenerRegistrationHandler() {
   }

   static ListenerRegistrationHandler of(Consumer<IListenerRegistrationHandler> consumer) {
      ListenerRegistrationHandler handler = new ListenerRegistrationHandler();
      consumer.accept(handler);
      return handler;
   }

   @Override
   public void onZenDataRegistrationCompletion(Runnable runnable) {
      this.zenListeners.add(runnable);
   }

   @Override
   public void onCraftTweakerLoadCompletion(Runnable runnable) {
      this.endListeners.add(runnable);
   }

   @Override
   public void onExecuteRun(Consumer<ScriptRunConfiguration> executionConsumer) {
      this.executeRunListeners.add(executionConsumer);
   }

   List<Runnable> endListeners() {
      return this.endListeners;
   }

   List<Runnable> zenListeners() {
      return this.zenListeners;
   }

   List<Consumer<ScriptRunConfiguration>> executeRunListeners() {
      return this.executeRunListeners;
   }
}
