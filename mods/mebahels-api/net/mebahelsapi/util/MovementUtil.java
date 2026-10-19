package net.mebahelsapi.util;

import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3532;

public class MovementUtil {
   private final class_1308 actor;
   private double strafeDistance;
   private double tolerance = 2.0;
   private double approachSpeed = 1.0;
   private MovementUtil.Mode mode = MovementUtil.Mode.COMBAT;
   private int approachWantedTicks = 0;
   private int combatWantedTicks = 0;
   private static final int SWITCH_CONFIRM_TICKS = 8;
   private static final double HYST = 1.5;
   private static final int MODE_SWITCH_PAUSE_TICKS = 1;
   private int modeSwitchPauseTicks = 0;
   private MovementUtil.Mode pendingMode = null;
   public boolean movingToLeft;
   public boolean backward;
   public int combatTicks = -1;
   private class_243 jumpDirection = class_243.field_1353;
   public int jumpCooldown = 0;
   public int jumpDuration = 0;
   private class_243 lastPosition;
   private int stuckTicks = 0;
   private static final int MAX_STUCK_TICKS = 20;
   public int targetSeeingTicker;

   public MovementUtil(class_1308 actor, double strafeDistance) {
      this.actor = actor;
      this.strafeDistance = strafeDistance;
      this.lastPosition = actor.method_19538();
   }

   public MovementUtil(class_1308 actor) {
      this.actor = actor;
      this.strafeDistance = 7.0;
      this.lastPosition = actor.method_19538();
   }

   public void setTolerance(double tolerance) {
      this.tolerance = Math.max(0.0, tolerance);
   }

   public void setApproachSpeed(double approachSpeed) {
      this.approachSpeed = Math.max(0.05, approachSpeed);
   }

   public void setStrafeDistance(double strafeDistance) {
      this.strafeDistance = strafeDistance;
   }

   public void maintainRangedPosition(class_1309 target) {
      if (this.modeSwitchPauseTicks > 0) {
         this.modeSwitchPauseTicks--;
         this.stopTotally(target);
         if (this.modeSwitchPauseTicks == 0 && this.pendingMode != null) {
            this.mode = this.pendingMode;
            this.pendingMode = null;
            this.actor.method_5942().method_6340();
            this.actor.method_5962().method_6243(0.0F, 0.0F);
            this.cancelJumpAndStuckWhileApproaching();
         }
      } else {
         double d = this.actor.method_5739(target);
         double min = this.strafeDistance - this.tolerance;
         double max = this.strafeDistance + this.tolerance;
         double enterApproach = max;
         double exitApproach = Math.max(0.0, max - 1.5);
         double enterBackoff = min;
         double exitBackoff = min + 1.5;
         boolean wantsApproach = this.mode == MovementUtil.Mode.APPROACH ? d > exitApproach : d > enterApproach;
         if (wantsApproach) {
            this.approachWantedTicks++;
            this.combatWantedTicks = 0;
         } else {
            this.combatWantedTicks++;
            this.approachWantedTicks = 0;
         }

         if (this.mode == MovementUtil.Mode.COMBAT && this.approachWantedTicks >= 8) {
            this.requestModeSwitch(MovementUtil.Mode.APPROACH, target);
            this.approachWantedTicks = 0;
         } else if (this.mode == MovementUtil.Mode.APPROACH && this.combatWantedTicks >= 8) {
            this.requestModeSwitch(MovementUtil.Mode.COMBAT, target);
            this.combatWantedTicks = 0;
         } else if (this.mode == MovementUtil.Mode.APPROACH) {
            this.cancelJumpAndStuckWhileApproaching();
            this.actor.method_5962().method_6243(0.0F, 0.0F);
            this.actor.method_5988().method_6226(target, 30.0F, 30.0F);
            this.actor.method_5942().method_6335(target, this.approachSpeed);
         } else {
            this.checkIfStuck(target, this.actor);
            if (!this.handleJumpTick(target, this.actor)) {
               this.actor.method_5942().method_6340();
               this.lookAtTargetStrafe(target, this.actor);
               boolean shouldBackoff = d < enterBackoff || this.backward && d < exitBackoff;
               if (shouldBackoff) {
                  this.backward = true;
                  this.moveBackward(target, this.actor);
               } else {
                  this.backward = false;
                  this.strafeInZone(target, this.actor);
               }
            }
         }
      }
   }

   private void requestModeSwitch(MovementUtil.Mode newMode, class_1309 target) {
      if (this.pendingMode != newMode) {
         this.pendingMode = newMode;
         this.modeSwitchPauseTicks = 1;
         this.stopTotally(target);
      }
   }

   private void stopTotally(class_1309 target) {
      this.actor.method_5942().method_6340();
      this.actor.method_5962().method_6243(0.0F, 0.0F);
      class_243 v = this.actor.method_18798();
      this.actor.method_18800(0.0, v.field_1351, 0.0);
      this.actor.field_6007 = true;
      this.jumpDuration = 0;
      this.jumpCooldown = 0;
      this.jumpDirection = class_243.field_1353;
      this.stuckTicks = 0;
      this.lastPosition = this.actor.method_19538();
      this.actor.method_5988().method_6226(target, 30.0F, 30.0F);
   }

   private void cancelJumpAndStuckWhileApproaching() {
      this.jumpDuration = 0;
      this.jumpCooldown = 0;
      this.jumpDirection = class_243.field_1353;
      this.stuckTicks = 0;
      this.lastPosition = this.actor.method_19538();
   }

   public void lookAtTargetStrafe(class_1309 target, class_1309 actor) {
      double dx = target.method_23317() - actor.method_23317();
      double dz = target.method_23321() - actor.method_23321();
      double dy = target.method_23320() - actor.method_23320();
      double distance = Math.sqrt(dx * dx + dz * dz);
      float yaw = (float)(class_3532.method_15349(dz, dx) * (180.0 / Math.PI)) - 90.0F;
      float pitch = (float)(-(class_3532.method_15349(dy, distance) * (180.0 / Math.PI)));
      actor.method_36456(yaw);
      actor.method_5847(yaw);
      actor.method_36457(pitch);
   }

   private boolean handleJumpTick(class_1309 target, class_1308 actor) {
      if (this.jumpDuration > 0) {
         actor.method_5942().method_6340();
         actor.method_5962().method_6243(0.0F, 0.0F);
         this.jumpDuration--;
         this.performJump(this.jumpDirection, actor);
         if (this.jumpDuration == 0) {
            this.jumpCooldown = 18;
         }

         this.lookAtTargetStrafe(target, actor);
         return true;
      } else {
         return false;
      }
   }

   public void performJump(class_243 direction, class_1309 actor) {
      actor.method_18799(direction);
      actor.field_6007 = true;
   }

   private void strafeInZone(class_1309 target, class_1308 actor) {
      if (this.jumpCooldown > 0) {
         this.jumpCooldown--;
      }

      if (this.combatTicks >= 20) {
         if (actor.method_59922().method_43057() < 0.3F) {
            this.movingToLeft = !this.movingToLeft;
         }

         this.combatTicks = 0;
      }

      this.combatTicks = Math.max(this.combatTicks, 0) + 1;
      float strafeDirection = this.movingToLeft ? 1.0F : -1.0F;
      float forwardDirection = 0.0F;
      class_243 lateralDirection = new class_243(strafeDirection, 0.0, 0.0);
      class_243 strafeVec = actor.method_19538().method_1019(lateralDirection);
      class_2338 strafeBlockPos = new class_2338((int)strafeVec.field_1352, (int)actor.method_23318(), (int)strafeVec.field_1350);
      class_2680 strafeBlockState = actor.method_37908().method_8320(strafeBlockPos);
      class_2680 blockAboveStrafeState = actor.method_37908().method_8320(strafeBlockPos.method_10084());
      class_2680 blockBelowStrafeState = actor.method_37908().method_8320(strafeBlockPos.method_10074());
      if (this.jumpCooldown <= 0) {
         if (!strafeBlockState.method_26215()
            && (blockAboveStrafeState.method_26215() || !blockAboveStrafeState.method_26234(actor.method_37908(), strafeBlockPos.method_10084()))
            && strafeBlockState.method_26234(actor.method_37908(), strafeBlockPos)) {
            this.jumpDirection = new class_243(strafeDirection, 0.9, 0.0).method_1029().method_1021(0.25);
            this.jumpDuration = 8;
            this.performJump(this.jumpDirection, actor);
            return;
         }

         if (blockBelowStrafeState.method_26215() || !blockBelowStrafeState.method_26234(actor.method_37908(), strafeBlockPos.method_10074())) {
            this.jumpDirection = new class_243(strafeDirection, 0.04, 0.0).method_1029().method_1021(0.12);
            this.jumpDuration = 8;
            this.performJump(this.jumpDirection, actor);
            return;
         }
      }

      actor.method_5962().method_6243(forwardDirection, strafeDirection);
   }

   public void moveBackward(class_1309 target, class_1308 actor) {
      class_243 directionToTarget = new class_243(target.method_23317() - actor.method_23317(), 0.0, target.method_23321() - actor.method_23321())
         .method_1029();
      class_243 backwardDirection = directionToTarget.method_1021(-1.0);
      class_243 backwardDirection2 = directionToTarget.method_1021(-2.0);
      class_243 lateralDirection = new class_243(-backwardDirection.field_1350, 0.0, backwardDirection.field_1352);
      double distanceToTarget = actor.method_5739(target);
      class_243 backwardsVec = actor.method_19538().method_1019(backwardDirection);
      class_243 backwardsVec2 = actor.method_19538().method_1019(backwardDirection2);
      class_243 leftVec = backwardsVec.method_1019(lateralDirection);
      class_243 rightVec = backwardsVec.method_1019(lateralDirection.method_1021(-1.0));
      class_243 leftVec2 = backwardsVec2.method_1019(lateralDirection);
      class_243 rightVec2 = backwardsVec2.method_1019(lateralDirection.method_1021(-1.0));
      class_2338 blockBehindPos = new class_2338((int)backwardsVec.field_1352, (int)actor.method_23318(), (int)backwardsVec.field_1350);
      class_2338 blockBehindPos2 = new class_2338((int)backwardsVec2.field_1352, (int)actor.method_23318(), (int)backwardsVec2.field_1350);
      class_2338 blockLeftPos = new class_2338((int)leftVec.field_1352, (int)actor.method_23318(), (int)leftVec.field_1350);
      class_2338 blockRightPos = new class_2338((int)rightVec.field_1352, (int)actor.method_23318(), (int)rightVec.field_1350);
      class_2338 blockLeftPos2 = new class_2338((int)leftVec2.field_1352, (int)actor.method_23318(), (int)leftVec2.field_1350);
      class_2338 blockRightPos2 = new class_2338((int)rightVec2.field_1352, (int)actor.method_23318(), (int)rightVec2.field_1350);
      class_2680 blockBehindState = actor.method_37908().method_8320(blockBehindPos);
      class_2680 blockBehindState2 = actor.method_37908().method_8320(blockBehindPos2);
      class_2680 blockLeftState = actor.method_37908().method_8320(blockLeftPos);
      class_2680 blockRightState = actor.method_37908().method_8320(blockRightPos);
      class_2680 blockLeftState2 = actor.method_37908().method_8320(blockLeftPos2);
      class_2680 blockRightState2 = actor.method_37908().method_8320(blockRightPos2);
      class_2680 blockAboveBehindState = actor.method_37908().method_8320(blockBehindPos.method_10084());
      class_2680 blockBelowBehindState = actor.method_37908().method_8320(blockBehindPos.method_10074());
      class_2680 blockBelowBehindState2 = actor.method_37908().method_8320(blockBehindPos2.method_10074());
      class_2680 blockBelowLeftState = actor.method_37908().method_8320(blockLeftPos.method_10074());
      class_2680 blockBelowRightState = actor.method_37908().method_8320(blockRightPos.method_10074());
      class_2680 blockBelowLeftState2 = actor.method_37908().method_8320(blockLeftPos2.method_10074());
      class_2680 blockBelowRightState2 = actor.method_37908().method_8320(blockRightPos2.method_10074());
      if (!blockBehindState.method_26227().method_15769()
         || blockBehindState.method_26204() == class_2246.field_10029
         || blockBehindState2.method_26204() == class_2246.field_10029
         || blockLeftState.method_26204() == class_2246.field_10029
         || blockRightState.method_26204() == class_2246.field_10029
         || blockLeftState2.method_26204() == class_2246.field_10029
         || blockRightState2.method_26204() == class_2246.field_10029) {
         this.strafeInZone(target, actor);
      } else if (this.jumpCooldown > 0 && distanceToTarget <= this.strafeDistance) {
         actor.method_5962().method_6243(-0.8F, 0.0F);
         this.jumpCooldown--;
      } else if (this.jumpCooldown > 0) {
         this.jumpCooldown--;
      } else if (blockBehindState.method_26215() && blockBehindState2.method_26215()
         || !blockAboveBehindState.method_26215() && blockAboveBehindState.method_26234(actor.method_37908(), blockBehindPos.method_10084())
         || !blockBehindState.method_26234(actor.method_37908(), blockBehindPos) && !blockBehindState2.method_26234(actor.method_37908(), blockBehindPos2)) {
         if (!blockBelowBehindState.method_26215()
            && blockBelowBehindState.method_26234(actor.method_37908(), blockBehindPos.method_10074())
            && !blockBelowBehindState2.method_26215()
            && blockBelowBehindState2.method_26234(actor.method_37908(), blockBehindPos2.method_10074())
            && !blockBelowLeftState.method_26215()
            && blockBelowLeftState.method_26234(actor.method_37908(), blockLeftPos.method_10074())
            && !blockBelowRightState.method_26215()
            && blockBelowRightState.method_26234(actor.method_37908(), blockRightPos.method_10074())
            && !blockBelowLeftState2.method_26215()
            && blockBelowLeftState2.method_26234(actor.method_37908(), blockLeftPos2.method_10074())
            && !blockBelowRightState2.method_26215()
            && blockBelowRightState2.method_26234(actor.method_37908(), blockRightPos2.method_10074())) {
            actor.method_5962().method_6243(-0.8F, 0.0F);
         } else {
            this.jumpDirection = new class_243(backwardDirection.field_1352, 0.0, backwardDirection.field_1350).method_1029().method_1021(0.2);
            this.jumpDuration = 8;
            this.performJump(this.jumpDirection, actor);
         }
      } else {
         this.jumpDirection = new class_243(backwardDirection.field_1352, 0.9, backwardDirection.field_1350).method_1029().method_1021(0.25);
         this.jumpDuration = 8;
         this.performJump(this.jumpDirection, actor);
      }
   }

   public void checkIfStuck(class_1309 target, class_1309 actor) {
      class_243 currentPosition = actor.method_19538();
      int currentX = Math.round((float)currentPosition.field_1352);
      int currentY = Math.round((float)currentPosition.field_1351);
      int currentZ = Math.round((float)currentPosition.field_1350);
      int lastX = Math.round((float)this.lastPosition.field_1352);
      int lastY = Math.round((float)this.lastPosition.field_1351);
      int lastZ = Math.round((float)this.lastPosition.field_1350);
      class_243 directionToTarget = new class_243(target.method_23317() - actor.method_23317(), 0.0, target.method_23321() - actor.method_23321())
         .method_1029();
      class_243 backwardDirection = directionToTarget.method_1021(-1.0);
      if (currentX == lastX && currentY == lastY && currentZ == lastZ) {
         this.stuckTicks++;
         if (this.stuckTicks >= 20) {
            this.jumpDirection = new class_243(backwardDirection.field_1352, 0.6, backwardDirection.field_1350).method_1029().method_1021(0.25);
            this.jumpDuration = 8;
            this.performJump(this.jumpDirection, actor);
            this.stuckTicks = 0;
         }
      } else {
         this.stuckTicks = 0;
      }

      this.lastPosition = currentPosition;
   }

   private enum Mode {
      APPROACH,
      COMBAT;
   }
}
