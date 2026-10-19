package net.unusual.block_factorys_bosses.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.world.entity.Entity;

@FieldsAreNonnullByDefault
public class StateSequence<E extends Entity> implements State<E> {
   private final List<StateRef<E>> sequence;

   public StateSequence(List<StateRef<E>> sequence) {
      if (sequence.isEmpty()) {
         throw new IllegalArgumentException("Tried to create an empty StateSequence");
      }

      this.sequence = new ArrayList<>(sequence);
   }

   @Nonnull
   @Override
   public State.Result onTick(ActiveState<E> state) {
      int sequenceIndex = state.getTimer();
      if (sequenceIndex >= this.sequence.size()) {
         return State.Result.END;
      }

      state.getController().push(this.sequence.get(sequenceIndex));
      state.getController().tick();
      return State.Result.CONTINUE;
   }

   @SafeVarargs
   public static <E extends Entity> StateSequence<E> of(StateRef<E>... states) {
      return new StateSequence<>(Arrays.asList(states));
   }

   public static class StateSequenceBuilder<E extends Entity> implements State.StateBuilder<E> {
      private final List<StateRef<E>> sequence = new ArrayList<>();

      public StateSequence.StateSequenceBuilder<E> then(StateRef<E> state) {
         this.sequence.add(state);
         return this;
      }

      public StateSequence.StateSequenceBuilder<E> then(List<StateRef<E>> states) {
         this.sequence.addAll(states);
         return this;
      }

      @SafeVarargs
      public final StateSequence.StateSequenceBuilder<E> then(StateRef<E>... states) {
         this.sequence.addAll(Arrays.asList(states));
         return this;
      }

      public StateSequence<E> build() {
         return new StateSequence<>(this.sequence);
      }
   }
}
