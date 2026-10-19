package org.anti_ad.mc.common.gen;

import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class ProfilesParser$ProfileContext extends H {
   public i PROFILE() {
      return this.getToken(2, 0);
   }

   public i Id() {
      return this.getToken(30, 0);
   }

   public ProfilesParser$SlotsDefContext slotsDef() {
      return (ProfilesParser$SlotsDefContext)this.getRuleContext(ProfilesParser$SlotsDefContext.class, 0);
   }

   public i ACTIVATE() {
      return this.getToken(3, 0);
   }

   public ProfilesParser$ActiveSlotNameContext activeSlotName() {
      return (ProfilesParser$ActiveSlotNameContext)this.getRuleContext(ProfilesParser$ActiveSlotNameContext.class, 0);
   }

   public ProfilesParser$ProfileContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 1;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).enterProfile(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).exitProfile(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof ProfilesParserVisitor ? ((ProfilesParserVisitor)var1).visitProfile(this) : var1.visitChildren(this);
   }
}
