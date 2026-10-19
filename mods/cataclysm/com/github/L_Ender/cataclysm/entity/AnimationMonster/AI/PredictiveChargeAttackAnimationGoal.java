package com.github.L_Ender.cataclysm.entity.AnimationMonster.AI;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.LLibrary_Monster;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class PredictiveChargeAttackAnimationGoal<T extends LLibrary_Monster & IAnimatedEntity> extends SimpleAnimationGoal<T> {
   protected LivingEntity target;
   private final int look1;
   private final int look2;
   private final float sensing;
   private final int charge;
   private final float motionx;
   private final float motionz;
   public double prevX;
   public double prevZ;
   private int newX;
   private int newZ;

   public PredictiveChargeAttackAnimationGoal(T entity, Animation animation, int look1, int look2, float sensing, int charge, float motionx, float motionz) {
      super(entity, animation);
      this.look1 = look1;
      this.look2 = look2;
      this.sensing = sensing;
      this.charge = charge;
      this.motionx = motionx;
      this.motionz = motionz;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
   }

   public void start() {
      super.start();
      this.target = this.entity.getTarget();
      if (this.target != null) {
         this.prevX = this.target.getX();
         this.prevZ = this.target.getZ();
      }
   }

   public void tick() {
      if (this.target != null) {
         boolean flag = this.entity.getAnimationTick() < this.look1 || this.entity.getAnimationTick() > this.look2;
         if (flag) {
            this.entity.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
            this.entity.lookAt(this.target, 30.0F, 30.0F);
         } else {
            this.entity.getLookControl().setLookAt(this.target, 0.0F, 30.0F);
            this.entity.setYRot(this.entity.yRotO);
         }
      } else {
         this.entity.setYRot(this.entity.yRotO);
      }

      if (this.entity.getAnimationTick() == this.charge - 1 && this.target != null) {
         double x = this.target.getX();
         double z = this.target.getZ();
         double vx = (x - this.prevX) / this.charge;
         double vz = (z - this.prevZ) / this.charge;
         this.newX = Mth.floor(x + vx * this.sensing);
         this.newZ = Mth.floor(z + vz * this.sensing);
      }

      if (this.entity.getAnimationTick() == this.charge && this.target != null) {
         this.entity.setDeltaMovement((this.newX - this.entity.getX()) * this.motionx, 0.0, (this.newZ - this.entity.getZ()) * this.motionz);
      }
   }
}
