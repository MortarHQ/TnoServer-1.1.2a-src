package org.anti_ad.a.b.a.a.a;

public class E implements org.anti_ad.a.b.a.a.a.d.e {
   public E(D var1) {
      this.a = var1;
      super();
   }

   @Override
   public void enterEveryRule(H var1) {
      System.out.println("enter   " + this.a.getRuleNames()[var1.getRuleIndex()] + ", LT(1)=" + this.a._input.f(1).b());
   }

   @Override
   public void visitTerminal(org.anti_ad.a.b.a.a.a.d.i var1) {
      System.out.println("consume " + var1.a() + " rule " + this.a.getRuleNames()[this.a._ctx.getRuleIndex()]);
   }

   @Override
   public void visitErrorNode(org.anti_ad.a.b.a.a.a.d.b var1) {
   }

   @Override
   public void exitEveryRule(H var1) {
      System.out.println("exit    " + this.a.getRuleNames()[var1.getRuleIndex()] + ", LT(1)=" + this.a._input.f(1).b());
   }
}
