package org.openzen.zenscript.parser.expression;

import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.partial.PartialDynamicMemberExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;
import org.openzen.zenscript.parser.type.IParsedType;

public class ParsedExpressionMember extends ParsedExpression {
   private final ParsedExpression value;
   private final String member;
   private final List<IParsedType> genericParameters;

   public ParsedExpressionMember(CodePosition position, ParsedExpression value, String member, List<IParsedType> genericParameters) {
      super(position);
      this.value = value;
      this.member = member;
      this.genericParameters = genericParameters;
   }

   @Override
   public IPartialExpression compile(ExpressionScope scope) throws CompileException {
      IPartialExpression cValue = this.value.compile(scope.withoutHints());
      TypeID[] typeArguments = IParsedType.compileTypes(this.genericParameters, scope);
      IPartialExpression member = cValue.getMember(this.position, scope, scope.hints, new GenericName(this.member, typeArguments));
      if (member == null) {
         Expression cValueExpression = cValue.eval();
         TypeMembers members = scope.getTypeMembers(cValueExpression.type);
         if (!members.hasOperator(OperatorType.MEMBERGETTER) && !members.hasOperator(OperatorType.MEMBERSETTER)) {
            throw new CompileException(this.position, CompileExceptionCode.NO_SUCH_MEMBER, "Member not found: " + this.member);
         } else {
            return new PartialDynamicMemberExpression(this.position, cValueExpression, members, this.member, scope);
         }
      } else {
         return member;
      }
   }

   @Override
   public boolean hasStrongType() {
      return true;
   }
}
