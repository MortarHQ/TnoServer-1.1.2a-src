package org.openzen.zenscript.parser.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.expression.ConstantCharExpression;
import org.openzen.zenscript.codemodel.expression.ConstantStringExpression;
import org.openzen.zenscript.codemodel.expression.switchvalue.CharSwitchValue;
import org.openzen.zenscript.codemodel.expression.switchvalue.StringSwitchValue;
import org.openzen.zenscript.codemodel.expression.switchvalue.SwitchValue;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;

public class ParsedExpressionString extends ParsedExpression {
   public final String value;
   public final boolean singleQuote;

   public ParsedExpressionString(CodePosition position, String value, boolean singleQuote) {
      super(position);
      this.value = value;
      this.singleQuote = singleQuote;
   }

   @Override
   public IPartialExpression compile(ExpressionScope scope) {
      return this.value.length() != 1 || !this.singleQuote && !scope.hints.contains(BasicTypeID.CHAR)
         ? new ConstantStringExpression(this.position, this.value)
         : new ConstantCharExpression(this.position, this.value.charAt(0));
   }

   @Override
   public SwitchValue compileToSwitchValue(TypeID type, ExpressionScope scope) throws CompileException {
      if (type == BasicTypeID.CHAR) {
         if (this.value.length() != 1) {
            throw new CompileException(this.position, CompileExceptionCode.INVALID_SWITCH_CASE, "char value expected but string given");
         } else {
            return new CharSwitchValue(this.value.charAt(0));
         }
      } else if (type == BasicTypeID.STRING) {
         return new StringSwitchValue(this.value);
      } else {
         throw new CompileException(this.position, CompileExceptionCode.INVALID_SWITCH_CASE, "Can only use string keys for string values");
      }
   }

   @Override
   public boolean hasStrongType() {
      return false;
   }
}
