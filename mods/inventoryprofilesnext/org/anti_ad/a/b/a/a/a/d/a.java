package org.anti_ad.a.b.a.a.a.d;

public abstract class a implements f {
   public Object visit(d var1) {
      return var1.accept(this);
   }

   @Override
   public Object visitChildren(g var1) {
      Object var2 = this.defaultResult();
      int var3 = var1.getChildCount();

      for (int var4 = 0; var4 < var3 && this.shouldVisitNextChild(var1, var2); var4++) {
         Object var5 = var1.getChild(var4).accept(this);
         var2 = this.aggregateResult(var2, var5);
      }

      return var2;
   }

   @Override
   public Object visitTerminal(i var1) {
      return this.defaultResult();
   }

   @Override
   public Object visitErrorNode(b var1) {
      return this.defaultResult();
   }

   protected Object defaultResult() {
      return null;
   }

   protected Object aggregateResult(Object var1, Object var2) {
      return var2;
   }

   protected boolean shouldVisitNextChild(g var1, Object var2) {
      return true;
   }
}
