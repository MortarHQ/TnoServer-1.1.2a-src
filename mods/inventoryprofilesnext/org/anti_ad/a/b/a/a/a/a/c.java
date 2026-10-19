package org.anti_ad.a.b.a.a.a.a;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class c implements Set {
   private boolean g = false;
   public d a;
   private ArrayList h = new ArrayList(7);
   public int b;
   protected BitSet c;
   public boolean d;
   public boolean e;
   public final boolean f;
   private int i = -1;

   public c(boolean var1) {
      this.a = new f();
      this.f = var1;
   }

   public c() {
      this(true);
   }

   public final boolean a(b var1) {
      return this.a(var1, null);
   }

   public final boolean a(b var1, org.anti_ad.a.b.a.a.a.c.d var2) {
      if (this.g) {
         throw new IllegalStateException("This set is readonly");
      }

      if (var1.e != ar.a) {
         this.d = true;
      }

      if ((var1.d & -1073741825) > 0) {
         this.e = true;
      }

      b var3;
      if ((var3 = (b)this.a.b(var1)) == var1) {
         this.i = -1;
         this.h.add(var1);
         return true;
      }

      boolean var4 = !this.f;
      af var5 = af.a(var3.c, var1.c, var4, var2);
      var3.d = Math.max(var3.d, var1.d);
      if (var1.a()) {
         var3.b();
      }

      var3.c = var5;
      return true;
   }

   public final BitSet a() {
      BitSet var1 = new BitSet();

      for (b var3 : this.h) {
         var1.set(var3.b);
      }

      return var1;
   }

   public final void a(Y var1) {
      if (this.g) {
         throw new IllegalStateException("This set is readonly");
      }

      if (!this.a.isEmpty()) {
         Iterator var2 = this.h.iterator();

         while (var2.hasNext()) {
            b var3;
            (var3 = (b)var2.next()).c = var1.a(var3.c);
         }
      }
   }

   @Override
   public boolean addAll(Collection var1) {
      for (b var2 : var1) {
         this.a(var2, null);
      }

      return false;
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      }

      if (!(var1 instanceof c)) {
         return false;
      }

      var1 = var1;
      return this.h != null && this.h.equals(var1.h) && this.f == var1.f && this.b == var1.b && this.c == var1.c && this.d == var1.d && this.e == var1.e;
   }

   @Override
   public int hashCode() {
      if (this.g) {
         if (this.i == -1) {
            this.i = this.h.hashCode();
         }

         return this.i;
      } else {
         return this.h.hashCode();
      }
   }

   @Override
   public int size() {
      return this.h.size();
   }

   @Override
   public boolean isEmpty() {
      return this.h.isEmpty();
   }

   @Override
   public boolean contains(Object var1) {
      if (this.a == null) {
         throw new UnsupportedOperationException("This method is not implemented for readonly sets.");
      } else {
         return this.a.contains(var1);
      }
   }

   @Override
   public Iterator iterator() {
      return this.h.iterator();
   }

   @Override
   public void clear() {
      if (this.g) {
         throw new IllegalStateException("This set is readonly");
      }

      this.h.clear();
      this.i = -1;
      this.a.clear();
   }

   public final boolean b() {
      return this.g;
   }

   public final void c() {
      this.g = true;
      this.a = null;
   }

   @Override
   public String toString() {
      StringBuilder var1;
      (var1 = new StringBuilder()).append(this.h.toString());
      if (this.d) {
         var1.append(",hasSemanticContext=").append(this.d);
      }

      if (this.b != 0) {
         var1.append(",uniqueAlt=").append(this.b);
      }

      if (this.c != null) {
         var1.append(",conflictingAlts=").append(this.c);
      }

      if (this.e) {
         var1.append(",dipsIntoOuterContext");
      }

      return var1.toString();
   }

   @Override
   public Object[] toArray(Object[] var1) {
      return this.a.toArray(var1);
   }

   @Override
   public boolean remove(Object var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean containsAll(Collection var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean retainAll(Collection var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean removeAll(Collection var1) {
      throw new UnsupportedOperationException();
   }
}
