package org.anti_ad.a.b.a.a.a;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

public class h {
   private final int a;
   private final ByteBuffer b;
   private final CharBuffer c;
   private final IntBuffer d;

   private h(int var1, ByteBuffer var2, CharBuffer var3, IntBuffer var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final int a() {
      switch (i.a[this.a - 1]) {
         case 1:
            return this.b.position();
         case 2:
            return this.c.position();
         case 3:
            return this.d.position();
         default:
            throw new UnsupportedOperationException("Not reached");
      }
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final int b() {
      switch (i.a[this.a - 1]) {
         case 1:
            return this.b.remaining();
         case 2:
            return this.c.remaining();
         case 3:
            return this.d.remaining();
         default:
            throw new UnsupportedOperationException("Not reached");
      }
   }

   final int c() {
      return this.a;
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   final int d() {
      switch (i.a[this.a - 1]) {
         case 1:
            return this.b.arrayOffset();
         case 2:
            return this.c.arrayOffset();
         case 3:
            return this.d.arrayOffset();
         default:
            throw new UnsupportedOperationException("Not reached");
      }
   }

   final byte[] e() {
      assert this.a == k.a;
      return this.b.array();
   }

   final char[] f() {
      assert this.a == k.b;
      return this.c.array();
   }

   final int[] g() {
      assert this.a == k.c;
      return this.d.array();
   }

   public static j a(int var0) {
      return new j(var0, (byte)0);
   }
}
