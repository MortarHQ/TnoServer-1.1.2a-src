package net.tslat.smartbrainlib.api.core.behaviour;

import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public abstract class HeldBehaviour<E extends LivingEntity> extends ExtendedBehaviour<E> {
   protected Function<E, Boolean> tickConsumer = entity -> true;
   protected int runningTime = 0;

   public HeldBehaviour() {
      this.noTimeout();
   }

   public HeldBehaviour<E> onTick(Function<E, Boolean> tickConsumer) {
      this.tickConsumer = tickConsumer;
      return this;
   }

   public int getRunningTime() {
      return this.runningTime;
   }

   @Override
   protected boolean shouldKeepRunning(E entity) {
      return true;
   }

   @Override
   protected void start(ServerLevel level, E entity, long gameTime) {
      super.start(level, entity, gameTime);
      this.runningTime = 0;
   }

   @Override
   protected void tick(ServerLevel level, E owner, long gameTime) {
      super.tick(level, owner, gameTime);
      if (!this.tickConsumer.apply(owner)) {
         this.doStop(level, owner, gameTime);
      }

      this.runningTime++;
   }
}
