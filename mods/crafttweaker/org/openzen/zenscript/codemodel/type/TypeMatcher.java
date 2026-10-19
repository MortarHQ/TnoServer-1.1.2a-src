package org.openzen.zenscript.codemodel.type;

import java.util.HashMap;
import java.util.Map;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.type.member.LocalMemberCache;

public class TypeMatcher implements TypeVisitorWithContext<TypeMatcher.Matching, Boolean, RuntimeException> {
   private static final TypeMatcher INSTANCE = new TypeMatcher();

   private TypeMatcher() {
   }

   public static Map<TypeParameter, TypeID> match(LocalMemberCache cache, TypeID type, TypeID pattern) {
      TypeMatcher.Matching matching = new TypeMatcher.Matching(cache, type);
      return pattern.accept(matching, INSTANCE) ? matching.mapping : null;
   }

   public Boolean visitBasic(TypeMatcher.Matching context, BasicTypeID basic) {
      return context.type == basic;
   }

   public Boolean visitArray(TypeMatcher.Matching context, ArrayTypeID array) {
      if (context.type instanceof ArrayTypeID arrayType) {
         return arrayType.dimension != array.dimension ? false : this.match(context, arrayType.elementType, array.elementType);
      } else {
         return false;
      }
   }

   public Boolean visitAssoc(TypeMatcher.Matching context, AssocTypeID assoc) {
      return !(context.type instanceof AssocTypeID assocType)
         ? false
         : this.match(context, assocType.keyType, assoc.keyType) && this.match(context, assocType.valueType, assoc.valueType);
   }

   public Boolean visitInvalid(TypeMatcher.Matching context, InvalidTypeID invalid) {
      return false;
   }

   public Boolean visitIterator(TypeMatcher.Matching context, IteratorTypeID iterator) {
      if (!(context.type instanceof IteratorTypeID iteratorType)) {
         return false;
      } else {
         if (iteratorType.iteratorTypes.length != iterator.iteratorTypes.length) {
            return false;
         }

         boolean result = true;

         for (int i = 0; i < iteratorType.iteratorTypes.length; i++) {
            result = result && this.match(context, iterator.iteratorTypes[i], iteratorType.iteratorTypes[i]);
         }

         return result;
      }
   }

   public Boolean visitFunction(TypeMatcher.Matching context, FunctionTypeID function) {
      if (context.type instanceof FunctionTypeID functionType) {
         if (functionType.header.parameters.length != function.header.parameters.length) {
            return false;
         }

         if (!this.match(context, functionType.header.getReturnType(), function.header.getReturnType())) {
            return false;
         }

         for (int i = 0; i < function.header.parameters.length; i++) {
            if (!this.match(context, functionType.header.parameters[i].type, function.header.parameters[i].type)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public Boolean visitDefinition(TypeMatcher.Matching context, DefinitionTypeID definition) {
      if (context.type instanceof DefinitionTypeID definitionType) {
         if (definitionType.definition != definition.definition) {
            return false;
         }

         if (definition.typeArguments != null) {
            for (int i = 0; i < definitionType.typeArguments.length; i++) {
               if (!this.match(context, definitionType.typeArguments[i], definition.typeArguments[i])) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public Boolean visitGeneric(TypeMatcher.Matching context, GenericTypeID generic) {
      if (context.mapping.containsKey(generic.parameter)) {
         TypeID argument = context.mapping.get(generic.parameter);
         return argument == context.type;
      }

      if (context.type != generic && !generic.matches(context.cache, context.type)) {
         return false;
      }

      context.mapping.put(generic.parameter, context.type);
      return true;
   }

   public Boolean visitRange(TypeMatcher.Matching context, RangeTypeID range) {
      return context.type instanceof RangeTypeID rangeType ? this.match(context, rangeType.baseType, range.baseType) : false;
   }

   public Boolean visitOptional(TypeMatcher.Matching context, OptionalTypeID type) {
      return context.type instanceof OptionalTypeID modified ? this.match(context, modified.baseType, type.baseType) : false;
   }

   private boolean match(TypeMatcher.Matching context, TypeID type, TypeID pattern) {
      return pattern.accept(context.withType(type), this);
   }

   public Boolean visitGenericMap(TypeMatcher.Matching context, GenericMapTypeID map) {
      return map == context.type;
   }

   public static final class Matching {
      public final LocalMemberCache cache;
      public final TypeID type;
      public final Map<TypeParameter, TypeID> mapping;

      public Matching(LocalMemberCache cache, TypeID type) {
         this.cache = cache;
         this.type = type;
         this.mapping = new HashMap<>();
      }

      private Matching(LocalMemberCache cache, TypeID type, Map<TypeParameter, TypeID> mapping) {
         this.cache = cache;
         this.type = type;
         this.mapping = mapping;
      }

      public TypeMatcher.Matching withType(TypeID type) {
         return new TypeMatcher.Matching(this.cache, type, this.mapping);
      }
   }
}
