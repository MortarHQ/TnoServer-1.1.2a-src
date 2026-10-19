package org.anti_ad.a.b.a.a.a;

public class J extends RuntimeException {
   private final K a;
   private final H b;
   private final x c;
   private O d;
   private int e = -1;

   public J(K var1, x var2, H var3) {
      this.a = var1;
      this.c = var2;
      this.b = var3;
      if (var1 != null) {
         this.e = var1.getState();
      }
   }

   public J(String var1, G var2, R var3, H var4) {
      super(var1);
      this.a = var2;
      this.c = var3;
      this.b = var4;
      if (var2 != null) {
         this.e = var2.getState();
      }
   }

   protected final void a(int var1) {
      this.e = var1;
   }

   public final org.anti_ad.a.b.a.a.a.c.l c() {
      return this.a != null ? this.a.getATN().a(this.e, this.b) : null;
   }

   public x a() {
      return this.c;
   }

   public final O d() {
      return this.d;
   }

   protected final void a(O var1) {
      this.d = var1;
   }
}
