package org.openzen.zenscript.codemodel.annotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.context.StatementContext;
import org.openzen.zenscript.codemodel.context.TypeContext;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.PanicExpression;
import org.openzen.zenscript.codemodel.member.FunctionalMember;
import org.openzen.zenscript.codemodel.member.GetterMember;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.SetterMember;
import org.openzen.zenscript.codemodel.scope.BaseScope;
import org.openzen.zenscript.codemodel.serialization.CodeSerializationOutput;
import org.openzen.zenscript.codemodel.statement.BlockStatement;
import org.openzen.zenscript.codemodel.statement.ExpressionStatement;
import org.openzen.zenscript.codemodel.statement.IfStatement;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;

public class PreconditionForMethod implements MemberAnnotation {
   private final CodePosition position;
   private final String enforcement;
   private final Expression condition;
   private final Expression message;

   public PreconditionForMethod(CodePosition position, String enforcement, Expression condition, Expression message) {
      this.position = position;
      this.enforcement = enforcement;
      this.condition = condition;
      this.message = message;
   }

   @Override
   public AnnotationDefinition getDefinition() {
      return PreconditionAnnotationDefinition.INSTANCE;
   }

   @Override
   public void apply(IDefinitionMember member, BaseScope scope) {
      if (member instanceof GetterMember) {
         this.applyOnOverridingGetter((GetterMember)member, scope);
      } else if (member instanceof SetterMember) {
         this.applyOnOverridingSetter((SetterMember)member, scope);
      } else if (member instanceof FunctionalMember) {
         this.applyOnOverridingMethod((FunctionalMember)member, scope);
      }
   }

   @Override
   public void applyOnOverridingMethod(FunctionalMember member, BaseScope scope) {
      member.body = this.applyOnOverride(member.body, scope);
   }

   @Override
   public void applyOnOverridingGetter(GetterMember member, BaseScope scope) {
      member.body = this.applyOnOverride(member.body, scope);
   }

   @Override
   public void applyOnOverridingSetter(SetterMember member, BaseScope scope) {
      member.body = this.applyOnOverride(member.body, scope);
   }

   private Statement applyOnOverride(Statement body, BaseScope scope) {
      if (body == null) {
         return body;
      }

      try {
         TypeMembers members = scope.getTypeMembers(this.condition.type);
         Expression inverseCondition = members.getGroup(OperatorType.NOT).call(this.position, scope, this.condition, CallArguments.EMPTY, false);
         Statement throwStatement = new ExpressionStatement(this.position, new PanicExpression(this.position, BasicTypeID.VOID, this.message));
         List<Statement> statements = new ArrayList<>();
         statements.add(new IfStatement(this.position, inverseCondition, throwStatement, null));
         if (body instanceof BlockStatement) {
            statements.addAll(Arrays.asList(((BlockStatement)body).statements));
         } else {
            statements.add(body);
         }

         return new BlockStatement(this.position, statements.toArray(new Statement[statements.size()]));
      } catch (CompileException ex) {
         ex.printStackTrace();
         return body;
      }
   }

   @Override
   public void serialize(CodeSerializationOutput output, IDefinitionMember member, TypeContext context) {
      output.serialize(this.position);
      output.writeString(this.enforcement);
      StatementContext statementContext = new StatementContext(this.position, context, member.getHeader());
      output.serialize(statementContext, this.condition);
      output.serialize(statementContext, this.message);
   }
}
