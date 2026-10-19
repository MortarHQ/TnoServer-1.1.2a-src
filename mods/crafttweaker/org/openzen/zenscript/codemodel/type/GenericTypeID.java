package org.openzen.zenscript.codemodel.type;

import java.util.List;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.type.member.LocalMemberCache;

public class GenericTypeID implements TypeID {
   public final TypeParameter parameter;

   public GenericTypeID(TypeParameter parameter) {
      this.parameter = parameter;
   }

   public boolean matches(LocalMemberCache cache, TypeID type) {
      return this.parameter.matches(cache, type);
   }

   public GenericTypeID getNormalized() {
      return this;
   }

   @Override
   public TypeID instance(GenericMapper mapper) {
      return mapper.mapGeneric(this);
   }

   @Override
   public <R> R accept(TypeVisitor<R> visitor) {
      return visitor.visitGeneric(this);
   }

   @Override
   public <C, R, E extends Exception> R accept(C context, TypeVisitorWithContext<C, R, E> visitor) throws E {
      return visitor.visitGeneric(context, this);
   }

   @Override
   public boolean isOptional() {
      return false;
   }

   @Override
   public boolean isValueType() {
      return false;
   }

   @Override
   public boolean isGeneric() {
      return true;
   }

   @Override
   public boolean hasDefaultValue() {
      return false;
   }

   @Override
   public void extractTypeParameters(List<TypeParameter> typeParameters) {
      if (!typeParameters.contains(this.parameter)) {
         typeParameters.add(this.parameter);
      }
   }

   @Override
   public int hashCode() {
      int hash = 7;
      return 47 * hash + this.parameter.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }

      if (obj == null) {
         return false;
      }

      if (this.getClass() != obj.getClass()) {
         return false;
      }

      GenericTypeID other = (GenericTypeID)obj;
      return this.parameter.equals(other.parameter);
   }

   @Override
   public String toString() {
      return this.parameter.toString();
   }
}
