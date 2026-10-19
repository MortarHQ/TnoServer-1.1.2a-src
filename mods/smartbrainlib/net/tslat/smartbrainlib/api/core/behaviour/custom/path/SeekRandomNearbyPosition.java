package net.tslat.smartbrainlib.api.core.behaviour.custom.path;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.RandomUtil;
import org.jetbrains.annotations.Nullable;

public class SeekRandomNearbyPosition<E extends LivingEntity> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(1).noMemory(MemoryModuleType.WALK_TARGET);
   protected BiPredicate<E, BlockState> validPosition = (entity, state) -> false;
   protected BiFunction<E, Vec3, Float> speedModifier = (entity, targetPos) -> 1.0F;
   protected SquareRadius radius = new SquareRadius(10.0, 6.0);
   protected Function<E, Integer> tries = entity -> 10;
   protected Vec3 targetPos = null;

   public SeekRandomNearbyPosition<E> setRadius(double radius) {
      return this.setRadius(radius, radius);
   }

   public SeekRandomNearbyPosition<E> setRadius(double xz, double y) {
      this.radius = new SquareRadius(xz, y);
      return this;
   }

   public SeekRandomNearbyPosition<E> speedModifier(float modifier) {
      return this.speedModifier((entity, targetPos) -> modifier);
   }

   public SeekRandomNearbyPosition<E> speedModifier(BiFunction<E, Vec3, Float> function) {
      this.speedModifier = function;
      return this;
   }

   public SeekRandomNearbyPosition<E> attempts(int attempts) {
      return this.attempts(entity -> attempts);
   }

   public SeekRandomNearbyPosition<E> attempts(Function<E, Integer> function) {
      this.tries = function;
      return this;
   }

   public SeekRandomNearbyPosition<E> validPositions(BiPredicate<E, BlockState> predicate) {
      this.validPosition = predicate;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      this.targetPos = this.getTargetPos(entity);
      return this.targetPos != null;
   }

   @Override
   protected void start(E entity) {
      BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(this.targetPos, this.speedModifier.apply(entity, this.targetPos), 0));
   }

   @Nullable
   protected Vec3 getTargetPos(E entity) {
      BlockPos entityPos = entity.blockPosition();
      BlockPos targetPos = RandomUtil.getRandomPositionWithinRange(
         entityPos,
         (int)this.radius.xzRadius(),
         (int)this.radius.yRadius(),
         (int)this.radius.xzRadius(),
         0,
         0,
         0,
         false,
         entity.level(),
         10,
         (state, pos) -> this.validPosition.test(entity, state)
      );
      return targetPos == entityPos ? null : Vec3.atBottomCenterOf(targetPos);
   }
}
