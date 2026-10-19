package net.tslat.smartbrainlib.api.core.behaviour.custom.target;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.FreePositionTracker;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;

public class SetRandomLookTarget<E extends Mob> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(1).noMemory(MemoryModuleType.LOOK_TARGET);
   protected FloatProvider runChance = ConstantFloat.of(0.02F);
   protected Function<E, Integer> lookTime = entity -> entity.getRandom().nextInt(20) + 20;

   public SetRandomLookTarget<E> lookChance(FloatProvider chance) {
      this.runChance = chance;
      return this;
   }

   public SetRandomLookTarget<E> lookTime(Function<E, Integer> function) {
      this.lookTime = function;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      return entity.getRandom().nextFloat() < this.runChance.sample(entity.getRandom());
   }

   protected void start(E entity) {
      double angle = (float) (Math.PI * 2) * entity.getRandom().nextDouble();
      BrainUtils.setForgettableMemory(
         entity,
         MemoryModuleType.LOOK_TARGET,
         new FreePositionTracker(entity.getEyePosition().add(Math.cos(angle), 0.0, Math.sin(angle))),
         this.lookTime.apply(entity)
      );
   }
}
