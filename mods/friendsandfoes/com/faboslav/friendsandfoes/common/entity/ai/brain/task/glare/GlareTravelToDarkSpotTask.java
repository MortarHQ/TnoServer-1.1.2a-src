package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public final class GlareTravelToDarkSpotTask extends Behavior<GlareEntity> {
   private static final int MAX_TRAVELLING_TICKS = 300;
   private static final float WITHING_DISTANCE = 1.5F;

   public GlareTravelToDarkSpotTask() {
      super(Map.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(), MemoryStatus.VALUE_PRESENT), 300);
   }

   protected boolean checkExtraStartConditions(ServerLevel world, GlareEntity glare) {
      GlobalPos darkSpotPos = glare.getDarkSpotPos();
      return canTravelToDarkSpot(glare) && !darkSpotPos.pos().closerToCenterThan(glare.position(), 1.5);
   }

   protected void start(ServerLevel world, GlareEntity glare, long time) {
      this.flyTowardsDarkSpot(glare);
   }

   protected boolean canStillUse(ServerLevel world, GlareEntity glare, long time) {
      GlobalPos darkSpotPos = glare.getDarkSpotPos();
      return canTravelToDarkSpot(glare) && (!darkSpotPos.pos().closerToCenterThan(glare.position(), 1.5) || glare.getNavigation().isInProgress());
   }

   protected void tick(ServerLevel world, GlareEntity glare, long time) {
      if (!glare.getNavigation().isInProgress()) {
         this.flyTowardsDarkSpot(glare);
      }
   }

   protected void stop(ServerLevel world, GlareEntity glare, long time) {
      glare.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      GlobalPos darkSpotPos = glare.getDarkSpotPos();
      if (darkSpotPos != null && (!darkSpotPos.pos().closerToCenterThan(glare.position(), 1.5) || !glare.isDarkSpotDark(darkSpotPos.pos()))) {
         glare.getBrain().eraseMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get());
         GlareBrain.setDarkSpotLocatingCooldown(glare, TimeUtil.rangeOfSeconds(10, 20));
      }
   }

   private void flyTowardsDarkSpot(GlareEntity glare) {
      GlobalPos darkSpotPos = glare.getDarkSpotPos();
      if (darkSpotPos != null) {
         BehaviorUtils.setWalkAndLookTargetMemories(glare, new BlockPos(darkSpotPos.pos()), 1.0F, 0);
      }
   }

   public static boolean canTravelToDarkSpot(GlareEntity glare) {
      if (!GlareLocateDarkSpotTask.canLocateDarkSpot(glare)) {
         return false;
      }

      GlobalPos darkSpotPos = glare.getDarkSpotPos();
      return darkSpotPos != null && glare.isDarkSpotDark(darkSpotPos.pos());
   }
}
