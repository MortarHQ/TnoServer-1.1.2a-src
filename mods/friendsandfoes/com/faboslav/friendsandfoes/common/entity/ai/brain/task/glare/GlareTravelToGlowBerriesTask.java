package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public final class GlareTravelToGlowBerriesTask extends Behavior<GlareEntity> {
   private static final int MAX_TRAVELLING_TICKS = 300;
   private static final float WITHING_DISTANCE = 1.5F;

   public GlareTravelToGlowBerriesTask() {
      super(Map.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get(), MemoryStatus.VALUE_PRESENT), 300);
   }

   protected boolean checkExtraStartConditions(ServerLevel world, GlareEntity glare) {
      GlobalPos glowBerriesPos = glare.getGlowBerriesPos();
      return FriendsAndFoes.getConfig().enableGlareGriefing
         && !glare.isLeashed()
         && !glare.isOrderedToSit()
         && glare.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()
         && glare.canEatGlowBerriesAt(glowBerriesPos.pos())
         && glowBerriesPos != null
         && !glowBerriesPos.pos().closerToCenterThan(glare.position(), 1.5);
   }

   protected void start(ServerLevel world, GlareEntity glare, long time) {
      this.flyTowardsGlowBerries(glare);
   }

   protected boolean canStillUse(ServerLevel world, GlareEntity glare, long time) {
      GlobalPos glowBerriesPos = glare.getGlowBerriesPos();
      return glowBerriesPos != null
         && glare.canEatGlowBerriesAt(glowBerriesPos.pos())
         && (!glowBerriesPos.pos().closerToCenterThan(glare.position(), 1.5) || glare.getNavigation().isInProgress())
         && FriendsAndFoes.getConfig().enableGlareGriefing
         && !glare.isLeashed()
         && !glare.isOrderedToSit()
         && glare.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
   }

   protected void tick(ServerLevel world, GlareEntity glare, long time) {
      if (!glare.getNavigation().isInProgress()) {
         this.flyTowardsGlowBerries(glare);
      }
   }

   protected void stop(ServerLevel world, GlareEntity glare, long time) {
      glare.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      GlobalPos glowBerriesPos = glare.getGlowBerriesPos();
      if (glowBerriesPos != null && (!glowBerriesPos.pos().closerToCenterThan(glare.position(), 1.5) || !glare.canEatGlowBerriesAt(glowBerriesPos.pos()))) {
         glare.getBrain().eraseMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get());
         GlareBrain.setLocatingGlowBerriesCooldown(glare, TimeUtil.rangeOfSeconds(10, 20));
      }
   }

   private void flyTowardsGlowBerries(GlareEntity glare) {
      GlobalPos glowBerriesPos = glare.getGlowBerriesPos();
      if (glowBerriesPos != null) {
         BehaviorUtils.setWalkAndLookTargetMemories(glare, new BlockPos(glowBerriesPos.pos()), 1.0F, 0);
      }
   }
}
