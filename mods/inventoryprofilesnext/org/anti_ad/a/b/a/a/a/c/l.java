package org.anti_ad.a.b.a.a.a.c;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.anti_ad.a.b.a.a.a.S;

public class l implements h {
   private List a;
   private boolean b;

   private l(l var1) {
      this();
      this.a(var1);
   }

   public l(int... var1) {
      this.a = new ArrayList(var1.length);
      int var10000 = var1.length;
   }

   public static l a(int var0) {
      l var1;
      (var1 = new l()).b(var0);
      return var1;
   }

   public static l a(int var0, int var1) {
      l var2;
      (var2 = new l()).b(var0, var1);
      return var2;
   }

   public final void b(int var1) {
      if (this.b) {
         throw new IllegalStateException("can't alter readonly IntervalSet");
      }

      this.b(var1, var1);
   }

   public final void b(int var1, int var2) {
      this.a(k.a(var1, var2));
   }

   private void a(k var1) {
      if (this.b) {
         throw new IllegalStateException("can't alter readonly IntervalSet");
      }

      if (var1.c >= var1.b) {
         ListIterator var2 = this.a.listIterator();

         while (var2.hasNext()) {
            k var3 = (k)var2.next();
            if (var1.equals(var3)) {
               return;
            }

            if (var1.c(var3) || !var1.b(var3)) {
               var1 = var1.d(var3);
               var2.set(var1);

               while (var2.hasNext()) {
                  var3 = (k)var2.next();
                  if (!var1.c(var3) && var1.b(var3)) {
                     break;
                  }

                  var2.remove();
                  var2.previous();
                  var2.set(var1.d(var3));
                  var2.next();
               }

               return;
            }

            if (var1.a(var3)) {
               var2.previous();
               var2.add(var1);
               return;
            }
         }

         this.a.add(var1);
      }
   }

   public final l a(l var1) {
      if (var1 == null) {
         return this;
      }

      if (var1 instanceof l) {
         l var5;
         int var2 = (var5 = var1).a.size();

         for (int var3 = 0; var3 < var2; var3++) {
            k var4 = (k)var5.a.get(var3);
            this.b(var4.b, var4.c);
         }
      } else {
         for (int var7 : var1.b()) {
            this.b(var7);
         }
      }

      return this;
   }

   public final l b(l var1) {
      if (var1 != null && !var1.a()) {
         l var2;
         if (var1 instanceof l) {
            var2 = var1;
         } else {
            (var2 = new l()).a(var1);
         }

         l var12 = this;
         var1 = var2;
         if (var12.a()) {
            return new l(var1);
         }

         var2 = var12;
         var1 = var1;
         if (var1.a()) {
            return new l();
         }

         var1 = new l(var1);
         if (var2 != null && !var2.a()) {
            int var3 = 0;
            int var4 = 0;

            while (var3 < var1.a.size() && var4 < var2.a.size()) {
               k var5 = (k)var1.a.get(var3);
               k var6;
               if ((var6 = (k)var2.a.get(var4)).c < var5.b) {
                  var4++;
               } else if (var6.b > var5.c) {
                  var3++;
               } else {
                  k var7 = null;
                  k var8 = null;
                  if (var6.b > var5.b) {
                     var7 = new k(var5.b, var6.b - 1);
                  }

                  if (var6.c < var5.c) {
                     var8 = new k(var6.c + 1, var5.c);
                  }

                  if (var7 != null) {
                     if (var8 != null) {
                        var1.a.set(var3, var7);
                        var1.a.add(var3 + 1, var8);
                        var3++;
                        var4++;
                     } else {
                        var1.a.set(var3, var7);
                        var3++;
                     }
                  } else if (var8 != null) {
                     var1.a.set(var3, var8);
                     var4++;
                  } else {
                     var1.a.remove(var3);
                  }
               }
            }

            return var1;
         } else {
            return var1;
         }
      } else {
         return null;
      }
   }

   public final boolean c(int var1) {
      int var2 = this.a.size();
      int var3 = 0;
      var2--;

      while (var3 <= var2) {
         int var4 = (var3 + var2) / 2;
         k var5;
         int var6 = (var5 = (k)this.a.get(var4)).b;
         if (var5.c < var1) {
            var3 = var4 + 1;
         } else {
            if (var6 <= var1) {
               return true;
            }

            var2 = var4 - 1;
         }
      }

      return false;
   }

   @Override
   public final boolean a() {
      return this.a == null || this.a.isEmpty();
   }

   public final int c() {
      if (this.a()) {
         throw new RuntimeException("set is empty");
      } else {
         return ((k)this.a.get(0)).b;
      }
   }

   @Override
   public int hashCode() {
      int var1 = 0;

      for (k var3 : this.a) {
         var1 = m.a(m.a(var1, var3.b), var3.c);
      }

      return m.b(var1, this.a.size() << 1);
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof l) {
         var1 = var1;
         return this.a.equals(var1.a);
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      l var1 = this;
      StringBuilder var2 = new StringBuilder();
      if (var1.a != null && !var1.a.isEmpty()) {
         if (var1.d() > 1) {
            var2.append("{");
         }

         Iterator var3 = var1.a.iterator();

         while (var3.hasNext()) {
            k var4;
            int var5 = (var4 = (k)var3.next()).b;
            int var6 = var4.c;
            if (var5 == var6) {
               if (var5 == -1) {
                  var2.append("<EOF>");
               } else {
                  var2.append(var5);
               }
            } else {
               var2.append(var5).append("..").append(var6);
            }

            if (var3.hasNext()) {
               var2.append(", ");
            }
         }

         if (var1.d() > 1) {
            var2.append("}");
         }

         return var2.toString();
      } else {
         return "{}";
      }
   }

   public final String a(S var1) {
      StringBuilder var2 = new StringBuilder();
      if (this.a != null && !this.a.isEmpty()) {
         if (this.d() > 1) {
            var2.append("{");
         }

         Iterator var3 = this.a.iterator();

         while (var3.hasNext()) {
            k var4;
            int var5 = (var4 = (k)var3.next()).b;
            int var7 = var4.c;
            if (var5 == var7) {
               var2.append(a(var1, var5));
            } else {
               for (int var6 = var5; var6 <= var7; var6++) {
                  if (var6 > var5) {
                     var2.append(", ");
                  }

                  var2.append(a(var1, var6));
               }
            }

            if (var3.hasNext()) {
               var2.append(", ");
            }
         }

         if (this.d() > 1) {
            var2.append("}");
         }

         return var2.toString();
      } else {
         return "{}";
      }
   }

   private static String a(S var0, int var1) {
      if (var1 == -1) {
         return "<EOF>";
      } else {
         return var1 == -2 ? "<EPSILON>" : var0.c(var1);
      }
   }

   public final int d() {
      int var1 = 0;
      int var2;
      if ((var2 = this.a.size()) == 1) {
         k var5;
         return (var5 = (k)this.a.get(0)).c - var5.b + 1;
      }

      for (int var3 = 0; var3 < var2; var3++) {
         k var4 = (k)this.a.get(var3);
         var1 += var4.c - var4.b + 1;
      }

      return var1;
   }

   @Override
   public final List b() {
      ArrayList var1 = new ArrayList();
      int var2 = this.a.size();

      for (int var3 = 0; var3 < var2; var3++) {
         k var4;
         int var5 = (var4 = (k)this.a.get(var3)).b;

         for (int var6 = var4.c; var5 <= var6; var5++) {
            var1.add(var5);
         }
      }

      return var1;
   }

   public final void e() {
      if (this.b) {
         throw new IllegalStateException("can't alter readonly IntervalSet");
      }

      int var1 = this.a.size();

      for (int var2 = 0; var2 < var1; var2++) {
         k var3;
         int var4 = (var3 = (k)this.a.get(var2)).b;
         int var5 = var3.c;
         if (-2 < var4) {
            break;
         }

         if (-2 == var4 && -2 == var5) {
            this.a.remove(var2);
            return;
         }

         if (-2 == var4) {
            var3.b++;
            return;
         }

         if (-2 == var5) {
            var3.c--;
            return;
         }

         if (-2 > var4 && -2 < var5) {
            var4 = var3.c;
            var3.c = -3;
            this.b(-1, var4);
         }
      }
   }

   public final void f() {
      this.b = true;
   }

   static {
      a(0, 1114111).f();
      new l().f();
   }
}
