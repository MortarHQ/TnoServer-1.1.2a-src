package org.openzen.zenscript.codemodel.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.ConcatMap;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.member.ref.DefinitionMemberRef;
import org.openzen.zenscript.codemodel.member.ref.SetterMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPriority;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;

public class SetterMember extends PropertyMember {
   public final String name;
   public Statement body;
   public FunctionParameter parameter;
   private SetterMemberRef overrides;

   public SetterMember(CodePosition position, HighLevelDefinition definition, int modifiers, String name, TypeID type, BuiltinID builtin) {
      super(position, definition, modifiers, type, builtin);
      this.name = name;
      this.parameter = new FunctionParameter(type, "value");
   }

   public void setBody(Statement body) {
      this.body = body;
   }

   @Override
   public void registerTo(TypeMembers members, TypeMemberPriority priority, GenericMapper mapper) {
      members.addSetter(new SetterMemberRef(members.type, this, mapper), priority);
   }

   @Override
   public String describe() {
      return "setter " + this.name;
   }

   @Override
   public <T> T accept(MemberVisitor<T> visitor) {
      return visitor.visitSetter(this);
   }

   @Override
   public <C, R> R accept(C context, MemberVisitorWithContext<C, R> visitor) {
      return visitor.visitSetter(context, this);
   }

   public SetterMemberRef getOverrides() {
      return this.overrides;
   }

   public void setOverrides(SetterMemberRef overrides) {
      this.overrides = overrides;
      if (this.getType() == BasicTypeID.UNDETERMINED) {
         this.setType(overrides.getType());
         this.parameter = new FunctionParameter(overrides.getType(), "value");
      }
   }

   @Override
   public int getEffectiveModifiers() {
      int result = this.modifiers;
      if (this.definition.isInterface() || this.overrides != null && this.overrides.getTarget().getDefinition().isInterface()) {
         result |= 1;
      }

      if (!Modifiers.hasAccess(result)) {
         result |= 2;
      }

      return result;
   }

   @Override
   public void normalize(TypeScope scope) {
      if (this.body != null) {
         this.body = this.body.normalize(scope, ConcatMap.empty(LoopStatement.class, LoopStatement.class));
      }
   }

   @Override
   public boolean isAbstract() {
      return this.body == null && this.builtin == null;
   }

   @Override
   public DefinitionMemberRef ref(TypeID type, GenericMapper mapper) {
      return new SetterMemberRef(type, this, mapper);
   }

   @Override
   public FunctionHeader getHeader() {
      return new FunctionHeader(BasicTypeID.VOID, this.getType());
   }
}
