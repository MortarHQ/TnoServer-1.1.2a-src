package org.openzen.zenscript.codemodel.type;

import java.util.Arrays;
import java.util.List;
import org.openzen.zenscript.codemodel.GenericMapper;
import org.openzen.zenscript.codemodel.generic.TypeParameter;

public class IteratorTypeID implements TypeID {
   public final TypeID[] iteratorTypes;
   private final IteratorTypeID normalized;

   public IteratorTypeID(GlobalTypeRegistry registry, TypeID[] iteratorTypes) {
      this.iteratorTypes = iteratorTypes;
      this.normalized = this.isDenormalized() ? this.normalize(registry) : this;
   }

   public IteratorTypeID getNormalized() {
      return this.normalized;
   }

   private boolean isDenormalized() {
      for (TypeID type : this.iteratorTypes) {
         if (type.getNormalized() != type) {
            return true;
         }
      }

      return false;
   }

   private IteratorTypeID normalize(GlobalTypeRegistry registry) {
      TypeID[] normalizedTypes = new TypeID[this.iteratorTypes.length];

      for (int i = 0; i < normalizedTypes.length; i++) {
         normalizedTypes[i] = this.iteratorTypes[i].getNormalized();
      }

      return registry.getIterator(normalizedTypes);
   }

   @Override
   public <R> R accept(TypeVisitor<R> visitor) {
      return visitor.visitIterator(this);
   }

   @Override
   public <C, R, E extends Exception> R accept(C context, TypeVisitorWithContext<C, R, E> visitor) throws E {
      return visitor.visitIterator(context, this);
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
   public TypeID instance(GenericMapper mapper) {
      TypeID[] instanced = mapper.map(this.iteratorTypes);
      return mapper.registry.getIterator(instanced);
   }

   @Override
   public boolean hasDefaultValue() {
      return false;
   }

   @Override
   public void extractTypeParameters(List<TypeParameter> typeParameters) {
      for (TypeID type : this.iteratorTypes) {
         type.extractTypeParameters(typeParameters);
      }
   }

   @Override
   public int hashCode() {
      int hash = 5;
      return 13 * hash + Arrays.deepHashCode(this.iteratorTypes);
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

      IteratorTypeID other = (IteratorTypeID)obj;
      return Arrays.deepEquals(this.iteratorTypes, other.iteratorTypes);
   }
}
