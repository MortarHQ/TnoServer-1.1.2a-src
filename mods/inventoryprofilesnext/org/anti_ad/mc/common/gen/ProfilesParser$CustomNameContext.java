package org.anti_ad.mc.common.gen;

import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class ProfilesParser$CustomNameContext extends H {
   public i LPAREN() {
      return this.getToken(19, 0);
   }

   public i STRING() {
      return this.getToken(32, 0);
   }

   public i RPAREN() {
      return this.getToken(20, 0);
   }

   public ProfilesParser$CustomNameContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 6;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).enterCustomName(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).exitCustomName(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof ProfilesParserVisitor ? ((ProfilesParserVisitor)var1).visitCustomName(this) : var1.visitChildren(this);
   }
}
