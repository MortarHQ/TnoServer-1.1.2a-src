package org.openzen.zenscript.codemodel.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.definition.EnumDefinition;
import org.openzen.zenscript.codemodel.expression.ConstructorSuperCallExpression;
import org.openzen.zenscript.codemodel.expression.ConstructorThisCallExpression;
import org.openzen.zenscript.codemodel.member.ref.DefinitionMemberRef;
import org.openzen.zenscript.codemodel.statement.BlockStatement;
import org.openzen.zenscript.codemodel.statement.ExpressionStatement;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPriority;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;

public class ConstructorMember extends FunctionalMember {
   public ConstructorMember(CodePosition position, HighLevelDefinition definition, int modifiers, FunctionHeader header, BuiltinID builtin) {
      super(position, definition, modifiers, new FunctionHeader(header.typeParameters, BasicTypeID.VOID, header.thrownType, header.parameters), builtin);
   }

   public boolean isConstructorForwarded() {
      ExpressionStatement firstExpression = null;
      if (this.body instanceof ExpressionStatement) {
         firstExpression = (ExpressionStatement)this.body;
      } else if (this.body instanceof BlockStatement blockBody && blockBody.statements.length > 0 && blockBody.statements[0] instanceof ExpressionStatement) {
         firstExpression = (ExpressionStatement)blockBody.statements[0];
      }

      return firstExpression != null
         && (firstExpression.expression instanceof ConstructorSuperCallExpression || firstExpression.expression instanceof ConstructorThisCallExpression);
   }

   @Override
   public String getCanonicalName() {
      return this.definition.getFullName() + ":this" + this.header.getCanonical();
   }

   @Override
   public FunctionalKind getKind() {
      return FunctionalKind.CONSTRUCTOR;
   }

   @Override
   public void registerTo(TypeMembers type, TypeMemberPriority priority, GenericMapper mapper) {
      if (priority == TypeMemberPriority.SPECIFIED) {
         type.addConstructor(this.ref(type.type, mapper), priority);
      }
   }

   @Override
   public String describe() {
      return "constructor " + this.header.toString();
   }

   @Override
   public <T> T accept(MemberVisitor<T> visitor) {
      return visitor.visitConstructor(this);
   }

   @Override
   public <C, R> R accept(C context, MemberVisitorWithContext<C, R> visitor) {
      return visitor.visitConstructor(context, this);
   }

   @Override
   public DefinitionMemberRef getOverrides() {
      return null;
   }

   @Override
   public int getEffectiveModifiers() {
      int result = this.modifiers;
      if (this.definition instanceof EnumDefinition) {
         result |= 4;
      } else if (!Modifiers.hasAccess(result)) {
         result |= 2;
      }

      return result;
   }
}
