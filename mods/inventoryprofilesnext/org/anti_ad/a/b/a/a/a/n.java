package org.anti_ad.a.b.a.a.a;

final class n extends l {
   private final char[] c;

   private n(int var1, int var2, String var3, char[] var4, int var5) {
      super(var1, var2, var3, (byte)0);
      this.c = var4;
      assert var5 == 0;
   }

   @Override
   public final String a(org.anti_ad.a.b.a.a.a.c.k var1) {
      int var2 = Math.min(var1.b, super.a);
      int var3 = Math.min(var1.c - var1.b + 1, super.a - var2);
      return new String(this.c, var2, var3);
   }

   @Override
   public final int a(int var1) {
      switch (Integer.signum(var1)) {
         case -1:
            if ((var1 = super.b + var1) < 0) {
               return -1;
            }

            return this.c[var1] & 65535;
         case 0:
            return 0;
         case 1:
            if ((var1 = super.b + var1 - 1) >= super.a) {
               return -1;
            }

            return this.c[var1] & 65535;
         default:
            throw new UnsupportedOperationException("Not reached");
      }
   }
}
