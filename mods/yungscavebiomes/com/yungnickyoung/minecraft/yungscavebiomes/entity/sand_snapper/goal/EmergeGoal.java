package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EmergeGoal extends Goal {
   private final SandSnapperEntity sandSnapper;
   private final float minDistanceFromPlayer;
   private final float minDistanceFromPlayerDuringSandstorm;
   private final float lookAtPlayerRange;
   private final int cooldown;
   private int ticksRunning;
   private int lastUseTime;

   public EmergeGoal(
      SandSnapperEntity sandSnapper, float minDistanceFromPlayer, float minDistanceFromPlayerDuringSandstorm, float lookAtPlayerRange, int cooldown
   ) {
      this.sandSnapper = sandSnapper;
      this.minDistanceFromPlayer = minDistanceFromPlayer;
      this.minDistanceFromPlayerDuringSandstorm = minDistanceFromPlayerDuringSandstorm;
      this.lookAtPlayerRange = lookAtPlayerRange;
      this.cooldown = cooldown;
      this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
   }

   public void tick() {
      this.ticksRunning++;
      Player nearestPlayer = this.sandSnapper.level().getNearestPlayer(this.sandSnapper, this.lookAtPlayerRange);
      if (this.sandSnapper.isLookingAtPlayer() && nearestPlayer != null) {
         this.sandSnapper.getLookControl().setLookAt(nearestPlayer);
      }
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public void start() {
      this.ticksRunning = 0;
      this.sandSnapper.setEmerging(true);
      this.sandSnapper.setLookingAtPlayer(!this.getPlayersInRange(this.lookAtPlayerRange).isEmpty());
      this.sandSnapper.getNavigation().stop();
   }

   public void stop() {
      this.sandSnapper.setEmerging(false);
      this.sandSnapper.setDiving(true);
      this.lastUseTime = this.sandSnapper.tickCount;
      SandstormServerData sandstormServerData = ((ISandstormServerDataProvider)this.sandSnapper.level()).getSandstormServerData();
      float dist = sandstormServerData.isSandstormActive() ? this.minDistanceFromPlayerDuringSandstorm : this.minDistanceFromPlayer;
      if (!this.getPlayersInRange(dist).isEmpty()) {
         this.sandSnapper.tryPlayPanicSound();
      }
   }

   public boolean canUse() {
      if (this.sandSnapper.isSubmerged() && this.sandSnapper.canMove()) {
         if (this.sandSnapper.tickCount - this.lastUseTime < this.cooldown) {
            return false;
         }

         float halfWidth = 0.8F;
         Vec3 startPos = new Vec3(this.sandSnapper.getX() - halfWidth, this.sandSnapper.getY() - 2.0, this.sandSnapper.getZ() - halfWidth);
         Vec3 endPos = new Vec3(this.sandSnapper.getX() + halfWidth, this.sandSnapper.getY() - 0.6F, this.sandSnapper.getZ() + halfWidth);
         boolean intersectsAir = BlockPos.betweenClosedStream(new AABB(startPos, endPos)).anyMatch(pos -> this.sandSnapper.level().getBlockState(pos).isAir());
         if (intersectsAir) {
            return false;
         }

         SandstormServerData sandstormServerData = ((ISandstormServerDataProvider)this.sandSnapper.level()).getSandstormServerData();
         float dist = sandstormServerData.isSandstormActive() ? this.minDistanceFromPlayerDuringSandstorm : this.minDistanceFromPlayer;
         return this.getPlayersInRange(dist).isEmpty();
      } else {
         return false;
      }
   }

   public boolean canContinueToUse() {
      SandstormServerData sandstormServerData = ((ISandstormServerDataProvider)this.sandSnapper.level()).getSandstormServerData();
      float dist = sandstormServerData.isSandstormActive() ? this.minDistanceFromPlayerDuringSandstorm : this.minDistanceFromPlayer;
      return this.getPlayersInRange(dist).isEmpty() && this.ticksRunning <= 57;
   }

   public boolean isInterruptable() {
      return false;
   }

   private List<Player> getPlayersInRange(float range) {
      AABB searchBox = this.sandSnapper.getBoundingBox().inflate(range, 4.0, range);
      return this.sandSnapper.level().getNearbyPlayers(TargetingConditions.DEFAULT, this.sandSnapper, searchBox);
   }
}
