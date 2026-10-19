package org.anti_ad.mc.common.gen;

import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class ProfilesParser$ItemNameContext extends H {
   public i NamespacedId() {
      return this.getToken(31, 0);
   }

   public ProfilesParser$CustomNameContext customName() {
      return (ProfilesParser$CustomNameContext)this.getRuleContext(ProfilesParser$CustomNameContext.class, 0);
   }

   public ProfilesParser$ItemNameContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 5;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).enterItemName(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).exitItemName(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof ProfilesParserVisitor ? ((ProfilesParserVisitor)var1).visitItemName(this) : var1.visitChildren(this);
   }
}
