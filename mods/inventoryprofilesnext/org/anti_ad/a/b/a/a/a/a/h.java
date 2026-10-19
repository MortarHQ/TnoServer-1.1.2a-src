package org.anti_ad.a.b.a.a.a.a;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public final class h {
   private static int a = 4;
   private final g b;

   public h() {
      this(g.a());
   }

   public h(g var1) {
      if (var1 == null) {
         var1 = g.a();
      }

      this.b = var1;
   }

   public final a a(char[] var1) {
      int[] var2 = new int[var1.length];
      int var3 = 0;
      int var4 = 0;

      while (var3 < var1.length) {
         char var5;
         if (((var5 = var1[var3++]) & '耀') == 0) {
            var2[var4++] = var5;
         } else {
            char var6 = var1[var3++];
            if (var5 == '\uffff' && var6 == '\uffff') {
               var2[var4++] = -1;
            } else {
               var2[var4++] = (var5 & 32767) << 16 | var6 & '\uffff';
            }
         }
      }

      h var16 = this;
      var3 = 0;
      var3++;
      if ((var4 = var2[0]) != a) {
         String var39 = String.format(Locale.getDefault(), "Could not deserialize ATN with version %d (expected %d).", var4, a);
         throw new UnsupportedOperationException(new InvalidClassException(a.class.getName(), var39));
      }

      int[] var10000 = l.a();
      var3++;
      var4 = var10000[var2[1]];
      var3++;
      int var40 = var2[2];
      a var38 = new a(var4, var40);
      ArrayList var41 = new ArrayList();
      ArrayList var47 = new ArrayList();
      var3++;
      int var7 = var2[3];

      for (int var8 = 0; var8 < var7; var8++) {
         int var9;
         if ((var9 = var2[var3++]) == 0) {
            var38.b(null);
         } else {
            label410: {
               int var10 = var2[var3++];
               int var12 = var10;
               int var11 = var9;
               k var100;
               switch (var9) {
                  case 0:
                     var140 = null;
                     break label410;
                  case 1:
                     var100 = new s();
                     break;
                  case 2:
                     var100 = new am();
                     break;
                  case 3:
                     var100 = new r();
                     break;
                  case 4:
                     var100 = new aa();
                     break;
                  case 5:
                     var100 = new ay();
                     break;
                  case 6:
                     var100 = new aB();
                     break;
                  case 7:
                     var100 = new an();
                     break;
                  case 8:
                     var100 = new t();
                     break;
                  case 9:
                     var100 = new aA();
                     break;
                  case 10:
                     var100 = new az();
                     break;
                  case 11:
                     var100 = new ab();
                     break;
                  case 12:
                     var100 = new T();
                     break;
                  default:
                     String var99 = String.format(Locale.getDefault(), "The specified state type %d is not valid.", var11);
                     throw new IllegalArgumentException(var99);
               }

               var100.c = var12;
               var140 = var100;
            }

            k var101 = var140;
            if (var9 == 12) {
               var9 = var2[var3++];
               var41.add(new org.anti_ad.a.b.a.a.a.c.o((T)var101, var9));
            } else if (var101 instanceof u) {
               var9 = var2[var3++];
               var47.add(new org.anti_ad.a.b.a.a.a.c.o((u)var101, var9));
            }

            var38.b(var101);
         }
      }

      Iterator var61 = var41.iterator();

      while (var61.hasNext()) {
         org.anti_ad.a.b.a.a.a.c.o var78;
         ((T)(var78 = (org.anti_ad.a.b.a.a.a.c.o)var61.next()).a).g = (k)var38.a.get((Integer)var78.b);
      }

      var61 = var47.iterator();

      while (var61.hasNext()) {
         org.anti_ad.a.b.a.a.a.c.o var79;
         ((u)(var79 = (org.anti_ad.a.b.a.a.a.c.o)var61.next()).a).g = (t)var38.a.get((Integer)var79.b);
      }

      int var63 = var2[var3++];

      for (int var80 = 0; var80 < var63; var80++) {
         int var86 = var2[var3++];
         ((y)var38.a.get(var86)).i = true;
      }

      int var81 = var2[var3++];

      for (int var87 = 0; var87 < var81; var87++) {
         int var102 = var2[var3++];
         ((am)var38.a.get(var102)).h = true;
      }

      int var88 = var2[var3++];
      if (var38.e == l.a) {
         var38.g = new int[var88];
      }

      var38.c = new am[var88];

      for (int var103 = 0; var103 < var88; var103++) {
         var81 = var2[var3++];
         am var42 = (am)var38.a.get(var81);
         var38.c[var103] = var42;
         if (var38.e == l.a) {
            int var48 = var2[var3++];
            var38.g[var103] = var48;
         }
      }

      var38.d = new an[var88];
      Iterator var104 = var38.a.iterator();

      while (var104.hasNext()) {
         k var83;
         if ((var83 = (k)var104.next()) instanceof an) {
            an var43 = (an)var83;
            var38.d[var83.c] = var43;
            var38.c[var83.c].g = var43;
         }
      }

      int var105 = var2[var3++];

      for (int var84 = 0; var84 < var105; var84++) {
         var40 = var2[var3++];
         var38.i.add((aB)var38.a.get(var40));
      }

      ArrayList var85 = new ArrayList();
      ArrayList var64 = var85;
      int var117 = var3;
      int[] var106 = var2;
      var88 = var2[var117++];

      for (int var13 = 0; var13 < var88; var13++) {
         int var14 = var106[var117];
         var117++;
         org.anti_ad.a.b.a.a.a.c.l var15 = new org.anti_ad.a.b.a.a.a.c.l();
         var64.add(var15);
         if (var106[var117++] != 0) {
            var15.b(-1);
         }

         for (int var27 = 0; var27 < var14; var27++) {
            var40 = var106[var117++];
            int var49 = var106[var117++];
            var15.b(var40, var49);
         }
      }

      var3 = var117;
      var40 = var2[var3++];

      for (int var50 = 0; var50 < var40; var50++) {
         var7 = var2[var3];
         int var65 = var2[var3 + 1];
         var88 = var2[var3 + 2];
         int var107 = var2[var3 + 3];
         var117 = var2[var3 + 4];
         int var131 = var2[var3 + 5];
         ArrayList var139 = var85;
         int var138 = var131;
         var131 = var117;
         int var91 = var107;
         int var66 = var65;
         var117 = var88;
         a var108 = var38;
         k var67 = (k)var38.a.get(var66);
         aC var141;
         switch (var117) {
            case 1:
               var141 = new A(var67);
               break;
            case 2:
               var141 = var138 != 0 ? new al(var67, -1, var131) : new al(var67, var91, var131);
               break;
            case 3:
               var141 = new ao((am)var108.a.get(var91), var131, var138, var67);
               break;
            case 4:
               var141 = new ae(var67, var91, var131, var138 != 0);
               break;
            case 5:
               var141 = var138 != 0 ? new q(var67, -1) : new q(var67, var91);
               break;
            case 6:
               var141 = new n(var67, var91, var131, var138 != 0);
               break;
            case 7:
               var141 = new aw(var67, (org.anti_ad.a.b.a.a.a.c.l)var139.get(var91));
               break;
            case 8:
               var141 = new U(var67, (org.anti_ad.a.b.a.a.a.c.l)var139.get(var91));
               break;
            case 9:
               var141 = new aE(var67);
               break;
            case 10:
               var141 = new ac(var67, var91);
               break;
            default:
               throw new IllegalArgumentException("The specified transition type is not valid.");
         }

         aC var123 = var141;
         ((k)var38.a.get(var7)).a(var123);
         var3 += 6;
      }

      for (k var55 : var38.a) {
         for (int var68 = 0; var68 < var55.a(); var68++) {
            aC var92;
            if ((var92 = var55.a(var68)) instanceof ao) {
               ao var109 = (ao)var92;
               var117 = -1;
               if (var38.c[var109.d.c].h && var109.a == 0) {
                  var117 = var109.d.c;
               }

               A var133 = new A(var109.b, var117);
               var38.d[var109.d.c].a(var133);
            }
         }
      }

      Iterator var52 = var38.a.iterator();

      while (var52.hasNext()) {
         k var56;
         if ((var56 = (k)var52.next()) instanceof u) {
            if (((u)var56).g == null) {
               throw new IllegalStateException();
            }

            if (((u)var56).g.g != null) {
               throw new IllegalStateException();
            }

            ((u)var56).g.g = (u)var56;
         }

         if (var56 instanceof ab) {
            ab var70 = (ab)var56;

            for (int var94 = 0; var94 < var70.a(); var94++) {
               k var111;
               if ((var111 = var70.a(var94).d) instanceof aa) {
                  ((aa)var111).j = var70;
               }
            }
         } else if (var56 instanceof aA) {
            aA var69 = (aA)var56;

            for (int var93 = 0; var93 < var69.a(); var93++) {
               k var110;
               if ((var110 = var69.a(var93).d) instanceof az) {
                  ((az)var110).g = var69;
               }
            }
         }
      }

      int var53 = var2[var3++];

      for (int var57 = 1; var57 <= var53; var57++) {
         int var71 = var2[var3++];
         y var95 = (y)var38.a.get(var71);
         var38.b.add(var95);
         var95.h = var57 - 1;
      }

      if (var38.e == l.a) {
         var38.h = new G[var2[var3++]];

         for (int var58 = 0; var58 < var38.h.length; var58++) {
            I var72 = I.a()[var2[var3++]];
            var88 = var2[var3++];
            int var112 = var2[var3++];
            int var73 = var112;
            var117 = var88;
            I var113 = var72;
            G var144;
            switch (var113) {
               case a:
                  var144 = new J(var117);
                  break;
               case b:
                  var144 = new K(var117, var73);
                  break;
               case c:
                  var144 = new M(var117);
                  break;
               case d:
                  var144 = N.a;
                  break;
               case e:
                  var144 = O.a;
                  break;
               case f:
                  var144 = new P(var117);
                  break;
               case g:
                  var144 = Q.a;
                  break;
               case h:
                  var144 = new R(var117);
                  break;
               default:
                  throw new IllegalArgumentException(String.format(Locale.getDefault(), "The specified lexer action type %s is not valid.", var113));
            }

            G var126 = var144;
            var38.h[var58] = var126;
         }
      }

      a var114 = var38;
      Iterator var127 = var38.a.iterator();

      while (var127.hasNext()) {
         k var74;
         k var97;
         if ((var74 = (k)var127.next()) instanceof az
            && var114.c[var74.c].h
            && (var97 = var74.a(var74.a() - 1).d) instanceof T
            && var97.d
            && var97.a(0).d instanceof an) {
            ((az)var74).j = true;
         }
      }

      if (var16.b.b()) {
         a(var38);
      }

      if (var16.b.c() && var38.e == l.b) {
         var38.g = new int[var38.c.length];

         for (int var59 = 0; var59 < var38.c.length; var59++) {
            var38.g[var59] = var38.f + var59 + 1;
         }

         for (int var60 = 0; var60 < var38.c.length; var60++) {
            r var75;
            (var75 = new r()).c = var60;
            var38.b(var75);
            t var98;
            (var98 = new t()).c = var60;
            var38.b(var98);
            var75.g = var98;
            r var128 = var75;
            a var115 = var38;
            var38.b.add(var128);
            var128.h = var115.b.size() - 1;
            var98.g = var75;
            aC var129 = null;
            k var116;
            if (!var38.c[var60].h) {
               var116 = var38.d[var60];
            } else {
               var116 = null;
               Iterator var134 = var38.a.iterator();

               while (var134.hasNext()) {
                  k var34;
                  k var130;
                  if ((var130 = (k)var134.next()).c == var60
                     && var130 instanceof az
                     && (var34 = var130.a(var130.a() - 1).d) instanceof T
                     && var34.d
                     && var34.a(0).d instanceof an) {
                     var116 = var130;
                     break;
                  }
               }

               if (var116 == null) {
                  throw new UnsupportedOperationException("Couldn't identify final state of the precedence rule prefix section.");
               }

               var129 = ((az)var116).g.a(0);
            }

            Iterator var135 = var38.a.iterator();

            while (var135.hasNext()) {
               Iterator var35 = ((k)var135.next()).e.iterator();

               while (var35.hasNext()) {
                  aC var17;
                  if ((var17 = (aC)var35.next()) != var129 && var17.d == var116) {
                     var17.d = var98;
                  }
               }
            }

            while (var38.c[var60].a() > 0) {
               aC var136 = var38.c[var60].b(var38.c[var60].a() - 1);
               var75.a(var136);
            }

            var38.c[var60].a(new A(var75));
            var98.a(new A(var116));
            s var137 = new s();
            var38.b(var137);
            var137.a(new q(var98, var38.g[var60]));
            var75.a(new A(var137));
         }

         if (var16.b.b()) {
            a(var38);
         }
      }

      return var38;
   }

   private static void a(a var0) {
      Iterator var3 = var0.a.iterator();

      while (var3.hasNext()) {
         k var1;
         if ((var1 = (k)var3.next()) != null) {
            a(var1.d || var1.a() <= 1);
            if (var1 instanceof aa) {
               a(((aa)var1).j != null);
            }

            if (var1 instanceof az) {
               az var2;
               a((var2 = (az)var1).g != null);
               a(var2.a() == 2);
               if (var2.a(0).d instanceof ay) {
                  a(var2.a(1).d instanceof T);
                  a(!var2.i);
               } else {
                  if (!(var2.a(0).d instanceof T)) {
                     throw new IllegalStateException();
                  }

                  a(var2.a(1).d instanceof ay);
                  a(var2.i);
               }
            }

            if (var1 instanceof aA) {
               a(var1.a() == 1);
               a(var1.a(0).d instanceof az);
            }

            if (var1 instanceof T) {
               a(((T)var1).g != null);
            }

            if (var1 instanceof am) {
               a(((am)var1).g != null);
            }

            if (var1 instanceof u) {
               a(((u)var1).g != null);
            }

            if (var1 instanceof t) {
               a(((t)var1).g != null);
            }

            if (var1 instanceof y) {
               y var4;
               a((var4 = (y)var1).a() <= 1 || var4.h >= 0);
            } else {
               a(var1.a() <= 1 || var1 instanceof an);
            }
         }
      }
   }

   private static void a(boolean var0) {
      if (!var0) {
         throw new IllegalStateException((String)null);
      }
   }
}
