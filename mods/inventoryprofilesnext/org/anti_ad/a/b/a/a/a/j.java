package org.anti_ad.a.b.a.a.a;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

public final class j {
   private int a;
   private ByteBuffer b;
   private CharBuffer c;
   private IntBuffer d;
   private int e;

   private j(int var1) {
      this.a = k.a;
      this.b = ByteBuffer.allocate(var1);
      this.c = null;
      this.d = null;
      this.e = -1;
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final h a() {
      switch (i.a[this.a - 1]) {
         case 1:
            ((Buffer)this.b).flip();
            break;
         case 2:
            ((Buffer)this.c).flip();
            break;
         case 3:
            ((Buffer)this.d).flip();
      }

      return new h(this.a, this.b, this.c, this.d, (byte)0);
   }

   private static int a(int var0) {
      var0 = 32 - Integer.numberOfLeadingZeros(var0 - 1);
      return (int)Math.pow(2.0, var0);
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void a(CharBuffer var1) {
      int var3 = var1.remaining();
      j var2 = this;
      switch (i.a[var2.a - 1]) {
         case 1:
            if (var2.b.remaining() < var3) {
               ByteBuffer var21 = ByteBuffer.allocate(a(var2.b.capacity() + var3));
               ((Buffer)var2.b).flip();
               var21.put(var2.b);
               var2.b = var21;
            }
            break;
         case 2:
            if (var2.c.remaining() < var3) {
               CharBuffer var20 = CharBuffer.allocate(a(var2.c.capacity() + var3));
               ((Buffer)var2.c).flip();
               var20.put(var2.c);
               var2.c = var20;
            }
            break;
         case 3:
            if (var2.d.remaining() < var3) {
               IntBuffer var4 = IntBuffer.allocate(a(var2.d.capacity() + var3));
               ((Buffer)var2.d).flip();
               var4.put(var2.d);
               var2.d = var4;
            }
      }

      if (!var1.hasArray()) {
         throw new UnsupportedOperationException("TODO");
      }

      CharBuffer var18 = var1;
      var2 = this;
      assert var18.hasArray();
      switch (i.a[var2.a - 1]) {
         case 1:
            CharBuffer var22 = var18;
            j var19 = var2;
            assert var19.e == -1;
            char[] var9 = var22.array();
            int var13 = var22.arrayOffset() + var22.position();
            int var5 = var22.arrayOffset() + var22.limit();
            byte[] var6 = var19.b.array();

            int var7;
            for (var7 = var19.b.arrayOffset() + var19.b.position(); var13 < var5; var7++) {
               char var8;
               if ((var8 = var9[var13]) > 255) {
                  ((Buffer)var22).position(var13 - var22.arrayOffset());
                  ((Buffer)var19.b).position(var7 - var19.b.arrayOffset());
                  if (!Character.isHighSurrogate(var8)) {
                     int var16 = var22.remaining();
                     j var11 = var19;
                     ((Buffer)var19.b).flip();
                     CharBuffer var17 = CharBuffer.allocate(Math.max(var11.b.remaining() + var16, var11.b.capacity() / 2));

                     while (var11.b.hasRemaining()) {
                        var17.put((char)(var11.b.get() & 0xFF));
                     }

                     var11.a = k.b;
                     var11.b = null;
                     var11.c = var17;
                     var19.b(var22);
                     return;
                  }

                  int var14 = var22.remaining();
                  j var10 = var19;
                  ((Buffer)var19.b).flip();
                  IntBuffer var15 = IntBuffer.allocate(Math.max(var10.b.remaining() + var14, var10.b.capacity() / 4));

                  while (var10.b.hasRemaining()) {
                     var15.put(var10.b.get() & 255);
                  }

                  var10.a = k.c;
                  var10.b = null;
                  var10.d = var15;
                  var19.c(var22);
                  return;
               }

               var6[var7] = (byte)var8;
               var13++;
            }

            ((Buffer)var22).position(var13 - var22.arrayOffset());
            ((Buffer)var19.b).position(var7 - var19.b.arrayOffset());
            return;
         case 2:
            var2.b(var18);
            return;
         case 3:
            var2.c(var18);
      }
   }

   private void b(CharBuffer var1) {
      assert this.e == -1;
      char[] var2 = var1.array();
      int var3 = var1.arrayOffset() + var1.position();
      int var4 = var1.arrayOffset() + var1.limit();
      char[] var5 = this.c.array();

      int var6;
      for (var6 = this.c.arrayOffset() + this.c.position(); var3 < var4; var6++) {
         char var7;
         if (Character.isHighSurrogate(var7 = var2[var3])) {
            ((Buffer)var1).position(var3 - var1.arrayOffset());
            ((Buffer)this.c).position(var6 - this.c.arrayOffset());
            this.b(var1.remaining());
            this.c(var1);
            return;
         }

         var5[var6] = var7;
         var3++;
      }

      ((Buffer)var1).position(var3 - var1.arrayOffset());
      ((Buffer)this.c).position(var6 - this.c.arrayOffset());
   }

   private void c(CharBuffer var1) {
      char[] var2 = var1.array();
      int var3 = var1.arrayOffset() + var1.position();
      int var4 = var1.arrayOffset() + var1.limit();
      int[] var5 = this.d.array();
      int var6 = this.d.arrayOffset() + this.d.position();

      while (var3 < var4) {
         char var7 = var2[var3];
         var3++;
         if (this.e != -1) {
            if (Character.isLowSurrogate(var7)) {
               var5[var6] = Character.toCodePoint((char)this.e, var7);
               var6++;
               this.e = -1;
            } else {
               var5[var6] = this.e;
               var6++;
               if (Character.isHighSurrogate(var7)) {
                  this.e = var7 & '\uffff';
               } else {
                  var5[var6] = var7 & '\uffff';
                  var6++;
                  this.e = -1;
               }
            }
         } else if (Character.isHighSurrogate(var7)) {
            this.e = var7 & '\uffff';
         } else {
            var5[var6] = var7 & '\uffff';
            var6++;
         }
      }

      if (this.e != -1) {
         var5[var6] = this.e & 65535;
         var6++;
      }

      ((Buffer)var1).position(var3 - var1.arrayOffset());
      ((Buffer)this.d).position(var6 - this.d.arrayOffset());
   }

   private void b(int var1) {
      ((Buffer)this.c).flip();
      IntBuffer var2 = IntBuffer.allocate(Math.max(this.c.remaining() + var1, this.c.capacity() / 2));

      while (this.c.hasRemaining()) {
         var2.put(this.c.get() & 65535);
      }

      this.a = k.c;
      this.c = null;
      this.d = var2;
   }
}
