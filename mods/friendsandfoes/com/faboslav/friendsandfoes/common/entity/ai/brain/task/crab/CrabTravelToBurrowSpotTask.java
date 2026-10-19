package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public final class CrabTravelToBurrowSpotTask extends Behavior<CrabEntity> {
   private static final int MAX_TRAVELLING_TICKS = 600;
   private static final float WITHING_DISTANCE = 1.5F;

   public CrabTravelToBurrowSpotTask() {
      super(
         Map.of(
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(),
            MemoryStatus.VALUE_PRESENT,
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(),
            MemoryStatus.VALUE_PRESENT
         ),
         600
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel world, CrabEntity crab) {
      GlobalPos burrowSpotPos = crab.getBurrowSpotPos();
      return burrowSpotPos != null;
   }

   protected void start(ServerLevel world, CrabEntity crab, long time) {
      this.walkTowardsBurrowSpot(crab);
   }

   protected boolean canStillUse(ServerLevel world, CrabEntity crab, long time) {
      GlobalPos burrowSpotPos = crab.getBurrowSpotPos();
      return burrowSpotPos != null && crab.isBurrowSpotAccessible(burrowSpotPos.pos());
   }

   protected void tick(ServerLevel world, CrabEntity crab, long time) {
      if (!crab.getNavigation().isInProgress()) {
         this.walkTowardsBurrowSpot(crab);
      }
   }

   protected void stop(ServerLevel world, CrabEntity crab, long time) {
      crab.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      GlobalPos burrowSpotPos = crab.getBurrowSpotPos();
      if (burrowSpotPos != null && (!burrowSpotPos.pos().closerToCenterThan(crab.position(), 1.5) || !crab.isBurrowSpotAccessible(burrowSpotPos.pos()))) {
         crab.setHasEgg(false);
         crab.setInLoveTime(600);
         crab.getBrain().eraseMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get());
      }
   }

   private void walkTowardsBurrowSpot(CrabEntity crab) {
      GlobalPos burrowSpotPos = (GlobalPos)crab.getBrain()
         .getMemoryInternal((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get())
         .orElse(null);
      if (burrowSpotPos != null) {
         BehaviorUtils.setWalkAndLookTargetMemories(crab, new BlockPos(burrowSpotPos.pos()), 0.6F, 0);
      }
   }
}
