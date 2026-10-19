package net.tslat.smartbrainlib.api.core.behaviour.custom.path;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.block.state.BlockState;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.util.BrainUtils;

public class SetWalkTargetToBlock<E extends PathfinderMob> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(1).hasMemory(SBLMemoryTypes.NEARBY_BLOCKS.get());
   protected BiPredicate<E, Pair<BlockPos, BlockState>> predicate = (entity, block) -> true;
   protected BiFunction<E, Pair<BlockPos, BlockState>, Float> speedMod = (owner, pos) -> 1.0F;
   protected BiFunction<E, Pair<BlockPos, BlockState>, Integer> closeEnoughDist = (entity, pos) -> 2;
   protected Pair<BlockPos, BlockState> target = null;

   public SetWalkTargetToBlock<E> predicate(BiPredicate<E, Pair<BlockPos, BlockState>> predicate) {
      this.predicate = predicate;
      return this;
   }

   public SetWalkTargetToBlock<E> speedMod(BiFunction<E, Pair<BlockPos, BlockState>, Float> speedModifier) {
      this.speedMod = speedModifier;
      return this;
   }

   public SetWalkTargetToBlock<E> closeEnoughWhen(BiFunction<E, Pair<BlockPos, BlockState>, Integer> function) {
      this.closeEnoughDist = function;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      for (Pair<BlockPos, BlockState> position : (List)BrainUtils.getMemory(entity, SBLMemoryTypes.NEARBY_BLOCKS.get())) {
         if (this.predicate.test(entity, position)) {
            this.target = position;
            break;
         }
      }

      return this.target != null;
   }

   protected void start(E entity) {
      BrainUtils.setMemory(
         entity,
         MemoryModuleType.WALK_TARGET,
         new WalkTarget((BlockPos)this.target.getFirst(), this.speedMod.apply(entity, this.target), this.closeEnoughDist.apply(entity, this.target))
      );
      BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new BlockPosTracker((BlockPos)this.target.getFirst()));
   }

   protected void stop(E entity) {
      this.target = null;
   }
}
