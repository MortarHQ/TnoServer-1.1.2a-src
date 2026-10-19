package io.github.Memoires.mysticism.entity.goals;

import com.mojang.datafixers.util.Pair;
import io.github.Memoires.mysticism.entity.skill.SentryEntity;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

public class SentryShootGoal extends ExtendedBehaviour<SentryEntity> {
   protected boolean checkExtraStartConditions(ServerLevel level, SentryEntity sentry) {
      LivingEntity target = BrainUtils.getTargetOfEntity(sentry);
      return target != null && target.isAlive() && sentry.hasLineOfSight(target) && sentry.distanceToSqr(target) < 64.0;
   }

   protected void tick(SentryEntity sentry) {
      LivingEntity target = BrainUtils.getTargetOfEntity(sentry);
      if (target != null) {
         sentry.getLookControl().setLookAt(target);
      }
   }

   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return List.of();
   }
}
