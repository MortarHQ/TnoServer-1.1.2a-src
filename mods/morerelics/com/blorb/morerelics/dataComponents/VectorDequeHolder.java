package com.blorb.morerelics.dataComponents;

import java.util.LinkedList;
import java.util.Objects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

public class VectorDequeHolder {
   public LinkedList<Vec3> vecList;
   public LinkedList<ServerLevel> levelList;
   public int length;

   public VectorDequeHolder(int length) {
      this.length = length;
      this.vecList = new LinkedList<>();
      this.levelList = new LinkedList<>();
   }

   public int getLength() {
      return this.length;
   }

   public LinkedList<Vec3> getList() {
      return this.vecList;
   }

   public Vec3 getOldestVec() {
      return this.vecList.isEmpty() ? null : this.vecList.getFirst();
   }

   public ServerLevel getOldestLevel() {
      return this.levelList.isEmpty() ? null : this.levelList.getFirst();
   }

   private void addVec(Vec3 vec) {
      if (this.vecList.size() == this.length) {
         this.vecList.removeFirst();
      }

      this.vecList.addLast(vec);
   }

   private void addLevel(ServerLevel level) {
      if (this.levelList.size() == this.length) {
         this.levelList.removeFirst();
      }

      this.levelList.addLast(level);
   }

   public void addVecAndLevel(Vec3 vec, ServerLevel level) {
      this.addVec(vec);
      this.addLevel(level);
   }

   public void clear() {
      this.vecList.clear();
      this.levelList.clear();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.vecList, this.levelList, this.length);
   }

   @Override
   public boolean equals(Object obj) {
      return obj == this ? true : obj instanceof VectorDequeHolder ex && this.vecList == ex.vecList && this.length == ex.length;
   }
}
