package org.openzen.zenscript.parser.statements;

import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.expression.GetLocalVariableExpression;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.scope.StatementScope;
import org.openzen.zenscript.codemodel.statement.CatchClause;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.statement.VarStatement;
import org.openzen.zenscript.codemodel.statement.VariableID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.LocalMemberCache;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPreparer;
import org.openzen.zenscript.parser.type.IParsedType;

public class ParsedCatchClause {
   public final CodePosition position;
   public final String exceptionName;
   public final IParsedType exceptionType;
   public final ParsedStatement content;

   public ParsedCatchClause(CodePosition position, String exceptionName, IParsedType exceptionType, ParsedStatement content) {
      this.position = position;
      this.exceptionName = exceptionName;
      this.exceptionType = exceptionType;
      this.content = content;
   }

   public CatchClause compile(StatementScope scope) {
      VarStatement exceptionVariable = new VarStatement(this.position, new VariableID(), this.exceptionName, this.exceptionType.compile(scope), null, true);
      ParsedCatchClause.CatchScope localScope = new ParsedCatchClause.CatchScope(scope, exceptionVariable);
      return new CatchClause(this.position, exceptionVariable, this.content.compile(localScope));
   }

   private static class CatchScope extends StatementScope {
      private final StatementScope outer;
      private final VarStatement exceptionVariable;

      public CatchScope(StatementScope outer, VarStatement exceptionVariable) {
         this.outer = outer;
         this.exceptionVariable = exceptionVariable;
      }

      @Override
      public ZSPackage getRootPackage() {
         return this.outer.getRootPackage();
      }

      @Override
      public IPartialExpression get(CodePosition position, GenericName name) throws CompileException {
         return name.hasNoArguments() && this.exceptionVariable.name.equals(name.name)
            ? new GetLocalVariableExpression(position, this.exceptionVariable)
            : this.outer.get(position, name);
      }

      @Override
      public LocalMemberCache getMemberCache() {
         return this.outer.getMemberCache();
      }

      @Override
      public TypeID getType(CodePosition position, List<GenericName> name) {
         return this.outer.getType(position, name);
      }

      @Override
      public LoopStatement getLoop(String name) {
         return this.outer.getLoop(name);
      }

      @Override
      public FunctionHeader getFunctionHeader() {
         return this.outer.getFunctionHeader();
      }

      @Override
      public TypeID getThisType() {
         return this.outer.getThisType();
      }

      @Override
      public BaseScope.DollarEvaluator getDollar() {
         return this.outer.getDollar();
      }

      @Override
      public IPartialExpression getOuterInstance(CodePosition position) throws CompileException {
         return this.outer.getOuterInstance(position);
      }

      @Override
      public AnnotationDefinition getAnnotation(String name) {
         return this.outer.getAnnotation(name);
      }

      @Override
      public TypeMemberPreparer getPreparer() {
         return this.outer.getPreparer();
      }

      @Override
      public GenericMapper getLocalTypeParameters() {
         return this.outer.getLocalTypeParameters();
      }
   }
}
