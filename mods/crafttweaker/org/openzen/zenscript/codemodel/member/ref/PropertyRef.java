package org.openzen.zenscript.codemodel.member.ref;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.Tag;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.annotations.MemberAnnotation;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.PropertyMember;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;

public abstract class PropertyRef implements DefinitionMemberRef {
   private final PropertyMember member;
   private final TypeID owner;
   private TypeID type;
   private GenericMapper mapper;

   public PropertyRef(TypeID owner, PropertyMember member, GenericMapper mapper) {
      this.owner = owner;
      this.member = member;
      if (member.getType() == BasicTypeID.UNDETERMINED) {
         this.type = null;
         this.mapper = mapper;
      } else {
         this.type = mapper == null ? member.getType() : member.getType().instance(mapper);
         this.mapper = null;
      }
   }

   @Override
   public final TypeID getOwnerType() {
      return this.owner;
   }

   public final TypeID getType() {
      if (this.type == null) {
         this.type = this.mapper == null ? this.member.getType() : this.member.getType().instance(this.mapper);
         this.mapper = null;
      }

      return this.type;
   }

   @Override
   public final CodePosition getPosition() {
      return this.member.getPosition();
   }

   @Override
   public final String describe() {
      return this.member.describe();
   }

   @Override
   public final <T extends Tag> T getTag(Class<T> type) {
      return this.member.getTag(type);
   }

   public final boolean isStatic() {
      return this.member.isStatic();
   }

   public final boolean isFinal() {
      return this.member.isFinal();
   }

   @Override
   public final FunctionHeader getHeader() {
      return null;
   }

   @Override
   public final MemberAnnotation[] getAnnotations() {
      return this.member.getAnnotations();
   }

   @Override
   public final IDefinitionMember getTarget() {
      return this.member;
   }
}
