package org.openzen.zenscript.codemodel.member.ref;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.Tag;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.annotations.MemberAnnotation;
import org.openzen.zenscript.codemodel.expression.CastExpression;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.member.CasterMember;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.type.TypeID;

public class CasterMemberRef implements DefinitionMemberRef {
   public final CasterMember member;
   public final TypeID type;
   public final TypeID toType;

   public CasterMemberRef(CasterMember member, TypeID type, TypeID toType) {
      this.member = member;
      this.type = type;
      this.toType = toType;
   }

   @Override
   public CodePosition getPosition() {
      return this.member.position;
   }

   @Override
   public TypeID getOwnerType() {
      return this.type;
   }

   @Override
   public String describe() {
      return this.member.describe();
   }

   @Override
   public <T extends Tag> T getTag(Class<T> type) {
      return this.member.getTag(type);
   }

   public Expression cast(CodePosition position, Expression value, boolean implicit) {
      return new CastExpression(position, value, this, implicit);
   }

   public boolean isImplicit() {
      return Modifiers.isImplicit(this.member.getSpecifiedModifiers());
   }

   @Override
   public DefinitionMemberRef getOverrides() {
      return this.member.getOverrides();
   }

   @Override
   public FunctionHeader getHeader() {
      return this.member.header;
   }

   @Override
   public MemberAnnotation[] getAnnotations() {
      return this.member.annotations;
   }

   @Override
   public IDefinitionMember getTarget() {
      return this.member;
   }
}
