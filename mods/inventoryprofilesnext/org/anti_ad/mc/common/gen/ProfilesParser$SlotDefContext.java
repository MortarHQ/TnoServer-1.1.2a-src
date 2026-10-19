package org.anti_ad.mc.common.gen;

import java.util.List;
import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;

public class ProfilesParser$SlotDefContext extends H {
   public ProfilesParser$SlotnameContext slotname() {
      return (ProfilesParser$SlotnameContext)this.getRuleContext(ProfilesParser$SlotnameContext.class, 0);
   }

   public List itemDef() {
      return this.getRuleContexts(ProfilesParser$ItemDefContext.class);
   }

   public ProfilesParser$ItemDefContext itemDef(int var1) {
      return (ProfilesParser$ItemDefContext)this.getRuleContext(ProfilesParser$ItemDefContext.class, var1);
   }

   public ProfilesParser$SlotDefContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 3;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).enterSlotDef(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).exitSlotDef(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof ProfilesParserVisitor ? ((ProfilesParserVisitor)var1).visitSlotDef(this) : var1.visitChildren(this);
   }
}
