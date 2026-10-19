package com.cerbon.bosses_of_mass_destruction.entity.util;

import java.util.Arrays;
import java.util.List;
import net.minecraft.world.level.Level;

public class CompositeEntityTick<T extends Level> implements IEntityTick<T> {
   private final List<IEntityTick<T>> tickList;

   @SafeVarargs
   public CompositeEntityTick(IEntityTick<T>... tickHandlers) {
      this.tickList = Arrays.asList(tickHandlers);
   }

   @Override
   public void tick(T level) {
      for (IEntityTick<T> tickHandler : this.tickList) {
         tickHandler.tick(level);
      }
   }
}
