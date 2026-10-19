package net.tslat.smartbrainlib.api.core.behaviour;

import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior.Status;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.object.SBLShufflingList;
import org.jetbrains.annotations.Nullable;

public abstract class GroupBehaviour<E extends LivingEntity> extends ExtendedBehaviour<E> {
   protected final SBLShufflingList<ExtendedBehaviour<? super E>> behaviours;
   @Nullable
   protected ExtendedBehaviour<? super E> runningBehaviour = null;

   @SafeVarargs
   public GroupBehaviour(Pair<ExtendedBehaviour<? super E>, Integer>... behaviours) {
      this.behaviours = new SBLShufflingList<>(behaviours);
      this.noTimeout();
   }

   @SafeVarargs
   public GroupBehaviour(ExtendedBehaviour<? super E>... behaviours) {
      this.behaviours = new SBLShufflingList<>();

      for (ExtendedBehaviour<? super E> behaviour : behaviours) {
         this.behaviours.add(behaviour, 1);
      }

      this.noTimeout();
   }

   public GroupBehaviour(Collection<Pair<ExtendedBehaviour<? super E>, Integer>> behaviours) {
      this.behaviours = new SBLShufflingList<>(behaviours);
      this.noTimeout();
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return List.of();
   }

   public Iterator<ExtendedBehaviour<? super E>> getBehaviours() {
      return this.behaviours.iterator();
   }

   @Nullable
   protected abstract ExtendedBehaviour<? super E> pickBehaviour(ServerLevel var1, E var2, long var3, SBLShufflingList<ExtendedBehaviour<? super E>> var5);

   @Override
   protected boolean doStartCheck(ServerLevel level, E entity, long gameTime) {
      return !super.doStartCheck(level, entity, gameTime)
         ? false
         : (this.runningBehaviour = this.pickBehaviour(level, entity, gameTime, this.behaviours)) != null;
   }

   @Override
   protected boolean shouldKeepRunning(E entity) {
      return this.runningBehaviour != null && this.runningBehaviour.canStillUse((ServerLevel)entity.level(), entity, entity.level().getGameTime());
   }

   @Override
   protected boolean timedOut(long gameTime) {
      return this.runningBehaviour == null || this.runningBehaviour.timedOut(gameTime);
   }

   @Override
   protected void tick(ServerLevel level, E owner, long gameTime) {
      this.runningBehaviour.tickOrStop(level, owner, gameTime);
      if (this.runningBehaviour.getStatus() == Status.STOPPED) {
         this.runningBehaviour = null;
         this.doStop(level, owner, gameTime);
      }
   }

   @Override
   protected void stop(ServerLevel level, E entity, long gameTime) {
      super.stop(level, entity, gameTime);
      if (this.runningBehaviour != null) {
         this.runningBehaviour.stop(level, entity, gameTime);
      }

      this.runningBehaviour = null;
   }

   public Status getStatus() {
      return this.runningBehaviour == null ? Status.STOPPED : this.runningBehaviour.getStatus();
   }

   public String toString() {
      return "(" + this.getClass().getSimpleName() + "): " + (this.runningBehaviour != null ? this.runningBehaviour.toString() : "{}");
   }

   public String debugString() {
      return this.getClass().getSimpleName() + " -> " + (this.runningBehaviour != null ? this.runningBehaviour.debugString() : "{}");
   }
}
