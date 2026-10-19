package org.anti_ad.mc.common.gen;

import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class ProfilesParser$SlotnameContext extends H {
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

   public i CHESTPLATE() {
      return this.getToken(13, 0);
   }

   public i LEGS() {
      return this.getToken(14, 0);
   }

   public i FEET() {
      return this.getToken(15, 0);
   }

   public i HEAD() {
      return this.getToken(16, 0);
   }

   public i OFFHAND() {
      return this.getToken(17, 0);
   }

   public ProfilesParser$SlotnameContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 10;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).enterSlotname(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).exitSlotname(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof ProfilesParserVisitor ? ((ProfilesParserVisitor)var1).visitSlotname(this) : var1.visitChildren(this);
   }
}
