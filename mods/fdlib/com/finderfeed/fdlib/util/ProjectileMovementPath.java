package com.finderfeed.fdlib.util;

import com.finderfeed.fdlib.nbt.AutoSerializable;
import com.finderfeed.fdlib.nbt.FDTagHelper;
import com.finderfeed.fdlib.nbt.SerializableField;
import com.finderfeed.fdlib.util.math.FDMathUtil;
import com.finderfeed.fdlib.util.rendering.FDEasings;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ProjectileMovementPath implements AutoSerializable {
   private List<Vec3> positions = new ArrayList<>();
   @SerializableField
   private int time;
   @SerializableField
   private int progress = 0;
   @SerializableField
   private boolean cycle = false;
   @SerializableField
   private boolean easeInOut = false;
   @SerializableField
   private boolean easeIn = false;
   @SerializableField
   private Vec3 speedOnEnd = null;
   @SerializableField
   private ProjectileMovementPath next;

   public ProjectileMovementPath() {
   }

   public ProjectileMovementPath(int time, boolean cycle) {
      this.time = time;
      this.cycle = cycle;
   }

   public ProjectileMovementPath(Vec3 initPos, int time, boolean cycle) {
      this.positions.add(initPos);
      this.time = time;
      this.cycle = cycle;
   }

   public ProjectileMovementPath addPos(Vec3 pos) {
      this.positions.add(pos);
      return this;
   }

   public void tick(Entity entity) {
      if (!this.cycle) {
         if (this.isFinished()) {
            if (this.speedOnEnd != null) {
               entity.setDeltaMovement(this.speedOnEnd);
            }

            return;
         }

         this.progress++;
      } else {
         this.progress = (this.progress + 1) % (this.time + 1);
      }

      float p = (float)this.progress / this.time;
      if (this.easeInOut) {
         p = FDEasings.easeInOut(p);
      } else if (this.easeIn) {
         p = FDEasings.easeIn(p);
      }

      Vec3 v = FDMathUtil.linear(this.positions, p);
      Vec3 currentPos = entity.position();
      Vec3 b = v.subtract(currentPos);
      entity.setDeltaMovement(b);
   }

   public boolean isFinished() {
      return this.progress >= this.time && !this.cycle;
   }

   public void setEaseInOut(boolean easeInOut) {
      this.easeInOut = easeInOut;
   }

   public void setEaseIn(boolean easeIn) {
      this.easeIn = easeIn;
   }

   public void setSpeedOnEnd(Vec3 speedOnEnd) {
      this.speedOnEnd = speedOnEnd;
   }

   public List<Vec3> getPositions() {
      return this.positions;
   }

   public ProjectileMovementPath getNext() {
      return this.next;
   }

   public void setNext(ProjectileMovementPath next) {
      this.next = next;
   }

   public int getTime() {
      return this.time;
   }

   public int getProgress() {
      return this.progress;
   }

   public Vec3 getSpeedOnEnd() {
      return this.speedOnEnd;
   }

   @Override
   public void autoSave(CompoundTag tag) {
      AutoSerializable.super.autoSave(tag);
      FDTagHelper.saveVec3List(tag, "pos", this.positions);
   }

   @Override
   public void autoLoad(CompoundTag tag) {
      AutoSerializable.super.autoLoad(tag);
      this.positions = FDTagHelper.loadVec3List(tag, "pos");
   }
}
