package net.tslat.smartbrainlib.api.core.behaviour;

import java.util.function.Consumer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public abstract class DelayedBehaviour<E extends LivingEntity> extends ExtendedBehaviour<E> {
   protected final int delayTime;
   protected long delayFinishedAt = 0L;
   protected Consumer<E> delayedCallback = entity -> {};

   public DelayedBehaviour(int delayTicks) {
      this.delayTime = delayTicks;
      this.runFor(entity -> Math.max(delayTicks, 60));
   }

   public final DelayedBehaviour<E> whenActivating(Consumer<E> callback) {
      this.delayedCallback = callback;
      return this;
   }

   @Override
   protected final void start(ServerLevel level, E entity, long gameTime) {
      if (this.delayTime > 0) {
         this.delayFinishedAt = gameTime + this.delayTime;
         super.start(level, entity, gameTime);
      } else {
         super.start(level, entity, gameTime);
         this.doDelayedAction(entity);
      }
   }

   @Override
   protected final void stop(ServerLevel level, E entity, long gameTime) {
      super.stop(level, entity, gameTime);
      this.delayFinishedAt = 0L;
   }

   @Override
   protected boolean shouldKeepRunning(E entity) {
      return this.delayFinishedAt >= entity.level().getGameTime();
   }

   @Override
   protected final void tick(ServerLevel level, E entity, long gameTime) {
      super.tick(level, entity, gameTime);
      if (this.delayFinishedAt <= gameTime) {
         this.doDelayedAction(entity);
         this.delayedCallback.accept(entity);
      }
   }

   protected void doDelayedAction(E entity) {
   }
}
