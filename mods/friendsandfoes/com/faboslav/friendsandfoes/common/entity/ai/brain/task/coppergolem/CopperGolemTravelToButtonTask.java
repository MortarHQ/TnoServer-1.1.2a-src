package com.faboslav.friendsandfoes.common.entity.ai.brain.task.coppergolem;

import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public final class CopperGolemTravelToButtonTask extends Behavior<CopperGolemEntity> {
   private static final int MAX_TRAVELLING_TICKS = 600;
   private static final float WITHING_DISTANCE = 1.5F;

   public CopperGolemTravelToButtonTask() {
      super(
         Map.of(
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get(),
            MemoryStatus.VALUE_PRESENT,
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(),
            MemoryStatus.VALUE_ABSENT
         ),
         600
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel world, CopperGolemEntity copperGolem) {
      GlobalPos buttonPos = copperGolem.getButtonPos();
      return buttonPos != null && copperGolem.isButtonValidToBePressed(buttonPos.pos());
   }

   protected void start(ServerLevel world, CopperGolemEntity copperGolem, long time) {
      this.walkTowardsButton(copperGolem);
   }

   protected boolean canStillUse(ServerLevel world, CopperGolemEntity copperGolem, long time) {
      GlobalPos buttonPos = copperGolem.getButtonPos();
      return buttonPos != null
         && copperGolem.isButtonValidToBePressed(buttonPos.pos())
         && (!buttonPos.pos().closerToCenterThan(copperGolem.position(), 1.5) || copperGolem.getNavigation().isInProgress())
         && !copperGolem.isOxidized();
   }

   protected void tick(ServerLevel world, CopperGolemEntity copperGolem, long time) {
      if (!copperGolem.getNavigation().isInProgress()) {
         this.walkTowardsButton(copperGolem);
      }
   }

   protected void stop(ServerLevel world, CopperGolemEntity copperGolem, long time) {
      copperGolem.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      GlobalPos buttonPos = copperGolem.getButtonPos();
      if (buttonPos != null && (!buttonPos.pos().closerToCenterThan(copperGolem.position(), 1.5) || !copperGolem.isButtonValidToBePressed(buttonPos.pos()))) {
         copperGolem.getBrain().eraseMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get());
      }
   }

   private void walkTowardsButton(CopperGolemEntity copperGolem) {
      GlobalPos buttonPos = copperGolem.getButtonPos();
      if (buttonPos != null) {
         BehaviorUtils.setWalkAndLookTargetMemories(copperGolem, new BlockPos(buttonPos.pos()), 1.0F, 0);
      }
   }
}
