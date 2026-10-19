package org.anti_ad.a.b.a.a.a;

import java.util.List;

public final class B implements Q {
   private List a;
   private final String b;
   private int c;
   private O d;
   private P e = r.a;

   public B(List var1) {
      this(var1, (byte)0);
   }

   private B(List var1, byte var2) {
      if (var1 == null) {
         throw new NullPointerException("tokens cannot be null");
      }

      this.a = var1;
      this.b = null;
   }

   @Override
   public final int getCharPositionInLine() {
      if (this.c < this.a.size()) {
         return ((O)this.a.get(this.c)).d();
      } else if (this.d != null) {
         return this.d.d();
      } else if (this.a.size() > 0) {
         O var1;
         String var2;
         int var3;
         return (var2 = (var1 = (O)this.a.get(this.a.size() - 1)).b()) != null && (var3 = var2.lastIndexOf(10)) >= 0
            ? var2.length() - var3 - 1
            : var1.d() + var1.g() - var1.f() + 1;
      } else {
         return 0;
      }
   }

   @Override
   public final O nextToken() {
      if (this.c >= this.a.size()) {
         if (this.d == null) {
            int var3 = -1;
            int var2;
            if (this.a.size() > 0 && (var2 = ((O)this.a.get(this.a.size() - 1)).g()) != -1) {
               var3 = var2 + 1;
            }

            var2 = Math.max(-1, var3 - 1);
            this.d = this.e
               .a(new org.anti_ad.a.b.a.a.a.c.o(this, this.getInputStream()), -1, "EOF", 0, var3, var2, this.getLine(), this.getCharPositionInLine());
         }

         return this.d;
      } else {
         O var1 = (O)this.a.get(this.c);
         if (this.c == this.a.size() - 1 && var1.a() == -1) {
            this.d = var1;
         }

         this.c++;
         return var1;
      }
   }

   @Override
   public final int getLine() {
      if (this.c < this.a.size()) {
         return ((O)this.a.get(this.c)).c();
      }

      if (this.d != null) {
         return this.d.c();
      }

      if (this.a.size() <= 0) {
         return 1;
      }

      O var1;
      int var2 = (var1 = (O)this.a.get(this.a.size() - 1)).c();
      String var4;
      if ((var4 = var1.b()) != null) {
         for (int var3 = 0; var3 < var4.length(); var3++) {
            if (var4.charAt(var3) == '\n') {
               var2++;
            }
         }
      }

      return var2;
   }

   @Override
   public final g getInputStream() {
      if (this.c < this.a.size()) {
         return ((O)this.a.get(this.c)).j();
      } else if (this.d != null) {
         return this.d.j();
      } else {
         return this.a.size() > 0 ? ((O)this.a.get(this.a.size() - 1)).j() : null;
      }
   }

   @Override
   public final String getSourceName() {
      g var1;
      return (var1 = this.getInputStream()) != null ? var1.d() : "List";
   }

   @Override
   public final void setTokenFactory(P var1) {
      this.e = var1;
   }

   @Override
   public final P getTokenFactory() {
      return this.e;
   }
}
