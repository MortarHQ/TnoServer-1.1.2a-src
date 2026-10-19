package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.DeathWormEntity;
import com.iafenvoy.iceandfire.entity.util.IGroundMount;
import java.util.EnumSet;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class EntityGroundAIRideGoal<T extends Mob & IGroundMount> extends Goal {
   private final T dragon;
   private Player player;

   public EntityGroundAIRideGoal(T dragon) {
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      this.dragon = dragon;
   }

   public boolean canUse() {
      this.player = this.dragon.getRidingPlayer();
      return this.player != null;
   }

   public void start() {
      this.dragon.getNavigation().stop();
   }

   public void tick() {
      this.dragon.getNavigation().stop();
      this.dragon.setTarget(null);
      double x = this.dragon.getX();
      double y = this.dragon.getY();
      if (this.dragon instanceof DeathWormEntity worm) {
         y = worm.processRiderY(y);
      }

      double z = this.dragon.getZ();
      double speed = 1.8F * this.dragon.getRideSpeedModifier();
      if (this.player.xxa != 0.0F || this.player.zza != 0.0F) {
         Vec3 lookVec = this.player.getLookAngle();
         if (this.player.zza < 0.0F) {
            lookVec = lookVec.yRot((float) Math.PI);
         } else if (this.player.xxa > 0.0F) {
            lookVec = lookVec.yRot((float) (Math.PI / 2));
         } else if (this.player.xxa < 0.0F) {
            lookVec = lookVec.yRot((float) (-Math.PI / 2));
         }

         if (Math.abs(this.player.xxa) > 0.0) {
            speed *= 0.25;
         }

         if (this.player.zza < 0.0) {
            speed *= 0.15;
         }

         x += lookVec.x * 10.0;
         z += lookVec.z * 10.0;
      }

      this.dragon.getMoveControl().setWantedPosition(x, y, z, speed);
   }
}
