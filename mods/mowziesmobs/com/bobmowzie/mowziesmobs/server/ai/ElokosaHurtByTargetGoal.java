package com.bobmowzie.mowziesmobs.server.ai;

import com.bobmowzie.mowziesmobs.server.entity.elokosa.EntityElokosa;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.Team;

public class ElokosaHurtByTargetGoal extends HurtByTargetGoal {
   private EntityElokosa elokosa;
   private boolean checkSight;
   private int unseenTicks;

   public ElokosaHurtByTargetGoal(EntityElokosa entity, boolean checkSight, Class<?>... p_26040_) {
      super(entity, p_26040_);
      this.setAlertOthers(new Class[0]);
      this.checkSight = checkSight;
      this.elokosa = entity;
   }

   protected void alertOthers() {
      double d0 = this.getFollowDistance();
      AABB aabb = AABB.unitCubeFromLowerCorner(this.mob.position()).inflate(d0, 10.0, d0);
      List<? extends PathfinderMob> listUmvuthana = this.mob.level().getEntitiesOfClass(EntityElokosa.class, aabb, EntitySelector.NO_SPECTATORS);
      List<PathfinderMob> list = new ArrayList<>();
      list.addAll(listUmvuthana);

      label56:
      for (Mob mob : list) {
         if (this.mob != mob
            && mob.getTarget() == null
            && (!(this.mob instanceof TamableAnimal) || ((TamableAnimal)this.mob).getOwner() == ((TamableAnimal)mob).getOwner())
            && !mob.isAlliedTo(this.mob.getLastHurtByMob())) {
            if (this.toIgnoreAlert != null) {
               boolean flag = false;
               Class[] var9 = this.toIgnoreAlert;
               int var10 = var9.length;
               int var11 = 0;

               while (true) {
                  if (var11 < var10) {
                     Class<?> oclass = var9[var11];
                     if (mob.getClass() != oclass) {
                        var11++;
                        continue;
                     }

                     flag = true;
                  }

                  if (!flag) {
                     break;
                  }
                  continue label56;
               }
            }

            this.alertOther(mob, this.mob.getLastHurtByMob());
         }
      }
   }

   protected double getFollowDistance() {
      return super.getFollowDistance() * 1.7;
   }

   public boolean canUse() {
      return this.elokosa.getNightForm() && super.canUse();
   }

   public boolean canContinueToUse() {
      if (!this.elokosa.getNightForm()) {
         return false;
      }

      LivingEntity livingentity = this.mob.getTarget();
      if (livingentity == null) {
         livingentity = this.targetMob;
      }

      if (livingentity == null) {
         return false;
      }

      if (!this.mob.canAttack(livingentity)) {
         return false;
      }

      Team team = this.mob.getTeam();
      Team team1 = livingentity.getTeam();
      if (team != null && team1 == team) {
         return false;
      }

      double d0 = this.getFollowDistance();
      if (this.mob.distanceToSqr(livingentity) > d0 * d0) {
         return false;
      }

      if (this.checkSight) {
         if (this.mob.getSensing().hasLineOfSight(livingentity)) {
            this.unseenTicks = 0;
         } else if (++this.unseenTicks > reducedTickDelay(this.unseenMemoryTicks)) {
            return false;
         }
      }

      this.mob.setTarget(livingentity);
      return true;
   }

   public void start() {
      super.start();
      this.unseenTicks = 0;
      this.elokosa.setStalking(false);
   }
}
