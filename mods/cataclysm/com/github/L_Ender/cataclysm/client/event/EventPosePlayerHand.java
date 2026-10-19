package com.github.L_Ender.cataclysm.client.event;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.Event;

@OnlyIn(Dist.CLIENT)
public class EventPosePlayerHand extends Event {
   private LivingEntity entityIn;
   private HumanoidModel model;
   private boolean left;
   private EventPosePlayerHand.Result result = EventPosePlayerHand.Result.DEFAULT;

   public EventPosePlayerHand(LivingEntity entityIn, HumanoidModel model, boolean left) {
      this.entityIn = entityIn;
      this.model = model;
      this.left = left;
   }

   public Entity getEntityIn() {
      return this.entityIn;
   }

   public HumanoidModel getModel() {
      return this.model;
   }

   public boolean isLeftHand() {
      return this.left;
   }

   public void setResult(EventPosePlayerHand.Result result) {
      this.result = result;
   }

   public EventPosePlayerHand.Result getResult() {
      return this.result;
   }

   public enum Result {
      ALLOW,
      DEFAULT,
      DENY;
   }
}
