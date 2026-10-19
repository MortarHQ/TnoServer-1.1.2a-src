package net.tslat.smartbrainlib.api.core.behaviour.custom.attack;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.HeldBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public class ConditionlessHeldAttack<E extends LivingEntity> extends HeldBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(1).noMemory(MemoryModuleType.ATTACK_COOLING_DOWN);
   protected boolean requireTarget = false;
   @Nullable
   protected LivingEntity target = null;

   public ConditionlessHeldAttack<E> requiresTarget() {
      this.requireTarget = true;
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
   protected void stop(ServerLevel level, E entity, long gameTime) {
      super.stop(level, entity, gameTime);
      this.target = null;
   }
}
