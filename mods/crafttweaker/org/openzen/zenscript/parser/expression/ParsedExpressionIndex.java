package org.openzen.zenscript.parser.expression;

import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.TypeMemberGroup;

public class ParsedExpressionIndex extends ParsedExpression {
   private final ParsedExpression value;
   private final List<ParsedExpression> indexes;

   public ParsedExpressionIndex(CodePosition position, ParsedExpression value, List<ParsedExpression> indexes) {
      super(position);
      this.value = value;
      this.indexes = indexes;
   }

   @Override
   public IPartialExpression compile(ExpressionScope scope) throws CompileException {
      return new ParsedExpressionIndex.PartialIndexedExpression(scope);
   }

   @Override
   public boolean hasStrongType() {
      return true;
   }

   private class PartialIndexedExpression implements IPartialExpression {
      private final ExpressionScope scope;
      private final Expression target;

      private PartialIndexedExpression(ExpressionScope scope) throws CompileException {
         this.scope = scope;
         this.target = ParsedExpressionIndex.this.value.compile(scope.withoutHints()).eval();
      }

      @Override
      public Expression eval() throws CompileException {
         TypeMemberGroup members = this.scope.getTypeMembers(this.target.type).getOrCreateGroup(OperatorType.INDEXGET);
         List<TypeID>[] predictedTypes = members.predictCallTypes(
            ParsedExpressionIndex.this.position, this.scope, this.scope.hints, ParsedExpressionIndex.this.indexes.size()
         );
         Expression[] arguments = new Expression[ParsedExpressionIndex.this.indexes.size()];

         for (int i = 0; i < arguments.length; i++) {
            arguments[i] = ParsedExpressionIndex.this.indexes.get(i).compile(this.scope.createInner(predictedTypes[i], this::getLength)).eval();
         }

         return members.call(ParsedExpressionIndex.this.position, this.scope, this.target, new CallArguments(arguments), false);
      }

      @Override
      public List<TypeID>[] predictCallTypes(CodePosition position, TypeScope scope, List<TypeID> hints, int arguments) throws CompileException {
         return this.eval().predictCallTypes(position, scope, hints, arguments);
      }

      @Override
      public List<FunctionHeader> getPossibleFunctionHeaders(TypeScope scope, List<TypeID> hints, int arguments) throws CompileException {
         return this.eval().getPossibleFunctionHeaders(scope, hints, arguments);
      }

      @Override
      public IPartialExpression getMember(CodePosition position, TypeScope scope, List<TypeID> hints, GenericName name) throws CompileException {
         return this.eval().getMember(position, scope, hints, name);
      }

      @Override
      public Expression call(CodePosition position, TypeScope scope, List<TypeID> hints, CallArguments arguments) throws CompileException {
         return this.eval().call(position, scope, hints, arguments);
      }

      @Override
      public Expression assign(CodePosition position, TypeScope scope, Expression value) throws CompileException {
         TypeMemberGroup members = scope.getTypeMembers(this.target.type).getOrCreateGroup(OperatorType.INDEXSET);
         List<TypeID>[] predictedTypes = members.predictCallTypes(position, scope, this.scope.hints, ParsedExpressionIndex.this.indexes.size() + 1);
         Expression[] arguments = new Expression[ParsedExpressionIndex.this.indexes.size() + 1];

         for (int i = 0; i < arguments.length - 1; i++) {
            arguments[i] = ParsedExpressionIndex.this.indexes.get(i).compile(this.scope.createInner(predictedTypes[i], this::getLength)).eval();
         }

         arguments[ParsedExpressionIndex.this.indexes.size()] = value;
         return members.call(position, scope, this.target, new CallArguments(arguments), false);
      }

      @Override
      public List<TypeID> getAssignHints() {
         TypeMemberGroup members = this.scope.getTypeMembers(this.target.type).getOrCreateGroup(OperatorType.INDEXSET);
         List<TypeID>[] predictedTypes = members.predictCallTypes(
            ParsedExpressionIndex.this.position, this.scope, this.scope.hints, ParsedExpressionIndex.this.indexes.size() + 1
         );
         return predictedTypes[ParsedExpressionIndex.this.indexes.size()];
      }

      private Expression getLength(CodePosition position) throws CompileException {
         return this.target.getMember(position, this.scope, this.scope.hints, new GenericName("length")).eval();
      }

      @Override
      public TypeID[] getTypeArguments() {
         return null;
      }
   }
}
