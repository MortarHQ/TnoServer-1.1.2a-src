package com.teamresourceful.resourcefullib.common.inventory;

import net.minecraft.world.inventory.ContainerData;

public class IntContainerData implements ContainerData {
   private final short[] data;

   public IntContainerData(int size) {
      this.data = new short[size * 2];
   }

   @Deprecated
   public final void set(int index, int value) {
      this.data[index] = (short)value;
   }

   public void setInt(int index, int value) {
      int i = index * 2;
      this.set(i, value >> 16);
      this.set(i + 1, value & 65535);
   }

   @Deprecated
   public final int get(int index) {
      return this.data[index];
   }

   public int getInt(int index) {
      int i = index * 2;
      return this.get(i) << 16 | this.get(i + 1) & 65535;
   }

   @Deprecated
   public final int getCount() {
      return this.data.length;
   }

   public final int getSize() {
      return this.data.length / 2;
   }
}
