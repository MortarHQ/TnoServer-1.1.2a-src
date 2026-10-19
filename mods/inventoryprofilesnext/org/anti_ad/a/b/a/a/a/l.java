package org.anti_ad.a.b.a.a.a;

public abstract class l implements g {
   protected final int a;
   private String c;
   protected int b;

   private l(int var1, int var2, String var3) {
      assert var1 == 0;
      this.a = var2;
      this.c = var3;
      this.b = 0;
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static l a(h var0, String var1) {
      switch (m.a[var0.c() - 1]) {
         case 1:
            return new p(var0.a(), var0.b(), var1, var0.e(), var0.d(), (byte)0);
         case 2:
            return new n(var0.a(), var0.b(), var1, var0.f(), var0.d(), (byte)0);
         case 3:
            return new o(var0.a(), var0.b(), var1, var0.g(), var0.d(), (byte)0);
         default:
            throw new UnsupportedOperationException("Not reached");
      }
   }

   @Override
   public final void a() {
      if (this.a - this.b == 0) {
         assert this.a(1) == -1;
         throw new IllegalStateException("cannot consume EOF");
      }

      this.b++;
   }

   @Override
   public final int b() {
      return this.b;
   }

   @Override
   public final int c() {
      return this.a;
   }

   @Override
   public final void b(int var1) {
      this.b = var1;
   }

   @Override
   public final String d() {
      return this.c != null && !this.c.isEmpty() ? this.c : "<unknown>";
   }

   @Override
   public final String toString() {
      return this.a(org.anti_ad.a.b.a.a.a.c.k.a(0, this.a - 1));
   }
}
