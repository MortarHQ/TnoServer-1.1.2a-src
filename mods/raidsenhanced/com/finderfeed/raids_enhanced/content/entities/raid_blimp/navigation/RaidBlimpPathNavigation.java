package com.finderfeed.raids_enhanced.content.entities.raid_blimp.navigation;

import com.finderfeed.raids_enhanced.content.entities.raid_blimp.RaidBlimp;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

public class RaidBlimpPathNavigation extends FlyingPathNavigation {
   private int recalculationCooldown = 0;
   private int currentNode = 0;
   private int nextMoveNode = 0;
   private Vec3 nextPos = null;
   private RaidBlimp blimp;
   private boolean finishedMovingToPos = false;

   public RaidBlimpPathNavigation(RaidBlimp raidBlimp, Level p_26425_) {
      super(raidBlimp, p_26425_);
      this.blimp = raidBlimp;
   }

   public void tick() {
      RaidBlimpMoveControl moveControl = (RaidBlimpMoveControl)this.blimp.getMoveControl();
      this.tick++;
      if (this.hasDelayedRecomputation) {
         this.recomputePath();
      }

      if (!this.isDone()) {
         this.processPath();
         if (this.nextPos != null) {
            moveControl.setWantedPosition(this.nextPos.x, this.nextPos.y, this.nextPos.z, this.speedModifier);
         }
      } else {
         this.path = null;
         this.currentNode = 0;
         this.nextPos = null;
      }
   }

   public void setFinishedMovingToPos(boolean finishedMovingToPos) {
      this.finishedMovingToPos = finishedMovingToPos;
   }

   public boolean isDone() {
      return super.isDone();
   }

   private void processPath() {
      if (!this.isDone()) {
         Vec3 ePos = this.mob.position();
         if (this.nextPos != null && this.finishedMovingToPos) {
            if (this.nextMoveNode >= this.path.getNodeCount() - 1) {
               this.path.setNextNodeIndex(this.path.getNodeCount());
               return;
            }

            this.finishedMovingToPos = false;
            this.currentNode = this.nextMoveNode;
            this.recalculationCooldown = 0;
         }

         if (this.recalculationCooldown <= 0) {
            int nextMoveNode = this.currentNode;

            for (int i = this.currentNode + 1; i < this.path.getNodeCount(); i++) {
               Vec3 v = this.path.getEntityPosAtNode(this.mob, i);
               ClipContext clipContext = new ClipContext(v, ePos, Block.COLLIDER, Fluid.NONE, CollisionContext.empty());
               BlockHitResult result = this.level.clip(clipContext);
               if (result.getType() == Type.MISS) {
                  if (i >= this.path.getNodeCount() - 1) {
                     nextMoveNode = this.path.getNodeCount() - 1;
                  }
               } else {
                  nextMoveNode = i - 1;
               }
            }

            this.nextMoveNode = nextMoveNode;
            this.nextPos = this.path.getEntityPosAtNode(this.mob, nextMoveNode);
            this.recalculationCooldown = 10;
         }
      }

      this.recalculationCooldown = Mth.clamp(this.recalculationCooldown - 1, 0, Integer.MAX_VALUE);
   }

   public boolean moveTo(@Nullable Path p_26537_, double p_26538_) {
      Path p = this.path;
      boolean res;
      if (res = super.moveTo(p_26537_, p_26538_) && p != p_26537_) {
         this.finishedMovingToPos = false;
         this.currentNode = 0;
         this.nextPos = null;
         this.nextMoveNode = 0;
      }

      return res;
   }
}
