package org.openzen.zenscript.codemodel.member;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.member.ref.CasterMemberRef;
import org.openzen.zenscript.codemodel.scope.TypeScope;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.codemodel.type.member.BuiltinID;
import org.openzen.zenscript.codemodel.type.member.TypeMemberPriority;
import org.openzen.zenscript.codemodel.type.member.TypeMembers;

public class CasterMember extends FunctionalMember {
   public TypeID toType;
   public CasterMemberRef overrides;

   public CasterMember(CodePosition position, HighLevelDefinition definition, int modifiers, TypeID toType, BuiltinID builtin) {
      super(position, definition, modifiers, new FunctionHeader(toType), builtin);
      this.toType = toType;
   }

   @Override
   public String getCanonicalName() {
      return this.definition.getFullName() + ":caster:" + this.toType.toString();
   }

   @Override
   public FunctionalKind getKind() {
      return FunctionalKind.CASTER;
   }

   @Override
   public void registerTo(TypeMembers type, TypeMemberPriority priority, GenericMapper mapper) {
      type.addCaster(new CasterMemberRef(this, type.type, mapper == null ? this.toType : this.toType.instance(mapper)), priority);
   }

   @Override
   public String describe() {
      return "caster to " + this.toType.toString();
   }

   public TypeID getTargetType() {
      return this.toType;
   }

   public boolean isImplicit() {
      return Modifiers.isImplicit(this.modifiers);
   }

   @Override
   public <T> T accept(MemberVisitor<T> visitor) {
      return visitor.visitCaster(this);
   }

   @Override
   public <C, R> R accept(C context, MemberVisitorWithContext<C, R> visitor) {
      return visitor.visitCaster(context, this);
   }

   @Override
   public int getEffectiveModifiers() {
      int result = super.getEffectiveModifiers();
      if (this.overrides != null && this.overrides.getTarget().getDefinition().isInterface()) {
         result |= 1;
      }

      return result;
   }

   public void setOverrides(GlobalTypeRegistry registry, CasterMemberRef overrides) {
      this.overrides = overrides;
   }

   public CasterMemberRef getOverrides() {
      return this.overrides;
   }

   @Override
   public void normalize(TypeScope scope) {
      super.normalize(scope);
      this.toType = this.toType.getNormalized();
   }
}
