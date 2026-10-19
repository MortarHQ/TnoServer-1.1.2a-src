package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.head;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimatedObject;
import com.finderfeed.fdlib.systems.bedrock.models.FDModel;
import java.util.HashMap;
import java.util.Map.Entry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.neoforged.neoforge.network.PacketDistributor;

public class HeadControllerContainer<T extends Mob & AnimatedObject & IHasHead<T>> extends LookControl {
   private HashMap<String, HeadController<T>> headControllers = new HashMap<>();
   private HeadControllerContainer.Mode mode = HeadControllerContainer.Mode.LOOK;
   private T entity;

   public HeadControllerContainer(T entity) {
      super(entity);
      this.entity = entity;
   }

   public HeadControllerContainer<T> addHeadController(FDModel model, String headBone) {
      return this.addHeadController(model, headBone, false);
   }

   public HeadControllerContainer<T> addHeadController(FDModel model, String headBone, boolean headReversed) {
      HeadController<T> controller = new HeadController<>(this, model, headBone, this.entity);
      controller.setHeadReversed(headReversed);
      this.headControllers.put(headBone, controller);
      return this;
   }

   public void clientTick() {
      for (Entry<String, HeadController<T>> entry : this.headControllers.entrySet()) {
         HeadController<T> value = entry.getValue();
         value.clientTick();
      }
   }

   public HeadController<T> getController(String headBone) {
      return this.headControllers.get(headBone);
   }

   public HeadControllerContainer.Mode getControllersMode() {
      return this.mode;
   }

   public void setControllersMode(HeadControllerContainer.Mode mode) {
      if (mode != this.mode) {
         HeadControllerContainer.Mode old = this.mode;
         this.mode = mode;
         if (!this.entity.level().isClientSide) {
            PacketDistributor.sendToPlayersTrackingEntity(this.entity, new ChangeHeadControllerModePacket(this.entity, mode), new CustomPacketPayload[0]);
            return;
         }

         for (Entry<String, HeadController<T>> entry : this.headControllers.entrySet()) {
            HeadController<T> value = entry.getValue();
            value.onControllerModeChanged(old, mode);
         }
      }
   }

   public void tick() {
      if (this.resetXRotOnTick()) {
         this.mob.setXRot(0.0F);
      }

      if (this.lookAtCooldown > 0) {
         this.lookAtCooldown--;
         this.getYRotD().ifPresent(p_287447_ -> this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, p_287447_, this.yMaxRotSpeed));
         this.getXRotD().ifPresent(p_352768_ -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), p_352768_, this.xMaxRotAngle)));
      } else {
         if (!this.entity.level().isClientSide) {
            PacketDistributor.sendToPlayersTrackingEntity(this.entity, new LookStraightPacket(this.entity), new CustomPacketPayload[0]);
         }

         this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, 10.0F);
      }

      this.clampHeadRotationToBody();
   }

   public void setLookAt(double x, double y, double z, float p_24954_, float p_24955_) {
      if (!this.entity.level().isClientSide) {
         PacketDistributor.sendToPlayersTrackingEntity(this.entity, new WantedLookCoordinatesPacket(this.entity, x, y, z), new CustomPacketPayload[0]);
      }

      super.setLookAt(x, y, z, p_24954_, p_24955_);
   }

   public enum Mode {
      LOOK,
      ANIMATION,
      ANIMATION_AND_LOOK;
   }
}
