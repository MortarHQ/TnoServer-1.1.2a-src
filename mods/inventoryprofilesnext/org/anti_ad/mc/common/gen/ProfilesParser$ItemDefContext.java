package org.anti_ad.mc.common.gen;

import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class ProfilesParser$ItemDefContext extends H {
   public ProfilesParser$ItemNameContext itemName() {
      return (ProfilesParser$ItemNameContext)this.getRuleContext(ProfilesParser$ItemNameContext.class, 0);
   }

   public i ARROW() {
      return this.getToken(28, 0);
   }

   public ProfilesParser$ComponentsContext components() {
      return (ProfilesParser$ComponentsContext)this.getRuleContext(ProfilesParser$ComponentsContext.class, 0);
   }

   public ProfilesParser$ItemDefContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 4;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).enterItemDef(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).exitItemDef(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof ProfilesParserVisitor ? ((ProfilesParserVisitor)var1).visitItemDef(this) : var1.visitChildren(this);
   }
}
