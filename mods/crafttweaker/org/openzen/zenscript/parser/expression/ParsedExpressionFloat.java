package org.openzen.zenscript.parser.expression;

import java.util.Collections;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.ConstantDoubleExpression;
import org.openzen.zenscript.codemodel.expression.ConstantFloatExpression;
import org.openzen.zenscript.codemodel.expression.InvalidExpression;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;

public class ParsedExpressionFloat extends ParsedExpression {
   public final double value;
   public final String suffix;

   public ParsedExpressionFloat(CodePosition position, String value) {
      super(position);
      int split = value.length();

      while (isLetter(value.charAt(split - 1))) {
         split--;
      }

      this.value = Double.parseDouble(value.substring(0, split));
      this.suffix = value.substring(split);
   }

   private ParsedExpressionFloat(CodePosition position, double value) {
      super(position);
      this.value = value;
      this.suffix = "";
   }

   private static boolean isLetter(char c) {
      return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_';
   }

   @Override
   public IPartialExpression compile(ExpressionScope scope) throws CompileException {
      if (!this.suffix.equals("f") && !this.suffix.equals("F")) {
         if (!this.suffix.equals("d") && !this.suffix.equals("D")) {
            for (TypeID hint : scope.hints) {
               if (this.suffix.isEmpty()) {
                  if (hint == BasicTypeID.DOUBLE) {
                     return new ConstantDoubleExpression(this.position, this.value);
                  }

                  if (hint == BasicTypeID.FLOAT) {
                     return new ConstantFloatExpression(this.position, (float)this.value);
                  }
               } else {
                  TypeMembers members = scope.getTypeMembers(hint);
                  FunctionalMemberRef method = members.getOrCreateGroup(this.suffix, true).getStaticMethod(1, hint);
                  if (method != null) {
                     try {
                        ParsedCallArguments parsedArguments = new ParsedCallArguments(
                           Collections.emptyList(), Collections.singletonList(new ParsedExpressionFloat(this.position, this.value))
                        );
                        CallArguments arguments = parsedArguments.compileCall(this.position, scope, TypeID.NONE, method.getHeader());
                        return method.callStatic(this.position, hint, method.getHeader(), arguments, scope);
                     } catch (CompileException ex) {
                        return new InvalidExpression(hint, ex);
                     }
                  }
               }
            }

            if (this.suffix.isEmpty()) {
               return new ConstantDoubleExpression(this.position, this.value);
            } else {
               throw new CompileException(this.position, CompileExceptionCode.INVALID_SUFFIX, "Invalid suffix: " + this.suffix);
            }
         } else {
            return new ConstantDoubleExpression(this.position, this.value);
         }
      } else {
         return new ConstantFloatExpression(this.position, (float)this.value);
      }
   }

   @Override
   public boolean hasStrongType() {
      return false;
   }
}
