package com.bobmowzie.mowziesmobs.server.ai;

import com.bobmowzie.mowziesmobs.server.entity.grottol.EntityGrottol;
import com.google.common.base.Predicate;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.vehicle.Minecart;

public final class EntityAIGrottolFindMinecart extends Goal {
   private final EntityGrottol grottol;
   private final Comparator<Entity> sorter;
   private final Predicate<Minecart> predicate;
   private Minecart minecart;
   private int time;

   public EntityAIGrottolFindMinecart(EntityGrottol grottol) {
      this.grottol = grottol;
      this.sorter = Comparator.comparing(grottol::distanceToSqr);
      this.predicate = minecart -> minecart != null
         && minecart.isAlive()
         && !minecart.isVehicle()
         && EntityGrottol.isBlockRail(minecart.level().getBlockState(minecart.blockPosition()).getBlock());
      this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
   }

   public boolean canUse() {
      if (this.grottol.fleeTime <= 1) {
         return false;
      }

      List<Minecart> minecarts = this.grottol.level().getEntitiesOfClass(Minecart.class, this.grottol.getBoundingBox().inflate(8.0, 4.0, 8.0), this.predicate);
      minecarts.sort(this.sorter);
      if (minecarts.isEmpty()) {
         return false;
      }

      this.minecart = minecarts.get(0);
      return true;
   }

   public boolean canContinueToUse() {
      return this.predicate.test(this.minecart) && this.time < 1200 && !this.grottol.isInMinecart();
   }

   public void start() {
      this.time = 0;
      this.grottol.getNavigation().moveTo(this.minecart, 0.5);
   }

   public void stop() {
      this.grottol.getNavigation().stop();
   }

   public void tick() {
      if (this.grottol.distanceToSqr(this.minecart) > 2.1025) {
         this.grottol.getLookControl().setLookAt(this.minecart, 10.0F, this.grottol.getMaxHeadXRot());
         if (++this.time % 40 == 0) {
            this.grottol.getNavigation().moveTo(this.minecart, 0.5);
         }
      } else {
         this.grottol.startRiding(this.minecart, true);
         if (this.minecart.getHurtTime() == 0) {
            this.minecart.setHurtDir(-this.minecart.getHurtDir());
            this.minecart.setHurtTime(10);
            this.minecart.setDamage(50.0F);
         }
      }
   }
}
