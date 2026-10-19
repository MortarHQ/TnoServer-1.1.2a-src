package org.openzen.zenscript.parser.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;

public class ParsedDollarExpression extends ParsedExpression {
   public ParsedDollarExpression(CodePosition position) {
      super(position);
   }

   @Override
   public IPartialExpression compile(ExpressionScope scope) throws CompileException {
      BaseScope.DollarEvaluator dollar = scope.getDollar();
      if (dollar == null) {
         throw new CompileException(this.position, CompileExceptionCode.NO_DOLLAR_HERE, "No dollar expression available in this context");
      } else {
         return dollar.apply(this.position);
      }
   }

   @Override
   public boolean hasStrongType() {
      return true;
   }
}
