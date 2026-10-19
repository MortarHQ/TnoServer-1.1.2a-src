package org.anti_ad.a.b.a.a.a;

import java.util.ArrayList;

public final class F implements org.anti_ad.a.b.a.a.a.d.e {
   public static final F a = new F();

   @Override
   public final void enterEveryRule(H var1) {
   }

   @Override
   public final void visitTerminal(org.anti_ad.a.b.a.a.a.d.i var1) {
   }

   @Override
   public final void visitErrorNode(org.anti_ad.a.b.a.a.a.d.b var1) {
   }

   @Override
   public final void exitEveryRule(H var1) {
      if (var1.children instanceof ArrayList) {
         ((ArrayList)var1.children).trimToSize();
      }
   }
}
