package org.openzen.zenscript.parser.statements;

import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.WhitespaceInfo;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.definition.ZSPackage;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.scope.StatementScope;
import org.openzen.zenscript.codemodel.statement.InvalidStatement;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.statement.SwitchStatement;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.LocalMemberCache;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPreparer;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.expression.ParsedExpression;

public class ParsedStatementSwitch extends ParsedStatement {
   private final String name;
   private final ParsedExpression value;
   private final List<ParsedSwitchCase> cases;

   public ParsedStatementSwitch(
      CodePosition position, ParsedAnnotation[] annotations, WhitespaceInfo whitespace, String name, ParsedExpression value, List<ParsedSwitchCase> cases
   ) {
      super(position, annotations, whitespace);
      this.name = name;
      this.value = value;
      this.cases = cases;
   }

   @Override
   public Statement compile(StatementScope scope) {
      try {
         SwitchStatement result = new SwitchStatement(this.position, this.name, this.value.compile(new ExpressionScope(scope)).eval());
         ParsedStatementSwitch.SwitchScope innerScope = new ParsedStatementSwitch.SwitchScope(scope, result);

         for (ParsedSwitchCase switchCase : this.cases) {
            try {
               result.cases.add(switchCase.compile(result.value.type, innerScope));
            } catch (CompileException ex) {
               return this.result(new InvalidStatement(ex), scope);
            }
         }

         return result;
      } catch (CompileException ex) {
         return this.result(new InvalidStatement(ex), scope);
      }
   }

   private static class SwitchScope extends StatementScope {
      private final StatementScope outer;
      private final SwitchStatement target;

      public SwitchScope(StatementScope outer, SwitchStatement target) {
         this.outer = outer;
         this.target = target;
      }

      @Override
      public ZSPackage getRootPackage() {
         return this.outer.getRootPackage();
      }

      @Override
      public IPartialExpression get(CodePosition position, GenericName name) throws CompileException {
         IPartialExpression result = super.get(position, name);
         return result == null ? this.outer.get(position, name) : result;
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
         return name != null && !name.equals(this.target.label) ? this.outer.getLoop(name) : this.target;
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
