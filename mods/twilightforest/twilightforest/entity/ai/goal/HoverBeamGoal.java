package twilightforest.entity.ai.goal;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import twilightforest.entity.boss.SnowQueen;

public class HoverBeamGoal extends HoverBaseGoal<SnowQueen> {
   private int hoverTimer;
   private int beamTimer;
   private int seekTimer;
   private final int maxHoverTime;
   private final int maxBeamTime;
   private final int maxSeekTime;
   private double beamY;
   private boolean isInPosition;

   public HoverBeamGoal(SnowQueen snowQueen, int hoverTime, int dropTime) {
      super(snowQueen, 3.0F, 4.0F);
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      this.maxHoverTime = hoverTime;
      this.maxSeekTime = hoverTime;
      this.maxBeamTime = dropTime;
      this.hoverTimer = 0;
      this.isInPosition = false;
   }

   public boolean canUse() {
      LivingEntity target = this.attacker.getTarget();
      if (target == null) {
         return false;
      } else {
         return !target.isAlive() ? false : this.attacker.getCurrentPhase() == SnowQueen.Phase.BEAM;
      }
   }

   public boolean canContinueToUse() {
      LivingEntity target = this.attacker.getTarget();
      if (target != null && target.isAlive()) {
         if (this.attacker.getCurrentPhase() != SnowQueen.Phase.BEAM) {
            return false;
         } else {
            return this.seekTimer >= this.maxSeekTime ? false : this.beamTimer < this.maxBeamTime;
         }
      } else {
         return false;
      }
   }

   public void stop() {
      this.seekTimer = 0;
      this.hoverTimer = 0;
      this.beamTimer = 0;
      this.isInPosition = false;
      this.attacker.setBreathing(false);
   }

   public void tick() {
      if (this.attacker.distanceToSqr(this.hoverPosX, this.hoverPosY, this.hoverPosZ) <= 1.0) {
         this.isInPosition = true;
      }

      if (this.isInPosition) {
         this.hoverTimer++;
      } else {
         this.seekTimer++;
      }

      if (this.hoverTimer >= this.maxHoverTime) {
         this.beamTimer++;
         this.attacker.setBreathing(true);
         this.doRayAttack();
         this.hoverPosY -= 0.05F;
         if (this.hoverPosY < this.beamY) {
            this.hoverPosY = this.beamY;
         }
      }

      double offsetX = this.hoverPosX - this.attacker.getX();
      double offsetY = this.hoverPosY - this.attacker.getY();
      double offsetZ = this.hoverPosZ - this.attacker.getZ();
      double distanceDesired = offsetX * offsetX + offsetY * offsetY + offsetZ * offsetZ;
      distanceDesired = Mth.sqrt((float)distanceDesired);
      if (distanceDesired > 0.5) {
         double velX = offsetX / distanceDesired * 0.05;
         double velY = offsetY / distanceDesired * 0.1;
         double velZ = offsetZ / distanceDesired * 0.05;
         velY += 0.045F;
         this.attacker.push(velX, velY, velZ);
      }

      LivingEntity target = this.attacker.getTarget();
      if (target != null) {
         float tracking = this.isInPosition ? 1.0F : 20.0F;
         this.attacker.lookAt(target, tracking, tracking);
         this.attacker.getLookControl().setLookAt(target, tracking, tracking);
      }
   }

   private void doRayAttack() {
      double range = 20.0;
      double offset = 10.0;
      Vec3 srcVec = new Vec3(this.attacker.getX(), this.attacker.getY() + 0.25, this.attacker.getZ());
      Vec3 lookVec = this.attacker.getViewVector(1.0F);
      Vec3 destVec = srcVec.add(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range);
      List<Entity> possibleList = this.attacker
         .level()
         .getEntities(
            this.attacker, this.attacker.getBoundingBox().move(lookVec.x() * offset, lookVec.y() * offset, lookVec.z() * offset).inflate(range, range, range)
         );
      double hitDist = 0.0;
      if (this.attacker.isMultipartEntity()) {
         possibleList.removeAll(Arrays.asList(Objects.requireNonNull(this.attacker.getParts())));
      }

      for (Entity possibleEntity : possibleList) {
         if (possibleEntity.isPickable() && possibleEntity != this.attacker) {
            float borderSize = possibleEntity.getPickRadius();
            AABB collisionBB = possibleEntity.getBoundingBox().inflate(borderSize, borderSize, borderSize);
            Optional<Vec3> interceptPos = collisionBB.clip(srcVec, destVec);
            if (collisionBB.contains(srcVec)) {
               if (0.0 < hitDist || hitDist == 0.0) {
                  this.attacker.doBreathAttack(possibleEntity);
                  hitDist = 0.0;
               }
            } else if (interceptPos.isPresent()) {
               double possibleDist = srcVec.distanceTo(interceptPos.get());
               if (possibleDist < hitDist || hitDist == 0.0) {
                  this.attacker.doBreathAttack(possibleEntity);
                  hitDist = possibleDist;
               }
            }
         }
      }
   }

   @Override
   protected void makeNewHoverSpot(LivingEntity target) {
      super.makeNewHoverSpot(target);
      this.beamY = target.getY();
      this.seekTimer = 0;
   }
}
