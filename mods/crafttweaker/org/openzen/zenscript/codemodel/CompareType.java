package org.openzen.zenscript.codemodel;

public enum CompareType {
   LT("<"),
   GT(">"),
   EQ("=="),
   NE("!="),
   LE("<="),
   GE(">=");

   public final String str;

   CompareType(String str) {
      this.str = str;
   }
}
