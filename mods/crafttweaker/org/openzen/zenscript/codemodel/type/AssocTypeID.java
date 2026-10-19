package org.openzen.zenscript.codemodel.type;

import java.util.List;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.generic.TypeParameter;

public class AssocTypeID implements TypeID {
   public final TypeID keyType;
   public final TypeID valueType;
   private final AssocTypeID normalized;

   public AssocTypeID(GlobalTypeRegistry typeRegistry, TypeID keyType, TypeID valueType) {
      this.keyType = keyType;
      this.valueType = valueType;
      if (keyType == keyType.getNormalized() && valueType == valueType.getNormalized()) {
         this.normalized = this;
      } else {
         this.normalized = typeRegistry.getAssociative(keyType.getNormalized(), valueType.getNormalized());
      }
   }

   @Override
   public TypeID instance(GenericMapper mapper) {
      return mapper.registry.getAssociative(this.keyType.instance(mapper), this.valueType.instance(mapper));
   }

   public AssocTypeID getNormalized() {
      return this.normalized;
   }

   @Override
   public <R> R accept(TypeVisitor<R> visitor) {
      return visitor.visitAssoc(this);
   }

   @Override
   public <C, R, E extends Exception> R accept(C context, TypeVisitorWithContext<C, R, E> visitor) throws E {
      return visitor.visitAssoc(context, this);
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
   public boolean hasDefaultValue() {
      return true;
   }

   @Override
   public void extractTypeParameters(List<TypeParameter> typeParameters) {
      this.keyType.extractTypeParameters(typeParameters);
      this.valueType.extractTypeParameters(typeParameters);
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 29 * hash + this.keyType.hashCode();
      return 29 * hash + this.valueType.hashCode();
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

      AssocTypeID other = (AssocTypeID)obj;
      return this.keyType.equals(other.keyType) && this.valueType.equals(other.valueType);
   }

   @Override
   public String toString() {
      return this.valueType.toString() + "[" + this.keyType.toString() + "]";
   }
}
