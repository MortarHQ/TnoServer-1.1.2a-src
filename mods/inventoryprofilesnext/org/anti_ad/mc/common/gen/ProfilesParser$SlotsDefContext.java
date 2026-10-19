package org.anti_ad.mc.common.gen;

import java.util.List;
import org.anti_ad.a.b.a.a.a.H;
import org.anti_ad.a.b.a.a.a.d.e;
import org.anti_ad.a.b.a.a.a.d.f;

public class ProfilesParser$SlotsDefContext extends H {
   public List slotDef() {
      return this.getRuleContexts(ProfilesParser$SlotDefContext.class);
   }

   public ProfilesParser$SlotDefContext slotDef(int var1) {
      return (ProfilesParser$SlotDefContext)this.getRuleContext(ProfilesParser$SlotDefContext.class, var1);
   }

   public ProfilesParser$SlotsDefContext(H var1, int var2) {
      super(var1, var2);
   }

   @Override
   public int getRuleIndex() {
      return 2;
   }

   @Override
   public void enterRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).enterSlotsDef(this);
      }
   }

   @Override
   public void exitRule(e var1) {
      if (var1 instanceof ProfilesParserListener) {
         ((ProfilesParserListener)var1).exitSlotsDef(this);
      }
   }

   @Override
   public Object accept(f var1) {
      return var1 instanceof ProfilesParserVisitor ? ((ProfilesParserVisitor)var1).visitSlotsDef(this) : var1.visitChildren(this);
   }
}
