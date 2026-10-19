package org.openzen.zenscript.codemodel.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPriority;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;

public class OperatorMember extends FunctionalMember {
   public final OperatorType operator;
   private FunctionalMemberRef overrides;

   public OperatorMember(CodePosition position, HighLevelDefinition definition, int modifiers, OperatorType operator, FunctionHeader header, BuiltinID builtin) {
      super(position, definition, modifiers, header, builtin);
      this.operator = operator;
   }

   @Override
   public String getCanonicalName() {
      return this.definition.getFullName() + ":" + this.operator.operator + this.header.getCanonical();
   }

   @Override
   public FunctionalKind getKind() {
      return FunctionalKind.OPERATOR;
   }

   @Override
   public void registerTo(TypeMembers type, TypeMemberPriority priority, GenericMapper mapper) {
      type.addOperator(this.operator, this.ref(type.type, mapper), priority);
   }

   @Override
   public String describe() {
      return this.operator.operator + this.header.toString();
   }

   @Override
   public <T> T accept(MemberVisitor<T> visitor) {
      return visitor.visitOperator(this);
   }

   @Override
   public <C, R> R accept(C context, MemberVisitorWithContext<C, R> visitor) {
      return visitor.visitOperator(context, this);
   }

   @Override
   public int getEffectiveModifiers() {
      int result = super.getEffectiveModifiers();
      if (this.overrides != null) {
         if (this.overrides.getTarget().isPublic()) {
            result |= 1;
         }

         if (this.overrides.getTarget().isProtected()) {
            result |= 256;
         }
      }

      return result;
   }

   public FunctionalMemberRef getOverrides() {
      return this.overrides;
   }

   public void setOverrides(GlobalTypeRegistry registry, FunctionalMemberRef overrides) {
      this.overrides = overrides;
      this.header = this.header.inferFromOverride(registry, overrides.getHeader());
   }
}
