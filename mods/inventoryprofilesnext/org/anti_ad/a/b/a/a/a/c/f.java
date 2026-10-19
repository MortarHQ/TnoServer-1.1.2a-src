package org.anti_ad.a.b.a.a.a.c;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import org.anti_ad.a.b.a.a.a.a.ai;

public class f implements Map {
   private a a;
   private LinkedList[] b;
   private int c = 0;
   private int d = 1;
   private int e;
   private int f;
   private int g;

   public f(ai var1) {
      this(var1, (byte)0);
   }

   private f(ai var1, byte var2) {
      if (var1 == null) {
         var1 = n.a;
      }

      this.a = var1;
      this.f = 16;
      this.g = 8;
      this.e = (int)Math.floor(12.0);
      this.b = new LinkedList[8];
   }

   private int a(Object var1) {
      return this.a.a(var1) & this.b.length - 1;
   }

   @Override
   public Object get(Object var1) {
      Object var2 = var1;
      if (var1 == null) {
         return null;
      }

      var1 = this.a(var2);
      LinkedList var5;
      if ((var5 = this.b[var1]) == null) {
         return null;
      }

      for (g var3 : var5) {
         if (this.a.a(var3.a, var2)) {
            return var3.b;
         }
      }

      return null;
   }

   @Override
   public Object put(Object var1, Object var2) {
      if (var1 == null) {
         return null;
      }

      if (this.c > this.e) {
         f var3 = this;
         LinkedList[] var4 = this.b;
         var3.d += 4;
         int var5;
         LinkedList[] var6 = new LinkedList[var5 = var3.b.length << 1];
         var3.b = var6;
         var3.e = (int)(var5 * 0.75);
         var5 = var3.size();
         int var16 = var4.length;

         for (int var7 = 0; var7 < var16; var7++) {
            LinkedList var8;
            if ((var8 = var4[var7]) != null) {
               Iterator var17 = var8.iterator();

               g var9;
               while (var17.hasNext() && (var9 = (g)var17.next()) != null) {
                  var3.put(var9.a, var9.b);
               }
            }
         }

         var3.c = var5;
      }

      int var11 = this.a(var1);
      LinkedList var13;
      if ((var13 = this.b[var11]) == null) {
         var13 = this.b[var11] = new LinkedList();
      }

      for (g var15 : var13) {
         if (this.a.a(var15.a, var1)) {
            var1 = var15.b;
            var15.b = var2;
            this.c++;
            return var1;
         }
      }

      var13.add(new g(var1, var2));
      this.c++;
      return null;
   }

   @Override
   public Object remove(Object var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void putAll(Map var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Set keySet() {
      throw new UnsupportedOperationException();
   }

   @Override
   public Collection values() {
      ArrayList var1 = new ArrayList(this.size());
      LinkedList[] var2 = this.b;
      int var3 = this.b.length;

      for (int var4 = 0; var4 < var3; var4++) {
         LinkedList var5;
         if ((var5 = var2[var4]) != null) {
            for (g var6 : var5) {
               var1.add(var6.b);
            }
         }
      }

      return var1;
   }

   @Override
   public Set entrySet() {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean containsKey(Object var1) {
      return this.get(var1) != null;
   }

   @Override
   public boolean containsValue(Object var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public int hashCode() {
      int var1 = 0;
      LinkedList[] var2 = this.b;
      int var3 = this.b.length;

      for (int var4 = 0; var4 < var3; var4++) {
         LinkedList var5;
         if ((var5 = var2[var4]) != null) {
            Iterator var7 = var5.iterator();

            g var6;
            while (var7.hasNext() && (var6 = (g)var7.next()) != null) {
               var1 = m.a(var1, this.a.a(var6.a));
            }
         }
      }

      return m.b(var1, this.size());
   }

   @Override
   public boolean equals(Object var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public int size() {
      return this.c;
   }

   @Override
   public boolean isEmpty() {
      return this.c == 0;
   }

   @Override
   public void clear() {
      this.b = new LinkedList[this.f];
      this.c = 0;
      this.e = (int)Math.floor(this.f * 0.75);
   }

   @Override
   public String toString() {
      if (this.size() == 0) {
         return "{}";
      }

      StringBuilder var1;
      (var1 = new StringBuilder()).append('{');
      boolean var2 = true;
      LinkedList[] var3 = this.b;
      int var4 = this.b.length;

      for (int var5 = 0; var5 < var4; var5++) {
         LinkedList var6;
         if ((var6 = var3[var5]) != null) {
            Iterator var8 = var6.iterator();

            g var7;
            while (var8.hasNext() && (var7 = (g)var8.next()) != null) {
               if (var2) {
                  var2 = false;
               } else {
                  var1.append(", ");
               }

               var1.append(var7.toString());
            }
         }
      }

      var1.append('}');
      return var1.toString();
   }
}
