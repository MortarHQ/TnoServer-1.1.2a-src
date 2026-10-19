package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import java.util.Map;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class CrabLayEggTask extends Behavior<CrabEntity> {
   private static final float WITHING_DISTANCE = 2.0F;

   public CrabLayEggTask() {
      super(
         Map.of(
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(),
            MemoryStatus.VALUE_PRESENT,
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(),
            MemoryStatus.VALUE_PRESENT
         ),
         100
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel world, CrabEntity crab) {
      return crab.getBurrowSpotPos() != null
         && crab.isBurrowSpotAccessible(crab.getBurrowSpotPos().pos())
         && crab.getBurrowSpotPos().pos().closerToCenterThan(crab.position(), 2.0)
         && !crab.getNavigation().isInProgress();
   }

   protected void start(ServerLevel world, CrabEntity crab, long time) {
   }

   protected boolean canStillUse(ServerLevel world, CrabEntity crab, long time) {
      return true;
   }

   protected void tick(ServerLevel world, CrabEntity crab, long time) {
      GlobalPos burrowSpotPos = crab.getBurrowSpotPos();
      if (burrowSpotPos != null && time % 5L == 0L) {
         world.levelEvent(2001, burrowSpotPos.pos(), Block.getId(world.getBlockState(burrowSpotPos.pos().below())));
      }
   }

   protected void stop(ServerLevel world, CrabEntity crab, long time) {
      GlobalPos burrowSpotPos = crab.getBurrowSpotPos();
      if (burrowSpotPos != null) {
         world.playSound(
            null,
            burrowSpotPos.pos(),
            (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_CRAB_LAY_EGG.get(),
            SoundSource.BLOCKS,
            0.3F,
            0.9F + world.random.nextFloat() * 0.2F
         );
         world.setBlock(
            burrowSpotPos.pos(),
            (BlockState)((Block)FriendsAndFoesBlocks.CRAB_EGG.get()).defaultBlockState().setValue(TurtleEggBlock.EGGS, crab.getRandom().nextInt(4) + 1),
            3
         );
      }

      crab.setHasEgg(false);
      crab.setInLoveTime(600);
      crab.getBrain().eraseMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get());
   }
}
