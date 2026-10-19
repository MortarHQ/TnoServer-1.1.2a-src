package org.anti_ad.mc.common.gen;

import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class ProfilesParser$ActiveSlotNameContext extends H {
   public i HOT1() {
      return this.getToken(4, 0);
   }

   public i HOT2() {
      return this.getToken(5, 0);
   }

   public i HOT3() {
      return this.getToken(6, 0);
   }

   public i HOT4() {
      return this.getToken(7, 0);
   }

   public i HOT5() {
      return this.getToken(8, 0);
   }

   public i HOT6() {
      return this.getToken(9, 0);
   }

   public i HOT7() {
      return this.getToken(10, 0);
   }

   public i HOT8() {
      return this.getToken(11, 0);
   }

   public i HOT9() {
      return this.getToken(12, 0);
   }

   public ProfilesParser$ActiveSlotNameContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 11;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).enterActiveSlotName(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).exitActiveSlotName(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof ProfilesParserVisitor ? ((ProfilesParserVisitor)var1).visitActiveSlotName(this) : var1.visitChildren(this);
   }
}
