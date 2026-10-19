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

public class SentryOwnerTargetGoal extends ExtendedBehaviour<SentryEntity> {
   protected boolean checkExtraStartConditions(ServerLevel level, SentryEntity sentry) {
      LivingEntity owner = sentry.getOwner();
      if (owner == null) {
         return false;
      }

      LivingEntity target = owner.getLastHurtMob();
      return target != null && target.isAlive() && target != sentry;
   }

   protected void start(SentryEntity sentry) {
      LivingEntity owner = sentry.getOwner();
      if (owner != null) {
         LivingEntity target = owner.getLastHurtMob();
         if (target != null) {
            BrainUtils.setTargetOfEntity(sentry, target);
         }
      }
   }

   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return List.of();
   }
}
