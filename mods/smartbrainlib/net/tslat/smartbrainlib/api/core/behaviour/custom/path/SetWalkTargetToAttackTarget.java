package net.tslat.smartbrainlib.api.core.behaviour.custom.path;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.ToIntBiFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.apache.commons.lang3.function.ToBooleanBiFunction;

public class SetWalkTargetToAttackTarget<E extends Mob> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(3)
      .hasMemory(MemoryModuleType.ATTACK_TARGET)
      .usesMemories(MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET);
   @Deprecated(forRemoval = true)
   protected float speedModifier = 1.0F;
   protected BiFunction<E, LivingEntity, Float> speedMod = (owner, target) -> 1.0F;
   protected ToIntBiFunction<E, LivingEntity> closeEnoughWhen = (owner, target) -> 0;
   protected ToBooleanBiFunction<E, LivingEntity> targetEyePosition = (owner, target) -> false;

   @Deprecated(forRemoval = true)
   public SetWalkTargetToAttackTarget<E> speedMod(float speedModifier) {
      return this.speedMod((owner, target) -> speedModifier);
   }

   public SetWalkTargetToAttackTarget<E> speedMod(BiFunction<E, LivingEntity, Float> speedModifier) {
      this.speedMod = speedModifier;
      return this;
   }

   public SetWalkTargetToAttackTarget<E> closeEnoughDist(ToIntBiFunction<E, LivingEntity> closeEnoughMod) {
      this.closeEnoughWhen = closeEnoughMod;
      return this;
   }

   public SetWalkTargetToAttackTarget<E> targetEyePosition() {
      return this.targetEyePosition((owner, target) -> true);
   }

   public SetWalkTargetToAttackTarget<E> targetEyePosition(ToBooleanBiFunction<E, LivingEntity> function) {
      this.targetEyePosition = function;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   protected void start(E entity) {
      Brain<?> brain = entity.getBrain();
      LivingEntity target = BrainUtils.getTargetOfEntity(entity);
      if (entity.getSensing().hasLineOfSight(target) && BehaviorUtils.isWithinAttackRange(entity, target, 1)) {
         BrainUtils.clearMemory(brain, MemoryModuleType.WALK_TARGET);
      } else {
         BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityTracker(target, true));
         BrainUtils.setMemory(
            brain,
            MemoryModuleType.WALK_TARGET,
            new WalkTarget(
               new EntityTracker(target, this.targetEyePosition.applyAsBoolean(entity, target)),
               this.speedMod.apply(entity, target),
               this.closeEnoughWhen.applyAsInt(entity, target)
            )
         );
      }
   }
}
