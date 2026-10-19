package com.bobmowzie.mowziesmobs.server.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.dynamics.DynamicChain;
import com.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;

public class EntityDynamicsTester extends MowzieLLibraryEntity {
   public DynamicChain dc;

   public EntityDynamicsTester(Level world) {
      super((EntityType<? extends MowzieEntity>)EntityHandler.NAGA.get(), world);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.3));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
   }

   @Override
   public Animation getDeathAnimation() {
      return null;
   }

   @Override
   public Animation getHurtAnimation() {
      return null;
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[0];
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         if (this.tickCount == 1) {
            this.dc = new DynamicChain(this);
         }

         this.dc.updateSpringConstraint(0.1F, 0.3F, 0.6F, 1.0F, true, 0.5F, 1);
         this.yBodyRot = this.getYRot();
      }
   }
}
