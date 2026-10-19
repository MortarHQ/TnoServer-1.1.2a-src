package org.anti_ad.mc.common.gen;

import java.util.List;
import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;
import org.anti_ad.a.b.a.a.a.d.i;

public class ProfilesParser$ScriptContext extends H {
   public i EOF() {
      return this.getToken(-1, 0);
   }

   public List profile() {
      return this.getRuleContexts(ProfilesParser$ProfileContext.class);
   }

   public ProfilesParser$ProfileContext profile(int var1) {
      return (ProfilesParser$ProfileContext)this.getRuleContext(ProfilesParser$ProfileContext.class, var1);
   }

   public ProfilesParser$ScriptContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 0;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).enterScript(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).exitScript(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof ProfilesParserVisitor ? ((ProfilesParserVisitor)var1).visitScript(this) : var1.visitChildren(this);
   }
}
