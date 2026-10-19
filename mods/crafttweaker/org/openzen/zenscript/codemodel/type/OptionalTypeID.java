package org.openzen.zenscript.codemodel.type;

import java.util.List;
import java.util.Objects;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.NullExpression;
import org.openzen.zenscript.codemodel.generic.TypeParameter;

public class OptionalTypeID implements TypeID {
   public final TypeID baseType;
   private final TypeID normalized;

   public OptionalTypeID(GlobalTypeRegistry registry, TypeID baseType) {
      this.baseType = baseType;
      this.normalized = baseType.getNormalized() == baseType ? this : registry.getOptional(baseType.getNormalized());
   }

   @Override
   public Expression getDefaultValue() {
      return new NullExpression(CodePosition.UNKNOWN);
   }

   @Override
   public TypeID getNormalized() {
      return this.normalized;
   }

   @Override
   public TypeID instance(GenericMapper mapper) {
      TypeID base = this.baseType.instance(mapper);
      return mapper.registry.getOptional(base);
   }

   @Override
   public <R> R accept(TypeVisitor<R> visitor) {
      return visitor.visitOptional(this);
   }

   @Override
   public <C, R, E extends Exception> R accept(C context, TypeVisitorWithContext<C, R, E> visitor) throws E {
      return visitor.visitOptional(context, this);
   }

   @Override
   public boolean isOptional() {
      return true;
   }

   @Override
   public boolean isValueType() {
      return this.baseType.isValueType();
   }

   @Override
   public TypeID withoutOptional() {
      return this.baseType;
   }

   @Override
   public boolean hasDefaultValue() {
      return this.isOptional() || this.baseType.hasDefaultValue();
   }

   @Override
   public void extractTypeParameters(List<TypeParameter> typeParameters) {
      this.baseType.extractTypeParameters(typeParameters);
   }

   @Override
   public int hashCode() {
      int hash = 3;
      return 79 * hash + Objects.hashCode(this.baseType);
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

      OptionalTypeID other = (OptionalTypeID)obj;
      return this.baseType == other.baseType;
   }

   @Override
   public String toString() {
      return this.baseType.toString() + "?";
   }

   @Override
   public boolean isGeneric() {
      return this.baseType.isGeneric();
   }
}
