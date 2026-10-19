package net.tslat.smartbrainlib.api.core.behaviour.custom.attack;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public class ConditionlessAttack<E extends LivingEntity> extends DelayedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(1).noMemory(MemoryModuleType.ATTACK_COOLING_DOWN);
   protected Function<E, Integer> attackIntervalSupplier = entity -> 20;
   protected boolean requireTarget = false;
   protected Consumer<E> effect = entity -> {};
   @Nullable
   protected LivingEntity target = null;

   public ConditionlessAttack(int delayTicks) {
      super(delayTicks);
   }

   public ConditionlessAttack<E> attackInterval(Function<E, Integer> supplier) {
      this.attackIntervalSupplier = supplier;
      return this;
   }

   public ConditionlessAttack<E> requiresTarget() {
      this.requireTarget = true;
      return this;
   }

   public ConditionlessAttack<E> attack(Consumer<E> consumer) {
      this.effect = consumer;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      if (!this.requireTarget) {
         return true;
      }

      this.target = BrainUtils.getTargetOfEntity(entity);
      return this.target != null;
   }

   @Override
   protected void start(E entity) {
      entity.swing(InteractionHand.MAIN_HAND);
      if (this.requireTarget) {
         BehaviorUtils.lookAtEntity(entity, this.target);
      }
   }

   @Override
   protected void doDelayedAction(E entity) {
      if (!this.requireTarget || this.target != null) {
         this.effect.accept(entity);
         BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));
      }
   }
}
