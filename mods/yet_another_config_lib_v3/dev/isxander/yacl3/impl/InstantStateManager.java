package dev.isxander.yacl3.impl;

import dev.isxander.yacl3.api.Binding;
import dev.isxander.yacl3.api.StateManager;
import java.util.Objects;

public class InstantStateManager<T> implements StateManager<T>, ProvidesBindingForDeprecation<T> {
   private final Binding<T> binding;
   private StateManager.StateListener<T> stateListener;

   public InstantStateManager(Binding<T> binding) {
      this.binding = binding;
      this.stateListener = StateManager.StateListener.noop();
   }

   @Override
   public void set(T value) {
      boolean changed = !Objects.equals(this.get(), value);
      this.binding.setValue(value);
      if (changed) {
         this.stateListener.onStateChange(this.get(), value);
      }
   }

   @Override
   public T get() {
      return this.binding.getValue();
   }

   @Override
   public void apply() {
   }

   @Override
   public void resetToDefault(StateManager.ResetAction action) {
      this.set(this.binding.defaultValue());
   }

   @Override
   public void sync() {
   }

   @Override
   public boolean isSynced() {
      return true;
   }

   @Override
   public boolean isAlwaysSynced() {
      return true;
   }

   @Override
   public boolean isDefault() {
      return this.binding.defaultValue().equals(this.get());
   }

   @Override
   public void addListener(StateManager.StateListener<T> stateListener) {
      this.stateListener = this.stateListener.andThen(stateListener);
   }

   @Override
   public Binding<T> getBinding() {
      return this.binding;
   }
}
