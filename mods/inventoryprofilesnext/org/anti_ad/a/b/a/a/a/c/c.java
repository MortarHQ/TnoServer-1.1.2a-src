package org.anti_ad.a.b.a.a.a.c;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class c implements Iterator {
   private Object[] a;
   private int b;
   private boolean c;

   public c(b var1, Object[] var2) {
      this.d = var1;
      super();
      this.b = 0;
      this.c = true;
      this.a = var2;
   }

   @Override
   public final boolean hasNext() {
      return this.b < this.a.length;
   }

   @Override
   public final Object next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      }

      this.c = false;
      return this.a[this.b++];
   }

   @Override
   public final void remove() {
      if (this.c) {
         throw new IllegalStateException();
      }

      this.d.remove(this.a[this.b - 1]);
      this.c = true;
   }
}
