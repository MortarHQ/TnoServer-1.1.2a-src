package net.tslat.smartbrainlib.api.core.behaviour.custom.path;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public class SetRandomHoverTarget<E extends PathfinderMob> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(1).noMemory(MemoryModuleType.WALK_TARGET);
   protected BiFunction<E, Vec3, Float> speedModifier = (entity, targetPos) -> 1.0F;
   protected SquareRadius radius = new SquareRadius(10.0, 7.0);
   protected BiPredicate<E, Vec3> positionPredicate = (entity, pos) -> true;

   public SetRandomHoverTarget<E> setRadius(double radius) {
      return this.setRadius(radius, radius);
   }

   public SetRandomHoverTarget<E> setRadius(double xz, double y) {
      this.radius = new SquareRadius(xz, y);
      return this;
   }

   public SetRandomHoverTarget<E> speedModifier(float modifier) {
      return this.speedModifier((entity, targetPos) -> modifier);
   }

   public SetRandomHoverTarget<E> speedModifier(BiFunction<E, Vec3, Float> function) {
      this.speedModifier = function;
      return this;
   }

   public SetRandomHoverTarget<E> flightTargetPredicate(BiPredicate<E, Vec3> predicate) {
      this.positionPredicate = predicate;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   protected void start(E entity) {
      Vec3 targetPos = this.getTargetPos(entity);
      if (!this.positionPredicate.test(entity, targetPos)) {
         targetPos = null;
      }

      if (targetPos == null) {
         BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
      } else {
         BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, this.speedModifier.apply(entity, targetPos), 0));
      }
   }

   @Nullable
   protected Vec3 getTargetPos(E entity) {
      Vec3 entityFacing = entity.getViewVector(0.0F);
      Vec3 hoverPos = HoverRandomPos.getPos(
         entity, (int)Math.ceil(this.radius.xzRadius()), (int)Math.ceil(this.radius.yRadius()), entityFacing.x, entityFacing.z, (float) (Math.PI / 2), 3, 1
      );
      return hoverPos != null
         ? hoverPos
         : AirAndWaterRandomPos.getPos(
            entity, (int)Math.ceil(this.radius.xzRadius()), (int)Math.ceil(this.radius.yRadius()), -2, entityFacing.x, entityFacing.z, (float) (Math.PI / 2)
         );
   }
}
