package net.tslat.smartbrainlib.api.core.behaviour.custom.attack;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public class AnimatableMeleeAttack<E extends Mob> extends DelayedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(2)
      .hasMemory(MemoryModuleType.ATTACK_TARGET)
      .noMemory(MemoryModuleType.ATTACK_COOLING_DOWN);
   protected Function<E, Integer> attackIntervalSupplier = entity -> 20;
   @Nullable
   protected LivingEntity target = null;

   public AnimatableMeleeAttack(int delayTicks) {
      super(delayTicks);
   }

   public AnimatableMeleeAttack<E> attackInterval(Function<E, Integer> supplier) {
      this.attackIntervalSupplier = supplier;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      this.target = BrainUtils.getTargetOfEntity(entity);
      return entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target);
   }

   protected void start(E entity) {
      entity.swing(InteractionHand.MAIN_HAND);
      BehaviorUtils.lookAtEntity(entity, this.target);
   }

   protected void stop(E entity) {
      this.target = null;
   }

   protected void doDelayedAction(E entity) {
      BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));
      if (this.target != null) {
         if (entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target)) {
            entity.doHurtTarget(this.target);
         }
      }
   }
}
