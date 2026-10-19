package org.anti_ad.a.b.a.a.a.d;

import org.anti_ad.a.b.a.a.a.M;
import org.anti_ad.a.b.a.a.a.O;

public class j implements i {
   private O a;
   private M b;

   public j(O var1) {
      this.a = var1;
   }

   @Override
   public d getChild(int var1) {
      return null;
   }

   @Override
   public final O a() {
      return this.a;
   }

   @Override
   public void setParent(M var1) {
      this.b = var1;
   }

   @Override
   public int getChildCount() {
      return 0;
   }

   @Override
   public Object accept(f var1) {
      return var1.visitTerminal(this);
   }

   @Override
   public String getText() {
      return this.a.b();
   }

   @Override
   public String toString() {
      return this.a.a() == -1 ? "<EOF>" : this.a.b();
   }
}
