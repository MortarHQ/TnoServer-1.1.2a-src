package com.tom.storagemod.util;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import net.minecraft.world.inventory.ContainerData;

public class IntDataSlots implements ContainerData {
   private static final int MASK = 65535;
   private IntConsumer c;
   private IntSupplier s;
   private Runnable onUpdate;
   private int value;

   public int get(int off) {
      int bitOff = off << 4;
      return (this.s.getAsInt() & 65535 << bitOff) >> bitOff;
   }

   public void set(int off, int val) {
      int bitOff = off << 4;
      int newVal = this.value & ~(65535 << bitOff) | (val & 65535) << bitOff;
      this.c.accept(newVal);
      this.value = newVal;
      if (this.onUpdate != null) {
         this.onUpdate.run();
      }
   }

   public int getCount() {
      return 2;
   }

   public static IntDataSlots create(IntConsumer c, IntSupplier s) {
      return new IntDataSlots(c, s);
   }

   public IntDataSlots(IntConsumer c, IntSupplier s) {
      this.c = c;
      this.s = s;
   }

   public IntDataSlots onUpdate(Runnable r) {
      this.onUpdate = r;
      return this;
   }
}
