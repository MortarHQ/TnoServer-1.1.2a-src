package org.openzen.zenscript.parser.expression;

import java.util.Collections;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.ConstantByteExpression;
import org.openzen.zenscript.codemodel.expression.ConstantCharExpression;
import org.openzen.zenscript.codemodel.expression.ConstantDoubleExpression;
import org.openzen.zenscript.codemodel.expression.ConstantFloatExpression;
import org.openzen.zenscript.codemodel.expression.ConstantIntExpression;
import org.openzen.zenscript.codemodel.expression.ConstantLongExpression;
import org.openzen.zenscript.codemodel.expression.ConstantSByteExpression;
import org.openzen.zenscript.codemodel.expression.ConstantShortExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUIntExpression;
import org.openzen.zenscript.codemodel.expression.ConstantULongExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUShortExpression;
import org.openzen.zenscript.codemodel.expression.ConstantUSizeExpression;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.InvalidExpression;
import org.openzen.zenscript.codemodel.expression.switchvalue.IntSwitchValue;
import org.openzen.zenscript.codemodel.expression.switchvalue.SwitchValue;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;

public class ParsedExpressionInt extends ParsedExpression {
   public final boolean negative;
   public final long value;
   public final String suffix;

   public ParsedExpressionInt(CodePosition position, String value) {
      super(position);
      int split = value.length();

      while (isLetter(value.charAt(split - 1))) {
         split--;
      }

      this.negative = value.charAt(0) == '-';
      this.value = Long.parseLong(value.substring(0, split));
      this.suffix = value.substring(split);
   }

   private ParsedExpressionInt(CodePosition position, boolean negative, long value, String suffix) {
      super(position);
      this.negative = negative;
      this.value = value;
      this.suffix = suffix;
   }

   public static ParsedExpressionInt parsePrefixed(CodePosition position, String value) {
      boolean negative = value.startsWith("-");
      if (negative) {
         value = value.substring(1);
      }

      String suffix = "";
      if (value.endsWith("u") || value.endsWith("l") || value.endsWith("U") || value.endsWith("L")) {
         suffix = value.substring(value.length() - 1);
         value = value.substring(0, value.length() - 1);
      } else if (value.endsWith("ul") || value.endsWith("UL")) {
         suffix = value.substring(value.length() - 2);
         value = value.substring(0, value.length() - 2);
      }

      value = value.toLowerCase();
      long parsed = 0L;
      if (value.startsWith("0x")) {
         for (char c : value.substring(2).toCharArray()) {
            if (c >= '0' && c <= '9') {
               parsed = parsed * 16L + (c - '0');
            } else if (c >= 'a' && c <= 'f') {
               parsed = parsed * 16L + 10L + (c - 'a');
            } else if (c != '_') {
               throw new NumberFormatException("Invalid number: " + value);
            }
         }
      } else if (value.startsWith("0b")) {
         for (char c : value.substring(2).toCharArray()) {
            if (c == '0') {
               parsed *= 2L;
            } else if (c == '1') {
               parsed = parsed * 2L + 1L;
            } else if (c != '_') {
               throw new NumberFormatException("Invalid number: " + value);
            }
         }
      } else {
         if (!value.startsWith("0o")) {
            throw new NumberFormatException("Invalid number: " + value);
         }

         for (char c : value.substring(2).toCharArray()) {
            if (c >= '0' && c <= '7') {
               parsed = parsed * 8L + c - 48L;
            } else if (c != '_') {
               throw new NumberFormatException("Invalid number: " + value);
            }
         }
      }

      return new ParsedExpressionInt(position, negative, negative ? -parsed : parsed, suffix);
   }

   private static boolean isLetter(char c) {
      return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_';
   }

   public Expression compile(ExpressionScope scope) throws CompileException {
      if (this.suffix.equals("L") || this.suffix.equals("l")) {
         return new ConstantLongExpression(this.position, this.value);
      }

      if (this.suffix.equals("UL") || this.suffix.equals("ul")) {
         return new ConstantULongExpression(this.position, this.value);
      }

      if (this.suffix.equals("U") || this.suffix.equals("u")) {
         return new ConstantUIntExpression(this.position, (int)this.value);
      }

      if (!this.suffix.equals("D") && !this.suffix.equals("d")) {
         if (!this.suffix.equals("F") && !this.suffix.equals("f")) {
            for (TypeID hint : scope.hints) {
               if (this.suffix.isEmpty() && hint instanceof BasicTypeID) {
                  switch ((BasicTypeID)hint) {
                     case SBYTE:
                        return new ConstantSByteExpression(this.position, (byte)this.value);
                     case BYTE:
                        if (!this.negative) {
                           return new ConstantByteExpression(this.position, (int)(this.value & 255L));
                        }
                        break;
                     case SHORT:
                        return new ConstantShortExpression(this.position, (short)this.value);
                     case USHORT:
                        if (!this.negative) {
                           return new ConstantUShortExpression(this.position, (int)(this.value & 65535L));
                        }
                        break;
                     case INT:
                        return new ConstantIntExpression(this.position, (int)this.value);
                     case UINT:
                        if (!this.negative) {
                           return new ConstantUIntExpression(this.position, (int)this.value);
                        }
                        break;
                     case LONG:
                        return new ConstantLongExpression(this.position, this.value);
                     case ULONG:
                        if (!this.negative) {
                           return new ConstantULongExpression(this.position, this.value);
                        }
                        break;
                     case USIZE:
                        if (!this.negative) {
                           return new ConstantUSizeExpression(this.position, this.value);
                        }
                        break;
                     case CHAR:
                        if (!this.negative) {
                           return new ConstantCharExpression(this.position, (char)this.value);
                        }
                  }
               } else if (!this.suffix.isEmpty()) {
                  TypeMembers members = scope.getTypeMembers(hint);
                  FunctionalMemberRef method = members.getOrCreateGroup(this.suffix, true).getStaticMethod(1, hint);
                  if (method != null) {
                     try {
                        ParsedCallArguments parsedArguments = new ParsedCallArguments(
                           Collections.emptyList(), Collections.singletonList(new ParsedExpressionInt(this.position, this.negative, this.value, ""))
                        );
                        CallArguments arguments = parsedArguments.compileCall(this.position, scope, TypeID.NONE, method.getHeader());
                        return method.callStatic(this.position, hint, method.getHeader(), arguments, scope);
                     } catch (CompileException ex) {
                        return new InvalidExpression(hint, ex);
                     }
                  }
               }
            }

            if (!this.suffix.isEmpty()) {
               throw new CompileException(this.position, CompileExceptionCode.INVALID_SUFFIX, "Invalid suffix: " + this.suffix);
            } else {
               return this.value <= 2147483647L && this.value >= -2147483648L
                  ? new ConstantIntExpression(this.position, (int)this.value)
                  : new ConstantLongExpression(this.position, this.value);
            }
         } else {
            return new ConstantFloatExpression(this.position, (float)this.value);
         }
      } else {
         return new ConstantDoubleExpression(this.position, this.value);
      }
   }

   @Override
   public SwitchValue compileToSwitchValue(TypeID type, ExpressionScope scope) throws CompileException {
      if (this.value >= -2147483648L && this.value <= 2147483647L) {
         return new IntSwitchValue((int)this.value);
      } else {
         throw new CompileException(this.position, CompileExceptionCode.INVALID_SWITCH_CASE, "value is too large for a switch case");
      }
   }

   @Override
   public boolean hasStrongType() {
      return false;
   }
}
