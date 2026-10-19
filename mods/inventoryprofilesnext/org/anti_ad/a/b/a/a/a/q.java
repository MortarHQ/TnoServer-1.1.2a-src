package org.anti_ad.a.b.a.a.a;

import java.io.Serializable;

public class q implements Serializable, U {
   private static org.anti_ad.a.b.a.a.a.c.o b = new org.anti_ad.a.b.a.a.a.c.o(null, null);
   protected int a;
   private int c;
   private int d = -1;
   private int e = 0;
   private org.anti_ad.a.b.a.a.a.c.o f;
   private String g;
   private int h = -1;
   private int i;
   private int j;

   public q(int var1) {
      this.a = var1;
      this.f = b;
   }

   public q(org.anti_ad.a.b.a.a.a.c.o var1, int var2, int var3, int var4, int var5) {
      this.f = var1;
      this.a = var2;
      this.e = var3;
      this.i = var4;
      this.j = var5;
      if (var1.a != null) {
         this.c = ((Q)var1.a).getLine();
         this.d = ((Q)var1.a).getCharPositionInLine();
      }
   }

   @Override
   public final int a() {
      return this.a;
   }

   public final void a(int var1) {
      this.c = var1;
   }

   @Override
   public String b() {
      if (this.g != null) {
         return this.g;
      }

      g var1;
      if ((var1 = this.j()) == null) {
         return null;
      }

      int var2 = var1.c();
      return this.i < var2 && this.j < var2 ? var1.a(org.anti_ad.a.b.a.a.a.c.k.a(this.i, this.j)) : "<EOF>";
   }

   public final void a(String var1) {
      this.g = var1;
   }

   @Override
   public final int c() {
      return this.c;
   }

   @Override
   public final int d() {
      return this.d;
   }

   public final void b(int var1) {
      this.d = var1;
   }

   @Override
   public final int e() {
      return this.e;
   }

   @Override
   public final int f() {
      return this.i;
   }

   @Override
   public final int g() {
      return this.j;
   }

   @Override
   public final int h() {
      return this.h;
   }

   @Override
   public final void c(int var1) {
      this.h = var1;
   }

   @Override
   public final Q i() {
      return (Q)this.f.a;
   }

   @Override
   public final g j() {
      return (g)this.f.b;
   }

   @Override
   public String toString() {
      q var1 = this;
      String var2 = "";
      if (var1.e > 0) {
         var2 = ",channel=" + var1.e;
      }

      String var3;
      if ((var3 = var1.b()) != null) {
         var3 = var3.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
      } else {
         var3 = "<no text>";
      }

      String var4 = String.valueOf(var1.a);
      return "[@" + var1.h + "," + var1.i + ":" + var1.j + "='" + var3 + "',<" + var4 + ">" + var2 + "," + var1.c + ":" + var1.d + "]";
   }
}
