package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.ArrayList;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public final class CrabLocateBurrowSpotTask extends Behavior<CrabEntity> {
   public CrabLocateBurrowSpotTask() {
      super(
         Map.of(
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(),
            MemoryStatus.VALUE_PRESENT,
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(),
            MemoryStatus.VALUE_ABSENT
         )
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel world, CrabEntity crab) {
      return crab.isCloseToHomePos(6.0F);
   }

   protected void start(ServerLevel world, CrabEntity crab, long time) {
      BlockPos burrowSpotPos = this.findRandomBurrowSpot(crab);
      if (burrowSpotPos == null) {
         crab.setHasEgg(false);
         crab.setInLoveTime(600);
      } else {
         ResourceKey<Level> registryKey = crab.level().dimension();
         crab.getBrain().setMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), GlobalPos.of(registryKey, burrowSpotPos));
      }
   }

   private ArrayList<BlockPos> findBurrowSpots(CrabEntity crab) {
      BlockPos blockPos = crab.blockPosition();
      ArrayList<BlockPos> darkSpots = new ArrayList<>();
      int searchDistance = 16;

      for (int i = 0; i <= searchDistance; i = i > 0 ? -i : 1 - i) {
         for (int j = 0; j < searchDistance; j++) {
            for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
               for (int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                  MutableBlockPos possibleBurrowSpotBlockPos = new MutableBlockPos();
                  possibleBurrowSpotBlockPos.setWithOffset(blockPos, k, i - 1, l);
                  boolean isBlockWithinDistance = blockPos.closerThan(possibleBurrowSpotBlockPos, searchDistance);
                  if (isBlockWithinDistance && crab.isBurrowSpotAccessible(possibleBurrowSpotBlockPos)) {
                     darkSpots.add(possibleBurrowSpotBlockPos);
                  }
               }
            }
         }
      }

      return darkSpots;
   }

   @Nullable
   private BlockPos findRandomBurrowSpot(CrabEntity crab) {
      ArrayList<BlockPos> burrowSpots = this.findBurrowSpots(crab);
      return burrowSpots.isEmpty() ? null : burrowSpots.get(crab.getRandom().nextInt(burrowSpots.size()));
   }
}
