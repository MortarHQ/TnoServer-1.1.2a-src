package net.tslat.smartbrainlib.api.core.behaviour.custom.move;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public class FollowParent<E extends AgeableMob> extends FollowEntity<E, AgeableMob> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(1).hasMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
   private BiPredicate<E, AgeableMob> parentPredicate = (entity, other) -> entity.getClass() == other.getClass() && other.getAge() >= 0;

   public FollowParent() {
      this.following(this::getParent);
      this.stopFollowingWithin(2.0);
   }

   public FollowParent<E> parentPredicate(BiPredicate<E, AgeableMob> predicate) {
      this.parentPredicate = predicate;
      return this;
   }

   @Override
   public List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      return entity.getAge() < 0 && super.checkExtraStartConditions(level, entity);
   }

   @Nullable
   protected AgeableMob getParent(E entity) {
      return BrainUtils.<NearestVisibleLivingEntities>getMemory(entity, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
         .findClosest(other -> other instanceof AgeableMob ageableMob && this.parentPredicate.test(entity, ageableMob))
         .map(AgeableMob.class::cast)
         .orElse(null);
   }
}
