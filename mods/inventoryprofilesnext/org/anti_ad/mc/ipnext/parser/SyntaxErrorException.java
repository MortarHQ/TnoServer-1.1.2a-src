package org.anti_ad.mc.ipnext.parser;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SyntaxErrorException extends RuntimeException {
   private final int line;
   private final int pos;
   @NotNull
   private final String msg;

   public SyntaxErrorException(int var1, int var2, @NotNull String var3) {
      Intrinsics.checkNotNullParameter(var3, "");
      super();
      this.line = var1;
      this.pos = var2;
      this.msg = var3;
   }

   public final int getLine() {
      return this.line;
   }

   public final int getPos() {
      return this.pos;
   }

   @NotNull
   public final String getMsg() {
      return this.msg;
   }

   public final int component1() {
      return this.line;
   }

   public final int component2() {
      return this.pos;
   }

   @NotNull
   public final String component3() {
      return this.msg;
   }

   @NotNull
   public final SyntaxErrorException copy(int var1, int var2, @NotNull String var3) {
      Intrinsics.checkNotNullParameter(var3, "");
      return new SyntaxErrorException(var1, var2, var3);
   }

   @NotNull
   @Override
   public final String toString() {
      return "SyntaxErrorException(line=" + this.line + ", pos=" + this.pos + ", msg=" + this.msg + ")";
   }

   @Override
   public final int hashCode() {
      return (Integer.hashCode(this.line) * 31 + Integer.hashCode(this.pos)) * 31 + this.msg.hashCode();
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof SyntaxErrorException var2)) {
         return false;
      } else if (this.line != var2.line) {
         return false;
      } else {
         return this.pos != var2.pos ? false : Intrinsics.areEqual(this.msg, var2.msg);
      }
   }
}
