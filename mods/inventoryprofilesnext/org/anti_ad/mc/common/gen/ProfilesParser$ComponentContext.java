package org.anti_ad.mc.common.gen;

import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class ProfilesParser$ComponentContext extends H {
   public i NamespacedId() {
      return this.getToken(31, 0);
   }

   public ProfilesParser$CustomNameContext customName() {
      return (ProfilesParser$CustomNameContext)this.getRuleContext(ProfilesParser$CustomNameContext.class, 0);
   }

   public ProfilesParser$ComponentContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 8;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).enterComponent(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).exitComponent(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof ProfilesParserVisitor ? ((ProfilesParserVisitor)var1).visitComponent(this) : var1.visitChildren(this);
   }
}
