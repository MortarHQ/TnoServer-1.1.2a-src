package org.anti_ad.mc.common.gen;

import java.util.List;
import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class ProfilesParser$ComponentsContext extends H {
   public i LBRACK() {
      return this.getToken(21, 0);
   }

   public List component() {
      return this.getRuleContexts(ProfilesParser$ComponentContext.class);
   }

   public ProfilesParser$ComponentContext component(int var1) {
      return (ProfilesParser$ComponentContext)this.getRuleContext(ProfilesParser$ComponentContext.class, var1);
   }

   public i RBRACK() {
      return this.getToken(22, 0);
   }

   public List COMMA() {
      return this.getTokens(18);
   }

   public i COMMA(int var1) {
      return this.getToken(18, var1);
   }

   public ProfilesParser$ComponentsContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 7;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).enterComponents(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).exitComponents(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof ProfilesParserVisitor ? ((ProfilesParserVisitor)var1).visitComponents(this) : var1.visitChildren(this);
   }
}
