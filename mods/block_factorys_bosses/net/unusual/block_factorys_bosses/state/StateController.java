package net.unusual.block_factorys_bosses.state;

import com.mojang.serialization.Codec;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@FieldsAreNonnullByDefault
public class StateController<E extends Entity> {
   private static final String TAG_STATE_CONTROLLER = "state_controller";
   private static final String TAG_STATE_STACK = "state_stack";
   private static final String TAG_NAME = "name";
   private static final String TAG_TIMER = "timer";
   private static final String TAG_DATA = "data";
   private final E entity;
   private final Function<StateController<E>, StateRef<E>> defaultStateSupplier;
   private final StateRegistry<E> registry;
   private final Deque<ActiveState<E>> stateStack = new ArrayDeque<>();

   public StateController(E entity, StateRegistry<E> stateRegistry, Function<StateController<E>, StateRef<E>> defaultStateSupplier) {
      this.entity = entity;
      this.registry = stateRegistry;
      this.defaultStateSupplier = defaultStateSupplier;
   }

   public E getEntity() {
      return this.entity;
   }

   public StateRegistry<E> getRegistry() {
      return this.registry;
   }

   @Nullable
   public ActiveState<E> getActiveState() {
      return this.stateStack.peekFirst();
   }

   @Nullable
   public StateRef<E> getActiveStateRef() {
      ActiveState<E> activeState = this.getActiveState();
      return activeState == null ? null : activeState.getStateRef();
   }

   public Deque<ActiveState<E>> getStateStack() {
      return this.stateStack;
   }

   public boolean hasActiveState(Predicate<? super ActiveState<E>> predicate) {
      return this.stateStack.stream().anyMatch(predicate);
   }

   public boolean hasActiveStateRef(StateRef<E> stateRef) {
      return this.hasActiveStateRef(it -> it == stateRef);
   }

   public boolean hasActiveStateRef(Predicate<? super StateRef<E>> predicate) {
      return this.stateStack.stream().map(ActiveState::getStateRef).anyMatch(predicate);
   }

   @Nullable
   public ActiveState<E> findActiveState(Predicate<? super ActiveState<E>> predicate) {
      return this.stateStack.stream().filter(predicate).findFirst().orElse(null);
   }

   @Nullable
   public ActiveState<E> findActiveStateByRef(StateRef<E> stateRef) {
      return this.stateStack.stream().filter(state -> state.getStateRef() == stateRef).findFirst().orElse(null);
   }

   @Nullable
   public ActiveState<E> findActiveStateByRef(Predicate<? super StateRef<E>> predicate) {
      return this.stateStack.stream().filter(state -> predicate.test(state.getStateRef())).findFirst().orElse(null);
   }

   public boolean isStateActive(@Nullable StateRef<E> state) {
      ActiveState<E> activeState = this.getActiveState();
      return activeState == null ? state == null : activeState.getStateRef() == state;
   }

   @Nullable
   public State<E> getState() {
      ActiveState<E> activeState = this.getActiveState();
      return activeState == null ? null : activeState.getState();
   }

   private ActiveState<E> createInstance(StateRef<E> stateRef) {
      State<E> state = Objects.requireNonNull(this.getRegistry().getState(stateRef));
      return state instanceof DataState<E, StateData> dataState
         ? new ActiveDataState<>(this, stateRef, dataState, dataState.createData())
         : new ActiveState<>(this, stateRef, state);
   }

   public ActiveState<E> push(StateRef<E> state) {
      ActiveState<E> activeState = this.createInstance(state);
      this.stateStack.addFirst(activeState);
      return activeState;
   }

   public ActiveState<E> pushBefore(ActiveState<E> before, StateRef<E> state) {
      ActiveState<E> activeState = null;
      ArrayList<ActiveState<E>> tempStack = new ArrayList<>(this.stateStack.size());

      while (true) {
         ActiveState<E> next = this.stateStack.pollFirst();
         if (next == null) {
            break;
         }

         tempStack.add(next);
         if (next == before) {
            activeState = this.push(state);
            break;
         }
      }

      while (!tempStack.isEmpty()) {
         this.stateStack.addFirst(tempStack.removeLast());
      }

      if (activeState == null) {
         throw new IndexOutOfBoundsException("StateController doesn't have the given state " + before);
      } else {
         return activeState;
      }
   }

   public ActiveState<E> replaceActive(StateRef<E> state) {
      this.endActive();
      return this.push(state);
   }

   public ActiveState<E> replaceAll(StateRef<E> state) {
      this.endAll();
      return this.push(state);
   }

   @Nullable
   public ActiveState<E> endActive() {
      ActiveState<E> activeState = this.stateStack.removeFirst();
      if (activeState == null) {
         return null;
      }

      activeState.getState().onEnd(activeState);
      return activeState;
   }

   public void endDownTo(ActiveState<E> activeState) {
      if (this.stateStack.contains(activeState)) {
         while (this.endActive() != activeState) {
         }
      }
   }

   public void endAll() {
      while (!this.stateStack.isEmpty()) {
         this.endActive();
      }
   }

   public void tick() {
      ActiveState<E> activeState = this.stateStack.peekFirst();
      if (activeState == null) {
         StateRef<E> newState = this.defaultStateSupplier.apply(this);
         if (newState == null) {
            return;
         }

         activeState = this.push(newState);
      }

      State.Result result = activeState.tick();
      if (result == State.Result.END) {
         if (this.stateStack.getFirst() == activeState) {
            this.endActive();
         }

         assert !this.stateStack.contains(activeState);
      }
   }

   public void addAdditionalSaveData(CompoundTag entityCompound) {
      CompoundTag stateControllerCompound = new CompoundTag();
      ListTag stackList = new ListTag(this.stateStack.size());

      for (ActiveState<E> activeState : this.stateStack) {
         CompoundTag activeStateCompound = new CompoundTag();
         activeStateCompound.putString("name", activeState.getStateRef().getName());
         activeStateCompound.putInt("timer", activeState.getTimer());
         if (activeState instanceof ActiveDataState<?, ?> activeDataState) {
            StateData stateData = activeDataState.getData();
            Codec<StateData> dataCodec = (Codec<StateData>)activeDataState.getState().getDataCodec();
            Tag dataCompound = (Tag)dataCodec.encodeStart(NbtOps.INSTANCE, stateData).getOrThrow();
            activeStateCompound.put("data", dataCompound);
         }

         stackList.add(activeStateCompound);
      }

      stateControllerCompound.put("state_stack", stackList);
      entityCompound.put("state_controller", stateControllerCompound);
   }

   public void readAdditionalSaveData(CompoundTag entityCompound) {
      assert this.stateStack.isEmpty();
      if (entityCompound.contains("state_controller")) {
         CompoundTag stateControllerCompound = entityCompound.getCompound("state_controller");

         for (Tag tag : stateControllerCompound.getList("state_stack", 10)) {
            if (tag instanceof CompoundTag activeStateCompound) {
               StateRef<E> stateRef = Objects.requireNonNull(this.getRegistry().getStateRef(activeStateCompound.getString("name")));
               State<E> state = Objects.requireNonNull(this.getRegistry().getState(stateRef));
               ActiveState<E> activeState;
               if (state instanceof DataState<E, StateData> dataState) {
                  CompoundTag stateDataCompound = activeStateCompound.getCompound("data");
                  StateData stateData = (StateData)((DataState)state).getDataCodec().decode(NbtOps.INSTANCE, stateDataCompound).getOrThrow();
                  activeState = new ActiveDataState<>(this, stateRef, dataState, stateData);
               } else {
                  activeState = new ActiveState<>(this, stateRef, state);
               }

               if (activeStateCompound.contains("timer")) {
                  activeState.setTimer(activeStateCompound.getInt("timer"));
               }

               this.stateStack.addFirst(activeState);
            }
         }
      }
   }
}
