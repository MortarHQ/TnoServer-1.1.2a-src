package net.tslat.smartbrainlib.api.core.sensor;

import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public abstract class EntityFilteringSensor<P, E extends LivingEntity> extends PredicateSensor<LivingEntity, E> {
   protected abstract MemoryModuleType<P> getMemory();

   @Override
   protected abstract BiPredicate<LivingEntity, E> predicate();

   @Override
   public List<MemoryModuleType<?>> memoriesUsed() {
      return List.of(this.getMemory());
   }

   @Override
   protected void doTick(ServerLevel level, E entity) {
      BrainUtils.setMemory(entity, this.getMemory(), this.testForEntity(entity));
   }

   protected P testForEntity(E entity) {
      NearestVisibleLivingEntities matcher = BrainUtils.getMemory(entity, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
      return matcher == null ? null : this.findMatches(entity, matcher);
   }

   @Nullable
   protected abstract P findMatches(E var1, NearestVisibleLivingEntities var2);
}
