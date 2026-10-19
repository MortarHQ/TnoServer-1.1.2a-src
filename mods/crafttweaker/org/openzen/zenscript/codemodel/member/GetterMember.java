package org.openzen.zenscript.codemodel.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.ConcatMap;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.member.ref.GetterMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.statement.LoopStatement;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPriority;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;

public class GetterMember extends PropertyMember {
   public final String name;
   public Statement body = null;
   private GetterMemberRef overrides;

   public GetterMember(CodePosition position, HighLevelDefinition definition, int modifiers, String name, TypeID type, BuiltinID builtin) {
      super(position, definition, modifiers, type, builtin);
      this.name = name;
   }

   public void setBody(Statement body) {
      this.body = body;
      if (this.getType() == BasicTypeID.UNDETERMINED) {
         TypeID returnType = body.getReturnType();
         if (returnType != null) {
            this.setType(returnType);
         }
      }
   }

   @Override
   public boolean isAbstract() {
      return this.body == null && this.builtin == null;
   }

   @Override
   public void registerTo(TypeMembers members, TypeMemberPriority priority, GenericMapper mapper) {
      members.addGetter(new GetterMemberRef(members.type, this, mapper), priority);
   }

   @Override
   public String describe() {
      return "getter " + this.name;
   }

   @Override
   public <T> T accept(MemberVisitor<T> visitor) {
      return visitor.visitGetter(this);
   }

   @Override
   public <C, R> R accept(C context, MemberVisitorWithContext<C, R> visitor) {
      return visitor.visitGetter(context, this);
   }

   public GetterMemberRef getOverrides() {
      return this.overrides;
   }

   public void setOverrides(GetterMemberRef override) {
      this.overrides = override;
      if (this.getType() == BasicTypeID.UNDETERMINED) {
         this.setType(override.getType());
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

   public GetterMemberRef ref(TypeID type, GenericMapper mapper) {
      return new GetterMemberRef(type, this, mapper);
   }

   @Override
   public FunctionHeader getHeader() {
      return new FunctionHeader(this.getType());
   }
}
