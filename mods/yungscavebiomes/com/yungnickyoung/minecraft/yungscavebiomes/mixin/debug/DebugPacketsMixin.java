package com.yungnickyoung.minecraft.yungscavebiomes.mixin.debug;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.GoalDebugPayload;
import net.minecraft.network.protocol.common.custom.PathfindingDebugPayload;
import net.minecraft.network.protocol.common.custom.GoalDebugPayload.DebugGoal;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugPackets.class)
public abstract class DebugPacketsMixin {
   @Shadow
   private static void sendPacketToAllPlayers(ServerLevel $$0, CustomPacketPayload $$1) {
   }

   @Inject(method = "sendGoalSelector", at = @At("HEAD"))
   private static void yungscavebiomes_debugSendGoalSelector(Level level, Mob mob, GoalSelector goalSelector, CallbackInfo ci) {
      if (level instanceof ServerLevel serverLevel && YungsCaveBiomesCommon.DEBUG_RENDERING) {
         List<DebugGoal> debugGoals = goalSelector.getAvailableGoals()
            .stream()
            .sorted((a, b) -> Integer.compare(b.getPriority(), a.getPriority()))
            .map(goal -> new DebugGoal(goal.getPriority(), goal.isRunning(), goal.getGoal().toString()))
            .toList();
         GoalDebugPayload payload = new GoalDebugPayload(mob.getId(), mob.blockPosition(), debugGoals);
         sendPacketToAllPlayers(serverLevel, payload);
      }
   }

   @Inject(method = "sendPathFindingPacket", at = @At("HEAD"))
   private static void yungscavebiomes_debugPathfinding(Level level, Mob mob, @Nullable Path path, float maxDistanceToWaypoint, CallbackInfo ci) {
      if (path != null) {
         Path pathCopy = path.copy();
         if (level instanceof ServerLevel serverLevel && YungsCaveBiomesCommon.DEBUG_RENDERING) {
            PathfindingDebugPayload payload = new PathfindingDebugPayload(mob.getId(), pathCopy, maxDistanceToWaypoint);
            sendPacketToAllPlayers(serverLevel, payload);
         }
      }
   }
}
