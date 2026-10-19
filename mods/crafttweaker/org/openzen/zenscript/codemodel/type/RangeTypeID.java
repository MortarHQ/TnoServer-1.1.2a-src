package org.openzen.zenscript.codemodel.type;

import java.util.List;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.generic.TypeParameter;

public class RangeTypeID implements TypeID {
   public static final RangeTypeID INT = new RangeTypeID(null, BasicTypeID.INT);
   public static final RangeTypeID USIZE = new RangeTypeID(null, BasicTypeID.USIZE);
   public final TypeID baseType;
   private final RangeTypeID normalized;

   public RangeTypeID(GlobalTypeRegistry registry, TypeID baseType) {
      this.baseType = baseType;
      if (baseType.getNormalized().equals(baseType)) {
         this.normalized = this;
      } else {
         this.normalized = registry.getRange(baseType.getNormalized());
      }
   }

   public RangeTypeID getNormalized() {
      return this.normalized;
   }

   @Override
   public TypeID instance(GenericMapper mapper) {
      return mapper.registry.getRange(this.baseType.instance(mapper));
   }

   @Override
   public <R> R accept(TypeVisitor<R> visitor) {
      return visitor.visitRange(this);
   }

   @Override
   public <C, R, E extends Exception> R accept(C context, TypeVisitorWithContext<C, R, E> visitor) throws E {
      return visitor.visitRange(context, this);
   }

   @Override
   public boolean isOptional() {
      return false;
   }

   @Override
   public boolean isValueType() {
      return this.baseType.isValueType();
   }

   @Override
   public boolean hasDefaultValue() {
      return false;
   }

   @Override
   public void extractTypeParameters(List<TypeParameter> typeParameters) {
      this.baseType.extractTypeParameters(typeParameters);
   }

   @Override
   public int hashCode() {
      int hash = 5;
      return 89 * hash + this.baseType.hashCode();
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

      RangeTypeID other = (RangeTypeID)obj;
      return this.baseType.equals(other.baseType);
   }

   @Override
   public String toString() {
      return this.baseType.toString() + " .. " + this.baseType.toString();
   }
}
