package net.mebahelsapi.network;

import net.minecraft.class_1262;
import net.minecraft.class_1263;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_2371;

public class SyncedInventoryWrapper implements class_1263 {
   private final class_2371<class_1799> backingList;

   public SyncedInventoryWrapper(class_2371<class_1799> list) {
      this.backingList = list;
   }

   public int method_5439() {
      return this.backingList.size();
   }

   public boolean method_5442() {
      for (class_1799 stack : this.backingList) {
         if (!stack.method_7960()) {
            return false;
         }
      }

      return true;
   }

   public class_1799 method_5438(int slot) {
      return (class_1799)this.backingList.get(slot);
   }

   public class_1799 method_5434(int slot, int amount) {
      class_1799 result = class_1262.method_5430(this.backingList, slot, amount);
      if (!result.method_7960()) {
         this.method_5431();
      }

      return result;
   }

   public class_1799 method_5441(int slot) {
      class_1799 result = (class_1799)this.backingList.get(slot);
      this.backingList.set(slot, class_1799.field_8037);
      this.method_5431();
      return result;
   }

   public void method_5447(int slot, class_1799 stack) {
      this.backingList.set(slot, stack);
      if (stack.method_7947() > this.method_5444()) {
         stack.method_7939(this.method_5444());
      }

      this.method_5431();
   }

   public void method_5431() {
   }

   public boolean method_5443(class_1657 player) {
      return true;
   }

   public void method_5448() {
      this.backingList.clear();
      this.method_5431();
   }

   public boolean method_5437(int slot, class_1799 stack) {
      return true;
   }
}
