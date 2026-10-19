package org.openzen.zenscript.codemodel.type;

import java.util.Arrays;
import java.util.List;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.generic.TypeParameter;

public class FunctionTypeID implements TypeID {
   public final FunctionHeader header;
   protected final FunctionTypeID normalized;

   public FunctionTypeID(GlobalTypeRegistry registry, FunctionHeader header) {
      this.header = header;
      FunctionHeader normalizedHeader = header.normalize(registry);
      this.normalized = header == normalizedHeader ? this : registry.getFunction(normalizedHeader);
   }

   public FunctionTypeID getNormalized() {
      return this.normalized;
   }

   @Override
   public TypeID instance(GenericMapper mapper) {
      return mapper.registry.getFunction(mapper.map(this.header));
   }

   @Override
   public <R> R accept(TypeVisitor<R> visitor) {
      return visitor.visitFunction(this);
   }

   @Override
   public <C, R, E extends Exception> R accept(C context, TypeVisitorWithContext<C, R, E> visitor) throws E {
      return visitor.visitFunction(context, this);
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
      return false;
   }

   @Override
   public void extractTypeParameters(List<TypeParameter> typeParameters) {
      this.header.getReturnType().extractTypeParameters(typeParameters);

      for (FunctionParameter parameter : this.header.parameters) {
         parameter.type.extractTypeParameters(typeParameters);
      }
   }

   @Override
   public int hashCode() {
      int hash = 5;
      hash = 71 * hash + this.header.getReturnType().hashCode();
      hash = 71 * hash + Arrays.deepHashCode(this.header.parameters);
      return 71 * hash + Arrays.deepHashCode(this.header.typeParameters);
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

      FunctionTypeID other = (FunctionTypeID)obj;
      return this.header.getReturnType().equals(other.header.getReturnType())
         && Arrays.deepEquals(this.header.parameters, other.header.parameters)
         && Arrays.deepEquals(this.header.typeParameters, other.header.typeParameters);
   }

   @Override
   public String toString() {
      StringBuilder result = new StringBuilder();
      result.append("function");
      if (this.header.typeParameters.length > 0) {
         result.append('<');

         for (int i = 0; i < this.header.typeParameters.length; i++) {
            if (i > 0) {
               result.append(", ");
            }

            result.append(this.header.typeParameters[i].toString());
         }

         result.append('>');
      }

      result.append('(');

      for (int i = 0; i < this.header.parameters.length; i++) {
         if (i > 0) {
            result.append(", ");
         }

         FunctionParameter parameter = this.header.parameters[i];
         result.append(parameter.name);
         result.append(" as ");
         result.append(parameter.type);
      }

      result.append(')');
      result.append(" as ");
      result.append(this.header.getReturnType());
      return result.toString();
   }
}
