package org.anti_ad.a.b.a.a.a;

import java.util.Arrays;
import java.util.List;

public class M implements org.anti_ad.a.b.a.a.a.d.g {
   public M parent;
   public int invokingState = -1;

   public M() {
   }

   public M(M var1, int var2) {
      this.parent = var1;
      this.invokingState = var2;
   }

   public int depth() {
      int var1 = 0;

      for (M var2 = this; var2 != null; var1++) {
         var2 = var2.parent;
      }

      return var1;
   }

   public boolean isEmpty() {
      return this.invokingState == -1;
   }

   public org.anti_ad.a.b.a.a.a.c.k getSourceInterval() {
      return org.anti_ad.a.b.a.a.a.c.k.a;
   }

   public M getRuleContext() {
      return this;
   }

   public M getParent() {
      return this.parent;
   }

   public M getPayload() {
      return this;
   }

   @Override
   public String getText() {
      if (this.getChildCount() == 0) {
         return "";
      }

      StringBuilder var1 = new StringBuilder();

      for (int var2 = 0; var2 < this.getChildCount(); var2++) {
         var1.append(this.getChild(var2).getText());
      }

      return var1.toString();
   }

   public int getRuleIndex() {
      return -1;
   }

   public int getAltNumber() {
      return 0;
   }

   public void setAltNumber(int var1) {
   }

   @Override
   public void setParent(M var1) {
      this.parent = var1;
   }

   @Override
   public org.anti_ad.a.b.a.a.a.d.d getChild(int var1) {
      return null;
   }

   @Override
   public int getChildCount() {
      return 0;
   }

   @Override
   public Object accept(org.anti_ad.a.b.a.a.a.d.f var1) {
      return var1.visitChildren(this);
   }

   public String toStringTree(D var1) {
      D var2 = var1;
      M var3 = this;
      String[] var4;
      List var5 = (var4 = var2 != null ? var2.getRuleNames() : null) != null ? Arrays.asList(var4) : null;
      return org.anti_ad.a.b.a.a.a.d.l.a(var3, var5);
   }

   public String toStringTree(List var1) {
      return org.anti_ad.a.b.a.a.a.d.l.a(this, var1);
   }

   public String toStringTree() {
      return this.toStringTree((List)null);
   }

   @Override
   public String toString() {
      return this.toString((List)null, null);
   }

   public final String toString(K var1) {
      return this.toString(var1, H.EMPTY);
   }

   public final String toString(List var1) {
      return this.toString(var1, null);
   }

   public String toString(K var1, M var2) {
      String[] var3;
      List var4 = (var3 = var1 != null ? var1.getRuleNames() : null) != null ? Arrays.asList(var3) : null;
      return this.toString(var4, var2);
   }

   public String toString(List var1, M var2) {
      StringBuilder var3 = new StringBuilder();
      M var4 = this;
      var3.append("[");

      for (; var4 != null && var4 != var2; var4 = var4.parent) {
         if (var1 == null) {
            if (!var4.isEmpty()) {
               var3.append(var4.invokingState);
            }
         } else {
            int var5;
            String var6 = (var5 = var4.getRuleIndex()) >= 0 && var5 < var1.size() ? (String)var1.get(var5) : Integer.toString(var5);
            var3.append(var6);
         }

         if (var4.parent != null && (var1 != null || !var4.parent.isEmpty())) {
            var3.append(" ");
         }
      }

      var3.append("]");
      return var3.toString();
   }
}
