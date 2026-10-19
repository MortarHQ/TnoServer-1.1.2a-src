package org.anti_ad.a.b.a.a.a;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class H extends M {
   public static final H EMPTY = new H();
   public List children;
   public O start;
   public O stop;
   public J exception;

   public H() {
   }

   public void copyFrom(H var1) {
      super.parent = var1.parent;
      super.invokingState = var1.invokingState;
      this.start = var1.start;
      this.stop = var1.stop;
      if (var1.children != null) {
         this.children = new ArrayList();
         Iterator var3 = var1.children.iterator();

         while (var3.hasNext()) {
            org.anti_ad.a.b.a.a.a.d.d var2;
            if ((var2 = (org.anti_ad.a.b.a.a.a.d.d)var3.next()) instanceof org.anti_ad.a.b.a.a.a.d.b) {
               this.addChild((org.anti_ad.a.b.a.a.a.d.b)var2);
            }
         }
      }
   }

   public H(H var1, int var2) {
      super(var1, var2);
   }

   public void enterRule(org.anti_ad.a.b.a.a.a.d.e var1) {
   }

   public void exitRule(org.anti_ad.a.b.a.a.a.d.e var1) {
   }

   public org.anti_ad.a.b.a.a.a.d.d addAnyChild(org.anti_ad.a.b.a.a.a.d.d var1) {
      if (this.children == null) {
         this.children = new ArrayList();
      }

      this.children.add(var1);
      return var1;
   }

   public M addChild(M var1) {
      return (M)this.addAnyChild(var1);
   }

   public org.anti_ad.a.b.a.a.a.d.i addChild(org.anti_ad.a.b.a.a.a.d.i var1) {
      var1.setParent(this);
      return (org.anti_ad.a.b.a.a.a.d.i)this.addAnyChild(var1);
   }

   public org.anti_ad.a.b.a.a.a.d.b addErrorNode(org.anti_ad.a.b.a.a.a.d.b var1) {
      var1.setParent(this);
      return (org.anti_ad.a.b.a.a.a.d.b)this.addAnyChild(var1);
   }

   @Deprecated
   public org.anti_ad.a.b.a.a.a.d.i addChild(O var1) {
      org.anti_ad.a.b.a.a.a.d.j var2 = new org.anti_ad.a.b.a.a.a.d.j(var1);
      this.addAnyChild(var2);
      var2.setParent(this);
      return var2;
   }

   @Deprecated
   public org.anti_ad.a.b.a.a.a.d.b addErrorNode(O var1) {
      org.anti_ad.a.b.a.a.a.d.c var2 = new org.anti_ad.a.b.a.a.a.d.c(var1);
      this.addAnyChild(var2);
      var2.setParent(this);
      return var2;
   }

   public void removeLastChild() {
      if (this.children != null) {
         this.children.remove(this.children.size() - 1);
      }
   }

   public H getParent() {
      return (H)super.getParent();
   }

   @Override
   public org.anti_ad.a.b.a.a.a.d.d getChild(int var1) {
      return this.children != null && var1 >= 0 && var1 < this.children.size() ? (org.anti_ad.a.b.a.a.a.d.d)this.children.get(var1) : null;
   }

   public org.anti_ad.a.b.a.a.a.d.d getChild(Class var1, int var2) {
      if (this.children != null && var2 >= 0 && var2 < this.children.size()) {
         int var3 = -1;

         for (org.anti_ad.a.b.a.a.a.d.d var5 : this.children) {
            if (var1.isInstance(var5)) {
               if (++var3 == var2) {
                  return (org.anti_ad.a.b.a.a.a.d.d)var1.cast(var5);
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public org.anti_ad.a.b.a.a.a.d.i getToken(int var1, int var2) {
      if (this.children != null && var2 >= 0 && var2 < this.children.size()) {
         int var3 = -1;
         Iterator var4 = this.children.iterator();

         while (var4.hasNext()) {
            org.anti_ad.a.b.a.a.a.d.d var5;
            if ((var5 = (org.anti_ad.a.b.a.a.a.d.d)var4.next()) instanceof org.anti_ad.a.b.a.a.a.d.i && (var5 = var5).a().a() == var1) {
               if (++var3 == var2) {
                  return var5;
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public List getTokens(int var1) {
      if (this.children == null) {
         return Collections.emptyList();
      }

      ArrayList var2 = null;
      Iterator var3 = this.children.iterator();

      while (var3.hasNext()) {
         org.anti_ad.a.b.a.a.a.d.d var4;
         org.anti_ad.a.b.a.a.a.d.i var5;
         if ((var4 = (org.anti_ad.a.b.a.a.a.d.d)var3.next()) instanceof org.anti_ad.a.b.a.a.a.d.i && (var5 = (org.anti_ad.a.b.a.a.a.d.i)var4).a().a() == var1) {
            if (var2 == null) {
               var2 = new ArrayList();
            }

            var2.add(var5);
         }
      }

      return var2 == null ? Collections.emptyList() : var2;
   }

   public H getRuleContext(Class var1, int var2) {
      return (H)this.getChild(var1, var2);
   }

   public List getRuleContexts(Class var1) {
      if (this.children == null) {
         return Collections.emptyList();
      }

      ArrayList var2 = null;

      for (org.anti_ad.a.b.a.a.a.d.d var4 : this.children) {
         if (var1.isInstance(var4)) {
            if (var2 == null) {
               var2 = new ArrayList();
            }

            var2.add((H)var1.cast(var4));
         }
      }

      return var2 == null ? Collections.emptyList() : var2;
   }

   @Override
   public int getChildCount() {
      return this.children != null ? this.children.size() : 0;
   }

   @Override
   public org.anti_ad.a.b.a.a.a.c.k getSourceInterval() {
      if (this.start == null) {
         return org.anti_ad.a.b.a.a.a.c.k.a;
      } else {
         return this.stop != null && this.stop.h() >= this.start.h()
            ? org.anti_ad.a.b.a.a.a.c.k.a(this.start.h(), this.stop.h())
            : org.anti_ad.a.b.a.a.a.c.k.a(this.start.h(), this.start.h() - 1);
      }
   }

   public O getStart() {
      return this.start;
   }

   public O getStop() {
      return this.stop;
   }

   public String toInfoString(D var1) {
      List var2;
      Collections.reverse(var2 = var1.getRuleInvocationStack(this));
      return "ParserRuleContext" + var2 + "{start=" + this.start + ", stop=" + this.stop + '}';
   }
}
