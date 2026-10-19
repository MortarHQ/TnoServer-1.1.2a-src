package net.unusual.block_factorys_bosses.state;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ActiveState<E extends Entity> {
   private final StateController<E> controller;
   private final StateRef<E> stateRef;
   private final State<E> state;
   private int timer = -1;

   public ActiveState(StateController<E> controller, StateRef<E> stateRef, State<E> state) {
      this.controller = controller;
      this.stateRef = stateRef;
      this.state = state;
   }

   public StateController<E> getController() {
      return this.controller;
   }

   public E getEntity() {
      return this.controller.getEntity();
   }

   public StateRef<E> getStateRef() {
      return this.stateRef;
   }

   public State<E> getState() {
      return this.state;
   }

   public int getTimer() {
      return this.timer;
   }

   public void setTimer(int timer) {
      this.timer = timer;
   }

   public State.Result tick() {
      this.timer++;
      if (this.timer == 0) {
         this.state.onStart(this);
      }

      return this.state.onTick(this);
   }

   @Override
   public String toString() {
      return "ActiveState(stateRef = " + this.getStateRef().getName() + ", timer = " + this.getTimer() + ")";
   }
}
