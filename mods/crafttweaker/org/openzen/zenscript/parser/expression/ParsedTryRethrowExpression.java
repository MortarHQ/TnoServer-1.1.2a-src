package org.openzen.zenscript.parser.expression;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.TryRethrowAsExceptionExpression;
import org.openzen.zenscript.codemodel.expression.TryRethrowAsResultExpression;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;

public class ParsedTryRethrowExpression extends ParsedExpression {
   private final ParsedExpression source;

   public ParsedTryRethrowExpression(CodePosition position, ParsedExpression source) {
      super(position);
      this.source = source;
   }

   @Override
   public IPartialExpression compile(ExpressionScope scope) throws CompileException {
      HighLevelDefinition result = scope.getTypeRegistry().stdlib.getDefinition("Result");
      Expression cSource = this.source.compile(scope).eval();
      if (cSource.thrownType != null) {
         if (scope.getFunctionHeader() != null && scope.getFunctionHeader().thrownType == null) {
            TypeID resultType = scope.getTypeRegistry().getForDefinition(result, cSource.type, cSource.thrownType);
            return new TryRethrowAsResultExpression(this.position, resultType, cSource);
         } else {
            return new TryRethrowAsExceptionExpression(this.position, cSource.type, cSource, cSource.thrownType);
         }
      } else if (cSource.type instanceof DefinitionTypeID sourceType && sourceType.definition == result) {
         return new TryRethrowAsResultExpression(this.position, sourceType.typeArguments[0], cSource);
      } else if (scope.getFunctionHeader() == null) {
         throw new CompileException(this.position, CompileExceptionCode.TRY_RETHROW_NOT_A_RESULT, "type is not a Result type, cannot convert");
      } else {
         throw new CompileException(
            this.position, CompileExceptionCode.TRY_RETHROW_NOT_A_RESULT, "this expression doesn't throw an exception nor returns a result"
         );
      }
   }

   @Override
   public boolean hasStrongType() {
      return true;
   }
}
