package org.openzen.zenscript.codemodel.type;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.openzen.zenscript.codemodel.generic.TypeParameter;

public class InferenceBlockingTypeParameterVisitor implements TypeVisitor<Boolean> {
   private final TypeParameter[] parameters;
   private final Map<TypeID, Boolean> visitedTypes;

   public InferenceBlockingTypeParameterVisitor(TypeParameter[] parameters) {
      this.parameters = parameters;
      this.visitedTypes = new HashMap<>();
   }

   public Boolean visitBasic(BasicTypeID basic) {
      Optional<Boolean> knownResult = this.getKnownResult(basic);
      if (knownResult.isPresent()) {
         return knownResult.get();
      }

      this.visitedTypes.put(basic, false);
      return false;
   }

   public Boolean visitArray(ArrayTypeID array) {
      Optional<Boolean> knownResult = this.getKnownResult(array);
      if (knownResult.isPresent()) {
         return knownResult.get();
      } else {
         this.visitedTypes.put(array, false);
         TypeID elementType = array.elementType;
         Optional<Boolean> knownElementType = this.getKnownResult(elementType);
         if (knownElementType.isPresent()) {
            this.visitedTypes.put(array, knownElementType.get());
            return knownElementType.get();
         } else {
            this.visitedTypes.putIfAbsent(elementType, false);
            Boolean blocking = elementType.accept(this);
            this.visitedTypes.put(elementType, blocking);
            this.visitedTypes.put(array, blocking);
            return blocking;
         }
      }
   }

   public Boolean visitAssoc(AssocTypeID assoc) {
      Optional<Boolean> knownResult = this.getKnownResult(assoc);
      if (knownResult.isPresent()) {
         return knownResult.get();
      } else {
         this.visitedTypes.put(assoc, false);
         TypeID keyType = assoc.keyType;
         Optional<Boolean> knownKeyType = this.getKnownResult(keyType);
         if (knownKeyType.isPresent()) {
            this.visitedTypes.put(assoc, knownKeyType.get());
            return knownKeyType.get();
         } else {
            this.visitedTypes.putIfAbsent(keyType, false);
            Boolean keyBlocking = keyType.accept(this);
            this.visitedTypes.put(keyType, keyBlocking);
            this.visitedTypes.put(assoc, keyBlocking);
            if (keyBlocking) {
               return true;
            } else {
               TypeID valueType = assoc.valueType;
               Optional<Boolean> knownValueType = this.getKnownResult(valueType);
               if (knownValueType.isPresent()) {
                  this.visitedTypes.put(assoc, knownValueType.get());
                  return knownValueType.get();
               } else {
                  this.visitedTypes.putIfAbsent(valueType, false);
                  Boolean valueBlocking = valueType.accept(this);
                  this.visitedTypes.put(valueType, valueBlocking);
                  this.visitedTypes.put(assoc, valueBlocking);
                  return valueBlocking;
               }
            }
         }
      }
   }

   public Boolean visitGenericMap(GenericMapTypeID map) {
      Optional<Boolean> knownResult = this.getKnownResult(map);
      if (knownResult.isPresent()) {
         return knownResult.get();
      } else {
         this.visitedTypes.put(map, false);
         TypeID value = map.value;
         Optional<Boolean> knownValue = this.getKnownResult(value);
         if (knownValue.isPresent()) {
            this.visitedTypes.put(map, knownValue.get());
            return knownValue.get();
         } else {
            this.visitedTypes.putIfAbsent(value, false);
            Boolean blocking = value.accept(this);
            this.visitedTypes.put(value, blocking);
            this.visitedTypes.put(map, blocking);
            return blocking;
         }
      }
   }

   public Boolean visitIterator(IteratorTypeID iterator) {
      Optional<Boolean> knownResult = this.getKnownResult(iterator);
      if (knownResult.isPresent()) {
         return knownResult.get();
      }

      this.visitedTypes.put(iterator, false);

      for (TypeID type : iterator.iteratorTypes) {
         Optional<Boolean> knownType = this.getKnownResult(type);
         if (knownType.isPresent() && knownType.get()) {
            this.visitedTypes.put(iterator, true);
            return true;
         }

         this.visitedTypes.put(type, false);
         Boolean blocking = type.accept(this);
         this.visitedTypes.put(type, blocking);
         if (blocking) {
            this.visitedTypes.put(iterator, true);
            return true;
         }
      }

      return false;
   }

   public Boolean visitFunction(FunctionTypeID function) {
      Optional<Boolean> knownResult = this.getKnownResult(function);
      if (knownResult.isPresent()) {
         return knownResult.get();
      }

      this.visitedTypes.put(function, false);
      boolean blocking = function.header.hasInferenceBlockingTypeParameters(this.parameters);
      this.visitedTypes.put(function, blocking);
      return blocking;
   }

   public Boolean visitDefinition(DefinitionTypeID definition) {
      Optional<Boolean> knownResult = this.getKnownResult(definition);
      if (knownResult.isPresent()) {
         return knownResult.get();
      }

      this.visitedTypes.put(definition, false);
      if (definition.hasTypeParameters()) {
         for (TypeID typeArgument : definition.typeArguments) {
            Optional<Boolean> knownType = this.getKnownResult(typeArgument);
            if (knownType.isPresent() && knownType.get()) {
               this.visitedTypes.put(definition, true);
               return true;
            }

            this.visitedTypes.put(typeArgument, false);
            boolean blocking = typeArgument.accept(this);
            this.visitedTypes.put(typeArgument, blocking);
            if (blocking) {
               this.visitedTypes.put(definition, true);
               return true;
            }
         }
      }

      TypeID superType = definition.definition.getSuperType();
      if (superType != null) {
         Optional<Boolean> knownSuperType = this.getKnownResult(superType);
         if (knownSuperType.isPresent() && knownSuperType.get()) {
            this.visitedTypes.put(definition, true);
            return true;
         }

         this.visitedTypes.put(superType, false);
         Boolean blocking = superType.accept(this);
         this.visitedTypes.put(superType, blocking);
         if (blocking) {
            this.visitedTypes.put(definition, true);
            return true;
         }
      }

      return false;
   }

   public Boolean visitGeneric(GenericTypeID generic) {
      Optional<Boolean> knownResult = this.getKnownResult(generic);
      if (knownResult.isPresent()) {
         return knownResult.get();
      }

      for (TypeParameter parameter : this.parameters) {
         if (parameter == generic.parameter) {
            this.visitedTypes.put(generic, true);
            return true;
         }
      }

      this.visitedTypes.put(generic, false);
      return false;
   }

   public Boolean visitRange(RangeTypeID range) {
      Optional<Boolean> knownResult = this.getKnownResult(range);
      if (knownResult.isPresent()) {
         return knownResult.get();
      } else {
         this.visitedTypes.put(range, false);
         TypeID baseType = range.baseType;
         Optional<Boolean> knownElementType = this.getKnownResult(baseType);
         if (knownElementType.isPresent()) {
            this.visitedTypes.put(range, knownElementType.get());
            return knownElementType.get();
         } else {
            this.visitedTypes.putIfAbsent(baseType, false);
            Boolean blocking = baseType.accept(this);
            this.visitedTypes.put(baseType, blocking);
            this.visitedTypes.put(range, blocking);
            return blocking;
         }
      }
   }

   public Boolean visitOptional(OptionalTypeID type) {
      Optional<Boolean> knownResult = this.getKnownResult(type);
      if (knownResult.isPresent()) {
         return knownResult.get();
      } else {
         this.visitedTypes.put(type, false);
         TypeID baseType = type.baseType;
         Optional<Boolean> knownElementType = this.getKnownResult(baseType);
         if (knownElementType.isPresent()) {
            this.visitedTypes.put(type, knownElementType.get());
            return knownElementType.get();
         } else {
            this.visitedTypes.putIfAbsent(baseType, false);
            Boolean blocking = baseType.accept(this);
            this.visitedTypes.put(baseType, blocking);
            this.visitedTypes.put(type, blocking);
            return blocking;
         }
      }
   }

   public Boolean visitInvalid(InvalidTypeID type) {
      Optional<Boolean> knownResult = this.getKnownResult(type);
      if (knownResult.isPresent()) {
         return knownResult.get();
      }

      this.visitedTypes.put(type, false);
      return false;
   }

   private Optional<Boolean> getKnownResult(TypeID type) {
      return this.visitedTypes.containsKey(type) ? Optional.of(this.visitedTypes.get(type)) : Optional.empty();
   }
}
