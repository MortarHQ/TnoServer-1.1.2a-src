package com.faboslav.friendsandfoes.common.entity.ai.brain.task.tuffgolem;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.util.MovementUtil;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;

public final class TuffGolemGoToHomePositionTask extends Behavior<TuffGolemEntity> {
   private static final int GO_TO_SLEEP_POSITION_DURATION = 2400;

   public TuffGolemGoToHomePositionTask() {
      super(
         ImmutableMap.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.VALUE_ABSENT,
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(),
            MemoryStatus.VALUE_ABSENT
         ),
         2400
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel world, TuffGolemEntity tuffGolem) {
      return !tuffGolem.isInSleepingPose()
         && !tuffGolem.isAtHomePos()
         && !tuffGolem.isGlued()
         && !tuffGolem.isLeashed()
         && !tuffGolem.isPassenger()
         && tuffGolem.getBrain().getMemoryInternal((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get()).isEmpty();
   }

   protected void start(ServerLevel serverWorld, TuffGolemEntity tuffGolem, long l) {
      this.walkTowardsHomePos(tuffGolem);
   }

   protected boolean canStillUse(ServerLevel world, TuffGolemEntity tuffGolem, long time) {
      return !tuffGolem.isAtHome()
         && !tuffGolem.isGlued()
         && !tuffGolem.isLeashed()
         && !tuffGolem.isPassenger()
         && tuffGolem.getBrain().getMemoryInternal((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get()).isEmpty();
   }

   protected void tick(ServerLevel world, TuffGolemEntity tuffGolem, long time) {
      if (tuffGolem.getNavigation().isDone()) {
         this.walkTowardsHomePos(tuffGolem);
      }

      if (tuffGolem.isCloseToHomePos(2.0F) && Math.abs((int)tuffGolem.getY() - (int)tuffGolem.getHomePos().y()) <= 1) {
         tuffGolem.setDeltaMovement(
            new Vec3(
               tuffGolem.getHomePos().x() - tuffGolem.getX(), tuffGolem.getHomePos().y() - tuffGolem.getY(), tuffGolem.getHomePos().z() - tuffGolem.getZ()
            )
         );
         tuffGolem.setSpawnYaw(tuffGolem.getHomeYaw());
      }
   }

   protected void stop(ServerLevel world, TuffGolemEntity tuffGolem, long time) {
      if (tuffGolem.isCloseToHomePos(1.5F)) {
         tuffGolem.setPos(tuffGolem.getHomePos());
      }

      MovementUtil.stopMovement(tuffGolem);
   }

   private void walkTowardsHomePos(TuffGolemEntity tuffGolem) {
      BehaviorUtils.setWalkAndLookTargetMemories(
         tuffGolem, new BlockPos((int)tuffGolem.getHomePos().x(), (int)tuffGolem.getHomePos().y(), (int)tuffGolem.getHomePos().z()), 0.6F, 0
      );
   }
}
